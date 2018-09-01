package com.zbensoft.mmsmp.sp.ra.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;

public class SPServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SPServerHandler.class);

	public SPServerHandler() {
		
	}

	public void messageReceived(IoSession session, Object message) {
		System.out.println("into SPServerHandler messageReceived;message:"+message);
	}

}
