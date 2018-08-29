/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class UserOrderUseMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -3397595045817850168L;
/*     */   private String destUser;
/*     */   private String feeUser;
/*     */   private String contentCode;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*  21 */   private String extendOpCode = null;
/*  22 */   private String smsText = null;
/*     */   private int serviceId;
/*     */ 
/*     */   public void setServiceId(int serviceId)
/*     */   {
/*  26 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public int getServiceId() {
/*  30 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public String getExtendOpCode()
/*     */   {
/*  43 */     return this.extendOpCode;
/*     */   }
/*     */ 
/*     */   public void setExtendOpCode(String extendOpCode)
/*     */   {
/*  50 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */ 
/*     */   public String getSmsText()
/*     */   {
/*  57 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText)
/*     */   {
/*  64 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public String getOpCode()
/*     */   {
/*  71 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode)
/*     */   {
/*  78 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getDestUser() {
/*  82 */     return this.destUser;
/*     */   }
/*     */ 
/*     */   public void setDestUser(String destUser) {
/*  86 */     this.destUser = destUser;
/*     */   }
/*     */ 
/*     */   public String getFeeUser() {
/*  90 */     return this.feeUser;
/*     */   }
/*     */ 
/*     */   public void setFeeUser(String feeUser) {
/*  94 */     this.feeUser = feeUser;
/*     */   }
/*     */ 
/*     */   public String getContentCode()
/*     */   {
/* 100 */     return this.contentCode;
/*     */   }
/*     */ 
/*     */   public void setContentCode(String contentCode)
/*     */   {
/* 107 */     this.contentCode = contentCode;
/*     */   }
/*     */ 
/*     */   public String getServiceCode()
/*     */   {
/* 114 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 118 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 122 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 126 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 130 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 134 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException {
/*     */     try {
/* 139 */       message = super.decodeExtendFields(message);
/*     */ 
/* 141 */       String token = null;
/* 142 */       String name = null;
/* 143 */       String value = null;
/* 144 */       int pos = 0;
/*     */ 
/* 146 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 148 */         token = tokens.nextToken();
/*     */ 
/* 150 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 153 */         pos = token.indexOf(':');
/* 154 */         name = token.substring(0, pos);
/* 155 */         value = token.substring(pos + 1);
/*     */ 
/* 157 */         if (name.equals("destUser")) {
/* 158 */           if (value.equals("null"))
/* 159 */             setDestUser(null);
/*     */           else
/* 161 */             setDestUser(value);
/*     */         }
/* 163 */         else if (name.equals("feeUser")) {
/* 164 */           if (value.equals("null"))
/* 165 */             setFeeUser(null);
/*     */           else
/* 167 */             setFeeUser(value);
/*     */         }
/* 169 */         else if (name.equals("contentCode")) {
/* 170 */           if (value.equals("null"))
/* 171 */             setContentCode(null);
/*     */           else
/* 173 */             setContentCode(value);
/*     */         }
/* 175 */         else if (name.equals("vaspId")) {
/* 176 */           if (value.equals("null"))
/* 177 */             setVaspId(null);
/*     */           else
/* 179 */             setVaspId(value);
/*     */         }
/* 181 */         else if (name.equals("vasId")) {
/* 182 */           if (value.equals("null"))
/* 183 */             setVasId(null);
/*     */           else
/* 185 */             setVasId(value);
/*     */         }
/* 187 */         else if (name.equals("serviceCode")) {
/* 188 */           if (value.equals("null"))
/* 189 */             setServiceCode(null);
/*     */           else
/* 191 */             setServiceCode(value);
/*     */         }
/* 193 */         else if (name.equals("opCode")) {
/* 194 */           if (value.equals("null"))
/* 195 */             setOpCode(null);
/*     */           else {
/* 197 */             setOpCode(value);
/*     */           }
/*     */         }
/* 200 */         else if (name.equals("source"))
/*     */         {
/* 202 */           setSource(Integer.parseInt(value));
/*     */         }
/* 204 */         else if (name.equals("smsText"))
/*     */         {
/* 206 */           if (value.equals("null"))
/* 207 */             setSmsText(null);
/*     */           else
/* 209 */             setSmsText(value);
/*     */         }
/* 211 */         else if (name.equals("extendOpCode"))
/*     */         {
/* 213 */           if (value.equals("null"))
/* 214 */             setExtendOpCode(null);
/*     */           else
/* 216 */             setExtendOpCode(value);
/*     */         } else {
/* 218 */           if (!name.equals("serviceId"))
/*     */             continue;
/* 220 */           if (value.equals("null"))
/* 221 */             setServiceId(-1);
/*     */           else
/* 223 */             setServiceId(Integer.parseInt(value));
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 227 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 232 */     StringBuffer result = new StringBuffer();
/* 233 */     result.append("MessageType:" + MessageType.USER_ORDERUSE_MESSAGE.getMessageType() + "\n");
/* 234 */     result.append(super.encodeExtendFields());
/*     */ 
/* 236 */     result.append("destUser:" + this.destUser + "\n");
/* 237 */     result.append("feeUser:" + this.feeUser + "\n");
/* 238 */     result.append("contentCode:" + this.contentCode + "\n");
/* 239 */     result.append("vaspId:" + this.vaspId + "\n");
/* 240 */     result.append("vasId:" + this.vasId + "\n");
/* 241 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 242 */     result.append("opCode:" + this.opCode + "\n");
/* 243 */     result.append("source:" + this.source + "\n");
/* 244 */     result.append("smsText:" + this.smsText + "\n");
/* 245 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 246 */     result.append("serviceId:" + this.serviceId + "\n");
/* 247 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 251 */     return MessageType.USER_ORDERUSE_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 255 */     UserOrderUseMessage message = new UserOrderUseMessage();
/* 256 */     String str = message.encodeString();
/* 257 */     System.out.println(str);
/* 258 */     message = new UserOrderUseMessage();
/* 259 */     message.decodeString(str);
/*     */ 
/* 261 */     str = message.encodeString();
/* 262 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 267 */     return encodeString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.UserOrderUseMessage
 * JD-Core Version:    0.6.0
 */