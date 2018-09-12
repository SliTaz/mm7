

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.alibaba.fastjson.JSON;
import com.zbensoft.mmsmp.common.ra.common.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.DateUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/billing/confirm")
public class UserBillingConfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(UserBillingConfirmServlet.class);
    private ProxyPayMessageDao proxyPayMessageDao = null;
    private MessageQuene messageQuene = null;

    public UserBillingConfirmServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        if (this.messageQuene == null) {
            this.messageQuene = MessageQuene.getInstance();
        }

        this.proxyPayMessageDao = (ProxyPayMessageDao)this.messageQuene.getDao("proxyPayMessageDao");
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        this.doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Map<String, Object> returnMap = new HashMap();
        String strResult = "";

        try {
            String strGlobalMessageId = request.getParameter("messageId");
            String strValidateCode = request.getParameter("validateCode");
            String strContent = request.getParameter("Content");
            log.info("接收手机端支付二次确认请求：messageId=" + strGlobalMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent);
            StringBuffer sb = new StringBuffer();
            if (!StringUtils.isBlank(strGlobalMessageId) && !StringUtils.isBlank(strValidateCode)) {
                long billingTime = System.currentTimeMillis();
                ProxyPayMessage proxyPayMessage = this.messageQuene.removeProxyPayMap(strGlobalMessageId);
                if (proxyPayMessage != null && billingTime - proxyPayMessage.getPayRequestTime() <= AppContants.PAY_REQUEST_MAX_KEEP_TIME) {
                    String key = proxyPayMessage.getCooperKey();
                    if (StringUtils.isBlank(key)) {
                        log.error(AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC);
                        returnMap.put("returnCode", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL);
                        returnMap.put("returnDesc", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC);
                        strResult =  JSON.toJSONString(returnMap);
                        writer.write(strResult);
                    } else {
                        sb.append(key).append(strValidateCode);
                        if (!ValidateMessageUtil.doValidateMessage(sb.toString(), strContent)) {
                            log.error(AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC + "(messageId=" + strGlobalMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                        } else if (!strValidateCode.equals(proxyPayMessage.getValidateCode())) {
                            log.error(AppContants.RETURN_CODE_VALIDATE_CODE_ERROR_DESC + "(messageId=" + strGlobalMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_VALIDATE_CODE_ERROR);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_VALIDATE_CODE_ERROR_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                        } else {
                            proxyPayMessage.setSourceType(2);
                            String strDate = DateUtil.getFormatDate();
                            proxyPayMessage.setUpdateDate(strDate);
                            proxyPayMessage.setPayRequestTime(System.currentTimeMillis());
                            proxyPayMessage.setStatus("400");
                            this.proxyPayMessageDao.update(proxyPayMessage);
                            log.info("接收成功，提交corebiz处理(" + strGlobalMessageId + ")");
                            this.messageQuene.addProxyPayMap(strGlobalMessageId, proxyPayMessage);
                            this.messageQuene.getRequestQuence().add(proxyPayMessage);
                            returnMap.put("returnCode", AppContants.RETURN_CODE_SUCCESS);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_SUCCESS_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                        }
                    }
                } else {
                    log.error(AppContants.RETURN_CODE_APP_PAY_REQUESt_NULL_OR_OUT_DESC + "(messageId=" + strGlobalMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                    returnMap.put("returnCode", AppContants.RETURN_CODE_APP_PAY_REQUESt_NULL_OR_OUT);
                    returnMap.put("returnDesc", AppContants.RETURN_CODE_APP_PAY_REQUESt_NULL_OR_OUT_DESC);
                    strResult =  JSON.toJSONString(returnMap);
                    writer.write(strResult);
                }
            } else {
                log.error(AppContants.RETURN_CODE_DATA_NULL_DESC + "(messageId=" + strGlobalMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_DATA_NULL);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_DATA_NULL_DESC);
                strResult =  JSON.toJSONString(returnMap);
                writer.write(strResult);
            }
        } catch (Exception var15) {
            log.error(var15.getMessage(), var15);
            returnMap.put("returnCode", AppContants.RETURN_CODE_UNEXPECT_ERROR);
            returnMap.put("returnDesc", AppContants.RETURN_CODE_UNEXPECT_ERROR_DESC);
            strResult =  JSON.toJSONString(returnMap);
            writer.write(strResult);
        }
    }
}
