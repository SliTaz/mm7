package com.zbensoft.mmsmp.api.service.api;

import java.util.List;

import com.zbensoft.mmsmp.db.domain.MobileSegment;


public interface MobileSegmentService {
	 /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    int deleteByPrimaryKey(String mobileSegmentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    int insert(MobileSegment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    int insertSelective(MobileSegment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    MobileSegment selectByPrimaryKey(String mobileSegmentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    int updateByPrimaryKeySelective(MobileSegment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mobile_segment
     *
     * @mbg.generated Tue Aug 28 10:12:24 CST 2018
     */
    int updateByPrimaryKey(MobileSegment record);
    
 List<MobileSegment> selectPage(MobileSegment record);
    
    int count(MobileSegment record);

    List<MobileSegment> getCityIdBySegment(String segment);
}
