 package com.zbensoft.mmsmp.corebiz.cache;
 
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class CacheCenter
 {
   static final Logger logger = Logger.getLogger(CacheCenter.class);
   
   DataCache dataCache;
   DataMemory dataMemory;
   
   public DataCache getDataCache()
   {
     return this.dataCache;
   }
   
   public void setDataCache(DataCache dataCache) { this.dataCache = dataCache; }
   
   public DataMemory getDataMemory() {
     return this.dataMemory;
   }
   
   public void setDataMemory(DataMemory dataMemory) { this.dataMemory = dataMemory; }
 }





