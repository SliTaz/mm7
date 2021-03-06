package com.zbensoft.mmsmp.db.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AlarmInfo {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.alarm_info_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private String alarmInfoCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.alarm_level_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private String alarmLevelCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.alarm_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String alarmTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.alarm_origin
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private String alarmOrigin;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.content
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.handle
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private String handle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.handle_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String handleTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column alarm_info.status
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	private Integer status;
	
	
	private String alarmTimeStart;
	
	private String alarmTimeEnd;
	
	private String handleTimeStart;
	
	private String handleTimeEnd;
	
	//等级名称
	private String alarmLevelName;
	
	private String alarmOriginName;
	
	//未处理数
	private String unHandledCount;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.alarm_info_code
	 * @return  the value of alarm_info.alarm_info_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getAlarmInfoCode() {
		return alarmInfoCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.alarm_info_code
	 * @param alarmInfoCode  the value for alarm_info.alarm_info_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setAlarmInfoCode(String alarmInfoCode) {
		this.alarmInfoCode = alarmInfoCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.alarm_level_code
	 * @return  the value of alarm_info.alarm_level_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getAlarmLevelCode() {
		return alarmLevelCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.alarm_level_code
	 * @param alarmLevelCode  the value for alarm_info.alarm_level_code
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setAlarmLevelCode(String alarmLevelCode) {
		this.alarmLevelCode = alarmLevelCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.alarm_time
	 * @return  the value of alarm_info.alarm_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getAlarmTime() {
		return alarmTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.alarm_time
	 * @param alarmTime  the value for alarm_info.alarm_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.alarm_origin
	 * @return  the value of alarm_info.alarm_origin
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getAlarmOrigin() {
		return alarmOrigin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.alarm_origin
	 * @param alarmOrigin  the value for alarm_info.alarm_origin
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setAlarmOrigin(String alarmOrigin) {
		this.alarmOrigin = alarmOrigin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.content
	 * @return  the value of alarm_info.content
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.content
	 * @param content  the value for alarm_info.content
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.handle
	 * @return  the value of alarm_info.handle
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getHandle() {
		return handle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.handle
	 * @param handle  the value for alarm_info.handle
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.handle_time
	 * @return  the value of alarm_info.handle_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public String getHandleTime() {
		return handleTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.handle_time
	 * @param handleTime  the value for alarm_info.handle_time
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column alarm_info.status
	 * @return  the value of alarm_info.status
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column alarm_info.status
	 * @param status  the value for alarm_info.status
	 * @mbg.generated  Mon Jun 12 11:08:25 CST 2017
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAlarmTimeStart() {
		return alarmTimeStart;
	}

	public void setAlarmTimeStart(String alarmTimeStart) {
		this.alarmTimeStart = alarmTimeStart;
	}

	public String getAlarmTimeEnd() {
		return alarmTimeEnd;
	}

	public void setAlarmTimeEnd(String alarmTimeEnd) {
		this.alarmTimeEnd = alarmTimeEnd;
	}

	public String getHandleTimeStart() {
		return handleTimeStart;
	}

	public void setHandleTimeStart(String handleTimeStart) {
		this.handleTimeStart = handleTimeStart;
	}

	public String getHandleTimeEnd() {
		return handleTimeEnd;
	}

	public void setHandleTimeEnd(String handleTimeEnd) {
		this.handleTimeEnd = handleTimeEnd;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public String getUnHandledCount() {
		return unHandledCount;
	}

	public void setUnHandledCount(String unHandledCount) {
		this.unHandledCount = unHandledCount;
	}

	public String getAlarmOriginName() {
		return alarmOriginName;
	}

	public void setAlarmOriginName(String alarmOriginName) {
		this.alarmOriginName = alarmOriginName;
	}

}