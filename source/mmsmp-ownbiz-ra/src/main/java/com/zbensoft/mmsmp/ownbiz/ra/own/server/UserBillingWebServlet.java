

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.alibaba.fastjson.JSON;
import com.zbensoft.mmsmp.common.ra.common.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.UserOrderDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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


@WebServlet(urlPatterns = "/billing/web")
public class UserBillingWebServlet extends HttpServlet {
    private static final long serialVersionUID = 5321499768558169999L;
    private static final Log log = LogFactory.getLog(UserBillingWebServlet.class);
    private static MessageQuene messageQuene = null;
    private ProxyPayMessageDao proxyPayMessageDao = null;
    private VasServiceRelationDao vasServiceRelationDao = null;
    private static UserOrderDao userOrderDao;

    public UserBillingWebServlet() {
    }

    public static UserOrderDao getUserOrder() {
        if (userOrderDao == null) {
            userOrderDao = (UserOrderDao)(new ClassPathXmlApplicationContext("applicationContext.xml")).getBean("userOrderDao");
        }

        return userOrderDao;
    }

    public void init(ServletConfig config) throws ServletException {
        if (messageQuene == null) {
            messageQuene = MessageQuene.getInstance();
        }

        this.proxyPayMessageDao = (ProxyPayMessageDao)messageQuene.getDao("proxyPayMessageDao");
        this.vasServiceRelationDao = (VasServiceRelationDao)messageQuene.getDao("vasServiceRelationDao");
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
            String strProductId = request.getParameter("productId");
            String strInstruction = request.getParameter("instruction");
            String strMessageId = request.getParameter("messageId");
            String strValidateCode = request.getParameter("validateCode");
            String strContent = request.getParameter("Content");
            log.info("接收web端支付请求：accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent);
            StringBuffer sb = new StringBuffer();
            if (!StringUtils.isBlank(strAccountId) && !StringUtils.isBlank(strProductId) && !StringUtils.isBlank(strInstruction) && !StringUtils.isBlank(strMessageId) && !StringUtils.isBlank(strValidateCode) && !StringUtils.isBlank(strMobile)) {
                if (strMobile.length() == 11) {
                    ProxyPayMessage proxyPayMessage = null;
                    proxyPayMessage = messageQuene.removeProxyPayMap(strMessageId);
                    long requestTime = System.currentTimeMillis();
                    if (proxyPayMessage != null && requestTime - proxyPayMessage.getPayRequestTime() <= AppContants.PAY_REQUEST_MAX_KEEP_TIME) {
                        if (!proxyPayMessage.getPhoneNumber().equals(strMobile)) {
                            log.error(AppContants.RETURN_CODE_MOBILE_UNEQUAL_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_MOBILE_UNEQUAL);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_MOBILE_UNEQUAL_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        if (proxyPayMessage.getStatus().equals("400")) {
                            log.error(AppContants.RETURN_CODE_REPEAT_REQUEST_DESC);
                            returnMap.put("returnCode", AppContants.RETURN_CODE_REPEAT_REQUEST + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_REPEAT_REQUEST_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        StringBuffer controlKey = new StringBuffer();
                        controlKey = controlKey.append(strMobile).append(strProductId).append(strAccountId);
                        long billingTime = System.currentTimeMillis();
                        if (messageQuene.getPayTimeControlMap().containsKey(controlKey.toString())) {
                            if (billingTime - messageQuene.getPayTimeControl(controlKey.toString()) < AppContants.WEB_REPEAT_REQUEST_INTERVAL) {
                                log.error(AppContants.RETURN_CODE_PREDETERMINED_TIME_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",Content=" + strContent + ")");
                                returnMap.put("returnCode", AppContants.RETURN_CODE_PREDETERMINED_TIME);
                                returnMap.put("returnDesc", AppContants.RETURN_CODE_PREDETERMINED_TIME_DESC);
                                strResult = JSON.toJSONString(returnMap);
                                writer.write(strResult);
                                return;
                            }

                            messageQuene.removePayTimeControl(sb.toString());
                        }

                        String key = proxyPayMessage.getCooperKey();
                        if (!proxyPayMessage.getAccountId().equals(strAccountId) || StringUtils.isBlank(key)) {
                            log.error(AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        sb.append(key).append(strProductId).append(strInstruction).append(strAccountId).append(strMobile).append(strMessageId).append(strValidateCode);
                        if (!ValidateMessageUtil.doValidateMessage(sb.toString(), strContent)) {
                            log.error(AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        VasServiceRelationEntity vsr = DataCache.getVasServiceRelationEntity(strProductId);

                        if (vsr == null) {
                            vsr = this.vasServiceRelationDao.getVasServiceRelation(strProductId);
                        }

                        if (vsr == null) {
                            log.error(AppContants.RETURN_CODE_VAS_SERVICE_NULL + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_VAS_SERVICE_NULL);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_VAS_SERVICE_NULL_DESC);
                            strResult =  JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        if (!strValidateCode.equals(proxyPayMessage.getValidateCode())) {
                            log.error(AppContants.RETURN_CODE_VALIDATE_CODE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_VALIDATE_CODE_ERROR);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_VALIDATE_CODE_ERROR_DESC);
                            strResult = JSON.toJSONString(returnMap);
                            writer.write(strResult);
                            return;
                        }

                        String strFeeType = vsr.getFeeType();
                        if (strFeeType.equals("1")) {
                            if (strInstruction.toLowerCase().equals(vsr.getOrderCode().toLowerCase())) {
                                boolean orderRelation = getUserOrder().isExistOrderRelation(strMobile, strProductId);
                                if (orderRelation) {
                                    log.error(AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                                    returnMap.put("returnCode", AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_CODE);
                                    returnMap.put("returnDesc", AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_DESC);
                                    strResult =  JSON.toJSONString(returnMap);
                                    writer.write(strResult);
                                    return;
                                }

                                strFeeType = "1";
                            } else {
                                if (!strInstruction.toLowerCase().equals(vsr.getCancelOrderCode().toLowerCase())) {
                                    returnMap.put("returnCode", AppContants.RETURN_CODE_INSTRUCTION_ERROR);
                                    returnMap.put("returnDesc", AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC);
                                    strResult =  JSON.toJSONString(returnMap);
                                    writer.write(strResult);
                                    return;
                                }

                                strFeeType = "2";
                            }

                            proxyPayMessage.setFeeType(strFeeType);
                            proxyPayMessage.setFee(vsr.getOrderFee());
                        } else {
                            if (!strInstruction.toLowerCase().equals(vsr.getOnDemandCode().toLowerCase())) {
                                returnMap.put("returnCode", AppContants.RETURN_CODE_INSTRUCTION_ERROR);
                                returnMap.put("returnDesc", AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC);
                                strResult =  JSON.toJSONString(returnMap);
                                writer.write(strResult);
                                return;
                            }

                            strFeeType = "4";
                            proxyPayMessage.setFee(vsr.getDbFee());
                            proxyPayMessage.setFeeType(strFeeType);
                        }

                        proxyPayMessage.setGlobalMessageid(strMessageId);
                        proxyPayMessage.setAccountId(strAccountId);
                        proxyPayMessage.setProductId(strProductId);
                        proxyPayMessage.setPhoneNumber(strMobile);
                        proxyPayMessage.setServiceId(vsr.getServiceId());
                        proxyPayMessage.setServiceName(vsr.getServiceName());
                        proxyPayMessage.setSpId(vsr.getSpId());
                        proxyPayMessage.setStatus("400");
                        proxyPayMessage.setPayRequestTime(requestTime);
                        this.proxyPayMessageDao.update(proxyPayMessage);
                        log.info("接收成功，提交corebiz处理(" + strMessageId + ")feeType(" + proxyPayMessage.getFeeType() + ")");
                        messageQuene.getRequestQuence().add(proxyPayMessage);
                        log.info(controlKey.toString());
                        messageQuene.addPayTimeControl(controlKey.toString(), billingTime);
                        messageQuene.addProxyPayMap(strMessageId, proxyPayMessage);
                        returnMap.put("returnCode", AppContants.RETURN_CODE_SUCCESS);
                        returnMap.put("returnDesc", AppContants.RETURN_CODE_SUCCESS_DESC);
                        strResult =  JSON.toJSONString(returnMap);
                        writer.write(strResult);
                        return;
                    }

                    log.error(AppContants.RETURN_CODE_VALIDATE_CODE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                    returnMap.put("returnCode", AppContants.RETURN_CODE_VALIDATE_REQUEST_NULL_OR_OUT);
                    returnMap.put("returnDesc", AppContants.RETURN_CODE_VALIDATE_REQUEST_NULL_OR_OUT_DESC);
                    strResult =  JSON.toJSONString(returnMap);
                    writer.write(strResult);
                    return;
                }

                log.error(AppContants.RETURN_CODE_MOBILE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_MOBILE_ERROR);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_MOBILE_ERROR_DESC);
                strResult =  JSON.toJSONString(returnMap);
                writer.write(strResult);
            } else {
                log.error(AppContants.RETURN_CODE_DATA_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",instruction=" + strInstruction + ",messageId=" + strMessageId + ",validateCode=" + strValidateCode + ",Content=" + strContent + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_DATA_NULL);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_DATA_NULL_DESC);
                strResult =  JSON.toJSONString(returnMap);
                writer.write(strResult);
            }
        } catch (Exception var27) {
            log.error("send message to corebiz is error");
            returnMap.put("returnCode", AppContants.RETURN_CODE_UNEXPECT_ERROR);
            returnMap.put("returnDesc", AppContants.RETURN_CODE_UNEXPECT_ERROR_DESC);
            strResult =  JSON.toJSONString(returnMap);
            writer.write(strResult);
            return;
        } finally {
            writer.close();
            returnMap = null;
        }

    }
}
