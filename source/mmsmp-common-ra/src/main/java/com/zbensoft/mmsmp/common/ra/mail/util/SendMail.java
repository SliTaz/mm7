package com.zbensoft.mmsmp.common.ra.mail.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import sun.misc.BASE64Encoder;

abstract class SendMail {
	protected BASE64Encoder basEncoder = new BASE64Encoder();
	protected BodyPart messageBodyPart = null;
	protected Multipart multipart = null;
	protected MimeMessage mailMessage = null;
	protected Session mailSession = null;
	protected Properties mailProperties = System.getProperties();

	protected InternetAddress mailFromAddress = null;
	protected InternetAddress mailToAddress = null;
	protected MailAuthenticator authenticator = null;

	protected String mailSubject = "";
	protected Date mailSendDate = null;

	public SendMail(String smtpHost, String username, String password) {
		this.mailProperties.put("mail.smtp.host", smtpHost);
		this.mailProperties.put("mail.smtp.auth", "true");
		this.authenticator = new MailAuthenticator(username, password);
		this.mailSession = Session.getDefaultInstance(this.mailProperties, this.authenticator);
		this.mailMessage = new MimeMessage(this.mailSession);
		this.messageBodyPart = new MimeBodyPart();
	}

	public void setSubject(String mailSubject) throws MessagingException {
		this.mailSubject = mailSubject;
		this.mailMessage.setSubject(mailSubject);
	}

	protected abstract void setMailContent(String paramString) throws MessagingException;

	public void setSendDate(Date sendDate) throws MessagingException {
		this.mailSendDate = sendDate;
		this.mailMessage.setSentDate(sendDate);
	}

	public void setAttachments(String attachmentName) throws MessagingException {
		this.messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachmentName);
		this.messageBodyPart.setDataHandler(new DataHandler(source));
		int index = attachmentName.lastIndexOf('\\');
		String attachmentRealName = attachmentName.substring(index + 1);
		this.messageBodyPart.setFileName(attachmentRealName);
		this.multipart.addBodyPart(this.messageBodyPart);
	}

	public void setMailFrom(String mailFrom) throws MessagingException {
		InternetAddress mailFromAddress = new InternetAddress();
		mailFromAddress.setAddress(mailFrom);
		BASE64Encoder enc = new BASE64Encoder();

		this.mailMessage.setFrom(mailFromAddress);
	}

	public void setMailTo(String[] mailTo, String mailType) throws Exception {
		for (int i = 0; i < mailTo.length; i++) {
			this.mailToAddress = new InternetAddress(mailTo[i]);

			if (mailType.equalsIgnoreCase("to")) {
				this.mailMessage.addRecipient(Message.RecipientType.TO, this.mailToAddress);
			} else if (mailType.equalsIgnoreCase("cc")) {
				this.mailMessage.addRecipient(Message.RecipientType.CC, this.mailToAddress);
			} else if (mailType.equalsIgnoreCase("bcc")) {
				this.mailMessage.addRecipient(Message.RecipientType.BCC, this.mailToAddress);
			} else
				throw new Exception("Unknown mailType: " + mailType + "!");
		}
	}

	public void sendMail() throws MessagingException, SendFailedException {
		if (this.mailToAddress == null) {
			System.out.println("请填写邮件接收方地址");
		} else {
			this.mailMessage.setContent(this.multipart);
			System.out.println("准备发送邮件.......");
			Transport.send(this.mailMessage);
			System.out.println("邮件发送完毕！");
		}
	}
}
