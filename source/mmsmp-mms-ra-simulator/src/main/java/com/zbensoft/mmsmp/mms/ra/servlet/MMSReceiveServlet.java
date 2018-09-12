package com.zbensoft.mmsmp.mms.ra.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@WebServlet(urlPatterns = "/MMSServerServlet")
public class MMSReceiveServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(MMSReceiveServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	protected void service(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		logger.info("into MMSReceiveServlet service method");
		
		if (!"POST".equals(req.getMethod())) {
			logger.error("your request must be POST");
			response.sendError(400, "Http Method Must Be POST!");
			return;
		}
		
		//messageid  获取MM7APIVersion is ok
		String MM7APIVersion=req.getHeader("MM7APIVersion");
		logger.info("MM7APIVersion:"+MM7APIVersion);
		
		String messageid_one=req.getHeader("messageid");
		logger.info("messageid_one:"+messageid_one);
		String messageid_two=req.getHeader("MessageId");
		logger.info("messageid_two:"+messageid_two);
		
		String transactionid="";
		String sendnumber="";
		String recipientnumber="";
		String timestamp="";
		String messageid="";
		String StatusCode="1000";
		String StatusText="test111";
		
		if(messageid_one==null&&messageid_two==null){
			messageid="123456";
			UUID uuid=UUID.randomUUID();
	        String str = uuid.toString(); 
	        messageid=str.replace("-", "");
	        
	        logger.info("messageid:"+messageid);
		}
//		String report = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\" env:mustUnderstand=\"1\">"
//				+ transactionid + "</mm7:TransactionID></env:Header><env:Body>"
//				+ "<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0\">"
//				+ "<MM7Version>6.3.0</MM7Version><Sender><Number>" + sendnumber + "</Number></Sender>"
//				+ "<Recipient><Number>" + recipientnumber + "</Number></Recipient><TimeStamp>" + timestamp
//				+ "</TimeStamp>" + "<MMSRelayServerID>600003</MMSRelayServerID><MessageID>" + messageid + "</MessageID>"
//				+ "<StatusCode>" + StatusCode + "</StatusCode><StatusText>" + StatusText + "</StatusText>"
//				+ "</DeliveryReportReq></env:Body></env:Envelope>";
		
		String report="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SubmitRsp><StatusCode>1000</StatusCode><StatusText>test</StatusText><MessageID>"+messageid+"</MessageID></SubmitRsp>";
		
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.write(report);
		
		
		
		logger.info("PrintWriter write is finish.sleep 2000ms after send DeliveryReportReq");
		
		final String messageid_thread=messageid;
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception e1) {
					
				}
				
				logger.info("now send DeliveryReportReq");
				
				String resultUrl="http://localhost:9093/Receiver";
				
				String json ="";
				json=json+"<?xml version=\"1.0\" encoding=\"GB2312\"?>";
				json=json+"<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/ \">";
				json=json+"<env:Header>";
				json=json+"<mm7:TransactionID xmlns:mm7=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0 \" env:mustUnderstand=\"1\">1234567890</mm7:TransactionID>";
				json=json+"</env:Header>";
				json=json+"<env:Body>";
				json=json+"<DeliveryReportReq xmlns=\"http://www.3gpp.org/ftp/Specs/archive/23_series/23.140/schema/REL-6-MM7-1-0 \">";
				json=json+"<MessageID>"+messageid_thread+"</MessageID>";
				json=json+"<Recipient><Number>8613161608360</Number></Recipient>";
				json=json+"<Sender>1065580001</Sender>";
				json=json+"<MMStatus>Retrieved</MMStatus>";
				json=json+"<MMSStatusErrorCode>129</MMSStatusErrorCode>";
				json=json+"<StatusText>1000</StatusText>";
				json=json+"</DeliveryReportReq>";
				json=json+"</env:Body>";
				json=json+"</env:Envelope>";
				
				
				String result = null;
				OkHttpClient httpClient = new OkHttpClient();
				RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml"), json);
				Request request = new Request.Builder().url(resultUrl).post(requestBody).build();
				try {
					Response responseTmp = httpClient.newCall(request).execute();
					result = responseTmp.body().string();
					
					logger.info("send DeliveryReportReq is finish");
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("send DeliveryReportReq has error:"+e);
				}
			}
		}.start();
		
		
	}
}
