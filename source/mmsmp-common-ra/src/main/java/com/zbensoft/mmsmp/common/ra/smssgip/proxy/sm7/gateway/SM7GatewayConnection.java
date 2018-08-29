package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7PWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7Reader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7Transaction;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.SM7_Unbind;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class SM7GatewayConnection extends SMSSocketConnection {
    private String ipaddr;
    private Map serconns;
    private Logger log = Logger.getLogger(SM7GatewayConnection.class);

    public SM7GatewayConnection(Parameter args) {
    }

    public SM7GatewayConnection(Map serconns) {
        this.serconns = serconns;
    }

    public synchronized void attach(Parameter args, Socket socket) {
        this.init(args, socket);
        this.ipaddr = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
    }

    protected SMSWriter getWriter(Socket out) {
        return new SM7PWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new SM7Reader(in);
    }

    public int getChildId(Object message) {
        SM7_Command mes = (SM7_Command)message;
        int sequenceId = mes.getSeqID();
        return mes.getCommandID() != 2 && mes.getCommandID() != 1 && mes.getCommandID() != 4 && mes.getCommandID() != 6 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new SM7Transaction(this);
    }

    public int getTransactionTimeout() {
        return super.transactionTimeout;
    }

    public Resource getResource() {
        try {
            Resource resource = new Resource(this.getClass(), "resource");
            return resource;
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public synchronized void waitAvailable() {
        try {
            if (this.getError() == NOT_INIT) {
                this.wait((long)super.transactionTimeout);
            }
        } catch (InterruptedException var2) {
            ;
        }

    }

    public void close() {
        try {
            this.log.info("close connection. IP address " + this.host + ":" + this.port);
            SM7_Unbind msg = new SM7_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
        this.serconns.remove(this.ipaddr + this.port);
    }
}

