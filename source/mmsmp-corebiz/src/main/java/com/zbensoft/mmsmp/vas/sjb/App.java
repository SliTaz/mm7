 package com.zbensoft.mmsmp.vas.sjb;
 
 import com.zbensoft.mmsmp.common.ra.common.config.configbean.CorebizConfig;
 import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.PauseNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
 import com.zbensoft.mmsmp.common.ra.common.queue.MessageQueue;
 import com.zbensoft.mmsmp.common.ra.common.queue.ReceiveMessageHandler;
 import com.zbensoft.mmsmp.common.ra.common.queue.messagehandler.AbstractMessageHandler;
 import com.zbensoft.mmsmp.common.ra.common.util.ApplicationContextFactory;
 import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpServerImpl;
 import com.zbensoft.mmsmp.vas.sjb.common.DealMessageThread;
 import com.zbensoft.mmsmp.vas.sjb.common.Router;
 import com.zbensoft.mmsmp.vas.sjb.controller.TimerDipatcher;
 import com.zbensoft.mmsmp.vas.sjb.data.LogDao;
 import com.zbensoft.mmsmp.vas.sjb.log.DealMtLogThread;
 import com.zbensoft.mmsmp.vas.sjb.messagehandler.NotifyMessageHandler;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.UniBusiness;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.UniBusinessServiceImpl;
 import java.util.Map;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.codehaus.xfire.XFire;
 import org.codehaus.xfire.XFireFactory;
 import org.codehaus.xfire.jaxb2.JaxbServiceFactory;
 import org.codehaus.xfire.server.http.XFireHttpServer;
 import org.codehaus.xfire.service.Service;
 import org.codehaus.xfire.service.ServiceRegistry;
 import org.codehaus.xfire.service.invoker.BeanInvoker;
 import org.mortbay.jetty.Connector;
 import org.mortbay.jetty.Server;
 import org.mortbay.jetty.nio.SelectChannelConnector;
 import org.mortbay.jetty.servlet.ServletHandler;
 import org.mortbay.jetty.servlet.ServletHolder;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class App
 {
   private static final Log logger = LogFactory.getLog(App.class);
   
   private MessageQueue statusReport_queue;
   
   private MessageQueue MT_queue;
   
   private MessageQueue MO_queue;
   
   private Router sendRouter;
   private Router receiveRouter;
   private int sendThreadNum;
   private int receiveThreadNum;
   private int receiveReportThreadNum;
   private Map<String, AbstractMessage> logMap;
   private LogDao logDao;
   
   public static void main(String[] args)
   {
     App app = (App)ApplicationContextFactory.getBean("app");
     app.start();
   }
   
   public void start()
   {
     logger.info("starting controller");
     
     TimerDipatcher timerDipatcher = (TimerDipatcher)ApplicationContextFactory.getBean("timerDipatcher");
     Thread messageProducerThread = new Thread(timerDipatcher);
     messageProducerThread.start();
     logger.info("message Producer started");
     
 
 
     logger.info("starting sendThread vip");
     ExecutorService sendVipPool = Executors.newFixedThreadPool(this.sendThreadNum);
     for (int i = 0; i < this.sendThreadNum; i++) {
       DealMessageThread sendThread = new DealMessageThread(this.sendRouter, this.MT_queue, MessageQueue.PRIORITY_VIP);
       sendVipPool.execute(sendThread);
     }
     
     logger.info("starting sendThread common");
     ExecutorService sendCommonPool = Executors.newFixedThreadPool(this.sendThreadNum);
     for (int i = 0; i < this.sendThreadNum; i++) {
       DealMessageThread sendThread = new DealMessageThread(this.sendRouter, this.MT_queue, MessageQueue.PRIORITY_COMMON);
       sendCommonPool.execute(sendThread);
     }
     
     logger.info("starting sendThread low");
     ExecutorService sendLowPool = Executors.newFixedThreadPool(this.sendThreadNum);
     for (int i = 0; i < this.sendThreadNum; i++) {
       DealMessageThread sendThread = new DealMessageThread(this.sendRouter, this.MT_queue, MessageQueue.PRIORITY_LOW);
       sendLowPool.execute(sendThread);
     }
     
 
     logger.info("starting receiveThread");
     ExecutorService receivePool = Executors.newFixedThreadPool(this.receiveThreadNum);
     for (int i = 0; i < this.receiveThreadNum; i++) {
       DealMessageThread receiveThread = new DealMessageThread(this.receiveRouter, this.MO_queue, MessageQueue.PRIORITY_COMMON);
       receivePool.execute(receiveThread);
     }
     
     logger.info("starting receiveThread");
     ExecutorService receivePoolVIP = Executors.newFixedThreadPool(this.receiveThreadNum);
     for (int i = 0; i < this.receiveThreadNum; i++) {
       DealMessageThread receiveThread = new DealMessageThread(this.receiveRouter, this.MO_queue, MessageQueue.PRIORITY_VIP);
       receivePoolVIP.execute(receiveThread);
     }
     
 
 
 
 
 
 
 
 
 
     logger.info("starting logThread");
     DealMtLogThread dealMt = new DealMtLogThread(this.logMap, this.logDao);
     Thread logThread = new Thread(dealMt);
     logThread.start();
     
 
 
 
     TcpServerImpl tcpServer = new TcpServerImpl();
     NotifyMessageHandler handler = new NotifyMessageHandler();
     handler.registerMessageHandler(PauseNotificationMessage.class, (AbstractMessageHandler)ApplicationContextFactory.getBean("pauseNotificationMessageHandler"));
     handler.registerMessageHandler(SendNotificationMessage.class, (AbstractMessageHandler)ApplicationContextFactory.getBean("sendNotificationMessageHandler"));
     tcpServer.setDataHandler(handler, 51200);
     tcpServer.beginListen(ConfigUtil.getInstance().getCorebizConfig().getMtQueueListenPort());
     
 
 
 
 
 
 
 
 
     TcpServerImpl MOtcpServer = new TcpServerImpl();
     ReceiveMessageHandler MOhandler = new ReceiveMessageHandler(this.MO_queue);
     MOtcpServer.setDataHandler(MOhandler, 51200);
     MOtcpServer.beginListen(ConfigUtil.getInstance().getCorebizConfig().getMoQueueListenPort());
     
     startUniBusinessService();
     
     startHttpServer();
   }
   
   private void startUniBusinessService()
   {
     JaxbServiceFactory serviceFactory = new JaxbServiceFactory();
     
 
 
 
     UniBusinessServiceImpl ws = (UniBusinessServiceImpl)ApplicationContextFactory.getBean("UniBusinessService");
     
     Service service = serviceFactory.create(UniBusiness.class);
     service.setInvoker(new BeanInvoker(ws));
     
 
     XFire xfire = XFireFactory.newInstance().getXFire();
     xfire.getServiceRegistry().register(service);
     
 
     XFireHttpServer server = new XFireHttpServer();
     
     server.setPort(ConfigUtil.getInstance().getCorebizConfig().getUniBusinessListenPort());
     try
     {
       server.start();
     } catch (Exception ex) {
       ex.printStackTrace();
     }
     logger.info("unibusiness ws started at:" + ConfigUtil.getInstance().getCorebizConfig().getUniBusinessListenPort() + ".");
   }
   
   public MessageQueue getMO_queue() {
     return this.MO_queue;
   }
   
   public void setMO_queue(MessageQueue mo_queue) {
     this.MO_queue = mo_queue;
   }
   
   public void startHttpServer() {
     Server server = new Server();
     
     Connector connector = new SelectChannelConnector();
     
     connector.setPort(8080);
     server.setConnectors(new Connector[] { connector });
     
 
     ServletHandler handler = new ServletHandler();
     com.zbensoft.mmsmp.common.ra.MM7.sp.ReceiveServlet spReceiveServlet = new com.zbensoft.mmsmp.common.ra.MM7.sp.ReceiveServlet(this.MT_queue);
     
     handler.addServletWithMapping(new ServletHolder(), "/ucmmsagent/MM7request");
     
 
     com.zbensoft.mmsmp.common.ra.MM7.servlet.ReceiveServlet reportReceiveServlet = new com.zbensoft.mmsmp.common.ra.MM7.servlet.ReceiveServlet(this.MO_queue);
     handler.addServletWithMapping(new ServletHolder(), "/ucmmsagent/MM7SPrequest");
     
 
 
 
 
 
 
 
 
 
     server.addHandler(handler);
     try {
       server.start();
       logger.info("WebServer is starting  at 8080......");
       server.join();
     } catch (Exception e) {
       logger.info("startup webserver error:  ", e);
     }
   }
   
 
   public MessageQueue getMT_queue()
   {
     return this.MT_queue;
   }
   
   public void setMT_queue(MessageQueue mt_queue) {
     this.MT_queue = mt_queue;
   }
   
   public void setLogMap(Map<String, AbstractMessage> logMap) {
     this.logMap = logMap;
   }
   
   public void setSendRouter(Router sendRouter) {
     this.sendRouter = sendRouter;
   }
   
   public void setReceiveRouter(Router receiveRouter) {
     this.receiveRouter = receiveRouter;
   }
   
   public void setSendThreadNum(int sendThreadNum) {
     this.sendThreadNum = sendThreadNum;
   }
   
   public void setReceiveThreadNum(int receiveThreadNum) {
     this.receiveThreadNum = receiveThreadNum;
   }
   
   public int getReceiveReportThreadNum() {
     return this.receiveReportThreadNum;
   }
   
   public void setReceiveReportThreadNum(int receiveReportThreadNum) {
     this.receiveReportThreadNum = receiveReportThreadNum;
   }
   
   public MessageQueue getStatusReport_queue() {
     return this.statusReport_queue;
   }
   
   public void setStatusReport_queue(MessageQueue statusReport_queue) {
     this.statusReport_queue = statusReport_queue;
   }
   
   public void setLogDao(LogDao logDao) {
     this.logDao = logDao;
   }
 }


