package com.zbensoft.mmsmp.db.vo;

public class TradeInfoPayment {

    public TradeInfoPayment() {
    }

    public TradeInfoPayment(String startTime, String endTime, int tradeType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.tradeType = tradeType;
    }

    public TradeInfoPayment(String startTime, String endTime) {
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

    public long getPaymentBuyerNum() {
		return paymentBuyerNum;
	}

	public void setPaymentBuyerNum(long paymentBuyerNum) {
		this.paymentBuyerNum = paymentBuyerNum;
	}

	public long getPaymentSellerNum() {
		return paymentSellerNum;
	}

	public void setPaymentSellerNum(long paymentSellerNum) {
		this.paymentSellerNum = paymentSellerNum;
	}

	public long getRechargeNum() {
		return rechargeNum;
	}

	public void setRechargeNum(long rechargeNum) {
		this.rechargeNum = rechargeNum;
	}

	private String startTime;
    private String endTime;
    private String payUserId;
    private String recvEmployeeUserId;
    private String countSame;
    private Double amount;
    private int tradeType;
    private long paymentBuyerNum;
    private long paymentSellerNum;
    private long rechargeNum;
    private long paymentNum;
    private long chargeNum;
    private Double rechargeAmount;
    private Double paymentAmount;
    private Double chargeAmount;
	public long getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(long paymentNum) {
		this.paymentNum = paymentNum;
	}

	public long getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(long chargeNum) {
		this.chargeNum = chargeNum;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}

	public String getCountSame() {
		return countSame;
	}

	public void setCountSame(String countSame) {
		this.countSame = countSame;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRecvEmployeeUserId() {
		return recvEmployeeUserId;
	}

	public void setRecvEmployeeUserId(String recvEmployeeUserId) {
		this.recvEmployeeUserId = recvEmployeeUserId;
	}

   
}
