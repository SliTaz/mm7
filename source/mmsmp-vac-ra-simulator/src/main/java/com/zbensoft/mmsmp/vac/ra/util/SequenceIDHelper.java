package com.zbensoft.mmsmp.vac.ra.util;

import java.util.Random;

public class SequenceIDHelper {
	private static Integer value = null;
	private static final Integer MAX_VALUE = Integer.valueOf(2147483647);

	public static synchronized Integer getIntValue() {
		if (value == null)
			value = get_random();
		value += 1;
		if (value.intValue() >= MAX_VALUE.intValue())
			value = Integer.valueOf(0);
		return value;
	}

	public static synchronized String getStringValue() {
		StringBuffer sb = new StringBuffer();
		int max_len = String.valueOf(MAX_VALUE).length();
		int v = getIntValue().intValue();
		int len = String.valueOf(v).length();
		for (int i = 0; i < max_len - len;) {
			sb.append("0");
			i++;
		}
		sb.append(v);
		return sb.toString();
	}

	private static Integer get_random() {
		Random random = new Random(System.currentTimeMillis());
		return Integer.valueOf(random.nextInt(MAX_VALUE.intValue()));
	}

	public static void main(String[] args) throws Exception {
		System.out.println(get_random());
		while (true) {
			System.out.println(getStringValue());
			Thread.sleep(1L);
		}
	}
}
