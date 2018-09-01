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
import com.zbensoft.mmsmp.api.service.api.CpInfoService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.CpInfo;
import com.zbensoft.mmsmp.db.domain.SpInfo;

import io.swagger.annotations.ApiOperation;


@RequestMapping(value = "/cpInfo")
@RestController
public class CpInfoController {
	@Autowired
	CpInfoService cpInfoService;
	
	@Autowired
	SpInfoService spInfoService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_CPIM_Q')")
	@ApiOperation(value = "Query CpInfo，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<CpInfo>> selectPage(@RequestParam(required = false) String cpInfoId,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String cpName,@RequestParam(required = false) String cpAddr,@RequestParam(required = false) String legalPerson,
			@RequestParam(required = false) String bankName,@RequestParam(required = false) String webAddr,@RequestParam(required = false) String bankAccount,@RequestParam(required = false) String faxNo,
			@RequestParam(required = false) String businessPerson,@RequestParam(required = false) String businessTel,@RequestParam(required = false) String businessMobile,@RequestParam(required = false) String businessEmail,
			@RequestParam(required = false) String customerPerson,@RequestParam(required = false) String customerTel,@RequestParam(required = false) String customerMobile,@RequestParam(required = false) String customerEmail,
			@RequestParam(required = false) String loginUser,@RequestParam(required = false) Integer status,@RequestParam(required = false) Integer isEnable,@RequestParam(required = false) String registerEmail,
			@RequestParam(required = false) String cpCode,@RequestParam(required = false) String rejectReason,@RequestParam(required = false) String tecPerson,@RequestParam(required = false) String tecTel,
			@RequestParam(required = false) String tecMobile,@RequestParam(required = false) String tecEmail,@RequestParam(required = false) String financePerson,@RequestParam(required = false) String financeTel,
			@RequestParam(required = false) String financeMobile,@RequestParam(required = false) String financeEmail,@RequestParam(required = false) String cpLogo,@RequestParam(required = false) Integer cpType,
			@RequestParam(required = false) Double proportion,@RequestParam(required = false) String aear,@RequestParam(required = false) Integer type,@RequestParam(required = false) String cpAccessId,
			@RequestParam(required = false) String cpAccessIdExtend,@RequestParam(required = false) String cpAccessUrl, @RequestParam(required = false) String auditTimeStart, @RequestParam(required = false) String auditTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		CpInfo cpInfo = new CpInfo();
		cpInfo.setCpInfoId(cpInfoId);
		cpInfo.setSpInfoId(spInfoId);
		cpInfo.setCpName(cpName);
		cpInfo.setCpAddr(cpAddr);
		cpInfo.setLegalPerson(legalPerson);
		cpInfo.setBankName(bankName);
		cpInfo.setWebAddr(webAddr);
		cpInfo.setBankAccount(bankAccount);
		cpInfo.setFaxNo(faxNo);
		cpInfo.setBusinessEmail(businessEmail);
		cpInfo.setBusinessPerson(businessPerson);
		cpInfo.setBusinessMobile(businessMobile);
		cpInfo.setBusinessTel(businessTel);
		cpInfo.setCustomerPerson(customerPerson);
		cpInfo.setCustomerTel(customerTel);
		cpInfo.setCustomerMobile(customerMobile);
		cpInfo.setCustomerEmail(customerEmail);
		cpInfo.setLoginUser(loginUser);
		cpInfo.setStatus(status);
		cpInfo.setIsEnable(isEnable);
		cpInfo.setRegisterEmail(registerEmail);
		cpInfo.setCpCode(cpCode);
		cpInfo.setRejectReason(rejectReason);
		cpInfo.setTecPerson(tecPerson);
		cpInfo.setTecTel(tecTel);
		cpInfo.setTecMobile(tecMobile);
		cpInfo.setTecEmail(tecEmail);
		cpInfo.setFinancePerson(financePerson);
		cpInfo.setFinanceTel(financeTel);
		cpInfo.setFinanceMobile(financeMobile);
		cpInfo.setFinanceEmail(financeEmail);
		cpInfo.setCpLogo(cpLogo);
		cpInfo.setCpType(cpType);
		cpInfo.setProportion(proportion);
		cpInfo.setAear(aear);
		cpInfo.setType(type);
		cpInfo.setCpAccessId(cpAccessIdExtend);
		cpInfo.setCpAccessIdExtend(cpAccessIdExtend);
		cpInfo.setCpAccessUrl(cpAccessUrl);
		cpInfo.setAuditTimeStart(auditTimeStart);
		cpInfo.setAuditTimeEnd(auditTimeEnd);
		int count = cpInfoService.count(cpInfo);
		if (count == 0) {
			return new ResponseRestEntity<List<CpInfo>>(new ArrayList<CpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		List<CpInfo> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = cpInfoService.selectPage(cpInfo);

		} else {
			list = cpInfoService.selectPage(cpInfo);
		}
		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<CpInfo>>(new ArrayList<CpInfo>(), HttpRestStatus.NOT_FOUND);
		}
		List<CpInfo> listNew = new ArrayList<CpInfo>();
		for(CpInfo cpInfoNew : list){
			SpInfo spInfo = spInfoService.selectByPrimaryKey(cpInfoNew.getSpInfoId());
			if(spInfo!=null){
				cpInfoNew.setSpName(spInfo.getCompanyName());
			}
			listNew.add(cpInfoNew);
		}
		return new ResponseRestEntity<List<CpInfo>>(listNew, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Query CpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<CpInfo> selectByPrimaryKey(@PathVariable("id") String id) {
		CpInfo cpInfo = cpInfoService.selectByPrimaryKey(id);
		if (cpInfo == null) {
			return new ResponseRestEntity<CpInfo>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<CpInfo>(cpInfo, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Add CpInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createCpInfo(@Valid @RequestBody CpInfo cpInfo, BindingResult result, UriComponentsBuilder ucBuilder) {
		cpInfo.setCpInfoId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_GAS_COUPONID));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		cpInfoService.insert(cpInfo);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, cpInfo,CommonLogImpl.CP_INFO);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/cpInfo/{id}").buildAndExpand(cpInfo.getCpInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("cpInfo.create.created.message"));
	}

	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Edit CpInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<CpInfo> updateCpInfo(@PathVariable("id") String id, @Valid @RequestBody CpInfo cpInfo, BindingResult result) {

		CpInfo currentCpInfo = cpInfoService.selectByPrimaryKey(id);

		if (currentCpInfo == null) {
			return new ResponseRestEntity<CpInfo>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentCpInfo.setSpInfoId(cpInfo.getSpInfoId());
		currentCpInfo.setCpName(cpInfo.getCpName());
		currentCpInfo.setCpAddr(cpInfo.getCpAddr());
		currentCpInfo.setLegalPerson(cpInfo.getLegalPerson());
		currentCpInfo.setBankName(cpInfo.getBankName());
		currentCpInfo.setWebAddr(cpInfo.getWebAddr());
		currentCpInfo.setBankAccount(cpInfo.getBankAccount());
		currentCpInfo.setFaxNo(cpInfo.getFaxNo());
		currentCpInfo.setBusinessEmail(cpInfo.getBusinessEmail());
		currentCpInfo.setBusinessPerson(cpInfo.getBusinessPerson());
		currentCpInfo.setBusinessMobile(cpInfo.getBusinessMobile());
		currentCpInfo.setBusinessTel(cpInfo.getBusinessTel());
		currentCpInfo.setCustomerPerson(cpInfo.getCustomerPerson());
		currentCpInfo.setCustomerTel(cpInfo.getCustomerTel());
		currentCpInfo.setCustomerMobile(cpInfo.getCustomerMobile());
		currentCpInfo.setCustomerEmail(cpInfo.getCustomerEmail());
		currentCpInfo.setLoginUser(cpInfo.getLoginUser());
		currentCpInfo.setStatus(cpInfo.getStatus());
		currentCpInfo.setIsEnable(cpInfo.getIsEnable());
		currentCpInfo.setRegisterEmail(cpInfo.getRegisterEmail());
		currentCpInfo.setCpCode(cpInfo.getCpCode());
		currentCpInfo.setRejectReason(cpInfo.getRejectReason());
		currentCpInfo.setTecPerson(cpInfo.getTecPerson());
		currentCpInfo.setTecTel(cpInfo.getTecTel());
		currentCpInfo.setTecMobile(cpInfo.getTecMobile());
		currentCpInfo.setTecEmail(cpInfo.getTecEmail());
		currentCpInfo.setFinancePerson(cpInfo.getFinancePerson());
		currentCpInfo.setFinanceTel(cpInfo.getFinanceTel());
		currentCpInfo.setFinanceMobile(cpInfo.getFinanceMobile());
		currentCpInfo.setFinanceEmail(cpInfo.getFinanceEmail());
		currentCpInfo.setCpLogo(cpInfo.getCpLogo());
		currentCpInfo.setCpType(cpInfo.getCpType());
		currentCpInfo.setProportion(cpInfo.getProportion());
		currentCpInfo.setAear(cpInfo.getAear());
		currentCpInfo.setType(cpInfo.getType());
		currentCpInfo.setCpAccessId(cpInfo.getCpAccessId());
		currentCpInfo.setCpAccessIdExtend(cpInfo.getCpAccessIdExtend());
		currentCpInfo.setCpAccessUrl(cpInfo.getCpAccessUrl());
		currentCpInfo.setAuditTime(cpInfo.getAuditTime());
		currentCpInfo.setRegisterTime(cpInfo.getRegisterTime());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<CpInfo>(currentCpInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		cpInfoService.updateByPrimaryKey(currentCpInfo);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentCpInfo,CommonLogImpl.CP_INFO);
		return new ResponseRestEntity<CpInfo>(currentCpInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("cpInfo.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Edit Part CpInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<CpInfo> updateCpInfo(@PathVariable("id") String id, @RequestBody CpInfo cpInfo) {

		CpInfo currentCpInfo =cpInfoService.selectByPrimaryKey(id);

		if (currentCpInfo == null) {
			return new ResponseRestEntity<CpInfo>(HttpRestStatus.NOT_FOUND);
		}
		currentCpInfo.setSpInfoId(cpInfo.getSpInfoId());
		currentCpInfo.setCpName(cpInfo.getCpName());
		currentCpInfo.setCpAddr(cpInfo.getCpAddr());
		currentCpInfo.setLegalPerson(cpInfo.getLegalPerson());
		currentCpInfo.setBankName(cpInfo.getBankName());
		currentCpInfo.setWebAddr(cpInfo.getWebAddr());
		currentCpInfo.setBankAccount(cpInfo.getBankAccount());
		currentCpInfo.setFaxNo(cpInfo.getFaxNo());
		currentCpInfo.setBusinessEmail(cpInfo.getBusinessEmail());
		currentCpInfo.setBusinessPerson(cpInfo.getBusinessPerson());
		currentCpInfo.setBusinessMobile(cpInfo.getBusinessMobile());
		currentCpInfo.setBusinessTel(cpInfo.getBusinessTel());
		currentCpInfo.setCustomerPerson(cpInfo.getCustomerPerson());
		currentCpInfo.setCustomerTel(cpInfo.getCustomerTel());
		currentCpInfo.setCustomerMobile(cpInfo.getCustomerMobile());
		currentCpInfo.setCustomerEmail(cpInfo.getCustomerEmail());
		currentCpInfo.setLoginUser(cpInfo.getLoginUser());
		currentCpInfo.setStatus(cpInfo.getStatus());
		currentCpInfo.setIsEnable(cpInfo.getIsEnable());
		currentCpInfo.setRegisterEmail(cpInfo.getRegisterEmail());
		currentCpInfo.setCpCode(cpInfo.getCpCode());
		currentCpInfo.setRejectReason(cpInfo.getRejectReason());
		currentCpInfo.setTecPerson(cpInfo.getTecPerson());
		currentCpInfo.setTecTel(cpInfo.getTecTel());
		currentCpInfo.setTecMobile(cpInfo.getTecMobile());
		currentCpInfo.setTecEmail(cpInfo.getTecEmail());
		currentCpInfo.setFinancePerson(cpInfo.getFinancePerson());
		currentCpInfo.setFinanceTel(cpInfo.getFinanceTel());
		currentCpInfo.setFinanceMobile(cpInfo.getFinanceMobile());
		currentCpInfo.setFinanceEmail(cpInfo.getFinanceEmail());
		currentCpInfo.setCpLogo(cpInfo.getCpLogo());
		currentCpInfo.setCpType(cpInfo.getCpType());
		currentCpInfo.setProportion(cpInfo.getProportion());
		currentCpInfo.setAear(cpInfo.getAear());
		currentCpInfo.setType(cpInfo.getType());
		currentCpInfo.setCpAccessId(cpInfo.getCpAccessId());
		currentCpInfo.setCpAccessIdExtend(cpInfo.getCpAccessIdExtend());
		currentCpInfo.setCpAccessUrl(cpInfo.getCpAccessUrl());
		currentCpInfo.setAuditTime(cpInfo.getAuditTime());
		currentCpInfo.setRegisterTime(cpInfo.getRegisterTime());
		//修改日志
		cpInfoService.updateByPrimaryKeySelective(currentCpInfo);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentCpInfo,CommonLogImpl.CP_INFO);
		return new ResponseRestEntity<CpInfo>(currentCpInfo, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Delete CpInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<CpInfo> deleteCpInfo(@PathVariable("id") String id) {

		CpInfo cpInfo = cpInfoService.selectByPrimaryKey(id);
		if (cpInfo == null) {
			return new ResponseRestEntity<CpInfo>(HttpRestStatus.NOT_FOUND);
		}

		cpInfoService.deleteByPrimaryKey(id);
		//删除日志开始
		CpInfo delBean = new CpInfo();
		delBean.setCpInfoId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CP_INFO);
		//删除日志结束
		return new ResponseRestEntity<CpInfo>(HttpRestStatus.NO_CONTENT);
	}
}
