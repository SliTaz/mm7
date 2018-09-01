package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProduceMsgID {
    public static int seq;
    public String time;
    public int clientID;

    private ProduceMsgID() {
    }

    private static int getSeq() {
        ++seq;
        if (seq >= 999999) {
            seq = 0;
        }

        return seq;
    }

    private static byte[] getTime() {
        Calendar cal = new GregorianCalendar();
        byte[] time = new byte[]{TypeConvert.toBCD(cal.get(2) + 1)[0], TypeConvert.toBCD(cal.get(5))[0], TypeConvert.toBCD(cal.get(11))[0], TypeConvert.toBCD(cal.get(12))[0]};
        return time;
    }

    public static synchronized byte[] getMsgID(byte[] appID) {
        if (appID == null || appID.length == 0) {
            appID = new byte[3];
        }

        int seq = getSeq();
        byte[] dst = new byte[3];
        byte[] src = TypeConvert.toBCD(seq);
        int len = Math.min(src.length - 1, 2);

        for(int i = 0; i <= len; ++i) {
            dst[2 - i] = src[i];
        }

        byte[] time = getTime();
        byte[] result = new byte[10];
        byte[] id = new byte[3];
        len = appID.length;
        if (len > 3) {
            System.arraycopy(appID, len - 3, id, 0, 3);
        } else {
            System.arraycopy(appID, 0, id, 3 - len, len);
        }

        System.arraycopy(id, 0, result, 0, 3);
        System.arraycopy(time, 0, result, 3, 4);
        System.arraycopy(dst, 0, result, 7, 3);
        return result;
    }

    public static void main(String[] arg) {
        new ProduceMsgID();
        byte[] appID = TypeConvert.toBCD(123456789);
        byte[] xxx = getMsgID(appID);
    }
}
