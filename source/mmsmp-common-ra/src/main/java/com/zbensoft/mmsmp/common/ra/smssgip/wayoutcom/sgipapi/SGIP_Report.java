package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.nio.ByteBuffer;

public class SGIP_Report extends SGIP_Command {
    String UserNumber;
    long Seq_1;
    int Seq_2;
    int Seq_3;
    int State;
    int ErrorCode;
    int ReportType;

    public SGIP_Report(SGIP_Head sgiphead) {
        super(sgiphead);
    }

    public SGIP_Report() {
    }

    public SGIP_Report(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_Report(SGIP_Command sgip_command, SGIP_Head sgiphead) {
        super(sgiphead);
        this.byteBody = sgip_command.byteBody;
        this.TotalLength = sgip_command.TotalLength;
    }

    public int readbody() {
        this.byteBody.clear();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.clear();
        buffer.putInt(4, this.byteBody.getInt(0));
        buffer.clear();
        this.Seq_1 = buffer.getLong();
        this.Seq_2 = this.byteBody.getInt(4);
        this.Seq_3 = this.byteBody.getInt(8);
        this.ReportType = this.byteBody.getInt(12);
        this.UserNumber = (new String(this.byteBody.array(), 13, 21)).trim();
        this.State = this.byteBody.get(34);
        this.ErrorCode = this.byteBody.get(35);
        return 0;
    }

    public String getUserNumber() {
        return this.UserNumber;
    }

    public int getReportType() {
        return this.ReportType;
    }

    public long getSeq_1() {
        return this.Seq_1;
    }

    public int getSeq_2() {
        return this.Seq_2;
    }

    public int getSeq_3() {
        return this.Seq_3;
    }

    public void setSeq_1(long seq1) {
        this.Seq_1 = seq1;
        this.byteBody = this.byteBody.putInt(0, (int)seq1);
    }

    public void setSeq_2(int seq2) {
        this.Seq_2 = seq2;
        this.byteBody = this.byteBody.putInt(4, seq2);
    }

    public void setSeq_3(int seq3) {
        this.Seq_3 = seq3;
        this.byteBody = this.byteBody.putInt(8, seq3);
    }

    public int getState() {
        return this.State;
    }

    public int getErrorCode() {
        return this.ErrorCode;
    }

    public int hashCode() {
        return this.getSeq_2() << 2 + this.getSeq_3();
    }

    public boolean equals(Object o) {
        return o instanceof SGIP_Command && this.getSeq_1() == ((SGIP_Command)o).getSeqno_1() && this.getSeq_2() == ((SGIP_Command)o).getSeqno_2() && this.getSeq_3() == ((SGIP_Command)o).getSeqno_3();
    }

    public String toString() {
        return "SGIP_Report:seq3=" + super.getSeqno_3() + " seq1=" + this.getSeq_1() + " seq2=" + this.getSeq_2() + " seq3=" + this.getSeq_3();
    }
}
