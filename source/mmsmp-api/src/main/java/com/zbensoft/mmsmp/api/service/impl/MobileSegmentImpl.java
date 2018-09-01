package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.MobileSegmentService;
import com.zbensoft.mmsmp.db.domain.MobileSegment;
import com.zbensoft.mmsmp.db.mapper.MobileSegmentMapper;

@Service
public class MobileSegmentImpl implements MobileSegmentService {
	@Autowired
	MobileSegmentMapper mobileSegmentMapper;
	@Override
	public int deleteByPrimaryKey(String mobileSegmentId) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.deleteByPrimaryKey(mobileSegmentId);
	}

	@Override
	public int insert(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.insert(record);
	}

	@Override
	public int insertSelective(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.insertSelective(record);
	}

	@Override
	public MobileSegment selectByPrimaryKey(String mobileSegmentId) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.selectByPrimaryKey(mobileSegmentId);
	}

	@Override
	public int updateByPrimaryKeySelective(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<MobileSegment> selectPage(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.selectPage(record);
	}

	@Override
	public int count(MobileSegment record) {
		// TODO Auto-generated method stub
		return mobileSegmentMapper.count(record);
	}

}
