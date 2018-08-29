 package com.zbensoft.mmsmp.common.ra.common.config;
 
 import com.aceway.common.app.IFileModifyListener;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CommonConfig;
 
 public class CPUploadConfig
 {
   private static CPUploadConfig instance = new CPUploadConfig();
   private boolean initFlag = false;
   private CommonConfig cpuploadConfig = null;
 
   public static CPUploadConfig getInstance()
   {
     return instance;
   }
 
   private void init()
   {
     if (!this.initFlag)
     {
       this.cpuploadConfig = CommonConfig.getInstance("cpupload.properties");
       this.initFlag = true;
     }
   }
 
   public String getProperty(String name)
   {
     if (!this.initFlag) {
       init();
     }
     return this.cpuploadConfig.getProperty(name);
   }
 
   public void addFileModifyListener(IFileModifyListener listener)
   {
     this.cpuploadConfig.addFileModifyListener(listener);
   }
 
   public int getMaxUploadFileSizeAllowed() {
     return Integer.parseInt(getProperty("maxUploadFileSizeAllowed"));
   }
 
   public String getCPUploadFilePath() {
     String contentFilePath = getProperty("CPUploadFilePath");
 
     return contentFilePath;
   }
 
   public String getCPUploadFileBakPath() {
     String contentFilePath = getProperty("CPUploadFileBakPath");
 
     return contentFilePath;
   }
 
   public String getCPUploadFileTempPath() {
     String contentFilePath = getProperty("CPUploadFileTempPath");
 
     return contentFilePath;
   }
 
   public String getSubSystemId() {
     String contentFilePath = getProperty("subSystemID");
 
     return contentFilePath;
   }
 
   public int getWrappedPort() {
     return Integer.parseInt(getProperty("WrappedPort"));
   }
 }
