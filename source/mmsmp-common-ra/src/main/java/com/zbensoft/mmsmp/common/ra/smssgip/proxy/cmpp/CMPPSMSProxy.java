package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_DeliverResp;

import java.util.Map;

public class CMPPSMSProxy {
    private CMPPConnection conn;

    public CMPPSMSProxy(Map args) {
        this(new Parameter(args));
    }

    public CMPPSMSProxy(Parameter args) {
        this.conn = new CMPPConnection(args);
        this.conn.addEventListener(new CMPPEventListener(this));
        this.conn.waitAvailable();
        if (!this.conn.available()) {
            throw new IllegalStateException(this.conn.getError());
        }
    }

    public CMPP_Command send(CMPP_Command message) throws Exception {
        if (message == null) {
            return null;
        } else {
            CMPPTransaction t = (CMPPTransaction)this.conn.createChild();

            CMPP_Command var6;
            try {
                t.send(message);
                t.waitResponse();
                CMPP_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public CMPP_Command onDeliver(CMPP_Deliver msg) {
        return new CMPP_DeliverResp(msg);
    }

    public void close() {
        this.conn.close();
    }

    public CMPPConnection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }
}

