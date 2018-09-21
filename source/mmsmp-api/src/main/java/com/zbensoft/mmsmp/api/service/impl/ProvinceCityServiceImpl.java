package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ProvinceCityService;
import com.zbensoft.mmsmp.db.domain.MobileSegment;

import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.ProvinceWithCity;
import com.zbensoft.mmsmp.db.mapper.MobileSegmentMapper;
import com.zbensoft.mmsmp.db.mapper.ProvinceCityMapper;

@Service
public class ProvinceCityServiceImpl implements ProvinceCityService{
	@Autowired
	ProvinceCityMapper provinceCityMapper;
	@Autowired
	MobileSegmentMapper mobileSegmentMapper;
	@Override
	public int deleteByPrimaryKey(String provinceCityId) {
		// TODO Auto-generated method stub
		MobileSegment mobileSegment  = new MobileSegment();
		mobileSegment.setCityId(provinceCityId);
		List<MobileSegment> list = mobileSegmentMapper.selectPage(mobileSegment);
		if(list!=null&&list.size()>0)
		{   for(MobileSegment temp : list) {
			
			if(temp!=null){
				mobileSegmentMapper.deleteByPrimaryKey(temp.getMobileSegmentId());
			}
			} 
			
		}
		return provinceCityMapper.deleteByPrimaryKey(provinceCityId);
	}

	@Override
	public int insert(ProvinceCity record) {
		// TODO Auto-generated method stub
		return provinceCityMapper.insert(record);
	}

	@Override
	public int insertSelective(ProvinceCity record) {
		// TODO Auto-generated method stub
		return provinceCityMapper.insertSelective(record);
	}

	@Override
	public ProvinceCity selectByPrimaryKey(String provinceCityId) {
		// TODO Auto-generated method stub
		return provinceCityMapper.selectByPrimaryKey(provinceCityId);
	}

	@Override
	public int updateByPrimaryKeySelective(ProvinceCity record) {
		// TODO Auto-generated method stub
		return provinceCityMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProvinceCity record) {
		// TODO Auto-generated method stub
		return provinceCityMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProvinceCity> selectPage(ProvinceCity ProvinceCity) {
		// TODO Auto-generated method stub
		return provinceCityMapper.selectPage(ProvinceCity);
	}

	@Override
	public int count(ProvinceCity ProvinceCity) {
		// TODO Auto-generated method stub
		return provinceCityMapper.count(ProvinceCity);
	}

	@Override
	public List<ProvinceCity> AdvanceSelectPage(ProvinceCity ProvinceCity) {
		// TODO Auto-generated method stub
		return provinceCityMapper.AdvanceSelectPage(ProvinceCity);
	}

	@Override
	public int AdvanceCount(ProvinceCity ProvinceCity) {
		// TODO Auto-generated method stub
		return provinceCityMapper.AdvanceCount(ProvinceCity);
	}

	@Override
	public int countAllProvinceAndCity(ProvinceCity provinceCity) {
		// TODO Auto-generated method stub
		return provinceCityMapper.countAllProvinceAndCity(provinceCity);
	}
    
}
