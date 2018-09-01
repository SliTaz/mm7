package com.zbensoft.mmsmp.sp.ra.spagent.sp.mina;

import com.zbensoft.mmsmp.sp.ra.spagent.utils.Deliver;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class SendToSpHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SendToSpHandler.class);
	private Deliver deliver;

	public SendToSpHandler(Deliver deliver) {
		this.deliver = deliver;
	}

	public void sessionOpened(IoSession session) throws Exception {
		logger.info("into SendToSpHandler sessionOpened method");
		logger.info("session："+session);
		logger.info("this.deliver："+this.deliver);
		IoBuffer iobuffer = IoBuffer.wrap(this.deliver.serialize());
		logger.info("iobuffer："+iobuffer);
		session.write(iobuffer);
		logger.info("session.write is success");
		session.close(true);
		
//		logger.info("into SendToSpHandler sessionOpened method");
//		IoBuffer iobuffer = IoBuffer.wrap(this.deliver.serialize());
//		session.write(iobuffer);
//		logger.info("this time send deliver message to sp over.");
//		session.close(true);
		
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("into SendToSpHandler messageReceived method");
		logger.info(message);
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		session.close(true);
	}
}
