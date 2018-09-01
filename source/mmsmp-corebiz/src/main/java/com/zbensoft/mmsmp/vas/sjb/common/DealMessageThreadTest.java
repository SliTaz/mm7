 package com.zbensoft.mmsmp.vas.sjb.common;
 
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientTcpImpl;
 import junit.framework.TestCase;
 
 public class DealMessageThreadTest
   extends TestCase
 {
   public void testDealMoMessageThread()
   {
     MessageQueueClientTcpImpl client = new MessageQueueClientTcpImpl("172.16.5.253", 8890);
     MO_SMMessage mo_SMMessage = new MO_SMMessage();
     mo_SMMessage.setSendAddress("18907891001");
     mo_SMMessage.setLinkId("1234567890");
     mo_SMMessage.setSmsText("DB");
     mo_SMMessage.setVaspId("35100163");
     mo_SMMessage.setSourceType(1);
     mo_SMMessage.setVasId("3510016210688888");
     int result = client.send(mo_SMMessage);
     assertEquals(0, result);
   }
 }


