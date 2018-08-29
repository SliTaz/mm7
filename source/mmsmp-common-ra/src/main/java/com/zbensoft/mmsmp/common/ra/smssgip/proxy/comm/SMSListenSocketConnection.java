package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.Date;

public abstract class SMSListenSocketConnection extends SMSLayer {
    private static Logger log = Logger.getLogger(SMSListenSocketConnection.class);
    protected static String NOT_INIT;
    protected static String CONNECTING;
    protected static String RECONNECTING;
    protected static String CONNECTED;
    protected static String HEARTBEATING;
    protected static String RECEIVEING;
    protected static String CLOSEING;
    protected static String CLOSED;
    protected static String UNKNOWN_HOST;
    protected static String PORT_ERROR;
    protected static String CONNECT_REFUSE;
    protected static String NO_ROUTE_TO_HOST;
    protected static String RECEIVE_TIMEOUT;
    protected static String CLOSE_BY_PEER;
    protected static String RESET_BY_PEER;
    protected static String CONNECTION_CLOSED;
    protected static String COMMUNICATION_ERROR;
    protected static String CONNECT_ERROR;
    protected static String SEND_ERROR;
    protected static String RECEIVE_ERROR;
    protected static String CLOSE_ERROR;
    private String error;
    protected Date errorTime = new Date();
    protected String name;
    protected String host;
    protected int port = -1;
    protected String localHost;
    protected int localPort = -1;
    protected int heartbeatInterval;
    protected SMSReader in;
    protected SMSWriter out;
    protected int readTimeout;
    protected int reconnectInterval;
    protected Socket socket;
    protected WatchThread heartbeatThread;
    protected WatchThread receiveThread;
    protected int transactionTimeout;
    protected Resource resource;

    public SMSListenSocketConnection(Parameter args) {
        super((SMSLayer) null);
        this.init(args);
    }

    protected SMSListenSocketConnection() {
        super((SMSLayer) null);
    }

    protected void init(Parameter args) {
        this.resource = this.getResource();
        this.initResource();
        this.error = NOT_INIT;
        this.setAttributes(args);

        class ReceiveThread extends WatchThread {
            public void task() {
                try {
                    if (SMSListenSocketConnection.this.error == null) {
                        Object m = SMSListenSocketConnection.this.in.read();
                        if (m != null) {
                            SMSListenSocketConnection.this.onReceive(m);
                        } else {
                            SMSListenSocketConnection.this.error = "read a null message ";
                        }
                    } else {
                        if (SMSListenSocketConnection.this.error != SMSListenSocketConnection.NOT_INIT) {
                            try {
                                Thread.sleep((long) SMSListenSocketConnection.this.reconnectInterval);
                            } catch (InterruptedException var2) {
                                ;
                            }
                        }

                        SMSListenSocketConnection.this.connect();
                    }
                } catch (Exception var3) {
                    SMSListenSocketConnection.log.debug(var3.getMessage());
                    SMSListenSocketConnection.this.error = SMSListenSocketConnection.this.explain(var3);
                }

            }

            public ReceiveThread() {
                super(SMSListenSocketConnection.this.name + "_receive");
            }
        }

        this.receiveThread = new ReceiveThread();
        this.receiveThread.start();
    }

    protected void init(Parameter args, Socket socket) {
        this.resource = this.getResource();
        this.initResource();
        this.error = NOT_INIT;
        if (socket != null) {
            this.socket = socket;

            try {
                this.out = this.getWriter(this.socket);
                this.in = this.getReader(this.socket);
                this.setError((String) null);
            } catch (Exception var4) {
                this.setError(this.explain(var4));
                var4.printStackTrace();
                return;
            }

            if (args != null) {
                this.setAttributes1(args);
            }

            class ReceiveThread1 extends WatchThread {
                public void task() {
                    try {
                        if (SMSListenSocketConnection.this.error == null) {
                            Object m = SMSListenSocketConnection.this.in.read();
                            if (m != null) {
                                SMSListenSocketConnection.this.onReceive(m);
                            } else {
                                SMSListenSocketConnection.this.error = "read a null message";
                            }
                        } else {
                            SMSListenSocketConnection.this.terminate();
                        }
                    } catch (Exception var2) {
                        SMSListenSocketConnection.this.setError(SMSListenSocketConnection.this.explain(var2));
                        SMSListenSocketConnection.this.terminate();
                    }

                }

                public ReceiveThread1() {
                    super(SMSListenSocketConnection.this.name + "-receive");
                }
            }

            this.receiveThread = new ReceiveThread1();
            this.receiveThread.start();
        }

    }

    protected void onReadTimeOut() {
        throw new UnsupportedOperationException("Not implement");
    }

    public void setAttributes(Parameter args) {
        String oldHost = this.host;
        int oldport = this.port;
        this.host = args.get("host", (String) null);
        this.port = args.get("port", -1);
        this.localHost = args.get("local-host", (String) null);
        this.localPort = args.get("local-port", -1);
        this.name = args.get("name", (String) null);
        if (this.name == null) {
            this.name = "MT";
        }

        this.readTimeout = 1000 * args.get("read-timeout", this.readTimeout / 1000);
        if (this.socket != null) {
            try {
                this.socket.setSoTimeout(this.readTimeout);
            } catch (SocketException var5) {
                ;
            }
        }

        this.heartbeatInterval = 0;
        this.transactionTimeout = 1000 * args.get("transaction-timeout", -1);
        if (this.error == null && this.host != null && this.port != -1 && (!this.host.equals(oldHost) || this.port != oldport)) {
            this.setError(this.resource.get("comm/need-reconnect"));
            this.receiveThread.interrupt();
        }

    }

    public void setAttributes1(Parameter args) {
        this.host = args.get("host", (String) null);
        this.port = args.get("port", -1);
        this.localHost = args.get("local-host", (String) null);
        this.localPort = args.get("local-port", -1);
        this.name = args.get("name", (String) null);
        this.name = "MO";
        this.readTimeout = 1000 * args.get("read-timeout", this.readTimeout / 1000);
        if (this.socket != null) {
            try {
                this.socket.setSoTimeout(this.readTimeout);
            } catch (SocketException var3) {
                ;
            }
        }

        this.heartbeatInterval = 0;
        this.transactionTimeout = 1000 * args.get("transaction-timeout", -1);
    }

    public void send(Object message) throws SMSException {
        if (this.error != null) {
            throw new SMSException(SEND_ERROR + this.getError());
        } else {
            try {
                this.out.write(message);
            } catch (Exception var3) {
                this.setError(SEND_ERROR + this.explain(var3));
                System.out.println(var3);
            }

        }
    }

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int getReconnectInterval() {
        return this.reconnectInterval / 1000;
    }

    public int getReadTimeout() {
        return this.readTimeout / 1000;
    }

    public boolean available() {
        return this.error == null;
    }

    public String getError() {
        return this.error;
    }

    public Date getErrorTime() {
        return this.errorTime;
    }

    public synchronized void close() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.in = null;
                this.out = null;
                this.socket = null;
            }
        } catch (Exception var2) {
            ;
        }

        this.setError(NOT_INIT);
    }

    public synchronized void terminate() {
        this.close();
        if (this.heartbeatThread != null) {
            this.heartbeatThread.kill();
        }

        if (this.receiveThread != null) {
            this.receiveThread.kill();
        }

    }

    protected synchronized void connect() {
        if (this.error == NOT_INIT) {
            this.error = CONNECTING;
        } else if (this.error == null) {
            this.error = RECONNECTING;
        }

        this.errorTime = new Date();
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException var5) {
                ;
            }
        }

        try {
            if (this.port > 0 && this.port <= 65535) {
                if (this.localPort >= -1 && this.localPort <= 65535) {
                    if (this.localHost == null) {
                        this.socket = new Socket(this.host, this.port);
                    } else {
                        boolean isConnected = false;
                        InetAddress localAddr = InetAddress.getByName(this.localHost);
                        if (this.localPort != -1) {
                            this.socket = new Socket(this.host, this.port, localAddr, this.localPort);
                        } else {
                            for (int p = (int) (Math.random() * 64500.0D); p < 903000; p += 13) {
                                try {
                                    this.socket = new Socket(this.host, this.port, localAddr, 1025 + p % 'ﯴ');
                                    isConnected = true;
                                    break;
                                } catch (IOException var6) {
                                    ;
                                } catch (SecurityException var7) {
                                    ;
                                }
                            }

                            if (!isConnected) {
                                throw new SocketException("Can not find an avaliable local port");
                            }
                        }
                    }

                    this.socket.setSoTimeout(this.readTimeout);
                    this.out = this.getWriter(this.socket);
                    this.in = this.getReader(this.socket);
                    this.setError((String) null);
                } else {
                    this.setError(PORT_ERROR + "local-port:" + this.localPort);
                }
            } else {
                this.setError(PORT_ERROR + "port:" + this.port);
            }
        } catch (IOException var8) {
            this.setError(String.valueOf(CONNECT_ERROR) + this.explain(var8));
        }
    }

    protected void setError(String desc) {
        if ((this.error != null || desc != null) && (desc == null || !desc.equals(this.error))) {
            this.error = desc;
            this.errorTime = new Date();
            if (desc == null) {
                desc = CONNECTED;
            }

        }
    }

    protected abstract SMSWriter getWriter(Socket var1);

    protected abstract SMSReader getReader(Socket var1);

    protected abstract Resource getResource();

    protected void heartbeat() throws Exception, Exception {
    }

    public void initResource() {
        NOT_INIT = this.resource.get("comm/not-init");
        CONNECTING = this.resource.get("comm/connecting");
        RECONNECTING = this.resource.get("comm/reconnecting");
        CONNECTED = this.resource.get("comm/connected");
        HEARTBEATING = this.resource.get("comm/heartbeating");
        RECEIVEING = this.resource.get("comm/receiveing");
        CLOSEING = this.resource.get("comm/closeing");
        CLOSED = this.resource.get("comm/closed");
        UNKNOWN_HOST = this.resource.get("comm/unknown-host");
        PORT_ERROR = this.resource.get("comm/port-error");
        CONNECT_REFUSE = this.resource.get("comm/connect-refused");
        NO_ROUTE_TO_HOST = this.resource.get("comm/no-route");
        RECEIVE_TIMEOUT = this.resource.get("comm/receive-timeout");
        CLOSE_BY_PEER = this.resource.get("comm/close-by-peer");
        RESET_BY_PEER = this.resource.get("comm/reset-by-peer");
        CONNECTION_CLOSED = this.resource.get("comm/connection-closed");
        COMMUNICATION_ERROR = this.resource.get("comm/communication-error");
        CONNECT_ERROR = this.resource.get("comm/connect-error");
        SEND_ERROR = this.resource.get("comm/send-error");
        RECEIVE_ERROR = this.resource.get("comm/receive-error");
        CLOSE_ERROR = this.resource.get("comm/close-error");
    }

    protected String explain(Exception ex) {
        String msg = ex.getMessage();
        if (msg == null) {
            msg = "";
        }

        if (ex instanceof SMSException) {
            return ex.getMessage();
        } else if (ex instanceof EOFException) {
            return CLOSE_BY_PEER;
        } else if (msg.indexOf("Connection reset by peer") != -1) {
            return RESET_BY_PEER;
        } else if (msg.indexOf("SocketTimeoutException") != -1) {
            return RECEIVE_TIMEOUT;
        } else if (ex instanceof SocketTimeoutException) {
            return RECEIVE_TIMEOUT;
        } else if (ex instanceof NoRouteToHostException) {
            return NO_ROUTE_TO_HOST;
        } else if (ex instanceof ConnectException) {
            return CONNECT_REFUSE;
        } else if (ex instanceof UnknownHostException) {
            return UNKNOWN_HOST;
        } else {
            return msg.indexOf("errno: 128") != -1 ? NO_ROUTE_TO_HOST : ex.toString();
        }
    }

}
