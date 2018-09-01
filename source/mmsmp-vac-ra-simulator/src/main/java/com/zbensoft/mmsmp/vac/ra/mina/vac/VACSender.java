package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.mina.listen.CheckMessageQuence;
import com.zbensoft.mmsmp.vac.ra.mina.util.MessageProcessor;

public class VACSender {

	  private static final Logger logger = Logger.getLogger(VACSender.class);

	  private static List<VACMessage> requestList = new LinkedList();

	  public static Map<String, IoSession> sessionMap = new HashMap();
	  public static Map<String, Boolean> flagMap = new HashMap();
	  private String threadname;
	  private VACConnectManager connectManager = null;

	  private VACConnectManager getConnectManagerInstance() {
	    if (this.connectManager == null) {
	      this.connectManager = new VACConnectManager(this.threadname);
	    }
	    return this.connectManager;
	  }

	  public VACSender(String threadname2) {
	    this.threadname = threadname2;
	  }

	  public static List<VACMessage> getRequestList() {
	    return requestList;
	  }

	  public synchronized void send(Header o) {
	    try {
	      IoSession session = (IoSession)sessionMap.get(this.threadname);
	      boolean flag = ((Boolean)flagMap.get(this.threadname)).booleanValue();

	      logger.info("thread :" + this.threadname + "  session : " + session + "  flag: " + flag);

	      while (!session.isConnected())
	      {
	        logger.info("error : vac close");
	        getConnectManagerInstance().run();
	        session = (IoSession)sessionMap.get(this.threadname);
	      }
	      if ((flag) && (session.isConnected())) {
	        IoBuffer buffer = IoBuffer.wrap(o.serialize());
	        session.write(buffer);
	        logger.info("send message to vac ,messageid is " + o.getSequenceId());
	      }
	    } catch (Exception e) {
	      logger.error("send Header error", e);
	    }
	    logger.info(o.getClass().getName() + " => " + o.getSequenceId());
	  }

	  /**
	   * 接收包处理
	   * @param resp
	   */
	  public static void putResponse(Header resp)
	  {
	    MessageProcessor mp = new MessageProcessor(CheckMessageQuence.getInstance());
	    mp.dealCheckResponse(resp);
	  }
}
