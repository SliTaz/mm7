package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.CpInfoService;
import com.zbensoft.mmsmp.db.domain.CpInfo;
import com.zbensoft.mmsmp.db.mapper.CpInfoMapper;

@Service
public class CpInfoServiceImpl implements CpInfoService{
	@Autowired
	CpInfoMapper cpInfoMapper;
	@Override
	public int deleteByPrimaryKey(String cpInfoId) {
		// TODO Auto-generated method stub
		return cpInfoMapper.deleteByPrimaryKey(cpInfoId);
	}

	@Override
	public int insert(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.insertSelective(record);
	}

	@Override
	public CpInfo selectByPrimaryKey(String cpInfoId) {
		// TODO Auto-generated method stub
		return cpInfoMapper.selectByPrimaryKey(cpInfoId);
	}

	@Override
	public int updateByPrimaryKeySelective(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<CpInfo> selectPage(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.selectPage(record);
	}

	@Override
	public int count(CpInfo record) {
		// TODO Auto-generated method stub
		return cpInfoMapper.count(record);
	}

}
