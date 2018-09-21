package com.zbensoft.mmsmp.ftp.sysc.spftp.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SpAccessReq {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.sp_info_id
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String spInfoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.sp_access_number
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String spAccessNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.sp_access_number_extend
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String spAccessNumberExtend;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.sp_city
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String spCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.is_trust
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private Integer isTrust;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.sp_order_notify_url
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String spOrderNotifyUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.order_key
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String orderKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.syn_order_func
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private Integer synOrderFunc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.eff_time
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String effTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.exp_time
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String expTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sp_access.report_message_url
     *
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    private String reportMessageUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.sp_info_id
     *
     * @return the value of sp_access.sp_info_id
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */

    private String companyCode;
    private String companyName;
    private String provinceCityName;

    public String getSpInfoId() {
        return spInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.sp_info_id
     *
     * @param spInfoId the value for sp_access.sp_info_id
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSpInfoId(String spInfoId) {
        this.spInfoId = spInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.sp_access_number
     *
     * @return the value of sp_access.sp_access_number
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getSpAccessNumber() {
        return spAccessNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.sp_access_number
     *
     * @param spAccessNumber the value for sp_access.sp_access_number
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSpAccessNumber(String spAccessNumber) {
        this.spAccessNumber = spAccessNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.sp_access_number_extend
     *
     * @return the value of sp_access.sp_access_number_extend
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getSpAccessNumberExtend() {
        return spAccessNumberExtend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.sp_access_number_extend
     *
     * @param spAccessNumberExtend the value for sp_access.sp_access_number_extend
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSpAccessNumberExtend(String spAccessNumberExtend) {
        this.spAccessNumberExtend = spAccessNumberExtend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.sp_city
     *
     * @return the value of sp_access.sp_city
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getSpCity() {
        return spCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.sp_city
     *
     * @param spCity the value for sp_access.sp_city
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSpCity(String spCity) {
        this.spCity = spCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.is_trust
     *
     * @return the value of sp_access.is_trust
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public Integer getIsTrust() {
        return isTrust;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.is_trust
     *
     * @param isTrust the value for sp_access.is_trust
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setIsTrust(Integer isTrust) {
        this.isTrust = isTrust;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.sp_order_notify_url
     *
     * @return the value of sp_access.sp_order_notify_url
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getSpOrderNotifyUrl() {
        return spOrderNotifyUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.sp_order_notify_url
     *
     * @param spOrderNotifyUrl the value for sp_access.sp_order_notify_url
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSpOrderNotifyUrl(String spOrderNotifyUrl) {
        this.spOrderNotifyUrl = spOrderNotifyUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.order_key
     *
     * @return the value of sp_access.order_key
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getOrderKey() {
        return orderKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.order_key
     *
     * @param orderKey the value for sp_access.order_key
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.syn_order_func
     *
     * @return the value of sp_access.syn_order_func
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public Integer getSynOrderFunc() {
        return synOrderFunc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.syn_order_func
     *
     * @param synOrderFunc the value for sp_access.syn_order_func
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setSynOrderFunc(Integer synOrderFunc) {
        this.synOrderFunc = synOrderFunc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.eff_time
     *
     * @return the value of sp_access.eff_time
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getEffTime() {
        return effTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.eff_time
     *
     * @param effTime the value for sp_access.eff_time
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setEffTime(String effTime) {
        this.effTime = effTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.exp_time
     *
     * @return the value of sp_access.exp_time
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getExpTime() {
        return expTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.exp_time
     *
     * @param expTime the value for sp_access.exp_time
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sp_access.report_message_url
     *
     * @return the value of sp_access.report_message_url
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public String getReportMessageUrl() {
        return reportMessageUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sp_access.report_message_url
     *
     * @param reportMessageUrl the value for sp_access.report_message_url
     * @mbg.generated Fri Aug 24 14:06:15 CST 2018
     */
    public void setReportMessageUrl(String reportMessageUrl) {
        this.reportMessageUrl = reportMessageUrl;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvinceCityName() {
        return provinceCityName;
    }

    public void setProvinceCityName(String provinceCityName) {
        this.provinceCityName = provinceCityName;
    }

}