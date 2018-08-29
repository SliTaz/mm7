package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SGIP_SequenceNo {
    public static Class seqclass = (new SGIP_SequenceNo()).getClass();
    private static long globalSeq_1;
    private static int globalSeq_2;
    private static int globalSeq_3;
    private static int Min_Seq;
    private static int Max_Seq;

    public SGIP_SequenceNo(int nMinSeq, int nMaxSeq) {
        Min_Seq = nMinSeq;
        Max_Seq = nMaxSeq;
        globalSeq_3 = Min_Seq;
    }

    public SGIP_SequenceNo() {
        Min_Seq = 0;
        Max_Seq = 2147483647;
    }

    public void setNodeId(long lSrcNodeID) {
        globalSeq_1 = lSrcNodeID;
    }

    public long getGlobalSeq_1() {
        return globalSeq_1;
    }

    public int getGlobalSeq_2() {
        return globalSeq_2;
    }

    public int getGlobalSeq_3() {
        return globalSeq_3;
    }

    public static synchronized void computeSequence() {
        Calendar cal = new GregorianCalendar();
        if (globalSeq_3 == Max_Seq) {
            globalSeq_3 = Min_Seq;
        } else {
            ++globalSeq_3;
        }

        globalSeq_2 = (cal.get(2) + 1) * 100000000 + cal.get(5) * 1000000 + cal.get(11) * 10000 + cal.get(12) * 100 + cal.get(13);
    }
}
