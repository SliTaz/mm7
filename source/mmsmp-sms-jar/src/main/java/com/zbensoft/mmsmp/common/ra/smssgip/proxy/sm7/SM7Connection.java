package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.*;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class SM7Connection extends SMSSocketConnection {
    private int degree = 0;
    private int hbnoResponseOut = 3;
    private String clientid = null;
    private int loginMode = 2;
    private int version;
    private String shared_secret;

    public SM7Connection(Parameter args) {
        this.hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        this.clientid = args.get("clientid", "aceway");
        this.loginMode = args.get("loginmode", 2);
        this.version = args.get("version", 1);
        this.shared_secret = args.get("shared-secret", "");
        Sm7Constant.debug = args.get("debug", false);
        Sm7Constant.initConstant(this.getResource());
        this.init(args);
    }

    protected SMSWriter getWriter(Socket out) {
        return new SM7PWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new SM7Reader(in);
    }

    public int getChildId(Object message) {
        SM7_Command mes = (SM7_Command)message;
        int sequenceId = mes.getSeqID();
        return mes.getCommandID() != 3 && mes.getCommandID() != 4 && mes.getCommandID() != 6 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new SM7Transaction(this);
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
            SM7_Unbind msg = new SM7_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
    }

    protected void heartbeat() throws Exception {
        SM7Transaction t = (SM7Transaction)this.createChild();
        SM7_ActiveTest hbmes = new SM7_ActiveTest();
        t.send(hbmes);
        t.waitResponse();
        SM7_ActiveTestResp rsp = (SM7_ActiveTestResp)t.getResponse();
        if (rsp == null) {
            ++this.degree;
            if (this.degree == this.hbnoResponseOut) {
                this.degree = 0;
                throw new IOException(Sm7Constant.HEARTBEAT_ABNORMITY);
            }
        } else {
            this.degree = 0;
        }

        t.close();
    }

    protected synchronized void connect() {
        super.connect();
        if (this.available()) {
            SM7_Bind request = null;
            SM7_BindResp rsp = null;

            try {
                request = new SM7_Bind(this.clientid, this.shared_secret, this.loginMode, new Date(), this.version);
            } catch (IllegalArgumentException var6) {
                var6.printStackTrace();
                this.close();
                this.setError(Sm7Constant.CONNECT_INPUT_ERROR);
            }

            SM7Transaction t = (SM7Transaction)this.createChild();

            try {
                t.send(request);
                Object m = super.in.read();
                this.onReceive(m);
            } catch (Exception var5) {
                var5.printStackTrace();
                this.close();
                this.setError(Sm7Constant.LOGIN_ERROR + this.explain(var5));
            }

            rsp = (SM7_BindResp)t.getResponse();
            if (rsp == null) {
                this.close();
                this.setError(Sm7Constant.CONNECT_TIMEOUT);
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
