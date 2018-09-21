

package com.zbensoft.mmsmp.ownbiz.ra.own.listener;

import com.zbensoft.mmsmp.ownbiz.ra.own.dao.CooperKeyDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.ProxyPayMessageDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.dao.VasServiceRelationDao;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.client.ConnectManager;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.client.handler.MinaClientHandler;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.MinaServerHandler;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.MoSmMessageHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.OrderRelationUpdateNotifyRequestHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler.impl.ProxyPayMessageHandlerImpl;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread.ReceiveResultMessageThread;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread.ReportThread;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread.SendRequestMessageThread;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.AppContants;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MinaServerListener implements ApplicationListener {
    private static final Logger _log = Logger.getLogger(MinaServerListener.class);
    private NioSocketConnector _connector;
    private IoSession _session = null;
    private ProxyPayMessageHandlerImpl proxyPayMessagehandler;
    private MoSmMessageHandlerImpl moSmMessageHandler;
    private OrderRelationUpdateNotifyRequestHandlerImpl orunRequestHandler;
    private CooperKeyDao cooperKeyDao;
    private ProxyPayMessageDao proxyPayMessageDao;
    private VasServiceRelationDao vasServiceRelationDao;
    private MessageQuene messageQuene = null;
    private static volatile boolean started;

    public MinaServerListener() {
    }

    public void onApplicationEvent(ApplicationEvent event) {

        if(!(event instanceof ContextRefreshedEvent) || started){
            return;
        }

        if (this.messageQuene == null) {
            this.messageQuene = MessageQuene.getInstance();
        }

        this.messageQuene.addDaoMap("proxyPayMessageDao", this.getProxyPayMessageDao());
        this.messageQuene.addDaoMap("cooperKeyDao", this.getCooperKeyDao());
        this.messageQuene.addDaoMap("vasServiceRelationDao", this.getVasServiceRelationDao());
        this.startMinaServer();
        ConnectManager cm = new ConnectManager();
        cm.start();
        int iSendThreadNum = AppContants.SEND_REQUEST_THREAD_NUM;
        _log.debug(String.format("SendRequestMessageThread 线程启动，线程数：%d", iSendThreadNum));
        ExecutorService sendPool = Executors.newFixedThreadPool(iSendThreadNum);

        int iReceiveThreadNum;
        for(iReceiveThreadNum = 0; iReceiveThreadNum < iSendThreadNum; ++iReceiveThreadNum) {
            SendRequestMessageThread sendThread = new SendRequestMessageThread(this.messageQuene);
            sendPool.execute(sendThread);
        }

        iReceiveThreadNum = AppContants.RECEIVE_RESULT_THREAD_NUM;
        _log.debug(String.format("ReceiveResultMessageThread 线程启动，线程数：%d", iReceiveThreadNum));
        ExecutorService receivePool = Executors.newFixedThreadPool(iReceiveThreadNum);

        int sendReportNum;
        for(sendReportNum = 0; sendReportNum < iReceiveThreadNum; ++sendReportNum) {
            ReceiveResultMessageThread receiveThread = new ReceiveResultMessageThread(this.getProxyPayMessagehandler(), this.getMoSmMessageHandler(), this.getOrunRequestHandler(), this.messageQuene);
            receivePool.execute(receiveThread);
        }

        sendReportNum = PropertiesUtil.getInt("common", "sendReportNum");
        _log.debug(String.format("ReportThread 线程启动，线程数：%d", sendReportNum));
        ExecutorService reportPool = Executors.newFixedThreadPool(sendReportNum);

        for(int i = 0; i < sendReportNum; ++i) {
            ReportThread reportThread = new ReportThread();
            reportPool.execute(reportThread);
        }

    }

    private void startMinaServer() {
        _log.info("MinaServer 启动开始 .....");

        try {
            int iServerPort = AppContants.SERVER_PORT;
            _log.debug("NioSocketAcceptor 初始化 .....");
            NioSocketAcceptor acceptor = new NioSocketAcceptor();
            _log.debug(String.format("NioSocketAcceptor 中加入过滤器（%s）.", "codec"));
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
            _log.debug(String.format("ServerHandler 处理器初始化，处理队列数：%d.", this.messageQuene.getQuenceSize() * 2));
            MinaServerHandler sh = new MinaServerHandler(this.messageQuene);
            _log.debug(String.format("NioSocketAcceptor 中加入处理器（%s）.", MinaServerHandler.class.getName()));
            acceptor.setHandler(sh);
            _log.debug(String.format("NioSocketAcceptor 绑定监听端口：%s.", iServerPort));
            acceptor.bind(new InetSocketAddress(iServerPort));
            _log.info(String.format("MinaServer 启动成功，端口号: %s.", iServerPort));
        } catch (Exception var4) {
            _log.error(String.format("MinaServer 启动失败，错误消息：%s.", var4.getMessage()), var4);
        } catch (Throwable var5) {
            _log.error(String.format("MinaServer 启动失败，错误消息：%s.", var5.getMessage()), var5);
        }

        started =  true;

    }

    public int startClient() {
        _log.debug("MinaClient 创建服务器端链接开始 .....");
        if (this._connector != null && !this._connector.isDisposed()) {
            _log.debug("判断销毁原有连接:" + this._connector.isDisposed());
            this._connector.dispose();
            this._connector = null;
        }

        _log.debug("NioSocketConnector 初始化 .....");
        this._connector = new NioSocketConnector();
        _log.debug(String.format("NioSocketConnector 中加入过滤器（%s）.", "codec"));
        this._connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        _log.debug(String.format("NioSocketConnector 设置连接超时时间 %d 毫秒.", 1000L));
        this._connector.setConnectTimeoutMillis(1000L);
        _log.debug(String.format("ClientHandler 处理器初始化 ....."));
        this._connector.setHandler(new MinaClientHandler());
        String strHost = AppContants.CLIENT_IP;
        int iPort = AppContants.CLIENT_PORT;

        try {
            _log.info(String.format("InetSocketAddress 创建一个套接字地址：%s:%s.", strHost, iPort));
            InetSocketAddress isa = new InetSocketAddress(strHost, iPort);
            _log.debug(String.format("NioSocketConnector 尝试链接 ....."));
            ConnectFuture connFuture = this._connector.connect(isa);
            connFuture.awaitUninterruptibly(1000L);
            this._session = connFuture.getSession();
            if (this._session == null) {
                _log.debug("MinaClient 创建服务器端链接失败.");
                this._connector.dispose();
                SendRequestMessageThread.setSmsSession((IoSession)null);
                return 0;
            } else {
                SendRequestMessageThread.setSmsSession(this._session);
                _log.info(String.format("MinaClient 链接成功，套接字地址：%s:%s.", strHost, iPort));
                return 1;
            }
        } catch (RuntimeIoException var5) {
            _log.error(String.format("NioSocketConnector 连接远程服务器失败，远程服务：%s:%s 可能已经关闭！", strHost, iPort));
            return 0;
        } catch (Exception var6) {
            _log.error(String.format("NioSocketConnector 连接远程服务器失败，远程服务：%s:%s 可能已经关闭！", strHost, iPort), var6);
            return 0;
        }
    }

    public CooperKeyDao getCooperKeyDao() {
        return this.cooperKeyDao;
    }

    public void setCooperKeyDao(CooperKeyDao cooperKeyDao) {
        this.cooperKeyDao = cooperKeyDao;
    }

    public ProxyPayMessageDao getProxyPayMessageDao() {
        return this.proxyPayMessageDao;
    }

    public void setProxyPayMessageDao(ProxyPayMessageDao proxyPayMessageDao) {
        this.proxyPayMessageDao = proxyPayMessageDao;
    }

    public ProxyPayMessageHandlerImpl getProxyPayMessagehandler() {
        return this.proxyPayMessagehandler;
    }

    public void setProxyPayMessagehandler(ProxyPayMessageHandlerImpl proxyPayMessagehandler) {
        this.proxyPayMessagehandler = proxyPayMessagehandler;
    }

    public MoSmMessageHandlerImpl getMoSmMessageHandler() {
        return this.moSmMessageHandler;
    }

    public void setMoSmMessageHandler(MoSmMessageHandlerImpl moSmMessageHandler) {
        this.moSmMessageHandler = moSmMessageHandler;
    }

    public OrderRelationUpdateNotifyRequestHandlerImpl getOrunRequestHandler() {
        return this.orunRequestHandler;
    }

    public void setOrunRequestHandler(OrderRelationUpdateNotifyRequestHandlerImpl orunRequestHandler) {
        this.orunRequestHandler = orunRequestHandler;
    }

    public VasServiceRelationDao getVasServiceRelationDao() {
        return this.vasServiceRelationDao;
    }

    public void setVasServiceRelationDao(VasServiceRelationDao vasServiceRelationDao) {
        this.vasServiceRelationDao = vasServiceRelationDao;
    }
}
