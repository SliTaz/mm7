package com.zbensoft.sgip.gateway;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Bind;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_BindResp;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SgipGatewaySimulator {
    public SgipGatewaySimulator() {
    }

    public static void main(String[] args) {
        startServer();
    }



    private static void startServer() {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.setHandler(new TestServerHandler());

        try {
            int port = 8809;
            acceptor.bind(new InetSocketAddress(port));
        } catch (IOException var3) {
            ;
        }

    }

    public static class TestServerHandler extends IoHandlerAdapter {
        private static final Logger logger = Logger.getLogger(TestServerHandler.class);
        private static final String SUCCESS_CODE = "1";
        private static final String FAILED_CODE = "2";

        public TestServerHandler() {
        }

        public void messageReceived(IoSession session, Object message) throws Exception {
            if(message instanceof SGIP_Bind)
            {
                session.write(new SGIP_BindResp());
            }
        }

        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            logger.error(" server exceptionCaught(IoSession, Throwable)", cause);
        }

        @Override
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        }
    }

    static class SenderMoMessageThread extends Thread {
        IoSession session;

        public SenderMoMessageThread(IoSession session) {
            this.session = session;
        }

        @Override
        public void run() {
            while (true){

                try {
                    Thread.sleep(1000 *5 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MO_SMMessage momsg = new MO_SMMessage();
                momsg.setSendAddress("10655588");
                momsg.setSmsText("hhehehehh   测试测试");
                momsg.setVasId(null);
                momsg.setLinkId(null);
                momsg.setGlobalMessageid("asdfa42345");
                momsg.setGlobalCreateTime(System.currentTimeMillis());
                momsg.setSequence_Number_1((int)(1 >> 0));
                momsg.setSequence_Number_2(2);
                momsg.setSequence_Number_3(3);
                momsg.setMessageCoding((byte)1);
                momsg.setTP_pid((byte)1);
                momsg.setTP_udhi((byte)1);
                momsg.setContentLength(32);

                session.write(momsg);

            }
        }
    }
}
