package com.zbensoft.mmsmp.common.ra.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class CommonConfig {

	Properties _prop = null;

	private CommonConfig(Properties _prop2) {
		this._prop = _prop2;
	}

	public static CommonConfig getInstance(String filename) {
		File file = new File(CommonConfig.class.getClassLoader().getResource("").getPath() + "/" + filename);
		try {
			Properties _prop = new Properties();
			_prop.load(new FileInputStream(file));
			show_all(_prop);
			return new CommonConfig(_prop);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	public String getProperty(String name) {
		return _prop.getProperty(name);
	}

	private static void show_all(Properties _prop) {
		Enumeration keys = _prop.keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			System.out.println("Reading config " + key + " = " + _prop.getProperty((String) key));
		}
	}
}
