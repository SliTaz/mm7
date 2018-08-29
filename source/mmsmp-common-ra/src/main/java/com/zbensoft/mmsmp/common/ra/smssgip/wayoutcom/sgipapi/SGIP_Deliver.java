package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.io.UnsupportedEncodingException;

public class SGIP_Deliver extends SGIP_Command {
    String UserNumber;
    String SpNumber;
    int TP_pid;
    int TP_udhi;
    int MessageCoding;
    int MessageLength;
    String MessageContent;
    String linkId;

    public SGIP_Deliver(SGIP_Head sgiphead) {
        super(sgiphead);
    }

    public SGIP_Deliver(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_Deliver() {
    }

    public int readbody() {
        this.byteBody.clear();
        this.UserNumber = (new String(this.byteBody.array(), 0, 21)).trim();
        this.SpNumber = (new String(this.byteBody.array(), 21, 21)).trim();
        this.TP_pid = this.byteBody.get(42);
        this.TP_udhi = this.byteBody.get(43);
        this.MessageCoding = this.byteBody.get(44);
        this.MessageLength = this.byteBody.getInt(45);
        if (this.MessageLength > 160) {
            System.out.println("Message Length is too long!  " + this.MessageLength);
            this.MessageLength = 160;
        }

        try {
            if (this.MessageCoding == 8) {
                this.MessageContent = (new String(this.byteBody.array(), 49, this.MessageLength, "UTF-16BE")).trim();
            } else {
                this.MessageContent = (new String(this.byteBody.array(), 49, this.MessageLength)).trim();
            }
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        }

        this.linkId = (new String(this.byteBody.array(), 49 + this.MessageLength, 8)).trim();
        return 0;
    }

    public String getUserNumber() {
        return this.UserNumber;
    }

    public String getSPNumber() {
        return this.SpNumber;
    }

    public int getTP_pid() {
        return this.TP_pid;
    }

    public int getTP_udhi() {
        return this.TP_udhi;
    }

    public int getMessageCoding() {
        return this.MessageCoding;
    }

    public int getMessageLength() {
        return this.MessageLength;
    }

    public String getMessageContent() {
        return this.MessageContent;
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String toString() {
        return "SGIP_Deliver:seq3=" + super.getSeqno_3() + " UserNumber=" + this.UserNumber + " MessageContent=" + this.MessageContent + " MessageCoding=" + this.MessageCoding + " SpNumber=" + this.SpNumber;
    }
}
