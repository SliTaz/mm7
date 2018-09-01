 package com.zbensoft.mmsmp.corebiz.message;
 
 public class MT_MMHttpSPMessage_Report
 {
   private String globalMessageid;
   private long globalCreateTime;
   private String sequence;
   private String spid;
   private String sp_productid;
   private String globalReportUrl;
   private String serviceid;
   private String messageid;
   private long globalMessageTime;
   private String sourceGlobalMessageid;
   private String reqId;
   
   public String getSourceGlobalMessageid() {
     return this.sourceGlobalMessageid;
   }
   
   public void setSourceGlobalMessageid(String sourceGlobalMessageid) {
     this.sourceGlobalMessageid = sourceGlobalMessageid;
   }
   
   public String getReqId() {
     return this.reqId;
   }
   
   public void setReqId(String reqId) {
     this.reqId = reqId;
   }
   
   public String getSequence() {
     return this.sequence;
   }
   
   public void setSequence(String sequence) {
     this.sequence = sequence;
   }
   
   public String getSpid() {
     return this.spid;
   }
   
   public void setSpid(String spid) {
     this.spid = spid;
   }
   
   public String getSp_productid() {
     return this.sp_productid;
   }
   
   public void setSp_productid(String sp_productid) {
     this.sp_productid = sp_productid;
   }
   
   public String getGlobalReportUrl() {
     return this.globalReportUrl;
   }
   
   public void setGlobalReportUrl(String globalReportUrl) {
     this.globalReportUrl = globalReportUrl;
   }
   
   public String getServiceid() {
     return this.serviceid;
   }
   
   public void setServiceid(String serviceid) {
     this.serviceid = serviceid;
   }
   
   public String getMessageid() {
     return this.messageid;
   }
   
   public void setMessageid(String messageid) {
     this.messageid = messageid;
   }
   
   public long getGlobalMessageTime() {
     return this.globalMessageTime;
   }
   
   public void setGlobalMessageTime(long globalMessageTime) {
     this.globalMessageTime = globalMessageTime;
   }
   
   public String getGlobalMessageid() {
     return this.globalMessageid;
   }
   
   public long getGlobalCreateTime() {
     return this.globalCreateTime;
   }
   
   public void setGlobalMessageid(String globalMessageid) {
     this.globalMessageid = globalMessageid;
     setGlobalCreateTime(System.currentTimeMillis());
   }
   
   public void setGlobalCreateTime(long globalCreateTime) {
     this.globalCreateTime = globalCreateTime;
   }
 }





