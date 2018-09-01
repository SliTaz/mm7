package com.zbensoft.mmsmp.common.ra.utils;

import com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest;
import com.zbensoft.mmsmp.common.ra.send.util.XmlElementReplace;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletInputStream;
import org.apache.log4j.Logger;

public class Sender {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Sender.class);

	public static String sendmsg(String url, HttpRequest request) throws IOException {
		String res = "";
		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			logger.info("url:" + url);
			URL theURL = new URL(url);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setReadTimeout(5000);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("Content-Type", request.getContentType());

			httpURL.setRequestProperty("aceway-to", "mmsc.aceway.com.cn");

			httpURL.setRequestProperty("Connection", "keep-alive");
			httpURL.setRequestProperty("Content-Length", request.getInputStream().available()+"");
			InputStream in = request.getInputStream();
			if ((request.getLinkId() != null) && (request.getLinkId().length() > 0)) {
				logger.info("starting to add linkid element.....");
				in = XmlElementReplace.replaceLinkId(in, request.getLinkId());
			}

			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			StringBuilder moMmsStr = new StringBuilder();
			byte[] inb = new byte[1024];
			int contentLen = 0;
			int count = 0;
			while ((count = in.read(inb)) > 0) {
				contentLen += count;
				dos.write(inb, 0, count);
				moMmsStr.append(new String(inb, 0, count));
			}
			dos.flush();
			logger.info(moMmsStr.toString().length() > 1000 ? moMmsStr.toString().substring(0, 1000) : moMmsStr.toString());
			logger.info("httpURL.getResponseCode():" + httpURL.getResponseCode());
			if (httpURL.getResponseCode() == 200) {
				dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
				int len = dis.available();
				byte[] b = new byte[len];
				int r = dis.read(b);
				int pos = r;
				while (pos < len) {
					r = dis.read(b, pos, len - pos);
					pos += r;
				}

				res = new String(b);
			} else {
				res = "HTTP/1.1 " + httpURL.getResponseCode();
				logger.error("sp return error message: [" + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
			}
		} catch (Throwable ex) {
			res = "HTTP/1.1 404";
			logger.error("", ex);
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (Exception localException2) {
				}
			if (dos != null)
				try {
					dos.close();
				} catch (Exception localException3) {
				}
			if (httpURL != null) {
				httpURL.disconnect();
			}
		}
		logger.info("response: " + res);
		return res;
	}

	public static String sendmsg(String url, String str) {
		logger.info(url + " => " + str);

		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		String res;
		try {
			URL theURL = new URL(url);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("x-mmsc-msg-from", "mm7");
			httpURL.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			dos.write(str.getBytes());
			dos.flush();
//			String res;
			if (httpURL.getResponseCode() == 200) {
				dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
				int len = dis.available();
				byte[] b = new byte[len];
				int r = dis.read(b);
				int pos = r;
				while (pos < len) {
					r = dis.read(b, pos, len - pos);
					pos += r;
				}

				res = new String(b);
			} else {
				res = "HTTP/1.1 " + httpURL.getResponseCode();
				logger.error("sp return error message: [" + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
			}
		} catch (Throwable ex) {
			logger.error("", ex);
			res = "HTTP/1.1 404";
		} finally {
//			String res;
			if (dis != null)
				try {
					dis.close();
				} catch (Exception localException2) {
				}
			if (dos != null)
				try {
					dos.close();
				} catch (Exception localException3) {
				}
			if (httpURL != null)
				httpURL.disconnect();
		}
//		String res;
		logger.info("response: " + res);
		return res;
	}

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-2\" env:mustUnderstand=\"1\">dLj160WywOJ0</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-2\"><MM7Version>5.3.0</MM7Version><MMSRelayServerID>877101</MMSRelayServerID><MessageID>MessageID001</MessageID><Recipient><Number>8613161608360</Number></Recipient><Sender>1065580001</Sender><TimeStamp>2010-02-10T09:14:39+08:00</TimeStamp><MMStatus>Retrieved</MMStatus><MMSStatusErrorCode>129</MMSStatusErrorCode><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
		sendmsg("http://127.0.0.1:8001/ucmmsagent/MM7request", xml);
	}
}
