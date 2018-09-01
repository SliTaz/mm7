package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class CNGPConnection extends SMSSocketConnection {
    private int degree = 0;
    private int hbnoResponseOut = 3;
    private String clientid = null;
    private int loginMode = 2;
    private int version;
    private String shared_secret;

    public CNGPConnection(Parameter args) {
        this.hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        this.clientid = args.get("clientid", "aceway");
        this.loginMode = args.get("loginmode", 2);
        this.version = args.get("version", 1);
        this.shared_secret = args.get("shared-secret", "");
        CNGPConstant.debug = args.get("debug", false);
        CNGPConstant.initConstant(this.getResource());
        this.init(args);
    }

    protected SMSWriter getWriter(Socket out) {
        return new CNGPWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new CNGPReader(in);
    }

    public int getChildId(Object message) {
        CNGP_Command mes = (CNGP_Command)message;
        int sequenceId = mes.getSeqID();
        return mes.getCommandID() != 3 && mes.getCommandID() != 4 && mes.getCommandID() != 6 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new CNGPTransaction(this);
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
            CNGP_Unbind msg = new CNGP_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
    }

    protected void heartbeat() throws Exception {
        CNGPTransaction t = (CNGPTransaction)this.createChild();
        CNGP_ActiveTest hbmes = new CNGP_ActiveTest();
        t.send(hbmes);
        t.waitResponse();
        CNGP_ActiveTestResp rsp = (CNGP_ActiveTestResp)t.getResponse();
        if (rsp == null) {
            ++this.degree;
            if (this.degree == this.hbnoResponseOut) {
                this.degree = 0;
                throw new IOException(CNGPConstant.HEARTBEAT_ABNORMITY);
            }
        } else {
            this.degree = 0;
        }

        t.close();
    }

    protected synchronized void connect() {
        super.connect();
        if (this.available()) {
            CNGP_Bind request = null;
            CNGP_BindResp rsp = null;

            try {
                request = new CNGP_Bind(this.clientid, this.shared_secret, this.loginMode, new Date(), this.version);
            } catch (IllegalArgumentException var6) {
                var6.printStackTrace();
                this.close();
                this.setError(CNGPConstant.CONNECT_INPUT_ERROR);
            }

            CNGPTransaction t = (CNGPTransaction)this.createChild();

            try {
                t.send(request);
                Object m = super.in.read();
                this.onReceive(m);
            } catch (Exception var5) {
                var5.printStackTrace();
                this.close();
                this.setError(CNGPConstant.LOGIN_ERROR + this.explain(var5));
            }

            rsp = (CNGP_BindResp)t.getResponse();
            if (rsp == null) {
                this.close();
                this.setError(CNGPConstant.CONNECT_TIMEOUT);
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

