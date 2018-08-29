package com.zbensoft.mmsmp.common.ra.smssgip.proxy.cngp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Command;

import java.io.IOException;
import java.net.Socket;

public class CNGPWriter implements SMSWriter {
    Socket sc;

    public CNGPWriter(Socket sc) {
        this.sc = sc;
    }

    public void write(Object message) throws IOException {
        CNGP_Command cmd = (CNGP_Command)message;
        cmd.write(this.sc);
    }
}
