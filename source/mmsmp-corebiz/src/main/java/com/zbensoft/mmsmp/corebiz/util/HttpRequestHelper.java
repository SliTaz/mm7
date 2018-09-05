package com.zbensoft.mmsmp.corebiz.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zbensoft.mmsmp.common.ra.common.db.entity.CpInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ProductService;
import com.zbensoft.mmsmp.common.ra.common.db.entity.SysParameters;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserBlackWhiteList;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasp;
import com.zbensoft.mmsmp.common.ra.common.db.entity.VaspReserveInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.db.entity.VasserviceReserveInfo;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestHelper {
	static String apiUrl = "http://localhost:8080";
	@Value("${api.url}")
	private static String API_URL;
	   
	public static List<UserBlackWhiteList> getWhiteList() {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/whiteList")
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
	     List<UserBlackWhiteList> userList = new ArrayList<UserBlackWhiteList>();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 for(int i = 0; i < bodyArray.size(); i++){
				 UserBlackWhiteList userBlackWhite = new UserBlackWhiteList();
				 JSONObject message = bodyArray.getJSONObject(i);
				 userBlackWhite.setCellphoneNumber(message.getString("blackWhiteList"));
				 userBlackWhite.setDescription(message.getString("remark"));
				 userBlackWhite.setType(message.getInteger("numberType"));
//				 userBlackWhite.setProductId(message.getString("remark"));
				 userList.add(userBlackWhite);
			 }
	     }
	     return userList;
	}
	
	public static List<CpInfo> getCpInfo() {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/cpInfo")
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
	     List<CpInfo> cpInfoList = new ArrayList<CpInfo>();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 for(int i = 0; i < bodyArray.size(); i++){
				 CpInfo cpInfo = new CpInfo();
				 JSONObject message = bodyArray.getJSONObject(i);
				 cpInfo.setCpid(message.getString("cpInfoId"));
				 cpInfo.setCpname(message.getString("cpName"));
				 cpInfo.setCpaddress(message.getString("cpAddr"));
				 cpInfo.setArtificialperson(message.getString("legalPerson"));
				 cpInfo.setBankname(message.getString("bankName"));
				 cpInfo.setBankaccount(message.getString("bankAccount"));
				 cpInfo.setWebsite(message.getString("webAddr"));
				 cpInfo.setFax(message.getString("faxNo"));
				 cpInfo.setBusinesslinkman(message.getString("businessPerson"));
				 cpInfo.setBusinesslinkmanemail(message.getString("businessEmail"));
				 cpInfo.setBusinesslinkmanmobile(message.getString("businessMobile"));
				 cpInfo.setBusinesslinkmantel(message.getString("businessTel"));
				 cpInfo.setClientlinkman(message.getString("customerPerson"));
				 cpInfo.setClientlinkmanemail(message.getString("customerEmail"));
				 cpInfo.setClientlinkmanmobile(message.getString("customerMobile"));
				 cpInfo.setClientlinkmantel(message.getString("customerTel"));
				 cpInfo.setTechlinkman(message.getString("tecPerson"));
				 cpInfo.setTechlinkmanemail(message.getString("tecEmail"));
				 cpInfo.setTechlinkmanmobile(message.getString("tecMobile"));
				 cpInfo.setTechlinkmantel(message.getString("tecTel"));
				 cpInfo.setFinancelinkman(message.getString("financePerson"));
				 cpInfo.setFinancelinkmanemail(message.getString("financeEmail"));
				 cpInfo.setFinancelinkmanmobile(message.getString("financeMobile"));
				 cpInfo.setFinancelinkmantel(message.getString("financeTel"));
				 cpInfo.setStatus(message.getInteger("status").toString());
				 cpInfo.setCpcode(message.getString("cpCode"));
				 cpInfo.setRegemail(message.getString("registerEmail"));
				 cpInfo.setLoginno(message.getString("loginUser"));
				 cpInfo.setRejectdesc(message.getString("rejectReason"));
				 cpInfoList.add(cpInfo);
			 }
	     }
	     return cpInfoList;
	}
	
	public static UserOrder getUserOrder(String charge,String unique) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/userOrder?phoneNumber = " + charge + "&productInfoId = " + unique)
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
	     UserOrder userOrder = new UserOrder();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 JSONObject message = bodyArray.getJSONObject(0);
			 userOrder.setId(message.getString("phoneNumber"));
			 userOrder.setChargeparty(message.getString("chargePhoneNumber"));
			 userOrder.setOrderdate(message.getDate("orderTime"));
			 userOrder.setOrdermethod(message.getInteger("orderRoute").toString());
			 userOrder.setFeetype(message.getInteger("orderType").toString());
			 userOrder.setFee(message.getDouble("fee"));
//			 userOrder.setOrderhisid(orderhisid);
			 userOrder.setFeaturestr(message.getString("featureStr"));
			 userOrder.setStatus(message.getInteger("status"));
//			 userOrder.setServiceuniqueid(serviceuniqueid);
			 userOrder.setVersion(message.getString("version"));
			 userOrder.setNodisturbtime(message.getString("notDisturbTime"));
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 userOrder.setOrderDate1(sdf.format(message.getDate("effTime")));
			 userOrder.setPriority(message.getInteger("priority"));
			 userOrder.setUserarea(message.getString("area"));
	     }
	     return userOrder;
	}
	
	public static String getSpurlByVaspid(String spInfoId) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/getSpurlByVaspid?spInfoId = " + spInfoId)
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
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 JSONObject message = bodyArray.getJSONObject(0);
			 value = message.getString("spOrderNotifyUrl");
	     }
	     return value;
	}
	
	public static ProductService requestacc(String onDemandAccess) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/requestacc?onDemandAccess=" + onDemandAccess)
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
	     ProductService productService = new ProductService();
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
//			 JSONObject message = bodyArray.getJSONObject(0);
	    	 productService.setProductServiceId(message.getString("productServiceId"));
	    	 productService.setProductInfoId(message.getString("productInfoId"));
	    	 productService.setIsConfirm(message.getInteger("isConfirm"));
	    	 productService.setIsPresent(message.getInteger("isPresent"));
	    	 productService.setConfirmPrompt(message.getString("confirmPrompt"));
	    	 productService.setSuccPrompt(message.getString("succPrompt"));
	    	 productService.setCanncelPrompt(message.getString("canncelPrompt"));
	    	 productService.setPopularizeStartTime(message.getDate("popularizeStartTime"));
	    	 productService.setPopularizeEndTime(message.getDate("popularizeEndTime"));
	    	 productService.setIsFree(message.getInteger("isFree"));
	    	 productService.setFreeTime(message.getInteger("freeTime"));
	    	 productService.setBillingId(message.getString("billingId"));
	    	 productService.setDiscountRemark(message.getString("discountRemark"));
	    	 productService.setDiscountId(message.getString("discountId"));
	    	 productService.setNeedConfirm(message.getInteger("needConfirm"));
	    	 productService.setSendNum(message.getInteger("sendNum"));
	    	 productService.setOrderCommandMatch(message.getInteger("orderCommandMatch"));
	    	 productService.setOrderAccess(message.getString("orderAccess"));
	    	 productService.setOrderAccessMatch(message.getInteger("orderAccessMatch"));
	    	 productService.setCancelCommandMatch(message.getInteger("cancelCommandMatch"));
	    	 productService.setCancelAccess(message.getString("cancelAccess"));
	    	 productService.setCancelAccessMatch(message.getInteger("cancelAccessMatch"));
	    	 productService.setOnDemandCommandMatch(message.getInteger("onDemandCommandMatch"));
	    	 productService.setOnDemandAccess(message.getString("onDemandAccess"));
	    	 productService.setOnDemandAccessMatch(message.getInteger("onDemandAccessMatch"));
	    	 productService.setEffTime(message.getDate("effTime"));
	    	 productService.setExpTime(message.getDate("expTime"));
	    	 productService.setIsAloneOrder(message.getInteger("isAloneOrder"));
	    	 productService.setNotifyType(message.getInteger("notifyType"));
	    	 productService.setProductCity(message.getString("productCity"));
	    	 productService.setProductServicePeriodGrade(message.getString("productServicePeriodGrade"));
	    	 productService.setProductServiceGrade(message.getString("productServiceGrade"));
	    	 productService.setProductCredit(message.getString("productCredit"));
	    	 productService.setStatus(message.getInteger("status"));
	    	 productService.setReportMessageUrl(message.getString("reportMessageUrl"));
	    	 productService.setSpProductId(message.getString("spProductId"));
	    	 //子类查询
	    	 JSONObject productInfo = message.getJSONObject("productInfo");
	    	 productService.setCompanyCode(productInfo.getString("companyCode"));
	    	 productService.setCompanyName(productInfo.getString("companyName"));
	    	 productService.setProductName(productInfo.getString("productName"));
	    	 productService.setProductOrderId(productInfo.getString("productOrderId"));
	     }
	     return productService;
	}
	
	public static List<SysParameters> getSysConfig() {
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
	     List<SysParameters> sysParamsList = new ArrayList<SysParameters>();
	     if("OK".equals(statusCode)){
	    	 JSONArray bodyArray = jsonObject.getJSONArray("body");
			 for(int i = 0; i < bodyArray.size(); i++){
				 SysParameters sysParameters = new SysParameters();
				 JSONObject message = bodyArray.getJSONObject(i);
				 sysParameters.setName(message.getString("applicationServerCode") + message.getString("code"));
				 if(message.getString("value") == null || message.getString("value").equals("")){
					 sysParameters.setValue(message.getString("defaultValue"));
				 }else{
					 sysParameters.setValue(message.getString("value"));
				 }
				 sysParameters.setDescription(message.getString("remark"));
				 sysParamsList.add(sysParameters);
			 }
	     }
	     return sysParamsList;
	}
	
	public static String getServiceIDbyProductid(String spProductid) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/getServiceIDbyProductid?spProductId=" + spProductid)
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
	     String productServiceId = null;
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 productServiceId = message.getString("productServiceId");
	     }
	     return productServiceId;
	}
	
	public static String getServuniqueIdbySpproductid(String spProductid) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/getServiceIDbyProductid?spProductId=" + spProductid)
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
	     String productInfoId = null;
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 productInfoId = message.getString("productInfoId");
	     }
	     return productInfoId;
	}
	
	public static String getSpReportUrlByServiceCode(String spProductid) {
		 RequestBody body = new FormBody.Builder().build();
	     Request request = new Request.Builder()
	             .url(apiUrl + "/corbiz/getSpReportUrlByServiceCode?spProductId=" + spProductid)
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
	     String value = null;
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 value = message.getString("spOrderNotifyUrl");
	     }
	     return value;
	}
	
	public static List<UserBlackWhiteList> getBlackList() {
		RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder()
				.url(apiUrl + "/corbiz/blackList")
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
		List<UserBlackWhiteList> userList = new ArrayList<UserBlackWhiteList>();
		if("OK".equals(statusCode)){
			JSONArray bodyArray = jsonObject.getJSONArray("body");
			for(int i = 0; i < bodyArray.size(); i++){
				UserBlackWhiteList userBlackWhite = new UserBlackWhiteList();
				JSONObject message = bodyArray.getJSONObject(i);
				userBlackWhite.setCellphoneNumber(message.getString("blackWhiteList"));
				userBlackWhite.setDescription(message.getString("remark"));
				userBlackWhite.setType(message.getInteger("numberType"));
//				 userBlackWhite.setProductId(message.getString("remark"));
				userList.add(userBlackWhite);
			}
		}
		return userList;
	}
	public static List<Vasp> getSpInfo() {
		RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder()
				.url(apiUrl + "/corbiz/spInfo")
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
		List<Vasp> userList = new ArrayList<Vasp>();
		if("OK".equals(statusCode)){
			JSONArray bodyArray = jsonObject.getJSONArray("body");
			for(int i = 0; i < bodyArray.size(); i++){
				Vasp userBlackWhite = new Vasp();
				JSONObject message = bodyArray.getJSONObject(i);
				userBlackWhite.setBusinessphone(message.getString("businessTel"));
				userBlackWhite.setContactperson(message.getString("contactPerson"));
				userBlackWhite.setCreatedate(message.getString("createTime"));
				userBlackWhite.setEmailaddress(message.getString("emailAddr"));
				userBlackWhite.setFax(message.getString("faxNo"));
				userBlackWhite.setOfficeaddress(message.getString("officeAddr"));
				userBlackWhite.setProvince(message.getString("province"));
				userBlackWhite.setStatus(message.getInteger("status").toString());
				userBlackWhite.setUniqueid(Integer.parseInt(message.getString("spInfoId")));
				userBlackWhite.setUpdatedate(message.getString("updateTime"));
				userBlackWhite.setVaspdesc(message.getString("remark"));
				userBlackWhite.setVaspid(message.getString("companyCode"));
				userBlackWhite.setVaspname(message.getString("companyName"));
				userBlackWhite.setWebaddress(message.getString("webAddr"));
				JSONObject spAccess=message.getJSONObject("spAccess");
				VaspReserveInfo vaspReserveInfo = new VaspReserveInfo();
				vaspReserveInfo.setEffDate(spAccess.getString("effTime"));
				vaspReserveInfo.setExpdate(spAccess.getString("expTime"));
				vaspReserveInfo.setIsTrust(Integer.parseInt(spAccess.getString("isTrust")));
				vaspReserveInfo.setOrderKey(spAccess.getString("orderKey"));
				//vaspReserveInfo.setRecordSequenceId(message.getString("effTime"));
				vaspReserveInfo.setReportmessageurl(spAccess.getString("reportMessageUrl"));
				//vaspReserveInfo.setReserve1(message.getString("orderKey"));
				vaspReserveInfo.setSpCity(spAccess.getString("spCity"));
				//vaspReserveInfo.setSpExtendNum(message.getString("orderKey"));
				//vaspReserveInfo.setSpid(message.getString("orderKey"));
				vaspReserveInfo.setSpOrderUrl(spAccess.getString("spOrderNotifyUrl"));
				//vaspReserveInfo.setSpPsedoFlag(message.getString("orderKey"));
				vaspReserveInfo.setSynOrderFunc(Integer.parseInt(spAccess.getString("synOrderFunc")));
				
				userBlackWhite.setVaspReserveInfo(vaspReserveInfo);
				userList.add(userBlackWhite);
			}
		}
		return userList;
	}
	public static List<Vasservice> getNormalStatus() {
		RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder()
				.url(apiUrl + "/corbiz/productService")
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
		List<Vasservice> vasservice = new ArrayList<Vasservice>();
		if("OK".equals(statusCode)){
			JSONArray bodyArray = jsonObject.getJSONArray("body");
			for(int i = 0; i < bodyArray.size(); i++){
				Vasservice productService = new Vasservice();
				JSONObject messages = bodyArray.getJSONObject(i);
				 JSONObject message = messages.getJSONObject("productInfo");
				 productService.setApprover(message.getString("auditUser"));
		    	 productService.setAuditTimeInterval(message.getString("auditTimeinterval"));
		    	 productService.setBookManName(message.getString("bookmanName"));
		    	 productService.setComposeTimeInterval(message.getString("composeTimeinterval"));
		    	 productService.setCreatedate(message.getDate("createTime")+"");
		    	 productService.setDealStatus(message.getInteger("dealStatus"));
		    	 productService.setDefaultflag(message.getString("isDefault"));
		    	 productService.setFeedesc(message.getString("feeRemark"));
		    	 productService.setFeetype(message.getString("orderType"));
		    	 productService.setIsPackage(message.getString("isPackage"));
		    	 productService.setMtcontentmode(message.getString("sendContentMode"));
		    	 productService.setMtmode(message.getString("sendMode"));
		    	 productService.setOndemandcode(message.getString("onDemandCommand"));
		    	 productService.setOndemanddesc(message.getString("onDemandRemark"));
		    	 productService.setOndemandfee(message.getDouble("onDemandFee"));
		    	 productService.setOpinion(message.getString("auditRemark"));
		    	 productService.setOrdercode(message.getString("orderCommand"));
		    	 productService.setOrderfee(message.getDouble("orderFee"));
		    	 productService.setPriority(message.getInteger("priority"));
		    	 productService.setProAuditTimeInterval(message.getString("proAuditTimeinterval"));
		    	 productService.setProductdesc(message.getString("productRemark"));
		    	 productService.setProducttype(message.getString("productType"));
		    	 productService.setProofTimeInterval(message.getString("proofTimeinterval"));
		    	 productService.setProorderid(message.getString("productOnDemandId"));
		    	 productService.setRunstatus(message.getString("runStatus"));
		    	 productService.setSeparateScale(message.getString("cooperationProportion"));
		    	 productService.setServicecode(message.getString("productOrderId"));
		    	 productService.setServicedesc(message.getString("remark"));
		    	 productService.setServicename(message.getString("productName"));
		    	 productService.setServicetype(message.getString("productType"));
		    	 productService.setSmallpic(message.getString("wapPicUrl"));
		    	 productService.setSmskey(message.getString("keyWorld"));
		    	 productService.setSource(message.getInteger("productSource"));
		    	 productService.setStatus(message.getString("status"));
		    	 productService.setTreeid(message.getString("classification"));
		    	 productService.setUniqueid(message.getInteger("productInfoId"));
		    	 productService.setUpdatedate(message.getDate("updateTime").toString());
		    	 productService.setVasid(message.getString("cpAccessId"));
		    	 productService.setVaspid(message.getString("companyCode"));
		    	 productService.setViewpic(message.getString("webPicUrl"));
		    	 VasserviceReserveInfo productInfos=new VasserviceReserveInfo();
		    	 productInfos.setConfirm(messages.getInteger("isConfirm"));
		    	 vasservice.add(productService);
			}
		}
		return vasservice;
	}
	public static String getSpIdByVasId(String vasId) {
		RequestBody body = new FormBody.Builder().build();
		Request request = new Request.Builder()
				.url(apiUrl + "/corbiz/productInfo?vasId="+vasId)
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
	     String vaspid = null;
	     String statusCode=jsonObject.getString("statusCode");
	     if("OK".equals(statusCode)){
	    	 JSONObject message = jsonObject.getJSONObject("body");
	    	 vaspid = message.getString("companyCode");
	     }
	     return vaspid;
	}

}
