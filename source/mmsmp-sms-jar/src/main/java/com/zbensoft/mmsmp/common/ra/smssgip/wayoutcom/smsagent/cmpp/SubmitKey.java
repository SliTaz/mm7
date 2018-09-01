package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.ProduceMsgID;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class SubmitKey {
    private CMPP_Submit submit;
    private Long msgID;

    SubmitKey(CMPP_Submit submit, Long msgID) {
        this.submit = submit;
        this.msgID = msgID;
    }

    CMPP_Submit getSubmit() {
        return this.submit;
    }

    void setSubmit(CMPP_Submit submit) {
        this.submit = submit;
    }

    Long getMsgID() {
        return this.msgID;
    }

    void setMsgID(Long msgID) {
        this.msgID = msgID;
    }

    public int hashCode() {
        int msgSeq = 0;
        if (this.msgID != null) {
            msgSeq = ProduceMsgID.getSeq(this.msgID);
        }

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
            SubmitKey submitKey = (SubmitKey)o;
            if (this.submit != null && submitKey != null && submitKey.submit != null) {
                seq = this.submit.getSeqID() == submitKey.submit.getSeqID();
            } else if (this.submit == null && this.msgID != null) {
                seq = true;
            }

            if (this.msgID != null && submitKey != null && submitKey.msgID != null) {
                long msgid = submitKey.msgID;
                msg = msgid == this.msgID;
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

        int msgSeq = 0;
        byte[] seq = new byte[3];
        if (this.msgID != null) {
            msgSeq = ProduceMsgID.getSeq(this.msgID);
        }

        return "seq:" + seqID + " msg:" + msgSeq;
    }

    public static void main(String[] arg) {
        HashMap hm = new HashMap();
        CMPP_Submit submit = new CMPP_Submit();
        submit.setSeqID(0);
        SubmitKey key = new SubmitKey(submit, (Long)null);
        hm.put(key, "0-null");
        submit = new CMPP_Submit();
        submit.setSeqID(1);
        key = new SubmitKey(submit, (Long)null);
        hm.put(key, "1-null");
        submit = new CMPP_Submit();
        submit.setSeqID(0);
        Long msgID = new Long(1L);
        key = new SubmitKey(submit, msgID);
        hm.put(key, "0-1");
        submit = new CMPP_Submit();
        submit.setSeqID(1);
        msgID = new Long(2L);
        key = new SubmitKey(submit, msgID);
        hm.put(key, "1-2");
        submit = new CMPP_Submit();
        submit.setSeqID(2);
        msgID = new Long(3L);
        key = new SubmitKey(submit, msgID);
        hm.put(key, "2-3");
        Set set = hm.keySet();
        Iterator it = set.iterator();
        Long m = new Long(1L);
        SubmitKey obj = new SubmitKey((CMPP_Submit)null, m);
        hm.remove(obj);
    }
}

