package com.zbensoft.mmsmp.sp.ra.utils;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class Deliver extends Header {
	public String UserNumber = "";
	public String SPNumber = "";
	public byte TP_pid;
	public byte TP_udhi;
	public byte MessageCoding = 15;

	public String MessageContent = "";
	public String Reserve = "";
	public int ContentLength;

	public ByteBuffer serialize() {
		int len = 69 + this.ContentLength + 8;
		this.Message_Length = Integer.valueOf(len);
		ByteBuffer bb = ByteBuffer.allocate(len);
		bb.putInt(this.Message_Length.intValue());
		bb.putInt(4);
		bb.putInt(this.Sequence_Number_1.intValue());
		bb.putInt(this.Sequence_Number_2.intValue());
		bb.putInt(this.Sequence_Number_3.intValue());
		Utils.putString(bb, this.UserNumber, 21);
		Utils.putString(bb, this.SPNumber, 21);
		bb.put(this.TP_pid);
		bb.put(this.TP_udhi);
		bb.put(this.MessageCoding);
		bb.putInt(this.ContentLength);
		Utils.putString(bb, this.MessageContent, getEncoding(this.MessageCoding));
		Utils.putString(bb, this.Reserve, 8);
		bb.flip();
		
		System.out.println("Deliver serialize is success");
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer bb) {
		this.Message_Length = Integer.valueOf(bb.getInt());
		this.Command_ID = Integer.valueOf(bb.getInt());
		this.Sequence_Number_1 = Integer.valueOf(bb.getInt());
		this.Sequence_Number_2 = Integer.valueOf(bb.getInt());
		this.Sequence_Number_3 = Integer.valueOf(bb.getInt());
		try {
			this.UserNumber = Utils.getString(bb, 21);
			this.SPNumber = Utils.getString(bb, 21);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TP_pid = bb.get();
		this.TP_udhi = bb.get();
		this.MessageCoding = bb.get();
		this.ContentLength = bb.getInt();
		this.MessageContent = Utils.getString(bb, this.ContentLength, getEncoding(this.MessageCoding));
		try {
			this.Reserve = Utils.getString(bb, 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bb;
	}

	private String getEncoding(int MessageEncoding) {
		if (MessageEncoding == 8)
			return "UnicodeBigUnmarked";
		if (MessageEncoding == 15)
			return "GBK";
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" UserNumber=").append(this.UserNumber);
		sb.append(" SPNumber=").append(this.SPNumber);
		sb.append(" TP_pid=").append(this.TP_pid);
		sb.append(" TP_udhi=").append(this.TP_udhi);
		sb.append(" MessageCoding=").append(this.MessageCoding);
		sb.append(" MessageContent=").append(this.MessageContent);
		sb.append(" ContentLength=").append(this.ContentLength);
		sb.append(" Reserve=").append(this.Reserve);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Deliver d = new Deliver();
		d.UserNumber = "23456789456";
		d.SPNumber = "234454";
		d.TP_pid = 1;
		d.TP_udhi = 2;
		d.MessageCoding = 2;

		d.MessageContent = "ssdrftytr434r5t6ytrew";
		d.Reserve = "xxx";

		new FileOutputStream("/tmp/x").write(d.serialize().array());
	}
}
