/*     */ package com.zbensoft.mmsmp.common.ra.common.message;
/*     */ import com.cx.queue.MessageReceiver;
/*     */ import com.cx.queue.MessageSender;
/*     */ import com.cx.queue.MessageSenderPool;
import com.zbensoft.mmsmp.common.ra.common.db.cache.ServCtlsCache;
import com.zbensoft.mmsmp.common.ra.common.db.cache.SysappCache;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ServCtls;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Sysapp;

/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MQ
/*     */ {
/*  22 */   private static final Log logger = LogFactory.getLog(MQ.class);
/*     */ 
/*  24 */   private static MQ instance = null;
/*     */ 
/*  45 */   private int poolSize = 10;
/*     */ 
/*  47 */   private boolean stopped = false;
/*     */ 
/*  49 */   private ConcurrentMap<String, MessageSenderPool> senderPoolMap = new ConcurrentHashMap();
/*     */ 
/* 112 */   private Map<String, MessageReceiverThread> paylayXReceiveThreadsMap = null;
/*     */ 
/* 114 */   private Map<String, Map<String, MessageReceiverThread>> controllerReceiveThreadsMap = new HashMap();
/*     */ 
/* 116 */   private ReceiveMessageList receiveList = new ReceiveMessageList(null);
/*     */ 
/*     */   public static MQ getInstance()
/*     */   {
/*  31 */     if (instance == null) {
/*  32 */       synchronized (MQ.class.getName()) {
/*  33 */         if (instance == null) {
/*  34 */           instance = new MQ();
/*     */         }
/*     */       }
/*     */     }
/*  38 */     return instance;
/*     */   }
/*     */ 
/*     */   public void stopMQ()
/*     */   {
/*  56 */     close();
/*  57 */     this.stopped = true;
/*     */   }
/*     */ 
/*     */   public void startMQ() {
/*  61 */     this.stopped = false;
/*     */   }
/*     */ 
/*     */   public void setPoolSize(int poolSize)
/*     */   {
/*  69 */     this.poolSize = poolSize;
/*     */   }
/*     */ 
/*     */   public void sendMessage(IMessage message)
/*     */     throws SendMessageException
/*     */   {
/*  78 */     sendMessage(message, false);
/*     */   }
/*     */ 
/*     */   public void sendMessage(IMessage message, boolean adFlag)
/*     */     throws SendMessageException
/*     */   {
/*  90 */     if (this.stopped) {
/*  91 */       return;
/*     */     }
/*  93 */     MessageSenderPool senderPool = getMessageSenderPool(message, adFlag ? -1 : message.getServiceId());
/*  94 */     if (senderPool == null) {
/*  95 */       throw new SendMessageException("no availabel SenderPool found.", 
/*  96 */         null);
/*     */     }
/*  98 */     MessageSender sender = senderPool.getSender();
/*     */ 
/* 100 */     if (sender == null)
/* 101 */       throw new SendMessageException("no availabel Sender found.", null);
/*     */     try
/*     */     {
/* 104 */       sender.send(message.encodeString());
/*     */     } catch (Exception e) {
/* 106 */       throw new SendMessageException("failed to send message.", e);
/*     */     } finally {
/* 108 */       senderPool.putSender(sender);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IMessage getPaylayXMessage(String paylayXId)
/*     */     throws GetMessageException
/*     */   {
/*     */     try
/*     */     {
/* 127 */       if ((!this.stopped) && (this.paylayXReceiveThreadsMap == null)) {
/* 128 */         this.paylayXReceiveThreadsMap = new HashMap();
/* 129 */         List servCtlsList = ServCtlsCache.getInstance().getServCtlsList();
/* 130 */         String mqAppName = null;
/* 131 */         String bakMqAppName = null;
/* 132 */         String paylayXAppName = null;
/* 133 */         MessageReceiverThread thread = null;
/*     */ 
/* 135 */         for (ServCtls servCtls : servCtlsList) {
/* 136 */           mqAppName = servCtls.getMqappname();
/* 137 */           bakMqAppName = servCtls.getBakmqappname();
/* 138 */           paylayXAppName = servCtls.getPaylayxappname();
/* 139 */           if ((paylayXAppName != null) && (paylayXAppName.trim().equals(""))) {
/* 140 */             paylayXAppName = null;
/*     */           }
/* 142 */           if (bakMqAppName == null) {
/* 143 */             bakMqAppName = mqAppName;
/*     */           }
/* 145 */           if (((paylayXAppName != null) && (paylayXId != null) && 
/* 146 */             (!paylayXAppName.equals(paylayXId))) || 
/* 147 */             (this.paylayXReceiveThreadsMap.containsKey(mqAppName + 
/* 148 */             bakMqAppName))) continue;
/* 149 */           thread = new MessageReceiverThread(mqAppName, 
/* 150 */             bakMqAppName, this.receiveList, "MT");
/* 151 */           thread.start();
/* 152 */           this.paylayXReceiveThreadsMap.put(mqAppName + bakMqAppName, 
/* 153 */             thread);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 158 */       return this.receiveList.getMessage(); } catch (Exception e) {
/*     */     }
/* 160 */     throw new GetMessageException("failed to receive message.", e);
/*     */   }
/*     */ 
/*     */   public IMessage getControllerMessage(String controllerId)
/*     */     throws GetMessageException
/*     */   {
/* 172 */     if (this.stopped)
/* 173 */       return null;
/*     */     try
/*     */     {
/* 176 */       Map controllerReceiveThreadsMap = 
/* 177 */         (Map)this.controllerReceiveThreadsMap
/* 177 */         .get(controllerId);
/*     */ 
/* 179 */       if ((!this.stopped) && (controllerReceiveThreadsMap == null)) {
/* 180 */         controllerReceiveThreadsMap = new HashMap();
/*     */ 
/* 182 */         this.controllerReceiveThreadsMap.put(controllerId, 
/* 183 */           controllerReceiveThreadsMap);
/*     */ 
/* 185 */         List servCtlrsList = ServCtlsCache.getInstance().getServCtlsList();
/* 186 */         String mqAppName = null;
/* 187 */         String bakMqAppName = null;
/* 188 */         String ctlrAppName = null;
/* 189 */         MessageReceiverThread thread = null;
/*     */ 
/* 191 */         for (ServCtls servCtls : servCtlrsList) {
/* 192 */           mqAppName = servCtls.getMqappname();
/* 193 */           bakMqAppName = servCtls.getBakmqappname();
/*     */ 
/* 195 */           if (bakMqAppName == null) {
/* 196 */             bakMqAppName = mqAppName;
/*     */           }
/* 198 */           ctlrAppName = servCtls.getCtlappname();
/*     */ 
/* 200 */           if (!ctlrAppName.equals(controllerId)) {
/*     */             continue;
/*     */           }
/* 203 */           if (controllerReceiveThreadsMap.containsKey(mqAppName + 
/* 204 */             bakMqAppName + ctlrAppName)) continue;
/* 205 */           thread = new MessageReceiverThread(mqAppName, 
/* 206 */             bakMqAppName, this.receiveList, ctlrAppName);
/* 207 */           thread.start();
/*     */ 
/* 209 */           controllerReceiveThreadsMap.put(mqAppName + 
/* 210 */             bakMqAppName, thread);
/*     */         }
/*     */ 
/* 214 */         this.controllerReceiveThreadsMap.put(controllerId, 
/* 215 */           controllerReceiveThreadsMap);
/*     */       }
/*     */ 
/* 218 */       return this.receiveList.getMessage(); } catch (Exception e) {
/*     */     }
/* 220 */     throw new GetMessageException("failed to receive message.", e);
/*     */   }
/*     */ 
/*     */   private MessageSenderPool getMessageSenderPool(IMessage message, int servId)
/*     */   {
/* 231 */     ServCtls servCtlrs = ServCtlsCache.getInstance().getServCtls(servId);
/*     */ 
/* 233 */     logger.info("*********************************************");
/* 234 */     logger.info(servCtlrs.toString());
/* 235 */     logger.info("*********************************************");
/* 236 */     if (servCtlrs == null) {
/* 237 */       logger.warn("no service controlle info found -- servId: " + servId);
/* 238 */       return null;
/*     */     }
/*     */ 
/* 241 */     Sysapp mqApp = SysappCache.getInstance().getApp(servCtlrs.getMqappname());
/*     */ 
/* 243 */     if (mqApp == null) {
/* 244 */       logger.warn("no mqApp info found -- servId: " + servId + 
/* 245 */         " mqAppName: " + servCtlrs.getMqappname());
/* 246 */       return null;
/*     */     }
/*     */ 
/* 249 */     Sysapp ctlApp = SysappCache.getInstance().getApp(
/* 250 */       servCtlrs.getCtlappname());
/*     */ 
/* 252 */     if ((servId != -1) && (ctlApp == null)) {
/* 253 */       logger.warn("no ctlApp info found -- servId: " + servId + 
/* 254 */         " ctlAppName: " + servCtlrs.getCtlappname());
/* 255 */       return null;
/*     */     }
/*     */ 
/* 258 */     Sysapp backMqApp = SysappCache.getInstance().getApp(
/* 259 */       servCtlrs.getBakmqappname());
/*     */ 
/* 261 */     if (backMqApp == null) {
/* 262 */       backMqApp = mqApp;
/*     */     }
/* 264 */     String queueName = servId == -1 ? "MT" : ctlApp.getStrappname();
/*     */ 
/* 266 */     if (((message instanceof MT_MMMessage)) || ((message instanceof MT_SMMessage)) || 
/* 267 */       ((message instanceof MT_WapPushMessage))) {
/* 268 */       queueName = "MT";
/*     */     }
/* 270 */     String senderPoolName = queueName + mqApp.getStrappname() + 
/* 271 */       backMqApp.getStrappname();
/*     */ 
/* 273 */     if (this.senderPoolMap.containsKey(senderPoolName)) {
/* 274 */       return (MessageSenderPool)this.senderPoolMap.get(senderPoolName);
/*     */     }
/* 276 */     String queueURL = mqApp.getStrappagentipaddress() + ":1115;" + 
/* 277 */       backMqApp.getStrappagentipaddress() + ":1115";
/*     */ 
/* 279 */     if (logger.isDebugEnabled()) {
/* 280 */       logger.debug("queueURL:" + queueURL);
/*     */     }
/* 282 */     if ((queueURL == null) || (queueName == null)) {
/* 283 */       logger.warn("queueName or queueURL is null");
/* 284 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 288 */       MessageSenderPool senderPool = new MessageSenderPool(this.poolSize, 
/* 289 */         queueURL, queueName);
/*     */ 
/* 291 */       this.senderPoolMap.put(senderPoolName, senderPool);
/*     */ 
/* 293 */       return senderPool; } catch (Exception e) {
/*     */     }
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */   public void refresh()
/*     */   {
/* 301 */     close();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 309 */     Map temp = new HashMap();
/* 310 */     temp.putAll(this.senderPoolMap);
/*     */ 
/* 312 */     this.senderPoolMap = new ConcurrentHashMap();
/* 313 */     String key = null;
/* 314 */     MessageSenderPool senderPool = null;
/*     */ 
/* 316 */     for (Iterator iter = temp.keySet().iterator(); iter.hasNext(); ) {
/* 317 */       key = (String)iter.next();
/*     */ 
/* 319 */       senderPool = (MessageSenderPool)temp.get(key);
/* 320 */       senderPool.clear();
/*     */     }
/*     */ 
/* 323 */     if (this.paylayXReceiveThreadsMap != null) {
/* 324 */       Map tempMap = this.paylayXReceiveThreadsMap;
/*     */ 
/* 326 */       for (Iterator iterTemp = tempMap.values()
/* 327 */         .iterator(); 
/* 327 */         iterTemp.hasNext(); )
/*     */       {
/* 328 */         ((MessageReceiverThread)iterTemp.next()).myStop();
/*     */       }
/*     */ 
/* 331 */       this.paylayXReceiveThreadsMap = null;
/*     */     }
/*     */ 
/* 334 */     for (Iterator iterTemp2 = this.controllerReceiveThreadsMap
/* 335 */       .values().iterator(); 
/* 335 */       iterTemp2.hasNext(); )
/*     */     {
/* 336 */       Map controllerReceiveThreadsMap = 
/* 337 */         (Map)iterTemp2
/* 337 */         .next();
/*     */ 
/* 339 */       if (controllerReceiveThreadsMap != null) {
/* 340 */         Map tempMap = controllerReceiveThreadsMap;
/*     */ 
/* 342 */         for (Iterator iterTemp = tempMap
/* 343 */           .values().iterator(); 
/* 343 */           iterTemp.hasNext(); )
/*     */         {
/* 344 */           ((MessageReceiverThread)iterTemp.next()).myStop();
/*     */         }
/* 346 */         controllerReceiveThreadsMap = null;
/*     */       }
/*     */     }
/*     */ 
/* 350 */     this.controllerReceiveThreadsMap = new HashMap();
/*     */   }
/*     */ 
/*     */   private class MessageReceiverThread extends Thread
/*     */   {
/* 400 */     private final Logger log = Logger.getLogger(MessageReceiverThread.class);
/*     */ 
/* 402 */     private String mqAppName = null;
/*     */ 
/* 404 */     private String bakMqAppName = null;
/*     */ 
/* 406 */     private MQ.ReceiveMessageList receiveList = null;
/*     */ 
/* 408 */     private String queueName = null;
/*     */ 
/* 410 */     private MessageReceiver receiver = null;
/*     */ 
/* 412 */     private boolean isWorking = true;
/*     */ 
/*     */     public void myStop()
/*     */     {
/* 419 */       this.isWorking = false;
/* 420 */       this.receiver.close();
/*     */     }
/*     */ 
/*     */     public MessageReceiverThread(String mqAppName, String bakMqAppName, MQ.ReceiveMessageList receiveList, String queueName)
/*     */     {
/* 432 */       this.mqAppName = mqAppName;
/* 433 */       this.bakMqAppName = bakMqAppName;
/* 434 */       this.receiveList = receiveList;
/* 435 */       this.queueName = queueName;
/*     */     }
/*     */ 
/*     */     private void init()
/*     */     {
/* 443 */       if (this.log.isDebugEnabled()) {
/* 444 */         this.log.debug("MQAppName is > " + this.mqAppName + 
/* 445 */           " <BackMQAppName is> " + this.bakMqAppName);
/*     */       }
/* 447 */       Sysapp mqApp = SysappCache.getInstance().getApp(this.mqAppName);
/* 448 */       Sysapp backMqApp = SysappCache.getInstance().getApp(this.bakMqAppName);
/* 449 */       String queueURL = mqApp.getStrappagentipaddress() + ":1115;" + 
/* 450 */         backMqApp.getStrappagentipaddress() + ":1115";
/* 451 */       this.receiver = new MessageReceiver(queueURL, this.queueName);
/*     */     }
/*     */     public void run() {
/* 454 */       init();
/* 455 */       String message = null;
/*     */ 
/* 457 */       while (this.isWorking) {
/* 458 */         if (this.receiveList.size() > 30) {
/*     */           try {
/* 460 */             Thread.sleep(100L);
/*     */           } catch (Exception e) {
/* 462 */             Thread.currentThread().interrupt();
/*     */           }
/*     */         }
/*     */         else
/*     */           try
/*     */           {
/* 468 */             message = (String)this.receiver.receive();
/* 469 */             if (message != null)
/* 470 */               this.receiveList.addMessage(MessageDecoder.getMessage(message));
/*     */           } catch (Exception e) {
/* 472 */             MQ.logger.error("", e);
/*     */           }
/*     */       }
/*     */       try
/*     */       {
/* 477 */         this.receiver.close();
/*     */       } catch (Throwable e) {
/* 479 */         MQ.logger.error("", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ReceiveMessageList
/*     */   {
/* 359 */     private List<IMessage> messageList = new LinkedList();
/*     */ 
/*     */     private ReceiveMessageList()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int size() {
/* 366 */       return this.messageList.size();
/*     */     }
/*     */ 
/*     */     public void addMessage(IMessage message)
/*     */     {
/* 374 */       synchronized (this.messageList) {
/* 375 */         this.messageList.add(message);
/*     */       }
/*     */     }
/*     */ 
/*     */     public IMessage getMessage()
/*     */     {
/* 384 */       synchronized (this.messageList) {
/* 385 */         if ((this.messageList == null) || (this.messageList.size() == 0)) {
/* 386 */           return null;
/*     */         }
/* 388 */         return (IMessage)this.messageList.remove(0);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MQ
 * JD-Core Version:    0.6.0
 */