package com.zbensoft.mmsmp.common.ra.vacNew.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {
	public static int String2Int(String str) {
		try {
			return new Integer(str).intValue();
		} catch (NumberFormatException ex) {
		}
		return -1;
	}

	public static float String2float(String str) {
		try {
			return new Float(str).floatValue();
		} catch (NumberFormatException ex) {
		}
		return 0.0F;
	}

	public static long String2Long(String str) {
		try {
			return new Long(str).longValue();
		} catch (NumberFormatException ex) {
		}
		return -1L;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static String md5_encrypt(String inStr) {
		String out = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(inStr.getBytes());
			out = byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return out;
	}

	public static String mySubjectCheck(String s) {
		byte[] ori = s.getBytes();
		byte[] aa = (byte[]) null;
		for (int i = 0; i < ori.length; i++) {
			if ((ori[i] == -124) && (i + 5 < ori.length)) {
				if ((ori[(i + 1)] == 49) && (ori[(i + 2)] == -92) && (ori[(i + 3)] == 55)) {
					if (ori[(i + 4)] == 127) {
						aa = new byte[ori.length - 5 - i];
						System.arraycopy(ori, i + 5, aa, 0, aa.length);
					} else {
						aa = new byte[ori.length - 4 - i];
						System.arraycopy(ori, i + 4, aa, 0, aa.length);
					}
					return new String(aa);
				}
			}
		}
		return s;
	}
}
