package com.zbensoft.mmsmp.mms.ra.corbize;

import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpSPMessage;
import com.zbensoft.mmsmp.common.ra.common.message.SubmitReq;
import com.zbensoft.mmsmp.mms.ra.mmsagent.ApplicationListener;
import com.zbensoft.mmsmp.mms.ra.mmsagent.MinaClientProxy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@WebServlet(urlPatterns = "/CorbizeReceiveServlet")
public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiveServlet.class);
	MinaClientProxy minaClientProxy;
//	NioSocketAcceptor nioSocketAcceptor;

	public void init() throws ServletException {
		this.minaClientProxy = new MinaClientProxy();
//		this.nioSocketAcceptor = ((NioSocketAcceptor) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("minaSocketAcceptor"));
		this.minaClientProxy.setHost("127.0.0.1");
		this.minaClientProxy.setPort(ApplicationListener.serverPort);
	}

	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		logger.info(" into ReceiveServlet(CorbizeReceiveServlet) service method");
		try {
			MT_MMHttpSPMessage mt = new MT_MMHttpSPMessage();

			Map reqHeads = new HashMap();
			ByteArrayOutputStream baos = null;
			ServletInputStream bio = null;
			byte[] aByte = new byte[1024];
			int readLen = 0;
			byte[] contentBytes = (byte[]) null;
			try {
				Enumeration eHeads = req.getHeaderNames();
				while (eHeads.hasMoreElements()) {
					String key = (String) eHeads.nextElement();
					String val = req.getHeader(key);
					reqHeads.put(key, val);
				}

				baos = new ByteArrayOutputStream();
				bio = req.getInputStream();

				baos.write(req.getContentType().getBytes());
				baos.write("\r\n".getBytes());
				while ((readLen = bio.read(aByte)) != -1) {
					baos.write(aByte, 0, readLen);
				}

				contentBytes = baos.toByteArray();
			} catch (Exception ex) {
				logger.error("", ex);
				try {
					baos.close();
				} catch (Exception localException1) {
				}
				try {
					bio.close();
				} catch (Exception localException2) {
				}
			} finally {
				try {
					baos.close();
				} catch (Exception localException3) {
				}
				try {
					bio.close();
				} catch (Exception localException4) {
				}
			}
			mt.setRequestHeads(reqHeads);
			mt.setContentByte(contentBytes);
			mt.setRemoteHost(req.getRemoteHost());

			StringBuilder sb = new StringBuilder("Corebiz Mock receive MT_MMHttpSPMessage message from spAgent");
			sb.append("\r\n");
			sb.append("[");
			sb.append(" RemoteHost:").append(req.getRemoteHost());
			sb.append(" VASID:").append(mt.getSubmitReq().getVASID());
			sb.append(" VASPID:").append(mt.getSubmitReq().getVASPID());
			sb.append(" To:").append(mt.getSubmitReq().getTo());
			sb.append(" SenderAddress:").append(mt.getSubmitReq().getSenderAddress());
			sb.append(" ServiceCode: ").append(mt.getSubmitReq().getServiceCode());
			sb.append(" LinkedID: ").append(mt.getSubmitReq().getLinkedID());
			sb.append(" Subject: ").append(mt.getSubmitReq().getSubject());
			sb.append(" TransactionID: ").append(mt.getSubmitReq().getTransactionID());
			sb.append(" Authorization: ").append(mt.getHead("Authorization"));
			sb.append(" SOAPAction: ").append(mt.getHead("SOAPAction"));
			sb.append(" MM7APIVersion: ").append(mt.getHead("MM7APIVersion"));
			sb.append(" MIME-Version: ").append(mt.getHead("MIME-Version"));
			sb.append(" Content-Transfer-Encoding: ").append(mt.getHead("Content-Transfer-Encoding"));
			sb.append("]");

			logger.info(sb.toString());

			this.minaClientProxy.submit(mt);
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}
}
