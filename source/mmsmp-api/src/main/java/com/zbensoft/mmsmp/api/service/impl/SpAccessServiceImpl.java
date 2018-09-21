package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.SpAccessService;
import com.zbensoft.mmsmp.db.domain.SpAccess;
import com.zbensoft.mmsmp.db.mapper.SpAccessMapper;
@Service
public class SpAccessServiceImpl implements SpAccessService {
	@Autowired
	SpAccessMapper scpAccess;
	@Override
	public int deleteByPrimaryKey(String spInfoId) {
		return scpAccess.deleteByPrimaryKey(spInfoId);
	}

	@Override
	public int insert(SpAccess record) {
		return scpAccess.insert(record);
	}

	@Override
	public int insertSelective(SpAccess record) {
		return scpAccess.insertSelective(record);
	}

	@Override
	public SpAccess selectByPrimaryKey(String spInfoId) {
		return scpAccess.selectByPrimaryKey(spInfoId);
	}

	@Override
	public int updateByPrimaryKeySelective(SpAccess record) {
		return scpAccess.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SpAccess record) {
		return scpAccess.updateByPrimaryKey(record);
	}

	@Override
	public List<SpAccess> selectPage(SpAccess record) {
		return scpAccess.selectPage(record);
	}

	@Override
	public int count(SpAccess record) {
		return scpAccess.count(record);
	}

	@Override
	public String getMaxServiceAccessNumber(String spid) {
		return scpAccess.getMaxServiceAccessNumber(spid);
	}

}
