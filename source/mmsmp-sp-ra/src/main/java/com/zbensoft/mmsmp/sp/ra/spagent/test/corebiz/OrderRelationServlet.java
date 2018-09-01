package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima.Sender;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.Utility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderRelationServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String cmdtext = request.getParameter("cmdtext");
		String productid = request.getParameter("productid");
		String spid = request.getParameter("spid");
		String user = request.getParameter("user");
		String notifyspurl = request.getParameter("notifyspurl");

		OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
		orderRelationUpdateNotifyRequest.setRecordSequenceId(System.currentTimeMillis()+"");
		orderRelationUpdateNotifyRequest.setContent(cmdtext);
		orderRelationUpdateNotifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
		orderRelationUpdateNotifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		orderRelationUpdateNotifyRequest.setLinkId("");
		orderRelationUpdateNotifyRequest.setServiceType("31");
		orderRelationUpdateNotifyRequest.setProductId(productid);
		orderRelationUpdateNotifyRequest.setSpId(spid);
		orderRelationUpdateNotifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		orderRelationUpdateNotifyRequest.setUpdateDesc("");
		orderRelationUpdateNotifyRequest.setUpdateTime("");
		orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(1));
		orderRelationUpdateNotifyRequest.setUserId(user);
		orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(1));

		orderRelationUpdateNotifyRequest.setNotifySPURL(notifyspurl);

		Sender.send(orderRelationUpdateNotifyRequest);

		request.getRequestDispatcher("orderrelation.jsp").forward(request, response);
	}
}
