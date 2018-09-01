package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UniCenter {
    public UniCenter() {
    }

    public static HashMap loadParameter() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap info = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select name, flag from unicenter");
            rs = pstm.executeQuery();

            while(rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                info.put(name, value);
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return info;
    }
}

