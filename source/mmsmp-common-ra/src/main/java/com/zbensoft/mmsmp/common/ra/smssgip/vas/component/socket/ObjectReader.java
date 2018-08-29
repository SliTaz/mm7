package com.zbensoft.mmsmp.common.ra.smssgip.vas.component.socket;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ObjectReader implements SMSReader {
    private Socket sc;
    private DataInputStream in;

    public ObjectReader(Socket sc) {
        this.sc = sc;
    }

    public Object read() throws Exception {
        if (this.sc != null) {
            ObjectInputStream in1 = new ObjectInputStream(this.sc.getInputStream());
            return in1.readObject();
        } else {
            return null;
        }
    }
}