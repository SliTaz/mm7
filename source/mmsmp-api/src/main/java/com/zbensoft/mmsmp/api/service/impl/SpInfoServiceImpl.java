package com.zbensoft.mmsmp.api.service.impl;

import java.util.Collections;
import java.util.List;

import com.zbensoft.mmsmp.db.mapper.SpAccessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.SpInfoService;
import com.zbensoft.mmsmp.db.domain.SpInfo;
import com.zbensoft.mmsmp.db.mapper.SpInfoMapper;

@Service
public class SpInfoServiceImpl implements SpInfoService {
    @Autowired
    SpInfoMapper spInfoMapper;

    @Override
    public int deleteByPrimaryKey(String spInfoId) {
        return spInfoMapper.deleteByPrimaryKey(spInfoId);
    }

    @Override
    public int insert(SpInfo record) {
        return spInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(SpInfo record) {
        return spInfoMapper.insertSelective(record);
    }

    @Override
    public SpInfo selectByPrimaryKey(String spInfoId) {
        return spInfoMapper.selectByPrimaryKey(spInfoId);
    }

    @Override
    public int updateByPrimaryKeySelective(SpInfo record) {
        return spInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SpInfo record) {
        return spInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SpInfo> selectPage(SpInfo record) {
        return spInfoMapper.selectPage(record);
    }

    @Override
    public int count(SpInfo record) {
        return spInfoMapper.count(record);
    }

    @Override
    public List<SpInfo> getSpInfo(SpInfo record) {
        return spInfoMapper.getSpInfo(record);
    }

    @Override
    public List<SpInfo> selectAll() {
        // TODO Auto-generated method stub
        return spInfoMapper.selectAll();
    }

    @Override
    public List<SpInfo> getSpInfoByCompanyCode(String productInfoId) {
        // TODO Auto-generated method stub
        return spInfoMapper.getSpInfoByCompanyCode(productInfoId);
    }

    @Override
    public int getSpInfoCountById(String spId) {
        return spInfoMapper.getSpInfoCountById(spId);
    }

    @Override
    public void sycDeleteSpInfo(String spId) {
        spInfoMapper.sycDeleteSpInfo(spId);
    }

    @Override
    public List<String> getALlSpIds() {
        List<String> spIds = spInfoMapper.getALlSpIds();
        return spIds == null ? Collections.emptyList() : spIds;
    }

}
