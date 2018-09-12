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


@WebServlet(urlPatterns = "/sp")
public class SpServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(SpServlet.class);

    public SpServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        this.doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPhone = request.getParameter("mobile");
        String productId = request.getParameter("productId");
        String type = request.getParameter("type");
        if (type == null) {
            log.info("通知类型为空！");
        } else {
            if (type.equals("3")) {
                log.info("=====================接收彩信平台点播通知结果：" + userPhone + ":" + productId + ":" + type);
            } else {
                log.info("=====================接收彩信平台订购通知结果：" + userPhone + ":" + productId + ":" + type);
            }

            PrintWriter writer = response.getWriter();
            writer.write("接收成功!");
            writer.close();
        }
    }
}