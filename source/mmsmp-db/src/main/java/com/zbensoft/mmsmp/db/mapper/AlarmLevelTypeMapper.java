package com.zbensoft.mmsmp.db.mapper;

import java.util.List;

import com.zbensoft.mmsmp.db.domain.AlarmLevelType;

public interface AlarmLevelTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_level_type
     *
     * @mbg.generated Fri Jun 09 15:47:28 CST 2017
     */
    int deleteByPrimaryKey(AlarmLevelType key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_level_type
     *
     * @mbg.generated Fri Jun 09 15:47:28 CST 2017
     */
    int insert(AlarmLevelType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_level_type
     *
     * @mbg.generated Fri Jun 09 15:47:28 CST 2017
     */
    int insertSelective(AlarmLevelType record);

	int updateByPrimaryKey(AlarmLevelType record);

	List<AlarmLevelType> selectPage(AlarmLevelType record);

	int deleteAll();

	int count(AlarmLevelType alarmLevelType);

	AlarmLevelType selectByPrimaryKey(AlarmLevelType alarmLevelType);

	int updateByPrimaryKeySelective(AlarmLevelType currentAlarmLevelType);

	List<AlarmLevelType> selectByAlarmLevelId(String alarmLevelId);

	List<AlarmLevelType> selectAll();
}