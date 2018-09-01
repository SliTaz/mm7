package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;

import java.io.IOException;
import java.net.Socket;

public class SM7PWriter implements SMSWriter {
    Socket sc;

    public SM7PWriter(Socket sc) {
        this.sc = sc;
    }

    public void write(Object message) throws IOException {
        SM7_Command cmd = (SM7_Command)message;
        cmd.write(this.sc);
    }
}