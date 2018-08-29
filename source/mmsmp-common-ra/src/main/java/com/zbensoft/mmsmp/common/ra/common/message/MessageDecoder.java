/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ 
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MessageDecoder
/*     */ {
/*   7 */   private static final Logger log = Logger.getLogger(MessageDecoder.class);
/*     */ 
/*     */   public static IMessage getMessage(String theMessage)
/*     */     throws DecodeMessageException
/*     */   {
/*     */     try
/*     */     {
/*  19 */       if (theMessage == null)
/*     */       {
/*  21 */         log.warn("The Message is NULL!");
/*  22 */         return null;
/*     */       }
/*     */ 
/*  25 */       int pos = theMessage.indexOf("\n");
/*  26 */       String messageTypeStr = theMessage.substring(0, pos);
/*  27 */       theMessage = theMessage.substring(pos + 1);
/*     */ 
/*  29 */       if (!messageTypeStr.startsWith("MessageType:")) {
/*  30 */         throw new DecodeMessageException("Invalid Message Format!", null);
/*     */       }
/*  32 */       pos = messageTypeStr.indexOf(":");
/*  33 */       String messageType = messageTypeStr.substring(pos + 1);
/*     */ 
/*  35 */       if (messageType.equals(MessageType.MO_SM_MESSAGE.getMessageType()))
/*     */       {
/*  37 */         MO_SMMessage result = new MO_SMMessage();
/*  38 */         result.decodeString(theMessage);
/*     */ 
/*  40 */         return result;
/*     */       }
/*  42 */       if (messageType.equals(MessageType.MT_MM_MESSAGE.getMessageType()))
/*     */       {
/*  44 */         MT_MMMessage result = new MT_MMMessage();
/*  45 */         result.decodeString(theMessage);
/*     */ 
/*  47 */         return result;
/*     */       }
/*  49 */       if (messageType.equals(MessageType.USER_ONDEMAND_MESSAGE.getMessageType()))
/*     */       {
/*  51 */         UserOnDemandMessage result = new UserOnDemandMessage();
/*  52 */         result.decodeString(theMessage);
/*     */ 
/*  54 */         return result;
/*     */       }
/*  56 */       if (messageType.equals(MessageType.USER_ORDER_MESSAGE.getMessageType()))
/*     */       {
/*  58 */         UserOrderMessage result = new UserOrderMessage();
/*  59 */         result.decodeString(theMessage);
/*     */ 
/*  61 */         return result;
/*     */       }
/*  63 */       if (messageType.equals(MessageType.USER_ORDERUSE_MESSAGE.getMessageType()))
/*     */       {
/*  65 */         UserOrderUseMessage result = new UserOrderUseMessage();
/*  66 */         result.decodeString(theMessage);
/*     */ 
/*  68 */         return result;
/*     */       }
/*  70 */       if (messageType.equals(MessageType.MT_SM_MESSAGE.getMessageType()))
/*     */       {
/*  72 */         MT_SMMessage result = new MT_SMMessage();
/*  73 */         result.decodeString(theMessage);
/*     */ 
/*  75 */         return result;
/*     */       }
/*  77 */       if (messageType.equals(MessageType.MO_MM_MESSAGE.getMessageType()))
/*     */       {
/*  79 */         MO_MMMessage result = new MO_MMMessage();
/*  80 */         result.decodeString(theMessage);
/*     */ 
/*  82 */         return result;
/*     */       }
/*  84 */       if (messageType.equals(MessageType.MT_WAPPUSH_MESSAGE.getMessageType()))
/*     */       {
/*  86 */         MT_WapPushMessage result = new MT_WapPushMessage();
/*  87 */         result.decodeString(theMessage);
/*     */ 
/*  89 */         return result;
/*     */       }
/*  91 */       if (messageType.equals(MessageType.USER_CANCELORDER_MESSAGE.getMessageType()))
/*     */       {
/*  93 */         UserCancelOrderMessage result = new UserCancelOrderMessage();
/*  94 */         result.decodeString(theMessage);
/*     */ 
/*  96 */         return result;
/*     */       }
/*  98 */       if (messageType.equals(MessageType.MO_MM_DR_MESSAGE.getMessageType()))
/*     */       {
/* 100 */         MO_MM_DRMessage result = new MO_MM_DRMessage();
/* 101 */         result.decodeString(theMessage);
/*     */ 
/* 103 */         return result;
/*     */       }
/*     */ 
/* 106 */       throw new DecodeMessageException("unknown MessageType: " + messageType, null);
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 110 */     throw new DecodeMessageException("decode Message Exception", e);
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MessageDecoder
 * JD-Core Version:    0.6.0
 */