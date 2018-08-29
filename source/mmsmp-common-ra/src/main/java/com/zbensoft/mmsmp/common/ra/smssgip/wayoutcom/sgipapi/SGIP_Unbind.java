package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

public class SGIP_Unbind extends SGIP_Command {
    private static final int CommandLength = 0;
    private static final int CommandID = 2;
    private int flag = 1;

    public SGIP_Unbind(long lSrcNodeID) {
        super(lSrcNodeID, 0, 2);
    }

    public SGIP_Unbind(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public int GetFlag() {
        return this.flag;
    }

    public SGIP_Unbind() {
        super(0, 2);
    }

    public String toString() {
        return "SGIP_Unbind:seq3=" + super.getSeqno_3();
    }
}
