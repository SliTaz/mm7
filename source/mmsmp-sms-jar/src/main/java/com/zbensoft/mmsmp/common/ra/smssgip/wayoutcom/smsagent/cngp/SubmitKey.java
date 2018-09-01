package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

class SubmitKey implements Cloneable {
    private CNGP_Submit submit;
    private byte[] msgID;

    SubmitKey(CNGP_Submit submit, byte[] msgID) {
        this.submit = submit;
        this.msgID = msgID;
    }

    CNGP_Submit getSubmit() {
        return this.submit;
    }

    void setSubmit(CNGP_Submit submit) {
        this.submit = submit;
    }

    byte[] getMsgID() {
        return this.msgID;
    }

    void setMsgID(byte[] msgID) {
        this.msgID = msgID;
    }

    public int hashCode() {
        byte[] seq = new byte[3];
        if (this.msgID != null) {
            System.arraycopy(this.msgID, 7, seq, 0, seq.length);
        }

        int msgSeq = TypeConvert.byte2int(seq);
        int seqID = 0;
        if (this.submit != null) {
            seqID = this.submit.getSeqID();
        }

        if (this.msgID != null) {
            seqID = 0;
        }

        return seqID + msgSeq;
    }

    public boolean equals(Object o) {
        boolean result = false;
        boolean obj = o instanceof SubmitKey;
        if (obj) {
            boolean seq = false;
            boolean msg = false;
            SubmitKey submitKey = (SubmitKey) o;
            if (this.submit != null && submitKey != null && submitKey.submit != null) {
                seq = this.submit.getSeqID() == submitKey.submit.getSeqID();
            } else if (this.submit == null && this.msgID != null) {
                seq = true;
            }

            if (this.msgID != null && submitKey != null && submitKey.msgID != null) {
                byte[] msgid = submitKey.msgID;
                msg = msgid.length == this.msgID.length;

                for (int i = 0; i < msgid.length && msg; ++i) {
                    msg = msg && msgid[i] == this.msgID[i];
                }
            } else if (this.msgID == null && submitKey != null && submitKey.msgID == null) {
                msg = true;
            }

            result = seq && msg;
        }

        return result;
    }

    public String toString() {
        int seqID = 0;
        if (this.submit != null) {
            seqID = this.submit.getSeqID();
        }

        byte[] seq = new byte[3];
        if (this.msgID != null) {
            System.arraycopy(this.msgID, 7, seq, 0, seq.length);
        }

        int msgSeq = TypeConvert.byte2int(seq);
        return "seq:" + seqID + " msg:" + msgSeq;
    }

    public Object clone() {
        Object o = null;

        try {
            o = super.clone();
        } catch (CloneNotSupportedException var3) {
            System.err.println("SubmitKey can't clone");
        }

        return o;
    }
}

