package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;

import java.net.Socket;

public class SM7Reader implements SMSReader {
    Socket sc;

    public SM7Reader(Socket sc) {
        this.sc = sc;
    }

    public Object read() throws Exception {
        return SM7_Command.read(this.sc);
    }
}
