 package com.zbensoft.mmsmp.common.ra.common.config.configbean;
 
 public class CommonConfig
 {
   private String uniBusinessUrl;
   private String contentFilePath;
   private String vaspId;
   private String mmsEncoding;
 
   public String getMmsEncoding()
   {
     return this.mmsEncoding;
   }
 
   public void setMmsEncoding(String mmsEncoding) {
     this.mmsEncoding = mmsEncoding;
   }
 
   public String getUniBusinessUrl()
   {
     return this.uniBusinessUrl;
   }
 
   public void setUniBusinessUrl(String uniBusinessUrl)
   {
     this.uniBusinessUrl = uniBusinessUrl;
   }
 
   public String getContentFilePath() {
     return this.contentFilePath;
   }
 
   public void setContentFilePath(String contentFilePath) {
     this.contentFilePath = contentFilePath;
   }
 
   public String getVaspId() {
     return this.vaspId;
   }
 
   public void setVaspId(String vaspId) {
     this.vaspId = vaspId;
   }
 }
