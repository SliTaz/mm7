package com.zbensoft.mmsmp.vac.ra.aaa;

import java.nio.ByteBuffer;

import com.zbensoft.mmsmp.vac.ra.util.Utils;

public class TLV implements Serialize {
	private Short Tag;
	private Short Length;
	private String Value;

	public TLV() {
	}

	public Integer size() {
		return Integer.valueOf(this.Length.shortValue() + 4);
	}

	public TLV(Short tag, Short length, String value) {
		this.Tag = tag;
		this.Length = length;
		this.Value = value;
	}

	public Short getTag() {
		return this.Tag;
	}

	public void setTag(Short tag) {
		this.Tag = tag;
	}

	public Short getLength() {
		return this.Length;
	}

	public void setLength(Short length) {
		this.Length = length;
	}

	public String getValue() {
		return this.Value;
	}

	public void setValue(String value) {
		this.Value = value;
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(4 + this.Length.shortValue());
		bb.putShort(this.Tag.shortValue());
		bb.putShort(this.Length.shortValue());
		Utils.putString(bb, this.Value, this.Length.shortValue());
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer bb) throws Exception {
		this.Tag = Short.valueOf(bb.getShort());
		this.Length = Short.valueOf(bb.getShort());
		this.Value = Utils.getString(bb, this.Length.shortValue());
		return bb;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Tag=").append(this.Tag);
		sb.append(" Length=").append(this.Length);
		sb.append(" Value=").append(this.Value);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		TLV tlv = new TLV();
		tlv.Tag = Common.TAG.LinkID;
		tlv.Length = 30;
		tlv.Value = "qwertyuiop1234567890asdfghjklxcvbnm,ertyui45678i9opfghyjiol";
		System.out.println(tlv);
		byte[] x = tlv.serialize().array();
		System.out.println(x.length);
		tlv.unserialize(tlv.serialize());
		System.out.println(tlv);
	}

}
