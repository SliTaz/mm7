/*     */ package com.zbensoft.mmsmp.common.ra.common.queue;
/*     */

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class MessageQueue
/*     */ {
/*  25 */   private static final Log logger = LogFactory.getLog(MessageQueue.class);
/*     */ 
/*  27 */   public static int PRIORITY_VIP = 1;
/*  28 */   public static int PRIORITY_COMMON = 2;
/*  29 */   public static int PRIORITY_LOW = 3;
/*  30 */   public static int PRIORITY_COMMON_SMS = 4;
/*  31 */   public static int PRIORITY_COMMON_WAP = 5;
/*     */   private LinkedBlockingQueue<AbstractMessage> VIPQueue;
/*     */   private LinkedBlockingQueue<AbstractMessage> commonQueue;
/*     */   private LinkedBlockingQueue<AbstractMessage> commonQueueSms;
/*     */   private LinkedBlockingQueue<AbstractMessage> commonQueueWap;
/*     */   private LinkedBlockingQueue<AbstractMessage> lowQueue;
/*  46 */   private Lock lock = new ReentrantLock();
/*     */ 
/*  50 */   private boolean removing = false;
/*     */ 
/*  54 */   private Condition removCondition = this.lock.newCondition();
/*     */ 
/*     */   public MessageQueue(int size)
/*     */   {
/*  62 */     this.commonQueue = new LinkedBlockingQueue(size);
/*  63 */     this.commonQueueSms = new LinkedBlockingQueue(size);
/*  64 */     this.commonQueueWap = new LinkedBlockingQueue(size);
/*  65 */     this.VIPQueue = new LinkedBlockingQueue(size);
/*  66 */     this.lowQueue = new LinkedBlockingQueue(size);
/*     */   }
/*     */ 
/*     */   public void addMessage(AbstractMessage message, Integer priority)
/*     */   {
/*     */     try
/*     */     {
/*  76 */       if (priority == null) {
/*  77 */         this.commonQueue.put(message);
/*     */       }
/*  79 */       else if (priority.intValue() == PRIORITY_VIP) {
/*  80 */         this.VIPQueue.put(message);
/*     */       }
/*  82 */       else if (priority.intValue() == PRIORITY_COMMON) {
/*  83 */         this.commonQueue.put(message);
/*     */       }
/*  85 */       else if (priority.intValue() == PRIORITY_LOW) {
/*  86 */         this.lowQueue.put(message);
/*     */       }
/*  88 */       else if (priority.intValue() == PRIORITY_COMMON_SMS) {
/*  89 */         this.commonQueueSms.put(message);
/*     */       }
/*  91 */       else if (priority.intValue() == PRIORITY_COMMON_WAP)
/*  92 */         this.commonQueueWap.put(message);
/*     */     }
/*     */     catch (Exception ex) {
/*  95 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addMessageNoBlock(AbstractMessage message, int priority)
/*     */   {
/* 107 */     if (priority == 0) {
/* 108 */       this.commonQueue.offer(message);
/*     */     }
/* 110 */     else if (priority == PRIORITY_VIP) {
/* 111 */       if (this.VIPQueue.offer(message)) {
/* 112 */         logger.debug("add message success.");
/*     */       }
/*     */       else {
/* 115 */         logger.error("add message fail. queue is full.");
/*     */       }
/*     */     }
/* 118 */     else if (priority == PRIORITY_COMMON) {
/* 119 */       if (this.commonQueue.offer(message)) {
/* 120 */         logger.debug("add message success.");
/*     */       }
/*     */       else {
/* 123 */         logger.error("add message fail. queue is full.");
/*     */       }
/*     */     }
/* 126 */     else if (priority == PRIORITY_LOW) {
/* 127 */       if (this.lowQueue.offer(message)) {
/* 128 */         logger.debug("add message success.");
/*     */       }
/*     */       else {
/* 131 */         logger.error("add message fail. queue is full.");
/*     */       }
/*     */ 
/*     */     }
/* 135 */     else if (priority == PRIORITY_COMMON_SMS) {
/* 136 */       if (this.commonQueueSms.offer(message)) {
/* 137 */         logger.debug("add message success.");
/*     */       }
/*     */       else {
/* 140 */         logger.error("add message fail. queue is full.");
/*     */       }
/*     */     }
/* 143 */     else if (priority == PRIORITY_COMMON_WAP)
/* 144 */       if (this.commonQueueWap.offer(message)) {
/* 145 */         logger.debug("add message success.");
/*     */       }
/*     */       else
/* 148 */         logger.error("add message fail. queue is full.");
/*     */   }
/*     */ 
/*     */   public AbstractMessage getMessage(Integer priority)
/*     */   {
/*     */     try
/*     */     {
/* 164 */       if ((priority == null) || (priority.intValue() == PRIORITY_COMMON)) {
/*     */         try {
/* 166 */           this.lock.lock();
/* 167 */           if (this.removing)
/* 168 */             this.removCondition.await();
/*     */         }
/*     */         finally {
/* 171 */           this.lock.unlock();
/*     */         }
/* 173 */         return (AbstractMessage)this.commonQueue.take();
/*     */       }
/*     */ 
/* 176 */       if (priority.intValue() == PRIORITY_VIP) {
/* 177 */         return (AbstractMessage)this.VIPQueue.take();
/*     */       }
/* 179 */       if (priority.intValue() == PRIORITY_LOW)
/* 180 */         return (AbstractMessage)this.lowQueue.take();
/* 181 */       if (priority.intValue() == PRIORITY_COMMON_SMS)
/*     */       {
/*     */         try {
/* 184 */           this.lock.lock();
/* 185 */           if (this.removing)
/* 186 */             this.removCondition.await();
/*     */         }
/*     */         finally {
/* 189 */           this.lock.unlock();
/*     */         }
/* 191 */         return (AbstractMessage)this.commonQueueSms.take();
/*     */       }
/* 193 */       if (priority.intValue() == PRIORITY_COMMON_WAP) {
/*     */         try {
/* 195 */           this.lock.lock();
/* 196 */           if (this.removing)
/* 197 */             this.removCondition.await();
/*     */         }
/*     */         finally {
/* 200 */           this.lock.unlock();
/*     */         }
/* 202 */         return (AbstractMessage)this.commonQueueWap.take();
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 206 */       ex.printStackTrace();
/*     */     }
/*     */ 
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   public int getSize(int priority)
/*     */   {
/* 218 */     if (priority == PRIORITY_VIP) {
/* 219 */       return this.VIPQueue.size();
/*     */     }
/* 221 */     if (priority == PRIORITY_LOW) {
/* 222 */       return this.lowQueue.size();
/*     */     }
/* 224 */     if (priority == PRIORITY_COMMON_SMS) {
/* 225 */       return this.commonQueueSms.size();
/*     */     }
/* 227 */     if (priority == PRIORITY_COMMON_WAP) {
/* 228 */       return this.commonQueueWap.size();
/*     */     }
/* 230 */     return this.commonQueue.size();
/*     */   }
/*     */ 
/*     */   public void removeMessage(Integer contentID)
/*     */   {
/* 239 */     logger.info("remove message by contentID:" + contentID);
/*     */     try {
/* 241 */       this.lock.lock();
/* 242 */       this.removing = true;
/*     */ 
/* 244 */       Iterator ite = this.commonQueue.iterator();
/*     */ 
/* 246 */       while (ite.hasNext()) {
/* 247 */         AbstractMessage amessage = (AbstractMessage)ite.next();
/* 248 */         if ((amessage != null) && (amessage.getContentid() == contentID)) {
/* 249 */           logger.debug("remove element:" + amessage.toString());
/* 250 */           ite.remove();
/*     */         }
/*     */       }
/*     */ 
/* 254 */       this.removing = false;
/* 255 */       this.removCondition.signalAll();
/*     */     }
/*     */     catch (Exception ex) {
/* 258 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 261 */       this.lock.unlock();
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.queue.MessageQueue
 * JD-Core Version:    0.6.0
 */