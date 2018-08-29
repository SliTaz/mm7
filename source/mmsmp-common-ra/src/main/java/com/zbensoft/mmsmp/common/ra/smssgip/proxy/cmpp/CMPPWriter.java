package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cmpp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Command;

import java.io.IOException;
import java.net.Socket;

public class CMPPWriter implements SMSWriter {
    Socket sc;

    public CMPPWriter(Socket out) {
        this.sc = out;
    }

    public void write(Object message) throws IOException {
        CMPP_Command cmd = (CMPP_Command)message;
        cmd.write(this.sc);
    }
}
