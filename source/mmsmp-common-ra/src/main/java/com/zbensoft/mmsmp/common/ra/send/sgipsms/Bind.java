package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import com.zbensoft.mmsmp.common.ra.vac.aaa.utils.Utils;
import java.nio.ByteBuffer;
import java.util.Random;

public class Bind extends Header {
	private byte Login_Type = 2;

	public String Login_Name = "openet";
	public String Login_Passowrd = "openet";
	public String Reserve = "";

	public ByteBuffer serialize() {
		int len = 61;
		this.Message_Length = Integer.valueOf(len);
		ByteBuffer bb = ByteBuffer.allocate(len);
		bb.putInt(this.Message_Length.intValue());
		bb.putInt(1);
		Random random = new Random(System.currentTimeMillis());
		bb.putInt(random.nextInt());
		bb.putInt(random.nextInt());
		bb.putInt(random.nextInt());
		bb.put(this.Login_Type);
		Utils.putString(bb, this.Login_Name, 16);
		Utils.putString(bb, this.Login_Passowrd, 16);
		Utils.putString(bb, this.Reserve, 8);
		bb.flip();
		return bb;
	}
}
