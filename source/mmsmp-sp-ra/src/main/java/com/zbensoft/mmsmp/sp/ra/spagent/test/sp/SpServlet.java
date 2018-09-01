package com.zbensoft.mmsmp.sp.ra.spagent.test.sp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class SpServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(SpServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = "";
		String content = "";
		while ((line = in.readLine()) != null) {
			content = content + line;
		}
		logger.fatal(content);
	}
}
