package com.zbensoft.mmsmp.vac.ra.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.vac.ra.aaa.TLV;

public class Utils {

	private static final Logger logger = Logger.getLogger(Utils.class);

	public static void putString(ByteBuffer buffer, String str, int len) {
		if (str == null)
			str = "";
		byte[] data = str.getBytes();
		int srcLen = data.length;
		if (srcLen <= len)
			buffer.put(data);
		else
			buffer.put(data, 0, len);
		while (srcLen < len) {
			buffer.put((byte) 0);
			srcLen++;
		}
	}

	public static void putString1(ByteBuffer buffer, String str, int len) {
		if (str == null)
			str = "";
		byte[] data = new byte[len];
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			int num = c[i];
			data[i] = (byte) num;
		}
		buffer.put(data);
	}

	public static void putString(ByteBuffer buffer, String str, String encoding) {
		if (str == null)
			str = "";
		byte[] data = (byte[]) null;
		try {
			if (encoding != null)
				data = str.getBytes(encoding);
			else
				data = str.getBytes();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (data != null) {
			buffer.put(data, 0, data.length);
		}
	}

	public static String getString(ByteBuffer buffer, int len) throws Exception {
		if (len <= 0)
			return "";
		byte[] b = new byte[len];
		buffer.get(b);
		String s = new String(b);
		return s.trim();
	}

	public static ByteBuffer putTLV(ByteBuffer buffer, TLV tvl) throws Exception {
		if (tvl != null) {
			return buffer.put(tvl.serialize());
		}
		return buffer;
	}

	public static TLV getTLV(ByteBuffer buffer) throws Exception {
		TLV tlv = new TLV();
		tlv.unserialize(buffer);
		if (tlv.getTag().shortValue() == 0)
			return null;
		return tlv;
	}
}
