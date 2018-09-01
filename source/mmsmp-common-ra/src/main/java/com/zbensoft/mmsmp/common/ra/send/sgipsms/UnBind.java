package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import java.nio.ByteBuffer;

public class UnBind extends Header {
	public UnBind() {
		this.Command_ID = Integer.valueOf(2);
	}

	public ByteBuffer serialize() {
		return super.serialize();
	}
}
