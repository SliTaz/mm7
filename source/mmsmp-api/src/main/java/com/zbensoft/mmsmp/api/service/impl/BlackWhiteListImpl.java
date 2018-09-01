package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.BlackWhiteListService;
import com.zbensoft.mmsmp.db.domain.BlackWhiteList;
import com.zbensoft.mmsmp.db.mapper.BlackWhiteListMapper;

@Service
public class BlackWhiteListImpl implements BlackWhiteListService {
	@Autowired
	BlackWhiteListMapper blackWhiteListMapper;
	@Override
	public int deleteByPrimaryKey(String blackWhiteList) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.deleteByPrimaryKey(blackWhiteList);
	}

	@Override
	public int insert(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.insert(record);
	}

	@Override
	public int insertSelective(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.insertSelective(record);
	}

	@Override
	public BlackWhiteList selectByPrimaryKey(String blackWhiteList) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.selectByPrimaryKey(blackWhiteList);
	}

	@Override
	public int updateByPrimaryKeySelective(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<BlackWhiteList> selectPage(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.selectPage(record);
	}

	@Override
	public int count(BlackWhiteList record) {
		// TODO Auto-generated method stub
		return blackWhiteListMapper.count(record);
	}
	

}
