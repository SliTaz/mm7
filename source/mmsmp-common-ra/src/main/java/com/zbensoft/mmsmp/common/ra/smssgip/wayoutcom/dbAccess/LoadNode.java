package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LoadNode {
    private LoadNode() {
    }

    public static HashMap loadNode() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String nodeName = null;
        String className = null;
        String timeoutJump = null;
        int timeout_seconds;
        String needTrigger = null;
        String exception_node = null;
        String content = null;
        String need_snap = null;
        HashMap info = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select node_name, class_name, timeout_jump, timeout_seconds, need_trigger, exception_node, content, need_snap from state_node");
            rs = pstm.executeQuery();

            while(rs.next()) {
                nodeName = rs.getString(1);
                className = rs.getString(2);
                timeoutJump = rs.getString(3);
                timeout_seconds = rs.getInt(4);
                needTrigger = rs.getString(5).toLowerCase();
                exception_node = rs.getString(6);
                content = rs.getString(7);
                need_snap = rs.getString(8).toLowerCase();
                info.put(nodeName, new Object[]{className, timeoutJump, new Integer(timeout_seconds), needTrigger, exception_node, content, need_snap});
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return info;
    }

    public static Collection loadTrunk() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String start_node = null;
        String end_node = null;
        String event = null;
        ArrayList list = new ArrayList();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select start_node, end_node, event from state_link");
            rs = pstm.executeQuery();

            while(rs.next()) {
                Object[] info = new Object[3];
                start_node = rs.getString(1);
                end_node = rs.getString(2);
                event = rs.getString(3);
                info[0] = start_node;
                info[1] = end_node;
                info[2] = event;
                list.add(info);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return list;
    }

    public static void main(String[] arg) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long[] pre_seq = (long[])null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select pre_seq1, pre_seq2, pre_seq3 from ? where post_seq1=? and post_seq2=? and post_seq3=? ");
            pstm.setString(1, "smssubmitresend");
            pstm.setLong(2, 1L);
            pstm.setInt(3, 2);
            pstm.setInt(4, 3);

            for(rs = pstm.executeQuery(); rs.next(); pre_seq = new long[]{rs.getLong(1), (long)rs.getInt(2), (long)rs.getInt(3)}) {
                ;
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

    }
}
