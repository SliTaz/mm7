package com.zbensoft.mmsmp.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesHelper {
    private static final File file = new File(PropertiesHelper.class.getClassLoader().getResource("").getPath() + "/app.properties");
    private static Properties _prop;

    static {
        do_reload_config();
        (new Thread(new Runnable() {
            public void run() {
                long modified = PropertiesHelper.file.lastModified();

                while(true) {
                    long last_modified = PropertiesHelper.file.lastModified();
                    if (modified != last_modified) {
                        System.out.println("reloading configure...");
                        PropertiesHelper.do_reload_config();
                        modified = last_modified;
                    }

                    try {
                        Thread.sleep(5000L);
                    } catch (Exception var6) {
                        ;
                    }
                }
            }
        })).start();
    }

    public PropertiesHelper() {
    }

    public static boolean get_ftp_debug() {
        return getBoolean("ftp_debug");
    }

    public static String get_sid() {
        return getString("sid");
    }

    public static String get_sidpwd() {
        return getString("sidpwd");
    }

    public static String get_poolname() {
        return getString("db.pool.name");
    }

    public static boolean is_usepool() {
        return getBoolean("db.usepool");
    }

    public static String get_driver() {
        return getString("db.driver");
    }

    public static String get_conn() {
        return getString("db.conn");
    }

    public static String get_user() {
        return getString("db.user");
    }

    public static String get_pass() {
        return getString("db.pass");
    }

    public static int getInt(String key) {
        String value = _prop.getProperty(key);
        return Integer.valueOf(value);
    }

    public static int getInt(String key, int def) {
        String value = _prop.getProperty(key);
        return value != null ? Integer.valueOf(value) : def;
    }

    public static String getString(String key) {
        return _prop.getProperty(key);
    }

    public static Boolean getBoolean(String key) {
        String value = _prop.getProperty(key);
        return Boolean.valueOf(value);
    }

    public static void do_reload_config() {
        try {
            _prop = new Properties();
            _prop.load(new FileInputStream(file));
            show_all();
        } catch (Exception var1) {
            var1.printStackTrace(System.err);
        }

    }

    public static void show_all() {
        Enumeration keys = _prop.keys();

        while(keys.hasMoreElements()) {
            Object key = keys.nextElement();
            System.out.println("Reading config " + key + " = " + _prop.getProperty((String)key));
        }

    }

    public static void main(String[] args) throws Exception {
        while(true) {
            System.out.println(get_pass());
            Thread.sleep(5000L);
        }
    }
}
