package com.zbensoft.mmsmp.sms.ra.sgip.pkg.body;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipBody;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipField;

public class DeliverBody extends SgipBody {
    public DeliverBody() {
        super(items);
    }

    private String userNumber;
    private String spNumber;
    private String tpPid;
    private String tpUdhi;
    private String messageCoding;
    private String messageLength;
    private String messageContent;
    private String reserve;

    private static final SgipField[] items = new SgipField[]{
            new SgipField("userNumber", 21, '\0', SgipField.FillSide.RIGHT),
            new SgipField("spNumber", 21, '\0', SgipField.FillSide.RIGHT),
            new SgipField("tpPid", 1, '\0', SgipField.FillSide.RIGHT),
            new SgipField("tpUdhi", 1, '\0', SgipField.FillSide.RIGHT),
            new SgipField("messageCoding", 1, '\0', SgipField.FillSide.RIGHT),
            new SgipField("messageLength", 4, '\0', SgipField.FillSide.RIGHT),
            new SgipField("messageContent", 4, '\0', SgipField.FillSide.RIGHT),
            new SgipField("reserve", 8, '\0', SgipField.FillSide.RIGHT)
    };


    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getSpNumber() {
        return spNumber;
    }

    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber;
    }

    public String getTpPid() {
        return tpPid;
    }

    public void setTpPid(String tpPid) {
        this.tpPid = tpPid;
    }

    public String getTpUdhi() {
        return tpUdhi;
    }

    public void setTpUdhi(String tpUdhi) {
        this.tpUdhi = tpUdhi;
    }

    public String getMessageCoding() {
        return messageCoding;
    }

    public void setMessageCoding(String messageCoding) {
        this.messageCoding = messageCoding;
    }

    public String getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(String messageLength) {
        this.messageLength = messageLength;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

}
