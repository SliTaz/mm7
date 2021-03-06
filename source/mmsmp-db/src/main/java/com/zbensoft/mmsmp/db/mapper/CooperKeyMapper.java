package com.zbensoft.mmsmp.db.mapper;

import java.util.List;

import com.zbensoft.mmsmp.db.domain.CooperKey;


public interface CooperKeyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    int deleteByPrimaryKey(String keyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    int insert(CooperKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    int insertSelective(CooperKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    CooperKey selectByPrimaryKey(String keyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    int updateByPrimaryKeySelective(CooperKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cooper_key
     *
     * @mbg.generated Fri Sep 07 11:31:26 CST 2018
     */
    int updateByPrimaryKey(CooperKey record);
    List<CooperKey> selectPage(CooperKey record);
    
    int count(CooperKey record);
}