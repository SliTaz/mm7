package com.zbensoft.mmsmp.db.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SpInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.sp_info_id
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String spInfoId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.company_code
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String companyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.company_name
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String companyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.business_tel
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String businessTel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.contact_person
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String contactPerson;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.fax_no
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String faxNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.email_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String emailAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.web_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String webAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.office_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String officeAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.create_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.province
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.status
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.update_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.remark
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.max_con
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private Integer maxCon;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.max_flow
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private Integer maxFlow;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_info.serlf_sp
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    private Integer serlfSp;
    
    private String spAccessNumber;
    private String spAccessNumberExtend;
    private String provinceCityName;
    private SpAccess spAccess;
    private String createTimeStart;
    private String createTimeEnd;
	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.sp_info_id
     *
     * @return the value of sp_info.sp_info_id
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getSpInfoId() {
        return spInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.sp_info_id
     *
     * @param spInfoId the value for sp_info.sp_info_id
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setSpInfoId(String spInfoId) {
        this.spInfoId = spInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.company_code
     *
     * @return the value of sp_info.company_code
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.company_code
     *
     * @param companyCode the value for sp_info.company_code
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.company_name
     *
     * @return the value of sp_info.company_name
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.company_name
     *
     * @param companyName the value for sp_info.company_name
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.business_tel
     *
     * @return the value of sp_info.business_tel
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getBusinessTel() {
        return businessTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.business_tel
     *
     * @param businessTel the value for sp_info.business_tel
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setBusinessTel(String businessTel) {
        this.businessTel = businessTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.contact_person
     *
     * @return the value of sp_info.contact_person
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.contact_person
     *
     * @param contactPerson the value for sp_info.contact_person
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.fax_no
     *
     * @return the value of sp_info.fax_no
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getFaxNo() {
        return faxNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.fax_no
     *
     * @param faxNo the value for sp_info.fax_no
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.email_addr
     *
     * @return the value of sp_info.email_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.email_addr
     *
     * @param emailAddr the value for sp_info.email_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.web_addr
     *
     * @return the value of sp_info.web_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getWebAddr() {
        return webAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.web_addr
     *
     * @param webAddr the value for sp_info.web_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setWebAddr(String webAddr) {
        this.webAddr = webAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.office_addr
     *
     * @return the value of sp_info.office_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getOfficeAddr() {
        return officeAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.office_addr
     *
     * @param officeAddr the value for sp_info.office_addr
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.create_time
     *
     * @return the value of sp_info.create_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.create_time
     *
     * @param createTime the value for sp_info.create_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.province
     *
     * @return the value of sp_info.province
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.province
     *
     * @param province the value for sp_info.province
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.status
     *
     * @return the value of sp_info.status
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.status
     *
     * @param status the value for sp_info.status
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.update_time
     *
     * @return the value of sp_info.update_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.update_time
     *
     * @param updateTime the value for sp_info.update_time
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.remark
     *
     * @return the value of sp_info.remark
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.remark
     *
     * @param remark the value for sp_info.remark
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.max_con
     *
     * @return the value of sp_info.max_con
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Integer getMaxCon() {
        return maxCon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.max_con
     *
     * @param maxCon the value for sp_info.max_con
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setMaxCon(Integer maxCon) {
        this.maxCon = maxCon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.max_flow
     *
     * @return the value of sp_info.max_flow
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Integer getMaxFlow() {
        return maxFlow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.max_flow
     *
     * @param maxFlow the value for sp_info.max_flow
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setMaxFlow(Integer maxFlow) {
        this.maxFlow = maxFlow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_info.serlf_sp
     *
     * @return the value of sp_info.serlf_sp
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public Integer getSerlfSp() {
        return serlfSp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_info.serlf_sp
     *
     * @param serlfSp the value for sp_info.serlf_sp
     *
     * @mbg.generated Fri Aug 24 09:35:46 CST 2018
     */
    public void setSerlfSp(Integer serlfSp) {
        this.serlfSp = serlfSp;
    }

	public String getSpAccessNumber() {
		return spAccessNumber;
	}

	public void setSpAccessNumber(String spAccessNumber) {
		this.spAccessNumber = spAccessNumber;
	}

	public String getSpAccessNumberExtend() {
		return spAccessNumberExtend;
	}

	public void setSpAccessNumberExtend(String spAccessNumberExtend) {
		this.spAccessNumberExtend = spAccessNumberExtend;
	}

	public String getProvinceCityName() {
		return provinceCityName;
	}

	public void setProvinceCityName(String provinceCityName) {
		this.provinceCityName = provinceCityName;
	}

	public SpAccess getSpAccess() {
		return spAccess;
	}

	public void setSpAccess(SpAccess spAccess) {
		this.spAccess = spAccess;
	}
    
}