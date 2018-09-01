package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima.Sender;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoMmsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phonenumber");
		String accnumber = request.getParameter("accnumber");
		String linkid = request.getParameter("linkid");
		String spurl = request.getParameter("spurl");
		String xml = MoMms.getMms(phone, accnumber, "90111", "31002100", "3100210001", linkid);

		List list = new ArrayList();
		list.add(accnumber);

		MO_MMDeliverSPMessage me = new MO_MMDeliverSPMessage();
		me.setContentByte(xml.getBytes());
		me.setContentType("multipart/related");
		me.setSender(phone);
		me.setTo(list);
		me.setLinkedID(linkid);
		me.setSendurl(spurl);

		Sender.send(me);

		request.getRequestDispatcher("momms.jsp").forward(request, response);
	}
}
