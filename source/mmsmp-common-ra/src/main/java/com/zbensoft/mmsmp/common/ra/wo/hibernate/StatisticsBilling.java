package com.zbensoft.mmsmp.common.ra.wo.hibernate;

import java.io.Serializable;
import java.util.Date;

public class StatisticsBilling implements Serializable {
	private Integer id;
	private String spno;
	private String spOrderid;
	private String wopaytime;
	private String sprequestsequenceid;
	private String requesttime;
	private String userid;
	private String uername;
	private String userphonenumber;
	private Integer fee;
	private String paytype;
	private Date createDate;

	public StatisticsBilling() {
	}

	public StatisticsBilling(Integer id) {
		this.id = id;
	}

	public StatisticsBilling(Integer id, String spno, String spOrderid, String wopaytime, String sprequestsequenceid, String requesttime, String userid, String uername, String userphonenumber, Integer fee,
			String paytype, Date createDate) {
		this.id = id;
		this.spno = spno;
		this.spOrderid = spOrderid;
		this.wopaytime = wopaytime;
		this.sprequestsequenceid = sprequestsequenceid;
		this.requesttime = requesttime;
		this.userid = userid;
		this.uername = uername;
		this.userphonenumber = userphonenumber;
		this.fee = fee;
		this.paytype = paytype;
		this.createDate = createDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpno() {
		return this.spno;
	}

	public void setSpno(String spno) {
		this.spno = spno;
	}

	public String getSpOrderid() {
		return this.spOrderid;
	}

	public void setSpOrderid(String spOrderid) {
		this.spOrderid = spOrderid;
	}

	public String getWopaytime() {
		return this.wopaytime;
	}

	public void setWopaytime(String wopaytime) {
		this.wopaytime = wopaytime;
	}

	public String getSprequestsequenceid() {
		return this.sprequestsequenceid;
	}

	public void setSprequestsequenceid(String sprequestsequenceid) {
		this.sprequestsequenceid = sprequestsequenceid;
	}

	public String getRequesttime() {
		return this.requesttime;
	}

	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUername() {
		return this.uername;
	}

	public void setUername(String uername) {
		this.uername = uername;
	}

	public String getUserphonenumber() {
		return this.userphonenumber;
	}

	public void setUserphonenumber(String userphonenumber) {
		this.userphonenumber = userphonenumber;
	}

	public Integer getFee() {
		return this.fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
