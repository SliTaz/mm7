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
import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.api.service.api.UserServiceSendService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;
import com.zbensoft.mmsmp.db.domain.UserServiceSend;

import io.swagger.annotations.ApiOperation;
@RequestMapping(value = "/spStatisticsDailyReport")
@RestController
public class SpStatisticsDailyReportController {
	@Autowired
	SpInfoService spInfoService;
	@Autowired
	SpAccessService spAccessService;
	@Autowired
	ProvinceCityService provinceCityService;
	@Autowired
	UserOrderHisService userOrderHisService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	UserServiceSendService userServiceSendService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	//查询通知，支持分页
	@PreAuthorize("hasRole('R_SSDR_Q')")
	@ApiOperation(value = "Query SpInfo，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<UserOrderHis>> selectPage(
			@RequestParam(required = false) String spInfoId,@RequestParam(required = false) Integer orderType,
			@RequestParam(required = false) String orderTimeStart,@RequestParam(required = false) String orderTimeEnd,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		List<UserOrderHis> listNew = new ArrayList<>();
		int count1 = 0;
		SpInfo spInfo= new SpInfo();
		if(!(spInfoId==null || spInfoId.equals(""))){
			spInfo.setSpInfoId(spInfoId);
		}
		List<SpInfo> spInfoList = spInfoService.selectPage(spInfo);
		if(spInfoList==null || spInfoList.isEmpty()){
			return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
		}
		for (SpInfo spInfo2 : spInfoList) {
			ProductInfo productInfo1= new ProductInfo();
			UserOrderHis userOrderHis = new UserOrderHis();
			productInfo1.setCompanyCode(spInfo2.getCompanyCode());
			List<ProductInfo> productInfoList = productInfoService.selectPage(productInfo1);
			if(productInfoList==null || productInfoList.isEmpty()){
				return new ResponseRestEntity<List<UserOrderHis>>(new ArrayList<UserOrderHis>(), HttpRestStatus.NOT_FOUND);
			}
			userOrderHis.setSpInfoId(spInfo2.getSpInfoId());
			userOrderHis.setCompanyCode(spInfo2.getCompanyCode());
			userOrderHis.setOrderType(orderType);
			String str = orderTimeStart;
		    String des = "-";
		    int cnt = 0;
		    int offset = 0;
		    while((offset = str.indexOf(des, offset)) != -1){
		        offset = offset + des.length();
		        cnt++;
		    }
			if(cnt==1){
				userOrderHis.setOrderTimeStart(orderTimeStart + "-01");
			}else{
				userOrderHis.setOrderTimeStart(orderTimeStart);
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
				userOrderHis.setOrderTimeEnd(orderTimeEnd + "-01");
			}else{
				userOrderHis.setOrderTimeEnd(orderTimeEnd);
			}
			count1 = userOrderHisService.count(userOrderHis);
			userOrderHis.setOnDemandTimes(count1);
			userOrderHis.setOnDemandFee(productInfoList.get(0).getOnDemandFee());
			userOrderHis.setCountOnDemandFee(userOrderHis.getOnDemandFee()*userOrderHis.getOnDemandTimes());
			
			userOrderHis.setOrderType(MessageDef.ORDER_TYPE.ORDER_MONTHLY);
			
			
			userOrderHis.setOrderTimeStart("");
			if(cnts==1){
				userOrderHis.setOrderTimeEnd(orderTimeEnd + "-01");
			}else{
				userOrderHis.setOrderTimeEnd(orderTimeEnd);
			}
			int countOrder=userOrderHisService.count(userOrderHis);
			userOrderHis.setOrderTimeStart("");
			if(cnts==1){
				userOrderHis.setOrderTimeEnd(orderTimeStart + "-01");
			}else{
				userOrderHis.setOrderTimeEnd(orderTimeStart);
			}
			int countOrderBefore=userOrderHisService.count(userOrderHis);
			userOrderHis.setNewOrderNumber(countOrder-countOrderBefore);
			userOrderHis.setNewOrderNumberFee(userOrderHisService.count(userOrderHis)*(productInfoList.get(0).getOrderFee()));
			userOrderHis.setCountFee(userOrderHis.getCountOnDemandFee()+userOrderHis.getNewOrderNumberFee());
			
			UserServiceSend userServiceSend = new UserServiceSend();
			userServiceSend.setSpInfoId(spInfo2.getSpInfoId());
			userServiceSend.setSendServiceType(MessageDef.SEND_SERVICE_TYPE.ORDER);
			userOrderHis.setCountMMSDownlink(userServiceSendService.count(userServiceSend));
			userServiceSend.setStatus(MessageDef.USER_SERVICE_SEND_STATUS.TERMINAL_SUCCESS_RECEIVED);
			userOrderHis.setCountTerminalArrival(userServiceSendService.count(userServiceSend));
			userOrderHis.setOrderTimeStart(orderTimeStart+ "-01"+"---"+orderTimeEnd+"-01");
			listNew.add(userOrderHis);
		}
		int count=listNew.size();
		
	    return new ResponseRestEntity<List<UserOrderHis>>(listNew, HttpRestStatus.OK,count,count);
	}

}
