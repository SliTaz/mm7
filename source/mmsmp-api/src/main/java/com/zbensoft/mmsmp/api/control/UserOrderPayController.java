package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
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
import com.zbensoft.mmsmp.api.service.api.UserOrderPayService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.UserOrderPay;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/userOrderPay")
@RestController
public class UserOrderPayController {
	@Autowired
	UserOrderPayService userOrderPayService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Autowired
	private ProductInfoService productInfoService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_UOP_Q')")
	@ApiOperation(value = "Query UserOrderPay，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderPay>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer priority,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		UserOrderPay UserOrderPay = new UserOrderPay();
		UserOrderPay.setUserOrderPayId(id);
		UserOrderPay.setPhoneNumber(phoneNumber);
		UserOrderPay.setProductInfoId(productInfoId);
		UserOrderPay.setSpInfoId(spInfoId);
		
		if (status != null) {
			UserOrderPay.setStatus(status);
		}
		if (priority != null) {
			UserOrderPay.setPriority(priority);
		}
		List<UserOrderPay> list = new ArrayList<UserOrderPay>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userOrderPayService.selectPage(UserOrderPay);

		} else {
			list = userOrderPayService.selectPage(UserOrderPay);
		}

		int count = userOrderPayService.count(UserOrderPay);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserOrderPay>>(new ArrayList<UserOrderPay>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrderPay> listNew = new ArrayList<UserOrderPay>();
		for (UserOrderPay bean : list) {
			ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getArea());
			ProvinceCity provinceCityNew = provinceCityService.selectByPrimaryKey(provinceCity.getParentProvinceCityId());
			ProductInfo productInfo = productInfoService.selectByPrimaryKey(bean.getProductInfoId());
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
	    return new ResponseRestEntity<List<UserOrderPay>>(listNew, HttpRestStatus.OK,count,count);
	}

	//查询通知
	@PreAuthorize("hasRole('R_UOP_Q')")
	@ApiOperation(value = "Query UserOrderPay", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserOrderPay> selectByPrimaryKey(@PathVariable("id") String id) {
		UserOrderPay UserOrderPay = userOrderPayService.selectByPrimaryKey(id);
		if (UserOrderPay == null) {
			return new ResponseRestEntity<UserOrderPay>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<UserOrderPay>(UserOrderPay, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_UOP_E')")
	@ApiOperation(value = "Add UserOrderPay", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody UserOrderPay UserOrderPay, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		UserOrderPay bean = userOrderPayService.selectByPrimaryKey(UserOrderPay.getUserOrderPayId());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		
		userOrderPayService.insert(UserOrderPay);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, UserOrderPay,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/UserOrderPay/{id}").buildAndExpand(UserOrderPay.getPhoneNumber()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_UOP_E')")
	@ApiOperation(value = "Edit UserOrderPay", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserOrderPay> update(@PathVariable("id") String id, @Valid @RequestBody UserOrderPay UserOrderPay, BindingResult result) {

		UserOrderPay currentUserOrderPay = userOrderPayService.selectByPrimaryKey(id);

		if (currentUserOrderPay == null) {
			return new ResponseRestEntity<UserOrderPay>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentUserOrderPay.setUserOrderPayId(UserOrderPay.getUserOrderPayId());
		currentUserOrderPay.setPhoneNumber(UserOrderPay.getPhoneNumber());
		currentUserOrderPay.setStatus(UserOrderPay.getStatus());
		currentUserOrderPay.setArea(UserOrderPay.getArea());
		currentUserOrderPay.setChargePhoneNumber(UserOrderPay.getChargePhoneNumber());
		currentUserOrderPay.setEffTime(UserOrderPay.getEffTime());
		currentUserOrderPay.setFeatureStr(UserOrderPay.getFeatureStr());
		currentUserOrderPay.setFee(UserOrderPay.getFee());
		currentUserOrderPay.setIsPackage(UserOrderPay.getIsPackage());
		currentUserOrderPay.setLastBatchId(UserOrderPay.getLastBatchId());
		currentUserOrderPay.setNotDisturbTime(UserOrderPay.getNotDisturbTime());
		currentUserOrderPay.setOrderRoute(UserOrderPay.getOrderRoute());
		currentUserOrderPay.setOrderTime(UserOrderPay.getOrderTime());
		currentUserOrderPay.setOrderType(UserOrderPay.getOrderType());
		currentUserOrderPay.setPriority(UserOrderPay.getPriority());
		currentUserOrderPay.setProductInfoId(UserOrderPay.getProductInfoId());
		currentUserOrderPay.setSpInfoId(UserOrderPay.getSpInfoId());
		currentUserOrderPay.setTransactionId(UserOrderPay.getTransactionId());
		currentUserOrderPay.setVersion(UserOrderPay.getVersion());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<UserOrderPay>(currentUserOrderPay,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userOrderPayService.updateByPrimaryKey(currentUserOrderPay);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderPay,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrderPay>(currentUserOrderPay, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_UOP_E')")
	@ApiOperation(value = "Delete UserOrderPay", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrderPay> delete(@PathVariable("id") String id) {

		UserOrderPay UserOrderPay = userOrderPayService.selectByPrimaryKey(id);
		if (UserOrderPay == null) {
			return new ResponseRestEntity<UserOrderPay>(HttpRestStatus.NOT_FOUND);
		}

		userOrderPayService.deleteByPrimaryKey(id);
		//删除日志开始
		UserOrderPay delBean = new UserOrderPay();
		delBean.setPhoneNumber(id);              
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserOrderPay>(HttpRestStatus.NO_CONTENT);
	}
}
