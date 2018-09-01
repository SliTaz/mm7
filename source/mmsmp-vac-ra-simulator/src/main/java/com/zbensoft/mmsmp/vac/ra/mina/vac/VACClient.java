package com.zbensoft.mmsmp.vac.ra.mina.vac;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;

public class VACClient {

	  private static final Logger logger = Logger.getLogger(VACClient.class.getName());
	  private NioSocketConnector connector = new NioSocketConnector();
	  private String ip;
	  private int port;

	  public VACClient()
	  {
	    this.ip = PropertiesHelper.getVacAaaIp();
	    this.port = PropertiesHelper.getVacAaaPort().intValue();
	  }

	  public int run(String threadame)
	  {
	    int connected = 0;
	    try {
	      if (this.connector == null) {
	        this.connector = new NioSocketConnector();
	      }
	      this.connector.setConnectTimeoutMillis(30000L);

	      this.connector.getSessionConfig().setMinReadBufferSize(5120);
	      this.connector.getSessionConfig().setMaxReadBufferSize(10240);
	      this.connector.getSessionConfig().setReceiveBufferSize(20480);
	      this.connector.setHandler(new VACClientHandler(threadame));

	      ConnectFuture connFuture = this.connector.connect(new InetSocketAddress(this.ip, this.port));
	      connFuture.awaitUninterruptibly(1000L);

	      if (connFuture.isDone())
	      {
	        if (!connFuture.isConnected())
	        {
	          logger.info("can't connect to vac server-" + this.ip + ":" + this.port);
	          this.connector.dispose();
	          this.connector = null;
	        }
	        else
	        {
	          logger.info("connect to vac server: " + this.ip + " success");
	          connected = 1;
	        }
	      }
	    } catch (Exception e) {
	      logger.error("can't connect to vac server.....", e);
	      this.connector.dispose();
	      this.connector = null;
	    } finally {
	      return connected;
	    }
	  }

	  public static void main(String[] args) throws Exception
	  {
	    new VACClient().run("thread0");
	  }
}
