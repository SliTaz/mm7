package com.zbensoft.mmsmp.common.ra.common.message;

import java.util.Date;
import java.util.StringTokenizer;

public class MT_SMMessage extends AbstractMessage {
	private static final long serialVersionUID = -1075075188946250645L;
	private String sendAddress;
	private String[] rcvAddresses;
	private String chargeParty;
	private String vaspId;
	private String vasId;
	private String serviceCode;
	private String opCode;
	private String smsText;
	private String spPassword;
	private String mtTranId;
	private boolean groupSend;
	private int serviceId;
	private String timeStamp;
	private boolean needReceipt = true;
	private String mtUrl;

	public boolean isNeedReceipt() {
		return this.needReceipt;
	}

	public void setNeedReceipt(boolean needReceipt) {
		this.needReceipt = needReceipt;
	}

	public boolean isGroupSend() {
		return this.groupSend;
	}

	public void setGroupSend(boolean groupSend) {
		this.groupSend = groupSend;
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

	public String getSpPassword() {
		return this.spPassword;
	}

	public void setSpPassword(String spPassword) {
		this.spPassword = spPassword;
	}

	public String getSmsText() {
		return this.smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
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

				if (name.equals("sendAddress")) {
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
				} else if (name.equals("smsText")) {
					if (value.equals("null"))
						setSmsText(null);
					else
						setSmsText(value);
				} else if (name.equals("linkId")) {
					if (value.equals("null"))
						setLinkId(null);
					else
						setLinkId(value);
				} else if (name.equals("mtTranId")) {
					if (value.equals("null"))
						setMtTranId(null);
					else
						setMtTranId(value);
				} else if (name.equals("serviceId"))
					if (value.equals("null"))
						setServiceId(-1);
					else
						setServiceId(Integer.parseInt(value));
			}
		} catch (Exception e) {
			throw new DecodeMessageException("failed to decode Message", e);
		}
	}

	public String encodeString() {
		if (this.mtTranId == null) {
			this.mtTranId = new Date().getTime() + "";
		}
		StringBuffer result = new StringBuffer();
		result.append("MessageType:" + MessageType.MT_SM_MESSAGE.getMessageType() + "\n");
		result.append(super.encodeExtendFields());
		result.append("sendAddress:" + this.sendAddress + "\n");
		result.append("chargeParty:" + this.chargeParty + "\n");
		result.append("smsText:" + this.smsText + "\n");
		result.append("vaspId:" + this.vaspId + "\n");
		result.append("vasId:" + this.vasId + "\n");
		result.append("serviceCode:" + this.serviceCode + "\n");
		result.append("opCode:" + this.opCode + "\n");
		result.append("mtTranId:" + this.mtTranId + "\n");
		result.append("serviceId:" + this.serviceId);
		result.append("needReceipt:  " + this.needReceipt);
		return result.toString();
	}

	public MessageType getMessageType() {
		return MessageType.MT_SM_MESSAGE;
	}

	public static void main(String[] args) throws Exception {
		MT_SMMessage message = new MT_SMMessage();
		String str = message.encodeString();
		System.out.println(str);
		message = new MT_SMMessage();
		message.decodeString(str);

		str = message.encodeString();
		System.out.println(str);
	}

	public String toString() {
		return encodeString();
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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
