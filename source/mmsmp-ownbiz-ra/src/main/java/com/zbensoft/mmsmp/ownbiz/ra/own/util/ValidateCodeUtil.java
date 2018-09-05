package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import org.apache.commons.lang.RandomStringUtils;

public class ValidateCodeUtil {
    public ValidateCodeUtil() {
    }

    public static String getValidateCode() {
        String str = RandomStringUtils.randomAlphabetic(6).toLowerCase();
        return str;
    }

    public static void main(String[] args) {
        System.out.println(getValidateCode());
    }
}
