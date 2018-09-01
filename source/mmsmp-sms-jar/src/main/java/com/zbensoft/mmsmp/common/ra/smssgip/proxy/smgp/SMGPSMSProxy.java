package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_SubmitResp;

import java.util.Map;

public class SMGPSMSProxy {
    private SMGPConnection conn;
    private boolean blockSend;

    public SMGPSMSProxy(Map args) {
        this(new Parameter(args));
    }

    public SMGPSMSProxy(Parameter args) {
        this.blockSend = true;
        this.conn = new SMGPConnection(args);
        this.conn.addEventListener(new SMGPEventListener(this));
        this.blockSend = args.get("block-send", true);
        this.conn.waitAvailable();
        if (!this.conn.available()) {
            throw new IllegalStateException(this.conn.getError());
        }
    }

    public SMGPSMSProxy() {
        this.blockSend = true;
    }

    public SMGP_Command send(SMGP_Command message) throws Exception {
        if (message == null) {
            return null;
        } else {
            SMGPTransaction t = (SMGPTransaction)this.conn.createChild();

            SMGP_Command var6;
            try {
                t.send(message);
                if (this.blockSend) {
                    System.out.println("blockSend");
                    t.waitResponse();
                }

                SMGP_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public SMGP_Command onDeliver(SMGP_Deliver msg) {
        return new SMGP_DeliverResp(msg);
    }

    public SMGP_Command onSubmitResp(SMGP_SubmitResp resp) {
        return null;
    }

    public void close() {
        this.conn.close();
    }

    public SMGPConnection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }
}

