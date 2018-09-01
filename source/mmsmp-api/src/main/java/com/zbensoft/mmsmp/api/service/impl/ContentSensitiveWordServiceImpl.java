package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ContentSensitiveWordService;
import com.zbensoft.mmsmp.db.domain.ContentInfo;
import com.zbensoft.mmsmp.db.domain.ContentSensitiveWord;
import com.zbensoft.mmsmp.db.mapper.ContentSensitiveWordMapper;

@Service
public class ContentSensitiveWordServiceImpl implements ContentSensitiveWordService{
@Autowired
ContentSensitiveWordMapper contentSensitiveWordMapper;
	
	public int deleteByPrimaryKey(String contentSensitiveWordId) {
		return contentSensitiveWordMapper.deleteByPrimaryKey(contentSensitiveWordId);
	}

	public int insert(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.insert(record);
	}

	public int insertSelective(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.insertSelective(record);
	}

	public ContentSensitiveWord selectByPrimaryKey(String contentSensitiveWordId) {
		return contentSensitiveWordMapper.selectByPrimaryKey(contentSensitiveWordId);
	}

	public int updateByPrimaryKeySelective(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ContentSensitiveWord> selectPage(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.selectPage(record);
	}

	@Override
	public int count(ContentSensitiveWord record) {
		return contentSensitiveWordMapper.count(record);
	}
	
	
}