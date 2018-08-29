package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSListenSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Bind;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_BindResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Unbind;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class SGIPConnection extends SMSListenSocketConnection {
    private boolean asClient;
    private String login_name;
    private String login_pass;
    private long src_nodeid;
    private String ipaddr;
    private Map connmap;
    private Logger log = Logger.getLogger(SGIPConnection.class);
    private boolean blockSend;

    public SGIPConnection(Parameter args, boolean ifasClient, Map connmap) {
        this.login_name = args.get("login-name", "");
        this.login_pass = args.get("login-pass", "");
        this.src_nodeid = args.get("source-addr", 0L);
        this.connmap = connmap;
        this.asClient = ifasClient;
        if (this.asClient) {
            this.init(args);
        }

        this.blockSend = args.get("block-send", true);
        SGIPConstant.debug = args.get("debug", false);
        SGIPConstant.initConstant(this.getResource());
    }

    public synchronized void attach(Parameter args, Socket socket) {
        if (this.asClient) {
            throw new UnsupportedOperationException("Client socket can not accept connection");
        } else {
            this.init(args, socket);
            this.ipaddr = socket.getInetAddress().getHostAddress();
            this.port = socket.getPort();
        }
    }

    protected void onReadTimeOut() {
        this.close();
    }

    public int getChildId(Object message) {
        SGIP_Command mes = (SGIP_Command) message;
        int sequenceId = mes.getHead().GetSeq_3();
        return (this.asClient || mes.getCommandID() != 1) && mes.getCommandID() != 4 && mes.getCommandID() != 5 && mes.getCommandID() != 2 && mes.getCommandID() != 17 && (this.blockSend || mes.getCommandID() != -2147483645) && mes.getCommandID() != 3 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new SGIPTransaction(this);
    }

    public int getTransactionTimeout() {
        return super.transactionTimeout;
    }

    public Resource getResource() {
        try {
            Resource resource = new Resource(this.getClass(), "resource");
            return resource;
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public synchronized void waitAvailable() {
        try {
            if (this.getError() == SMSListenSocketConnection.NOT_INIT) {
                this.wait((long) super.transactionTimeout);
            }
        } catch (InterruptedException var2) {
            ;
        }

    }

    public void close() {
        this.log.info("close connection. IP address " + this.host + ":" + this.port);
        SGIPTransaction t = (SGIPTransaction) this.createChild();

        try {
            SGIP_Unbind msg = new SGIP_Unbind(this.src_nodeid);
            t.send(msg);
        } catch (Exception var6) {
            ;
        } finally {
            t.close();
        }

        super.close();
        if (!this.asClient && this.connmap != null) {
            this.connmap.remove(this.ipaddr + this.port);
        }

    }

    protected void heartbeat() throws IOException {
    }

    protected synchronized void connect() {
        super.connect();
        if (this.available()) {
            SGIP_Bind request = null;
            SGIP_BindResp rsp = null;
            request = new SGIP_Bind(this.src_nodeid, 1, this.login_name, this.login_pass);
            SGIPTransaction t = (SGIPTransaction) this.createChild();

            try {
                t.send(request);
                Object m = super.in.read();
                this.onReceive(m);
            } catch (IOException var5) {
                var5.printStackTrace();
                this.close();
                this.setError(SGIPConstant.LOGIN_ERROR + this.explain(var5));
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            rsp = (SGIP_BindResp) t.getResponse();
            if (rsp == null) {
                this.close();
                this.setError(SGIPConstant.CONNECT_TIMEOUT);
            }

            t.close();
            if (rsp != null && rsp.getResult() != 0) {
                this.close();
                if (rsp.getResult() == 1) {
                    this.setError(SGIPConstant.NONLICETSP_LOGNAME);
                } else {
                    this.setError(SGIPConstant.OTHER_ERROR);
                }
            }

            this.notifyAll();
        }
    }

    protected SMSReader getReader(Socket in) {
        return new SGIPReader(in);
    }

    protected SMSWriter getWriter(Socket out) {
        return new SGIPWriter(out);
    }
}