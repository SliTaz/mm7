package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionPool {
    private String jdbcDriver = "";
    private String dbUrl = "";
    private String dbUsername = "";
    private String dbPassword = "";
    private String testTable = "";
    private int initialConnections = 2;
    private int incrementalConnections = 2;
    private int maxConnections = 50;
    private Vector connections = null;

    public ConnectionPool(String jdbcDriver, String dbUrl, String dbUsername, String dbPassword) {
        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public int getInitialConnections() {
        return this.initialConnections;
    }

    public void setInitialConnections(int initialConnections) {
        this.initialConnections = initialConnections;
    }

    public int getIncrementalConnections() {
        return this.incrementalConnections;
    }

    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getTestTable() {
        return this.testTable;
    }

    public void setTestTable(String testTable) {
        this.testTable = testTable;
    }

    public synchronized void createPool() throws Exception {
        if (this.connections == null) {
            Driver driver = (Driver)Class.forName(this.jdbcDriver).newInstance();
            DriverManager.registerDriver(driver);
            this.connections = new Vector();
            this.createConnections(this.initialConnections);
            System.out.println(" 数据库连接池创建成功！ ");
        }
    }

    private void createConnections(int numConnections) throws SQLException {
        for(int x = 0; x < numConnections && (this.maxConnections <= 0 || this.connections.size() < this.maxConnections); ++x) {
            try {
                this.connections.addElement(new ConnectionPool.PooledConnection(this.newConnection(), this));
            } catch (SQLException var4) {
                System.out.println(" 创建数据库连接失败！ " + var4.getMessage());
                throw new SQLException();
            }

            System.out.println(" 数据库连接己创建 ......");
        }

    }

    private Connection newConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(this.dbUrl, this.dbUsername, this.dbPassword);
        if (this.connections.size() == 0) {
            DatabaseMetaData metaData = conn.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections();
            if (driverMaxConnections > 0 && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }

        return conn;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (this.connections == null) {
            return null;
        } else {
            Connection conn;
            for(conn = this.getFreeConnection(); conn == null; conn = this.getFreeConnection()) {
                this.wait(250);
            }

            return conn;
        }
    }

    private Connection getFreeConnection() throws SQLException {
        Connection conn = this.findFreeConnection();
        if (conn == null) {
            this.createConnections(this.incrementalConnections);
            conn = this.findFreeConnection();
            if (conn == null) {
                return null;
            }
        }

        return conn;
    }

    private Connection findFreeConnection() throws SQLException {
        Connection conn = null;
        ConnectionPool.PooledConnection pConn = null;
        Enumeration e_num = this.connections.elements();

        while(e_num.hasMoreElements()) {
            pConn = (ConnectionPool.PooledConnection)e_num.nextElement();
            if (!pConn.isBusy()) {
                conn = pConn.getConnection();
                pConn.setBusy(true);
                if (!this.testConnection(conn)) {
                    try {
                        conn = this.newConnection();
                    } catch (SQLException var5) {
                        System.out.println(" 创建数据库连接失败！ " + var5.getMessage());
                        return null;
                    }

                    pConn.setConnection(conn);
                }
                break;
            }
        }

        return conn;
    }

    private boolean testConnection(Connection conn) {
        try {
            if (this.testTable.equals("")) {
                conn.setAutoCommit(true);
            } else {
                Statement stmt = conn.createStatement();
                stmt.execute("select count(*) from " + this.testTable);
            }

            return true;
        } catch (SQLException var3) {
            this.closeConnection(conn);
            return false;
        }
    }

    public void returnConnection(Connection conn) {
        System.out.println("Invoce the return Connection method!");
        if (this.connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
        } else {
            ConnectionPool.PooledConnection pConn = null;
            Enumeration e_num = this.connections.elements();

            while(e_num.hasMoreElements()) {
                pConn = (ConnectionPool.PooledConnection)e_num.nextElement();
                if (conn == pConn.getOConnection()) {
                    pConn.setBusy(false);
                    break;
                }
            }

        }
    }

    public synchronized void refreshConnections() throws SQLException {
        if (this.connections == null) {
            System.out.println(" 连接池不存在，无法刷新 !");
        } else {
            ConnectionPool.PooledConnection pConn = null;
            Enumeration e_num = this.connections.elements();

            while(e_num.hasMoreElements()) {
                pConn = (ConnectionPool.PooledConnection)e_num.nextElement();
                if (pConn.isBusy()) {
                    this.wait(5000);
                }

                this.closeConnection(pConn.getConnection());
                pConn.setConnection(this.newConnection());
                pConn.setBusy(false);
            }

        }
    }

    public synchronized void closeConnectionPool() throws SQLException {
        if (this.connections == null) {
            System.out.println(" 连接池不存在，无法关闭 !");
        } else {
            ConnectionPool.PooledConnection pConn = null;
            Enumeration e_num = this.connections.elements();

            while(e_num.hasMoreElements()) {
                pConn = (ConnectionPool.PooledConnection)e_num.nextElement();
                if (pConn.isBusy()) {
                    this.wait(5000);
                }

                this.closeConnection(pConn.getConnection());
                this.connections.removeElement(pConn);
            }

            this.connections = null;
        }
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException var3) {
            System.out.println(" 关闭数据库连接出错： " + var3.getMessage());
        }

    }

    private void wait(int mSeconds) {
        try {
            Thread.sleep((long)mSeconds);
        } catch (InterruptedException var3) {
            ;
        }

    }

    class PooledConnection implements InvocationHandler {
        Connection connection = null;
        Connection newConnection = null;
        ConnectionPool connPool = null;
        boolean busy = false;

        public PooledConnection(Connection connection) {
            this.connection = connection;
        }

        public PooledConnection(Connection connection, ConnectionPool connPool) {
            this.connPool = connPool;
            this.connection = connection;
            Class[] interfaces = new Class[]{Connection.class};
            this.newConnection = (Connection)Proxy.newProxyInstance(connection.getClass().getClassLoader(), interfaces, this);
        }

        public Connection getConnection() {
            return this.newConnection;
        }

        public Connection getOConnection() {
            return this.connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public boolean isBusy() {
            return this.busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obj = null;
            System.out.println("invoke in :" + method.getName());
            if ("close".equalsIgnoreCase(method.getName())) {
                this.connPool.returnConnection(this.connection);
            } else {
                obj = method.invoke(this.connection, args);
            }

            return obj;
        }
    }
}

