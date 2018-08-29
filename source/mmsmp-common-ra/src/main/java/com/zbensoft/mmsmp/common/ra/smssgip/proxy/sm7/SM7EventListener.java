package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.*;

public class SM7EventListener extends SMSEventListener {
    private SM7SMSProxy smProxy;

    public SM7EventListener(SM7SMSProxy proxy) {
        this.smProxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        SM7Transaction t = (SM7Transaction)child;
        SM7_Command msg = t.getResponse();
        SM7_Command resmsg = null;
        if (msg.getCommandID() == 6) {
            resmsg = new SM7_UnbindResp((SM7_Unbind)msg);
            this.smProxy.onTerminate();
        } else if (msg.getCommandID() == 3) {
            SM7_Deliver tmpmes = (SM7_Deliver)msg;
            resmsg = this.smProxy.onDeliver(tmpmes);
        } else if (msg.getCommandID() == 4) {
            SM7_ActiveTest test = (SM7_ActiveTest)msg;
            resmsg = new SM7_ActiveTestResp(test);
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
