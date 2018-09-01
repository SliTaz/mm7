package com.zbensoft.mmsmp.api.common.seq;

public class SeqVo {
	private long maxValue;
	private long minValue;
	private long increment; // 每次递增大小
	private int formatLen;
	public SeqVo(Long minValue, Long maxValue, Long increment, Integer formatLen) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.increment = increment;
		this.formatLen = formatLen;
	}
	
	public int getFormatLen() {
		return formatLen;
	}

	public void setFormatLen(int formatLen) {
		this.formatLen = formatLen;
	}

	public long getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(long maxValue) {
		this.maxValue = maxValue;
	}
	public long getMinValue() {
		return minValue;
	}
	public void setMinValue(long minValue) {
		this.minValue = minValue;
	}
	public long getIncrement() {
		return increment;
	}
	public void setIncrement(long increment) {
		this.increment = increment;
	}

}