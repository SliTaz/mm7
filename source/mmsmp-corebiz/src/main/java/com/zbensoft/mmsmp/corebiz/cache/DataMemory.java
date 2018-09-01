 package com.zbensoft.mmsmp.corebiz.cache;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import java.util.concurrent.ConcurrentHashMap;
 
 
 
 
 
 
 
 
 
 
 
 
 public class DataMemory
 {
   ConcurrentHashMap<String, AbstractMessage> smsDataMap;
   ConcurrentHashMap<String, AbstractMessage> mmsDataMap;
   ConcurrentHashMap<String, AbstractMessage> uniDataMap;
   ConcurrentHashMap<String, AbstractMessage> commonDataMap;
   ConcurrentHashMap<String, AbstractMessage> mmsRportDataMap;
   
   public ConcurrentHashMap getSmsDataMap()
   {
     return this.smsDataMap;
   }
   
   public void setSmsDataMap(ConcurrentHashMap smsDataMap) { this.smsDataMap = smsDataMap; }
   
   public ConcurrentHashMap getMmsDataMap() {
     return this.mmsDataMap;
   }
   
   public void setMmsDataMap(ConcurrentHashMap mmsDataMap) { this.mmsDataMap = mmsDataMap; }
   
   public ConcurrentHashMap getUniDataMap() {
     return this.uniDataMap;
   }
   
   public void setUniDataMap(ConcurrentHashMap uniDataMap) { this.uniDataMap = uniDataMap; }
   
   public ConcurrentHashMap<String, AbstractMessage> getCommonDataMap() {
     return this.commonDataMap;
   }
   
   public void setCommonDataMap(ConcurrentHashMap<String, AbstractMessage> commonDataMap) {
     this.commonDataMap = commonDataMap;
   }
   
   public ConcurrentHashMap<String, AbstractMessage> getMmsRportDataMap() { return this.mmsRportDataMap; }
   
   public void setMmsRportDataMap(ConcurrentHashMap<String, AbstractMessage> mmsRportDataMap) {
     this.mmsRportDataMap = mmsRportDataMap;
   }
 }





