 package com.zbensoft.mmsmp.corebiz.service.xfire;
 
 import com.zbensoft.mmsmp.common.ra.common.message.WOCheckResponse;
 import com.zbensoft.mmsmp.corebiz.app.AppContextFactory;
 import com.zbensoft.mmsmp.corebiz.message.OrderRelationMessage;
 import com.zbensoft.mmsmp.corebiz.route.ReceiveRouter;
 import com.zbensoft.mmsmp.corebiz.util.StringUtil;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.Constants;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.OrderRelationRequest;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.ProductManageRequest;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.Response;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.ReverseUnsubscribeManageRequest;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.ServiceCapabilityManageRequest;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.ServiceManageRequest;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.UniBusiness;
 import java.io.PrintStream;
 import java.util.Map;
 import javax.jws.WebService;
 import org.apache.log4j.Logger;
 import org.springframework.context.ApplicationContext;
 
 
 
 
 
 
 
 
 @WebService(serviceName="UniBusinessService", targetNamespace="http://unibusiness.sjb.vas.aceway.com", endpointInterface="com.zbensoft.mmsmp.vas.sjb.unibusiness.UniBusiness")
 public class UniBusinessServiceImpl implements UniBusiness
 {
   private static Logger logger = Logger.getLogger(UniBusinessServiceImpl.class);
   ReceiveRouter receiveRouter;
   
   public void setReceiveRouter(ReceiveRouter receiveRouter) {
     this.receiveRouter = receiveRouter;
   }
   
 
 
 
   public Response orderRelationManage(OrderRelationRequest request)
   {
     logger.info("received  orderRelationManage request : " + request.toString());
     Response res = new Response();
     res.setDesc("业务已受理");
     res.setCodeType(Constants.ResponseCodeType_MMSMP);
     res.setReturnCode(Constants.ResponseCode_Sucess);
     
     if ((request.getUserPhone() == null) || (request.getUserPhone().trim().length() < 11)) {
       res.setCodeType(Constants.ResponseCodeType_MMSMP);
       res.setReturnCode(Constants.ResponseCode_UserPhoneError);
       res.setDesc("用户号码不合法");
       return res;
     }
     request.setUserPhone(StringUtil.getPhone11(request.getUserPhone()));
     
     String channel = request.getChannel();
     String globalMessageid = "";
     
     int intChannel = Integer.parseInt(channel);
     OrderRelationMessage OrderRelationMessage = new OrderRelationMessage();
     switch (intChannel)
     {
     case 1: 
       globalMessageid = OrderRelationMessage.generateUUID("UNI-WEB");
       break;
     
     case 4: 
       globalMessageid = OrderRelationMessage.generateUUID("UNI-WEB");
       break;
     
 
     case 2: 
       globalMessageid = OrderRelationMessage.generateUUID("UNI-WAP");
       break;
     
 
 
     case 3: 
     case 6: 
       globalMessageid = OrderRelationMessage.generateUUID("UNI-IPHONE");
       break;
     case 5: 
     default: 
       res.setCodeType(Constants.ResponseCodeType_MMSMP);
       res.setReturnCode(Constants.ResponseCode_ChannelTypeError);
       res.setDesc("未知渠道");
       return res;
     }
     
     OrderRelationMessage.setOrderRelationRequest(request);
     OrderRelationMessage.setGlobalMessageid(globalMessageid);
     
     this.receiveRouter.doRouter(OrderRelationMessage);
     logger.info("OrderRelationMessage doRouter  [channel:" + channel + " globalMessageid:" + globalMessageid + "]");
     return res;
   }
   
 
 
   public Response produceManage(ProductManageRequest request)
   {
     logger.info("=======admin管理门户 内容暂停恢复消息通知corbize =========" + request.getProductID());
     
     String[] str = request.getProductID().split("[|]");
     System.out.println("sp_productID=======" + str[6]);
     
     if (request.getProductID().startsWith("HF"))
     {
       logger.info("恢复Map key=======ZT" + str[6]);
       ((Map)AppContextFactory.getApplicationContext().getBean("commonDataMemory")).remove("ZT" + str[6]);
     }
     else
     {
       WOCheckResponse wc = new WOCheckResponse();
       wc.setMessageid(request.getProductID());
       logger.info("暂停Map key=======ZT" + str[6]);
       ((Map)AppContextFactory.getApplicationContext().getBean("commonDataMemory")).put("ZT" + str[6], wc);
     }
     
     Response res = new Response();
     res.setDesc("业务已受理");
     res.setReturnCode("0");
     return res;
   }
   
 
 
 
   public Response reverseUnsubscribeManage(ReverseUnsubscribeManageRequest arg0)
   {
     return null;
   }
   
 
 
   public Response serviceCapabilityManage(ServiceCapabilityManageRequest arg0)
   {
     return null;
   }
   
 
 
   public Response serviceManage(ServiceManageRequest arg0)
   {
     return null;
   }
 }


