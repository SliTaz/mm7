package com.zbensoft.mmsmp.api.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/productInfo")
@RestController
public class ProductInfoController {
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	@Autowired
	private ProductInfoService productInfoService;
	@ApiOperation(value = "Query ProductInfo, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductInfo>> selectPage(@RequestParam(required = false) String productInfoId, @RequestParam(required = false) String cpAccessId,
			                                                @RequestParam(required = false) String productName, @RequestParam(required = false) String orderType, 
			                                                @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer priority, 
		                                                    @RequestParam(required = false) String start,@RequestParam(required = false) String length) {

		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductInfoId(productInfoId);
		productInfo.setStatus(status);
		productInfo.setPriority(priority);
		productInfo.setOrderType(orderType);
	 
		    if(productName!=null)
		    {
			try {
				productName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				productName=URLDecoder.decode(productName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		    productInfo.setProductName(productName);
		

		List<ProductInfo> list = productInfoService.selectPage(productInfo);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = productInfoService.selectPage(productInfo);

		} else {
			list = productInfoService.selectPage(productInfo);
		}

		int count = productInfoService.count(productInfo);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductInfo>>(new ArrayList<ProductInfo>(), HttpRestStatus.NOT_FOUND);
		}

	
		
		return new ResponseRestEntity<List<ProductInfo>>(list, HttpRestStatus.OK, count, count);
	}
//	@PreAuthorize("hasRole('R_CHANNEL_Q')")
	@ApiOperation(value = "Query ProductInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<ProductInfo> selectByPrimaryKey(@PathVariable("id") String id) {
		ProductInfo productInfo = productInfoService.selectByPrimaryKey(id);
		if (productInfo == null) {
			return new ResponseRestEntity<ProductInfo>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<ProductInfo>(productInfo, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_PC_E')")
	@ApiOperation(value = "Add ProductInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProductInfo(@Valid @RequestBody ProductInfo productInfo, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		ProductInfo bean1 = productInfoService.selectByPrimaryKey(productInfo.getProductInfoId());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
	
		productInfoService.insert(productInfo);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/productInfo/{id}").buildAndExpand(productInfo.getProductInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}
	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Edit ProductInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<ProductInfo> updateProductInfo(@PathVariable("id") String id, @Valid @RequestBody ProductInfo productInfo, BindingResult result) {

		ProductInfo currentProductInfo = productInfoService.selectByPrimaryKey(id);

		if (currentProductInfo == null) {
			return new ResponseRestEntity<ProductInfo>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentProductInfo.setApplyTime(productInfo.getApplyTime());
		currentProductInfo.setAuditRemark(productInfo.getAuditRemark());
		currentProductInfo.setAuditTimeinterval(productInfo.getAuditTimeinterval());
		currentProductInfo.setAuditUser(productInfo.getAuditUser());
		currentProductInfo.setBookmanName(productInfo.getBookmanName());
		
		currentProductInfo.setClassification(productInfo.getClassification());
		currentProductInfo.setCompanyCode(productInfo.getCompanyCode());
		currentProductInfo.setCompanyName(productInfo.getCompanyName());
		currentProductInfo.setComposeTimeinterval(productInfo.getComposeTimeinterval());
		currentProductInfo.setCooperationProportion(productInfo.getCooperationProportion());
		
		currentProductInfo.setCpAccessId(productInfo.getCpAccessId());
		currentProductInfo.setCpAccessIdExtend(productInfo.getCpAccessIdExtend());
		currentProductInfo.setCreateTime(productInfo.getCreateTime());
		currentProductInfo.setDealStatus(productInfo.getDealStatus());
		currentProductInfo.setFeeRemark(productInfo.getFeeRemark());
		
		currentProductInfo.setIsDefault(productInfo.getIsDefault());
	    currentProductInfo.setIsPackage(productInfo.getIsPackage());
	    currentProductInfo.setIsPresent(productInfo.getIsPresent());
	    currentProductInfo.setKeyWorld(productInfo.getKeyWorld());
	    currentProductInfo.setOnDemandCommand(productInfo.getOnDemandCommand());
	    
	    currentProductInfo.setOnDemandFee(productInfo.getOnDemandFee());
	    currentProductInfo.setOnDemandRemark(productInfo.getOnDemandRemark());
	    currentProductInfo.setOperationId(productInfo.getOperationId());
	    currentProductInfo.setOrderCommand(productInfo.getOrderCommand());
	    currentProductInfo.setOrderFee(productInfo.getOrderFee());
	     
	    currentProductInfo.setOrderType(productInfo.getOrderType());
	 
	    currentProductInfo.setPriority(productInfo.getPriority());
	    currentProductInfo.setProAuditTimeinterval(productInfo.getProAuditTimeinterval());
	    currentProductInfo.setProductInfoId(productInfo.getProductInfoId());
	    currentProductInfo.setProductName(productInfo.getProductName());
	    
		currentProductInfo.setProductOnDemandId(productInfo.getProductOnDemandId());
		currentProductInfo.setProductRemark(productInfo.getProductRemark());
		currentProductInfo.setProductSource(productInfo.getProductSource());
		currentProductInfo.setProductType(productInfo.getProductType());
		currentProductInfo.setProofTimeinterval(productInfo.getProofTimeinterval());
		currentProductInfo.setProductOrderId(productInfo.getProductOrderId());
		currentProductInfo.setQueueNum(productInfo.getQueueNum());
		currentProductInfo.setRemark(productInfo.getRemark());
		currentProductInfo.setRunStatus(productInfo.getRunStatus());
		currentProductInfo.setSendContentMode(productInfo.getSendContentMode());
		currentProductInfo.setSendMode(productInfo.getSendMode());
		
		currentProductInfo.setStatus(productInfo.getStatus());
		currentProductInfo.setUpdateTime(productInfo.getUpdateTime());
		currentProductInfo.setWapPicUrl(productInfo.getWapPicUrl());
		currentProductInfo.setWebPicUrl(productInfo.getWebPicUrl());
		
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<ProductInfo>(currentProductInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		productInfoService.updateByPrimaryKey(currentProductInfo);

	
		return new ResponseRestEntity<ProductInfo>(currentProductInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}
//	@PreAuthorize("hasRole('R_PC_E')")
	@ApiOperation(value = "Delete ProductInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ProductInfo> deleteUserClap(@PathVariable("id") String id) {

		ProductInfo currentProductInfo = productInfoService.selectByPrimaryKey(id);
		if (currentProductInfo == null) {
			return new ResponseRestEntity<ProductInfo>(HttpRestStatus.NOT_FOUND);
		}
	
		productInfoService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<ProductInfo>(HttpRestStatus.NO_CONTENT);
	}
}
