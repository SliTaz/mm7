package com.zbensoft.mmsmp.common.ra.smssgip.vas.component.socket;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ObjectWriter implements SMSWriter {
    Socket sc;
    DataOutputStream out;

    public ObjectWriter(Socket sc) {
        this.sc = sc;
    }

    public void write(Object message) throws IOException {
        if (this.sc != null && message instanceof Serializable) {
            ObjectOutputStream out = new ObjectOutputStream(this.sc.getOutputStream());
            out.writeObject(message);
        }

    }
}
