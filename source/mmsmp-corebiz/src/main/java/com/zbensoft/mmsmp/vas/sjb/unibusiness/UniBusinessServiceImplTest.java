 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import junit.framework.TestCase;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class UniBusinessServiceImplTest
   extends TestCase
 {
   public void testReverseUnsubscribeManageRequest()
   {
     UniBusinessServiceClient client = new UniBusinessServiceClient();
     UniBusiness businessClient = client.getUniBusinessService("http://localhost:8080/UniBusiness");
     ReverseUnsubscribeManageRequest request = new ReverseUnsubscribeManageRequest();
     Response response = businessClient.reverseUnsubscribeManage(request);
     String result = response.getReturnCode();
     assertEquals("0", result);
   }
 }





