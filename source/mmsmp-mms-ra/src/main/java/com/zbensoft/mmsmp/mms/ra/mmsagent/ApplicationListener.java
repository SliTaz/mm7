package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.zbensoft.mmsmp.mms.ra.test.MinaServerTestCase;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(ApplicationListener.class);
	
	ApplicationContext applicationContext;
	static NioSocketAcceptor minaServer;
	static MessageListener moQueueListener;
	static MessageListener mtQueueListener;
	static MessageListener hrQueueListener;
	MinaServerTestCase corebizMock;
	
	static LinkedBlockingQueue moQueue;
	static LinkedBlockingQueue mtQueue;
	static LinkedBlockingQueue hrQueue;
	
	static String runLevel = "0";
	static int echoInterval= 60;
	static boolean runEcho=true;
	
	static MessageRouter messageRouter;
	static MinaClientProxy minaClientProxy;
	static Mm7ClientProxy mm7ClientProxy;
	static MessageDispather messageDispather;
	
	
	public static final int serverPort=8006;
	
	static{
		System.out.println("ApplicationListener static");
		
		moQueue=new java.util.concurrent.LinkedBlockingQueue(10000);
		mtQueue=new java.util.concurrent.LinkedBlockingQueue(10000);
		hrQueue=new java.util.concurrent.LinkedBlockingQueue(5000);
		
		messageRouter=new com.zbensoft.mmsmp.mms.ra.mmsagent.MessageRouter();
		
		ConcurrentHashMap<String, BlockingQueue> messageRoutePolocy=new java.util.concurrent.ConcurrentHashMap<String, BlockingQueue>();
		messageRoutePolocy.put("com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage", moQueue);
		messageRoutePolocy.put("com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage", mtQueue);
		messageRoutePolocy.put("com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage", hrQueue);
		messageRoutePolocy.put("com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage", hrQueue);
		
		messageRouter.setPolicyMap(messageRoutePolocy);
		
		minaClientProxy=new com.zbensoft.mmsmp.mms.ra.mmsagent.MinaClientProxy();
		minaClientProxy.setHost("10.199.201.71");
		minaClientProxy.setPort(8000);
		
		mm7ClientProxy=new com.zbensoft.mmsmp.mms.ra.mmsagent.Mm7ClientProxy();
		mm7ClientProxy.setMessageRouter(messageRouter);
		mm7ClientProxy.setMmsmcUrl("http://10.199.84.188:8899/vas");
		
		messageDispather=new com.zbensoft.mmsmp.mms.ra.mmsagent.MessageDispather();
		messageDispather.setMinaClientProxy(minaClientProxy);
		messageDispather.setMm7ClientProxy(mm7ClientProxy);
		
		moQueueListener=new com.zbensoft.mmsmp.mms.ra.mmsagent.MessageListener();
		moQueueListener.setDispather(messageDispather);
		moQueueListener.setQueue(moQueue);
		moQueueListener.setNumber(3);
		
		mtQueueListener=new com.zbensoft.mmsmp.mms.ra.mmsagent.MessageListener();
		mtQueueListener.setDispather(messageDispather);
		mtQueueListener.setQueue(mtQueue);
		mtQueueListener.setNumber(10);
		
		hrQueueListener=new com.zbensoft.mmsmp.mms.ra.mmsagent.MessageListener();
		hrQueueListener.setDispather(messageDispather);
		hrQueueListener.setQueue(hrQueue);
		hrQueueListener.setNumber(2);
		
		
		//start mina server
		MinaServerHandler minaServerHandler=new com.zbensoft.mmsmp.mms.ra.mmsagent.MinaServerHandler();
		minaServerHandler.setMessageRouter(messageRouter);
		
//		org.apache.mina.filter.executor.ExecutorFilter executorFilter=new org.apache.mina.filter.executor.ExecutorFilter();
//		org.apache.mina.filter.codec.ProtocolCodecFilter minaCodecFilter=new org.apache.mina.filter.codec.ProtocolCodecFilter(new org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory());
//		Map<String, IoFilter> filters=new HashMap<String,IoFilter>();
//		filters.put("codecFilter", minaCodecFilter);
//		filters.put("executor", executorFilter);
//		
//		org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder minaChainBuilder=new org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder();
//		minaChainBuilder.setFilters(filters);
		
		minaServer=new org.apache.mina.transport.socket.nio.NioSocketAcceptor();
		
		//去除通过set的方式，改为sp接口中的方式。通过addlast的方式
//		minaServer.setFilterChainBuilder(minaChainBuilder);
		DefaultIoFilterChainBuilder chain = minaServer.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		chain.addLast("ThreadPool", new ExecutorFilter());
		
		minaServer.setHandler(minaServerHandler);
		//int serverPort=serverPort;//
		try {
			minaServer.bind(new InetSocketAddress(serverPort));
			logger.info("started mina server at port: " + serverPort + " success");
		} catch (IOException e) {
			logger.error("started mina server at port: " + serverPort + " failed ", e);
		}
		
		
	}

	public void contextDestroyed(ServletContextEvent content) {
		try {
			this.minaServer.unbind();
		} catch (Exception localException) {
		}
		try {
			this.minaServer.dispose();
		} catch (Exception localException1) {
		}
		try {
			this.moQueueListener.stop();
		} catch (Exception localException2) {
		}
		try {
			this.mtQueueListener.stop();
		} catch (Exception localException3) {
		}
		try {
			this.hrQueueListener.stop();
		} catch (Exception localException4) {
		}
		try {
			Mm7ClientProxy.helper.shutdown();
		} catch (Exception localException5) {
		}
		try {
			runEcho = false;
		} catch (Exception localException6) {
		}
	}

	public void contextInitialized(ServletContextEvent content) {
//		this.applicationContext = WebApplicationContextUtils.getWebApplicationContext(content.getServletContext());
//		
//		runLevel = content.getServletContext().getInitParameter("runLevel");
//		runEcho = true;
//		try {
//			echoInterval = Integer.parseInt(content.getServletContext().getInitParameter("logInterval"));
//		} catch (Exception ex) {
//			echoInterval = 60;
//		}
//
//		this.minaServer = ((NioSocketAcceptor) this.applicationContext.getBean("minaSocketAcceptor"));
//		System.out.println("MmsAgent mt Server startup with port " + this.minaServer.getLocalAddress().getPort());
//
//		this.moQueueListener = ((MessageListener) this.applicationContext.getBean("moQueueListener"));
//		this.moQueueListener.start();
//		System.out.println("MmsAgent mo Queue Listener startup with " + this.moQueueListener.getNumber() + " Threads");
//
//		this.mtQueueListener = ((MessageListener) this.applicationContext.getBean("mtQueueListener"));
//		this.mtQueueListener.start();
//		System.out.println("MmsAgent mt Queue Listener startup with " + this.mtQueueListener.getNumber() + " Threads");
//
//		this.hrQueueListener = ((MessageListener) this.applicationContext.getBean("hrQueueListener"));
//		this.hrQueueListener.start();
//		System.out.println("MmsAgent hr Queue Listener startup with " + this.hrQueueListener.getNumber() + " Threads");
//
//		this.moQueue = ((LinkedBlockingQueue) this.applicationContext.getBean("mo_queue"));
//		this.mtQueue = ((LinkedBlockingQueue) this.applicationContext.getBean("mt_queue"));
//		this.hrQueue = ((LinkedBlockingQueue) this.applicationContext.getBean("hr_queue"));
		
		moQueueListener.start();
		mtQueueListener.start();
		hrQueueListener.start();
		
		monitorInitialized(content);
		debugInitialized(content);
	}
	
	public static MessageRouter getMessageRouter(){
		return messageRouter;
	}
	
	public static MinaClientProxy getMinaClientProxy(){
		return minaClientProxy;
	}
	
	public static Mm7ClientProxy getMm7ClientProxy(){
		return mm7ClientProxy;
	}
	
	public static MessageDispather getMessageDispather(){
		return messageDispather;
	}

	private void monitorInitialized(ServletContextEvent content) {
		if (echoInterval > 0) {
			new MonitorThread().start();
			System.out.println("MmsAgent monitor thread startup with interval " + echoInterval + " second");
		}
	}

	private void debugInitialized(ServletContextEvent content) {
		if ((runLevel.equals("2")) || (runLevel.equals("3"))) {
			for (int i = 0; i < 5; i++) {
				new MockThread().start();
			}
		}
	}

	public static String getRunLevel() {
		return runLevel;
	}

	class MockThread extends Thread {
		MockThread() {
		}

		public void run() {
			String mms = "--==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==Content-ID: <SimpleTeam88025833>Content-Type: text/xml; charset=\"utf8\"Content-Transfer-Encoding: 8bit<?xml version=\"1.0\" encoding=\"utf-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">60567875009767375926</mm7:TransactionID></env:Header><env:Body><DeliverReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\"><MM7Version>6.3.0</MM7Version><SenderIdentification><VASPID>888888</VASPID><VASID>33333</VASID><SenderAddress>10658888</SenderAddress></SenderIdentification><Recipients><To><Number>3333</Number></To></Recipients><ServiceCode>33333</ServiceCode><LinkedID>343243243</LinkedID><TimeStamp>2011-01-20T11:57:10+08:00</TimeStamp><Priority>Normal</Priority><Subject>3333</Subject><Sender>3333</Sender></DeliverReq></env:Body></env:Envelope>--==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==Content-Type: multipart/mixed;\tboundary=\"==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==\"--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==Content-ID: <SimpleTeam.txt>Content-Type: text/plain; charset=\"utf-8\"Content-Transfer-Encoding: base64MzMzMw==--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==Content-ID: <SimpleTeam.gif>Content-Type: image/gifContent-Transfer-Encoding: base64R0lGODlheAA3ANUAAABmmZkAAABNdABSe73P2EGGqQAAAGScuL/AwQBDZCVoif///0B5lQA9W3+is0wySwBmmABhkgBklo+PjwBYhL/X4wBejdHi6nMaJgBVgAAlOH+uxgBJbQBikwBcikByjABGaShcdRBunY+4zUBtgwBllwBahwBgj+/19yguMqHB0QANE8/f5hBmkAAzTTCDqwAGCQA/XzB9pAAaJhBWeQAtQwATHRBJZVB9lGB/jzpAX4YNE48GCn+TnTo7WRBghyH5BAAAAAAALAAAAAB4ADcAAAb/QIBwh0YIy30W6UjuENKQqiHkJFUTScYwpiMaoIAnR3k+Uo6yk6ZMpSedoEpTekaVJDwlE2oAAw2sCxW4";

			while (mms.getBytes().length < 51200) {
				mms = mms + "h0YIy30W6UjuENKQqiHkJFUTScYwpiMaoIAnR3k+Uo6yk6ZMpSedoEpTekaVJDwlE2oAAw2sCxW4";
			}
			mms = mms
					+ "vCUu+xAEADs=--==SimpleTeam=7c0c6a737d106855b0e7ef0bf7ba482f==----==SimpleTeam=df4f8c96af4d3e7f9247fd6e973507f3==--";

			for (int i = 0; i < 80000; i++)
				try {
					MT_MMHttpSPMessage mt = new MT_MMHttpSPMessage();
					mt.setGlobalMessageid(MT_SPMMHttpMessage.generateUUID("MMSMT"));
					mt.setGlobalCreateTime(mt.getGlobalCreateTime());
					mt.setGlobalMessageTime(System.currentTimeMillis());
					mt.setContentType("text/xml;charset=\"UTF-8\"");
					mt.setContent_Transfer_Encoding("utf-8");
					mt.setAuthorization("auth");
					mt.setSOAPAction("soap");
					mt.setMM7APIVersion("2.0");
					mt.setMime_Version("7.0");
					mt.setContentByte(mms.getBytes());
					mt.setMessageid("10000000" + i);
					ApplicationListener.this.mtQueue.put(mt);
				} catch (Exception localException) {
				}
		}
	}

	class MonitorThread extends Thread {
		final Logger logger = Logger.getLogger(ApplicationListener.class);

		MonitorThread() {
		}

		public void run() {
			while (ApplicationListener.runEcho) {
				try {
					Thread.sleep(ApplicationListener.echoInterval * 1000);

					StringBuilder sb = new StringBuilder("system status monitor report");
					sb.append("\r\n\r\n");
					sb.append("\t").append("moQueue size: ").append(ApplicationListener.this.moQueue.size())
							.append(" num\r\n");
					sb.append("\t").append("mtQueue size: ").append(ApplicationListener.this.mtQueue.size())
							.append(" num\r\n");
					sb.append("\t").append("hrQueue size: ").append(ApplicationListener.this.hrQueue.size())
							.append(" num\r\n");
					sb.append("\t").append("jvmTotalMemory size: ").append(Runtime.getRuntime().totalMemory() / 1024L)
							.append(" kb\r\n");
					sb.append("\t").append("jvmFreeMemory size: ")
							.append((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L)
							.append(" kb\r\n");
					sb.append("\t").append("jvmUsedMemory size: ").append(Runtime.getRuntime().freeMemory() / 1024L)
							.append(" kb\r\n");
					sb.append("\t").append("jvmMaxMemory size: ").append(Runtime.getRuntime().maxMemory() / 1024L)
							.append(" kb\r\n");
					sb.append("\r\n\r\n");

					this.logger.info(sb.toString());
				} catch (Exception localException) {
				}
			}
		}
	}
}
