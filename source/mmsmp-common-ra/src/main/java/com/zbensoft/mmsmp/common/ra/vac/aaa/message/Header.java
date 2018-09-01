package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import com.zbensoft.mmsmp.common.ra.utils.DateHelper;
import com.zbensoft.mmsmp.common.ra.utils.PropertiesHelper;
import com.zbensoft.mmsmp.common.ra.vac.aaa.utils.SequenceIDHelper;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Date;

public class Header implements Serialize {
	public byte status = 0;
	public int sended_count = 0;
	public long last_access_time = System.currentTimeMillis();
	public static final int CMDID_Bind = 268435457;
	public static final int CMDID_BindResp = -2147483647;
	public static final int CMDID_UnBind = 268435458;
	public static final int CMDID_UnBindResp = -2147483646;
	public static final int CMDID_Handset = 268435459;
	public static final int CMDID_HandsetResp = -2147483645;
	public static final int CMDID_CheckPrice = 268435461;
	public static final int CMDID_CheckPriceResp = -2147483643;
	public static final int CMDID_CheckPriceConfirm = 268435462;
	public static final int CMDID_CheckPriceConfirmResp = -2147483642;
	public static final int CMDID_TrafficPrice = 268435463;
	public static final int CMDID_TrafficPriceResp = -2147483641;
	public static final int CMDID_ContentAbstractReq = 268435464;
	public static final int CMDID_ContentAbstractResp = -2147483640;
	public static final Integer HEADER_LENGTH = Integer.valueOf(12);
	protected Integer TotalLength;
	protected Integer CommandId;
	private Integer SequenceId;
	private static int seq = 1000;
	private static int seq_max = 9999;

	public Header() {
		this.TotalLength = HEADER_LENGTH;
		this.SequenceId = SequenceIDHelper.getIntValue();
	}

	protected void calcTotalLength() {
	}

	public Integer getTotalLength() {
		calcTotalLength();
		return this.TotalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.TotalLength = totalLength;
	}

	public Integer getCommandId() {
		return this.CommandId;
	}

	public Integer getSequenceId() {
		return this.SequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.SequenceId = sequenceId;
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(HEADER_LENGTH.intValue());
		bb.putInt(this.TotalLength.intValue());
		bb.putInt(this.CommandId.intValue());
		bb.putInt(this.SequenceId.intValue());
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		this.TotalLength = Integer.valueOf(content.getInt());
		this.CommandId = Integer.valueOf(content.getInt());
		this.SequenceId = Integer.valueOf(content.getInt());
		return content;
	}

	public String generateSequenceNumber() {
		String time = DateHelper.getString(new Date(), "MMddHHmmss");
		String dev_id = PropertiesHelper.getString("vac.src.dev.id", "090101");
		String seq_id = getSeq();
		return time + dev_id + seq_id;
	}

	private synchronized String getSeq() {
		if (seq++ > seq_max)
			seq = 1000;
		return String.valueOf(seq);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" TotalLength=").append(this.TotalLength);
		sb.append(" CommandId=").append(this.CommandId);
		sb.append(" SequenceId=").append(this.SequenceId);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		Header header = new Header();
		header.TotalLength = Integer.valueOf(200);
		header.CommandId = Integer.valueOf(268435457);
		header.SequenceId = SequenceIDHelper.getIntValue();

		System.out.println(header);

		header.unserialize(header.serialize());
		System.out.println(header);
	}
}
