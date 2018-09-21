package com.zbensoft.mmsmp.ownbiz.ra.own.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zbensoft.mmsmp.common.ra.common.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpHelper {
	static String apiUrl = "";

	static {
		apiUrl =  PropertiesUtil.get("common","mmsmp.api.url");
	}
	   
	public static String getSystemParamBykey(String key) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getSystemParamBykey?code="+key)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");
	     String value = null;
	     if("OK".equals(statusCode)){
	    	JSONObject message = jsonObject.getJSONObject("body");
			if(message.getString("value") == null || message.getString("value").equals("")){
				value = message.getString("defaultValue");
			}else{
				value = message.getString("value");
			}
	     }
	     return value;
	}
	
	public static boolean isExistOrderRelation(String phoneNumber, String spProductId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/userOrderCheck?phoneNumber="+phoneNumber+"&spProductId="+spProductId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}

        int count = Integer.parseInt(responseData);

        return count > 0;
}

public static long insertUserOrderHis(UserOrderEntity userOrderHis) {
	 String phoneNumber=userOrderHis.getCellPhoneNo();
	 String chargePhoneNumber=userOrderHis.getChargeParty();
	 String orderTime=userOrderHis.getOrderDate();
	 String orderRoute=userOrderHis.getOrderMethod();
	 String fee=userOrderHis.getFee();
	 RequestBody body = new FormBody.Builder().build();
    Request request = new Request.Builder()
            .url(apiUrl + "/ownbiz/userOrderHis?phoneNumber="+phoneNumber+"&chargePhoneNumber="+chargePhoneNumber
            		+"&orderTime="+orderTime+"&orderRoute="+orderRoute+"&fee="+fee+"&spInfoId="+userOrderHis.getSpInfoId() + "&serviceUniqueId="+userOrderHis.getServiceUniqueId()+"&orderType="+userOrderHis.getFeeType())
            .get()
            .build();
    OkHttpClient okHttp = new OkHttpClient();
    Call call = okHttp.newCall(request);
    Response response = null;
    try {
        response = call.execute();
    } catch (IOException e) {
        e.printStackTrace();
    }
   String responseData = null;
	try {
		responseData = response.body().string();
	} catch (IOException e) {
		e.printStackTrace();
	}
	JSONObject jsonObject = JSONObject.parseObject(responseData);
    String statusCode=jsonObject.getString("statusCode");;
    if("CREATED".equals(statusCode)){
    	return 1L;
    }
	return 0L;
}

public static long insertUserOrder(UserOrderEntity userOrder, long hisID) {
	 String phoneNumber=userOrder.getCellPhoneNo();
	 String chargePhoneNumber=userOrder.getChargeParty();
	 String orderTime=userOrder.getOrderDate();
	 String orderRoute=userOrder.getOrderMethod();
	 String fee=userOrder.getFee();
	 String spInfoId=userOrder.getSpInfoId();
	 RequestBody body = new FormBody.Builder().build();
    Request request = new Request.Builder()
            .url(apiUrl + "/ownbiz/userOrder?phoneNumber="+phoneNumber+"&chargePhoneNumber="+chargePhoneNumber
            		+"&orderTime="+orderTime+"&orderRoute="+orderRoute+"&spInfoId="+spInfoId+"&fee="+fee+"&serviceUniqueId="+userOrder.getServiceUniqueId()+"&orderType="+userOrder.getFeeType())
            .get()
            .build();
    OkHttpClient okHttp = new OkHttpClient();
    Call call = okHttp.newCall(request);
    Response response = null;
    try {
        response = call.execute();
    } catch (IOException e) {
        e.printStackTrace();
    }
   String responseData = null;
	try {
		responseData = response.body().string();
	} catch (IOException e) {
		e.printStackTrace();
	}
	JSONObject jsonObject = JSONObject.parseObject(responseData);
    String statusCode=jsonObject.getString("statusCode");;
    if("CREATED".equals(statusCode)){
    	return 1L;
    }
	return 0L;
}

public static void deleteUserOrder(String phoneNumber, String productInfoIds) {
	String productInfoId=productInfoIds+"";
	 RequestBody body = new FormBody.Builder().build();
     Request request = new Request.Builder()
             .url(apiUrl + "/ownbiz/userOrderDelete/"+phoneNumber+"/"+productInfoId)
             .delete()
             .build();
     OkHttpClient okHttp = new OkHttpClient();
     Call call = okHttp.newCall(request);
     Response response = null;
     try {
         response = call.execute();
     } catch (IOException e) {
         e.printStackTrace();
     }
    String responseData = null;
	try {
		responseData = response.body().string();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}

public static Long selectContentIDInSend() {
	 RequestBody body = new FormBody.Builder().build();
     Request request = new Request.Builder()
             .url(apiUrl + "/ownbiz/contentInfo")
             .get()
             .build();
     OkHttpClient okHttp = new OkHttpClient();
     Call call = okHttp.newCall(request);
     Response response = null;
     try {
         response = call.execute();
     } catch (IOException e) {
         e.printStackTrace();
     }
    String responseData = null;
	try {
		responseData = response.body().string();
	} catch (IOException e) {
		e.printStackTrace();
	}
	JSONObject jsonObject = JSONObject.parseObject(responseData);
    String statusCode=jsonObject.getString("statusCode");;
    /* ArrayList<String> contentIDList = new ArrayList();
    	JSONArray bodyArray = jsonObject.getJSONArray("body");
		 for(int i = 0; i < bodyArray.size(); i++){
			 JSONObject message = bodyArray.getJSONObject(i);
			 contentIDList.add(message.getString("contentInfoId"));
		 }
    }*/
    if("OK".equals(statusCode)){
    	return 1L;
    }
    return 0L;
}

public static boolean deleteSendMMSUsersByContentID(Long contentInfoIds) {
	String contentInfoId=contentInfoIds+"";
	 RequestBody body = new FormBody.Builder().build();
    Request request = new Request.Builder()
            .url(apiUrl + "/ownbiz/contentInfoDelete/"+contentInfoId)
            .delete()
            .build();
    OkHttpClient okHttp = new OkHttpClient();
    Call call = okHttp.newCall(request);
    Response response = null;
    try {
        response = call.execute();
    } catch (IOException e) {
        e.printStackTrace();
    }
   String responseData = null;
	try {
		responseData = response.body().string();
	} catch (IOException e) {
		e.printStackTrace();
	}
	JSONObject jsonObject = JSONObject.parseObject(responseData);
    String statusCode=jsonObject.getString("statusCode");;
    if("NO_CONTENT".equals(statusCode)){
    	return true;
    }
	return false;
}
	public static List<VaspEnitiy> getAllVaspEnitiy() {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getAllVaspEnitiy")
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");
	     List<VaspEnitiy> list = new ArrayList<VaspEnitiy>();
	     if("OK".equals(statusCode)){
	    	JSONArray jsonArray = jsonObject.getJSONArray("body");
	    	for(int i=0;i<jsonArray.size();i++){
	    		VaspEnitiy vaspEnitiy = new VaspEnitiy();
	    		JSONObject message = jsonArray.getJSONObject(i);
	    		vaspEnitiy.setVaspId(message.getString("companyCode"));
	    		vaspEnitiy.setVaspName(message.getString("companyName"));
	    		vaspEnitiy.setBusinessPhone(message.getString("businessTel"));
	    		list.add(vaspEnitiy);
	    	}
	     }
	     return list;
	}
	
	public static List<SysParametersEntity> querySysParameters() {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/getSysConfig")
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     List<SysParametersEntity> sysParamsList = new ArrayList<SysParametersEntity>();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 for(int i = 0; i < bodyArray.size(); i++){
				 SysParametersEntity sysParameters = new SysParametersEntity();
				 JSONObject message = bodyArray.getJSONObject(i);
				 sysParameters.setKey(message.getString("applicationServerCode") + message.getString("code"));
				 if(message.getString("value") == null || message.getString("value").equals("")){
					 sysParameters.setValue(message.getString("defaultValue"));
				 }else{
					 sysParameters.setValue(message.getString("value"));
				 }
				 sysParameters.setDesc(message.getString("remark"));
				 sysParamsList.add(sysParameters);
			 }
	     }
	     return sysParamsList;
	}
	
	public static List<VasServiceRelationEntity> getAllVasServiceRelation() {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getAllVasServiceRelation")
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");
	     List<VasServiceRelationEntity> vasList = new ArrayList<VasServiceRelationEntity>();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 for(int i = 0; i < bodyArray.size(); i++){
				 VasServiceRelationEntity vas = new VasServiceRelationEntity();
				 JSONObject message = bodyArray.getJSONObject(i);
				 vas.setProductName(message.getString("productName"));
				 vas.setVasserviceUniqueId(message.getString("productInfoId"));
				 vas.setAccessNumber(message.getString("cpAccessId"));
				 vas.setOrderCode(message.getString("orderCommand"));
				 vas.setOrderFee(message.getDoubleValue("orderFee"));
				 vas.setOnDemandCode(message.getString("onDemandCommand"));
				 vas.setDbFee(message.getDouble("onDemandFee"));
				 vas.setFeeType(message.getString("orderType"));
				 vas.setSpId(message.getString("companyCode"));
				 JSONObject productService = message.getJSONObject("productService");
				 vas.setSpProductId(productService.getString("spProductId"));
				 vas.setReportUrl(productService.getString("reportMessageUrl"));
	
				 vasList.add(vas);
			 }
	     }
	     return vasList;
	}
	
	public static VasServiceRelationEntity getVasServiceRelation(String spProductId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getVasServiceRelation?spProductId=" + spProductId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     VasServiceRelationEntity vas = new VasServiceRelationEntity();
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 vas.setSpProductId(message.getString("spProductId"));
			 vas.setReportUrl(message.getString("reportMessageUrl"));
			 JSONObject productInfo = message.getJSONObject("productInfo");
			 vas.setProductName(productInfo.getString("productName"));
			 vas.setVasserviceUniqueId(productInfo.getString("productInfoId"));
			 vas.setAccessNumber(productInfo.getString("cpAccessId"));
			 vas.setOrderCode(productInfo.getString("orderCommand"));
			 vas.setOrderFee(productInfo.getDoubleValue("orderFee"));
			 vas.setOnDemandCode(productInfo.getString("onDemandCommand"));
			 vas.setDbFee(productInfo.getDouble("onDemandFee"));
			 vas.setSpId(productInfo.getString("companyCode"));
			 vas.setFeeType(message.getString("orderType"));
	     }
	     return vas;
	}
	
	public static int queryContentCount(String spProductId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/queryContentCount?spProductId=" + spProductId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     int value = Integer.valueOf(responseData);
	     return value;
	}
	
	public static void updateNotifySpStatus(String uniqueid, String userPhone, int status) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/updateNotifySpStatus?productInfoId=" + uniqueid +"&phoneNumber="+userPhone+"&status="+status)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CooperKeyEntity getCooperKeyEntityByAccountId(String spAccountId) {
		
		RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/cooperkey?cooperId=" + spAccountId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     CooperKeyEntity vas = new CooperKeyEntity();
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 vas.setKeyId(message.getString("keyId"));
	    	 vas.setCooperId(message.getString("cooperId"));
			 vas.setCooperName(message.getString("cooperName"));
			 vas.setCooperKey(message.getString("cooperKey"));
			 vas.setIPS(message.getString("ips"));
			 vas.setNotifyUrl(message.getString("notifyUrl"));
			 vas.setRemark(message.getString("remark"));
			 vas.setServiceTel(message.getString("serviceTel"));
			 vas.setAppName(message.getString("appName"));
	     }
	     return vas;
	}

	public static List<CooperKeyEntity> queryAllCooperKeyEntity() {
		RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/selectCooperkey")
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 List<CooperKeyEntity> cooperKeyEntityList= new ArrayList<>();
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");
	     if("OK".equals(statusCode)){
	     	JSONArray bodyArray = jsonObject.getJSONArray("body");
	 		 for(int i = 0; i < bodyArray.size(); i++){
	 			 JSONObject message = bodyArray.getJSONObject(i);
	 			CooperKeyEntity vas = new CooperKeyEntity();
		    	 vas.setKeyId(message.getString("keyId"));
		    	 vas.setCooperId(message.getString("cooperId"));
				 vas.setCooperName(message.getString("cooperName"));
				 vas.setCooperKey(message.getString("cooperKey"));
				 vas.setIPS(message.getString("ips"));
				 vas.setNotifyUrl(message.getString("notifyUrl"));
				 vas.setRemark(message.getString("remark"));
				 vas.setServiceTel(message.getString("serviceTel"));
				 vas.setAppName(message.getString("appName"));
				 cooperKeyEntityList.add(vas);
	 		 }
	     }
	     return cooperKeyEntityList;
	}

	public static void insert(ProxyPayMessage ppm) {
		RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/proxypaymessage?globalmessageid=" + ppm.getGlobalMessageid()
	            		 +"&phonenumber="+ppm.getPhoneNumber()+"&chargeparty="+ppm.getChargeParty()+"&cpid="+ppm.getCpID()
	            		 +"&serviceid="+ppm.getServiceId()+"&servicename="+ppm.getServiceName()+"&feetype="+ppm.getFeeType()
	            		 +"&fee="+ppm.getFee()+"&accountid="+ppm.getAccountId()+"&status="+ppm.getStatus()
	            		 +"&validatecode="+ppm.getValidateCode()+"&spid="+ppm.getSpId()+"&usertype="+ppm.getUserType()
	            		 +"&perorid="+ppm.getPerorid()+"&productid="+ppm.getProductId()+"&productname="
	            		 +ppm.getProductName()+"&createdate="+ppm.getCreateDate()
	            		 +"&updatedate="+ppm.getUpdateDate()+"&sourcetype="+ppm.getSourceType())
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void insertUserOrderPay(UserOrderPayEntity ppm) {
		RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/userOrderPay?userOrderPayId=" + ppm.getCellPhonenum() 
	                +"&phoneNumber="+ppm.getCellPhonenum()+"&orderTime="+ppm.getOrderTime()+"&fee="+ppm.getFee()
	                +"&productInfoId="+ppm.getServiceUniqueid()+""+"&status="+ppm.getStatus()+"&spInfoId="+ppm.getSpId()+"&keyId="+ppm.getKeyId() + "&orderType=" +  ppm.getFeeType())
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUserOrderPay(UserOrderPayEntity userOrderPayId) {
		String phoneNumber=userOrderPayId.getCellPhonenum();
		String productInfoId=userOrderPayId.getServiceUniqueid()+"";
		 RequestBody body = new FormBody.Builder().build();
		    Request request = new Request.Builder()
		            .url(apiUrl + "/ownbiz/userOrderPay/"+phoneNumber+"/"+productInfoId)
		            .delete()
		            .build();
		    OkHttpClient okHttp = new OkHttpClient();
		     Call call = okHttp.newCall(request);
		    Response response = null;
		     try {
		         response = call.execute();
		     } catch (IOException e) {
		         e.printStackTrace();
		     }
		    String responseData = null;
			try {
				responseData = response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static void update(ProxyPayMessage proxyPayMessage) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/updateProxyPayMessage?globalMessageid=" + proxyPayMessage.getGlobalMessageid() +"&phoneNumber="+proxyPayMessage.getPhoneNumber()
	             +"&cpID="+proxyPayMessage.getCpID()+"&serviceId="+proxyPayMessage.getServiceId()+"&serviceName="+proxyPayMessage.getServiceName()+"&feeType="+proxyPayMessage.getFeeType()
	             +"&fee="+proxyPayMessage.getFee()+"&status="+proxyPayMessage.getStatus()+"&validateCode="+proxyPayMessage.getValidateCode()
	             +"&accountId="+proxyPayMessage.getAccountId()+"&spId="+proxyPayMessage.getSpId()+"&userType="+proxyPayMessage.getUserType()
	             +"&perorid="+proxyPayMessage.getPerorid()+"&productId="+proxyPayMessage.getProductId()+"&productName="+proxyPayMessage.getProductName()
	             +"&chargeParty="+proxyPayMessage.getChargeParty())
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static CooperKeyEntity getCooperKeyMessage(String messageId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getCooperKeyMessage?globalMessageid=" + messageId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     CooperKeyEntity cooperKeyEntity = new CooperKeyEntity();
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
			 JSONObject cooperKey = message.getJSONObject("cooperKey");
			 cooperKeyEntity.setCooperKey(cooperKey.getString("cooperKey"));
			 cooperKeyEntity.setNotifyUrl(cooperKey.getString("notifyUrl"));
	     }
	     return cooperKeyEntity;
	}
	
	public static boolean getProxyPayMessage(String messageId, String validateCode) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getProxyPayMessage?globalMessageid=" + messageId + "&validateCode=" + validateCode)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     boolean flag = false;
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 if(message!=null){
	    		 flag = true;
	    	 }
	     }
	     return flag;
	}
	
	public static ProxyPayMessage getProxyPayMessageById(String messageId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/ownbiz/getProxyPayMessageById?globalMessageid=" + messageId)
	             .get()
	             .build();
	     OkHttpClient okHttp = new OkHttpClient();
	     Call call = okHttp.newCall(request);
	     Response response = null;
	     try {
	         response = call.execute();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	    String responseData = null;
		try {
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JSONObject jsonObject = JSONObject.parseObject(responseData);
	     String statusCode=jsonObject.getString("statusCode");;
	     ProxyPayMessage model = new ProxyPayMessage();
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 model.setFeeType(message.getString("feetype"));
	    	 model.setAccountId(message.getString("accountid"));
	    	 model.setCpID(message.getString("cpid"));
	    	 model.setPhoneNumber(message.getString("phonenumber"));
	    	 model.setProductId(message.getString("productid"));
	    	 model.setGlobalMessageid(message.getString("globalmessageid"));
	    	 model.setSourceType(Integer.valueOf(message.getString("sourceType")));
			 JSONObject cooperKey = message.getJSONObject("cooperKey");
			 model.setCooperKey(cooperKey.getString("cooperKey"));
			 model.setNotifyURL(cooperKey.getString("notifyUrl"));
	     }
	     return model;
	}
	
}
