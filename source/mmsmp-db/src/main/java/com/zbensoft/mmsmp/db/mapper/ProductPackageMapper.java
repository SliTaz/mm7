package com.zbensoft.mmsmp.db.mapper;

import com.zbensoft.mmsmp.db.domain.ProductPackageKey;

public interface ProductPackageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_package
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    int deleteByPrimaryKey(ProductPackageKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_package
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    int insert(ProductPackageKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_package
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    int insertSelective(ProductPackageKey record);
}