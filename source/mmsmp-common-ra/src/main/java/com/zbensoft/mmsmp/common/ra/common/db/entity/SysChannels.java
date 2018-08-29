/*     */ package com.zbensoft.mmsmp.common.ra.common.db.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SysChannels
/*     */   implements Serializable
/*     */ {
/*   9 */   public static String ChannelType_MMS = "m";
/*  10 */   public static String ChannelType_SMS = "s";
/*  11 */   public static String ChannelType_WAPPUSH = "p";
/*  12 */   public static String ChannelType_MME = "e";
/*     */   private Integer channelID;
/*     */   private String channelName;
/*     */   private String channelURL;
/*     */   private String channelType;
/*     */   private String userName;
/*     */   private String userPasswd;
/*     */   private String status;
/*     */   private String areaID;
/*     */   private int multiNum;
/*     */   private String operators;
/*     */ 
/*     */   public int getMultiNum()
/*     */   {
/*  37 */     return this.multiNum;
/*     */   }
/*     */ 
/*     */   public void setMultiNum(int multiNum)
/*     */   {
/*  44 */     this.multiNum = multiNum;
/*     */   }
/*     */ 
/*     */   public String getAreaID()
/*     */   {
/*  52 */     return this.areaID;
/*     */   }
/*     */ 
/*     */   public void setAreaID(String areaID) {
/*  56 */     this.areaID = areaID;
/*     */   }
/*     */ 
/*     */   public Integer getChannelID() {
/*  60 */     return this.channelID;
/*     */   }
/*     */ 
/*     */   public void setChannelID(Integer channelID) {
/*  64 */     this.channelID = channelID;
/*     */   }
/*     */ 
/*     */   public String getChannelName() {
/*  68 */     return this.channelName;
/*     */   }
/*     */ 
/*     */   public void setChannelName(String channelName) {
/*  72 */     this.channelName = channelName;
/*     */   }
/*     */ 
/*     */   public String getChannelType() {
/*  76 */     return this.channelType;
/*     */   }
/*     */ 
/*     */   public void setChannelType(String channelType) {
/*  80 */     this.channelType = channelType;
/*     */   }
/*     */ 
/*     */   public String getChannelURL() {
/*  84 */     return this.channelURL;
/*     */   }
/*     */ 
/*     */   public void setChannelURL(String channelURL) {
/*  88 */     this.channelURL = channelURL;
/*     */   }
/*     */ 
/*     */   public String getStatus()
/*     */   {
/*  93 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/*  97 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getUserName() {
/* 101 */     return this.userName;
/*     */   }
/*     */ 
/*     */   public void setUserName(String userName) {
/* 105 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */   public String getUserPasswd() {
/* 109 */     return this.userPasswd;
/*     */   }
/*     */ 
/*     */   public void setUserPasswd(String userPasswd) {
/* 113 */     this.userPasswd = userPasswd;
/*     */   }
/*     */ 
/*     */   public String getOperators() {
/* 117 */     return this.operators;
/*     */   }
/*     */ 
/*     */   public void setOperators(String operators) {
/* 121 */     this.operators = operators;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.db.entity.SysChannels
 * JD-Core Version:    0.6.0
 */