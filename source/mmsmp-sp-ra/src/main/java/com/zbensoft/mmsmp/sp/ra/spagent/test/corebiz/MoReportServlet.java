package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima.Sender;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoReportServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phonenumber");
		String accnumber = request.getParameter("accnumber");
		String reporturl = request.getParameter("reporturl");
		String number = request.getParameter("number");
		String transactionid = new Random().nextInt(1000)+"";
		String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
		String messageid = "11111111";

		String report = reportStr(transactionid, phone, accnumber, timestamp, messageid);
		MO_ReportMessage morpt = new MO_ReportMessage();
		morpt.setReportUrl(reporturl);
		morpt.setContent(report);

		int num = 1;
		if ((number != null) && (!"".equals(number))) {
			num = Integer.parseInt(number);
		}
		for (int i = 0; i < num; i++) {
			Sender.send(morpt);
		}

		request.getRequestDispatcher("moreport.jsp").forward(request, response);
	}

	private String reportStr(String transactionid, String sendnumber, String recipientnumber, String timestamp,
			String messageid) {
		String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">"
				+ transactionid + "</mm7:TransactionID></env:Header><env:Body>"
				+ "<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\">"
				+ "<MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber
				+ "</Number></Sender><Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>"
				+ timestamp + "</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid
				+ "</MessageID><MMStatus>Third:User Retrieved</MMStatus><StatusText>1000</StatusText>"
				+ "</DeliveryReportReq></env:Body></env:Envelope>";
		return report;
	}
}
