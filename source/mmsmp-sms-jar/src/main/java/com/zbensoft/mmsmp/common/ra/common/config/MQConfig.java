 package com.zbensoft.mmsmp.common.ra.common.config;

public class MQConfig
 {
   private static MQConfig instance = new MQConfig();
   private boolean initFlag = false;
   private CommonConfig mqConfig;
 
   public static MQConfig getInstance()
   {
     return instance;
   }
 
   private void init()
   {
     if (!this.initFlag)
       this.mqConfig = CommonConfig.getInstance("mq.properties");
   }
 
   public String getProperty(String name)
   {
     if (!this.initFlag) {
       init();
     }
 
     return this.mqConfig.getProperty(name);
   }
 
	// public void addFileModifyListener(IFileModifyListener listener)
	// {
	// this.mqConfig.addFileModifyListener(listener);
	// }
 
   public String getSubSystemId()
   {
     return getProperty("SubSystemId");
   }
 
   public int getWrapperPort()
   {
     return Integer.parseInt(getProperty("WrapperPort"));
   }
 }
