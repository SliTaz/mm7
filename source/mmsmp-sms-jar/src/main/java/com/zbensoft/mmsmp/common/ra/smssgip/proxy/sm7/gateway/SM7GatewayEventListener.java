package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7Transaction;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.*;

public class SM7GatewayEventListener extends SMSEventListener {
    private SM7Gateway proxy;

    public SM7GatewayEventListener(SM7Gateway proxy) {
        this.proxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        SM7Transaction t = (SM7Transaction)child;
        SM7_Command msg = t.getResponse();
        SM7_Command resmsg = null;
        if (msg.getCommandID() == 1) {
            resmsg = this.proxy.onLogin((SM7_Bind)msg);
        }

        if (msg.getCommandID() == 6) {
            resmsg = new SM7_UnbindResp((SM7_Unbind)msg);
            this.proxy.onTerminate();
        } else if (msg.getCommandID() == 2) {
            SM7_Submit tmpmes = (SM7_Submit)msg;
            resmsg = this.proxy.onSubmit(tmpmes);
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