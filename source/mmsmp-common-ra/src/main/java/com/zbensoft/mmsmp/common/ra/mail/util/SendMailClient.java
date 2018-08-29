package com.zbensoft.mmsmp.common.ra.mail.util;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

public class SendMailClient {
	private static SendMailClient instance = null;

	private static final Log logger = LogFactory.getLog(SendMailClient.class);

	public static SendMailClient getInstance() {
		if (instance == null) {
			synchronized (SendMailClient.class.getName()) {
				if (instance == null) {
					instance = new SendMailClient();
				}
			}
		}
		return instance;
	}

	public boolean sendEmail(int productId) {
		return false;
	}

	public boolean sendEmail(int productId, String mobile) {
		return false;
	}

	public boolean sendEmail(String smtpHost, String userName, String password, String title, String emlFilePath,
			String toMobile, int contentId, int serviceId) {
		String[] mobiles = toMobile.split(",");
		return sendEmail(smtpHost, userName, password, title, emlFilePath, mobiles, contentId, serviceId);
	}

	public boolean sendEmail(String smtpHost, String userName, String password, String title, String emlFilePath,
			String[] mobiles, int contentId, int serviceId) {
		String mobile = null;
		StringBuffer sb = new StringBuffer();
		sb.append("smtpHost = ").append(smtpHost).append("\r\n");
		sb.append("userName = ").append(userName).append("\r\n");
		sb.append("password = ").append(password).append("\r\n");
		sb.append("title = ").append(title).append("\r\n");
		sb.append("emlFilePath = ").append(emlFilePath).append("\r\n");

		for (String mobileStr : mobiles) {
			sb.append("toMobile = ").append(mobileStr).append("\r\n");
		}
		sb.append("contentId = ").append(contentId).append("\r\n");
		sb.append("serviceId = ").append(serviceId);

		logger.info(sb.toString());
		try {
			logger.info("send email begin ..........................");
			SendMail sendmail = new MailSendEml(smtpHost, userName, password);
			sendmail.setMailFrom(userName);
			sendmail.setMailTo(mobiles, "bcc");

			BASE64Encoder enc = new BASE64Encoder();
			sendmail.setSubject("=?GB2312?B?" + enc.encode(title.getBytes()) + "?=");
			sendmail.setSendDate(new Date());
			sendmail.setMailContent(emlFilePath);
			sendmail.sendMail();

			for (int i = 0; i < mobiles.length; i++) {
				mobile = mobiles[i].substring(0, mobiles[i].indexOf("@"));
			}

			logger.info("send email succeed!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("send email failure!");
			try {
				for (int i = 0; i < mobiles.length; i++) {
					mobile = mobiles[i].substring(0, mobiles[i].indexOf("@"));
				}

			} catch (Exception localException2) {
			}

			logger.info(e.getMessage());
		}
		return false;
	}

	public boolean sendEmail(String emlFilePath) {
		return false;
	}

	public static void main(String[] args) {
	}

	public boolean sendEmail(String smtpHost, String userName, String password, String emlFilePath, String subject,
			String[] rcvPhones) {
		try {
			SendMail sendmail = new MailSendEml(smtpHost, userName, password);
			sendmail.setMailFrom(userName);
			sendmail.setMailTo(rcvPhones, "bcc");
			BASE64Encoder enc = new BASE64Encoder();
			sendmail.setSubject("=?GB2312?B?" + enc.encode(subject.getBytes()) + "?=");
			sendmail.setSendDate(new Date());
			sendmail.setMailContent(emlFilePath);
			sendmail.sendMail();
			return true;
		} catch (Exception e) {
			logger.error("send mme error: " + e.getMessage());
		}
		return false;
	}
}
