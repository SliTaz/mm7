

package com.zbensoft.mmsmp.ownbiz.ra.own.server;

import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.*;
import com.cmcc.mm7.vasp.service.MM7ReceiveServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.Enumeration;

public class ReceiveServlet extends MM7ReceiveServlet {
    static final Logger logger = Logger.getLogger(ReceiveServlet.class);
    static final String mm7ConfigFile = "./mm7Config.xml";

    public ReceiveServlet() {
    }

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        Enumeration initpnames = this.getServletConfig().getInitParameterNames();

        while(initpnames.hasMoreElements()) {
            String name = (String)initpnames.nextElement();
            String value = this.getInitParameter(name);
            if (name == null) {
                throw new ServletException("init-param without param-name. Maybe the web.xml is not well-formed?");
            }

            if (value == null) {
                throw new ServletException("init-param without param-value. Maybe the web.xml is not well-formed?");
            }
        }

        this.Config = new MM7Config("./mm7Config.xml");
    }

    public MM7VASPRes doDeliver(MM7DeliverReq req) {
        StringBuilder sb = new StringBuilder("ownagent receive one mmsmo message");
        sb.append("\r\n");
        sb.append("[");
        sb.append(" To:").append(req.getTo());
        sb.append(" sender: ").append(req.getSender());
        sb.append(" LinkedID: ").append(req.getLinkedID());
        sb.append(" Subject:").append(req.getSubject());
        sb.append("]");
        logger.info(sb.toString());
        MM7DeliverRes mm7DeliverRes = new MM7DeliverRes();
        mm7DeliverRes.setServiceCode("服务代码");
        mm7DeliverRes.setStatusCode(1000);
        mm7DeliverRes.setStatusText("success");
        mm7DeliverRes.setTransactionID(req.getTransactionID());
        return mm7DeliverRes;
    }

    public MM7VASPRes doDeliveryReport(MM7DeliveryReportReq req) {
        StringBuilder sb = new StringBuilder("ownagent receive one mmsmr message");
        sb.append("\r\n");
        sb.append("[");
        sb.append(" receipts: ").append(req.getRecipient());
        sb.append(" sender: ").append(req.getSender());
        sb.append(" messageid: ").append(req.getMessageID());
        sb.append(" statusText: ").append(req);
        sb.append("]");
        logger.info(sb.toString());
        MM7DeliveryReportRes mm7DeliveryReportRes = new MM7DeliveryReportRes();
        mm7DeliveryReportRes.setStatusCode(1000);
        mm7DeliveryReportRes.setTransactionID(req.getTransactionID());
        return mm7DeliveryReportRes;
    }
}
