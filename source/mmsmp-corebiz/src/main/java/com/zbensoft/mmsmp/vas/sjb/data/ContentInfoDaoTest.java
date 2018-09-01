 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
 import com.zbensoft.mmsmp.common.ra.common.util.ApplicationContextFactory;
 import java.io.PrintStream;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 import junit.framework.TestCase;
 
 
 
 
 
 
 
 
 
 public class ContentInfoDaoTest
   extends TestCase
 {
   public void testgetLatestContentList()
   {
     ContentInfoDAO contentInfoDAO = (ContentInfoDAO)ApplicationContextFactory.getBean("contentInfoDAO");
     
 
     List<ContentInfo> list = contentInfoDAO.getLatestContentList(9633792, false);
     ContentInfo localContentInfo;
     for (Iterator localIterator = list.iterator(); localIterator.hasNext(); localContentInfo = (ContentInfo)localIterator.next()) {}
   }
   
 
 
 
   public void testgetNeedSendContentList()
   {
     ContentInfoDAO contentInfoDAO = (ContentInfoDAO)ApplicationContextFactory.getBean("contentInfoDAO");
     
 
     List<ContentInfo> list = contentInfoDAO.getNeedSendContentList(9633792, true, false);
     
     for (ContentInfo cont : list) {
       System.out.println(cont);
     }
   }
   
 
   public void testupdateFlag()
   {
     ContentInfoDAO contentInfoDAO = (ContentInfoDAO)ApplicationContextFactory.getBean("contentInfoDAO");
     
     List<ContentInfo> contlist = new LinkedList();
     
     ContentInfo content = new ContentInfo();
     content.setContentid(Integer.valueOf(7438336));
     
     contlist.add(content);
     
     ContentInfo content1 = new ContentInfo();
     content1.setContentid(Integer.valueOf(12648448));
     
     contlist.add(content1);
     
     boolean result = contentInfoDAO.updateFlag(contlist, "0");
     
     assertTrue(result);
   }
 }





