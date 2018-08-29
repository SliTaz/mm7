package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class SMGPConnection extends SMSSocketConnection {
    private int degree = 0;
    private int hbnoResponseOut = 3;
    private String clientid = null;
    private int loginMode = 2;
    private int version;
    private String shared_secret;
    private boolean blockSend = true;

    public SMGPConnection(Parameter args) {
        this.hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        this.clientid = args.get("clientid", "aceway");
        this.loginMode = args.get("loginmode", 2);
        this.version = args.get("version", 1);
        this.shared_secret = args.get("shared-secret", "");
        this.blockSend = args.get("block-send", true);
        SMGPConstant.debug = args.get("debug", false);
        SMGPConstant.initConstant(this.getResource());
        this.init(args);
    }

    public SMGPConnection(Parameter args, Socket socket) {
        this.hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        this.clientid = args.get("clientid", "aceway");
        this.loginMode = args.get("loginmode", 2);
        this.version = args.get("version", 1);
        this.shared_secret = args.get("shared-secret", "");
        this.blockSend = args.get("block-send", true);
        SMGPConstant.debug = args.get("debug", false);
        SMGPConstant.initConstant(this.getResource());
        this.init(args, socket);
    }

    protected SMSWriter getWriter(Socket out) {
        return new SMGPWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new SMGPReader(in);
    }

    public int getChildId(Object message) {
        SMGP_Command mes = (SMGP_Command)message;
        int sequenceId = mes.getSeqID();
        int cad = mes.getCommandID();
        return cad != 3 && cad != 6 && cad != 4 && cad != 2 && cad != 1 && (cad != -2147483646 || this.blockSend) ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new SMGPTransaction(this);
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
            if (this.getError() == NOT_INIT) {
                this.wait((long)super.transactionTimeout);
            }
        } catch (InterruptedException var2) {
            ;
        }

    }

    public void close() {
        try {
            SMGP_Unbind msg = new SMGP_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
    }

    protected void heartbeat() throws Exception {
        SMGPTransaction t = (SMGPTransaction)this.createChild();
        SMGP_ActiveTest hbmes = new SMGP_ActiveTest();
        t.send(hbmes);
        t.waitResponse();
        SMGP_ActiveTestResp rsp = (SMGP_ActiveTestResp)t.getResponse();
        if (rsp == null) {
            ++this.degree;
            if (this.degree == this.hbnoResponseOut) {
                this.degree = 0;
                throw new IOException(SMGPConstant.HEARTBEAT_ABNORMITY);
            }
        } else {
            this.degree = 0;
        }

        t.close();
    }

    protected synchronized void connect() {
        super.connect();
        if (this.available()) {
            SMGP_Bind request = null;
            SMGP_BindResp rsp = null;

            try {
                request = new SMGP_Bind(this.clientid, this.shared_secret, this.loginMode, new Date(), this.version);
            } catch (IllegalArgumentException var6) {
                var6.printStackTrace();
                this.close();
                this.setError(SMGPConstant.CONNECT_INPUT_ERROR);
            }

            SMGPTransaction t = (SMGPTransaction)this.createChild();

            try {
                t.send(request);
                Object m = super.in.read();
                this.onReceive(m);
            } catch (Exception var5) {
                var5.printStackTrace();
                this.close();
                this.setError(SMGPConstant.LOGIN_ERROR + this.explain(var5));
            }

            rsp = (SMGP_BindResp)t.getResponse();
            if (rsp == null) {
                this.close();
                this.setError(SMGPConstant.CONNECT_TIMEOUT);
            }

            t.close();
            if (rsp != null && rsp.getStatus() != 0) {
                this.close();
                this.setError("Fail to login,the status code id " + rsp.getStatus());
            }

            this.notifyAll();
        }
    }
}

