package com.zbensoft.mmsmp.sp.ra.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(urlPatterns = "/SPServerServlet")
public class SPReceiveServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(SPReceiveServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		logger.info("into SPReceiveServlet service method");
		
		if (!"POST".equals(req.getMethod())) {
			logger.error("your request must be POST");
			response.sendError(400, "Http Method Must Be POST!");
			return;
		}
		
	}
}
