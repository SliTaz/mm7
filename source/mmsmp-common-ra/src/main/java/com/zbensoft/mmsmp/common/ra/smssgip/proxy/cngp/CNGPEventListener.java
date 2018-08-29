package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.*;

public class CNGPEventListener extends SMSEventListener {
    private CNGPSMSProxy smProxy;

    public CNGPEventListener(CNGPSMSProxy proxy) {
        this.smProxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        CNGPTransaction t = (CNGPTransaction)child;
        CNGP_Command msg = t.getResponse();
        CNGP_Command resmsg = null;
        if (msg.getCommandID() == 6) {
            resmsg = new CNGP_UnbindResp((CNGP_Unbind)msg);
            this.smProxy.onTerminate();
        } else if (msg.getCommandID() == 3) {
            CNGP_Deliver tmpmes = (CNGP_Deliver)msg;
            resmsg = this.smProxy.onDeliver(tmpmes);
        } else if (msg.getCommandID() == 4) {
            CNGP_ActiveTest test = (CNGP_ActiveTest)msg;
            resmsg = new CNGP_ActiveTestResp(test);
        } else {
            t.close();
        }

        if (resmsg != null) {
            try {
                t.send(resmsg);
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            t.close();
        }

    }
}

