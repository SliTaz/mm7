 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
 import com.zbensoft.mmsmp.common.ra.common.util.ApplicationContextFactory;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class LoadUserOrderDaoTest
   extends TestCase
 {
   public void test2()
   {
     LoadUserOrderDao loadUserOrderDao = (LoadUserOrderDao)ApplicationContextFactory.getBean("loadUserOrderDao");
     
     List<UserOrder> userInfoList = loadUserOrderDao.db2ProcTest();
     
     for (UserOrder user : userInfoList) {
       System.out.println(user.getId().getCellphonenumber());
     }
     System.out.println(userInfoList.size());
   }
 }





