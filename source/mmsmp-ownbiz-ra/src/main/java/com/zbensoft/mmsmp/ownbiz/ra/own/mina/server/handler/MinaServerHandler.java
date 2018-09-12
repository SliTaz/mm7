

package com.zbensoft.mmsmp.ownbiz.ra.own.mina.server.handler;

import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.common.ra.common.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.cache.DataCache;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread.ReportThread;
import com.zbensoft.mmsmp.ownbiz.ra.own.queue.MessageQuene;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.WriteReport;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaServerHandler extends IoHandlerAdapter {
    private MessageQuene messageQuene = null;
    static final Logger logger = Logger.getLogger(MinaServerHandler.class);

    public MinaServerHandler(MessageQuene messageQuene) {
        this.messageQuene = messageQuene;
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message == null) {
            logger.error("mmsownbiz mina server receive message is Null");
        } else if (message instanceof MO_SMMessage) {
            logger.info("开始处理自主业务点播流程.......");
            this.messageQuene.getResultQuence().add((MO_SMMessage)message);
        } else if (message instanceof ProxyPayMessage) {
            logger.info("接收corebiz返回的鉴权消息(ProxyPayMessage)的状态为：" + ((ProxyPayMessage)message).getGlobalMessageid() + ":" + ((ProxyPayMessage)message).getStatus());
            this.messageQuene.getResultQuence().add((ProxyPayMessage)message);
        } else if (message instanceof OrderRelationUpdateNotifyRequest) {
            logger.info("接收corebiz返回的鉴权消息(OrderRelationUpdateNotifyRequest)为：" + ((OrderRelationUpdateNotifyRequest)message).getGlobalMessageid() + ":" + ((OrderRelationUpdateNotifyRequest)message).getProductId());
            this.messageQuene.getResultQuence().add((OrderRelationUpdateNotifyRequest)message);
        } else if (message instanceof MO_ReportMessage) {
            MO_ReportMessage morpt = (MO_ReportMessage)message;
            if (morpt != null) {
                if (morpt.getServiceCode() != null) {
                    String spProductId = morpt.getServiceCode();
                    VasServiceRelationEntity vsre = DataCache.getVasServiceRelationEntity(spProductId);
                    if (vsre.getCpType() == 3) {
                        morpt.setReportUrl(vsre.getReportUrl());
                        logger.info("Content is not in this platform , status report notify sp(cp) .MessageId:" + morpt.getMessageId() + "\tGlobalMessageid" + morpt.getGlobalMessageid());
                        ReportThread.getRepotList().add(morpt);
                        WriteReport.toWrite(morpt.getContent());
                    } else {
                        logger.info("Content in this platform , status report receive end . MessageId:" + morpt.getMessageId() + "\tGlobalMessageid" + morpt.getGlobalMessageid());
                    }
                } else {
                    logger.info("MO_ReportMessage : serviceCode is null，status report receive stop .");
                }
            } else {
                logger.info("MO_ReportMessage is null , status report receive stop .");
            }
        } else if (message instanceof String) {
            logger.error("mmsownbiz mina server receive string message:" + message.toString());
        } else {
            logger.error("mmsownbiz receive message type error:" + message.getClass().getName());
        }

    }

    public void sessionOpened(IoSession session) throws Exception {
        session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 30);
        session.getConfig().setIdleTime(IdleStatus.WRITER_IDLE, 30);
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        session.write("mmsownbiz-> corebiz client echo idle message");
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("Unexpected exception.", cause);
        session.close(true);
    }
}
