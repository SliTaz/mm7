package com.zbensoft.mmsmp.common.ra.smssgip.proxy.demo;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp.CMPPSMSProxy;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;

import java.util.Date;
import java.util.GregorianCalendar;

public class MyCmppProxy extends CMPPSMSProxy {
    public MyCmppProxy(Parameter args) {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource(MySgipProxy.class, "app");
        Parameter param = resource.getParameter("CMPPConnect/*");
        CMPPSMSProxy proxy = new CMPPSMSProxy(param);
        int Pk_total = 1;
        int Pk_number = 1;
        int Registered_Delivery = 1;
        int Msg_level = 1;
        String Service_Id = "-MF";
        int Fee_UserType = 0;
        String Fee_terminal_Id = "13900001111";
        int Fee_terminal_type = 0;
        int TP_pId = 0;
        int TP_udhi = 0;
        int Msg_Fmt = 15;
        String Msg_src = "901238";
        String FeeType = "01";
        String FeeCode = "0";
        Date ValId_Time = (new GregorianCalendar()).getTime();
        Date At_Time = new Date();
        String Src_Id = "09999";
        String[] Dest_terminal_Id = new String[]{"13900001111"};
        int Dest_terminal_type = 0;
        String Msg_Content = "test";
        String LinkID = "";
        CMPP_Submit submit = new CMPP_Submit(Pk_total, Pk_number, Registered_Delivery, Msg_level, Service_Id, Fee_UserType, Fee_terminal_Id, Fee_terminal_type, TP_pId, TP_udhi, Msg_Fmt, Msg_src, FeeType, FeeCode, ValId_Time, At_Time, Src_Id, Dest_terminal_Id, Dest_terminal_type, Msg_Content, LinkID);
        proxy.send(submit);
        proxy.send(submit);
        proxy.send(submit);
        proxy.close();
    }
}
