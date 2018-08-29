package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_DeliverResp;

import java.util.Map;

public class CNGPSMSProxy {
    private CNGPConnection conn;

    public CNGPSMSProxy(Map args) {
        this(new Parameter(args));
    }

    public CNGPSMSProxy(Parameter args) {
        this.conn = new CNGPConnection(args);
        this.conn.addEventListener(new CNGPEventListener(this));
        this.conn.waitAvailable();
        if (!this.conn.available()) {
            throw new IllegalStateException(this.conn.getError());
        }
    }

    public CNGP_Command send(CNGP_Command message) throws Exception {
        if (message == null) {
            return null;
        } else {
            CNGPTransaction t = (CNGPTransaction)this.conn.createChild();

            CNGP_Command var6;
            try {
                t.send(message);
                t.waitResponse();
                CNGP_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public CNGP_Command onDeliver(CNGP_Deliver msg) {
        return new CNGP_DeliverResp(msg, 0);
    }

    public void close() {
        this.conn.close();
    }

    public CNGPConnection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }
}

