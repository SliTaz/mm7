package com.zbensoft.mmsmp.sp.ra.spagent.test.sp;

import com.zbensoft.mmsmp.sp.ra.spagent.utils.Deliver;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class SPServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SPServerHandler.class);

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("exceptionCaught(IoSession, Throwable)", cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		IoBuffer buf = (IoBuffer) message;
		ByteBuffer bb = buf.buf();

		Deliver deliver = new Deliver();
		deliver.unserialize(bb.duplicate());
		logger.fatal(deliver);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.write("vacAgent server write idle,send handset.");
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}
}
