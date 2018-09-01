package com.zbensoft.mmsmp.common.ra.wo.order;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class UserOrderServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(UserOrderServlet.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		byte[] data = new byte[1024];
		ServletInputStream inS = req.getInputStream();
		StringBuffer sInXml = new StringBuffer();
		int nR;
		while ((nR = inS.read(data)) > 0) {
//			int nR;
			sInXml.append(new String(data, 0, nR));
		}
		inS.close();
		String content = sInXml.toString();
		UserOrderRequest request = UserOrderXml.parseDocument(content);
		String response = UserOrderClient.userOrder(request);
		out.println(response);
		out.flush();
		out.close();
	}
}
