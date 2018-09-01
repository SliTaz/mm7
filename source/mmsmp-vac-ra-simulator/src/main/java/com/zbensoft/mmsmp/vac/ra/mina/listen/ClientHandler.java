package com.zbensoft.mmsmp.vac.ra.mina.listen;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHandler  extends IoHandlerAdapter
{
	  private static final Logger logger = Logger.getLogger(ClientHandler.class);
	  private CheckMessageQuence cMessageQuence;

	  public ClientHandler(CheckMessageQuence messageQuence)
	  {
	    this.cMessageQuence = messageQuence;
	  }

	  public void exceptionCaught(IoSession session, Throwable cause)
	    throws Exception
	  {
	    logger.info("client exceptionCaught", cause);
	  }

	  public void sessionClosed(IoSession session) throws Exception
	  {
	    logger.info("client session closeed and reconnct");
	    new ReConnector().start();
	  }

	  public void sessionIdle(IoSession session, IdleStatus status)
	    throws Exception
	  {
	    session.write("vacagent client send headset");
	    if (session.getIdleCount(IdleStatus.READER_IDLE) >= 29)
	      session.close(true);
	  }

	  public void sessionOpened(IoSession session)
	    throws Exception
	  {
	    session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

	    ClientSenderThread.session = session;
	  }

}
