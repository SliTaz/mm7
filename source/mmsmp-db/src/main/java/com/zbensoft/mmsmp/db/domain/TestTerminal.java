package com.zbensoft.mmsmp.db.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TestTerminal {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.test_terminal_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String testTerminalId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.msisdn
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String msisdn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.cp_info_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String cpInfoId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.create_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.create_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.audit_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String auditUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.audit_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.status
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test_terminal.remark
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    private String remark;
    
    private String productInfoId;
    
    private String proudctName;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.test_terminal_id
     *
     * @return the value of test_terminal.test_terminal_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getTestTerminalId() {
        return testTerminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.test_terminal_id
     *
     * @param testTerminalId the value for test_terminal.test_terminal_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setTestTerminalId(String testTerminalId) {
        this.testTerminalId = testTerminalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.msisdn
     *
     * @return the value of test_terminal.msisdn
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.msisdn
     *
     * @param msisdn the value for test_terminal.msisdn
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.cp_info_id
     *
     * @return the value of test_terminal.cp_info_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getCpInfoId() {
        return cpInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.cp_info_id
     *
     * @param cpInfoId the value for test_terminal.cp_info_id
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setCpInfoId(String cpInfoId) {
        this.cpInfoId = cpInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.create_user
     *
     * @return the value of test_terminal.create_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.create_user
     *
     * @param createUser the value for test_terminal.create_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.create_time
     *
     * @return the value of test_terminal.create_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.create_time
     *
     * @param createTime the value for test_terminal.create_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.audit_user
     *
     * @return the value of test_terminal.audit_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.audit_user
     *
     * @param auditUser the value for test_terminal.audit_user
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.audit_time
     *
     * @return the value of test_terminal.audit_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.audit_time
     *
     * @param auditTime the value for test_terminal.audit_time
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.status
     *
     * @return the value of test_terminal.status
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.status
     *
     * @param status the value for test_terminal.status
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test_terminal.remark
     *
     * @return the value of test_terminal.remark
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test_terminal.remark
     *
     * @param remark the value for test_terminal.remark
     *
     * @mbg.generated Sat Sep 15 10:04:48 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getProductInfoId() {
		return productInfoId;
	}

	public void setProductInfoId(String productInfoId) {
		this.productInfoId = productInfoId;
	}

	public String getProudctName() {
		return proudctName;
	}

	public void setProudctName(String proudctName) {
		this.proudctName = proudctName;
	}
}