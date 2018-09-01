package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSLayer;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSListenSocketConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPReader;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPTransaction;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPWriter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Unbind;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class SMGPGatewayConnection extends SMSListenSocketConnection {
    private String ipaddr;
    private Map serconns;
    private Logger log = Logger.getLogger(SMGPGatewayConnection.class);

    public SMGPGatewayConnection(Parameter args) {
    }

    public SMGPGatewayConnection(Map serconns) {
        this.serconns = serconns;
    }

    public synchronized void attach(Parameter args, Socket socket) {
        this.init(args, socket);
        this.ipaddr = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
    }

    protected SMSWriter getWriter(Socket out) {
        return new SMGPWriter(out);
    }

    protected SMSReader getReader(Socket in) {
        return new SMGPReader(in);
    }

    public int getChildId(Object message) {
        SMGP_Command mes = (SMGP_Command)message;
        int sequenceId = mes.getSeqID();
        return mes.getCommandID() != 2 && mes.getCommandID() != 1 && mes.getCommandID() != 4 && mes.getCommandID() != 6 ? sequenceId : -1;
    }

    public SMSLayer createChild() {
        return new SMGPTransaction(this);
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
            SMGP_Unbind msg = new SMGP_Unbind();
            this.send(msg);
        } catch (Exception var2) {
            ;
        }

        super.close();
        this.serconns.remove(this.ipaddr + ":" + this.port);
    }
}
