package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import com.zbensoft.mmsmp.common.ra.vac.aaa.utils.Utils;
import java.nio.ByteBuffer;

public class BindResp extends Header {
	public byte Result;
	public String Reserve;

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		ByteBuffer bb = super.unserialize(content);
		this.Result = bb.get();
		this.Reserve = Utils.getString(bb, 8);
		return bb;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Result=").append(this.Result);
		sb.append(" Reserve=").append(this.Reserve);
		sb.append(" ]");
		return sb.toString();
	}
}
