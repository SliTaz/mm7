package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;

import java.io.IOException;
import java.net.Socket;

public class SMGPWriter implements SMSWriter {
    private Socket sc;

    public SMGPWriter(Socket out) {
        this.sc = out;
    }

    public void write(Object message) throws IOException {
        SMGP_Command cmd = (SMGP_Command)message;
        cmd.write(this.sc);
    }
}
