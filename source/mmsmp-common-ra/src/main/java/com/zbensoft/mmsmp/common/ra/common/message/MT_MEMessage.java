/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MT_MEMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -4320322700818136659L;
/*     */   private String sendAddress;
/*     */   private String[] rcvAddresses;
/*     */   private String chargeParty;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*  24 */   private boolean adaptFlag = false;
/*  25 */   private String mmFile = null;
/*     */   private String subject;
/*     */   private String mtTranId;
/*  28 */   private boolean groupSend = false;
/*     */   private int serviceId;
/*     */   private int sendTimes;
/*     */   private String timeStamp;
/*     */   private String sendType;
/*  33 */   private boolean needReceipt = true;
/*     */ 
/*     */   public boolean isNeedReceipt() {
/*  36 */     return this.needReceipt;
/*     */   }
/*     */ 
/*     */   public void setNeedReceipt(boolean needReceipt) {
/*  40 */     this.needReceipt = needReceipt;
/*     */   }
/*     */ 
/*     */   public String getSendType() {
/*  44 */     return this.sendType;
/*     */   }
/*     */ 
/*     */   public void setSendType(String sendType) {
/*  48 */     this.sendType = sendType;
/*     */   }
/*     */ 
/*     */   public String getTimeStamp() {
/*  52 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(String timeStamp) {
/*  56 */     this.timeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public int getSendTimes() {
/*  60 */     return this.sendTimes;
/*     */   }
/*     */ 
/*     */   public void setSendTimes(int sendTimes) {
/*  64 */     this.sendTimes = sendTimes;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  69 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public String getMtTranId()
/*     */   {
/*  83 */     return this.mtTranId;
/*     */   }
/*     */ 
/*     */   public void setMtTranId(String mtTranId)
/*     */   {
/*  91 */     this.mtTranId = mtTranId;
/*     */   }
/*     */ 
/*     */   public String getMmFile()
/*     */   {
/*  99 */     return this.mmFile;
/*     */   }
/*     */ 
/*     */   public void setMmFile(String mmFile)
/*     */   {
/* 107 */     this.mmFile = mmFile;
/*     */   }
/*     */ 
/*     */   public boolean isAdaptFlag() {
/* 111 */     return this.adaptFlag;
/*     */   }
/*     */ 
/*     */   public void setAdaptFlag(boolean adaptFlag)
/*     */   {
/* 119 */     this.adaptFlag = adaptFlag;
/*     */   }
/*     */ 
/*     */   public String getChargeParty() {
/* 123 */     return this.chargeParty;
/*     */   }
/*     */ 
/*     */   public void setChargeParty(String chargeParty)
/*     */   {
/* 130 */     this.chargeParty = chargeParty;
/*     */   }
/*     */ 
/*     */   public String getOpCode()
/*     */   {
/* 136 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/* 140 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/* 144 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 148 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 152 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 156 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 160 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 164 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 168 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 172 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/* 180 */       message = super.decodeExtendFields(message);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 184 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */ 
/* 187 */     String token = null;
/* 188 */     int pos = 0;
/* 189 */     String name = null;
/* 190 */     String value = null;
/*     */ 
/* 192 */     for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */     {
/* 194 */       token = tokens.nextToken();
/*     */ 
/* 196 */       if (token.startsWith("MessageType")) {
/*     */         continue;
/*     */       }
/* 199 */       pos = token.indexOf(':');
/* 200 */       name = token.substring(0, pos);
/* 201 */       value = token.substring(pos + 1);
/* 202 */       if (name.equals("Subject"))
/*     */       {
/* 204 */         if (value.equals("null"))
/* 205 */           setSubject("null");
/*     */         else
/* 207 */           setSubject(value);
/*     */       }
/* 209 */       else if (name.equals("sendAddress"))
/*     */       {
/* 211 */         if (value.equals("null"))
/* 212 */           setSendAddress(null);
/*     */         else
/* 214 */           setSendAddress(value);
/*     */       }
/* 216 */       else if (name.equals("vaspId"))
/*     */       {
/* 218 */         if (value.equals("null"))
/* 219 */           setVaspId(null);
/*     */         else
/* 221 */           setVaspId(value);
/*     */       }
/* 223 */       else if (name.equals("vasId"))
/*     */       {
/* 225 */         if (value.equals("null"))
/* 226 */           setVasId(null);
/*     */         else
/* 228 */           setVasId(value);
/*     */       }
/* 230 */       else if (name.equals("serviceCode"))
/*     */       {
/* 232 */         if (value.equals("null"))
/* 233 */           setServiceCode(null);
/*     */         else
/* 235 */           setServiceCode(value);
/*     */       }
/* 237 */       else if (name.equals("opCode")) {
/* 238 */         if (value.equals("null"))
/* 239 */           setOpCode(null);
/*     */         else
/* 241 */           setOpCode(value);
/*     */       }
/* 243 */       else if (name.equals("chargeParty")) {
/* 244 */         if (value.equals("null"))
/* 245 */           setChargeParty(null);
/*     */         else
/* 247 */           setChargeParty(value);
/*     */       }
/* 249 */       else if (name.equals("mmFile")) {
/* 250 */         if (value.equals("null"))
/* 251 */           setMmFile(null);
/*     */         else
/* 253 */           setMmFile(value);
/*     */       }
/* 255 */       else if (name.equals("adaptFlag")) {
/* 256 */         if ((value.equals("null")) || (value.equals("false")))
/* 257 */           setAdaptFlag(false);
/*     */         else
/* 259 */           setAdaptFlag(true);
/*     */       }
/* 261 */       else if (name.equals("mtTranId")) {
/* 262 */         if (value.equals("null"))
/* 263 */           setMtTranId(null);
/*     */         else
/* 265 */           setMtTranId(value);
/*     */       }
/* 267 */       else if (name.equals("serviceId")) {
/* 268 */         if (value.equals("null"))
/* 269 */           setServiceId(-1);
/*     */         else
/* 271 */           setServiceId(Integer.parseInt(value));
/*     */       }
/* 273 */       else if (name.equals("sendTimes"))
/* 274 */         if (value.equals("null"))
/* 275 */           setSendTimes(0);
/*     */         else
/* 277 */           setSendTimes(Integer.parseInt(value));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 285 */     if (this.mtTranId == null)
/* 286 */       this.mtTranId = new Date().getTime();
/* 287 */     StringBuffer result = new StringBuffer();
/*     */ 
/* 289 */     result.append("MessageType:" + MessageType.MT_MM_MESSAGE.getMessageType() + "\n");
/* 290 */     result.append(super.encodeExtendFields());
/* 291 */     result.append("Subject:" + this.subject + "\n");
/* 292 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 293 */     result.append("chargeParty:" + this.chargeParty + "\n");
/* 294 */     result.append("mmFile:" + this.mmFile + "\n");
/* 295 */     result.append("vaspId:" + this.vaspId + "\n");
/* 296 */     result.append("vasId:" + this.vasId + "\n");
/* 297 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 298 */     result.append("opCode:" + this.opCode + "\n");
/* 299 */     result.append("adaptFlag:" + (this.adaptFlag ? "true" : "false") + "\n");
/* 300 */     result.append("mtTranId:" + this.mtTranId + "\n");
/* 301 */     result.append("serviceId:" + this.serviceId + "\n");
/* 302 */     result.append("sendTimes:" + this.sendTimes);
/*     */ 
/* 304 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 308 */     return MessageType.MT_MM_MESSAGE;
/*     */   }
/*     */ 
/*     */   public String getSubject() {
/* 312 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject)
/*     */   {
/* 319 */     this.subject = subject;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 323 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public boolean isGroupSend()
/*     */   {
/* 328 */     return this.groupSend;
/*     */   }
/*     */ 
/*     */   public void setGroupSend(boolean groupSend)
/*     */   {
/* 336 */     this.groupSend = groupSend;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId) {
/* 340 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String[] getRcvAddresses() {
/* 344 */     return this.rcvAddresses;
/*     */   }
/*     */ 
/*     */   public void setRcvAddresses(String[] rcvAddresses) {
/* 348 */     this.rcvAddresses = rcvAddresses;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_MEMessage
 * JD-Core Version:    0.6.0
 */