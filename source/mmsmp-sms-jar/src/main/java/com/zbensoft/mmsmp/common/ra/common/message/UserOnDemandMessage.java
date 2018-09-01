/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */ import java.util.StringTokenizer;

/*     */
/*     */ public class UserOnDemandMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -3581179486566728866L;
/*  12 */   private String destUser = null;
/*  13 */   private String feeUser = null;
/*  14 */   private String vaspId = null;
/*  15 */   private String vasId = null;
/*  16 */   private String serviceCode = null;
/*  17 */   private String contentCode = null;
/*  18 */   private String linkId = null;
/*  19 */   private String uaProf = null;
/*     */ 
/*  22 */   private String opCode = null;
/*  23 */   private String extendOpCode = null;
/*  24 */   private String smsText = null;
/*     */   private int serviceId;
/*     */ 
/*     */   public String getExtendOpCode()
/*     */   {
/*  28 */     return this.extendOpCode;
/*     */   }
/*     */ 
/*     */   public void setExtendOpCode(String extendOpCode) {
/*  32 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */ 
/*     */   public String getLinkId() {
/*  36 */     return this.linkId;
/*     */   }
/*     */ 
/*     */   public void setLinkId(String linkId) {
/*  40 */     this.linkId = linkId;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/*  44 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/*  48 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSmsText() {
/*  52 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText) {
/*  56 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId) {
/*  60 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  65 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public String getDestUser()
/*     */   {
/*  81 */     return this.destUser;
/*     */   }
/*     */ 
/*     */   public void setDestUser(String destUser)
/*     */   {
/*  90 */     this.destUser = destUser;
/*     */   }
/*     */ 
/*     */   public String getFeeUser()
/*     */   {
/*  98 */     return this.feeUser;
/*     */   }
/*     */ 
/*     */   public void setFeeUser(String feeUser)
/*     */   {
/* 107 */     this.feeUser = feeUser;
/*     */   }
/*     */ 
/*     */   public String getContentCode()
/*     */   {
/* 116 */     return this.contentCode;
/*     */   }
/*     */ 
/*     */   public void setContentCode(String contentCode)
/*     */   {
/* 125 */     this.contentCode = contentCode;
/*     */   }
/*     */ 
/*     */   public String getServiceCode()
/*     */   {
/* 133 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode)
/*     */   {
/* 142 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId()
/*     */   {
/* 151 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public String getUaProf()
/*     */   {
/* 159 */     return this.uaProf;
/*     */   }
/*     */ 
/*     */   public void setUaProf(String uaProf)
/*     */   {
/* 167 */     this.uaProf = uaProf;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId)
/*     */   {
/* 176 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId()
/*     */   {
/* 185 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId)
/*     */   {
/* 193 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/* 200 */       message = super.decodeExtendFields(message);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 204 */       throw new DecodeMessageException("Failed to decode Message", e);
/*     */     }
/*     */ 
/* 207 */     String token = null;
/* 208 */     String name = null;
/* 209 */     String value = null;
/* 210 */     int pos = 0;
/*     */ 
/* 212 */     for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */     {
/* 214 */       token = tokens.nextToken();
/*     */ 
/* 216 */       if (token.startsWith("MessageType")) {
/*     */         continue;
/*     */       }
/* 219 */       pos = token.indexOf(':');
/* 220 */       name = token.substring(0, pos);
/* 221 */       value = token.substring(pos + 1);
/*     */ 
/* 223 */       if (name.equals("destUser"))
/*     */       {
/* 225 */         if (value.equals("null"))
/* 226 */           setDestUser(null);
/*     */         else
/* 228 */           setDestUser(value);
/*     */       }
/* 230 */       else if (name.equals("feeUser"))
/*     */       {
/* 232 */         if (value.equals("null"))
/* 233 */           setFeeUser(null);
/*     */         else
/* 235 */           setFeeUser(value);
/*     */       }
/* 237 */       else if (name.equals("vaspId"))
/*     */       {
/* 239 */         if (value.equals("null"))
/* 240 */           setVaspId(null);
/*     */         else
/* 242 */           setVaspId(value);
/*     */       }
/* 244 */       else if (name.equals("vasId"))
/*     */       {
/* 246 */         if (value.equals("null"))
/* 247 */           setVasId(null);
/*     */         else
/* 249 */           setVasId(value);
/*     */       }
/* 251 */       else if (name.equals("serviceCode"))
/*     */       {
/* 253 */         if (value.equals("null"))
/* 254 */           setServiceCode(null);
/*     */         else
/* 256 */           setServiceCode(value);
/*     */       }
/* 258 */       else if (name.equals("contentCode"))
/*     */       {
/* 260 */         if (value.equals("null"))
/* 261 */           setContentCode(null);
/*     */         else
/* 263 */           setContentCode(value);
/*     */       }
/* 265 */       else if (name.equals("linkId"))
/*     */       {
/* 267 */         if (value.equals("null"))
/* 268 */           setLinkId(null);
/*     */         else
/* 270 */           setLinkId(value);
/*     */       }
/* 272 */       else if (name.equals("uaProf"))
/*     */       {
/* 274 */         if (value.equals("null"))
/* 275 */           setUaProf(null);
/*     */         else
/* 277 */           setUaProf(value);
/*     */       }
/* 279 */       else if (name.equals("source"))
/*     */       {
/* 281 */         setSource(Integer.parseInt(value));
/*     */       }
/* 283 */       else if (name.equals("opCode"))
/*     */       {
/* 285 */         if (value.equals("null"))
/* 286 */           setOpCode(null);
/*     */         else
/* 288 */           setOpCode(value);
/*     */       }
/* 290 */       else if (name.equals("smsText"))
/*     */       {
/* 292 */         if (value.equals("null"))
/* 293 */           setSmsText(null);
/*     */         else
/* 295 */           setSmsText(value);
/*     */       }
/* 297 */       else if (name.equals("extendOpCode"))
/*     */       {
/* 299 */         if (value.equals("null"))
/* 300 */           setExtendOpCode(null);
/*     */         else
/* 302 */           setExtendOpCode(value); 
/*     */       } else {
/* 303 */         if (!name.equals("serviceId"))
/*     */           continue;
/* 305 */         if (value.equals("null"))
/* 306 */           setServiceId(-1);
/*     */         else
/* 308 */           setServiceId(Integer.parseInt(value));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString()
/*     */   {
/* 315 */     StringBuffer result = new StringBuffer();
/*     */ 
/* 317 */     result.append("MessageType:" + MessageType.USER_ONDEMAND_MESSAGE.getMessageType() + "\n");
/* 318 */     result.append(super.encodeExtendFields());
/* 319 */     result.append("destUser:" + this.destUser + "\n");
/* 320 */     result.append("feeUser:" + this.feeUser + "\n");
/* 321 */     result.append("vaspId:" + this.vaspId + "\n");
/* 322 */     result.append("vasId:" + this.vasId + "\n");
/* 323 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 324 */     result.append("linkId:" + this.linkId + "\n");
/* 325 */     result.append("contentCode:" + this.contentCode + "\n");
/* 326 */     result.append("source:" + this.source + "\n");
/* 327 */     result.append("opCode:" + this.opCode + "\n");
/* 328 */     result.append("smsText:" + this.smsText + "\n");
/* 329 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 330 */     result.append("uaProf:" + this.uaProf + "\n");
/* 331 */     result.append("serviceId:" + this.serviceId);
/*     */ 
/* 333 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 338 */     return MessageType.USER_ONDEMAND_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception
/*     */   {
/* 343 */     UserOnDemandMessage message = new UserOnDemandMessage();
/* 344 */     String str = message.encodeString();
/* 345 */     System.out.println(str);
/* 346 */     message = new UserOnDemandMessage();
/* 347 */     message.decodeString(str);
/*     */ 
/* 349 */     str = message.encodeString();
/* 350 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 354 */     return encodeString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.UserOnDemandMessage
 * JD-Core Version:    0.6.0
 */