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
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.domain.SpInfo;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/spInfo")
@RestController
public class SpInfoController {
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	SpAccessService spAccessService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_SPI_Q')")
	@ApiOperation(value = "Query SpInfo，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpInfo>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String companyCode,@RequestParam(required = false) String companyName,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) String businessTel,@RequestParam(required = false) String contactPerson,
			@RequestParam(required = false) String faxNo,@RequestParam(required = false) String emailAddr,@RequestParam(required = false) String webAddr,
			@RequestParam(required = false) String officeAddr,@RequestParam(required = false) String province,@RequestParam(required = false) Integer maxCon,
			@RequestParam(required = false) Integer maxFlow,@RequestParam(required = false) Integer serlfSp,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		SpInfo spInfo = new SpInfo();
		spInfo.setSpInfoId(id);
		spInfo.setCompanyCode(companyCode);
		spInfo.setCompanyName(companyName);
		spInfo.setBusinessTel(businessTel);
		spInfo.setContactPerson(contactPerson);
		spInfo.setFaxNo(faxNo);
		spInfo.setEmailAddr(emailAddr);
		spInfo.setWebAddr(webAddr);
		spInfo.setOfficeAddr(officeAddr);
		spInfo.setProvince(province);
		
		if (status != null) {
			spInfo.setStatus(status);
		}
		if (maxCon != null) {
			spInfo.setStatus(maxCon);
		}
		if (maxFlow != null) {
			spInfo.setStatus(maxFlow);
		}
		if (serlfSp != null) {
			spInfo.setStatus(serlfSp);
		}
		List<SpInfo> list = new ArrayList<SpInfo>();
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = spInfoService.selectPage(spInfo);

		} else {
			list = spInfoService.selectPage(spInfo);
		}

		int count = spInfoService.count(spInfo);
		// 分页 end

		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<SpInfo>>(new ArrayList<SpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		
		
		List<SpInfo> listNew = new ArrayList<SpInfo>();
		for (SpInfo bean : list) {
			SpAccess bankInfo = spAccessService.selectByPrimaryKey(bean.getSpInfoId());
			if (bankInfo != null) {
				bean.setSpAccessNumber(bankInfo.getSpAccessNumber());
				bean.setSpAccessNumberExtend(bankInfo.getSpAccessNumberExtend());
			}
			listNew.add(bean);
		}
	    return new ResponseRestEntity<List<SpInfo>>(listNew, HttpRestStatus.OK,count,count);
	}

	//查询通知
	@PreAuthorize("hasRole('R_SPI_Q')")
	@ApiOperation(value = "Query SpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<SpInfo> selectByPrimaryKey(@PathVariable("id") String id) {
		SpInfo SpInfo = spInfoService.selectByPrimaryKey(id);
		if (SpInfo == null) {
			return new ResponseRestEntity<SpInfo>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<SpInfo>(SpInfo, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_SPI_E')")
	@ApiOperation(value = "Add SpInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody SpInfo spInfo, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		SpInfo bean = spInfoService.selectByPrimaryKey(spInfo.getSpInfoId());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		
		spInfo.setCreateTime(PageHelperUtil.getCurrentDate());
		spInfo.setUpdateTime(PageHelperUtil.getCurrentDate());
		spInfoService.insert(spInfo);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, spInfo,CommonLogImpl.SP);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/SpInfo/{id}").buildAndExpand(spInfo.getSpInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_SPI_E')")
	@ApiOperation(value = "Edit SpInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<SpInfo> update(@PathVariable("id") String id, @Valid @RequestBody SpInfo spInfo, BindingResult result) {

		SpInfo currentSpInfo = spInfoService.selectByPrimaryKey(id);

		if (currentSpInfo == null) {
			return new ResponseRestEntity<SpInfo>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentSpInfo.setSpInfoId(id);
		currentSpInfo.setBusinessTel(spInfo.getBusinessTel());
		currentSpInfo.setCompanyCode(spInfo.getCompanyCode());
		currentSpInfo.setCompanyName(spInfo.getCompanyName());
		currentSpInfo.setContactPerson(spInfo.getContactPerson());
		currentSpInfo.setStatus(spInfo.getStatus());
		currentSpInfo.setEmailAddr(spInfo.getEmailAddr());
		currentSpInfo.setFaxNo(spInfo.getFaxNo());
		currentSpInfo.setMaxCon(spInfo.getMaxCon());
		currentSpInfo.setMaxFlow(spInfo.getMaxFlow());
		currentSpInfo.setOfficeAddr(spInfo.getOfficeAddr());
		currentSpInfo.setProvince(spInfo.getProvince());
		currentSpInfo.setRemark(spInfo.getRemark());
		currentSpInfo.setSerlfSp(spInfo.getSerlfSp());
		currentSpInfo.setUpdateTime(PageHelperUtil.getCurrentDate());
		currentSpInfo.setWebAddr(spInfo.getWebAddr());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<SpInfo>(currentSpInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		spInfoService.updateByPrimaryKey(currentSpInfo);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentSpInfo,CommonLogImpl.SP);
		return new ResponseRestEntity<SpInfo>(currentSpInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_SPI_E')")
	@ApiOperation(value = "Delete SpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<SpInfo> delete(@PathVariable("id") String id) {

		SpInfo spInfo = spInfoService.selectByPrimaryKey(id);
		if (spInfo == null) {
			return new ResponseRestEntity<SpInfo>(HttpRestStatus.NOT_FOUND);
		}

		spInfoService.deleteByPrimaryKey(id);
		//删除日志开始
		SpInfo delBean = new SpInfo();
		delBean.setSpInfoId(id);              
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.SP);
		//删除日志结束
		return new ResponseRestEntity<SpInfo>(HttpRestStatus.NO_CONTENT);
	}
}
