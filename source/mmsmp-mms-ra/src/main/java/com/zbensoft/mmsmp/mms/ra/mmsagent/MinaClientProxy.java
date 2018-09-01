package com.zbensoft.mmsmp.mms.ra.mmsagent;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import java.io.Serializable;
import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClientProxy {
	static final Logger logger = Logger.getLogger(MinaClientProxy.class);
	private String host;
	private int port;
	NioSocketConnector connector = null;
	ConnectFuture future = null;
	IoSession session = null;

	public void deliver(Serializable message) throws Exception {
		try {
			if ((this.session == null) || (this.future == null) || (this.connector == null)
					|| (!this.session.isConnected()) || (!this.future.isConnected()) || (this.connector.isDisposed())) {
				connection();
			}
			this.session.write(message);
			logger.info(toMessage(message, "success"));
		} catch (Exception ex) {
			logger.info(toMessage(message, "failed"));
			logger.error(toMessage(message, "failed"), ex);
			reConnection();
		}
	}

	public synchronized void connection() throws Exception {
		try {
			this.connector = new NioSocketConnector();
			this.connector.setConnectTimeoutMillis(30000L);
			this.connector.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
			this.connector.setHandler(new MinaClientSessionHandler());

			this.future = this.connector.connect(new InetSocketAddress(this.host, this.port));
			this.future.awaitUninterruptibly(1000L);

			if (this.future.isDone()) {
				if (!this.future.isConnected()) {
					logger.info("mina fail to connect " + this.host);
					this.connector.dispose();
				}
			}
			this.session = this.future.getSession();
			Thread.sleep(100L);
		} catch (Exception ex) {
			throw ex;
		} finally {
			int int_1=this.session != null ? 1 : 0;
			int int_2=this.future != null ? 1 : 0;
			int int_3=this.connector != null ? 1 : 0;
			int int_4=this.connector.isDisposed() ? 0 : 1;
			if ((int_1==1) & (int_2==1) & (int_3==1) &this.session.isConnected()& this.future.isConnected() & (int_4 != 0) && this.connector.isActive()){
				logger.info("mina client connection " + this.host + ":" + this.port + " success");
			} else {
				logger.info("mina client connection " + this.host + ":" + this.port + " failure");
			}
		}
	}

	public synchronized void reConnection() {
		try {
			dispose();
			Thread.sleep(100L);
			connection();
		} catch (Exception localException) {
		}
	}

	public synchronized void dispose() {
		try {
			this.session.close();
			this.session = null;
		} catch (Exception localException) {
		}
		try {
			this.future.cancel();
			this.future = null;
		} catch (Exception localException1) {
		}
		try {
			this.connector.dispose();
		} catch (Exception localException2) {
		}
	}

	public String toMessage(Serializable message, String info) {
		StringBuilder sb = new StringBuilder();
		try {
			if ((message instanceof MO_MMDeliverSPMessage)) {
				MO_MMDeliverSPMessage msg = (MO_MMDeliverSPMessage) message;
				MM7DeliverReq req = msg.getMM7DeliverReq();

				sb.append("mmsagent-> corebiz one mmsmo message " + info);
				sb.append("\r\n");
				sb.append("[");
				sb.append(" gmsgid:").append(msg.getGlobalMessageid());
				sb.append(" To:").append(req.getTo());
				sb.append(" sender: ").append(req.getSender());
				sb.append(" LinkedID: ").append(req.getLinkedID());
				sb.append(" TransactionID: ").append(req.getTransactionID());
				sb.append(" Content-Type: ").append(req.getContentType());
				sb.append(" MM7Version: ").append(req.getMM7Version());
				sb.append(" MMSRelayServerID: ").append(req.getMMSRelayServerID());
				sb.append(" Priority: ").append(req.getPriority());
				sb.append(" Subject: ").append(req.getSubject());
				sb.append("]");
			} else if ((message instanceof MO_ReportMessage)) {
				MO_ReportMessage msg = (MO_ReportMessage) message;

				sb.append("mmsagent-> corebiz one mmsmr message " + info);
				sb.append("\r\n");
				sb.append("[");
				sb.append(" gmsgid:").append(msg.getGlobalMessageid());
				sb.append(" Correlator: ").append(msg.getCorrelator());
				sb.append(" Status: ").append(msg.getStatus());
				sb.append("]");
			} else if ((message instanceof MT_ReportMessage)) {
				MT_ReportMessage msg = (MT_ReportMessage) message;

				sb.append("mmsagent-> coreabiz one mmsmh message " + info);
				sb.append("\r\n");
				sb.append("[");
				sb.append(" gmsgid:").append(msg.getGlobalMessageid());
				sb.append(" Correlator: ").append(msg.getCorrelator());
				sb.append(" Status: ").append(msg.getStatus());
				sb.append("]");
			} else if ((message instanceof MT_SPMMHttpMessage)) {
				MT_SPMMHttpMessage msg = (MT_SPMMHttpMessage) message;

				sb.append("mmsagent mock-> coreabiz one mmsmt message " + info);
				sb.append("\r\n");
				sb.append("[");
				sb.append(" gmsgid:").append(msg.getGlobalMessageid());
				sb.append("]");
			} else {
				sb.append("Unkown Type Message");
				sb.append("\r\n");
				sb.append("[");
				sb.append(message.toString());
				sb.append("]");
			}
		} catch (Exception ex) {
			logger.error(sb.toString(), ex);
		}

		return sb.toString();
	}

	public void submit(Serializable message) throws Exception {
		deliver(message);
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	static class ClientHandler extends IoHandlerAdapter {
		public void sessionOpened(IoSession session) throws Exception {
			session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		}

		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			if (status == IdleStatus.READER_IDLE) {
				session.close(true);
			}
		}

		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			session.close();
		}
	}

	class MinaClientSessionHandler extends IoHandlerAdapter {
		MinaClientSessionHandler() {
		}

		public void sessionCreated(IoSession session) throws Exception {
			SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
			cfg.setKeepAlive(true);
			cfg.setSoLinger(0);
		}

		public void sessionOpened(IoSession session) throws Exception {
		}

		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			session.write("mmsagent client send headset");

			if (session.getIdleCount(IdleStatus.READER_IDLE) >= 29) {
				session.close(true);
			}
		}

		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			session.close();
		}
	}
}
