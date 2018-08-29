package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public class OperData {
    private static Logger log = Logger.getLogger(OperData.class);

    public OperData() {
    }

    public void insertSubmitok(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smssubmitok(Command, seq1, seq2, seq3) values (?, ?, ?, ?) ");
            System.err.println("**************在insertSubmit的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer buffer = (ByteBuffer)obj[0];
                byte[] comm = buffer.array();
                long[] seq = (long[])obj[1];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setBytes(1, comm);
                pstm.setLong(2, seq1);
                pstm.setInt(3, seq2);
                pstm.setInt(4, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void insertReportok(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smsreportok(Command, seq1, seq2, seq3) values (?, ?, ?, ?) ");
            System.err.println("**************在insertReport的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer buffer = (ByteBuffer)obj[0];
                byte[] comm = buffer.array();
                long[] seq = (long[])obj[1];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setBytes(1, comm);
                pstm.setLong(2, seq1);
                pstm.setInt(3, seq2);
                pstm.setInt(4, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void insertDeliverok(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smsdeliverok(Command, seq1, seq2, seq3) values (?, ?, ?, ?) ");
            System.err.println("**************在insertDeliver的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer buffer = (ByteBuffer)obj[0];
                byte[] comm = buffer.array();
                long[] seq = (long[])obj[1];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setBytes(1, comm);
                pstm.setLong(2, seq1);
                pstm.setInt(3, seq2);
                pstm.setInt(4, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void insertSubmitUnresp(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smssubmitresend(postCommand, preCommand, post_seq1, post_seq2, post_seq3, pre_seq1, pre_seq2, pre_seq3) values (?, ?, ?, ?, ?, ?, ?, ?) ");
            System.err.println("**************在insertSubmitUnresp的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer postbuffer = (ByteBuffer)obj[0];
                ByteBuffer prebuffer = (ByteBuffer)obj[1];
                byte[] postcomm = postbuffer.array();
                byte[] precomm = prebuffer.array();
                long[] seq = (long[])obj[2];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                long seq4 = seq[3];
                int seq5 = (int)seq[4];
                int seq6 = (int)seq[5];
                pstm.setBytes(1, postcomm);
                pstm.setBytes(2, precomm);
                pstm.setLong(3, seq1);
                pstm.setInt(4, seq2);
                pstm.setInt(5, seq3);
                pstm.setLong(6, seq4);
                pstm.setInt(7, seq5);
                pstm.setInt(8, seq6);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void insertReportUnresp(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smsreportresend(Command, seq1, seq2, seq3, cseq1, cseq2, cseq3) values (?, ?, ?, ?, ?, ?, ?) ");
            System.err.println("**************在insertReportunresp的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer buffer = (ByteBuffer)obj[0];
                byte[] comm = buffer.array();
                long[] seq = (long[])obj[1];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                long seq4 = seq[3];
                int seq5 = (int)seq[4];
                int seq6 = (int)seq[5];
                pstm.setBytes(1, comm);
                pstm.setLong(2, seq1);
                pstm.setInt(3, seq2);
                pstm.setInt(4, seq3);
                pstm.setLong(5, seq4);
                pstm.setInt(6, seq5);
                pstm.setInt(7, seq6);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var20) {
            var20.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void insertDeliverUnresp(List list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("insert into smsdeliverresend(Command, seq1, seq2, seq3) values (?, ?, ?, ?) ");
            System.err.println("**************在insertDeliverunresp的list里面有包：" + list.size());

            for(int i = 0; i < list.size(); ++i) {
                Object[] obj = (Object[])list.get(i);
                ByteBuffer buffer = (ByteBuffer)obj[0];
                byte[] comm = buffer.array();
                long[] seq = (long[])obj[1];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setBytes(1, comm);
                pstm.setLong(2, seq1);
                pstm.setInt(3, seq2);
                pstm.setInt(4, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public long[] getSeq(long seq1, int seq2, int seq3) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long[] pre_seq = (long[])null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select pre_seq1, pre_seq2, pre_seq3 from smssubmitresend where post_seq1=? and post_seq2=? and post_seq3=? ");
            pstm.setLong(1, seq1);
            pstm.setInt(2, seq2);
            pstm.setInt(3, seq3);

            for(rs = pstm.executeQuery(); rs.next(); pre_seq = new long[]{rs.getLong(1), (long)rs.getInt(2), (long)rs.getInt(3)}) {
                ;
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return pre_seq;
    }

    public void submitResp(Object[] list) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("update smssubmitresend set resp = 'Y' where post_seq1=? and post_seq2=? and post_seq3=? ");

            for(int i = 0; i < list.length; ++i) {
                long[] seq = (long[])list[i];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setLong(1, seq1);
                pstm.setInt(2, seq2);
                pstm.setInt(3, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void reportResp(Object[] report) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("update smsreportresend set resp = 'Y' where seq1=? and seq2=? and seq3=? ");

            for(int i = 0; i < report.length; ++i) {
                long[] seq = (long[])report[i];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setLong(1, seq1);
                pstm.setInt(2, seq2);
                pstm.setInt(3, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public void deliverResp(Object[] deliver) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("update smsdeliverresend set resp = 'Y' where seq1=? and seq2=? and seq3=? ");

            for(int i = 0; i < deliver.length; ++i) {
                long[] seq = (long[])deliver[i];
                long seq1 = seq[0];
                int seq2 = (int)seq[1];
                int seq3 = (int)seq[2];
                pstm.setLong(1, seq1);
                pstm.setInt(2, seq2);
                pstm.setInt(3, seq3);
                pstm.addBatch();
            }

            pstm.executeBatch();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

    }

    public List submitResend() {
        List list = new ArrayList();
        Connection conn = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList seq = new ArrayList();

        ArrayList var11;
        try {
            conn = DbAccess.getConnection();
            stm = conn.createStatement();
            String sql = "select postCommand, post_seq1, post_seq2, post_seq3 from smssubmitresend where resp = 'N' and (((convertTime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'submit_resend') and retries = 0) or ((convertTime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'submit_interval') and retries != 0))";
            rs = stm.executeQuery(sql);

            while(rs.next()) {
                long[] post = new long[3];
                list.add(rs.getBytes(1));
                post[0] = rs.getLong(2);
                post[1] = (long)rs.getInt(3);
                post[2] = (long)rs.getInt(4);
                seq.add(post);
            }

            rs.close();
            stm.close();
            if (list.size() != 0) {
                pstm = conn.prepareStatement("update smssubmitresend set retries = retries+1 where post_seq1 = ? and post_seq2 = ? and post_seq3 = ?");

                for(int i = 0; i < seq.size(); ++i) {
                    long[] Lseq = (long[])seq.get(i);
                    pstm.setLong(1, Lseq[0]);
                    pstm.setInt(2, (int)Lseq[1]);
                    pstm.setInt(3, (int)Lseq[2]);
                    pstm.addBatch();
                }

                pstm.executeBatch();
                return list;
            }

            var11 = list;
        } catch (Exception var14) {
            var14.printStackTrace();
            return list;
        } finally {
            DbAccess.Close(conn, pstm);
        }

        return var11;
    }

    public List reportResend() {
        List list = new ArrayList();
        Connection conn = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long[] pre = new long[3];
        ArrayList seq = new ArrayList();

        ArrayList var12;
        try {
            conn = DbAccess.getConnection();
            stm = conn.createStatement();
            String sql = "select Command, seq1, seq2, seq3 from smsreportresend where resp = 'N' and (((recvtime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'report_resend') and retries = 0) or ((recvtime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'report_interval') and retries != 0))";

            for(rs = stm.executeQuery(sql); rs.next(); pre = new long[3]) {
                list.add(rs.getBytes(1));
                pre[0] = rs.getLong(2);
                pre[1] = (long)rs.getInt(3);
                pre[2] = (long)rs.getInt(4);
                seq.add(pre);
            }

            rs.close();
            stm.close();
            if (list.size() != 0) {
                pstm = conn.prepareStatement("update smsreportresend set retries = retries+1 where seq1 = ? and seq2 = ? and seq3 = ?");

                for(int i = 0; i < seq.size(); ++i) {
                    long[] Lseq = (long[])seq.get(i);
                    pstm.setLong(1, Lseq[0]);
                    pstm.setInt(2, (int)Lseq[1]);
                    pstm.setInt(3, (int)Lseq[2]);
                    pstm.addBatch();
                }

                pstm.executeBatch();
                return list;
            }

            var12 = list;
        } catch (Exception var15) {
            var15.printStackTrace();
            return list;
        } finally {
            DbAccess.Close(conn, pstm);
        }

        return var12;
    }

    public List deliverResend() {
        List list = new ArrayList();
        Connection conn = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long[] pre = new long[3];
        ArrayList seq = new ArrayList();

        ArrayList var12;
        try {
            conn = DbAccess.getConnection();
            stm = conn.createStatement();
            String sql = "select Command, seq1, seq2, seq3 from smsdeliverresend where resp = 'N' and (((recvtime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'deliver_resend') and retries = 0) or ((recvtime - SYSDATE)*24*60 >=(select value from smsparameter where name = 'deliver_interval') and retries != 0))";

            for(rs = stm.executeQuery(sql); rs.next(); pre = new long[3]) {
                list.add(rs.getBytes(1));
                pre[0] = rs.getLong(2);
                pre[1] = (long)rs.getInt(3);
                pre[2] = (long)rs.getInt(4);
                seq.add(pre);
            }

            rs.close();
            stm.close();
            if (list.size() != 0) {
                pstm = conn.prepareStatement("update smsdeliverresend set retries = retries+1 where seq1 = ? and seq2 = ? and seq3 = ?");

                for(int i = 0; i < seq.size(); ++i) {
                    long[] Lseq = (long[])seq.get(i);
                    pstm.setLong(1, Lseq[0]);
                    pstm.setInt(2, (int)Lseq[1]);
                    pstm.setInt(3, (int)Lseq[2]);
                    pstm.addBatch();
                }

                pstm.executeBatch();
                return list;
            }

            var12 = list;
        } catch (Exception var15) {
            var15.printStackTrace();
            return list;
        } finally {
            DbAccess.Close(conn, pstm);
        }

        return var12;
    }

    public void moveData() {
        Connection conn = null;
        CallableStatement cstm = null;

        try {
            conn = DbAccess.getConnection();
            cstm = conn.prepareCall("{call submit_move()}");
            cstm.execute();
            cstm.close();
            cstm = conn.prepareCall("{call deliver_move()}");
            cstm.execute();
            cstm.close();
            cstm = conn.prepareCall("{call report_move()}");
            cstm.execute();
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            DbAccess.Close(conn, cstm);
        }

    }

    public boolean IPCheck(String IP) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean var5 = false;

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select * from app where clientip=? ");
            pstm.setString(1, IP);
            rs = pstm.executeQuery();
            if (rs.next()) {
                var5 = true;
            }
        } catch (Exception var13) {
            String exStr = var13.toString();
            StackTraceElement[] trace = var13.getStackTrace();

            for(int i = 0; i < trace.length; ++i) {
                exStr = exStr + "\n\tat " + trace[i];
            }

            log.info(exStr);
            var13.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm);
        }

        return true;
    }

    public void loadAppInfo(HashMap report, HashMap deliver, String IP) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        synchronized(report) {
            if (report == null) {
                return;
            }
        }

        synchronized(deliver) {
            if (deliver == null) {
                return;
            }
        }

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select id, serverip, serverport from app where clientip=? ");
            pstm.setString(1, IP);
            rs = pstm.executeQuery();

            while(rs.next()) {
                Integer id = new Integer(rs.getInt(1));
                String ip = rs.getString(2);
                int port = rs.getInt(3);
                InetSocketAddress address = new InetSocketAddress(ip, port);
                synchronized(report) {
                    report.put(id, address);
                }
            }

            rs.close();
            rs = null;
            pstm.close();
            pstm = null;
            pstm = conn.prepareStatement("select serverip, serverport, code from app, application where app.id=application.APPID and clientip=? ");
            pstm.setString(1, IP);
            rs = pstm.executeQuery();

            while(rs.next()) {
                String ip = rs.getString(1);
                int port = rs.getInt(2);
                InetSocketAddress address = new InetSocketAddress(ip, port);
                synchronized(deliver) {
                    deliver.put(rs.getString(3), address);
                }
            }
        } catch (Exception var21) {
            var21.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

    }

    public void loadAppInfo(HashMap report, HashMap deliver) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap reportTmp = new HashMap();
        HashMap deliverTmp = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select id, serverip, serverport from app");
            rs = pstm.executeQuery();

            while(rs.next()) {
                Integer id = new Integer(rs.getInt(1));
                String ip = rs.getString(2);
                int port = rs.getInt(3);
                InetSocketAddress address = new InetSocketAddress(ip, port);
                synchronized(report) {
                    reportTmp.put(id, address);
                }
            }

            rs.close();
            rs = null;
            pstm.close();
            pstm = null;
            synchronized(report) {
                report.clear();
                report.putAll(reportTmp);
                reportTmp = null;
            }

            pstm = conn.prepareStatement("select serverip, serverport, access_num from app, application where app.id=application.APPID");
            rs = pstm.executeQuery();

            while(rs.next()) {
                String ip = rs.getString(1);
                int port = rs.getInt(2);
                InetSocketAddress address = new InetSocketAddress(ip, port);
                deliverTmp.put(rs.getString(3), address);
            }

            synchronized(deliver) {
                deliver.clear();
                deliver.putAll(deliverTmp);
                deliverTmp = null;
            }
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

    }

    public HashMap loadConfigInfo() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap info = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select configName, ip, port from smsconfig");
            rs = pstm.executeQuery();

            while(rs.next()) {
                String name = rs.getString(1);
                String ip = rs.getString(2);
                int port = rs.getInt(3);
                InetSocketAddress address = new InetSocketAddress(ip, port);
                info.put(name, address);
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return info;
    }

    public HashMap loadParameter() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap info = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select name, value from smsparameter");
            rs = pstm.executeQuery();

            while(rs.next()) {
                String name = rs.getString(1);
                int value = rs.getInt(2);
                info.put(name, new Integer(value));
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return info;
    }

    public HashMap loadLogin() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap info = new HashMap();

        try {
            conn = DbAccess.getConnection();
            pstm = conn.prepareStatement("select name, value from smslogin");
            rs = pstm.executeQuery();

            while(rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                info.put(name, value);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            DbAccess.Close(conn, pstm, rs);
        }

        return info;
    }
}
