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
import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;


import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/blackWhiteList")
@RestController
public class BlackWhiteListController {
	@Autowired
	BlackWhiteListService  blackWhiteListService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query BlackWhiteList, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<BlackWhiteList>> selectPage(@RequestParam(required = false) String blackWhiteList, @RequestParam(required = false) Integer blackWhiteListType,@RequestParam(required = false) Integer numberType, 
			@RequestParam(required = false) String remark,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		BlackWhiteList currentBlackWhiteList = new BlackWhiteList();
		currentBlackWhiteList.setBlackWhiteList(blackWhiteList);
		currentBlackWhiteList.setBlackWhiteListType(blackWhiteListType);
		currentBlackWhiteList.setNumberType(numberType);
		currentBlackWhiteList.setRemark(remark);
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
	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query BlackWhiteList", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<BlackWhiteList> selectByPrimaryKey(@PathVariable("id") String id) {
		BlackWhiteList blackWhiteList  = blackWhiteListService.selectByPrimaryKey(id);
		if (blackWhiteList == null) {
			return new ResponseRestEntity<BlackWhiteList>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<BlackWhiteList>(blackWhiteList, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Add BlackWhiteList", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProvinceCity(@Valid @RequestBody BlackWhiteList blackWhiteList, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		BlackWhiteList bean1 = blackWhiteListService.selectByPrimaryKey(blackWhiteList.getBlackWhiteList());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
		if(blackWhiteList.getBlackWhiteListType()==null||blackWhiteList.getNumberType()==null)
		{
			return new ResponseRestEntity<Void>(HttpRestStatus.EMPTY);
		}
		
		blackWhiteListService.insert(blackWhiteList);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/blackWhiteList/{id}").buildAndExpand(blackWhiteList.getBlackWhiteList()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Edit BlackWhiteList ", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<BlackWhiteList> updateBlackWhiteList(@PathVariable("id") String id, @Valid @RequestBody BlackWhiteList bean, BindingResult result) {
		BlackWhiteList currentBlackWhiteList = blackWhiteListService.selectByPrimaryKey(id);
		if (currentBlackWhiteList == null) {
			return new ResponseRestEntity<BlackWhiteList>(HttpRestStatus.NOT_FOUND);
		}	
		
	
		if(bean.getBlackWhiteListType()==null||bean.getNumberType()==null)
		{
			return new ResponseRestEntity<BlackWhiteList>(HttpRestStatus.EMPTY);
		}
		
		
		currentBlackWhiteList.setBlackWhiteList(bean.getBlackWhiteList());
		currentBlackWhiteList.setBlackWhiteListType(bean.getBlackWhiteListType());
		currentBlackWhiteList.setNumberType(bean.getNumberType());
		currentBlackWhiteList.setRemark(bean.getRemark());
		
	  
		blackWhiteListService.updateByPrimaryKey(currentBlackWhiteList);
		return new ResponseRestEntity<BlackWhiteList>(currentBlackWhiteList, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Delete BlackWhiteList", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<BlackWhiteList> deleteBlackWhiteList(@PathVariable("id") String id) {

		BlackWhiteList blackWhiteList = blackWhiteListService.selectByPrimaryKey(id);
		if (blackWhiteList == null) {
			return new ResponseRestEntity<>(HttpRestStatus.NOT_FOUND);
		}

		blackWhiteListService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<BlackWhiteList>(HttpRestStatus.NO_CONTENT);
	}
	
}
