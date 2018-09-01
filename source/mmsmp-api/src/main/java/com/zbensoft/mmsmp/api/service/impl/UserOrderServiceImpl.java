package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserOrderService;
import com.zbensoft.mmsmp.db.domain.UserOrder;
import com.zbensoft.mmsmp.db.mapper.UserOrderMapper;
@Service
public class UserOrderServiceImpl implements UserOrderService {
	@Autowired
	UserOrderMapper  userOrderMapper;
	@Override
	public int deleteByPrimaryKey(String phoneNumber) {
		return userOrderMapper.deleteByPrimaryKey(phoneNumber);
	}

	@Override
	public int insert(UserOrder record) {
		return userOrderMapper.insert(record);
	}

	@Override
	public int insertSelective(UserOrder record) {
		return userOrderMapper.insertSelective(record);
	}

	@Override
	public UserOrder selectByPrimaryKey(String phoneNumber) {
		return userOrderMapper.selectByPrimaryKey(phoneNumber);
	}

	@Override
	public int updateByPrimaryKeySelective(UserOrder record) {
		return userOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserOrder record) {
		return userOrderMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserOrder> selectPage(UserOrder record) {
		return userOrderMapper.selectPage(record);
	}

	@Override
	public int count(UserOrder record) {
		return userOrderMapper.count(record);
	}

}
