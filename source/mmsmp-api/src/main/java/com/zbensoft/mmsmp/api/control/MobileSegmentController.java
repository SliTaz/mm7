package com.zbensoft.mmsmp.api.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.zbensoft.mmsmp.api.common.IDGenerate;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.MobileSegmentService;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.db.domain.MobileSegment;
import com.zbensoft.mmsmp.db.domain.MobileSegmentCityName;


import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/mobileSegment")
@RestController
public class MobileSegmentController {
	private static final Logger log = LoggerFactory.getLogger(MobileSegmentController.class);
	@Autowired
	MobileSegmentService mobileSegmentService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Value("${upload.file.mobile.segment}")
	private String UPLOAD_FILE_MOBILE_SEGMENT;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;	
	@PreAuthorize("hasRole('R_MS_Q')")
	@ApiOperation(value = "Query MobileSegment, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<MobileSegmentCityName>> selectPage(@RequestParam(required = false) String mobileSegmentId, @RequestParam(required = false) String segment,@RequestParam(required = false) String provinceId, 
			@RequestParam(required = false) String cityId,@RequestParam(required = false) String areaCode,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		MobileSegment mobileSegment = new MobileSegment();
		mobileSegment.setMobileSegmentId(mobileSegmentId);
		mobileSegment.setAreaCode(areaCode);
		mobileSegment.setCityId(cityId);
		mobileSegment.setProvinceId(provinceId);
		mobileSegment.setSegment(segment);
	
		 
	
		

		List<MobileSegment> list = mobileSegmentService.selectPage(mobileSegment);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = mobileSegmentService.selectPage(mobileSegment);

		} else {
			list = mobileSegmentService.selectPage(mobileSegment);
		}

		int count = mobileSegmentService.count(mobileSegment);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<MobileSegmentCityName>>(new ArrayList<MobileSegmentCityName>(), HttpRestStatus.NOT_FOUND);
		}
		List<MobileSegmentCityName> returnList = new ArrayList<MobileSegmentCityName>();
		for(MobileSegment temp : list) {
			
			if(temp!=null){
				returnList.add(translate(temp));
			}
			}  
		
		
		
		return new ResponseRestEntity<List<MobileSegmentCityName>>(returnList, HttpRestStatus.OK, count, count);
	}
	
	private MobileSegmentCityName translate(MobileSegment mobileSegment)
	{   MobileSegmentCityName  mobileSegmentCityName = new MobileSegmentCityName();
		if(mobileSegment!=null)
		{ mobileSegmentCityName.setAreaCode(mobileSegment.getAreaCode());
		mobileSegmentCityName.setCityId(mobileSegment.getCityId());
		mobileSegmentCityName.setMobileSegmentId(mobileSegment.getMobileSegmentId());
		mobileSegmentCityName.setProvinceId(mobileSegment.getProvinceId());
		mobileSegmentCityName.setSegment(mobileSegment.getSegment());
		if(provinceCityService.selectByPrimaryKey(mobileSegment.getProvinceId())!=null){
		mobileSegmentCityName.setProvinceName(provinceCityService.selectByPrimaryKey(mobileSegment.getProvinceId()).getProvinceCityName());	
		}
		if(provinceCityService.selectByPrimaryKey(mobileSegment.getCityId())!=null)
		{mobileSegmentCityName.setCityName(provinceCityService.selectByPrimaryKey(mobileSegment.getCityId()).getProvinceCityName());	
		}
		}
		
	   return mobileSegmentCityName;	
	}
//	@PreAuthorize("hasRole('R_CHANNEL_Q')")
	@ApiOperation(value = "Query MobileSegment", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<MobileSegment> selectByPrimaryKey(@PathVariable("id") String id) {
		MobileSegment mobileSegment  = mobileSegmentService.selectByPrimaryKey(id);
		if (mobileSegment == null) {
			return new ResponseRestEntity<MobileSegment>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<MobileSegment>(mobileSegment, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_MS_E')")
	@ApiOperation(value = "Add MobileSegment", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProvinceCity(@Valid @RequestBody MobileSegment mobileSegment, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		MobileSegment bean1 = mobileSegmentService.selectByPrimaryKey(mobileSegment.getMobileSegmentId());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
		
		MobileSegment bean2 = new MobileSegment();
		    bean2.setSegment(mobileSegment.getSegment());
		    List<MobileSegment> isExistList = mobileSegmentService.selectPage(bean2);
		    if((isExistList!=null && isExistList.size()>0)){
				return new ResponseRestEntity<Void>(HttpRestStatus.SEGEMENT_IS_EXIST);
		    }
		
		    mobileSegmentService.insert(mobileSegment);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/mobileSegment/{id}").buildAndExpand(mobileSegment.getMobileSegmentId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
	@PreAuthorize("hasRole('R_MS_E')")
	@ApiOperation(value = "Edit MobileSegment ", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<MobileSegment> updateMobileSegment(@PathVariable("id") String id, @Valid @RequestBody MobileSegment bean, BindingResult result) {
		MobileSegment currentMobileSegment = mobileSegmentService.selectByPrimaryKey(id);
		if (currentMobileSegment == null) {
			return new ResponseRestEntity<MobileSegment>(HttpRestStatus.NOT_FOUND);
		}	
		
		MobileSegment bean2 = new MobileSegment();
	    bean2.setSegment(bean.getSegment());
	    List<MobileSegment> isExistList = mobileSegmentService.selectPage(bean2);
	    if((isExistList.size()==1)){
			if(!isExistList.get(0).getMobileSegmentId().equals(id))
	    	{return new ResponseRestEntity<MobileSegment>(HttpRestStatus.SEGEMENT_IS_EXIST);
	   
	    	}
	    }
	    currentMobileSegment.setAreaCode(bean.getAreaCode());
	    currentMobileSegment.setCityId(bean.getCityId());
	    currentMobileSegment.setMobileSegmentId(bean.getMobileSegmentId());
	    currentMobileSegment.setProvinceId(bean.getProvinceId());
	    currentMobileSegment.setSegment(bean.getSegment());
	    mobileSegmentService.updateByPrimaryKey(currentMobileSegment);
		return new ResponseRestEntity<MobileSegment>(currentMobileSegment, HttpRestStatus.OK, localeMessageSourceService.getMessage("common.update.ok.message"));
	}
	
	@PreAuthorize("hasRole('R_MS_E')")
	@ApiOperation(value = "Delete MobileSegment", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<MobileSegment> deleteMobileSegment(@PathVariable("id") String id) {

		MobileSegment mobileSegment = mobileSegmentService.selectByPrimaryKey(id);
		if (mobileSegment == null) {
			return new ResponseRestEntity<MobileSegment>(HttpRestStatus.NOT_FOUND);
		}

		mobileSegmentService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<MobileSegment>(HttpRestStatus.NO_CONTENT);
	}

	
	//号段导入
	@PreAuthorize("hasRole('R_MS_E')")
	@RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
	public Map<String, Object> singleFileUpload(HttpServletRequest request, @RequestParam("name") String name) throws Exception {
		String filePre = System.currentTimeMillis() + "";
		name = filePre + "_" + name;
		String path = UPLOAD_FILE_MOBILE_SEGMENT;// request.getSession().getServletContext().getRealPath("upload");

		File targetFile = new File(path, name);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		write(path, name, request.getInputStream());

		int import_int = insertToDB(targetFile);

		Map<String, Object> result_map = new HashMap<String, Object>();
		result_map.put("importNum", import_int);
		return result_map;
	}
	private void write(String path, String filename, InputStream in) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.mkdirs()) {// 若创建文件夹不成功
			}
		}

		File targetfile = new File(path + filename);
		OutputStream os = null;
		try {
			os = new FileOutputStream(targetfile);
			int ch = 0;
			while ((ch = in.read()) != -1) {
				os.write(ch);
			}
			os.flush();
		} catch (Exception e) {
			log.error("",e);
		} finally {
			try {
				os.close();
				in.close();
			} catch (Exception e) {
				log.error("",e);
			}
		}
	}
	private int insertToDB(File file) {
		List<MobileSegment> consumerGasCouponList = new ArrayList<MobileSegment>();
		int import_int = 0;
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader in = new BufferedReader(isr);
				String s;
				while ((s = in.readLine()) != null && StringUtils.isNotEmpty(s)) {
					String[] str = s.split(",");
					MobileSegment mobileSegment = new MobileSegment();
					if (str != null) {
					if(str[1].trim().equals("本张表已网间交换小计：")){
						continue;
					}else{
							mobileSegment.setSegment(str[0].trim());
							mobileSegment.setProvinceId(str[3].trim());
							mobileSegment.setAreaCode(str[5].trim());
							mobileSegment.setCityId(str[6].trim());
					}
					consumerGasCouponList.add(mobileSegment);
					}
				}
				
				for (MobileSegment mobileSegment : consumerGasCouponList) {
					MobileSegment mobileSegmentNew = new MobileSegment();
					mobileSegmentNew.setMobileSegmentId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
					mobileSegmentNew.setSegment(mobileSegment.getSegment());
					mobileSegmentNew.setAreaCode(mobileSegment.getAreaCode());
					mobileSegmentNew.setCityId(mobileSegment.getCityId());
					mobileSegmentNew.setProvinceId(mobileSegment.getProvinceId());
					mobileSegmentService.insert(mobileSegmentNew);
				}
					return import_int+1;
					} catch (Exception e) {
						log.error("",e);
					}
					return 0;
				}

}
