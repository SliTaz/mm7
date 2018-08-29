package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class SecurityTools {
    private static final byte[] salt = "webplat".getBytes();

    public SecurityTools() {
    }

    public static String digest(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA");
            md5.update(salt);
            String s = Base64.encode(md5.digest(str.getBytes()));
            return s;
        } catch (NoSuchAlgorithmException var3) {
            throw new UnsupportedOperationException(var3.toString());
        }
    }

    public static void md5(byte[] data, int offset, int length, byte[] digest, int dOffset) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data, offset, length);
            md5.digest(digest, dOffset, 16);
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (DigestException var7) {
            var7.printStackTrace();
        }

    }

    public static byte[] md5(byte[] data, int offset, int length) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data, offset, length);
            byte[] abyte0 = md5.digest();
            return abyte0;
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            byte[] abyte1 = (byte[])null;
            return abyte1;
        }
    }

    public static byte[] encrypt(byte[] key, byte[] src) {
        try {
            byte[] abyte0 = getCipher(key, 1).doFinal(src);
            return abyte0;
        } catch (BadPaddingException var3) {
            throw new UnsupportedOperationException(var3.toString());
        } catch (IllegalBlockSizeException var4) {
            throw new UnsupportedOperationException(var4.toString());
        }
    }

    public static String encrypt(String key, String src) {
        try {
            String s = Base64.encode(getCipher(key.getBytes("UTF8"), 1).doFinal(src.getBytes("UTF8")));
            return s;
        } catch (UnsupportedEncodingException var3) {
            throw new UnsupportedOperationException(var3.toString());
        } catch (BadPaddingException var4) {
            throw new UnsupportedOperationException(var4.toString());
        } catch (IllegalBlockSizeException var5) {
            throw new UnsupportedOperationException(var5.toString());
        }
    }

    public static byte[] decrypt(byte[] key, byte[] src) {
        try {
            byte[] abyte0 = getCipher(key, 2).doFinal(src);
            return abyte0;
        } catch (IllegalBlockSizeException var3) {
            throw new UnsupportedOperationException(var3.toString());
        } catch (BadPaddingException var4) {
            throw new UnsupportedOperationException(var4.toString());
        }
    }

    public static String decrypt(String key, String src) {
        try {
            String s = new String(getCipher(key.getBytes("UTF8"), 2).doFinal(Base64.decode(src)), "UTF8");
            return s;
        } catch (UnsupportedEncodingException var3) {
            throw new UnsupportedOperationException(var3.toString());
        } catch (BadPaddingException var4) {
            throw new UnsupportedOperationException(var4.toString());
        } catch (IllegalBlockSizeException var5) {
            throw new UnsupportedOperationException(var5.toString());
        }
    }

    public static Cipher getCipher(byte[] key, int mode) {
        try {
            if (key.length < 8) {
                byte[] oldkey = key;
                key = new byte[8];
                System.arraycopy(oldkey, 0, key, 0, oldkey.length);
            }

            Object keySpec;
            Cipher c;
            SecretKeyFactory keyFactory;
            if (key.length >= 24) {
                keyFactory = SecretKeyFactory.getInstance("DESede");
                keySpec = new DESedeKeySpec(key);
                c = Cipher.getInstance("DESede");
            } else {
                keyFactory = SecretKeyFactory.getInstance("DES");
                keySpec = new DESKeySpec(key);
                c = Cipher.getInstance("DES");
            }

            SecretKey k = keyFactory.generateSecret((KeySpec)keySpec);
            c.init(mode, k);
            return c;
        } catch (NoSuchAlgorithmException var7) {
            throw new UnsupportedOperationException(var7.toString());
        } catch (InvalidKeyException var8) {
            throw new UnsupportedOperationException(var8.toString());
        } catch (NoSuchPaddingException var9) {
            throw new UnsupportedOperationException(var9.toString());
        } catch (InvalidKeySpecException var10) {
            throw new UnsupportedOperationException(var10.toString());
        }
    }
}
