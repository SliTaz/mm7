package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp;

public class StringCheck {
    public StringCheck() {
    }

    public static boolean allAscii(String text) {
        if (text == null) {
            return true;
        } else {
            int l = text.length();

            for(int i = 0; i < l; ++i) {
                int ch = text.charAt(i);
                if (ch >= 127 || ch < ' ' && ch != '\r' && ch != '\n' && ch != '\t') {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean allDigital(String text) {
        if (text == null) {
            return true;
        } else {
            int l = text.length();

            for(int i = 0; i < l; ++i) {
                int ch = text.charAt(i);
                if (ch > '9' || ch < '0') {
                    return false;
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        String test = "999你好";
        allAscii(test);
        allDigital(test);
    }
}
