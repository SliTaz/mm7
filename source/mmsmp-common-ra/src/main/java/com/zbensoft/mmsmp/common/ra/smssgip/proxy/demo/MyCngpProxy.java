package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp.CNGPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_DeliverResp;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.ZX_CNGP_Submit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCngpProxy extends CNGPSMSProxy {
    private static List submitList = new ArrayList();

    public MyCngpProxy(Parameter args) {
        super(args);
    }

    public CNGP_Command onDeliver(CNGP_Deliver msg) {
        String[] userPhones = new String[]{msg.getSrcTermID()};
        if (!msg.isReport()) {
            ZX_CNGP_Submit submit = new ZX_CNGP_Submit("77777", 1, 1, 0, "77777", "01", 0, "000000", 15, (Date)null, (Date)null, "77777", msg.getSrcTermID(), userPhones, "Hello", 0);
            submitList.add(submit);
        }

        return new CNGP_DeliverResp(msg, 0);
    }

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("CNGPConnect/*");
        MyCngpProxy proxy = new MyCngpProxy(param);
        String[] userPhones = new String[]{"18801030035"};
        ZX_CNGP_Submit submit = new ZX_CNGP_Submit("77777", 1, 1, 0, "77777", "01", 0, "000000", 15, (Date)null, (Date)null, "77777", "18801030035", userPhones, "我是谁", 0);
        proxy.send(submit);

        while(true) {
            do {
                Thread.sleep(10000L);
            } while(submitList.size() <= 0);

            ZX_CNGP_Submit obj = (ZX_CNGP_Submit)submitList.remove(0);
            proxy.send(obj);
        }
    }
}
