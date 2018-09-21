 package com.zbensoft.mmsmp.corebiz.handle.impl;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
 import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import org.apache.log4j.Logger;
 
 
 public class SendNotifyMessageHandlerimpl
 {
   static final Logger logger = Logger.getLogger(SendNotifyMessageHandlerimpl.class);
   IMessageRouter messageRouter;
   
   public void doHandler(AbstractMessage message) { 
	   SendNotificationMessage msg = (SendNotificationMessage)message;
     if ((message instanceof SendNotificationMessage)) {
       for (int i = 0; i < msg.getPhoneNumber().length; i++) {
         MT_SMMessage mtsmm = new MT_SMMessage();
         mtsmm.setGlobalMessageid(MT_SMMessage.generateUUID("admin-sms"));
         mtsmm.setSmsText(msg.getSendContent());
         mtsmm.setRcvAddresses(new String[] { msg.getPhoneNumber()[i] });
         this.messageRouter.doRouter(mtsmm);
         COREBIZ_LOG.INFO("[received  test send sms MT_SMMessage globalMessageid:" + mtsmm.getGlobalMessageid() + " phonenumber" + msg.getPhoneNumber()[i] + "]");
       }
     }
   }
   
   public void setMessageRouter(IMessageRouter messageRouter)
   {
     this.messageRouter = messageRouter;
   }
 }





