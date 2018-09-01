package com.zbensoft.mmsmp.common.ra.smssgip.proxy.sgip;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSException;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SMSSeverThread;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm.SocketEventListener;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SGIPSMSProxy implements SocketEventListener {
    private SGIPConnection conn;
    private Map serconns;
    private Parameter args;
    private SMSSeverThread severThread;
    private static final Logger log = Logger.getLogger(SGIPSMSProxy.class);
    private boolean blockSend;

    public SGIPSMSProxy(Parameter args) {
        this.args = args;
        this.blockSend = args.get("block-send", true);
    }

    public synchronized boolean connect(String loginName, String loginPass) {
        boolean result = true;
        if (loginName != null) {
            this.args.set("login-name", loginName.trim());
        }

        if (loginPass != null) {
            this.args.set("login-pass", loginPass.trim());
        }

        this.conn = new SGIPConnection(this.args, true, (Map) null);
        this.conn.addEventListener(new SGIPEventListener(this, this.conn));
        this.conn.waitAvailable();
        if (!this.conn.available()) {
            result = false;
            throw new IllegalStateException(this.conn.getError());
        } else {
            return result;
        }
    }

    public synchronized void startService(String localhost, int localport) {
        if (this.severThread == null) {
            try {
                this.severThread = new SMSSeverThread(localhost, localport, this);
                this.severThread.beginListen();
            } catch (Exception var4) {
                ;
            }

        }
    }

    public synchronized void stopService() {
        if (this.severThread != null) {
            this.severThread.stopListen();
            if (this.serconns != null) {
                Iterator iterator = this.serconns.keySet().iterator();

                while (iterator.hasNext()) {
                    String addr = (String) iterator.next();
                    SGIPConnection conn = (SGIPConnection) this.serconns.get(addr);
                    conn.close();
                }

                this.serconns.clear();
            }

        }
    }

    public SGIP_Command send(SGIP_Command message) throws IOException, SMSException {
        if (message == null) {
            return null;
        } else {
            SGIPTransaction t = (SGIPTransaction) this.conn.createChild();

            try {
                t.send(message);
                if (this.blockSend) {
                    t.waitResponse();
                    SGIP_Command var4 = (SGIP_Command) t.getResponse();
                    return var4;
                }
            } finally {
                t.close();
            }

            return null;
        }
    }

    public void onTerminate() {
    }

    public SGIP_Command onDeliver(SGIP_Deliver msg) {
        try {
            SGIP_DeliverResp resp = new SGIP_DeliverResp(msg.getHead());
            resp.SetResult(0);
            return resp;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public SGIP_Command onReport(SGIP_Report msg) {
        try {
            SGIP_ReportResp resp = new SGIP_ReportResp(msg.getHead());
            resp.SetResult(0);
            return resp;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public SGIP_Command onUserReport(SGIP_UserReport msg) {
        try {
            SGIP_UserReportResp resp = new SGIP_UserReportResp(msg.getHead());
            resp.SetResult(0);
            return resp;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public SGIP_Command onSubmit(SGIP_Submit msg) {
        try {
            SGIP_SubmitResp resp = new SGIP_SubmitResp(msg.getHead());
            resp.SetResult(0);
            return resp;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public SGIP_Command onSubmitResp(SGIP_SubmitResp msg) {
        return null;
    }

    public void close() {
        this.conn.terminate();
    }

    public String getConnState() {
        return this.conn == null ? "还未建立连接" : this.conn.getError();
    }

    public synchronized void onConnect(Socket socket) {
        log.info("new connection coming " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
        String peerIP = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        if (this.serconns == null) {
            this.serconns = new HashMap();
        }

        SGIPConnection conn = new SGIPConnection(this.args, false, this.serconns);
        conn.addEventListener(new SGIPEventListener(this, conn));
        conn.attach(this.args, socket);
        this.serconns.put(peerIP + port, conn);
    }
}