 package com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl;

 import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.IServerHandler;
 import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.TcpServer;
 import org.apache.log4j.Logger;

 import java.io.*;
 import java.net.InetSocketAddress;
 import java.nio.ByteBuffer;
 import java.nio.channels.*;
 import java.util.*;


 public class TcpServerImpl
   implements TcpServer
 {
   static Logger log = Logger.getLogger(TcpServerImpl.class);
 
   private Map connections = new HashMap();
 
   private List sendList = new ArrayList();
 
   private ServerSocketChannel serverSocketChannel = null;
 
   private int maxPacketSize = 1024;
 
   private boolean bCanRun = true;
 
   Selector selector = null;
 
   IServerHandler serverHandler = null;
 
   SendThread send = null;
 
   Run run = null;
 
   int connectCount = -1;
 
   public boolean beginListen(int port, String ipAddress)
   {
     try {
       InetSocketAddress socketAddress = null;
       if ((ipAddress != null) && (ipAddress.length() > 0))
         socketAddress = new InetSocketAddress(ipAddress, port);
       else
         socketAddress = new InetSocketAddress(port);
       this.serverSocketChannel = ServerSocketChannel.open();
 
       this.serverSocketChannel.configureBlocking(false);
       this.selector = Selector.open();
       this.serverSocketChannel.socket().bind(socketAddress);
       this.serverSocketChannel.register(this.selector, 16);
       log.info("已经对" + port + "端时行监听!");
       this.run = new Run();
       this.run.start();
       log.info("启动发送线程");
       this.send = new SendThread();
       this.send.start();
       return true;
     } catch (IOException e) {
       log.error("开始监听port端口的时候出现问题");
       log.error(e.getMessage());
     }return false;
   }
 
   public void setDataHandler(IServerHandler handler, int maxPacketSize)
   {
     this.serverHandler = handler;
     this.maxPacketSize = maxPacketSize;
   }
 
   public boolean beginListen(int port) {
     return beginListen(port, null);
   }
 
   public boolean endListening() {
     this.bCanRun = false;
     this.connectCount = -1;
     try
     {
       for (Iterator iter = this.connections.entrySet().iterator(); iter
         .hasNext(); )
       {
         Map.Entry entry = (Map.Entry)iter.next();
         ConnectInfo ci = (ConnectInfo)entry.getValue();
         SelectionKey sk = ci.getKey();
         sk.cancel();
         this.serverHandler.onDisconnect(
           ((ConnectInfo)sk.attachment()).getConnectId(), sk.attachment());
       }
       this.selector.close();
       this.serverSocketChannel.socket().close();
       this.connections.clear();
       this.sendList.clear();
       this.send.interrupt();
       this.run.interrupt();
       log.info("成功关闭Server");
       return true;
     } catch (Exception ex) {
       log.error("关闭Server出错: " + ex.getMessage());
       ex.printStackTrace();
     }return false;
   }
 
   public void attach(int connectId, Object obj)
   {
     if (this.connections.containsKey(new Integer(connectId))) {
       ConnectInfo ci = 
         (ConnectInfo)this.connections
         .get(new Integer(connectId));
       ci.setAttachObj(obj);
     } else {
       log.warn("attach obj的时候出错, 可能连接列表里没有connectId");
     }
   }
 
   public int sendToClient(int connectId, byte[] bytes) {
     synchronized (this.connections) {
       if (this.connections.containsKey(new Integer(connectId))) {
         ConnectInfo ci = (ConnectInfo)this.connections.get(
           new Integer(connectId));
         ci.addSendData(bytes);
         SelectionKey key = ci.getKey();
         try {
           if (key != null) {
             key.interestOps(5);
           }
           this.selector.wakeup();
         } catch (CancelledKeyException ex) {
           ex.printStackTrace();
           this.connections.remove(ci);
           this.sendList.remove(key);
         }
         this.connections.notifyAll();
         return 0;
       }
       log.warn("sendToClient 的时候出错,可能连接列表里没有connectId");
       return 1;
     }
   }
 
   public void disconnect(int connectId)
   {
     Integer mapKey = new Integer(connectId);
     Channel channel = null;
 
     synchronized (this.connections) {
       ConnectInfo ci = (ConnectInfo)this.connections.get(mapKey);
       if (ci != null) {
         channel = ci.getKey().channel();
       }
     }
 
     if (channel != null)
       try {
         channel.close();
       }
       catch (Exception localException)
       {
       }
   }
 
   private void removeConnection(SelectionKey key)
   {
     ConnectInfo ci = (ConnectInfo)key.attachment();
     try {
       ci.getKey().channel().close();
     } catch (Exception localException) {
     }
     synchronized (this.connections) {
       this.connections.remove(new Integer(ci.getConnectId()));
       this.connections.notifyAll();
     }
     synchronized (this.sendList) {
       this.sendList.remove(key);
       this.sendList.notifyAll();
     }
   }
 
   private synchronized void sliceAndDealWithData(ConnectInfo ci, IServerHandler dh)
   {
     ByteBuffer buff = ci.getRecvBuff();
 
     int dataLen = buff.position();
 
     int iPos = 0;
 
     int count = 0;
     while (dataLen - iPos > 4)
     {
       buff.position(iPos);
       try {
         if (buff.getInt(iPos) == 1) {
           if (dataLen - iPos <= 8) break;
           int packageSize = buff.getInt(iPos + 4);
 
           if (packageSize > this.maxPacketSize) {
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
             log.error("接收对象的时候发生错误");
             e.printStackTrace();
             break;
           } catch (ClassNotFoundException e) {
             log.error("接收对象的时候发生错误, 对象没有找到");
             e.printStackTrace();
             break;
           }
           dh.onReceiveMsg(ci.getConnectId(), ci.getAttachObj(), 
             (Serializable)o);
 
           iPos += 8 + packageSize;
           continue;
         }
 
         byte[] msg = new byte[dataLen - iPos];
         buff.get(msg, 0, dataLen - iPos);
         int sliceResult = dh.slice(ci.getConnectId(), msg);
         if (sliceResult > 0) {
           byte[] msgPkt = new byte[sliceResult];
           System.arraycopy(msg, 0, msgPkt, 0, sliceResult);
           dh.onReceiveMsg(ci.getConnectId(), ci.getAttachObj(), 
             msgPkt);
 
           iPos += sliceResult;
         }
       }
       catch (Exception e) {
         log.error("songzbtest sliceAndDealWithData is error", e);
         break;
       }
       count++;
       log.info("count:\t\t" + count);
       if (count > 10)
       {
         break;
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
 
   private void accept(SelectionKey key)
     throws Exception
   {
     ServerSocketChannel server = (ServerSocketChannel)key.channel();
     SocketChannel sc = server.accept();
     String remoteAddress = sc.socket().getRemoteSocketAddress().toString();
     String ip = remoteAddress.substring(1, remoteAddress.lastIndexOf(":"));
     int port = Integer.parseInt(remoteAddress.substring(
       remoteAddress.lastIndexOf(":") + 1, remoteAddress.length()));
     log.info(remoteAddress + " 成功接入Server");
 
     int connectId = ++this.connectCount;
     ConnectInfo ci = new ConnectInfo(connectId, this.maxPacketSize);
 
     sc.configureBlocking(false);
     SelectionKey sk = sc.register(this.selector, 1, ci);
     ci.setKey(sk);
     synchronized (this.connections) {
       this.connections.put(new Integer(connectId), ci);
       this.connections.notifyAll();
     }
     this.serverHandler.onConnect(connectId, ip, port);
   }
 
   private void read(SelectionKey key)
   {
     try {
       int count = 0;
       SocketChannel sc = (SocketChannel)key.channel();
 
       ByteBuffer buff = null;
       ConnectInfo ci = (ConnectInfo)key.attachment();
 
       buff = ci.getRecvBuff();
       try
       {
         while ((count = sc.read(buff)) > 0);
       }
       catch (IOException ex)
       {
         log.error(sc.socket().getRemoteSocketAddress().toString() + 
           " 强行关闭连接");
         key.cancel();
         synchronized (this.connections) {
           if (this.connections.containsKey(key)) {
             this.connections.remove(key);
           }
           this.connections.notifyAll();
         }
         synchronized (this.sendList) {
           if (this.sendList.contains(key)) {
             this.sendList.remove(key);
           }
           this.sendList.notifyAll();
         }
         this.serverHandler
           .onDisconnect(ci.getConnectId(), ci.getAttachObj());
         sc.close();
       }
 
       if (count == -1)
       {
         log.info(sc.socket().getRemoteSocketAddress().toString() + 
           " 关闭连接");
         key.cancel();
         synchronized (this.connections) {
           if (this.connections.containsKey(key)) {
             this.connections.remove(key);
           }
           this.connections.notifyAll();
         }
         synchronized (this.sendList) {
           if (this.sendList.contains(key)) {
             this.sendList.remove(key);
           }
           this.sendList.notifyAll();
         }
         this.serverHandler
           .onDisconnect(ci.getConnectId(), ci.getAttachObj());
         sc.close();
       }
 
       sliceAndDealWithData(ci, this.serverHandler);
     }
     catch (Exception ex)
     {
       log.error(ex);
       key.cancel();
       try
       {
         key.channel().close();
       } catch (IOException e) {
         log.error(e);
         e.printStackTrace();
       }
       synchronized (this.connections) {
         if (this.connections.containsKey(key)) {
           this.connections.remove(key);
         }
         this.connections.notifyAll();
       }
       synchronized (this.sendList) {
         if (this.sendList.contains(key)) {
           this.sendList.remove(key);
         }
         this.sendList.notifyAll();
       }
       ex.printStackTrace();
       log.error("从客户端读数据的时候发生错误");
       log.error(ex.getMessage());
     }
   }
 
   private void send(SelectionKey key)
   {
     synchronized (this.sendList) {
       this.sendList.add(key);
       this.sendList.notifyAll();
     }
   }
 
   private SelectionKey getSend() {
     try {
       synchronized (this.sendList) {
         if (this.sendList.isEmpty()) {
           this.sendList.wait();
         } else {
           SelectionKey key = null;
           key = (SelectionKey)this.sendList.remove(0);
           return key;
         }
       }
       return null; } catch (Exception ex) {
     }
     return null;
   }
 
   public int sendToClient(int connectId, Serializable obj)
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
       System.arraycopy(byteOut.toByteArray(), 0, bytes, 8, len);
       sendToClient(connectId, bytes);
       return 0;
     }
     catch (IOException e) {
       e.printStackTrace();
     }return 1;
   }
 
   private class Run extends Thread
   {
     private Run()
     {
     }
 
     public void run()
     {
       while (TcpServerImpl.this.bCanRun)
       {
         try {
           selectKeys();
         } catch (Exception ex) {
           TcpServerImpl.log.error(ex.getMessage());
         }
 
         yield();
       }
     }
 
     private void selectKeys() throws Exception {
       int n = TcpServerImpl.this.selector.select();
       if (n == 0) {
         return;
       }
 
       Iterator it = TcpServerImpl.this.selector.selectedKeys().iterator();
 
       label125: while (it.hasNext()) {
         SelectionKey key = (SelectionKey)it.next();
 
         if (key.isValid())
         {
           if (key.isAcceptable()) {
             TcpServerImpl.this.accept(key);
           }
 
           try
           {
             if (key.isReadable()) {
               TcpServerImpl.this.read(key);
             }
 
             if (!key.isWritable()) break label125; TcpServerImpl.this.send(key);
           }
           catch (CancelledKeyException ex)
           {
             TcpServerImpl.this.removeConnection(key);
           }
         }
         else
         {
           key.cancel();
           TcpServerImpl.this.removeConnection(key);
         }
         it.remove();
       }
     }
   }
 
   private class SendThread extends Thread
   {
     private SendThread()
     {
     }
 
     public void run()
     {
       while (TcpServerImpl.this.bCanRun) {
         SelectionKey key = null;
         while ((key = TcpServerImpl.this.getSend()) != null) {
           SocketChannel sc = (SocketChannel)key.channel();
           ConnectInfo ci = (ConnectInfo)key.attachment();
           List l = ci.getSendData();
           while (!l.isEmpty()) {
             ByteBuffer bb = (ByteBuffer)l.remove(0);
             try {
               while (bb.hasRemaining()) {
                 sc.write(bb);
               }
               byte[] bytes = bb.array();
 
               if (TypeConvert.byte2int(bytes, 0) != 1) {
                 TcpServerImpl.this.serverHandler.onSendedMsg(ci.getConnectId(), 
                   ci.getAttachObj(), bytes);
               } else {
                 byte[] temp = new byte[bytes.length - 8];
 
                 System.arraycopy(bytes, 8, temp, 0, 
                   temp.length);
                 ByteArrayInputStream byteIn = new ByteArrayInputStream(
                   temp);
                 ObjectInputStream in = new ObjectInputStream(
                   byteIn);
 
                 Object o = in.readObject();
                 TcpServerImpl.this.serverHandler.onSendedMsg(ci.getConnectId(), 
                   ci.getAttachObj(), (Serializable)o);
               }
             }
             catch (IOException ex) {
               TcpServerImpl.log.error("向客户端写消息的时候发生错误");
               TcpServerImpl.log.error(ex.getMessage());
             }
             catch (ClassNotFoundException e) {
               e.printStackTrace();
               TcpServerImpl.log.error("发送的object有错误");
             }
 
           }
 
           if (key.isValid())
             key.interestOps(1);
         }
       }
     }
   }
 }
