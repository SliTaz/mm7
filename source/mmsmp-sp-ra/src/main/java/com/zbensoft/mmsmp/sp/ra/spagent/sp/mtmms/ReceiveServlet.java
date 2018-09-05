package com.zbensoft.mmsmp.sp.ra.spagent.sp.mtmms;

import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.common.ra.mms.xmlutil.service.impl.VaspXmlServiceImpl;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import com.zbensoft.mmsmp.sp.ra.spagent.mina.SPMessageQuene;
import com.zbensoft.mmsmp.sp.ra.spagent.utils.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

//接收sp模拟器发送过来的MTMMS
@WebServlet(urlPatterns = "/SPReceiveMTMMS")
public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReceiveServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		logger.info("into ReceiveServlet service method");
		
		if (!"POST".equals(req.getMethod())) {
			logger.error("your request must be POST");
			response.sendError(400, "Http Method Must Be POST!");
			return;
		}
		HttpRequest request = new HttpRequest(req);
		SubmitReq submit = new SubmitReq();
		submit.parser(request.getContent());

		logger.info("Receive MT MMS From SP,  SubmitReq:" + submit.toString());

		MT_SPMMHttpMessage spmmsmessage = new MT_SPMMHttpMessage();
		spmmsmessage.setOperatorsType(3);

		spmmsmessage.setAuthorization(req.getHeader("Authorization"));
		spmmsmessage.setContentType(req.getContentType());
		spmmsmessage.setContent_Transfer_Encoding(req.getHeader("Content-Transfer-Encoding"));
		spmmsmessage.setMime_Version(req.getHeader("MIME-Version"));
		spmmsmessage.setMM7APIVersion(req.getHeader("MM7APIVersion"));
		spmmsmessage.setSOAPAction(req.getHeader("SOAPAction"));
		String gmessageid = generateMessageiD();
		spmmsmessage.setGlobalMessageid(gmessageid);
		spmmsmessage.setContentid(Integer.valueOf(1));
		spmmsmessage.setChargetNumber(submit.getChargedPartyID());

		String messageid = getMessageid();
		spmmsmessage.setMessageid(messageid);

		InputStream in = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[8192];
		int count = 0;
		while ((count = in.read(b)) > 0) {
			baos.write(b, 0, count);
		}
		
		byte[] byteArrays=baos.toByteArray();
		logger.info("byteArrays-> str:"+new String(byteArrays));
		
//		spmmsmessage.setContentbyte(baos.toByteArray());
		spmmsmessage.setContentbyte(byteArrays);

		spmmsmessage.setGlobalMessageTime(System.currentTimeMillis());
		try {
			SPMessageQuene.getInstance().getMtQuence().put(spmmsmessage);
			logger.info("put mt mms to mtquene,mtQuene size is [" + SPMessageQuene.getInstance().getMtQuence().size()
					+ "] [usernumber:" + submit.getTo() + "  messageid:" + spmmsmessage.getGlobalMessageid() + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				baos.close();
				baos = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}

		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
			String reportStr = SubmitResp.getSubmitResp(submit.getTransactionID(), messageid, null, null);
			logger.info("=====>First Report:" + reportStr);
			out.write(reportStr);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	private String getMessageid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String generateMessageiD() {
		return "MMSMT" + UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		VaspXmlServiceImpl vaspXmlService = new VaspXmlServiceImpl();
		vaspXmlService.setSpIpConfigFilePath("D:\\temp\\mms\\spIpValidate.xml");
		try {
			String[] sp = vaspXmlService.getSp("91405");
			System.out.println(sp[0]);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
