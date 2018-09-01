 package com.zbensoft.mmsmp.common.ra.common.config;

public class ReportConfig
 {
   private static ReportConfig instance = new ReportConfig();
   private boolean initFlag = false;
   private CommonConfig mmsmsConfig = null;
 
   public static ReportConfig getInstance()
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
 
   public String getProperty(String name)
   {
     if (!this.initFlag) {
       init();
     }
     return this.mmsmsConfig.getProperty(name);
   }
 
	// public void addFileModifyListener(IFileModifyListener listener)
	// {
	// this.mmsmsConfig.addFileModifyListener(listener);
	// }
 
   public String getTaskName(int index)
   {
     return getProperty("TaskName" + index);
   }
 
   public String getStartStatTime()
   {
     return getProperty("StartStatTime");
   }
 
   public int getTaskNums()
   {
     return Integer.parseInt(getProperty("TaskNums"));
   }
 }
