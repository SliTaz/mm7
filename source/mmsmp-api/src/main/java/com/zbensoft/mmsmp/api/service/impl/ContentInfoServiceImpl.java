package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ContentInfoService;
import com.zbensoft.mmsmp.db.domain.ContentInfo;
import com.zbensoft.mmsmp.db.domain.SysLog;
import com.zbensoft.mmsmp.db.mapper.ContentInfoMapper;

@Service
public class ContentInfoServiceImpl implements ContentInfoService{
	@Autowired
	ContentInfoMapper contentInfoMapper;
	public int deleteByPrimaryKey(String contentInfoId) {
		return contentInfoMapper.deleteByPrimaryKey(contentInfoId);
	}

	public int insert(ContentInfo record) {
		return contentInfoMapper.insert(record);
	}

	public int insertSelective(ContentInfo record) {
		return contentInfoMapper.insertSelective(record);
	}

	public ContentInfo selectByPrimaryKey(String contentInfoId) {
		return contentInfoMapper.selectByPrimaryKey(contentInfoId);
	}

	public int updateByPrimaryKeySelective(ContentInfo record) {
		return contentInfoMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(ContentInfo record) {
		return contentInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ContentInfo> selectPage(ContentInfo record) {
		return contentInfoMapper.selectPage(record);
	}

	@Override
	public int count(ContentInfo record) {
		return contentInfoMapper.count(record);
	}
	
	
	
}