package com.zbensoft.mmsmp.api.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.CommonLogImpl;
import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.IDGenerate;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.api.service.api.ContentInfoService;
import com.zbensoft.mmsmp.api.service.api.CooperKeyService;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.api.service.api.ProxypaymessageService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.SystemConfigService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.api.service.api.UserOrderPayService;
import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.domain.ContentInfo;
import com.zbensoft.mmsmp.db.domain.CooperKey;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.Proxypaymessage;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.SystemConfig;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;
import com.zbensoft.mmsmp.db.domain.UserOrderPay;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/ownbiz")
@RestController
public class OwnbizController {
	@Autowired
	BlackWhiteListService  blackWhiteListService;
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	ContentInfoService contentInfoService;
	@Autowired
	SystemConfigService  systemConfigService;
	@Autowired
	SpInfoService  spInfoService;
	@Autowired
	ProductInfoService  productInfoService;
	@Autowired
	ProductServiceService  productServiceService;
	@Autowired
	UserOrderHisService  userOrderHisService;
	@Autowired
	UserOrderPayService userOrderPayService;
	@Autowired
	ProxypaymessageService  proxypaymessageService;
	@Autowired
	CooperKeyService  cooperKeyService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@ApiOperation(value = "Query WhiteList, support paging", notes = "")
	@RequestMapping(value = "/whiteList", method = RequestMethod.GET)
	public ResponseRestEntity<List<BlackWhiteList>> selectWhiteList(@RequestParam(required = false) String blackWhiteList, @RequestParam(required = false) Integer blackWhiteListType,@RequestParam(required = false) Integer numberType, 
			@RequestParam(required = false) String remark,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		BlackWhiteList currentBlackWhiteList = new BlackWhiteList();
		currentBlackWhiteList.setBlackWhiteListType(MessageDef.BLACK_WHITE_LIST_TYPE.WHITE);
		List<BlackWhiteList> list = blackWhiteListService.selectPage(currentBlackWhiteList);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = blackWhiteListService.selectPage(currentBlackWhiteList);

		} else {
			list = blackWhiteListService.selectPage(currentBlackWhiteList);
		}

		int count = blackWhiteListService.count(currentBlackWhiteList);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<BlackWhiteList>>(new ArrayList<BlackWhiteList>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<BlackWhiteList>>(list, HttpRestStatus.OK, count, count);
	}
	
	@ApiOperation(value = "Query UserOrder", notes = "")
	@RequestMapping(value = "/userOrderCheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public int selectUserOrderByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber,@RequestParam("spProductId") String spProductId) {
		UserOrder userOrder=new UserOrder();
		userOrder.setSpInfoId(spProductId);
		userOrder.setPhoneNumber(phoneNumber);
	    int count = userOrderService.selectOrderRelationBySpProductId(userOrder);
		return count;
	}
	
	@ApiOperation(value = "Add UserOrderHis", notes = "")
	@RequestMapping(value = "/userOrderHis", method = RequestMethod.GET)
	public ResponseRestEntity<Void> createUserOrderHis(@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String chargePhoneNumber,
			@RequestParam(required = false) String orderTime,@RequestParam(required = false) String orderRoute,@RequestParam(required = false) String fee,@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String serviceUniqueId,@RequestParam(required = false) Integer orderType) {
		UserOrderHis userOrderHis=new UserOrderHis();
		userOrderHis.setUserOrderHisId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_COUPON));
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setChargePhoneNumber(chargePhoneNumber);
		userOrderHis.setOrderTime(new Date());
		userOrderHis.setOrderRoute(Integer.parseInt(orderRoute));
		userOrderHis.setFee(Double.parseDouble(fee));
		userOrderHis.setProductInfoId(serviceUniqueId);
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setOrderType(orderType);
		userOrderHisService.insert(userOrderHis);
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	
	@ApiOperation(value = "Add UserOrder", notes = "")
	@RequestMapping(value = "/userOrder", method = RequestMethod.GET)
	public ResponseRestEntity<Void> createUserOrder(@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String chargePhoneNumber,
			@RequestParam(required = false) String orderTime,@RequestParam(required = false) String orderRoute,@RequestParam(required = false) String fee,@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String serviceUniqueId,@RequestParam(required = false) Integer orderType) {
		UserOrder userOrderHis=new UserOrder();
		userOrderHis.setUserOrderId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setProductInfoId(serviceUniqueId);
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setChargePhoneNumber(chargePhoneNumber);
		userOrderHis.setOrderTime(new Date());
		userOrderHis.setOrderRoute(Integer.parseInt(orderRoute));
		userOrderHis.setFee(Double.parseDouble(fee));
		userOrderHis.setOrderType(orderType);
		userOrderService.insert(userOrderHis);
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	@ApiOperation(value = "Delete UserOrder", notes = "")
	@RequestMapping(value = "/userOrderDelete/{phoneNumber}/{productInfoId}", method = RequestMethod.DELETE)
	public void userOrderDelete(@PathVariable("phoneNumber") String phoneNumber,@PathVariable("productInfoId") String productInfoId) {
		UserOrder userOrderHis=new UserOrder();
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setProductInfoId(productInfoId);
		userOrderService.delUserOrder(userOrderHis);
	}
	@ApiOperation(value = "Query contentInfo, support paging", notes = "")
	@RequestMapping(value = "/contentInfo", method = RequestMethod.GET)
	public ResponseRestEntity<List<ContentInfo>> selectWhiteList(@RequestParam(required = false) String contentType, @RequestParam(required = false) String status,@RequestParam(required = false) String isSend) {

		ContentInfo contentInfo = new ContentInfo();
		if(contentType!=null){
			contentInfo.setContentType(Integer.parseInt(contentType));
		}
		if(status!=null){
			contentInfo.setContentType(Integer.parseInt(status));
		}
		if(isSend!=null){
			contentInfo.setContentType(Integer.parseInt(isSend));
		}
		
		List<ContentInfo> list = contentInfoService.selectPage(contentInfo);

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ContentInfo>>(new ArrayList<ContentInfo>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<ContentInfo>>(list, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Delete contentInfo", notes = "")
	@RequestMapping(value = "/contentInfoDelete/{contentInfoId}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ContentInfo> delete(@PathVariable("contentInfoId") String contentInfoId) {

		ContentInfo contentInfo = contentInfoService.selectByPrimaryKey(contentInfoId);
		if (contentInfo == null) {
			return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NOT_FOUND);
		}

		contentInfoService.deleteByPrimaryKey(contentInfoId);
		return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NO_CONTENT);
	}
	@ApiOperation(value = "Query WhiteList, support paging", notes = "")
	@RequestMapping(value = "/getSystemParamBykey", method = RequestMethod.GET)
	public ResponseRestEntity<SystemConfig> selectWhiteList(@RequestParam(required = false) String code) {
		SystemConfig systemConfig = systemConfigService.selectBycode(code);
		if (systemConfig == null) {
			return new ResponseRestEntity<SystemConfig>(new SystemConfig(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<SystemConfig>(systemConfig, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query spInfo support paging", notes = "")
	@RequestMapping(value = "/getAllVaspEnitiy", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpInfo>> selectWhiteList() {
		List<SpInfo> list = spInfoService.selectAll();
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SpInfo>>(new ArrayList<SpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<SpInfo>>(list, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query productInfo support paging", notes = "")
	@RequestMapping(value = "/getAllVasServiceRelation", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductInfo>> selectProductInfo() {
		List<ProductInfo> list = productInfoService.getAllVasServiceRelation();
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductInfo>>(new ArrayList<ProductInfo>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ProductInfo>>(list, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getVasServiceRelation", method = RequestMethod.GET)
	public ResponseRestEntity<ProductService> selectSpProductId1(@RequestParam(required = false) String spProductId) {
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		ProductService p = productServiceService.getVasServiceRelation(productService);
		if (p == null) {
			return new ResponseRestEntity<ProductService>(new ProductService(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductService>(p, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/queryContentCount", method = RequestMethod.GET)
	public int getCount(@RequestParam(required = false) String spProductId) {
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		int count = productServiceService.queryContentCount(productService);
	    return count;
	}
	
	@ApiOperation(value = "Edit userOrderHis", notes = "")
	@RequestMapping(value = "/updateNotifySpStatus", method = RequestMethod.GET)
	public ResponseRestEntity<UserOrderHis> updateCpInfo(@RequestParam(required = false) String productInfoId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) Integer status) {

		UserOrderHis userOrderHis = new UserOrderHis();
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setProductInfoId(productInfoId);
		UserOrderHis currentUserOrderHis = userOrderHisService.selectByPhoneAndProductId(userOrderHis);
		if (currentUserOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentUserOrderHis.setStatus(status);
		userOrderHisService.updateByPrimaryKey(currentUserOrderHis);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderHis,CommonLogImpl.CP_INFO);
		return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis, HttpRestStatus.OK,localeMessageSourceService.getMessage("cpInfo.update.ok.message"));
	}
	
	@ApiOperation(value = "Query userOrderPay，Support paging", notes = "")
	@RequestMapping(value = "/proxypaymessage", method = RequestMethod.GET)
	public void proxypaymessage(@RequestParam(required = false) String globalmessageid,
			@RequestParam(required = false) String phonenumber,@RequestParam(required = false) String chargeparty,
			@RequestParam(required = false) String cpid,@RequestParam(required = false) Integer serviceid,
			@RequestParam(required = false) String servicename,@RequestParam(required = false) String feetype,
			@RequestParam(required = false) Double fee,@RequestParam(required = false) String accountid,
			@RequestParam(required = false) String validatecode,@RequestParam(required = false) String spid,
			@RequestParam(required = false) String usertype,@RequestParam(required = false) String perorid,
			@RequestParam(required = false) String productname,@RequestParam(required = false) String productid,
			@RequestParam(required = false) String createdate,@RequestParam(required = false) String updatedate,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer sourcetype) {
		Proxypaymessage proxypaymessage = new Proxypaymessage();
		proxypaymessage.setGlobalmessageid(globalmessageid);
		proxypaymessage.setPhonenumber(phonenumber);
		proxypaymessage.setChargeparty(chargeparty);
		proxypaymessage.setCpid(cpid);
		proxypaymessage.setServiceid(serviceid+"");
		proxypaymessage.setServicename(servicename);
		proxypaymessage.setFeetype(feetype);
		proxypaymessage.setFee(fee+"");
		proxypaymessage.setAccountid(accountid);
		proxypaymessage.setValidatecode(validatecode);
		proxypaymessage.setSpid(spid);
		proxypaymessage.setUsertype(usertype);
		proxypaymessage.setPerorid(perorid);
		proxypaymessage.setProductname(productname);
		proxypaymessage.setProductid(productid);
		proxypaymessage.setProductid(productid);
		proxypaymessage.setCreatedate(createdate);
		proxypaymessage.setUpdatedate(updatedate);
		proxypaymessage.setStatus(status+"");
		proxypaymessage.setSourceType(sourcetype+"");
		proxypaymessageService.insert(proxypaymessage);
	}
	@ApiOperation(value = "Query userOrderPay，Support paging", notes = "")
	@RequestMapping(value = "/userOrderPay", method = RequestMethod.GET)
	public void userOrderPaySelect(@RequestParam(required = false) String userOrderPayId,
			@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String orderTime,
			@RequestParam(required = false) Double fee,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) String spInfoId,@RequestParam(required = false) Integer orderType) {
		UserOrderPay userOrderPay = new UserOrderPay();
		userOrderPay.setUserOrderPayId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_COUPON));
		userOrderPay.setPhoneNumber(phoneNumber);
		userOrderPay.setOrderTime(new Date());
		userOrderPay.setFee(fee);
		userOrderPay.setProductInfoId(productInfoId);
		userOrderPay.setStatus(status);
		userOrderPay.setSpInfoId(spInfoId);
		userOrderPay.setOrderType(orderType);
		
		//插入前进行判断。如果该手机号码已存在则不进行插入 start
		UserOrderPay userOrderPayTmp=userOrderPayService.selectByPhoneNumberAndProductInfoId(userOrderPay);
		if(userOrderPayTmp==null){//需要插入
			userOrderPayService.insert(userOrderPay);
		}else{//已存在，不需要插入
			
		}
		//插入前进行判断。如果该手机号码已存在则不进行插入 end
	}
	
	@ApiOperation(value = "Delete UserOrderPay", notes = "")
	@RequestMapping(value = "/userOrderPay/{phoneNumber}/{productInfoId}", method = RequestMethod.DELETE)
	public void userOrderPay(@PathVariable("phoneNumber") String phoneNumber,@PathVariable("productInfoId") String productInfoId) {
		UserOrderPay userOrderPay=new UserOrderPay();
		userOrderPay.setPhoneNumber(phoneNumber);
		userOrderPay.setProductInfoId(productInfoId);
		userOrderPayService.deleteByPnPi(userOrderPay);
	}
	@ApiOperation(value = "Query ProductInfo By cpAccessId，Support paging", notes = "")
	@RequestMapping(value = "/cooperkey", method = RequestMethod.GET)
	public ResponseRestEntity<CooperKey> cooperkey(@RequestParam(required = false) String cooperId) {
		CooperKey p = new CooperKey();
		p.setCooperId(cooperId);
		List<CooperKey> list = cooperKeyService.selectPage(p);
		if (list == null) {
			return new ResponseRestEntity<CooperKey>(new CooperKey(), HttpRestStatus.NOT_FOUND);
		}
		CooperKey cooperKey = new CooperKey();
		cooperKey.setAppName(list.get(0).getAppName());
		cooperKey.setCooperId(list.get(0).getCooperId());
		cooperKey.setCooperKey(list.get(0).getCooperKey());
		cooperKey.setCooperName(list.get(0).getCooperName());
		cooperKey.setIps(list.get(0).getIps());
		cooperKey.setKeyId(list.get(0).getKeyId());
		cooperKey.setNotifyUrl(list.get(0).getNotifyUrl());
		cooperKey.setRemark(list.get(0).getRemark());
		cooperKey.setServiceTel(list.get(0).getServiceTel());
		return new ResponseRestEntity<CooperKey>(cooperKey, HttpRestStatus.OK);
	}
	@ApiOperation(value = "Query ProductInfo By cpAccessId，Support paging", notes = "")
	@RequestMapping(value = "/selectCooperkey", method = RequestMethod.GET)
	public ResponseRestEntity<List<CooperKey>> selectCooperkey() {
		CooperKey p = new CooperKey();
		List<CooperKey> list = cooperKeyService.selectPage(p);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<CooperKey>>(new ArrayList<CooperKey>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<CooperKey>>(list, HttpRestStatus.OK);
	}
	@ApiOperation(value = "Edit userOrderHis", notes = "")
	@RequestMapping(value = "/updateProxyPayMessage", method = RequestMethod.GET)
	public ResponseRestEntity<Proxypaymessage> updateProxyPayMessage(@RequestParam(required = false) String globalMessageid,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String cpID,@RequestParam(required = false) String serviceId,@RequestParam(required = false) String serviceName,
			@RequestParam(required = false) String feeType,@RequestParam(required = false) Double fee,@RequestParam(required = false) String status,
			@RequestParam(required = false) String validateCode,@RequestParam(required = false) String accountId,@RequestParam(required = false) String spId,
			@RequestParam(required = false) String userType,@RequestParam(required = false) String perorid,@RequestParam(required = false) String productId,
			@RequestParam(required = false) String productName,@RequestParam(required = false) String chargeParty) {

		Proxypaymessage currentProxypaymessage = proxypaymessageService.selectByPrimaryKey(globalMessageid);
		if (currentProxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		if(!phoneNumber.equals("") && !phoneNumber.equals("null")){
			currentProxypaymessage.setPhonenumber(phoneNumber);
		}
		if(cpID != null && !cpID.equals("") && !cpID.equals("null")){
			currentProxypaymessage.setCpid(cpID);
		}
		if(serviceId != null && !serviceId.equals("") && !serviceId.equals("null")){
			currentProxypaymessage.setServiceid(serviceId);
		}
		if(serviceName != null && !serviceName.equals("") && !serviceName.equals("null")){
			currentProxypaymessage.setServicename(serviceName);
		}
		if(feeType != null && !serviceName.equals("") && !serviceName.equals("null")){
			currentProxypaymessage.setFeetype(feeType.toString());
		}
		if(fee != 0){
			currentProxypaymessage.setFee(fee.toString());
		}
		if(status != null && !serviceName.equals("") && !serviceName.equals("null")){
			currentProxypaymessage.setStatus(status.toString());
		}
		if(validateCode != null && !validateCode.equals("") && !validateCode.equals("null")){
			currentProxypaymessage.setValidatecode(validateCode);
		}
		if(accountId != null && !accountId.equals("") && !accountId.equals("null")){
			currentProxypaymessage.setAccountid(accountId);
		}
		if(spId != null && !spId.equals("") && !spId.equals("null")){
			currentProxypaymessage.setSpid(spId);
		}
		if(userType != null && !serviceName.equals("") && !serviceName.equals("null")){
			currentProxypaymessage.setUsertype(userType.toString());
		}
		if(perorid != null && !perorid.equals("") && !perorid.equals("null")){
			currentProxypaymessage.setPerorid(perorid);
		}
		if(productId != null && !productId.equals("") && !productId.equals("null")){
			currentProxypaymessage.setProductid(productId);
		}
		if(productName != null && !productName.equals("") && !productName.equals("null")){
			currentProxypaymessage.setProductname(productName);
		}
		if(chargeParty != null && !chargeParty.equals("") && !chargeParty.equals("null")){
			currentProxypaymessage.setChargeparty(chargeParty);
		}
		
		proxypaymessageService.updateByPrimaryKeySelective(currentProxypaymessage);
		//修改日志
//		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentProxypaymessage,CommonLogImpl.CP_INFO);
		return new ResponseRestEntity<Proxypaymessage>(currentProxypaymessage, HttpRestStatus.OK,localeMessageSourceService.getMessage("ProxyPayMessage.update.ok.message"));
	}
	
	@ApiOperation(value = "Query Proxypaymessage By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getCooperKeyMessage", method = RequestMethod.GET)
	public ResponseRestEntity<Proxypaymessage> selectProxypaymessage(@RequestParam(required = false) String globalMessageid) {
		Proxypaymessage proxypaymessage = proxypaymessageService.getCooperKeyMessage(globalMessageid);
		if (proxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(new Proxypaymessage(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<Proxypaymessage>(proxypaymessage, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query Proxypaymessage By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getProxyPayMessage", method = RequestMethod.GET)
	public ResponseRestEntity<Proxypaymessage> getProxyPayMessage(@RequestParam(required = false) String globalMessageid,@RequestParam(required = false) String validateCode) {
		Proxypaymessage proxypaymessage = new Proxypaymessage();
		proxypaymessage.setGlobalmessageid(globalMessageid);
		proxypaymessage.setValidatecode(validateCode);
		Proxypaymessage currentProxypaymessage = proxypaymessageService.getProxyPayMessage(proxypaymessage);
		if (currentProxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<Proxypaymessage>(currentProxypaymessage, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query Proxypaymessage By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getProxyPayMessageById", method = RequestMethod.GET)
	public ResponseRestEntity<Proxypaymessage> getProxyPayMessageById(@RequestParam(required = false) String globalMessageid) {
		Proxypaymessage proxypaymessage = proxypaymessageService.getProxyPayMessageById(globalMessageid);
		if (proxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(new Proxypaymessage(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<Proxypaymessage>(proxypaymessage, HttpRestStatus.OK);
	}
}
