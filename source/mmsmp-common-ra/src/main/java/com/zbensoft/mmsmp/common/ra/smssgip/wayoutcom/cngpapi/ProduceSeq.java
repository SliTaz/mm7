package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProduceSeq {
    public static int seq;

    private ProduceSeq() {
    }

    public static synchronized int getSeq() {
        int time = getTime();
        return time | seq++ & '\uffff';
    }

    private static int getTime() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(5) << 27;
        int hour = cal.get(11) << 22;
        int min = cal.get(12) << 16;
        return day | hour | min;
    }

    public static int getDay(int seq) {
        return seq >>> 27 & 31;
    }

    public static int getHour(int seq) {
        return seq >>> 22 & 31;
    }

    public static int getMinute(int seq) {
        return seq >>> 16 & 63;
    }

    public static void main(String[] arg) {
        while(true) {
            int SEQ = getSeq();
            int seq = SEQ & '\uffff';
            int day = getDay(SEQ);
            int hour = getHour(SEQ);
            int min = getMinute(SEQ);
            System.out.println(day + "日 " + hour + ":" + min + "->seq:" + seq);

            try {
                Thread.sleep(10L);
            } catch (Exception var7) {
                ;
            }
        }
    }
}
