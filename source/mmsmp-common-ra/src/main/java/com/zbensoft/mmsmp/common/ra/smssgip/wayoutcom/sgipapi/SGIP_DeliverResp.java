package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_DeliverResp extends SGIP_Command {
    private int Result;
    private static final int CommandLength = 9;
    private static final int CommandID = -2147483644;

    public SGIP_DeliverResp(long lSrcNodeID, int nResult) {
        super(lSrcNodeID, 9, -2147483644);
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
    }

    public SGIP_DeliverResp(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_DeliverResp(SGIP_Head sgiphead) {
        super(sgiphead, 9, -2147483644);
    }

    public void SetResult(int nResult) {
        this.Result = nResult;
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
    }

    public int getResult() {
        return this.Result;
    }

    public int readbody() {
        this.byteBody.clear();
        this.Result = this.byteBody.get(0);
        return 0;
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
        return "SGIP_DeliverResp:seq3=" + super.getSeqno_3() + " Result=" + this.Result;
    }
}
