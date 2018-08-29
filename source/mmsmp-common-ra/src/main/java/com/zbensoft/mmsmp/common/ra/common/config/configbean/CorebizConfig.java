/*     */ package com.zbensoft.mmsmp.common.ra.common.config.configbean;
/*     */ 
/*     */ public class CorebizConfig
/*     */ {
/*     */   private int dealDemand;
/*     */   private int mtQueueListenPort;
/*     */   private int moQueueListenPort;
/*     */   private int reportQueueListenPort;
/*     */   private int uniBusinessListenPort;
/*     */   private String wappushUrl;
/*     */   private int mmsReqNum;
/*     */   private int mmeReqNum;
/*     */   private int smsReqNum;
/*     */   private int wapReqNum;
/*     */   private boolean manualWork;
/*     */   private long mmsInterval;
/*     */   private long mmeInterval;
/*     */   private long smsInterval;
/*     */   private long wapInterval;
/*     */   private boolean sameProduct;
/*     */   private boolean sameQueue;
/*     */   private String codeAnswerDelim;
/*     */   private String sms4AnswerTwice;
/*     */   private String sms4WrongAnswer;
/*     */   private String sms4RightAnswer;
/*     */   private boolean terminalAdapter;
/*     */   private boolean autoResend;
/*     */   private String mobileSeq;
/*     */   private String telecomSeq;
/*     */   private String unicomSeq;
/*     */ 
/*     */   public boolean isAutoResend()
/*     */   {
/*  95 */     return this.autoResend;
/*     */   }
/*     */ 
/*     */   public void setAutoResend(boolean autoResend) {
/*  99 */     this.autoResend = autoResend;
/*     */   }
/*     */ 
/*     */   public boolean isTerminalAdapter() {
/* 103 */     return this.terminalAdapter;
/*     */   }
/*     */ 
/*     */   public void setTerminalAdapter(boolean terminalAdapter) {
/* 107 */     this.terminalAdapter = terminalAdapter;
/*     */   }
/*     */ 
/*     */   public String getSms4RightAnswer() {
/* 111 */     return this.sms4RightAnswer;
/*     */   }
/*     */ 
/*     */   public void setSms4RightAnswer(String sms4RightAnswer) {
/* 115 */     this.sms4RightAnswer = sms4RightAnswer;
/*     */   }
/*     */ 
/*     */   public String getCodeAnswerDelim() {
/* 119 */     return this.codeAnswerDelim;
/*     */   }
/*     */ 
/*     */   public void setCodeAnswerDelim(String codeAnswerDelim) {
/* 123 */     this.codeAnswerDelim = codeAnswerDelim;
/*     */   }
/*     */ 
/*     */   public String getSms4AnswerTwice() {
/* 127 */     return this.sms4AnswerTwice;
/*     */   }
/*     */ 
/*     */   public void setSms4AnswerTwice(String sms4AnswerTwice) {
/* 131 */     this.sms4AnswerTwice = sms4AnswerTwice;
/*     */   }
/*     */ 
/*     */   public String getSms4WrongAnswer() {
/* 135 */     return this.sms4WrongAnswer;
/*     */   }
/*     */ 
/*     */   public void setSms4WrongAnswer(String sms4WrongAnswer) {
/* 139 */     this.sms4WrongAnswer = sms4WrongAnswer;
/*     */   }
/*     */ 
/*     */   public boolean isSameQueue() {
/* 143 */     return this.sameQueue;
/*     */   }
/*     */ 
/*     */   public void setSameQueue(boolean sameQueue) {
/* 147 */     this.sameQueue = sameQueue;
/*     */   }
/*     */ 
/*     */   public boolean isSameProduct()
/*     */   {
/* 154 */     return this.sameProduct;
/*     */   }
/*     */ 
/*     */   public void setSameProduct(boolean sameProduct)
/*     */   {
/* 161 */     this.sameProduct = sameProduct;
/*     */   }
/*     */ 
/*     */   public int getDealDemand() {
/* 165 */     return this.dealDemand;
/*     */   }
/*     */ 
/*     */   public void setDealDemand(int dealDemand) {
/* 169 */     this.dealDemand = dealDemand;
/*     */   }
/*     */ 
/*     */   public int getMoQueueListenPort() {
/* 173 */     return this.moQueueListenPort;
/*     */   }
/*     */ 
/*     */   public void setMoQueueListenPort(int moQueueListenPort) {
/* 177 */     this.moQueueListenPort = moQueueListenPort;
/*     */   }
/*     */ 
/*     */   public int getMtQueueListenPort() {
/* 181 */     return this.mtQueueListenPort;
/*     */   }
/*     */ 
/*     */   public void setMtQueueListenPort(int mtQueueListenPort) {
/* 185 */     this.mtQueueListenPort = mtQueueListenPort;
/*     */   }
/*     */ 
/*     */   public int getReportQueueListenPort() {
/* 189 */     return this.reportQueueListenPort;
/*     */   }
/*     */ 
/*     */   public void setReportQueueListenPort(int reportQueueListenPort) {
/* 193 */     this.reportQueueListenPort = reportQueueListenPort;
/*     */   }
/*     */ 
/*     */   public int getUniBusinessListenPort() {
/* 197 */     return this.uniBusinessListenPort;
/*     */   }
/*     */ 
/*     */   public void setUniBusinessListenPort(int uniBusinessListenPort) {
/* 201 */     this.uniBusinessListenPort = uniBusinessListenPort;
/*     */   }
/*     */ 
/*     */   public String getWappushUrl()
/*     */   {
/* 206 */     return this.wappushUrl;
/*     */   }
/*     */ 
/*     */   public void setWappushUrl(String wappushUrl) {
/* 210 */     this.wappushUrl = wappushUrl;
/*     */   }
/*     */ 
/*     */   public int getMmsReqNum()
/*     */   {
/* 218 */     return this.mmsReqNum;
/*     */   }
/*     */ 
/*     */   public void setMmsReqNum(int mmsReqNum)
/*     */   {
/* 225 */     this.mmsReqNum = mmsReqNum;
/*     */   }
/*     */ 
/*     */   public int getMmeReqNum()
/*     */   {
/* 232 */     return this.mmeReqNum;
/*     */   }
/*     */ 
/*     */   public void setMmeReqNum(int mmeReqNum)
/*     */   {
/* 239 */     this.mmeReqNum = mmeReqNum;
/*     */   }
/*     */ 
/*     */   public int getSmsReqNum()
/*     */   {
/* 246 */     return this.smsReqNum;
/*     */   }
/*     */ 
/*     */   public void setSmsReqNum(int smsReqNum)
/*     */   {
/* 253 */     this.smsReqNum = smsReqNum;
/*     */   }
/*     */ 
/*     */   public int getWapReqNum()
/*     */   {
/* 260 */     return this.wapReqNum;
/*     */   }
/*     */ 
/*     */   public void setWapReqNum(int wapReqNum)
/*     */   {
/* 267 */     this.wapReqNum = wapReqNum;
/*     */   }
/*     */ 
/*     */   public boolean isManualWork() {
/* 271 */     return this.manualWork;
/*     */   }
/*     */ 
/*     */   public void setManualWork(boolean manualWork) {
/* 275 */     this.manualWork = manualWork;
/*     */   }
/*     */ 
/*     */   public long getMmsInterval()
/*     */   {
/* 282 */     return this.mmsInterval;
/*     */   }
/*     */ 
/*     */   public void setMmsInterval(long mmsInterval)
/*     */   {
/* 289 */     this.mmsInterval = mmsInterval;
/*     */   }
/*     */ 
/*     */   public long getMmeInterval()
/*     */   {
/* 296 */     return this.mmeInterval;
/*     */   }
/*     */ 
/*     */   public void setMmeInterval(long mmeInterval)
/*     */   {
/* 303 */     this.mmeInterval = mmeInterval;
/*     */   }
/*     */ 
/*     */   public long getSmsInterval()
/*     */   {
/* 310 */     return this.smsInterval;
/*     */   }
/*     */ 
/*     */   public void setSmsInterval(long smsInterval)
/*     */   {
/* 317 */     this.smsInterval = smsInterval;
/*     */   }
/*     */ 
/*     */   public long getWapInterval()
/*     */   {
/* 324 */     return this.wapInterval;
/*     */   }
/*     */ 
/*     */   public void setWapInterval(long wapInterval)
/*     */   {
/* 331 */     this.wapInterval = wapInterval;
/*     */   }
/*     */ 
/*     */   public String getMobileSeq() {
/* 335 */     return this.mobileSeq;
/*     */   }
/*     */ 
/*     */   public void setMobileSeq(String mobileSeq) {
/* 339 */     this.mobileSeq = mobileSeq;
/*     */   }
/*     */ 
/*     */   public String getTelecomSeq() {
/* 343 */     return this.telecomSeq;
/*     */   }
/*     */ 
/*     */   public void setTelecomSeq(String telecomSeq) {
/* 347 */     this.telecomSeq = telecomSeq;
/*     */   }
/*     */ 
/*     */   public String getUnicomSeq() {
/* 351 */     return this.unicomSeq;
/*     */   }
/*     */ 
/*     */   public void setUnicomSeq(String unicomSeq) {
/* 355 */     this.unicomSeq = unicomSeq;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.configbean.CorebizConfig
 * JD-Core Version:    0.6.0
 */