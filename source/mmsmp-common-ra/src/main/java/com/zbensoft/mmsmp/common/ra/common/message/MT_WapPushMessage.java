package com.zbensoft.mmsmp.common.ra.common.message;

import java.io.PrintStream;
import java.util.Date;
import java.util.StringTokenizer;

public class MT_WapPushMessage extends AbstractMessage {
	private static final long serialVersionUID = 7336347501534825303L;
	private String sendAddress;
	private String[] rcvAddresses;
	private String chargeParty;
	private String vaspId;
	private String vasId;
	private String serviceCode;
	private String opCode;
	private String pushText;
	private String pushUrl;
	private String mtTranId;
	private boolean groupSend;
	private String timeStamp;
	private String mtUrl;
	private int serviceId;
	private boolean needReceipt = true;

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getPushUrl() {
		return this.pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getPushText() {
		return this.pushText;
	}

	public void setPushText(String pushText) {
		this.pushText = pushText;
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
				} else if (name.equals("pushUrl")) {
					if (value.equals("null"))
						setPushText(null);
					else
						setPushUrl(value);
				} else if (name.equals("pushText")) {
					if (value.equals("null"))
						setPushText(null);
					else
						setPushText(value);
				} else {
					if (!name.equals("mtTranId"))
						continue;
					if (value.equals("null"))
						setMtTranId(null);
					else
						setMtTranId(value);
				}
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
		result.append("MessageType:" + MessageType.MT_WAPPUSH_MESSAGE.getMessageType() + "\n");
		result.append(super.encodeExtendFields());

		result.append("sendAddress:" + this.sendAddress + "\n");
		result.append("chargeParty:" + this.chargeParty + "\n");
		result.append("pushText:" + this.pushText + "\n");
		result.append("vaspId:" + this.vaspId + "\n");
		result.append("vasId:" + this.vasId + "\n");
		result.append("serviceCode:" + this.serviceCode + "\n");
		result.append("opCode:" + this.opCode + "\n");
		result.append("pushUrl:" + this.pushUrl + "\n");
		result.append("mtTranId:" + this.mtTranId);
		return result.toString();
	}

	public MessageType getMessageType() {
		return MessageType.MT_WAPPUSH_MESSAGE;
	}

	public static void main(String[] args) throws Exception {
		MT_WapPushMessage message = new MT_WapPushMessage();
		String str = message.encodeString();
		System.out.println(str);
		message = new MT_WapPushMessage();
		message.decodeString(str);

		str = message.encodeString();
		System.out.println(str);
	}

	public String toString() {
		return encodeString();
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

	public boolean isNeedReceipt() {
		return this.needReceipt;
	}

	public void setNeedReceipt(boolean needReceipt) {
		this.needReceipt = needReceipt;
	}
}
