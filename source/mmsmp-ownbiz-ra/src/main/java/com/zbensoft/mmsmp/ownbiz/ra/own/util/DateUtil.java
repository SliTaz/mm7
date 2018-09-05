package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public DateUtil() {
    }

    public static String getFormatTime(Date date) {
        return (new SimpleDateFormat("HH:mm:ss")).format(date);
    }

    public static String getAllTime() {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
    }

    public static String getFormatDate() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getFormatDate());
    }
}
