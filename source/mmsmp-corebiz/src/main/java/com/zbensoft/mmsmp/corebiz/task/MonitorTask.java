package com.zbensoft.mmsmp.corebiz.task;

import com.zbensoft.mmsmp.corebiz.cache.CacheCenter;
import com.zbensoft.mmsmp.corebiz.cache.DataMemory;
import com.zbensoft.mmsmp.corebiz.message.MmsDBListener;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;






public class MonitorTask
{
  static final Logger logger = Logger.getLogger(MonitorTask.class);
  
  LinkedBlockingQueue receiveHeightLevelQueue;
  
  LinkedBlockingQueue receiveHeightLevelExtendQueue;
  
  LinkedBlockingQueue receiveCommonLevelQueue;
  
  LinkedBlockingQueue receiveLowerLevelQueue;
  LinkedBlockingQueue receiveProxyPayQueue;
  LinkedBlockingQueue dispatchSmsAgentQueue;
  LinkedBlockingQueue dispatchMmsAgentQueue;
  LinkedBlockingQueue dispatchVacAgentQueue;
  LinkedBlockingQueue dispatchSpAgentQueue;
  LinkedBlockingQueue dispatchWoAgentQueue;
  LinkedBlockingQueue dispatchOwnServiceQueue;
  MmsDBListener mmsDBListener;
  CacheCenter cacheCenter;
  
  public void doCheckLogerServer()
  {
    COREBIZ_LOG.INFO("echo udp link layer message " + new SimpleDateFormat("yyyyMMddHHmmss ").format(new Date()));
  }
  


  public void doCheckSystemStatus()
  {
    StringBuilder sb = new StringBuilder("system status monitor report");
    sb.append("\r\n\r\n");
    sb.append("\r\n\r\n");
    sb.append("\t").append("ReceiveHeightLevelQueue size: ").append(this.receiveHeightLevelQueue.size()).append(" num\r\n");
    sb.append("\t").append("receiveHeightLevelExtendQueue size: ").append(this.receiveHeightLevelExtendQueue.size()).append(" num\r\n");
    sb.append("\t").append("ReceiveCommonLevelQueue size: ").append(this.receiveCommonLevelQueue.size()).append(" num\r\n");
    sb.append("\t").append("ReceiveLowerLevelQueue size: ").append(this.receiveLowerLevelQueue.size()).append(" num\r\n");
    sb.append("\t").append("ReceiveProxyPayQueue size: ").append(this.receiveProxyPayQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchSmsAgentQueue size: ").append(this.dispatchSmsAgentQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchMmsAgentQueue size: ").append(this.dispatchMmsAgentQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchVacAgentQueue size: ").append(this.dispatchVacAgentQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchSpAgentQueue size: ").append(this.dispatchSpAgentQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchWoAgentQueue size: ").append(this.dispatchWoAgentQueue.size()).append(" num\r\n");
    sb.append("\t").append("DispatchOwnServiceQueue size: ").append(this.dispatchOwnServiceQueue.size()).append(" num\r\n");
    sb.append("\t").append("MmsDatabaseQueue size: ").append(this.mmsDBListener.getQueueSize()).append(" num\r\n");
    sb.append("\t").append("SmsMemoryCache size: ").append(this.cacheCenter.getDataMemory().getSmsDataMap().size()).append(" num\r\n");
    sb.append("\t").append("MmsMemoryCache size: ").append(this.cacheCenter.getDataMemory().getMmsDataMap().size()).append(" num\r\n");
    sb.append("\t").append("UniMemoryCache size: ").append(this.cacheCenter.getDataMemory().getUniDataMap().size()).append(" num\r\n");
    sb.append("\t").append("CommonMemoryCache size: ").append(this.cacheCenter.getDataMemory().getCommonDataMap().size()).append(" num\r\n");
    sb.append("\t").append("MmsRportDataMap size: ").append(this.cacheCenter.getDataMemory().getMmsRportDataMap().size()).append(" num\r\n");
    




   sb.append("\t").append("JvmTotalMemory size: ").append(Runtime.getRuntime().totalMemory() / 1024L).append(" kb\r\n");
   sb.append("\t").append("JvmFreeMemory size: ").append((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L).append(" kb\r\n");
   sb.append("\t").append("JvmUsedMemory size: ").append(Runtime.getRuntime().freeMemory() / 1024L).append(" kb\r\n");
   sb.append("\t").append("JvmMaxMemory size: ").append(Runtime.getRuntime().maxMemory() / 1024L).append(" kb\r\n");
    




    for (ThreadGroup parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent()) {
	    int totalThread = parentThread.activeCount();
	    sb.append("\t").append("TotalThreads number: ").append(totalThread).append(" num\r\n");
	    sb.append("\r\n\r\n");
    }






    COREBIZ_LOG.INFO(sb.toString());
  }
  

  public void setReceiveHeightLevelQueue(LinkedBlockingQueue receiveHeightLevelQueue)
  {
    this.receiveHeightLevelQueue = receiveHeightLevelQueue;
  }
  
  public LinkedBlockingQueue getReceiveHeightLevelExtendQueue()
  {
    return this.receiveHeightLevelExtendQueue;
  }
  
  public void setReceiveHeightLevelExtendQueue(LinkedBlockingQueue receiveHeightLevelExtendQueue)
  {
    this.receiveHeightLevelExtendQueue = receiveHeightLevelExtendQueue;
  }
  
  public void setReceiveCommonLevelQueue(LinkedBlockingQueue receiveCommonLevelQueue)
  {
    this.receiveCommonLevelQueue = receiveCommonLevelQueue;
  }
  
  public void setReceiveLowerLevelQueue(LinkedBlockingQueue receiveLowerLevelQueue) {
    this.receiveLowerLevelQueue = receiveLowerLevelQueue;
  }
  
  public LinkedBlockingQueue getReceiveProxyPayQueue()
  {
    return this.receiveProxyPayQueue;
  }
  
  public void setReceiveProxyPayQueue(LinkedBlockingQueue receiveProxyPayQueue)
  {
    this.receiveProxyPayQueue = receiveProxyPayQueue;
  }
  
  public void setDispatchSmsAgentQueue(LinkedBlockingQueue dispatchSmsAgentQueue) {
    this.dispatchSmsAgentQueue = dispatchSmsAgentQueue;
  }
  
  public void setDispatchMmsAgentQueue(LinkedBlockingQueue dispatchMmsAgentQueue) {
    this.dispatchMmsAgentQueue = dispatchMmsAgentQueue;
  }
  
  public void setDispatchVacAgentQueue(LinkedBlockingQueue dispatchVacAgentQueue) {
    this.dispatchVacAgentQueue = dispatchVacAgentQueue;
  }
  
  public void setDispatchSpAgentQueue(LinkedBlockingQueue dispatchSpAgentQueue) {
    this.dispatchSpAgentQueue = dispatchSpAgentQueue;
  }
  
  public void setDispatchWoAgentQueue(LinkedBlockingQueue dispatchWoAgentQueue) {
    this.dispatchWoAgentQueue = dispatchWoAgentQueue;
  }
  
  public LinkedBlockingQueue getDispatchOwnServiceQueue()
  {
    return this.dispatchOwnServiceQueue;
  }
  

  public void setDispatchOwnServiceQueue(LinkedBlockingQueue dispatchOwnServiceQueue)
  {
    this.dispatchOwnServiceQueue = dispatchOwnServiceQueue;
  }
  
  public void setCacheCenter(CacheCenter cacheCenter) {
    this.cacheCenter = cacheCenter;
  }
  
  public LinkedBlockingQueue getReceiveHeightLevelQueue() {
    return this.receiveHeightLevelQueue;
  }
  
  public LinkedBlockingQueue getReceiveCommonLevelQueue()
  {
    return this.receiveCommonLevelQueue;
  }
  
  public LinkedBlockingQueue getReceiveLowerLevelQueue() {
    return this.receiveLowerLevelQueue;
  }
  
  public LinkedBlockingQueue getDispatchSmsAgentQueue() {
    return this.dispatchSmsAgentQueue;
  }
  
  public LinkedBlockingQueue getDispatchMmsAgentQueue() {
    return this.dispatchMmsAgentQueue;
  }
  
  public LinkedBlockingQueue getDispatchVacAgentQueue() {
    return this.dispatchVacAgentQueue;
  }
  
  public LinkedBlockingQueue getDispatchSpAgentQueue() {
    return this.dispatchSpAgentQueue;
  }
  
  public LinkedBlockingQueue getDispatchWoAgentQueue() {
    return this.dispatchWoAgentQueue;
  }
  
  public Runtime getRuntime()
  {
    return Runtime.getRuntime();
  }
  
  public CacheCenter getCacheCenter() {
    return this.cacheCenter;
  }
  
  public MmsDBListener getMmsDBListener()
  {
    return this.mmsDBListener;
  }
  
  public void setMmsDBListener(MmsDBListener mmsDBListener) {
    this.mmsDBListener = mmsDBListener;
  }
}

