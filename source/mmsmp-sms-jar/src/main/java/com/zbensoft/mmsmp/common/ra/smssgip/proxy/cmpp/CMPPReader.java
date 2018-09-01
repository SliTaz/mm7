package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;

import java.io.IOException;
import java.net.Socket;

public class CMPPReader implements SMSReader {
    Socket sc;

    public CMPPReader(Socket in) {
        this.sc = in;
    }

    public Object read() throws IOException, SMSException, Exception {
        return CMPP_Command.read(this.sc);
    }
}
