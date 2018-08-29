package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.gateway;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSeverThread;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SocketEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7Connection;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.SM7Transaction;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.sm7.sm7api.*;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.TypeConvert;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SM7Gateway implements SocketEventListener {
    private int seq;
    private SM7Connection conn;
    private SMSSeverThread severThread;
    private static final Logger log = Logger.getLogger(SM7Gateway.class);
    private HashMap serconns;
    private Parameter args;

    public SM7Gateway(Map args) {
        this(new Parameter(args));
    }

    public SM7Gateway(Parameter args) {
        this.seq = 0;
        this.args = args;
    }

    public SM7_Command send(SM7_Command message) throws Exception {
        if (message == null) {
            return null;
        } else if (this.conn == null) {
            log.info("还未SP连接到该网关");
            return null;
        } else {
            SM7Transaction t = (SM7Transaction) this.conn.createChild();

            SM7_Command var6;
            try {
                t.send(message);
                t.waitResponse();
                SM7_Command rsp = t.getResponse();
                var6 = rsp;
            } finally {
                t.close();
            }

            return var6;
        }
    }

    public void onTerminate() {
    }

    public SM7_Command onDeliver(SM7_Deliver msg) {
        return new SM7_DeliverResp(msg, 0);
    }

    public void close() {
        this.conn.close();
    }

    public SM7Connection getConn() {
        return this.conn;
    }

    public String getConnState() {
        return this.conn.getError();
    }

    public SM7_Command onSubmit(SM7_Submit msg) {
        log.info(msg.getSrcTermId() + ":" + msg.getMsgContent());
        byte[] msgId = new byte[10];
        msgId[2] = 1;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        byte[] dateByte = TypeConvert.toBCD(Integer.parseInt(df.format(new Date())));

        for (int i = 0; i < 4; ++i) {
            msgId[2 + i] = dateByte[i];
        }

        TypeConvert.int2byte(msg.getSeqID(), msgId, 6);
        int congestionState = 0;
        return new SM7_SubmitResp(msg, msgId, congestionState);
    }

    public SM7_Command onLogin(SM7_Bind msg) {
        log.info("receive a login package");
        return new SM7_BindResp(msg, 0);
    }

    public void onConnect(Socket socket) {
        log.info("new connection coming " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
        String peerIP = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        if (this.serconns == null) {
            this.serconns = new HashMap();
        }

        SM7GatewayConnection conn = new SM7GatewayConnection(this.serconns);
        conn.addEventListener(new SM7GatewayEventListener(this));
        conn.attach(this.args, socket);
        this.serconns.put(peerIP + port, conn);
    }

    public synchronized void startService(int localport) {
        try {
            if (this.severThread == null) {
                this.severThread = new SMSSeverThread(localport, this);
            }

            this.severThread.beginListen();
        } catch (Exception var3) {
            var3.printStackTrace();
            System.exit(-1);
        }

    }

    public synchronized void stopService() {
        try {
            if (this.severThread != null) {
                this.severThread.stopListen();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(-1);
        }

    }

    public static void main(String[] args) {
        Resource resource = new Resource("app");
        Parameter param = resource.getParameter("SM7Connect/*");
        SM7Gateway gw = new SM7Gateway(param);
        log.info("SM7 GateWay starting……");
        gw.startService(param.get("port", 9890));
    }
}
