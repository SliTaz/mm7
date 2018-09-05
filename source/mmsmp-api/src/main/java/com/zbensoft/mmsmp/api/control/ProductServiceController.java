package com.zbensoft.mmsmp.api.control;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.ProductServiceExtend;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/productService")
@RestController
public class ProductServiceController {
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductServiceService productServiceService;
	@ApiOperation(value = "Query ProductService, support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductServiceExtend>> selectPage(@RequestParam(required = false) String productInfoId, @RequestParam(required = false) String productServiceId,
		                                                    @RequestParam(required = false) String start,@RequestParam(required = false) String length) {
            ProductService   productService= new ProductService();
            productService.setProductInfoId(productInfoId);
            productService.setProductServiceId(productServiceId);
            
	 
//		    if(productName!=null)
//		    {
//			try {
//				productName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
//				productName=URLDecoder.decode(productName, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    }
//		    productInfo.setProductName(productName);
		

		List<ProductService> list = productServiceService.selectPage(productService);
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = productServiceService.selectPage(productService);

		} else {
			list = productServiceService.selectPage(productService);
		}

		int count = productServiceService.count(productService);
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ProductServiceExtend>>(new ArrayList<ProductServiceExtend>(), HttpRestStatus.NOT_FOUND);
		}
		List<ProductServiceExtend> returnlist = new ArrayList<>();
        for(ProductService temp : list){
        	
        	returnlist.add(translate(temp));
        }
	
		
		return new ResponseRestEntity<List<ProductServiceExtend>>(returnlist, HttpRestStatus.OK, count, count);
	}
	public ProductServiceExtend translate(ProductService  productService)
	{   ProductServiceExtend productServiceExtend = new ProductServiceExtend();
		if(productService!=null)
		{
			productServiceExtend.setBillingId(productService.getBillingId());
			productServiceExtend.setCancelAccess(productService.getCancelAccess());
			productServiceExtend.setCancelAccessMatch(productService.getCancelAccessMatch());
			productServiceExtend.setCancelCommandMatch(productService.getCancelCommandMatch());
			productServiceExtend.setCanncelPrompt(productService.getCanncelPrompt());
			
			productServiceExtend.setConfirmPrompt(productService.getConfirmPrompt());
			productServiceExtend.setDiscountId(productService.getDiscountId());
			productServiceExtend.setDiscountRemark(productService.getDiscountRemark());
			productServiceExtend.setEffTime(productService.getEffTime());
			productServiceExtend.setExpTime(productService.getExpTime());
			
			productServiceExtend.setFreeTime(productService.getFreeTime());
			productServiceExtend.setIsAloneOrder(productService.getIsAloneOrder());
			productServiceExtend.setIsConfirm(productService.getIsConfirm());
			productServiceExtend.setIsFree(productService.getIsFree());
			productServiceExtend.setIsPresent(productService.getIsPresent());
			
			productServiceExtend.setNeedConfirm(productService.getNeedConfirm());
			productServiceExtend.setNotifyType(productService.getNotifyType());
			productServiceExtend.setOnDemandAccess(productService.getOnDemandAccess());
			productServiceExtend.setOnDemandAccessMatch(productService.getOnDemandCommandMatch());
			productServiceExtend.setOnDemandCommandMatch(productService.getOnDemandCommandMatch());
			
			productServiceExtend.setOrderAccess(productService.getOrderAccess());
			productServiceExtend.setOrderAccessMatch(productService.getOrderAccessMatch());
			productServiceExtend.setOrderCommandMatch(productService.getOrderCommandMatch());
			productServiceExtend.setPopularizeEndTime(productService.getPopularizeEndTime());
			productServiceExtend.setPopularizeStartTime(productService.getPopularizeStartTime());

			productServiceExtend.setProductCity(productService.getProductCity());
			productServiceExtend.setProductCredit(productService.getProductCredit());
			productServiceExtend.setProductInfoId(productService.getProductInfoId());
			productServiceExtend.setProductServiceGrade(productService.getProductServiceGrade());
			productServiceExtend.setProductServiceId(productService.getProductServiceId());
			
			productServiceExtend.setProductServicePeriodGrade(productService.getProductServicePeriodGrade());
			productServiceExtend.setReportMessageUrl(productService.getReportMessageUrl());
			productServiceExtend.setSendNum(productService.getSendNum());
			productServiceExtend.setSpProductId(productService.getSpProductId());
			productServiceExtend.setStatus(productService.getStatus());
			
			productServiceExtend.setSuccPrompt(productService.getSuccPrompt());
		
		}
	    ProductInfo temp =productInfoService.selectByPrimaryKey(productServiceExtend.getProductInfoId());
		if(temp!=null)
		{
			productServiceExtend.setProductInfoName(temp.getProductName());
		}	
	    return productServiceExtend;
	}
//	@PreAuthorize("hasRole('R_PC_E')")
	@ApiOperation(value = "Add ProductService", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> addProductService(@Valid @RequestBody ProductService productService, BindingResult result, UriComponentsBuilder ucBuilder) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list), HttpRestStatusFactory.createStatusMessage(list));
		}
		
		ProductService bean1 = productServiceService.selectByPrimaryKey(productService.getProductServiceId());
	    
		if (bean1 != null) {
			return new ResponseRestEntity<Void>(HttpRestStatus.CONFLICT);
		}
	
		productServiceService.insert(productService);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/productService/{id}").buildAndExpand(productService.getProductServiceId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED, localeMessageSourceService.getMessage("common.create.created.message"));
	}	
//	@PreAuthorize("hasRole('R_CPIM_E')")
	@ApiOperation(value = "Edit ProductService", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<ProductServiceExtend> updateProductInfo(@PathVariable("id") String id, @Valid @RequestBody ProductService productService, BindingResult result) {

		ProductService currentProductService = productServiceService.selectByPrimaryKey(id);

		if (currentProductService == null) {
			return new ResponseRestEntity<ProductServiceExtend>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		
		currentProductService.setBillingId(productService.getBillingId());
		currentProductService.setCancelAccess(productService.getCancelAccess());
		currentProductService.setCancelAccessMatch(productService.getCancelAccessMatch());
		currentProductService.setCancelCommandMatch(productService.getCancelCommandMatch());
		currentProductService.setCanncelPrompt(productService.getCanncelPrompt());
		
		currentProductService.setConfirmPrompt(productService.getConfirmPrompt());
		currentProductService.setDiscountId(productService.getDiscountId());
		currentProductService.setDiscountRemark(productService.getDiscountRemark());
		currentProductService.setEffTime(productService.getEffTime());
		currentProductService.setExpTime(productService.getExpTime());
		
		currentProductService.setFreeTime(productService.getFreeTime());
		currentProductService.setIsAloneOrder(productService.getIsAloneOrder());
		currentProductService.setIsConfirm(productService.getIsConfirm());
		currentProductService.setIsFree(productService.getIsFree());
		currentProductService.setIsPresent(productService.getIsPresent());
		
		currentProductService.setNeedConfirm(productService.getNeedConfirm());
		currentProductService.setNotifyType(productService.getNotifyType());
		currentProductService.setOnDemandAccess(productService.getOnDemandAccess());
		currentProductService.setOnDemandAccessMatch(productService.getOnDemandCommandMatch());
		currentProductService.setOnDemandCommandMatch(productService.getOnDemandCommandMatch());
		
		currentProductService.setOrderAccess(productService.getOrderAccess());
		currentProductService.setOrderAccessMatch(productService.getOrderAccessMatch());
		currentProductService.setOrderCommandMatch(productService.getOrderCommandMatch());
		currentProductService.setPopularizeEndTime(productService.getPopularizeEndTime());
		currentProductService.setPopularizeStartTime(productService.getPopularizeStartTime());

		currentProductService.setProductCity(productService.getProductCity());
		currentProductService.setProductCredit(productService.getProductCredit());
		currentProductService.setProductInfoId(productService.getProductInfoId());
		currentProductService.setProductServiceGrade(productService.getProductServiceGrade());
		currentProductService.setProductServiceId(productService.getProductServiceId());
		
		currentProductService.setProductServicePeriodGrade(productService.getProductServicePeriodGrade());
		currentProductService.setReportMessageUrl(productService.getReportMessageUrl());
		currentProductService.setSendNum(productService.getSendNum());
		currentProductService.setSpProductId(productService.getSpProductId());
		currentProductService.setStatus(productService.getStatus());
		
		currentProductService.setSuccPrompt(productService.getSuccPrompt());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<ProductServiceExtend>(translate(currentProductService),HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		productServiceService.updateByPrimaryKey(currentProductService);

	
		return new ResponseRestEntity<ProductServiceExtend>(translate(currentProductService), HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}
//	@PreAuthorize("hasRole('R_PC_E')")
	@ApiOperation(value = "Delete ProductService", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ProductServiceExtend> deleteProductService(@PathVariable("id") String id) {

		ProductService currentProductService = productServiceService.selectByPrimaryKey(id);
		if (currentProductService == null) {
			return new ResponseRestEntity<ProductServiceExtend>(HttpRestStatus.NOT_FOUND);
		}
	
		productServiceService.deleteByPrimaryKey(id);
		return new ResponseRestEntity<ProductServiceExtend>(HttpRestStatus.NO_CONTENT);
	}
	
}
