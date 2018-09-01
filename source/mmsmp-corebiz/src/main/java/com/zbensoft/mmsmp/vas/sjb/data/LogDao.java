 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
 import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 
 
 
 
 
 
 
 
 
 
 public class LogDao
   extends JdbcDaoSupport
 {
   private static Logger logger = Logger.getLogger(LogDao.class);
   private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
 
 
 
 
   public void save(AbstractMessage message, String keyValue)
   {
     MT_ReportMessage mtMsg = (MT_ReportMessage)message;
     int SERVUNIQUEID = 0;
     String servicecode = String.valueOf(mtMsg.getServiceId());
     if ((servicecode != null) && (servicecode.equals("172101"))) {
       SERVUNIQUEID = 31002013;
     }
     if ((servicecode != null) && (servicecode.equals("172102"))) {
       SERVUNIQUEID = 31002014;
     }
     logger.info("\n\n\n\n servceid:" + mtMsg.getServiceId() + "\n\n\n\n");
     String phone = keyValue.substring(mtMsg.getCorrelator().length());
     List argsList = new ArrayList();
     String sql = "insert into  USER_SERV_HISTORY (REQID,STATUS,MTSERVTYPE,MSGTYPE,MMSFILE,MTTIME ,MTTRANID ,CELLPHONENUMBER,contentid,SERVUNIQUEID,spid) values(?,?,?,?,?,?,?,?,?,?,?)";
     argsList.add(mtMsg.getReqId() == null ? "" : mtMsg.getReqId());
     argsList.add(mtMsg.getStatus());
     argsList.add(mtMsg.getMtSerType() == null ? "" : mtMsg.getMtSerType());
     argsList.add(mtMsg.getMsgType() == null ? "" : mtMsg.getMsgType());
     argsList.add(mtMsg.getMmsFile() == null ? "" : mtMsg.getMmsFile());
     argsList.add(this.formatDate.format(mtMsg.getMtDate()));
     argsList.add(mtMsg.getCorrelator());
     argsList.add(phone);
     argsList.add(Integer.valueOf(1));
     argsList.add(Integer.valueOf(SERVUNIQUEID));
     argsList.add(mtMsg.getSpid());
     try {
       getJdbcTemplate().update(sql, argsList.toArray());
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("保存下行消息出现异常", e);
     }
   }
   
 
 
 
   public void update(AbstractMessage message)
   {
     MO_ReportMessage mtMsg = (MO_ReportMessage)message;
     
     String sql = "UPDATE USER_SERV_HISTORY set STATUS='" + mtMsg.getStatus() + "' WHERE reqid='" + mtMsg.getCorrelator() + "'";
     logger.info(sql);
     try {
       getJdbcTemplate().update(sql);
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("更新下行消息出现异常", e);
     }
   }
   
 
 
 
 
   public void saveMoSmsMsg(AbstractMessage message, Integer isCorrect)
   {
     MO_SMMessage moMsg = (MO_SMMessage)message;
     
     String sqlSeqNext = "select NEXT VALUE FOR USER_MO_HISTORY_SEQ from sysibm.sysdummy1 ";
     int seqNextId = getJdbcTemplate().queryForInt(sqlSeqNext);
     
     List argsList = new ArrayList();
     String sql = "INSERT INTO USER_MO_HISTORY (ID,CELLPHONENUMBER,SMSTEXT,MOTIME,MESSAGETYPE) VALUES(?,?,?,current timestamp,?)";
     argsList.add(Integer.valueOf(seqNextId));
     argsList.add(moMsg.getSendAddress());
     argsList.add(moMsg.getSmsText());
     
     argsList.add("s");
     try
     {
       getJdbcTemplate().update(sql, argsList.toArray());
     } catch (Exception e) {
       logger.error("保存上行消息出现异常", e);
     }
     
     if (isCorrect != null) {
       updateMoSmsMsg(seqNextId, isCorrect.intValue());
     }
   }
   
 
 
 
 
 
   public void saveMoMMSMsg(AbstractMessage message, String SERVICEACTIVATIONNUMBER, Integer isCorrect)
   {
     MO_MMDeliverMessage moMsg = (MO_MMDeliverMessage)message;
     String phone = moMsg.getSender();
     String str_phone = phone;
     if (phone.startsWith("+86")) { str_phone = phone.substring(3);
     } else if (phone.startsWith("86")) str_phone = phone.substring(2);
     String sqlSeqNext = "select NEXT VALUE FOR USER_MO_HISTORY_SEQ from sysibm.sysdummy1 ";
     int seqNextId = getJdbcTemplate().queryForInt(sqlSeqNext);
     
 
     String sql = "INSERT INTO USER_MO_HISTORY (ID,CELLPHONENUMBER,SERVICEACTIVATIONNUMBER,SMSTEXT,MOTIME,MESSAGETYPE,isCorrect) VALUES(" + seqNextId + ",'" + str_phone + "','" + SERVICEACTIVATIONNUMBER + "','彩信内容',{ts '" + this.formatDate.format(new Date()) + "'},'m','" + isCorrect + "')";
     
 
 
 
 
     try
     {
       getJdbcTemplate().update(sql);
     } catch (Exception e) {
       logger.error("保存上行消息出现异常", e);
     }
   }
   
 
 
 
 
 
 
 
 
   public String getServicecode(String messageid)
   {
     messageid = messageid.replace("null", "");
     logger.info("select vas.SERVICEID from USER_SERV_HISTORY ush,VASSERVICE vass,VAS vas where ush.SERVUNIQUEID=vass.UNIQUEID and vass.VASID=vas.VASID and ush.MTTRANID='" + messageid + "'");
     String service_code = (String)getJdbcTemplate().queryForObject(
       "select vas.SERVICEID from USER_SERV_HISTORY ush,VASSERVICE vass,VAS vas where ush.SERVUNIQUEID=vass.UNIQUEID and vass.VASID=vas.VASID and ush.MTTRANID=?", 
       new Object[] { messageid }, 
       String.class);
     logger.info("service_code:" + service_code);
     return service_code;
   }
   
 
 
 
 
   public void updateMoSmsMsg(int id, int iscorrect)
   {
     String sql = " update user_mo_history set iscorrect = ? where id = ?";
     getJdbcTemplate().update(sql, new String[] { String.valueOf(iscorrect), String.valueOf(id) });
   }
   
   public void insert(MT_MMMessage msg) {
     logger.info("insert sp mms mt message: " + msg.getMtTranId());
     List argsList = new ArrayList();
     if ((msg.getServiceCode() != null) && (msg.getServiceCode().equals("172101"))) {
       msg.setServiceCode("31002013");
     }
     if ((msg.getServiceCode() != null) && (msg.getServiceCode().equals("172102"))) {
       msg.setServiceCode("31002014");
     }
     
     String sql = "insert into USER_SERV_HISTORY (MTTRANID,REQID,CELLPHONENUMBER,CHARGEPARTY,SERVUNIQUEID,CONTENTID,MMSFILE,MTTIME,STATUS,MTKIND) values ('" + 
       msg.getMtTranId() + "','" + msg.getMtTranId() + "','" + msg.getRcvAddresses()[0] + "','" + msg.getRcvAddresses()[0] + "',1,1,'" + msg.getServiceCode() + "',{ts '" + this.formatDate.format(new Date()) + "'},'2','2')";
     
 
 
 
 
 
 
 
 
 
 
 
     try
     {
       getJdbcTemplate().update(sql);
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("保存下行消息出现异常", e);
     }
   }
 }





