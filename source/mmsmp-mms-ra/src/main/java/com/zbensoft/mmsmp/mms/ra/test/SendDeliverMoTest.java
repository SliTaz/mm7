package com.zbensoft.mmsmp.mms.ra.test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendDeliverMoTest {
	private static final String REPORT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>888888</VASPID><VASID>106555</VASID><SenderAddress>10658888</SenderAddress></SenderIdentification><Recipients><To><Number>1065556500101</Number></To></Recipients><ServiceCode>33333</ServiceCode><LinkedID>343243243</LinkedID><TimeStamp>2011-01-20T11:57:10+08:00</TimeStamp><Priority>Normal</Priority><Subject>3333发送发送的</Subject><Sender><Number>13012345678</Number></Sender><MMSRelayServerID>127.0.0.1</MMSRelayServerID></DeliverReq></env:Body></env:Envelope>";
	private static final String REPORT_URL = "http://127.0.0.1:8080/MmsAgent/ucmmsagent/MM7SPrequest";

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
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
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>888888</VASPID><VASID>106555</VASID><SenderAddress>10658888</SenderAddress></SenderIdentification><Recipients><To><Number>1065556500101</Number></To></Recipients><ServiceCode>33333</ServiceCode><LinkedID>343243243</LinkedID><TimeStamp>2011-01-20T11:57:10+08:00</TimeStamp><Priority>Normal</Priority><Subject>3333发送发送的</Subject><Sender><Number>13012345678</Number></Sender><MMSRelayServerID>127.0.0.1</MMSRelayServerID></DeliverReq></env:Body></env:Envelope>"
								.getBytes("UTF-8").length+"");
				httpURL.setRequestMethod("POST");
				String reportstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">0640000225669100609102226303</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>888888</VASPID><VASID>106555</VASID><SenderAddress>10658888</SenderAddress></SenderIdentification><Recipients><To><Number>1065556500101</Number></To></Recipients><ServiceCode>33333</ServiceCode><LinkedID>343243243</LinkedID><TimeStamp>2011-01-20T11:57:10+08:00</TimeStamp><Priority>Normal</Priority><Subject>3333发送发送的</Subject><Sender><Number>13012345678</Number></Sender><MMSRelayServerID>127.0.0.1</MMSRelayServerID></DeliverReq></env:Body></env:Envelope>";
				InputStream input = new ByteArrayInputStream(reportstr.getBytes("UTF-8"));
				dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
				byte[] buf = reportstr.getBytes();
				int n = 0;
				while ((n = input.read(buf)) != -1) {
					dos.write(buf, 0, n);
				}
				dos.flush();

				httpURL.getResponseCode();
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
