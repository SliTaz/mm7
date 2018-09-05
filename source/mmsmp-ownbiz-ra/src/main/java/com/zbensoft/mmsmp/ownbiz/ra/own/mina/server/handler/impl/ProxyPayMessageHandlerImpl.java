

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.CooperKeyDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.UserOrderDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.UserOrderEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.UserOrderPayEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.ValidateMessageUtil;

public class ProxyPayMessageHandlerImpl {
    private static final Logger log = Logger.getLogger(ProxyPayMessageHandlerImpl.class);
    private VasServiceRelationDao vasServiceRelationDao;
    private CooperKeyDao cooperKeyDao;
    private ProxyPayMessageDao proxyPayMessageDao;
    private UserOrderDao userOrderDao;
    private MessageQuene messageQuene = null;

    public ProxyPayMessageHandlerImpl() {
    }

    public void doHandler(ProxyPayMessage proxyPayMessage) {
        try {
            if (this.messageQuene == null) {
                this.messageQuene = MessageQuene.getInstance();
            }

            if (proxyPayMessage == null || StringUtils.isBlank(proxyPayMessage.getGlobalMessageid())) {
                log.error("proxyPayMessage is error ");
                return;
            }

            log.info("现在处理corebiz待支付响应：messageId=" + proxyPayMessage.getGlobalMessageid() + ",status=" + proxyPayMessage.getStatus());
            String messageId = proxyPayMessage.getGlobalMessageid();
            ProxyPayMessage proxyPayOld = this.messageQuene.removeProxyPayMap(messageId);
            if (proxyPayOld == null) {
                proxyPayOld = this.proxyPayMessageDao.getProxyPayMessageById(messageId);
            }

            if (proxyPayMessage == null) {
                ;
            }

            this.proxyPayMessageDao.update(proxyPayMessage);
            if (proxyPayMessage.getStatus().equals("0")) {
                VasServiceRelationEntity vsr = DataCache.getVasServiceRelationEntity(proxyPayMessage.getProductId());
                if (vsr == null) {
                    vsr = this.vasServiceRelationDao.getVasServiceRelation(proxyPayMessage.getProductId());
                }

                if (vsr == null) {
                    log.error("产品相关信息不存在 ");
                    return;
                }

                UserOrderPayEntity uope = new UserOrderPayEntity();
                uope.setCellPhonenum(proxyPayMessage.getPhoneNumber());
                uope.setGlobalMessageid(messageId);
                uope.setServiceUniqueid(vsr.getVasserviceUniqueId());
                uope.setSpId(vsr.getSpId());
                uope.setFee(vsr.getOrderFee());
                if (proxyPayMessage.getFeeType().equals("1")) {
                    this.getProxyPayMessageDao().insertUserOrderPay(uope);
                } else if (proxyPayMessage.getFeeType().equals("2")) {
                    this.getProxyPayMessageDao().deleteUserOrderPay(uope);
                }

                String smsTextTemplate;
                VaspEnitiy vasp;
                UserOrderEntity userOrder;
                if ("4".equals(proxyPayMessage.getFeeType())) {
                    proxyPayMessage.setFeeType("9");
                    smsTextTemplate = (String)DataCache.get("OWN_CHARGE_ONDEMAND_NOTIRY");
                    smsTextTemplate = smsTextTemplate.replace("{0}", vsr.getProductName());
                    smsTextTemplate = smsTextTemplate.replace("{1}", String.valueOf(vsr.getDbFee() / 100.0D));
                    vasp = DataCache.getVaspEnitiyBySpId(vsr.getSpId());
                    smsTextTemplate = smsTextTemplate.replace("{2}", vasp.getVaspName());
                    smsTextTemplate = smsTextTemplate.replace("{3}", vsr.getClientLinkManTel() != null ? vsr.getClientLinkManTel() : "");
                    proxyPayMessage.setSmsText(smsTextTemplate);
                    proxyPayMessage.setFeeType("9");
                    if ("0".equals(vsr.getIsNotifySms())) {
                        log.info("send sms msg =" + proxyPayMessage.getSmsText());
                        this.messageQuene.getRequestQuence().add(proxyPayMessage);
                    }

                    userOrder = new UserOrderEntity();
                    userOrder.setCellPhoneNo(proxyPayMessage.getPhoneNumber());
                    userOrder.setChargeParty(proxyPayMessage.getPhoneNumber());
                    userOrder.setFeeType("2");
                    userOrder.setFee("" + vsr.getDbFee());
                    userOrder.setNotifySpFlag("2");
                    userOrder.setOrderMethod("6");
                    userOrder.setServiceUniqueId("" + vsr.getVasserviceUniqueId());
                    userOrder.setSpOrderId("");
                    this.userOrderDao.insertUserOrderHis(userOrder);
                } else if ("1".equals(proxyPayMessage.getFeeType())) {
                    smsTextTemplate = (String)DataCache.get("OWN_CHARGE_ORDER_NOTIRY");
                    smsTextTemplate = smsTextTemplate.replace("{0}", vsr.getProductName());
                    smsTextTemplate = smsTextTemplate.replace("{1}", String.valueOf(vsr.getOrderFee() / 100.0D));
                    vasp = DataCache.getVaspEnitiyBySpId(vsr.getSpId());
                    smsTextTemplate = smsTextTemplate.replace("{2}", vasp.getVaspName());
                    smsTextTemplate = smsTextTemplate.replace("{3}", vsr.getClientLinkManTel() != null ? vsr.getClientLinkManTel() : "");
                    proxyPayMessage.setSmsText(smsTextTemplate);
                    proxyPayMessage.setFeeType("9");
                    if ("0".equals(vsr.getIsNotifySms())) {
                        log.info("send sms msg =" + proxyPayMessage.getSmsText());
                        this.messageQuene.getRequestQuence().add(proxyPayMessage);
                    }

                    userOrder = new UserOrderEntity();
                    userOrder.setCellPhoneNo(proxyPayMessage.getPhoneNumber());
                    userOrder.setChargeParty(proxyPayMessage.getPhoneNumber());
                    userOrder.setFeeType("1");
                    userOrder.setFee("" + vsr.getOrderFee());
                    userOrder.setNotifySpFlag("2");
                    userOrder.setOrderMethod("6");
                    userOrder.setServiceUniqueId("" + vsr.getVasserviceUniqueId());
                    userOrder.setSpOrderId("");
                    long hisID = this.userOrderDao.insertUserOrderHis(userOrder);
                    this.userOrderDao.insertUserOrder(userOrder, hisID);
                } else if ("2".equals(proxyPayMessage.getFeeType())) {
                    smsTextTemplate = (String)DataCache.get("OWN_CHARGE_CANCEL_NOTIRY");
                    smsTextTemplate = smsTextTemplate.replace("{0}", vsr.getProductName());
                    smsTextTemplate = smsTextTemplate.replace("{1}", String.valueOf(vsr.getOrderFee() / 100.0D));
                    vasp = DataCache.getVaspEnitiyBySpId(vsr.getSpId());
                    smsTextTemplate = smsTextTemplate.replace("{2}", vasp.getVaspName());
                    smsTextTemplate = smsTextTemplate.replace("{3}", vsr.getClientLinkManTel() != null ? vsr.getClientLinkManTel() : "");
                    proxyPayMessage.setSmsText(smsTextTemplate);
                    proxyPayMessage.setFeeType("9");
                    if ("0".equals(vsr.getIsNotifySms())) {
                        log.info("send sms msg =" + proxyPayMessage.getSmsText());
                        this.messageQuene.getRequestQuence().add(proxyPayMessage);
                    }

                    this.userOrderDao.deleteUserOrder(proxyPayMessage.getPhoneNumber(), vsr.getVasserviceUniqueId());
                } else {
                    log.info("===========recv FeeType was " + proxyPayMessage.getFeeType() + ",error feeType cannot send notify msg to your.");
                }
            }

            String key = proxyPayOld.getCooperKey();
            String url = proxyPayOld.getNotifyURL();
            if (StringUtils.isBlank(key) || StringUtils.isBlank(url)) {
                log.error("第三方(" + proxyPayOld.getAccountId() + ")密钥或者通知地址为空(key=" + key + ",url=" + url + ")");
                return;
            }

            log.info("处理corebiz返回鉴权信息成功");
            StringBuffer sb = new StringBuffer();
            sb.append(key).append(proxyPayMessage.getStatus()).append(proxyPayMessage.getGlobalMessageid());
            StringBuffer params = new StringBuffer();
            params.append("messageId=").append(proxyPayMessage.getGlobalMessageid()).append("&payStatus=").append(proxyPayMessage.getStatus()).append("&Content=").append(ValidateMessageUtil.getMD5String(sb.toString())).append("&realMobile=").append(proxyPayMessage.getPhoneNumber());
            byte[] bypes = params.toString().getBytes();
            log.info("通知第三方url为：" + url + params.toString());
            int code = HttpUtil.notifySP(url, bypes);
            log.info("第三方接受鉴权结果返回状态:  " + code);
        } catch (Exception var11) {
            log.error("连接第三方异常,异常信息" + var11.getMessage(), var11);
        }

    }

    public VasServiceRelationDao getVasServiceRelationDao() {
        return this.vasServiceRelationDao;
    }

    public void setVasServiceRelationDao(VasServiceRelationDao vasServiceRelationDao) {
        this.vasServiceRelationDao = vasServiceRelationDao;
    }

    public CooperKeyDao getCooperKeyDao() {
        return this.cooperKeyDao;
    }

    public void setCooperKeyDao(CooperKeyDao cooperKeyDao) {
        this.cooperKeyDao = cooperKeyDao;
    }

    public ProxyPayMessageDao getProxyPayMessageDao() {
        return this.proxyPayMessageDao;
    }

    public void setProxyPayMessageDao(ProxyPayMessageDao proxyPayMessageDao) {
        this.proxyPayMessageDao = proxyPayMessageDao;
    }

    public MessageQuene getMessageQuene() {
        return this.messageQuene;
    }

    public void setMessageQuene(MessageQuene messageQuene) {
        this.messageQuene = messageQuene;
    }

    public UserOrderDao getUserOrderDao() {
        return this.userOrderDao;
    }

    public void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }
}
