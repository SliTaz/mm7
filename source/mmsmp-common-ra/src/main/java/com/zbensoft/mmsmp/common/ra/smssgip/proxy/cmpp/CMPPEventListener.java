package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.*;

public class CMPPEventListener extends SMSEventListener {
    private CMPPSMSProxy smProxy;

    public CMPPEventListener(CMPPSMSProxy proxy) {
        this.smProxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        CMPPTransaction t = (CMPPTransaction)child;
        CMPP_Command msg = t.getResponse();
        CMPP_Command resmsg = null;
        if (msg.getCommandID() == 2) {
            resmsg = new CMPP_UnbindResp((CMPP_Unbind)msg);
            this.smProxy.onTerminate();
        } else if (msg.getCommandID() == 5) {
            CMPP_Deliver tmpmes = (CMPP_Deliver)msg;
            resmsg = this.smProxy.onDeliver(tmpmes);
        } else if (msg.getCommandID() == 8) {
            CMPP_ActiveTest tmpmes = (CMPP_ActiveTest)msg;
            resmsg = new CMPP_ActiveTestResp(tmpmes);
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
