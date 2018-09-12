package com.zbensoft.mmsmp.api.control;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import io.swagger.annotations.ApiOperation;


@RequestMapping(value = "/province")
@RestController
public class ProvinceManagerController {
	@Autowired
	ProvinceCityService provinceCityService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;	
	
	
	@PreAuthorize("hasRole('R_PM_Q')")
	@ApiOperation(value = "Query ProvinceCity, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProvinceCity>> selectPage(@RequestParam(required = false) String parentProvinceCityId, @RequestParam(required = false) String provinceCityId,@RequestParam(required = false) String provinceCityName, 
		@RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		ProvinceCity provinceCity = new ProvinceCity();
		provinceCity.setParentProvinceCityId("-1");
	
		    if(provinceCityName!=null)
		    {
			try {
				provinceCityName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				provinceCityName=URLDecoder.decode(provinceCityName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		provinceCity.setProvinceCityId(provinceCityId);
		provinceCity.setProvinceCityName(provinceCityName);
		

		List<ProvinceCity> list = provinceCityService.selectPage(provinceCity);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = provinceCityService.selectPage(provinceCity);

		} else {
			list = provinceCityService.selectPage(provinceCity);
		}

		int count = provinceCityService.count(provinceCity);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProvinceCity>>(new ArrayList<ProvinceCity>(), HttpRestStatus.NOT_FOUND);
		}

		return new ResponseRestEntity<List<ProvinceCity>>(list, HttpRestStatus.OK, count, count);
	}
	@PreAuthorize("hasRole('R_PM_Q')")
	@ApiOperation(value = "Query Province", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<ProvinceCity> selectByPrimaryKey(@PathVariable("id") String id) {
		ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(id);
		if (provinceCity == null) {
			return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<ProvinceCity>(provinceCity, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_PM_E')")
	@ApiOperation(value = "Add Province", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProvinceCity(@Valid @RequestBody ProvinceCity provinceCity, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		if(provinceCity.getProvinceCityId().equals("-1"))
		{
			return new ResponseRestEntity<Void>(HttpRestStatus.PROVINCECITYNAME_ILLEGA_EXIST);
		}
		
		ProvinceCity bean1 = provinceCityService.selectByPrimaryKey(provinceCity.getProvinceCityId());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
		  
		ProvinceCity bean2 = new ProvinceCity();
	    bean2.setProvinceCityName(provinceCity.getProvinceCityName());
	    List<ProvinceCity> isExistList = provinceCityService.selectPage(bean2);
	    if((isExistList!=null && isExistList.size()>0)){
			return new ResponseRestEntity<Void>(HttpRestStatus.PROVINCECITYNAME_IS_EXIST);
	    }
	    provinceCity.setParentProvinceCityId("-1");
	    provinceCityService.insert(provinceCity);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/province/{id}").buildAndExpand(provinceCity.getParentProvinceCityId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
	@PreAuthorize("hasRole('R_PM_E')")
	@ApiOperation(value = "Edit Province ", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<ProvinceCity> updateProvinceCity(@PathVariable("id") String id, @Valid @RequestBody ProvinceCity bean, BindingResult result) {
		ProvinceCity currentProvinceCity = provinceCityService.selectByPrimaryKey(id);
		if (currentProvinceCity == null) {
			return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.NOT_FOUND);
		}	
		if(!bean.getParentProvinceCityId().equals("-1"))
		{
			
				return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.NOT_FOUND);	
		}	
		ProvinceCity bean2 = new ProvinceCity();
	    bean2.setProvinceCityName(bean.getProvinceCityName());
	    List<ProvinceCity> isExistList = provinceCityService.selectPage(bean2);
	    if(isExistList.size()==1)
	    {
	    	 if(!bean.getProvinceCityId().equals(isExistList.get(0).getProvinceCityId()))
	    	 {
	    		 return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.PROVINCECITYNAME_IS_EXIST);
	    	 }
	    }
		currentProvinceCity.setParentProvinceCityId("-1");
		currentProvinceCity.setProvinceCityId(bean.getProvinceCityId());
		currentProvinceCity.setProvinceCityName(bean.getProvinceCityName());		
		provinceCityService.updateByPrimaryKey(currentProvinceCity);
		return new ResponseRestEntity<ProvinceCity>(currentProvinceCity, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
	}
	private void deleProvinceCity(ProvinceCity bean )
	{  ProvinceCity bean2 = new ProvinceCity();
       bean2.setParentProvinceCityId(bean.getProvinceCityId());
       List<ProvinceCity> isExistList = provinceCityService.selectPage(bean2);  
       if((isExistList!=null && isExistList.size()>0)){
	    	 for(ProvinceCity beans : isExistList){     
	    		 provinceCityService.deleteByPrimaryKey(beans.getProvinceCityId());
	    }
	    	}
    	   provinceCityService.deleteByPrimaryKey(bean.getProvinceCityId());
       
		
	}
	@PreAuthorize("hasRole('R_PM_E')")
	@ApiOperation(value = "Delete Province", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ProvinceCity> deleteUserClap(@PathVariable("id") String id) {

		ProvinceCity currentProvinceCity = provinceCityService.selectByPrimaryKey(id);
		if (currentProvinceCity == null) {
			return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.NOT_FOUND);
		}
         deleProvinceCity(currentProvinceCity);
		return new ResponseRestEntity<ProvinceCity>(HttpRestStatus.NO_CONTENT);
	}
	

}
