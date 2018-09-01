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
import com.zbensoft.mmsmp.api.common.IDGenerate;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserServiceSendService;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserServiceSend;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/userServiceSend")
@RestController
public class UserServiceSendController {
	@Autowired
	UserServiceSendService userServiceSendService;
	
	@Autowired
	SpInfoService spInfoService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_USS_Q')")
	@ApiOperation(value = "Query UserServiceSend，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserServiceSend>> selectPage(@RequestParam(required = false) String userServiceSendId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,@RequestParam(required = false) String requestId,
			@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) String contentInfoId,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer serviceType,@RequestParam(required = false) Integer sendServiceType,
			@RequestParam(required = false) String sendServiceRelId,@RequestParam(required = false) String errorInfo,@RequestParam(required = false) Integer sendType,
			@RequestParam(required = false) String reportTimeStart,@RequestParam(required = false) String reportTimeEnd,@RequestParam(required = false) String mmsPath,
			@RequestParam(required = false) String batchId,@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		UserServiceSend userServiceSend = new UserServiceSend();
		userServiceSend.setUserServiceSendId(userServiceSendId);
		userServiceSend.setPhoneNumber(phoneNumber);
		userServiceSend.setSpInfoId(spInfoId);
		userServiceSend.setProductInfoId(productInfoId);
		userServiceSend.setRequestId(requestId);
		userServiceSend.setChargePhoneNumber(chargePhoneNumber);
		userServiceSend.setContentInfoId(contentInfoId);
		userServiceSend.setStatus(status);
		userServiceSend.setServiceType(sendServiceType);
		userServiceSend.setSendServiceType(sendServiceType);
		userServiceSend.setSendServiceRelId(sendServiceRelId);
		userServiceSend.setErrorInfo(errorInfo);
		userServiceSend.setSendType(sendType);
		userServiceSend.setMmsPath(mmsPath);
		userServiceSend.setBatchId(batchId);
		userServiceSend.setReportTimeStart(reportTimeStart);
		userServiceSend.setReportTimeEnd(reportTimeEnd);
		int count = userServiceSendService.count(userServiceSend);
		if (count == 0) {
			return new ResponseRestEntity<List<UserServiceSend>>(new ArrayList<UserServiceSend>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserServiceSend> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userServiceSendService.selectPage(userServiceSend);

		} else {
			list = userServiceSendService.selectPage(userServiceSend);
		}

		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserServiceSend>>(new ArrayList<UserServiceSend>(), HttpRestStatus.NOT_FOUND);
		}
		
		List<UserServiceSend> listNew = new ArrayList<>();
		for(UserServiceSend userServiceSendNew : list){
			SpInfo spInfo = spInfoService.selectByPrimaryKey(userServiceSendNew.getSpInfoId()); 
			if(spInfo != null){
				userServiceSendNew.setCompanyName(spInfo.getCompanyName());
			}
			listNew.add(userServiceSendNew);
		}
		return new ResponseRestEntity<List<UserServiceSend>>(listNew, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_USS_Q')")
	@ApiOperation(value = "Query UserServiceSend", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserServiceSend> selectByPrimaryKey(@PathVariable("id") String id) {
		UserServiceSend userServiceSend = userServiceSendService.selectByPrimaryKey(id);
		if (userServiceSend == null) {
			return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<UserServiceSend>(userServiceSend, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_USS_E')")
	@ApiOperation(value = "Add UserServiceSend", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserServiceSend(@Valid @RequestBody UserServiceSend userServiceSend, BindingResult result, UriComponentsBuilder ucBuilder) {
		userServiceSend.setUserServiceSendId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userServiceSendService.insert(userServiceSend);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userServiceSend,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/userServiceSend/{id}").buildAndExpand(userServiceSend.getUserServiceSendId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userServiceSend.create.created.message"));
	}

	@PreAuthorize("hasRole('R_USS_E')")
	@ApiOperation(value = "Edit UserServiceSend", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserServiceSend> updateUserServiceSend(@PathVariable("id") String id, @Valid @RequestBody UserServiceSend userServiceSend, BindingResult result) {

		UserServiceSend currentUserServiceSend = userServiceSendService.selectByPrimaryKey(id);

		if (currentUserServiceSend == null) {
			return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentUserServiceSend.setPhoneNumber(userServiceSend.getPhoneNumber());
		currentUserServiceSend.setSpInfoId(userServiceSend.getSpInfoId());
		currentUserServiceSend.setProductInfoId(userServiceSend.getProductInfoId());
		currentUserServiceSend.setRequestId(userServiceSend.getRequestId());
		currentUserServiceSend.setChargePhoneNumber(userServiceSend.getChargePhoneNumber());
		currentUserServiceSend.setContentInfoId(userServiceSend.getContentInfoId());
		currentUserServiceSend.setStatus(userServiceSend.getStatus());
		currentUserServiceSend.setServiceType(userServiceSend.getServiceType());
		currentUserServiceSend.setSendServiceType(userServiceSend.getSendServiceType());
		currentUserServiceSend.setSendServiceRelId(userServiceSend.getSendServiceRelId());
		currentUserServiceSend.setErrorInfo(userServiceSend.getErrorInfo());
		currentUserServiceSend.setSendType(userServiceSend.getSendType());
		currentUserServiceSend.setMmsPath(userServiceSend.getMmsPath());
		currentUserServiceSend.setBatchId(userServiceSend.getBatchId());
		currentUserServiceSend.setReportTime(userServiceSend.getReportTime());
		currentUserServiceSend.setSendTime(userServiceSend.getSendTime());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<UserServiceSend>(currentUserServiceSend,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		userServiceSendService.updateByPrimaryKey(currentUserServiceSend);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserServiceSend,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserServiceSend>(currentUserServiceSend, HttpRestStatus.OK,localeMessageSourceService.getMessage("currentUserServiceSend.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_USS_E')")
	@ApiOperation(value = "Edit Part UserServiceSend", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<UserServiceSend> updateUserServiceSend(@PathVariable("id") String id, @RequestBody UserServiceSend userServiceSend) {

		UserServiceSend currentUserServiceSend =userServiceSendService.selectByPrimaryKey(id);

		if (currentUserServiceSend == null) {
			return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NOT_FOUND);
		}
		currentUserServiceSend.setPhoneNumber(userServiceSend.getPhoneNumber());
		currentUserServiceSend.setSpInfoId(userServiceSend.getSpInfoId());
		currentUserServiceSend.setProductInfoId(userServiceSend.getProductInfoId());
		currentUserServiceSend.setRequestId(userServiceSend.getRequestId());
		currentUserServiceSend.setChargePhoneNumber(userServiceSend.getChargePhoneNumber());
		currentUserServiceSend.setContentInfoId(userServiceSend.getContentInfoId());
		currentUserServiceSend.setStatus(userServiceSend.getStatus());
		currentUserServiceSend.setServiceType(userServiceSend.getServiceType());
		currentUserServiceSend.setSendServiceType(userServiceSend.getSendServiceType());
		currentUserServiceSend.setSendServiceRelId(userServiceSend.getSendServiceRelId());
		currentUserServiceSend.setErrorInfo(userServiceSend.getErrorInfo());
		currentUserServiceSend.setSendType(userServiceSend.getSendType());
		currentUserServiceSend.setMmsPath(userServiceSend.getMmsPath());
		currentUserServiceSend.setBatchId(userServiceSend.getBatchId());
		currentUserServiceSend.setReportTime(userServiceSend.getReportTime());
		currentUserServiceSend.setSendTime(userServiceSend.getSendTime());
		//修改日志
		userServiceSendService.updateByPrimaryKeySelective(currentUserServiceSend);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserServiceSend,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserServiceSend>(currentUserServiceSend, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_USS_E')")
	@ApiOperation(value = "Delete UserServiceSend", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserServiceSend> deleteUserServiceSend(@PathVariable("id") String id) {

		UserServiceSend userServiceSend = userServiceSendService.selectByPrimaryKey(id);
		if (userServiceSend == null) {
			return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NOT_FOUND);
		}

		userServiceSendService.deleteByPrimaryKey(id);
		//删除日志开始
		UserServiceSend delBean = new UserServiceSend();
		delBean.setUserServiceSendId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserServiceSend>(HttpRestStatus.NO_CONTENT);
	}
}
