package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_UnbindResp extends SGIP_Command {
    private static final int CommandLength = 0;
    private static final int CommandID = -2147483646;
    private int flag = 1;

    public SGIP_UnbindResp(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_UnbindResp(SGIP_Head sgiphead) {
        super(sgiphead, 0, -2147483646);
    }

    public SGIP_UnbindResp(long lSrcNodeID) {
        super(lSrcNodeID, 0, -2147483646);
    }

    public SGIP_UnbindResp() {
        super(0, -2147483646);
    }

    public int GetFlag() {
        return this.flag;
    }

    public int write(Socket sc) throws IOException {
        byte[] abyte0 = new byte[this.TotalLength];
        OutputStream writer = sc.getOutputStream();
        ByteBuffer sendByte = ByteBuffer.allocate(this.TotalLength);
        sendByte.clear();
        sendByte.put(this.sgipHead.GetHeadByte());
        sendByte.position(20);
        this.byteBody.flip();
        this.byteBody.clear();
        sendByte.put(this.byteBody);
        sendByte.clear();
        writer.write(sendByte.array());
        int nSend = sendByte.array().length;
        return nSend;
    }

    public String toString() {
        return "SGIP_UnbindResp:seq3=" + super.getSeqno_3();
    }
}
