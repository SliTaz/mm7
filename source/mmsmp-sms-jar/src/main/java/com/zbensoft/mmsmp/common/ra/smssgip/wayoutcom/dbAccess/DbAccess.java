package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbAccess {
    private static Properties _properties = null;
    public static ConnectionPoolManager poolManager;

    public DbAccess() {
    }

    public static String getProperty(String key) {
        if (_properties == null) {
            try {
                InputStream ins = DbAccess.class.getResourceAsStream("/app.properties");
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
                poolManager = new ConnectionPoolManager(300);
                Class.forName(driver);
                poolManager.addAlias("smsDB", driver, url, user, password, Integer.parseInt(getProperty("DB.MaxConn")), Integer.parseInt(getProperty("DB.Idle")), Integer.parseInt(getProperty("DB.CheckOut")));
            } catch (Exception var6) {
                poolManager = null;
            }
        }

        if (poolManager != null) {
            conn = DriverManager.getConnection("jdbc:bitmechanic:pool:smsDB", (String)null, (String)null);
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
                conn.close();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void Close(Connection conn, PreparedStatement pstm) {
        try {
            if (pstm != null) {
                pstm.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
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
                conn.close();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
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
                conn.close();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void Close(Connection conn, CallableStatement cstm) {
        try {
            if (cstm != null) {
                cstm.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void Close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
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
                conn.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
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
                conn.close();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
