package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip.SGIPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Exception;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MySGIPGateway extends SGIPSMSProxy {
    public MySGIPGateway(Parameter args) {
        super(args);
    }

    public static void main(String[] args) throws Exception, SGIP_Exception, SMSException {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("SGIPConnect/*");
        MySgipProxy proxy = new MySgipProxy(param);
        if (args.length > 0) {
            proxy.startService(args[0], 9801);
        } else {
            proxy.startService("127.0.0.1", 9801);
        }

        String line;
        SGIP_Submit message;
        for(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); (line = reader.readLine()) != null; proxy.send(message)) {
            String[] usernumber = new String[]{"8613331019461"};
            message = new SGIP_Submit(1L, "10114", "8613331019461", usernumber, "00000", "xw", 1, "0", "0", 1, 1, 0, "", "", 1, 0, 0, 8, 1, line, "");
            if (proxy.getConnState().equals("还未建立连接")) {
                proxy.connect("hm", "hm");
            }
        }

    }
}

