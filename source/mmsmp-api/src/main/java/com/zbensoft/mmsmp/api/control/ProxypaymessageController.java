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
import com.zbensoft.mmsmp.api.service.api.ProxypaymessageService;
import com.zbensoft.mmsmp.db.domain.Proxypaymessage;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/proxypaymessage")
@RestController
public class ProxypaymessageController {
	@Autowired
	ProxypaymessageService  proxypaymessageService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query Proxypaymessage, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<Proxypaymessage>> selectPage(@RequestParam(required = false) String globalmessageid, @RequestParam(required = false) String phonenumber,
			                                                    @RequestParam(required = false) String cpid, @RequestParam(required = false) String serviceid, 
			                                                    @RequestParam(required = false) String servicename,@RequestParam(required = false) String feetype, 
			                                                    @RequestParam(required = false) String fee, @RequestParam(required = false) String accountid,
			                                                    @RequestParam(required = false) String status, @RequestParam(required = false) String validatecode, 
			                                                    @RequestParam(required = false) String usertype,@RequestParam(required = false) String spid,
			                                                    @RequestParam(required = false) String perorid, @RequestParam(required = false) String productid,
			                                                    @RequestParam(required = false) String chargeparty, @RequestParam(required = false) String createdate, 
			                                                    @RequestParam(required = false) String updatedate,@RequestParam(required = false) String extendfiled1, 
			                                                    @RequestParam(required = false) String extendfiiled2, @RequestParam(required = false) String productname,
			                                                    @RequestParam(required = false) String sourceType, 
			                                                    @RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		Proxypaymessage currentProxypaymessage = new Proxypaymessage();
		currentProxypaymessage.setAccountid(accountid);
		currentProxypaymessage.setChargeparty(chargeparty);
		currentProxypaymessage.setCpid(cpid);
		currentProxypaymessage.setCreatedate(createdate);
		currentProxypaymessage.setExtendfiiled2(extendfiiled2);
		
		currentProxypaymessage.setExtendfiled1(extendfiled1);
		currentProxypaymessage.setFee(fee);
		currentProxypaymessage.setFeetype(feetype);
		currentProxypaymessage.setPerorid(perorid);
		currentProxypaymessage.setPhonenumber(phonenumber);
		
		currentProxypaymessage.setProductid(productid);
		currentProxypaymessage.setProductname(productname);
		currentProxypaymessage.setServiceid(serviceid);
		currentProxypaymessage.setServicename(servicename);
		currentProxypaymessage.setSourceType(sourceType);
		
		currentProxypaymessage.setSpid(spid);
		currentProxypaymessage.setStatus(status);
		currentProxypaymessage.setUpdatedate(updatedate);
		currentProxypaymessage.setUsertype(usertype);
		currentProxypaymessage.setValidatecode(validatecode);
		
		currentProxypaymessage.setGlobalmessageid(globalmessageid);
		
		List<Proxypaymessage> list = proxypaymessageService.selectPage(currentProxypaymessage);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = proxypaymessageService.selectPage(currentProxypaymessage);

		} else {
			list = proxypaymessageService.selectPage(currentProxypaymessage);
		}

		int count = proxypaymessageService.count(currentProxypaymessage);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<Proxypaymessage>>(new ArrayList<Proxypaymessage>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<Proxypaymessage>>(list, HttpRestStatus.OK, count, count);
	}
	@PreAuthorize("hasRole('R_BWL_Q')")
	@ApiOperation(value = "Query Proxypaymessage", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<Proxypaymessage> selectByPrimaryKey(@PathVariable("id") String id) {
		Proxypaymessage proxypaymessage  = proxypaymessageService.selectByPrimaryKey(id);
		if (proxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<Proxypaymessage>(proxypaymessage, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Add Proxypaymessage", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProxypaymessage(@Valid @RequestBody Proxypaymessage proxypaymessage, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		Proxypaymessage bean1 = proxypaymessageService.selectByPrimaryKey(proxypaymessage.getGlobalmessageid());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}

		
		proxypaymessageService.insert(proxypaymessage);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/proxypaymessage/{id}").buildAndExpand(proxypaymessage.getGlobalmessageid()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Edit Proxypaymessage ", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<Proxypaymessage> updateProxypaymessage(@PathVariable("id") String id, @Valid @RequestBody Proxypaymessage bean, BindingResult result) {
		Proxypaymessage currentProxypaymessage = proxypaymessageService.selectByPrimaryKey(id);
		if (currentProxypaymessage == null) {
			return new ResponseRestEntity<Proxypaymessage>(HttpRestStatus.NOT_FOUND);
		}	
		currentProxypaymessage.setAccountid(bean.getAccountid());
		currentProxypaymessage.setChargeparty(bean.getChargeparty());
		currentProxypaymessage.setCpid(bean.getCpid());
		currentProxypaymessage.setCreatedate(bean.getCreatedate());
		currentProxypaymessage.setExtendfiiled2(bean.getExtendfiiled2());
		
		currentProxypaymessage.setExtendfiled1(bean.getExtendfiled1());
		currentProxypaymessage.setFee(bean.getFee());
		currentProxypaymessage.setFeetype(bean.getFeetype());
		currentProxypaymessage.setPerorid(bean.getPerorid());
		currentProxypaymessage.setPhonenumber(bean.getPhonenumber());
		
		currentProxypaymessage.setProductid(bean.getProductid());
		currentProxypaymessage.setProductname(bean.getProductname());
		currentProxypaymessage.setServiceid(bean.getServiceid());
		currentProxypaymessage.setServicename(bean.getServicename());
		currentProxypaymessage.setSourceType(bean.getSourceType());
		
		currentProxypaymessage.setSpid(bean.getSpid());
		currentProxypaymessage.setStatus(bean.getStatus());
		currentProxypaymessage.setUpdatedate(bean.getUpdatedate());
		currentProxypaymessage.setUsertype(bean.getUsertype());
		currentProxypaymessage.setValidatecode(bean.getValidatecode());
		
		currentProxypaymessage.setGlobalmessageid(bean.getGlobalmessageid());

		proxypaymessageService.updateByPrimaryKey(currentProxypaymessage);
		return new ResponseRestEntity<Proxypaymessage>(currentProxypaymessage, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
	}
	@PreAuthorize("hasRole('R_BWL_E')")
	@ApiOperation(value = "Delete Proxypaymessage", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<Proxypaymessage> deleteProxypaymessage(@PathVariable("id") String id) {

		Proxypaymessage proxypaymessage = proxypaymessageService.selectByPrimaryKey(id);
		if (proxypaymessage == null) {
			return new ResponseRestEntity<>(HttpRestStatus.NOT_FOUND);
		}

		proxypaymessageService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<Proxypaymessage>(HttpRestStatus.NO_CONTENT);
	}
}
