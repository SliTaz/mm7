/*     */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*     */ 
/*     */ public class AgentConfig
/*     */ {
/*     */   private String spPassword;
/*     */   private String isagWappushUrl;
/*     */   private String emailSmtpHost;
/*     */   private String emailUserName;
/*     */   private String emailPassword;
/*     */   private String emailUser;
/*     */   private String isagSmsMtUrl;
/*     */   private String isagMmsMtUrl;
/*     */   private String isagWapMtUrl;
/*     */   private String spWithdrawUrl;
/*     */   private String uniBusinessUrl;
/*     */   private String spAdminName;
/*     */   private String spAdminPwd;
/*     */   private String emailSuffix;
/*     */   private String emailAlias;
/*     */   private int smsReceiveThreadNum;
/*     */   private int smsSendThreadNum;
/*     */   private int smsQuenceSize;
/*     */   private String smsServerIP;
/*     */   private int smsServerPort;
/*     */   private String smsClientIP;
/*     */   private int smsClientPort;
/*     */   private int vacRequestThreadNum;
/*     */   private int vacResponseThreadNum;
/*     */   private int vacQuenceSize;
/*     */   private String vacServerIP;
/*     */   private int vacServerPort;
/*     */   private String vacClientIP;
/*     */   private int vacClientPort;
/*     */   private int spMOThreadNum;
/*     */   private int spMTThreadNum;
/*     */   private int spQuenceSize;
/*     */   private String spServerIP;
/*     */   private int spServerPort;
/*     */   private String spClientIP;
/*     */   private int spClientPort;
/*     */   private int woRequestThreadNum;
/*     */   private int woResponseThreadNum;
/*     */   private int woQuenceSize;
/*     */   private String woServerIP;
/*     */   private int woServerPort;
/*     */   private String woClientIP;
/*     */   private int woClientPort;
/*     */ 
/*     */   public String getSmsClientIP()
/*     */   {
/*  71 */     return this.smsClientIP;
/*     */   }
/*     */   public void setSmsClientIP(String smsClientIP) {
/*  74 */     this.smsClientIP = smsClientIP;
/*     */   }
/*     */   public int getSmsClientPort() {
/*  77 */     return this.smsClientPort;
/*     */   }
/*     */   public void setSmsClientPort(int smsClientPort) {
/*  80 */     this.smsClientPort = smsClientPort;
/*     */   }
/*     */   public String getVacClientIP() {
/*  83 */     return this.vacClientIP;
/*     */   }
/*     */   public void setVacClientIP(String vacClientIP) {
/*  86 */     this.vacClientIP = vacClientIP;
/*     */   }
/*     */   public int getVacClientPort() {
/*  89 */     return this.vacClientPort;
/*     */   }
/*     */   public void setVacClientPort(int vacClientPort) {
/*  92 */     this.vacClientPort = vacClientPort;
/*     */   }
/*     */   public String getSpClientIP() {
/*  95 */     return this.spClientIP;
/*     */   }
/*     */   public void setSpClientIP(String spClientIP) {
/*  98 */     this.spClientIP = spClientIP;
/*     */   }
/*     */   public int getSpClientPort() {
/* 101 */     return this.spClientPort;
/*     */   }
/*     */   public void setSpClientPort(int spClientPort) {
/* 104 */     this.spClientPort = spClientPort;
/*     */   }
/*     */   public String getWoClientIP() {
/* 107 */     return this.woClientIP;
/*     */   }
/*     */   public void setWoClientIP(String woClientIP) {
/* 110 */     this.woClientIP = woClientIP;
/*     */   }
/*     */   public int getWoClientPort() {
/* 113 */     return this.woClientPort;
/*     */   }
/*     */   public void setWoClientPort(int woClientPort) {
/* 116 */     this.woClientPort = woClientPort;
/*     */   }
/*     */   public int getWoRequestThreadNum() {
/* 119 */     return this.woRequestThreadNum;
/*     */   }
/*     */   public void setWoRequestThreadNum(int woRequestThreadNum) {
/* 122 */     this.woRequestThreadNum = woRequestThreadNum;
/*     */   }
/*     */   public int getWoResponseThreadNum() {
/* 125 */     return this.woResponseThreadNum;
/*     */   }
/*     */   public void setWoResponseThreadNum(int woResponseThreadNum) {
/* 128 */     this.woResponseThreadNum = woResponseThreadNum;
/*     */   }
/*     */   public int getWoQuenceSize() {
/* 131 */     return this.woQuenceSize;
/*     */   }
/*     */   public void setWoQuenceSize(int woQuenceSize) {
/* 134 */     this.woQuenceSize = woQuenceSize;
/*     */   }
/*     */   public String getWoServerIP() {
/* 137 */     return this.woServerIP;
/*     */   }
/*     */   public void setWoServerIP(String woServerIP) {
/* 140 */     this.woServerIP = woServerIP;
/*     */   }
/*     */   public int getWoServerPort() {
/* 143 */     return this.woServerPort;
/*     */   }
/*     */   public void setWoServerPort(int woServerPort) {
/* 146 */     this.woServerPort = woServerPort;
/*     */   }
/*     */   public int getSpMOThreadNum() {
/* 149 */     return this.spMOThreadNum;
/*     */   }
/*     */   public void setSpMOThreadNum(int spMOThreadNum) {
/* 152 */     this.spMOThreadNum = spMOThreadNum;
/*     */   }
/*     */   public int getSpMTThreadNum() {
/* 155 */     return this.spMTThreadNum;
/*     */   }
/*     */   public void setSpMTThreadNum(int spMTThreadNum) {
/* 158 */     this.spMTThreadNum = spMTThreadNum;
/*     */   }
/*     */   public int getSpQuenceSize() {
/* 161 */     return this.spQuenceSize;
/*     */   }
/*     */   public void setSpQuenceSize(int spQuenceSize) {
/* 164 */     this.spQuenceSize = spQuenceSize;
/*     */   }
/*     */   public String getSpServerIP() {
/* 167 */     return this.spServerIP;
/*     */   }
/*     */   public void setSpServerIP(String spServerIP) {
/* 170 */     this.spServerIP = spServerIP;
/*     */   }
/*     */   public int getSpServerPort() {
/* 173 */     return this.spServerPort;
/*     */   }
/*     */   public void setSpServerPort(int spServerPort) {
/* 176 */     this.spServerPort = spServerPort;
/*     */   }
/*     */   public int getVacRequestThreadNum() {
/* 179 */     return this.vacRequestThreadNum;
/*     */   }
/*     */   public void setVacRequestThreadNum(int vacRequestThreadNum) {
/* 182 */     this.vacRequestThreadNum = vacRequestThreadNum;
/*     */   }
/*     */   public int getVacResponseThreadNum() {
/* 185 */     return this.vacResponseThreadNum;
/*     */   }
/*     */   public void setVacResponseThreadNum(int vacResponseThreadNum) {
/* 188 */     this.vacResponseThreadNum = vacResponseThreadNum;
/*     */   }
/*     */   public int getVacQuenceSize() {
/* 191 */     return this.vacQuenceSize;
/*     */   }
/*     */   public void setVacQuenceSize(int vacQuenceSize) {
/* 194 */     this.vacQuenceSize = vacQuenceSize;
/*     */   }
/*     */   public String getVacServerIP() {
/* 197 */     return this.vacServerIP;
/*     */   }
/*     */   public void setVacServerIP(String vacServerIP) {
/* 200 */     this.vacServerIP = vacServerIP;
/*     */   }
/*     */   public int getVacServerPort() {
/* 203 */     return this.vacServerPort;
/*     */   }
/*     */   public void setVacServerPort(int vacServerPort) {
/* 206 */     this.vacServerPort = vacServerPort;
/*     */   }
/*     */   public String getSmsServerIP() {
/* 209 */     return this.smsServerIP;
/*     */   }
/*     */   public void setSmsServerIP(String smsServerIP) {
/* 212 */     this.smsServerIP = smsServerIP;
/*     */   }
/*     */   public int getSmsServerPort() {
/* 215 */     return this.smsServerPort;
/*     */   }
/*     */   public void setSmsServerPort(int smsServerPort) {
/* 218 */     this.smsServerPort = smsServerPort;
/*     */   }
/*     */   public int getSmsReceiveThreadNum() {
/* 221 */     return this.smsReceiveThreadNum;
/*     */   }
/*     */   public void setSmsReceiveThreadNum(int smsReceiveThreadNum) {
/* 224 */     this.smsReceiveThreadNum = smsReceiveThreadNum;
/*     */   }
/*     */   public int getSmsSendThreadNum() {
/* 227 */     return this.smsSendThreadNum;
/*     */   }
/*     */   public void setSmsSendThreadNum(int smsSendThreadNum) {
/* 230 */     this.smsSendThreadNum = smsSendThreadNum;
/*     */   }
/*     */   public int getSmsQuenceSize() {
/* 233 */     return this.smsQuenceSize;
/*     */   }
/*     */   public void setSmsQuenceSize(int smsQuenceSize) {
/* 236 */     this.smsQuenceSize = smsQuenceSize;
/*     */   }
/*     */   public String getEmailSuffix() {
/* 239 */     return this.emailSuffix;
/*     */   }
/*     */   public void setEmailSuffix(String emailSuffix) {
/* 242 */     this.emailSuffix = emailSuffix;
/*     */   }
/*     */   public String getEmailAlias() {
/* 245 */     return this.emailAlias;
/*     */   }
/*     */   public void setEmailAlias(String emailAlias) {
/* 248 */     this.emailAlias = emailAlias;
/*     */   }
/*     */   public String getSpAdminName() {
/* 251 */     return this.spAdminName;
/*     */   }
/*     */   public void setSpAdminName(String spAdminName) {
/* 254 */     this.spAdminName = spAdminName;
/*     */   }
/*     */   public String getSpAdminPwd() {
/* 257 */     return this.spAdminPwd;
/*     */   }
/*     */   public void setSpAdminPwd(String spAdminPwd) {
/* 260 */     this.spAdminPwd = spAdminPwd;
/*     */   }
/*     */   public String getSpWithdrawUrl() {
/* 263 */     return this.spWithdrawUrl;
/*     */   }
/*     */   public void setSpWithdrawUrl(String spWithdrawUrl) {
/* 266 */     this.spWithdrawUrl = spWithdrawUrl;
/*     */   }
/*     */   public String getUniBusinessUrl() {
/* 269 */     return this.uniBusinessUrl;
/*     */   }
/*     */   public void setUniBusinessUrl(String uniBusinessUrl) {
/* 272 */     this.uniBusinessUrl = uniBusinessUrl;
/*     */   }
/*     */   public String getSpPassword() {
/* 275 */     return this.spPassword;
/*     */   }
/*     */   public void setSpPassword(String spPassword) {
/* 278 */     this.spPassword = spPassword;
/*     */   }
/*     */   public String getIsagWappushUrl() {
/* 281 */     return this.isagWappushUrl;
/*     */   }
/*     */   public void setIsagWappushUrl(String isagWappushUrl) {
/* 284 */     this.isagWappushUrl = isagWappushUrl;
/*     */   }
/*     */   public String getEmailSmtpHost() {
/* 287 */     return this.emailSmtpHost;
/*     */   }
/*     */   public void setEmailSmtpHost(String emailSmtpHost) {
/* 290 */     this.emailSmtpHost = emailSmtpHost;
/*     */   }
/*     */   public String getEmailUserName() {
/* 293 */     return this.emailUserName;
/*     */   }
/*     */   public void setEmailUserName(String emailUserName) {
/* 296 */     this.emailUserName = emailUserName;
/*     */   }
/*     */   public String getEmailPassword() {
/* 299 */     return this.emailPassword;
/*     */   }
/*     */   public void setEmailPassword(String emailPassword) {
/* 302 */     this.emailPassword = emailPassword;
/*     */   }
/*     */   public String getIsagSmsMtUrl() {
/* 305 */     return this.isagSmsMtUrl;
/*     */   }
/*     */   public void setIsagSmsMtUrl(String isagSmsMtUrl) {
/* 308 */     this.isagSmsMtUrl = isagSmsMtUrl;
/*     */   }
/*     */   public String getIsagMmsMtUrl() {
/* 311 */     return this.isagMmsMtUrl;
/*     */   }
/*     */   public void setIsagMmsMtUrl(String isagMmsMtUrl) {
/* 314 */     this.isagMmsMtUrl = isagMmsMtUrl;
/*     */   }
/*     */   public String getIsagWapMtUrl() {
/* 317 */     return this.isagWapMtUrl;
/*     */   }
/*     */   public void setIsagWapMtUrl(String isagWapMtUrl) {
/* 320 */     this.isagWapMtUrl = isagWapMtUrl;
/*     */   }
/*     */   public String getEmailUser() {
/* 323 */     return this.emailUser;
/*     */   }
/*     */   public void setEmailUser(String emailUser) {
/* 326 */     this.emailUser = emailUser;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.AgentConfig
 * JD-Core Version:    0.6.0
 */