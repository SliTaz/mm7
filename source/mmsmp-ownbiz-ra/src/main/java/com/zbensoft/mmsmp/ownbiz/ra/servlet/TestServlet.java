package com.zbensoft.mmsmp.ownbiz.ra.servlet;

import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.ImgeUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet(urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(TestServlet.class);

    public TestServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        this.doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String operateType = request.getParameter("operateType");
//        String strAccountId = request.getParameter("accountId");
//        String strMobile = request.getParameter("mobile");
//        if (strMobile != null) {
//            strMobile = URLDecoder.decode(strMobile, "utf-8");
//        }
//
//        String key = request.getParameter("key");
//        if (operateType == null) {
//            operateType = "9";
//        }
//
//        StringBuffer sb = new StringBuffer();
//        String filePath;
//        String strInstruction;
//        if (operateType.equals("1")) {
//            sb.append(key).append(strAccountId).append(strMobile);
//            filePath = ValidateMessageUtil.getMD5String(sb.toString());
//            filePath = AppContants.TEST_URl + "getVerificationCode?accountId=" + strAccountId + "&mobile=" + strMobile + "&Content=" + filePath;
//
//            try {
//                strInstruction = HttpUtil.accessUrl(filePath);
//                JSONObject obj = JSONObject.fromObject(strInstruction);
//                if (!obj.get("returnCode").toString().equals("1000")) {
//                    request.setAttribute("returnDesc", obj.get("returnDesc"));
//                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                    return;
//                }
//
//                request.setAttribute("accountId", strAccountId);
//                request.setAttribute("mobile", strMobile);
//                request.setAttribute("messageId", obj.get("messageId"));
//                request.setAttribute("validateCode", obj.get("validateCode"));
//                request.setAttribute("key", key);
//            } catch (IOException var20) {
//                var20.printStackTrace();
//                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//            }
//
//            request.getRequestDispatcher("/jsp/UserBillingWebTest.jsp").forward(request, response);
//        } else {
//            String strResponse;
//            String url;
//            String content;
//            if (operateType.equals("2")) {
//                filePath = request.getParameter("messageId");
//                filePath = request.getParameter("productId");
//                strInstruction = request.getParameter("instruction");
//                content = request.getParameter("validateCode");
//                sb.append(key).append(filePath).append(strInstruction).append(strAccountId).append(strMobile).append(filePath).append(content);
//                strResponse = ValidateMessageUtil.getMD5String(sb.toString());
//                strResponse = AppContants.TEST_URl + "web";
//                strResponse = strResponse + "?accountId=" + strAccountId;
//                strResponse = strResponse + "&mobile=" + strMobile;
//                strResponse = strResponse + "&productId=" + filePath;
//                strResponse = strResponse + "&instruction=";
//                if (strInstruction != null) {
//                    strResponse = strResponse + URLEncoder.encode(strInstruction, "utf-8");
//                }
//
//                strResponse = strResponse + "&messageId=" + filePath;
//                strResponse = strResponse + "&validateCode=" + content;
//                strResponse = strResponse + "&Content=" + strResponse;
//
//                try {
//                    url = HttpUtil.accessUrl(strResponse);
//                    JSONObject obj = JSONObject.fromObject(url);
//                    if (!obj.get("returnCode").toString().equals("1000")) {
//                        request.setAttribute("returnDesc", obj.get("returnDesc"));
//                        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                        return;
//                    }
//
//                    request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);
//                } catch (IOException var19) {
//                    var19.printStackTrace();
//                    request.setAttribute("returnDesc", "发送错误异常");
//                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                }
//            } else if (operateType.equals("3")) {
//                filePath = AppContants.TEST_URl + "app";
//                filePath = request.getParameter("productId");
//                strInstruction = request.getParameter("instruction");
//                sb.append(key).append(strAccountId).append(strMobile).append(filePath).append(strInstruction);
//                content = ValidateMessageUtil.getMD5String(sb.toString());
//                strMobile = URLEncoder.encode(strMobile, "utf-8");
//                filePath = filePath + "?mobile=" + strMobile;
//                filePath = filePath + "&accountId=" + strAccountId;
//                filePath = filePath + "&productId=" + filePath;
//                filePath = filePath + "&instruction=";
//                if (strInstruction != null) {
//                    filePath = filePath + URLEncoder.encode(strInstruction, "utf-8");
//                }
//
//                filePath = filePath + "&Content=" + content;
//
//                try {
//                    strResponse = HttpUtil.accessUrl(filePath);
//                    JSONObject obj = JSONObject.fromObject(strResponse);
//                    if (!obj.get("returnCode").toString().equals("1000")) {
//                        request.setAttribute("returnDesc", obj.get("returnDesc"));
//                        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                        return;
//                    }
//
//                    request.setAttribute("returnCode", obj.get("returnCode"));
//                    request.setAttribute("returnDesc", obj.get("returnDesc"));
//                    request.setAttribute("productId", obj.get("productid"));
//                    request.setAttribute("productName", obj.get("productName"));
//                    request.setAttribute("appName", obj.get("appName"));
//                    request.setAttribute("cpName", obj.get("cpName"));
//                    request.setAttribute("price", obj.get("price"));
//                    request.setAttribute("serviceTel", obj.get("serviceTel"));
//                    request.setAttribute("tips", obj.get("tips"));
//                    request.setAttribute("confirmurl", obj.get("confirmurl"));
//                    request.setAttribute("key", key);
//                    url = obj.get("confirmurl").toString();
//                    File file = new File(request.getSession().getServletContext().getRealPath("/") + "/valcodeImg/");
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//
//                    filePath = "/valcodeImg/" + url.split("messageId=")[1] + ".jpeg";
//                    ImgeUtil.generateImage(obj.getString("validateCode"), request.getSession().getServletContext().getRealPath("/") + filePath);
//                    request.setAttribute("validateCodeFile", filePath);
//                    request.getRequestDispatcher("/jsp/UserBillingConfirmTest.jsp").forward(request, response);
//                } catch (IOException var18) {
//                    var18.printStackTrace();
//                    request.setAttribute("returnDesc", "发送错误异常");
//                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                }
//            } else if (operateType.equals("4")) {
//                filePath = request.getParameter("confirmurl");
//                filePath = request.getParameter("validateCodeFile");
//                File file = new File(request.getSession().getServletContext().getRealPath("/") + filePath);
//                file.delete();
//                content = request.getParameter("validateCode");
//                sb.append(key).append(content);
//                strResponse = ValidateMessageUtil.getMD5String(sb.toString());
//                filePath = filePath + "&validateCode=" + content;
//                filePath = filePath + "&Content=" + strResponse;
//
//                try {
//                    strResponse = HttpUtil.accessUrl(filePath);
//                    JSONObject obj = JSONObject.fromObject(strResponse);
//                    if (!obj.get("returnCode").toString().equals("1000")) {
//                        request.setAttribute("returnDesc", obj.get("returnDesc"));
//                        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                        return;
//                    }
//
//                    request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);
//                } catch (IOException var17) {
//                    var17.printStackTrace();
//                    request.setAttribute("returnDesc", "发送错误异常");
//                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                }
//            } else if (operateType.equals("5")) {
//                try {
//                    filePath = request.getParameter("validateCodeFile");
//                    File file = new File(request.getSession().getServletContext().getRealPath("/") + filePath);
//                    file.delete();
//                    request.getRequestDispatcher("/jsp/UserBillingAppTest.jsp").forward(request, response);
//                    return;
//                } catch (IOException var21) {
//                    var21.printStackTrace();
//                    request.setAttribute("returnDesc", "发送错误异常");
//                    request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
//                }
//            }
//        }

    }
}
