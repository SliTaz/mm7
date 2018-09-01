 package com.zbensoft.mmsmp.common.ra.common.config;
 
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
 public class DispatcherConfig
 {
   private static final Log logger = LogFactory.getLog(DispatcherConfig.class);
   private static DispatcherConfig mInstance = new DispatcherConfig();
   private boolean initFlag = false;
   private CommonConfig mDispatcherConfig = null;
 
   public static DispatcherConfig getInstance()
   {
     return mInstance;
   }
 
   private void init()
   {
     if (!this.initFlag)
       this.mDispatcherConfig = CommonConfig.getInstance("dispatcher.properties");
     logger.info("mDispatcherConfig = null :" + this.mDispatcherConfig);
   }
 
   public String getProperty(String name)
   {
     if (!this.initFlag)
       init();
     logger.info("Para name: " + name);
     return this.mDispatcherConfig.getProperty(name);
   }
 
//   public void addFileModifyListener(IFileModifyListener listener)
//   {
//     this.mDispatcherConfig.addFileModifyListener(listener);
//   }
 
   public int getPort()
   {
     return Integer.parseInt(getProperty("Port"));
   }
 
   public int getHandlerNum()
   {
     return Integer.parseInt(getProperty("HandleNumber"));
   }
 
   public boolean toStartBundle()
   {
     return Boolean.parseBoolean(getProperty("StartBundle"));
   }
 
   public int getWrapperPort()
   {
     return Integer.parseInt(getProperty("WrapperPort"));
   }
 
   public String getSubSystemId()
   {
     return getProperty("SubSystemId");
   }
 
   public String getDispatchServerIP()
   {
     return getProperty("DispatchServerIP");
   }
 }
