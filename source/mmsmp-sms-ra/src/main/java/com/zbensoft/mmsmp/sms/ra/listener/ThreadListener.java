package com.zbensoft.mmsmp.sms.ra.listener;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpServerImpl;
import com.zbensoft.mmsmp.sms.ra.log.SMS_LOG;
import com.zbensoft.mmsmp.sms.ra.mina.ClientHandler;
import com.zbensoft.mmsmp.sms.ra.mina.ServerHandler;
import com.zbensoft.mmsmp.sms.ra.smsg.SmsSender;
import com.zbensoft.mmsmp.sms.ra.thread.ReceiveMessageThread;
import com.zbensoft.mmsmp.sms.ra.thread.SendMessageThread;
import com.zbensoft.mmsmp.sms.ra.utils.ReceiveMTSmsHandler;
import com.zbensoft.mmsmp.sms.ra.utils.SmsMessageQuene;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class ThreadListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ThreadListener.class);
    private int sendThreadNum = ConfigUtil.getInstance().getAgentConfig().getSmsSendThreadNum();
    private String sreverIP = ConfigUtil.getInstance().getAgentConfig().getSmsServerIP();
    private int serverPort = ConfigUtil.getInstance().getAgentConfig().getSmsServerPort();
    private SmsSender smsSender;
    private String host = ConfigUtil.getInstance().getAgentConfig().getSmsClientIP();
    private int port = ConfigUtil.getInstance().getAgentConfig().getSmsClientPort();
    private IoSession session = null;
    private int receiveThreadNum = ConfigUtil.getInstance().getAgentConfig().getSmsReceiveThreadNum();
    NioSocketConnector connector;

    public ThreadListener() {
    }

    public SmsSender getSmsSender() {
        return this.smsSender;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.startMinaServer();
        (new ConnectManager()).start();
        this.startSGIPClient();
        SMS_LOG.INFO("started mtmsg thread...");
        ExecutorService sendPool = Executors.newFixedThreadPool(this.sendThreadNum);

        for(int i = 0; i < this.sendThreadNum; ++i) {
            SendMessageThread sendThread = new SendMessageThread(this.smsSender);
            sendPool.execute(sendThread);
        }

        SMS_LOG.INFO("started momsg thread...");
        ExecutorService receivePool = Executors.newFixedThreadPool(this.receiveThreadNum);
        ReceiveMessageThread receiveThread = new ReceiveMessageThread();

        for(int i = 0; i < this.receiveThreadNum; ++i) {
            receivePool.execute(receiveThread);
        }

    }

    private void startSGIPClient() {
        SMS_LOG.INFO("begin send mtmsg to smsg.......");

        try {
            this.smsSender = new SmsSender();
            SMS_LOG.INFO("create  SmsSender success....");
        } catch (Exception var2) {
            SMS_LOG.INFO("create  SmsSender failed....");
        }

    }

    private void startMinaServer() {
        SMS_LOG.INFO("begin to start mina server.....");
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.setHandler(new ServerHandler(SmsMessageQuene.getInstance()));

        try {
            acceptor.bind(new InetSocketAddress(this.serverPort));
            SMS_LOG.INFO("started mina server at port: " + this.serverPort + " success");
        } catch (IOException var4) {
            SMS_LOG.ERROR("started mina server at port: " + this.serverPort + " failed ", var4);
        }

    }

    public int startClient() {
        SMS_LOG.INFO("start mina smsclient ......");
        if (this.connector != null) {
            logger.info("connector.isDisposed():" + this.connector.isDisposed());
            if (!this.connector.isDisposed()) {
                this.connector.dispose();
                this.connector = null;
            }
        }

        this.connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = this.connector.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        this.connector.setConnectTimeoutMillis(1000L);
        this.connector.setHandler(new ClientHandler());

        try {
            ConnectFuture connFuture = this.connector.connect(new InetSocketAddress(this.host, this.port));
            connFuture.awaitUninterruptibly(1000L);
            this.session = connFuture.getSession();
        } catch (Exception var3) {
            SMS_LOG.ERROR("连接远程服务器失败，远程服务：" + this.host + ":" + this.port + " 可能已经关闭！", var3);
        }

        if (this.session == null) {
            this.connector.dispose();
            return 0;
        } else {
            SMS_LOG.INFO("start mina smsclient success ...server address: " + this.host + ":" + this.port);
            return 1;
        }
    }

    public static void sendMsg(AbstractMessage message) {
        String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
        int port = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.setConnectTimeoutMillis(1000L);
        connector.setHandler(new ClientHandler());
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(host, port));
        connFuture.awaitUninterruptibly(1000L);
        IoSession session = connFuture.getSession();
        SMS_LOG.INFO("Send Message to ip= " + host + " port" + port + "  message == " + message);
        if (session != null) {
            if (message != null) {
                session.write(message);
            }
        } else {
            SMS_LOG.INFO("The Client Mina session Not open!");
        }

    }

    private void startTCPServer() {
        SMS_LOG.INFO("start receive from corebiz  message server...");
        TcpServerImpl mtTcpServer = new TcpServerImpl();
        ReceiveMTSmsHandler mtHandler = new ReceiveMTSmsHandler(SmsMessageQuene.getInstance());
        mtTcpServer.setDataHandler(mtHandler, 51200);
        int port = ConfigUtil.getInstance().getAgentConfig().getSmsServerPort();
        mtTcpServer.beginListen(port);
        SMS_LOG.INFO("started  server at:" + port);
    }

    public int getSendThreadNum() {
        return this.sendThreadNum;
    }

    public void setSendThreadNum(int sendThreadNum) {
        this.sendThreadNum = sendThreadNum;
    }
}
