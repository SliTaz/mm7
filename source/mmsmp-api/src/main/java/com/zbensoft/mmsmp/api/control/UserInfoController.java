package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.UserInfoService;
import com.zbensoft.mmsmp.db.domain.UserInfo;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/userInfo")
@RestController
public class UserInfoController {
	@Autowired
	UserInfoService userInfoService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_UI_Q')")
	@ApiOperation(value = "Query UserInfo，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserInfo>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) Integer terminalType,
			@RequestParam(required = false) String userName,@RequestParam(required = false) Integer status,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		UserInfo UserInfo = new UserInfo();
		UserInfo.setPhoneNumber(id);
		UserInfo.setChargePhoneNumber(chargePhoneNumber);
		UserInfo.setTerminalType(terminalType);
		UserInfo.setUserName(userName);
		UserInfo.setDeleteFlag(MessageDef.DELETE_FLAG.UNDELETE);
		if (status != null) {
			UserInfo.setStatus(status);
		}
		List<UserInfo> list = new ArrayList<UserInfo>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userInfoService.selectPage(UserInfo);

		} else {
			list = userInfoService.selectPage(UserInfo);
		}

		int count = userInfoService.count(UserInfo);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserInfo>>(new ArrayList<UserInfo>(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<List<UserInfo>>(list, HttpRestStatus.OK,count,count);
	}

	//查询通知
	@PreAuthorize("hasRole('R_UI_Q')")
	@ApiOperation(value = "Query UserInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserInfo> selectByPrimaryKey(@PathVariable("id") String id) {
		UserInfo UserInfo = userInfoService.selectByPrimaryKey(id);
		if (UserInfo == null) {
			return new ResponseRestEntity<UserInfo>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<UserInfo>(UserInfo, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_UI_E')")
	@ApiOperation(value = "Add UserInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody UserInfo UserInfo, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		UserInfo bean = userInfoService.selectByPrimaryKey(UserInfo.getPhoneNumber());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserInfo.setUserPassword(encoder.encode(UserInfo.getUserPassword()));
		UserInfo.setCreateTime(PageHelperUtil.getCurrentDate());
		UserInfo.setDeleteFlag(MessageDef.DELETE_FLAG.UNDELETE);
		userInfoService.insert(UserInfo);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, UserInfo,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/UserInfo/{id}").buildAndExpand(UserInfo.getPhoneNumber()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_UI_E')")
	@ApiOperation(value = "Edit UserInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserInfo> update(@PathVariable("id") String id, @Valid @RequestBody UserInfo UserInfo, BindingResult result) {

		UserInfo currentUserInfo = userInfoService.selectByPrimaryKey(id);

		if (currentUserInfo == null) {
			return new ResponseRestEntity<UserInfo>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentUserInfo.setPhoneNumber(id);
		currentUserInfo.setStatus(UserInfo.getStatus());
		currentUserInfo.setProvince(UserInfo.getProvince());
		currentUserInfo.setBirthday(UserInfo.getBirthday());
		currentUserInfo.setBrand(UserInfo.getBrand());
		currentUserInfo.setCertificateNumber(UserInfo.getCertificateNumber());
		currentUserInfo.setCertificateType(UserInfo.getCertificateType());
		currentUserInfo.setChargePhoneNumber(UserInfo.getChargePhoneNumber());
		currentUserInfo.setCity(UserInfo.getCity());
		currentUserInfo.setCountry(UserInfo.getCountry());
		currentUserInfo.setHobby(UserInfo.getHobby());
		currentUserInfo.setMarriage(UserInfo.getMarriage());
		currentUserInfo.setNationality(UserInfo.getNationality());
		currentUserInfo.setNickName(UserInfo.getNickName());
		currentUserInfo.setPostalCode(UserInfo.getPostalCode());
		currentUserInfo.setProfession(UserInfo.getProfession());
		currentUserInfo.setTerminalType(UserInfo.getTerminalType());
		currentUserInfo.setUserAddr(UserInfo.getUserAddr());
		currentUserInfo.setUserEmail(UserInfo.getUserEmail());
		currentUserInfo.setUserName(UserInfo.getUserName());
	//	currentUserInfo.setUserPassword(UserInfo.getUserPassword());
		currentUserInfo.setUserSex(UserInfo.getUserSex());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<UserInfo>(currentUserInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userInfoService.updateByPrimaryKey(currentUserInfo);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserInfo,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserInfo>(currentUserInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_UI_E')")
	@ApiOperation(value = "Delete UserInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserInfo> delete(@PathVariable("id") String id) {

		UserInfo UserInfo = userInfoService.selectByPrimaryKey(id);
		if (UserInfo == null) {
			return new ResponseRestEntity<UserInfo>(HttpRestStatus.NOT_FOUND);
		}
		UserInfo.setDeleteFlag(MessageDef.DELETE_FLAG.DELETED);
		userInfoService.updateByPrimaryKey(UserInfo);
		//删除日志开始
		UserInfo delBean = new UserInfo();
		delBean.setPhoneNumber(id);   
		delBean.setDeleteFlag(MessageDef.DELETE_FLAG.DELETED);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserInfo>(HttpRestStatus.NO_CONTENT);
	}
	// 重置密码
	@PreAuthorize("hasRole('R_UI_E')")
	@ApiOperation(value = "Reset users password to default", notes = "")
	@RequestMapping(value = "/reset/defaultPassword/{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserInfo> resetDefaultPassword(@PathVariable("id") String id, @Valid @RequestBody UserInfo userInfo) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserInfo sysUserValidate = userInfoService.selectByPrimaryKey(id);
		if (null == sysUserValidate) {
			return new ResponseRestEntity<UserInfo>(HttpRestStatus.NOT_FOUND, localeMessageSourceService.getMessage("common.no.correspondinguser"));
		}
		sysUserValidate.setUserPassword(encoder.encode(userInfo.getUserPassword()));
		userInfoService.updateByPrimaryKeySelective(sysUserValidate);
		sysUserValidate.setUserPassword(null);
		// 修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_RESETPWD, sysUserValidate, CommonLogImpl.MANAGE_USER);
		return new ResponseRestEntity<UserInfo>(sysUserValidate, HttpRestStatus.OK);
	}
}
