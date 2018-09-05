

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl;

import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.impl.DataCacheJob;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpUtil;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderRelationUpdateNotifyRequestHandlerImpl {
    private static final Logger log = Logger.getLogger(OrderRelationUpdateNotifyRequestHandlerImpl.class);
    private VasServiceRelationDao vasServiceRelationDao;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrderRelationUpdateNotifyRequestHandlerImpl() {
    }

    public void doHandler(OrderRelationUpdateNotifyRequest message) {
        if (message != null) {
            String notifyUrlStr = "";

            try {
                String spProductId;
                String userId;
                boolean is_own;
                label267: {
                    spProductId = message.getProductId();
                    String spId = message.getSpId();
                    userId = message.getUserId();
                    if (spProductId != null && spProductId.length() > 0 && spId != null && spId.length() > 0 && userId != null && userId.length() > 0) {
                        is_own = false;
                        int i = 0;

                        while(true) {
                            if (i >= DataCacheJob.OWN_SP.size()) {
                                break label267;
                            }

                            if (message.getSpId().equals(((VaspEnitiy)DataCacheJob.OWN_SP.get(i)).getVaspId())) {
                                is_own = true;
                                break label267;
                            }

                            ++i;
                        }
                    }

                    log.info("订购消息错误,缺少必要字段(" + this.getRequestMessage(message) + ")！");
                    return;
                }

                if (!is_own) {
                    log.info("订购消息错误,不是联通自有产品(" + this.getRequestMessage(message) + ")！");
                    return;
                }

                log.info("开始处理代订购消息：" + this.getRequestMessage(message));
                VasServiceRelationEntity vsre = DataCache.getVasServiceRelationEntity(spProductId);
                if (vsre != null && vsre.getCpId() <= 0L) {
                    log.info(message.getGlobalMessageid() + "订购产品(" + spProductId + ")没有关联cp！");
                    return;
                }

                vsre = DataCache.getDistributeProductRelation(spProductId);
                if (vsre == null) {
                    log.info(message.getGlobalMessageid() + "订购的产品(" + spProductId + ")或者关联的cp不存在！");
                    return;
                }

                if (vsre.getCpType() != 3) {
                    log.info(userId + "订购产品(" + spProductId + ")内容在我们平台!");
                    return;
                }

                notifyUrlStr = vsre.getAccessUrl();
                if (notifyUrlStr == null || notifyUrlStr.length() <= 0) {
                    log.info("消息(" + message.getGlobalMessageid() + ")" + userId + "订购产品(" + spProductId + ")内容不在彩信平台，但是同时第三方url为空！");
                    return;
                }

                int returnStatus = 0;
                StringBuffer params = new StringBuffer();
                params.append("mobile=").append(message.getUserId()).append("&productId=").append(spProductId).append("&orderTime=").append(sdf.format(new Date())).append("&type=").append(message.getUpdateType()).append("&content=").append(message.getContent()).append("&linkId=").append(message.getLinkId()).append("&spId=").append(message.getSpId()).append("&messageId=").append(message.getGlobalMessageid());
                byte[] bypes = params.toString().getBytes();
                log.info("通知第三方url为：" + notifyUrlStr + "?" + params.toString());

                try {
                    returnStatus = HttpUtil.notifySP(notifyUrlStr, bypes);
                } catch (Exception var16) {
                    log.error(var16.getMessage(), var16);
                }

                if (message.getUpdateType() == 1) {
                    int status = 0;
                    if (returnStatus != 200) {
                        status = 2;
                        log.info("消息(" + message.getGlobalMessageid() + ")" + userId + "通知第三方失败！");
                    }

                    this.getVasServiceRelationDao().updateNotifySpStatus(vsre.getVasserviceUniqueId(), userId, status);
                }
            } catch (Exception var17) {
                log.error(var17.getMessage(), var17);
                return;
            } finally {
                log.info("处理" + this.getRequestMessage(message) + "结束!");
            }

        }
    }

    public VasServiceRelationDao getVasServiceRelationDao() {
        return this.vasServiceRelationDao;
    }

    public void setVasServiceRelationDao(VasServiceRelationDao vasServiceRelationDao) {
        this.vasServiceRelationDao = vasServiceRelationDao;
    }

    private String getRequestMessage(OrderRelationUpdateNotifyRequest message) {
        if (message == null) {
            return "";
        } else {
            String messageString = "订购关系请求 OrderRelationUpdateNotifyRequest:productId=" + message.getProductId() + ",spId=" + message.getSpId() + ",messageId=" + message.getGlobalMessageid() + ",userPhone=" + message.getUserId() + ",updateType= " + message.getUpdateType();
            return messageString;
        }
    }
}
