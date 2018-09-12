package com.zbensoft.mmsmp.mms.ra.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class MMSServer {
	
	static int serverPort=29093;//sp模拟器的端口
	
	private static final Logger logger = Logger.getLogger(MMSServer.class.getName());
	
	public void run(){	
		
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
//		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

		acceptor.setHandler(new MMSServerHandler());
		try {
			acceptor.bind(new InetSocketAddress(this.serverPort));
			logger.info("started mms server at port: " + this.serverPort + " success");
		} catch (IOException e) {
			logger.error("started mms server at port: " + this.serverPort + " failed ", e);
		}
		
	}
	
	public static void main(String[] args) {
		new MMSServer().run();
	}
}
