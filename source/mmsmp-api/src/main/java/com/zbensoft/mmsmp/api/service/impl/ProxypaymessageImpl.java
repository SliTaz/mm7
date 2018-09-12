package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ProxypaymessageService;
import com.zbensoft.mmsmp.db.domain.Proxypaymessage;
import com.zbensoft.mmsmp.db.mapper.ProxypaymessageMapper;

@Service
public class ProxypaymessageImpl implements ProxypaymessageService {
	@Autowired
	ProxypaymessageMapper proxypaymessageMapper;
	@Override
	public int deleteByPrimaryKey(String globalmessageid) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.deleteByPrimaryKey(globalmessageid);
	}

	@Override
	public int insert(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.insert(record);
	}

	@Override
	public int insertSelective(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.insertSelective(record);
	}

	@Override
	public Proxypaymessage selectByPrimaryKey(String globalmessageid) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.selectByPrimaryKey(globalmessageid);
	}

	@Override
	public int updateByPrimaryKeySelective(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Proxypaymessage> selectPage(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.selectPage(record);
	}

	@Override
	public int count(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.count(record);
	}

	@Override
	public Proxypaymessage getCooperKeyMessage(String globalmessageid) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.getCooperKeyMessage(globalmessageid);
	}

	@Override
	public Proxypaymessage getProxyPayMessage(Proxypaymessage record) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.getProxyPayMessage(record);
	}

	@Override
	public Proxypaymessage getProxyPayMessageById(String globalmessageid) {
		// TODO Auto-generated method stub
		return proxypaymessageMapper.getProxyPayMessageById(globalmessageid);
	}

}
