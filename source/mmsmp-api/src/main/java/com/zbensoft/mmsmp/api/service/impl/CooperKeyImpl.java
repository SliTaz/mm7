package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.CooperKeyService;
import com.zbensoft.mmsmp.db.domain.CooperKey;
import com.zbensoft.mmsmp.db.mapper.CooperKeyMapper;
@Service
public class CooperKeyImpl implements CooperKeyService {
	@Autowired
	CooperKeyMapper  cooperKeyMapper;
	@Override
	public int deleteByPrimaryKey(String keyId) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.deleteByPrimaryKey(keyId);
	}

	@Override
	public int insert(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.insert(record);
	}

	@Override
	public int insertSelective(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.insertSelective(record);
	}

	@Override
	public CooperKey selectByPrimaryKey(String keyId) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.selectByPrimaryKey(keyId);
	}

	@Override
	public int updateByPrimaryKeySelective(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CooperKey> selectPage(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.selectPage(record);
	}

	@Override
	public int count(CooperKey record) {
		// TODO Auto-generated method stub
		return cooperKeyMapper.count(record);
	}

}
