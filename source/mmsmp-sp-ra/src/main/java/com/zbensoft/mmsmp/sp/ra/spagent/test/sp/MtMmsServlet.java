package com.zbensoft.mmsmp.sp.ra.spagent.test.sp;

import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.MoMms;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class MtMmsServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(MtMmsServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String chargePhoneNumber = request.getParameter("chargePhoneNumber");
		String phonenumber = request.getParameter("phonenumber");
		String accnumber = request.getParameter("accnumber");
		String vaspid = request.getParameter("vaspid");
		String vasid = request.getParameter("vasid");
		String servicecode = request.getParameter("servicecode");
		String linkid = request.getParameter("linkid");

		logger.info("phonenumber:" + phonenumber + "    accnumber:" + accnumber);

		String res = "";
		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			URL theURL = new URL("http://localhost:8080/spagent/mm7Receive");

			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setReadTimeout(5000);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("Authorization", "liss");
			httpURL.setRequestProperty("Content-Type", "text/xml;charset=\"UTF-8\"");
			httpURL.setRequestProperty("Content-Transfer-Encoding", "utf-8");
			httpURL.setRequestProperty("Connection", "keep-alive");

			String mms = MoMms.getMms(chargePhoneNumber, phonenumber, accnumber, vaspid, vasid, servicecode, linkid);
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] inb = mms.getBytes();
			dos.write(inb);
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("sp submit mms, the return message from spagent \r\n" + res);

		request.getRequestDispatcher("mtmms.jsp").forward(request, response);
	}
}
