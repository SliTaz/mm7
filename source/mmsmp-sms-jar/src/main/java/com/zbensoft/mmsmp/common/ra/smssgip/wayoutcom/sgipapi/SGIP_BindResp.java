package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_BindResp extends SGIP_Command {
    private static final int CommandLength = 9;
    private static final int CommandID = -2147483647;
    private int Result;
    private int flag = 1;

    public SGIP_BindResp(long lSrcNodeID) {
        super(lSrcNodeID, 9, -2147483647);
    }

    public SGIP_BindResp(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public SGIP_BindResp(SGIP_Head sgiphead) {
        super(sgiphead, 9, -2147483647);
    }

    public SGIP_BindResp() {
        super(9, -2147483647);
    }

    public int GetFlag() {
        return this.flag;
    }

    public SGIP_BindResp(long lSrcNodeID, int nResult) {
        super(lSrcNodeID, 9, -2147483647);
        this.Result = nResult;
        this.byteBody.position(0);
        this.byteBody.putLong(lSrcNodeID);
        this.byteBody.position(4);
        this.byteBody.put((byte)nResult);
    }

    public int readbody() {
        this.byteBody.clear();
        this.Result = this.byteBody.get(0);
        this.flag = 0;
        return 0;
    }

    public int getResult() {
        return this.Result;
    }

    public void SetResult(int nResult) {
        this.Result = nResult;
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
    }

    public int write(Socket sc) throws IOException {
        int nSend;
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
         nSend = sendByte.array().length;
        this.flag = 1;
        return nSend;
    }

    public String toString() {
        return "SGIP_BindResp:seq3=" + super.getSeqno_3() + " Result=" + this.Result;
    }
}
