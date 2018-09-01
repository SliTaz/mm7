package com.zbensoft.mmsmp.vac.ra.aaa;

import java.nio.ByteBuffer;

import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;
import com.zbensoft.mmsmp.vac.ra.util.Utils;

public class CheckPriceConfirm extends Header {
	private Integer SourceDevice_Type;
	private String SourceDevice_ID;
	private Integer DestinationDevice_Type;
	private String DestinationDevice_ID;
	private String SequenceNumber;
	private Integer ErrCode;
	private String End_Time;
	private String ServiceType;

	public CheckPriceConfirm(Integer messageid, String threadname) {
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + 88);
		this.SequenceId = messageid;
		this.CommandId = CMDID_CheckPriceConfirm;
		this.SourceDevice_Type = PropertiesHelper.getInteger("vac.src.dev.type", Integer.valueOf(9));

		String srcdevids = PropertiesHelper.getString("vac.src.dev.id", "090102,090103,090104");
		String[] srcdevid = srcdevids.split(",");
		int index = Integer.parseInt(threadname.substring(threadname.length() - 1));
		if (index + 1 > srcdevid.length) {
			throw new RuntimeException("thread number cannot more than SourceDevice_ID number");
		}
		this.SourceDevice_ID = srcdevid[index];

		this.DestinationDevice_Type = PropertiesHelper.getInteger("vac.dest.dev.type", Integer.valueOf(0));
		this.DestinationDevice_ID = PropertiesHelper.getString("vac.dest.dev.id", "000101");
		this.SequenceNumber = generateSequenceNumber(this.SourceDevice_ID);
		this.ServiceType = PropertiesHelper.getString("vac.service_type", "b");
	}

	public Integer getSourceDevice_Type() {
		return this.SourceDevice_Type;
	}

	public void setSourceDevice_Type(Integer sourceDeviceType) {
		this.SourceDevice_Type = sourceDeviceType;
	}

	public String getSourceDevice_ID() {
		return this.SourceDevice_ID;
	}

	public void setSourceDevice_ID(String sourceDeviceID) {
		this.SourceDevice_ID = sourceDeviceID;
	}

	public Integer getDestinationDevice_Type() {
		return this.DestinationDevice_Type;
	}

	public void setDestinationDevice_Type(Integer destinationDeviceType) {
		this.DestinationDevice_Type = destinationDeviceType;
	}

	public String getDestinationDevice_ID() {
		return this.DestinationDevice_ID;
	}

	public void setDestinationDevice_ID(String destinationDeviceID) {
		this.DestinationDevice_ID = destinationDeviceID;
	}

	public String getSequenceNumber() {
		return this.SequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.SequenceNumber = sequenceNumber;
	}

	public Integer getErrCode() {
		return this.ErrCode;
	}

	public void setErrCode(Integer errCode) {
		this.ErrCode = errCode;
	}

	public String getEnd_Time() {
		return this.End_Time;
	}

	public void setEnd_Time(String endTime) {
		this.End_Time = endTime;
	}

	public String getServiceType() {
		return this.ServiceType;
	}

	public void setServiceType(String serviceType) {
		this.ServiceType = serviceType;
	}

	public ByteBuffer serialize() throws Exception {
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue());
		bb.put(super.serialize());
		bb.putInt(this.SourceDevice_Type.intValue());
		Utils.putString(bb, this.SourceDevice_ID, 20);
		bb.putInt(this.DestinationDevice_Type.intValue());
		Utils.putString(bb, this.DestinationDevice_ID, 20);
		Utils.putString(bb, this.SequenceNumber, 20);
		bb.putInt(this.ErrCode.intValue());
		Utils.putString(bb, this.End_Time, 14);
		Utils.putString(bb, this.ServiceType, 2);
		bb.flip();
		return bb;
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		ByteBuffer bb = super.unserialize(content);
		this.SourceDevice_Type = Integer.valueOf(bb.getInt());
		this.SourceDevice_ID = Utils.getString(bb, 20);
		this.DestinationDevice_Type = Integer.valueOf(bb.getInt());
		this.DestinationDevice_ID = Utils.getString(bb, 20);
		this.SequenceNumber = Utils.getString(bb, 20);
		this.ErrCode = Integer.valueOf(bb.getInt());
		this.End_Time = Utils.getString(bb, 14);
		this.ServiceType = Utils.getString(bb, 2);
		return bb;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" SourceDevice_Type=").append(this.SourceDevice_Type);
		sb.append(" SourceDevice_ID=").append(this.SourceDevice_ID);
		sb.append(" DestinationDevice_Type=").append(this.DestinationDevice_Type);
		sb.append(" DestinationDevice_ID=").append(this.DestinationDevice_ID);
		sb.append(" SequenceNumber=").append(this.SequenceNumber);
		sb.append(" ErrCode=").append(this.ErrCode);
		sb.append(" End_Time=").append(this.End_Time);
		sb.append(" ServiceType=").append(this.ServiceType);
		sb.append(" ]");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		CheckPriceConfirm x = new CheckPriceConfirm(Integer.valueOf(123456), "thread0");
		x.SourceDevice_Type = Integer.valueOf(123);
		x.SourceDevice_ID = "DFGSADFASDFASDFASDF";
		x.DestinationDevice_Type = Integer.valueOf(12341234);
		x.DestinationDevice_ID = "asdlfjldjf";
		x.SequenceNumber = "sdlkfjlasdkjf";
		x.ErrCode = Integer.valueOf(3241);
		x.End_Time = "w3resdf";
		x.ServiceType = "sadlfkjasd";
		System.out.println(x);
		x.unserialize(x.serialize());
		System.out.println(x);
	}

}
