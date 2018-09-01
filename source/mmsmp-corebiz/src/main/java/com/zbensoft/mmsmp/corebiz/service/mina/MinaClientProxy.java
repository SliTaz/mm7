package com.zbensoft.mmsmp.corebiz.service.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClientProxy
{
  final Logger logger = Logger.getLogger(getClass().getName());
  
  String info;
  
  String host;
  int port;
  NioSocketConnector connector = null;
  ConnectFuture future = null;
  IoSession session = null;
  
  public synchronized void connection()throws Exception{
		try {
			this.connector = new NioSocketConnector();
			this.connector.setConnectTimeoutMillis(30000L);
			this.connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			this.connector.setHandler(new MinaClientSessionHandler());

			this.future = this.connector.connect(new InetSocketAddress(this.host, this.port));
			this.future.awaitUninterruptibly(1000L);

			if (this.future.isDone()) {
				if (!this.future.isConnected()) {
					logger.info("mina fail to connect " + this.host);
					this.connector.dispose();
				}
			}
			this.session = this.future.getSession();
			Thread.sleep(100L);
		} catch (Exception ex) {
			throw ex;
		} finally {
			int int_1=this.session != null ? 1 : 0;
			int int_2=this.future != null ? 1 : 0;
			int int_3=this.connector != null ? 1 : 0;
			int int_4=this.connector.isDisposed() ? 0 : 1;
			if ((int_1==1) & (int_2==1) & (int_3==1) &this.session.isConnected()& this.future.isConnected() & (int_4 != 0) && this.connector.isActive()){
				logger.info("mina client connection " + this.host + ":" + this.port + " success");
			} else {
				logger.info("mina client connection " + this.host + ":" + this.port + " failure");
			}
		}
	}
  
  public synchronized void reConnection()
    throws Exception
  {
    try
    {
      this.logger.info("mina client reconnection " + this.host + ":" + this.port + " try again");
      
      Thread.sleep(200L);
      dispose();
      connection();
    } catch (Exception ex) {
      dispose();
      throw ex;
    }
  }
  
	public synchronized void dispose() {
		try {
			if (this.session != null) {
				this.session.getCloseFuture().awaitUninterruptibly();
			}
		} catch (Exception localException) {
		}
		try {
			if (this.session != null) {
				this.session.close(true);
			}
		} catch (Exception localException1) {
		}
		try {
			if (this.future != null) {
				this.future.cancel();
			}
		} catch (Exception localException2) {
		}
		try {
			if (this.connector != null) {
				this.connector.dispose();
			}
		} catch (Exception localException3) {
		}

		this.session = null;
		this.future = null;
		this.connector = null;
	}
  
  public synchronized void send(AbstractMessage message) throws Exception
  {
    try
    {
      if (message == null)
      {
        this.logger.info(this.info + " message is null");
        return;
      }
      
      if ((this.session == null) || (this.future == null) || (this.connector == null) || (!this.session.isConnected()) || (!this.future.isConnected()) || 
        (this.connector.isDisposed()))
      {
        reConnection();
      }
      
      this.session.write(message);
      this.logger.info(this.info + " one message success[gmsgid:" + message.getGlobalMessageid() + 
        ",classType:" + message.getClass().getSimpleName() + "]");
    }
    catch (Exception ex) {
      this.logger.error(this.info + " one message failure[gmsgid:" + message.getGlobalMessageid() + 
        ",classType:" + message.getClass().getSimpleName() + "]", ex);
      throw ex;
    }
  }
  
  class MinaClientSessionHandler extends IoHandlerAdapter
  {
    MinaClientSessionHandler() {}
    
    public void sessionCreated(IoSession session) throws Exception {
      SocketSessionConfig cfg = (SocketSessionConfig)session.getConfig();
      cfg.setKeepAlive(true);
      cfg.setSoLinger(0);
    }
    

    public void sessionOpened(IoSession session)
      throws Exception
    {}
    

    public void sessionIdle(IoSession session, IdleStatus status)
      throws Exception
    {
      session.write("corebiz client send headset");
      
      if (session.getIdleCount(IdleStatus.READER_IDLE) >= 29)
      {
        session.close(true);
      }
    }
    
    public void exceptionCaught(IoSession session, Throwable cause)
      throws Exception
    {
      session.close(true);
    }
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public int getPort()
  {
    return this.port;
  }
}


