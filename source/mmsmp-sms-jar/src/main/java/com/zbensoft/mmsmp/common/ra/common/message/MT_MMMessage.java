package com.zbensoft.mmsmp.common.ra.common.message;

import java.util.Date;
import java.util.StringTokenizer;

public class MT_MMMessage extends AbstractMessage {
	private static final long serialVersionUID = -4320322700818136659L;
	private String sendAddress;
	private String[] rcvAddresses;
	private String chargeParty;
	private String vaspId;
	private String vasId;
	private String serviceCode;
	private String opCode;
	private boolean adaptFlag = false;
	private String mmFile = null;
	private String subject;
	private String mtTranId;
	private String mtUrl;
	private boolean groupSend = false;
	private int serviceId;
	private int sendTimes;
	private String timeStamp;
	private String sendType;
	private boolean needReceipt = true;

	public boolean isNeedReceipt() {
		return this.needReceipt;
	}

	public void setNeedReceipt(boolean needReceipt) {
		this.needReceipt = needReceipt;
	}

	public String getSendType() {
		return this.sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getSendTimes() {
		return this.sendTimes;
	}

	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public String getMtTranId() {
		return this.mtTranId;
	}

	public void setMtTranId(String mtTranId) {
		this.mtTranId = mtTranId;
	}

	public String getMmFile() {
		return this.mmFile;
	}

	public void setMmFile(String mmFile) {
		this.mmFile = mmFile;
	}

	public boolean isAdaptFlag() {
		return this.adaptFlag;
	}

	public void setAdaptFlag(boolean adaptFlag) {
		this.adaptFlag = adaptFlag;
	}

	public String getChargeParty() {
		return this.chargeParty;
	}

	public void setChargeParty(String chargeParty) {
		this.chargeParty = chargeParty;
	}

	public String getOpCode() {
		return this.opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getSendAddress() {
		return this.sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getVasId() {
		return this.vasId;
	}

	public void setVasId(String vasId) {
		this.vasId = vasId;
	}

	public String getVaspId() {
		return this.vaspId;
	}

	public void setVaspId(String vaspId) {
		this.vaspId = vaspId;
	}

	public void decodeString(String message) throws DecodeMessageException {
		try {
			message = super.decodeExtendFields(message);
		} catch (Exception e) {
			throw new DecodeMessageException("failed to decode Message", e);
		}

		String token = null;
		int pos = 0;
		String name = null;
		String value = null;

		for (StringTokenizer tokens = new StringTokenizer(message, "\n"); tokens.hasMoreTokens();) {
			token = tokens.nextToken();

			if (token.startsWith("MessageType")) {
				continue;
			}
			pos = token.indexOf(':');
			name = token.substring(0, pos);
			value = token.substring(pos + 1);
			if (name.equals("Subject")) {
				if (value.equals("null"))
					setSubject("null");
				else
					setSubject(value);
			} else if (name.equals("sendAddress")) {
				if (value.equals("null"))
					setSendAddress(null);
				else
					setSendAddress(value);
			} else if (name.equals("vaspId")) {
				if (value.equals("null"))
					setVaspId(null);
				else
					setVaspId(value);
			} else if (name.equals("vasId")) {
				if (value.equals("null"))
					setVasId(null);
				else
					setVasId(value);
			} else if (name.equals("serviceCode")) {
				if (value.equals("null"))
					setServiceCode(null);
				else
					setServiceCode(value);
			} else if (name.equals("opCode")) {
				if (value.equals("null"))
					setOpCode(null);
				else
					setOpCode(value);
			} else if (name.equals("chargeParty")) {
				if (value.equals("null"))
					setChargeParty(null);
				else
					setChargeParty(value);
			} else if (name.equals("mmFile")) {
				if (value.equals("null"))
					setMmFile(null);
				else
					setMmFile(value);
			} else if (name.equals("adaptFlag")) {
				if ((value.equals("null")) || (value.equals("false")))
					setAdaptFlag(false);
				else
					setAdaptFlag(true);
			} else if (name.equals("mtTranId")) {
				if (value.equals("null"))
					setMtTranId(null);
				else
					setMtTranId(value);
			} else if (name.equals("serviceId")) {
				if (value.equals("null"))
					setServiceId(-1);
				else
					setServiceId(Integer.parseInt(value));
			} else if (name.equals("sendTimes"))
				if (value.equals("null"))
					setSendTimes(0);
				else
					setSendTimes(Integer.parseInt(value));
		}
	}

	public String encodeString() {
		if (this.mtTranId == null)
			this.mtTranId = new Date().getTime() + "";
		StringBuffer result = new StringBuffer();

		result.append("MessageType:" + MessageType.MT_MM_MESSAGE.getMessageType() + "\n");
		result.append(super.encodeExtendFields());
		result.append("Subject:" + this.subject + "\n");
		result.append("sendAddress:" + this.sendAddress + "\n");
		result.append("chargeParty:" + this.chargeParty + "\n");
		result.append("mmFile:" + this.mmFile + "\n");
		result.append("vaspId:" + this.vaspId + "\n");
		result.append("vasId:" + this.vasId + "\n");
		result.append("serviceCode:" + this.serviceCode + "\n");
		result.append("opCode:" + this.opCode + "\n");
		result.append("adaptFlag:" + (this.adaptFlag ? "true" : "false") + "\n");
		result.append("mtTranId:" + this.mtTranId + "\n");
		result.append("serviceId:" + this.serviceId + "\n");
		result.append("sendTimes:" + this.sendTimes);

		return result.toString();
	}

	public MessageType getMessageType() {
		return MessageType.MT_MM_MESSAGE;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String toString() {
		return encodeString();
	}

	public boolean isGroupSend() {
		return this.groupSend;
	}

	public void setGroupSend(boolean groupSend) {
		this.groupSend = groupSend;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String[] getRcvAddresses() {
		return this.rcvAddresses;
	}

	public void setRcvAddresses(String[] rcvAddresses) {
		this.rcvAddresses = rcvAddresses;
	}

	public String getMtUrl() {
		return this.mtUrl;
	}

	public void setMtUrl(String mtUrl) {
		this.mtUrl = mtUrl;
	}
}
