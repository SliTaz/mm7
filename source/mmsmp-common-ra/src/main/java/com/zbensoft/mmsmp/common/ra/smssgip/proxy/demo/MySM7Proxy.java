package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7SMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;

import java.util.Date;

public class MySM7Proxy extends SM7SMSProxy {
    public MySM7Proxy(Parameter args) {
        super(args);
    }

    public SM7_Command onDeliver(SM7_Deliver msg) {
        String[] var10000 = new String[]{msg.getSrcTermID()};
        if (!msg.isReport()) {
            String[] destTermId = new String[2];
            String[] chargeTermId = new String[2];
            destTermId[0] = "100000000000000000000";
            destTermId[1] = "111111111111111111111";
            chargeTermId[0] = "222222222222222222222";
            chargeTermId[1] = "333333333333333333333";
            String[] aaa = new String[]{"11111111111111111111111111111111", "44444444444444444444444444444444"};
            new Date();
            SM7_Deliver deliver = new SM7_Deliver("1000000000", 1, 15, new Date(), "10000000000000", 1, "100000000000000000000", 1, "", "10000000000000000000", 9, "22222222222222222222222222222222", "1234567890", "333", "333", "2007101609", "2007101609", "1234567", "444", "55555555555555555555");

            try {
                this.send(deliver);
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        return new SM7_DeliverResp(msg, 0);
    }

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("CNGPConnect/*");
        MySM7Proxy proxy = new MySM7Proxy(param);
        String[] userPhones = new String[]{"01086201609"};
        String[] destTermId = new String[2];
        String[] chargeTermId = new String[2];
        destTermId[0] = "100000000000000000000";
        destTermId[1] = "111111111111111111111";
        chargeTermId[0] = "222222222222222222222";
        chargeTermId[1] = "333333333333333333333";
        String[] aaa = new String[]{"11111111111111111111111111111111", "44444444444444444444444444444444"};
        new Date();
        SM7_Submit submit = new SM7_Submit("77777", 1, 1, 0, "77777", "01", 0, "000000", 15, (Date)null, (Date)null, "77777", "18801030035", userPhones, "我是谁", 0);
        proxy.send(submit);
        proxy.send(submit);
        Thread.sleep(1000000L);
        proxy.close();
    }
}

