/*     */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*     */ 
/*     */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*     */
/*     */
/*     */

/*     */
/*     */ public class MM7Config
/*     */ {
/*     */   private String AuthenticationMode;
/*     */   private String UserName;
/*     */   private String Password;
/*     */   private String MaxMessageSize;
/*     */   private String LogPath;
/*     */   private String logLevel;
/*     */   private String Charset;
/*     */   private String mmscURL;
/*     */   private String mmscIP;
/*     */   private String MmscID;
/*     */   private String UseSSL;
/*     */   private String ListenIP;
/*     */   private String ListenPort;
/*     */   private String BackLog;
/*     */   private String TimeOut;
/*     */   private String ReSendCount;
/*     */   private String LogNum;
/*     */   private String LogInterval;
/*     */   private String LogSize;
/*     */   private String KeepAlive;
/*     */   private String MaxKeepAliveRequests;
/*     */   private String ServerMaxKeepAlive;
/*     */   private String MinKeepAliveRequests;
/*     */   private String ScanInterval;
/*     */   private String KeepAliveTimeout;
/*     */   private String step;
/*     */ 
/*     */   public void setAuthenticationMode(String _AuthenticationMode)
/*     */   {
/*  36 */     this.AuthenticationMode = _AuthenticationMode; } 
/*  37 */   public String getAuthenticationMode() { return this.AuthenticationMode; } 
/*  38 */   public void setUserName(String _UserName) { this.UserName = _UserName; } 
/*  39 */   public String getUserName() { return this.UserName; } 
/*  40 */   public void setPassword(String _Password) { this.Password = _Password; } 
/*  41 */   public String getPassword() { return this.Password; } 
/*  42 */   public void setMaxMessageSize(String _MaxMessageSize) { this.MaxMessageSize = _MaxMessageSize; } 
/*  43 */   public String getMaxMessageSize() { return this.MaxMessageSize; } 
/*  44 */   public void setLogPath(String _LogPath) { this.LogPath = _LogPath; } 
/*  45 */   public String getLogPath() { return this.LogPath; } 
/*  46 */   public void setLogLevel(String _logLevel) { this.logLevel = _logLevel; } 
/*  47 */   public String getLogLevel() { return this.logLevel; } 
/*  48 */   public void setCharset(String _Charset) { this.Charset = _Charset; } 
/*  49 */   public String getCharset() { return this.Charset; } 
/*  50 */   public void setMmscURL(String _mmscURL) { this.mmscURL = _mmscURL; } 
/*  51 */   public String getMmscURL() { return this.mmscURL; } 
/*  52 */   public void setMmscIP(String _mmscIP) { this.mmscIP = _mmscIP; } 
/*  53 */   public String getMmscIP() { return this.mmscIP; } 
/*  54 */   public void setMmscID(String _MmscID) { this.MmscID = _MmscID; } 
/*  55 */   public String getMmscID() { return this.MmscID; } 
/*  56 */   public void setUseSSL(String _UseSSL) { this.UseSSL = _UseSSL; } 
/*  57 */   public String getUseSSL() { return this.UseSSL; } 
/*  58 */   public void setListenIP(String _ListenIP) { this.ListenIP = _ListenIP; } 
/*  59 */   public String getListenIP() { return this.ListenIP; } 
/*  60 */   public void setListenPort(String _ListenPort) { this.ListenPort = _ListenPort; } 
/*  61 */   public String getListenPort() { return this.ListenPort; } 
/*  62 */   public void setBackLog(String _BackLog) { this.BackLog = _BackLog; } 
/*  63 */   public String getBackLog() { return this.BackLog; } 
/*  64 */   public void setTimeOut(String _TimeOut) { this.TimeOut = _TimeOut; } 
/*  65 */   public String getTimeOut() { return this.TimeOut; } 
/*  66 */   public void setReSendCount(String _ReSendCount) { this.ReSendCount = _ReSendCount; } 
/*  67 */   public String getReSendCount() { return this.ReSendCount; } 
/*  68 */   public void setLogNum(String _LogNum) { this.LogNum = _LogNum; } 
/*  69 */   public String getLogNum() { return this.LogNum; } 
/*  70 */   public void setLogInterval(String _LogInterval) { this.LogInterval = _LogInterval; } 
/*  71 */   public String getLogInterval() { return this.LogInterval; } 
/*  72 */   public void setLogSize(String _LogSize) { this.LogSize = _LogSize; } 
/*  73 */   public String getLogSize() { return this.LogSize; } 
/*  74 */   public void setKeepAlive(String _KeepAlive) { this.KeepAlive = _KeepAlive; } 
/*  75 */   public String getKeepAlive() { return this.KeepAlive; } 
/*  76 */   public void setMaxKeepAliveRequests(String _MaxKeepAliveRequests) { this.MaxKeepAliveRequests = _MaxKeepAliveRequests; } 
/*  77 */   public String getMaxKeepAliveRequests() { return this.MaxKeepAliveRequests; } 
/*  78 */   public void setServerMaxKeepAlive(String _ServerMaxKeepAlive) { this.ServerMaxKeepAlive = _ServerMaxKeepAlive; } 
/*  79 */   public String getServerMaxKeepAlive() { return this.ServerMaxKeepAlive; } 
/*  80 */   public void setMinKeepAliveRequests(String _MinKeepAliveRequests) { this.MinKeepAliveRequests = _MinKeepAliveRequests; } 
/*  81 */   public String getMinKeepAliveRequests() { return this.MinKeepAliveRequests; } 
/*  82 */   public void setScanInterval(String _ScanInterval) { this.ScanInterval = _ScanInterval; } 
/*  83 */   public String getScanInterval() { return this.ScanInterval; } 
/*  84 */   public void setKeepAliveTimeout(String _KeepAliveTimeout) { this.KeepAliveTimeout = _KeepAliveTimeout; } 
/*  85 */   public String getKeepAliveTimeout() { return this.KeepAliveTimeout; } 
/*  86 */   public void setStep(String _step) { this.step = _step; } 
/*  87 */   public String getStep() { return this.step; }
/*     */ 
/*     */   public String toString() {
/*  90 */     StringBuilder sb = new StringBuilder(super.toString());
/*  91 */     sb.append(" [");
/*  92 */     sb.append(" AuthenticationMode=").append(this.AuthenticationMode);
/*  93 */     sb.append(" UserName=").append(this.UserName);
/*  94 */     sb.append(" Password=").append(this.Password);
/*  95 */     sb.append(" MaxMessageSize=").append(this.MaxMessageSize);
/*  96 */     sb.append(" LogPath=").append(this.LogPath);
/*  97 */     sb.append(" logLevel=").append(this.logLevel);
/*  98 */     sb.append(" Charset=").append(this.Charset);
/*  99 */     sb.append(" mmscURL=").append(this.mmscURL);
/* 100 */     sb.append(" mmscIP=").append(this.mmscIP);
/* 101 */     sb.append(" MmscID=").append(this.MmscID);
/* 102 */     sb.append(" UseSSL=").append(this.UseSSL);
/* 103 */     sb.append(" ListenIP=").append(this.ListenIP);
/* 104 */     sb.append(" ListenPort=").append(this.ListenPort);
/* 105 */     sb.append(" BackLog=").append(this.BackLog);
/* 106 */     sb.append(" TimeOut=").append(this.TimeOut);
/* 107 */     sb.append(" ReSendCount=").append(this.ReSendCount);
/* 108 */     sb.append(" LogNum=").append(this.LogNum);
/* 109 */     sb.append(" LogInterval=").append(this.LogInterval);
/* 110 */     sb.append(" LogSize=").append(this.LogSize);
/* 111 */     sb.append(" KeepAlive=").append(this.KeepAlive);
/* 112 */     sb.append(" MaxKeepAliveRequests=").append(this.MaxKeepAliveRequests);
/* 113 */     sb.append(" ServerMaxKeepAlive=").append(this.ServerMaxKeepAlive);
/* 114 */     sb.append(" MinKeepAliveRequests=").append(this.MinKeepAliveRequests);
/* 115 */     sb.append(" ScanInterval=").append(this.ScanInterval);
/* 116 */     sb.append(" KeepAliveTimeout=").append(this.KeepAliveTimeout);
/* 117 */     sb.append(" step=").append(this.step);
/* 118 */     sb.append(" ]");
/* 119 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public Map toHashMap() {
/* 123 */     Map hashmap = new HashMap();
/* 124 */     hashmap.put("AuthenticationMode", this.AuthenticationMode);
/* 125 */     hashmap.put("UserName", this.UserName);
/* 126 */     hashmap.put("Password", this.Password);
/* 127 */     hashmap.put("MaxMessageSize", this.MaxMessageSize);
/* 128 */     hashmap.put("LogPath", this.LogPath);
/* 129 */     hashmap.put("logLevel", this.logLevel);
/* 130 */     hashmap.put("Charset", this.Charset);
/* 131 */     hashmap.put("mmscURL", this.mmscURL);
/* 132 */     List list = new ArrayList();
/* 133 */     list.add(this.mmscIP);
/* 134 */     hashmap.put("mmscIP", list);
/* 135 */     hashmap.put("MmscID", this.MmscID);
/* 136 */     hashmap.put("UseSSL", this.UseSSL);
/* 137 */     hashmap.put("ListenIP", this.ListenIP);
/* 138 */     hashmap.put("ListenPort", this.ListenPort);
/* 139 */     hashmap.put("BackLog", this.BackLog);
/* 140 */     hashmap.put("TimeOut", this.TimeOut);
/* 141 */     hashmap.put("ReSendCount", this.ReSendCount);
/* 142 */     hashmap.put("LogNum", this.LogNum);
/* 143 */     hashmap.put("LogInterval", this.LogInterval);
/* 144 */     hashmap.put("LogSize", this.LogSize);
/* 145 */     hashmap.put("KeepAlive", this.KeepAlive);
/* 146 */     hashmap.put("MaxKeepAliveRequests", this.MaxKeepAliveRequests);
/* 147 */     hashmap.put("ServerMaxKeepAlive", this.ServerMaxKeepAlive);
/* 148 */     hashmap.put("MinKeepAliveRequests", this.MinKeepAliveRequests);
/* 149 */     hashmap.put("ScanInterval", this.ScanInterval);
/* 150 */     hashmap.put("KeepAliveTimeout", this.KeepAliveTimeout);
/* 151 */     hashmap.put("step", this.step);
/* 152 */     return hashmap;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.MM7Config
 * JD-Core Version:    0.6.0
 */