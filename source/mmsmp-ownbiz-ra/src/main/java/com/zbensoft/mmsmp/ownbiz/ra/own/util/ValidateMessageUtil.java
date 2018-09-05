package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import org.apache.commons.codec.digest.DigestUtils;

public class ValidateMessageUtil {
    private static final DigestUtils du = new DigestUtils();

    public ValidateMessageUtil() {
    }

    public static boolean doValidateMessage(String source, String target) {
        boolean bool = false;
        if (DigestUtils.md5Hex(source).equalsIgnoreCase(target)) {
            bool = true;
        }

        return bool;
    }

    public static String getMD5String(String str) {
        return DigestUtils.md5Hex(str);
    }
}
