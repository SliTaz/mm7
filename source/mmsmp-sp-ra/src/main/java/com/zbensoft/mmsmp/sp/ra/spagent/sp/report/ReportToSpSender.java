package com.zbensoft.mmsmp.sp.ra.spagent.sp.report;

import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.apache.log4j.Logger;

public class ReportToSpSender {
	private static final Logger logger = Logger.getLogger(ReportToSpSender.class);

	public void sendReportToSp(MO_ReportMessage morpt) {
		String reportUrl = morpt.getReportUrl();
		if ((reportUrl == null) || (reportUrl.trim().length() == 0)) {
			logger.error("sp report url is null....");
			return;
		}
		HttpURLConnection httpURL = null;
		DataOutputStream dos = null;
		InputStream in = null;
		try {
			logger.info("=====>spreporturl:" + reportUrl);
			URL theURL = new URL(reportUrl);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setRequestProperty("content-type", "text/xml;charset=\"UTF-8\"");
			httpURL.setRequestProperty("Content-Transfer-Encoding", "8bit");
			httpURL.setRequestProperty("Connection", "keep-alive");
			httpURL.setRequestMethod("POST");

			httpURL.setConnectTimeout(1000);
			httpURL.setReadTimeout(1000);

			String reportStr = morpt.getContent();
			in = new ByteArrayInputStream(reportStr.getBytes("UTF-8"));
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] buf = reportStr.getBytes();
			int n = 0;
			while ((n = in.read(buf)) != -1) {
				dos.write(buf, 0, n);
			}
			dos.flush();
			logger.info("=====>reportStr :" + reportStr);
			logger.info("=====>send report to sp ,return code :" + httpURL.getResponseCode());
		} catch (MalformedURLException e) {
			logger.info("=====>send report to sp ,MalformedURLException :", e);
		} catch (ProtocolException e) {
			logger.info("=====>send report to sp ,ProtocolException :", e);
		} catch (UnsupportedEncodingException e) {
			logger.info("=====>send report to sp ,UnsupportedEncodingException :", e);
		} catch (IOException e) {
			try {
				logger.info("=====>send report to sp ,return code :" + httpURL.getResponseCode());
			} catch (IOException e1) {
				logger.info("=====>send report to sp ,IOException :", e);
			}
		} catch (Exception e) {
			logger.error("sendReportToSp is error", e);
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (Exception ex2) {
					logger.error(ex2.getMessage());
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (httpURL != null)
				httpURL.disconnect();
		}
	}

	public static void main(String[] args) {
		MO_ReportMessage morpt = new MO_ReportMessage();
		morpt.setReportUrl("http://localhost:29095/SPServerServlet");
		morpt.setContent("qqqqqqqqqqq");
		ReportToSpSender sender = new ReportToSpSender();
		sender.sendReportToSp(morpt);
	}
}
