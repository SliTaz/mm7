package com.zbensoft.mmsmp.sp.ra.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class Utils {
	
	public static String SPReceiveMTMMS_url="http://localhost:9095/SPReceiveMTMMS";
	public static int ORDER_INT=1;//订购类型
	public static int UN_ORDER_INT=2;//退订类型
	
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

	public static String getString(ByteBuffer buffer, int len, String encoding) {
		if (len <= 0) {
			return "";
		}
		byte[] data = new byte[len];
		buffer.get(data);
		String str = null;
		if (data != null) {
			try {
				if (encoding == null)
					str = new String(data);
				else
					str = new String(data, encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public static String getString(ByteBuffer buffer, int len) throws Exception {
		if (len <= 0)
			return "";
		byte[] b = new byte[len];
		buffer.get(b);
		String s = new String(b);
		return s.trim();
	}

//	public static ByteBuffer putTLV(ByteBuffer buffer, TLV tvl) throws Exception {
//		if (tvl != null) {
//			return buffer.put(tvl.serialize());
//		}
//		return buffer;
//	}
//
//	public static TLV getTLV(ByteBuffer buffer) throws Exception {
//		TLV tlv = new TLV();
//		tlv.unserialize(buffer);
//		if (tlv.getTag().shortValue() == 0)
//			return null;
//		return tlv;
//	}
}
