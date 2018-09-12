package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.CommonFun;
import com.zbensoft.mmsmp.api.common.CommonLogImpl;
import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.HttpRestStatusFactory;
import com.zbensoft.mmsmp.api.common.IDGenerate;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.api.service.api.CpInfoService;
import com.zbensoft.mmsmp.api.service.api.MobileSegmentService;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.SpUrlService;
import com.zbensoft.mmsmp.api.service.api.SystemConfigService;
import com.zbensoft.mmsmp.api.service.api.UserInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.api.service.api.UserRecvService;
import com.zbensoft.mmsmp.api.service.api.UserServiceSendService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.domain.CpInfo;
import com.zbensoft.mmsmp.db.domain.MobileSegment;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.SpUrl;
import com.zbensoft.mmsmp.db.domain.SystemConfig;
import com.zbensoft.mmsmp.db.domain.UserInfo;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserRecv;
import com.zbensoft.mmsmp.db.domain.UserServiceSend;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/corbiz")
@RestController
public class CorbizController {
	@Autowired
	MobileSegmentService mobileSegmentService;
	@Autowired
	BlackWhiteListService  blackWhiteListService;
	@Autowired
	CpInfoService cpInfoService;
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	ProductServiceService productServiceService;
	@Autowired
	SpAccessService spAccessService;
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	SpUrlService spUrlService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	UserRecvService userRecvService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	UserOrderHisService userOrderHisService;
	@Autowired
	UserServiceSendService userServiceSendService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
//	@PreAuthorize("hasRole('R_BWL_Q')")
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
	
	@ApiOperation(value = "Query BlackList, support paging", notes = "")
	@RequestMapping(value = "/blackList", method = RequestMethod.GET)
	public ResponseRestEntity<List<BlackWhiteList>> selectBlackList(@RequestParam(required = false) String blackWhiteList, @RequestParam(required = false) Integer blackWhiteListType,@RequestParam(required = false) Integer numberType, 
			@RequestParam(required = false) String remark,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		BlackWhiteList currentBlackWhiteList = new BlackWhiteList();
		currentBlackWhiteList.setBlackWhiteListType(MessageDef.BLACK_WHITE_LIST_TYPE.BLACK);
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
	
//	@PreAuthorize("hasRole('R_CPIM_Q')")
	@ApiOperation(value = "Query CpInfo，Support paging", notes = "")
	@RequestMapping(value = "/cpInfo", method = RequestMethod.GET)
	public ResponseRestEntity<List<CpInfo>> selectPage(@RequestParam(required = false) String cpInfoId,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String cpName,@RequestParam(required = false) String cpAddr,@RequestParam(required = false) String legalPerson,
			@RequestParam(required = false) String bankName,@RequestParam(required = false) String webAddr,@RequestParam(required = false) String bankAccount,@RequestParam(required = false) String faxNo,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		CpInfo cpInfo = new CpInfo();
		cpInfo.setCpInfoId(cpInfoId);
		cpInfo.setSpInfoId(spInfoId);
		cpInfo.setCpName(cpName);
		int count = cpInfoService.count(cpInfo);
		if (count == 0) {
			return new ResponseRestEntity<List<CpInfo>>(new ArrayList<CpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		List<CpInfo> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = cpInfoService.selectPage(cpInfo);

		} else {
			list = cpInfoService.selectPage(cpInfo);
		}
		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<CpInfo>>(new ArrayList<CpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<CpInfo>>(list, HttpRestStatus.OK, count, count);
	}
	
//	@PreAuthorize("hasRole('R_UO_Q')")
	@ApiOperation(value = "Query UserOrder，Support paging", notes = "")
	@RequestMapping(value = "/userOrder", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrder>> selectUserOrder(
			@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String chargePhoneNumber,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer priority,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		UserOrder userOrder = new UserOrder();
		userOrder.setPhoneNumber(phoneNumber);
		userOrder.setProductInfoId(productInfoId);
		if (status != null) {
			userOrder.setStatus(status);
		}
		if (priority != null) {
			userOrder.setPriority(priority);
		}
		List<UserOrder> list = new ArrayList<UserOrder>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userOrderService.selectPage(userOrder);

		} else {
			list = userOrderService.selectPage(userOrder);
		}

		int count = userOrderService.count(userOrder);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserOrder>>(new ArrayList<UserOrder>(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<List<UserOrder>>(list, HttpRestStatus.OK,count,count);
	}
	//getSpurlByVaspid(spinfoId)
	@ApiOperation(value = "Query ProductService By spInfoId，Support paging", notes = "")
	@RequestMapping(value = "/getSpurlByVaspid", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpAccess>> selectProductService(@RequestParam(required = false) String spInfoId,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		SpAccess spAccess = new SpAccess();
		spAccess.setSpInfoId(spInfoId);
		List<SpAccess> list = new ArrayList<SpAccess>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = spAccessService.selectPage(spAccess);

		} else {
			list = spAccessService.selectPage(spAccess);
		}

		int count = spAccessService.count(spAccess);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SpAccess>>(new ArrayList<SpAccess>(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<List<SpAccess>>(list, HttpRestStatus.OK,count,count);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/requestacc", method = RequestMethod.GET)
	public ResponseRestEntity<ProductService> selectPage(@RequestParam(required = false) String onDemandAccess) {
		ProductService productService = new ProductService();
		productService.setOnDemandAccess(onDemandAccess);
		ProductService p = productServiceService.getProductServiceByOnDemandAccess(productService);
		if (p == null) {
			return new ResponseRestEntity<ProductService>(new ProductService(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductService>(p, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query systemConfigMapper，Support paging", notes = "")
	@RequestMapping(value = "/getSysConfig", method = RequestMethod.GET)
	public ResponseRestEntity<List<SystemConfig>> selectPage(@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		SystemConfig systemConfig = new SystemConfig();
		List<SystemConfig> list = new ArrayList<SystemConfig>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = systemConfigService.selectPage(systemConfig);

		} else {
			list = systemConfigService.selectPage(systemConfig);
		}

		int count = systemConfigService.count(systemConfig);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SystemConfig>>(new ArrayList<SystemConfig>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<SystemConfig>>(list, HttpRestStatus.OK, count, count);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getServiceIDbyProductid", method = RequestMethod.GET)
	public ResponseRestEntity<ProductService> selectSpProductId1(@RequestParam(required = false) String spProductId) {
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		ProductService p = productServiceService.getServiceIDbyProductid(productService);
		if (p == null) {
			return new ResponseRestEntity<ProductService>(new ProductService(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductService>(p, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query SpAccess By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getSpReportUrlByServiceCode", method = RequestMethod.GET)
	public ResponseRestEntity<SpAccess> selectSpProductId2(@RequestParam(required = false) String spProductId) {
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		ProductService p = productServiceService.getServiceIDbyProductid(productService);
		SpInfo spInfo = new SpInfo();
		if(p != null){
			spInfo.setCompanyCode(p.getProductInfo().getCompanyCode());
		}
		//企业代码唯一，只有一条数据
		List<SpInfo> spInfoList = new ArrayList();
		if(spInfo != null){
			spInfoList = spInfoService.selectPage(spInfo);
		}
		SpAccess spAccess = new SpAccess();
		if(spInfoList != null || spInfoList.isEmpty()){
			spAccess = spAccessService.selectByPrimaryKey(spInfoList.get(0).getSpInfoId());
		}
		if (spAccess == null) {
			return new ResponseRestEntity<SpAccess>(new SpAccess(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<SpAccess>(spAccess, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query SpInfo, support paging", notes = "")
	@RequestMapping(value = "/spInfo", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpInfo>> selectSpInfo(@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		
		SpInfo spInfo = new SpInfo();
		List<SpInfo> list = spInfoService.getSpInfo(spInfo);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SpInfo>>(new ArrayList<SpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<SpInfo>>(list, HttpRestStatus.OK);
	}
	@ApiOperation(value = "Query ProductInfo, support paging", notes = "")
	@RequestMapping(value = "/productService", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductService>> selectProductInfo(@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		
		ProductService productInfo = new ProductService();
		List<ProductService> list = productServiceService.getProductInfo(productInfo);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductService>>(new ArrayList<ProductService>(), HttpRestStatus.NOT_FOUND);
		}
		
		return new ResponseRestEntity<List<ProductService>>(list, HttpRestStatus.OK);
	}
	@ApiOperation(value = "Query ProductInfo, support paging", notes = "")
	@RequestMapping(value = "/productInfo", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductInfo>> getProductInfo(@RequestParam(required = false) String vasId,@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setCpAccessId(vasId);
		List<ProductInfo> list = productInfoService.selectPage(productInfo);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductInfo>>(new ArrayList<ProductInfo>(), HttpRestStatus.NOT_FOUND);
		}
		
		return new ResponseRestEntity<List<ProductInfo>>(list, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getConfirmmsgByProductid", method = RequestMethod.GET)
	public ResponseRestEntity<ProductService> selectSpProductId3(@RequestParam(required = false) String spProductId) {
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		ProductService p = productServiceService.getServiceIDbyProductid(productService);
		if (p == null) {
			return new ResponseRestEntity<ProductService>(new ProductService(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductService>(p, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getSpProductId", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductService>> getSpProductId(@RequestParam(required = false) String cpAccessId) {
		List<ProductService> list =  productServiceService.getSpProductId(cpAccessId);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductService>>(new ArrayList<ProductService>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ProductService>>(list, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Delete UserOrder", notes = "")
	@RequestMapping(value = "/delUserOrder/{sendAddress}/{productInfoId}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrder> delete(@PathVariable("sendAddress") String sendAddress,@PathVariable("productInfoId") String productInfoId) {
		UserOrder userOrder=new UserOrder();
		userOrder.setPhoneNumber(sendAddress);
		userOrder.setProductInfoId(productInfoId);
		List<UserOrder> list = userOrderService.selectPage(userOrder);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND);
		}
		userOrderService.delUserOrder(userOrder);
		return new ResponseRestEntity<UserOrder>(HttpRestStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Query UserOrder", notes = "")
	@RequestMapping(value = "/selectUserOrder/{phoneNumber}/{spProductId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserOrder> selectByPrimaryKey(@PathVariable("phoneNumber") String phoneNumber,@PathVariable("spProductId") String spProductId) {
		UserOrder userOrder=new UserOrder();
		userOrder.setSpInfoId(spProductId);
		userOrder.setPhoneNumber(phoneNumber);
		UserOrder userOrderNew = userOrderService.selectOrderRelation(userOrder);
		if (userOrderNew == null ) {
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<UserOrder>(userOrderNew, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Add UserRecv", notes = "")
	@RequestMapping(value = "/userRecv", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserRecv(@Valid @RequestBody String messageContent, BindingResult result, UriComponentsBuilder ucBuilder) {
		UserRecv userRecv = new UserRecv();
		String[] s = messageContent.split("&");
		userRecv.setUserRecvId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		userRecv.setMessageContent((s[0].split("="))[1]);
		userRecv.setPhoneNumber((s[1].split("="))[1]);
		userRecv.setIsOrder(1);
		userRecvService.insert(userRecv);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userRecv,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/userRecv/{id}").buildAndExpand(userRecv.getUserRecvId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getLatestMoOrderMsgText", method = RequestMethod.GET)
	public ResponseRestEntity<UserRecv> getLatestMoOrderMsgText(@RequestParam(required = false) String phoneNumber) {
		UserRecv userRecv = new UserRecv();
		userRecv.setPhoneNumber(phoneNumber);
		
		UserRecv userRecvTmp = userRecvService.getLatestMoOrderMsgText(userRecv);
		if(userRecvTmp!=null){
			userRecv.setMessageContent(userRecvTmp.getMessageContent());
			return new ResponseRestEntity<UserRecv>(userRecv, HttpRestStatus.OK);
		}else{
			return new ResponseRestEntity<UserRecv>(new UserRecv(), HttpRestStatus.NOT_FOUND);
		}
	    
	}
	
	@ApiOperation(value = "Query ProductService By onDemandAccess，Support paging", notes = "")
	@RequestMapping(value = "/getServiceNameByAcc", method = RequestMethod.GET)
	public ResponseRestEntity<ProductInfo> selectCompany(@RequestParam(required = false) String cpAccessId) {
		//接入号此处做产品编号
		ProductInfo productInfo = productInfoService.selectByPrimaryKey(cpAccessId);
		if (productInfo == null) {
			return new ResponseRestEntity<ProductInfo>(new ProductInfo(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductInfo>(productInfo, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query ProductInfo By cpAccessId，Support paging", notes = "")
	@RequestMapping(value = "/getVasSpCpInfo", method = RequestMethod.GET)
	public ResponseRestEntity<ProductInfo> selectProductInfo(@RequestParam(required = false) String cpAccessId) {
		ProductInfo p = new ProductInfo();
		p.setCpAccessId(cpAccessId);
		//接入号此处做产品编号
//		ProductService productService = productServiceService.getVasSpCpInfo(cpAccessId);
		ProductInfo productInfo = productInfoService.getVasSpCpInfo(p);
		if (productInfo == null) {
			return new ResponseRestEntity<ProductInfo>(new ProductInfo(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<ProductInfo>(productInfo, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Delete All UserOrder", notes = "")
	@RequestMapping(value = "/delAllOrderRelation/{phoneNumber}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrder> delete(@PathVariable("phoneNumber") String phoneNumber) {
		userOrderService.deleteByPhoneNumber(phoneNumber);
		return new ResponseRestEntity<UserOrder>(HttpRestStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Query UserInfo By phoneNumber", notes = "")
	@RequestMapping(value = "/getAreaCodeByUserPhone", method = RequestMethod.GET)
	public ResponseRestEntity<UserInfo> selectUserInfo(@RequestParam(required = false) String phoneNumber) {
		UserInfo userInfo = userInfoService.selectByPrimaryKey(phoneNumber);
		if (userInfo == null) {
			return new ResponseRestEntity<UserInfo>(new UserInfo(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<UserInfo>(userInfo, HttpRestStatus.OK);
	}
	
	
	@ApiOperation(value = "Add UserOrderHis", notes = "")
	@RequestMapping(value = "/saveDemandMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> saveDemandMessage(@Valid @RequestBody String messageContent,BindingResult result,UriComponentsBuilder ucBuilder) {
		JSONArray jsonArray = JSONArray.parseArray(messageContent);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		UserOrderHis userOrderHis=new UserOrderHis();
		userOrderHis.setUserOrderHisId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_COUPON));
		userOrderHis.setPhoneNumber(jsonObject.getString("sendAddress"));
		userOrderHis.setSpInfoId(jsonObject.getString("vaspId"));
		userOrderHis.setEffTime(new Date());
		userOrderHis.setOrderType(CommonFun.DIANBO_INT);
		
		//得到城市号
		String segment=jsonObject.getString("sendAddress").substring(0,7);
		List<MobileSegment> mobileSegmentLists=mobileSegmentService.getCityIdBySegment(segment);
		if(mobileSegmentLists!=null&&mobileSegmentLists.size()>0){
			userOrderHis.setArea(mobileSegmentLists.get(0).getCityId());
		}
		
		String serviceCode=jsonObject.getString("serviceCode");
		String spProductId=serviceCode.split("#")[2];
		ProductService productService = new ProductService();
		productService.setSpProductId(spProductId);
		ProductService productServiceTmp = productServiceService.getServiceIDbyProductid(productService);
		if(productServiceTmp!=null){
			userOrderHis.setProductInfoId(productServiceTmp.getProductInfoId());
		}
		
		userOrderHisService.insert(userOrderHis);
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	
	@ApiOperation(value = "Query UserInfo By phoneNumber", notes = "")
	@RequestMapping(value = "/getSmsSenderUrl", method = RequestMethod.GET)
	public ResponseRestEntity<SpUrl> getSmsSenderUrl(@RequestParam(required = false) String spInfoId) {
//		ProductService productService = productServiceService.selectByPrimaryKey(serviceCode);
//		SpUrl spUrl = new SpUrl();
//		List<SpUrl> url = new ArrayList<SpUrl>();
//		if(productService!=null){
//			List<SpInfo> spInfoList = spInfoService.getSpInfoByCompanyCode(productService.getProductInfoId());
//			if(spInfoList !=null && !spInfoList.isEmpty()){
//				spUrl.setSpInfoId(spInfoList.get(0).getSpInfoId());
//				url = spUrlService.selectPage(spUrl);
//			}
//		}
//		if(url!=null && !url.isEmpty()){
//			if (url.get(0) == null) {
//				return new ResponseRestEntity<SpUrl>(new SpUrl(), HttpRestStatus.NOT_FOUND);
//			}
//		}
//	    return new ResponseRestEntity<SpUrl>(url.get(0), HttpRestStatus.OK);
		
		List<SpUrl> url = new ArrayList<SpUrl>();
		SpUrl spUrl = new SpUrl();
		spUrl.setSpInfoId(spInfoId);
		
		url = spUrlService.selectPage(spUrl);
		if (url != null && !url.isEmpty()) {
			if (url.get(0) == null) {
				return new ResponseRestEntity<SpUrl>(new SpUrl(), HttpRestStatus.NOT_FOUND);
			}
		}
		return new ResponseRestEntity<SpUrl>(url.get(0), HttpRestStatus.OK);
	}
	@ApiOperation(value = "Query UserInfo By phoneNumber", notes = "")
	@RequestMapping(value = "/updateMmsGrsCode", method = RequestMethod.GET)
	public ResponseRestEntity<UserServiceSend> updateMmsGrsCode(@RequestParam(required = false) String messageId,@RequestParam(required = false) String status) {
		UserServiceSend userServiceSend = userServiceSendService.selectByMessageId(messageId);
		if(userServiceSend != null){
			userServiceSend.setStatus(Integer.valueOf(status));
		}
		userServiceSendService.updateByPrimaryKeySelective(userServiceSend);
		return new ResponseRestEntity<UserServiceSend>(userServiceSend, HttpRestStatus.OK);
	}
	@ApiOperation(value = "Query UserInfo By phoneNumber", notes = "")
	@RequestMapping(value = "/saveUserZSMTRecord", method = RequestMethod.GET)
	public ResponseRestEntity<Void> saveUserZSMTRecord(@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) String contentInfoId,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,@RequestParam(required = false) String messageId) {
		UserServiceSend userServiceSend=new UserServiceSend();
		userServiceSend.setUserServiceSendId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		userServiceSend.setPhoneNumber(phoneNumber);
		userServiceSend.setChargePhoneNumber(chargePhoneNumber);
		userServiceSend.setContentInfoId(contentInfoId);
		userServiceSend.setSpInfoId(spInfoId);
		userServiceSend.setProductInfoId(productInfoId);
		userServiceSend.setMessageId(messageId);
		userServiceSendService.insert(userServiceSend);
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	@ApiOperation(value = "Query UserInfo By phoneNumber", notes = "")
	@RequestMapping(value = "/updateSpMMSSendRecord", method = RequestMethod.GET)
	public ResponseRestEntity<UserServiceSend> updateSpMMSSendRecord(@RequestParam(required = false) String requestId,@RequestParam(required = false) String status) {
		UserServiceSend userServiceSend = userServiceSendService.selectByRequestId(requestId);
		if(userServiceSend != null){
			userServiceSend.setStatus(Integer.valueOf(status));
		}
		userServiceSendService.updateByPrimaryKeySelective(userServiceSend);
		return new ResponseRestEntity<UserServiceSend>(userServiceSend, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Add userServiceSend", notes = "")
	@RequestMapping(value = "/batchInsertMTRecords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Void> batchInsertMTRecords(@Valid @RequestBody String messageContent, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		JSONArray jsonArray = JSONArray.parseArray(messageContent);
		for(int i = 0 ; i < jsonArray.size();i++){
			UserServiceSend userServiceSend = new UserServiceSend();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			userServiceSend.setUserServiceSendId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
			userServiceSend.setSpInfoId(jsonObject.getString("spId"));
			userServiceSend.setPhoneNumber(jsonObject.getString("userPhone"));
			userServiceSend.setStatus(1);
			userServiceSend.setContentInfoId("1");
			userServiceSend.setSendTime(jsonObject.getDate("receiveDate"));
			userServiceSend.setMessageId(jsonObject.getString("messageId"));
			userServiceSend.setProductInfoId(jsonObject.getString("serviceId"));
			userServiceSendService.insert(userServiceSend);
		}
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	
	@ApiOperation(value = "update UserUpdate By messageId", notes = "")
	@RequestMapping(value = "/updateGatewaySRecord", method = RequestMethod.GET)
	public ResponseRestEntity<UserServiceSend> updateGatewaySRecord(@RequestParam(required = false) String status,@RequestParam(required = false) String messageId,@RequestParam(required = false) String reqId) {
		UserServiceSend userServiceSend = userServiceSendService.selectByMessageId(messageId);
		if(userServiceSend != null){
			userServiceSend.setStatus(Integer.valueOf(status));
			userServiceSend.setRequestId(reqId);
		} else {
			return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		userServiceSendService.updateByPrimaryKeySelective(userServiceSend);
		return new ResponseRestEntity<UserServiceSend>(userServiceSend, HttpRestStatus.OK,localeMessageSourceService.getMessage("UserServiceSend In CorbizController.update.ok.message"));
	}
	
	@ApiOperation(value = "Add UserOrder", notes = "")
	@RequestMapping(value = "/saveOrderMessage", method = RequestMethod.GET)
	public ResponseRestEntity<Void> saveOrderMessage(@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String productInfoId,@RequestParam(required = false) String chargePhoneNumber,
			@RequestParam(required = false) String orderTime,@RequestParam(required = false) String orderRoute,@RequestParam(required = false) String fee,@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String serviceUniqueId) {
		UserOrder userOrderHis=new UserOrder();
		userOrderHis.setUserOrderId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setProductInfoId(productInfoId);
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setChargePhoneNumber(chargePhoneNumber);
		userOrderHis.setOrderTime(new Date());
		userOrderHis.setOrderRoute(Integer.parseInt(orderRoute));
		userOrderHis.setOrderType(CommonFun.ORDER_INT);
		userOrderHis.setFee(Double.parseDouble(fee));
		
		//得到城市号
		String segment=phoneNumber.substring(0,7);
		List<MobileSegment> mobileSegmentLists=mobileSegmentService.getCityIdBySegment(segment);
		if(mobileSegmentLists!=null&&mobileSegmentLists.size()>0){
			userOrderHis.setArea(mobileSegmentLists.get(0).getCityId());
		}
		
		UserOrderHis userOrderHisTmp=new UserOrderHis();
		userOrderHisTmp.setUserOrderHisId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		userOrderHisTmp.setSpInfoId(spInfoId);
		userOrderHisTmp.setProductInfoId(productInfoId);
		userOrderHisTmp.setPhoneNumber(phoneNumber);
		userOrderHisTmp.setChargePhoneNumber(chargePhoneNumber);
		userOrderHisTmp.setOrderTime(new Date());
		userOrderHisTmp.setOrderRoute(Integer.parseInt(orderRoute));
		userOrderHisTmp.setOrderType(CommonFun.ORDER_INT);
		userOrderHisTmp.setFee(Double.parseDouble(fee));
		if(mobileSegmentLists!=null&&mobileSegmentLists.size()>0){
			userOrderHisTmp.setArea(mobileSegmentLists.get(0).getCityId());
		}
		
		//插入用户订购关系表
		userOrderService.insert(userOrderHis);
		
		//插入用户订购关系历史表
		userOrderHisService.insert(userOrderHisTmp);
		
		return new ResponseRestEntity<Void>(HttpRestStatus.CREATED);
	}
	
	@ApiOperation(value = "update UserOrder By phoneNumber", notes = "")
	@RequestMapping(value = "/updateOrderMessage", method = RequestMethod.GET)
	public ResponseRestEntity<UserOrder> updateOrderMessage(@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String serviceCode) {
		UserOrder userOrder = new UserOrder();
		userOrder.setPhoneNumber(phoneNumber);
		ProductService productService = productServiceService.selectByPrimaryKey(serviceCode);
		if(productService!=null){
			userOrder.setProductInfoId(productService.getProductInfoId());
		}else{
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		List<UserOrder> userOrderList = userOrderService.selectPage(userOrder);
		if(userOrderList != null && !userOrderList.isEmpty()){
			userOrderList.get(0).setNotifySpFlag(0);
		}else{
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		userOrderService.updateByPrimaryKeySelective(userOrderList.get(0));
		return new ResponseRestEntity<UserOrder>(userOrderList.get(0), HttpRestStatus.OK,localeMessageSourceService.getMessage("UserOrder In CorbizController.update.ok.message"));
	}
	
	@ApiOperation(value = "get all sp_product_id for phonenumber", notes = "")
	@RequestMapping(value = "/getSpProductIds", method = RequestMethod.GET)
	public ResponseRestEntity<List<String>> getSpProductIds(@RequestParam(required = false) String phoneNumber) {
		List<UserOrder> lists=new ArrayList<UserOrder>();
		lists=userOrderService.getSpProductIds(phoneNumber);
		
		if (lists == null || lists.isEmpty()) {
			return new ResponseRestEntity<List<String>>(new ArrayList<String>(), HttpRestStatus.NOT_FOUND);
		}else{
			List<String> strLists=new ArrayList<String>();
			for(int i=0;i<lists.size();i++){
				strLists.add(lists.get(i).getSpProductId());
			}
			return new ResponseRestEntity<List<String>>(strLists, HttpRestStatus.OK);
		}
		
	}
}
