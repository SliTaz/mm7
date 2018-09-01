 package com.zbensoft.mmsmp.vas.sjb.messagehandler;
 
 import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.notifymessageclient.NotifyMessageClientTcpImpl;
 import junit.framework.TestCase;
 public class SendNotificationMessageHandlerTest
   extends TestCase
 {
   public void testHandleMessage()
   {
     SendNotificationMessage notifyMsg = new SendNotificationMessage();
     notifyMsg.setContentId(14909441);
     notifyMsg.setPhoneNumber(new String[] { "18907891001" });
     notifyMsg.setProvince(new String[] { "02" });
     notifyMsg.setSendType(100);
     notifyMsg.setServiceCode("135000000000000000162");
     notifyMsg.setStatus(new String[] { "1", "6" });
     notifyMsg.setMtType("1");
     NotifyMessageClientTcpImpl notifyMsgClient = new NotifyMessageClientTcpImpl("localhost", 8888);
     int result = notifyMsgClient.send(notifyMsg);
     assertEquals(0, result);
   }
 }





