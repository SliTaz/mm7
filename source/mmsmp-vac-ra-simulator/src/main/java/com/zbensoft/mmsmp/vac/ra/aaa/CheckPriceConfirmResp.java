package com.zbensoft.mmsmp.vac.ra.aaa;

import java.nio.ByteBuffer;

public class CheckPriceConfirmResp extends Header {
	private Integer Result_Code;

	public CheckPriceConfirmResp() {
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + 4);
		this.CommandId = CMDID_CheckPriceConfirmResp;
	}

	public Integer getResult_Code() {
		return this.Result_Code;
	}

	public void setResult_Code(Integer resultCode) {
		this.Result_Code = resultCode;
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue() + 1024);
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

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Result_Code=").append(this.Result_Code);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		CheckPriceConfirmResp x = new CheckPriceConfirmResp();
		x.setResult_Code(Integer.valueOf(1234123));

		System.out.println(x);

		x.unserialize(x.serialize());
		System.out.println(x);
	}

}
