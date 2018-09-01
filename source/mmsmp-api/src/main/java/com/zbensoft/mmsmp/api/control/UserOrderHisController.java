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
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/userOrderHis")
@RestController
public class UserOrderHisController {
	@Autowired
	UserOrderHisService userOrderHisService;
	@Autowired
	SpInfoService spInfoService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_UOH_Q')")
	@ApiOperation(value = "Query UserOrderHis，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderHis>> selectPage(@RequestParam(required = false) String userOrderHisId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) Integer orderRoute,
			@RequestParam(required = false) Integer orderType,@RequestParam(required = false) Double fee,
			@RequestParam(required = false) Integer cancelRoute,@RequestParam(required = false) String featureStr,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) String version,
			@RequestParam(required = false) String notDisturbTime,@RequestParam(required = false) String transactionId,
			@RequestParam(required = false) String area,@RequestParam(required = false) Integer priority,
			@RequestParam(required = false) String lastBatchId,@RequestParam(required = false) Integer isPackage,
			@RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		UserOrderHis userOrderHis = new UserOrderHis();
		userOrderHis.setUserOrderHisId(userOrderHisId);
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setProductInfoId(productInfoId);
		userOrderHis.setChargePhoneNumber(chargePhoneNumber);
		userOrderHis.setOrderRoute(orderRoute);
		userOrderHis.setOrderType(orderType);
		userOrderHis.setFee(fee);
		userOrderHis.setCancelRoute(cancelRoute);
		userOrderHis.setFeatureStr(featureStr);
		userOrderHis.setStatus(status);
		userOrderHis.setVersion(version);
		userOrderHis.setNotDisturbTime(notDisturbTime);
		userOrderHis.setTransactionId(transactionId);
		userOrderHis.setArea(area);
		userOrderHis.setPriority(priority);
		userOrderHis.setLastBatchId(lastBatchId);
		userOrderHis.setIsPackage(isPackage);
		userOrderHis.setOrderTimeStart(orderTimeStart);
		userOrderHis.setOrderTimeEnd(orderTimeEnd);
		int count = userOrderHisService.count(userOrderHis);
		if (count == 0) {
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrderHis> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userOrderHisService.selectPage(userOrderHis);

		} else {
			list = userOrderHisService.selectPage(userOrderHis);
		}

		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrderHis> listNew = new ArrayList<>();
		for(UserOrderHis bean: list){
			SpInfo spInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
			if(spInfo != null){
				bean.setCompanyName(spInfo.getCompanyName());
			}
			listNew.add(bean);
		}
		return new ResponseRestEntity<List<UserOrderHis>>(listNew, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_UOH_Q')")
	@ApiOperation(value = "Query UserRecv", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserOrderHis> selectByPrimaryKey(@PathVariable("id") String id) {
		UserOrderHis userOrderHis = userOrderHisService.selectByPrimaryKey(id);
		if (userOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<UserOrderHis>(userOrderHis, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Add UserOrderHis", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserOrderHis(@Valid @RequestBody UserOrderHis userOrderHis, BindingResult result, UriComponentsBuilder ucBuilder) {
//		cpInfo.setCpInfoId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_GAS_COUPONID));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userOrderHisService.insert(userOrderHis);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/userOrderHis/{id}").buildAndExpand(userOrderHis.getUserOrderHisId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userOrderHis.create.created.message"));
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Edit UserOrderHis", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserOrderHis> updateUserOrderHis(@PathVariable("id") String id, @Valid @RequestBody UserOrderHis userOrderHis, BindingResult result) {

		UserOrderHis currentUserOrderHis = userOrderHisService.selectByPrimaryKey(id);

		if (currentUserOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentUserOrderHis.setPhoneNumber(userOrderHis.getPhoneNumber());
		currentUserOrderHis.setSpInfoId(userOrderHis.getSpInfoId());
		currentUserOrderHis.setProductInfoId(userOrderHis.getProductInfoId());
		currentUserOrderHis.setChargePhoneNumber(userOrderHis.getChargePhoneNumber());
		currentUserOrderHis.setOrderRoute(userOrderHis.getOrderRoute());
		currentUserOrderHis.setOrderType(userOrderHis.getOrderType());
		currentUserOrderHis.setFee(userOrderHis.getFee());
		currentUserOrderHis.setCancelRoute(userOrderHis.getCancelRoute());
		currentUserOrderHis.setFeatureStr(userOrderHis.getFeatureStr());
		currentUserOrderHis.setStatus(userOrderHis.getStatus());
		currentUserOrderHis.setVersion(userOrderHis.getVersion());
		currentUserOrderHis.setNotDisturbTime(userOrderHis.getNotDisturbTime());
		currentUserOrderHis.setTransactionId(userOrderHis.getTransactionId());
		currentUserOrderHis.setArea(userOrderHis.getArea());
		currentUserOrderHis.setPriority(userOrderHis.getPriority());
		currentUserOrderHis.setLastBatchId(userOrderHis.getLastBatchId());
		currentUserOrderHis.setIsPackage(userOrderHis.getIsPackage());
		currentUserOrderHis.setEffTime(userOrderHis.getEffTime());
		currentUserOrderHis.setCancelTime(userOrderHis.getCancelTime());
		currentUserOrderHis.setOrderTime(userOrderHis.getOrderTime());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		userOrderHisService.updateByPrimaryKey(currentUserOrderHis);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis, HttpRestStatus.OK,localeMessageSourceService.getMessage("UserOrderHis.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Edit Part UserOrderHis", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<UserOrderHis> updateUserOrderHis(@PathVariable("id") String id, @RequestBody UserOrderHis userOrderHis) {

		UserOrderHis currentUserOrderHis =userOrderHisService.selectByPrimaryKey(id);

		if (currentUserOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}
		currentUserOrderHis.setPhoneNumber(userOrderHis.getPhoneNumber());
		currentUserOrderHis.setSpInfoId(userOrderHis.getSpInfoId());
		currentUserOrderHis.setProductInfoId(userOrderHis.getProductInfoId());
		currentUserOrderHis.setChargePhoneNumber(userOrderHis.getChargePhoneNumber());
		currentUserOrderHis.setOrderRoute(userOrderHis.getOrderRoute());
		currentUserOrderHis.setOrderType(userOrderHis.getOrderType());
		currentUserOrderHis.setFee(userOrderHis.getFee());
		currentUserOrderHis.setCancelRoute(userOrderHis.getCancelRoute());
		currentUserOrderHis.setFeatureStr(userOrderHis.getFeatureStr());
		currentUserOrderHis.setStatus(userOrderHis.getStatus());
		currentUserOrderHis.setVersion(userOrderHis.getVersion());
		currentUserOrderHis.setNotDisturbTime(userOrderHis.getNotDisturbTime());
		currentUserOrderHis.setTransactionId(userOrderHis.getTransactionId());
		currentUserOrderHis.setArea(userOrderHis.getArea());
		currentUserOrderHis.setPriority(userOrderHis.getPriority());
		currentUserOrderHis.setLastBatchId(userOrderHis.getLastBatchId());
		currentUserOrderHis.setIsPackage(userOrderHis.getIsPackage());
		currentUserOrderHis.setEffTime(userOrderHis.getEffTime());
		currentUserOrderHis.setCancelTime(userOrderHis.getCancelTime());
		currentUserOrderHis.setOrderTime(userOrderHis.getOrderTime());
		//修改日志
		userOrderHisService.updateByPrimaryKeySelective(currentUserOrderHis);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Delete UserOrderHis", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrderHis> deleteUserOrderHis(@PathVariable("id") String id) {

		UserOrderHis userOrderHis = userOrderHisService.selectByPrimaryKey(id);
		if (userOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}

		userOrderHisService.deleteByPrimaryKey(id);
		//删除日志开始
		UserOrderHis delBean = new UserOrderHis();
		delBean.setUserOrderHisId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NO_CONTENT);
	}
}
