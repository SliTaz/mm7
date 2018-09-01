package com.zbensoft.mmsmp.sp.ra.spagent.sp.momms;

import com.zbensoft.mmsmp.sp.ra.spagent.utils.HttpRequest;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.XmlElementReplace;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletInputStream;
import org.apache.log4j.Logger;

public class MoMMSToSpSender {
	private static final Logger logger = Logger.getLogger(MoMMSToSpSender.class);
	private HttpRequest request;

	public MoMMSToSpSender(HttpRequest request) {
		this.request = request;
	}

	public String send() {
		String res = "";
		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			logger.info("url:" + this.request.getSendurl());
			URL theURL = new URL(this.request.getSendurl());
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setReadTimeout(5000);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("Content-Type", this.request.getContentType());

			httpURL.setRequestProperty("aceway-to", "mmsc.aceway.com.cn");

			httpURL.setRequestProperty("Connection", "keep-alive");
			httpURL.setRequestProperty("Content-Length", this.request.getInputStream().available()+"");
			InputStream in = this.request.getInputStream();
			if ((this.request.getLinkId() != null) && (this.request.getLinkId().length() > 0)) {
				logger.info("starting to add linkid element.....");
				in = XmlElementReplace.replaceLinkId(in, this.request.getLinkId());
			}

			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] inb = new byte[1024];
			int count = 0;
			while ((count = in.read(inb)) > 0) {
				dos.write(inb, 0, count);
			}
			dos.flush();
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
				logger.error(
						"sp return error message: [" + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
			}
		} catch (Throwable ex) {
			res = "HTTP/1.1 404";
			logger.error("", ex);
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (Exception ex1) {
					logger.error(ex1.getMessage());
				}

			if (dos != null)
				try {
					dos.close();
				} catch (Exception ex2) {
					logger.error(ex2.getMessage());
				}

			if (httpURL != null) {
				httpURL.disconnect();
			}
		}
		logger.info("mo mms response: " + res);
		return res;
	}
}
