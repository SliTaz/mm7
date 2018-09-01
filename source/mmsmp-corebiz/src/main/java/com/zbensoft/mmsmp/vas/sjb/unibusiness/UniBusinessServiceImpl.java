 package com.zbensoft.mmsmp.vas.sjb.unibusiness;
 
 import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.SgipSubmit;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ServiceCapacity;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vas;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
import com.zbensoft.mmsmp.common.ra.common.notifymessageclient.NotifyMessageClientTcpImpl;
import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDao;
import com.zbensoft.mmsmp.vas.sjb.data.BusinessManageDAO;
import com.zbensoft.mmsmp.vas.sjb.data.ContentInfoDAO;
import com.zbensoft.mmsmp.vas.sjb.data.OrderRelationDAO;
import com.zbensoft.mmsmp.vas.sjb.messagehandler.SendNotificationMessageHandler;
 
 
@WebService(serviceName="UniBusinessService", targetNamespace="http://unibusiness.sjb.vas.aceway.com", endpointInterface="com.aceway.vas.sjb.unibusiness.UniBusiness")
public class UniBusinessServiceImpl
  implements UniBusiness
{
  private static Logger logger = Logger.getLogger(UniBusinessServiceImpl.class);
  
  private BusinessManageDAO businessManageDAO;
  private OrderRelationDAO orderRelationDAO;
  private ContentInfoDAO contentInfoDAO;
  private SmsSenderDao smsSenderDao;
  private SendNotificationMessageHandler sendNotificationMessageHandler;
  private SgipSubmit sgipSubmit = ConfigUtil.getInstance().getSgipSubmit();
  
  public SendNotificationMessageHandler getSendNotificationMessageHandler() {
    return this.sendNotificationMessageHandler;
  }
  
  public void setSendNotificationMessageHandler(SendNotificationMessageHandler sendNotificationMessageHandler) { this.sendNotificationMessageHandler = sendNotificationMessageHandler; }
  
  public Response serviceCapabilityManage(ServiceCapabilityManageRequest request)
  {
    logger.debug("received serviceCapabilityManage request :" + request.toString());
    
    Response res = new Response();
    try
    {
      ServiceCapacity serviceCapacity = this.businessManageDAO.getServiceCapacityByCAPACITYID(request.getServiceCapabilityID());
      if (serviceCapacity == null) {
        res.setReturnCode(Constants.ResponseCode_IDError);
        return res;
      }
      
      int operate = request.getOperate();
      
      if (operate == Constants.ManageRequest_Operate_Apply) {
        serviceCapacity.setStatus("1");
      } else if (operate == Constants.ManageRequest_Operate_Cancel) {
        serviceCapacity.setStatus("4");
      } else if (operate == Constants.ManageRequest_Operate_Normal) {
        serviceCapacity.setStatus("2");
      } else if (operate == Constants.ManageRequest_Operate_Pause) {
        serviceCapacity.setStatus("3");
      } else if (operate == Constants.ManageRequest_Operate_PreCancel) {
        serviceCapacity.setStatus("5");
      } else {
        res.setReturnCode(Constants.ResponseCode_StatusError);
        return res;
      }
      

      this.businessManageDAO.updateByCapacity(serviceCapacity);
      
      res.setReturnCode(Constants.ResponseCode_Sucess);
      return res;
    }
    catch (Throwable ex) {
      ex.printStackTrace();
      

      res.setReturnCode(Constants.ResponseCode_OtherError); }
    return res;
  }
  
  /* Error */
  public Response orderRelationManage(OrderRelationRequest request3)
  {
	return null;
 
   }
   
   public Response produceManage(ProductManageRequest request2)
   {
     logger.debug("received produceManage request :" + request2.toString());
     
     Response res = new Response();
     try
     {
       Vasservice product = this.businessManageDAO.getVASServiceByServiceCode(
         request2.getProductID());
       if (product == null)
       {
         if (Integer.valueOf(request2.getProductType()).intValue() == Constants.OrderType_Product) {
           res.setReturnCode(Constants.ResponseCode_ProductCodeError);
           return res; }
         if (Integer.valueOf(request2.getProductType()).intValue() == Constants.OrderType_Package) {
           res.setReturnCode(Constants.ResponseCode_PackageCodeError);
           return res;
         }
       } else {
         int operate = request2.getOperate();
         
         if (operate == Constants.ManageRequest_Operate_Apply) {
           product.setStatus("1");
         } else if (operate == Constants.ManageRequest_Operate_Cancel) {
           product.setStatus("4");
         } else if (operate == Constants.ManageRequest_Operate_Normal) {
           product.setStatus("2");
         } else if (operate == Constants.ManageRequest_Operate_Pause) {
           product.setStatus("3");
         } else if (operate == Constants.ManageRequest_Operate_PreCancel) {
           product.setStatus("5");
         } else {
           res.setReturnCode(Constants.ResponseCode_StatusError);
           return res;
         }
         
 
         this.businessManageDAO.updateByProduct(product);
         
         res.setReturnCode(Constants.ResponseCode_Sucess);
         return res;
       }
     } catch (Throwable ex) {
       ex.printStackTrace();
       
 
       res.setReturnCode(Constants.ResponseCode_OtherError); }
     return res;
   }
   
   public Response serviceManage(ServiceManageRequest request1)
   {
     logger.debug("received serviceManage request :" + request1.toString());
     
     Response res = new Response();
     try
     {
       Vas vas = this.businessManageDAO.getVASByVASID(request1.getServiceID());
       if (vas == null)
       {
         res.setReturnCode(Constants.ResponseCode_IDError);
         return res;
       }
       
       int operate = request1.getOperate();
       
       if (operate == Constants.ManageRequest_Operate_Apply) {
         vas.setStatus("1");
       } else if (operate == Constants.ManageRequest_Operate_Cancel) {
         vas.setStatus("4");
       } else if (operate == Constants.ManageRequest_Operate_Normal) {
         vas.setStatus("2");
       } else if (operate == Constants.ManageRequest_Operate_Pause) {
         vas.setStatus("3");
       } else if (operate == Constants.ManageRequest_Operate_PreCancel) {
         vas.setStatus("5");
       } else {
         res.setReturnCode(Constants.ResponseCode_StatusError);
         return res;
       }
       
 
       this.businessManageDAO.updateByVas(vas);
       
       res.setReturnCode(Constants.ResponseCode_Sucess);
       return res;
     }
     catch (Throwable ex) {
       ex.printStackTrace();
       
 
       res.setReturnCode(Constants.ResponseCode_OtherError); }
     return res;
   }
   
   public BusinessManageDAO getBusinessManageDAO() {
     return this.businessManageDAO;
   }
   
   public void setBusinessManageDAO(BusinessManageDAO businessManageDAO) {
     this.businessManageDAO = businessManageDAO;
   }
   
   public OrderRelationDAO getOrderRelationDAO() {
     return this.orderRelationDAO;
   }
   
   public void setOrderRelationDAO(OrderRelationDAO orderRelationDAO) {
     this.orderRelationDAO = orderRelationDAO;
   }
   
   public ContentInfoDAO getContentInfoDAO() { return this.contentInfoDAO; }
   
   public void setContentInfoDAO(ContentInfoDAO contentInfoDAO) {
     this.contentInfoDAO = contentInfoDAO;
   }
   
 
 
   public Response reverseUnsubscribeManage(ReverseUnsubscribeManageRequest request1)
   {
     logger.debug("received reverseUnsubscribeManage request :" + request1.toString());
     String userId = request1.getPhone();
     int userIdType = request1.getUserType().intValue();
     int idType = request1.getProductType().intValue();
     String id = request1.getProductID();
//     ISMPClient ismpClient = new ISMPClient();
//     int result = ismpClient.withDrawUserOrder(userId, Integer.valueOf(userIdType), Integer.valueOf(idType), id);
     Response response = new Response();
     response.setReturnCode("0");
//     if (result == 0) {
       try {
         this.orderRelationDAO.delOrderRelation(userId, id);
       } catch (Exception e) {
         logger.error(e.getMessage());
         response.setReturnCode(Constants.ResponseCode_OtherError);
         return response;
       }
       response.setReturnCode(Constants.ResponseCode_Sucess);
//     } else {
//       response.setReturnCode(Constants.ResponseCode_OtherError);
//     }
     return response;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public void SaveOrderRaltion(String userPhone, String productId, String spId, String channel, String spOrderId, String notifySpFlag, int period)
   {
     throw new Error("Unresolved compilation problem: \n\tThe method AddOrderRelation(String, String, Vasservice, String, String, String, String, String, int) in the type SmsSenderDao is not applicable for the arguments (String, Vasservice, String, String, String, String, String, int)\n");
   }
   
 
 
 
 
 
 
 
 
 
   public void CancelOrderRaltion(String userPhone, String productId, String channel)
   {
     Vasservice service = this.smsSenderDao.getVASServiceByServiceCode(productId);
     if (service == null) {
       logger.info("=====>产品ID不存在");
     }
     String uniqueid = this.smsSenderDao.getUniqueid(productId);
     UserOrder userOrder = this.smsSenderDao.getOrderRelation(userPhone, uniqueid);
     if (userOrder == null) {
       logger.info("=====>定制关系不存在");
 
     }
     else
     {
       this.smsSenderDao.CancelOrderRelation(userPhone, service, userOrder, channel);
     }
   }
   
   public void setSmsSenderDao(SmsSenderDao smsSenderDao) {
     this.smsSenderDao = smsSenderDao;
   }
   
 
 
 
 
   public static boolean sendMessage(String smstext, String[] receiveaddresses)
   {
     try
     {
       SendNotificationMessage notificatonMsg = new SendNotificationMessage();
       notificatonMsg.setPhoneNumber(checkPhones(receiveaddresses));
       notificatonMsg.setSendContent(smstext);
       notificatonMsg.setMtType("4");
       String host = ConfigUtil.getInstance().getAdminConfig().getNotifyMessageIP();
       String port = ConfigUtil.getInstance().getAdminConfig().getNotifyMessagePort();
       NotifyMessageClientTcpImpl notifyMsgClient = new NotifyMessageClientTcpImpl(host, Integer.parseInt(port));
       
       int result = notifyMsgClient.send(notificatonMsg);
       if (result == 0) {
         return true;
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
     return false;
   }
   
 
 
 
   private static String[] checkPhones(String[] receiveaddresses)
   {
     for (int i = 0; i < receiveaddresses.length; i++) {
       if (!receiveaddresses[i].startsWith("86")) {
         receiveaddresses[i] = ("86" + receiveaddresses[i]);
       }
     }
     return receiveaddresses;
   }
   
   public static void main(String[] args) { sendMessage("您的验证码为：1212", new String[] { "8615901405008" }); }
 }





