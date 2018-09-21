package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ProductServiceService;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.mapper.ProductServiceMapper;

@Service
public class ProductServiceServiceImpl implements ProductServiceService{
	@Autowired
	ProductServiceMapper productServiceMapper;
	@Override
	public int deleteByPrimaryKey(String productServiceId) {
		// TODO Auto-generated method stub
		return productServiceMapper.deleteByPrimaryKey(productServiceId);
	}

	@Override
	public int insert(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.insert(record);
	}

	@Override
	public int insertSelective(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.insertSelective(record);
	}

	@Override
	public ProductService selectByPrimaryKey(String productServiceId) {
		// TODO Auto-generated method stub
		return productServiceMapper.selectByPrimaryKey(productServiceId);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProductService> selectPage(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.selectPage(record);
	}

	@Override
	public int count(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.count(record);
	}

	@Override
	public ProductService getProductServiceByOnDemandAccess(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.getProductServiceByOnDemandAccess(record);
	}

	@Override
	public ProductService getServiceIDbyProductid(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.getServiceIDbyProductid(record);
	}
	
	@Override
	public List<ProductService> getProductInfo(ProductService productInfo) {
		// TODO Auto-generated method stub
		return productServiceMapper.getProductInfo(productInfo);
	}

	@Override
	public List<ProductService> getSpProductId(String cpAccessId) {
		// TODO Auto-generated method stub
		return productServiceMapper.getSpProductId(cpAccessId);
	}

	@Override
	public ProductService getVasServiceRelation(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.getVasServiceRelation(record);
	}

	@Override
	public int queryContentCount(ProductService record) {
		// TODO Auto-generated method stub
		return productServiceMapper.queryContentCount(record);
	}

	@Override
	public void updateByProductId(ProductService productService) {
		 productServiceMapper.updateByProductId(productService);
	}


}
