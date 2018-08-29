package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_SubmitResp;

import java.util.Date;

public class MySmgpProxy extends SMGPSMSProxy {
    public SMGP_Command onSubmitResp(SMGP_SubmitResp resp) {
        System.out.println("Hello,我是业务");
        return super.onSubmitResp(resp);
    }

    public MySmgpProxy(Parameter args) {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("SMGPConnect/*");
        MySmgpProxy proxy = new MySmgpProxy(param);
        String[] userphone = new String[]{"10405945592571"};
        SMGP_Submit submit = new SMGP_Submit(6, 1, 1, "wayout", "00", "", "", 15, new Date(), new Date(), "99003", "01012345678", userphone, "Hello,我来自中国", "");
        proxy.send(submit);
        proxy.send(submit);
        Thread.sleep(1000000L);
        proxy.close();
    }
}

