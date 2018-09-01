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
import com.zbensoft.mmsmp.api.service.api.SpUrlService;
import com.zbensoft.mmsmp.db.domain.SpUrl;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/spUrl")
@RestController
public class SpUrlController {
	@Autowired
	SpUrlService spUrlService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_SPU_Q')")
	@ApiOperation(value = "Query SpInfo，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpUrl>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String spInfoId,
			@RequestParam(required = false) String hostAddr,@RequestParam(required = false) String hostPort,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		SpUrl spInfo = new SpUrl();
		spInfo.setSpUrlId(id);
		spInfo.setSpInfoId(spInfoId);
		spInfo.setHostAddr(hostAddr);
		spInfo.setHostPort(hostPort);
		
		List<SpUrl> list = new ArrayList<SpUrl>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = spUrlService.selectPage(spInfo);

		} else {
			list = spUrlService.selectPage(spInfo);
		}

		int count = spUrlService.count(spInfo);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SpUrl>>(new ArrayList<SpUrl>(), HttpRestStatus.NOT_FOUND);
		}
	    return new ResponseRestEntity<List<SpUrl>>(list, HttpRestStatus.OK,count,count);
	}

	//查询通知
	@PreAuthorize("hasRole('R_SPU_Q')")
	@ApiOperation(value = "Query SpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<SpUrl> selectByPrimaryKey(@PathVariable("id") String id) {
		SpUrl spUrl = spUrlService.selectByPrimaryKey(id);
		if (spUrl == null) {
			return new ResponseRestEntity<SpUrl>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<SpUrl>(spUrl, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_SPU_E')")
	@ApiOperation(value = "Add SpInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody SpUrl spUrl, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		SpUrl bean = spUrlService.selectByPrimaryKey(spUrl.getSpUrlId());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		
		spUrlService.insert(spUrl);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, spUrl,CommonLogImpl.SP);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/SpUrl/{id}").buildAndExpand(spUrl.getSpInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_SPU_E')")
	@ApiOperation(value = "Edit SpUrl", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<SpUrl> update(@PathVariable("id") String id, @Valid @RequestBody SpUrl spInfo, BindingResult result) {

		SpUrl currentSpInfo = spUrlService.selectByPrimaryKey(id);

		if (currentSpInfo == null) {
			return new ResponseRestEntity<SpUrl>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentSpInfo.setSpUrlId(id);
		currentSpInfo.setSpInfoId(spInfo.getSpInfoId());
		currentSpInfo.setHostAddr(spInfo.getHostAddr());
		currentSpInfo.setHostPort(spInfo.getHostPort());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<SpUrl>(currentSpInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		spUrlService.updateByPrimaryKey(currentSpInfo);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentSpInfo,CommonLogImpl.SP);
		return new ResponseRestEntity<SpUrl>(currentSpInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_SPU_E')")
	@ApiOperation(value = "Delete SpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<SpUrl> delete(@PathVariable("id") String id) {

		SpUrl spInfo = spUrlService.selectByPrimaryKey(id);
		if (spInfo == null) {
			return new ResponseRestEntity<SpUrl>(HttpRestStatus.NOT_FOUND);
		}

		spUrlService.deleteByPrimaryKey(id);
		//删除日志开始
		SpUrl delBean = new SpUrl();
		delBean.setSpUrlId(id);             
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.SP);
		//删除日志结束
		return new ResponseRestEntity<SpUrl>(HttpRestStatus.NO_CONTENT);
	}
}
