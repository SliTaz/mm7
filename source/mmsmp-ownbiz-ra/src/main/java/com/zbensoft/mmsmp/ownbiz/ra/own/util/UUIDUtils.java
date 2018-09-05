package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import java.util.UUID;

public class UUIDUtils {
    private static UUID uuid = UUID.randomUUID();

    public UUIDUtils() {
    }

    public static String getUUId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getPPSUuid() {
        return "PPS" + getUUId();
    }

    public static void main(String[] args) {
        System.out.println(getUUId());
    }
}
