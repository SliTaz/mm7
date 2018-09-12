

package com.zbensoft.mmsmp.ownbiz.ra.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/notify/cooper")
public class CooperServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(CooperServlet.class);

    public CooperServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        this.doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messageId = request.getParameter("messageId");
        String payStatus = request.getParameter("payStatus");
        String content = request.getParameter("Content");
        log.info("=====================接收彩信平台支付结果通知：messageId=" + messageId + ":payStatus=" + payStatus + ":Content=" + content);
        PrintWriter writer = response.getWriter();
        writer.write("接收成功!");
        writer.close();
    }
}
