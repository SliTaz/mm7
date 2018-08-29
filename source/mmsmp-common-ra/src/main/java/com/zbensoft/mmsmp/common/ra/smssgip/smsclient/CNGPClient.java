package com.zbensoft.mmsmp.common.ra.smssgip.smsclient;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Submit;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class CNGPClient {
    public CNGPClient() {
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

        CNGP_Submit smt = createSubmit("13320401615", number, "测试！test");
        System.out.println(smt);
        smt.write(sc);
        CNGP_Submit.read(sc);
    }

    public static CNGP_Submit createSubmit(String chargePhone, String[] userPhone, String content) {
        CNGP_Submit submit = new CNGP_Submit("1", 1, 1, 0, "9999", "01", 0, "1", 15, (Date)null, (Date)null, "6660115", "6660115", userPhone, content, 0);
        return submit;
    }
}

