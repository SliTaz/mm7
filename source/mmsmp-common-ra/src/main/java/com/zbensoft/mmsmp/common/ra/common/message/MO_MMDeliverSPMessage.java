package com.zbensoft.mmsmp.common.ra.common.message;

import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.common.SOAPDecoder;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import com.cmcc.mm7.vasp.message.MM7RSReq;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MO_MMDeliverSPMessage extends AbstractMessage implements Serializable {
	private List bcclist;
	private List cclist;
	private MMContent Content;
	private String LinkedID;
	private String MM7Version;
	private String MMSRelayServerID;
	private String ReplyChargingID;
	private String Sender;
	private String Subject;
	private Date TimeStamp;
	private List To;
	private String TransactionID;
	private String contentType;
	private String sendurl;
	private String messageid;
	private Map<String, String> requestHeads = new HashMap();
	private byte[] contentByte;
	private String needConfirm;
	private String spid;
	private String servicesId;
	private String productId;
	private int checkType;

	public String getNeedConfirm() {
		return this.needConfirm;
	}

	public void setNeedConfirm(String needConfirm) {
		this.needConfirm = needConfirm;
	}

	public String getServicesId() {
		return this.servicesId;
	}

	public void setServicesId(String servicesId) {
		this.servicesId = servicesId;
	}

	public String getSpid() {
		return this.spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCheckType() {
		return this.checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

	public String getSendurl() {
		return this.sendurl;
	}

	public void setSendurl(String sendurl) {
		this.sendurl = sendurl;
	}

	public String getMessageid() {
		return this.messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public Map<String, String> getRequestHeads() {
		return this.requestHeads;
	}

	public void setRequestHeads(Map<String, String> requestHeads) {
		this.requestHeads = requestHeads;
	}

	public byte[] getContentByte() {
		return this.contentByte;
	}

	public void setContentByte(byte[] contentByte) {
		this.contentByte = contentByte;
	}

	public void decodeString(String paramString) throws DecodeMessageException {
	}

	public String encodeString() {
		return null;
	}

	public MessageType getMessageType() {
		return null;
	}

	public int getServiceId() {
		return 0;
	}

	public MM7DeliverReq getMM7DeliverReq() {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			baos.write(this.contentByte);
			SOAPDecoder soapDecoder = new SOAPDecoder();
			soapDecoder.setMessage(baos);
			soapDecoder.decodeMessage();
			MM7RSReq rsReq = soapDecoder.getMessage();
			MM7DeliverReq localMM7DeliverReq = (MM7DeliverReq) rsReq;
			return localMM7DeliverReq;
		} catch (Exception ex) {
			return null;
		} finally {
			try {
				baos.close();
			} catch (Exception localException3) {
				localException3.printStackTrace();
			}
		}
	}

	public String getHead(String key) {
		return (String) this.requestHeads.get(key.toLowerCase());
	}

	public List getBcclist() {
		return this.bcclist;
	}

	public void setBcclist(List bcclist) {
		this.bcclist = bcclist;
	}

	public List getCclist() {
		return this.cclist;
	}

	public void setCclist(List cclist) {
		this.cclist = cclist;
	}

	public MMContent getContent() {
		return this.Content;
	}

	public void setContent(MMContent content) {
		this.Content = content;
	}

	public String getLinkedID() {
		return this.LinkedID;
	}

	public void setLinkedID(String linkedID) {
		this.LinkedID = linkedID;
	}

	public String getMM7Version() {
		return this.MM7Version;
	}

	public void setMM7Version(String version) {
		this.MM7Version = version;
	}

	public String getMMSRelayServerID() {
		return this.MMSRelayServerID;
	}

	public void setMMSRelayServerID(String relayServerID) {
		this.MMSRelayServerID = relayServerID;
	}

	public String getReplyChargingID() {
		return this.ReplyChargingID;
	}

	public void setReplyChargingID(String replyChargingID) {
		this.ReplyChargingID = replyChargingID;
	}

	public String getSender() {
		return this.Sender;
	}

	public void setSender(String sender) {
		this.Sender = sender;
	}

	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public Date getTimeStamp() {
		return this.TimeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.TimeStamp = timeStamp;
	}

	public List getTo() {
		return this.To;
	}

	public void setTo(List to) {
		this.To = to;
	}

	public String getTransactionID() {
		return this.TransactionID;
	}

	public void setTransactionID(String transactionID) {
		this.TransactionID = transactionID;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
