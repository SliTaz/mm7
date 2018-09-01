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
import com.zbensoft.mmsmp.api.service.api.UserRecvService;
import com.zbensoft.mmsmp.db.domain.CpInfo;
import com.zbensoft.mmsmp.db.domain.UserRecv;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/userRecv")
@RestController
public class UserRecvController {
	@Autowired
	UserRecvService userRecvService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_UR_Q')")
	@ApiOperation(value = "Query UserRecv，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserRecv>> selectPage(@RequestParam(required = false) String userRecvId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String messageContent,@RequestParam(required = false) Integer messageType,
			@RequestParam(required = false) Integer isCorrect,@RequestParam(required = false) Integer isOrder,
			@RequestParam(required = false) String recvTimeStart,@RequestParam(required = false) String recvTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		UserRecv userRecv = new UserRecv();
		userRecv.setUserRecvId(userRecvId);
		userRecv.setPhoneNumber(phoneNumber);
		userRecv.setMessageContent(messageContent);
		userRecv.setMessageType(messageType);
		userRecv.setIsCorrect(isCorrect);
		userRecv.setIsOrder(isOrder);
		userRecv.setRecvTimeStart(recvTimeStart);
		userRecv.setRecvTimeEnd(recvTimeEnd);
		int count = userRecvService.count(userRecv);
		if (count == 0) {
			return new ResponseRestEntity<List<UserRecv>>(new ArrayList<UserRecv>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserRecv> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userRecvService.selectPage(userRecv);

		} else {
			list = userRecvService.selectPage(userRecv);
		}

		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserRecv>>(new ArrayList<UserRecv>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<UserRecv>>(list, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_UR_Q')")
	@ApiOperation(value = "Query UserRecv", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserRecv> selectByPrimaryKey(@PathVariable("id") String id) {
		UserRecv userRecv = userRecvService.selectByPrimaryKey(id);
		if (userRecv == null) {
			return new ResponseRestEntity<UserRecv>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<UserRecv>(userRecv, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Add UserRecv", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserRecv(@Valid @RequestBody UserRecv userRecv, BindingResult result, UriComponentsBuilder ucBuilder) {
		userRecv.setUserRecvId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userRecvService.insert(userRecv);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userRecv,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/userRecv/{id}").buildAndExpand(userRecv.getUserRecvId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}

	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit UserRecv", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserRecv> updateUserRecv(@PathVariable("id") String id, @Valid @RequestBody UserRecv userRecv, BindingResult result) {

		UserRecv currentUserRecv = userRecvService.selectByPrimaryKey(id);

		if (currentUserRecv == null) {
			return new ResponseRestEntity<UserRecv>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentUserRecv.setPhoneNumber(userRecv.getPhoneNumber());
		currentUserRecv.setMessageContent(userRecv.getMessageContent());
		currentUserRecv.setMessageType(userRecv.getMessageType());
		currentUserRecv.setIsCorrect(userRecv.getIsCorrect());
		currentUserRecv.setIsOrder(userRecv.getIsOrder());
		currentUserRecv.setRecvTime(userRecv.getRecvTime());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<UserRecv>(currentUserRecv,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		userRecvService.updateByPrimaryKey(currentUserRecv);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserRecv,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserRecv>(currentUserRecv, HttpRestStatus.OK,localeMessageSourceService.getMessage("UserRecv.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit Part UserRecv", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<UserRecv> updateUserRecv(@PathVariable("id") String id, @RequestBody UserRecv userRecv) {

		UserRecv currentUserRecv =userRecvService.selectByPrimaryKey(id);

		if (currentUserRecv == null) {
			return new ResponseRestEntity<UserRecv>(HttpRestStatus.NOT_FOUND);
		}
		currentUserRecv.setPhoneNumber(userRecv.getPhoneNumber());
		currentUserRecv.setMessageContent(userRecv.getMessageContent());
		currentUserRecv.setMessageType(userRecv.getMessageType());
		currentUserRecv.setIsCorrect(userRecv.getIsCorrect());
		currentUserRecv.setIsOrder(userRecv.getIsOrder());
		currentUserRecv.setRecvTime(userRecv.getRecvTime());
		//修改日志
		userRecvService.updateByPrimaryKeySelective(currentUserRecv);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserRecv,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserRecv>(currentUserRecv, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Delete UserRecv", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserRecv> deleteUserRecv(@PathVariable("id") String id) {

		UserRecv userRecv = userRecvService.selectByPrimaryKey(id);
		if (userRecv == null) {
			return new ResponseRestEntity<UserRecv>(HttpRestStatus.NOT_FOUND);
		}

		userRecvService.deleteByPrimaryKey(id);
		//删除日志开始
		UserRecv delBean = new UserRecv();
		delBean.setUserRecvId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserRecv>(HttpRestStatus.NO_CONTENT);
	}
}
