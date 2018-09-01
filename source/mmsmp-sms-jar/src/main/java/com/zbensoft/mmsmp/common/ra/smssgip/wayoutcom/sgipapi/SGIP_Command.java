package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_Command {
    protected ByteBuffer byteBody;
    protected SGIP_Head sgipHead;
    protected int TotalLength = 0;
    private Logger log = Logger.getLogger(SGIP_Command.class);
    protected static SGIP_SequenceNo seq = null;
    public static boolean bBound;

    public SGIP_Command() {
        this.sgipHead = new SGIP_Head();
        if (seq == null) {
            seq = new SGIP_SequenceNo();
        }

    }

    public SGIP_Command(long lSrcNodeID) {
        this.sgipHead = new SGIP_Head();
        if (seq == null) {
            seq = new SGIP_SequenceNo();
        }

        seq.setNodeId(lSrcNodeID);
    }

    public SGIP_Command(SGIP_Head sgiphead) {
        this.sgipHead = sgiphead;
    }

    public SGIP_Command(SGIP_Command sgip_command) {
        this.sgipHead = new SGIP_Head(sgip_command.sgipHead.GetHeadByte().array());
        this.byteBody = sgip_command.byteBody;
        this.TotalLength = sgip_command.TotalLength;
    }

    public SGIP_Command(long nSrcNodeID, int nMinSeq, int nMaxSeq, int nBodyLength, int nCmdID) {
        if (seq == null) {
            seq = new SGIP_SequenceNo(nMinSeq, nMaxSeq);
        }

        seq.setNodeId(nSrcNodeID);
        this.TotalLength = 20 + nBodyLength;
        this.sgipHead = new SGIP_Head();
        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(nCmdID);
        this.sgipHead.SetSeq_1(seq.getGlobalSeq_1());
        this.sgipHead.SetSeq_2(seq.getGlobalSeq_2());
        this.sgipHead.SetSeq_3(seq.getGlobalSeq_3());
        this.byteBody = ByteBuffer.allocate(nBodyLength);
    }

    public SGIP_Command(SGIP_Head sgiphead, int nBodyLength, int nCmdID) {
        this.TotalLength = 20 + nBodyLength;
        this.sgipHead = new SGIP_Head();
        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(nCmdID);
        this.sgipHead.SetSeq_1(sgiphead.GetSeq_1());
        this.sgipHead.SetSeq_2(sgiphead.GetSeq_2());
        this.sgipHead.SetSeq_3(sgiphead.GetSeq_3());
        this.byteBody = ByteBuffer.allocate(nBodyLength);
    }

    public SGIP_Command(long lSrcNodeID, int nBodyLength, int nCmdID) {
        if (seq == null) {
            seq = new SGIP_SequenceNo();
        }

        seq.setNodeId(lSrcNodeID);
        this.TotalLength = 20 + nBodyLength;
        this.sgipHead = new SGIP_Head();
        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(nCmdID);
        this.sgipHead.SetSeq_1(seq.getGlobalSeq_1());
        this.sgipHead.SetSeq_2(seq.getGlobalSeq_2());
        this.sgipHead.SetSeq_3(seq.getGlobalSeq_3());
        this.byteBody = ByteBuffer.allocate(nBodyLength);
    }

    public SGIP_Command(int nBodyLength, int nCmdID) {
        if (seq == null) {
            seq = new SGIP_SequenceNo();
        }

        this.TotalLength = 20 + nBodyLength;
        this.sgipHead = new SGIP_Head();
        this.sgipHead.SetTotalLength(this.TotalLength);
        this.sgipHead.SetCommandID(nCmdID);
        this.sgipHead.SetSeq_1(seq.getGlobalSeq_1());
        this.sgipHead.SetSeq_2(seq.getGlobalSeq_2());
        this.sgipHead.SetSeq_3(seq.getGlobalSeq_3());
        this.byteBody = ByteBuffer.allocate(nBodyLength);
    }

    public int write(Socket sc) throws IOException {
        OutputStream writer = sc.getOutputStream();
        ByteBuffer sendByte = ByteBuffer.allocate(this.TotalLength);
        Class var5 = SGIP_SequenceNo.seqclass;
        synchronized(SGIP_SequenceNo.seqclass) {
            SGIP_SequenceNo.computeSequence();
            this.sgipHead.SetSeq_2(seq.getGlobalSeq_2());
            this.sgipHead.SetSeq_3(seq.getGlobalSeq_3());
        }

        sendByte.clear();
        int i = this.sgipHead.GetHeadByte().array().length;
        sendByte.put(this.sgipHead.GetHeadByte());
        sendByte.position(20);
        this.byteBody.clear();
        sendByte.put(this.byteBody);
        sendByte.clear();
        writer.write(sendByte.array());
        int nSend = sendByte.array().length;
        return nSend;
    }

    public SGIP_Command read(Socket sc) throws IOException {
        int nret = this.sgipHead.ReadHead(sc);
        if (nret == -1) {
            return null;
        } else {
            this.readBody(sc);
            SGIP_Command result = null;
            switch(this.sgipHead.GetCommandID()) {
                case -2147483647:
                    result = new SGIP_BindResp(this);
                    break;
                case -2147483646:
                    result = new SGIP_UnbindResp(this);
                    break;
                case -2147483645:
                    result = new SGIP_SubmitResp(this);
                    break;
                case -2147483644:
                    result = new SGIP_DeliverResp(this);
                    break;
                case -2147483643:
                    result = new SGIP_ReportResp(this);
                    break;
                case -2147479552:
                    result = new SGIP_TraceResp(this);
                    break;
                case 1:
                    result = new SGIP_Bind(this);
                    break;
                case 2:
                    result = new SGIP_Unbind(this);
                    break;
                case 3:
                    result = new SGIP_Submit(this);
                    break;
                case 4:
                    result = new SGIP_Deliver(this);
                    break;
                case 5:
                    result = new SGIP_Report(this);
                    break;
                case 17:
                    return new SGIP_UserReport(this);
            }

            if (result != null) {
                ((SGIP_Command)result).readbody();
            }

            return (SGIP_Command)result;
        }
    }

    private int readBody(Socket sc) throws IOException {
        InputStream reader = sc.getInputStream();
        this.TotalLength = this.sgipHead.GetTotalLength();
        if (this.TotalLength < 20) {
            SGIP_Common.MsgTrace("CRBTCommand, readBody, read head error!");
            return this.TotalLength;
        } else {
            this.byteBody = ByteBuffer.allocate(this.TotalLength - 20);
            this.byteBody.clear();
            int nlen = reader.read(this.byteBody.array());
            return nlen;
        }
    }

    public int readbody() {
        return -1;
    }

    public int getTotalLength() {
        return this.sgipHead.GetTotalLength();
    }

    public long getSeqno_1() {
        return this.sgipHead.GetSeq_1();
    }

    public int getSeqno_2() {
        return this.sgipHead.GetSeq_2();
    }

    public int getSeqno_3() {
        return this.sgipHead.GetSeq_3();
    }

    public SGIP_Head getHead() {
        return this.sgipHead;
    }

    public ByteBuffer getBodyByte() {
        return this.byteBody;
    }

    public void setBodyByte(ByteBuffer body) {
        this.byteBody = body;
    }

    public void setSeqno_1(long lSrcNodeID) {
        this.sgipHead.SetSeq_1(lSrcNodeID);
    }

    public int getCommandID() {
        return this.sgipHead.GetCommandID();
    }

    public String toString() {
        String info = "";
        info = "commandid: ";
        info = info + this.getCommandID();
        info = info + " seq1:";
        info = info + this.getSeqno_1();
        info = info + " seq2:";
        info = info + this.getSeqno_2();
        info = info + " seq3:";
        info = info + this.getSeqno_3();
        return info;
    }
}
