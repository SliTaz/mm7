package com.zbensoft.mmsmp.api.control;

import java.util.ArrayList;
import java.util.Date;
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
import com.zbensoft.mmsmp.api.service.api.ContentSensitiveWordService;
import com.zbensoft.mmsmp.db.domain.ContentSensitiveWord;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/contentSensitiveWord")
@RestController
public class ContentSensitiveWordController {
	@Autowired
	ContentSensitiveWordService contentSensitiveWordService;
	
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;

	@PreAuthorize("hasRole('R_CSW_Q')")
	@ApiOperation(value = "Query ContentSensitiveWord, Support paging", notes = "")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseRestEntity<List<ContentSensitiveWord>> selectPage(@RequestParam(required = false) String id,
			@RequestParam(required = false) String word,
			@RequestParam(required = false) String createrUser,	
			@RequestParam(required = false) Integer type,	
			@RequestParam(required = false) String createTimeStart,	
			@RequestParam(required = false) String createTimeEnd,	
			@RequestParam(required = false) String updateTimeStart,	
			@RequestParam(required = false) String updateTimeEnd,	
			@RequestParam(required = false) String start,
			@RequestParam(required = false) String length) {
		ContentSensitiveWord ContentSensitiveWord = new ContentSensitiveWord();
		ContentSensitiveWord.setContentSensitiveWordId(id);
		ContentSensitiveWord.setCreateTimeStart(createTimeStart);
		ContentSensitiveWord.setCreateTimeEnd(createTimeEnd);
		ContentSensitiveWord.setUpdateTimeStart(updateTimeStart);
		ContentSensitiveWord.setUpdateTimeEnd(updateTimeEnd);
		ContentSensitiveWord.setType(type);
		ContentSensitiveWord.setCreaterUser(createrUser);
		ContentSensitiveWord.setWord(word);
		
		
		int count = contentSensitiveWordService.count(ContentSensitiveWord);
		if (count == 0) {
			return new ResponseRestEntity<List<ContentSensitiveWord>>(new ArrayList<ContentSensitiveWord>(), HttpRestStatus.NOT_FOUND);
		}
		List<ContentSensitiveWord> list = null;
		// 分页 start
		if (start != null && length != null) {// 需要进行分页
			/*
			 * 第一个参数是第几页；第二个参数是每页显示条数。
			 */
			int pageNum = PageHelperUtil.getPageNum(start, length);
			int pageSize = PageHelperUtil.getPageSize(start, length);
			PageHelper.startPage(pageNum, pageSize);
			list = contentSensitiveWordService.selectPage(ContentSensitiveWord);
			// System.out.println("pageNum:"+pageNum+";pageSize:"+pageSize);
			// System.out.println("list.size:"+list.size());

		} else {
			list = contentSensitiveWordService.selectPage(ContentSensitiveWord);
		}
		if (list == null || list.isEmpty()) {
			return new ResponseRestEntity<List<ContentSensitiveWord>>(new ArrayList<ContentSensitiveWord>(),HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<List<ContentSensitiveWord>>(list, HttpRestStatus.OK, count, count);
	}

	@PreAuthorize("hasRole('R_CSW_Q')")
	@ApiOperation(value = "Query contentInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseRestEntity<ContentSensitiveWord> selectByPrimaryKey(@PathVariable("id") String id) {
		ContentSensitiveWord contentSensitiveWord = contentSensitiveWordService.selectByPrimaryKey(id);
		if (contentSensitiveWord == null) {
			return new ResponseRestEntity<ContentSensitiveWord>(HttpRestStatus.NOT_FOUND);
		}
		return new ResponseRestEntity<ContentSensitiveWord>(contentSensitiveWord, HttpRestStatus.OK);
	}

	@PreAuthorize("hasRole('R_CSW_E')")
	@ApiOperation(value = "Add contentInfo", notes = "")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseRestEntity<Void> create(@Valid @RequestBody ContentSensitiveWord contentSensitiveWord,BindingResult result, UriComponentsBuilder ucBuilder) {
		contentSensitiveWord.setContentSensitiveWordId(IDGenerate.generateCommOne(IDGenerate.CONTENT_SENSITIVE_WORD));
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();

			return new ResponseRestEntity<Void>(HttpRestStatusFactory.createStatus(list),
					HttpRestStatusFactory.createStatusMessage(list));
		}
		contentSensitiveWordService.insert(contentSensitiveWord);

		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_INSERT, contentSensitiveWord,CommonLogImpl.CONTENT_INFO);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/contentSensitiveWord/{id}").buildAndExpand(contentSensitiveWord.getContentSensitiveWordId()).toUri());
		return new ResponseRestEntity<Void>(headers, HttpRestStatus.CREATED,localeMessageSourceService.getMessage("common.create.created.message"));
	}

	@PreAuthorize("hasRole('R_CSW_E')")
	@ApiOperation(value = "Edit contentInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseRestEntity<ContentSensitiveWord> update(@PathVariable("id") String id,@Valid @RequestBody ContentSensitiveWord contentSensitiveWord, BindingResult result) {

		ContentSensitiveWord currentContentSensitiveWord = contentSensitiveWordService.selectByPrimaryKey(id);

		if (currentContentSensitiveWord == null) {
			return new ResponseRestEntity<ContentSensitiveWord>(HttpRestStatus.NOT_FOUND,localeMessageSourceService.getMessage("common.update.not_found.message"));
		}

		currentContentSensitiveWord.setWord(contentSensitiveWord.getWord());
		currentContentSensitiveWord.setUpdateTime(new Date());
		currentContentSensitiveWord.setType(contentSensitiveWord.getType());
		currentContentSensitiveWord.setRemark(contentSensitiveWord.getRemark());
		currentContentSensitiveWord.setCreaterUser(contentSensitiveWord.getCreaterUser());
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
//				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}

			return new ResponseRestEntity<ContentSensitiveWord>(currentContentSensitiveWord,HttpRestStatusFactory.createStatus(list),HttpRestStatusFactory.createStatusMessage(list));
		}
		contentSensitiveWordService.updateByPrimaryKey(currentContentSensitiveWord);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentContentSensitiveWord,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentSensitiveWord>(currentContentSensitiveWord, HttpRestStatus.OK,localeMessageSourceService.getMessage("common.update.ok.message"));
	}

	@PreAuthorize("hasRole('R_CSW_E')")
	@ApiOperation(value = "Edit Part contentInfo", notes = "")
	@RequestMapping(value = "{id}", method = RequestMethod.PATCH)
	public ResponseRestEntity<ContentSensitiveWord> updateSelective(@PathVariable("id") String id,
			@RequestBody ContentSensitiveWord contentSensitiveWord) {

		ContentSensitiveWord currentContentSensitiveWord = contentSensitiveWordService.selectByPrimaryKey(id);

		if (currentContentSensitiveWord == null) {
			return new ResponseRestEntity<ContentSensitiveWord>(HttpRestStatus.NOT_FOUND);
		}
		currentContentSensitiveWord.setCreateTime(contentSensitiveWord.getCreateTime());
		currentContentSensitiveWord.setWord(contentSensitiveWord.getWord());
		currentContentSensitiveWord.setUpdateTime(contentSensitiveWord.getUpdateTime());
		currentContentSensitiveWord.setType(contentSensitiveWord.getType());
		contentSensitiveWordService.updateByPrimaryKeySelective(currentContentSensitiveWord);
		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_UPDATE, currentContentSensitiveWord,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentSensitiveWord>(currentContentSensitiveWord, HttpRestStatus.OK);
	}
	

	

	@PreAuthorize("hasRole('R_CSW_E')")
	@ApiOperation(value = "Delete contentInfo", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseRestEntity<ContentSensitiveWord> delete(@PathVariable("id") String id) {

		ContentSensitiveWord contentSensitiveWord = contentSensitiveWordService.selectByPrimaryKey(id);
		if (contentSensitiveWord == null) {
			return new ResponseRestEntity<ContentSensitiveWord>(HttpRestStatus.NOT_FOUND);
		}

		contentSensitiveWordService.deleteByPrimaryKey(id);

		CommonLogImpl.insertLog(CommonLogImpl.OPERTYPE_DELETE, contentSensitiveWord,CommonLogImpl.CONTENT_INFO);
		return new ResponseRestEntity<ContentSensitiveWord>(HttpRestStatus.NO_CONTENT);
	}

}