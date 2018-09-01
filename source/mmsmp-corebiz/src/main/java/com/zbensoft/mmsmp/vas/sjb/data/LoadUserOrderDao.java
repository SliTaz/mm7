 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.LinkedList;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.RowCallbackHandler;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class LoadUserOrderDao
   extends JdbcDaoSupport
 {
   private static Logger logger = Logger.getLogger(LoadUserOrderDao.class);
   

   public List<UserInfo> getUserOrderRecord(Integer serviceuniqueid, Integer reqNum, String streamingNum, String province, Integer priority, String contentID, boolean isNational)
   {
	return null;
	   
   }
   

   public List<UserInfo> getUserOrderRecordBySendStatus(Integer uniqueid, Integer contentid, int reqNum, String streamingNum, String province, String[] status, String mtType)
   {
	return null;
	   
   }
   
   public List<UserInfo> getUserInfo(Integer uniqueid, Integer contentid, String generateStreamingNum, String[] phoneNumber, String mtType)
   {
     String sql = null;
     final List<UserInfo> members = new LinkedList();
     String[] arrayOfString; int j = (arrayOfString = phoneNumber).length; for (int i = 0; i < j; i++) { String phone = arrayOfString[i];
       sql = "select i.* from user_info i,user_order o where o.cellphonenumber='" + 
         phone + "' and i.cellphonenumber=o.cellphonenumber and o.serviceuniqueid = " + uniqueid;
       try
       {
         JdbcTemplate jta = getJdbcTemplate();
         jta.query(sql, new RowCallbackHandler() {
           public void processRow(ResultSet rs) throws SQLException {
             UserInfo info = new UserInfo();
             info.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
             info.setProvincecode(rs.getString("PROVINCECODE"));
             info.setUaTypeId(Integer.valueOf(rs.getInt("ua_type_id")));
             members.add(info);
           }
         });
       } catch (Exception e) {
         e.printStackTrace();
       }
       if ((members != null) && (members.size() > 0))
       {
 
 
         List argsList = new ArrayList();
         String insertsql = "INSERT INTO USER_SERV_HISTORY (MTTRANID,CELLPHONENUMBER,SERVUNIQUEID,CONTENTID,STATUS,SENDTYPE) VALUES(?,?,?,?,?,?)";
         argsList.add(generateStreamingNum);
         argsList.add(phone);
         argsList.add(uniqueid);
         argsList.add(contentid);
         argsList.add("1");
         argsList.add(mtType);
         try {
           getJdbcTemplate().update(insertsql, argsList.toArray());
         } catch (Exception e) {
           e.printStackTrace();
           logger.error("保存下发记录出现异常", e);
         }
       }
     }
     return members;
   }
   
 
 
 
   public List<UserInfo> getUserInfoForDemand(String[] phoneNumber, String mtTrandId, int contentid, int serviceid, String mtType)
   {
     List<UserInfo> list = new LinkedList();
     String sql = null;
     final List<UserInfo> members = new LinkedList();
     String[] arrayOfString; int j = (arrayOfString = phoneNumber).length; for (int i = 0; i < j; i++) { String phone = arrayOfString[i];
       sql = "select i.* from user_info i where i.cellphonenumber='" + phone + "'";
       try {
         JdbcTemplate jta = getJdbcTemplate();
         jta.query(sql, new RowCallbackHandler() {
           public void processRow(ResultSet rs) throws SQLException {
             UserInfo info = new UserInfo();
             info.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
             info.setProvincecode(rs.getString("PROVINCECODE"));
             info.setUaTypeId(Integer.valueOf(rs.getInt("ua_type_id")));
             members.add(info);
           }
         });
       } catch (Exception e) {
         e.printStackTrace();
       }
       
       if ((members != null) && (members.size() > 0)) {
         list.add((UserInfo)members.get(0));
         members.remove(0);
         
 
         List argsList = new ArrayList();
         String insertsql = "INSERT INTO USER_SERV_HISTORY (MTTRANID,CELLPHONENUMBER,SERVUNIQUEID,CONTENTID,STATUS,SENDTYPE) VALUES(?,?,?,?,?,?)";
         argsList.add(mtTrandId);
         argsList.add(phone);
         argsList.add(Integer.valueOf(serviceid));
         argsList.add(Integer.valueOf(contentid));
         argsList.add("1");
         argsList.add(mtType);
         try {
           getJdbcTemplate().update(insertsql, argsList.toArray());
           logger.debug("save user_serv_history successfully!!");
         } catch (Exception e) {
           e.printStackTrace();
           logger.error("保存下发记录出现异常", e);
         }
       }
     }
     
     return list;
   }
   

   public List<com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder> db2ProcTest()
   {
	return null;
	   
   }
 }





