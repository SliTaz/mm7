

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.PreUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.SmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class JobDao implements Dao{
    Log log = LogFactory.getLog(JobDao.class);

    public JobDao() {
    }

    public PreUserEntity preMmsUsers() {
    	//xian bu xie
        PreUserEntity preUser = new PreUserEntity();
//        Connection con = null;
//        CallableStatement cstmt = null;
//
//        try {
//            con = this.getConnection();
//            cstmt = con.prepareCall("{call PRO_INSERT_MMSUSER(?,?,?)}");
//            cstmt.registerOutParameter(1, 2);
//            cstmt.registerOutParameter(2, 4);
//            cstmt.registerOutParameter(3, 4);
//            cstmt.execute();
//            preUser.setContentId(cstmt.getLong(1));
//            preUser.setUsers(cstmt.getInt(2));
//            preUser.setCode(cstmt.getInt(3));
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        } finally {
//            this.close(con, cstmt, (ResultSet)null);
//        }

        return preUser;
    }

    public List<SmsUserEntity> getSendsmsUsers(int rows) {
    	//xian bu xie
//        List lsuser = new ArrayList();
//        Connection con = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = " select * from vasservice_send where sendtime<=current_timestamp and date(sendtime)=current_date fetch first " + rows + " row only ";
//            con = this.getConnection();
//            stmt = con.prepareStatement(sql);
//            rs = stmt.executeQuery();
//
//            while(rs.next()) {
//                SmsUserEntity user = new SmsUserEntity();
//                user.setUniqueid(rs.getLong("UNIQUEID"));
//                user.setProductId(rs.getString("PRODUCTID"));
//                user.setServiceId(rs.getString("SERVICEID"));
//                user.setCellPhonenum(rs.getString("CELLPHONENUM"));
//                user.setOrderTime(rs.getString("ORDERTIME"));
//                user.setSendTime(rs.getString("SENDTIME"));
//                user.setChannel(rs.getString("CHANNEL"));
//                user.setServiceName(rs.getString("SERVICENAME"));
//                user.setSpid(rs.getString("SPID"));
//                user.setFee(rs.getObject("FEE") == null ? 0 : rs.getInt("FEE"));
//                user.setStatus(rs.getObject("STATUS") == null ? 0 : rs.getInt("STATUS"));
//                user.setSendCount(rs.getObject("SENDCOUNT") == null ? 10 : rs.getInt("SENDCOUNT"));
//                lsuser.add(user);
//            }
//        } catch (Exception var11) {
//            var11.printStackTrace();
//        } finally {
//            this.close(con, stmt, rs);
//        }
//
//        return lsuser;
        return null;
    }

    public Long selectContentIDInSend() {
//        Long result = null;
//        Connection con = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        ArrayList<String> contentIDList = new ArrayList();
//        con = this.getConnection();
//
//        try {
//            stmt = con.prepareStatement("select contentid from content_info where contenttype='0' and status='10' and sendflag='2'");
//            rs = stmt.executeQuery();
//
//            while(rs != null & rs.next()) {
//                String contentId = rs.getString("contentid");
//                contentIDList.add(contentId);
//            }
//        } catch (SQLException var19) {
//            var19.printStackTrace();
//        } finally {
//            this.close(con, stmt, rs);
//        }
//
//        if (contentIDList.size() == 0) {
//            return result;
//        } else {
//            StringBuilder whereSQL = new StringBuilder();
//
//            for(int i = 0; i < contentIDList.size(); ++i) {
//                if (i != 0) {
//                    whereSQL.append(" OR");
//                } else {
//                    whereSQL.append(" WHERE ");
//                }
//
//                whereSQL.append(" CONTENTID=").append((String)contentIDList.get(i));
//            }
//
//            try {
//                String sql = "select contentid from user_send_temp " + whereSQL.toString() + " order by PRIORITY asc fetch first 1 row only ";
//                this.logger.debug("SQL:" + sql);
//                con = this.getConnection();
//                stmt = con.prepareStatement(sql);
//                rs = stmt.executeQuery();
//                if (rs != null && rs.next()) {
//                    result = rs.getLong("contentId");
//                }
//            } catch (Exception var17) {
//                var17.printStackTrace();
//            } finally {
//                this.close(con, stmt, rs);
//            }
//
//            return result;
//        }
        return HttpHelper.selectContentIDInSend();
    }

    public boolean deleteSendMMSUsersByContentID(Long contentid) {
//        boolean result = true;
//        Connection con = null;
//        PreparedStatement stmt = null;
//
//        try {
//            String sql = "delete from user_send_temp where contentid=" + contentid;
//            con = this.getConnection();
//            stmt = con.prepareStatement(sql);
//            stmt.executeUpdate();
//        } catch (Exception var9) {
//            result = false;
//            var9.printStackTrace();
//        } finally {
//            this.close(con, stmt, (ResultSet)null);
//        }

        return HttpHelper.deleteSendMMSUsersByContentID(contentid);
    }

    public List<MmsUserEntity> getSendmmsUsers(Long contentid, int begin, int size) {
//        List<MmsUserEntity> lsuser = new ArrayList();
//        Connection con = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "select * from (select USER_SEND_TEMP.ID, USER_SEND_TEMP.CONTENTID,USER_SEND_TEMP.SERVICEID,USER_SEND_TEMP.VASID,USER_SEND_TEMP.VASPID,USER_SEND_TEMP.SENDADDRESS,USER_SEND_TEMP.PRODUCTID, USER_SEND_TEMP.USERNUMBER, USER_SEND_TEMP.CHARGENUMBER, USER_SEND_TEMP.CONTENTNAME, USER_SEND_TEMP.CONTENTPATH, USER_SEND_TEMP.CREATEDATE, USER_SEND_TEMP.PRIORITY, rownumber() over(order by id asc ) as rowid  from user_send_temp where  contentid=" + contentid + ") as t where t.rowid>=" + begin + " and t.rowid<" + (begin + size);
//            this.logger.info("sql:=  " + sql);
//            con = this.getConnection();
//            stmt = con.prepareStatement(sql);
//            rs = stmt.executeQuery();
//
//            while(rs.next()) {
//                MmsUserEntity user = new MmsUserEntity();
//                user.setId(rs.getLong("id"));
//                user.setContentId(rs.getLong("contentId"));
//                user.setServiceId(rs.getLong("serviceId"));
//                user.setVasId(rs.getString("sendaddress"));
//                user.setVaspId(rs.getString("vaspId"));
//                user.setSendAddress(rs.getString("sendaddress"));
//                user.setProductId(rs.getString("productId"));
//                user.setUserNumber(rs.getString("userNumber"));
//                user.setChargeNumber(rs.getString("chargeNumber"));
//                user.setContentName(rs.getString("contentName"));
//                user.setContentFile(rs.getString("contentPath"));
//                user.setCreateDate(rs.getString("createDate"));
//                lsuser.add(user);
//            }
//        } catch (Exception var13) {
//            var13.printStackTrace();
//        } finally {
//            this.close(con, stmt, rs);
//        }

//        return lsuser;
        return null;
    }

    public MmsUserEntity getSendmmsUserEntity(Long contentid) {
//        MmsUserEntity user = new MmsUserEntity();
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = " select * from USER_SEND_TEMP ust where ust.CONTENTID=" + contentid + " fetch first 1 row only ";
//            this.logger.info("sql:=  " + sql);
//            con = this.getConnection();
//            stmt = con.createStatement();
//            rs = stmt.executeQuery(sql);
//            if (rs.next()) {
//                user.setId(rs.getLong("id"));
//                user.setContentId(rs.getLong("contentId"));
//                user.setServiceId(rs.getLong("serviceId"));
//                user.setVasId(rs.getString("sendaddress"));
//                user.setVaspId(rs.getString("vaspId"));
//                user.setSendAddress(rs.getString("sendaddress"));
//                user.setProductId(rs.getString("productId"));
//                user.setUserNumber(rs.getString("userNumber"));
//                user.setChargeNumber(rs.getString("chargeNumber"));
//                user.setContentName(rs.getString("contentName"));
//                user.setContentFile(rs.getString("contentPath"));
//                user.setCreateDate(rs.getString("createDate"));
//            }
//        } catch (Exception var10) {
//            var10.printStackTrace();
//        } finally {
//            this.close(con, stmt, rs);
//        }
//
//        return user;
        return null;
    }

    public int getSendmmsCount(long contentid) {
        int count = 1;
//        Connection con = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "select count(*) from user_send_temp where contentid=?";
//            con = this.getConnection();
//            stmt = con.prepareStatement(sql);
//            stmt.setLong(1, contentid);
//            rs = stmt.executeQuery();
//            if (rs.next()) {
//                count = rs.getInt(1);
//            }
//        } catch (Exception var11) {
//            var11.printStackTrace();
//        } finally {
//            this.close(con, stmt, rs);
//        }

        return count;
    }

    public boolean removeMmsUsersById(List<Long> ids) {
        if (ids != null && ids.size() != 0) {
            boolean result = true;
            Connection con = null;
            PreparedStatement stmt = null;
            Object var5 = null;

            try {
                con = this.getConnection();
                con.setAutoCommit(false);
                stmt = con.prepareStatement("delete from user_send_temp where id=?");
                Iterator var7 = ids.iterator();

                while(var7.hasNext()) {
                    Long id = (Long)var7.next();
                    stmt.setLong(1, id);
                    stmt.addBatch();
                }

                stmt.executeBatch();
                con.commit();
            } catch (Exception var18) {
                result = false;

                try {
                    con.rollback();
                } catch (SQLException var17) {
                    ;
                }

                var18.printStackTrace();
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception var16) {
                    ;
                }

                this.close(con, stmt, (ResultSet)null);
            }

            return result;
        } else {
            return true;
        }
    }

    public void removeMmsUsers(List<MmsUserEntity> sends) {
        Connection con = null;
        PreparedStatement stmt = null;
        Object var4 = null;

        try {
            con = this.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement("delete from user_send_temp where id=?");
            Iterator var6 = sends.iterator();

            while(var6.hasNext()) {
                MmsUserEntity send = (MmsUserEntity)var6.next();
                stmt.setLong(1, send.getId());
                stmt.addBatch();
            }

            stmt.executeBatch();
            con.commit();
        } catch (Exception var17) {
            try {
                con.rollback();
            } catch (SQLException var16) {
                ;
            }

            var17.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception var15) {
                ;
            }

            this.close(con, stmt, (ResultSet)null);
        }

    }

    public void removeSmsUser(SmsUserEntity sms) {
        Connection con = null;
        PreparedStatement stmt = null;
        Object var4 = null;

        try {
            con = this.getConnection();
            stmt = con.prepareStatement("delete from vasservice_send where uniqueid=?");
            stmt.setLong(1, sms.getUniqueid());
            stmt.executeUpdate();
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            this.close(con, stmt, (ResultSet)null);
        }

    }

    private Connection getConnection() {
        return null;
    }

    public void updateSendmmsFlag(long contentid) {
//        Connection con = null;
//        PreparedStatement stmt = null;
//        Object var5 = null;
//
//        try {
//            con = this.getConnection();
//            stmt = con.prepareStatement("update content_info set sendflag='1',status='30',validendtime=current_timestamp where contentid=?");
//            stmt.setLong(1, contentid);
//            stmt.executeUpdate();
//        } catch (Exception var10) {
//            var10.printStackTrace();
//        } finally {
//            this.close(con, stmt, (ResultSet)null);
//        }

    }

    public void updateSendmmsCount(List<MmsUserEntity> sends) {
        Connection con = null;
        PreparedStatement stmt = null;
        Object var4 = null;

        try {
            con = this.getConnection();
            stmt = con.prepareStatement("update user_send_temp set sendcount=sendcount+1 where id=?");
            Iterator var6 = sends.iterator();

            while(var6.hasNext()) {
                MmsUserEntity send = (MmsUserEntity)var6.next();
                stmt.setLong(1, send.getId());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            this.close(con, stmt, (ResultSet)null);
        }

    }

    public void updateSendsmsCount(SmsUserEntity send) {
        Connection con = null;
        PreparedStatement stmt = null;
        Object var4 = null;

        try {
            con = this.getConnection();
            stmt = con.prepareStatement("update vasservice_send set sendcount=sendcount+1 where uniqueid=?");
            stmt.setLong(1, send.getUniqueid());
            stmt.executeUpdate();
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            this.close(con, stmt, (ResultSet)null);
        }

    }

    public void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception var7) {
            ;
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception var6) {
            ;
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception var5) {
            ;
        }

    }
}
