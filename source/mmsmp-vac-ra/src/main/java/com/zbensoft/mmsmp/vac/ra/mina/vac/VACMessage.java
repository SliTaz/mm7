package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class VACMessage 
implements Serializable
{
private static final long serialVersionUID = -4567133292045468239L;
private static final Logger logger = Logger.getLogger(VACMessage.class);

public byte status = 0;
public int sended_count = 0;
public long last_access_time = System.currentTimeMillis();
private boolean flag = true;
private Header request;
private Header response;

public VACMessage(Header request)
{
  this.request = request;
}
public Header getRequest() {
  return this.request;
}
public void setRequest(Header request) {
  this.request = request;
}
public Header getResponse() {
  return this.response;
}
public void setResponse(Header response) {
  this.response = response;
  synchronized (this) {
    notify();
  }
}

public Header waitResponse() {
  if (this.response == null)
    synchronized (this) {
      try {
        new NotifyThread(this).start();
        logger.info(" this thread waite " + PropertiesHelper.getVacAaaMsgTimeout());
        wait(PropertiesHelper.getVacAaaMsgTimeout().longValue());
        logger.info("this thread is waite over....");
      }
      catch (InterruptedException localInterruptedException) {
      }
    }
  if (this.response == null) {
    this.flag = false;
    logger.error("timeout, id=" + this.request.getSequenceId());

    List requestList = VACSender.getRequestList();
    synchronized (requestList) {
      Iterator it = requestList.iterator();
      while (it.hasNext()) {
        VACMessage msg = (VACMessage)it.next();
        if (this.request.getSequenceId() == msg.getRequest().getSequenceId()) {
          it.remove();
          break;
        }
      }
    }
  }
  return this.response;
}

public static void main(String[] args)
{
  VACMessage msg = new VACMessage(new Header());
  msg.startThread(msg);
  Header h = msg.waitResponse();
  try {
    Thread.sleep(5000L);
  } catch (InterruptedException e) {
    e.printStackTrace();
  }
  logger.info("main method response is " + h);
}

private void startThread(VACMessage msg)
{
  new TestMessage(msg).start();
}

class NotifyThread extends Thread
{
  private VACMessage message;

  public NotifyThread(VACMessage message)
  {
    this.message = message;
  }

  public void run() {
    while (VACMessage.this.flag) {
      VACMessage.logger.info("the response is " + VACMessage.this.response);
      if (VACMessage.this.response != null) {
        synchronized (this.message) {
          this.message.notify();
        }
        VACMessage.logger.info("the waite response thread is notified");
        break;
      }
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class TestMessage extends Thread
{
  private VACMessage msg;

  public TestMessage(VACMessage msg)
  {
    this.msg = msg;
  }

  public void run() {
    VACMessage.logger.info(this.msg.response);
    try {
      Thread.sleep(10000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.msg.response = new Header();
    VACMessage.logger.info(this.msg.response);
  }
}

}
