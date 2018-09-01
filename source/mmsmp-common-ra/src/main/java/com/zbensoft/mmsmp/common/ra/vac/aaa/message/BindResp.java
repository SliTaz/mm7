package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class BindResp extends Header {
	public static final Integer SUCCESS = Integer.valueOf(0);
	public static final Integer ACCOUNT_ERR = Integer.valueOf(1);
	public static final Integer PASSWD_ERR = Integer.valueOf(2);
	public static final Integer DEV_TYPE_ERR = Integer.valueOf(3);
	public static final Integer DEV_ID_ERR = Integer.valueOf(4);
	public static final Integer DST_DEV_TYPE_ERR = Integer.valueOf(5);
	public static final Integer DST_DEV_ID_ERR = Integer.valueOf(6);
	public static final Integer DUP_CONN_ERR = Integer.valueOf(7);
	private Integer Result_Code;

	public BindResp() {
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + 4);
		this.CommandId = Integer.valueOf(-2147483647);
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue());
		bb.put(super.serialize());
		bb.putInt(this.Result_Code.intValue());
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		ByteBuffer bb = super.unserialize(content);
		this.Result_Code = Integer.valueOf(bb.getInt());
		return bb;
	}

	public Integer getResult_Code() {
		return this.Result_Code;
	}

	public void setResult_Code(Integer resultCode) {
		this.Result_Code = resultCode;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Result_Code=").append(this.Result_Code);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		BindResp x = new BindResp();
		x.setResult_Code(Integer.valueOf(0));
		System.out.println(x);
		x.unserialize(x.serialize());
		System.out.println(x);
	}
}
