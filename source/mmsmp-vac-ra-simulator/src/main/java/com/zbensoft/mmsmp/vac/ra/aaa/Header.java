package com.zbensoft.mmsmp.vac.ra.aaa;

import java.nio.ByteBuffer;
import java.util.Date;

import com.zbensoft.mmsmp.vac.ra.util.DateHelper;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;
import com.zbensoft.mmsmp.vac.ra.util.SequenceIDHelper;

public class Header implements Serialize {

	
	
	public byte status = 0;
	public int sended_count = 0;
	public long last_access_time = System.currentTimeMillis();
	public static final int CMDID_Bind = 0x10000001;
	public static final int CMDID_BindResp = 0x80000001;
	public static final int CMDID_UnBind = 0x10000002;
	public static final int CMDID_UnBindResp = 0x80000002;
	public static final int CMDID_Handset = 0x10000003;
	public static final int CMDID_HandsetResp = 0x80000003;
	public static final int CMDID_CheckPrice = 0x10000005;
	public static final int CMDID_CheckPriceResp = 0x80000005;
	public static final int CMDID_CheckPriceConfirm = 0x10000006;
	public static final int CMDID_CheckPriceConfirmResp = 0x80000006;
	public static final int CMDID_TrafficPrice = 0x10000007;
	public static final int CMDID_TrafficPriceResp = 0x80000007;
	public static final int CMDID_ContentAbstractReq = 0x10000008;
	public static final int CMDID_ContentAbstractResp = 0x80000008;
	public static final Integer HEADER_LENGTH = Integer.valueOf(12);
	protected Integer TotalLength;
	protected Integer CommandId;
	protected Integer SequenceId;
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

	public String generateSequenceNumber(String SourceDevice_ID) {
		String time = DateHelper.getString(new Date(), "MMddHHmmss");
		String dev_id = PropertiesHelper.getString("vac.src.dev.id", "090113");
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
