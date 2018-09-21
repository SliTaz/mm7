package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserOrderHisService;
import com.zbensoft.mmsmp.db.domain.UserOrderHis;
import com.zbensoft.mmsmp.db.mapper.UserOrderHisMapper;

@Service
public class UserOrderHisServiceImpl implements UserOrderHisService{
	@Autowired
	UserOrderHisMapper userOrderHisMapper;

	@Override
	public int deleteByPrimaryKey(String userOrderHisId) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.deleteByPrimaryKey(userOrderHisId);
	}

	@Override
	public int insert(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.insert(record);
	}

	@Override
	public int insertSelective(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.insertSelective(record);
	}

	@Override
	public UserOrderHis selectByPrimaryKey(String userOrderHisId) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.selectByPrimaryKey(userOrderHisId);
	}

	@Override
	public int updateByPrimaryKeySelective(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserOrderHis> selectPage(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.selectPage(record);
	}

	@Override
	public int count(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.count(record);
	}

	@Override
	public UserOrderHis selectByPhoneAndProductId(UserOrderHis record) {
		// TODO Auto-generated method stub
		return userOrderHisMapper.selectByPhoneAndProductId(record);
	}

	@Override
	public void updateStatus(UserOrderHis record) {
		// TODO Auto-generated method stub
		userOrderHisMapper.updateStatus(record);
	}

	@Override
	public void updateStatusByPhoneNumber(UserOrderHis record) {
		// TODO Auto-generated method stub
		userOrderHisMapper.updateStatusByPhoneNumber(record);
	}
}
