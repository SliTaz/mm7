 package com.zbensoft.mmsmp.corebiz.dao;
 
 import com.zbensoft.mmsmp.common.ra.ckp.server.SyncNotifySPDao;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDao;
import com.zbensoft.mmsmp.common.ra.utils.MM7DAO;
import com.zbensoft.mmsmp.vas.sjb.data.BusinessManageDAO;
import com.zbensoft.mmsmp.vas.sjb.data.ContentInfoDAO;
import com.zbensoft.mmsmp.vas.sjb.data.DemandDao;
import com.zbensoft.mmsmp.vas.sjb.data.LoadUserOrderDao;
import com.zbensoft.mmsmp.vas.sjb.data.LogDao;
import com.zbensoft.mmsmp.vas.sjb.data.OrderRelationDAO;
import com.zbensoft.mmsmp.vas.sjb.data.UserServiceHisDao;
 
 public class DaoUtil
 {
   LogDao logDao;
   DemandDao demandDao;
   BusinessManageDAO businessManageDAO;
   LoadUserOrderDao loadUserOrderDao;
   OrderRelationDAO orderRelationDAO;
   ContentInfoDAO contentInfoDAO;
   UserServiceHisDao userServiceHisDao;
   SmsSenderDao smsSenderDao;
   MM7DAO mm7dao;
//   UserMTQuotaDao userMTQuotaDao;
//   UserBlackWhiteListDao userBlackWhiteListDao;
   SyncNotifySPDao syncNotifySPDao;
//   UserRelationDAO userRelationDAO;
//   ProductInfoDAO productInfoDAO;
//   ServiceInfoDAO serviceInfoDAO;
//   ServiceCapacityDAO serviceCapacityDAO;
//   SpInfoDAO spInfoDAO;
   SmsDAO smsDAO;
   
   public SmsDAO getSmsDAO()
   {
     return this.smsDAO;
   }
   
   public void setSmsDAO(SmsDAO smsDAO) { this.smsDAO = smsDAO; }
   
   public LogDao getLogDao() {
     return this.logDao;
   }
   
   public void setLogDao(LogDao logDao) { this.logDao = logDao; }
   
   public DemandDao getDemandDao() {
     return this.demandDao;
   }
   
   public void setDemandDao(DemandDao demandDao) { this.demandDao = demandDao; }
   
   public BusinessManageDAO getBusinessManageDAO() {
     return this.businessManageDAO;
   }
   
   public void setBusinessManageDAO(BusinessManageDAO businessManageDAO) { this.businessManageDAO = businessManageDAO; }
   
   public LoadUserOrderDao getLoadUserOrderDao() {
     return this.loadUserOrderDao;
   }
   
   public void setLoadUserOrderDao(LoadUserOrderDao loadUserOrderDao) { this.loadUserOrderDao = loadUserOrderDao; }
   
   public OrderRelationDAO getOrderRelationDAO() {
     return this.orderRelationDAO;
   }
   
   public void setOrderRelationDAO(OrderRelationDAO orderRelationDAO) { this.orderRelationDAO = orderRelationDAO; }
   
   public ContentInfoDAO getContentInfoDAO() {
     return this.contentInfoDAO;
   }
   
   public void setContentInfoDAO(ContentInfoDAO contentInfoDAO) { this.contentInfoDAO = contentInfoDAO; }
   
   public UserServiceHisDao getUserServiceHisDao() {
     return this.userServiceHisDao;
   }
   
   public void setUserServiceHisDao(UserServiceHisDao userServiceHisDao) { this.userServiceHisDao = userServiceHisDao; }
   
   public SmsSenderDao getSmsSenderDao() {
     return this.smsSenderDao;
   }
   
   public void setSmsSenderDao(SmsSenderDao smsSenderDao) { this.smsSenderDao = smsSenderDao; }
   
   public MM7DAO getMm7dao() {
     return this.mm7dao;
   }
   
   public void setMm7dao(MM7DAO mm7dao) { this.mm7dao = mm7dao; }
   
   
   
   public SyncNotifySPDao getSyncNotifySPDao() {
     return this.syncNotifySPDao;
   }
 }   
   





