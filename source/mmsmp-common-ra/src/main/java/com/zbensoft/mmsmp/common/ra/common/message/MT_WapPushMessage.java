/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MT_WapPushMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = 7336347501534825303L;
/*     */   private String sendAddress;
/*     */   private String[] rcvAddresses;
/*     */   private String chargeParty;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*     */   private String pushText;
/*     */   private String pushUrl;
/*     */   private String mtTranId;
/*     */   private boolean groupSend;
/*     */   private String timeStamp;
/*     */   private String mtUrl;
/*     */   private int serviceId;
/*  28 */   private boolean needReceipt = true;
/*     */ 
/*     */   public void setServiceId(int serviceId) {
/*  31 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String getTimeStamp() {
/*  35 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(String timeStamp) {
/*  39 */     this.timeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public boolean isGroupSend()
/*     */   {
/*  49 */     return this.groupSend;
/*     */   }
/*     */ 
/*     */   public void setGroupSend(boolean groupSend)
/*     */   {
/*  58 */     this.groupSend = groupSend;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  69 */     return this.serviceId;
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
/*     */   public String getPushUrl() {
/*  90 */     return this.pushUrl;
/*     */   }
/*     */ 
/*     */   public void setPushUrl(String pushUrl) {
/*  94 */     this.pushUrl = pushUrl;
/*     */   }
/*     */ 
/*     */   public String getPushText() {
/*  98 */     return this.pushText;
/*     */   }
/*     */ 
/*     */   public void setPushText(String pushText) {
/* 102 */     this.pushText = pushText;
/*     */   }
/*     */ 
/*     */   public String getChargeParty() {
/* 106 */     return this.chargeParty;
/*     */   }
/*     */ 
/*     */   public void setChargeParty(String chargeParty) {
/* 110 */     this.chargeParty = chargeParty;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/* 114 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/* 118 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/* 122 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 126 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 130 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 134 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 138 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 142 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 146 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 150 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/* 157 */       message = super.decodeExtendFields(message);
/*     */ 
/* 159 */       String token = null;
/* 160 */       int pos = 0;
/* 161 */       String name = null;
/* 162 */       String value = null;
/*     */ 
/* 164 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 166 */         token = tokens.nextToken();
/*     */ 
/* 168 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 171 */         pos = token.indexOf(':');
/* 172 */         name = token.substring(0, pos);
/* 173 */         value = token.substring(pos + 1);
/*     */ 
/* 175 */         if (name.equals("sendAddress"))
/*     */         {
/* 177 */           if (value.equals("null"))
/* 178 */             setSendAddress(null);
/*     */           else
/* 180 */             setSendAddress(value);
/*     */         }
/* 182 */         else if (name.equals("vaspId"))
/*     */         {
/* 184 */           if (value.equals("null"))
/* 185 */             setVaspId(null);
/*     */           else
/* 187 */             setVaspId(value);
/*     */         }
/* 189 */         else if (name.equals("vasId"))
/*     */         {
/* 191 */           if (value.equals("null"))
/* 192 */             setVasId(null);
/*     */           else
/* 194 */             setVasId(value);
/*     */         }
/* 196 */         else if (name.equals("serviceCode"))
/*     */         {
/* 198 */           if (value.equals("null"))
/* 199 */             setServiceCode(null);
/*     */           else
/* 201 */             setServiceCode(value);
/*     */         }
/* 203 */         else if (name.equals("opCode"))
/*     */         {
/* 205 */           if (value.equals("null"))
/* 206 */             setOpCode(null);
/*     */           else
/* 208 */             setOpCode(value);
/*     */         }
/* 210 */         else if (name.equals("chargeParty"))
/*     */         {
/* 212 */           if (value.equals("null"))
/* 213 */             setChargeParty(null);
/*     */           else
/* 215 */             setChargeParty(value);
/*     */         }
/* 217 */         else if (name.equals("pushUrl"))
/*     */         {
/* 219 */           if (value.equals("null"))
/* 220 */             setPushText(null);
/*     */           else
/* 222 */             setPushUrl(value);
/*     */         }
/* 224 */         else if (name.equals("pushText"))
/*     */         {
/* 226 */           if (value.equals("null"))
/* 227 */             setPushText(null);
/*     */           else
/* 229 */             setPushText(value);
/*     */         } else {
/* 231 */           if (!name.equals("mtTranId"))
/*     */             continue;
/* 233 */           if (value.equals("null"))
/* 234 */             setMtTranId(null);
/*     */           else
/* 236 */             setMtTranId(value);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 242 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 248 */     if (this.mtTranId == null) {
/* 249 */       this.mtTranId = new Date().getTime();
/*     */     }
/* 251 */     StringBuffer result = new StringBuffer();
/* 252 */     result.append("MessageType:" + MessageType.MT_WAPPUSH_MESSAGE.getMessageType() + "\n");
/* 253 */     result.append(super.encodeExtendFields());
/*     */ 
/* 255 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 256 */     result.append("chargeParty:" + this.chargeParty + "\n");
/* 257 */     result.append("pushText:" + this.pushText + "\n");
/* 258 */     result.append("vaspId:" + this.vaspId + "\n");
/* 259 */     result.append("vasId:" + this.vasId + "\n");
/* 260 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 261 */     result.append("opCode:" + this.opCode + "\n");
/* 262 */     result.append("pushUrl:" + this.pushUrl + "\n");
/* 263 */     result.append("mtTranId:" + this.mtTranId);
/* 264 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 268 */     return MessageType.MT_WAPPUSH_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception
/*     */   {
/* 273 */     MT_WapPushMessage message = new MT_WapPushMessage();
/* 274 */     String str = message.encodeString();
/* 275 */     System.out.println(str);
/* 276 */     message = new MT_WapPushMessage();
/* 277 */     message.decodeString(str);
/*     */ 
/* 279 */     str = message.encodeString();
/* 280 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 287 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public String[] getRcvAddresses() {
/* 291 */     return this.rcvAddresses;
/*     */   }
/*     */ 
/*     */   public void setRcvAddresses(String[] rcvAddresses) {
/* 295 */     this.rcvAddresses = rcvAddresses;
/*     */   }
/*     */ 
/*     */   public String getMtUrl() {
/* 299 */     return this.mtUrl;
/*     */   }
/*     */ 
/*     */   public void setMtUrl(String mtUrl) {
/* 303 */     this.mtUrl = mtUrl;
/*     */   }
/*     */ 
/*     */   public boolean isNeedReceipt() {
/* 307 */     return this.needReceipt;
/*     */   }
/*     */ 
/*     */   public void setNeedReceipt(boolean needReceipt) {
/* 311 */     this.needReceipt = needReceipt;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_WapPushMessage
 * JD-Core Version:    0.6.0
 */