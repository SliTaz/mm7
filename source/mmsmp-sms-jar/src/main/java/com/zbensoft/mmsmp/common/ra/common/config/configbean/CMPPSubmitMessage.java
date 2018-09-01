/*     */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*     */ 
/*     */ public class CMPPSubmitMessage
/*     */ {
/*     */   private String pk_Total;
/*     */   private String pk_Number;
/*     */   private String registered_Delivery;
/*     */   private String msg_Level;
/*     */   private String service_Id;
/*     */   private String fee_UserType;
/*     */   private String tp_Pid;
/*     */   private String tp_Udhi;
/*     */   private String msg_Src;
/*     */   private String fee_Type;
/*     */   private String fee_Code;
/*     */   private String valid_Time;
/*     */   private String at_Time;
/*     */   private String src_Terminal_Id;
/*     */   private String destUsr_tl;
/*     */   private String dest_Terminal_Id;
/*     */ 
/*     */   public String getPk_Total()
/*     */   {
/*  42 */     return this.pk_Total;
/*     */   }
/*     */   public void setPk_Total(String pkTotal) {
/*  45 */     this.pk_Total = pkTotal;
/*     */   }
/*     */   public String getPk_Number() {
/*  48 */     return this.pk_Number;
/*     */   }
/*     */   public void setPk_Number(String pkNumber) {
/*  51 */     this.pk_Number = pkNumber;
/*     */   }
/*     */   public String getRegistered_Delivery() {
/*  54 */     return this.registered_Delivery;
/*     */   }
/*     */   public void setRegistered_Delivery(String registeredDelivery) {
/*  57 */     this.registered_Delivery = registeredDelivery;
/*     */   }
/*     */   public String getMsg_Level() {
/*  60 */     return this.msg_Level;
/*     */   }
/*     */   public void setMsg_Level(String msgLevel) {
/*  63 */     this.msg_Level = msgLevel;
/*     */   }
/*     */   public String getService_Id() {
/*  66 */     return this.service_Id;
/*     */   }
/*     */   public void setService_Id(String serviceId) {
/*  69 */     this.service_Id = serviceId;
/*     */   }
/*     */   public String getFee_UserType() {
/*  72 */     return this.fee_UserType;
/*     */   }
/*     */   public void setFee_UserType(String feeUserType) {
/*  75 */     this.fee_UserType = feeUserType;
/*     */   }
/*     */   public String getTp_Pid() {
/*  78 */     return this.tp_Pid;
/*     */   }
/*     */   public void setTp_Pid(String tpPid) {
/*  81 */     this.tp_Pid = tpPid;
/*     */   }
/*     */   public String getTp_Udhi() {
/*  84 */     return this.tp_Udhi;
/*     */   }
/*     */   public void setTp_Udhi(String tpUdhi) {
/*  87 */     this.tp_Udhi = tpUdhi;
/*     */   }
/*     */   public String getMsg_Src() {
/*  90 */     return this.msg_Src;
/*     */   }
/*     */   public void setMsg_Src(String msgSrc) {
/*  93 */     this.msg_Src = msgSrc;
/*     */   }
/*     */   public String getFee_Type() {
/*  96 */     return this.fee_Type;
/*     */   }
/*     */   public void setFee_Type(String feeType) {
/*  99 */     this.fee_Type = feeType;
/*     */   }
/*     */   public String getFee_Code() {
/* 102 */     return this.fee_Code;
/*     */   }
/*     */   public void setFee_Code(String feeCode) {
/* 105 */     this.fee_Code = feeCode;
/*     */   }
/*     */   public String getValid_Time() {
/* 108 */     return this.valid_Time;
/*     */   }
/*     */   public void setValid_Time(String validTime) {
/* 111 */     this.valid_Time = validTime;
/*     */   }
/*     */   public String getAt_Time() {
/* 114 */     return this.at_Time;
/*     */   }
/*     */   public void setAt_Time(String atTime) {
/* 117 */     this.at_Time = atTime;
/*     */   }
/*     */   public String getSrc_Terminal_Id() {
/* 120 */     return this.src_Terminal_Id;
/*     */   }
/*     */   public void setSrc_Terminal_Id(String srcTerminalId) {
/* 123 */     this.src_Terminal_Id = srcTerminalId;
/*     */   }
/*     */   public String getDestUsr_tl() {
/* 126 */     return this.destUsr_tl;
/*     */   }
/*     */   public void setDestUsr_tl(String destUsrTl) {
/* 129 */     this.destUsr_tl = destUsrTl;
/*     */   }
/*     */   public String getDest_Terminal_Id() {
/* 132 */     return this.dest_Terminal_Id;
/*     */   }
/*     */   public void setDest_Terminal_Id(String destTerminalId) {
/* 135 */     this.dest_Terminal_Id = destTerminalId;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 139 */     StringBuilder sb = new StringBuilder(super.toString());
/* 140 */     sb.append(" [");
/* 141 */     sb.append(" pk_Total=").append(this.pk_Total);
/* 142 */     sb.append(" pk_Number=").append(this.pk_Number);
/* 143 */     sb.append(" registered_Delivery=").append(this.registered_Delivery);
/* 144 */     sb.append(" msg_Level=").append(this.msg_Level);
/* 145 */     sb.append(" service_Id=").append(this.service_Id);
/* 146 */     sb.append(" fee_UserType=").append(this.fee_UserType);
/* 147 */     sb.append(" tp_Pid=").append(this.tp_Pid);
/* 148 */     sb.append(" tp_Udhi=").append(this.tp_Udhi);
/* 149 */     sb.append(" msg_Src=").append(this.msg_Src);
/* 150 */     sb.append(" fee_Type=").append(this.fee_Type);
/* 151 */     sb.append(" fee_Code=").append(this.fee_Code);
/* 152 */     sb.append(" valid_Time=").append(this.valid_Time);
/* 153 */     sb.append(" at_Time=").append(this.at_Time);
/* 154 */     sb.append(" src_Terminal_Id=").append(this.src_Terminal_Id);
/* 155 */     sb.append(" destUsr_tl=").append(this.destUsr_tl);
/* 156 */     sb.append(" dest_Terminal_Id=").append(this.dest_Terminal_Id);
/* 157 */     sb.append(" ]");
/* 158 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.CMPPSubmitMessage
 * JD-Core Version:    0.6.0
 */