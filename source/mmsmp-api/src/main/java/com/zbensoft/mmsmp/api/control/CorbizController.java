package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.api.service.api.CpInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.SystemConfigService;
import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.domain.CpInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.SystemConfig;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.SpInfo;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/corbiz")
@RestController
public class CorbizController {
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
	ProductInfoService productInfoService;
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
}
