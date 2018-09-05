package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import com.zbensoft.mmsmp.common.ra.common.unibusiness.OrderRelationRequest;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.Response;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusiness;
import com.zbensoft.mmsmp.common.ra.common.unibusiness.UniBusinessServiceClient;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.SmsUserEntity;
import org.apache.log4j.Logger;

public class BusinessUtil {
    static final Logger logger = Logger.getLogger(BusinessUtil.class);
    String businessUrl = null;

    public BusinessUtil() {
    }

    public int sendSms(SmsUserEntity sms) {
        boolean var2 = false;

        int flag;
        try {
            OrderRelationRequest req = new OrderRelationRequest();
            req.setProductID(sms.getProductId());
            req.setServiceId(sms.getServiceId());
            req.setServiceName(sms.getServiceName());
            req.setSpCode(sms.getSpid());
            req.setUserPhone(sms.getCellPhonenum());
            req.setChargeparty(sms.getCellPhonenum());
            req.setFee(sms.getFee());
            req.setChannel(sms.getChannel());
            req.setAaaURL("");
            req.setStatus(7);
            req.setPeroid(0);
            req.setUserType(1);
            UniBusinessServiceClient client = new UniBusinessServiceClient();
            UniBusiness buiz = client.getUniBusinessService(this.businessUrl);
            Response rsp = buiz.orderRelationManage(req);
            logger.info("call send sms success[phone:" + sms.getCellPhonenum() + ",productid:" + sms.getProductId() + ",returncode:" + rsp.getReturnCode() + ",statusdesc:" + rsp.getDesc() + "]");
            flag = Integer.parseInt(rsp.getReturnCode());
        } catch (Exception var7) {
            logger.error("call send sms excepton", var7);
            flag = -1;
        }

        return flag;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }
}
