/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */

import java.io.Serializable;
import java.util.StringTokenizer;

/*     */

/*     */
/*     */ public class MO_ReportMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7020548480001164266L;
/*     */   private String sendAddress;
/*     */   private String status;
/*     */   private String msgType;
/*     */   private String correlator;
/*     */   private String content;
/*     */   private String reportUrl;
/*     */   private String serviceCode;
/*     */   private String strPhone;
/*     */   private String messageId;
/*     */   private String recipient;
/*     */   private String transactionID;
/*     */ 
/*     */   public String getServiceCode()
/*     */   {
/*  42 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  46 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getStrPhone() {
/*  50 */     return this.strPhone;
/*     */   }
/*     */ 
/*     */   public void setStrPhone(String strPhone) {
/*  54 */     this.strPhone = strPhone;
/*     */   }
/*     */ 
/*     */   public String getMessageId() {
/*  58 */     return this.messageId;
/*     */   }
/*     */ 
/*     */   public void setMessageId(String messageId) {
/*  62 */     this.messageId = messageId;
/*     */   }
/*     */ 
/*     */   public String getRecipient() {
/*  66 */     return this.recipient;
/*     */   }
/*     */ 
/*     */   public void setRecipient(String recipient) {
/*  70 */     this.recipient = recipient;
/*     */   }
/*     */ 
/*     */   public String getTransactionID() {
/*  74 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */   public void setTransactionID(String transactionID) {
/*  78 */     this.transactionID = transactionID;
/*     */   }
/*     */ 
/*     */   public String getReportUrl() {
/*  82 */     return this.reportUrl;
/*     */   }
/*     */ 
/*     */   public void setReportUrl(String reportUrl) {
/*  86 */     this.reportUrl = reportUrl;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  90 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  94 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try {
/* 100 */       message = super.decodeExtendFields(message);
/*     */ 
/* 102 */       String token = null;
/* 103 */       int pos = 0;
/* 104 */       String name = null;
/* 105 */       String value = null;
/*     */ 
/* 107 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 109 */         token = tokens.nextToken();
/*     */ 
/* 111 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 114 */         pos = token.indexOf(':');
/* 115 */         name = token.substring(0, pos);
/* 116 */         value = token.substring(pos + 1);
/*     */ 
/* 118 */         if (name.equals("sendAddress"))
/*     */         {
/* 120 */           if (value.equals("null"))
/* 121 */             setSendAddress(null);
/*     */           else
/* 123 */             setSendAddress(value);
/*     */         }
/* 125 */         else if (name.equals("status"))
/*     */         {
/* 127 */           if (value.equals("null"))
/* 128 */             setStatus(null);
/*     */           else
/* 130 */             setStatus(value);
/*     */         } else {
/* 132 */           if (!name.equals("msgType"))
/*     */             continue;
/* 134 */           if (value.equals("null"))
/* 135 */             setMsgType(null);
/*     */           else
/* 137 */             setMsgType(value);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 142 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 147 */     StringBuffer result = new StringBuffer();
/* 148 */     result.append("MessageType:" + MessageType.MO_MM_MESSAGE.getMessageType() + "\n");
/* 149 */     result.append(super.encodeExtendFields());
/* 150 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 151 */     result.append("status:" + this.status + "\n");
/* 152 */     result.append("msgType:" + this.msgType + "\n");
/* 153 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 158 */     return MessageType.MO_REPORT_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 162 */     MO_ReportMessage message = new MO_ReportMessage();
/* 163 */     String str = message.encodeString();
/* 164 */     System.out.println(str);
/* 165 */     message = new MO_ReportMessage();
/* 166 */     message.decodeString(str);
/*     */ 
/* 168 */     str = message.encodeString();
/* 169 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 174 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 182 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/* 186 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 190 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 194 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 198 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 202 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/* 206 */     this.msgType = msgType;
/*     */   }
/*     */ 
/*     */   public String getCorrelator() {
/* 210 */     return this.correlator;
/*     */   }
/*     */ 
/*     */   public void setCorrelator(String correlator) {
/* 214 */     this.correlator = correlator;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_ReportMessage
 * JD-Core Version:    0.6.0
 */