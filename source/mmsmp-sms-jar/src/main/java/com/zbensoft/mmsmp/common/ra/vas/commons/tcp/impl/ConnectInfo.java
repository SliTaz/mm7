/*    */ package com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl;
/*    */ 
/*    */

/*    */

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ public class ConnectInfo
/*    */ {
/* 14 */   private int connectId = -1;
/* 15 */   private ByteBuffer recvBuff = null;
/* 16 */   private List sendDataList = new ArrayList();
/* 17 */   private Object attachObj = null;
/* 18 */   private SelectionKey key = null;
/*    */ 
/*    */   public void setAttachObj(Object attachObj) {
/* 21 */     this.attachObj = attachObj;
/*    */   }
/*    */   public Object getAttachObj() {
/* 24 */     return this.attachObj;
/*    */   }
/*    */ 
/*    */   public void setKey(SelectionKey key) {
/* 28 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public SelectionKey getKey() {
/* 32 */     return this.key;
/*    */   }
/*    */ 
/*    */   public ConnectInfo(int connectId, int bufferCapacity) {
/* 36 */     this.connectId = connectId;
/* 37 */     this.recvBuff = ByteBuffer.allocate(bufferCapacity);
/*    */   }
/*    */   public int getConnectId() {
/* 40 */     return this.connectId;
/*    */   }
/*    */ 
/*    */   public void addSendData(byte[] bytes) {
/* 44 */     this.sendDataList.add(ByteBuffer.wrap(bytes));
/*    */   }
/*    */ 
/*    */   public List getSendData() {
/* 48 */     return this.sendDataList;
/*    */   }
/*    */ 
/*    */   public SocketAddress getRemoteAddress()
/*    */   {
/* 53 */     SocketChannel sc = (SocketChannel)this.key.channel();
/* 54 */     return sc.socket().getRemoteSocketAddress();
/*    */   }
/*    */ 
/*    */   public ByteBuffer getRecvBuff() {
/* 58 */     return this.recvBuff;
/*    */   }
/*    */ 
/*    */   public void setRecvBuff(ByteBuffer buff) {
/* 62 */     this.recvBuff = buff;
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.impl.ConnectInfo
 * JD-Core Version:    0.6.0
 */