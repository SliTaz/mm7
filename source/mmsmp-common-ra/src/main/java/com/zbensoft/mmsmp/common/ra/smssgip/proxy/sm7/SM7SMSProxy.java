package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;

import java.util.Map;

public class SM7SMSProxy {
    private SM7Connection conn;

    public SM7SMSProxy(Map args) {
        this(new Parameter(args));
    }

    public SM7SMSProxy(Parameter args) {
        this.conn = new SM7Connection(args);
        this.conn.addEventListener(new SM7EventListener(this));
        this.conn.waitAvailable();
        if (!this.conn.available()) {
            throw new IllegalStateException(this.conn.getError());
        }
    }

    public SM7_Command send(SM7_Command message) throws Exception {
        if (message == null) {
            return null;
        } else {
            SM7Transaction t = (SM7Transaction)this.conn.createChild();

            SM7_Command var6;
            try {
                t.send(message);
                t.waitResponse();
                SM7_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public SM7_Command onDeliver(SM7_Deliver msg) {
        return new SM7_DeliverResp(msg, 0);
    }

    public void close() {
        this.conn.close();
    }

    public SM7Connection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }
}

