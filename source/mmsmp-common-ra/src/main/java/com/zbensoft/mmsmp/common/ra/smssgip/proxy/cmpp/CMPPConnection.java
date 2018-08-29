package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class CMPPConnection extends SMSSocketConnection {
    private int degree = 0;
    private int hbnoResponseOut = 3;
    private String clientid = null;
    private int loginMode = 2;
    private int version;
    private String shared_secret;

    public CMPPConnection(Parameter args) {
        this.hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        this.clientid = args.get("clientid", "aceway");
        this.loginMode = args.get("loginmode", 2);
        this.version = args.get("version", 1);
        this.shared_secret = args.get("shared-secret", "");
        CMPPConstant.debug = args.get("debug", false);
        CMPPConstant.initConstant(this.getResource());
        this.init(args);
    }

    protected SMSWriter getWriter(Socket out) {
        return new CMPPWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new CMPPReader(in);
    }

    public int getChildId(Object message) {
        CMPP_Command mes = (CMPP_Command)message;
        int sequenceId = mes.getSeqID();
        return mes.getCommandID() != 5 && mes.getCommandID() != 8 && mes.getCommandID() != 2 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new CMPPTransaction(this);
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
            CMPP_Unbind msg = new CMPP_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
    }

    protected void heartbeat() throws Exception {
        CMPPTransaction t = (CMPPTransaction)this.createChild();
        CMPP_ActiveTest hbmes = new CMPP_ActiveTest();
        t.send(hbmes);
        t.waitResponse();
        CMPP_ActiveTestResp rsp = (CMPP_ActiveTestResp)t.getResponse();
        if (rsp == null) {
            ++this.degree;
            if (this.degree == this.hbnoResponseOut) {
                this.degree = 0;
                throw new IOException(CMPPConstant.HEARTBEAT_ABNORMITY);
            }
        } else {
            this.degree = 0;
        }

        t.close();
    }

    protected synchronized void connect() {
        super.connect();
        if (this.available()) {
            CMPP_Bind request = null;
            CMPP_BindResp rsp = null;

            try {
                request = new CMPP_Bind(this.clientid, this.version, this.shared_secret, new Date());
            } catch (IllegalArgumentException var6) {
                var6.printStackTrace();
                this.close();
                this.setError(CMPPConstant.CONNECT_INPUT_ERROR);
            }

            CMPPTransaction t = (CMPPTransaction)this.createChild();

            try {
                t.send(request);
                Object m = super.in.read();
                this.onReceive(m);
            } catch (Exception var5) {
                var5.printStackTrace();
                this.close();
                this.setError(CMPPConstant.LOGIN_ERROR + this.explain(var5));
            }

            rsp = (CMPP_BindResp)t.getResponse();
            if (rsp == null) {
                this.close();
                this.setError(CMPPConstant.CONNECT_TIMEOUT);
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

