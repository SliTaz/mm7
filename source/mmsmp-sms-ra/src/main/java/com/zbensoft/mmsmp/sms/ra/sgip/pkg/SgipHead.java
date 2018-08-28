package com.zbensoft.mmsmp.sms.ra.sgip.pkg;

public class SgipHead extends SgipBody {
    private int messageLength;
    private int commandId;
    private int sequenceNumber;


    public SgipHead() {
        super(items);
    }

    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    private static final SgipField[] items = new SgipField[]{
            new SgipField("messageLength", 4, '0', SgipField.FillSide.RIGHT),
            new SgipField("commandId", 4, '0', SgipField.FillSide.RIGHT),
            new SgipField("sequenceNumber", 12, '0', SgipField.FillSide.RIGHT),
    };

}
