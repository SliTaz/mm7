package com.zbensoft.mmsmp.mms.ra.test;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServerTestCase {
	static final Logger logger = Logger.getLogger(MinaServerTestCase.class);
	NioSocketAcceptor corebizMockServer;
	int serverPort;

	public void startup() {
		try {
			this.corebizMockServer = new NioSocketAcceptor();
			DefaultIoFilterChainBuilder chain = this.corebizMockServer.getFilterChain();
			chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			this.corebizMockServer.setHandler(new ServerHandler());
			this.corebizMockServer.bind(new InetSocketAddress(this.serverPort));
			logger.info("MmsAgent corebiz mock server startup with " + this.serverPort);
		} catch (Exception ex) {
			logger.error("MmsAgent corebiz mock server startup failed", ex);
			ex.printStackTrace();
		}
	}

	public void stop() {
		try {
			this.corebizMockServer.unbind();
		} catch (Exception localException) {
		}
		try {
			this.corebizMockServer.dispose();
		} catch (Exception localException1) {
		}
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public static void main(String[] args) {
		MinaServerTestCase test = new MinaServerTestCase();
		test.setServerPort(8008);
		test.startup();
	}

	static class ServerHandler extends IoHandlerAdapter {
		public void print(Object message) {
			StringBuilder sb = new StringBuilder();
			try {
				if ((message instanceof MO_MMDeliverSPMessage)) {
					MO_MMDeliverSPMessage msg = (MO_MMDeliverSPMessage) message;
					MM7DeliverReq req = msg.getMM7DeliverReq();

					sb.append("Corebiz Mock receive MO_MMDeliverSPMessage message from mmsAgent");
					sb.append("\r\n");
					sb.append("[");
					sb.append(" To:").append(req.getTo());
					sb.append(" sender: ").append(req.getSender());
					sb.append(" LinkedID: ").append(req.getLinkedID());
					sb.append(" TransactionID: ").append(req.getTransactionID());
					sb.append(" Content-Type: ").append(req.getContentType());
					sb.append(" Priority: ").append(req.getPriority());
					sb.append(" Subject: ").append(req.getSubject());

					sb.append("]");
				} else if ((message instanceof MO_ReportMessage)) {
					MO_ReportMessage msg = (MO_ReportMessage) message;

					sb.append("Corebiz Mock receive MO_ReportMessage message from mmsAgent");
					sb.append("\r\n");
					sb.append("[");
					sb.append(" Correlator: ").append(msg.getCorrelator());
					sb.append(" Status: ").append(msg.getStatus());
					sb.append("]");
				} else if ((message instanceof MT_ReportMessage)) {
					MT_ReportMessage msg = (MT_ReportMessage) message;
					sb.append("Corebiz Mock receive MT_ReportMessage message from mmsAgent");
					sb.append("\r\n");
					sb.append("[");
					sb.append(" Correlator: ").append(msg.getCorrelator());
					sb.append(" Status: ").append(msg.getStatus());
					sb.append("]");
				} else {
					sb.append("Corebiz Mock receive receive unkown type Message");
					sb.append("\r\n");
					sb.append("[");
					sb.append(message.toString());
					sb.append("]");
				}

				MinaServerTestCase.logger.info(sb.toString());
			} catch (Exception ex) {
				MinaServerTestCase.logger.error(sb.toString(), ex);
			}
		}

		public void messageReceived(IoSession session, Object message) throws Exception {
			print(message);
		}

		public void sessionOpened(IoSession session) throws Exception {
			session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 30);
			session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, 30);
		}

		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			session.close(true);
		}
	}
}
