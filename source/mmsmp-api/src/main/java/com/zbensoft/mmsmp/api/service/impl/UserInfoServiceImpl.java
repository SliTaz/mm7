package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserInfoService;
import com.zbensoft.mmsmp.db.domain.UserInfo;
import com.zbensoft.mmsmp.db.mapper.UserInfoMapper;
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	UserInfoMapper userInfoMapper;
	@Override
	public int deleteByPrimaryKey(String phoneNumber) {
		return userInfoMapper.deleteByPrimaryKey(phoneNumber);
	}

	@Override
	public int insert(UserInfo record) {
		return userInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(UserInfo record) {
		return userInfoMapper.insertSelective(record);
	}

	@Override
	public UserInfo selectByPrimaryKey(String phoneNumber) {
		return userInfoMapper.selectByPrimaryKey(phoneNumber);
	}

	@Override
	public int updateByPrimaryKeySelective(UserInfo record) {
		return userInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserInfo record) {
		return userInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserInfo> selectPage(UserInfo record) {
		return userInfoMapper.selectPage(record);
	}

	@Override
	public int count(UserInfo record) {
		return userInfoMapper.count(record);
	}

}
