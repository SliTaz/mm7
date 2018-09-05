package com.zbensoft.mmsmp.common.ra.common.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

public abstract class AbstractMessage implements IMessage {
	private static final long serialVersionUID = 1L;
	private Map<ExtendMessageField, String> extendFields = new HashMap();
	protected int source = 4;
	private Integer contentid;
	private int priority;
	private int sourceType;
	private boolean resend = false;
	private int operatorsType;
	private String provinceCode;
	private String linkId;
	private String globalMessageid;
	private String globalReportUrl;
	private long globalMessageTime;
	private long globalCreateTime;
	private int globalStep;
	private String chargetNumber;

	public String getChargetNumber() {
		return this.chargetNumber;
	}

	public void setChargetNumber(String chargetNumber) {
		this.chargetNumber = chargetNumber;
	}

	public static String generateUUID(String source) {
		String id = source == null ? "" : source.trim();

		UUID uuid = UUID.randomUUID();
		id = id + uuid.toString().replaceAll("-", "");

		return id.toUpperCase();
	}

	public String getGlobalReportUrl() {
		return this.globalReportUrl;
	}

	public void setGlobalReportUrl(String globalReportUrl) {
		this.globalReportUrl = globalReportUrl;
	}

	public int getGlobalStep() {
		return this.globalStep;
	}

	public void setGlobalStep(int globalStep) {
		this.globalStep = globalStep;
	}

	public long getGlobalCreateTime() {
		return this.globalCreateTime;
	}

	public void setGlobalCreateTime(long globalCreateTime) {
		this.globalCreateTime = globalCreateTime;
	}

	public String getGlobalMessageid() {
		return this.globalMessageid;
	}

	public void setGlobalMessageid(String globalMessageid) {
		this.globalMessageid = globalMessageid;
		setGlobalCreateTime(System.currentTimeMillis());
	}

	public long getGlobalMessageTime() {
		return this.globalMessageTime;
	}

	public void setGlobalMessageTime(long globalMessageTime) {
		this.globalMessageTime = globalMessageTime;
	}

	public String getLinkId() {
		return this.linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid = contentid;
	}

	public int getSource() {
		return this.source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public void setExtendFieldValue(ExtendMessageField field, String value) {
		this.extendFields.put(field, value);
	}

	public String getExtendFieldValue(ExtendMessageField id) {
		return (String) this.extendFields.get(id);
	}

	public Map<ExtendMessageField, String> getExtendFields() {
		return this.extendFields;
	}

	public void copyExtendFields(AbstractMessage source) {
		this.extendFields.putAll(source.getExtendFields());
	}

	public String decodeExtendFields(String message) throws Exception {
		int pos = message.indexOf("\n\n");
		this.extendFields.clear();

		if (pos > 0) {
			String customMessage = message.substring(0, pos);
			message = message.substring(pos + 2);
			String token = null;
			String name = null;
			String value = null;
			ExtendMessageField field = null;

			for (StringTokenizer tokens = new StringTokenizer(customMessage, "\n"); tokens.hasMoreTokens();) {
				token = tokens.nextToken();
				pos = token.indexOf(':');

				if (pos < 0)
					continue;
				name = token.substring(0, pos);
				value = token.substring(pos + 1);

				field = ExtendMessageField.parseString(name);

				if (field == null) {
					continue;
				}
				this.extendFields.put(field, value);
			}

			return message;
		}

		return message;
	}

	public StringBuffer encodeExtendFields() {
		StringBuffer result = new StringBuffer();
		ExtendMessageField field = null;

		for (Iterator iter = this.extendFields.keySet().iterator(); iter.hasNext();) {
			field = (ExtendMessageField) iter.next();
			result.append(field.toString() + ":" + (String) this.extendFields.get(field) + "\n");
		}

		if (result.length() != 0) {
			result.append("\n");
		}
		return result;
	}

	public int getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public boolean isResend() {
		return this.resend;
	}

	public void setResend(boolean resend) {
		this.resend = resend;
	}

	public int getOperatorsType() {
		return this.operatorsType;
	}

	public void setOperatorsType(int operatorsType) {
		this.operatorsType = operatorsType;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(" ");
		sb.append("globalMessageid").append("=").append(globalMessageid).append(",");
		sb.append("source").append("=").append(source).append(",");
		sb.append("contentid").append("=").append(contentid).append(",");
		sb.append("priority").append("=").append(priority).append(",");
		sb.append("sourceType").append("=").append(sourceType).append(",");
		sb.append("resend").append("=").append(resend).append(",");
		sb.append("operatorsType").append("=").append(operatorsType).append(",");
		sb.append("provinceCode").append("=").append(provinceCode).append(",");
		sb.append("linkId").append("=").append(linkId).append(",");
		sb.append("globalReportUrl").append("=").append(globalReportUrl).append(",");
		sb.append("globalMessageTime").append("=").append(globalMessageTime).append(",");
		sb.append("globalCreateTime").append("=").append(globalCreateTime).append(",");
		sb.append("globalStep").append("=").append(globalStep).append(",");
		sb.append("chargetNumber").append("=").append(chargetNumber).append(",");
		if (extendFields != null && extendFields.keySet().size() > 0) {
			sb.append("extendFields").append("=").append("[");
			for (Iterator keys = extendFields.keySet().iterator(); keys.hasNext();) {
				ExtendMessageField key = (ExtendMessageField) keys.next();
				sb.append("key").append("=").append(key).append(",");
				sb.append("value").append("=").append(extendFields.get(key)).append(",");

			}
			sb.append("]");
		}
		return sb.toString();
	}
}
