package com.zbensoft.mmsmp.common.ra.smssgip.vas.component.socket;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;

import java.net.Socket;

public class ClientConnection extends SMSSocketConnection {
    public ClientConnection() {
    }

    protected SMSReader getReader(Socket inputstream) {
        return new ObjectReader(inputstream);
    }

    protected Resource getResource() {
        return null;
    }

    protected SMSWriter getWriter(Socket outputstream) {
        return new ObjectWriter(outputstream);
    }
}
