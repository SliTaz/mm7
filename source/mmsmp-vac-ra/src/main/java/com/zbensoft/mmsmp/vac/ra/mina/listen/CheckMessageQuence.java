package com.zbensoft.mmsmp.vac.ra.mina.listen;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class CheckMessageQuence {

	  private static final Logger logger = Logger.getLogger(CheckMessageQuence.class);
	  private LinkedBlockingQueue<CheckRequest> requestQuence;
	  private LinkedBlockingQueue<CheckResponse> responseQuence;
	  private Map<Integer, CheckRequest> requestMap;
	  private static int quencesize = ConfigUtil.getInstance().getAgentConfig().getVacQuenceSize();
	  private static CheckMessageQuence cMessageQuence = null;

	  public static String resp = null;

	  public CheckMessageQuence(int size) {
	    this.requestQuence = new LinkedBlockingQueue(size);
	    this.responseQuence = new LinkedBlockingQueue(size);
	    this.requestMap = new HashMap();
	  }

	  public static CheckMessageQuence getInstance() {
	    if (cMessageQuence == null) {
	      cMessageQuence = new CheckMessageQuence(quencesize);
	      cMessageQuence.startClean();
	    }
	    return cMessageQuence;
	  }

	  public LinkedBlockingQueue<CheckResponse> getResponseQuence()
	  {
	    return this.responseQuence;
	  }

	  public LinkedBlockingQueue<CheckRequest> getRequestQuence() {
	    return this.requestQuence;
	  }

	  public Map<Integer, CheckRequest> getRequestMap() {
	    return this.requestMap;
	  }

	  private void startClean()
	  {
	    new CleanThread().start();
	  }

	  class CleanThread extends Thread
	  {
	    private long stayTime = PropertiesHelper.getVacAaaMsgTimeout().longValue();

	    CleanThread() {
	    }
	    public void run() { while (true) { int cleanNum = 0;
	        if (CheckMessageQuence.this.requestMap.size() > 0) {
	          long currentTime = new Date().getTime();
	          synchronized (CheckMessageQuence.this.requestMap) {
	            Iterator it = CheckMessageQuence.this.requestMap.entrySet().iterator();
	            while (it.hasNext()) {
	              Map.Entry entry = (Map.Entry)it.next();
	              CheckRequest req = (CheckRequest)entry.getValue();
	              if (currentTime - req.getPutTime() >= this.stayTime) {
	                cleanNum++;
	                it.remove();
	              }
	            }
	          }
	        }
	        CheckMessageQuence.logger.info("this time clean requestMap over, the clean number is " + cleanNum);
	        try {
	          Thread.sleep(20000L);
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    }
	  }
}
