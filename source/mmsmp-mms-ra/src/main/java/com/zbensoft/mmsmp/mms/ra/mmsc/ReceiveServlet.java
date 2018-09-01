package com.zbensoft.mmsmp.mms.ra.mmsc;

import com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiveServlet.class);

	public void init(ServletConfig config) throws ServletException {
	}

	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		String messageid = generateMessageiD();
		HttpRequest request = new HttpRequest(req);
		SubmitReq submit = new SubmitReq();
		submit.parser(request.getContent());

		StringBuilder sb = new StringBuilder("");
		sb.append("Mmsc mock<-mmsAgent one mmsmt message");
		sb.append("\r\n");

		logger.info(sb.toString());

		String reportStr = SubmitResp.getSubmitResp(submit.getTransactionID(), messageid, null, null);
		out.write(reportStr);
		out.flush();
		out.close();

		sb = new StringBuilder("Mmsc mock return SubmitResp to mmsAgent");
		sb.append("\r\n");
		sb.append(reportStr);
		logger.info(sb.toString());
	}

	private String generateMessageiD() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
