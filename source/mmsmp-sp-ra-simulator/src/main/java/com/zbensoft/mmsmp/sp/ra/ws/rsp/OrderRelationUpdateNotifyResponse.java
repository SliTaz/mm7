package com.zbensoft.mmsmp.sp.ra.ws.rsp;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class OrderRelationUpdateNotifyResponse implements Serializable {
	private String recordSequenceId;
	private int resultCode;
	private Object __equalsCalc = null;

	private boolean __hashCodeCalc = false;

	private static TypeDesc typeDesc = new TypeDesc(OrderRelationUpdateNotifyResponse.class, true);

	static {
		typeDesc.setXmlType(
				new QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("recordSequenceId");
		elemField.setXmlName(new QName("", "recordSequenceId"));
		elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resultCode");
		elemField.setXmlName(new QName("", "resultCode"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	public OrderRelationUpdateNotifyResponse() {
	}

	public OrderRelationUpdateNotifyResponse(String recordSequenceId, int resultCode) {
		this.recordSequenceId = recordSequenceId;
		this.resultCode = resultCode;
	}

	public String getRecordSequenceId() {
		return this.recordSequenceId;
	}

	public void setRecordSequenceId(String recordSequenceId) {
		this.recordSequenceId = recordSequenceId;
	}

	public int getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof OrderRelationUpdateNotifyResponse))
			return false;
		OrderRelationUpdateNotifyResponse other = (OrderRelationUpdateNotifyResponse) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (this.__equalsCalc != null) {
			return this.__equalsCalc == obj;
		}
		this.__equalsCalc = obj;

		boolean _equals = ((this.recordSequenceId == null) && (other.getRecordSequenceId() == null))
				|| ((this.recordSequenceId != null) && (this.recordSequenceId.equals(other.getRecordSequenceId()))
						&& (this.resultCode == other.getResultCode()));
		this.__equalsCalc = null;
		return _equals;
	}

	public synchronized int hashCode() {
		if (this.__hashCodeCalc) {
			return 0;
		}
		this.__hashCodeCalc = true;
		int _hashCode = 1;
		if (getRecordSequenceId() != null) {
			_hashCode += getRecordSequenceId().hashCode();
		}
		_hashCode += getResultCode();
		this.__hashCodeCalc = false;
		return _hashCode;
	}

	public static TypeDesc getTypeDesc() {
		return typeDesc;
	}

	public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
		return new BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
		return new BeanDeserializer(_javaType, _xmlType, typeDesc);
	}
}
