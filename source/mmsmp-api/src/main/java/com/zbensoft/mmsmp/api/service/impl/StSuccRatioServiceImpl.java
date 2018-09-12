package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.StSuccRatioService;
import com.zbensoft.mmsmp.db.domain.StSuccRatio;
import com.zbensoft.mmsmp.db.domain.StSuccRatioKey;
import com.zbensoft.mmsmp.db.mapper.StSuccRatioMapper;
@Service
public class StSuccRatioServiceImpl implements StSuccRatioService{
	@Autowired
	StSuccRatioMapper stSuccRatioMapper;
	@Override
	public int deleteByPrimaryKey(StSuccRatioKey key) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.insert(record);
	}

	@Override
	public int insertSelective(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.insertSelective(record);
	}

	@Override
	public StSuccRatio selectByPrimaryKey(StSuccRatioKey key) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<StSuccRatio> selectPage(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.selectPage(record);
	}

	@Override
	public int count(StSuccRatio record) {
		// TODO Auto-generated method stub
		return stSuccRatioMapper.count(record);
	}

}
