package com.zbensoft.mmsmp.common.ra.send.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

public class XmlElementReplace {
	private static final Logger logger = Logger.getLogger(XmlElementReplace.class);
	private static final String REPALACE_ELEMENT_START = "<ServiceCode>";
	private static final String REPALACE_ELEMENT_END = "</ServiceCode>";

	public static InputStream ReplaceServiceCode(InputStream in, String ServiceCode) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1024];
		int count = 0;
		while ((count = in.read(b)) > 0) {
			sb.append(new String(b, 0, count));
		}

		int startIndex = sb.indexOf("<ServiceCode>") + "<ServiceCode>".length();
		int endIndex = sb.indexOf("</ServiceCode>");
		sb.replace(startIndex, endIndex, ServiceCode);

		InputStream out = new ByteArrayInputStream(sb.toString().getBytes());
		return out;
	}

	public static String ReplaceServiceCodeStr(InputStream in, String ServiceCode) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1024];
		int count = 0;
		while ((count = in.read(b)) > 0) {
			sb.append(new String(b, 0, count));
		}

		int startIndex = sb.indexOf("<ServiceCode>") + "<ServiceCode>".length();
		int endIndex = sb.indexOf("</ServiceCode>");
		sb.replace(startIndex, endIndex, ServiceCode);

		return sb.toString();
	}

	public static InputStream ReplaceMessageId(InputStream in, String newmessageid) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1024];
		int count = 0;
		while ((count = in.read(b)) > 0) {
			sb.append(new String(b, 0, count));
		}
		logger.info("=========================repacle start==========================");
		logger.info(sb.toString());
		int startIndex = sb.indexOf("<MessageID>") + "<MessageID>".length();
		int endIndex = sb.indexOf("</MessageID>");
		sb.replace(startIndex, endIndex, newmessageid);
		logger.info("=========================repacle doing==========================");
		logger.info(sb.toString());
		logger.info("=========================repacle end==========================");
		InputStream out = new ByteArrayInputStream(sb.toString().getBytes());
		return out;
	}

	public static InputStream replaceLinkId(InputStream in, String linkid) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1024];
		int count = 0;
		while ((count = in.read(b)) > 0) {
			sb.append(new String(b, 0, count));
		}
		int startIndex = sb.indexOf("<Sender>");
		sb.insert(startIndex, "<LinkedID>" + linkid + "</LinkedID>");
		InputStream out = new ByteArrayInputStream(sb.toString().getBytes());
		return out;
	}

	public static void main(String[] args) throws IOException {
	}
}
