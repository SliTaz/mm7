package com.zbensoft.mmsmp.sms.ra.mine;

import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.sms.ra.mina.ClientHandler;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.Test;

import java.net.InetSocketAddress;

public class TestSendMTMessage {
    @Test
    public void sendMtMessage(){
        srartClient();
    }

    private static void srartClient() {
        String sreverIP = ConfigUtil.getInstance().getAgentConfig().getSmsServerIP();
        int serverPort = ConfigUtil.getInstance().getAgentConfig().getSmsServerPort();
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.setConnectTimeoutMillis(1000L);
        connector.setHandler(new ClientHandler());
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(sreverIP, serverPort));
        connFuture.awaitUninterruptibly(1000L);
        IoSession session = connFuture.getSession();
        MT_SMMessage mtmsg = new MT_SMMessage();
        mtmsg.setRcvAddresses(new String[]{"13122222222"});
        mtmsg.setSendAddress("10655588");
        mtmsg.setSmsText("hhehehehh   测试测试");
        session.write(mtmsg);
    }
}
