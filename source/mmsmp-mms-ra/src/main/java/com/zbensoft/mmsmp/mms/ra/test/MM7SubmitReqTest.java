package com.zbensoft.mmsmp.mms.ra.test;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MM7SubmitReqTest {
	public static String mms = "--==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==Content-ID: <SimpleTeam88025833>Content-Type: text/xml; charset=\"utf8\"Content-Transfer-Encoding: 8bit<?xml version=\"1.0\" encoding=\"utf-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">60567875009767375926</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>888888</VASPID><VASID>33333</VASID><SenderAddress>10658888</SenderAddress></SenderIdentification><Recipients><To><Number>3333</Number></To></Recipients><ServiceCode>33333</ServiceCode><LinkedID>343243243</LinkedID><TimeStamp>2011-01-20T11:57:10+08:00</TimeStamp><Priority>Normal</Priority><Subject>3333</Subject><Sender>3333</Sender></DeliverReq></env:Body></env:Envelope>--==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==Content-Type: multipart/mixed;\tboundary=\"==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==\"--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==Content-ID: <SimpleTeam.txt>Content-Type: text/plain; charset=\"utf-8\"Content-Transfer-Encoding: base64MzMzMw==--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==Content-ID: <SimpleTeam.gif>Content-Type: image/gifContent-Transfer-Encoding: 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--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==----==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==--";

	public static void main(String[] args) {
		AbstractMessage me = new MO_MMDeliverMessage();

		System.out.println(me.getClass().getName());
		try {
			URL url = new URL("http://127.0.0.1:8081/MmsAgent/ucmmsagent/MM7SPrequest");
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			hc.setRequestMethod("POST");
			hc.setAllowUserInteraction(true);
			hc.setDoOutput(true);
			hc.setDoInput(true);
			hc.setUseCaches(false);
			hc.setRequestProperty("SOAPAction", "");
			hc.setRequestProperty("Date", "2011-01-20 11:57:15");
			hc.setRequestProperty("Content-Type",
					"multipart/related; boundary=\"==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==\"; start=\"<SimpleTeam88025833>\"");
			hc.setRequestProperty("MIME-Version", "1.0");
			hc.setRequestProperty("Accept", "*/*");
			hc.setRequestProperty("Accept", "*/*");
			hc.connect();

			PrintWriter pw = new PrintWriter(hc.getOutputStream());

			pw.println(new String(mms.getBytes(), "utf-8"));
			pw.flush();

			System.out.println(hc.getResponseCode());

			String str = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(hc.getInputStream())));
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}

			pw.close();
			hc.disconnect();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
