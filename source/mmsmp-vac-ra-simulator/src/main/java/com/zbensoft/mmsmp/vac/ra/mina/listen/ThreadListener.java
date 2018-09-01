package com.zbensoft.mmsmp.vac.ra.mina.listen;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;

public class ThreadListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ThreadListener.class);

	private int serverPort = ConfigUtil.getInstance().getAgentConfig().getVacServerPort();

	private String host = ConfigUtil.getInstance().getAgentConfig().getVacClientIP();
	private int port = ConfigUtil.getInstance().getAgentConfig().getVacClientPort();

	private int ssthreadnum = ConfigUtil.getInstance().getAgentConfig().getVacRequestThreadNum();
	private int csthreadnum = ConfigUtil.getInstance().getAgentConfig().getVacResponseThreadNum();

	private CheckMessageQuence cMessageQuence = CheckMessageQuence.getInstance();
	NioSocketConnector connector;

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		startMinaServer();

		new ReConnector().start();

		logger.info("started request thread...");
		ExecutorService pool = Executors.newFixedThreadPool(this.ssthreadnum);
		for (int i = 0; i < this.ssthreadnum; i++) {
			ServerSenderThread ssThread = new ServerSenderThread(this.cMessageQuence, "thread" + i);
			pool.execute(ssThread);
		}

		logger.info("started response thread...");
		ExecutorService cpool = Executors.newFixedThreadPool(this.csthreadnum);
		ClientSenderThread csthread = new ClientSenderThread(this.cMessageQuence);
		for (int i = 0; i < this.csthreadnum; i++)
			cpool.execute(csthread);
	}

	/**
	 * 启动客户端连接corebiz server
	 * @return
	 */
	public int startMinaClient() {
		int connected = 1;
		logger.info("start mina client ......");

		this.connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = this.connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		this.connector.setConnectTimeoutMillis(1000L);
		this.connector.setHandler(new ClientHandler(this.cMessageQuence));
		ConnectFuture connFuture = this.connector.connect(new InetSocketAddress(this.host, this.port));
		connFuture.awaitUninterruptibly(1000L);

		if (connFuture.isDone()) {
			if (!connFuture.isConnected()) {
				logger.info("can't connect to corebiz server- " + this.host + ":" + this.port);
				this.connector.dispose();
				this.connector = null;
				connected = 0;
			} else {
				logger.info("connect to corebiz server: " + this.host + " success");
			}
		}

		return connected;
	}

	/**
	 * 启动服务端
	 */
	private void startMinaServer() {
		logger.info("begin to start mina server.....");
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		acceptor.setHandler(new ServerHandler(this.cMessageQuence));
		try {
			acceptor.bind(new InetSocketAddress(this.serverPort));
			logger.info("started mina server at port: " + this.serverPort + " success");
		} catch (IOException e) {
			logger.error("started mina server at port: " + this.serverPort + " failed ", e);
		}
	}

}
