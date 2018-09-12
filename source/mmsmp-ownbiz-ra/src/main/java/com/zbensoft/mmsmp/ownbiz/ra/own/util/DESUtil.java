package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class DESUtil {
    private static final Log log = LogFactory.getLog(DESUtil.class);

    public DESUtil() {
    }

    public static String encrypt(String content, String password) {
        try {
            byte[] datasource = content.getBytes();
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, securekey, random);
            return (new BASE64Encoder()).encode(cipher.doFinal(datasource));
        } catch (Exception var8) {
            log.error(var8.getMessage(), var8);
            return null;
        }
    }

    public static String descrypt(String enContent, String password) {
        try {
            byte[] datasource = (new BASE64Decoder()).decodeBuffer(enContent);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, securekey, random);
            return new String(cipher.doFinal(datasource));
        } catch (Exception var8) {
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "13312345678";
        long nowTime = System.currentTimeMillis();
        String password = AppContants.DES_ENCRYPTION_KEY;
        str = str + "," + nowTime;
        String result = encrypt(str, password);
        System.out.println("原始des加密串：" + result);

        try {
            result = URLEncoder.encode(result, "utf-8");
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        }

        System.out.println("URLEncoder加密后内容为：" + new String(result));

        try {
            result = URLDecoder.decode(result, "utf-8");
            String decryResult = descrypt(result, password);
            System.out.println("解密后内容为：" + decryResult);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }
}
