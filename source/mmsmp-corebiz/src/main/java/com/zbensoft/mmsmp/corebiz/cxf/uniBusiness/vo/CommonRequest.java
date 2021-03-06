package com.zbensoft.mmsmp.corebiz.cxf.uniBusiness.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonRequest", propOrder = { "channel", "operator", "streamingNum" })
public class CommonRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 534562514447047162L;

	@XmlElement(required = true, nillable = true)
	protected String channel;

	@XmlElement(required = true, nillable = true)
	protected String operator;

	@XmlElement(required = true, nillable = true)
	protected String streamingNum;

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String value) {
		this.channel = value;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String value) {
		this.operator = value;
	}

	public String getStreamingNum() {
		return this.streamingNum;
	}

	public void setStreamingNum(String value) {
		this.streamingNum = value;
	}
}
