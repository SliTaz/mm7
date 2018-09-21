package com.zbensoft.mmsmp.ftp.util;

import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesUtil {
    private static Logger _log = Logger.getLogger(PropertiesUtil.class);

    public PropertiesUtil() {
    }

    public static String get(String filePath, String key) {
        ResourceBundle rb = null;
        String strValue = "";

        try {
            rb = ResourceBundle.getBundle(filePath);
            strValue = rb.getString(key);
            _log.debug(filePath + ".properties，key: " + key + "=" + strValue);
        } catch (MissingResourceException var5) {
            _log.debug(filePath + ".properties，key:" + key, var5);
        } catch (Exception var6) {
            _log.debug(filePath + ".properties，key:" + key, var6);
        } catch (Throwable var7) {
            _log.debug(filePath + ".properties，key:" + key, var7);
        }

        return strValue;
    }

    public static int getInt(String filePath, String key) {
        String strValue = get(filePath, key);
        return strValue != "" && strValue != null ? Integer.parseInt(strValue) : -1;
    }

    public static long getLong(String filePath, String key) {
        String strValue = get(filePath, key);
        return strValue != "" && strValue != null ? Long.parseLong(strValue) : -1L;
    }

    public static boolean getBoolean(String filePath, String key) {
        String strValue = get(filePath, key);
        return strValue != "" && strValue != null ? Boolean.parseBoolean(strValue) : false;
    }

    public static void main(String[] args) {
        System.out.println(get("common", "client_ip"));
    }
}
