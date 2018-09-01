 package com.zbensoft.mmsmp.common.ra.common.config;

public class ParlayXServiceConfig
 {
   private static ParlayXServiceConfig instance = new ParlayXServiceConfig();
   private boolean initFlag = false;
   private CommonConfig parlayxServiceConfig;
 
   public static ParlayXServiceConfig getInstance()
   {
     return instance;
   }
 
   private void init()
   {
     if (!this.initFlag) {
       this.parlayxServiceConfig = CommonConfig.getInstance("parlayXService.properties");
       this.initFlag = true;
     }
   }
 
   public String getProperty(String name) {
     if (!this.initFlag) {
       init();
     }
     return this.parlayxServiceConfig.getProperty(name);
   }

	// public void addFileModifyListener(IFileModifyListener listener) {
	// this.parlayxServiceConfig.addFileModifyListener(listener);
	// }
   public String getSubSystemId() {
     return getProperty("SubSystemId");
   }
 
   public int getPxsmsWrapperPort()
   {
     return Integer.parseInt(getProperty("pxsms.WrapperPort").trim());
   }
 
   public int getPxmmsWrapperPort()
   {
     return Integer.parseInt(getProperty("pxmms.WrapperPort").trim());
   }
 
   public int getVasIdLength()
   {
     return Integer.parseInt(getProperty("vasid.length").trim());
   }
   public int getSmsVasIdLength() {
     return Integer.parseInt(getProperty("sms.vasid.length").trim());
   }
 }
