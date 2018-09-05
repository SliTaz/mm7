

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.CooperKeyDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SendValidateCodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(SendValidateCodeServlet.class);
    private CooperKeyDao cooperKeyDao = null;
    private ProxyPayMessageDao proxyPayMessageDao = null;
    private MessageQuene messageQuene = null;

    public SendValidateCodeServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        if (this.messageQuene == null) {
            this.messageQuene = MessageQuene.getInstance();
        }

        this.cooperKeyDao = (CooperKeyDao)this.messageQuene.getDao("cooperKeyDao");
        this.proxyPayMessageDao = (ProxyPayMessageDao)this.messageQuene.getDao("proxyPayMessageDao");
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        this.doExecute(request, response);
    }

    private void doExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        Map<String, Object> returnMap = new HashMap();
        String strResult = "";

        try {
            String strAccountId = request.getParameter("accountId");
            String strMobile = request.getParameter("mobile");
            String strContent = request.getParameter("Content");
            log.info("接收下发验证码请求：accountId=" + strAccountId + ",mobile=" + strMobile + ",Content=" + strContent);
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isBlank(strAccountId) || StringUtils.isBlank(strMobile)) {
                log.error(AppContants.RETURN_CODE_DATA_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_DATA_NULL);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_DATA_NULL_DESC);
                strResult = JSONObject.fromObject(returnMap).toString();
                writer.write(strResult);
                return;
            }

            if (strMobile.length() != 11) {
                log.error(AppContants.RETURN_CODE_MOBILE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_MOBILE_ERROR);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_MOBILE_ERROR_DESC);
                strResult = JSONObject.fromObject(returnMap).toString();
                writer.write(strResult);
                return;
            }

            CooperKeyEntity entity = DataCache.getCooperKey(strAccountId);
            String key = "";
            if (entity == null || StringUtils.isBlank(entity.getCooperKey())) {
                entity = this.cooperKeyDao.getCooperKeyEntityByAccountId(strAccountId);
            }

            if (entity == null || StringUtils.isBlank(entity.getCooperKey())) {
                log.error(AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC);
                strResult = JSONObject.fromObject(returnMap).toString();
                writer.write(strResult);
                return;
            }

            key = entity.getCooperKey();
            sb.append(key).append(strAccountId).append(strMobile);
            if (!ValidateMessageUtil.doValidateMessage(sb.toString(), strContent)) {
                log.error(AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC);
                strResult = JSONObject.fromObject(returnMap).toString();
                writer.write(strResult);
                return;
            }

            String messageId = UUIDUtils.getPPSUuid();
            String validateCode = ValidateCodeUtil.getValidateCode();
            ProxyPayMessage proxyPayMessage = new ProxyPayMessage();
            proxyPayMessage.setGlobalMessageid(messageId);
            proxyPayMessage.setAccountId(strAccountId);
            proxyPayMessage.setPhoneNumber(strMobile);
            String strDate = DateUtil.getFormatDate();
            proxyPayMessage.setCreateDate(strDate);
            proxyPayMessage.setUpdateDate(strDate);
            proxyPayMessage.setValidateCode(validateCode);
            proxyPayMessage.setSmsText(validateCode);
            proxyPayMessage.setFeeType("9");
            proxyPayMessage.setStatus("9");
            proxyPayMessage.setSourceType(1);
            proxyPayMessage.setCooperKey(key);
            proxyPayMessage.setNotifyURL(entity.getNotifyUrl());
            proxyPayMessage.setPayRequestTime(System.currentTimeMillis());
            this.proxyPayMessageDao.insert(proxyPayMessage);
            log.info("接收成功,生成消息ID(" + messageId + ")提交corebiz进行验证码(" + validateCode + ")处理");
            this.messageQuene.addProxyPayMap(messageId, proxyPayMessage);
            String smsTextTemplate = (String)DataCache.get("OWN_VALIDCODE_SMS");
            proxyPayMessage.setSmsText(smsTextTemplate.replace("{0}", proxyPayMessage.getSmsText()));
            log.info("send sms msg =" + proxyPayMessage.getSmsText());
            proxyPayMessage.setFeeType("9");
            this.messageQuene.getRequestQuence().add(proxyPayMessage);
            returnMap.put("returnCode", AppContants.RETURN_CODE_SUCCESS);
            returnMap.put("returnDesc", AppContants.RETURN_CODE_SUCCESS_DESC);
            returnMap.put("messageId", messageId);
            strResult = JSONObject.fromObject(returnMap).toString();
            writer.write(strResult);
        } catch (Exception var20) {
            log.error(var20.getMessage(), var20);
            returnMap.put("returnCode", AppContants.RETURN_CODE_UNEXPECT_ERROR);
            returnMap.put("returnDesc", AppContants.RETURN_CODE_UNEXPECT_ERROR_DESC);
            strResult = JSONObject.fromObject(returnMap).toString();
            writer.write(strResult);
        } finally {
            writer.close();
            returnMap = null;
        }

    }
}
