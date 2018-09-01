package com.zbensoft.mmsmp.db.vo;

public class TradeAmountVo {

    public TradeAmountVo() {
    }

    public TradeAmountVo(String startTime, String endTime, int tradeType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.tradeType = tradeType;
    }

    public TradeAmountVo(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    private String startTime;
    private String endTime;
    private int tradeType;
    private String bankId;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
