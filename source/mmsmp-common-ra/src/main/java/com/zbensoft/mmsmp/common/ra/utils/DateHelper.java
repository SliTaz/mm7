package com.zbensoft.mmsmp.common.ra.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	public static final String FULLPATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM = "yyyy-MM";
	public static final String yyyyMM = "yyyyMM";
	public static final String MMddHHmmss = "MMddHHmmss";

	public static String getString(Date time, String pattern) {
		SimpleDateFormat sp = new SimpleDateFormat(pattern);
		return sp.format(time);
	}

	public static Date getDate(String dateStr, String pattern) {
		if (dateStr == null)
			return null;
		Date resultDate = null;
		try {
			resultDate = new SimpleDateFormat(pattern).parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	public static String getFullString(Date time) {
		return getString(time, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getFullDate(String dateStr) {
		return getDate(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getyyyyMMddHHmmss(Date time) {
		return getString(time, "yyyyMMddHHmmss");
	}

	public static void main(String[] args) {
		String str = getString(new Date(), "yyyyMMddHHmmss");
		System.out.println(str);
	}
}
