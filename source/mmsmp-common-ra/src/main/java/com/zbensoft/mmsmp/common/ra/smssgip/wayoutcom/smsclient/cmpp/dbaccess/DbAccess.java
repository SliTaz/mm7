package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbAccess {
    private static Properties _properties = null;
    private static Logger log = Logger.getLogger(DbAccess.class);
    public static ConnectionPool poolManager;

    public DbAccess() {
    }

    public static String getProperty(String key) {
        if (_properties == null) {
            try {
                InputStream ins = DbAccess.class.getResourceAsStream("/client_app.properties");
                _properties = new Properties();
                _properties.load(ins);
            } catch (Exception var3) {
                _properties = null;
            }
        }

        return _properties.getProperty(key);
    }

    public static Connection getConnection() throws Exception {
        Connection conn = null;
        if (poolManager == null) {
            String driver = getProperty("smsDB.Driver");
            String url = getProperty("smsDB.URL");
            String user = getProperty("smsDB.User");
            String password = getProperty("smsDB.Password");

            try {
                poolManager = new ConnectionPool(driver, url, user, password);
                poolManager.createPool();
            } catch (Exception var6) {
                log.error(var6);
                poolManager = null;
            }
        }

        if (poolManager != null) {
            conn = poolManager.getConnection();
        } else {
            conn = null;
        }

        return conn;
    }

    public static void Close(Connection conn, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (pstm != null) {
                pstm.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var4) {
            log.error(var4);
        }

    }

    public static void Close(Connection conn, PreparedStatement pstm) {
        try {
            if (pstm != null) {
                pstm.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var3) {
            log.error(var3);
        }

    }

    public static void Close(Connection conn, CallableStatement cstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (cstm != null) {
                cstm.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var4) {
            log.error(var4);
        }

    }

    public static void Close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var4) {
            log.error(var4);
        }

    }

    public static void Close(Connection conn, CallableStatement cstm) {
        try {
            if (cstm != null) {
                cstm.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var3) {
            log.error(var3);
        }

    }

    public static void Close(Connection conn) {
        try {
            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var2) {
            log.error(var2);
        }

    }

    public static void Close(Connection conn, PreparedStatement pstm, CallableStatement cstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (pstm != null) {
                pstm.close();
            }

            if (cstm != null) {
                cstm.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var5) {
            log.error(var5);
        }

    }

    public static void Close(Connection conn, PreparedStatement pstm1, PreparedStatement pstm2, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (pstm1 != null) {
                pstm1.close();
            }

            if (pstm2 != null) {
                pstm2.close();
            }

            if (conn != null) {
                poolManager.returnConnection(conn);
            }
        } catch (Exception var5) {
            log.error(var5);
        }

    }
}

