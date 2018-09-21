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
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.StSuccRatioService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.StSuccRatio;
import com.zbensoft.mmsmp.db.domain.StSuccRatioKey;
import com.zbensoft.mmsmp.db.domain.UserRecv;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/stSuccRatio")
@RestController
public class StSuccRatioController {
	@Autowired
	StSuccRatioService stSuccRatioService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	ProvinceCityService provinceCityService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_PFSR_Q')")
	@ApiOperation(value = "Query stSuccRatio，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<StSuccRatio>> selectPage(@RequestParam(required = false) String province,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String stTimeStart,@RequestParam(required = false) String stTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		StSuccRatio stSuccRatio = new StSuccRatio();
		stSuccRatio.setProvince(province);
		stSuccRatio.setProductInfoId(productInfoId);
		stSuccRatio.setSpInfoId(spInfoId);
		stSuccRatio.setStTimeStart(stTimeStart);
		stSuccRatio.setStTimeEnd(stTimeEnd);
		int count = stSuccRatioService.count(stSuccRatio);
		if (count == 0) {
			return new ResponseRestEntity<List<StSuccRatio>>(new ArrayList<StSuccRatio>(), HttpRestStatus.NOT_FOUND);
		}
		List<StSuccRatio> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = stSuccRatioService.selectPage(stSuccRatio);

		} else {
			list = stSuccRatioService.selectPage(stSuccRatio);
		}
		
		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<StSuccRatio>>(new ArrayList<StSuccRatio>(), HttpRestStatus.NOT_FOUND);
		}
		List<StSuccRatio> listNew = new ArrayList();
		for (StSuccRatio stSuccRatio2 : list) {
			ProductInfo productInfo = productInfoService.selectByPrimaryKey(stSuccRatio2.getProductInfoId());
			if(productInfo!=null){
				stSuccRatio2.setProductName(productInfo.getProductName());
			}
			ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(stSuccRatio2.getProvince());
			if(provinceCity!=null){
				stSuccRatio2.setProvinceName(provinceCity.getProvinceCityName());
			}
			stSuccRatio2.setUserNum(stSuccRatio2.getMmsUserNum() + stSuccRatio2.getSmsUserNum());
			Double userSuccRate = (stSuccRatio2.getSmsUserNum()+stSuccRatio2.getMmsUserNum())/(stSuccRatio2.getSendSmsNum()+stSuccRatio2.getSendMmsNum());
			stSuccRatio2.setUserSuccRate(Math.round(userSuccRate*100)/100.0);
			listNew.add(stSuccRatio2);
		}
		return new ResponseRestEntity<List<StSuccRatio>>(listNew, HttpRestStatus.OK, count, count);
	}

//	@PreAuthorize("hasRole('R_UR_Q')")
	@ApiOperation(value = "Query UserRecv", notes = "")
	@RequestMapping(value = "/{province}/{productInfoId}/{spInfoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<StSuccRatio> selectByPrimaryKey(@PathVariable("province") String province,@PathVariable("productInfoId") String productInfoId,@PathVariable("spInfoId") String spInfoId) {
		StSuccRatioKey stSuccRatioKey = new StSuccRatioKey();
		stSuccRatioKey.setProvince(province);
		stSuccRatioKey.setProductInfoId(productInfoId);
		stSuccRatioKey.setSpInfoId(spInfoId);
		StSuccRatio stSuccRatio = stSuccRatioService.selectByPrimaryKey(stSuccRatioKey);
		if (stSuccRatio == null) {
			return new ResponseRestEntity<StSuccRatio>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<StSuccRatio>(stSuccRatio, HttpRestStatus.OK);
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Add UserRecv", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserRecv(@Valid @RequestBody StSuccRatio stSuccRatio, BindingResult result, UriComponentsBuilder ucBuilder) {
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		stSuccRatioService.insert(stSuccRatio);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, stSuccRatio,CommonLogImpl.STATISTICS);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/stSuccRatio/{province}").buildAndExpand(stSuccRatio.getProvince()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userRecv.create.created.message"));
	}

	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit UserRecv", notes = "")
	@RequestMapping(value = "{province}/{productInfoId}/{spInfoId}", method = RequestMethod.PUT)
	public ResponseRestEntity<StSuccRatio> updateUserRecv(@PathVariable("province") String province,@PathVariable("productInfoId") String productInfoId,@PathVariable("spInfoId") String spInfoId, 
			@Valid @RequestBody StSuccRatio stSuccRatio, BindingResult result) {

		StSuccRatioKey stSuccRatioKey = new StSuccRatioKey();
		stSuccRatioKey.setProductInfoId(productInfoId);
		stSuccRatioKey.setProvince(province);
		stSuccRatioKey.setSpInfoId(spInfoId);
		StSuccRatio currentStSuccRatio = stSuccRatioService.selectByPrimaryKey(stSuccRatioKey);
		if (currentStSuccRatio == null) {
			return new ResponseRestEntity<StSuccRatio>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentStSuccRatio.setMmsGatewayNum(stSuccRatio.getMmsGatewayNum());
		currentStSuccRatio.setMmsUserNum(stSuccRatio.getMmsUserNum());
		currentStSuccRatio.setRecvSmsNum(stSuccRatio.getRecvSmsNum());
		currentStSuccRatio.setSendMmsNum(stSuccRatio.getSendMmsNum());
		currentStSuccRatio.setSendSmsNum(stSuccRatio.getSendSmsNum());
		currentStSuccRatio.setSmsGatewayNum(stSuccRatio.getSmsGatewayNum());
		currentStSuccRatio.setSmsUserNum(stSuccRatio.getSmsUserNum());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<StSuccRatio>(currentStSuccRatio,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		stSuccRatioService.updateByPrimaryKey(currentStSuccRatio);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentStSuccRatio,CommonLogImpl.STATISTICS);
		return new ResponseRestEntity<StSuccRatio>(currentStSuccRatio, HttpRestStatus.OK,localeMessageSourceService.getMessage("UserRecv.update.ok.message"));
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit Part UserRecv", notes = "")
	@RequestMapping(value = "{province}/{productInfoId}/{spInfoId}", method = RequestMethod.PATCH)
	public ResponseRestEntity<StSuccRatio> updateUserRecv(@PathVariable("province") String province,@PathVariable("productInfoId") String productInfoId,@PathVariable("spInfoId") String spInfoId, 
		@RequestBody StSuccRatio stSuccRatio) {

		StSuccRatioKey stSuccRatioKey = new StSuccRatioKey();
		stSuccRatioKey.setProductInfoId(productInfoId);
		stSuccRatioKey.setProvince(province);
		stSuccRatioKey.setSpInfoId(spInfoId);
		StSuccRatio currentStSuccRatio = stSuccRatioService.selectByPrimaryKey(stSuccRatioKey);
		if (currentStSuccRatio == null) {
			return new ResponseRestEntity<StSuccRatio>(HttpRestStatus.NOT_FOUND);
		}
		
		currentStSuccRatio.setMmsGatewayNum(stSuccRatio.getMmsGatewayNum());
		currentStSuccRatio.setMmsUserNum(stSuccRatio.getMmsUserNum());
		currentStSuccRatio.setRecvSmsNum(stSuccRatio.getRecvSmsNum());
		currentStSuccRatio.setSendMmsNum(stSuccRatio.getSendMmsNum());
		currentStSuccRatio.setSendSmsNum(stSuccRatio.getSendSmsNum());
		currentStSuccRatio.setSmsGatewayNum(stSuccRatio.getSmsGatewayNum());
		currentStSuccRatio.setSmsUserNum(stSuccRatio.getSmsUserNum());
		//修改日志
		stSuccRatioService.updateByPrimaryKey(currentStSuccRatio);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentStSuccRatio,CommonLogImpl.STATISTICS);
		return new ResponseRestEntity<StSuccRatio>(currentStSuccRatio, HttpRestStatus.OK);
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Delete UserRecv", notes = "")
	@RequestMapping(value = "/{province}/{productInfoId}/{spInfoId}", method = RequestMethod.DELETE)
	public ResponseRestEntity<StSuccRatio> deleteUserRecv(@PathVariable("province") String province,@PathVariable("productInfoId") String productInfoId,@PathVariable("spInfoId") String spInfoId) {

		StSuccRatioKey stSuccRatioKey = new StSuccRatioKey();
		stSuccRatioKey.setProductInfoId(productInfoId);
		stSuccRatioKey.setProvince(province);
		stSuccRatioKey.setSpInfoId(spInfoId);
		StSuccRatio stSuccRatio = stSuccRatioService.selectByPrimaryKey(stSuccRatioKey);
		if (stSuccRatio == null) {
			return new ResponseRestEntity<StSuccRatio>(HttpRestStatus.NOT_FOUND);
		}

		stSuccRatioService.deleteByPrimaryKey(stSuccRatioKey);
		//删除日志开始
		StSuccRatioKey delBean = new StSuccRatioKey();
		delBean.setProductInfoId(productInfoId);
		delBean.setProvince(province);
		delBean.setSpInfoId(spInfoId);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.STATISTICS);
		//删除日志结束
		return new ResponseRestEntity<StSuccRatio>(HttpRestStatus.NO_CONTENT);
	}
}
