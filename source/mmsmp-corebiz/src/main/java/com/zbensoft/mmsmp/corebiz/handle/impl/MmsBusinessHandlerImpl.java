 package com.zbensoft.mmsmp.corebiz.handle.impl;
 
 import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
import com.zbensoft.mmsmp.corebiz.cache.DataCache;
import com.zbensoft.mmsmp.corebiz.dao.DaoUtil;
import com.zbensoft.mmsmp.corebiz.message.MT_MMHttpSPMessage_Report;
import com.zbensoft.mmsmp.corebiz.message.MmsDBListener;
import com.zbensoft.mmsmp.corebiz.message.MmsHistoryMessage;
import com.zbensoft.mmsmp.corebiz.message.MmsUpdateListener;
import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
import com.zbensoft.mmsmp.corebiz.util.StringUtil;
import com.zbensoft.mmsmp.vas.sjb.data.DemandDto;
 
 public class MmsBusinessHandlerImpl
 {
   static final Logger logger = Logger.getLogger(MmsBusinessHandlerImpl.class);
   
   static final String SERVICEN_NUMBER = "10655565";
   
   static final String MMSMH = "MMS-MH";
   static final Integer WHITE_FLAG = Integer.valueOf(2);
   static final Integer BLACK_FLAG = Integer.valueOf(1);
   
   IMessageRouter messageRouter;
   
   DaoUtil daoUtil;
   
   DataCache dataCache;
   ConcurrentHashMap<String, AbstractMessage> dataMap;
   ConcurrentHashMap<String, MT_MMHttpSPMessage_Report> mmsReportDataMap;
   ConcurrentHashMap<String, AbstractMessage> commonMap;
   MmsDBListener mmsDBListener;
   MmsUpdateListener mmsUpdateListener;
   boolean debug = false;
   
   public MmsBusinessHandlerImpl()
   {
     String value = System.getProperty("debug");
     if ((value != null) && (value.equalsIgnoreCase("true"))) {
       this.debug = true;
     }
   }
   
   public void doHandler(AbstractMessage message)
   {
     try
     {
       if ((message instanceof MO_MMDeliverSPMessage))
       {
         doMO((MO_MMDeliverSPMessage)message);
       }
       else if ((message instanceof MT_SPMMHttpMessage))
       {
         doMT((MT_SPMMHttpMessage)message);
       }
       else if ((message instanceof MT_ReportMessage))
       {
         doMH((MT_ReportMessage)message);
       }
       else if ((message instanceof MO_ReportMessage))
       {
         doMR((MO_ReportMessage)message);
       }
       else if ((message instanceof WOCheckResponse))
       {
         doCR((WOCheckResponse)message);
       }
       else if ((message instanceof CheckResponse))
       {
         doCR((CheckResponse)message);
       }
     }
     catch (Exception ex)
     {
       logger.info("mms business exception:" + ex.getMessage());
       logger.error("mms business exception:", ex);
     }
   }
   
 
 
 
 
 
 
   public void doMO(MO_MMDeliverSPMessage msg)
   {
     String gmsgid = msg.getGlobalMessageid();
     String sendto = msg.getTo().get(0).toString();
     String phone = StringUtil.getPhone11(msg.getSender());
     
     sendto = sendto.replace("[", "");
     sendto = sendto.replace("]", "");
     
     MT_SMMessage smsmt = new MT_SMMessage();
     smsmt.setGlobalMessageid(gmsgid);
     smsmt.setRcvAddresses(new String[] { "86" + phone });
     smsmt.setServiceCode("10655565");
     smsmt.setLinkId(String.valueOf(System.currentTimeMillis()));
     
     DemandDto dto = this.dataCache.getDemandDtoBySendto(sendto);
     
 
     if ((dto == null) || (dto.getServiceid() == null) || ("".equals(dto.getServiceid().trim())))
     {
       logger.info("mmsmo service not exists [gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto + "]");
       
       smsmt.setSmsText(this.dataCache.queryErrorMsgTemplate() + "发送失败，请检查接入号是否正确！");
       sendSmsMessage(smsmt);
       saveMmsmoHistory(msg, "1");
       return;
     }
     if ((dto.getSpreporturl() == null) || ("".equals(dto.getSpreporturl().trim())))
     {
       logger.info("mmsmo spurl is empty [gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto + "]");
       saveMmsmoHistory(msg, "1");
       return;
     }
     
 
 
 
 
     msg.setSendurl(dto.getSpreporturl());
     msg.setServicesId(dto.getServiceid());
     msg.setProductId(dto.getProductId());
     msg.setSpid(dto.getSpid());
     msg.setNeedConfirm(dto.getNeedConfirm());
     msg.setCheckType(4);
     
 
 
 
     if (this.dataCache.isWhiteUser(phone, dto.getProductId(), null))
     {
       logger.info("mmsmo user is white list[gmsgid:" + gmsgid + ",phone:" + phone + ",productId:" + dto.getProductId() + "]");
       this.messageRouter.doRouter(msg);
       
 
 
//       SmsSenderDto ssd = this.dataCache.getDianBoByNumber(sendto);
       String smsText = "";
//       
//       if ((ssd == null) || (ssd.getVasid() == null))
//       {
//         smsText = this.dataCache.queryErrorMsgTemplate() + "你好，没有这个业务";
//       }
//       else
//       {
         smsText = this.dataCache.queryUpOndemandMessage();
         smsText = smsText.replace("{0}", "test1");//ssd.getServicename());
         smsText = smsText.replace("{1}", "test2");//ssd.getFee());
//       }
       
       smsmt.setSmsText(smsText);
       sendSmsMessage(smsmt);
 
     }
     else
     {
       this.dataMap.put(gmsgid, msg);
       
       logger.info("mmsmo vac auth(4) request[gmsgid:" + gmsgid + ",phone:" + phone + ",sendto:" + sendto + ",needconfirm:" + msg.getNeedConfirm() + "]");
       
 
       CheckRequest cr4 = new CheckRequest(phone, msg.getProductId(), msg.getSpid(), null, null, "4", null);
       cr4.setGlobalMessageid(gmsgid);
       cr4.setResponse(true);
       
       this.messageRouter.doRouter(cr4);
     }
   }
   
 
 
 
 
 
 
   public void doMH(MT_ReportMessage msg)
   {
     String gmsgid = msg.getGlobalMessageid();
     String messageid = msg.getMessageid();
     String reqid = msg.getReqId();
     String spurl = msg.getSpUrl();
     String status = msg.getStatus();
     String content = msg.getContent();
     String mmsCode = msg.getMsgType();
     
 
 
 
 
     logger.info("mmsmh submit result transfer[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid + ",status:" + status + "]");
     
     MO_ReportMessage mr = new MO_ReportMessage();
     mr.setGlobalMessageid(gmsgid);
     mr.setReportUrl(spurl);
     mr.setMessageId(messageid);
     mr.setContent(content);
     
 
 
 
     if ((reqid == null) || ("".equals(reqid.trim())))
     {
       logger.info("mmsmh submit result reqid is null[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid + ",status:" + status + "]");
 
 
     }
     else if (this.mmsReportDataMap.get(gmsgid) == null) {
       logger.info("mmsmh submit result match failure[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid + ",status:" + status + "]");
 
     }
     else
     {
 
       logger.info("mmsmh submit result match success[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid + ",status:" + status + "]");
       
       String gmhid = "MMS-MH" + reqid;
       
       MT_MMHttpSPMessage_Report mt = (MT_MMHttpSPMessage_Report)this.mmsReportDataMap.get(gmsgid);
       mt.setSourceGlobalMessageid(gmsgid);
       mt.setGlobalMessageid(gmhid);
       mt.setGlobalCreateTime(mt.getGlobalCreateTime());
       mt.setGlobalMessageTime(System.currentTimeMillis());
       mt.setReqId(reqid);
       
       mr.setServiceCode(mt.getSp_productid());
       
       this.mmsReportDataMap.put(gmhid, mt);
     }
     
     this.messageRouter.doRouter(mr);
     
 
 
     removeReportMessage(gmsgid);
     
 
     try
     {
       long btime = System.currentTimeMillis();
       
 
 
 
 
       MmsHistoryMessage mhm = new MmsHistoryMessage();
       mhm.setStatus(status);
       mhm.setMessageId(messageid);
       mhm.setReqId(reqid);
       mhm.setMmsGreCode(mmsCode);
       mhm.setReceiveDate(new Date());
       this.mmsUpdateListener.put(mhm);
       
 
       logger.info("mmsmh save mh history success[gmsgid:" + gmsgid + ",messageid:" + messageid + ",reqid:" + reqid + ",status:" + status + ",mmsCode:" + mmsCode + ",exectime:" + (System.currentTimeMillis() - btime) + "]");
     }
     catch (Exception ex)
     {
       logger.error("mmsmh submit result update", ex);
     }
   }
   
 
 
 
 
 
   public void doMR(MO_ReportMessage mr)
   {
     String gmsgid = mr.getGlobalMessageid();
     String reqid = mr.getCorrelator();
     String phone = StringUtil.getPhone11(mr.getRecipient());
     
     String mmscode = mr.getStatus();
     
     String gmhid = "MMS-MH" + reqid;
     
 
 
     if (this.mmsReportDataMap.get(gmhid) != null)
     {
       MT_MMHttpSPMessage_Report mt = (MT_MMHttpSPMessage_Report)this.mmsReportDataMap.get(gmhid);
       String mtmessageid = mt.getMessageid();
       String sequence = mt.getSequence();
       String spid = mt.getSpid();
       String sendnumber = "";
       
 
       logger.info("mmsmr status report transfer[gmsgid:" + mt.getGlobalMessageid() + ",mtmessageid:" + mtmessageid + ",gmrid :" + gmsgid + ",phone:" + phone + ",sequence:" + sequence + ",mmscode:" + mmscode + "]");
       
       mr.setServiceCode(mt.getSp_productid());
       mr.setGlobalMessageid(mt.getGlobalMessageid());
       mr.setReportUrl(mt.getGlobalReportUrl());
       mr.setContent(StringUtil.getReportString(String.valueOf(new Random().nextInt()), sendnumber, phone, StringUtil.getTime0800(), mtmessageid, mmscode));
       this.messageRouter.doRouter(mr);
       
 
       if ((sequence != null) && (!sequence.trim().equals("")))
       {
         logger.info("mmsmt on demand vac auth(6) request[gmsgid:" + mt.getSourceGlobalMessageid() + ",phone:" + phone + ",sequence:" + sequence + ",sp_productid:" + mt.getSp_productid() + ",spid:" + spid + ",serviceid" + mt.getServiceid() + "]");
         
         CheckRequest cr6 = new CheckRequest(phone, mt.getSp_productid(), spid, mt.getServiceid(), sequence, "6", null);
         cr6.setGlobalMessageid(mt.getSourceGlobalMessageid());
         cr6.setResponse(false);
         
         this.messageRouter.doRouter(cr6);
       }
       
 
       removeReportMessage(gmhid);
     }
     else
     {
       logger.info("mmsmr status report match failure[gmsgid:" + gmsgid + ",matchkey:" + gmhid + ",reqid:" + reqid + "]");
     }
     
 
 
     try
     {
       long btime = System.currentTimeMillis();
       
 
       this.daoUtil.getDemandDao().updateSpMMSSendRecord("5", reqid, mmscode);
       
 
 
 
       logger.info("mmsmr save mr history success[gmsgid:" + gmsgid + ",reqid:" + reqid + "mmsCode:" + mmscode + ",exectime:" + (System.currentTimeMillis() - btime) + "]");
     }
     catch (Exception ex)
     {
       logger.error("mmsmr report status update", ex);
     }
   }
   

   public void doMT(MT_SPMMHttpMessage msg)
   {
	   
   }
   
   public void doCR(WOCheckResponse rsp)
   {
     String gmsgid = rsp.getWoRequest().getGlobalMessageid();
     
 
     if (this.dataMap.get(gmsgid) == null)
     {
       logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response match key failure[gmsgid:" + gmsgid + "]");
       return;
     }
     
     AbstractMessage msg = (AbstractMessage)this.dataMap.get(gmsgid);
     int rspCode = Integer.parseInt(rsp.getReturnStr());
     
 
     if ((msg instanceof MT_MMHttpSPMessage))
     {
       MT_MMHttpSPMessage mmsmt = (MT_MMHttpSPMessage)msg;
       
       String messageid = mmsmt.getMessageid();
       
 
 
       if (20000 == rspCode)
       {
         logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response success[gmsgid:" + gmsgid + ",phone:" + mmsmt.getPhone() + ",result:" + (rspCode == 20000 ? "success" : "failue") + "]");
         
         this.messageRouter.doRouter(mmsmt);
       }
       else
       {
         logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response failure[gmsgid:" + gmsgid + ",phone:" + mmsmt.getPhone() + ",code:" + rspCode + ",messageid:" + messageid + "]");
         
 
 
//         this.daoUtil.getSmsDAO().updateMmsGrsCode("4", messageid, rspCode);
         
         recycleMessage(gmsgid);
       }
     }
     else
     {
       logger.info("mmsmt wo auth(" + rsp.getReqType() + ") response match class error[gmsgid:" + gmsgid + ",classtype:" + msg.getClass().getName() + "]");
     }
   }
   

   public void doCR(CheckResponse rsp)
   {
	   
   }
   
   void recycleMessage(String msgid)
   {
     try
     {
       this.dataMap.remove(msgid);
       
       logger.info("recycleMessage msgid : " + msgid);
     }
     catch (Exception ex)
     {
       logger.error("recycleMessage msgid : " + msgid + "\t" + ex.getMessage());
     }
   }
   
   public void removeReportMessage(String msgid)
   {
     try
     {
       this.mmsReportDataMap.remove(msgid);
       logger.info("removeReportMessage msgid : " + msgid);
     }
     catch (Exception ex) {
       logger.error("removeReportMessage msgid : " + msgid + "\t" + ex.getMessage());
     }
   }
   
   void sendSmsMessage(MT_SMMessage sms)
   {
     try
     {
       logger.info("mmsmo short message service[gmsgid:" + sms.getGlobalMessageid() + ",phone:" + sms.getRcvAddresses()[0] + ",text:" + sms.getSmsText() + "]");
       
       this.messageRouter.doRouter(sms);
     }
     catch (Exception localException) {}
   }
   
 
 
 
 
   void saveMmsmoHistory(MO_MMDeliverSPMessage mmsmo, String sCorrect)
   {
     try
     {
       long btime = System.currentTimeMillis();
       
       MO_MMDeliverMessage msg = new MO_MMDeliverMessage();
       msg.setSender(mmsmo.getSender());
       String sendto = mmsmo.getTo().get(0).toString();
       sendto = sendto.replace("[", "");
       sendto = sendto.replace("]", "");
       int isCorrect = Integer.parseInt(sCorrect);
       
       this.daoUtil.getLogDao().saveMoMMSMsg(msg, sendto, Integer.valueOf(isCorrect));
       
       logger.info("mmsmo save mo history success[gmsgid:" + mmsmo.getGlobalMessageid() + ",phone:" + mmsmo.getSender() + ",exectime:" + (System.currentTimeMillis() - btime) + "]");
     }
     catch (Exception ex)
     {
       logger.error("mmsmo save mo history exception", ex);
     }
   }
   
 
   public MT_MMHttpSPMessage_Report getMMHTttpSpMessageReport(MT_MMHttpSPMessage mt)
   {
     MT_MMHttpSPMessage_Report mtmr = new MT_MMHttpSPMessage_Report();
     mtmr.setGlobalMessageid(mt.getGlobalMessageid());
     mtmr.setGlobalMessageTime(System.currentTimeMillis());
     mtmr.setServiceid(mt.getServiceid());
     mtmr.setMessageid(mt.getMessageid());
     mtmr.setGlobalReportUrl(mt.getGlobalReportUrl());
     mtmr.setGlobalCreateTime(mt.getGlobalCreateTime());
     mtmr.setSourceGlobalMessageid(mt.getGlobalMessageid());
     mtmr.setSequence(mt.getSequence());
     mtmr.setReqId(mt.getReqId());
     
     mtmr.setSpid(new String(mt.getSpid().toCharArray()));
     mtmr.setSp_productid(new String(mt.getSp_productid().toCharArray()));
     
     return mtmr;
   }
   
   public MT_MMHttpSPMessage getMMHTttpSpMessage(MT_MMHttpSPMessage msg)
   {
     MT_MMHttpSPMessage mt = new MT_MMHttpSPMessage();
     
     mt.setGlobalMessageid(msg.getGlobalMessageid());
     mt.setSequence(msg.getSequence());
     
 
     mt.setSpid(new String(msg.getSpid().toCharArray()));
     mt.setSp_productid(new String(msg.getSp_productid().toCharArray()));
     
     mt.setGlobalReportUrl(msg.getGlobalReportUrl());
     mt.setServiceid(msg.getServiceid());
     mt.setMessageid(msg.getMessageid());
     mt.setGlobalMessageTime(System.currentTimeMillis());
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     return mt;
   }
   
   public void setMessageRouter(IMessageRouter messageRouter)
   {
     this.messageRouter = messageRouter;
   }
   
   public void setDaoUtil(DaoUtil daoUtil) { this.daoUtil = daoUtil; }
   
   public void setDataCache(DataCache dataCache)
   {
     this.dataCache = dataCache;
   }
   
   public void setDataMap(ConcurrentHashMap<String, AbstractMessage> dataMap) {
     this.dataMap = dataMap;
   }
   
   public void setCommonMap(ConcurrentHashMap<String, AbstractMessage> commonMap) {
     this.commonMap = commonMap;
   }
   
 
   public void setMmsDBListener(MmsDBListener mmsDBListener)
   {
     this.mmsDBListener = mmsDBListener;
   }
   
   public void setMmsUpdateListener(MmsUpdateListener mmsUpdateListener) {
     this.mmsUpdateListener = mmsUpdateListener;
   }
   
   public void setMmsReportDataMap(ConcurrentHashMap<String, MT_MMHttpSPMessage_Report> mmsReportDataMap) {
     this.mmsReportDataMap = mmsReportDataMap;
   }
 }





