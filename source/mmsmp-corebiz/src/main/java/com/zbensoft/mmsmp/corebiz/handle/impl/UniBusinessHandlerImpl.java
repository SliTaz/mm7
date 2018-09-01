 package com.zbensoft.mmsmp.corebiz.handle.impl;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
 import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
 import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse;
 import com.zbensoft.mmsmp.common.ra.common.message.WOCheckRequest;
 import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
 import com.zbensoft.mmsmp.corebiz.cache.DataCache;
 import com.zbensoft.mmsmp.corebiz.dao.DaoUtil;
 import com.zbensoft.mmsmp.corebiz.dao.SmsDAO;
 import com.zbensoft.mmsmp.corebiz.message.OrderRelationMessage;
 import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
 import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDao;
 import com.zbensoft.mmsmp.common.ra.send.util.SmsSenderUtil;
 import com.zbensoft.mmsmp.common.ra.utils.DateHelper;
 import com.zbensoft.mmsmp.common.ra.vacNew.util.Utility;
 import com.zbensoft.mmsmp.vas.sjb.data.OrderRelationDAO;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.Constants;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.OrderRelationRequest;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.log4j.Logger;
 
 
 public class UniBusinessHandlerImpl
 {
   static final Logger logger = Logger.getLogger(UniBusinessHandlerImpl.class);
   
   IMessageRouter messageRouter;
   
   DaoUtil daoUtil;
   
   DataCache dataCache;
   
   ConcurrentHashMap<String, AbstractMessage> dataMap;
   ConcurrentHashMap<String, AbstractMessage> commonMap;
   
   public void doHandler(AbstractMessage message)
   {
     if ((message instanceof OrderRelationMessage)) {
       orderRelationManage((OrderRelationMessage)message);
     } else if ((message instanceof WOCheckResponse)) {
       doWoAgent((WOCheckResponse)message);
     } else if ((message instanceof CheckResponse)) {
       doVacAgent((CheckResponse)message);
     } else if ((message instanceof OrderRelationUpdateNotifyResponse))
     {
       updateOrder((OrderRelationUpdateNotifyResponse)message);
     }
   }
   
 
 
 
 
 
 
   public void orderRelationManage(OrderRelationMessage orderRelationMessage)
   {
     this.dataMap.put(orderRelationMessage.getGlobalMessageid(), orderRelationMessage);
     
 
     logger.info("starting to handler OrderRelationMessage......");
     OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
     String channel = "2";
     int status = 2;
     Vasservice vasservice = null;
     try {
       channel = orderRelationRequest.getChannel();
       status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();
       
       vasservice = this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
       if (vasservice == null) {
         vasservice = new Vasservice();
       }
     } catch (RuntimeException e) {
       e.printStackTrace();
       logger.error("channel : " + channel + " status : " + status + " vasservice: " + vasservice);
     }
     
 
 
     boolean inWhiteList = this.dataCache.isWhiteUser(orderRelationRequest.getUserPhone(), orderRelationRequest.getProductID(), vasservice.getVasid());
     logger.info("is the user in whitelist: " + inWhiteList + " globalMessageid=" + orderRelationMessage.getGlobalMessageid());
     
 
     int intChannel = Integer.parseInt(channel);
     
     if ((intChannel == 1) || (intChannel == 2)) {
       if (intChannel == 1) logger.info("order from web...."); else
         logger.info("order from wap....");
       int feetype = 0;
       try {
         if (Constants.OrderRelation_Status_Order == status) {
           orderRelationMessage.getOrderRelationRequest().setFeeType(1);
           feetype = 1;
         } else if (Constants.OrderRelation_Status_Demand == status) {
           orderRelationMessage.getOrderRelationRequest().setFeeType(4);
           feetype = 2;
         } else if (Constants.OrderRelation_Status_Cancel == status) {
           orderRelationMessage.getOrderRelationRequest().setFeeType(2);
           feetype = 1;
         } else {
           logger.error("unknown web order type...");
           return;
         }
         
         if (!inWhiteList)
         {
           CheckRequest checkRequest = new CheckRequest();
           checkRequest.setSp_product_id(orderRelationRequest.getProductID());
           checkRequest.setSp_id(orderRelationRequest.getSpCode());
           checkRequest.setService_id(orderRelationRequest.getServiceId());
           
           checkRequest.setUser_number(orderRelationRequest.getChargeparty());
           checkRequest.setServiceType(String.valueOf(orderRelationRequest.getFeeType()));
           checkRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
           this.messageRouter.doRouter(checkRequest);
           logger.info("order relation check vacAgent ......[chargeparty:" + orderRelationRequest.getChargeparty() + " globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
         }
         else {
           if (orderRelationRequest.getUserType() == 2)
           {
             WOCheckResponse wr = new WOCheckResponse();
             wr.setChargetNumber(orderRelationRequest.getChargeparty());
             this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
             logger.info("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID());
           }
           
 
 
           saveOrder(orderRelationMessage.getGlobalMessageid(), feetype);
           
 
           if ((Constants.OrderRelation_Status_Order == status) || (Constants.OrderRelation_Status_Cancel == status)) {
             String orderNotifyUrl = this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());
             
             logger.info("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:" + orderNotifyUrl);
             
 
             OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
             
             orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
             orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
             orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
             if (status == 0) {
               orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
             } else {
               orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
             }
             orderRelationUpdateNotifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
             orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
             orderRelationUpdateNotifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
             orderRelationUpdateNotifyRequest.setLinkId("");
             orderRelationUpdateNotifyRequest.setServiceType("31");
             orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
             orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
             orderRelationUpdateNotifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
             orderRelationUpdateNotifyRequest.setUpdateDesc("");
             orderRelationUpdateNotifyRequest.setUpdateTime("");
             orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
             orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
             orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(orderRelationRequest.getFeeType()));
             
 
             this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
             logger.info("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
           }
           else if (Constants.OrderRelation_Status_Demand == status) {
             String url = this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
             
             String vasId = this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
             
 
             MO_SMMessage mosms = new MO_SMMessage();
             
             mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
             mosms.setSendAddress(orderRelationRequest.getUserPhone());
             mosms.setVasId(vasId);
             mosms.setSmsText(vasservice.getOndemandcode());
             mosms.setLinkId("");
             mosms.setNotirySPURL(url);
             
             mosms.setSequence_Number_1(Integer.valueOf(111));
             mosms.setSequence_Number_2(Integer.valueOf(222));
             mosms.setSequence_Number_3(Integer.valueOf(333));
             mosms.setMessageCoding((byte)15);
             mosms.setContentLength(vasservice.getOndemandcode().length());
             this.messageRouter.doRouter(mosms);
             logger.info("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
           } else {
             logger.info("unknown order type...[status: status:" + status + "]");
           }
         }
       } catch (Throwable ex) {
         logger.error("", ex);
       }
     }
     if (intChannel == 3) {
       logger.info("order from iphone.....");
       int reqtype = 0;
       int feetype = 0;
       if (Constants.OrderRelation_Status_Order == status) {
         reqtype = 2;
         feetype = 1;
       } else if (Constants.OrderRelation_Status_Demand == status) {
         reqtype = 1;
         feetype = 2;
       } else {
         logger.error("unknown web order type...");
         return;
       }
       try {
         if (!inWhiteList)
         {
           WOCheckRequest wc = new WOCheckRequest();
           wc.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
           
           wc.setUserPhone(orderRelationRequest.getChargeparty());
           wc.setSp_productID(orderRelationRequest.getProductID());
           wc.setServicename(orderRelationRequest.getServiceName());
           wc.setFee(orderRelationRequest.getFee());
           wc.setFeeType(String.valueOf(feetype));
           wc.setPeroid(orderRelationRequest.getPeroid());
           wc.setReqType(reqtype);
           this.messageRouter.doRouter(wc);
           logger.info("order relation check woAgent......[chargeparty:" + orderRelationRequest.getChargeparty() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
         } else {
           if (orderRelationRequest.getUserType() == 2)
           {
             WOCheckResponse wr = new WOCheckResponse();
             wr.setChargetNumber(orderRelationRequest.getChargeparty());
             this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
             logger.info("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID());
           }
           
           saveOrder(orderRelationMessage.getGlobalMessageid(), feetype);
           
           if (Constants.OrderRelation_Status_Order == status)
           {
 
 
             String orderNotifyUrl = this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());
             
 
 
 
 
             logger.info("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:" + orderNotifyUrl);
             
             Calendar calendar = Calendar.getInstance();
             calendar.add(2, orderRelationRequest.getPeroid());
             
             OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
             
             orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
             orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
             orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
             if (status == 0) {
               orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
             } else {
               orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
             }
             orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
             orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
             orderRelationUpdateNotifyRequest.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
             orderRelationUpdateNotifyRequest.setLinkId("");
             orderRelationUpdateNotifyRequest.setServiceType("31");
             orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
             orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
             orderRelationUpdateNotifyRequest.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
             orderRelationUpdateNotifyRequest.setUpdateDesc("");
             orderRelationUpdateNotifyRequest.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
             orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
             orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
             this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
             
             logger.info("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
           }
           else if (Constants.OrderRelation_Status_Demand == orderRelationRequest.getStatus())
           {
             String url = this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
             
             String vasId = this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
             String linkid = "ACE" + SmsSenderUtil.generateRandomCode(5);
             logger.info("linkid generated by aceway is : " + linkid);
             
             MO_SMMessage mosms = new MO_SMMessage();
             
             mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
             mosms.setSendAddress(orderRelationRequest.getUserPhone());
             mosms.setVasId(vasId);
             mosms.setSmsText(vasservice.getOndemandcode());
             mosms.setLinkId(linkid);
             mosms.setNotirySPURL(url);
             
             mosms.setSequence_Number_1(Integer.valueOf(111));
             mosms.setSequence_Number_2(Integer.valueOf(222));
             mosms.setSequence_Number_3(Integer.valueOf(333));
             mosms.setMessageCoding((byte)15);
             mosms.setContentLength(vasservice.getOndemandcode().length());
             this.messageRouter.doRouter(mosms);
             
             logger.info("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
           }
         }
       }
       catch (Throwable ex) {
         logger.error("", ex);
       }
       
     }
     else if (intChannel == 6) {
       logger.info(" iphone cannel  order relation...");
       
 
       saveOrder(orderRelationMessage.getGlobalMessageid(), 1);
       
 
       String orderNotifyUrl = this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());
       
       logger.info("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:" + orderNotifyUrl);
       
       Calendar calendar = Calendar.getInstance();
       calendar.add(2, orderRelationRequest.getPeroid());
       
       OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
       
       orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
       orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
       orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
       
       orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
       orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
       orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
       orderRelationUpdateNotifyRequest.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
       orderRelationUpdateNotifyRequest.setLinkId("");
       orderRelationUpdateNotifyRequest.setServiceType("31");
       orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
       orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
       orderRelationUpdateNotifyRequest.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
       orderRelationUpdateNotifyRequest.setUpdateDesc("");
       orderRelationUpdateNotifyRequest.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
       orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(2));
       orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
       this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
       
       logger.info("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
     }
     else
     {
       logger.error("channel Error...");
     }
   }
   
 
 
 
   public void doWoAgent(WOCheckResponse response)
   {
     logger.info("=====> receive WOCheckResponse from woAgent !  code:" + response.getReturnStr());
     
 
 
     OrderRelationMessage orderRelationMessage = (OrderRelationMessage)this.dataMap.get(response.getGlobalMessageid());
     OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
     int status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();
     Vasservice vasservice = this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
     if (Constants.OrderRelation_Status_Order == status) {
       logger.info("order relation  status" + status);
       if (response.getReturnStr().equals("20000"))
       {
         logger.info("=====>order relation check woAgent successfully!");
         
         if (orderRelationRequest.getUserType() == 2)
         {
           WOCheckResponse wr = new WOCheckResponse();
           wr.setChargetNumber(orderRelationRequest.getChargeparty());
           this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
           logger.info("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID());
         }
         
 
         saveOrder(response.getGlobalMessageid(), 1);
         
         String orderNotifyUrl = this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());
         
         logger.info("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:" + orderNotifyUrl);
         
         Calendar calendar = Calendar.getInstance();
         calendar.add(2, orderRelationRequest.getPeroid());
         
 
         OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
         
         orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
         orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
         if (status == 0) {
           orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
         } else {
           orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
         }
         orderRelationUpdateNotifyRequest.setEffectiveDate(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
         orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
         orderRelationUpdateNotifyRequest.setExpireDate(DateHelper.getString(calendar.getTime(), "yyyyMMddHHmmss"));
         orderRelationUpdateNotifyRequest.setLinkId("");
         orderRelationUpdateNotifyRequest.setServiceType("31");
         orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
         orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
         orderRelationUpdateNotifyRequest.setTime_stamp(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
         orderRelationUpdateNotifyRequest.setUpdateDesc("");
         orderRelationUpdateNotifyRequest.setUpdateTime(DateHelper.getString(new Date(), "yyyyMMddHHmmss"));
         orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(1));
         orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
         orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(1));
         this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
         logger.info("[orderNotifyUrl:" + orderNotifyUrl + "]");
       }
       else {
         String _msg = "订购失败";
         String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
         MT_SMMessage mtsmm = new MT_SMMessage();
         mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         mtsmm.setSmsText(_msg);
         mtsmm.setRcvAddresses(userPhoneArr);
         this.messageRouter.doRouter(mtsmm);
         logger.info("=====>order relation check woAgent failed! response.returnStr()" + response.getReturnStr());
       }
     }
     else if (Constants.OrderRelation_Status_Demand == status)
     {
       logger.info("demand  status==" + status + "vasservice.getOndemandfee:" + orderRelationRequest.getFee());
       
 
       String wo_money = response.getReturnStr();
       double wo_money_double = Double.parseDouble(wo_money);
       
 
       double fee = orderRelationRequest.getFee();
       logger.info("[wo_money_double:" + wo_money_double + " fee:" + fee + "]");
       if (wo_money_double < fee) {
         logger.info("=====>demand fails, your money less fertile, please recharge");
         logger.info("=====>wo_money_double" + wo_money_double + "fee==" + fee);
         
 
         String _msg = "您定购失败！,沃币不足，请及时充值";
         String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
         MT_SMMessage mtsmm = new MT_SMMessage();
         mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         mtsmm.setSmsText(_msg);
         mtsmm.setRcvAddresses(userPhoneArr);
         this.messageRouter.doRouter(mtsmm);
       }
       else {
         logger.info("=====>demand check woAgent successfully!");
         
         if (orderRelationRequest.getUserType() == 2)
         {
           WOCheckResponse wr = new WOCheckResponse();
           wr.setChargetNumber(orderRelationRequest.getChargeparty());
           this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
           logger.info("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID());
         }
         
 
         saveOrder(response.getGlobalMessageid(), 2);
         
         String url = this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
         String vasId = this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
         String linkid = "ACE" + SmsSenderUtil.generateRandomCode(5);
         logger.info("linkid generated by aceway is : " + linkid);
         
         MO_SMMessage mosms = new MO_SMMessage();
         
         mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         mosms.setSendAddress(orderRelationRequest.getUserPhone());
         mosms.setVasId(vasId);
         mosms.setSmsText(vasservice.getOndemandcode());
         mosms.setLinkId(linkid);
         mosms.setNotirySPURL(url);
         
         mosms.setSequence_Number_1(Integer.valueOf(111));
         mosms.setSequence_Number_2(Integer.valueOf(222));
         mosms.setSequence_Number_3(Integer.valueOf(333));
         mosms.setMessageCoding((byte)15);
         mosms.setContentLength(vasservice.getOndemandcode().length());
         
         this.messageRouter.doRouter(mosms);
         
         this.commonMap.put("WO" + linkid + orderRelationRequest.getUserPhone(), response);
         logger.info("缓存给彩信下行用key=======WO" + linkid + orderRelationRequest.getUserPhone());
       }
     }
   }
   
 
 
 
   public void doVacAgent(CheckResponse response)
   {
     logger.info("=====> receive CheckResponse from vacAgent !  code:" + response.getResult_Code());
     
     OrderRelationMessage orderRelationMessage = (OrderRelationMessage)this.dataMap.get(response.getCRequest().getGlobalMessageid());
     OrderRelationRequest orderRelationRequest = orderRelationMessage.getOrderRelationRequest();
     int status = Integer.valueOf(orderRelationRequest.getStatus()).intValue();
     Vasservice vasservice = this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(orderRelationRequest.getProductID());
     if ((response.getResult_Code().equals("0")) || (response.getResult_Code().equals("1201")))
     {
       logger.info("=====>order relation check vacAgent successfully!");
       if (orderRelationRequest.getUserType() == 2)
       {
         WOCheckResponse wr = new WOCheckResponse();
         wr.setChargetNumber(orderRelationRequest.getChargeparty());
         this.commonMap.put("ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID(), wr);
         logger.info("赠送缓存计费号码给彩信下行用key=======ZS" + orderRelationRequest.getUserPhone() + orderRelationRequest.getProductID());
       }
       
 
       if ((Constants.OrderRelation_Status_Order == status) || (Constants.OrderRelation_Status_Cancel == status))
       {
 
         saveOrder(response.getCRequest().getGlobalMessageid(), 1);
         
         String orderNotifyUrl = this.daoUtil.getSmsSenderDao().getRelationNotifyUrl(orderRelationRequest.getSpCode());
         
         logger.info("the relation notifysp url,  spid:" + orderRelationRequest.getSpCode() + "     url:" + orderNotifyUrl);
         
 
         OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest = new OrderRelationUpdateNotifyRequest();
         
         orderRelationUpdateNotifyRequest.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         orderRelationUpdateNotifyRequest.setNotifySPURL(orderNotifyUrl);
         orderRelationUpdateNotifyRequest.setRecordSequenceId(String.valueOf(System.currentTimeMillis()));
         if (status == 0) {
           orderRelationUpdateNotifyRequest.setContent(vasservice.getOrdercode());
         } else {
           orderRelationUpdateNotifyRequest.setContent(vasservice.getCancelordercode());
         }
         orderRelationUpdateNotifyRequest.setEffectiveDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
         orderRelationUpdateNotifyRequest.setEncodeStr(Utility.md5_encrypt("test"));
         orderRelationUpdateNotifyRequest.setExpireDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
         orderRelationUpdateNotifyRequest.setLinkId("");
         orderRelationUpdateNotifyRequest.setServiceType("31");
         orderRelationUpdateNotifyRequest.setProductId(orderRelationRequest.getProductID());
         orderRelationUpdateNotifyRequest.setSpId(orderRelationRequest.getSpCode());
         orderRelationUpdateNotifyRequest.setTime_stamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
         orderRelationUpdateNotifyRequest.setUpdateDesc("");
         orderRelationUpdateNotifyRequest.setUpdateTime("");
         orderRelationUpdateNotifyRequest.setUserId(orderRelationRequest.getUserPhone());
         orderRelationUpdateNotifyRequest.setUpdateType(Integer.valueOf(orderRelationRequest.getFeeType()));
         orderRelationUpdateNotifyRequest.setUserIdType(Integer.valueOf(orderRelationRequest.getFeeType()));
         
 
         this.messageRouter.doRouter(orderRelationUpdateNotifyRequest);
         
         logger.info("send order relation to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
       }
       else if (Constants.OrderRelation_Status_Demand == status)
       {
 
         saveOrder(response.getCRequest().getGlobalMessageid(), 2);
         
         String url = this.daoUtil.getSmsSenderDao().getSmsSenderUrl(orderRelationRequest.getServiceId());
         String vasId = this.daoUtil.getSmsSenderDao().getService_idByProduct_id(orderRelationRequest.getProductID());
         
 
         MO_SMMessage mosms = new MO_SMMessage();
         
         mosms.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
         mosms.setSendAddress(orderRelationRequest.getUserPhone());
         mosms.setVasId(vasId);
         mosms.setSmsText(vasservice.getOndemandcode());
         mosms.setLinkId(response.getLinkID());
         mosms.setNotirySPURL(url);
         
         mosms.setSequence_Number_1(Integer.valueOf(111));
         mosms.setSequence_Number_2(Integer.valueOf(222));
         mosms.setSequence_Number_3(Integer.valueOf(333));
         mosms.setMessageCoding((byte)15);
         mosms.setContentLength(vasservice.getOndemandcode().length());
         
         this.messageRouter.doRouter(mosms);
         
         logger.info("send demand to spAgent ......[user:" + orderRelationRequest.getUserPhone() + "   globalMessageid:" + orderRelationMessage.getGlobalMessageid() + " status:" + status + "]");
       }
     }
     else if ((response.getServerResult_code().equals("1200")) && (Constants.OrderRelation_Status_Order == status))
     {
       logger.info("=====>order relation check vacAgent failed==>order relation is exists !");
       
 
       String _msg = "您已经订购！,无须重复订购";
       String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
       MT_SMMessage mtsmm = new MT_SMMessage();
       mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
       mtsmm.setSmsText(_msg);
       mtsmm.setRcvAddresses(userPhoneArr);
       
       this.messageRouter.doRouter(mtsmm);
     }
     else
     {
       logger.info("=====>order relation check vacAgent fail! response.serverResult_code:" + response.getServerResult_code());
       
 
       String _msg = "";
       if (Constants.OrderRelation_Status_Order == status) { _msg = "订购失败";
       } else if (Constants.OrderRelation_Status_Demand == status) _msg = "点播失败"; else
         _msg = "退订失败";
       String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
       MT_SMMessage mtsmm = new MT_SMMessage();
       mtsmm.setGlobalMessageid(orderRelationMessage.getGlobalMessageid());
       mtsmm.setSmsText(_msg);
       mtsmm.setRcvAddresses(userPhoneArr);
       this.messageRouter.doRouter(mtsmm);
     }
   }
   
 
 
 
   public void saveOrder(String globalMessageid, int feetype)
   {
     OrderRelationRequest orderRelationRequest = ((OrderRelationMessage)this.dataMap.get(globalMessageid)).getOrderRelationRequest();
     String _msg = "";
     String _msg1 = "";
     com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDto smsSenderDto = this.daoUtil.getSmsSenderDao().getProductInfo(orderRelationRequest.getProductID(), feetype);
     
     com.zbensoft.mmsmp.corebiz.handle.impl.sms.SmsSenderDto spBean = this.daoUtil.getSmsDAO().getSPInfoByProdId(orderRelationRequest.getProductID());
     if ((Constants.OrderRelation_Status_Order == orderRelationRequest.getStatus()) || (Constants.OrderRelation_Status_Demand == orderRelationRequest.getStatus())) {
       SaveOrderRaltion(orderRelationRequest.getUserPhone(), orderRelationRequest.getChargeparty(), orderRelationRequest.getProductID(), orderRelationRequest.getSpCode(), orderRelationRequest.getChannel(), "", "1", orderRelationRequest.getPeroid());
       
       if (orderRelationRequest.getUserType() == 2) {
         _msg = "您赠送给" + orderRelationRequest.getUserPhone() + "[" + smsSenderDto.getServicename() + "]成功！,资费:" + smsSenderDto.getFee() + "元";
         _msg1 = orderRelationRequest.getChargeparty() + "赠送给您" + "[" + smsSenderDto.getServicename() + "]成功";
       }
       else if (1 == feetype)
       {
 
         _msg = "您已成功订购" + spBean.getVaspname() + "的" + smsSenderDto.getServicename() + "服务，" + smsSenderDto.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone();
       } else if (2 == feetype)
       {
         _msg = "尊敬的用户，感谢您点播" + spBean.getVaspname() + "的" + smsSenderDto.getServicename() + "服务，" + smsSenderDto.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone() + "。";
       } else {
         _msg = "尊敬的用户，感谢您使用" + spBean.getVaspname() + "的" + smsSenderDto.getServicename() + "服务，" + smsSenderDto.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone() + "。";
       }
     }
     else if (Constants.OrderRelation_Status_Cancel == orderRelationRequest.getStatus()) {
       logger.info("=====>cancel order relation notify sp successfully! status:" + orderRelationRequest.getStatus());
       int uniqueid = Integer.valueOf(this.daoUtil.getSmsSenderDao().getUniqueid(orderRelationRequest.getProductID())).intValue();
       this.daoUtil.getOrderRelationDAO().delOrderRelation(orderRelationRequest.getUserPhone(), Integer.valueOf(uniqueid));
       
       _msg = "您已成功退订" + spBean.getVaspname() + "的" + smsSenderDto.getServicename() + "服务，" + smsSenderDto.getFee() + "元，由联通代收。客服电话" + spBean.getBusinessphone();
     }
     
 
 
     if (orderRelationRequest.getUserType() == 2)
     {
       String[] charPhoneArr = { orderRelationRequest.getChargeparty() };
       MT_SMMessage userMtsmm = new MT_SMMessage();
       userMtsmm.setGlobalMessageid(globalMessageid);
       userMtsmm.setSmsText(_msg);
       userMtsmm.setRcvAddresses(charPhoneArr);
       this.messageRouter.doRouter(userMtsmm);
       
 
       String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
       MT_SMMessage charMtsmm = new MT_SMMessage();
       charMtsmm.setGlobalMessageid(globalMessageid);
       charMtsmm.setSmsText(_msg1);
       charMtsmm.setRcvAddresses(userPhoneArr);
       this.messageRouter.doRouter(charMtsmm);
     }
     else
     {
       String[] userPhoneArr = { orderRelationRequest.getUserPhone() };
       MT_SMMessage mtsmm = new MT_SMMessage();
       mtsmm.setGlobalMessageid(globalMessageid);
       mtsmm.setSmsText(_msg);
       mtsmm.setRcvAddresses(userPhoneArr);
       this.messageRouter.doRouter(mtsmm);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
   public void SaveOrderRaltion(String userPhone, String chargeparty, String productId, String spId, String channel, String spOrderId, String notifySpFlag, int period)
   {
     Vasservice service = this.daoUtil.getSmsSenderDao().getVASServiceByServiceCode(productId);
     if (service == null) {
       logger.info("productId not exists!");
     }
     String uniqueid = this.daoUtil.getSmsSenderDao().getUniqueid(productId);
     UserOrder userOrder = this.daoUtil.getSmsSenderDao().getOrderRelation(userPhone, uniqueid);
     if ((userOrder != null) && (service.getFeetype().equals("1"))) {
       logger.info("order relation already exists!");
 
     }
     else
     {
       String provinceCode = this.daoUtil.getSmsSenderDao().getAreaCodeByUserPhone(userPhone);
       String cityCode = this.daoUtil.getSmsSenderDao().getCityCodeByUserPhone(userPhone);
       this.daoUtil.getSmsSenderDao().AddOrderRelation(userPhone, chargeparty, service, provinceCode, cityCode, channel, spOrderId, notifySpFlag, String.valueOf(period));
     }
   }
   
 
 
   public void updateOrder(OrderRelationUpdateNotifyResponse response)
   {
     if (response.getResultCode() == 0) {
       logger.info("=====>order relation notify sp successfully!");
       OrderRelationRequest orderRelationRequest = ((OrderRelationMessage)this.dataMap.get(response.getGlobalMessageid())).getOrderRelationRequest();
       String uniqueid = this.daoUtil.getSmsSenderDao().getUniqueid(orderRelationRequest.getProductID());
       this.daoUtil.getSmsDAO().updateOrderRelation(orderRelationRequest.getUserPhone(), uniqueid);
       logger.info("update userPhone" + orderRelationRequest.getUserPhone() + " serviceuniqueid:" + uniqueid);
     }
   }
   
   public void setMessageRouter(IMessageRouter messageRouter) { this.messageRouter = messageRouter; }
   
   public void setDaoUtil(DaoUtil daoUtil) {
     this.daoUtil = daoUtil;
   }
   
   public void setDataCache(DataCache dataCache) { this.dataCache = dataCache; }
   
   public void setDataMap(ConcurrentHashMap<String, AbstractMessage> dataMap) {
     this.dataMap = dataMap;
   }
   
   public ConcurrentHashMap<String, AbstractMessage> getCommonMap() { return this.commonMap; }
   
   public void setCommonMap(ConcurrentHashMap<String, AbstractMessage> commonMap) {
     this.commonMap = commonMap;
   }
 }





