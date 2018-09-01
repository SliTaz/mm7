 package com.zbensoft.mmsmp.corebiz.handle.impl;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.corebiz.app.AppContextFactory;
 import org.junit.Assert;
 import org.junit.Before;
 import org.junit.Test;
 import org.springframework.context.ApplicationContext;
 
 
 
 public class SmsBusinessHandlerImplTest
 {
   final String SHORT_VASID = "10655565";
   final String LONG_VASID = "1065556500101";
   final String SP_VASID = "10668888";
   final String ERROR_VASID = "1065556501";
   SmsBusinessHandlerImpl smsBusiness;
   
   @Before
   public void setUp() throws Exception { this.smsBusiness = ((SmsBusinessHandlerImpl)AppContextFactory.getApplicationContext().getBean("smsBusinessHandlerImpl")); }
   
 
 
   @Test
   public void testDoHandler()
   {
     this.smsBusiness.doHandler(createMessage("Y", "1065556553001", "18601106635", 1));
   }
   
 
 
 
 
 
 
 
 
   private AbstractMessage createMessage(String smsText, String vasId, String sendAddress, int type)
   {
     MO_SMMessage mo_sms = new MO_SMMessage();
     switch (type) {
     case 1: 
       mo_sms.setSmsText(smsText);
       mo_sms.setVasId(vasId);
       mo_sms.setSendAddress(sendAddress);
       mo_sms.setGlobalMessageid("11111");
       
       return mo_sms;
     }
     
     return mo_sms;
   }
   
 
 
   public void testGetClientMessage()
   {
     Assert.fail("Not yet implemented");
   }
   
   public void testSetMessageRouter()
   {
     Assert.fail("Not yet implemented");
   }
   
   public void testSetDaoUtil()
   {
     Assert.fail("Not yet implemented");
   }
   
   public void testSetDataCache()
   {
     Assert.fail("Not yet implemented");
   }
   
   public void testSetDataMap()
   {
     Assert.fail("Not yet implemented");
   }
 }





