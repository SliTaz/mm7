 package com.zbensoft.mmsmp.vas.sjb.controller;
 
 import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
 import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtMode;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtModeId;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
 import com.zbensoft.mmsmp.common.ra.common.util.DateUtil;
 import com.zbensoft.mmsmp.vas.sjb.data.BusinessManageDAO;
 import com.zbensoft.mmsmp.vas.sjb.data.ContentInfoDAO;
 import com.zbensoft.mmsmp.vas.sjb.data.LoadUserOrderDao;
 import com.zbensoft.mmsmp.vas.sjb.data.UserServiceHisDao;
 import com.zbensoft.mmsmp.vas.sjb.data.Userservhistory;
 import com.zbensoft.mmsmp.vas.sjb.messagehandler.SendNotificationMessageHandler;
 import com.zbensoft.mmsmp.vas.sjb.util.DateString;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import org.apache.commons.lang3.*;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
 public class TimerDipatcher
   implements Runnable
 {
   private static final Log logger = LogFactory.getLog(TimerDipatcher.class);
   
 
 
 
   private boolean manualWork = ConfigUtil.getInstance()
     .getCorebizConfig().isManualWork();
   
 
 
   private boolean dispatchByContentTime = false;
   
   private MessageQueue queue;
   
   private LoadUserOrderDao loadUserOrderDao;
   
   private ContentInfoDAO contentInfoDAO;
   
   private BusinessManageDAO businessManageDAO;
   
   private UserServiceHisDao userServiceHisDao;
   
   List<String> areaList;
   Map<String, String> sysParamsMap;
   boolean isNational = false;
   
   private String lastDateString = "";
   ExecutorService loaderThreadPool = Executors.newFixedThreadPool(3);
   
 
 
   private DataCenter dataCenter;
   
 
 
   private SendNotificationMessageHandler sendNotificationMessageHandler;
   
 
 
 
   public TimerDipatcher(MessageQueue queue, LoadUserOrderDao loadUserOrderDao, ContentInfoDAO contentInfoDAO, BusinessManageDAO businessManageDAO, DataCenter dataCenter)
   {
     this.queue = queue;
     this.loadUserOrderDao = loadUserOrderDao;
     this.contentInfoDAO = contentInfoDAO;
     this.businessManageDAO = businessManageDAO;
     this.dataCenter = dataCenter;
     this.areaList = new ArrayList();
     this.sysParamsMap = dataCenter.getSysParamsMap();
     this.isNational = ("0000".equals(((String)this.sysParamsMap.get("PLATFORM_AREA")).split("@")[0]));
     String platformModel = ((String)this.sysParamsMap.get("PLATFORM_AREA")).split("@")[0];
     boolean adapter = !"0".equals(((String)this.sysParamsMap.get("ADAPTER_SWITCH")).split("@")[0]);
     if ((platformModel != null) && (!"0000".equals(platformModel))) {
       if (adapter) {
         this.areaList = this.businessManageDAO.getALLArea(platformModel);
       } else {
         this.areaList.add(platformModel);
         this.isNational = true;
       }
     } else {
       List<String> provinceList = this.businessManageDAO.getALLProvince();
       this.areaList = provinceList;
     }
   }
   
 
 
 
 
 
 
 
   public void run()
   {
     List<Userservhistory> userServHisList = this.userServiceHisDao.getUserInfo();
     for (Userservhistory userServ : userServHisList) {
       SendNotificationMessage notifyMsg = new SendNotificationMessage();
       notifyMsg.setServiceCode(userServ.getServiceCode());
       notifyMsg.setContentId(userServ.getContentid());
       notifyMsg.setPhoneNumber(new String[] { userServ.getCellphonenumber() });
       notifyMsg.setMtType("0");
       notifyMsg.setSendType(1);
       this.sendNotificationMessageHandler.handleMessage(notifyMsg);
     }
     
     List<Vasservice> serviceList = this.userServiceHisDao.findService();
     if (!this.businessManageDAO.updateProductDealStatus(Integer.valueOf(0), serviceList)) {
       logger.error("更新产品状态失败！");
     }
     
     Object contentList = this.userServiceHisDao.getNeedSendContentList(this.isNational);
     if (!this.contentInfoDAO.updateFlag((List)contentList, "1")) {
       logger.error("更新内容状态失败！");
     }
     
     for (;;)
     {
       String currentDateString = DateString.getDateHourString();
       
 
 
 
 
       try
       {
         this.dataCenter.refreshAll();
       }
       catch (Exception e) {
         try {
           Thread.sleep(10000L);
         } catch (InterruptedException e1) {
           e1.printStackTrace();
         }
       }
       
 
 
 
 
 
 
 
 
       Map<Integer, Vasservice> servMap = this.dataCenter.getServiceMapById();
       Map<String, String> sysParamsMap = this.dataCenter.getSysParamsMap();
       
       List<Integer> vasIdList = new ArrayList();
       
       DateUtil dateUtil = new DateUtil();
       String currentTime = DateUtil.getTime(new Date());
       String currentHour = currentTime.substring(0, 2);
       
 
 
 
       for (Map.Entry<Integer, Vasservice> entry : servMap.entrySet())
       {
         if (((Vasservice)entry.getValue()).getFeetype().equals("1"))
         {
           if (this.manualWork) {
             int status = this.businessManageDAO.getDealStatus((Integer)entry.getKey()).intValue();
             
 
             if (status == 1) {
               vasIdList.add((Integer)entry.getKey());
             }
             
 
           }
           else
           {
             String dispatchContent = ((String)sysParamsMap.get("SEND_TIME_BASIS")).split("@")[0];
             if ("0".equals(dispatchContent)) {
               this.dispatchByContentTime = true;
               vasIdList.add((Integer)entry.getKey());
 
             }
             else
             {
 
               ServMtMode mtMode = this.dataCenter.findServMtMode(
                 new ServMtModeId((Integer)entry.getKey(), dateUtil.getWeekNum(new Date())));
               
               if (mtMode == null) {
                 mtMode = this.dataCenter.findServMtMode(
                   new ServMtModeId(
                   (Integer)entry.getKey(), dateUtil.getDayOfMonthNum(new Date())));
               }
               Vasservice service = this.dataCenter.findService((Integer)entry.getKey());
               
 
 
               if ((noSendTime(true, mtMode, service)) || 
                 (isSendTime(currentHour, mtMode, service)))
               {
                 vasIdList.add((Integer)entry.getKey());
               }
             }
           }
         }
       }
       
 
 
 
 
 
 
 
       for (Integer serviceID : vasIdList) {
         try
         {
           Vasservice vas = this.dataCenter.findService(serviceID);
           
           List<ContentInfo> needSendContentList = this.contentInfoDAO.getNeedSendContentList(serviceID.intValue(), this.dispatchByContentTime, this.isNational);
           
 
           if ((needSendContentList != null) && (needSendContentList.size() > 0))
           {
 
 
             this.businessManageDAO.updateProductDealStatus(Integer.valueOf(2), vas.getUniqueid());
             
 
 
 
             DispatchingProduct loader = new DispatchingProduct(this.queue, needSendContentList, vas, this.areaList, 
               this.loadUserOrderDao, this.contentInfoDAO, this.businessManageDAO, this.dataCenter);
             
             this.loaderThreadPool.execute(loader);
           }
         }
         catch (Exception e) {
           e.printStackTrace();
         }
       }
       
 
       try
       {
         Thread.sleep(60000L);
       } catch (Exception ex) {
         ex.printStackTrace();
       }
     }
   }
   
 
 
 
 
 
 
 
   private boolean isSendTime(String currentHour, ServMtMode mtMode, Vasservice vasservice)
   {
     if (mtMode == null) {
       return true;
     }
     String time1 = mtMode.getMtstarttime1() == null ? "0" : 
       mtMode.getMtstarttime1();
     String time11 = mtMode.getMtendtime1() == null ? "0" : 
       mtMode.getMtendtime1();
     String time2 = mtMode.getMtstarttime2() == null ? "0" : 
       mtMode.getMtstarttime2();
     String time22 = mtMode.getMtendtime2() == null ? "0" : 
       mtMode.getMtendtime2();
     String time3 = mtMode.getMtstarttime3() == null ? "0" : 
       mtMode.getMtstarttime3();
     String time33 = mtMode.getMtendtime3() == null ? "0" : 
       mtMode.getMtendtime3();
     String time4 = mtMode.getMtstarttime4() == null ? "0" : 
       mtMode.getMtstarttime4();
     String time44 = mtMode.getMtendtime4() == null ? "0" : 
       mtMode.getMtendtime4();
     String time5 = mtMode.getMtstarttime5() == null ? "0" : 
       mtMode.getMtstarttime5();
     String time55 = mtMode.getMtendtime5() == null ? "0" : 
       mtMode.getMtendtime5();
     if (noSendTime(false, mtMode, vasservice))
       return true;
     if ((StringUtils.isNotEmpty(time1)) && 
       (StringUtils.isNotEmpty(time2)) && 
       (StringUtils.isNotEmpty(time3)) && 
       (StringUtils.isNotEmpty(time11)) && 
       (StringUtils.isNotEmpty(time22)) && 
       (StringUtils.isNotEmpty(time33)) && 
       (StringUtils.isNotEmpty(time4)) && 
       (StringUtils.isNotEmpty(time44)) && 
       (StringUtils.isNotEmpty(time5)) && 
       (StringUtils.isNotEmpty(time55))) {
       if (((currentHour.compareTo(time1) >= 0) && 
         (currentHour.compareTo(time11) <= 0)) || 
         ((currentHour.compareTo(time2) >= 0) && 
         (currentHour.compareTo(time22) <= 0)) || 
         ((currentHour.compareTo(time3) >= 0) && 
         (currentHour.compareTo(time33) <= 0)) || 
         ((currentHour.compareTo(time4) >= 0) && 
         (currentHour.compareTo(time44) <= 0)) || (
         (currentHour.compareTo(time5) >= 0) && 
         (currentHour.compareTo(time55) <= 0))) {
         return true;
       }
     } else if ((StringUtils.isNotEmpty(time1)) && 
       (StringUtils.isNotEmpty(time11))) {
       if ((currentHour.compareTo(time1) >= 0) && 
         (currentHour.compareTo(time11) <= 0)) {
         return true;
       }
     } else if ((StringUtils.isNotEmpty(time1)) && (StringUtils.isEmpty(time11))) {
       if (currentHour.compareTo(time1) >= 0) {
         return true;
       }
     } else if ((StringUtils.isEmpty(time1)) && (StringUtils.isNotEmpty(time11)) && 
       (currentHour.compareTo(time11) <= 0)) {
       return true;
     }
     
 
 
 
     return false;
   }
   
 
 
 
 
 
 
   private boolean noSendTime(boolean bool, ServMtMode mtMode, Vasservice vasservice)
   {
     if (mtMode == null)
     {
 
 
 
 
       return true;
     }
     String time1 = mtMode.getMtstarttime1() == null ? "0" : 
       mtMode.getMtstarttime1();
     String time11 = mtMode.getMtendtime1() == null ? "0" : 
       mtMode.getMtendtime1();
     String time2 = mtMode.getMtstarttime2() == null ? "0" : 
       mtMode.getMtstarttime2();
     String time22 = mtMode.getMtendtime2() == null ? "0" : 
       mtMode.getMtendtime2();
     String time3 = mtMode.getMtstarttime3() == null ? "0" : 
       mtMode.getMtstarttime3();
     String time33 = mtMode.getMtendtime3() == null ? "0" : 
       mtMode.getMtendtime3();
     String time4 = mtMode.getMtstarttime4() == null ? "0" : 
       mtMode.getMtstarttime4();
     String time44 = mtMode.getMtendtime4() == null ? "0" : 
       mtMode.getMtendtime4();
     String time5 = mtMode.getMtstarttime5() == null ? "0" : 
       mtMode.getMtstarttime5();
     String time55 = mtMode.getMtendtime5() == null ? "0" : 
       mtMode.getMtendtime5();
     if ((StringUtils.isEmpty(time1)) && (StringUtils.isEmpty(time11)) && 
       (StringUtils.isEmpty(time2)) && (StringUtils.isEmpty(time22)) && 
       (StringUtils.isEmpty(time3)) && (StringUtils.isEmpty(time33)) && 
       (StringUtils.isEmpty(time4)) && (StringUtils.isEmpty(time44)) && 
       (StringUtils.isEmpty(time5)) && (StringUtils.isEmpty(time55)))
     {
 
 
 
 
       return true;
     }
     return false;
   }
   
 
 
   public BusinessManageDAO getBusinessManageDAO()
   {
     return this.businessManageDAO;
   }
   
   public void setBusinessManageDAO(BusinessManageDAO businessManageDAO) {
     this.businessManageDAO = businessManageDAO;
   }
   
   public ContentInfoDAO getContentInfoDAO() {
     return this.contentInfoDAO;
   }
   
   public void setContentInfoDAO(ContentInfoDAO contentInfoDAO) {
     this.contentInfoDAO = contentInfoDAO;
   }
   
   public LoadUserOrderDao getLoadUserOrderDao() {
     return this.loadUserOrderDao;
   }
   
   public void setLoadUserOrderDao(LoadUserOrderDao loadUserOrderDao) {
     this.loadUserOrderDao = loadUserOrderDao;
   }
   
   public MessageQueue getQueue() {
     return this.queue;
   }
   
   public void setQueue(MessageQueue queue) {
     this.queue = queue;
   }
   
   public SendNotificationMessageHandler getSendNotificationMessageHandler() {
     return this.sendNotificationMessageHandler;
   }
   
   public void setSendNotificationMessageHandler(SendNotificationMessageHandler sendNotificationMessageHandler)
   {
     this.sendNotificationMessageHandler = sendNotificationMessageHandler;
   }
   
   public UserServiceHisDao getUserServiceHisDao() {
     return this.userServiceHisDao;
   }
   
   public void setUserServiceHisDao(UserServiceHisDao userServiceHisDao) {
     this.userServiceHisDao = userServiceHisDao;
   }
 }





