package com.zbensoft.mmsmp.common.ra.vas.sjb.unibusiness.define;

public class CommonRequest {
	private String streamingNum;
	private String channel;
	private String operator;

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStreamingNum() {
		return this.streamingNum;
	}

	public void setStreamingNum(String streamingNum) {
		this.streamingNum = streamingNum;
	}
}
