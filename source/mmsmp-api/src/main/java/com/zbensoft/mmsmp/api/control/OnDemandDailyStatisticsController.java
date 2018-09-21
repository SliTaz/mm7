package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zbensoft.mmsmp.api.common.HttpRestStatus;
import com.zbensoft.mmsmp.api.common.LocaleMessageSourceService;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/onDemandDailyStatistics")
@RestController
public class OnDemandDailyStatisticsController {
	@Autowired
	UserOrderHisService userOrderHisService;
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	ProductServiceService productServiceService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_ODS_Q')")
	@ApiOperation(value = "Query UserOrderHis，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderHis>> selectPage(@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer orderType,@RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		
		List<UserOrderHis> listNew = new ArrayList<>();
		int count1 = 0;
		ProductService productService= new ProductService();
		ProductInfo productInfo1= new ProductInfo();
		UserOrderHis userOrderHis = new UserOrderHis();
		if(!(spInfoId==null || spInfoId.equals("")) && (productInfoId==null || productInfoId.equals(""))){
			SpInfo spInfo1=new SpInfo();
			spInfo1.setSpInfoId(spInfoId);
			SpInfo spInfoNew = spInfoService.selectByPrimaryKey(spInfoId);
			if(spInfoNew!=null){
				productInfo1.setCompanyCode(spInfoNew.getCompanyCode());
				List<ProductInfo> productInfoList = productInfoService.selectPage(productInfo1);
				if(productInfoList==null || productInfoList.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				for (ProductInfo productInfo : productInfoList) {
					productService.setProductInfoId(productInfo.getProductInfoId());
				}
			}
		}else if(!(productInfoId==null || productInfoId.equals("")) && (spInfoId==null || spInfoId.equals(""))){
			productService.setProductInfoId(productInfoId);
		}else if(!(spInfoId==null || spInfoId.equals("")) && !(productInfoId==null || productInfoId.equals(""))){
			SpInfo spInfo1=new SpInfo();
			spInfo1.setSpInfoId(spInfoId);
			SpInfo spInfoNew = spInfoService.selectByPrimaryKey(spInfoId);
			if(spInfoNew!=null){
				productInfo1.setCompanyCode(spInfoNew.getCompanyCode());
				List<ProductInfo> productInfoList = productInfoService.selectPage(productInfo1);
				if(productInfoList==null || productInfoList.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				for (ProductInfo productInfo : productInfoList) {
					if(productInfo.getProductInfoId().equals(productInfoId)){
						productService.setProductInfoId(productInfoId);
					}else{
						return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
					}
				}
			}
		}
		
		List<ProductService> productServiceLists = productServiceService.selectPage(productService);
		if(productServiceLists==null || productServiceLists.isEmpty()){
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		for (ProductService productService2 : productServiceLists) {
			userOrderHis.setProductInfoId(productService2.getProductInfoId());
				UserOrderHis userOrderHis2 = new UserOrderHis();
			    ProductInfo productInfo = productInfoService.selectByPrimaryKey(productService2.getProductInfoId());
				SpInfo spInfos=new SpInfo();
				spInfos.setCompanyCode(productInfo.getCompanyCode());
				List<SpInfo> spInfo=spInfoService.selectPage(spInfos);
				if(spInfo==null || spInfo.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				userOrderHis2.setSpInfoId(spInfo.get(0).getSpInfoId()); 
				userOrderHis2.setCompanyCode(productInfo.getCompanyCode()); 
				userOrderHis2.setProductName(productInfo.getProductName()); 
				userOrderHis2.setProductInfoId(productInfo.getProductInfoId()); 
				userOrderHis2.setProductServiceId(productService2.getProductServiceId()); 
				userOrderHis2.setOrderType(orderType);
			    String str = orderTimeStart;
			    String des = "-";
			    int cnt = 0;
			    int offset = 0;
			    while((offset = str.indexOf(des, offset)) != -1){
			        offset = offset + des.length();
			        cnt++;
			    }
				if(cnt==1){
					userOrderHis2.setOrderTimeStart(orderTimeStart + "-01");
				}else{
					userOrderHis2.setOrderTimeStart(orderTimeStart);
				}
				String strs = orderTimeEnd;
				String dess = "-";
				int cnts = 0;
				int offsets = 0;
				while((offsets = strs.indexOf(dess, offsets)) != -1){
					offsets = offsets + dess.length();
					cnts++;
				}
				if(cnts==1){
					userOrderHis2.setOrderTimeEnd(orderTimeEnd + "-01");
				}else{
					userOrderHis2.setOrderTimeEnd(orderTimeEnd);
				}
				count1 = userOrderHisService.count(userOrderHis2);
				userOrderHis2.setOnDemandTimes(count1);
				userOrderHis2.setOnDemandFee(productInfo.getOnDemandFee());
				userOrderHis2.setCountOnDemandFee(userOrderHis2.getOnDemandFee()*userOrderHis2.getOnDemandTimes());
				userOrderHis2.setOrderTimeStart(orderTimeStart+ "-01"+"---"+orderTimeEnd+"-01");
				listNew.add(userOrderHis2);
		}
		int count=listNew.size();
		return new ResponseRestEntity<List<UserOrderHis>>(listNew, HttpRestStatus.OK, count, count);
	}
	
	@PreAuthorize("hasRole('R_ODRT_Q')")
	@ApiOperation(value = "Query UserOrderHis，Support paging", notes = "")
	@RequestMapping(value = "/userOrderHis", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderHis>> selectOrder(@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) Integer orderType,@RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		
		List<UserOrderHis> listNew = new ArrayList<>();
		int count1 = 0;
		ProductService productService= new ProductService();
		ProductInfo productInfo1= new ProductInfo();
		UserOrderHis userOrderHis = new UserOrderHis();
		if(!(spInfoId==null || spInfoId.equals("")) && (productInfoId==null || productInfoId.equals(""))){
			SpInfo spInfo1=new SpInfo();
			spInfo1.setSpInfoId(spInfoId);
			SpInfo spInfoNew = spInfoService.selectByPrimaryKey(spInfoId);
			if(spInfoNew!=null){
				productInfo1.setCompanyCode(spInfoNew.getCompanyCode());
				List<ProductInfo> productInfoList = productInfoService.selectPage(productInfo1);
				if(productInfoList==null || productInfoList.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				for (ProductInfo productInfo : productInfoList) {
					productService.setProductInfoId(productInfo.getProductInfoId());
				}
			}
		}else if(!(productInfoId==null || productInfoId.equals("")) && (spInfoId==null || spInfoId.equals(""))){
			productService.setProductInfoId(productInfoId);
		}else if(!(spInfoId==null || spInfoId.equals("")) && !(productInfoId==null || productInfoId.equals(""))){
			SpInfo spInfo1=new SpInfo();
			spInfo1.setSpInfoId(spInfoId);
			SpInfo spInfoNew = spInfoService.selectByPrimaryKey(spInfoId);
			if(spInfoNew!=null){
				productInfo1.setCompanyCode(spInfoNew.getCompanyCode());
				List<ProductInfo> productInfoList = productInfoService.selectPage(productInfo1);
				if(productInfoList==null || productInfoList.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				for (ProductInfo productInfo : productInfoList) {
					if(productInfo.getProductInfoId().equals(productInfoId)){
						productService.setProductInfoId(productInfoId);
					}else{
						return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
					}
				}
			}
		}
		
		List<ProductService> productServiceLists = productServiceService.selectPage(productService);
		if(productServiceLists==null || productServiceLists.isEmpty()){
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		for (ProductService productService2 : productServiceLists) {
				userOrderHis.setProductInfoId(productService2.getProductInfoId());
				UserOrderHis userOrderHis2 = new UserOrderHis();
			    ProductInfo productInfo = productInfoService.selectByPrimaryKey(productService2.getProductInfoId());
				SpInfo spInfos=new SpInfo();
				spInfos.setCompanyCode(productInfo.getCompanyCode());
				List<SpInfo> spInfo=spInfoService.selectPage(spInfos);
				if(spInfo==null || spInfo.isEmpty()){
					return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
				}
				userOrderHis2.setSpInfoId(spInfo.get(0).getSpInfoId()); 
				userOrderHis2.setCompanyCode(productInfo.getCompanyCode()); 
				userOrderHis2.setProductName(productInfo.getProductName()); 
				userOrderHis2.setProductInfoId(productInfo.getProductInfoId()); 
				userOrderHis2.setProductServiceId(productService2.getProductServiceId()); 
				userOrderHis2.setOrderType(orderType);
				userOrderHis2.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
			    String str = orderTimeStart;
			    String des = "-";
			    int cnt = 0;
			    int offset = 0;
			    while((offset = str.indexOf(des, offset)) != -1){
			        offset = offset + des.length();
			        cnt++;
			    }
				if(cnt==1){
					userOrderHis2.setOrderTimeStart(orderTimeStart + "-01");
				}else{
					userOrderHis2.setOrderTimeStart(orderTimeStart);
				}
				String strs = orderTimeEnd;
				String dess = "-";
				int cnts = 0;
				int offsets = 0;
				while((offsets = strs.indexOf(dess, offsets)) != -1){
					offsets = offsets + dess.length();
					cnts++;
				}
				if(cnts==1){
					userOrderHis2.setOrderTimeEnd(orderTimeEnd + "-01");
				}else{
					userOrderHis2.setOrderTimeEnd(orderTimeEnd);
				}
				count1 = userOrderHisService.count(userOrderHis2);
				userOrderHis2.setOrderTimes(count1);
				userOrderHis2.setOrderFee(productInfo.getOrderFee());
				userOrderHis2.setCountOrderFee(userOrderHis2.getOrderFee()*userOrderHis2.getOrderTimes());
				
				userOrderHis2.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
				userOrderHis2.setOrderTimeStart("");
				if(cnts==1){
					userOrderHis2.setOrderTimeEnd(orderTimeEnd + "-01");
				}else{
					userOrderHis2.setOrderTimeEnd(orderTimeEnd);
				}
				int countOrder=userOrderHisService.count(userOrderHis2);
				userOrderHis2.setOrderTimeStart("");
				if(cnts==1){
					userOrderHis2.setOrderTimeEnd(orderTimeStart + "-01");
				}else{
					userOrderHis2.setOrderTimeEnd(orderTimeStart);
				}
				int countOrderBefore=userOrderHisService.count(userOrderHis2);
				userOrderHis2.setNewOrderNumber(countOrder-countOrderBefore);
				
				if(cnt==1){
					userOrderHis2.setOrderTimeStart(orderTimeStart + "-01");
				}else{
					userOrderHis2.setOrderTimeStart(orderTimeStart);
				}
				if(cnts==1){
					userOrderHis2.setOrderTimeEnd(orderTimeEnd + "-01");
				}else{
					userOrderHis2.setOrderTimeEnd(orderTimeEnd);
				}
				userOrderHis2.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_CANCEL);
				userOrderHis2.setUnsubscribe(userOrderHisService.count(userOrderHis2));
				userOrderHis2.setOrderTimeStart(orderTimeStart+ "-01"+"---"+orderTimeEnd+"-01");
				listNew.add(userOrderHis2);
		}
		int count=listNew.size();
		return new ResponseRestEntity<List<UserOrderHis>>(listNew, HttpRestStatus.OK, count, count);
	}
}
