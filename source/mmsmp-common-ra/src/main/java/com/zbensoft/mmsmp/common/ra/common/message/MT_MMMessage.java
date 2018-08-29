/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MT_MMMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -4320322700818136659L;
/*     */   private String sendAddress;
/*     */   private String[] rcvAddresses;
/*     */   private String chargeParty;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*  18 */   private boolean adaptFlag = false;
/*  19 */   private String mmFile = null;
/*     */   private String subject;
/*     */   private String mtTranId;
/*     */   private String mtUrl;
/*  23 */   private boolean groupSend = false;
/*     */   private int serviceId;
/*     */   private int sendTimes;
/*     */   private String timeStamp;
/*     */   private String sendType;
/*  28 */   private boolean needReceipt = true;
/*     */ 
/*     */   public boolean isNeedReceipt() {
/*  31 */     return this.needReceipt;
/*     */   }
/*     */ 
/*     */   public void setNeedReceipt(boolean needReceipt) {
/*  35 */     this.needReceipt = needReceipt;
/*     */   }
/*     */ 
/*     */   public String getSendType() {
/*  39 */     return this.sendType;
/*     */   }
/*     */ 
/*     */   public void setSendType(String sendType) {
/*  43 */     this.sendType = sendType;
/*     */   }
/*     */ 
/*     */   public String getTimeStamp() {
/*  47 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(String timeStamp) {
/*  51 */     this.timeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public int getSendTimes() {
/*  55 */     return this.sendTimes;
/*     */   }
/*     */ 
/*     */   public void setSendTimes(int sendTimes) {
/*  59 */     this.sendTimes = sendTimes;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  64 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public String getMtTranId()
/*     */   {
/*  78 */     return this.mtTranId;
/*     */   }
/*     */ 
/*     */   public void setMtTranId(String mtTranId)
/*     */   {
/*  86 */     this.mtTranId = mtTranId;
/*     */   }
/*     */ 
/*     */   public String getMmFile()
/*     */   {
/*  94 */     return this.mmFile;
/*     */   }
/*     */ 
/*     */   public void setMmFile(String mmFile)
/*     */   {
/* 102 */     this.mmFile = mmFile;
/*     */   }
/*     */ 
/*     */   public boolean isAdaptFlag() {
/* 106 */     return this.adaptFlag;
/*     */   }
/*     */ 
/*     */   public void setAdaptFlag(boolean adaptFlag)
/*     */   {
/* 114 */     this.adaptFlag = adaptFlag;
/*     */   }
/*     */ 
/*     */   public String getChargeParty() {
/* 118 */     return this.chargeParty;
/*     */   }
/*     */ 
/*     */   public void setChargeParty(String chargeParty)
/*     */   {
/* 125 */     this.chargeParty = chargeParty;
/*     */   }
/*     */ 
/*     */   public String getOpCode()
/*     */   {
/* 131 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/* 135 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/* 139 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 143 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 147 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 151 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 155 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 159 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 163 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 167 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message)
/*     */     throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/* 175 */       message = super.decodeExtendFields(message);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 179 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */ 
/* 182 */     String token = null;
/* 183 */     int pos = 0;
/* 184 */     String name = null;
/* 185 */     String value = null;
/*     */ 
/* 187 */     for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */     {
/* 189 */       token = tokens.nextToken();
/*     */ 
/* 191 */       if (token.startsWith("MessageType")) {
/*     */         continue;
/*     */       }
/* 194 */       pos = token.indexOf(':');
/* 195 */       name = token.substring(0, pos);
/* 196 */       value = token.substring(pos + 1);
/* 197 */       if (name.equals("Subject"))
/*     */       {
/* 199 */         if (value.equals("null"))
/* 200 */           setSubject("null");
/*     */         else
/* 202 */           setSubject(value);
/*     */       }
/* 204 */       else if (name.equals("sendAddress"))
/*     */       {
/* 206 */         if (value.equals("null"))
/* 207 */           setSendAddress(null);
/*     */         else
/* 209 */           setSendAddress(value);
/*     */       }
/* 211 */       else if (name.equals("vaspId"))
/*     */       {
/* 213 */         if (value.equals("null"))
/* 214 */           setVaspId(null);
/*     */         else
/* 216 */           setVaspId(value);
/*     */       }
/* 218 */       else if (name.equals("vasId"))
/*     */       {
/* 220 */         if (value.equals("null"))
/* 221 */           setVasId(null);
/*     */         else
/* 223 */           setVasId(value);
/*     */       }
/* 225 */       else if (name.equals("serviceCode"))
/*     */       {
/* 227 */         if (value.equals("null"))
/* 228 */           setServiceCode(null);
/*     */         else
/* 230 */           setServiceCode(value);
/*     */       }
/* 232 */       else if (name.equals("opCode")) {
/* 233 */         if (value.equals("null"))
/* 234 */           setOpCode(null);
/*     */         else
/* 236 */           setOpCode(value);
/*     */       }
/* 238 */       else if (name.equals("chargeParty")) {
/* 239 */         if (value.equals("null"))
/* 240 */           setChargeParty(null);
/*     */         else
/* 242 */           setChargeParty(value);
/*     */       }
/* 244 */       else if (name.equals("mmFile")) {
/* 245 */         if (value.equals("null"))
/* 246 */           setMmFile(null);
/*     */         else
/* 248 */           setMmFile(value);
/*     */       }
/* 250 */       else if (name.equals("adaptFlag")) {
/* 251 */         if ((value.equals("null")) || (value.equals("false")))
/* 252 */           setAdaptFlag(false);
/*     */         else
/* 254 */           setAdaptFlag(true);
/*     */       }
/* 256 */       else if (name.equals("mtTranId")) {
/* 257 */         if (value.equals("null"))
/* 258 */           setMtTranId(null);
/*     */         else
/* 260 */           setMtTranId(value);
/*     */       }
/* 262 */       else if (name.equals("serviceId")) {
/* 263 */         if (value.equals("null"))
/* 264 */           setServiceId(-1);
/*     */         else
/* 266 */           setServiceId(Integer.parseInt(value));
/*     */       }
/* 268 */       else if (name.equals("sendTimes"))
/* 269 */         if (value.equals("null"))
/* 270 */           setSendTimes(0);
/*     */         else
/* 272 */           setSendTimes(Integer.parseInt(value));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 280 */     if (this.mtTranId == null)
/* 281 */       this.mtTranId = new Date().getTime();
/* 282 */     StringBuffer result = new StringBuffer();
/*     */ 
/* 284 */     result.append("MessageType:" + MessageType.MT_MM_MESSAGE.getMessageType() + "\n");
/* 285 */     result.append(super.encodeExtendFields());
/* 286 */     result.append("Subject:" + this.subject + "\n");
/* 287 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 288 */     result.append("chargeParty:" + this.chargeParty + "\n");
/* 289 */     result.append("mmFile:" + this.mmFile + "\n");
/* 290 */     result.append("vaspId:" + this.vaspId + "\n");
/* 291 */     result.append("vasId:" + this.vasId + "\n");
/* 292 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 293 */     result.append("opCode:" + this.opCode + "\n");
/* 294 */     result.append("adaptFlag:" + (this.adaptFlag ? "true" : "false") + "\n");
/* 295 */     result.append("mtTranId:" + this.mtTranId + "\n");
/* 296 */     result.append("serviceId:" + this.serviceId + "\n");
/* 297 */     result.append("sendTimes:" + this.sendTimes);
/*     */ 
/* 299 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 303 */     return MessageType.MT_MM_MESSAGE;
/*     */   }
/*     */ 
/*     */   public String getSubject() {
/* 307 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject)
/*     */   {
/* 314 */     this.subject = subject;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 318 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public boolean isGroupSend()
/*     */   {
/* 323 */     return this.groupSend;
/*     */   }
/*     */ 
/*     */   public void setGroupSend(boolean groupSend)
/*     */   {
/* 331 */     this.groupSend = groupSend;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId) {
/* 335 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String[] getRcvAddresses() {
/* 339 */     return this.rcvAddresses;
/*     */   }
/*     */ 
/*     */   public void setRcvAddresses(String[] rcvAddresses) {
/* 343 */     this.rcvAddresses = rcvAddresses;
/*     */   }
/*     */ 
/*     */   public String getMtUrl() {
/* 347 */     return this.mtUrl;
/*     */   }
/*     */ 
/*     */   public void setMtUrl(String mtUrl) {
/* 351 */     this.mtUrl = mtUrl;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_MMMessage
 * JD-Core Version:    0.6.0
 */