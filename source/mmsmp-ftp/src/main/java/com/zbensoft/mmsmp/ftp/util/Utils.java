package com.zbensoft.mmsmp.ftp.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class);

    public Utils() {
    }

    public static void putString(ByteBuffer buffer, String str, int len) {
        if (str == null) {
            str = "";
        }

        byte[] data = str.getBytes();
        int srcLen = data.length;
        if (srcLen <= len) {
            buffer.put(data);
        } else {
            buffer.put(data, 0, len);
        }

        while (srcLen < len) {
            buffer.put((byte) 0);
            ++srcLen;
        }

    }

    public static void putString(ByteBuffer buffer, String str, String encoding) {
        if (str == null) {
            str = "";
        }

        byte[] data = (byte[]) null;

        try {
            if (encoding != null) {
                data = str.getBytes(encoding);
            } else {
                data = str.getBytes();
            }
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        if (data != null) {
            buffer.put(data, 0, data.length);
        }

    }

    public static String getString(ByteBuffer buffer, int len) throws Exception {
        if (len <= 0) {
            return "";
        } else {
            byte[] b = new byte[len];
            buffer.get(b);
            String s = new String(b);
            return s.trim();
        }
    }
}
