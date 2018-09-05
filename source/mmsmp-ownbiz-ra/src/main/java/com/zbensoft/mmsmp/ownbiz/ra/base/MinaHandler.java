package com.zbensoft.mmsmp.ownbiz.ra.base;

import java.io.PrintStream;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

class MinaHandler extends IoHandlerAdapter {
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("-----------------------------------");
	}
}
