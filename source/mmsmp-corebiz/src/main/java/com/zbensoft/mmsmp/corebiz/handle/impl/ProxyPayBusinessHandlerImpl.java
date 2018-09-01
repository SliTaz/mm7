 package com.zbensoft.mmsmp.corebiz.handle.impl;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
 import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_SMMessage;
 import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
 import com.zbensoft.mmsmp.corebiz.route.IMessageRouter;
 import org.apache.log4j.Logger;
 
 
 
 
 public class ProxyPayBusinessHandlerImpl
 {
   static final Logger logger = Logger.getLogger(ProxyPayBusinessHandlerImpl.class);
   
   IMessageRouter messageRouter;
   
 
   public void doHandler(AbstractMessage message)
   {
     if ((message instanceof ProxyPayMessage))
     {
       logger.info("ownservice ---> corebiz");
       
 
 
       ProxyPayMessage ppy = (ProxyPayMessage)message;
       
 
       int status = Integer.parseInt(ppy.getFeeType());
       
       if (9 == status)
       {
         logger.info("doHandler validate sms message proxy Instead of sending[feetype:" + status + "gmsgid:" + ppy.getGlobalMessageid() + " phoneNumber:" + ppy.getPhoneNumber() + "]");
         sendMTMsgToQuence(ppy.getSmsText(), ppy.getPhoneNumber());
 
       }
       else if (1 == status)
       {
         logger.info("doHandler proxypaymessage vac auth(1) checkrequest[gmsgid:" + ppy.getGlobalMessageid() + ",phone:" + ppy.getPhoneNumber() + "]");
         doPPM(ppy);
       }
       else if (2 == status)
       {
         logger.info("doHandler proxypaymessage vac auth(2) checkrequest[gmsgid:" + ppy.getGlobalMessageid() + ",phone:" + ppy.getPhoneNumber() + "]");
         doPPM(ppy);
       }
       else if (4 == status)
       {
         logger.info("doHandler proxypaymessage vac auth(4) checkrequest[gmsgid:" + ppy.getGlobalMessageid() + ",phone:" + ppy.getPhoneNumber() + "]");
         doPPM(ppy);
       }
       else
       {
         logger.info("doHandler proxypaymessage feetype is error,checkrequest failed[gmsgid:" + ppy.getGlobalMessageid() + ",phone:" + ppy.getPhoneNumber() + "]");
       }
       
     }
     else if ((message instanceof CheckResponse))
     {
       CheckResponse response = (CheckResponse)message;
       logger.info("=====> proxypay receive CheckResponse from vacAgent !  code:" + response.getResult_Code());
       doCR(response);
     }
   }
   
   private void doCR(CheckResponse response)
   {
     CheckRequest request = response.getCRequest();
     
     String rspCode = response.getResult_Code();
     
 
     ProxyPayMessage ppy = new ProxyPayMessage();
     ppy.setGlobalMessageid(response.getCRequest().getGlobalMessageid());
     ppy.setStatus(response.getResult_Code());
     ppy.setSourceType(response.getSourceType());
     ppy.setFeeType(request.getServiceType());
     ppy.setProductId(request.getSp_product_id());
     ppy.setSpId(request.getSp_id());
     ppy.setServiceId(request.getService_id());
     ppy.setPhoneNumber(request.getUser_number());
     ppy.setSmsText(response.getReturnMessage());
     
 
     if ("1".equals(request.getServiceType()))
     {
       logger.info("proxypaymessage vac auth(" + request.getServiceType() + ") repsonse[gmsgid:" + response.getCRequest().getGlobalMessageid() + ",phone:" + response.getCRequest().getUser_number() + ",code:" + rspCode + "]");
       this.messageRouter.doRouter(ppy);
 
     }
     else if ("2".equals(request.getServiceType()))
     {
       logger.info("proxypaymessage vac auth(" + request.getServiceType() + ") repsonse[gmsgid:" + response.getCRequest().getGlobalMessageid() + ",phone:" + response.getCRequest().getUser_number() + ",code:" + rspCode + "]");
       this.messageRouter.doRouter(ppy);
 
 
     }
     else if ("4".equals(request.getServiceType()))
     {
       logger.info("vac -->corebize checkresponse  return_code :" + response.getResult_Code() + " service type:" + request.getServiceType());
       
       String gmsgid = request.getGlobalMessageid();
       String phone = request.getUser_number();
       String linkid = response.getLinkID();
       
 
       if ((rspCode == null) || (rspCode.trim().equals("")) || (rspCode.toLowerCase().equals("null")))
       {
         logger.info("proxypaymessage vac auth(4) response failure[gmsgid:" + gmsgid + ",phone:" + phone + ",desc:code is null]");
         
         ppy.setSmsText("系统响应超时，请稍后再试");
         this.messageRouter.doRouter(ppy);
         return;
       }
       
       if (!rspCode.equals("0"))
       {
         logger.info("proxypaymessage vac auth(4) response failure[gmsgid:" + gmsgid + ",phone:" + phone + ",rspcode:" + rspCode + "]");
         ppy.setSmsText("系统响应超时，请稍后再试");
         this.messageRouter.doRouter(ppy);
         return;
       }
       
       if ((linkid == null) || (linkid.length() <= 0) || ("NULL".equalsIgnoreCase(linkid.toUpperCase())))
       {
         logger.info("proxypaymessage vac auth(4) response failure[gmsgid:" + gmsgid + ",phone:" + phone + ",desc:linkid is null]");
         ppy.setSmsText("系统响应超时，请稍后再试");
         this.messageRouter.doRouter(ppy);
         return;
       }
       ppy.setFeeType("5");
       
       logger.info("proxypaymessage vac auth(5) checkrequest[gmsgid:" + response.getCRequest().getGlobalMessageid() + ",phone:" + response.getCRequest().getUser_number() + ",code:" + rspCode + ",linkid:" + response.getLinkID() + "]");
       
       CheckRequest checkRequest = new CheckRequest();
       checkRequest.setLinkID(linkid);
       checkRequest.setGlobalMessageid(gmsgid);
       checkRequest.setSp_product_id(ppy.getProductId());
       checkRequest.setSp_id(ppy.getSpId());
       checkRequest.setService_id(String.valueOf(ppy.getServiceId()));
       checkRequest.setUser_number(phone);
       checkRequest.setServiceType(ppy.getFeeType());
       this.messageRouter.doRouter(checkRequest);
 
 
     }
     else if ("5".equals(request.getServiceType()))
     {
       logger.info("proxypaymessage vac auth(5) repsonse[gmsgid:" + response.getCRequest().getGlobalMessageid() + ",phone:" + response.getCRequest().getUser_number() + ",code:" + rspCode + ",linkid:" + response.getLinkID() + "]");
       
       ppy.setFeeType("4");
       
       if ("1".equals(response.getNeedConfirm()))
       {
         CheckRequest cr6 = new CheckRequest(null, null, null, null, response.getSrc_SequenceNumber(), "6", null);
         cr6.setGlobalMessageid(request.getGlobalMessageid());
         cr6.setResponse(false);
         
         this.messageRouter.doRouter(cr6);
       }
       
       this.messageRouter.doRouter(ppy);
 
     }
     else if ("6".equals(request.getServiceType()))
     {
       logger.info("proxypaymessage vac auth(6) repsonse[gmsgid:" + response.getCRequest().getGlobalMessageid() + ",phone:" + response.getCRequest().getUser_number() + ",code:" + rspCode + "]");
     }
     else
     {
       logger.info("proxypaymessage vac auth(" + response.getCRequest().getServiceType() + ") repsonse type error[gmsgid:" + response.getCRequest().getGlobalMessageid() + "]");
     }
   }
   
   private void doPPM(ProxyPayMessage ppy)
   {
     CheckRequest checkRequest = new CheckRequest();
     checkRequest.setSp_product_id(ppy.getProductId());
     checkRequest.setSp_id(ppy.getSpId());
     checkRequest.setService_id(String.valueOf(ppy.getServiceId()));
     checkRequest.setUser_number(ppy.getPhoneNumber());
     checkRequest.setServiceType(ppy.getFeeType());
     checkRequest.setGlobalMessageid(String.valueOf(ppy.getGlobalMessageid()));
     this.messageRouter.doRouter(checkRequest);
   }
   
   private void sendMTMsgToQuence(String _msg, String send_addr)
   {
     MT_SMMessage mtsms = new MT_SMMessage();
     mtsms.setMtTranId(String.valueOf(System.currentTimeMillis()));
     mtsms.setSendAddress("10655565");
     mtsms.setRcvAddresses(new String[] { send_addr });
     mtsms.setSmsText(_msg);
     this.messageRouter.doRouter(mtsms);
   }
   
   public void setMessageRouter(IMessageRouter messageRouter)
   {
     this.messageRouter = messageRouter;
   }
 }





