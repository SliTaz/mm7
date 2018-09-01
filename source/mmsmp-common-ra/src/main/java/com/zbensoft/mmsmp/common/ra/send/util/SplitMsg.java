package com.zbensoft.mmsmp.common.ra.send.util;

import java.io.PrintStream;

public class SplitMsg {
	private static final int PAGE = 5;

	public static String[] split(String msg, int len) {
		int msgLen = len * 2;
		String[] splitMsgs = (String[]) null;
		int msgLength = 0;
		int beginPos = 0;
		int splitMsgCount = 0;
		int splitMsgLength = 0;
		int splitLength = msgLen - 5;
		StringBuffer content = new StringBuffer();
		String perMsg = null;
		if ((msg != null) || (!"".equals(msg))) {
			msgLength = msg.getBytes().length;
			splitMsgCount = msgLength % splitLength != 0 ? msgLength / splitLength + 1 : msgLength / splitLength;
			splitMsgs = new String[splitMsgCount];
			if (msgLength < msgLen)
				splitMsgs[0] = msg;
			else
				for (int i = 0; i < splitMsgCount; i++) {
					if (beginPos + msgLen > msgLength)
						perMsg = substring(msg, msgLength - beginPos, beginPos);
					else {
						perMsg = substring(msg, splitLength, beginPos);
					}

					content.append("(").append(i + 1).append("/").append(splitMsgCount).append(")").append(perMsg);
					splitMsgs[i] = content.toString();
					content.delete(0, content.length());
					splitMsgLength = perMsg.getBytes().length;
					if (splitMsgLength < splitLength)
						beginPos += splitLength - 1;
					else
						beginPos += splitLength;
				}
		} else {
			return new String[1];
		}

		return splitMsgs;
	}

	public static boolean isLetter(char c) {
		int k = 128;
		return c / k == 0;
	}

	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static String substring(String origin, int len, int srcPos) {
		if ((origin == null) || (origin.equals("")) || (len < 1))
			return "";
		byte[] strByte = new byte[len];
		if (len > length(origin)) {
			return origin;
		}
		System.arraycopy(origin.getBytes(), srcPos, strByte, 0, len);
		int count = 0;
		for (int i = 0; i < len; i++) {
			int value = strByte[i];
			if (value < 0) {
				count++;
			}
		}
		if (count % 2 != 0) {
			len++;
			len--;
			len = len == 1 ? len : len;
		}
		return new String(strByte, 0, len);
	}

	public static void main(String[] agrs) {
		String str = "aaa中中中中avvvv中中中中中中中中中中中中中中中中中中中中";
		String[] ss = split(str, 20);
		for (String msg : ss)
			System.out.println(msg);
	}
}
