package com.zbensoft.mmsmp.common.ra.send.util;

import com.zbensoft.mmsmp.common.ra.utils.PropertiesHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class ErrorProperties {
	private static final File file = new File(PropertiesHelper.class.getClassLoader().getResource("").getPath() + "/error.properties");
	private static Properties _prop;

	static {
		do_reload_config();
		new Thread(new Runnable() {
			public void run() {
				long modified = ErrorProperties.file.lastModified();
				while (true) {
					long last_modified = ErrorProperties.file.lastModified();
					if (modified != last_modified) {
						ErrorProperties.do_reload_config();
						modified = last_modified;
					}
					try {
						Thread.sleep(5000L);
					} catch (Exception localException) {
					}
				}
			}
		}).start();
	}

	public static Long getLong(String key, Long def) {
		String value = _prop.getProperty(key);
		return Long.valueOf(value != null ? Long.parseLong(value) : def.longValue());
	}

	public static Integer getInteger(String key, Integer def) {
		String value = _prop.getProperty(key);
		return value != null ? Integer.valueOf(value) : def;
	}

	public static String getString(String key, String def) {
		String value = _prop.getProperty(key);
		try {
			value = new String(value.getBytes("ISO8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
     while (keys.hasMoreElements()){
    	 Object localObject = keys.nextElement();
     }
       
   }

	public static void main(String[] args) {
		System.out.println(getString("1001", ""));
	}
}
