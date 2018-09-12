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
import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.HttpRestStatusFactory;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.CooperKeyService;
import com.zbensoft.mmsmp.db.domain.CooperKey;


import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/cooperKey")
@RestController
public class CooperKeyController {
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;	
	@Autowired
	CooperKeyService  cooperKeyService;
//	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query CooperKey, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<CooperKey>> selectPage(@RequestParam(required = false) String keyId, @RequestParam(required = false) String cooperId,
		@RequestParam(required = false) String cooperName, @RequestParam(required = false) String cooperKey1, @RequestParam(required = false) String ips,@RequestParam(required = false) String notifyUrl, 
		@RequestParam(required = false) String remark, @RequestParam(required = false) String serviceTel,
		@RequestParam(required = false) String appName,
		@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		CooperKey cooperKey = new CooperKey();
		cooperKey.setAppName(appName);
		cooperKey.setCooperId(cooperId);
		cooperKey.setCooperKey(cooperKey1);
		cooperKey.setIps(ips);
		cooperKey.setKeyId(keyId);
		cooperKey.setNotifyUrl(notifyUrl);
		cooperKey.setServiceTel(serviceTel);
		cooperKey.setCooperName(cooperName);
		List<CooperKey> list = new ArrayList<CooperKey>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = cooperKeyService.selectPage(cooperKey);

		} else {
			list = cooperKeyService.selectPage(cooperKey);
		}

		int count = cooperKeyService.count(cooperKey);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<CooperKey>>(new ArrayList<CooperKey>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<CooperKey>>(list, HttpRestStatus.OK, count, count);
	}
//	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query CooperKey", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<CooperKey> selectByPrimaryKey(@PathVariable("id") String id) {
		CooperKey cooperKey  = cooperKeyService.selectByPrimaryKey(id);
		if (cooperKey == null) {
			return new ResponseRestEntity<CooperKey>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<CooperKey>(cooperKey, HttpRestStatus.OK);
	}
//	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Add CooperKey", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addCooperKey(@Valid @RequestBody CooperKey cooperKey, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		CooperKey bean1 = cooperKeyService.selectByPrimaryKey(cooperKey.getKeyId());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
		cooperKeyService.insert(cooperKey);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/cooperKey/{id}").buildAndExpand(cooperKey.getKeyId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
//	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Edit CooperKey ", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<CooperKey> updateCooperKey(@PathVariable("id") String id, @Valid @RequestBody CooperKey bean, BindingResult result) {
		CooperKey currentCooperKey = cooperKeyService.selectByPrimaryKey(id);
		if (currentCooperKey == null) {
			return new ResponseRestEntity<CooperKey>(HttpRestStatus.NOT_FOUND);
		}	
		currentCooperKey.setAppName(bean.getAppName());
		currentCooperKey.setCooperId(bean.getCooperId());
		currentCooperKey.setCooperKey(bean.getCooperKey());
		currentCooperKey.setCooperName(bean.getCooperName());
		currentCooperKey.setIps(bean.getIps());
		
		currentCooperKey.setKeyId(bean.getKeyId());
		currentCooperKey.setNotifyUrl(bean.getNotifyUrl());
		currentCooperKey.setRemark(bean.getRemark());
		currentCooperKey.setServiceTel(bean.getServiceTel());

		cooperKeyService.updateByPrimaryKey(currentCooperKey);
		return new ResponseRestEntity<CooperKey>(currentCooperKey, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
	}
//	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Delete CooperKey", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<CooperKey> deleteCooperKey(@PathVariable("id") String id) {

		CooperKey cooperKey = cooperKeyService.selectByPrimaryKey(id);
		if (cooperKey == null) {
			return new ResponseRestEntity<>(HttpRestStatus.NOT_FOUND);
		}

		cooperKeyService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<CooperKey>(HttpRestStatus.NO_CONTENT);
	}
}
