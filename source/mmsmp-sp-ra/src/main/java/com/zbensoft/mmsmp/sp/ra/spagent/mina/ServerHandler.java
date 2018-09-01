package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class ServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	private SPMessageQuene messageQuene;

	public ServerHandler(SPMessageQuene instance) {
		this.messageQuene = instance;
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("exceptionCaught(IoSession, Throwable)", cause);
		session.close(true);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		try {
			if (message == null) {
				logger.error("spagent mina server receive message is Null");
			} else if ((message instanceof AbstractMessage)) {
				AbstractMessage momessage = (AbstractMessage) message;
				logger.info("the message from corebiz is : " + momessage);
				if ((momessage instanceof MO_SMMessage)) {
					this.messageQuene.getMoQuence().put(momessage);
					logger.info("this time put MO_SMMessage to quence，this MOquene size is ["
							+ this.messageQuene.getMoQuence().size() + "]");
				} else if ((momessage instanceof MO_ReportMessage)) {
					this.messageQuene.getReportQuence().put(momessage);
					logger.info("this time put MO_ReportMessage to quence，this Reportquene size is ["
							+ this.messageQuene.getReportQuence().size() + "]");
				} else if ((momessage instanceof OrderRelationUpdateNotifyRequest)) {
					this.messageQuene.getMoQuence().put(momessage);
					logger.info("this time put OrderRelationUpdateNotifyRequest to quence，this MOquene size is ["
							+ this.messageQuene.getMoQuence().size() + "]");
				} else if ((momessage instanceof MO_MMDeliverSPMessage)) {
					this.messageQuene.getMoQuence().put(momessage);
					logger.info("this time put MO_MMDeliverSPMessage to quence，this MOquene size is ["
							+ this.messageQuene.getMoQuence().size() + "]");
				}
			}
		} catch (Exception e) {
			logger.error("received corebiz message is error" + e);
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.write("spagent-> corebiz client echo idle message");
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 30);
		session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, 30);
	}
}
