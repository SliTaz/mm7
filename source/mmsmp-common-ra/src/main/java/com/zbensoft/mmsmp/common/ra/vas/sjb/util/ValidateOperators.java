package com.zbensoft.mmsmp.common.ra.vas.sjb.util;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import java.io.PrintStream;

public class ValidateOperators {
	private static String mobileSeq = ConfigUtil.getInstance().getCorebizConfig().getMobileSeq();
	private static String telecomSeq = ConfigUtil.getInstance().getCorebizConfig().getTelecomSeq();
	private static String unicomSeq = ConfigUtil.getInstance().getCorebizConfig().getUnicomSeq();

	public static boolean isChinaMobile(String phone) {
		String prefix = phone.substring(0, 3);
		return mobileSeq.contains(prefix);
	}

	public static boolean isChinaTelecom(String phone) {
		String prefix = phone.substring(0, 3);
		return telecomSeq.contains(prefix);
	}

	public static boolean isChinaUnicom(String phone) {
		String prefix = phone.substring(0, 3);
		return unicomSeq.contains(prefix);
	}

	public static void main(String[] args) {
		System.out.println(isChinaMobile("13111001100"));
	}
}
