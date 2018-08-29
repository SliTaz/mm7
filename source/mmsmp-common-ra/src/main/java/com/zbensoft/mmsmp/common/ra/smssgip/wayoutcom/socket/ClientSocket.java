package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.socket;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common.SmsHandler;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.produce_consume.Channel;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Command;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Head;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_SubmitResp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ClientSocket extends SmsHandler {
    protected Socket sc = null;
    protected String host;
    protected int port;
    protected Thread SendThread = null;
    protected InetSocketAddress isa = null;
    private int countRecv = 0;
    protected String name;
    private Channel channel;
    private int CHANNEL_SIZE = 10;
    protected ClientSocket.ActiveTestThread activeTestThread;
    protected Logger LogHandler = Logger.getLogger(ClientSocket.class);

    public ClientSocket() {
        this.name = this.getClientName();
        this.channel = new Channel(this.CHANNEL_SIZE);
    }

    public ClientSocket(String host, int port) {
        this.host = host;
        this.port = port;
        this.isa = new InetSocketAddress(host, port);
        this.name = this.getClientName();
        this.channel = new Channel(this.CHANNEL_SIZE);
    }

    protected String getClientName() {
        return "ClientSocket";
    }

    private Socket createChannel() {
        Socket sc = null;

        try {
            sc = new Socket();

            while(sc == null) {
                ;
            }

            while(!sc.isConnected()) {
                sc.connect(this.isa);
            }

            sc.setKeepAlive(true);
            sc.setSoTimeout(20000);
            Thread.sleep(100L);
            this.LogHandler.info("ClientSocket, connected to the server successfully " + this.name);
            return sc;
        } catch (IOException var3) {
            var3.printStackTrace();
            this.LogHandler.error("ClientSocket,There is an IOException in createChannel " + this.name);
            return null;
        } catch (Exception var4) {
            var4.printStackTrace();
            this.LogHandler.error("ClientSocket,There is an Exception in createChannel " + this.name);
            return null;
        }
    }

    public final void send(Object objSMS) {
        try {
            this.thisLayerSend(objSMS);
        } catch (InterruptedException var3) {
            ;
        }

    }

    public final void start(boolean isLongConn) {
        if (isLongConn) {
            this.sc = this.refreshSocket((Socket)null);
            this.activeTestThread = new ClientSocket.ActiveTestThread();
            this.activeTestThread.start();
            this.sendThreadL();
        } else {
            this.SendThread = new Thread(new Runnable() {
                public void run() {
                    ClientSocket.this.sendThread();
                }
            });
            this.SendThread.setName("SendThread");
            this.SendThread.start();
            this.LogHandler.info("ClientSocket start successfully in " + this.name);
        }

    }

    private void thisLayerSend(Object objSMS) throws InterruptedException {
        this.channel.put(objSMS);
    }

    private Object[] checkResponse() throws InterruptedException {
        Object[] tmp = (Object[])null;
        tmp = this.channel.removeAll();
        this.LogHandler.debug("在ClientSocket的发送队列里面有: " + tmp.length + " IN " + this.name);
        return tmp;
    }

    protected int sendtoSMS(Object objSMS, Socket sc) {
        boolean var3 = true;

        try {
            if (sc == null) {
                return -1;
            }

            OutputStream writer = sc.getOutputStream();
            ByteBuffer bf = ByteBuffer.wrap(((ByteBuffer)objSMS).array());
            byte[] ar = bf.array();
            byte[] head = new byte[20];
            byte[] body = new byte[ar.length - 20];
            System.arraycopy(ar, 0, head, 0, 20);
            System.arraycopy(ar, 20, body, 0, ar.length - 20);
            SGIP_Head shead = new SGIP_Head(head);
            SGIP_Command cmd = new SGIP_Command(shead);
            cmd.setBodyByte(ByteBuffer.wrap(body));
            SGIP_Submit submit = new SGIP_Submit(cmd);
            submit.readbody();
            submit.write(sc);
        } catch (IOException var12) {
            this.LogHandler.error("ClientSocket,There is an IOException in sendtoSMS " + this.name);
            var12.printStackTrace();
            return -1;
        }

        return this.readResp(sc) ? 1 : -1;
    }

    protected int sendtoSMSL(Object objSMS, Socket sc) {
        return this.sendtoSMS(objSMS, sc);
    }

    private boolean readResp(Socket sc) {
        SGIP_Command sgip_Cmd = null;
        SGIP_Command sgip_temp = new SGIP_Command();

        try {
            sgip_Cmd = sgip_temp.read(sc);
            this.LogHandler.debug(sgip_Cmd.toString());
        } catch (Exception var5) {
            this.LogHandler.error("In " + this.name + ":" + var5);
            return false;
        }

        if (sgip_Cmd == null) {
            this.LogHandler.error("In " + this.name + ": Get Null from socket");
            return false;
        } else {
            switch(sgip_Cmd.getCommandID()) {
                case -2147483645:
                    SGIP_SubmitResp submitresp = (SGIP_SubmitResp)sgip_Cmd;
                    if (submitresp.getResult() == 0) {
                        return true;
                    }

                    this.LogHandler.error("resp error code:" + submitresp.getResult() + "-----" + this.name);
                    return false;
                default:
                    this.LogHandler.error("ClientSocket,Unsupported respID " + Integer.toHexString(sgip_Cmd.getCommandID()) + "-----" + this.name);
                    return false;
            }
        }
    }

    private void sendThread() {
        Object[] tmp_respQ = (Object[])null;

        while(true) {
            try {
                tmp_respQ = this.checkResponse();
            } catch (InterruptedException var3) {
                this.LogHandler.error(var3 + "---" + this.name);
            }

            this.sendObj(tmp_respQ);
        }
    }

    protected void recvResp() {
    }

    private void sendThreadL() {
        this.recvResp();
        (new Thread(new Runnable() {
            public void run() {
                Object[] tmp_respQ = (Object[])null;

                while(true) {
                    try {
                        tmp_respQ = ClientSocket.this.checkResponse();
                    } catch (InterruptedException var3) {
                        ClientSocket.this.LogHandler.error("ClientSocket,There is an InterruptedException in checkResponse " + ClientSocket.this.name);
                    }

                    ClientSocket.this.sendObj(tmp_respQ, ClientSocket.this.sc);
                }
            }
        })).start();
    }

    protected void sendObj(Object[] tmp_respQ) {
        Socket sc = this.refreshSocket((Socket)null);
        this.LogHandler.debug("There are " + tmp_respQ.length + " messages in buffer. " + this.name);

        for(int i = 0; i < tmp_respQ.length; ++i) {
            Object smsSend = tmp_respQ[i];
            int nRet = -1;

            int sendcount;
            for(sendcount = 1; nRet <= 0 && sendcount <= 3; ++sendcount) {
                nRet = this.sendtoSMS(smsSend, sc);
                if (nRet > 0) {
                    break;
                }

                sc = this.refreshSocket(sc);
            }

            if (nRet <= 0 && sendcount >= 3) {
                this.LogHandler.error("ClientSocket,Server is unvalaible, Send failed" + this.name + sc.getRemoteSocketAddress());
            } else {
                this.LogHandler.debug("spclient发送成功,发送次数:" + sendcount + "次");
            }
        }

        try {
            while(sc != null && !sc.isClosed()) {
                sc.close();
            }
        } catch (Exception var7) {
            this.LogHandler.error("ClientSocket,There is an Exception in socket.close() " + this.name);
        }

    }

    protected void sendObj(Object[] tmp_respQ, Socket sc) {
        this.LogHandler.debug("ClientSocket,In buffer there are messages：" + tmp_respQ.length + this.name);

        for(int i = 0; i < tmp_respQ.length; ++i) {
            Object smsSend = tmp_respQ[i];
            int nRet = -1;
            int sendcount = 0;

            while(nRet <= 0) {
                try {
                    if (this.activeTestThread.canRun()) {
                        nRet = this.sendtoSMSL(smsSend, sc);
                    }

                    if (nRet > 0) {
                        this.LogHandler.debug("ClientSocket, Ready for next submit " + this.name);
                    } else {
                        this.LogHandler.error("ClientSocket Send error times:" + (sendcount + 1) + this.name);
                        this.activeTestThread.setActiveFalse();
                    }

                    ++sendcount;
                } catch (Exception var8) {
                    ;
                }
            }

            if (nRet <= 0) {
                this.LogHandler.error("ClientSocket,Server is unvalaible, Send failed" + this.name);
            } else {
                this.LogHandler.debug("ClientSocket send needs times:" + sendcount + this.name);
            }
        }

    }

    protected Socket refreshSocket(Socket sc) {
        while(true) {
            try {
                if (sc != null && !sc.isClosed()) {
                    sc.close();
                    continue;
                }
            } catch (Exception var10) {
                var10.printStackTrace();
                this.LogHandler.error("ClientSocket,There is an Exception in refreshSocket socket.close() " + this.name);
            } finally {
                sc = null;
            }

            int retries = 0;

            while(true) {
                sc = this.createChannel();
                if (sc != null) {
                    if (this.Login(sc)) {
                        this.LogHandler.info("ClientSocket,Login success " + this.name + sc.getPort() + sc.getRemoteSocketAddress());
                        return sc;
                    }

                    this.LogHandler.info("ClientSocket,Login failed " + this.name);

                    try {
                        Thread.sleep(10000L);
                    } catch (Exception var8) {
                        ;
                    }

                    return null;
                }

                ++retries;

                try {
                    switch(retries) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            Thread.sleep(1000L * (long)Math.pow((double)retries, (double)retries));
                            break;
                        default:
                            Thread.sleep(600000L);
                    }
                } catch (Exception var9) {
                    ;
                }
            }
        }
    }

    protected boolean Login(Socket sc) {
        return true;
    }

    protected void sendActiveTest() throws Exception {
    }

    public class ActiveTestThread extends Thread {
        private volatile boolean isActive = false;

        public ActiveTestThread() {
            (new Timer()).schedule(new TimerTask() {
                public void run() {
                    try {
                        if (ActiveTestThread.this.canRun()) {
                            ClientSocket.this.sendActiveTest();
                        }
                    } catch (IOException var2) {
                        ActiveTestThread.this.setActiveFalse();
                    } catch (InterruptedException var3) {
                        return;
                    } catch (Exception var4) {
                        ;
                    }

                }
            }, 0L, 60000L);
        }

        public synchronized void setActiveFalse() {
            this.isActive = false;
            this.notifyAll();
        }

        public synchronized boolean canRun() throws InterruptedException {
            while(!this.isActive) {
                this.wait();
            }

            return this.isActive;
        }

        private synchronized void test() throws InterruptedException {
            while(this.isActive) {
                this.wait();
            }

            ClientSocket.this.sc = ClientSocket.this.refreshSocket(ClientSocket.this.sc);
            this.isActive = true;
            this.notifyAll();
        }

        public void run() {
            ClientSocket.this.LogHandler.info("ActiveTestThread run......");

            try {
                while(true) {
                    this.test();
                }
            } catch (Exception var2) {
                var2.printStackTrace();
                ClientSocket.this.LogHandler.error("ActiveTestThread exit......");
            }
        }
    }
}
