package com.zbensoft.mmsmp.mms.ra.test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendDeliverReportTest {
	private static final String REPORT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>13012345678</Number></Sender><Recipient><Number>+8613280872320</Number></Recipient><TimeStamp>2010-06-09T10:22:46+08:00</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>08171042446000030927018671407892</MessageID><MMStatus>Retrieved</MMStatus><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
	private static final String REPORT_URL = "http://127.0.0.1:8080/MmsAgent/ucmmsagent/MM7SPrequest";

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			HttpURLConnection httpURL = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				URL theURL = new URL("http://127.0.0.1:8080/MmsAgent/ucmmsagent/MM7SPrequest");
				httpURL = (HttpURLConnection) theURL.openConnection();
				httpURL.setDoInput(true);
				httpURL.setDoOutput(true);
				httpURL.setRequestProperty("content-type", "text/xml;charset=\"UTF-8\"");
				httpURL.setRequestProperty("Content-Transfer-Encoding", "");
				httpURL.setRequestProperty("Authorization", "");
				httpURL.setRequestProperty("SOAPAction", "");
				httpURL.setRequestProperty("MM7APIVersion", "");
				httpURL.setRequestProperty("Mime-Version", "");
				httpURL.setRequestProperty("Content-Length",
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>13012345678</Number></Sender><Recipient><Number>+8613280872320</Number></Recipient><TimeStamp>2010-06-09T10:22:46+08:00</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>08171042446000030927018671407892</MessageID><MMStatus>Retrieved</MMStatus><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>"
								.getBytes("UTF-8").length+"");
				httpURL.setRequestMethod("POST");

				String reportstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>13012345678</Number></Sender><Recipient><Number>+8613280872320</Number></Recipient><TimeStamp>2010-06-09T10:22:46+08:00</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>08171042446000030927018671407892</MessageID><MMStatus>Retrieved</MMStatus><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
				InputStream input = new ByteArrayInputStream(reportstr.getBytes("UTF-8"));
				dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
				byte[] buf = reportstr.getBytes();

				int n = 0;
				while ((n = input.read(buf)) != -1) {
					dos.write(buf, 0, n);
				}
				dos.flush();
				if (httpURL.getResponseCode() == 200)
					System.out.println("send report successfully");
			} catch (Throwable ex) {
				ex.printStackTrace();
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
				if (httpURL != null)
					httpURL.disconnect();
			}
		}
	}
}
