package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima;

import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyResponse;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class ServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("exceptionCaught(IoSession, Throwable)", cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		if ((message instanceof MT_SPMMHttpMessage)) {
			MT_SPMMHttpMessage mtmms = (MT_SPMMHttpMessage) message;
			logger.fatal("the mt mms from spagent is : \r\n operatorstype:" + mtmms.getOperatorsType()
					+ "\r\n messageid:" + mtmms.getMessageid() + "\r\nmmscontent:" + mtmms.getContentbyte().length);
		} else if ((message instanceof OrderRelationUpdateNotifyResponse)) {
			OrderRelationUpdateNotifyResponse res = (OrderRelationUpdateNotifyResponse) message;
			logger.fatal("the orderrelation response from spagent is : " + res.getResultCode()
					+ "    and the request is :" + res.getOrderRequest().getUserId());
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.write("vacAgent server write idle,send handset.");
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}
}
