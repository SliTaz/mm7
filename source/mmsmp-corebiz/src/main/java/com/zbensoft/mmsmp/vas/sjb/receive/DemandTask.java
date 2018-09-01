 package com.zbensoft.mmsmp.vas.sjb.receive;
 
 import com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest;
 import com.zbensoft.mmsmp.common.ra.aaa.util.CheckRequest;
 import com.zbensoft.mmsmp.common.ra.aaa.util.CheckResponse;
 import com.zbensoft.mmsmp.common.ra.aaa.util.CheckXml;
 import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
 import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.AnswerRecord;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.ConfirmPriceMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
 import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDto;
 import com.zbensoft.mmsmp.common.ra.utils.DianBo;
 import com.zbensoft.mmsmp.common.ra.utils.Sender;
 import com.zbensoft.mmsmp.vas.sjb.common.Task;
 import com.zbensoft.mmsmp.vas.sjb.data.DemandDao;
 import com.zbensoft.mmsmp.vas.sjb.data.DemandDto;
 import com.zbensoft.mmsmp.vas.sjb.data.LogDao;
 import com.zbensoft.mmsmp.vas.sjb.data.Userservhistory;
 import com.zbensoft.mmsmp.vas.sjb.messagehandler.SendNotificationMessageHandler;
 import java.io.BufferedOutputStream;
 import java.io.ByteArrayInputStream;
 import java.io.DataInputStream;
 import java.io.DataOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
 import java.util.Date;
 import java.util.List;
 import java.util.Random;
 import java.util.TimeZone;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class DemandTask
   implements Task
 {
   private static final Logger log = Logger.getLogger(DemandTask.class);
   
   private LogDao logDao;
   
   private DemandDao demandDao;
   
   private CorebizConfig config = ConfigUtil.getInstance().getCorebizConfig();
   
   private MO_SMMessage msg;
   
   private MessageQueue MT_queue;
   
   private SendNotificationMessageHandler sendNotificationMessageHandler;
   
   private final String SERVICEN_NUMBER = "10655565";
   
   public MessageQueue getMT_queue() {
     return this.MT_queue;
   }
   
   public void setMT_queue(MessageQueue mt_queue) { this.MT_queue = mt_queue; }
   
   public void setLogDao(LogDao logDao) {
     this.logDao = logDao;
   }
   
 
 
 
   public void doTask(AbstractMessage message)
   {
     if ((message instanceof MO_SMMessage)) {
       this.msg = ((MO_SMMessage)message);
       int dealType = this.config.getDealDemand();
       if (dealType == 2) {
         if (message.getSourceType() == 2)
         {
 
 
           String codeAnswerDelim = ConfigUtil.getInstance().getCorebizConfig().getCodeAnswerDelim();
           String moText = this.msg.getSmsText();
           if (moText.indexOf(codeAnswerDelim) != -1) {
             String code = moText.split(codeAnswerDelim)[0];
             Object[] results = this.demandDao.judgeAnswer(this.msg.getSendAddress(), moText, codeAnswerDelim);
             Integer isActivityNum = (Integer)results[0];
             boolean isActivityCode = isActivityNum.intValue() == 1;
             if (isActivityCode)
             {
               AnswerRecord answer = (AnswerRecord)results[1];
               
               boolean isAnsweredTwice = this.demandDao.isReplied4Twice(this.msg.getSendAddress(), code, codeAnswerDelim, answer.getBeginDate(), answer.getEndDate());
               
               if (isAnsweredTwice)
               {
 
                 Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
                 String sms4AnsweredTwice = ConfigUtil.getInstance().getCorebizConfig().getSms4AnswerTwice();
                 SendNotificationMessage notifyMsg = new SendNotificationMessage();
                 String contentId = sms4AnsweredTwice;
                 log.info("you answered more than once :  msg contentid : " + contentId);
                 notifyMsg.setServiceCode(this.msg.getServiceCode());
                 notifyMsg.setContentId(Integer.parseInt(contentId));
                 notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                 notifyMsg.setLinkId(this.msg.getLinkId());
                 notifyMsg.setMtType("5");
                 notifyMsg.setSendType(1);
                 notifyMsg.setServiceType(1);
                 this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                 
                 this.logDao.saveMoSmsMsg(message, null);
               }
               else
               {
                 boolean isAnswerRight = isAnswerRight(answer, moText);
                 
                 if (isAnswerRight)
                 {
 
                   boolean existUserOrderRelation = this.demandDao.existUserOrderRelation(this.msg.getSendAddress(), answer.getServiceId());
                   if (existUserOrderRelation)
                   {
 
                     SendNotificationMessage notifyMsg = new SendNotificationMessage();
                     notifyMsg.setServiceCode(this.msg.getServiceCode());
                     String contentId = ConfigUtil.getInstance().getCorebizConfig().getSms4RightAnswer();
                     notifyMsg.setContentId(Integer.parseInt(contentId));
                     notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                     notifyMsg.setLinkId(this.msg.getLinkId());
                     notifyMsg.setMtType("5");
                     notifyMsg.setSendType(1);
                     notifyMsg.setServiceType(1);
                     log.info("answer correct: step 1-- send sms to inform user  and contentid = " + contentId);
                     this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                     
 
                     Vasservice vasservice = this.demandDao.queryProductByServiceId(answer.getServiceId());
                     notifyMsg.setServiceCode(vasservice.getServicecode());
                     notifyMsg.setContentId(answer.getContentId());
                     notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                     notifyMsg.setLinkId(this.msg.getLinkId());
                     notifyMsg.setMtType("0");
                     notifyMsg.setSendType(1);
                     notifyMsg.setServiceType(1);
                     log.info("answer correct : step 2--send reward(mms) to user ");
                     this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                     this.demandDao.insertUserOrderHistory(this.msg, vasservice);
                   }
                   this.logDao.saveMoSmsMsg(message, Integer.valueOf(1));
                 } else {
                   Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
                   String sms4WrongAnswer = ConfigUtil.getInstance().getCorebizConfig().getSms4WrongAnswer();
                   SendNotificationMessage notifyMsg = new SendNotificationMessage();
                   String contentId = sms4WrongAnswer;
                   notifyMsg.setServiceCode(this.msg.getServiceCode());
                   notifyMsg.setContentId(Integer.parseInt(contentId));
                   notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                   notifyMsg.setLinkId(this.msg.getLinkId());
                   notifyMsg.setMtType("5");
                   notifyMsg.setSendType(1);
                   notifyMsg.setServiceType(1);
                   log.info("answer is not correct : send sms to user: " + contentId);
                   this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                   this.demandDao.insertUserOrderHistory(this.msg, vasservice);
                   this.logDao.saveMoSmsMsg(message, Integer.valueOf(0));
                 }
               }
             } else {
               Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
               if (vasservice != null) {
                 ContentInfo contentInfo = this.demandDao.queryContentInfoByProductID(vasservice.getUniqueid());
                 if (contentInfo != null) {
                   SendNotificationMessage notifyMsg = new SendNotificationMessage();
                   notifyMsg.setContentId(contentInfo.getContentid().intValue());
                   notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                   notifyMsg.setServiceCode(vasservice.getServicecode());
                   notifyMsg.setSendType(1);
                   notifyMsg.setMtType("5");
                   notifyMsg.setServiceType(1);
                   notifyMsg.setLinkId(this.msg.getLinkId());
                   log.info("this isn't interactive mosms");
                   this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                   this.demandDao.insertUserOrderHistory(this.msg, vasservice);
                   this.logDao.saveMoSmsMsg(message, null);
                 }
               }
             }
           }
           else {
             Vasservice vasservice = this.demandDao.queryProductByDemandCode(this.msg.getSmsText());
             if (vasservice != null) {
               ContentInfo contentInfo = this.demandDao.queryContentInfoByProductID(vasservice.getUniqueid());
               if (contentInfo != null) {
                 SendNotificationMessage notifyMsg = new SendNotificationMessage();
                 notifyMsg.setContentId(contentInfo.getContentid().intValue());
                 notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
                 notifyMsg.setServiceCode(vasservice.getServicecode());
                 notifyMsg.setSendType(1);
                 notifyMsg.setMtType("5");
                 notifyMsg.setServiceType(1);
                 notifyMsg.setLinkId(this.msg.getLinkId());
                 log.info("mosms doesn't contain delimeter: " + codeAnswerDelim);
                 this.sendNotificationMessageHandler.handleMessage(notifyMsg);
                 this.demandDao.insertUserOrderHistory(this.msg, vasservice);
                 this.logDao.saveMoSmsMsg(message, null);
               }
             }
           }
         } else if (message.getSourceType() == 1) {
           this.logDao.saveMoSmsMsg(message, null);
         }
       } else if ((dealType == 1) && 
         (message.getSourceType() == 1))
       {
 
         this.logDao.saveMoSmsMsg(message, null);
       }
       
 
     }
     else if ((message instanceof MO_MMDeliverMessage))
     {
       MO_MMDeliverMessage me = (MO_MMDeliverMessage)message;
//       HttpServletRequest req = me.getRequest();
       
       String to = me.getTo().get(0).toString();
       
       String user_number = me.getSender();
       String[] strings = new String[me.getTo().size()];
       String sendto = Arrays.toString(me.getTo().toArray(strings));
       sendto = sendto.replace("[", "");
       sendto = sendto.replace("]", "");
       log.info("sendto:" + sendto);
       SendNotificationMessage notifyMsg = new SendNotificationMessage();
       notifyMsg.setSendType(100);
       
       DemandDto dto = this.demandDao.getDemandMomms(sendto);
       
       String str_phone = user_number;
       if (user_number.startsWith("+86")) { str_phone = user_number.substring(3);
       } else if (user_number.startsWith("86")) { str_phone = user_number.substring(2);
       }
       notifyMsg.setPhoneNumber(new String[] { "86" + str_phone });
       
       log.info("dto.getServiceid() : " + dto.getServiceid());
       log.info(Boolean.valueOf((dto != null) && (dto.getServiceid() != null) && (!"".equals(dto.getServiceid()))));
       if ((dto != null) && (dto.getServiceid() != null) && (!"".equals(dto.getServiceid()))) {
         String serviceid = dto.getServiceid();
         String productId = dto.getProductId();
         String sp_id = dto.getSpid();
         String sendurl = dto.getSpreporturl();
         
 
 
 
         CheckRequest crequest = new CheckRequest(str_phone, productId, sp_id, null, null, "4", null);
         CheckResponse cresponse = CheckXml.GetAAAResponse(crequest);
         
         String retruecode = cresponse.getResult_Code();
         
 
         notifyMsg.setServiceCode("10655565");
         notifyMsg.setLinkId(String.valueOf(System.currentTimeMillis()));
         
 
         if ((retruecode == null) || (retruecode.equals("")) || (retruecode.equals("null"))) {
           retruecode = "1";
           notifyMsg.setSendContent(this.demandDao.queryErrorMsgTemplate() + "系统响应超时，请稍后再试");
         }
         else
         {
//           HttpRequest request = new HttpRequest(req);
//           request.setContentType(me.getContentType());
           
           String linkid = cresponse.getLinkID();
           log.info("[user_number: \t" + str_phone + "\tsp_id:\t" + sp_id + "\t serviceid:\t" + serviceid + "\tproductid:\t" + productId + "\tserver_rt_code:\t" + cresponse.getServerResult_code() + "]");
           if ((linkid != null) && (linkid.length() > 0) && (!"NULL".equalsIgnoreCase(linkid))) {
//             request.setLinkId(linkid);
             log.info("Mo MMS get linkid : \t\t" + linkid);
             log.info("product need confirm ..\t" + dto.getNeedConfirm());
             
 
             if ("1".equals(dto.getNeedConfirm())) {
               CheckRequest checkRequest = new CheckRequest(str_phone, productId, sp_id, serviceid, null, "5", linkid);
               CheckResponse checkResponse = CheckXml.GetAAAResponse(checkRequest);
               log.info("check price : 5  \t needConfirm:\t" + checkResponse.getNeedConfirm() + "\t sequence_Number:\t" + checkResponse.getSrc_SequenceNumber());
             }
             
 
             try
             {
//               Sender.sendmsg(sendurl, request);
               String needConfirm = cresponse.getNeedConfirm();
               SmsSenderDto ssd = this.demandDao.getDianBoByNumber(to);
               
               String _msg = "";
               if (ssd.getVasid() != null) {
                 if ("1".equals(needConfirm)) {
                   _msg = this.demandDao.queryOndemandMessage();
                   _msg = _msg.replace("{0}", ssd.getServicename());
                   _msg = _msg.replace("{1}", ssd.getFee());
                 }
                 if ("0".equals(needConfirm)) {
                   _msg = this.demandDao.queryUpOndemandMessage();
                   _msg = _msg.replace("{0}", ssd.getServicename());
                   _msg = _msg.replace("{1}", ssd.getFee());
                 }
               }
               else {
                 _msg = getDemandDao().queryErrorMsgTemplate() + "你好，没有这个业务";
               }
               
               notifyMsg.setSendContent(_msg);
             }
             catch (Exception e)
             {
               notifyMsg.setSendContent(this.demandDao.queryErrorMsgTemplate() + "投递到网关失败！");
               log.error("Forward Deliver Message error:  ", e);
             }
//             catch (NullPointerException e) {
//               notifyMsg.setSendContent(this.demandDao.queryErrorMsgTemplate() + "没有这个接入号");
//               log.error("Forward Deliver Message error:  ", e);
//             }
           }
           else {
             notifyMsg.setSendContent(this.demandDao.queryErrorMsgTemplate() + "系统响应超时，请稍后再试");
             log.error("Mo MMS get linkid is null  ");
           }
         }
         this.logDao.saveMoMMSMsg(me, sendto, Integer.valueOf(Integer.parseInt(retruecode)));
       } else {
         notifyMsg.setSendContent(this.demandDao.queryErrorMsgTemplate() + "发送失败，请检查接入号是否正确！");
         
         log.info("===========error:no rows==========");
       }
       
       this.sendNotificationMessageHandler.handleMessage(notifyMsg);
     }
     else if ((message instanceof ConfirmPriceMessage)) {
       log.info("========================recevice a Confirm Price Message========================");
       ConfirmPriceMessage m = (ConfirmPriceMessage)message;
       String messageid = m.getMessageId() + m.getStrPhone();
       
       log.info("messageid:" + messageid);
       log.info("getMessageId:" + m.getMessageId());
       log.info("getRecipient:" + m.getRecipient());
       log.info("getTransactionID:" + m.getTransactionID());
       String _service_code = "";
       try {
         _service_code = this.logDao.getServicecode(messageid);
       } catch (Exception e) {
         log.info("select service code error!");
       }
       log.info("servicecode:" + _service_code);
       String sequence_num = "";
       try {
         sequence_num = DianBo.get_sequence_number(_service_code, m.getStrPhone());
       } catch (Exception e) {
         e.printStackTrace();
       }
       log.info("=============>query db for sequence number:" + sequence_num);
       
       if (sequence_num != null) {
         CheckRequest crequest = new CheckRequest(null, null, null, null, sequence_num, "6", null);
         CheckResponse cresponse = CheckXml.GetAAAResponse(crequest);
         int configstate = Integer.parseInt(cresponse.getResult_Code());
         log.info("messsageid:" + m.getMessageId() + "   Check Price Confirm:" + configstate);
       }
     }
     else if ((message instanceof MO_ReportMessage)) {
       log.info("========================MO_ReportMessage Message========================");
       MO_ReportMessage m = (MO_ReportMessage)message;
       
       dispatchMessage(m);
       
       this.demandDao.updateSpMMSSendRecord("5", m.getCorrelator());
     }
   }
   
 
 
   private void dispatchMessage(MO_ReportMessage m)
   {
     String MTTRANID = m.getCorrelator();
     log.info("=====>messageid:" + MTTRANID);
     String spreporturl = this.demandDao.getSp_url(MTTRANID);
     Userservhistory ush = this.demandDao.getUserservhistoryBymttranid(MTTRANID);
     if ((ush.getReqid() == null) || (ush.getReqid().length() == 0)) {
       log.error("XXXXXXX   mt record not exists: " + MTTRANID);
       return;
     }
     HttpURLConnection httpURL = null;
     DataInputStream dis = null;
     DataOutputStream dos = null;
     try {
       log.info("=====>spreporturl:" + spreporturl);
       String reportstr = reportStr(String.valueOf(new Random().nextInt()), ush.getServiceCode(), ush.getCellphonenumber(), getTimeStr(), ush.getMessageid());
       log.info("third report is : " + reportstr);
       URL theURL = new URL(spreporturl);
       httpURL = (HttpURLConnection)theURL.openConnection();
       httpURL.setDoInput(true);
       httpURL.setDoOutput(true);
       httpURL.setRequestProperty("content-type", "text/xml;charset=\"UTF-8\"");
       httpURL.setRequestProperty("Content-Transfer-Encoding", "8bit");
       httpURL.setRequestProperty("Connection", "keep-alive");
       httpURL.setRequestMethod("POST");
       InputStream in = new ByteArrayInputStream(reportstr.getBytes("UTF-8"));
       dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
       byte[] buf = reportstr.getBytes();
       int n = 0;
       while ((n = in.read(buf)) != -1) {
         dos.write(buf, 0, n);
       }
       dos.flush();
       log.info("=====>httpURL.getResponseCode():" + httpURL.getResponseCode());
     }
     catch (IOException e) {
       log.error("send Report to Sp Error  \t", e);
     }
     log.info("=====>end Send Report To SP!");
   }
   
   private String getTimeStr() {
     Calendar now = Calendar.getInstance();
     Date d = new Date();
     String year = String.valueOf(now.get(1));
     String date = String.valueOf(now.get(5));
     String month = String.valueOf(now.get(2) + 1);
     String hour = String.valueOf(now.get(10));
     String min = String.valueOf(now.get(12));
     String sec = String.valueOf(now.get(13));
     if (date.length() < 2) date = "0" + date;
     if (month.length() < 2) month = "0" + month;
     if (hour.length() < 2) hour = "0" + hour;
     if (min.length() < 2) min = "0" + min;
     if (sec.length() < 2) sec = "0" + sec;
     return year + "-" + month + "-" + date + "T" + hour + ":" + min + ":" + sec + "+08:00";
   }
   
   private String reportStr(String transactionid, String sendnumber, String recipientnumber, String timestamp, String messageid)
   {
	 String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">" + transactionid + "</mm7:TransactionID></env:Header><env:Body><DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender><Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp + "</TimeStamp><MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID><MMStatus>Third:User Retrieved</MMStatus><StatusText>1000</StatusText></DeliveryReportReq></env:Body></env:Envelope>";
     return report;
   }
   
   public String Ttime()
   {
     SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
     d1.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
     return d1.format(new Date());
   }
   
 
 
 
 
   private void do_interactive(AbstractMessage message)
   {
     String codeAnswerDelim = ConfigUtil.getInstance().getCorebizConfig().getCodeAnswerDelim();
     String moText = this.msg.getSmsText();
     if (moText.indexOf(codeAnswerDelim) != -1) {
       String code = moText.split(codeAnswerDelim)[0];
       Object[] results = this.demandDao.judgeAnswer(this.msg.getSendAddress(), moText, codeAnswerDelim);
       Integer isActivityNum = (Integer)results[0];
       boolean isActivityCode = isActivityNum.intValue() == 1;
       if (isActivityCode)
       {
         AnswerRecord answer = (AnswerRecord)results[1];
         
         boolean isAnsweredTwice = this.demandDao.isReplied4Twice(this.msg.getSendAddress(), code, codeAnswerDelim, answer.getBeginDate(), answer.getEndDate());
         
         if (isAnsweredTwice)
         {
           Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
           String sms4AnsweredTwice = ConfigUtil.getInstance().getCorebizConfig().getSms4AnswerTwice();
           SendNotificationMessage notifyMsg = new SendNotificationMessage();
           String contentId = sms4AnsweredTwice;
           notifyMsg.setServiceCode(vasservice.getServicecode());
           notifyMsg.setContentId(Integer.parseInt(contentId));
           notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
           notifyMsg.setLinkId(this.msg.getLinkId());
           notifyMsg.setMtType("5");
           notifyMsg.setSendType(1);
           notifyMsg.setServiceType(1);
           this.sendNotificationMessageHandler.handleMessage(notifyMsg);
           
           this.logDao.saveMoSmsMsg(message, null);
         }
         else
         {
           boolean isAnswerRight = isAnswerRight(answer, moText);
           
           if (isAnswerRight)
           {
             Vasservice vasservice = this.demandDao.queryProductByServiceId(answer.getServiceId());
             SendNotificationMessage notifyMsg = new SendNotificationMessage();
             notifyMsg.setServiceCode(vasservice.getServicecode());
             notifyMsg.setContentId(answer.getContentId());
             notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
             notifyMsg.setLinkId(this.msg.getLinkId());
             notifyMsg.setMtType("5");
             notifyMsg.setSendType(1);
             notifyMsg.setServiceType(1);
             this.sendNotificationMessageHandler.handleMessage(notifyMsg);
             this.demandDao.insertUserOrderHistory(this.msg, vasservice);
             this.logDao.saveMoSmsMsg(message, Integer.valueOf(1));
           } else {
             Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
             String sms4WrongAnswer = ConfigUtil.getInstance().getCorebizConfig().getSms4WrongAnswer();
             SendNotificationMessage notifyMsg = new SendNotificationMessage();
             String contentId = sms4WrongAnswer;
             notifyMsg.setServiceCode(this.msg.getServiceCode());
             notifyMsg.setContentId(Integer.parseInt(contentId));
             notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
             notifyMsg.setLinkId(this.msg.getLinkId());
             notifyMsg.setMtType("5");
             notifyMsg.setSendType(1);
             notifyMsg.setServiceType(1);
             this.sendNotificationMessageHandler.handleMessage(notifyMsg);
             this.demandDao.insertUserOrderHistory(this.msg, vasservice);
             this.logDao.saveMoSmsMsg(message, Integer.valueOf(0));
           }
         }
       } else {
         Vasservice vasservice = this.demandDao.queryProductByDemandCode(code);
         if (vasservice != null) {
           ContentInfo contentInfo = this.demandDao.queryContentInfoByProductID(vasservice.getUniqueid());
           if (contentInfo != null) {
             SendNotificationMessage notifyMsg = new SendNotificationMessage();
             notifyMsg.setContentId(contentInfo.getContentid().intValue());
             notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
             notifyMsg.setServiceCode(vasservice.getServicecode());
             notifyMsg.setSendType(1);
             notifyMsg.setMtType("5");
             notifyMsg.setServiceType(1);
             notifyMsg.setLinkId(this.msg.getLinkId());
             this.sendNotificationMessageHandler.handleMessage(notifyMsg);
             this.demandDao.insertUserOrderHistory(this.msg, vasservice);
             this.logDao.saveMoSmsMsg(message, null);
           }
         }
       }
     }
     else {
       Vasservice vasservice = this.demandDao.queryProductByDemandCode(this.msg.getSmsText());
       if (vasservice != null) {
         ContentInfo contentInfo = this.demandDao.queryContentInfoByProductID(vasservice.getUniqueid());
         if (contentInfo != null) {
           SendNotificationMessage notifyMsg = new SendNotificationMessage();
           notifyMsg.setContentId(contentInfo.getContentid().intValue());
           notifyMsg.setPhoneNumber(new String[] { this.msg.getSendAddress() });
           notifyMsg.setServiceCode(vasservice.getServicecode());
           notifyMsg.setSendType(1);
           notifyMsg.setMtType("5");
           notifyMsg.setServiceType(1);
           notifyMsg.setLinkId(this.msg.getLinkId());
           this.sendNotificationMessageHandler.handleMessage(notifyMsg);
           this.demandDao.insertUserOrderHistory(this.msg, vasservice);
           this.logDao.saveMoSmsMsg(message, null);
         }
       }
     }
   }
   
   public DemandDao getDemandDao() { return this.demandDao; }
   
   public void setDemandDao(DemandDao demandDao) {
     this.demandDao = demandDao;
   }
   
   public SendNotificationMessageHandler getSendNotificationMessageHandler() { return this.sendNotificationMessageHandler; }
   
   public void setSendNotificationMessageHandler(SendNotificationMessageHandler sendNotificationMessageHandler) {
     this.sendNotificationMessageHandler = sendNotificationMessageHandler;
   }
   
 
   private boolean isAnswerRight(AnswerRecord answer, String moText)
   {
     if ((compareDate(answer.getBeginDate(), new Date()) <= 0) && 
       (compareDate(new Date(), answer.getEndDate()) <= 0))
     {
 
       if (answer.getAnswer().equalsIgnoreCase(moText.trim())) {
         return true;
       }
     }
     return false;
   }
   
 
 
 
 
 
 
 
   private int compareDate(Date date1, Date date2)
   {
     String format = "yyyy-MM-dd";
     DateFormat df = new SimpleDateFormat(format);
     return df.format(date1).compareTo(df.format(date2));
   }
   
 
 
 
   private SendNotificationMessage populate(int contentId, String[] phoneNumbers, String serviceCode, int sendType, String mtType, int serviceType, String linkId)
   {
     SendNotificationMessage notifyMsg = new SendNotificationMessage();
     notifyMsg.setContentId(contentId);
     notifyMsg.setPhoneNumber(phoneNumbers);
     notifyMsg.setServiceCode(serviceCode);
     notifyMsg.setSendType(sendType);
     notifyMsg.setMtType(mtType);
     notifyMsg.setServiceType(serviceType);
     notifyMsg.setLinkId(linkId);
     return notifyMsg;
   }
 }





