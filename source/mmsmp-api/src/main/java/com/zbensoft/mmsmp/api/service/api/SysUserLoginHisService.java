package com.zbensoft.mmsmp.api.service.api;

import java.util.List;

import com.zbensoft.mmsmp.db.domain.SysUserLoginHis;

public interface SysUserLoginHisService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    int deleteByPrimaryKey(String consumerUserLoginHisId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    int insert(SysUserLoginHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    int insertSelective(SysUserLoginHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    SysUserLoginHis selectByPrimaryKey(String consumerUserLoginHisId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    int updateByPrimaryKeySelective(SysUserLoginHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_login_his
     *
     * @mbg.generated Wed Jun 07 14:48:24 CST 2017
     */
    int updateByPrimaryKey(SysUserLoginHis record);

	List<SysUserLoginHis> selectPage(SysUserLoginHis sysUserLoginHis);

	int count(SysUserLoginHis sysUserLoginHis);

	void deleteAll();
}