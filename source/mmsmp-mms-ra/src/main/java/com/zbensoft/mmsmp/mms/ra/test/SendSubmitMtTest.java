package com.zbensoft.mmsmp.mms.ra.test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSubmitMtTest {
	private static final String REPORT_XML = "this is a multi-part message in MIME format\r\n\r\n\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:text/xml;charset=\"UTF-8\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>111</VASPID><VASID>4321</VASID><SenderAddress>18910229166</SenderAddress></SenderIdentification><Recipients><To><Number>8613111111111</Number></To></Recipients><ServiceCode>1234</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>奥那个</Subject><ChargedParty>Recipient</ChargedParty><ChargedPartyID>8613111111111</ChargedPartyID></SubmitReq></env:Body></env:Envelope>\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:multipart/mixed;boundary=\"--------------------------------------------------------SubPart_2\"\r\nContent-ID:<SaturnPics-01020930>\r\nContent-Transfer-Encoding:8bit";

	public static void main(String[] args) {
		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			URL theURL = new URL("http://127.0.0.1:8080/MmsAgent/corebiz/ReceiveServlet");
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
					"this is a multi-part message in MIME format\r\n\r\n\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:text/xml;charset=\"UTF-8\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>111</VASPID><VASID>4321</VASID><SenderAddress>18910229166</SenderAddress></SenderIdentification><Recipients><To><Number>8613111111111</Number></To></Recipients><ServiceCode>1234</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>奥那个</Subject><ChargedParty>Recipient</ChargedParty><ChargedPartyID>8613111111111</ChargedPartyID></SubmitReq></env:Body></env:Envelope>\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:multipart/mixed;boundary=\"--------------------------------------------------------SubPart_2\"\r\nContent-ID:<SaturnPics-01020930>\r\nContent-Transfer-Encoding:8bit"
							.getBytes("UTF-8").length+"");
			httpURL.setRequestMethod("POST");
			String reportstr = "this is a multi-part message in MIME format\r\n\r\n\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:text/xml;charset=\"UTF-8\"\r\nContent-Transfer-Encoding:8bit\r\nContent-ID:</tnn-200102/mm7-vasp>\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID></env:Header><env:Body><SubmitReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>111</VASPID><VASID>4321</VASID><SenderAddress>18910229166</SenderAddress></SenderIdentification><Recipients><To><Number>8613111111111</Number></To></Recipients><ServiceCode>1234</ServiceCode><DeliveryReport>true</DeliveryReport><ReadReply>true</ReadReply><Subject>奥那个</Subject><ChargedParty>Recipient</ChargedParty><ChargedPartyID>8613111111111</ChargedPartyID></SubmitReq></env:Body></env:Envelope>\r\n---------------------------------------------------------NextPart_1\r\nContent-Type:multipart/mixed;boundary=\"--------------------------------------------------------SubPart_2\"\r\nContent-ID:<SaturnPics-01020930>\r\nContent-Transfer-Encoding:8bit";
			InputStream input = new ByteArrayInputStream(reportstr.getBytes());
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] buf = reportstr.getBytes();
			int n = 0;
			while ((n = input.read(buf)) != -1) {
				dos.write(buf, 0, n);
			}
			dos.flush();
			System.out.println(httpURL.getResponseCode());
			if (httpURL.getResponseCode() == 200)
				System.out.println("send mt message to mmsagent successfully");
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
