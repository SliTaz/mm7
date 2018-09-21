package com.zbensoft.mmsmp.ftp.util;
import java.util.List;

public class SpFilter {
    private static String[] serviceArray = new String[]{"31000014", "31002001", "31002002", "31002003"};

    public SpFilter() {
    }

    public static boolean spFilter(String spid, List<String> spIdList) {
        for(int i = 0; i < spIdList.size(); ++i) {
            if (spid.equals(spIdList.get(i))) {
                return true;
            }
        }

        return false;
    }

    public static boolean serviceFilter(String serviceId) {
        for(int i = 0; i < serviceArray.length; ++i) {
            if (serviceId.equals(serviceArray[i])) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        boolean flag = serviceFilter("31002098");
        System.out.println(flag);
    }
}
