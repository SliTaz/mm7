/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.StringTokenizer;

/*     */
/*     */

/*     */
/*     */ public class MO_SMMessage extends AbstractMessage
/*     */   implements Serializable
/*     */ {
/*  10 */   private static final Logger logger = Logger.getLogger(MO_SMMessage.class);
/*     */   private static final long serialVersionUID = -5482134376137627420L;
/*  14 */   private String sendAddress = null;
/*  15 */   private String vaspId = null;
/*  16 */   private String vasId = null;
/*  17 */   private String serviceCode = null;
/*  18 */   private String opCode = null;
/*  19 */   private String contentCode = null;
/*  20 */   private String smsText = null;
/*  21 */   private String extendOpCode = null;
/*     */   private int serviceId;
/*     */   private String notirySPURL;
/*     */   private Integer Sequence_Number_1;
/*     */   private Integer Sequence_Number_2;
/*     */   private Integer Sequence_Number_3;
/*     */   private byte MessageCoding;
/*     */   private byte TP_pid;
/*     */   private byte TP_udhi;
/*     */   private int ContentLength;
/*     */ 
/*     */   public Integer getSequence_Number_1()
/*     */   {
/*  40 */     return this.Sequence_Number_1;
/*     */   }
/*     */ 
/*     */   public void setSequence_Number_1(Integer sequence_Number_1) {
/*  44 */     this.Sequence_Number_1 = sequence_Number_1;
/*     */   }
/*     */ 
/*     */   public Integer getSequence_Number_2() {
/*  48 */     return this.Sequence_Number_2;
/*     */   }
/*     */ 
/*     */   public void setSequence_Number_2(Integer sequence_Number_2) {
/*  52 */     this.Sequence_Number_2 = sequence_Number_2;
/*     */   }
/*     */ 
/*     */   public Integer getSequence_Number_3() {
/*  56 */     return this.Sequence_Number_3;
/*     */   }
/*     */ 
/*     */   public void setSequence_Number_3(Integer sequence_Number_3) {
/*  60 */     this.Sequence_Number_3 = sequence_Number_3;
/*     */   }
/*     */ 
/*     */   public byte getMessageCoding() {
/*  64 */     return this.MessageCoding;
/*     */   }
/*     */ 
/*     */   public void setMessageCoding(byte messageCoding) {
/*  68 */     this.MessageCoding = messageCoding;
/*     */   }
/*     */ 
/*     */   public byte getTP_pid() {
/*  72 */     return this.TP_pid;
/*     */   }
/*     */ 
/*     */   public void setTP_pid(byte tp_pid) {
/*  76 */     this.TP_pid = tp_pid;
/*     */   }
/*     */ 
/*     */   public byte getTP_udhi() {
/*  80 */     return this.TP_udhi;
/*     */   }
/*     */ 
/*     */   public void setTP_udhi(byte tp_udhi) {
/*  84 */     this.TP_udhi = tp_udhi;
/*     */   }
/*     */ 
/*     */   public int getContentLength() {
/*  88 */     return this.ContentLength;
/*     */   }
/*     */ 
/*     */   public void setContentLength(int contentLength) {
/*  92 */     this.ContentLength = contentLength;
/*     */   }
/*     */ 
/*     */   public String getNotirySPURL() {
/*  96 */     return this.notirySPURL;
/*     */   }
/*     */ 
/*     */   public void setNotirySPURL(String notirySPURL) {
/* 100 */     this.notirySPURL = notirySPURL;
/*     */   }
/*     */ 
/*     */   public int getServiceId() {
/* 104 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId) {
/* 108 */     this.serviceId = serviceId;
/*     */   }
/*     */   public String getContentCode() {
/* 111 */     return this.contentCode;
/*     */   }
/*     */ 
/*     */   public void setContentCode(String contentCode) {
/* 115 */     this.contentCode = contentCode;
/*     */   }
/*     */ 
/*     */   public String getExtendOpCode() {
/* 119 */     return this.extendOpCode;
/*     */   }
/*     */ 
/*     */   public void setExtendOpCode(String extendOpCode)
/*     */   {
/* 124 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */ 
/*     */   public String getSmsText()
/*     */   {
/* 130 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText)
/*     */   {
/* 138 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/* 142 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/* 146 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/* 150 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 154 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 158 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 162 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 166 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 170 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 174 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 178 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/* 185 */       message = super.decodeExtendFields(message);
/*     */ 
/* 187 */       String token = null;
/* 188 */       int pos = 0;
/* 189 */       String name = null;
/* 190 */       String value = null;
/*     */ 
/* 192 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 194 */         token = tokens.nextToken();
/*     */ 
/* 196 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 199 */         pos = token.indexOf(':');
/* 200 */         name = token.substring(0, pos);
/* 201 */         value = token.substring(pos + 1);
/*     */ 
/* 203 */         if (name.equals("sendAddress"))
/*     */         {
/* 205 */           if (value.equals("null"))
/* 206 */             setSendAddress(null);
/*     */           else
/* 208 */             setSendAddress(value);
/*     */         }
/* 210 */         else if (name.equals("vaspId"))
/*     */         {
/* 212 */           if (value.equals("null"))
/*     */           {
/* 214 */             setVaspId(null);
/*     */           }
/*     */           else
/*     */           {
/* 218 */             setVaspId(value);
/*     */           }
/*     */         }
/* 221 */         else if (name.equals("vasId")) {
/* 222 */           if (value.equals("null"))
/* 223 */             setVasId(null);
/*     */           else
/* 225 */             setVasId(value);
/*     */         }
/* 227 */         else if (name.equals("serviceCode")) {
/* 228 */           if (value.equals("null"))
/* 229 */             setServiceCode(null);
/*     */           else
/* 231 */             setServiceCode(value);
/*     */         }
/* 233 */         else if (name.equals("opCode")) {
/* 234 */           if (value.equals("null"))
/* 235 */             setOpCode(null);
/*     */           else
/* 237 */             setOpCode(value);
/*     */         }
/* 239 */         else if (name.equals("linkId")) {
/* 240 */           if (value.equals("null"))
/* 241 */             setLinkId(null);
/*     */           else
/* 243 */             setLinkId(value);
/*     */         }
/* 245 */         else if (name.equals("smsText")) {
/* 246 */           if (value.equals("null"))
/* 247 */             setSmsText(null);
/*     */           else
/* 249 */             setSmsText(value);
/*     */         }
/* 251 */         else if (name.equals("extendOpCode")) {
/* 252 */           if (value.equals("null"))
/* 253 */             setExtendOpCode(null);
/*     */           else
/* 255 */             setExtendOpCode(value);
/*     */         }
/* 257 */         else if (name.equals("serviceId")) {
/* 258 */           if (value.equals("null"))
/* 259 */             setServiceId(-1);
/*     */           else
/* 261 */             setServiceId(Integer.parseInt(value));
/*     */         }
/* 263 */         else if (name.equals("contentCode"))
/* 264 */           if (value.equals("null"))
/* 265 */             setContentCode(null);
/*     */           else
/* 267 */             setContentCode(value);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 272 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 277 */     StringBuffer result = new StringBuffer();
/* 278 */     result.append("MessageType:" + MessageType.MO_SM_MESSAGE.getMessageType() + "\n");
/* 279 */     result.append(super.encodeExtendFields());
/*     */ 
/* 281 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 282 */     result.append("vaspId:" + this.vaspId + "\n");
/* 283 */     result.append("vasId:" + this.vasId + "\n");
/* 284 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 285 */     result.append("opCode:" + this.opCode + "\n");
/* 286 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 287 */     result.append("smsText:" + this.smsText + "\n");
/* 288 */     result.append("serviceId:" + this.serviceId + "\n");
/* 289 */     result.append("contentCode:" + this.contentCode);
/* 290 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 294 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 298 */     return MessageType.MO_SM_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 302 */     MO_SMMessage message = new MO_SMMessage();
/* 303 */     String str = message.encodeString();
/* 304 */     System.out.println(str);
/* 305 */     message = new MO_SMMessage();
/* 306 */     message.decodeString(str);
/*     */ 
/* 308 */     str = message.encodeString();
/* 309 */     System.out.println(str);
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_SMMessage
 * JD-Core Version:    0.6.0
 */