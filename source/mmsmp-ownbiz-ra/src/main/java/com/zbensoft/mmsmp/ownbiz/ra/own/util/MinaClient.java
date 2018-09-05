package com.zbensoft.mmsmp.ownbiz.ra.own.util;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class MinaClient {
    public IoSession session = null;
    IoConnector connector = null;
    ConnectFuture future = null;
    public Object message;

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public MinaClient(String ip, int port) {
        this.connector = new NioSocketConnector();
        this.connector.setConnectTimeoutMillis(60000L);
        this.connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        this.connector.setHandler(this.getHandler());
        this.future = this.connector.connect(new InetSocketAddress(ip, port));
        this.future.awaitUninterruptibly();
        this.session = this.future.getSession();
    }

    public Object send(Serializable message) {
        this.session.write(message);
        this.session.getCloseFuture().awaitUninterruptibly();
        this.connector.dispose();
        return this.getMessage();
    }

    public IoHandlerAdapter getHandler() {
        return new IoHandlerAdapter() {
            public void sessionOpened(IoSession session) throws Exception {
                System.out.println("incomming client : " + session.getRemoteAddress());
            }

            public void messageReceived(IoSession session, Object message) {
                MinaClient.this.setMessage(message);
                session.close(true);
            }
        };
    }

    public static void main(String[] args) {
        Object obj = (new MinaClient("127.0.0.1", 9988)).send("");
        System.out.println(obj);
    }
}
