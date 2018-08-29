package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.*;

import java.nio.ByteBuffer;

public class SMGPEventListener extends SMSEventListener {
    private SMGPSMSProxy smProxy;

    public SMGPEventListener(SMGPSMSProxy proxy) {
        this.smProxy = proxy;
    }

    public void childCreated(SMSLayer child) {
        SMGPTransaction t = (SMGPTransaction)child;
        SMGP_Command msg = t.getResponse();
        SMGP_Command resmsg = null;
        int cad = msg.getCommandID();
        if (cad == 1) {
            ByteBuffer buf = ByteBuffer.allocate(33);
            resmsg = new SMGP_BindResp(buf);
            ((SMGP_Command)resmsg).setCommandID(-2147483647);
            ((SMGP_Command)resmsg).setPacketLength(33);
            ((SMGP_Command)resmsg).setSeqID(msg.getSeqID());
            ((SMGP_BindResp)resmsg).setStatus(0);
        }

        if (cad == 6) {
            resmsg = new SMGP_UnbindResp((SMGP_Unbind)msg);
            this.smProxy.onTerminate();
        } else if (cad == 3) {
            SMGP_Deliver tmpmes = (SMGP_Deliver)msg;
            resmsg = this.smProxy.onDeliver(tmpmes);
        }

        if (cad == 4) {
            SMGP_ActiveTest tmpmes = (SMGP_ActiveTest)msg;
            resmsg = new SMGP_ActiveTestResp(tmpmes);
        } else {
            SMGP_Submit tmpmes;
            if (cad == 2) {
                tmpmes = (SMGP_Submit)msg;
                resmsg = new SMGP_SubmitResp(tmpmes, new byte[10]);
            } else if (cad == 2) {
                tmpmes = (SMGP_Submit)msg;
                resmsg = new SMGP_SubmitResp(tmpmes, new byte[10]);
            } else if (cad == -2147483646) {
                SMGP_SubmitResp resp = (SMGP_SubmitResp)msg;
                this.smProxy.onSubmitResp(resp);
                t.close();
            } else {
                t.close();
            }
        }

        if (resmsg != null) {
            try {
                t.send(resmsg);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            t.close();
        }

    }
}

