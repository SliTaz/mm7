/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class UserCancelOrderMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = 5168707962664911404L;
/*     */   private String destUser;
/*     */   private String feeUser;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String itemId;
/*  20 */   private int source = 4;
/*     */   private String opCode;
/*  23 */   private String extendOpCode = null;
/*  24 */   private String smsText = null;
/*     */   private int serviceId;
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  28 */     return this.serviceId;
/*     */   }
/*     */ 
/*     */   public void setServiceId(int serviceId)
/*     */   {
/*  38 */     this.serviceId = serviceId;
/*     */   }
/*     */ 
/*     */   public String getExtendOpCode() {
/*  42 */     return this.extendOpCode;
/*     */   }
/*     */   public void setExtendOpCode(String extendOpCode) {
/*  45 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */ 
/*     */   public String getSmsText() {
/*  49 */     return this.smsText;
/*     */   }
/*     */ 
/*     */   public void setSmsText(String smsText)
/*     */   {
/*  56 */     this.smsText = smsText;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/*  60 */     return this.opCode;
/*     */   }
/*     */   public void setOpCode(String opCode) {
/*  63 */     this.opCode = opCode;
/*     */   }
/*     */   public int getSource() {
/*  66 */     return this.source;
/*     */   }
/*     */ 
/*     */   public void setSource(int source) {
/*  70 */     this.source = source;
/*     */   }
/*     */ 
/*     */   public String getDestUser() {
/*  74 */     return this.destUser;
/*     */   }
/*     */ 
/*     */   public void setDestUser(String destUser) {
/*  78 */     this.destUser = destUser;
/*     */   }
/*     */ 
/*     */   public String getFeeUser() {
/*  82 */     return this.feeUser;
/*     */   }
/*     */ 
/*     */   public void setFeeUser(String feeUser) {
/*  86 */     this.feeUser = feeUser;
/*     */   }
/*     */ 
/*     */   public String getItemId() {
/*  90 */     return this.itemId;
/*     */   }
/*     */ 
/*     */   public void setItemId(String itemid) {
/*  94 */     this.itemId = itemid;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  98 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/* 102 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/* 106 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/* 110 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 114 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 118 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException {
/*     */     try {
/* 123 */       message = super.decodeExtendFields(message);
/*     */ 
/* 125 */       String token = null;
/* 126 */       String name = null;
/* 127 */       String value = null;
/* 128 */       int pos = 0;
/*     */ 
/* 130 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 132 */         token = tokens.nextToken();
/*     */ 
/* 134 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 137 */         pos = token.indexOf(':');
/* 138 */         name = token.substring(0, pos);
/* 139 */         value = token.substring(pos + 1);
/*     */ 
/* 141 */         if (name.equals("destUser"))
/*     */         {
/* 143 */           if (value.equals("null"))
/* 144 */             setDestUser(null);
/*     */           else
/* 146 */             setDestUser(value);
/*     */         }
/* 148 */         else if (name.equals("feeUser"))
/*     */         {
/* 150 */           if (value.equals("null"))
/* 151 */             setFeeUser(null);
/*     */           else
/* 153 */             setFeeUser(value);
/*     */         }
/* 155 */         else if (name.equals("itemId")) {
/* 156 */           if (value.equals("null"))
/* 157 */             setItemId(null);
/*     */           else
/* 159 */             setItemId(value);
/*     */         }
/* 161 */         else if (name.equals("vaspId")) {
/* 162 */           if (value.equals("null"))
/* 163 */             setVaspId(null);
/*     */           else
/* 165 */             setVaspId(value);
/*     */         }
/* 167 */         else if (name.equals("vasId")) {
/* 168 */           if (value.equals("null"))
/* 169 */             setVasId(null);
/*     */           else
/* 171 */             setVasId(value);
/*     */         }
/* 173 */         else if (name.equals("serviceCode")) {
/* 174 */           if (value.equals("null"))
/* 175 */             setServiceCode(null);
/*     */           else
/* 177 */             setServiceCode(value);
/*     */         }
/* 179 */         else if (name.equals("opCode")) {
/* 180 */           if (value.equals("null"))
/* 181 */             setOpCode(null);
/*     */           else {
/* 183 */             setOpCode(value);
/*     */           }
/*     */         }
/* 186 */         else if (name.equals("source"))
/*     */         {
/* 188 */           setSource(Integer.parseInt(value));
/*     */         }
/* 190 */         else if (name.equals("smsText"))
/*     */         {
/* 192 */           if (value.equals("null"))
/* 193 */             setSmsText(null);
/*     */           else
/* 195 */             setSmsText(value);
/*     */         }
/* 197 */         else if (name.equals("extendOpCode"))
/*     */         {
/* 199 */           if (value.equals("null"))
/* 200 */             setExtendOpCode(null);
/*     */           else
/* 202 */             setExtendOpCode(value); 
/*     */         } else {
/* 203 */           if (!name.equals("serviceId"))
/*     */             continue;
/* 205 */           if (value.equals("null"))
/* 206 */             setServiceId(-1);
/*     */           else
/* 208 */             setServiceId(Integer.parseInt(value));
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 212 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 217 */     StringBuffer result = new StringBuffer();
/* 218 */     result.append("MessageType:" + MessageType.USER_CANCELORDER_MESSAGE.getMessageType() + "\n");
/* 219 */     result.append(super.encodeExtendFields());
/*     */ 
/* 221 */     result.append("feeUser:" + this.feeUser + "\n");
/* 222 */     result.append("destUser:" + this.destUser + "\n");
/* 223 */     result.append("vaspId:" + this.vaspId + "\n");
/* 224 */     result.append("vasId:" + this.vasId + "\n");
/* 225 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 226 */     result.append("opCode:" + this.opCode + "\n");
/* 227 */     result.append("source:" + this.source + "\n");
/* 228 */     result.append("smsText:" + this.smsText + "\n");
/* 229 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 230 */     result.append("itemId:" + this.itemId + "\n");
/* 231 */     result.append("serviceId:" + this.serviceId + "\n");
/*     */ 
/* 233 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 237 */     return MessageType.USER_CANCELORDER_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 241 */     UserCancelOrderMessage message = new UserCancelOrderMessage();
/* 242 */     String str = message.encodeString();
/* 243 */     System.out.println(str);
/* 244 */     message = new UserCancelOrderMessage();
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
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.UserCancelOrderMessage
 * JD-Core Version:    0.6.0
 */