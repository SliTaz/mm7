

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.impl.DataCacheJob;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.DESUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpUtil;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.MmsUtil;
import org.apache.log4j.Logger;

import java.net.URLEncoder;

public class MoSmMessageHandlerImpl {
    private static final Logger log = Logger.getLogger(MoSmMessageHandlerImpl.class);
    private VasServiceRelationDao vasServiceRelationDao;
    private MmsUtil mmsUtil;

    public MoSmMessageHandlerImpl() {
    }

    public void doHandler(MO_SMMessage message) {
        if (message == null) {
            log.error("消息为空！");
        } else {
            log.info("ownbiz开发处理" + this.getMessage(message));
            String vasId = message.getVasId();
            String getMobileVasid = AppContants.GET_MOBILE_VASID;
            if (vasId != null && vasId.startsWith(getMobileVasid)) {
                this.doGetMobile(message);
            } else {
                this.doDianBo(message);
            }

            log.info("处理" + this.getMessage(message) + "结束!");
        }
    }

    private void doGetMobile(MO_SMMessage message) {
        try {
            String phoneNumber = message.getSendAddress();
            String vasid = message.getVasId();
            String unikey = message.getSmsText();
            if (unikey == null) {
                log.info("短信内容为空！");
            } else {
                int cooperCode = Integer.parseInt(vasid.substring(11));
                if (cooperCode < 0) {
                    log.info("无效接入号");
                } else if (phoneNumber != null && phoneNumber.length() >= 11) {
                    CooperKeyEntity cke = DataCache.getCooperKeyMobile(cooperCode);
                    if (cke == null) {
                        log.info("没有相关合作商信息");
                    } else {
                        String telNotifyUrl = cke.getTelNotifyUrl();
                        if (telNotifyUrl != null && telNotifyUrl.length() >= 0) {
                            phoneNumber = phoneNumber + "," + System.currentTimeMillis();
                            phoneNumber = DESUtil.encrypt(phoneNumber, AppContants.DES_ENCRYPTION_KEY);
                            phoneNumber = URLEncoder.encode(phoneNumber, "utf-8");
                            StringBuffer params = new StringBuffer();
                            params.append("mobile=").append(phoneNumber).append("&unikey=").append(unikey);
                            byte[] bypes = params.toString().getBytes();
                            log.info(telNotifyUrl);
                            int result = HttpUtil.notifySP(telNotifyUrl, bypes);
                            log.info("通知sp" + (result == 200 ? "成功" : "失败"));
                        } else {
                            log.info("无效电话通知网址");
                        }
                    }
                } else {
                    log.info("手机号码错误");
                }
            }
        } catch (Exception var11) {
            log.error(var11.getMessage(), var11);
        }
    }

    private void doDianBo(MO_SMMessage message) {
        try {
            String vasId = message.getVasId();
            String vaspId = message.getVaspId();
            String serviceCode = message.getServiceCode();
            String sendAddress = message.getSendAddress();
            if (vasId != null && vasId.length() > 0 && vaspId != null && vaspId.length() > 0 && serviceCode != null && serviceCode.length() > 0 && sendAddress != null && sendAddress.length() > 0) {
                boolean is_own = false;
                int i = 0;

                while(true) {
                    if (i < DataCacheJob.OWN_SP.size()) {
                        if (!message.getVaspId().equals(((VaspEnitiy)DataCacheJob.OWN_SP.get(i)).getVaspId())) {
                            ++i;
                            continue;
                        }

                        is_own = true;
                    }

                    if (!is_own) {
                        log.info("不是联通自有产品(" + this.getMessage(message) + ")！");
                        return;
                    }

                    String[] vasRelations = message.getServiceCode().split("#");
                    if (vasRelations != null && vasRelations.length >= 6) {
                        String spProductId = vasRelations[2];
                        VasServiceRelationEntity vsre = DataCache.getVasServiceRelationEntity(spProductId);
                        if (vsre != null && vsre.getCpId() <= 0L) {
                            log.info(message.getGlobalMessageid() + "点播的产品(" + spProductId + ")没有关联cp,无法下发彩信！");
                            return;
                        }

                        vsre = DataCache.getDistributeProductRelation(spProductId);
                        String notifyUrlStr = "";
                        boolean notifyFlag = false;
                        if (vsre == null) {
                            log.info(message.getGlobalMessageid() + "点播的产品(" + spProductId + ")或者关联的cp不存在！");
                            return;
                        }

                        vsre.getFeeType().equals("");
                        if (vsre.getCpType() == 3) {
                            notifyUrlStr = vsre.getAccessUrl();
                            if (notifyUrlStr == null || notifyUrlStr.length() <= 0) {
                                log.info("消息(" + message.getGlobalMessageid() + ")" + sendAddress + "点播产品(" + spProductId + ")内容不在彩信平台，但是通知第三方url为空！");
                                return;
                            }

                            notifyFlag = true;
                        } else {
                            notifyFlag = false;
                        }

                        if (notifyFlag) {
                            StringBuffer params = new StringBuffer();
                            params.append("mobile=").append(message.getSendAddress()).append("&productId=").append(spProductId).append("&smsContent=").append(message.getSmsText()).append("&encodeSmsContent=").append(URLEncoder.encode(message.getSmsText(), "utf-8")).append("&type=3").append("&sequenceNumber=").append("" + message.getSequence_Number_1() + message.getSequence_Number_2() + message.getSequence_Number_3()).append("&SPNumber=").append(message.getVasId()).append("&linkId=").append(message.getLinkId());
                            byte[] bypes = params.toString().getBytes();
                            log.info("通知第三方url为：" + notifyUrlStr + "?" + params.toString());

                            try {
                                int strRes = HttpUtil.notifySP(notifyUrlStr, bypes);
                                log.info(strRes);
                            } catch (Exception var15) {
                                log.error(var15.getMessage(), var15);
                            }

                            return;
                        }

                        MmsUserEntity mue = this.vasServiceRelationDao.queryMmsUserEntity(message);
                        if (mue != null) {
                            mue.setContentFile(mue.getContentFile());
                            mue.setProductId(spProductId);
                            int sendFlag = this.mmsUtil.sendMms(mue, message.getLinkId());
                            log.info("点播彩信(" + mue.getContentName() + ")下发" + (sendFlag == 0 ? "成功" : "失败"));
                        } else {
                            log.error("没有相关点播彩信需要下发");
                        }

                        return;
                    }

                    log.error("点播消息中业务格式错误(serviceCode=" + serviceCode + ")");
                    return;
                }
            } else {
                log.error("缺少必要字段(" + this.getMessage(message) + ")");
            }
        } catch (Exception var16) {
            log.error(var16.getMessage(), var16);
        }
    }

    public VasServiceRelationDao getVasServiceRelationDao() {
        return this.vasServiceRelationDao;
    }

    public void setVasServiceRelationDao(VasServiceRelationDao vasServiceRelationDao) {
        this.vasServiceRelationDao = vasServiceRelationDao;
    }

    public MmsUtil getMmsUtil() {
        return this.mmsUtil;
    }

    public void setMmsUtil(MmsUtil mmsUtil) {
        this.mmsUtil = mmsUtil;
    }

    private String getMessage(MO_SMMessage message) {
        if (message == null) {
            return "";
        } else {
            String strMessage = " 上行消息：messsageId=" + message.getGlobalMessageid() + ",vasId=" + message.getVasId() + ",vaspId=" + message.getVaspId() + ",serviceCode=" + message.getServiceCode() + ",sendAddress=" + message.getSendAddress();
            return strMessage;
        }
    }
}
