/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */

import java.util.Date;
import java.util.StringTokenizer;

/*     */

/*     */
/*     */ public class MT_ReportMessage extends AbstractMessage
/*     */ {
/*     */   private String[] rcvAddresses;
/*     */   private String status;
/*     */   private String msgType;
/*     */   private String correlator;
/*     */   private String reqId;
/*     */   private String serviceId;
/*     */   private String mtSerType;
/*     */   private String mmsFile;
/*     */   private Date mtDate;
/*     */   private String spid;
/*     */   private String phone;
/*     */   private String content;
/*     */   private String spUrl;
/*     */   private String messageid;
/*     */   private String serviceCode;
/*     */ 
/*     */   public String getPhone()
/*     */   {
/*  49 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String phone) {
/*  53 */     this.phone = phone;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/*  57 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/*  61 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getSpUrl() {
/*  65 */     return this.spUrl;
/*     */   }
/*     */ 
/*     */   public void setSpUrl(String spUrl) {
/*  69 */     this.spUrl = spUrl;
/*     */   }
/*     */ 
/*     */   public String getMessageid() {
/*  73 */     return this.messageid;
/*     */   }
/*     */ 
/*     */   public void setMessageid(String messageid) {
/*  77 */     this.messageid = messageid;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  81 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  85 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getSpid() {
/*  89 */     return this.spid;
/*     */   }
/*     */ 
/*     */   public void setSpid(String spid) {
/*  93 */     this.spid = spid;
/*     */   }
/*     */ 
/*     */   public Date getMtDate() {
/*  97 */     return this.mtDate;
/*     */   }
/*     */ 
/*     */   public void setMtDate(Date mtDate) {
/* 101 */     this.mtDate = mtDate;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try {
/* 107 */       message = super.decodeExtendFields(message);
/*     */ 
/* 109 */       String token = null;
/* 110 */       int pos = 0;
/* 111 */       String name = null;
/* 112 */       String value = null;
/*     */ 
/* 114 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 116 */         token = tokens.nextToken();
/*     */ 
/* 118 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 121 */         pos = token.indexOf(':');
/* 122 */         name = token.substring(0, pos);
/* 123 */         value = token.substring(pos + 1);
/*     */ 
/* 125 */         if (name.equals("status"))
/*     */         {
/* 127 */           if (value.equals("null"))
/* 128 */             setStatus(null);
/*     */           else
/* 130 */             setStatus(value);
/*     */         }
/* 132 */         else if (name.equals("msgType"))
/*     */         {
/* 134 */           if (value.equals("null"))
/* 135 */             setMsgType(null);
/*     */           else
/* 137 */             setMsgType(value);
/*     */         }
/* 139 */         else if (name.equals("correlator"))
/*     */         {
/* 141 */           if (value.equals("null"))
/* 142 */             setCorrelator(null);
/*     */           else
/* 144 */             setCorrelator(value);
/*     */         }
/* 146 */         else if (name.equals("reqId"))
/*     */         {
/* 148 */           if (value.equals("null"))
/* 149 */             setReqId(null);
/*     */           else
/* 151 */             setReqId(value);
/*     */         }
/* 153 */         else if (name.equals("serviceId"))
/*     */         {
/* 155 */           if (value.equals("null"))
/* 156 */             setServiceId("0");
/*     */           else
/* 158 */             setServiceId(value);
/*     */         }
/* 160 */         else if (name.equals("mtSerType"))
/*     */         {
/* 162 */           if (value.equals("null"))
/* 163 */             setMtSerType(null);
/*     */           else
/* 165 */             setMtSerType(value);
/*     */         }
/* 167 */         else if (name.equals("mmsFile"))
/*     */         {
/* 169 */           if (value.equals("null"))
/* 170 */             setMmsFile(null);
/*     */           else
/* 172 */             setMmsFile(value);
/*     */         } else {
/* 174 */           if (!name.equals("mtDate"))
/*     */             continue;
/* 176 */           if (value.equals("null"))
/* 177 */             setMtDate(null);
/*     */           else
/* 179 */             setMtDate(new Date(value));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 184 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 189 */     StringBuffer result = new StringBuffer();
/* 190 */     result.append("MessageType:" + MessageType.MO_MM_MESSAGE.getMessageType() + "\n");
/* 191 */     result.append(super.encodeExtendFields());
/* 192 */     result.append("status:" + this.status + "\n");
/* 193 */     result.append("msgType:" + this.msgType + "\n");
/* 194 */     result.append("correlator" + this.correlator + "\n");
/* 195 */     result.append("reqId" + this.reqId + "\n");
/* 196 */     result.append("serviceId" + this.serviceId + "\n");
/* 197 */     result.append("mtSerType" + this.mtSerType + "\n");
/* 198 */     result.append("mmsFile" + this.mmsFile + "\n");
/* 199 */     result.append("mtDate" + this.mtDate + "\n");
/*     */ 
/* 201 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 206 */     return MessageType.MT_REPORT_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 210 */     MT_ReportMessage message = new MT_ReportMessage();
/* 211 */     String str = message.encodeString();
/* 212 */     System.out.println(str);
/* 213 */     message = new MT_ReportMessage();
/* 214 */     message.decodeString(str);
/*     */ 
/* 216 */     str = message.encodeString();
/* 217 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 221 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/* 229 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getStatus() {
/* 233 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String status) {
/* 237 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 241 */     return this.msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String msgType) {
/* 245 */     this.msgType = msgType;
/*     */   }
/*     */ 
/*     */   public String getCorrelator() {
/* 249 */     return this.correlator;
/*     */   }
/*     */ 
/*     */   public void setCorrelator(String correlator) {
/* 253 */     this.correlator = correlator;
/*     */   }
/*     */ 
/*     */   public String getReqId() {
/* 257 */     return this.reqId;
/*     */   }
/*     */ 
/*     */   public void setReqId(String reqId) {
/* 261 */     this.reqId = reqId;
/*     */   }
/*     */ 
/*     */   public String getMtSerType() {
/* 265 */     return this.mtSerType;
/*     */   }
/*     */ 
/*     */   public void setMtSerType(String mtSerType) {
/* 269 */     this.mtSerType = mtSerType;
/*     */   }
/*     */ 
/*     */   public String getMmsFile() {
/* 273 */     return this.mmsFile;
/*     */   }
/*     */ 
/*     */   public void setMmsFile(String mmsFile) {
/* 277 */     this.mmsFile = mmsFile;
/*     */   }
/*     */ 
/*     */   public void setServiceId(String serviceId) {
/* 281 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String[] getRcvAddresses() {
/* 285 */     return this.rcvAddresses;
/*     */   }
/*     */ 
/*     */   public void setRcvAddresses(String[] rcvAddresses) {
/* 289 */     this.rcvAddresses = rcvAddresses;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_ReportMessage
 * JD-Core Version:    0.6.0
 */