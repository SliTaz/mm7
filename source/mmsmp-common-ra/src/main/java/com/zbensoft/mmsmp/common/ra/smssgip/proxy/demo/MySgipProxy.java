package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip.SGIPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Exception;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;

import java.io.IOException;

public class MySgipProxy extends SGIPSMSProxy {
    public SGIP_Command onDeliver(SGIP_Deliver msg) {
        System.out.println("收到短信：" + msg.getMessageContent());
        return super.onDeliver(msg);
    }

    public MySgipProxy(Parameter args) {
        super(args);
    }

    public static void main(String[] args) throws IOException, SGIP_Exception, SMSException, InterruptedException {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("SGIPConnect/*");
        MySgipProxy proxy = new MySgipProxy(param);
        proxy.connect("hm", "phm");
        proxy.startService("127.0.0.1", 10115);
        String[] usernumber = new String[]{"8613331019461", "8613241439596"};
        SGIP_Submit message = new SGIP_Submit(1L, "10115", "8613331019461", usernumber, "90801", "VSM10155", 1, "0", "0", 1, 1, 0, "", "", 1, 0, 0, 8, 1, "hello", "");
        proxy.send(message);
        Thread.sleep(30000L);
        proxy.close();
    }
}
