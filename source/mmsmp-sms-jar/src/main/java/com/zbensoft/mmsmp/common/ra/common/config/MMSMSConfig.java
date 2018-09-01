 package com.zbensoft.mmsmp.common.ra.common.config;

public class MMSMSConfig
 {
   private static MMSMSConfig instance = new MMSMSConfig();
   private boolean initFlag = false;
   private CommonConfig mmsmsConfig;
 
   public static MMSMSConfig getInstance()
   {
     return instance;
   }
 
   private void init()
   {
     if (!this.initFlag)
     {
       this.mmsmsConfig = CommonConfig.getInstance("mmsms.properties");
       this.initFlag = true;
     }
   }
 
   public String getProperty(String name) {
     if (!this.initFlag) {
       init();
     }
 
     return this.mmsmsConfig.getProperty(name);
   }
 
//   public void addFileModifyListener(IFileModifyListener listener) {
//     this.mmsmsConfig.addFileModifyListener(listener);
//   }
 
   public String getFlowFilePath()
   {
     return getProperty("flowFilePath");
   }
 
   public String getContentFilePath()
   {
     return getProperty("contentFilePath");
   }
 
   public String getContentViewPicFilePath()
   {
     return getProperty("contentViewPicFilePath");
   }
 
   public String getTempContentFilePath()
   {
     return getProperty("tempContentFilePath");
   }
 
   public String getADPhonesFilePath()
   {
     return getProperty("ADPhonesFilePath");
   }
 
   public String getDBUrl()
   {
     return getProperty("db.url");
   }
 
   public String getDBUserName()
   {
     return getProperty("db.user");
   }
 
   public String getDBUserPass()
   {
     return getProperty("db.password");
   }
 
   public String getDataSrcName()
   {
     return getProperty("DataSrcName");
   }
 
   public String getDriverClassName()
   {
     return getProperty("DriverClassName");
   }
 
   public int getDataSrcMaxActive()
   {
     String maxActive = getProperty("DataSrcMaxActive");
     if (maxActive != null) {
       return Integer.parseInt(getProperty("DataSrcMaxActive"));
     }
     return 10;
   }
 
   public long getDataSrcMaxWait()
   {
     String maxWaitTime = getProperty("DataSrcMaxWait");
     if (maxWaitTime != null) {
       return Long.parseLong(getProperty("DataSrcMaxWait"));
     }
     return 1000L;
   }
 }
