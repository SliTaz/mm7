package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.UserRecvService;
import com.zbensoft.mmsmp.db.domain.UserRecv;
import com.zbensoft.mmsmp.db.mapper.UserRecvMapper;
@Service
public class UserRecvServiceImpl implements UserRecvService{
	@Autowired
	UserRecvMapper userRecvMapper;
	@Override
	public int deleteByPrimaryKey(String userRecvId) {
		// TODO Auto-generated method stub
		return userRecvMapper.deleteByPrimaryKey(userRecvId);
	}

	@Override
	public int insert(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.insertSelective(record);
	}

	@Override
	public UserRecv selectByPrimaryKey(String userRecvId) {
		// TODO Auto-generated method stub
		return userRecvMapper.selectByPrimaryKey(userRecvId);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserRecv> selectPage(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.selectPage(record);
	}

	@Override
	public int count(UserRecv record) {
		// TODO Auto-generated method stub
		return userRecvMapper.count(record);
	}

	@Override
	public UserRecv getLatestMoOrderMsgText(UserRecv userRecv) {
		List<UserRecv> userRecvLists=userRecvMapper.getLatestMoOrderMsgText(userRecv);
		if(userRecvLists!=null&&userRecvLists.size()>1){
			return userRecvLists.get(1);
		}else{
			return null;
		}
		
	}

}
