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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pagehelper.PageHelper;
import com.zbensoft.mmsmp.api.common.CommonLogImpl;
import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.HttpRestStatusFactory;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.api.service.api.UserServiceSendService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.db.domain.UserStatistics;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/userOrder")
@RestController
public class UserOrderController {
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	UserServiceSendService userServiceSendService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_UO_Q')")
	@ApiOperation(value = "Query UserOrder，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrder>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer priority,@RequestParam(required = false) Integer notifySpFlag,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		UserOrder UserOrder = new UserOrder();
		UserOrder.setUserOrderId(id);
		UserOrder.setPhoneNumber(phoneNumber);
		UserOrder.setChargePhoneNumber(chargePhoneNumber);
		UserOrder.setSpInfoId(spInfoId);
		UserOrder.setProductInfoId(productInfoId);
		UserOrder.setNotifySpFlag(notifySpFlag);
		if (status != null) {
			UserOrder.setStatus(status);
		}
		if (priority != null) {
			UserOrder.setPriority(priority);
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
			list = userOrderService.selectPage(UserOrder);

		} else {
			list = userOrderService.selectPage(UserOrder);
		}

		int count = userOrderService.count(UserOrder);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserOrder>>(new ArrayList<UserOrder>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrder> listNew = new ArrayList<UserOrder>();
		for (UserOrder bean : list) {
			ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getArea());
			ProductInfo productInfo = productInfoService.selectByPrimaryKey(bean.getProductInfoId());
			ProvinceCity provinceCityNew = provinceCityService.selectByPrimaryKey(provinceCity.getParentProvinceCityId());
			if (provinceCity != null) {
				bean.setProvinceCityName(provinceCity.getProvinceCityName());
				bean.setParentProvinceCityId(provinceCity.getParentProvinceCityId());
			}
			if (provinceCityNew != null) {
				bean.setParentProvinceCityName(provinceCityNew.getProvinceCityName());
			}
			if (productInfo != null) {
				bean.setProductName(productInfo.getProductName());
			}
			listNew.add(bean);
		}
	    return new ResponseRestEntity<List<UserOrder>>(listNew, HttpRestStatus.OK,count,count);
	}

	//查询通知
	@PreAuthorize("hasRole('R_UO_Q')")
	@ApiOperation(value = "Query UserOrder", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserOrder> selectByPrimaryKey(@PathVariable("id") String id) {
		UserOrder UserOrder = userOrderService.selectByPrimaryKey(id);
		if (UserOrder == null) {
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<UserOrder>(UserOrder, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_UO_E')")
	@ApiOperation(value = "Add UserOrder", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody UserOrder UserOrder, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		UserOrder bean = userOrderService.selectByPrimaryKey(UserOrder.getPhoneNumber());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		
		userOrderService.insert(UserOrder);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, UserOrder,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/UserOrder/{id}").buildAndExpand(UserOrder.getPhoneNumber()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_UO_E')")
	@ApiOperation(value = "Edit UserOrder", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserOrder> update(@PathVariable("id") String id, @Valid @RequestBody UserOrder UserOrder, BindingResult result) {

		UserOrder currentUserOrder = userOrderService.selectByPrimaryKey(id);

		if (currentUserOrder == null) {
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentUserOrder.setUserOrderId(id);
		currentUserOrder.setPhoneNumber(UserOrder.getPhoneNumber());
		currentUserOrder.setStatus(UserOrder.getStatus());
		currentUserOrder.setArea(UserOrder.getArea());
		currentUserOrder.setChargePhoneNumber(UserOrder.getChargePhoneNumber());
		currentUserOrder.setEffTime(UserOrder.getEffTime());
		currentUserOrder.setFeatureStr(UserOrder.getFeatureStr());
		currentUserOrder.setFee(UserOrder.getFee());
		currentUserOrder.setIsPackage(UserOrder.getIsPackage());
		currentUserOrder.setLastBatchId(UserOrder.getLastBatchId());
		currentUserOrder.setNotDisturbTime(UserOrder.getNotDisturbTime());
		currentUserOrder.setOrderRoute(UserOrder.getOrderRoute());
		currentUserOrder.setOrderTime(UserOrder.getOrderTime());
		currentUserOrder.setOrderType(UserOrder.getOrderType());
		currentUserOrder.setPriority(UserOrder.getPriority());
		currentUserOrder.setProductInfoId(UserOrder.getProductInfoId());
		currentUserOrder.setSpInfoId(UserOrder.getSpInfoId());
		currentUserOrder.setTransactionId(UserOrder.getTransactionId());
		currentUserOrder.setVersion(UserOrder.getVersion());
		currentUserOrder.setNotifySpFlag(UserOrder.getNotifySpFlag());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<UserOrder>(currentUserOrder,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userOrderService.updateByPrimaryKey(currentUserOrder);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrder,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrder>(currentUserOrder, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_UO_E')")
	@ApiOperation(value = "Delete UserOrder", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrder> delete(@PathVariable("id") String id) {

		UserOrder UserOrder = userOrderService.selectByPrimaryKey(id);
		if (UserOrder == null) {
			return new ResponseRestEntity<UserOrder>(HttpRestStatus.NOT_FOUND);
		}

		userOrderService.deleteByPrimaryKey(id);
		//删除日志开始
		UserOrder delBean = new UserOrder();
		delBean.setPhoneNumber(id);              
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserOrder>(HttpRestStatus.NO_CONTENT);
	}
	
	//用户统计数
	@PreAuthorize("hasRole('R_USST_Q')")
	@ApiOperation(value = "Query UserStatistics", notes = "")
	@RequestMapping(value = "/userStatistics", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserStatistics>> selectUserStatistics(@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String monthStart,@RequestParam(required = false) String monthEnd) {
		UserStatistics userStatistics = new UserStatistics();
		userStatistics.setSpInfoId(spInfoId);
		
		if(monthStart!=null){
			monthStart = monthStart + "-01 00:00:00";
			userStatistics.setMonthStart(monthStart);
		}
		if(monthEnd!=null){
			monthEnd = monthFormat(monthEnd) + "-01 00:00:00";
			userStatistics.setMonthEnd(monthEnd);
		}
		userStatistics.setMonthEnd(monthEnd);
		List<UserStatistics> orderList = userOrderService.selectCountByOrderAndSpInfoId(userStatistics);
		List<UserStatistics> onDemandList = userOrderService.selectCountByOnDemandAndSpInfoId(userStatistics);
		if(orderList!=null && !orderList.isEmpty()){
			for (UserStatistics userStatistics2 : orderList) {
				//获取sp总费用
				Double orderFee = 0.0 ,onDemandFee = 0.0,spTotalCost = 0.0 ;
//				orderFee = userStatistics2.getOrderFee();
				SpInfo spInfo = spInfoService.selectByPrimaryKey(userStatistics2.getSpInfoId());
				//获取发送总条数
				int sendCount = userServiceSendService.getCountBySpInfoId(userStatistics2.getSpInfoId());
				userStatistics2.setSendCount(sendCount);
				List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
				if(spInfo!=null){
					//获取企业代码
					userStatistics2.setCompanyCode(spInfo.getCompanyCode());
					ProductInfo productInfo = new ProductInfo();
					productInfo.setCompanyCode(spInfo.getCompanyCode());
					productInfoList = productInfoService.selectPage(productInfo);
					if(productInfoList!=null && !productInfoList.isEmpty()){
						for (ProductInfo productInfo2 : productInfoList) {
							orderFee = productInfo2.getOrderFee();
							onDemandFee = productInfo2.getOnDemandFee();
						}
					}
				}else{
					continue;
				}
				//获取点播数
				if(onDemandList!=null&&!onDemandList.isEmpty()){
					for (UserStatistics userStatistics3 : onDemandList) {
						if(userStatistics2.getSpInfoId()==userStatistics3.getSpInfoId()){
							userStatistics2.setOnDemandCount(userStatistics3.getOnDemandCount());
//							userStatistics2.setOnDemandFee(userStatistics3.getOnDemandFee());
						}
					}
				}
//				onDemandFee = userStatistics2.getOnDemandFee();
				if(onDemandFee == null){
					onDemandFee = 0.0;
				}
				if(orderFee == null){
					orderFee = 0.0;
				}
				spTotalCost = orderFee*userStatistics2.getOrderCount() + onDemandFee*userStatistics2.getOnDemandCount();
				userStatistics2.setSpTotalCost(spTotalCost);
			}
		}
		
		return new ResponseRestEntity<List<UserStatistics>>(orderList, HttpRestStatus.OK);
	}
	//时间转换
	public static String monthFormat(String monthEnd){
		String[] time = monthEnd.split("-");
		int year = Integer.valueOf(time[0]);
		int month = Integer.valueOf(time[1]);
		if (month <= 11) {
			month+=1;
		}else if(month == 12){
			month = 1;
			year+=1;
		}
		String timeNew = year + "-" + month;
		return timeNew;
	}
}
