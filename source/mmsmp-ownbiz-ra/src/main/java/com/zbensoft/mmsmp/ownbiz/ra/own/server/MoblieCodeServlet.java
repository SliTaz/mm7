

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.DESUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MoblieCodeServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MoblieCodeServlet.class);
    private String strurl = null;
    public static final String RETRUECODE_KEY = "returnCode";
    public static final String RETRUERDESC_KEY = "returnDesc";
    public static final String MOBILE = "mobile";

    public MoblieCodeServlet() {
    }

    public void destroy() {
        super.destroy();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) {
        String unikey = request.getParameter("unikey");
        String operStr = String.format("用户访问IP：%s，接入unikey:%s", request.getRemoteAddr(), unikey);
        PrintWriter out = null;

        String strResult;
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            String reqcode;
            if (StringUtils.isBlank(unikey) || !unikey.matches("^\\w{32}$")) {
                reqcode = this.getResult(AppContants.RETRUE_MOBILE_PARAM_CODE, AppContants.RETRUE_MOBLIE_PARAM_DESC, "", operStr);
                this.write(out, reqcode);
                return;
            }

            reqcode = HttpUtil.getMethod(this.strurl + unikey);
            logger.info(String.format("%s,获取手机号码为:%s", operStr, reqcode));
            strResult = this.getResult(AppContants.RETRUE_MOBILE_GET_SUCCESS_CODE, AppContants.RETRUE_MOBILE_GET_SUCCESS_DESC, reqcode, operStr);
            this.write(out, strResult);
        } catch (Exception var8) {
            logger.error(String.format("用户net方式获取手机号出现异常，错误信息：%s", var8));
            var8.printStackTrace();
            strResult = this.getResult(AppContants.RETURN_CODE_UNEXPECT_ERROR, AppContants.RETURN_CODE_UNEXPECT_ERROR_DESC, "", operStr);
            this.write(out, strResult);
        }

    }

    public void init() throws ServletException {
        this.strurl = AppContants.MOBILE_URL;
        logger.info(String.format("获取手机号net方式接入url地址为：%s", this.strurl));
    }

    public String getResult(int returnCode, String returnDesc, String mobile, String operStr) {
        Map<String, Object> returnMap = new HashMap();
        if (mobile == null || "0".equals(mobile)) {
            mobile = "";
            returnCode = AppContants.RETRUE_MOBILE_GET_CODE;
            returnDesc = AppContants.RETRUE_MOBILE_GET_DESC;
        }

        String desMobile = null;
        if (StringUtils.isNotBlank(mobile) && !"0".equals(mobile)) {
            desMobile = DESUtil.encrypt(this.appendDateforMobile(mobile), AppContants.DES_ENCRYPTION_KEY);

            try {
                desMobile = URLEncoder.encode(desMobile, "utf-8");
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
            }
        }

        returnMap.put("returnCode", returnCode);
        returnMap.put("returnDesc", returnDesc);
        returnMap.put("mobile", desMobile == null ? mobile : desMobile);
        String strResult = JSONObject.fromObject(returnMap).toString();
        logger.info(String.format("%s,结果码returnCode = %d,结果描述returnDesc = %s ，获得手机号 mobile = %s，返回结果%s", operStr, returnCode, returnDesc, mobile, strResult));
        return strResult;
    }

    public void write(PrintWriter out, String strResult) {
        if (out != null) {
            try {
                out.write(strResult);
                out.flush();
            } catch (Exception var5) {
                ;
            }

            try {
                out.close();
            } catch (Exception var4) {
                ;
            }
        }

    }

    public String appendDateforMobile(String mobile) {
        StringBuffer newMobile = new StringBuffer();
        newMobile.append(mobile);
        newMobile.append(",");
        newMobile.append(System.currentTimeMillis());
        logger.info(String.format("组装手机号+日期（long）字段值为：%s", newMobile.toString()));
        return newMobile.toString();
    }
}
