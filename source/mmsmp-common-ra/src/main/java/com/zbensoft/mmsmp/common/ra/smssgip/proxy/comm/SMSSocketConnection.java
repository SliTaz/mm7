package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Parameter;
import com.zbensoft.mmsmp.common.ra.smssgip.proxy.util.Resource;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SMSSocketConnection extends SMSLayer {
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
    protected static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    protected int readTimeout;
    protected int reconnectInterval;
    protected Socket socket;
    protected WatchThread heartbeatThread;
    protected WatchThread receiveThread;
    protected int transactionTimeout;
    protected Resource resource;

    public SMSSocketConnection(Parameter args) {
        super((SMSLayer)null);
        this.init(args);
    }

    protected SMSSocketConnection() {
        super((SMSLayer)null);
    }

    protected void init(Parameter args) {
        this.resource = this.getResource();
        this.initResource();
        this.error = NOT_INIT;
        this.setAttributes(args);
        if (this.heartbeatInterval > 0) {
            class HeartbeatThread extends WatchThread {
                public void task() {
                    try {
                        Thread.sleep((long)SMSSocketConnection.this.heartbeatInterval);
                    } catch (InterruptedException var3) {
                        ;
                    }

                    if (SMSSocketConnection.this.error == null && SMSSocketConnection.this.out != null) {
                        try {
                            SMSSocketConnection.this.heartbeat();
                        } catch (Exception var2) {
                            SMSSocketConnection.this.setError(SMSSocketConnection.SEND_ERROR + SMSSocketConnection.this.explain(var2));
                        }
                    }

                }

                public HeartbeatThread() {
                    super(SMSSocketConnection.this.name + "-heartbeat");
                    this.setState1(SMSSocketConnection.HEARTBEATING);
                }
            }

            this.heartbeatThread = new HeartbeatThread();
            this.heartbeatThread.start();
        }

        class ReceiveThread extends WatchThread {
            public void task() {
                try {
                    if (SMSSocketConnection.this.error == null) {
                        Object m = SMSSocketConnection.this.in.read();
                        if (m != null) {
                            SMSSocketConnection.this.onReceive(m);
                        }
                    } else {
                        if (SMSSocketConnection.this.error != SMSSocketConnection.NOT_INIT) {
                            try {
                                Thread.sleep((long)SMSSocketConnection.this.reconnectInterval);
                            } catch (InterruptedException var2) {
                                ;
                            }
                        }

                        SMSSocketConnection.this.connect();
                    }
                } catch (IOException var3) {
                    SMSSocketConnection.this.setError(SMSSocketConnection.this.explain(var3));
                } catch (Exception var4) {
                    SMSSocketConnection.this.setError(SMSSocketConnection.this.explain(var4));
                    var4.printStackTrace();
                }

            }

            public ReceiveThread() {
                super(SMSSocketConnection.this.name + "-receive");
            }
        }

        this.receiveThread = new ReceiveThread();
        this.receiveThread.start();
    }

    protected void init(Parameter args, Socket socket) {
        this.resource = this.getResource();
        this.initResource();
        this.error = NOT_INIT;
        this.setAttributes(args, socket);

        class ReceiveThread1 extends WatchThread {
            public void task() {
                try {
                    if (SMSSocketConnection.this.error == null) {
                        Object m = SMSSocketConnection.this.in.read();
                        if (m != null) {
                            SMSSocketConnection.this.onReceive(m);
                        }
                    } else {
                        SMSSocketConnection.this.close();
                    }
                } catch (IOException var2) {
                    ;
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

            }

            public ReceiveThread1() {
                super(SMSSocketConnection.this.name + "-receive");
            }
        }

        this.receiveThread = new ReceiveThread1();
        this.receiveThread.start();
    }

    public void setAttributes(Parameter args) {
        String oldHost = this.host;
        int oldPort = this.port;
        String oldLocalHost = this.localHost;
        int oldLocalPort = this.localPort;
        this.host = args.get("host", (String)null);
        this.port = args.get("port", -1);
        this.localHost = args.get("local-host", (String)null);
        this.localPort = args.get("local-port", -1);
        this.name = args.get("name", (String)null);
        if (this.name == null) {
            this.name = this.host + ":" + this.port;
        }

        this.readTimeout = 1000 * args.get("read-timeout", this.readTimeout / 1000);
        if (this.socket != null) {
            try {
                this.socket.setSoTimeout(this.readTimeout);
            } catch (SocketException var7) {
                ;
            }
        }

        this.reconnectInterval = 1000 * args.get("reconnect-interval", -1);
        this.heartbeatInterval = 1000 * args.get("heartbeat-interval", -1);
        this.transactionTimeout = 1000 * args.get("transaction-timeout", -1);
        if (this.error == null && this.host != null && this.port != -1 && (!this.host.equals(oldHost) || this.port != oldPort)) {
            this.setError(this.resource.get("comm/need-reconnect"));
            this.receiveThread.interrupt();
        }

    }

    public void setAttributes(Parameter args, Socket socket) {
        this.readTimeout = 1000 * args.get("read-timeout", this.readTimeout / 1000);
        this.socket = socket;
        if (socket != null) {
            try {
                socket.setSoTimeout(this.readTimeout);
                this.out = this.getWriter(socket);
                this.in = this.getReader(socket);
                this.setError((String)null);
            } catch (SocketException var4) {
                ;
            }
        }

        this.reconnectInterval = 1000 * args.get("reconnect-interval", -1);
        this.heartbeatInterval = 1000 * args.get("heartbeat-interval", -1);
        this.transactionTimeout = 1000 * args.get("transaction-timeout", -1);
    }

    public void send(Object message) throws SMSException {
        if (this.error != null) {
            throw new SMSException(SEND_ERROR + this.getError());
        } else {
            try {
                this.out.write(message);
                this.fireEvent(new Event(8, this, message));
            } catch (Exception var3) {
                this.fireEvent(new Event(16, this, message));
                this.setError(SEND_ERROR + this.explain(var3));
                throw new SMSException(var3.getMessage());
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

    public String toString() {
        return "SMSSocketConnection:" + this.name + '(' + this.host + ':' + this.port + ')';
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

            if (this.heartbeatThread != null) {
                this.heartbeatThread.kill();
            }

            if (this.heartbeatThread != null) {
                this.receiveThread.kill();
            }
        } catch (Exception var2) {
            ;
        }

        this.setError(NOT_INIT);
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
                            for(int p = (int)(Math.random() * 64500.0D); p < 903000; p += 13) {
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
                    this.setError((String)null);
                } else {
                    this.setError(PORT_ERROR + "local-port:" + this.localPort);
                }
            } else {
                this.setError(PORT_ERROR + "port:" + this.port);
            }
        } catch (Exception var8) {
            this.setError(CONNECT_ERROR + this.explain(var8));
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

    protected void heartbeat() throws IOException, Exception {
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
        } else if (ex instanceof NoRouteToHostException) {
            return NO_ROUTE_TO_HOST;
        } else if (ex instanceof ConnectException) {
            return CONNECT_REFUSE;
        } else if (ex instanceof UnknownHostException) {
            return UNKNOWN_HOST;
        } else if (msg.indexOf("errno: 128") != -1) {
            return NO_ROUTE_TO_HOST;
        } else {
            ex.printStackTrace();
            return ex.toString();
        }
    }

}
