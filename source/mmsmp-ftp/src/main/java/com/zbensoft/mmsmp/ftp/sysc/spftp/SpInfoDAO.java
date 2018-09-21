package com.zbensoft.mmsmp.ftp.sysc.spftp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.zbensoft.mmsmp.ftp.sysc.spftp.req.SpAccessReq;
import com.zbensoft.mmsmp.ftp.sysc.spftp.req.SpInfoReq;
import com.zbensoft.mmsmp.ftp.util.DataUtil;
import com.zbensoft.mmsmp.ftp.util.HttpHelper;
import org.apache.log4j.Logger;


public class SpInfoDAO {
    private static final Logger logger = Logger.getLogger(SpInfoDAO.class);

    public SpInfoDAO() {
    }

    public void saveSpInfo(List<SpInfo> ors) {
        Iterator iterator = ors.iterator();

        while (iterator.hasNext()) {
            SpInfo spInfo = (SpInfo) iterator.next();
            this.saveSpInfo(spInfo);
        }

    }

    public void saveSpInfo(SpInfo spInfo) {
        try {
            int count = HttpHelper.getSpCount(spInfo.spid);
            int updateType = spInfo.updateType;
            if (updateType == 1 || (updateType == 2 && count > 0)) {
                SpInfoReq spInfoReq = buildSp(spInfo);
                HttpHelper.updateSp(spInfoReq, updateType);
            } else if (updateType == 3 && count > 0) {
                this.deleteInfos(spInfo);
            } else if (updateType != 1 && updateType != 2 && updateType != 3) {
                logger.info("updateType is error!");
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }


    private void deleteInfos(SpInfo spInfo) {
        HttpHelper.deleteSpInfo(spInfo.spid);
    }

    private SpInfoReq buildSp(SpInfo spInfo) {

        SpInfoReq spInfoReq = new SpInfoReq();
        spInfoReq.setCompanyName(spInfo.spName);
        spInfoReq.setBusinessTel(spInfo.spSrvPhone);
        spInfoReq.setUpdateTime(new Date());
        spInfoReq.setProvince(spInfo.spAreaID);
        spInfoReq.setStatus(DataUtil.getSpStatus(spInfo.spStatus));
        spInfoReq.setSpInfoId(spInfo.spid);

        SpAccessReq spAccessReq = new SpAccessReq();
        spAccessReq.setSpInfoId(spInfoReq.getSpInfoId());
        spAccessReq.setIsTrust(spInfo.isTrust);
        spAccessReq.setSpCity(spInfo.spCity);
        spAccessReq.setSpOrderNotifyUrl(spInfo.spOrderUrl);
        spAccessReq.setSynOrderFunc(spInfo.synOrderFunc);
        spAccessReq.setOrderKey(spInfo.orderKey);
        spAccessReq.setEffTime(spInfo.effDate);
        spAccessReq.setExpTime(spInfo.expDate);
        spInfoReq.setSpAccess(spAccessReq);

        return spInfoReq;
    }

    public List<String> getSpId() {
        return HttpHelper.getALLSpIds();
    }
}

