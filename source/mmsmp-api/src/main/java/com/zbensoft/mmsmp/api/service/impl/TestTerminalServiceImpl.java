package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.TestTerminalService;
import com.zbensoft.mmsmp.db.domain.TestTerminal;
import com.zbensoft.mmsmp.db.mapper.TestTerminalMapper;

@Service
public class TestTerminalServiceImpl implements TestTerminalService{
	@Autowired
	TestTerminalMapper testTerminalMapper;

	@Override
	public int deleteByPrimaryKey(String testTerminalId) {
		// TODO Auto-generated method stub
		return testTerminalMapper.deleteByPrimaryKey(testTerminalId);
	}

	@Override
	public int insert(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.insert(record);
	}

	@Override
	public int insertSelective(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.insertSelective(record);
	}

	@Override
	public TestTerminal selectByPrimaryKey(String testTerminalId) {
		// TODO Auto-generated method stub
		return testTerminalMapper.selectByPrimaryKey(testTerminalId);
	}

	@Override
	public int updateByPrimaryKeySelective(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TestTerminal> selectPage(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.selectPage(record);
	}

	@Override
	public int count(TestTerminal record) {
		// TODO Auto-generated method stub
		return testTerminalMapper.count(record);
	}
}
