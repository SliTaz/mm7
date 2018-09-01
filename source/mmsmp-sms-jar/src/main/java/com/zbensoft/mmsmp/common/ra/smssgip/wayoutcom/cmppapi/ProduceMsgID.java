package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProduceMsgID {
    public static short seq;

    private ProduceMsgID() {
    }

    private static short getSeq() {
        return seq++;
    }

    private static long getTime() {
        Calendar cal = new GregorianCalendar();
        long month = (long)(cal.get(2) + 1) << 60;
        long day = (long)cal.get(5) << 55;
        long hour = (long)cal.get(11) << 50;
        long min = (long)cal.get(12) << 44;
        long second = (long)cal.get(13) << 38;
        return month | day | hour | min | second;
    }

    public static synchronized long getMsgID(int appID) {
        short seq = getSeq();
        long time = getTime();
        return time | (long)(appID & 4194303) << 16 | (long)(seq & '\uffff');
    }

    public static int getAppID(long msgID) {
        return (int)(msgID >>> 16 & 4194303L);
    }

    public static int getSeq(long msgID) {
        return (int)(msgID & 65535L);
    }

    public static int getMonth(long msgID) {
        return (int)(msgID >>> 60 & 15L);
    }

    public static int getDay(long msgID) {
        return (int)(msgID >>> 55 & 31L);
    }

    public static int getHour(long msgID) {
        return (int)(msgID >>> 50 & 31L);
    }

    public static int getMinute(long msgID) {
        return (int)(msgID >>> 44 & 63L);
    }

    public static int getSecond(long msgID) {
        return (int)(msgID >>> 38 & 63L);
    }

    public static void main(String[] arg) {
        new ProduceMsgID();
        int i = 0;

        while(true) {
            long msgID = -7437847341185892352L;
            int appID = getAppID(msgID);
            int seq = getSeq(msgID);
            int mon = getMonth(msgID);
            int day = getDay(msgID);
            int hour = getHour(msgID);
            int min = getMinute(msgID);
            int sec = getSecond(msgID);
            System.out.println("i=" + i + " appID:" + appID + " seq:" + seq + " " + mon + "-" + day + " " + hour + ":" + min + ":" + sec);

            try {
                Thread.sleep(10L);
            } catch (Exception var13) {
                ;
            }

            ++i;
        }
    }
}

