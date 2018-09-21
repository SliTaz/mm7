package com.zbensoft.mmsmp.api.control;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.PageHelperUtil;
import com.zbensoft.mmsmp.api.common.ResponseRestEntity;
import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.domain.ProductServiceExtend;
import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/userOrderHis")
@RestController
public class UserOrderHisController {
	@Autowired
	UserOrderHisService userOrderHisService;
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	private ProductServiceService productServiceService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
	@PreAuthorize("hasRole('R_UOH_Q')")
	@ApiOperation(value = "Query UserOrderHis，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderHis>> selectPage(@RequestParam(required = false) String userOrderHisId,@RequestParam(required = false) String phoneNumber,
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String chargePhoneNumber,@RequestParam(required = false) Integer orderRoute,
			@RequestParam(required = false) Integer orderType,@RequestParam(required = false) Double fee,
			@RequestParam(required = false) Integer cancelRoute,@RequestParam(required = false) String featureStr,
			@RequestParam(required = false) Integer status,@RequestParam(required = false) String version,
			@RequestParam(required = false) String notDisturbTime,@RequestParam(required = false) String transactionId,
			@RequestParam(required = false) String area,@RequestParam(required = false) Integer priority,
			@RequestParam(required = false) String lastBatchId,@RequestParam(required = false) Integer isPackage,@RequestParam(required = false) Integer notifySpFlag,
			@RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		UserOrderHis userOrderHis = new UserOrderHis();
		userOrderHis.setUserOrderHisId(userOrderHisId);
		userOrderHis.setPhoneNumber(phoneNumber);
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setProductInfoId(productInfoId);
		userOrderHis.setChargePhoneNumber(chargePhoneNumber);
		userOrderHis.setOrderRoute(orderRoute);
		userOrderHis.setOrderType(orderType);
		userOrderHis.setFee(fee);
		userOrderHis.setCancelRoute(cancelRoute);
		userOrderHis.setFeatureStr(featureStr);
		userOrderHis.setStatus(status);
		userOrderHis.setVersion(version);
		userOrderHis.setNotDisturbTime(notDisturbTime);
		userOrderHis.setTransactionId(transactionId);
		userOrderHis.setArea(area);
		userOrderHis.setPriority(priority);
		userOrderHis.setLastBatchId(lastBatchId);
		userOrderHis.setIsPackage(isPackage);
		userOrderHis.setOrderTimeStart(orderTimeStart);
		userOrderHis.setOrderTimeEnd(orderTimeEnd);
		userOrderHis.setNotifySpFlag(notifySpFlag);
		int count = userOrderHisService.count(userOrderHis);
		if (count == 0) {
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrderHis> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = userOrderHisService.selectPage(userOrderHis);

		} else {
			list = userOrderHisService.selectPage(userOrderHis);
		}

		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		List<UserOrderHis> listNew = new ArrayList<>();
		for(UserOrderHis bean: list){
			SpInfo spInfo = spInfoService.selectByPrimaryKey(bean.getSpInfoId());
			if(spInfo != null){
				bean.setCompanyName(spInfo.getCompanyName());
			}
			ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(bean.getArea());
			if(provinceCity != null){
				bean.setProvinceCityName(provinceCity.getProvinceCityName());
			}
			ProductInfo productInfo = productInfoService.selectByPrimaryKey(bean.getProductInfoId());
			if(productInfo != null){
				bean.setProductName(productInfo.getProductName());
			}
			listNew.add(bean);
		}
		return new ResponseRestEntity<List<UserOrderHis>>(listNew, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_UOH_Q')")
	@ApiOperation(value = "Query UserRecv", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<UserOrderHis> selectByPrimaryKey(@PathVariable("id") String id) {
		UserOrderHis userOrderHis = userOrderHisService.selectByPrimaryKey(id);
		if (userOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<UserOrderHis>(userOrderHis, HttpRestStatus.OK);
	}
	@PreAuthorize("hasRole('R_TPRE_Q')")
	@ApiOperation(value = "Query platform revenue, support paging", notes = "")
	@RequestMapping(value = "/platformRevenue", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductServiceExtend>> selectPlatformRevenue(
                                                                		   @RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			                                                                @RequestParam(required = false) String start,@RequestParam(required = false) String length)
	{     double sumOrderPrice=0.0;
	      double sumBuyPrice=0.0;
	      double totalMoney=0.0;
	      double ratio =0.0;
		 String startTime =orderTimeStart.split(" ")[0];
		 String endTime=orderTimeEnd.split(" ")[0];
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     long m=0;
		try {
			m = sdf.parse(startTime).getTime() - sdf.parse(endTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     int tempDay= (int) (m / (1000 * 60 * 60 * 24));
		 UserOrderHis userOrderHis = new UserOrderHis();
		 userOrderHis.setOrderTimeEnd(orderTimeEnd);
		 userOrderHis.setOrderTimeStart(orderTimeStart);
		 userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
		 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
		 int orderCount = userOrderHisService.count(userOrderHis);
		 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
		 int buyCount  = userOrderHisService.count(userOrderHis);
		 sumOrderPrice=CountMoney(orderTimeStart, orderTimeEnd, 2);
		 sumBuyPrice=CountMoney(orderTimeStart, orderTimeEnd, 1);
		 orderTimeStart=changeTime(orderTimeStart,0,0,tempDay);
		 orderTimeEnd=changeTime(orderTimeEnd,0,0,tempDay);
		 totalMoney=CountMoney(orderTimeStart, orderTimeEnd, 1)+CountMoney(orderTimeStart, orderTimeEnd, 2);
		if(totalMoney!=0.0)
		{
			
			ratio = (sumOrderPrice+sumBuyPrice-totalMoney)*1.0/totalMoney*100;
		    ratio =new BigDecimal(ratio).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		 ProductServiceExtend productServiceExtend = new ProductServiceExtend ();
		 productServiceExtend.setOrderCount(orderCount);
		 productServiceExtend.setBuyCount(buyCount);
		 productServiceExtend.setBuySum(sumBuyPrice);
		 productServiceExtend.setOrderSum(sumOrderPrice);
		 productServiceExtend.setYearMonth(startTime+"-"+endTime);
		 productServiceExtend.setRatioByNearMonth(ratio);
		 productServiceExtend.setSum(sumOrderPrice+sumBuyPrice);
		 List<ProductServiceExtend> returnlist= new ArrayList<ProductServiceExtend>();
		 returnlist.add(productServiceExtend);
		  return new ResponseRestEntity<List<ProductServiceExtend>>(returnlist, HttpRestStatus.OK, returnlist.size(), returnlist.size());	
	}
	@PreAuthorize("hasRole('R_PBVE_Q')")
	@ApiOperation(value = "Query provincialIncomeAnalysis total report , support paging", notes = "")
	@RequestMapping(value = "/provincialIncomeAnalysis", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductServiceExtend>> selectProvincialIncomeAnalysis(@RequestParam(required = false) String provinceCityId,@RequestParam(required = false) String spInfoId,
                                                                		   @RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
                                                                		   @RequestParam(required = false) String productInfoId,
			                                                               @RequestParam(required = false) String start,@RequestParam(required = false) String length)
	{  
		
	   if(orderTimeEnd.length()<5)
	  {
		   orderTimeStart=orderTimeEnd+"-01-01";
		orderTimeEnd=orderTimeEnd.length()<5&&orderTimeEnd.length()>1?orderTimeEnd+"-12-31":orderTimeEnd;
	  }
		UserOrderHis userOrderHis = new UserOrderHis();
		userOrderHis.setOrderTimeEnd(orderTimeEnd);
		userOrderHis.setOrderTimeStart(orderTimeStart);
		userOrderHis.setArea(provinceCityId);
		List<UserOrderHis> list = userOrderHisService.selectPage(userOrderHis);
		Hashtable<String, Hashtable<String ,HashSet<String>>> sp= new Hashtable<>();
	
		for(UserOrderHis temp: list)
		{ 
			if(sp.containsKey(temp.getArea()))
			{
				
				HashSet<String> temHash= new HashSet<>();
				temHash.add(temp.getProductInfoId());
				sp.get(temp.getArea()).put(temp.getSpInfoId(), temHash);
			
			}
			else
			{
				Hashtable<String,HashSet<String>> tempHash= new Hashtable<>();
				HashSet<String> temHash= new HashSet<>();
				temHash.add(temp.getProductInfoId());
				tempHash.put(temp.getSpInfoId(), temHash);
				sp.put(temp.getArea(), tempHash);
				
			}
		}
		 List<ProductServiceExtend> returnlist= new ArrayList<ProductServiceExtend>();
		for(Iterator<String> iterator=sp.keySet().iterator();iterator.hasNext();){
			String city=iterator.next();
			ProductServiceExtend tempProductServiceExtend = new ProductServiceExtend();
			tempProductServiceExtend.setNewBuySum(0.0);
			tempProductServiceExtend.setNewOrderSum(0.0);
			tempProductServiceExtend.setNewTotal(0.0);
			tempProductServiceExtend.setProvince(city);
			Hashtable<String,HashSet<String>> tempHash=sp.get(city);
			for(Iterator<String> iterator1=tempHash.keySet().iterator();iterator1.hasNext();){
				String spInfo1=iterator1.next();
				 
				Iterator iterator3 = tempHash.get(spInfo1).iterator();
				while (iterator3.hasNext()) {
					ProductServiceExtend productServiceExtend = new ProductServiceExtend();
					String product=(String) iterator3.next();
					userOrderHis.setOrderTimeEnd(orderTimeEnd);
					userOrderHis.setOrderTimeStart(orderTimeStart);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
					ProductInfo productInfo= productInfoService.selectByPrimaryKey(product);
					ProductService ProductService = new ProductService();
					ProductService.setProductInfoId(product);
					List<ProductService> list1 = productServiceService.selectPage(ProductService);
					ProductService productService = list1.get(0);
					productServiceExtend.setProvince(provinceCityService.selectByPrimaryKey(city).getProvinceCityName());
					productServiceExtend.setProductServiceId(productService.getProductServiceId());
					productServiceExtend.setProductInfoName(productInfo.getProductName());
					productServiceExtend.setProductInfoId(product);
					productServiceExtend.setSpInfoId(spInfo1);
					userOrderHis.setSpInfoId(spInfo1);
					userOrderHis.setArea(city);
					userOrderHis.setProductInfoId(product);
					 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					
					 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
                    //修改时间
					//查询从原点到开始时间的退订数 订购数  点播数
					userOrderHis.setOrderTimeStart("");		
					userOrderHis.setOrderTimeEnd(orderTimeStart);
					int tempBuyCount =userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_CANCEL);
					int tempUnsubscribe =userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
					userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					int tempOndemandCount = userOrderHisService.count(userOrderHis);
					//修改时间
					//查询从开始时间到结束的退订数 订购数
					
					userOrderHis.setOrderTimeEnd(orderTimeEnd);
					int orderCount = userOrderHisService.count(userOrderHis);
					int newOrderTimes =orderCount-tempOndemandCount;
					productServiceExtend.setNewOrderTimes(newOrderTimes);
					productServiceExtend.setOrderCount(orderCount);
					productServiceExtend.setOrderTimes(orderCount);
					userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
					int buyCount = userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_CANCEL);
					int unsubscribe= userOrderHisService.count(userOrderHis);
						
					int newBuyTimes = buyCount-tempBuyCount;
					unsubscribe=unsubscribe-tempUnsubscribe;
                    productServiceExtend.setNewBuyTimes(newBuyTimes);
					userOrderHis.setStatus(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					productServiceExtend.setUnsubscribe(unsubscribe);
					productServiceExtend.setBuyCount(buyCount);
                    if(productInfo.getOnDemandFee()!=null) 
                    {
                    	productServiceExtend.setOrderSum(orderCount*productInfo.getOnDemandFee()/100);
                    	productServiceExtend.setOnDemandFee(productInfo.getOnDemandFee()/100);   
                    	productServiceExtend.setNewOrderSum(newOrderTimes*productInfo.getOnDemandFee()/100);
                    }
         
                    if(productInfo.getOrderFee()!=null)
                    {
                    	productServiceExtend.setNewBuySum(newBuyTimes*productInfo.getOrderFee()/100);
                    	productServiceExtend.setBuySum(buyCount*productInfo.getOrderFee()/100);
                    	productServiceExtend.setBuyFee(productInfo.getOrderFee()/100);
                    	
                    }
					productServiceExtend.setYearMonth(orderTimeStart+"-"+orderTimeEnd);
					Double newTotal=productServiceExtend.getNewOrderSum()+productServiceExtend.getNewBuySum();
					productServiceExtend.setNewTotal(newTotal);
					productServiceExtend.setCompanyCode(productInfo.getCompanyCode());
					tempProductServiceExtend.setNewBuySum(productServiceExtend.getNewBuySum()+tempProductServiceExtend.getNewBuySum());
					tempProductServiceExtend.setNewOrderSum(productServiceExtend.getNewOrderSum()+tempProductServiceExtend.getNewOrderSum());
					Double tempTotal=tempProductServiceExtend.getNewTotal()+productServiceExtend.getNewTotal();
					tempTotal =new BigDecimal(tempTotal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					tempProductServiceExtend.setNewTotal(tempTotal);
					tempProductServiceExtend.setProvince(productServiceExtend.getProvince());
					
				}
			
				}
			returnlist.add(tempProductServiceExtend);
			
			
			
			
			
			}
		int pageCount =returnlist.size();
		
		if (returnlist == null || returnlist.isEmpty()) {
			return new ResponseRestEntity<List<ProductServiceExtend>>(new ArrayList<ProductServiceExtend>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ProductServiceExtend>>(returnlist, HttpRestStatus.OK, pageCount, pageCount);
	}
	@PreAuthorize("hasRole('O_RQA_Q')")
	@ApiOperation(value = "Query provincial total report , support paging", notes = "")
	@RequestMapping(value = "/provincialTotalReport", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductServiceExtend>> selectProvincialTotalReport(@RequestParam(required = false) String provinceCityId,@RequestParam(required = false) String spInfoId,
                                                                		   @RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
                                                                		   @RequestParam(required = false) String productInfoId,
			                                                               @RequestParam(required = false) String start,@RequestParam(required = false) String length)
	{  
		if(orderTimeEnd!=null)
	   {
		orderTimeEnd=orderTimeEnd.length()<8&&orderTimeEnd.length()>1?orderTimeEnd+"-01":orderTimeEnd;
		
	   }
	   if(orderTimeStart!=null)
	  {
		orderTimeStart=orderTimeStart.length()<8&&orderTimeStart.length()>1?orderTimeStart+"-01":orderTimeStart;
	  }
		UserOrderHis userOrderHis = new UserOrderHis();
		userOrderHis.setArea(provinceCityId);
		userOrderHis.setSpInfoId(spInfoId);
		userOrderHis.setProductInfoId(productInfoId);
		userOrderHis.setOrderTimeEnd(orderTimeEnd);
		userOrderHis.setOrderTimeStart(orderTimeStart);
		List<UserOrderHis> list = userOrderHisService.selectPage(userOrderHis);
		Hashtable<String, Hashtable<String ,HashSet<String>>> sp= new Hashtable<>();
	
		for(UserOrderHis temp: list)
		{ 
			if(sp.containsKey(temp.getArea()))
			{
				
				HashSet<String> temHash= new HashSet<>();
				temHash.add(temp.getProductInfoId());
				sp.get(temp.getArea()).put(temp.getSpInfoId(), temHash);
			
			}
			else
			{
				Hashtable<String,HashSet<String>> tempHash= new Hashtable<>();
				HashSet<String> temHash= new HashSet<>();
				temHash.add(temp.getProductInfoId());
				tempHash.put(temp.getSpInfoId(), temHash);
				sp.put(temp.getArea(), tempHash);
				
			}
		}
		 List<ProductServiceExtend> returnlist= new ArrayList<ProductServiceExtend>();
		for(Iterator<String> iterator=sp.keySet().iterator();iterator.hasNext();){
			String city=iterator.next();
			Hashtable<String,HashSet<String>> tempHash=sp.get(city);
			for(Iterator<String> iterator1=tempHash.keySet().iterator();iterator1.hasNext();){
				String spInfo1=iterator1.next();
				 
				Iterator iterator3 = tempHash.get(spInfo1).iterator();
				while (iterator3.hasNext()) {
					ProductServiceExtend productServiceExtend = new ProductServiceExtend();
					String product=(String) iterator3.next();
					userOrderHis.setOrderTimeEnd(orderTimeEnd);
					userOrderHis.setOrderTimeStart(orderTimeStart);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
					ProductInfo productInfo= productInfoService.selectByPrimaryKey(product);
					ProductService ProductService = new ProductService();
					ProductService.setProductInfoId(product);
					List<ProductService> list1 = productServiceService.selectPage(ProductService);
					ProductService productService = list1.get(0);
					productServiceExtend.setProvince(provinceCityService.selectByPrimaryKey(city).getProvinceCityName());
					productServiceExtend.setProductServiceId(productService.getProductServiceId());
					productServiceExtend.setProductInfoName(productInfo.getProductName());
					productServiceExtend.setProductInfoId(product);
					productServiceExtend.setSpInfoId(spInfo1);
					userOrderHis.setSpInfoId(spInfo1);
					userOrderHis.setArea(city);
					userOrderHis.setProductInfoId(product);
					 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					
					 userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
                    //修改时间
					//查询从原点到开始时间的退订数 订购数  点播数
					userOrderHis.setOrderTimeStart("");		
					userOrderHis.setOrderTimeEnd(orderTimeStart);
					int tempBuyCount =userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_CANCEL);
					int tempUnsubscribe =userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
					userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					int tempOndemandCount = userOrderHisService.count(userOrderHis);
					//修改时间
					//查询从开始时间到结束的退订数 订购数
					
					userOrderHis.setOrderTimeEnd(orderTimeEnd);
					int orderCount = userOrderHisService.count(userOrderHis);
					int newOrderTimes =orderCount-tempOndemandCount;
					productServiceExtend.setNewOrderTimes(newOrderTimes);
					productServiceExtend.setOrderCount(orderCount);
					productServiceExtend.setOrderTimes(orderCount);
					userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
					int buyCount = userOrderHisService.count(userOrderHis);
					userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_CANCEL);
					int unsubscribe= userOrderHisService.count(userOrderHis);
						
					int newBuyTimes = buyCount-tempBuyCount;
					unsubscribe=unsubscribe-tempUnsubscribe;
                    productServiceExtend.setNewBuyTimes(newBuyTimes);
					userOrderHis.setStatus(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
					productServiceExtend.setUnsubscribe(unsubscribe);
					productServiceExtend.setBuyCount(buyCount);
                    if(productInfo.getOnDemandFee()!=null) 
                    {
                    	productServiceExtend.setOrderSum(orderCount*productInfo.getOnDemandFee()/100);
                    	productServiceExtend.setOnDemandFee(productInfo.getOnDemandFee()/100);   
                    	productServiceExtend.setNewOrderSum(newOrderTimes*productInfo.getOnDemandFee()/100);
                    }
         
                    if(productInfo.getOrderFee()!=null)
                    {
                    	productServiceExtend.setNewBuySum(newBuyTimes*productInfo.getOrderFee()/100);
                    	productServiceExtend.setBuySum(buyCount*productInfo.getOrderFee()/100);
                    	productServiceExtend.setBuyFee(productInfo.getOrderFee()/100);
                    	
                    }
					productServiceExtend.setYearMonth(orderTimeStart+"-"+orderTimeEnd);
					Double newTotal=productServiceExtend.getNewOrderSum()+productServiceExtend.getNewBuySum();
					productServiceExtend.setNewTotal(newTotal);
					productServiceExtend.setCompanyCode(productInfo.getCompanyCode());
					returnlist.add(productServiceExtend);
				}
			
				}
			
			
			
			
			
			}
		int pageCount =returnlist.size();
		
		if (returnlist == null || returnlist.isEmpty()) {
			return new ResponseRestEntity<List<ProductServiceExtend>>(new ArrayList<ProductServiceExtend>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ProductServiceExtend>>(returnlist, HttpRestStatus.OK, pageCount, pageCount);
	}
	public double CountMoney(String orderTimeStart,String orderTimeEnd ,int type)
	{   double money =0.0;
		ProductService productService=new ProductService();
	    List<ProductService> list= productServiceService.selectPage(productService);
		for(ProductService temp:list)
		 { UserOrderHis userOrderHis = new UserOrderHis();
		   userOrderHis.setProductInfoId(temp.getProductInfoId());
		   userOrderHis.setOrderType(type);
		   userOrderHis.setOrderTimeEnd(orderTimeEnd);
		   userOrderHis.setOrderTimeStart(orderTimeStart);
		   userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
		   int tempCount = userOrderHisService.count(userOrderHis);
		   if(type==1)
		   {  
			   ProductInfo p= productInfoService.selectByPrimaryKey(temp.getProductInfoId());
			   if(p.getOnDemandFee()!=null)
			   {
				   
				   money+=tempCount*p.getOnDemandFee()/100; 
			   }
		   }
		   else if(type==2)
		   {
			   ProductInfo p= productInfoService.selectByPrimaryKey(temp.getProductInfoId());
			   if(p.getOrderFee()!=null)
			   {
				   
				   money+=tempCount*p.getOrderFee()/100;   
			   }
		   }
		   }
		return money;
		
		
	}
	@PreAuthorize("hasRole('R_BMRT_Q')")
	@ApiOperation(value = "Query ProductService by month, support paging", notes = "")
	@RequestMapping(value = "/ByMonth", method = RequestMethod.GET)
	public ResponseRestEntity<List<ProductServiceExtend>> selectPageReport(@RequestParam(required = false) String spInfoId,@RequestParam(required = false) String productInfoId,
                                                                		   @RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			                                                                @RequestParam(required = false) String start,@RequestParam(required = false) String length)
	{     if(orderTimeEnd!=null)
	   {
		orderTimeEnd=orderTimeEnd.length()<8&&orderTimeEnd.length()>1?orderTimeEnd+"-01":orderTimeEnd;
		
	   }
	   if(orderTimeStart!=null)
	  {
		orderTimeStart=orderTimeStart.length()<8&&orderTimeStart.length()>1?orderTimeStart+"-01":orderTimeStart;
	  }
		SpInfo spInfo= new SpInfo();
		spInfo=spInfoService.selectByPrimaryKey(spInfoId);
		 List<ProductInfo> list1=null;
		if(spInfo!=null)
		{   ProductInfo bean = new ProductInfo();
		    bean.setCompanyCode(spInfo.getCompanyCode());
		    bean.setProductInfoId(productInfoId);
		    list1=productInfoService.selectPage(bean);
		    if(list1==null||list1.isEmpty())
		    {
		    return new ResponseRestEntity<List<ProductServiceExtend>>(new ArrayList<ProductServiceExtend>(), HttpRestStatus.NOT_FOUND);	
             }
			
		}
		 int pageCount=0;
		 String startTime =orderTimeStart.split(" ")[0];
		 String endTime=orderTimeEnd.split(" ")[0];
		  List<ProductServiceExtend> returnlist = new ArrayList<ProductServiceExtend>();
		   ProductInfo  productInfo= productInfoService.selectByPrimaryKey(productInfoId); 
		   ProductService productService=new ProductService();
		   List<ProductService> list=new ArrayList<ProductService>();
		   if(productInfo==null)
		 {  if(list1!=null)
		 {
			 for(ProductInfo temp:list1)
			 {
				 productService.setProductInfoId(temp.getProductInfoId());   
				 List<ProductService> list2=productServiceService.selectPage(productService);
				 if(list2==null)
				 {continue;}
				 list.add(list2.get(0));
			 
			 }
		 }
		 else
		 {
			 productService.setProductInfoId(productInfoId);   
			  list=productServiceService.selectPage(productService);
		 }
		 }
		  else
		  {
			   productService.setProductInfoId(productInfoId);   
				 List<ProductService> list2=productServiceService.selectPage(productService);
				 list.add(list2.get(0));
		  }
		   
			pageCount=list.size();
			if(list!=null&&list.size()!=0)
			{   
			   for(ProductService temp: list)
			   {
				   UserOrderHis userOrderHis = new UserOrderHis();
				   userOrderHis.setOrderTimeEnd(endTime);
				   userOrderHis.setProductInfoId(temp.getProductInfoId());
				   userOrderHis.setOrderTimeStart(startTime);
				   userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_ONDEMAND);
				   userOrderHis.setStatus(MessageDef.ORDERHIS_STATUS.ORDERHIS_ORDER);
				   userOrderHis.setSpInfoId(spInfoId);
				  Integer tempMonth=Integer.parseInt(orderTimeStart.split("-")[1])-Integer.parseInt(orderTimeEnd.split("-")[1]);

				   int count = userOrderHisService.count(userOrderHis);
				   orderTimeStart=changeTime(orderTimeStart,-1,0,0);
				   orderTimeEnd=changeTime(orderTimeEnd,-1,0,0);
				   userOrderHis.setOrderTimeStart(orderTimeStart); 
				   userOrderHis.setOrderTimeEnd(orderTimeEnd);
				   int preYearCount = userOrderHisService.count(userOrderHis);
				   orderTimeStart=changeTime(orderTimeStart,1,tempMonth,0);
				   orderTimeEnd=changeTime(orderTimeEnd,1,tempMonth,0);
				   userOrderHis.setOrderTimeStart(orderTimeStart); 
				   userOrderHis.setOrderTimeEnd(orderTimeEnd);
				   int preMonthCount = userOrderHisService.count(userOrderHis);
				   
				   ProductService bean = productServiceService.selectByPrimaryKey(temp.getProductServiceId());
				   returnlist.add(translate(bean,count,startTime,endTime,preYearCount,preMonthCount));				  			   			   
			   }	
			}
		 
		if (returnlist == null || returnlist.isEmpty()) {
			return new ResponseRestEntity<List<ProductServiceExtend>>(new ArrayList<ProductServiceExtend>(), HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ProductServiceExtend>>(returnlist, HttpRestStatus.OK, pageCount, pageCount);
		
	}
	public String changeTime(String time,int year,int month,int day)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		   Date dt = null;
		try {
			dt = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   Calendar rightNow = Calendar.getInstance();
		   rightNow.setTime(dt);
		   rightNow.add(Calendar.YEAR,year);
		   rightNow.add(Calendar.MONTH,month);
		   rightNow.add(Calendar.DATE,day);
		   Date dt1=rightNow.getTime();
		   String reStr = sdf.format(dt1);
		   return reStr;
	}
	
	public ProductServiceExtend translate(ProductService  productService,Integer count,String orderTimeStart,String orderTimeEnd,int preYear,int preMonth)
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
			productServiceExtend.setYearMonth(orderTimeStart+"-"+orderTimeEnd);
		
		}
	    ProductInfo temp =productInfoService.selectByPrimaryKey(productServiceExtend.getProductInfoId());
		if(temp!=null)
		{
			productServiceExtend.setProductInfoName(temp.getProductName());
			productServiceExtend.setCompanyCode(temp.getCompanyCode());
			SpInfo spInfo= new SpInfo();
			spInfo.setCompanyCode(temp.getCompanyCode());
			List<SpInfo> list =spInfoService.selectPage(spInfo);			
			productServiceExtend.setSpInfoId(list.get(0).getSpInfoId());
			productServiceExtend.setOnDemandFee(temp.getOnDemandFee()/100);
			productServiceExtend.setOrderTimes(count);
			productServiceExtend.setTotalMonth();
			
			
		}	
		if(preYear!=0)
		{ double preYearRadio=0.0;
		preYearRadio=(preYear-count)/preYear*100;
		double value =new BigDecimal(preYearRadio).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		productServiceExtend.setRatioByNextYear(value);
			
		}else
		{
			productServiceExtend.setRatioByNextYear(0.0);
		}
		
		if(preMonth!=0)
		{
			double preMonthRadio=0.0;
			preMonthRadio=(count-preMonth)*1.0/preMonth*100;
			double value =new BigDecimal(preMonthRadio).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			productServiceExtend.setRatioByNearMonth(value);

			
		}else
		{
			productServiceExtend.setRatioByNearMonth(0.0);
		}
	
	    return productServiceExtend;
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Add UserOrderHis", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserOrderHis(@Valid @RequestBody UserOrderHis userOrderHis, BindingResult result, UriComponentsBuilder ucBuilder) {
//		cpInfo.setCpInfoId(IDGenerate.generateCommTwo(IDGenerate.CONSUMER_GAS_COUPONID));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		
		userOrderHisService.insert(userOrderHis);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, userOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/userOrderHis/{id}").buildAndExpand(userOrderHis.getUserOrderHisId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("userOrderHis.create.created.message"));
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Edit UserOrderHis", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<UserOrderHis> updateUserOrderHis(@PathVariable("id") String id, @Valid @RequestBody UserOrderHis userOrderHis, BindingResult result) {

		UserOrderHis currentUserOrderHis = userOrderHisService.selectByPrimaryKey(id);

		if (currentUserOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentUserOrderHis.setPhoneNumber(userOrderHis.getPhoneNumber());
		currentUserOrderHis.setSpInfoId(userOrderHis.getSpInfoId());
		currentUserOrderHis.setProductInfoId(userOrderHis.getProductInfoId());
		currentUserOrderHis.setChargePhoneNumber(userOrderHis.getChargePhoneNumber());
		currentUserOrderHis.setOrderRoute(userOrderHis.getOrderRoute());
		currentUserOrderHis.setOrderType(userOrderHis.getOrderType());
		currentUserOrderHis.setFee(userOrderHis.getFee());
		currentUserOrderHis.setCancelRoute(userOrderHis.getCancelRoute());
		currentUserOrderHis.setFeatureStr(userOrderHis.getFeatureStr());
		currentUserOrderHis.setStatus(userOrderHis.getStatus());
		currentUserOrderHis.setVersion(userOrderHis.getVersion());
		currentUserOrderHis.setNotDisturbTime(userOrderHis.getNotDisturbTime());
		currentUserOrderHis.setTransactionId(userOrderHis.getTransactionId());
		currentUserOrderHis.setArea(userOrderHis.getArea());
		currentUserOrderHis.setPriority(userOrderHis.getPriority());
		currentUserOrderHis.setLastBatchId(userOrderHis.getLastBatchId());
		currentUserOrderHis.setIsPackage(userOrderHis.getIsPackage());
		currentUserOrderHis.setEffTime(userOrderHis.getEffTime());
		currentUserOrderHis.setCancelTime(userOrderHis.getCancelTime());
		currentUserOrderHis.setOrderTime(userOrderHis.getOrderTime());
		currentUserOrderHis.setNotifySpFlag(userOrderHis.getNotifySpFlag());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		userOrderHisService.updateByPrimaryKey(currentUserOrderHis);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis, HttpRestStatus.OK,localeMessageSourceService.getMessage("UserOrderHis.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Edit Part UserOrderHis", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<UserOrderHis> updateUserOrderHis(@PathVariable("id") String id, @RequestBody UserOrderHis userOrderHis) {

		UserOrderHis currentUserOrderHis =userOrderHisService.selectByPrimaryKey(id);

		if (currentUserOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}
		currentUserOrderHis.setPhoneNumber(userOrderHis.getPhoneNumber());
		currentUserOrderHis.setSpInfoId(userOrderHis.getSpInfoId());
		currentUserOrderHis.setProductInfoId(userOrderHis.getProductInfoId());
		currentUserOrderHis.setChargePhoneNumber(userOrderHis.getChargePhoneNumber());
		currentUserOrderHis.setOrderRoute(userOrderHis.getOrderRoute());
		currentUserOrderHis.setOrderType(userOrderHis.getOrderType());
		currentUserOrderHis.setFee(userOrderHis.getFee());
		currentUserOrderHis.setCancelRoute(userOrderHis.getCancelRoute());
		currentUserOrderHis.setFeatureStr(userOrderHis.getFeatureStr());
		currentUserOrderHis.setStatus(userOrderHis.getStatus());
		currentUserOrderHis.setVersion(userOrderHis.getVersion());
		currentUserOrderHis.setNotDisturbTime(userOrderHis.getNotDisturbTime());
		currentUserOrderHis.setTransactionId(userOrderHis.getTransactionId());
		currentUserOrderHis.setArea(userOrderHis.getArea());
		currentUserOrderHis.setPriority(userOrderHis.getPriority());
		currentUserOrderHis.setLastBatchId(userOrderHis.getLastBatchId());
		currentUserOrderHis.setIsPackage(userOrderHis.getIsPackage());
		currentUserOrderHis.setEffTime(userOrderHis.getEffTime());
		currentUserOrderHis.setCancelTime(userOrderHis.getCancelTime());
		currentUserOrderHis.setOrderTime(userOrderHis.getOrderTime());
		currentUserOrderHis.setNotifySpFlag(userOrderHis.getNotifySpFlag());
		//修改日志
		userOrderHisService.updateByPrimaryKeySelective(currentUserOrderHis);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentUserOrderHis,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<UserOrderHis>(currentUserOrderHis, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_UOH_E')")
	@ApiOperation(value = "Delete UserOrderHis", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<UserOrderHis> deleteUserOrderHis(@PathVariable("id") String id) {

		UserOrderHis userOrderHis = userOrderHisService.selectByPrimaryKey(id);
		if (userOrderHis == null) {
			return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NOT_FOUND);
		}

		userOrderHisService.deleteByPrimaryKey(id);
		//删除日志开始
		UserOrderHis delBean = new UserOrderHis();
		delBean.setUserOrderHisId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<UserOrderHis>(HttpRestStatus.NO_CONTENT);
	}
}
