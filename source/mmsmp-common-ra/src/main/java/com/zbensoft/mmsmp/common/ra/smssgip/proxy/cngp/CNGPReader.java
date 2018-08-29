package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;

import java.net.Socket;

public class CNGPReader implements SMSReader {
    Socket sc;

    public CNGPReader(Socket sc) {
        this.sc = sc;
    }

    public Object read() throws Exception {
        return CNGP_Command.read(this.sc);
    }
}