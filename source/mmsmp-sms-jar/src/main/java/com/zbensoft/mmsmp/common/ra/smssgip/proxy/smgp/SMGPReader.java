package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;

import java.io.IOException;
import java.net.Socket;

public class SMGPReader implements SMSReader {
    private Socket sc;

    public SMGPReader(Socket in) {
        this.sc = in;
    }

    public Object read() throws IOException, SMSException, Exception {
        return SMGP_Command.read(this.sc);
    }
}
