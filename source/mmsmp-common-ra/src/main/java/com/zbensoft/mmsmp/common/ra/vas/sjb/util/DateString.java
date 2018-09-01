package com.zbensoft.mmsmp.common.ra.vas.sjb.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateString {
	public static String getString(Date time, String pattern) {
		SimpleDateFormat sp = new SimpleDateFormat(pattern);
		return sp.format(time);
	}

	public static String getDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}

	public static String getDateHourString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		return dateFormat.format(new Date());
	}

	public static String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	public static void main(String[] args) {
		System.out.println(getString(new Date(), "HHmm").compareTo("17"));
		System.out.println(getDateHourString());
	}
}
