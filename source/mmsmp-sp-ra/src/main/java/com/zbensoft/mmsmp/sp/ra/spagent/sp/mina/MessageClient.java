package com.zbensoft.mmsmp.sp.ra.spagent.sp.mina;

import com.zbensoft.mmsmp.sp.ra.spagent.mina.AcewayIoSessionDataStructureFactory;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.Deliver;
import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MessageClient {
	private static final Logger logger = Logger.getLogger(MessageClient.class);

	private NioSocketConnector connector = null;
	private String ip;
	private int port;
	private static MessageClient messageClient = null;

	public MessageClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public MessageClient(int port) {
		this("127.0.0.1", port);
	}

	public static MessageClient getClientInstance(String host, int port) {
		messageClient = new MessageClient(host, port);
		return messageClient;
	}

	public int send(Object message) {
		if ((message instanceof Deliver)) {
			Deliver deliver = (Deliver) message;
			try {
				this.connector = new NioSocketConnector();
				this.connector.setConnectTimeoutMillis(3000L);

				this.connector.setHandler(new SendToSpHandler(deliver));
				IoSession session = null;

				int connectnum = 0;
				while (connectnum < 2) {
					connectnum++;
					try {
						ConnectFuture future = this.connector.connect(new InetSocketAddress(this.ip, this.port));
						future.awaitUninterruptibly();
						session = future.getSession();
						if (session == null){
							
						}else{
							break;
						}
					} catch (RuntimeIoException e) {
						e.printStackTrace();
					}
				}

				session.getCloseFuture().awaitUninterruptibly();

				return 0;
			} catch (Exception e) {
				logger.error("connect to " + this.ip + ":" + this.port + " error: ", e);
				return 1;
			} finally {
				this.connector.dispose();
			}
		}
		return 1;
	}
}
