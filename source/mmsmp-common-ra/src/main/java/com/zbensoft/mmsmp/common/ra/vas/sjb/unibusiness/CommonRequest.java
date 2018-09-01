package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonRequest", propOrder = { "channel", "operator", "streamingNum" })
public class CommonRequest {

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
