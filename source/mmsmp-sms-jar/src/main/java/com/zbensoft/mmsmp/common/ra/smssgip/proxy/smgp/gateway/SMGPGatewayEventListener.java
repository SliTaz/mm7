package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPTransaction;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.*;

public class SMGPGatewayEventListener extends SMSEventListener {
    private SMGPGateway proxy;

    public SMGPGatewayEventListener(SMGPGateway proxy) {
        this.proxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        SMGPTransaction t = (SMGPTransaction)child;
        SMGP_Command msg = t.getResponse();
        SMGP_Command resmsg = null;
        if (msg.getCommandID() == 1) {
            resmsg = this.proxy.onLogin((SMGP_Bind)msg);
        } else if (msg.getCommandID() == 6) {
            resmsg = new SMGP_UnbindResp((SMGP_Unbind)msg);
            this.proxy.onTerminate();
        } else if (msg.getCommandID() == 2) {
            SMGP_Submit tmpmes = (SMGP_Submit)msg;
            resmsg = this.proxy.onSubmit(tmpmes);
        } else if (msg.getCommandID() == 4) {
            SMGP_ActiveTest test = (SMGP_ActiveTest)msg;
            resmsg = new SMGP_ActiveTestResp(test);
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

