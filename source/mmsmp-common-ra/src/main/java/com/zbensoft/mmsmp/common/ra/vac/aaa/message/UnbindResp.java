package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class UnbindResp extends Header {
	private Integer Result_Code;

	public UnbindResp() {
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + 4);
		this.CommandId = Integer.valueOf(-2147483646);
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

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Result_Code=").append(this.Result_Code);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		UnbindResp x = new UnbindResp();
		x.Result_Code = Integer.valueOf(111133311);
		System.out.println(x);
		UnbindResp o = new UnbindResp();
		o.unserialize(x.serialize());
		System.out.println(o.TotalLength);
		System.out.println(o);
	}
}
