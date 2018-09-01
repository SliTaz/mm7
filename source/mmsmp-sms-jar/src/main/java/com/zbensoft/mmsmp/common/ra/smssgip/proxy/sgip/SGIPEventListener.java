package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;

public class SGIPEventListener extends SMSEventListener {
    private SGIPConnection conn;
    private SGIPSMSProxy proxy;

    public SGIPEventListener(SGIPSMSProxy proxy, SGIPConnection conn) {
        this.proxy = proxy;
        this.conn = conn;
    }

    public void childCreated(SMSLayer child) {
        SGIPTransaction t = (SGIPTransaction)child;
        SGIP_Command msg = (SGIP_Command)t.getResponse();
        SGIP_Command resmsg = null;
        if (msg.getCommandID() == 2) {
            resmsg = new SGIP_UnbindResp(msg.getHead());
            if (t.isChildOf(this.conn)) {
                this.proxy.onTerminate();
            }
        } else if (msg.getCommandID() == 1) {
            SGIP_Bind tmpmes = (SGIP_Bind)msg;
            int logintype = tmpmes.GetLoginType();
            SGIP_BindResp resp = new SGIP_BindResp(tmpmes.getHead());
            resp.SetResult(0);
            resmsg = resp;
        } else if (msg.getCommandID() == 4) {
            SGIP_Deliver tmpmes = (SGIP_Deliver)msg;
            resmsg = this.proxy.onDeliver(tmpmes);
        } else if (msg.getCommandID() == 5) {
            SGIP_Report tmpmes = (SGIP_Report)msg;
            resmsg = this.proxy.onReport(tmpmes);
        } else if (msg.getCommandID() == 17) {
            SGIP_UserReport tmpmes = (SGIP_UserReport)msg;
            resmsg = this.proxy.onUserReport(tmpmes);
        } else if (msg.getCommandID() == -2147483645) {
            SGIP_SubmitResp tmpmes = (SGIP_SubmitResp)msg;
            resmsg = this.proxy.onSubmitResp(tmpmes);
        } else if (msg.getCommandID() == 3) {
            SGIP_Submit tmpmes = (SGIP_Submit)msg;
            resmsg = this.proxy.onSubmit(tmpmes);
            System.out.println("receive a submit:" + tmpmes.getSeqno_3());
        } else {
            t.close();
        }

        if (resmsg != null) {
            try {
                t.send(resmsg);
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        t.close();
        if (msg.getCommandID() == 2) {
            SGIPConnection theconn = (SGIPConnection)t.getParent();
            theconn.close();
        }

    }
}
