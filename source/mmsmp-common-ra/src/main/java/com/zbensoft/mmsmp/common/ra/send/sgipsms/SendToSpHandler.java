package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class SendToSpHandler extends IoHandlerAdapter {
	private Deliver deliver;

	public SendToSpHandler(Deliver deliver) {
		this.deliver = deliver;
	}

	public void sessionOpened(IoSession session) {
		System.out.println("session opened......");

		IoBuffer iobuffer = IoBuffer.wrap(login());
		session.write(iobuffer);
	}

	public void messageReceived(IoSession session, Object message) {
		System.out.println("Received Response in Handler:\t");
		try {
			IoBuffer buffer = IoBuffer.wrap(this.deliver.serialize());
			session.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void do_write(IoSession session, Deliver deliver) {
		try {
			IoBuffer buffer = IoBuffer.wrap(deliver.serialize());
			session.write(buffer);
		} catch (Exception localException) {
		}
	}

	public byte[] login() {
		Bind bind = new Bind();
		return bind.serialize().array();
	}
}
