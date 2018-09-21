package com.zbensoft.mmsmp.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zbensoft.mmsmp.api.service.api.ProductInfoService;
import com.zbensoft.mmsmp.db.domain.ProductInfo;
import com.zbensoft.mmsmp.db.domain.ProductService;
import com.zbensoft.mmsmp.db.mapper.ProductInfoMapper;
import com.zbensoft.mmsmp.db.mapper.ProductServiceMapper;

@Service
public class ProductInfoServiceImpl implements ProductInfoService{
	@Autowired
	ProductInfoMapper productInfoMapper;
	@Autowired
	ProductServiceMapper productServiceMapper;
	@Override
	public int deleteByPrimaryKey(String productInfoId) {
		// TODO Auto-generated method stub
		ProductService productService= new ProductService();
		productService.setProductInfoId(productInfoId);
		List<ProductService> list=productServiceMapper.selectPage(productService);
		for(ProductService temp:list)
		{
			productServiceMapper.deleteByPrimaryKey(temp.getProductServiceId());
		}
		return productInfoMapper.deleteByPrimaryKey(productInfoId);
	}

	@Override
	public int insert(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.insertSelective(record);
	}

	@Override
	public ProductInfo selectByPrimaryKey(String productInfoId) {
		// TODO Auto-generated method stub
		return productInfoMapper.selectByPrimaryKey(productInfoId);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ProductInfo> selectPage(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.selectPage(record);
	}

	@Override
	public int count(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.count(record);
	}

	@Override
	public ProductInfo getVasSpCpInfo(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.getVasSpCpInfo(record);
	}

	@Override
	public List<ProductInfo> getAllVasServiceRelation() {
		// TODO Auto-generated method stub
		return productInfoMapper.getAllVasServiceRelation();
	}

	@Override
	public List<ProductInfo> getSpProductIdsForSPsimulator(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.getSpProductIdsForSPsimulator(record);
	}

	@Override
	public ProductInfo getVasSpCpInfoByOrder(ProductInfo record) {
		// TODO Auto-generated method stub
		return productInfoMapper.getVasSpCpInfoByOrder(record);
	}

	@Override
	public void deleteProductByProductId(String productId) {
		 productInfoMapper.deleteProductByProductId(productId);
	}
}
