package com.zbensoft.mmsmp.common.ra.vac.aaa.message;

import com.zbensoft.mmsmp.common.ra.vac.aaa.utils.Utils;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public class CheckPriceResp extends Header {
	private Integer Result_Code;
	private Integer ConfirmInterval;
	private byte NeedConfirm;
	private String originalADR;
	private TLV LinkID;
	private TLV FeeType;
	private TLV Redirect;
	private TLV ReturnMessage;
	private TLV NeedToNextNode;
	private TLV SP_ProductID;
	private TLV SPEC_ProductID;
	private TLV ProductID;
	public String src_SequenceNumber;

	public CheckPriceResp() {
		this.CommandId = Integer.valueOf(-2147483643);
	}

	public void calcTotalLength() {
		this.TotalLength = Integer.valueOf(Header.HEADER_LENGTH.intValue() + 45);
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.LinkID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.FeeType).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.Redirect).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.ReturnMessage).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.NeedToNextNode).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.SP_ProductID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.SPEC_ProductID).intValue());
		this.TotalLength = Integer.valueOf(this.TotalLength.intValue() + getSize(this.ProductID).intValue());
	}

	private Integer getSize(TLV tlv) {
		return Integer.valueOf(tlv == null ? 0 : tlv.size().intValue());
	}

	public ByteBuffer serialize() throws Exception {
		calcTotalLength();
		ByteBuffer bb = ByteBuffer.allocate(this.TotalLength.intValue());
		bb.put(super.serialize());
		bb.putInt(this.Result_Code.intValue());
		bb.putInt(this.ConfirmInterval.intValue());
		bb.put((byte) (this.NeedConfirm >> 0));
		Utils.putString(bb, this.originalADR, 36);
		Utils.putTLV(bb, this.LinkID);
		Utils.putTLV(bb, this.FeeType);
		Utils.putTLV(bb, this.Redirect);
		Utils.putTLV(bb, this.ReturnMessage);
		Utils.putTLV(bb, this.NeedToNextNode);
		Utils.putTLV(bb, this.SP_ProductID);
		Utils.putTLV(bb, this.SPEC_ProductID);
		Utils.putTLV(bb, this.ProductID);
		bb.flip();
		return ByteBuffer.wrap(bb.array(), 0, this.TotalLength.intValue());
	}

	public ByteBuffer unserialize(ByteBuffer content) throws Exception {
		System.out.println("response from check price........");

		ByteBuffer bb = super.unserialize(content);
		this.Result_Code = Integer.valueOf(bb.getInt());
		System.out.println("Result_Code：  " + this.Result_Code);
		this.ConfirmInterval = Integer.valueOf(bb.getInt());
		System.out.println("ConfirmInterval:  " + this.ConfirmInterval);
		this.NeedConfirm = bb.get();
		System.out.println("NeedConfirm:   " + this.NeedConfirm);
		this.originalADR = Utils.getString(bb, 36);
		System.out.println("originalADR:   " + this.originalADR);
		do_setTLV(bb);

		return bb;
	}

	private void do_setTLV(ByteBuffer dins) throws Exception {
		if (dins.remaining() < 2)
			return;
		short tag = dins.getShort();
		System.out.println("tag:   " + tag);
		short len;
		switch (tag) {
		case 1:
			len = dins.getShort();
			this.LinkID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			System.out.println("LinkID:   " + this.LinkID);
			break;
		case 4610:
			len = dins.getShort();
			this.FeeType = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 4616:
			len = dins.getShort();
			this.NeedToNextNode = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 5377:
			len = dins.getShort();
			this.ReturnMessage = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 5380:
			len = dins.getShort();
			this.Redirect = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 5394:
			len = dins.getShort();
			this.SP_ProductID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 5395:
			len = dins.getShort();
			this.SPEC_ProductID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
			break;
		case 5396:
			len = dins.getShort();
			this.ProductID = new TLV(Short.valueOf(tag), Short.valueOf(len), Utils.getString(dins, len));
		}
		if (dins.hasRemaining())
			do_setTLV(dins);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(" [");
		sb.append(" Result_Code=").append(this.Result_Code);
		sb.append(" ConfirmInterval=").append(this.ConfirmInterval);
		sb.append(" NeedConfirm=").append(this.NeedConfirm);
		sb.append(" originalADR=").append(this.originalADR);
		sb.append(" \nLinkID=").append(this.LinkID);
		sb.append(" \nFeeType=").append(this.FeeType);
		sb.append(" \nRedirect=").append(this.Redirect);
		sb.append(" \nReturnMessage=").append(this.ReturnMessage);
		sb.append(" \nNeedToNextNode=").append(this.NeedToNextNode);
		sb.append(" \nSP_ProductID=").append(this.SP_ProductID);
		sb.append(" \nSPEC_ProductID=").append(this.SPEC_ProductID);
		sb.append(" \nProductID=").append(this.ProductID);
		sb.append(" ]");
		return sb.toString();
	}

	public Integer getResult_Code() {
		return this.Result_Code;
	}

	public void setResult_Code(Integer resultCode) {
		this.Result_Code = resultCode;
	}

	public Integer getConfirmInterval() {
		return this.ConfirmInterval;
	}

	public void setConfirmInterval(Integer confirmInterval) {
		this.ConfirmInterval = confirmInterval;
	}

	public byte getNeedConfirm() {
		return this.NeedConfirm;
	}

	public void setNeedConfirm(byte needConfirm) {
		this.NeedConfirm = needConfirm;
	}

	public String getOriginalADR() {
		return this.originalADR;
	}

	public void setOriginalADR(String originalADR) {
		this.originalADR = originalADR;
	}

	public TLV getLinkID() {
		return this.LinkID;
	}

	public String getLinkID_String() {
		return getString(this.LinkID);
	}

	private String getString(TLV tlv) {
		return tlv == null ? null : tlv.getValue();
	}

	public void setLinkID(TLV linkID) {
		this.LinkID = linkID;
	}

	public TLV getFeeType() {
		return this.FeeType;
	}

	public String getFeeType_String() {
		return getString(this.FeeType);
	}

	public void setFeeType(TLV feeType) {
		this.FeeType = feeType;
	}

	public TLV getRedirect() {
		return this.Redirect;
	}

	public String getRedirect_String() {
		return getString(this.Redirect);
	}

	public void setRedirect(TLV redirect) {
		this.Redirect = redirect;
	}

	public TLV getReturnMessage() {
		return this.ReturnMessage;
	}

	public String getReturnMessage_String() {
		return getString(this.ReturnMessage);
	}

	public void setReturnMessage(TLV returnMessage) {
		this.ReturnMessage = returnMessage;
	}

	public TLV getNeedToNextNode() {
		return this.NeedToNextNode;
	}

	public String getNeedToNextNode_String() {
		return getString(this.NeedToNextNode);
	}

	public void setNeedToNextNode(TLV needToNextNode) {
		this.NeedToNextNode = needToNextNode;
	}

	public TLV getSP_ProductID() {
		return this.SP_ProductID;
	}

	public String getSP_ProductID_String() {
		return getString(this.SP_ProductID);
	}

	public void setSP_ProductID(TLV sPProductID) {
		this.SP_ProductID = sPProductID;
	}

	public TLV getSPEC_ProductID() {
		return this.SPEC_ProductID;
	}

	public String getSPEC_ProductID_String() {
		return getString(this.SPEC_ProductID);
	}

	public void setSPEC_ProductID(TLV sPECProductID) {
		this.SPEC_ProductID = sPECProductID;
	}

	public TLV getProductID() {
		return this.ProductID;
	}

	public String getProductID_String() {
		return getString(this.ProductID);
	}

	public void setProductID(TLV productID) {
		this.ProductID = productID;
	}

	public static void main(String[] args) throws Exception {
		CheckPriceResp resp = new CheckPriceResp();

		resp.setResult_Code(Integer.valueOf(0));
		resp.setConfirmInterval(Integer.valueOf(4));
		resp.setNeedConfirm((byte) 1);
		resp.setOriginalADR("asdfkj");
		resp.setLinkID(new TLV(Common.TAG.LinkID, Short.valueOf((short) 3), "asdlfkj"));
		resp.setFeeType(new TLV(Common.TAG.FeeType, Short.valueOf((short) 3), "asdlfkj"));
		resp.setSP_ProductID(new TLV(Common.TAG.SP_ProductID, Short.valueOf((short) 32), "asdlfkj"));
		resp.setProductID(new TLV(Common.TAG.ProductID, Short.valueOf((short) 3), "asdlfkaaaaaaj"));

		System.out.println(resp);
		CheckPriceResp x = new CheckPriceResp();
		x.unserialize(resp.serialize());
		System.out.println(x);
	}
}
