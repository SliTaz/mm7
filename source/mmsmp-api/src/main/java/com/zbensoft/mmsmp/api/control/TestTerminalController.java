package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.zbensoft.mmsmp.api.service.api.TestTerminalProductService;
import com.zbensoft.mmsmp.api.service.api.TestTerminalService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.TestTerminal;
import com.zbensoft.mmsmp.db.domain.TestTerminalProductKey;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/testTerminal")
@RestController
public class TestTerminalController {
	@Autowired
	TestTerminalService testTerminalService;
	
	@Autowired
	TestTerminalProductService testTerminalProductService;
	@Autowired
	ProductInfoService productInfoService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	
//	@PreAuthorize("hasRole('R_UR_Q')")
	@ApiOperation(value = "Query UserRecv，Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<TestTerminal>> selectPage(@RequestParam(required = false) String phoneNumber,@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String start,@RequestParam(required = false) String length) {
		TestTerminal testTerminal = new TestTerminal();
		testTerminal.setMsisdn(phoneNumber);
		int count = testTerminalService.count(testTerminal);
		if (count == 0) {
			return new ResponseRestEntity<List<TestTerminal>>(new ArrayList<TestTerminal>(), HttpRestStatus.NOT_FOUND);
		}
		List<TestTerminal> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = testTerminalService.selectPage(testTerminal);

		} else {
			list = testTerminalService.selectPage(testTerminal);
		}

		// 分页 end
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<TestTerminal>>(new ArrayList<TestTerminal>(), HttpRestStatus.NOT_FOUND);
		}
		//根据productInfoId查询出，该产品下所有的测试审核产品，和原来list进行比较
		List<TestTerminal> listTransition = new ArrayList<TestTerminal>();
		if(productInfoId!=null){
			List<TestTerminalProductKey> testTerminalKeyList = testTerminalProductService.selectByProductInfoId(productInfoId);
			for (TestTerminalProductKey testTerminalProductKey : testTerminalKeyList) {
				for (TestTerminal testTerminal3 : list) {
					if(testTerminalProductKey.getTestTerminalId()==testTerminal3.getTestTerminalId()){
						listTransition.add(testTerminal3);
					}
				}
			}
		}
		//遍历获取productName数据
		List<TestTerminal> listNew = new ArrayList<TestTerminal>();
		if(listTransition == null || listTransition.isEmpty()){
			for (TestTerminal testTerminal2 : list) {
				TestTerminalProductKey testTerminalProduct = testTerminalProductService.selectByTestTerminalId(testTerminal2.getTestTerminalId());
				if(testTerminalProduct!=null){
					ProductInfo productInfo = productInfoService.selectByPrimaryKey(testTerminalProduct.getProductInfoId());
					if(productInfo!=null){
						testTerminal2.setProudctName(productInfo.getProductName());
					}
				}
				listNew.add(testTerminal2);
			}
		}else{
			for (TestTerminal testTerminal2 : listTransition) {
				TestTerminalProductKey testTerminalProduct = testTerminalProductService.selectByTestTerminalId(testTerminal2.getTestTerminalId());
				if(testTerminalProduct!=null){
					ProductInfo productInfo = productInfoService.selectByPrimaryKey(testTerminalProduct.getProductInfoId());
					if(productInfo!=null){
						testTerminal2.setProudctName(productInfo.getProductName());
					}
				}
				listNew.add(testTerminal2);
			}
		}

		return new ResponseRestEntity<List<TestTerminal>>(listNew, HttpRestStatus.OK, count, count);
	}

//	@PreAuthorize("hasRole('R_UR_Q')")
	@ApiOperation(value = "Query TestTerminal", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<TestTerminal> selectByPrimaryKey(@PathVariable("id") String id) {
		TestTerminal testTerminal = testTerminalService.selectByPrimaryKey(id);
		if (testTerminal == null) {
			return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<TestTerminal>(testTerminal, HttpRestStatus.OK);
	}
	
	@ApiOperation(value = "Query TestTerminalProduct", notes = "")
	@RequestMapping(value = "/TestTerminalProductKey/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<TestTerminal> selectByTestTerminalId(@PathVariable("id") String id) {
		TestTerminal testTerminal = testTerminalService.selectByPrimaryKey(id);
		if (testTerminal == null) {
			return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NOT_FOUND);
		}
		TestTerminalProductKey testTerminalProduct = testTerminalProductService.selectByTestTerminalId(id);
		testTerminal.setProductInfoId(testTerminalProduct.getProductInfoId());
		return new ResponseRestEntity<TestTerminal>(testTerminal, HttpRestStatus.OK);
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Add TestTerminal", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createUserRecv(@Valid @RequestBody TestTerminal testTerminal, BindingResult result, UriComponentsBuilder ucBuilder) {
		testTerminal.setTestTerminalId(IDGenerate.generateCommTwo(IDGenerate.CUSTOMER_MANAGER));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		testTerminal.setCreateTime(new Date());
		testTerminalService.insert(testTerminal);
		
		//表测试审核产品添加数据
		TestTerminalProductKey testTerminalProduct = new TestTerminalProductKey();
		testTerminalProduct.setTestTerminalId(testTerminal.getTestTerminalId());
		testTerminalProduct.setProductInfoId(testTerminal.getProductInfoId());
		testTerminalProductService.insert(testTerminalProduct);
		//新增日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, testTerminal,CommonLogImpl.CUSTOMER_MANAGE);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/testTerminal/{id}").buildAndExpand(testTerminal.getTestTerminalId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("testTerminal.create.created.message"));
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit UserRecv", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<TestTerminal> updateUserRecv(@PathVariable("id") String id, @Valid @RequestBody TestTerminal testTerminal, BindingResult result) {

		TestTerminal currentTestTerminal = testTerminalService.selectByPrimaryKey(id);

		if (currentTestTerminal == null) {
			return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}
		currentTestTerminal.setStatus(testTerminal.getStatus());
		currentTestTerminal.setAuditTime(testTerminal.getAuditTime());
		currentTestTerminal.setAuditUser(testTerminal.getAuditUser());
		currentTestTerminal.setCpInfoId(testTerminal.getCpInfoId());
		currentTestTerminal.setMsisdn(testTerminal.getMsisdn());
		currentTestTerminal.setRemark(testTerminal.getRemark());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				//System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			return new ResponseRestEntity<TestTerminal>(currentTestTerminal,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		testTerminalService.updateByPrimaryKey(currentTestTerminal);
		//修改日志
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentTestTerminal,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<TestTerminal>(currentTestTerminal, HttpRestStatus.OK,localeMessageSourceService.getMessage("testTerminal.update.ok.message"));
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Edit Part UserRecv", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<TestTerminal> updateTestTerminal(@PathVariable("id") String id, @RequestBody TestTerminal testTerminal) {

		TestTerminal currentTestTerminal = testTerminalService.selectByPrimaryKey(id);

		if (currentTestTerminal == null) {
			return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NOT_FOUND);
		}
		currentTestTerminal.setStatus(testTerminal.getStatus());
		currentTestTerminal.setAuditTime(testTerminal.getAuditTime());
		currentTestTerminal.setAuditUser(testTerminal.getAuditUser());
		currentTestTerminal.setCpInfoId(testTerminal.getCpInfoId());
		currentTestTerminal.setMsisdn(testTerminal.getMsisdn());
		currentTestTerminal.setRemark(testTerminal.getRemark());
		//修改日志
		testTerminalService.updateByPrimaryKeySelective(currentTestTerminal);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentTestTerminal,CommonLogImpl.CUSTOMER_MANAGE);
		return new ResponseRestEntity<TestTerminal>(currentTestTerminal, HttpRestStatus.OK);
	}

//	@PreAuthorize("hasRole('R_UR_E')")
	@ApiOperation(value = "Delete UserRecv", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<TestTerminal> deleteUserRecv(@PathVariable("id") String id) {
		TestTerminal testTerminal = testTerminalService.selectByPrimaryKey(id);
		if (testTerminal == null) {
			return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NOT_FOUND);
		}

		testTerminalService.deleteByPrimaryKey(id);
		
		TestTerminalProductKey testTerminalKey = testTerminalProductService.selectByTestTerminalId(id);
		testTerminalProductService.deleteByPrimaryKey(testTerminalKey);
		//删除日志开始
		TestTerminal delBean = new TestTerminal();
		delBean.setTestTerminalId(id);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, delBean,CommonLogImpl.CUSTOMER_MANAGE);
		//删除日志结束
		return new ResponseRestEntity<TestTerminal>(HttpRestStatus.NO_CONTENT);
	}
}
