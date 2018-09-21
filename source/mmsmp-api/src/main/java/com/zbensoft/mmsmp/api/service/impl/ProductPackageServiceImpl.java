package com.zbensoft.mmsmp.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ProductPackageService;
import com.zbensoft.mmsmp.db.domain.ProductPackageKey;
import com.zbensoft.mmsmp.db.mapper.ProductPackageMapper;

@Service
public class ProductPackageServiceImpl implements ProductPackageService{
	@Autowired
	ProductPackageMapper productPackageMapper;

	@Override
	public int deleteByPrimaryKey(ProductPackageKey key) {
		// TODO Auto-generated method stub
		return productPackageMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(ProductPackageKey record) {
		// TODO Auto-generated method stub
		return productPackageMapper.insert(record);
	}

	@Override
	public int insertSelective(ProductPackageKey record) {
		// TODO Auto-generated method stub
		return productPackageMapper.insertSelective(record);
	}
}
