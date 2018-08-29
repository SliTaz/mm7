/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MO_MMMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = -7020548480001164266L;
/*  15 */   private String sendAddress = null;
/*  16 */   private String vaspId = null;
/*  17 */   private String vasId = null;
/*  18 */   private String serviceCode = null;
/*  19 */   private String opCode = null;
/*  20 */   private String mmFile = null;
/*  21 */   private String mLinkId = null;
/*  22 */   private String extendOpCode = null;
/*     */   private int serviceId;
/*  24 */   private String contentCode = null;
/*     */ 
/*     */   public String getContentCode() {
/*  27 */     return this.contentCode;
/*     */   }
/*     */ 
/*     */   public void setContentCode(String contentCode) {
/*  31 */     this.contentCode = contentCode;
/*     */   }
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  36 */     return this.serviceId;
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
/*     */ 
/*     */   public void setExtendOpCode(String extendOpCode)
/*     */   {
/*  55 */     this.extendOpCode = extendOpCode;
/*     */   }
/*     */ 
/*     */   public String getMmFile()
/*     */   {
/*  60 */     return this.mmFile;
/*     */   }
/*     */ 
/*     */   public void setMmFile(String mmFile) {
/*  64 */     this.mmFile = mmFile;
/*     */   }
/*     */ 
/*     */   public String getOpCode() {
/*  68 */     return this.opCode;
/*     */   }
/*     */ 
/*     */   public void setOpCode(String opCode) {
/*  72 */     this.opCode = opCode;
/*     */   }
/*     */ 
/*     */   public String getSendAddress() {
/*  76 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/*  80 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  84 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  88 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/*  92 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/*  96 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/* 100 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/* 104 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public String getMLinkId()
/*     */   {
/* 112 */     return this.mLinkId;
/*     */   }
/*     */ 
/*     */   public void setMLinkId(String linkId)
/*     */   {
/* 121 */     this.mLinkId = linkId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try {
/* 127 */       message = super.decodeExtendFields(message);
/*     */ 
/* 129 */       String token = null;
/* 130 */       int pos = 0;
/* 131 */       String name = null;
/* 132 */       String value = null;
/*     */ 
/* 134 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/* 136 */         token = tokens.nextToken();
/*     */ 
/* 138 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 141 */         pos = token.indexOf(':');
/* 142 */         name = token.substring(0, pos);
/* 143 */         value = token.substring(pos + 1);
/*     */ 
/* 145 */         if (name.equals("sendAddress"))
/*     */         {
/* 147 */           if (value.equals("null"))
/* 148 */             setSendAddress(null);
/*     */           else
/* 150 */             setSendAddress(value);
/*     */         }
/* 152 */         else if (name.equals("vaspId"))
/*     */         {
/* 154 */           if (value.equals("null"))
/* 155 */             setVaspId(null);
/*     */           else
/* 157 */             setVaspId(value);
/*     */         }
/* 159 */         else if (name.equals("vasId"))
/*     */         {
/* 161 */           if (value.equals("null"))
/* 162 */             setVasId(null);
/*     */           else
/* 164 */             setVasId(value);
/*     */         }
/* 166 */         else if (name.equals("serviceCode"))
/*     */         {
/* 168 */           if (value.equals("null"))
/* 169 */             setServiceCode(null);
/*     */           else
/* 171 */             setServiceCode(value);
/*     */         }
/* 173 */         else if (name.equals("opCode"))
/*     */         {
/* 175 */           if (value.equals("null"))
/* 176 */             setOpCode(null);
/*     */           else
/* 178 */             setOpCode(value);
/*     */         }
/* 180 */         else if (name.equals("mmFile"))
/*     */         {
/* 182 */           if (value.equals("null"))
/* 183 */             setMmFile(null);
/*     */           else
/* 185 */             setMmFile(value);
/*     */         }
/* 187 */         else if (name.equals("linkId"))
/*     */         {
/* 189 */           if (value.equals("null"))
/* 190 */             setMmFile(null);
/*     */           else
/* 192 */             setMLinkId(value);
/*     */         }
/* 194 */         else if (name.equals("extendOpCode"))
/*     */         {
/* 196 */           if (value.equals("null"))
/* 197 */             setExtendOpCode(null);
/*     */           else
/* 199 */             setExtendOpCode(value);
/* 200 */         } else if (name.equals("serviceId"))
/*     */         {
/* 202 */           if (value.equals("null"))
/* 203 */             setServiceId(-1);
/*     */           else
/* 205 */             setServiceId(Integer.parseInt(value)); 
/*     */         } else {
/* 206 */           if (!name.equals("contentCode"))
/*     */             continue;
/* 208 */           if (value.equals("null"))
/* 209 */             setContentCode(null);
/*     */           else
/* 211 */             setContentCode(value);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 215 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 220 */     StringBuffer result = new StringBuffer();
/* 221 */     result.append("MessageType:" + MessageType.MO_MM_MESSAGE.getMessageType() + "\n");
/* 222 */     result.append(super.encodeExtendFields());
/* 223 */     result.append("sendAddress:" + this.sendAddress + "\n");
/* 224 */     result.append("vaspId:" + this.vaspId + "\n");
/* 225 */     result.append("vasId:" + this.vasId + "\n");
/* 226 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 227 */     result.append("opCode:" + this.opCode + "\n");
/* 228 */     result.append("mmFile:" + this.mmFile + "\n");
/* 229 */     result.append("extendOpCode:" + this.extendOpCode + "\n");
/* 230 */     result.append("linkId:" + this.mLinkId + "\n");
/* 231 */     result.append("serviceId:" + this.serviceId + "\n");
/* 232 */     result.append("contentCode:" + this.contentCode);
/* 233 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType()
/*     */   {
/* 238 */     return MessageType.MO_MM_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 242 */     MO_MMMessage message = new MO_MMMessage();
/* 243 */     String str = message.encodeString();
/* 244 */     System.out.println(str);
/* 245 */     message = new MO_MMMessage();
/* 246 */     message.decodeString(str);
/*     */ 
/* 248 */     str = message.encodeString();
/* 249 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 254 */     return encodeString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_MMMessage
 * JD-Core Version:    0.6.0
 */