package com.zbensoft.mmsmp.api.service.api;

import java.util.List;

import com.zbensoft.mmsmp.db.domain.TestTerminal;

public interface TestTerminalService {
	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    int deleteByPrimaryKey(String testTerminalId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    int insert(TestTerminal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    int insertSelective(TestTerminal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    TestTerminal selectByPrimaryKey(String testTerminalId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    int updateByPrimaryKeySelective(TestTerminal record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_terminal
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    int updateByPrimaryKey(TestTerminal record);
    
    List<TestTerminal> selectPage(TestTerminal record);
    
    int count(TestTerminal record);
}
