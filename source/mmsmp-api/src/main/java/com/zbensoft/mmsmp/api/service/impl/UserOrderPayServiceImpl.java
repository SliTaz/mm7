package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserOrderPayService;
import com.zbensoft.mmsmp.db.domain.AccessSendStatistics;
import com.zbensoft.mmsmp.db.domain.UserOrderPay;
import com.zbensoft.mmsmp.db.mapper.UserOrderPayMapper;
@Service
public class UserOrderPayServiceImpl implements UserOrderPayService {
	@Autowired
	UserOrderPayMapper  userOrderPayMapper;
	@Override
	public int deleteByPrimaryKey(String userOrderPayId) {
		return userOrderPayMapper.deleteByPrimaryKey(userOrderPayId);
	}

	@Override
	public int insert(UserOrderPay record) {
		return userOrderPayMapper.insert(record);
	}

	@Override
	public int insertSelective(UserOrderPay record) {
		return userOrderPayMapper.insertSelective(record);
	}

	@Override
	public UserOrderPay selectByPrimaryKey(String userOrderPayId) {
		return userOrderPayMapper.selectByPrimaryKey(userOrderPayId);
	}

	@Override
	public int updateByPrimaryKeySelective(UserOrderPay record) {
		return userOrderPayMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserOrderPay record) {
		return userOrderPayMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserOrderPay> selectPage(UserOrderPay record) {
		return userOrderPayMapper.selectPage(record);
	}

	@Override
	public int count(UserOrderPay record) {
		return userOrderPayMapper.count(record);
	}

	@Override
	public void deleteByPnPi(UserOrderPay userOrderPay) {
		userOrderPayMapper.deleteByPnPi(userOrderPay);		
	}

	@Override
	public UserOrderPay selectByPhoneNumberAndProductInfoId(UserOrderPay userOrderPay) {
		return userOrderPayMapper.selectByPhoneNumberAndProductInfoId(userOrderPay);
	}

	@Override
	public List<AccessSendStatistics> selectCountByOrderAndProductInfoId(AccessSendStatistics access) {
		// TODO Auto-generated method stub
		return userOrderPayMapper.selectCountByOrderAndProductInfoId(access);
	}

	@Override
	public List<AccessSendStatistics> selectCountByOnDemandAndProductInfoId(AccessSendStatistics access) {
		// TODO Auto-generated method stub
		return userOrderPayMapper.selectCountByOnDemandAndProductInfoId(access);
	}

}
