package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima.Sender;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MoSmsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String phone = request.getParameter("phonenumber");
		String accnumber = request.getParameter("accnumber");
		String content = request.getParameter("content");
		String linkid = request.getParameter("linkid");
		String notifyurl = request.getParameter("notifyurl");

		MO_SMMessage mosms = new MO_SMMessage();
		mosms.setSendAddress(phone);
		mosms.setVasId(accnumber);
		mosms.setSmsText(content);
		mosms.setLinkId(linkid);
		mosms.setNotirySPURL(notifyurl);

		mosms.setSequence_Number_1(Integer.valueOf(111));
		mosms.setSequence_Number_2(Integer.valueOf(222));
		mosms.setSequence_Number_3(Integer.valueOf(333));
		mosms.setMessageCoding((byte) 0);
		mosms.setTP_pid((byte) 5);
		mosms.setTP_udhi((byte) 6);
		mosms.setContentLength(mosms.getSmsText().length());

		Sender.send(mosms);

		request.getRequestDispatcher("mosms.jsp").forward(request, response);
	}
}
