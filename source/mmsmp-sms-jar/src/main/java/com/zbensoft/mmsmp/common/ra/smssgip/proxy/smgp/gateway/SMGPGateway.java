package com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSeverThread;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SocketEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPConnection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.smgp.SMGPTransaction;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.*;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SMGPGateway implements SocketEventListener {
    private int seq;
    private SMGPConnection conn;
    private SMSSeverThread severThread;
    private static final Logger log = Logger.getLogger(SMGPGateway.class);
    private HashMap serconns;
    private Parameter args;

    public SMGPGateway(Map args) {
        this(new Parameter(args));
    }

    public SMGPGateway(Parameter args) {
        this.seq = 0;
        this.args = args;
    }

    public SMGP_Command send(SMGP_Command message) throws Exception {
        if (message == null) {
            return null;
        } else {
            SMGPTransaction t = (SMGPTransaction)this.conn.createChild();

            SMGP_Command var6;
            try {
                t.send(message);
                t.waitResponse();
                SMGP_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public SMGP_Command onDeliver(SMGP_Deliver msg) {
        return new SMGP_DeliverResp(msg, 0);
    }

    public void close() {
        this.conn.close();
    }

    public SMGPConnection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }

    public SMGP_Command onSubmit(SMGP_Submit msg) {
        log.info(msg.getSrcTermID() + ":" + msg.getMsgContent());
        byte[] msgId = new byte[10];
        msgId[2] = 1;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        byte[] dateByte = TypeConvert.toBCD(Integer.parseInt(df.format(new Date())));

        for(int i = 0; i < 4; ++i) {
            msgId[2 + i] = dateByte[i];
        }

        TypeConvert.int2byte(msg.getSeqID(), msgId, 6);
        int congestionState = 0;
        return new SMGP_SubmitResp(msg, msgId, congestionState);
    }

    public SMGP_Command onLogin(SMGP_Bind msg) {
        log.info("receive a login package");
        return new SMGP_BindResp(msg, 0);
    }

    public void onConnect(Socket socket) {
        log.info("new connection coming " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
        String peerIP = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        if (this.serconns == null) {
            this.serconns = new HashMap();
        }

        SMGPGatewayConnection conn = new SMGPGatewayConnection(this.serconns);
        conn.addEventListener(new SMGPGatewayEventListener(this));
        conn.attach(this.args, socket);
        this.serconns.put(peerIP + ":" + port, conn);
    }

    public synchronized void startService(int localport) {
        if (this.severThread == null) {
            try {
                this.severThread = new SMSSeverThread(localport, this);
                this.severThread.beginListen();
            } catch (Exception var3) {
                var3.printStackTrace();
                System.exit(-1);
            }

        }
    }

    public static void main(String[] args) {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("SMGPConnect/*");
        SMGPGateway gw = new SMGPGateway(param);
        log.info("SMGP GateWay starting……");
        gw.startService(param.get("port", 8890));

        try {
            Thread.sleep(10000000L);
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        }

    }
}
