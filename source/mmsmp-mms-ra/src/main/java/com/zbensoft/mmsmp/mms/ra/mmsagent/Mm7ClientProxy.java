package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.MM7.sp.SubmitResp;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.SubmitReq;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class Mm7ClientProxy {
	static final Logger logger = Logger.getLogger(Mm7ClientProxy.class);
	static final ThreadPoolExecutor helper = new ThreadPoolExecutor(5, 30, 5L, TimeUnit.SECONDS,
			new ArrayBlockingQueue(1000), new ThreadPoolExecutor.CallerRunsPolicy());
	MessageRouter messageRouter;
	String mmsmcUrl;

	public void submit(MT_MMMessage message) {
	}

	public void submit(MT_MMHttpSPMessage mms) {
		HttpURLConnection httpURL = null;
		InputStream input = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		int httpResponseCode = 0;
		try {
			URL theURL = new URL(this.mmsmcUrl);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setUseCaches(false);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("content-type", mms.getContentType());
			httpURL.setRequestProperty("Content-Transfer-Encoding", mms.getContent_Transfer_Encoding());
			httpURL.setRequestProperty("Authorization", mms.getAuthorization());
			httpURL.setRequestProperty("SOAPAction", mms.getSOAPAction());
			httpURL.setRequestProperty("MM7APIVersion", mms.getMM7APIVersion());
			httpURL.setRequestProperty("Mime-Version", mms.getMime_Version());
			httpURL.setRequestProperty("Connection", "close");
			httpURL.connect();

			input = new ByteArrayInputStream(mms.getContentByte());
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));

			byte[] buf = new byte[2048];
			int n = 0;
			while ((n = input.read(buf)) != -1) {
				dos.write(buf, 0, n);
			}
			dos.flush();

			httpResponseCode = httpURL.getResponseCode();

			if (httpResponseCode != 200) {
				String statusCode = httpResponseCode+"";
				String statusText = "Ummmc Server Error";
				String reportStr = reportStr(new Random().nextInt(100000)+"", "10655565", mms.getSubmitReq().getTo(),
						getTimeStr(), mms.getMessageid(), 500, "Ummmc Server Error");

				MT_ReportMessage his = new MT_ReportMessage();
				his.setGlobalMessageid(mms.getGlobalMessageid());
				his.setGlobalCreateTime(System.currentTimeMillis());
				his.setGlobalReportUrl(mms.getGlobalReportUrl());
				his.setSpUrl(mms.getGlobalReportUrl());
				his.setPhone(mms.getPhone());
				his.setReqId("");
				his.setCorrelator(mms.getMessageid());
				his.setMessageid(mms.getMessageid());
				his.setStatus("0");
				his.setContent(reportStr);

				this.messageRouter.doRoute(his);

				logger.info(toMessage(mms, "failed", statusCode, statusText));
			} else {
				SubmitResp rsp = new SubmitResp();
				dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));

				int len = dis.available();
				StringBuilder submitResStr = new StringBuilder();
				byte[] b = new byte[len];
				int r = dis.read(b);
				submitResStr.append(new String(b, 0, r));
				int pos = r;
				while (pos < len) {
					r = dis.read(b, pos, len - pos);
					submitResStr.append(new String(b, pos, len - pos));
					pos += r;
				}

				if (ApplicationListener.getRunLevel().equals("3")) {
					logger.info("mmsagent<- mmsc mock one mmshr message");
				} else {
					helper.execute(new ResponseHandle(mms, b, rsp));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				dos.close();
			} catch (Exception localException1) {
			}
			try {
				input.close();
			} catch (Exception localException2) {
			}
			try {
				dis.close();
			} catch (Exception localException3) {
			}
			try {
				httpURL.disconnect();
			} catch (Exception localException4) {
			}
		} finally {
			try {
				dos.close();
			} catch (Exception localException5) {
			}
			try {
				input.close();
			} catch (Exception localException6) {
			}
			try {
				dis.close();
			} catch (Exception localException7) {
			}
			try {
				httpURL.disconnect();
			} catch (Exception localException8) {
			}
		}
	}

	public void doResponse(MT_MMHttpSPMessage mms, byte[] data, SubmitResp rsp) {
		String retStr = new String(data);

		if (retStr.indexOf("RSErrorRsp") < 0) {
			rsp.parser(retStr);
		} else {
			rsp.errorXmlParser(retStr);
		}

		String statusCode = rsp.getStatusCode();
		String statusText = rsp.getStatusText();

		int mmstatuscode = 0;
		String reportStr;
		if (statusCode.equals("1000")) {
			mmstatuscode = 6;
			reportStr = reportStr(new Random().nextInt(100000)+"", "10655565", mms.getSubmitReq().getTo(), getTimeStr(),
					mms.getMessageid(), 1000, "Second:MMSC Receive Success");
		} else {
			mmstatuscode = 7;
			reportStr = reportStr(new Random().nextInt(100000)+"", "10655565", mms.getSubmitReq().getTo(), getTimeStr(),
					mms.getMessageid(), Integer.parseInt(rsp.getStatusCode()), rsp.getStatusText());
		}

		MT_ReportMessage his = new MT_ReportMessage();
		his.setGlobalMessageid(mms.getGlobalMessageid());
		his.setGlobalCreateTime(System.currentTimeMillis());
		his.setGlobalReportUrl(mms.getGlobalReportUrl());
		his.setSpUrl(mms.getGlobalReportUrl());
		his.setPhone(mms.getPhone());
		his.setReqId(rsp.getMessageID());
		his.setCorrelator(mms.getMessageid());
		his.setMessageid(mms.getMessageid());
		his.setStatus(mmstatuscode+"");
		his.setMsgType(statusCode);
		his.setContent(reportStr);

		this.messageRouter.doRoute(his);

		logger.info(toMessage(mms, "success", statusCode, statusText));
	}

	public String toMessage(MT_MMHttpSPMessage mms, String result, String statusCode, String statusText) {
		try {
			StringBuilder sb = new StringBuilder("mmsagent-> mmsc one mmsmt message " + result);
			sb.append("\r\n");
			sb.append("[");
			sb.append(" gmsgid:").append(mms.getGlobalMessageid());
			sb.append(" Vasid:").append(mms.getSubmitReq().getVASID());
			sb.append(" Vaspid:").append(mms.getSubmitReq().getVASPID());
			sb.append(" To:").append(mms.getPhone());
			sb.append(" SenderAddress:").append(mms.getSubmitReq().getSenderAddress());
			sb.append(" ServiceCode: ").append(mms.getSubmitReq().getServiceCode());
			sb.append(" StatusCode: ").append(statusCode);
			sb.append(" StatusText: ").append(statusText);
			sb.append("]");

			return sb.toString();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public String reportStr(String transactionid, String sendnumber, String recipientnumber, String timestamp,
			String messageid, int returncode, String desc) {
		String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">"
				+ transactionid + "</mm7:TransactionID></env:Header><env:Body>"
				+ "<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\">"
				+ "<MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender>"
				+ "<Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp
				+ "</TimeStamp>" + "<MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID>"
				+ "<MMStatus>" + returncode + "</MMStatus><StatusText>" + desc + "</StatusText>"
				+ "</DeliveryReportReq></env:Body></env:Envelope>";
		return report;
	}

	public String getTimeStr() {
		Calendar now = Calendar.getInstance();
		Date d = new Date();
		String year = now.get(1)+"";
		String date = now.get(5)+"";
		String month = (now.get(2) + 1)+"";
		String hour = now.get(10)+"";
		String min = now.get(12)+"";
		String sec = now.get(13)+"";
		if (date.length() < 2)
			date = "0" + date;
		if (month.length() < 2)
			month = "0" + month;
		if (hour.length() < 2)
			hour = "0" + hour;
		if (min.length() < 2)
			min = "0" + min;
		if (sec.length() < 2)
			sec = "0" + sec;
		return year + "-" + month + "-" + date + "T" + hour + ":" + min + ":" + sec + "+08:00";
	}

	public void setMessageRouter(MessageRouter messageRouter) {
		this.messageRouter = messageRouter;
	}

	public void setMmsmcUrl(String mmsmcUrl) {
		this.mmsmcUrl = mmsmcUrl;
	}

	class ResponseHandle extends Thread {
		byte[] data;
		MT_MMHttpSPMessage mms;
		SubmitResp rsp;

		ResponseHandle(MT_MMHttpSPMessage mms, byte[] data, SubmitResp rsp) {
			this.data = data;
			this.mms = mms;
			this.rsp = rsp;
		}

		public void run() {
			Mm7ClientProxy.this.doResponse(this.mms, this.data, this.rsp);
		}
	}
}
