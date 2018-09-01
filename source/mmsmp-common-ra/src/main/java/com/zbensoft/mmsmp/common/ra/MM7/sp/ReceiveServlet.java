package com.zbensoft.mmsmp.common.ra.MM7.sp;

import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
import com.zbensoft.mmsmp.common.ra.common.util.PropertiesHelper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiveServlet.class);
	private String mmsc_url;
	private static int BUFFER_LENGTH = 2048;
	private MessageQueue MT_Queue;
	private static Map<String, Long> LINK_IDS = new HashMap();
	private SpRequestHandler sp_handler;

	public ReceiveServlet(MessageQueue queue) {
		this.MT_Queue = queue;
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.mmsc_url = PropertiesHelper.getString("mmsc.url", null);
		this.sp_handler = new SpRequestHandler();
	}

	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		if (!"POST".equals(req.getMethod())) {
			logger.error("your request must be POST");
			response.sendError(400, "Http Method Must Be POST!");
			return;
		}

		StringBuilder sb = new StringBuilder("\r\n");
		sb.append("Receive MT MMS From SP.......");
		logger.info(sb.toString());

		com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest request = new com.zbensoft.mmsmp.common.ra.MM7.servlet.HttpRequest(req);
		SubmitReq submit = new SubmitReq();
		submit.parser(request.getContent());

		MT_SPMMHttpMessage spmmsmessage = new MT_SPMMHttpMessage();
		spmmsmessage.setOperatorsType(3);
		spmmsmessage.setRequest(request);
		spmmsmessage.setAuthorization(req.getHeader("Authorization"));
		spmmsmessage.setContent_Transfer_Encoding(req.getHeader("Content-Transfer-Encoding"));
		spmmsmessage.setMime_Version(req.getHeader("MIME-Version"));
		spmmsmessage.setMM7APIVersion(req.getHeader("MM7APIVersion"));
		spmmsmessage.setSOAPAction(req.getHeader("SOAPAction"));

		spmmsmessage.setMessageid(submit.getTransactionID() + submit.getTo());
		this.MT_Queue.addMessage(spmmsmessage, Integer.valueOf(MessageQueue.PRIORITY_COMMON));
		logger.info("MT_Queue size is:" + this.MT_Queue.getSize(MessageQueue.PRIORITY_COMMON));

		PrintWriter out = response.getWriter();
		String returnstr = SubmitResp.getSubmitResp(submit.getTransactionID(), submit.getTo());
		logger.info("=====>First Report:" + returnstr);
		out.write(returnstr);
	}

	public static synchronized void put_linkid(String link_id) {
		LINK_IDS.put(link_id, Long.valueOf(System.currentTimeMillis()));
	}

	private synchronized boolean check_linkid(String linkedID) {
		if ((linkedID == null) || ("".equals(linkedID.trim())))
			return true;
		Iterator it = LINK_IDS.entrySet().iterator();
		long now = System.currentTimeMillis();
		while (it.hasNext()) {
			Map.Entry next = (Map.Entry) it.next();
			if (linkedID.equals(next.getKey())) {
				it.remove();
				return true;
			}
			if (now - ((Long) next.getValue()).longValue() > 7200000L)
				it.remove();
		}
		return false;
	}

	private void return_err_submitRsp(HttpServletResponse response, String TransactionID, String err_code, String err_msg) {
		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-2\" env:mustUnderstand=\"1\">"
				+ TransactionID + "</mm7:TransactionID>" + "</env:Header>" + "<env:Body>" + "<SubmitRsp xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-5-MM7-1-2\">"
				+ "<MM7Version>5.3.0</MM7Version>" + "<Status>" + "<StatusCode>" + err_code + "</StatusCode>" + "<StatusText>" + err_msg + "</StatusText>" + "</Status>" + "<MessageID></MessageID>" + "</SubmitRsp>"
				+ "</env:Body></env:Envelope>";
		try {
			PrintWriter writer = response.getWriter();
			writer.print(msg);
			writer.close();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void send_tommsc(com.zbensoft.mmsmp.common.ra.common.message.HttpRequest request, HttpServletResponse response) throws IOException {
		logger.info("send_tommsc");

		HttpURLConnection httpURL = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			logger.info("mmsc_url:" + this.mmsc_url);
			URL theURL = new URL(this.mmsc_url);
			httpURL = (HttpURLConnection) theURL.openConnection();
			httpURL.setDoInput(true);
			httpURL.setDoOutput(true);
			httpURL.setRequestProperty("content-type", request.getContentType());
			httpURL.setRequestProperty("Content-Transfer-Encoding", request.getContent_Transfer_Encoding());
			httpURL.setRequestProperty("Authorization", request.getAuthorization());
			httpURL.setRequestProperty("SOAPAction", request.getSOAPAction());
			httpURL.setRequestProperty("MM7APIVersion", request.getMM7APIVersion());
			httpURL.setRequestProperty("Mime-Version", request.getMime_Version());
			httpURL.setRequestMethod("POST");
			InputStream input = request.getInputStream();
			dos = new DataOutputStream(new BufferedOutputStream(httpURL.getOutputStream()));
			byte[] buf = new byte[BUFFER_LENGTH];
			int n = 0;
			while ((n = input.read(buf)) != -1) {
				dos.write(buf, 0, n);
			}
			dos.flush();
			logger.info("httpURL.getResponseCode():" + httpURL.getResponseCode());
			if (httpURL.getResponseCode() == 200) {
				dis = new DataInputStream(new BufferedInputStream(httpURL.getInputStream()));
				int len = dis.available();
				byte[] b = new byte[len];
				int r = dis.read(b);
				int pos = r;
				while (pos < len) {
					r = dis.read(b, pos, len - pos);
					pos += r;
				}

				String str = new String(b);
				request.setResponse(str);
				logger.info("mms center return message:" + str);
				this.sp_handler.updateRequest(request);

				PrintWriter writer = response.getWriter();
				writer.write(str);
				writer.close();
			} else {
				request.setResponse("HTTP/1.1 " + httpURL.getResponseCode());
				logger.error("mmc return error message: [" + httpURL.getResponseCode() + "] " + httpURL.getResponseMessage());
			}
		} catch (Throwable ex) {
			logger.error("", ex);
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (Exception localException2) {
				}
			if (dos != null)
				try {
					dos.close();
				} catch (Exception localException3) {
				}
			if (httpURL != null)
				httpURL.disconnect();
		}
	}
}
