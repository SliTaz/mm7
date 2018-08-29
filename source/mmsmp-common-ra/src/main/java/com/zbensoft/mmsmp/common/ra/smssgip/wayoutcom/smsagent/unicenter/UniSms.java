package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.unicenter;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess.UniCenter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cmpp.SmsCenter;

import java.util.HashMap;

public class UniSms {
    public UniSms() {
    }

    public static void main(String[] args) {
        HashMap hm = UniCenter.loadParameter();
        String flag = null;
        flag = (String)hm.get("CMPP");
        if (flag != null && flag.equals("Y")) {
            SmsCenter.main(args);
        }

        flag = (String)hm.get("SGIP");
        if (flag != null && flag.equals("Y")) {
            SmsCenter.main(args);
        }

        flag = (String)hm.get("SMGP");
        if (flag != null && flag.equals("Y")) {
           SmsCenter.main(args);
        }

    }
}

