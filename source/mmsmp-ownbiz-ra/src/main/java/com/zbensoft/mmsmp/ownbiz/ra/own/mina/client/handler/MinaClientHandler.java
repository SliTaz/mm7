

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.client.handler;

import com.zbensoft.mmsmp.ownbiz.ra.own.mina.client.ConnectManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter {
    private static final Log _log = LogFactory.getLog(MinaClientHandler.class);

    public MinaClientHandler() {
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        _log.info("recive data from  corebiz：" + message);
    }

    public void sessionOpened(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
    }

    public void sessionClosed(IoSession session) {
        _log.error("ClientHandler 关闭连接，启动ConnectManager.", (Throwable)null);
        (new ConnectManager()).start();
    }

    public void sessionIdle(IoSession session, IdleStatus status) {
        session.write("ownbiz client send headset");
        if (session.getIdleCount(IdleStatus.READER_IDLE) >= 29) {
            session.close(true);
        }

    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        _log.error(String.format("ClientHandler 客户端发生异常 exceptionCaught:%s", cause.getMessage()), cause);
        session.close(true);
    }
}
