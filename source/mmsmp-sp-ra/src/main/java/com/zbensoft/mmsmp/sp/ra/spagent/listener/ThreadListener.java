package com.zbensoft.mmsmp.sp.ra.spagent.listener;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.AgentConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.AcewayIoSessionDataStructureFactory;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ClientHandler;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ClientSenderThread;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ReConnector;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.SPMessageQuene;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ServerHandler;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ServerReportThread;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.ServerSenderThread;
import com.zbensoft.mmsmp.sp.ra.spagent.sp.mina.SendToSpHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ThreadListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ThreadListener.class);

	private int serverPort = ConfigUtil.getInstance().getAgentConfig().getSpServerPort();

	private String host = ConfigUtil.getInstance().getAgentConfig().getSpClientIP();
	private int port = ConfigUtil.getInstance().getAgentConfig().getSpClientPort();

	private int mothreadnum = ConfigUtil.getInstance().getAgentConfig().getSpMOThreadNum();
	int mtthreadnum = ConfigUtil.getInstance().getAgentConfig().getSpMTThreadNum();
	NioSocketConnector connector;

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		startMinaServer();

		new ReConnector().start();

		logger.info("started moMsg thread...");
		ExecutorService pool = Executors.newFixedThreadPool(this.mothreadnum);
		ServerSenderThread ssThread = new ServerSenderThread(SPMessageQuene.getInstance());
		for (int i = 0; i < this.mothreadnum; i++) {
			pool.execute(ssThread);
		}

		logger.info("started moReport thread...");
		ExecutorService reportpool = Executors.newFixedThreadPool(this.mothreadnum);
		ServerReportThread reportThread = new ServerReportThread(SPMessageQuene.getInstance());
		for (int i = 0; i < this.mothreadnum; i++) {
			reportpool.execute(reportThread);
		}

		logger.info("started mtmsg thread...");
		ExecutorService receivePool = Executors.newFixedThreadPool(this.mtthreadnum);
		ClientSenderThread csThread = new ClientSenderThread();
		for (int i = 0; i < this.mtthreadnum; i++)
			receivePool.execute(csThread);
		
		
//		testData();//模拟发送。和corebiz联调时，注释该行
	}
	
	public void testData(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			
		}
		
		
		MO_SMMessage mo_SMMessage=new MO_SMMessage();
		mo_SMMessage.setSendAddress("15895861272");//必须有
		mo_SMMessage.setVaspId("test123123123");
		mo_SMMessage.setVasId(null);
		mo_SMMessage.setServiceCode("11#22#33#44#55");
		mo_SMMessage.setOpCode(null);
		mo_SMMessage.setExtendOpCode(null);
		mo_SMMessage.setSmsText("100");//必须有
		mo_SMMessage.setServiceId(0);
		mo_SMMessage.setContentCode(null);
		mo_SMMessage.setNotirySPURL("127.0.0.1:39095");//必须有
		mo_SMMessage.setSequence_Number_1(Integer.valueOf(111));//必须有
		mo_SMMessage.setSequence_Number_2(Integer.valueOf(222));//必须有
		mo_SMMessage.setSequence_Number_3(Integer.valueOf(333));//必须有
//		mo_SMMessage.setMessageCoding((byte) 15);
//		mo_SMMessage.setTP_pid((byte) 5);
//		mo_SMMessage.setTP_udhi((byte) 6);
//		mo_SMMessage.setVasId("1065556500101");
//		mo_SMMessage.setVasId(null);
//		mo_SMMessage.setLinkId("123456");
//		mo_SMMessage.setLinkId(null);
		try {
			mo_SMMessage.setContentLength(mo_SMMessage.getSmsText().getBytes("GBK").length);
		} catch (UnsupportedEncodingException e1) {
			
		}
		try {
			SPMessageQuene.getInstance().getMoQuence().put(mo_SMMessage);
		} catch (IOException e) {
			
		}
		
//		starSPsimulatorClient("127.0.0.1",29095);
	}
	public int starSPsimulatorClient(String host,int port) {
		logger.info("start sp client ......");
		NioSocketConnector spConnector=null;
		if (spConnector != null) {
			logger.info("spConnector.isDisposed():" + spConnector.isDisposed());
			if (!spConnector.isDisposed()) {
				spConnector.dispose();
				spConnector = null;
			}
		}
		spConnector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = spConnector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		spConnector.setConnectTimeoutMillis(1000L);
		spConnector.setHandler(new SendToSpHandler(new com.zbensoft.mmsmp.sp.ra.spagent.utils.Deliver()));
		spConnector.setSessionDataStructureFactory(new AcewayIoSessionDataStructureFactory());

		ConnectFuture connFuture = spConnector.connect(new InetSocketAddress(host, port));
		connFuture.awaitUninterruptibly(1000L);
		IoSession session = null;
		try {
			session = connFuture.getSession();
		} catch (Exception e) {
			logger.error("连接sp 模拟器 服务：" + host + ":" + port + " 可能已经关闭！");
		}
		if (session == null) {
			this.connector.dispose();
			return 0;
		}
		logger.info("start sp success ...server address: " + host + ":" + port);
		return 1;
	}

	public int startMinaClient() {
		logger.info("start mina client ......");
		if (this.connector != null) {
			logger.info("connector.isDisposed():" + this.connector.isDisposed());
			if (!this.connector.isDisposed()) {
				this.connector.dispose();
				this.connector = null;
			}
		}
		this.connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = this.connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		this.connector.setConnectTimeoutMillis(1000L);
		this.connector.setHandler(new ClientHandler());
		this.connector.setSessionDataStructureFactory(new AcewayIoSessionDataStructureFactory());

		ConnectFuture connFuture = this.connector.connect(new InetSocketAddress(this.host, this.port));
		connFuture.awaitUninterruptibly(1000L);
		IoSession session = null;
		try {
			session = connFuture.getSession();
		} catch (Exception e) {
			logger.error("连接corebiz远程服务器失败，远程服务：" + this.host + ":" + this.port + " 可能已经关闭！");
		}
		if (session == null) {
			this.connector.dispose();
			return 0;
		}
		logger.info("start mina client success ...server address: " + this.host + ":" + this.port);
		return 1;
	}

	private void startMinaServer() {
		logger.info("begin to start mina server.....");
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		chain.addLast("ThreadPool", new ExecutorFilter(Executors.newFixedThreadPool(this.mothreadnum)));
		acceptor.setHandler(new ServerHandler(SPMessageQuene.getInstance()));
		try {
			acceptor.bind(new InetSocketAddress(this.serverPort));
			logger.info("started mina server at port: " + this.serverPort + " success");
		} catch (IOException e) {
			logger.error("started mina server at port: " + this.serverPort + " failed ", e);
		}
	}
}
