 package com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl;
 import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IClientHandler;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.TcpClient;

 
 public class TcpClientImpl
   implements TcpClient
 {
   static Logger log = Logger.getLogger(TcpClientImpl.class);
 
   private SocketChannel socketChannel = null;
 
   private int maxPacketSize = 1024;
 
   private IClientHandler clientHandler = null;
 
   private ByteBuffer recvBuff = null;
 
   private boolean autoReconnect = false;
 
   private long reconnectInterval = 0L;
 
   private boolean isConnected = false;
 
   private String ip = null;
 
   private int port = 0;
 
   ReconnectThread reconnectThread = null;
 
   static RecvThread recvThread = null;
 
   static List tcpClientList = new ArrayList();
 
   private static Selector selector = null;
 
   private IClientHandler getClientHandler() {
     return this.clientHandler;
   }
 
   public boolean connect(String ip, int port) {
     try {
       this.ip = ip;
       this.port = port;
 
       InetSocketAddress addr = new InetSocketAddress(ip, port);
 
       this.socketChannel = SocketChannel.open(addr);
 
       this.socketChannel.configureBlocking(false);
 
       synchronized (tcpClientList) {
         if (selector == null) {
           selector = Selector.open();
         }
 
         if (!tcpClientList.contains(this)) {
           tcpClientList.add(this);
         }
 
         this.socketChannel.register(selector, 1, this);
 
         if (recvThread == null) {
           recvThread = new RecvThread();
           recvThread.start();
         }
       }
       this.recvBuff.clear();
       this.isConnected = true;
 
       this.clientHandler.onConnect(ip, port);
 
       return true;
     } catch (Exception e) {
       log.error("连接错误, 可能远程的服务器已经关闭", e);
 
       if (this.autoReconnect)
         startReconnectThread();
     }
     return false;
   }
 
   public boolean disconnect()
   {
     try
     {
       this.socketChannel.register(selector, 0);
       this.socketChannel.close();
     } catch (Exception ex) {
       ex.printStackTrace();
     }
 
     synchronized (tcpClientList) {
       if (tcpClientList.contains(this)) {
         tcpClientList.remove(this);
         if (tcpClientList.size() < 1) {
           recvThread.shutdown();
           recvThread = null;
         }
       }
     }
     stopReconnectThread();
     this.clientHandler.onDisconnect();
 
     return true;
   }
 
   public synchronized int send(byte[] bytes)
   {
     try {
       ByteBuffer bb = ByteBuffer.wrap(bytes);
       while (bb.hasRemaining()) {
         this.socketChannel.write(bb);
       }
       if (TypeConvert.byte2int(bytes, 0) != 1) {
         this.clientHandler.onSendedMsg(bytes);
       }
       else {
         byte[] temp = new byte[bytes.length - 8];
         System.arraycopy(bytes, 8, temp, 0, temp.length);
         ByteArrayInputStream byteIn = new ByteArrayInputStream(temp);
         ObjectInputStream in = new ObjectInputStream(byteIn);
         Object o = in.readObject();
         this.clientHandler.onSendedMsg((Serializable)o);
       }
       return 0;
     } catch (Exception ex) {
       ex.printStackTrace();
       try {
         this.socketChannel.close();
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
     return 1;
   }
 
   private void onConnectionBroken()
   {
     this.isConnected = false;
     try {
       this.socketChannel.close();
     }
     catch (Exception localException) {
     }
     this.socketChannel = null;
     this.clientHandler.onDisconnect();
 
     if (this.autoReconnect)
       startReconnectThread();
   }
 
   public void setDataHandler(IClientHandler handler, int maxPacketSize)
   {
     this.clientHandler = handler;
     this.maxPacketSize = maxPacketSize;
     this.recvBuff = ByteBuffer.allocate(maxPacketSize);
   }
 
   public int send(Serializable obj)
   {
     try
     {
       ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
       ObjectOutputStream out = new ObjectOutputStream(byteOut);
       out.writeObject(obj);
 
       int len = byteOut.toByteArray().length;
       byte[] bytes = new byte[len + 8];
       TypeConvert.int2byte(1, bytes, 0);
       TypeConvert.int2byte(len, bytes, 4);
       System.arraycopy(byteOut.toByteArray(), 0, bytes, 8, 
         byteOut.toByteArray().length);
       return send(bytes);
     }
     catch (Exception e) {
       log.error("发送失败", e);
     }return 1;
   }
 
   public boolean isConnected()
   {
     return this.isConnected;
   }
 
   public void setAutoReconnect(boolean autoReconnect, long reconnectInterval) {
     this.autoReconnect = autoReconnect;
     this.reconnectInterval = reconnectInterval;
   }
 
   void startReconnectThread()
   {
     if (this.reconnectThread == null) {
       this.reconnectThread = new ReconnectThread();
       this.reconnectThread.start();
     }
   }
 
   void stopReconnectThread()
   {
     if (this.reconnectThread != null) {
       this.reconnectThread.shutdown();
       this.reconnectThread = null;
     }
   }
 
   public class ReconnectThread extends Thread
   {
     private boolean threadRunFlag = true;
 
     public ReconnectThread() {  }
 
     public void run() { int sleepInterval = 500;
       int totalSleep = 0;
       while (this.threadRunFlag)
       {
         try {
           Thread.sleep(sleepInterval);
         } catch (InterruptedException e) {
           TcpClientImpl.log.error("重连等待异常！");
           e.printStackTrace();
         }
         totalSleep += sleepInterval;
         if (totalSleep >= TcpClientImpl.this.reconnectInterval) {
           totalSleep = 0;
           boolean connected = TcpClientImpl.this.connect(TcpClientImpl.this.ip, TcpClientImpl.this.port);
           if (connected) {
             TcpClientImpl.this.reconnectThread = null;
             break;
           }
         }
       } }
 
     public void shutdown()
     {
       this.threadRunFlag = false;
     }
   }
 
   private class RecvThread extends Thread
   {
     private boolean threadRunFlag = true;
 
     private RecvThread() {  }
 
     public void run() { while (this.threadRunFlag)
         try {
           int keyCount = TcpClientImpl.selector.select(500L);
           if (keyCount > 0)
             readChannels();
         }
         catch (Exception e) {
           e.printStackTrace();
         }
     }
 
     public void shutdown()
     {
       this.threadRunFlag = false;
     }
 
     private void readChannels()
     {
       Iterator it = TcpClientImpl.selector.selectedKeys().iterator();
       while (it.hasNext()) {
         SelectionKey skey = (SelectionKey)it.next();
         it.remove();
 
         if (!skey.isReadable()) {
           continue;
         }
         SocketChannel socketChannel = (SocketChannel)skey.channel();
         TcpClientImpl tcpClient = (TcpClientImpl)skey.attachment();
         IClientHandler clientHandler = tcpClient.getClientHandler();
         ByteBuffer buffer = tcpClient.recvBuff;
         buffer.limit(buffer.capacity());
 
         boolean connectionBroken = true;
         try {
           while (socketChannel.read(buffer) > 0) {
             connectionBroken = false;
             sliceAndDealWithData(tcpClient, clientHandler);
           }
         } catch (IOException e) {
           e.printStackTrace();
         }
 
         if (connectionBroken) {
           skey.cancel();
           tcpClient.onConnectionBroken();
         }
       }
     }
 
     private void sliceAndDealWithData(TcpClientImpl ci, IClientHandler dh) {
       ByteBuffer buff = ci.recvBuff;
 
       int dataLen = buff.position();
 
       int iPos = 0;
 
       while (dataLen - iPos > 4) {
         buff.position(iPos);
 
         if (buff.getInt(iPos) == 1) {
           if (dataLen - iPos <= 8) break;
           int packageSize = buff.getInt(iPos + 4);
           if (packageSize > TcpClientImpl.this.maxPacketSize) {
             buff.clear();
             return;
           }
           if (dataLen - iPos - 8 < packageSize)
             break;
           buff.position(iPos + 8);
           byte[] byteObj = new byte[packageSize];
           buff.get(byteObj, 0, packageSize);
 
           ByteArrayInputStream byteIn = new ByteArrayInputStream(
             byteObj);
           ObjectInputStream in = null;
           Object o = null;
           try {
             in = new ObjectInputStream(byteIn);
             o = in.readObject();
           } catch (IOException e) {
             TcpClientImpl.log.error("接收对象的时候发生错误");
             e.printStackTrace();
             break;
           } catch (ClassNotFoundException e) {
             TcpClientImpl.log.error("接收对象的时候发生错误, 对象没有找到");
             e.printStackTrace();
             break;
           }
           dh.onReceiveMsg(
             (Serializable)o);
 
           iPos += 8 + packageSize;
         }
         else
         {
           byte[] msg = new byte[dataLen - iPos];
           buff.get(msg, 0, dataLen - iPos);
           int sliceResult = dh.slice(msg);
           if (sliceResult <= 0) break;
           byte[] msgPkt = new byte[sliceResult];
           System.arraycopy(msg, 0, msgPkt, 0, sliceResult);
           dh.onReceiveMsg(msgPkt);
 
           iPos += sliceResult;
         }
 
       }
 
       if (dataLen - iPos > 0) {
         buff.position(iPos);
         buff.limit(dataLen);
         buff.compact();
       } else {
         buff.clear();
       }
     }
   }
 }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.impl.TcpClientImpl
 * JD-Core Version:    0.6.0
 */