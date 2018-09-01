package com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz;

import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverSPMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.test.corebiz.mima.ClientHandler;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TestMoMms {
	private static final Logger logger = Logger.getLogger(TestMoMms.class);

	public static void main(String[] args) {
		startMinaClient();
	}

	public static int startMinaClient() {
		NioSocketConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		connector.setConnectTimeoutMillis(1000L);
		connector.setHandler(new ClientHandler());
		ConnectFuture connFuture = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
		connFuture.awaitUninterruptibly(1000L);
		IoSession session = null;
		try {
			session = connFuture.getSession();
		} catch (Exception e) {
			logger.error("连接spagent远程服务器失败");
		}
		if (session == null) {
			return 0;
		}
		logger.info("连接spagent远程服务器成功");

		MO_MMDeliverSPMessage me = getMessage();
		session.write(me);

		return 1;
	}

	private static MO_MMDeliverSPMessage getMessage() {
		ByteArrayOutputStream bos = null;
		try {
			InputStream in = new FileInputStream("e:/mms.txt");
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int count = 0;
			try {
				while ((count = in.read(b)) > 0)
					bos.write(b, 0, count);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		MO_MMDeliverSPMessage me = new MO_MMDeliverSPMessage();
		me.setContentByte(bos.toByteArray());
		me.setContentType("multipart/related");
		me.setLinkedID("111111");
		me.setSendurl("http://127.0.0.1:8000/spagent/spservlet");
		return me;
	}

	public static void send() {
		HttpURLConnection httpURL = null;
		InputStream in = null;
		DataOutputStream dos = null;
		try {
			URL theURL = new URL("http://127.0.0.1:8000/spagent/moservlet?aaa=111");
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setReadTimeout(5000);
			httpURL.setRequestMethod("POST");
			httpURL.setRequestProperty("Content-Type", "multipart/related");
			httpURL.setRequestProperty("aceway-to", "mmsc.aceway.com.cn");
			httpURL.setRequestProperty("Connection", "keep-alive");
			httpURL.setRequestProperty("Content-Length", "10000");

			in = new FileInputStream("e:/mms.txt");
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] inb = new byte[1024];
			int count = 0;
			while ((count = in.read(inb)) > 0) {
				dos.write(inb, 0, count);
			}
			dos.flush();

			logger.info("this time send over" + httpURL.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
