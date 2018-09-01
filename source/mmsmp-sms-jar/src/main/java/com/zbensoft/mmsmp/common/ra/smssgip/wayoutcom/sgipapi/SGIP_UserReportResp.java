package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIP_UserReportResp extends SGIP_Command {
    private static final int CommandLength = 9;
    private static final int CommandID = -2147483631;
    int Result;

    public SGIP_UserReportResp(long lSrcNodeID, int nResult) {
        super(lSrcNodeID, 9, -2147483631);
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
        this.Result = nResult;
    }

    public SGIP_UserReportResp(SGIP_Head sgiphead) {
        super(sgiphead, 9, -2147483631);
    }

    public SGIP_UserReportResp(int nResult) {
        super(9, -2147483631);
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
        this.Result = nResult;
    }

    public void SetResult(int nResult) {
        this.byteBody.position(0);
        this.byteBody.put((byte)nResult);
        this.Result = nResult;
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
}
