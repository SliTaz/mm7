package com.zbensoft.mmsmp.vac.ra.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesHelper {
	private static final File file = new File(PropertiesHelper.class.getClassLoader().getResource("").getPath() + "/app.properties");
	  private static Properties _prop;

	  static
	  {
	    do_reload_config();
	    new Thread(new Runnable() {
	      public void run() {
	        long modified = PropertiesHelper.file.lastModified();
	        while (true) {
	          long last_modified = PropertiesHelper.file.lastModified();
	          if (modified != last_modified) {
	            System.out.println("reloading configure...");
	            PropertiesHelper.do_reload_config();
	            modified = last_modified;
	          }
	          try {
	            Thread.sleep(5000L);
	          }
	          catch (Exception localException)
	          {
	          }
	        }
	      }
	    }).start();
	  }

	  public static Integer getVacAaaPort()
	  {
	    return getInteger("vac.aaa.port", Integer.valueOf(11111));
	  }
	  public static String getVacAaaIp() {
	    return getString("vac.aaa.ip", "127.0.0.1");
	  }
	  public static Integer getVacAaaIdleC() {
	    return getInteger("vac.aaa.idle.c", Integer.valueOf(60));
	  }
	  public static Integer getVacAaaIdleT() {
	    return getInteger("vac.aaa.idle.t", Integer.valueOf(60));
	  }
	  public static Integer getVacAaaIdleN() {
	    return getInteger("vac.aaa.idle.n", Integer.valueOf(3));
	  }
	  public static Long getVacAaaMsgTimeout() {
	    return getLong("vac.aaa.msg.timeout", Long.valueOf(10000L));
	  }
	  public static Long getVacAaaSendIntervel() {
	    return getLong("vac.aaa.sendthread.intervel", Long.valueOf(1000L));
	  }

	  public static Long getLong(String key, Long def)
	  {
	    String value = _prop.getProperty(key);
	    return Long.valueOf(value != null ? Long.parseLong(value) : def.longValue());
	  }
	  public static Integer getInteger(String key, Integer def) {
	    String value = _prop.getProperty(key);
	    return value != null ? Integer.valueOf(value) : def;
	  }
	  public static String getString(String key, String def) {
	    String value = _prop.getProperty(key);
	    return value != null ? value : def;
	  }
	  public static Boolean getBoolean(String key, Boolean def) {
	    String value = _prop.getProperty(key);
	    return value != null ? Boolean.valueOf(value) : def;
	  }

	  public static void do_reload_config() {
	    try {
	      _prop = new Properties();
	      _prop.load(new FileInputStream(file));
	      show_all();
	    } catch (Exception e) {
	      e.printStackTrace(System.err);
	    }
	  }

	  public static void show_all() {
	    Enumeration keys = _prop.keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      System.out.println("Reading config " + key + " = " + _prop.getProperty((String)key));
	    }
	  }
}
