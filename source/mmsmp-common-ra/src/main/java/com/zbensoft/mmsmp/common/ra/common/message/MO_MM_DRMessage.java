/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
import com.zbensoft.mmsmp.common.ra.common.db.cache.ServCache;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;

/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class MO_MM_DRMessage extends AbstractMessage
/*     */ {
/*     */   private static final long serialVersionUID = 5396453514081715560L;
/*     */   private String sendAddress;
/*     */   private String vaspId;
/*     */   private String vasId;
/*     */   private String serviceCode;
/*     */   private String opCode;
/*     */   private String transactionId;
/*     */ 
/*     */   public int getServiceId()
/*     */   {
/*  23 */     Vasservice serv = ServCache.getInstance().getServ(this.vaspId, this.vasId, this.serviceCode);
/*  24 */     if (serv != null) {
/*  25 */       return serv.getUniqueid().intValue();
/*     */     }
/*  27 */     return -1;
/*     */   }
/*     */ 
/*     */   public String getTransactionId()
/*     */   {
/*  36 */     return this.transactionId;
/*     */   }
/*     */ 
/*     */   public void setTransactionId(String transactionId) {
/*  40 */     this.transactionId = transactionId;
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
/*     */   public String getSendAddress() {
/*  52 */     return this.sendAddress;
/*     */   }
/*     */ 
/*     */   public void setSendAddress(String sendAddress) {
/*  56 */     this.sendAddress = sendAddress;
/*     */   }
/*     */ 
/*     */   public String getServiceCode() {
/*  60 */     return this.serviceCode;
/*     */   }
/*     */ 
/*     */   public void setServiceCode(String serviceCode) {
/*  64 */     this.serviceCode = serviceCode;
/*     */   }
/*     */ 
/*     */   public String getVasId() {
/*  68 */     return this.vasId;
/*     */   }
/*     */ 
/*     */   public void setVasId(String vasId) {
/*  72 */     this.vasId = vasId;
/*     */   }
/*     */ 
/*     */   public String getVaspId() {
/*  76 */     return this.vaspId;
/*     */   }
/*     */ 
/*     */   public void setVaspId(String vaspId) {
/*  80 */     this.vaspId = vaspId;
/*     */   }
/*     */ 
/*     */   public void decodeString(String message) throws DecodeMessageException
/*     */   {
/*     */     try {
/*  86 */       message = super.decodeExtendFields(message);
/*     */ 
/*  88 */       String token = null;
/*  89 */       int pos = 0;
/*  90 */       String name = null;
/*  91 */       String value = null;
/*     */ 
/*  93 */       for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens(); )
/*     */       {
/*  95 */         token = tokens.nextToken();
/*     */ 
/*  97 */         if (token.startsWith("MessageType")) {
/*     */           continue;
/*     */         }
/* 100 */         pos = token.indexOf(':');
/* 101 */         name = token.substring(0, pos);
/* 102 */         value = token.substring(pos + 1);
/*     */ 
/* 104 */         if (name.equals("sendAddress")) {
/* 105 */           if (value.equals("null"))
/* 106 */             setSendAddress(null);
/*     */           else
/* 108 */             setSendAddress(value);
/*     */         }
/* 110 */         else if (name.equals("vaspId")) {
/* 111 */           if (value.equals("null"))
/* 112 */             setVaspId(null);
/*     */           else
/* 114 */             setVaspId(value);
/*     */         }
/* 116 */         else if (name.equals("vasId")) {
/* 117 */           if (value.equals("null"))
/* 118 */             setVasId(null);
/*     */           else
/* 120 */             setVasId(value);
/*     */         }
/* 122 */         else if (name.equals("serviceCode")) {
/* 123 */           if (value.equals("null"))
/* 124 */             setServiceCode(null);
/*     */           else
/* 126 */             setServiceCode(value);
/*     */         }
/* 128 */         else if (name.equals("opCode")) {
/* 129 */           if (value.equals("null"))
/* 130 */             setOpCode(null);
/*     */           else
/* 132 */             setOpCode(value);
/*     */         }
/* 134 */         else if (name.equals("transactionId"))
/* 135 */           if (value.equals("null"))
/* 136 */             setTransactionId(null);
/*     */           else
/* 138 */             setTransactionId(value);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 143 */       throw new DecodeMessageException("failed to decode Message", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String encodeString() {
/* 148 */     StringBuffer result = new StringBuffer();
/* 149 */     result.append("MessageType:" + MessageType.MO_MM_DR_MESSAGE.getMessageType() + "\n");
/* 150 */     result.append(super.encodeExtendFields());
/*     */ 
/* 152 */     result.append("source:" + this.sendAddress + "\n");
/* 153 */     result.append("vaspId:" + this.vaspId + "\n");
/* 154 */     result.append("vasId:" + this.vasId + "\n");
/* 155 */     result.append("serviceCode:" + this.serviceCode + "\n");
/* 156 */     result.append("opCode:" + this.opCode);
/* 157 */     result.append("transactionId:" + this.transactionId);
/* 158 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public MessageType getMessageType() {
/* 162 */     return MessageType.MO_MM_DR_MESSAGE;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/* 166 */     MO_MM_DRMessage message = new MO_MM_DRMessage();
/* 167 */     String str = message.encodeString();
/* 168 */     System.out.println(str);
/* 169 */     message = new MO_MM_DRMessage();
/* 170 */     message.decodeString(str);
/*     */ 
/* 172 */     str = message.encodeString();
/* 173 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 178 */     return encodeString();
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_MM_DRMessage
 * JD-Core Version:    0.6.0
 */