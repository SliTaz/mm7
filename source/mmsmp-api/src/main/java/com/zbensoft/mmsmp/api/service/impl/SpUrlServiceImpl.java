package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.SpUrlService;
import com.zbensoft.mmsmp.db.domain.SpUrl;
import com.zbensoft.mmsmp.db.mapper.SpUrlMapper;
@Service
public class SpUrlServiceImpl implements SpUrlService {
	@Autowired
	SpUrlMapper spUrlMapper;

	@Override
	public int deleteByPrimaryKey(String spUrlId) {
		return spUrlMapper.deleteByPrimaryKey(spUrlId);
	}

	@Override
	public int insert(SpUrl record) {
		return spUrlMapper.insert(record);
	}

	@Override
	public int insertSelective(SpUrl record) {
		return spUrlMapper.insertSelective(record);
	}

	@Override
	public SpUrl selectByPrimaryKey(String spUrlId) {
		return spUrlMapper.selectByPrimaryKey(spUrlId);
	}

	@Override
	public int updateByPrimaryKeySelective(SpUrl record) {
		return spUrlMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SpUrl record) {
		return spUrlMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SpUrl> selectPage(SpUrl record) {
		return spUrlMapper.selectPage(record);
	}

	@Override
	public int count(SpUrl record) {
		return spUrlMapper.count(record);
	}

}
