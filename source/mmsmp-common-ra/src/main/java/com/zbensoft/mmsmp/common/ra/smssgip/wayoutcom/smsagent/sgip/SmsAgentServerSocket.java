package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket.SocketListen;

public class SmsAgentServerSocket extends SocketListen {
    public SmsAgentServerSocket(String localIP, int localPort) {
        super(localIP, localPort);
    }
}
