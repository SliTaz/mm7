package com.zbensoft.mmsmp.common.ra.wo.quitOrder;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class QuitUserOrderServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(QuitUserOrderServlet.class);
	private QuitUserOrderService quitUserOrderService;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String outstr = "";
		String sequence_id = req.getParameter("sequence_id");
		logger.info("sequence_id is : " + sequence_id);
		String userid = req.getParameter("userid");
		logger.info("userid is : " + userid);
		String userName = req.getParameter("userName");
		logger.info("userName is : " + userName);
		String mobile = req.getParameter("mobile");
		logger.info("mobile is : " + mobile);
		String res_id = req.getParameter("res_id");
		logger.info("res_id is : " + res_id);
		String Order_type = req.getParameter("Order_type");
		logger.info("Order_type is : " + Order_type);
		if (isNull(sequence_id))
			outstr = "sequence_id不能为空";
		else if (isNull(userid))
			outstr = "userid不能为空";
		else if (isNull(mobile))
			outstr = "mobile不能为空";
		else if (isNull(res_id)) {
			outstr = "res_id不能为空";
		}

		out.println(outstr);
		out.flush();
		out.close();
	}

	private boolean isNull(String str) {
		if ((str != null) && (!"".equals(str))) {
			return false;
		}
		return true;
	}

	public QuitUserOrderService getQuitUserOrderService() {
		return this.quitUserOrderService;
	}

	public void setQuitUserOrderService(QuitUserOrderService quitUserOrderService) {
		this.quitUserOrderService = quitUserOrderService;
	}
}
