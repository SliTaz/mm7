 package com.zbensoft.mmsmp.corebiz.task;
 
 import com.zbensoft.mmsmp.corebiz.cache.CacheCenter;
 import com.zbensoft.mmsmp.corebiz.cache.DataMemory;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class RefreshTask
 {
   static final Logger logger = Logger.getLogger(RefreshTask.class);
   CacheCenter cacheCenter;
   long smsMemoryKeep;
   long mmsMemoryKeep;
   long uniMemoryKeep;
   long commonMemoryKeep;
   
   
   public void doRefreshDataCache()
   {

   }
   
   public void doRefreshDataMemory()
   {
     execCleanDataMap(this.cacheCenter.getDataMemory().getSmsDataMap(), this.smsMemoryKeep, "sms data map");
     execCleanDataMap(this.cacheCenter.getDataMemory().getMmsDataMap(), this.mmsMemoryKeep, "mms data map");
     execCleanDataMap(this.cacheCenter.getDataMemory().getUniDataMap(), this.uniMemoryKeep, "uni data map");
     execCleanDataMap(this.cacheCenter.getDataMemory().getCommonDataMap(), this.commonMemoryKeep, "common data map");
     removeReportDataMap(this.cacheCenter.getDataMemory().getMmsRportDataMap(), this.mmsMemoryKeep, "report data map");
   }
   
   
   private void execCleanDataMap(java.util.Map dataMap, long keepTime, String info)
   {

   }
   
   
   private void removeReportDataMap(java.util.Map dataMap, long keepTime, String info)
   {

   }
   
   public void setCacheCenter(CacheCenter cacheCenter)
   {
     this.cacheCenter = cacheCenter;
   }
   
   public void setSmsMemoryKeep(long smsMemoryKeep) {
     this.smsMemoryKeep = smsMemoryKeep;
   }
   
   public void setMmsMemoryKeep(long mmsMemoryKeep) {
     this.mmsMemoryKeep = mmsMemoryKeep;
   }
   
   public void setUniMemoryKeep(long uniMemoryKeep) {
     this.uniMemoryKeep = uniMemoryKeep;
   }
   
   public void setCommonMemoryKeep(long commonMemoryKeep) {
     this.commonMemoryKeep = commonMemoryKeep;
   }
 }


