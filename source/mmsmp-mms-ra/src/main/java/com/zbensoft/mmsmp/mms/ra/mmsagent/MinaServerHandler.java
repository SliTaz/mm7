package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.SubmitReq;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class MinaServerHandler extends IoHandlerAdapter {
	static final Logger logger = Logger.getLogger(MinaServerHandler.class);
	MessageRouter messageRouter;

	public void messageReceived(IoSession session, Object message) throws Exception {
		StringBuilder sb = new StringBuilder();

		if (message == null) {
			logger.error("mmsagent mina server receive message is Null");
		} else if ((message instanceof MT_MMHttpSPMessage)) {
			MT_MMHttpSPMessage msg = (MT_MMHttpSPMessage) message;
			SubmitReq submitReq = msg.getSubmitReq();

			sb.append("mmsagent<- corebiz one mmsmt message");
			sb.append("\r\n");
			sb.append("[");
			sb.append(" gmsgid:").append(msg.getGlobalMessageid());
			sb.append(" messageid:").append(msg.getMessageid());
			sb.append(" VASID:").append(submitReq.getVASID());
			sb.append(" VASPID:").append(submitReq.getVASPID());
			sb.append(" To:").append(msg.getPhone());
			sb.append(" SenderAddress:").append(submitReq.getSenderAddress());
			sb.append(" ServiceCode: ").append(submitReq.getServiceCode());
			sb.append(" LinkedID: ").append(submitReq.getLinkedID());
			sb.append(" Subject: ").append(submitReq.getSubject());
			sb.append(" TransactionID: ").append(submitReq.getTransactionID());
			sb.append("]");

			logger.info(sb.toString());

			this.messageRouter.doRoute(msg);
		} else if ((message instanceof String)) {
			logger.error("mmsagent mina server receive string message:" + message.toString());
		} else {
			logger.error("mmsagent receive message type error:" + message.getClass().getName());
		}
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 30);
		session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, 30);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.write("mmsagent-> corebiz client echo idle message");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("Unexpected exception.", cause);
		session.close(true);
	}

	public void setMessageRouter(MessageRouter messageRouter) {
		this.messageRouter = messageRouter;
	}
}
