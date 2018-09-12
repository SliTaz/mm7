package com.zbensoft.mmsmp.sp.ra.utils;

import java.nio.ByteBuffer;
import java.util.Random;

public class Header {
	public Integer Message_Length = Integer.valueOf(20);
	public Integer Command_ID = Integer.valueOf(4);
	public Integer Sequence_Number_1 = Integer.valueOf(0);
	public Integer Sequence_Number_2 = Integer.valueOf(0);
	public Integer Sequence_Number_3 = Integer.valueOf(0);

	public ByteBuffer serialize() {
		ByteBuffer bb = ByteBuffer.allocate(this.Message_Length.intValue());
		bb.putInt(this.Message_Length.intValue());
		bb.putInt(this.Command_ID.intValue());
		Random random = new Random(System.currentTimeMillis());
		bb.putInt(random.nextInt());
		bb.putInt(random.nextInt());
		bb.putInt(random.nextInt());
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		this.Message_Length = Integer.valueOf(content.getInt());
		this.Command_ID = Integer.valueOf(content.getInt());
		this.Sequence_Number_1 = Integer.valueOf(content.getInt());
		this.Sequence_Number_2 = Integer.valueOf(content.getInt());
		this.Sequence_Number_3 = Integer.valueOf(content.getInt());
		return content;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Message_Length=").append(this.Message_Length);
		sb.append(" Command_ID=").append(this.Command_ID);
		sb.append(" Sequence_Number_1=").append(this.Sequence_Number_1);
		sb.append(" Sequence_Number_2=").append(this.Sequence_Number_2);
		sb.append(" Sequence_Number_3=").append(this.Sequence_Number_3);
		sb.append(" ]");
		return sb.toString();
	}
}
