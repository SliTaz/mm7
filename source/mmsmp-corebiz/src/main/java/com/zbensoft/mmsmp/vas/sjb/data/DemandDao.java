 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.AnswerRecord;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
 import com.zbensoft.mmsmp.common.ra.send.sgipsms.SmsSenderDto;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DateFormat;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.RowMapper;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 
 
 
 
 
 
 
 
 
 
 public class DemandDao
   extends JdbcDaoSupport
 {
   private static Logger logger = Logger.getLogger(DemandDao.class);
   private DecimalFormat format = new DecimalFormat("#####.00");
   
 
 
 
 
   public String getNewmessageid(String MTTRANID)
   {
     String messageid = "";
     String sql = "select MESSAGEID from USER_SERV_HISTORY where reqid='" + MTTRANID + "'";
     logger.info("=====>" + sql);
     List list = getJdbcTemplate().query(sql, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString(1);
       }
     });
     
     if ((list != null) && (list.size() > 0)) {
       messageid = list.get(0).toString();
     }
     return messageid;
   }
   
   public String queryErrorMsgTemplate() {
     String sql = "select value from SYS_PARAMETERS sys where sys.NAME='ERROR_MSG'";
     
     List list = getJdbcTemplate().query(sql, new RowMapper() {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString("value");
       }
     });
     if ((list != null) && (list.size() != 0)) {
       return list.get(0).toString();
     }
     return "";
   }
   
   public String queryOrderMessage() {
     String sql = "select value from SYS_PARAMETERS sys where sys.NAME='ORDER_TIP'";
     
     List list = getJdbcTemplate().query(sql, new RowMapper() {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString("value");
       }
     });
     if ((list != null) && (list.size() != 0)) {
       return list.get(0).toString();
     }
     return "";
   }
   
 
 
 
 
   public void updateSpMMSSendRecord(String status, String mttranid)
   {
     String sql = "update USER_SERV_HISTORY set status = '" + status + "' where reqid = '" + mttranid + "'";
     logger.info("sql:" + sql);
     try {
       getJdbcTemplate().update(sql);
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("=====>update MT message Exception", e);
     }
   }
   
 
 
 
   public void updateSpMMSSendRecord(String status, String mttranid, String mmscode)
   {
     String sql = "update USER_SERV_HISTORY set status = '" + status + "', MMSGRESCODE = '" + mmscode + "' where reqid = '" + mttranid + "'";
     logger.info("sql:" + sql);
     try {
       getJdbcTemplate().update(sql);
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("=====>update MT message Exception", e);
     }
   }
   
   public String queryOndemandMessage() { String sql = "select value from SYS_PARAMETERS sys where sys.NAME='ONDEMAND_TIP'";
     
     List list = getJdbcTemplate().query(sql, new RowMapper() {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString("value");
       }
     });
     if ((list != null) && (list.size() != 0)) {
       return list.get(0).toString();
     }
     return "";
   }
   
   public String queryUpOndemandMessage() {
     String sql = "select value from SYS_PARAMETERS sys where sys.NAME='UP_ONDEMAND_TIP'";
     
     List list = getJdbcTemplate().query(sql, new RowMapper() {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString("value");
       }
     });
     if ((list != null) && (list.size() != 0)) {
       return list.get(0).toString();
     }
     return "";
   }
   
 
 
 
 
 
   public SmsSenderDto getOrderAndProduct_id(String messageContent)
   {
     String sql = "select reinfo.sp_productid,        re.vasid,        re.cpid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.orderfee,        re.servicename,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '1'        and re.servicecode = reinfo.productid        and re.vasid = v.vasid        and lower(re.ordercode)='" + 
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       messageContent.toLowerCase() + "'";
     try
     {
       Object object = getJdbcTemplate().queryForObject(sql, new RowMapper() {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           SmsSenderDto bean = new SmsSenderDto();
           if (rs != null) {
             bean.setVasid(rs.getString("vasid"));
             bean.setSp_productid(rs.getString("sp_productid"));
             bean.setOrdercode(rs.getString("ordercode"));
             bean.setCancelordercode(rs.getString("cancelordercode"));
             bean.setOndemandcode(rs.getString("ondemandcode"));
             int orderfee = rs.getInt("orderfee");
             bean.setFee(DemandDao.this.format.format(orderfee / 100.0D).startsWith(".") ? "0" + DemandDao.this.format.format(orderfee / 100.0D) : DemandDao.this.format.format(orderfee / 100.0D));
             bean.setServiceId(rs.getString("serviceId"));
             bean.setServicename(rs.getString("servicename"));
             bean.setCpid(rs.getInt("cpid"));
             bean.setVaspid(rs.getString("vaspid"));
             return bean;
           }
           return new SmsSenderDto();
         }
       });
       
 
       if (object != null) {
         return (SmsSenderDto)object;
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
     return new SmsSenderDto();
   }
   
 
 
 
 
 
 
   public SmsSenderDto getDianBoByNumber(String serviceNumber)
   {
     String wheresql = "";
     String sql = "select reinfo.sp_productid,        re.servicename,        re.vaspid,        re.cpid,        re.vasid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.ondemandfee,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '2'        and re.vasid = v.vasid        and re.servicecode = reinfo.productid        and (reinfo.REQUESTACC = '" + 
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       serviceNumber + "' or re.vasid = '" + serviceNumber + "')";
     logger.info("=====>getDtoSql: " + sql);
     List beanlist = getJdbcTemplate().query(sql, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         SmsSenderDto bean = new SmsSenderDto();
         if (rs != null) {
           bean.setVasid(rs.getString("vasid"));
           bean.setSp_productid(rs.getString("sp_productid"));
           bean.setOrdercode(rs.getString("ordercode"));
           bean.setCancelordercode(rs.getString("cancelordercode"));
           bean.setOndemandcode(rs.getString("ondemandcode"));
           int ondemandfee = rs.getInt("ondemandfee");
           bean.setFee(DemandDao.this.format.format(ondemandfee / 100.0D).startsWith(".") ? "0" + DemandDao.this.format.format(ondemandfee / 100.0D) : DemandDao.this.format.format(ondemandfee / 100.0D));
           
 
           bean.setServiceId(rs.getString("serviceId"));
           bean.setServicename(rs.getString("servicename"));
           bean.setCpid(rs.getInt("cpid"));
           bean.setVaspid(rs.getString("vaspid"));
           return bean;
         }
         return new SmsSenderDto();
       }
     });
     
 
 
     if (beanlist.size() > 0) {
       return (SmsSenderDto)beanlist.get(0);
     }
     
     return new SmsSenderDto();
   }
   
 
   public Userservhistory getUserservhistoryBymttranid(String mttranid)
   {
     String sql = "select * from USER_SERV_HISTORY where reqid='" + mttranid + "'";
     logger.info("=====>sql:" + sql);
     try {
       Object object = getJdbcTemplate().queryForObject(sql, new RowMapper() {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           Userservhistory dto = new Userservhistory();
           if (rs != null) {
             dto.setMttranid(rs.getString("MTTRANID"));
             dto.setReqid(rs.getString("REQID"));
             dto.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
             dto.setServuniqueid(rs.getInt("SERVUNIQUEID"));
             dto.setStatus(rs.getString("STATUS"));
             dto.setSpid(rs.getString("SPID"));
             dto.setMessageid(rs.getString("MESSAGEID"));
             return dto;
           }
           return new DemandDto();
         }
       });
       
       if (object != null) {
         return (Userservhistory)object;
       }
     } catch (Exception e) {
       logger.info("=====>no record in db:");
     }
     return new Userservhistory();
   }
   
 
 
 
 
 
   public String getSp_url(String MTTRANID)
   {
     String url = "";
     String sql = "select va.reportmessageurl from VASP_RESERVE_INFO va,USER_SERV_HISTORY us where va.spid=us.spid and us.reqid='" + MTTRANID + "'";
     logger.info("=====>" + sql);
     List list = getJdbcTemplate().query(sql, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         return rs.getString(1);
       }
     });
     
     if ((list != null) && (list.size() > 0)) {
       url = list.get(0).toString();
     }
     return url;
   }
   
 
 
 
 
 
   public Vasservice queryProductByServiceCode(String ServiceCode)
   {
     String sql = "SELECT * FROM VASSERVICE WHERE STATUS='2' AND SERVICECODE = ?";
     List o = getJdbcTemplate().query(sql, new Object[] { ServiceCode }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         Vasservice ret = null;
         if (rs != null) {
           ret = new Vasservice();
           ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
           ret.setVaspid(rs.getString("vaspid"));
           ret.setVasid(rs.getString("vasid"));
           ret.setServicecode(rs.getString("servicecode"));
           ret.setFeetype(rs.getString("feetype"));
           ret.setOrderfee(Double.valueOf(rs.getDouble("orderfee")));
           ret.setIsPackage(rs.getString("isPackage"));
           ret.setProducttype(rs.getString("product_type"));
         }
         
         return ret;
       }
     });
     if ((o != null) && (o.size() > 0)) {
       return (Vasservice)o.get(0);
     }
     
     return null;
   }
   
 
 
 
 
 
   public Vasservice queryProductByServiceId(int serviceUniqueid)
   {
     String sql = "SELECT * FROM VASSERVICE WHERE STATUS='2' AND uniqueid = ?";
     List o = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(serviceUniqueid) }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         Vasservice ret = null;
         if (rs != null) {
           ret = new Vasservice();
           ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
           ret.setVaspid(rs.getString("vaspid"));
           ret.setVasid(rs.getString("vasid"));
           ret.setServicecode(rs.getString("servicecode"));
           ret.setFeetype(rs.getString("feetype"));
           ret.setOrderfee(Double.valueOf(rs.getDouble("orderfee")));
           ret.setIsPackage(rs.getString("isPackage"));
           ret.setProducttype(rs.getString("product_type"));
         }
         
         return ret;
       }
     });
     if ((o != null) && (o.size() > 0)) {
       return (Vasservice)o.get(0);
     }
     
     return null;
   }
   
 
 
 
 
 
   public void insertUserOrderHistory(MO_SMMessage msg, Vasservice vasservice)
   {
     List argsList = new ArrayList();
     String sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE) values(nextval FOR USER_ORDER_HIS_SEQ,?,?,?,current timestamp,?,?,?)";
     
 
     argsList.add(msg.getSendAddress());
     argsList.add(msg.getSendAddress());
     argsList.add(vasservice.getUniqueid());
     
     argsList.add("1");
     argsList.add(vasservice.getFeetype());
     argsList.add(vasservice.getOndemandfee() == null ? "" : vasservice.getOndemandfee());
     try {
       getJdbcTemplate().update(sql, argsList.toArray());
     } catch (Exception e) {
       e.printStackTrace();
       logger.error("保存用户定购记录出现异常", e);
     }
   }
   
 
 
 
   public Vasservice queryProductByDemandCode(String smsText)
   {
     String sql = "SELECT * FROM VASSERVICE WHERE STATUS='2' AND ONDEMANDCODE = ?";
     List o = getJdbcTemplate().query(sql, new Object[] { smsText }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         Vasservice ret = null;
         if (rs != null) {
           ret = new Vasservice();
           ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
           ret.setVaspid(rs.getString("vaspid"));
           ret.setVasid(rs.getString("vasid"));
           ret.setServicecode(rs.getString("servicecode"));
           ret.setFeetype(rs.getString("feetype"));
           ret.setOrderfee(Double.valueOf(rs.getDouble("orderfee")));
           ret.setIsPackage(rs.getString("isPackage"));
           ret.setProducttype(rs.getString("product_type"));
         }
         
         return ret;
       }
     });
     if ((o != null) && (o.size() > 0)) {
       return (Vasservice)o.get(0);
     }
     
     return null;
   }
   
 
 
 
 
   public ContentInfo queryContentInfoByProductID(Integer uniqueid)
   {
     String sql = "SELECT * FROM CONTENT_INFO WHERE STATUS='4' AND SERVICEID = ? order by CREATEDATE desc";
     List o = getJdbcTemplate().query(sql, new Object[] { uniqueid }, new RowMapper() {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         ContentInfo content = null;
         if (rs != null) {
           content = new ContentInfo();
           content.setContentid(Integer.valueOf(rs.getInt("contentid")));
           content.setContentname(rs.getString("contentname"));
           content.setContentpath(rs.getString("contentpath"));
           content.setContenttype(rs.getString("contenttype"));
           content.setValidstarttime(rs.getTimestamp("validstarttime"));
           content.setVersionId(Integer.valueOf(rs.getInt("version_id")));
           content.setAuthorname(rs.getString("authorname"));
           content.setSmsText(rs.getString("smstext"));
         }
         
         return content;
       }
     });
     if ((o != null) && (o.size() > 0)) {
       return (ContentInfo)o.get(0);
     }
     return null;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public Object[] judgeAnswer(String phone, String moText, String delim)
   {
     Object[] result = new Object[2];
     String[] codeAndAnswer = moText.split(delim);
     String code = codeAndAnswer[0];
     if (code != null) {
       code = code.toUpperCase();
     }
     
     int codeLength = code.length();
     
 
     String sql = "select * from answer_record t where upper(substr(answer,1,?)) = ?   and date(begin_date) <= current date and date(end_date) >= current date";
     logger.info(sql);
     
     List<AnswerRecord> results = getJdbcTemplate()
       .query(sql, new Object[] { Integer.valueOf(codeLength), code }, new RowMapper() {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           AnswerRecord answer = new AnswerRecord();
           answer.setAnswerId(rs.getInt(1));
           answer.setBeginDate(rs.getDate(2));
           answer.setEndDate(rs.getDate(3));
           answer.setAnswer(rs.getString(4));
           answer.setServiceId(rs.getInt(5));
           answer.setContentId(rs.getInt(6));
           return answer;
         }
       });
     
 
     if ((results == null) || (results.size() == 0)) {
       result[0] = Integer.valueOf(0);
       result[1] = null;
       return result;
     }
     result[0] = Integer.valueOf(1);
     
     result[1] = results.get(0);
     
     return result;
   }
   
 
 
 
 
 
   public boolean isReplied4Twice(String phone, String code, String delim, Date beginDate, Date endDate)
   {
     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     String dateStr = df.format(new Date());
     
     String sql = " select * from user_mo_history where cellphonenumber = cast(? as varchar(20)) and upper(substr(smstext,1,locate(cast(? as varchar(20)),smstext)-1)) = ? and iscorrect is not null";
     
     logger.info(sql);
     
     List results = getJdbcTemplate().query(sql, new Object[] { phone, delim, code.toUpperCase() }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException
       {
         return rs.getDate(5);
       }
     });
     
 
     if ((results == null) || (results.size() == 0)) {
       return false;
     }
     for (int i = 0; i < results.size(); i++) {
       Date motime = (Date)results.get(i);
       if ((motime != null) && 
         (compareDate(motime, beginDate) >= 0) && (compareDate(motime, endDate) <= 0)) {
         return true;
       }
     }
     
 
     return false;
   }
   
 
 
 
 
 
 
 
   public boolean existUserOrderRelation(String phonenumber, int serviceId)
   {
     String sql = " select * from user_order where cellphonenumber = ? and serviceuniqueid = ? ";
     List results = getJdbcTemplate().queryForList(sql, new Object[] { phonenumber, Integer.valueOf(serviceId) });
     if ((results == null) || (results.size() == 0)) {
       return false;
     }
     return true;
   }
   
 
 
 
 
 
 
 
 
   private int compareDate(Date date1, Date date2)
   {
     String format = "yyyy-MM-dd";
     DateFormat df = new SimpleDateFormat(format);
     return df.format(date1).compareTo(df.format(date2));
   }
   
   public String getSp_urlbySpid(String spid)
   {
     logger.info("=====>select reportmessageurl from VASP_RESERVE_INFO where spid=" + spid);
     
     String url = (String)getJdbcTemplate().queryForObject(
       "select reportmessageurl from VASP_RESERVE_INFO where spid=?", 
       new Object[] { spid }, 
       String.class);
     return url;
   }
   
 
   public DemandDto getDemandDto(String sendto)
   {
     String sql = "select vass.vaspid,vas.SERVICEID,vasri.SP_PRODUCTID,vasp.REPORTMESSAGEURL,vasri.NEEDCONFM from VAS vas,VASSERVICE vass,VASSERVICE_RESERVE_INFO vasri,VASP_RESERVE_INFO vasp where vass.SERVICECODE=vasri.PRODUCTID and vas.VASID=vass.VASID and vasp.spid=vass.vaspid and (vasri.requestacc='" + sendto + "' or vass.vasid='" + sendto + "') and(vass.FEETYPE='2' and vass.ondemandcode='') ";
     logger.info("=====>sql:" + sql);
     try {
       List results = getJdbcTemplate().query(sql, new RowMapper() {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           DemandDto dto = new DemandDto();
           if (rs != null) {
             dto.setServiceid(rs.getString("SERVICEID"));
             dto.setProductId(rs.getString("SP_PRODUCTID"));
             dto.setSpid(rs.getString("vaspid"));
             dto.setSpreporturl(rs.getString("REPORTMESSAGEURL"));
             dto.setNeedConfirm(rs.getString("NEEDCONFM"));
             return dto;
           }
           return null;
         }
       });
       if ((results != null) && (results.size() > 0)) {
         return (DemandDto)results.get(0);
       }
     } catch (Exception e) {
       e.printStackTrace();
       logger.info("=====>no record in db:");
     }
     return new DemandDto();
   }
   
 
   public DemandDto getDemandMomms(String sendto)
   {
     String sql = "select vass.vaspid,vas.SERVICEID,vasri.SP_PRODUCTID,vasp.REPORTMESSAGEURL,vasri.NEEDCONFM from VAS vas,VASSERVICE vass,VASSERVICE_RESERVE_INFO vasri,VASP_RESERVE_INFO vasp where vass.SERVICECODE=vasri.PRODUCTID and vas.VASID=vass.VASID and vasp.spid=vass.vaspid and (vasri.requestacc='" + sendto + "' or vass.vasid='" + sendto + "')";
     logger.info("=====>sql:" + sql);
     try {
       List results = getJdbcTemplate().query(sql, new RowMapper() {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           DemandDto dto = new DemandDto();
           if (rs != null) {
             dto.setServiceid(rs.getString("SERVICEID"));
             dto.setProductId(rs.getString("SP_PRODUCTID"));
             dto.setSpid(rs.getString("vaspid"));
             dto.setSpreporturl(rs.getString("REPORTMESSAGEURL"));
             dto.setNeedConfirm(rs.getString("NEEDCONFM"));
             return dto;
           }
           return null;
         }
       });
       if ((results != null) && (results.size() > 0)) {
         return (DemandDto)results.get(0);
       }
     } catch (Exception e) {
       e.printStackTrace();
       logger.info("=====>no record in db:");
     }
     return new DemandDto();
   }
 }





