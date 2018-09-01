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
import com.zbensoft.mmsmp.api.service.api.ContentInfoService;
import com.zbensoft.mmsmp.api.service.api.CpInfoService;
import com.zbensoft.mmsmp.db.domain.ContentInfo;
import com.zbensoft.mmsmp.db.domain.CpInfo;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/contentInfo")
@RestController
public class ContentInfoController {
	@Autowired
	ContentInfoService contentInfoService;
	
	@Autowired
	CpInfoService cpInfoService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	@PreAuthorize("hasRole('R_CI_Q')")
	@ApiOperation(value = "Query contentInfo, Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<ContentInfo>> selectPage(@RequestParam(required = false) String id,
			@RequestParam(required = false) String productInfoId,
			@RequestParam(required = false) String cpInfoId,	
			@RequestParam(required = false) String contentCode,	
			@RequestParam(required = false) String contentName,	
			@RequestParam(required = false) String contentRemark,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) Integer status,	
			@RequestParam(required = false) String contentPath,
			@RequestParam(required = false) Integer contentType,
			@RequestParam(required = false) String keyWord,	
			@RequestParam(required = false) Integer isRealtime,	
			@RequestParam(required = false) String realtimeContentUrl,	
			@RequestParam(required = false) String rejectReason,	
			@RequestParam(required = false) Integer isApplyDelete,	
			@RequestParam(required = false) String webPic,
			@RequestParam(required = false) String singer,	
			@RequestParam(required = false) String featureStr,	
			@RequestParam(required = false) String aduitUser,	
			@RequestParam(required = false) String versionId,	
			@RequestParam(required = false) Integer isSend,	
			@RequestParam(required = false) String smsText,
			@RequestParam(required = false) String setSendTimeUser,	
			@RequestParam(required = false) String adSendId,
			@RequestParam(required = false) String startTimeStart,
			@RequestParam(required = false) String startTimeEnd,
			@RequestParam(required = false) String endTimeStart,
			@RequestParam(required = false) String endTimeEnd,
			@RequestParam(required = false) String createTimeStart,
			@RequestParam(required = false) String createTimeEnd,
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		ContentInfo contentInfo = new ContentInfo();
		contentInfo.setContentInfoId(id);
		contentInfo.setProductInfoId(productInfoId);
		contentInfo.setCpInfoId(cpInfoId);
		contentInfo.setContentCode(contentCode);
		contentInfo.setContentName(contentName);
		contentInfo.setContentRemark(contentRemark);
		contentInfo.setAuthor(author);
		contentInfo.setStatus(status);
		contentInfo.setContentPath(contentPath);
		contentInfo.setContentType(contentType);
		contentInfo.setKeyWord(keyWord);
		contentInfo.setIsRealtime(isRealtime);
		contentInfo.setRealtimeContentUrl(realtimeContentUrl);
		contentInfo.setRejectReason(rejectReason);
		contentInfo.setIsApplyDelete(isApplyDelete);
		contentInfo.setWebPic(webPic);
		contentInfo.setSinger(singer);
		contentInfo.setFeatureStr(featureStr);
		contentInfo.setAdSendId(adSendId);
		contentInfo.setVersionId(versionId);
		contentInfo.setIsSend(isSend);
		contentInfo.setSmsText(smsText);
		contentInfo.setSetSendTimeUser(setSendTimeUser);
		contentInfo.setAdSendId(adSendId);
		contentInfo.setStartTimeStart(startTimeStart);
		contentInfo.setStartTimeEnd(startTimeEnd);
		contentInfo.setEndTimeStart(endTimeStart);
		contentInfo.setEndTimeEnd(endTimeEnd);
		contentInfo.setCreateTimeStart(createTimeStart);
		contentInfo.setCreateTimeEnd(createTimeEnd);
		int count = contentInfoService.count(contentInfo);
		if (count == 0) {
			return new ResponseRestEntity<List<ContentInfo>>(new ArrayList<ContentInfo>(), HttpRestStatus.NOT_FOUND);
		}
		List<ContentInfo> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = contentInfoService.selectPage(contentInfo);
			// System.out.println("pageNum:"+pageNum+";pageSize:"+pageSize);
			// System.out.println("list.size:"+list.size());

		} else {
			list = contentInfoService.selectPage(contentInfo);
		}
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ContentInfo>>(new ArrayList<ContentInfo>(),HttpRestStatus.NOT_FOUND);
		}
		List<ContentInfo> listNew = new ArrayList<ContentInfo>();
		for(ContentInfo contentInfoNew : list){
			CpInfo cpInfo = cpInfoService.selectByPrimaryKey(contentInfoNew.getCpInfoId());
			if(cpInfo != null){
				contentInfoNew.setCpName(cpInfo.getCpName());
			}
			listNew.add(contentInfoNew);
		}
		
		return new ResponseRestEntity<List<ContentInfo>>(listNew, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_CI_Q')")
	@ApiOperation(value = "Query contentInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<ContentInfo> selectByPrimaryKey(@PathVariable("id") String id) {
		ContentInfo contentInfo = contentInfoService.selectByPrimaryKey(id);
		if (contentInfo == null) {
			return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<ContentInfo>(contentInfo, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_CI_E')")
	@ApiOperation(value = "Add contentInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> createContentInfo(@Valid @RequestBody ContentInfo contentInfo,BindingResult result, UriComponentsBuilder ucBuilder) {
		contentInfo.setContentInfoId(IDGenerate.generateCommOne(IDGenerate.CONTENT_INFO));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		contentInfoService.insert(contentInfo);

		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, contentInfo,CommonLogImpl.CONTENT_INFO);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/contentInfo/{id}").buildAndExpand(contentInfo.getContentInfoId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	@PreAuthorize("hasRole('R_CI_E')")
	@ApiOperation(value = "Edit contentInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<ContentInfo> update(@PathVariable("id") String id,@Valid @RequestBody ContentInfo contentInfo, BindingResult result) {

		ContentInfo currentContentInfo = contentInfoService.selectByPrimaryKey(id);

		if (currentContentInfo == null) {
			return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentContentInfo.setProductInfoId(contentInfo.getProductInfoId());
		currentContentInfo.setCpInfoId(contentInfo.getCpInfoId());
		currentContentInfo.setContentCode(contentInfo.getContentCode());
		currentContentInfo.setContentName(contentInfo.getContentName());
		currentContentInfo.setContentRemark(contentInfo.getContentRemark());
		currentContentInfo.setCreateTime(contentInfo.getCreateTime());
		currentContentInfo.setAuthor(contentInfo.getAuthor());
		currentContentInfo.setStatus(contentInfo.getStatus());
		currentContentInfo.setContentPath(contentInfo.getContentPath());
		currentContentInfo.setContentType(contentInfo.getContentType());
		currentContentInfo.setKeyWord(contentInfo.getKeyWord());
		currentContentInfo.setIsRealtime(contentInfo.getIsRealtime());
		currentContentInfo.setRealtimeContentUrl(contentInfo.getRealtimeContentUrl());
		currentContentInfo.setRejectReason(contentInfo.getRejectReason());
		currentContentInfo.setIsApplyDelete(contentInfo.getIsApplyDelete());
		currentContentInfo.setWebPic(contentInfo.getWebPic());
		currentContentInfo.setStartTime(contentInfo.getStartTime());
		currentContentInfo.setEndTime(contentInfo.getEndTime());
		currentContentInfo.setSinger(contentInfo.getSinger());
		currentContentInfo.setFeatureStr(contentInfo.getFeatureStr());
		currentContentInfo.setAdSendId(contentInfo.getAdSendId());
		currentContentInfo.setVersionId(contentInfo.getVersionId());
		currentContentInfo.setIsSend(contentInfo.getIsSend());
		currentContentInfo.setSmsText(contentInfo.getSmsText());
		currentContentInfo.setLastAuditTime(contentInfo.getLastAuditTime());
		currentContentInfo.setSetSendTimeUser(contentInfo.getSetSendTimeUser());
		currentContentInfo.setAdSendId(contentInfo.getAdSendId());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
//				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}

			return new ResponseRestEntity<ContentInfo>(currentContentInfo,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		contentInfoService.updateByPrimaryKey(currentContentInfo);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentContentInfo,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentInfo>(currentContentInfo, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_CI_E')")
	@ApiOperation(value = "Edit Part contentInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<ContentInfo> updateSelective(@PathVariable("id") String id,
			@RequestBody ContentInfo contentInfo) {

		ContentInfo currentContentInfo = contentInfoService.selectByPrimaryKey(id);

		if (currentContentInfo == null) {
			return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NOT_FOUND);
		}
		currentContentInfo.setProductInfoId(contentInfo.getProductInfoId());
		currentContentInfo.setCpInfoId(contentInfo.getCpInfoId());
		currentContentInfo.setContentCode(contentInfo.getContentCode());
		currentContentInfo.setContentName(contentInfo.getContentName());
		currentContentInfo.setContentRemark(contentInfo.getContentRemark());
		currentContentInfo.setCreateTime(contentInfo.getCreateTime());
		currentContentInfo.setAuthor(contentInfo.getAuthor());
		currentContentInfo.setStatus(contentInfo.getStatus());
		currentContentInfo.setContentPath(contentInfo.getContentPath());
		currentContentInfo.setContentType(contentInfo.getContentType());
		currentContentInfo.setKeyWord(contentInfo.getKeyWord());
		currentContentInfo.setIsRealtime(contentInfo.getIsRealtime());
		currentContentInfo.setRealtimeContentUrl(contentInfo.getRealtimeContentUrl());
		currentContentInfo.setRejectReason(contentInfo.getRejectReason());
		currentContentInfo.setIsApplyDelete(contentInfo.getIsApplyDelete());
		currentContentInfo.setWebPic(contentInfo.getWebPic());
		currentContentInfo.setStartTime(contentInfo.getStartTime());
		currentContentInfo.setEndTime(contentInfo.getEndTime());
		currentContentInfo.setSinger(contentInfo.getSinger());
		currentContentInfo.setFeatureStr(contentInfo.getFeatureStr());
		currentContentInfo.setAdSendId(contentInfo.getAdSendId());
		currentContentInfo.setVersionId(contentInfo.getVersionId());
		currentContentInfo.setIsSend(contentInfo.getIsSend());
		currentContentInfo.setSmsText(contentInfo.getSmsText());
		currentContentInfo.setLastAuditTime(contentInfo.getLastAuditTime());
		currentContentInfo.setSetSendTimeUser(contentInfo.getSetSendTimeUser());
		currentContentInfo.setAdSendId(contentInfo.getAdSendId());
		contentInfoService.updateByPrimaryKeySelective(currentContentInfo);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentContentInfo,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentInfo>(currentContentInfo, HttpRestStatus.OK);
	}
	
	//批量
//	@PreAuthorize("hasRole('R_SYSTEM_L_E')")
//	@ApiOperation(value = "Delete Many contentInfo", notes = "")
//	@RequestMapping(value = "/deleteMany/{id}", method = RequestMethod.DELETE)
//	public ResponseRestEntity<SysLog> deleteSysLogMany(@PathVariable("id") String id) {
//        String[] idStr = id.split(",");
//        if(idStr!=null){
//        	for(String str :idStr){
//        	  sysLogService.deleteByPrimaryKey(str);
//        	}
//        }
//		return new ResponseRestEntity<SysLog>(HttpRestStatus.NO_CONTENT);
//	}
	

	@PreAuthorize("hasRole('R_CI_E')")
	@ApiOperation(value = "Delete contentInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ContentInfo> delete(@PathVariable("id") String id) {

		ContentInfo contentInfo = contentInfoService.selectByPrimaryKey(id);
		if (contentInfo == null) {
			return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NOT_FOUND);
		}

		contentInfoService.deleteByPrimaryKey(id);

		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, contentInfo,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentInfo>(HttpRestStatus.NO_CONTENT);
	}

}