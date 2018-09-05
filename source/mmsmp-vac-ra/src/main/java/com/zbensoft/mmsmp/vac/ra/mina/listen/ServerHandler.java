package com.zbensoft.mmsmp.vac.ra.mina.listen;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.vac.ra.log.PROCESS_LOG;

public class ServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	private CheckMessageQuence cMessageQuence;

	public ServerHandler(CheckMessageQuence instance) {
		this.cMessageQuence = instance;
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("exceptionCaught(IoSession, Throwable)", cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("message from corbize");
		if ((message instanceof CheckRequest)) {
			CheckRequest cRequest = (CheckRequest) message;

			PROCESS_LOG.INFO("server corebiz " + "recv:" + cRequest.toString());
			logger.info("the CheckRequest message from corebiz is [gmsgid:" + cRequest.getGlobalMessageid() + ",serviceType:" + cRequest.getServiceType() + ",phone:" + cRequest.getUser_number() + ",sequence:"
					+ cRequest.getSrc_SequenceNumber() + "]");
			this.cMessageQuence.getRequestQuence().put(cRequest);
			logger.info("this time put CheckRequest message to quence");
		} else {
			logger.info("the message is not CheckRequest message" + message);
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.write("vacAgent server write idle,send handset.");
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}

}
