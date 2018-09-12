package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserServiceSendService;
import com.zbensoft.mmsmp.db.domain.UserServiceSend;
import com.zbensoft.mmsmp.db.mapper.UserServiceSendMapper;

@Service
public class UserServiceSendServiceImpl implements UserServiceSendService{
	@Autowired
	UserServiceSendMapper userServiceSendMapper;
	@Override
	public int deleteByPrimaryKey(String userServiceSendId) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.deleteByPrimaryKey(userServiceSendId);
	}

	@Override
	public int insert(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.insert(record);
	}

	@Override
	public int insertSelective(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.insertSelective(record);
	}

	@Override
	public UserServiceSend selectByPrimaryKey(String userServiceSendId) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.selectByPrimaryKey(userServiceSendId);
	}

	@Override
	public int updateByPrimaryKeySelective(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserServiceSend> selectPage(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.selectPage(record);
	}

	@Override
	public int count(UserServiceSend record) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.count(record);
	}

	@Override
	public UserServiceSend selectByMessageId(String messageId) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.selectByMessageId(messageId);
	}

	@Override
	public UserServiceSend selectByRequestId(String requestId) {
		// TODO Auto-generated method stub
		return userServiceSendMapper.selectByRequestId(requestId);
	}

}
