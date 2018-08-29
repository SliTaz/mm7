/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MT_SMMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -1075075188946250645L;
/*     */   private String sendAddress;
/*     */   private String[] rcvAddresses;
/*     */   private String chargeParty;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*     */   private String smsText;
/*     */   private String spPassword;
/*     */   private String mtTranId;
/*     */   private boolean groupSend;
/*     */   private int serviceId;
/*     */   private String timeStamp;
/*  25 */   private boolean needReceipt = true;
/*     */   private String mtUrl;
/*     */ 
/*     */   public boolean isNeedReceipt()
/*     */   {
/*  29 */     return this.needReceipt;
/*     */   }
/*     */ 
/*     */   public void setNeedReceipt(boolean needReceipt)
/*     */   {
/*  34 */     this.needReceipt = needReceipt;
/*     */   }
/*     */ 
/*     */   public boolean isGroupSend()
/*     */   {
/*  39 */     return this.groupSend;
/*     */   }
/*     */ 
/*     */   public void setGroupSend(boolean groupSend)
/*     */   {
/*  44 */     this.groupSend = groupSend;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  49 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public String getMtTranId()
/*     */   {
/*  54 */     return this.mtTranId;
/*     */   }
/*     */ 
/*     */   public void setMtTranId(String mtTranId)
/*     */   {
/*  59 */     this.mtTranId = mtTranId;
/*     */   }
/*     */ 
/*     */   public String getSpPassword()
/*     */   {
/*  64 */     return this.spPassword;
/*     */   }
/*     */ 
/*     */   public void setSpPassword(String spPassword) {
/*  68 */     this.spPassword = spPassword;
/*     */   }
/*     */ 
/*     */   public String getSmsText()
/*     */   {
/*  73 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText) {
/*  77 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public String getChargeParty() {
/*  81 */     return this.chargeParty;
/*     */   }
/*     */ 
/*     */   public void setChargeParty(String chargeParty) {
/*  85 */     this.chargeParty = chargeParty;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/*  89 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/*  93 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/*  97 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/* 101 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/* 105 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 109 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 113 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 117 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 121 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 125 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException {
/*     */     try {
/* 130 */       message = super.decodeExtendFields(message);
/*     */ 
/* 132 */       String token = null;
/* 133 */       int pos = 0;
/* 134 */       String name = null;
/* 135 */       String value = null;
/*     */ 
/* 137 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 139 */         token = tokens.nextToken();
/*     */ 
/* 141 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 144 */         pos = token.indexOf(':');
/* 145 */         name = token.substring(0, pos);
/* 146 */         value = token.substring(pos + 1);
/*     */ 
/* 148 */         if (name.equals("sendAddress")) {
/* 149 */           if (value.equals("null"))
/* 150 */             setSendAddress(null);
/*     */           else
/* 152 */             setSendAddress(value);
/*     */         }
/* 154 */         else if (name.equals("vaspId")) {
/* 155 */           if (value.equals("null"))
/* 156 */             setVaspId(null);
/*     */           else
/* 158 */             setVaspId(value);
/*     */         }
/* 160 */         else if (name.equals("vasId")) {
/* 161 */           if (value.equals("null"))
/* 162 */             setVasId(null);
/*     */           else
/* 164 */             setVasId(value);
/*     */         }
/* 166 */         else if (name.equals("serviceCode")) {
/* 167 */           if (value.equals("null"))
/* 168 */             setServiceCode(null);
/*     */           else
/* 170 */             setServiceCode(value);
/*     */         }
/* 172 */         else if (name.equals("opCode")) {
/* 173 */           if (value.equals("null"))
/* 174 */             setOpCode(null);
/*     */           else
/* 176 */             setOpCode(value);
/*     */         }
/* 178 */         else if (name.equals("chargeParty")) {
/* 179 */           if (value.equals("null"))
/* 180 */             setChargeParty(null);
/*     */           else
/* 182 */             setChargeParty(value);
/*     */         }
/* 184 */         else if (name.equals("smsText")) {
/* 185 */           if (value.equals("null"))
/* 186 */             setSmsText(null);
/*     */           else
/* 188 */             setSmsText(value);
/*     */         }
/* 190 */         else if (name.equals("linkId")) {
/* 191 */           if (value.equals("null"))
/* 192 */             setLinkId(null);
/*     */           else
/* 194 */             setLinkId(value);
/*     */         }
/* 196 */         else if (name.equals("mtTranId")) {
/* 197 */           if (value.equals("null"))
/* 198 */             setMtTranId(null);
/*     */           else
/* 200 */             setMtTranId(value);
/*     */         }
/* 202 */         else if (name.equals("serviceId"))
/* 203 */           if (value.equals("null"))
/* 204 */             setServiceId(-1);
/*     */           else
/* 206 */             setServiceId(Integer.parseInt(value));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 211 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 217 */     if (this.mtTranId == null) {
/* 218 */       this.mtTranId = new Date().getTime();
/*     */     }
/* 220 */     StringBuffer result = new StringBuffer();
/* 221 */     result.append("MessageType:" + MessageType.MT_SM_MESSAGE.getMessageType() + "\n");
/* 222 */     result.append(super.encodeExtendFields());
/* 223 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 224 */     result.append("chargeParty:" + this.chargeParty + "\n");
/* 225 */     result.append("smsText:" + this.smsText + "\n");
/* 226 */     result.append("vaspId:" + this.vaspId + "\n");
/* 227 */     result.append("vasId:" + this.vasId + "\n");
/* 228 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 229 */     result.append("opCode:" + this.opCode + "\n");
/* 230 */     result.append("mtTranId:" + this.mtTranId + "\n");
/* 231 */     result.append("serviceId:" + this.serviceId);
/* 232 */     result.append("needReceipt:  " + this.needReceipt);
/* 233 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 237 */     return MessageType.MT_SM_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 241 */     MT_SMMessage message = new MT_SMMessage();
/* 242 */     String str = message.encodeString();
/* 243 */     System.out.println(str);
/* 244 */     message = new MT_SMMessage();
/* 245 */     message.decodeString(str);
/*     */ 
/* 247 */     str = message.encodeString();
/* 248 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 253 */     return encodeString();
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId)
/*     */   {
/* 258 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String getTimeStamp()
/*     */   {
/* 263 */     return this.timeStamp;
/*     */   }
/*     */ 
/*     */   public void setTimeStamp(String timeStamp)
/*     */   {
/* 268 */     this.timeStamp = timeStamp;
/*     */   }
/*     */ 
/*     */   public String[] getRcvAddresses()
/*     */   {
/* 273 */     return this.rcvAddresses;
/*     */   }
/*     */ 
/*     */   public void setRcvAddresses(String[] rcvAddresses)
/*     */   {
/* 278 */     this.rcvAddresses = rcvAddresses;
/*     */   }
/*     */ 
/*     */   public String getMtUrl()
/*     */   {
/* 283 */     return this.mtUrl;
/*     */   }
/*     */ 
/*     */   public void setMtUrl(String mtUrl)
/*     */   {
/* 288 */     this.mtUrl = mtUrl;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MT_SMMessage
 * JD-Core Version:    0.6.0
 */