

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.CooperKeyDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserBillingAppServlet extends HttpServlet {
    private static final long serialVersionUID = 1961985726725819129L;
    private static final Log log = LogFactory.getLog(UserBillingAppServlet.class);
    private CooperKeyDao cooperKeyDao = null;
    private VasServiceRelationDao vasServiceRelationDao = null;
    private ProxyPayMessageDao proxyPayMessageDao = null;
    private MessageQuene messageQuene = null;

    public UserBillingAppServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        if (this.messageQuene == null) {
            this.messageQuene = MessageQuene.getInstance();
        }

        this.cooperKeyDao = (CooperKeyDao)this.messageQuene.getDao("cooperKeyDao");
        this.vasServiceRelationDao = (VasServiceRelationDao)this.messageQuene.getDao("vasServiceRelationDao");
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
            String strMobile = request.getParameter("mobile");
            String strProductId = request.getParameter("productId");
            String strAccountId = request.getParameter("accountId");
            String strInstruction = request.getParameter("instruction");
            String strContent = request.getParameter("Content");
            log.info("接收手机端支付请求：accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",Content=" + strContent + ",instruction=" + strInstruction);
            if (!StringUtils.isBlank(strMobile) && !StringUtils.isBlank(strProductId) && !StringUtils.isBlank(strAccountId) && !StringUtils.isBlank(strInstruction)) {
                StringBuffer sb = new StringBuffer();
                sb = sb.append(strMobile).append(strProductId).append(strAccountId);
                long billingTime = System.currentTimeMillis();
                if (this.messageQuene.getPayTimeControlMap().containsKey(sb.toString())) {
                    if (billingTime - this.messageQuene.getPayTimeControl(sb.toString()) < AppContants.APP_REPEAT_REQUEST_INTERVAL) {
                        log.error(AppContants.RETURN_CODE_REPEAT_REQUEST_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",Content=" + strContent + ",instruction" + strInstruction + ")");
                        returnMap.put("returnCode", AppContants.RETURN_CODE_REPEAT_REQUEST);
                        returnMap.put("returnDesc", AppContants.RETURN_CODE_REPEAT_REQUEST_DESC);
                        strResult = JSONObject.fromObject(returnMap).toString();
                        writer.write(strResult);
                        return;
                    }

                    this.messageQuene.removePayTimeControl(sb.toString());
                }

                String strPhoneNumber = DESUtil.descrypt(strMobile, AppContants.DES_ENCRYPTION_KEY);
                String[] arr = (String[])null;
                long nowTime = System.currentTimeMillis();
                if (strPhoneNumber != null) {
                    arr = strPhoneNumber.split(",");
                }

                if (strPhoneNumber != null && arr.length == 2 && arr[0].length() == 11) {
                    if (nowTime - Long.parseLong(arr[1]) > AppContants.PHONE_NUMBER_KEY_DAY) {
                        log.error(AppContants.RETURN_CODE_MOBILE_OUT_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                        returnMap.put("returnCode", AppContants.RETURN_CODE_MOBILE_OUT);
                        returnMap.put("returnDesc", AppContants.RETURN_CODE_MOBILE_OUT_DESC);
                        strResult = JSONObject.fromObject(returnMap).toString();
                        writer.write(strResult);
                    } else {
                        log.info("用户手机号码为：" + arr[0]);
                        CooperKeyEntity cke = DataCache.getCooperKey(strAccountId);
                        if (cke == null || StringUtils.isBlank(cke.getCooperKey())) {
                            cke = this.cooperKeyDao.getCooperKeyEntityByAccountId(strAccountId);
                        }

                        if (cke == null || cke.getCooperKey() == null) {
                            log.error(AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                            returnMap.put("returnCode", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL);
                            returnMap.put("returnDesc", AppContants.RETURN_CODE_ACCOUNTID_OR_COOPER_KEY_NULL_DESC);
                            strResult = JSONObject.fromObject(returnMap).toString();
                            writer.write(strResult);
                        } else {
                            StringBuffer sbKey = new StringBuffer();
                            sbKey.append(cke.getCooperKey()).append(strAccountId).append(strMobile).append(strProductId).append(strInstruction);
                            if (!ValidateMessageUtil.doValidateMessage(sbKey.toString(), strContent)) {
                                log.error(AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                                returnMap.put("returnCode", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR);
                                returnMap.put("returnDesc", AppContants.RETURN_CODE_KEY_VALIDATE_ERROR_DESC);
                                strResult = JSONObject.fromObject(returnMap).toString();
                                writer.write(strResult);
                            } else {
                                VasServiceRelationEntity vsr = DataCache.getVasServiceRelationEntity(strProductId);
                                if (vsr == null) {
                                    vsr = this.vasServiceRelationDao.getVasServiceRelation(strProductId);
                                }

                                if (vsr == null) {
                                    log.error(AppContants.RETURN_CODE_VAS_SERVICE_NULL_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                                    returnMap.put("returnCode", AppContants.RETURN_CODE_VAS_SERVICE_NULL);
                                    returnMap.put("returnDesc", AppContants.RETURN_CODE_VAS_SERVICE_NULL_DESC);
                                    strResult = JSONObject.fromObject(returnMap).toString();
                                    writer.write(strResult);
                                } else {
                                    this.messageQuene.addPayTimeControl(sb.toString(), billingTime);
                                    String globalMessageId = UUIDUtils.getPPSUuid();
                                    ProxyPayMessage proxyPayMessage = new ProxyPayMessage();
                                    proxyPayMessage.setGlobalMessageid(globalMessageId);
                                    proxyPayMessage.setAccountId(strAccountId);
                                    String strFeeType = vsr.getFeeType();
                                    if (strFeeType.equals("1")) {
                                        if (strInstruction.toLowerCase().equals(vsr.getOrderCode().toLowerCase())) {
                                            String[] desPhone = DESUtil.descrypt(strMobile, AppContants.DES_ENCRYPTION_KEY).split(",");
                                            String cellphonenumber = "";
                                            if (desPhone.length > 0) {
                                                cellphonenumber = desPhone[0];
                                            }

                                            boolean orderRelation = UserBillingWebServlet.getUserOrder().isExistOrderRelation(cellphonenumber, strProductId);
                                            if (orderRelation) {
                                                log.error(AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_DESC + "(accountId=" + strAccountId + ",mobile=" + strMobile + ",productId=" + strProductId + ",Content=" + strContent + ",instruction=" + strInstruction + ")");
                                                returnMap.put("returnCode", AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_CODE);
                                                returnMap.put("returnDesc", AppContants.RETRUE_ORDERING_RELATIONS_ISEXISTS_DESC);
                                                strResult = JSONObject.fromObject(returnMap).toString();
                                                writer.write(strResult);
                                                return;
                                            }

                                            strFeeType = "1";
                                        } else {
                                            if (!strInstruction.toLowerCase().equals(vsr.getCancelOrderCode().toLowerCase())) {
                                                log.error(AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                                                returnMap.put("returnCode", AppContants.RETURN_CODE_INSTRUCTION_ERROR);
                                                returnMap.put("returnDesc", AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC);
                                                strResult = JSONObject.fromObject(returnMap).toString();
                                                writer.write(strResult);
                                                return;
                                            }

                                            strFeeType = "2";
                                        }

                                        proxyPayMessage.setFeeType(strFeeType);
                                        proxyPayMessage.setFee(vsr.getOrderFee());
                                    } else {
                                        if (!strInstruction.toLowerCase().equals(vsr.getOnDemandCode().toLowerCase())) {
                                            log.error(AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + arr[0] + ",productId=" + strProductId + ",instruction" + strInstruction + ",Content=" + strContent + ")");
                                            returnMap.put("returnCode", AppContants.RETURN_CODE_INSTRUCTION_ERROR);
                                            returnMap.put("returnDesc", AppContants.RETURN_CODE_INSTRUCTION_ERROR_DESC);
                                            strResult = JSONObject.fromObject(returnMap).toString();
                                            writer.write(strResult);
                                            return;
                                        }

                                        strFeeType = "4";
                                        proxyPayMessage.setFee(vsr.getDbFee());
                                        proxyPayMessage.setFeeType(strFeeType);
                                    }

                                    proxyPayMessage.setCooperKey(cke.getCooperKey());
                                    proxyPayMessage.setNotifyURL(cke.getNotifyUrl());
                                    proxyPayMessage.setProductId(strProductId);
                                    proxyPayMessage.setPhoneNumber(arr[0]);
                                    proxyPayMessage.setServiceId(vsr.getServiceId());
                                    proxyPayMessage.setServiceName(vsr.getServiceName());
                                    proxyPayMessage.setProductName(vsr.getProductName());
                                    proxyPayMessage.setCpID(String.valueOf(vsr.getCpId()));
                                    proxyPayMessage.setSpId(vsr.getSpId());
                                    proxyPayMessage.setSourceType(2);
                                    proxyPayMessage.setStatus("401");
                                    String strDate = DateUtil.getFormatDate();
                                    proxyPayMessage.setCreateDate(strDate);
                                    proxyPayMessage.setUpdateDate(strDate);
                                    proxyPayMessage.setPayRequestTime(System.currentTimeMillis());
                                    int validateCode = (int)(Math.random() * 9000.0D + 1000.0D);
                                    proxyPayMessage.setValidateCode(String.valueOf(validateCode));
                                    String strConfirmUrl = AppContants.CONFIRM_URL;
                                    strConfirmUrl = strConfirmUrl + "?messageId=" + globalMessageId;
                                    String strTips = "";
                                    strTips = AppContants.APP_PAY_TIPS;
                                    returnMap.put("returnCode", AppContants.RETURN_CODE_SUCCESS);
                                    returnMap.put("returnDesc", AppContants.RETURN_CODE_SUCCESS_DESC);
                                    returnMap.put("productId", strProductId);
                                    returnMap.put("productName", vsr.getProductName());
                                    returnMap.put("appName", cke.getAppName());
                                    returnMap.put("cpName", cke.getCooperName());
                                    returnMap.put("price", proxyPayMessage.getFee());
                                    returnMap.put("serviceTel", cke.getServiceTel());
                                    returnMap.put("tips", strTips);
                                    returnMap.put("confirmurl", strConfirmUrl);
                                    File file = ImgeUtil.createValidateFile(String.valueOf(validateCode), "/home/mmsmp/" + globalMessageId + ".jpeg");
                                    String strImgString = ImgeUtil.getImageStr(file);
                                    returnMap.put("validateCode", strImgString);
                                    strResult = JSONObject.fromObject(returnMap).toString();
                                    writer.write(strResult);
                                    writer.close();
                                    file.delete();
                                    this.proxyPayMessageDao.insert(proxyPayMessage);
                                    this.messageQuene.addProxyPayMap(globalMessageId, proxyPayMessage);
                                    log.info("接收成功，生成消息ID(" + globalMessageId + "),验证码(" + validateCode + "),返回等待用户二次确认");
                                }
                            }
                        }
                    }
                } else {
                    log.error(AppContants.RETURN_CODE_MOBILE_ERROR_DESC + "(accountId=" + strAccountId + ",mobile=" + strPhoneNumber + ",productId=" + strProductId + ",Content=" + strContent + ",instruction" + strInstruction + ")");
                    returnMap.put("returnCode", AppContants.RETURN_CODE_MOBILE_ERROR);
                    returnMap.put("returnDesc", AppContants.RETURN_CODE_MOBILE_ERROR_DESC);
                    strResult = JSONObject.fromObject(returnMap).toString();
                    writer.write(strResult);
                }
            } else {
                log.error(AppContants.RETURN_CODE_DATA_NULL_DESC + "(mobile=" + strMobile + ",productid=" + strProductId + ",accountId=" + strAccountId + ",instruction" + strInstruction + ")");
                returnMap.put("returnCode", AppContants.RETURN_CODE_DATA_NULL);
                returnMap.put("returnDesc", AppContants.RETURN_CODE_DATA_NULL_DESC);
                strResult = JSONObject.fromObject(returnMap).toString();
                writer.write(strResult);
            }
        } catch (Exception var33) {
            log.error(var33.getMessage(), var33);
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
