package com.zbensoft.mmsmp.db.mapper;

import java.util.List;


import com.zbensoft.mmsmp.db.domain.ProvinceCity;
import com.zbensoft.mmsmp.db.domain.ProvinceWithCity;


public interface ProvinceCityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    int deleteByPrimaryKey(String provinceCityId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    int insert(ProvinceCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    int insertSelective(ProvinceCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    ProvinceCity selectByPrimaryKey(String provinceCityId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    int updateByPrimaryKeySelective(ProvinceCity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table province_city
     *
     * @mbg.generated Fri Aug 24 09:58:12 CST 2018
     */
    int updateByPrimaryKey(ProvinceCity record);
    
    List<ProvinceCity> selectPage(ProvinceCity ProvinceCity);
    
    int count(ProvinceCity ProvinceCity);
   
    List<ProvinceCity> AdvanceSelectPage(ProvinceCity ProvinceCity);
    
    int AdvanceCount(ProvinceCity ProvinceCity);
    
    int countAllProvinceAndCity(ProvinceCity provinceCity);
}