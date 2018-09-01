package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.GregorianCalendar;

public class CMPPClient {
    public CMPPClient() {
    }

    public static void main(String[] args) throws Exception {
        Socket sc = new Socket();
        sc.connect(new InetSocketAddress("localhost", 7813));
        sc.setKeepAlive(true);
        sc.setSoTimeout(10000);
        String[] number = new String[100];

        for(int i = 100; i < 200; ++i) {
            number[i - 100] = "861332040262";
        }

        CMPP_Submit smt = createSubmit("13320401615", number, "测试！test");
        smt.write(sc);
        CMPP_Submit.read(sc);
    }

    public static CMPP_Submit createSubmit(String chargePhone, String[] userPhone, String content) {
        int Pk_total = 1;
        int Pk_number = 1;
        int Registered_Delivery = 1;
        int Msg_level = 1;
        String Service_Id = "-MF";
        int Fee_UserType = 0;
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
        int Dest_terminal_type = 0;
        String LinkID = "";
        CMPP_Submit submit = new CMPP_Submit(Pk_total, Pk_number, Registered_Delivery, Msg_level, Service_Id, Fee_UserType, chargePhone, Fee_terminal_type, TP_pId, TP_udhi, Msg_Fmt, Msg_src, FeeType, FeeCode, ValId_Time, At_Time, Src_Id, userPhone, Dest_terminal_type, content.getBytes(), LinkID);
        return submit;
    }
}
