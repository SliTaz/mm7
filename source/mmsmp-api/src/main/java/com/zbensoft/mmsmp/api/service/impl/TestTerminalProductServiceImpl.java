package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.TestTerminalProductService;
import com.zbensoft.mmsmp.db.domain.TestTerminalProductKey;
import com.zbensoft.mmsmp.db.mapper.TestTerminalProductMapper;

@Service
public class TestTerminalProductServiceImpl implements TestTerminalProductService{
	@Autowired
	TestTerminalProductMapper testTerminalProductMapper;

	@Override
	public int deleteByPrimaryKey(TestTerminalProductKey key) {
		// TODO Auto-generated method stub
		return testTerminalProductMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(TestTerminalProductKey record) {
		// TODO Auto-generated method stub
		return testTerminalProductMapper.insert(record);
	}

	@Override
	public int insertSelective(TestTerminalProductKey record) {
		// TODO Auto-generated method stub
		return testTerminalProductMapper.insertSelective(record);
	}

	@Override
	public TestTerminalProductKey selectByTestTerminalId(String TestTerminalId) {
		// TODO Auto-generated method stub
		return testTerminalProductMapper.selectByTestTerminalId(TestTerminalId);
	}

	@Override
	public List<TestTerminalProductKey> selectByProductInfoId(String productInfoId) {
		// TODO Auto-generated method stub
		return testTerminalProductMapper.selectByProductInfoId(productInfoId);
	}
}
