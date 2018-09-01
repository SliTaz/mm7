package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Submit;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class SMGPClient {
    public SMGPClient() {
    }

    public static void main(String[] args) throws Exception {
        Socket sc = new Socket();
        sc.connect(new InetSocketAddress("localhost", 8813));
        sc.setKeepAlive(true);
        sc.setSoTimeout(10000);
        String[] number = new String[100];

        for(int i = 100; i < 200; ++i) {
            number[i - 100] = "86133204021" + i;
        }

        SMGP_Submit smt = createSubmit("13320401615", number, "测试！test");
        System.out.println(smt);
        smt.write(sc);
        SMGP_Submit.read(sc);
    }

    public static SMGP_Submit createSubmit(String chargePhone, String[] userPhone, String content) {
        SMGP_Submit submit = new SMGP_Submit(6, 1, 1, "wayout", "00", "", "", 15, new Date(), new Date(), "118", "01012345678", userPhone, "9992", "");
        return submit;
    }
}

