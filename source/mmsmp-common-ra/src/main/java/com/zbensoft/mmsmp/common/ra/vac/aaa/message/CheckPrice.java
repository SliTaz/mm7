package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import com.zbensoft.mmsmp.common.ra.utils.DateHelper;
import com.zbensoft.mmsmp.common.ra.utils.PropertiesHelper;
import com.zbensoft.mmsmp.common.ra.vac.aaa.utils.Utils;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Date;

public class CheckPrice extends Header {
	public static final Integer AUTHENTICATE = Integer.valueOf(0);

	public static final Integer ORDER = Integer.valueOf(1);
	public static final Integer CANCEL = Integer.valueOf(2);
	public static final Integer CANCEL_ALL = Integer.valueOf(3);
	public static final Integer ONCE_ORDER = Integer.valueOf(4);
	public static final Integer ORDER_RELATION = Integer.valueOf(6);
	public static final Integer BINGBI = Integer.valueOf(7);
	public static final Integer HUIFU = Integer.valueOf(8);
	private Integer SourceDevice_Type;
	private String SourceDevice_ID;
	private Integer DestinationDevice_Type;
	private String DestinationDevice_ID;
	private String SequenceNumber;
	private Integer OA_Type;
	private String OANetwork_ID;
	private String OA = "";
	private Integer DA_Type;
	private String DANetwork_ID;
	private String DA = "";
	private Integer FAType;
	private String FANetwork_ID;
	private String FA;
	private Integer ServiceIDType;
	private String SP_IDorAccessNo = "";
	private String ServiceIDorFeatureString = "";
	private String ProductID = "        ";
	private Integer Service_updown_Type = Integer.valueOf(1);
	private String Begin_Time = getTime();
	private Integer ResentTimes = Integer.valueOf(0);
	private Integer Operation_Type;
	private String ServiceType;
	private TLV LinkID;
	private TLV SMSContentLen;
	private TLV SMSFormat;
	private TLV SMSContent;
	private TLV CPID;
	private TLV ContentID;
	private TLV OrderMethod;
	private TLV PushId;
	private TLV FeeType;
	private TLV Fee;
	private TLV AccessNo;

	public CheckPrice() {
		this.CommandId = Integer.valueOf(268435461);
		this.SourceDevice_Type = PropertiesHelper.getInteger("vac.src.dev.type", Integer.valueOf(9));
		this.SourceDevice_ID = PropertiesHelper.getString("vac.src.dev.id", "090101");
		this.DestinationDevice_Type = PropertiesHelper.getInteger("vac.dest.dev.type", Integer.valueOf(0));
		this.DestinationDevice_ID = PropertiesHelper.getString("vac.dest.dev.id", "000101");
		this.SP_IDorAccessNo = PropertiesHelper.getString("vac.sp_id", "90537");
		this.ServiceType = PropertiesHelper.getString("vac.service_type", "31");
		this.SequenceNumber = generateSequenceNumber();
		this.OA_Type = Common.OA_DA_Type.MSISDN;
		this.DA_Type = Common.OA_DA_Type.MSISDN;
		this.FAType = Common.OA_DA_Type.MSISDN;
		this.OANetwork_ID = "GSM";
		this.DANetwork_ID = "GSM";
		this.FANetwork_ID = "GSM";
	}

	private String getTime() {
		return DateHelper.getString(new Date(), "yyyyMMddHHmmss");
	}

	public void calcTotalLength() {
		this.TotalLength = Integer.valueOf(Header.HEADER_LENGTH.intValue() + 297);
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.LinkID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.SMSContentLen).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.SMSContent).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.CPID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.ContentID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.OrderMethod).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.PushId).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.FeeType).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.Fee).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.AccessNo).intValue());
	}

	private Integer getSize(TLV tlv) {
		return Integer.valueOf(tlv == null ? 0 : tlv.size().intValue());
	}

	public ByteBuffer serialize() throws Exception {
		System.out.println("check price request ..........");
		calcTotalLength();
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue());
		bb.put(super.serialize());
		bb.putInt(this.SourceDevice_Type.intValue());
		Utils.putString(bb, this.SourceDevice_ID, 20);
		bb.putInt(this.DestinationDevice_Type.intValue());
		Utils.putString(bb, this.DestinationDevice_ID, 20);
		Utils.putString(bb, this.SequenceNumber, 20);
		bb.putInt(this.OA_Type.intValue());
		Utils.putString(bb, this.OANetwork_ID, 10);
		Utils.putString(bb, this.OA, 36);
		bb.putInt(this.DA_Type.intValue());
		Utils.putString(bb, this.DANetwork_ID, 10);
		Utils.putString(bb, this.DA, 36);
		bb.putInt(this.FAType.intValue());
		Utils.putString(bb, this.FANetwork_ID, 10);
		Utils.putString(bb, this.FA, 36);
		bb.putInt(this.ServiceIDType.intValue());
		Utils.putString(bb, this.SP_IDorAccessNo, 21);
		Utils.putString(bb, this.ServiceIDorFeatureString, 21);
		Utils.putString(bb, this.ProductID, 8);
		bb.putInt(this.Service_updown_Type.intValue());
		Utils.putString(bb, this.Begin_Time, 14);
		bb.put((byte) (this.ResentTimes.intValue() >> 0));
		bb.putInt(this.Operation_Type.intValue());
		bb.put(this.ServiceType.getBytes());
		Utils.putTLV(bb, this.LinkID);
		Utils.putTLV(bb, this.SMSFormat);
		Utils.putTLV(bb, this.SMSContentLen);
		Utils.putTLV(bb, this.SMSContent);
		Utils.putTLV(bb, this.CPID);
		Utils.putTLV(bb, this.ContentID);
		Utils.putTLV(bb, this.OrderMethod);
		Utils.putTLV(bb, this.PushId);
		Utils.putTLV(bb, this.FeeType);
		Utils.putTLV(bb, this.Fee);
		Utils.putTLV(bb, this.AccessNo);
		bb.flip();

		return ByteBuffer.wrap(bb.array(), 0, this.TotalLength.intValue());
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		ByteBuffer bb = super.unserialize(content);
		this.SourceDevice_Type = Integer.valueOf(bb.getInt());
		this.SourceDevice_ID = Utils.getString(bb, 20);
		this.DestinationDevice_Type = Integer.valueOf(bb.getInt());
		this.DestinationDevice_ID = Utils.getString(bb, 20);
		this.SequenceNumber = Utils.getString(bb, 20);
		this.OA_Type = Integer.valueOf(bb.getInt());
		this.OANetwork_ID = Utils.getString(bb, 10);
		this.OA = Utils.getString(bb, 36);
		this.DA_Type = Integer.valueOf(bb.getInt());
		this.DANetwork_ID = Utils.getString(bb, 10);
		this.DA = Utils.getString(bb, 36);
		this.FAType = Integer.valueOf(bb.getInt());
		this.FANetwork_ID = Utils.getString(bb, 10);
		this.FA = Utils.getString(bb, 36);
		this.ServiceIDType = Integer.valueOf(bb.getInt());
		this.SP_IDorAccessNo = Utils.getString(bb, 21);
		this.ServiceIDorFeatureString = Utils.getString(bb, 21);
		this.ProductID = Utils.getString(bb, 8);
		this.Service_updown_Type = Integer.valueOf(bb.getInt());
		this.Begin_Time = Utils.getString(bb, 14);
		this.ResentTimes = Integer.valueOf(bb.get());
		this.Operation_Type = Integer.valueOf(bb.getInt());
		this.ServiceType = Utils.getString(bb, 2);
		do_setTLV(bb);
		return bb;
	}

	private void do_setTLV(ByteBuffer bb) throws Exception {
		if (bb.remaining() < 2)
			return;
		short tag = bb.getShort();
		short len;
		switch (tag) {
		case 1:
			len = bb.getShort();
			this.LinkID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 7:
			len = bb.getShort();
			this.SMSFormat = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 8:
			len = bb.getShort();
			this.SMSContentLen = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 9:
			len = bb.getShort();
			this.SMSContent = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 4610:
			len = bb.getShort();
			this.FeeType = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5383:
			len = bb.getShort();
			this.PushId = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5384:
			len = bb.getShort();
			this.Fee = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5385:
			len = bb.getShort();
			this.CPID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5392:
			len = bb.getShort();
			this.ContentID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5393:
			len = bb.getShort();
			this.OrderMethod = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
			break;
		case 5397:
			len = bb.getShort();
			this.AccessNo = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(bb, len));
		}
		if (bb.hasRemaining())
			do_setTLV(bb);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" \nSourceDevice_Type=").append(this.SourceDevice_Type);
		sb.append(" \nSourceDevice_ID=").append(this.SourceDevice_ID);
		sb.append(" \nDestinationDevice_Type=").append(this.DestinationDevice_Type);
		sb.append(" \nDestinationDevice_ID=").append(this.DestinationDevice_ID);
		sb.append(" \nSequenceNumber=").append(this.SequenceNumber);
		sb.append(" \nOA_Type=").append(this.OA_Type);
		sb.append(" \nOANetwork_ID=").append(this.OANetwork_ID);
		sb.append(" \nOA=").append(this.OA);
		sb.append(" \nDA_Type=").append(this.DA_Type);
		sb.append(" \nDANetwork_ID=").append(this.DANetwork_ID);
		sb.append(" \nDA=").append(this.DA);
		sb.append(" \nFAType=").append(this.FAType);
		sb.append(" \nFANetwork_ID=").append(this.FANetwork_ID);
		sb.append(" \nFA=").append(this.FA);
		sb.append(" \nServiceIDType=").append(this.ServiceIDType);
		sb.append(" \nSP_ID=").append(this.SP_IDorAccessNo);
		sb.append(" \nServiceID=").append(this.ServiceIDorFeatureString);
		sb.append(" \nProductID=").append(this.ProductID);
		sb.append(" \nService_updown_Type=").append(this.Service_updown_Type);
		sb.append(" \nBegin_Time=").append(this.Begin_Time);
		sb.append(" \nResentTimes=").append(this.ResentTimes);
		sb.append(" \nOperation_Type=").append(this.Operation_Type);
		sb.append(" \nServiceType=").append(this.ServiceType);
		sb.append(" \nLinkID=").append(this.LinkID);
		sb.append(" \nSMSFormat=").append(this.SMSFormat);
		sb.append(" \nSMSContentLen=").append(this.SMSContentLen);
		sb.append(" \nSMSContent=").append(this.SMSContent);
		sb.append(" \nCPID=").append(this.CPID);
		sb.append(" \nContentID=").append(this.ContentID);
		sb.append(" \nOrderMethod=").append(this.OrderMethod);
		sb.append(" \nPushId=").append(this.PushId);
		sb.append(" \nFeeType=").append(this.FeeType);
		sb.append(" \nfee=").append(this.Fee);
		sb.append(" \nAccessNo=").append(this.AccessNo);
		sb.append(" ]");
		return sb.toString();
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

	public Integer getOA_Type() {
		return this.OA_Type;
	}

	public void setOA_Type(Integer oAType) {
		this.OA_Type = oAType;
	}

	public String getOANetwork_ID() {
		return this.OANetwork_ID;
	}

	public void setOANetwork_ID(String oANetworkID) {
		this.OANetwork_ID = oANetworkID;
	}

	public String getOA() {
		return this.OA;
	}

	public void setOA(String oA) {
		this.OA = oA;
	}

	public Integer getDA_Type() {
		return this.DA_Type;
	}

	public void setDA_Type(Integer dAType) {
		this.DA_Type = dAType;
	}

	public String getDANetwork_ID() {
		return this.DANetwork_ID;
	}

	public void setDANetwork_ID(String dANetworkID) {
		this.DANetwork_ID = dANetworkID;
	}

	public String getDA() {
		return this.DA;
	}

	public void setDA(String dA) {
		this.DA = dA;
	}

	public Integer getFAType() {
		return this.FAType;
	}

	public void setFAType(Integer fAType) {
		this.FAType = fAType;
	}

	public String getFANetwork_ID() {
		return this.FANetwork_ID;
	}

	public void setFANetwork_ID(String fANetworkID) {
		this.FANetwork_ID = fANetworkID;
	}

	public String getFA() {
		return this.FA;
	}

	public void setFA(String fA) {
		this.FA = fA;
	}

	public Integer getServiceIDType() {
		return this.ServiceIDType;
	}

	public void setServiceIDType(Integer serviceIDType) {
		this.ServiceIDType = serviceIDType;
	}

	public String getSP_IDorAccessNo() {
		return this.SP_IDorAccessNo;
	}

	public void setSP_IDorAccessNo(String _SP_IDorAccessNo) {
		this.SP_IDorAccessNo = _SP_IDorAccessNo;
	}

	public String getServiceIDorFeatureString() {
		return this.ServiceIDorFeatureString;
	}

	public void setServiceIDorFeatureString(String _ServiceIDorFeatureString) {
		this.ServiceIDorFeatureString = _ServiceIDorFeatureString;
	}

	public String getProductID() {
		return this.ProductID;
	}

	public void setProductID(String productID) {
		this.ProductID = productID;
	}

	public Integer getService_updown_Type() {
		return this.Service_updown_Type;
	}

	public void setService_updown_Type(Integer serviceUpdownType) {
		this.Service_updown_Type = serviceUpdownType;
	}

	public String getBegin_Time() {
		return this.Begin_Time;
	}

	public void setBegin_Time(String beginTime) {
		this.Begin_Time = beginTime;
	}

	public Integer getResentTimes() {
		return this.ResentTimes;
	}

	public void setResentTimes(Integer resentTimes) {
		this.ResentTimes = resentTimes;
	}

	public Integer getOperation_Type() {
		return this.Operation_Type;
	}

	public void setOperation_Type(Integer operationType) {
		this.Operation_Type = operationType;
	}

	public String getServiceType() {
		return this.ServiceType;
	}

	public void setServiceType(String serviceType) {
		this.ServiceType = serviceType;
	}

	public TLV getLinkID() {
		return this.LinkID;
	}

	public void setLinkID(TLV linkID) {
		this.LinkID = linkID;
	}

	public void setLinkID(String linkID) {
		if (linkID != null)
			this.LinkID = new TLV(Common.TAG.LinkID, Short.valueOf((short) 20), linkID);
	}

	public TLV getSMSFormat() {
		return this.SMSFormat;
	}

	public void setSMSFormat(TLV sMSFormat) {
		this.SMSFormat = sMSFormat;
	}

	public void setSMSFormat(String sMSFormat) {
		if (sMSFormat != null)
			this.SMSFormat = new TLV(Common.TAG.SMSFormat, Short.valueOf((short) 4), sMSFormat);
	}

	public TLV getSMSContentLen() {
		return this.SMSContentLen;
	}

	public void setSMSContentLen(TLV sMSContentLen) {
		this.SMSContentLen = sMSContentLen;
	}

	public TLV getSMSContent() {
		return this.SMSContent;
	}

	public void setSMSContent(TLV sMSContent) {
		this.SMSContent = sMSContent;
	}

	public void setSMSContent(String sMSContent) {
		if (sMSContent != null) {
			int len = sMSContent.length();
			this.SMSContentLen = new TLV(Common.TAG.SMSContentLen, Short.valueOf((short) 4), String.valueOf(len));
			this.SMSContent = new TLV(Common.TAG.SMSContent, Short.valueOf((short) len), sMSContent);
		}
	}

	public TLV getCPID() {
		return this.CPID;
	}

	public void setCPID(TLV cPID) {
		this.CPID = cPID;
	}

	public void setCPID(String cPID) {
		if (cPID != null)
			this.CPID = new TLV(Common.TAG.CPID, Short.valueOf((short) 8), cPID);
	}

	public TLV getContentID() {
		return this.ContentID;
	}

	public void setContentID(TLV contentID) {
		this.ContentID = contentID;
	}

	public void setContentID(String contentID) {
		if (contentID != null)
			this.ContentID = new TLV(Common.TAG.ContentID, Short.valueOf((short) 22), contentID);
	}

	public TLV getOrderMethod() {
		return this.OrderMethod;
	}

	public void setOrderMethod(TLV orderMethod) {
		this.OrderMethod = orderMethod;
	}

	public void setOrderMethod(String orderMethod) {
		if (orderMethod != null)
			this.OrderMethod = new TLV(Common.TAG.OrderMethod, Short.valueOf((short) 4), orderMethod);
	}

	public TLV getPushId() {
		return this.PushId;
	}

	public void setPushId(TLV pushId) {
		this.PushId = pushId;
	}

	public void setPushId(String pushId) {
		if (pushId != null)
			this.PushId = new TLV(Common.TAG.PushId, Short.valueOf((short) 20), pushId);
	}

	public TLV getFeeType() {
		return this.FeeType;
	}

	public void setFeeType(TLV feeType) {
		this.FeeType = feeType;
	}

	public void setFeeType(String feeType) {
		if (feeType != null)
			this.FeeType = new TLV(Common.TAG.FeeType, Short.valueOf((short) 2), feeType);
	}

	public TLV getFee() {
		return this.Fee;
	}

	public void setFee(TLV fee) {
		this.Fee = fee;
	}

	public void setFee(String fee) {
		if (fee != null)
			this.Fee = new TLV(Common.TAG.Fee, Short.valueOf((short) 8), fee);
	}

	public TLV getAccessNo() {
		return this.AccessNo;
	}

	public void setAccessNo(TLV accessNo) {
		this.AccessNo = accessNo;
	}

	public void setAccessNo(String accessNo) {
		if (accessNo != null)
			this.AccessNo = new TLV(Common.TAG.AccessNo, Short.valueOf((short) 21), accessNo);
	}

	public static void main(String[] args) throws Exception {
		CheckPrice x = new CheckPrice();
		x.setSourceDevice_Type(Integer.valueOf(44));
		x.setSourceDevice_ID("xxxxx");
		x.setDestinationDevice_Type(Integer.valueOf(222));
		x.setDestinationDevice_ID("sssssssssssss");
		x.setSequenceNumber("eeeeeeeeeeee");
		x.setOA_Type(Integer.valueOf(324));
		x.setOANetwork_ID("erqwer");
		x.setOA("ewqdsfasf");
		x.setDA_Type(Integer.valueOf(234));
		x.setDANetwork_ID("wqoijiohsdf");
		x.setDA("aslkdfj");
		x.setFAType(Integer.valueOf(123412));
		x.setFANetwork_ID("asdlkfj");
		x.setFA("a;slkdfj");
		x.setServiceIDType(Integer.valueOf(2222));
		x.setSP_IDorAccessNo("sdklfjh");
		x.setServiceIDorFeatureString(";oqwieiu");
		x.setProductID("a;skdljf;kljsd");
		x.setService_updown_Type(Integer.valueOf(2342134));
		x.setBegin_Time("qweijsdklvjs");
		x.setResentTimes(Integer.valueOf(11));
		x.setOperation_Type(Integer.valueOf(33));
		x.setServiceType("31");

		x.setLinkID("qweertyuioie");
		x.setSMSContent("qwert");
		x.setContentID("wertyyui");
		x.setAccessNo("wertyyuasdfasdfi");

		CheckPrice a = new CheckPrice();

		System.out.println(a);
	}
}
