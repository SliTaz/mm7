/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */

/*     */ import java.util.StringTokenizer;

/*     */
/*     */ public class UserOrderMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = 6554742358360997958L;
/*     */   private String destUser;
/*     */   private String feeUser;
/*     */   private String itemId;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*  19 */   private String extendOpCode = null;
/*  20 */   private String smsText = null;
/*     */   private int serviceId;
/*  22 */   private String contentCode = null;
/*     */ 
/*     */   public String getContentCode() {
/*  25 */     return this.contentCode;
/*     */   }
/*     */   public void setContentCode(String contentCode) {
/*  28 */     this.contentCode = contentCode;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  34 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId)
/*     */   {
/*  46 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String getExtendOpCode() {
/*  50 */     return this.extendOpCode;
/*     */   }
/*     */   public void setExtendOpCode(String extendOpCode) {
/*  53 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */   public String getSmsText() {
/*  56 */     return this.smsText;
/*     */   }
/*     */   public void setSmsText(String smsText) {
/*  59 */     this.smsText = smsText;
/*     */   }
/*     */   public String getOpCode() {
/*  62 */     return this.opCode;
/*     */   }
/*     */   public void setOpCode(String opCode) {
/*  65 */     this.opCode = opCode;
/*     */   }
/*     */   public String getDestUser() {
/*  68 */     return this.destUser;
/*     */   }
/*     */ 
/*     */   public void setDestUser(String destUser) {
/*  72 */     this.destUser = destUser;
/*     */   }
/*     */ 
/*     */   public String getFeeUser() {
/*  76 */     return this.feeUser;
/*     */   }
/*     */ 
/*     */   public void setFeeUser(String feeUser) {
/*  80 */     this.feeUser = feeUser;
/*     */   }
/*     */ 
/*     */   public String getItemId() {
/*  84 */     return this.itemId;
/*     */   }
/*     */ 
/*     */   public void setItemId(String itemId) {
/*  88 */     this.itemId = itemId;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  92 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  96 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 100 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 104 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 108 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 112 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException {
/*     */     try {
/* 117 */       message = super.decodeExtendFields(message);
/*     */ 
/* 119 */       String token = null;
/* 120 */       String name = null;
/* 121 */       String value = null;
/* 122 */       int pos = 0;
/*     */ 
/* 124 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 126 */         token = tokens.nextToken();
/*     */ 
/* 128 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 131 */         pos = token.indexOf(':');
/* 132 */         name = token.substring(0, pos);
/* 133 */         value = token.substring(pos + 1);
/*     */ 
/* 135 */         if (name.equals("destUser")) {
/* 136 */           if (value.equals("null"))
/* 137 */             setDestUser(null);
/*     */           else
/* 139 */             setDestUser(value);
/*     */         }
/* 141 */         else if (name.equals("feeUser")) {
/* 142 */           if (value.equals("null"))
/* 143 */             setFeeUser(null);
/*     */           else
/* 145 */             setFeeUser(value);
/*     */         }
/* 147 */         else if (name.equals("itemId")) {
/* 148 */           if (value.equals("null"))
/* 149 */             setItemId(null);
/*     */           else
/* 151 */             setItemId(value);
/*     */         }
/* 153 */         else if (name.equals("vaspId")) {
/* 154 */           if (value.equals("null"))
/* 155 */             setVaspId(null);
/*     */           else
/* 157 */             setVaspId(value);
/*     */         }
/* 159 */         else if (name.equals("vasId")) {
/* 160 */           if (value.equals("null"))
/* 161 */             setVasId(null);
/*     */           else
/* 163 */             setVasId(value);
/*     */         }
/* 165 */         else if (name.equals("serviceCode")) {
/* 166 */           if (value.equals("null"))
/* 167 */             setServiceCode(null);
/*     */           else
/* 169 */             setServiceCode(value);
/*     */         }
/* 171 */         else if (name.equals("opCode")) {
/* 172 */           if (value.equals("null"))
/* 173 */             setOpCode(null);
/*     */           else {
/* 175 */             setOpCode(value);
/*     */           }
/*     */         }
/* 178 */         else if (name.equals("source"))
/*     */         {
/* 180 */           setSource(Integer.parseInt(value));
/*     */         }
/* 182 */         else if (name.equals("smsText"))
/*     */         {
/* 184 */           if (value.equals("null"))
/* 185 */             setSmsText(null);
/*     */           else
/* 187 */             setSmsText(value);
/*     */         }
/* 189 */         else if (name.equals("extendOpCode"))
/*     */         {
/* 191 */           if (value.equals("null"))
/* 192 */             setExtendOpCode(null);
/*     */           else
/* 194 */             setExtendOpCode(value);
/* 195 */         } else if (name.equals("serviceId"))
/*     */         {
/* 197 */           if (value.equals("null"))
/* 198 */             setServiceId(-1);
/*     */           else
/* 200 */             setServiceId(Integer.parseInt(value)); 
/*     */         } else {
/* 201 */           if (!name.equals("contentCode"))
/*     */             continue;
/* 203 */           if (value.equals("null"))
/* 204 */             setContentCode("null");
/*     */           else
/* 206 */             setContentCode(value);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 210 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 215 */     StringBuffer result = new StringBuffer();
/* 216 */     result.append("MessageType:" + MessageType.USER_ORDER_MESSAGE.getMessageType() + "\n");
/* 217 */     result.append(super.encodeExtendFields());
/*     */ 
/* 219 */     result.append("destUser:" + this.destUser + "\n");
/* 220 */     result.append("feeUser:" + this.feeUser + "\n");
/* 221 */     result.append("itemId:" + this.itemId + "\n");
/* 222 */     result.append("vaspId:" + this.vaspId + "\n");
/* 223 */     result.append("vasId:" + this.vasId + "\n");
/* 224 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 225 */     result.append("opCode:" + this.opCode + "\n");
/* 226 */     result.append("source:" + this.source + "\n");
/* 227 */     result.append("smsText:" + this.smsText + "\n");
/* 228 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 229 */     result.append("serviceId:" + this.serviceId + "\n");
/* 230 */     result.append("contentCode:" + this.contentCode);
/* 231 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 235 */     return MessageType.USER_ORDER_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 239 */     UserOrderMessage message = new UserOrderMessage();
/* 240 */     String str = message.encodeString();
/* 241 */     System.out.println(str);
/* 242 */     message = new UserOrderMessage();
/* 243 */     message.decodeString(str);
/*     */ 
/* 245 */     str = message.encodeString();
/* 246 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 251 */     return encodeString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.UserOrderMessage
 * JD-Core Version:    0.6.0
 */