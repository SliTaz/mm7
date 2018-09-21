 package com.zbensoft.mmsmp.corebiz.service.mm7;
 
 import com.zbensoft.mmsmp.common.ra.MM7.sp.SubmitReq;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SPMMHttpMessage;
import com.zbensoft.mmsmp.config.SpringBeanUtil;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import java.io.IOException;
 import java.io.InputStream;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.concurrent.LinkedBlockingQueue;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 
 
 
 
 
 
 
 
 
 public class MM7ReceiveServlet
   extends HttpServlet
 {
   static final Logger logger = Logger.getLogger(MM7ReceiveServlet.class);
   
   static final String TO_SPLIT = ",";
   LinkedBlockingQueue receiveQueue;
   
   public void init(ServletConfig config)
     throws ServletException
   {
     String queueName = config.getInitParameter("receiveQueue");
     this.receiveQueue = ((LinkedBlockingQueue)SpringBeanUtil.getBean(queueName));
     
     super.init(config);
   }
   
   protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
   {
     COREBIZ_LOG.INFO("ownbiz mms message areave corebiz");
     if (!"POST".equals(req.getMethod()))
     {
       res.sendError(400, "Http Method Must Be POST!");
       return;
     }
     
     HttpRequest request = new HttpRequest(req);
     SubmitReq submit = new SubmitReq();
     submit.parser(request.getContent());
     
     COREBIZ_LOG.INFO("corebiz<- ownbiz one mmsmt message normal[phones:" + submit.getTo() + "]");
     
     StringBuilder body = new StringBuilder();
     InputStream is = request.getInputStream();
     
     int count = 0;
     byte[] b = new byte['Ѐ'];
     
     while ((count = is.read(b)) > 0)
     {
       body.append(new String(b, 0, count));
     }
     
     String[] tos = submit.getTo().split(",");
     
     ArrayList<MT_SPMMHttpMessage> spmts = new ArrayList();
     
     for (int i = 0; (tos != null) && (i < tos.length); i++)
     {
       try
       {
         String[] nums = tos[i].split(":");
         String toNumber = nums[0];
         String chargeNumber = nums.length > 1 ? nums[1] : null;
         
         byte[] cbyte = replaceTo(body.toString(), toNumber).getBytes();
         
         MT_SPMMHttpMessage spmt = new MT_SPMMHttpMessage();
         
         spmt.setGlobalMessageid(MT_SPMMHttpMessage.generateUUID("MMS-OWN"));
         spmt.setGlobalMessageTime(System.currentTimeMillis());
         spmt.setMessageid(MT_SPMMHttpMessage.generateUUID(""));
         spmt.setOperatorsType(3);
         spmt.setAuthorization(req.getHeader("Authorization"));
         spmt.setContentType(req.getContentType());
         spmt.setContent_Transfer_Encoding(req.getHeader("Content-Transfer-Encoding"));
         spmt.setMime_Version(req.getHeader("MIME-Version"));
         spmt.setMM7APIVersion(req.getHeader("MM7APIVersion"));
         spmt.setSOAPAction(req.getHeader("SOAPAction"));
         spmt.setContentbyte(cbyte);
         spmt.setChargetNumber(chargeNumber);
         
 
 
         spmts.add(spmt);
       }
       catch (Exception localException) {}
     }
     
 
 
 
     this.receiveQueue.addAll(spmts);
     COREBIZ_LOG.INFO("corebiz handler ownbiz mms over");
     try {
       is.close();is = null;
     } catch (Exception localException1) {}
     PrintWriter out = res.getWriter();
     out.write(SubmitResp.getSubmitResp(submit.getTransactionID(), String.valueOf(System.currentTimeMillis()), "1000", "corebiz receive mmsmt success"));
     
     COREBIZ_LOG.INFO("corebiz send message tip to ownbiz");
   }
   
   public String replaceTo(String body, String to)
     throws IOException
   {
     StringBuilder sb = new StringBuilder(body);
     
     int startIndex = sb.indexOf("<To>") + "<To>".length();
     int endIndex = sb.indexOf("</To>");
     sb.replace(startIndex, endIndex, "<Number>" + to + "</Number>");
     
     return sb.toString();
   }
 }





