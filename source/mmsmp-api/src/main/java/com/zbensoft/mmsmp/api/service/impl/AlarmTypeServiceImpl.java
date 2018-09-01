package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.AlarmTypeService;
import com.zbensoft.mmsmp.db.domain.AlarmType;
import com.zbensoft.mmsmp.db.mapper.AlarmTypeMapper;

@Service
public class AlarmTypeServiceImpl implements AlarmTypeService {
	@Autowired
	AlarmTypeMapper alarmTypeMapper;

	@Override
	public int deleteByPrimaryKey(String alarmTypeCode) {

		return alarmTypeMapper.deleteByPrimaryKey(alarmTypeCode);
	}

	@Override
	public int insert(AlarmType record) {

		return alarmTypeMapper.insert(record);
	}

	@Override
	public int insertSelective(AlarmType record) {

		return alarmTypeMapper.insertSelective(record);
	}

	@Override
	public AlarmType selectByPrimaryKey(String alarmTypeCode) {

		return alarmTypeMapper.selectByPrimaryKey(alarmTypeCode);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmType record) {

		return alarmTypeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AlarmType record) {

		return alarmTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<AlarmType> selectPage(AlarmType record) {
		return alarmTypeMapper.selectPage(record);
	}

	@Override
	public void deleteAll() {
		alarmTypeMapper.deleteAll();
	}

	@Override
	public int count(AlarmType alarmType) {
		return alarmTypeMapper.count(alarmType);
	}

	@Override
	public List<AlarmType> selectAll() {
		return alarmTypeMapper.selectAll();
	}

}
