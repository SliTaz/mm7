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
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.domain.SpInfo;

import io.swagger.annotations.ApiOperation;


@RequestMapping(value = "/spAccess")
@RestController
public class SpAccessController {
	@Autowired
	SpAccessService spAccessService;
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_SPA_Q')")
	@ApiOperation(value = "Query SpAccess，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<SpAccess>> selectPage(
			@RequestParam(required = false) String id,@RequestParam(required = false) String spAccessNumberExtend,@RequestParam(required = false) String spAccessNumber,
			@RequestParam(required = false) String companyName,@RequestParam(required = false) String companyCode,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		SpAccess SpAccess = new SpAccess();
		
		if ((companyCode == null || "".equals(companyCode)) && (companyName == null || "".equals(companyName))) {
			SpAccess.setSpInfoId(id);
			SpAccess.setSpAccessNumberExtend(spAccessNumberExtend);
			SpAccess.setSpAccessNumber(spAccessNumber);
			List<SpAccess> list = new ArrayList<SpAccess>();
			// 分页 start
			if (start != null && length != null) {// 需要进行分页
				/*
				 * 第一个参数是第几页；第二个参数是每页显示条数。
				 */
				int pageNum = PageHelperUtil.getPageNum(start, length);
				int pageSize = PageHelperUtil.getPageSize(start, length);
				PageHelper.startPage(pageNum, pageSize);
				list = spAccessService.selectPage(SpAccess);

			} else {
				list = spAccessService.selectPage(SpAccess);
			}

			int count = spAccessService.count(SpAccess);
			// 分页 end

			if (list == null || list.isEmpty()) {
				return new ResponseRestEntity<List<SpAccess>>(new ArrayList<SpAccess>(), HttpRestStatus.NOT_FOUND);
			}
			
			List<SpAccess> listNew = new ArrayList<SpAccess>();
			for (SpAccess bean : list) {
				SpInfo bankInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
				if (bankInfo != null) {
					bean.setCompanyCode(bankInfo.getCompanyCode());
					bean.setCompanyName(bankInfo.getCompanyName());
				}
				ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getSpCity());
				if (provinceCity != null) {
					bean.setProvinceCityName(provinceCity.getProvinceCityName());
				}
				listNew.add(bean);
			}

		    return new ResponseRestEntity<List<SpAccess>>(listNew, HttpRestStatus.OK,count,count);
		}else{
			SpInfo spInfo= new SpInfo();
			spInfo.setCompanyCode(companyCode);
			spInfo.setCompanyName(companyName);
			List<SpInfo> spInfoList = spInfoService.selectPage(spInfo);
			if (spInfoList.size()==0) {
				return new ResponseRestEntity<List<SpAccess>>(new ArrayList<SpAccess>(), HttpRestStatus.NOT_FOUND);
			} else {
				List<SpAccess> listNews = new ArrayList<SpAccess>();
				int count=0;
				for (SpInfo spInfo2 : spInfoList) {
					SpAccess.setSpInfoId(spInfo2.getSpInfoId());
					SpAccess.setSpAccessNumberExtend(spAccessNumberExtend);
					SpAccess.setSpAccessNumber(spAccessNumber);
					List<SpAccess> list = new ArrayList<SpAccess>();
					// 分页 start
					if (start != null && length != null) {// 需要进行分页
						/*
						 * 第一个参数是第几页；第二个参数是每页显示条数。
						 */
						int pageNum = PageHelperUtil.getPageNum(start, length);
						int pageSize = PageHelperUtil.getPageSize(start, length);
						PageHelper.startPage(pageNum, pageSize);
						list = spAccessService.selectPage(SpAccess);

					} else {
						list = spAccessService.selectPage(SpAccess);
					}

					count = spAccessService.count(SpAccess);
					// 分页 end
					
					List<SpAccess> listNew = new ArrayList<SpAccess>();
					for (SpAccess bean : list) {
						SpInfo bankInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
						if (bankInfo != null) {
							bean.setCompanyCode(bankInfo.getCompanyCode());
							bean.setCompanyName(bankInfo.getCompanyName());
						}
						ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getSpCity());
						if (provinceCity != null) {
							bean.setProvinceCityName(provinceCity.getProvinceCityName());
						}
						listNew.add(bean);
					}
					listNews.addAll(listNew);
				}
				return new ResponseRestEntity<List<SpAccess>>(listNews, HttpRestStatus.OK,count,count);
			}
		}
	}

	//查询通知
	@PreAuthorize("hasRole('R_SPA_Q')")
	@ApiOperation(value = "Query SpAccess", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<SpAccess> selectByPrimaryKey(@PathVariable("id") String id) {
		SpAccess spAccess = spAccessService.selectByPrimaryKey(id);
		if (spAccess == null) {
			return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<SpAccess>(spAccess, HttpRestStatus.OK);
	}

	//新增通知
	@PreAuthorize("hasRole('R_SPA_E')")
	@ApiOperation(value = "Add SpAccess", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody SpAccess SpAccess, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		SpAccess bean = spAccessService.selectByPrimaryKey(SpAccess.getSpInfoId());
		if (bean !=null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT,localeMessageSourceService.getMessage("common.create.conflict.message"));
		}
		
		spAccessService.insert(SpAccess);
		//新增日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, SpAccess,CommonLogImpl.SP);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/SpAccess/{id}").buildAndExpand(SpAccess.getSpInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	//修改通知信息
	@PreAuthorize("hasRole('R_SPA_E')")
	@ApiOperation(value = "Edit SpAccess", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<SpAccess> update(@PathVariable("id") String id, @Valid @RequestBody SpAccess spAccess, BindingResult result) {

		SpAccess currentSpAccess = spAccessService.selectByPrimaryKey(id);

		if (currentSpAccess == null) {
			return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentSpAccess.setSpInfoId(id);
		currentSpAccess.setEffTime(spAccess.getEffTime());
		currentSpAccess.setExpTime(spAccess.getExpTime());
		currentSpAccess.setIsTrust(spAccess.getIsTrust());
		currentSpAccess.setOrderKey(spAccess.getOrderKey());
		currentSpAccess.setReportMessageUrl(spAccess.getReportMessageUrl());
		currentSpAccess.setSpAccessNumber(spAccess.getSpAccessNumber());
		currentSpAccess.setSpAccessNumberExtend(spAccess.getSpAccessNumberExtend());
		currentSpAccess.setSpCity(spAccess.getSpCity());
		currentSpAccess.setSpOrderNotifyUrl(spAccess.getSpOrderNotifyUrl());
		currentSpAccess.setSynOrderFunc(spAccess.getSynOrderFunc());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<SpAccess>(currentSpAccess,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		
		spAccessService.updateByPrimaryKey(currentSpAccess);
		//修改日志
        CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentSpAccess,CommonLogImpl.SP);
		return new ResponseRestEntity<SpAccess>(currentSpAccess, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}


	//删除指定通知
	@PreAuthorize("hasRole('R_SPA_E')")
	@ApiOperation(value = "Delete SpAccess", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<SpAccess> delete(@PathVariable("id") String id) {

		SpAccess spAccess = spAccessService.selectByPrimaryKey(id);
		if (spAccess == null) {
			return new ResponseRestEntity<SpAccess>(HttpRestStatus.NOT_FOUND);
		}

		spAccessService.deleteByPrimaryKey(id);
		//删除日志开始
		SpAccess delBean = new SpAccess();
		delBean.setSpInfoId(id);             
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.SP);
		//删除日志结束
		return new ResponseRestEntity<SpAccess>(HttpRestStatus.NO_CONTENT);
	}
}
