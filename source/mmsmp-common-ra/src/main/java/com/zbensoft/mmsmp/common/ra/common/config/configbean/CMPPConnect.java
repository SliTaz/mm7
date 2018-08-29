/*     */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*     */ 
/*     */ public class CMPPConnect
/*     */ {
/*     */   private String host;
/*     */   private String port;
/*     */   private String source_addr;
/*     */   private String shared_secret;
/*     */   private String heartbeat_interval;
/*     */   private String reconnect_interval;
/*     */   private String heartbeat_noresponseout;
/*     */   private String transaction_timeout;
/*     */   private String version;
/*     */   private boolean debug;
/*     */ 
/*     */   public String getHost()
/*     */   {
/*  26 */     return this.host;
/*     */   }
/*     */   public void setHost(String host) {
/*  29 */     this.host = host;
/*     */   }
/*     */   public String getPort() {
/*  32 */     return this.port;
/*     */   }
/*     */   public void setPort(String port) {
/*  35 */     this.port = port;
/*     */   }
/*     */   public String getSource_addr() {
/*  38 */     return this.source_addr;
/*     */   }
/*     */   public void setSource_addr(String sourceAddr) {
/*  41 */     this.source_addr = sourceAddr;
/*     */   }
/*     */   public String getShared_secret() {
/*  44 */     return this.shared_secret;
/*     */   }
/*     */   public void setShared_secret(String sharedSecret) {
/*  47 */     this.shared_secret = sharedSecret;
/*     */   }
/*     */   public String getHeartbeat_interval() {
/*  50 */     return this.heartbeat_interval;
/*     */   }
/*     */   public void setHeartbeat_interval(String heartbeatInterval) {
/*  53 */     this.heartbeat_interval = heartbeatInterval;
/*     */   }
/*     */   public String getReconnect_interval() {
/*  56 */     return this.reconnect_interval;
/*     */   }
/*     */   public void setReconnect_interval(String reconnectInterval) {
/*  59 */     this.reconnect_interval = reconnectInterval;
/*     */   }
/*     */   public String getHeartbeat_noresponseout() {
/*  62 */     return this.heartbeat_noresponseout;
/*     */   }
/*     */   public void setHeartbeat_noresponseout(String heartbeatNoresponseout) {
/*  65 */     this.heartbeat_noresponseout = heartbeatNoresponseout;
/*     */   }
/*     */   public String getTransaction_timeout() {
/*  68 */     return this.transaction_timeout;
/*     */   }
/*     */   public void setTransaction_timeout(String transactionTimeout) {
/*  71 */     this.transaction_timeout = transactionTimeout;
/*     */   }
/*     */   public String getVersion() {
/*  74 */     return this.version;
/*     */   }
/*     */   public void setVersion(String version) {
/*  77 */     this.version = version;
/*     */   }
/*     */   public boolean isDebug() {
/*  80 */     return this.debug;
/*     */   }
/*     */   public void setDebug(boolean debug) {
/*  83 */     this.debug = debug;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  87 */     StringBuilder sb = new StringBuilder(super.toString());
/*  88 */     sb.append(" [");
/*  89 */     sb.append(" host=").append(this.host);
/*  90 */     sb.append(" port=").append(this.port);
/*  91 */     sb.append(" source_addr=").append(this.source_addr);
/*  92 */     sb.append(" shared_secret=").append(this.shared_secret);
/*  93 */     sb.append(" heartbeat_interval=").append(this.heartbeat_interval);
/*  94 */     sb.append(" reconnect_interval=").append(this.reconnect_interval);
/*  95 */     sb.append(" heartbeat_noresponseout=").append(this.heartbeat_noresponseout);
/*  96 */     sb.append(" transaction_timeout=").append(this.transaction_timeout);
/*  97 */     sb.append(" version=").append(this.version);
/*  98 */     sb.append(" debug=").append(this.debug);
/*  99 */     sb.append(" ]");
/* 100 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.CMPPConnect
 * JD-Core Version:    0.6.0
 */