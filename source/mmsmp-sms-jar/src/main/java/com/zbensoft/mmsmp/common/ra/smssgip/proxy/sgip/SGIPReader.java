package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIPReader implements SMSReader {
    private Socket sc;

    public SGIPReader(Socket sc) {
        this.sc = sc;
    }

    public Object read() throws IOException, SMSException {
        InputStream in = this.sc.getInputStream();
        SGIP_Head sgipHead = new SGIP_Head();
        ByteBuffer recByte = ByteBuffer.allocate(20);

        int nrec;
        int len;
        for(nrec = 0; nrec < 20; nrec += len) {
            len = in.read(recByte.array(), nrec, recByte.array().length);
            if (len == -1) {
                return null;
            }
        }

        if (nrec == 20) {
            sgipHead.SetTotalLength(recByte.getInt(0));
            sgipHead.SetCommandID(recByte.getInt(4));
            sgipHead.SetSeq_1((long)recByte.getInt(8));
            sgipHead.SetSeq_2(recByte.getInt(12));
            sgipHead.SetSeq_3(recByte.getInt(16));
            len = sgipHead.GetTotalLength() - 20;
            if (len < 0) {
                throw new SMSException("CRBTCommand, readBody, read head error!");
            } else {
                byte[] body = new byte[len];
                in.read(body);
                ByteBuffer bodyBuffer = ByteBuffer.allocate(len);
                bodyBuffer.put(body);
                SGIP_Command result = new SGIP_Command(sgipHead);
                ((SGIP_Command)result).setBodyByte(bodyBuffer);
                switch(sgipHead.GetCommandID()) {
                    case -2147483647:
                        result = new SGIP_BindResp((SGIP_Command)result);
                        break;
                    case -2147483646:
                        result = new SGIP_UnbindResp((SGIP_Command)result);
                        break;
                    case -2147483645:
                        result = new SGIP_SubmitResp((SGIP_Command)result);
                        ((SGIP_Command)result).readbody();
                        break;
                    case -2147483644:
                        result = new SGIP_DeliverResp((SGIP_Command)result);
                        break;
                    case -2147483643:
                        result = new SGIP_ReportResp((SGIP_Command)result);
                        break;
                    case -2147479552:
                        result = new SGIP_TraceResp((SGIP_Command)result);
                        break;
                    case 1:
                        result = new SGIP_Bind((SGIP_Command)result);
                        break;
                    case 2:
                        result = new SGIP_Unbind((SGIP_Command)result);
                        break;
                    case 3:
                        result = new SGIP_Submit((SGIP_Command)result);
                        ((SGIP_Command)result).readbody();
                        break;
                    case 4:
                        result = new SGIP_Deliver((SGIP_Command)result);
                        ((SGIP_Command)result).readbody();
                        break;
                    case 5:
                        result = new SGIP_Report((SGIP_Command)result);
                        break;
                    case 17:
                        result = new SGIP_UserReport((SGIP_Command)result);
                }

                if (result != null) {
                    ((SGIP_Command)result).readbody();
                }

                return result;
            }
        } else {
            return null;
        }
    }
}

