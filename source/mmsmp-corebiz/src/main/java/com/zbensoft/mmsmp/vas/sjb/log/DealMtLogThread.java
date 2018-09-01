 package com.zbensoft.mmsmp.vas.sjb.log;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
 import com.zbensoft.mmsmp.vas.sjb.common.DealMessageThread;
 import com.zbensoft.mmsmp.vas.sjb.data.LogDao;
 import java.util.Map;
 import java.util.Map.Entry;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
 
 
 
 
 
 
 
 public class DealMtLogThread
   implements Runnable
 {
   private static final Log logger = LogFactory.getLog(DealMessageThread.class);
   private Map<String, AbstractMessage> logMap;
   private LogDao logDao;
   
   public DealMtLogThread(Map<String, AbstractMessage> logMap, LogDao logDao)
   {
     this.logMap = logMap;
     this.logDao = logDao;
   }
   
 
 
   public void run()
   {
     logger.info("begin Deal LogMessage !");
     for (;;) {
       AbstractMessage message = null;
       try
       {
         for (Map.Entry<String, AbstractMessage> entry : this.logMap.entrySet()) {
           message = (AbstractMessage)entry.getValue();
           if ((message instanceof MT_ReportMessage)) {
             logger.info("this is mt_report message... keyvalue: " + (String)entry.getKey());
 
           }
           else if ((message instanceof MO_ReportMessage)) {
             logger.info("this is mo_report message...keyvlue:" + (String)entry.getKey());
             this.logDao.update(message);
           }
           else if ((message instanceof MT_MMMessage)) {
             logger.info("this is MT_MMMessage message...keyvlue:" + (String)entry.getKey());
           }
           
           this.logMap.remove(entry.getKey());
         }
       } catch (Exception ex) {
         ex.printStackTrace();
         try {
           Thread.sleep(1000L);
         }
         catch (Exception e) {
           e.printStackTrace();
         } 
         continue;  
       }
      
       try
       {
         Thread.sleep(1000L);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
     }
   }
   
   public void setLogDao(LogDao logDao) {
     this.logDao = logDao;
   }
 }





