 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtMode;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.util.ApplicationContextFactory;
 import java.io.PrintStream;
 import java.util.List;
 import junit.framework.TestCase;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
 
 
 
 
 
 
 
 public class BusinessManageDaoTest
   extends TestCase
 {
   private static final Log logger = LogFactory.getLog(BusinessManageDaoTest.class);
   
 
 
 
   public void testfindAllServMod()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     List<ServMtMode> list = businessManageDAO.findAllServMod();
     
     for (ServMtMode mod : list) {
       System.out.println(mod);
     }
   }
   
 
 
 
 
   public void testfindNormalStatus()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     List<Vasservice> list = businessManageDAO.findNormalStatus();
     
     for (Vasservice service : list) {
       System.out.println(service);
     }
   }
   
 
 
 
 
   public void testgetALLProvince()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     List<String> list = businessManageDAO.getALLProvince();
     
     for (String service : list) {
       System.out.println(service);
     }
   }
   
 
 
 
 
   public void testgetContentStatus()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     String status = businessManageDAO.getContentStatus(Integer.valueOf(7438336));
     assertTrue(status.equalsIgnoreCase("5"));
   }
   
 
 
 
   public void testupdateProductDealStatus()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     boolean dealstatus = businessManageDAO.updateProductDealStatus(Integer.valueOf(1), Integer.valueOf(9633795));
     assertTrue(dealstatus);
   }
   
 
 
 
 
   public void testgetDealStatus()
   {
     BusinessManageDAO businessManageDAO = (BusinessManageDAO)ApplicationContextFactory.getBean("businessManageDAO");
     
     Integer dealstatus = businessManageDAO.getDealStatus(Integer.valueOf(9633795));
     assertTrue(dealstatus.intValue() == 1);
   }
 }





