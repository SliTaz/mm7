package com.zbensoft.mmsmp.ownbiz.ra.own.job.impl;

import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.CooperKeyDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.SystemParamDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.SysParametersEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import com.zbensoft.mmsmp.ownbiz.ra.own.job.Job;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import org.apache.log4j.Logger;

import java.util.*;

public class DataCacheJob extends Job {
    public static final Logger logger = Logger.getLogger(DataCacheJob.class);
    private DataCache dataCache;
    private CooperKeyDao cooperKeyDao;
    private VasServiceRelationDao vasServiceRelationDao;
    private SystemParamDao systemParamDao;
    private static String COOPER_KEY = "cooper_key";
    private static String PRODUCT_SERVICE_KEY = "product_service_key";
    private static String PRODUCT_DISTRIBUTE_KEY = "product_distribute_key";
    private static String COOPER_KEY_MOBILE = "cooper_key_mobile";
    private static String VASP_KEY = "vasp_key";
    public static List<VaspEnitiy> OWN_SP = new ArrayList();

    public DataCacheJob() {
    }

    public void doHandler() {
        List vaspList;
        HashMap vaspMap;
        try {
            logger.info("执行定时任务,刷新数据缓存开始**********》》》");
            vaspList = this.getCooperKeyDao().queryAllCooperKeyEntity();
            vaspMap = new HashMap();
            Map<Integer, CooperKeyEntity> mapMK = new HashMap();
            Iterator var5 = vaspList.iterator();

            while(var5.hasNext()) {
                CooperKeyEntity entity = (CooperKeyEntity)var5.next();
                vaspMap.put(entity.getCooperId(), entity);
                if (entity.getCooperCode() > 0) {
                    mapMK.put(entity.getCooperCode(), entity);
                }
            }

            this.dataCache.put(COOPER_KEY, vaspMap);
            this.dataCache.put(COOPER_KEY_MOBILE, mapMK);
        } catch (Exception var14) {
            logger.error(var14.getMessage(), var14);
        }

        VasServiceRelationEntity entity;
        Iterator var19;
        try {
            vaspList = this.getVasServiceRelationDao().getAllVasServiceRelation();
            vaspMap = new HashMap();
            var19 = vaspList.iterator();

            while(var19.hasNext()) {
                entity = (VasServiceRelationEntity)var19.next();
                vaspMap.put(entity.getSpProductId(), entity);
            }

            this.dataCache.put(PRODUCT_SERVICE_KEY, vaspMap);
        } catch (Exception var13) {
            logger.error(var13.getMessage(), var13);
        }

        try {
            OWN_SP = this.vasServiceRelationDao.getAllOwnVasps();
            vaspList = this.vasServiceRelationDao.getDistributeVasServiceRelation(OWN_SP);
            vaspMap = new HashMap();
            var19 = vaspList.iterator();

            while(var19.hasNext()) {
                entity = (VasServiceRelationEntity)var19.next();
                vaspMap.put(entity.getSpProductId(), entity);
            }

            this.dataCache.put(PRODUCT_DISTRIBUTE_KEY, vaspMap);
        } catch (Exception var12) {
            logger.error(var12.getMessage(), var12);
        }

        try {
            vaspList = this.vasServiceRelationDao.querySysParameters();
            Iterator var18 = vaspList.iterator();

            while(var18.hasNext()) {
                SysParametersEntity entity2 = (SysParametersEntity)var18.next();
                if (entity2 != null) {
                    if (entity2.getKey().equals("APP_PAY_TIPS")) {
                        AppContants.APP_PAY_TIPS = entity2.getValue();
                    }

                    if (entity2.getKey().equals("TAKE_ACC_NUMBER")) {
                        AppContants.GET_MOBILE_VASID = entity2.getValue();
                    }

                    if (entity2.getKey().equals("MOBILE_KEEP_TIME")) {
                        AppContants.PHONE_NUMBER_KEY_DAY = (long)(Integer.parseInt(entity2.getValue()) * 24 * 60 * 60 * 1000);
                    }

                    entity2.getKey().equals("OWN_VASID");
                    entity2.getKey().equals("OWN_SENDADDRESS");
                }
            }
        } catch (Exception var11) {
            logger.error(var11.getMessage(), var11);
        }

        String ownValidCodeSms;
        try {
            ownValidCodeSms = this.getSystemParamDao().getSystemParamBykey("OWN_VALIDCODE_SMS");
            if (ownValidCodeSms != null) {
                this.dataCache.put("OWN_VALIDCODE_SMS", ownValidCodeSms);
            }
        } catch (Exception var9) {
            logger.error(var9.getMessage(), var9);
        }

        try {
            ownValidCodeSms = this.getSystemParamDao().getSystemParamBykey("OWN_CHARGE_ONDEMAND_NOTIRY");
            if (ownValidCodeSms != null) {
                this.dataCache.put("OWN_CHARGE_ONDEMAND_NOTIRY", ownValidCodeSms);
            }
        } catch (Exception var8) {
            logger.error(var8.getMessage(), var8);
        }

        try {
            ownValidCodeSms = this.getSystemParamDao().getSystemParamBykey("OWN_CHARGE_ORDER_NOTIRY");
            if (ownValidCodeSms != null) {
                this.dataCache.put("OWN_CHARGE_ORDER_NOTIRY", ownValidCodeSms);
            }
        } catch (Exception var7) {
            logger.error(var7.getMessage(), var7);
        }

        try {
            ownValidCodeSms = this.getSystemParamDao().getSystemParamBykey("OWN_CHARGE_CANCEL_NOTIRY");
            if (ownValidCodeSms != null) {
                this.dataCache.put("OWN_CHARGE_CANCEL_NOTIRY", ownValidCodeSms);
            }
        } catch (Exception var6) {
            logger.error(var6.getMessage(), var6);
        }

        try {
            vaspList = this.getVasServiceRelationDao().getAllVaspEnitiy();
            if (vaspList != null) {
                vaspMap = new HashMap();
                var19 = vaspList.iterator();

                while(var19.hasNext()) {
                    VaspEnitiy vasp = (VaspEnitiy)var19.next();
                    vaspMap.put(vasp.getVaspId(), vasp);
                }

                this.dataCache.put(VASP_KEY, vaspMap);
            }
        } catch (Exception var10) {
            logger.error(var10.getMessage(), var10);
        }

        logger.info("《《《**********执行定时任务,刷新数据缓存结束");
    }

    public DataCache getDataCache() {
        return this.dataCache;
    }

    public void setDataCache(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    public CooperKeyDao getCooperKeyDao() {
        return this.cooperKeyDao;
    }

    public void setCooperKeyDao(CooperKeyDao cooperKeyDao) {
        this.cooperKeyDao = cooperKeyDao;
    }

    public VasServiceRelationDao getVasServiceRelationDao() {
        return this.vasServiceRelationDao;
    }

    public void setVasServiceRelationDao(VasServiceRelationDao vasServiceRelationDao) {
        this.vasServiceRelationDao = vasServiceRelationDao;
    }

    public SystemParamDao getSystemParamDao() {
        return this.systemParamDao;
    }

    public void setSystemParamDao(SystemParamDao systemParamDao) {
        this.systemParamDao = systemParamDao;
    }
}
