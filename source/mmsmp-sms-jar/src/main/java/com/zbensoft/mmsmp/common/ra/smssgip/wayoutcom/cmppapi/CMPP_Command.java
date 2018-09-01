package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public abstract class CMPP_Command {
    protected ByteBuffer buf;

    public CMPP_Command() {
    }

    public int getPacketLength() {
        return this.buf.getInt(0);
    }

    public void setPacketLength(int len) {
        this.buf.putInt(0, len);
    }

    public int getCommandID() {
        return this.buf.getInt(4);
    }

    public void setCommandID(int commandID) {
        this.buf.putInt(4, commandID);
    }

    public int getSeqID() {
        return this.buf.getInt(8);
    }

    public void setSeqID(int seqID) {
        this.buf.putInt(8, seqID);
    }

    public ByteBuffer getBuf() {
        return this.buf;
    }

    public boolean write(Socket sc) throws IOException {
        boolean result = false;
        OutputStream writer = sc.getOutputStream();
        byte[] ar = this.buf.array();
        writer.write(ar);
        result = true;
        return result;
    }

    public static CMPP_Command read(Socket sc) throws IOException, Exception {
        InputStream reader = sc.getInputStream();
        byte[] head = new byte[12];
        int count = reader.read(head);
        if (count < head.length) {
            return null;
        } else {
            int pac_len = TypeConvert.byte2int(head, 0);
            if (pac_len >= head.length && pac_len <= 65536) {
                byte[] body = new byte[pac_len - head.length];
                count = reader.read(body);
                if (count < body.length) {
                    return null;
                } else {
                    byte[] packet = new byte[pac_len];
                    System.arraycopy(head, 0, packet, 0, head.length);
                    System.arraycopy(body, 0, packet, head.length, body.length);
                    switch(TypeConvert.byte2int(packet, 4)) {
                        case -2147483647:
                            return new CMPP_BindResp(ByteBuffer.wrap(packet));
                        case -2147483644:
                            return new CMPP_SubmitResp(ByteBuffer.wrap(packet));
                        case -2147483643:
                            return new CMPP_DeliverResp(ByteBuffer.wrap(packet));
                        case 1:
                            return new CMPP_Bind(ByteBuffer.wrap(packet));
                        case 4:
                            return new CMPP_Submit(ByteBuffer.wrap(packet));
                        case 5:
                            return new CMPP_Deliver(ByteBuffer.wrap(packet));
                        default:
                            return null;
                    }
                }
            } else {
                return null;
            }
        }
    }
}
