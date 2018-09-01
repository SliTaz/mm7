package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SGIPWriter implements SMSWriter {
    private Socket sc;
    private Logger log = Logger.getLogger(SGIPWriter.class);

    public SGIPWriter(Socket sc) {
        this.sc = sc;
    }

    public void write(Object message) throws IOException {
        OutputStream out = this.sc.getOutputStream();
        SGIP_Command cmd = (SGIP_Command)message;
        ByteBuffer buffer = ByteBuffer.allocate(cmd.getTotalLength());
        buffer.put(cmd.getHead().GetHeadByte().array());
        buffer.position(20);
        buffer.put(cmd.getBodyByte().array());
        out.write(buffer.array());
    }
}
