package com.zbensoft.mmsmp.ownbiz.ra.base;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import java.io.Serializable;
import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	private String ip;
	private int port;
	static IoSession session = null;

	public static void main(String[] args) {
		MinaClient clent = new MinaClient("172.16.6.51", 8010);

		MT_SPMMHttpMessage spmt = new MT_SPMMHttpMessage();

		spmt.setGlobalMessageid(MT_SPMMHttpMessage.generateUUID("MMS-OWN"));
		spmt.setGlobalMessageTime(System.currentTimeMillis());
		spmt.setMessageid(MT_SPMMHttpMessage.generateUUID(""));
		spmt.setOperatorsType(3);
		spmt.setAuthorization("Basic enhtZTp6eG1l");

		OrderRelationUpdateNotifyRequest request = new OrderRelationUpdateNotifyRequest();
		request.setUserId("13011111111");
		request.setGlobalMessageid("12345678");
		request.setSpId("91527");
		request.setProductId("990932");
		request.setUpdateType(Integer.valueOf(1));
		MO_SMMessage ms = new MO_SMMessage();
		ms.setVaspId("91527");
		ms.setVasId("1065556500101");
		ms.setGlobalMessageid("12345678");
		ms.setSendAddress("13011111111");
		ms.setSmsText("123456");
		ms.setServiceCode("91527#Service_id#990932#Servicename#Fee#Uniqueid");
		clent.send(ms);
		session.close(true);
		System.exit(0);
	}

	public MinaClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		init();
	}

	public void send(Serializable msg) {
		session.write(msg);
	}

	public void init() {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000L);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		connector.setHandler(new MinaHandler());
		ConnectFuture cf = connector.connect(new InetSocketAddress(this.ip, this.port));
		cf.awaitUninterruptibly(1000L);
		session = cf.getSession();
	}
}
