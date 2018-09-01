package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import java.nio.ByteBuffer;

public abstract interface Serialize {
	public abstract ByteBuffer serialize() throws Exception;

	public abstract ByteBuffer unserialize(ByteBuffer paramByteBuffer) throws Exception;
}
