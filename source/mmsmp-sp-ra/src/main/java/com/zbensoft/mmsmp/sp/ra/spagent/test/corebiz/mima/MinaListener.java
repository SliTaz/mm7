package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.AgentConfig;
import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.sp.ra.spagent.test.sp.SPServerHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(MinaListener.class);

	private String serverHost = ConfigUtil.getInstance().getAgentConfig().getSpServerIP();
	private int serverPort = ConfigUtil.getInstance().getAgentConfig().getSpServerPort();

	private String host = ConfigUtil.getInstance().getAgentConfig().getSpClientIP();
	private int port = ConfigUtil.getInstance().getAgentConfig().getSpClientPort();

	private int spport = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		startMinaServer();
		startMinaClient();

		startSPMinaServer();
	}

	private int startMinaClient() {
		NioSocketConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		connector.setConnectTimeoutMillis(1000L);
		connector.setHandler(new ClientHandler());
		ConnectFuture connFuture = connector.connect(new InetSocketAddress(this.serverHost, this.serverPort));
		connFuture.awaitUninterruptibly(1000L);
		IoSession session = null;
		try {
			session = connFuture.getSession();
		} catch (Exception e) {
			logger.error("连接spagent远程服务器失败");
		}
		if (session == null) {
			return 0;
		}
		logger.info("连接spagent远程服务器成功");

		return 1;
	}

	private void startMinaServer() {
		logger.info("begin to start mina server.....");
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		acceptor.setHandler(new ServerHandler());
		try {
			acceptor.bind(new InetSocketAddress(this.port));
			logger.info("started mina server at port: " + this.port + " success");
		} catch (IOException e) {
			logger.error("started mina server at port: " + this.port + " failed ", e);
		}
	}

	private void startSPMinaServer() {
		logger.info("begin to start sp mina server.....");
		NioSocketAcceptor acceptor = new NioSocketAcceptor();

		acceptor.setHandler(new SPServerHandler());
		try {
			acceptor.bind(new InetSocketAddress(this.spport));
			logger.info("started sp mina server at port: " + this.spport + " success");
		} catch (IOException e) {
			logger.error("started sp mina server at port: " + this.spport + " failed ", e);
		}
	}
}
