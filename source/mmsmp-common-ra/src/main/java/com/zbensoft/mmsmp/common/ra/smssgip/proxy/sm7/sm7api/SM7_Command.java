package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public abstract class SM7_Command {
    protected ByteBuffer buf;

    public SM7_Command() {
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

    public int getStatus() {
        return this.buf.getInt(8);
    }

    public void setStatus(int status) {
        this.buf.putInt(8, status);
    }

    public int getSeqID() {
        return this.buf.getInt(12);
    }

    public void setSeqID(int seqID) {
        this.buf.putInt(12, seqID);
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

    public static SM7_Command read(Socket sc) throws IOException, Exception {
        InputStream reader = sc.getInputStream();
        byte[] head = new byte[16];
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
                            return new SM7_BindResp(ByteBuffer.wrap(packet));
                        case -2147483646:
                            return new SM7_SubmitResp(ByteBuffer.wrap(packet));
                        case -2147483645:
                            return new SM7_DeliverResp(ByteBuffer.wrap(packet));
                        case -2147483644:
                            return new SM7_ActiveTestResp(ByteBuffer.wrap(packet));
                        case -2147483642:
                            return new SM7_UnbindResp(ByteBuffer.wrap(packet));
                        case 1:
                            return new SM7_Bind(ByteBuffer.wrap(packet));
                        case 2:
                            return new SM7_Submit(ByteBuffer.wrap(packet));
                        case 3:
                            return new SM7_Deliver(ByteBuffer.wrap(packet));
                        case 4:
                            return new SM7_ActiveTest(ByteBuffer.wrap(packet));
                        case 6:
                            return new SM7_Unbind(ByteBuffer.wrap(packet));
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
