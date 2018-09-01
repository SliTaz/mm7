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
import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.db.domain.UserOrder;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/userOrder")
@RestController
public class UserOrderController {
	@Autowired
	UserOrderService userOrderService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_UO_Q')")
	@ApiOperation(value = "Query UserOrder，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrder>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String chargePhoneNumber,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer priority,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		UserOrder UserOrder = new UserOrder();
		UserOrder.setPhoneNumber(id);
		UserOrder.setChargePhoneNumber(chargePhoneNumber);
		UserOrder.setSpInfoId(spInfoId);
		UserOrder.setProductInfoId(productInfoId);
		
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
	    return new ResponseRestEntity<List<UserOrder>>(list, HttpRestStatus.OK,count,count);
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

		currentUserOrder.setPhoneNumber(id);
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
}
