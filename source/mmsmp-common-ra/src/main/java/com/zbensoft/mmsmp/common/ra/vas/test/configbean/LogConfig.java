/*    */ package com.zbensoft.mmsmp.common.ra.vas.test.configbean;
/*    */ 
/*    */ public class LogConfig
/*    */ {
/*    */   public int logLevel;
/*    */   public String logPath;
/*    */   public int needSNMP;
/*    */   public String snmpServerIp;
/*    */   public int snmpServerPort;
/*    */   public int needSPLIT;
/*    */   public int maxFileNum;
/*    */   public int maxFileSize;
/*    */ 
/*    */   public int getLogLevel()
/*    */   {
/* 17 */     return this.logLevel;
/*    */   }
/*    */   public void setLogLevel(int logLevel) {
/* 20 */     this.logLevel = logLevel;
/*    */   }
/*    */   public String getLogPath() {
/* 23 */     return this.logPath;
/*    */   }
/*    */   public void setLogPath(String logPath) {
/* 26 */     this.logPath = logPath;
/*    */   }
/*    */   public int getNeedSNMP() {
/* 29 */     return this.needSNMP;
/*    */   }
/*    */   public void setNeedSNMP(int needSNMP) {
/* 32 */     this.needSNMP = needSNMP;
/*    */   }
/*    */   public String getSnmpServerIp() {
/* 35 */     return this.snmpServerIp;
/*    */   }
/*    */   public void setSnmpServerIp(String snmpServerIp) {
/* 38 */     this.snmpServerIp = snmpServerIp;
/*    */   }
/*    */   public int getSnmpServerPort() {
/* 41 */     return this.snmpServerPort;
/*    */   }
/*    */   public void setSnmpServerPort(int snmpServerPort) {
/* 44 */     this.snmpServerPort = snmpServerPort;
/*    */   }
/*    */   public int getNeedSPLIT() {
/* 47 */     return this.needSPLIT;
/*    */   }
/*    */   public void setNeedSPLIT(int needSPLIT) {
/* 50 */     this.needSPLIT = needSPLIT;
/*    */   }
/*    */   public int getMaxFileNum() {
/* 53 */     return this.maxFileNum;
/*    */   }
/*    */   public void setMaxFileNum(int maxFileNum) {
/* 56 */     this.maxFileNum = maxFileNum;
/*    */   }
/*    */   public int getMaxFileSize() {
/* 59 */     return this.maxFileSize;
/*    */   }
/*    */   public void setMaxFileSize(int maxFileSize) {
/* 62 */     this.maxFileSize = maxFileSize;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.test.configbean.LogConfig
 * JD-Core Version:    0.6.0
 */