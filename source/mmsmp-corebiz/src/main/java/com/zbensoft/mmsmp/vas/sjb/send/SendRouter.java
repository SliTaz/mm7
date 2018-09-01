 package com.zbensoft.mmsmp.vas.sjb.send;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MEMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_WapPushMessage;
 import com.zbensoft.mmsmp.common.ra.common.send.SendChannel;
 import com.zbensoft.mmsmp.vas.sjb.common.Router;
 import com.zbensoft.mmsmp.vas.sjb.common.Task;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class SendRouter
   implements Router
 {
   private static final Logger log = Logger.getLogger(SendRouter.class);
   
 
   private Task logTask;
   
   private AbstractMessage reportMsg = null;
   
   private SendChannel mobileSendChannel;
   
   private SendChannel telecomSendChannel;
   
   private SendChannel unicomSendChannel;
   
 
   public void doRouter(AbstractMessage message)
   {
     if (message == null) {
       return;
     }
     if ((message instanceof MT_MMMessage)) {
       if (message.getOperatorsType() == 1) {
         this.reportMsg = this.mobileSendChannel.sendMMS(message);
       } else if (message.getOperatorsType() == 2) {
         this.reportMsg = this.telecomSendChannel.sendMMS(message);
       } else if (message.getOperatorsType() == 3) {
         this.reportMsg = this.unicomSendChannel.sendMMS(message);
         log.info("<<<<<<<<<<<<<send liantong");
       }
       this.logTask.doTask(this.reportMsg);
       return; }
     if ((message instanceof MT_MEMessage)) {
       this.reportMsg = this.telecomSendChannel.sendMME(message);
       this.logTask.doTask(this.reportMsg);
       return; }
     if ((message instanceof MT_SMMessage)) {
       if (message.getOperatorsType() == 1) {
         this.reportMsg = this.mobileSendChannel.sendSMS(message);
       } else if (message.getOperatorsType() == 2) {
         this.reportMsg = this.telecomSendChannel.sendSMS(message);
       } else if (message.getOperatorsType() == 3) {
         this.reportMsg = this.unicomSendChannel.sendSMS(message);
       }
       this.logTask.doTask(this.reportMsg);
       return; }
     if ((message instanceof MT_WapPushMessage)) {
       if (message.getOperatorsType() == 1) {
         this.reportMsg = this.mobileSendChannel.sendWAP(message);
       } else if (message.getOperatorsType() == 2) {
         this.reportMsg = this.telecomSendChannel.sendWAP(message);
       } else if (message.getOperatorsType() == 3) {
         this.reportMsg = this.unicomSendChannel.sendWAP(message);
       }
       this.logTask.doTask(this.reportMsg);
       return; }
     if ((message instanceof MT_MMHttpMessage)) {
       if (message.getOperatorsType() == 1) {
         this.reportMsg = this.mobileSendChannel.sendHttpMMS(message);
       } else if (message.getOperatorsType() == 2) {
         this.reportMsg = this.telecomSendChannel.sendHttpMMS(message);
       } else if (message.getOperatorsType() == 3) {
         this.reportMsg = this.unicomSendChannel.sendHttpMMS(message);
       }
       this.logTask.doTask(this.reportMsg);
     } else if ((message instanceof MT_SPMMHttpMessage))
     {
       if (message.getOperatorsType() == 1) {
         this.reportMsg = this.mobileSendChannel.doHandleSPMMS(message);
       } else if (message.getOperatorsType() == 2) {
         this.reportMsg = this.telecomSendChannel.doHandleSPMMS(message);
       } else if (message.getOperatorsType() == 3) {
         try {
           this.reportMsg = this.unicomSendChannel.doHandleSPMMS(message);
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
       this.logTask.doTask(this.reportMsg);
     }
   }
   
   public Task getLogTask() {
     return this.logTask;
   }
   
   public void setLogTask(Task logTask) {
     this.logTask = logTask;
   }
   
   public SendChannel getMobileSendChannel() {
     return this.mobileSendChannel;
   }
   
   public void setMobileSendChannel(SendChannel mobileSendChannel) {
     this.mobileSendChannel = mobileSendChannel;
   }
   
   public SendChannel getTelecomSendChannel() {
     return this.telecomSendChannel;
   }
   
   public void setTelecomSendChannel(SendChannel telecomSendChannel) {
     this.telecomSendChannel = telecomSendChannel;
   }
   
   public SendChannel getUnicomSendChannel() {
     return this.unicomSendChannel;
   }
   
   public void setUnicomSendChannel(SendChannel unicomSendChannel) {
     this.unicomSendChannel = unicomSendChannel;
   }
 }





