package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class VACServer {

	  private static final Logger logger = Logger.getLogger(VACServer.class.getName());
	  private NioSocketConnector connector = new NioSocketConnector();
//	  private String ip;
	  private int port;

	  public VACServer()
	  {
//	    this.ip = PropertiesHelper.getVacAaaIp();
	    this.port = PropertiesHelper.getVacAaaPort().intValue();
	  }

	  public void run()
	  {

			logger.info("begin to start vac server.....");
			NioSocketAcceptor acceptor = new NioSocketAcceptor();
//			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
//			chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

			acceptor.setHandler(new VACServerHandler());
			try {
				acceptor.bind(new InetSocketAddress(this.port));
				logger.info("started vac server at port: " + this.port + " success");
			} catch (IOException e) {
				logger.error("started vac server at port: " + this.port + " failed ", e);
			}
		
	  }

	  public static void main(String[] args) throws Exception
	  {
	    new VACServer().run();
	  }
}
