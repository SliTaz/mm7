package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_Head {
    private ByteBuffer byteHead;
    private int TotalLength;
    private int CommandID;
    private long Seq_1;
    private int Seq_2;
    private int Seq_3;
    private byte[] tempbytes;
    private Logger log = Logger.getLogger(SGIP_Head.class);

    public SGIP_Head(byte[] abyte0) {
        boolean flag = false;
        this.byteHead = ByteBuffer.allocate(20);
        this.byteHead.position(0);
        this.byteHead.put(abyte0);
        this.byteHead.flip();
        this.byteHead.position(0);
        this.TotalLength = this.byteHead.getInt(0);
        this.CommandID = this.byteHead.getInt(4);
        this.Seq_1 = (long)this.byteHead.getInt(8);
        this.Seq_2 = this.byteHead.getInt(12);
        this.Seq_3 = this.byteHead.getInt(16);
    }

    public SGIP_Head() {
        this.byteHead = ByteBuffer.allocate(20);
    }

    public SGIP_Head(int nTotalLength, int nCmdID, int AccessNodeID, int nDateTime, int nLocalSeqNo) {
        boolean flag = false;
        this.byteHead = ByteBuffer.allocate(20);
        this.TotalLength = nTotalLength;
        this.CommandID = nCmdID;
        this.Seq_1 = (long)AccessNodeID;
        this.Seq_2 = nDateTime;
        this.Seq_3 = nLocalSeqNo;
        this.byteHead.putInt(0, nTotalLength);
        this.byteHead.putInt(4, nCmdID);
        this.byteHead.putInt(8, AccessNodeID);
        this.byteHead.putInt(12, nDateTime);
        this.byteHead.putInt(16, nLocalSeqNo);
    }

    public void SetTotalLength(int nTotalLength) {
        this.TotalLength = nTotalLength;
        this.byteHead.putInt(0, nTotalLength);
    }

    public void SetCommandID(int nCmdID) {
        this.CommandID = nCmdID;
        this.byteHead.putInt(4, nCmdID);
    }

    public void SetSeq_1(long lsrcNodeID) {
        this.Seq_1 = lsrcNodeID;
        this.byteHead.putInt(8, (int)lsrcNodeID);
    }

    public void SetSeq_2(int nDateTime) {
        this.Seq_2 = nDateTime;
        this.byteHead.putInt(12, nDateTime);
    }

    public void SetSeq_3(int nLocalSeqNo) {
        this.Seq_3 = nLocalSeqNo;
        this.byteHead.putInt(16, nLocalSeqNo);
    }

    public int GetTotalLength() {
        return this.TotalLength;
    }

    public int GetCommandID() {
        return this.CommandID;
    }

    public long GetSeq_1() {
        return this.Seq_1;
    }

    public int GetSeq_2() {
        return this.Seq_2;
    }

    public int GetSeq_3() {
        return this.Seq_3;
    }

    public ByteBuffer GetHeadByte() {
        this.byteHead.putInt(0, this.TotalLength);
        this.byteHead.putInt(4, this.CommandID);
        this.byteHead.putInt(8, (int)this.Seq_1);
        this.byteHead.putInt(12, this.Seq_2);
        this.byteHead.putInt(16, this.Seq_3);
        return this.byteHead;
    }

    public int WriteHead(Socket sc) throws IOException {
        boolean flag = false;
        OutputStream writer = sc.getOutputStream();
        this.byteHead.flip();
        writer.write(this.byteHead.array());
        int nSend = this.byteHead.array().length;
        return nSend;
    }

    public int ReadHead(Socket sc) throws IOException {
        InputStream reader = sc.getInputStream();
        ByteBuffer recByte = ByteBuffer.allocate(20);
        recByte.clear();
        int nrec = reader.read(recByte.array());
        if (nrec > 0) {
            this.TotalLength = recByte.getInt(0);
            this.CommandID = recByte.getInt(4);
            this.Seq_1 = (long)recByte.getInt(8);
            this.Seq_2 = recByte.getInt(12);
            this.Seq_3 = recByte.getInt(16);
        }

        return nrec;
    }

    public String toString() {
        return "TotalLength:" + this.TotalLength + " CommandID:" + Integer.toHexString(this.CommandID) + " Seq_1:" + this.Seq_1 + " Seq_2:" + this.Seq_2 + " Seq_3:" + this.Seq_3;
    }
}
