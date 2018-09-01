package com.zbensoft.mmsmp.sms.ra.mine;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueueClientTcpImpl;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpServerImpl;
import com.zbensoft.mmsmp.sms.ra.thread.ReceiveMessageThread;
import com.zbensoft.mmsmp.sms.ra.utils.ReceiveMTSmsHandler;
import com.zbensoft.mmsmp.sms.ra.utils.SmsMessageQuene;
import org.apache.log4j.Logger;

public class TestReceiveMessageThread {
    private static final Logger logger = Logger.getLogger(ReceiveMessageThread.class);

    public TestReceiveMessageThread() {
    }

    public static void main(String[] args) {
        TestReceiveMessageThread test = new TestReceiveMessageThread();

        while(true) {
            test.testSend();

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }
    }

    private void start() {
        logger.info("start test corebiz server...");
        TcpServerImpl mtTcpServer = new TcpServerImpl();
        ReceiveMTSmsHandler mtHandler = new ReceiveMTSmsHandler(SmsMessageQuene.getInstance());
        mtTcpServer.setDataHandler(mtHandler, 51200);
        int port = ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort();
        mtTcpServer.beginListen(port);
        logger.info("started  server at:" + port);
    }

    private void testSend() {
        MT_SMMessage mtmsg = new MT_SMMessage();
        mtmsg.setRcvAddresses(new String[]{"13122222222"});
        mtmsg.setSendAddress("10655588");
        mtmsg.setSmsText("hhehehehh     ���Բ���");
        MessageQueueClientTcpImpl messageQueueClientInf = new MessageQueueClientTcpImpl("127.0.0.1", 9090);
        messageQueueClientInf.send(mtmsg);
    }
}

