 package com.zbensoft.mmsmp.corebiz.dao;
 
 import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasp;
import com.zbensoft.mmsmp.common.ra.common.db.entity.VaspReserveInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.corebiz.handle.impl.sms.SmsSenderDto;
import com.zbensoft.mmsmp.corebiz.message.MmsUserMessage;
 
 
 
 
 public class SmsDAO{} //extends JdbcDaoSupport
// {
//   private static final Logger log = Logger.getLogger(SmsDAO.class);
//   
//   static SimpleDateFormat formatDate = new SimpleDateFormat(
//     "yyyy-MM-dd HH:mm:ss");
//   
//   public String getSpIdByVasId(String spNumber)
//   {
//     String queryStr = "select distinct(vaspid) from VASSERVICE where vasid = '" + 
//       spNumber + "'";
//     
//     Connection conn = null;
//     PreparedStatement preStmt = null;
//     ResultSet result = null;
//     String ret = null;
//     try {
//       conn = getConnection();
//       preStmt = conn.prepareStatement(queryStr);
//       result = preStmt.executeQuery();
//       
//       if (result.next()) {
//         ret = result.getString("VASPID");
//       }
//       
//       return ret;
//     } catch (Exception e) {
//       e.printStackTrace();
//     } finally {
//       releaseConnection(conn);
//       if (preStmt != null) {
//         try {
//           preStmt.close();
//         } catch (SQLException e) {
//           e.printStackTrace();
//         }
//       }
//     }
//     return null;
//   }
//   
//   public List<Vasp> findOwnSpInfo() {
//     try {
//       String queryString = "select a.vaspid,a.vaspname,a.province,b.record_sequence_id,b.sp_psedo_flag,b.spid,b.sp_city,b.is_trust,b.sp_order_url,b.order_key,b.syn_order_func,b.eff_date,b.expdate,b.reserve1,b.sp_extend_num,b.reportmessageurl from vasp a, vasp_reserve_info b where a.vaspid = b.spid  and a.ISOWNBUSINESS = '1'";
//       
//       getJdbcTemplate().query(queryString, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           Vasp ret = null;
//           if (rs != null) {
//             ret = new Vasp();
//             ret.setVaspid(rs.getString("vaspid"));
//             ret.setVaspname(rs.getString("vaspname"));
//             ret.setProvince(rs.getString("province"));
//             
//             VaspReserveInfo info = new VaspReserveInfo();
//             info.setRecordSequenceId(rs
//               .getString("record_sequence_id"));
//             info.setSpPsedoFlag(Integer.valueOf(rs
//               .getInt("sp_psedo_flag")));
//             info.setSpid(rs.getString("spid"));
//             info.setSpCity(rs.getString("sp_city"));
//             info.setIsTrust(Integer.valueOf(rs.getInt("is_trust")));
//             info.setSpOrderUrl(rs.getString("sp_order_url"));
//             info.setOrderKey(rs.getString("order_key"));
//             info.setSynOrderFunc(Integer.valueOf(rs
//               .getInt("syn_order_func")));
//             info.setEffDate(rs.getString("eff_date"));
//             info.setExpdate(rs.getString("expdate"));
//             info.setReserve1(rs.getString("reserve1"));
//             info.setSpExtendNum(rs.getString("sp_extend_num"));
//             info.setReportmessageurl(rs
//               .getString("reportmessageurl"));
//             ret.setVaspReserveInfo(info);
//           }
//           return ret;
//         }
//       });
//     }
//     catch (RuntimeException re) {
//       log.error("find by property name failed", re);
//       throw re;
//     }
//	return null;
//   }
//   
//   public List<Vasp> findSpInfo() {
//     try {
//       String queryString = "select a.vaspid,a.vaspname,a.province,b.record_sequence_id,b.sp_psedo_flag,b.spid,b.sp_city,b.is_trust,b.sp_order_url,b.order_key,b.syn_order_func,b.eff_date,b.expdate,b.reserve1,b.sp_extend_num,b.reportmessageurl from vasp a, vasp_reserve_info b where a.vaspid = b.spid and a.ISOWNBUSINESS = '0'";
//       
//       getJdbcTemplate().query(queryString, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           Vasp ret = null;
//           if (rs != null) {
//             ret = new Vasp();
//             ret.setVaspid(rs.getString("vaspid"));
//             ret.setVaspname(rs.getString("vaspname"));
//             ret.setProvince(rs.getString("province"));
//             
//             VaspReserveInfo info = new VaspReserveInfo();
//             info.setRecordSequenceId(rs
//               .getString("record_sequence_id"));
//             info.setSpPsedoFlag(Integer.valueOf(rs
//               .getInt("sp_psedo_flag")));
//             info.setSpid(rs.getString("spid"));
//             info.setSpCity(rs.getString("sp_city"));
//             info.setIsTrust(Integer.valueOf(rs.getInt("is_trust")));
//             info.setSpOrderUrl(rs.getString("sp_order_url"));
//             info.setOrderKey(rs.getString("order_key"));
//             info.setSynOrderFunc(Integer.valueOf(rs
//               .getInt("syn_order_func")));
//             info.setEffDate(rs.getString("eff_date"));
//             info.setExpdate(rs.getString("expdate"));
//             info.setReserve1(rs.getString("reserve1"));
//             info.setSpExtendNum(rs.getString("sp_extend_num"));
//             info.setReportmessageurl(rs
//               .getString("reportmessageurl"));
//             ret.setVaspReserveInfo(info);
//           }
//           return ret;
//         }
//       });
//     }
//     catch (RuntimeException re) {
//       log.error("find by property name failed", re);
//       throw re;
//     }
//	return null;
//   }
//   
//
//   public void batchInsertMTRecords(List<com.zbensoft.mmsmp.corebiz.message.MmsHistoryMessage> historys)
//   {
//
//		String psql;
//		Connection conn;
//		PreparedStatement pstm;
//		psql = " insert into user_serv_history (status,mttime,cellphonenumber,contentid,servuniqueid,spid,messageid,servicecode) values('1',?,?,1,?,?,?,?)";
//		conn = null;
//		pstm = null;
//		try
//		{
//			conn = getConnection();
//			pstm = conn.prepareStatement(psql);
//			for (Iterator iterator = historys.iterator(); iterator.hasNext(); pstm.addBatch())
//			{
//				MmsHistoryMessage mmshis = (MmsHistoryMessage)iterator.next();
//				pstm.setString(1, formatDate.format(mmshis.getReceiveDate()));
//				pstm.setString(2, mmshis.getUserPhone());
//				pstm.setInt(3, Integer.parseInt(mmshis.getServiceId()));
//				pstm.setString(4, mmshis.getSpId());
//				pstm.setString(5, mmshis.getMessageId());
//				pstm.setString(6, mmshis.getServiceCode());
//			}
//
//			pstm.executeBatch();
//			break MISSING_BLOCK_LABEL_210;
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		try
//		{
//			pstm.close();
//		}
//		catch (Exception exception1) { }
//		releaseConnection(conn);
//		break MISSING_BLOCK_LABEL_227;
//		Exception exception;
//		exception;
//		try
//		{
//			pstm.close();
//		}
//		catch (Exception exception2) { }
//		releaseConnection(conn);
//		throw exception;
//		try
//		{
//			pstm.close();
//		}
//		catch (Exception exception3) { }
//		releaseConnection(conn);
//   }
//   
//   public int getThridOrderCount(String cellPhoneNumber, String productId)
//   {
//     String sql = "select count(*) from user_order_mmsmt where cellphonenumber = ? and productId = ?";
//     
//     return getJdbcTemplate().queryForInt(sql, 
//       new Object[] { cellPhoneNumber, productId });
//   }
//   
//   public List getThirdOrder()
//   {
//     String sql = "select * from user_order_mmsmt";
//     List list = getJdbcTemplate().query(sql, new Object[0], 
//       new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException
//         {
//           return 
//             rs.getString("cellphonenumber") + "|" + rs.getString("productId");
//         }
//         
//       });
//     return list;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public String[] getLatestMoOrderMsgText(String cellphonenumber, long deltaTime, String spNumber)
//   {
//     String sql = "select smstext,serviceactivationnumber from user_mo_history t where t.order_flag is not null and t.cellphonenumber = ? and t.serviceactivationnumber !=? order by t.id desc";
//     
// 
//     this.logger.info("=====sql:" + sql + "{" + cellphonenumber + "," + spNumber + 
//       "}");
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { cellphonenumber, spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException
//         {
//           String[] service = { "", "" };
//           service[0] = rs.getString("smstext");
//           service[1] = rs.getString("serviceactivationnumber");
//           return service;
//         }
//       });
//     
// 
//     if ((list != null) && (list.size() > 0)) {
//       return (String[])list.get(0);
//     }
//     return new String[] { "", "" };
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public void updateMmsGrsCode(String status, String messageid, String mmscode)
//   {
//     String sql = "update USER_SERV_HISTORY set status = ?, MMSGRESCODE = ? where messageid = ?";
//     this.logger.info(sql + " {" + status + "," + mmscode + "," + messageid + "}");
//     try {
//       getJdbcTemplate().update(sql, 
//         new Object[] { status, mmscode, messageid });
//     } catch (Exception e) {
//       e.printStackTrace();
//       this.logger.error("=====>update MT message Exception", e);
//     }
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public void updateSpMMSSendRecord(String status, String messageid, String reqid, String mmscode)
//   {
//     String sql = "update USER_SERV_HISTORY set status = ?, reqid = ?, MMSGRESCODE = ? where messageid = ?";
//     this.logger.info(sql + " {" + status + "," + reqid + "," + mmscode + "," + 
//       messageid + "}");
//     try {
//       getJdbcTemplate().update(sql, 
//         new Object[] { status, reqid, mmscode, messageid });
//     } catch (Exception e) {
//       e.printStackTrace();
//       this.logger.error("=====>update MT message Exception", e);
//     }
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public int updateGatewaySRecord(String status, String messageid, String reqid, String mmscode)
//   {
//     String sql = "update USER_SERV_HISTORY set status = ?, reqid = ?, MMSGRESCODE = ? where messageid = ?";
//     this.logger.info(sql + " {" + status + "," + reqid + "," + mmscode + "," + messageid + "}");
//     int result = -1;
//     try {
//       result = getJdbcTemplate().update(sql, new Object[] {
//         status, 
//         reqid, 
//         mmscode, 
//         messageid });
//     } catch (Exception e) {
//       e.printStackTrace();
//       this.logger.error("=====>update Gateway SRecord Exception", e);
//     } finally {
//       return result;
//     }
//   }
//   
// 
// 
// 
// 
// 
//   public String getConfirmmsgByProductid(String productId)
//   {
//     String confirmPrompt = "";
//     
// 
// 
//     String sql = "select confirmprompt from vasservice_reserve_info t where t.sp_productid = ?";
//     this.logger.info("=====>" + sql + " {" + productId + "}");
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { productId }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException
//         {
//           String confirm = rs.getString(1);
//           if ((confirm == null) || (confirm.trim().equals(""))) {
//             return "";
//           }
//           return confirm;
//         }
//       });
//     
//     if ((list != null) && (list.size() > 0)) {
//       confirmPrompt = list.get(0).toString();
//     }
//     return confirmPrompt;
//   }
//   
// 
// 
//   public String getSpurlByVaspid(String spid)
//   {
//     String sql = "select sp_order_url from vasp_reserve_info where spid = ?";
//     
//     List list = getJdbcTemplate().query(sql, new Object[] { spid }, 
//       new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1)
//           throws SQLException
//         {
//           return rs.getString("sp_order_url");
//         }
//         
//       });
//     return (list == null) || (list.size() == 0) ? null : (String)list.get(0);
//   }
//   
// 
//   public String getVasIDsByVaspID(String spid, String vasid)
//   {
//     String sql = "select VASID from VASSERVICE  where VASPID = ? and VASID=?";
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spid, vasid }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1)
//           throws SQLException
//         {
//           return rs.getString("VASID");
//         }
//         
//       });
//     return (list == null) || (list.size() == 0) ? null : (String)list.get(0);
//   }
//   
// 
// 
// 
// 
//   public String[] getLatestMoOrderMsgText(String phoneNumber)
//   {
//     String sql = "select smstext,serviceactivationnumber from user_mo_history t where t.order_flag is not null and t.cellphonenumber = ? and lower(t.SMSTEXT)!='y' order by id desc fetch first 1 rows only";
//     
// 
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { phoneNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException
//         {
//           String[] value = new String[2];
//           value[0] = rs.getString("smstext");
//           value[1] = rs.getString("serviceactivationnumber");
//           return value;
//         }
//         
//       });
//     return (list == null) || (list.size() == 0) ? null : 
//       (String[])list.get(0);
//   }
//   
//   public List<SmsSenderDto> getSmsSenderDto(String smstext) {
//     String sql = "select reinfo.sp_productid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.servicename,        re.orderfee,        re.ondemandfee,        re.uniqueid,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.servicecode = reinfo.productid        and re.vasid = v.vasid        and re.status='2'  ";
//     
// 
// 
// 
// 
// 
// 
// 
// 
//     this.logger.info("=====>" + sql);
//     
//     List list = getJdbcTemplate().query(sql, new RowMapper() {
//       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//         SmsSenderDto bean = new SmsSenderDto();
//         if (rs != null) {
//           bean.setSp_productid(rs.getString("sp_productid"));
//           bean.setOrdercode(rs.getString("ordercode"));
//           bean.setCancelordercode(rs.getString("cancelordercode"));
//           bean.setOndemandcode(rs.getString("ondemandcode"));
//           bean.setServiceId(rs.getString("serviceId"));
//           bean.setVaspid(rs.getString("vaspid"));
//           bean.setServicename(rs.getString("servicename"));
// //100.0D;
// //100.0D;
////           bean.setFee(String.valueOf(orderfee != 0.0D ? orderfee : 
////             ondemand));
//           bean.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
//           return bean;
//         }
//         return null;
//       }
//       
// 
//     });
//     List<SmsSenderDto> smsdtoList = new ArrayList();
//     if ((list != null) && (list.size() > 0)) {
//       for (int i = 0; i < list.size(); i++) {
//         SmsSenderDto dto = (SmsSenderDto)list.get(i);
//         if ((dto.getOndemandcode() != null) && 
//           (!"".equals(dto.getOndemandcode().trim()))) {
//           String ondemandcode = dto.getOndemandcode();
//           if ((ondemandcode.contains("#")) || 
//             (ondemandcode.contains("*"))) {
//             if (smstext.startsWith(ondemandcode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(1);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(ondemandcode)) {
//             dto.setType(1);
//             smsdtoList.add(dto);
//           }
//         }
//         
//         if ((dto.getOrdercode() != null) && 
//           (!"".equals(dto.getOrdercode().trim()))) {
//           String ordercode = dto.getOrdercode();
//           if ((ordercode.contains("#")) || (ordercode.contains("*"))) {
//             if (smstext.startsWith(ordercode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(2);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(ordercode)) {
//             dto.setType(2);
//             smsdtoList.add(dto);
//           }
//         }
//         
//         if ((dto.getCancelordercode() != null) && 
//           (!"".equals(dto.getCancelordercode().trim()))) {
//           String cancelordercode = dto.getCancelordercode();
//           if ((cancelordercode.contains("#")) || 
//             (cancelordercode.contains("*"))) {
//             if (smstext.startsWith(cancelordercode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(3);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(cancelordercode)) {
//             dto.setType(3);
//             smsdtoList.add(dto);
//           }
//         }
//       }
//     }
//     
//     return smsdtoList;
//   }
//   
//   public List<SmsSenderDto> getConfirmSmsSenderDto(String smstext) {
//     String sql = "select reinfo.sp_productid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.servicename,        re.orderfee,        re.ondemandfee,        re.uniqueid,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.servicecode = reinfo.productid        and re.vasid = v.vasid        and re.status='2'  and re.feetype='1'";
//     
// 
// 
// 
// 
// 
// 
// 
// 
//     this.logger.info("=====>" + sql);
//     
//     List list = getJdbcTemplate().query(sql, new RowMapper() {
//       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//         SmsSenderDto bean = new SmsSenderDto();
//         if (rs != null) {
//           bean.setSp_productid(rs.getString("sp_productid"));
//           bean.setOrdercode(rs.getString("ordercode"));
//           bean.setCancelordercode(rs.getString("cancelordercode"));
//           bean.setOndemandcode(rs.getString("ondemandcode"));
//           bean.setServiceId(rs.getString("serviceId"));
//           bean.setServicename(rs.getString("servicename"));
//// 100.0D;
//// 100.0D;
////           bean.setFee(String.valueOf(orderfee != 0.0D ? orderfee : 
////             ondemand));
//           bean.setVaspid(rs.getString("vaspid"));
//           bean.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
//           return bean;
//         }
//         return null;
//       }
//       
// 
//     });
//     List<SmsSenderDto> smsdtoList = new ArrayList();
//     if ((list != null) && (list.size() > 0)) {
//       for (int i = 0; i < list.size(); i++) {
//         SmsSenderDto dto = (SmsSenderDto)list.get(i);
//         if ((dto.getOrdercode() != null) && 
//           (!"".equals(dto.getOrdercode().trim()))) {
//           String ordercode = dto.getOrdercode().toLowerCase();
//           if ((ordercode.contains("#")) || (ordercode.contains("*"))) {
//             if (smstext.toLowerCase().startsWith(
//               ordercode.replace("#", "").replace("*", ""))) {
//               dto.setType(2);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.toLowerCase().equalsIgnoreCase(ordercode)) {
//             dto.setType(2);
//             smsdtoList.add(dto);
//           }
//         }
//       }
//     }
//     
//     return smsdtoList;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public List<SmsSenderDto> getSmsSenderDto(String spNumber, String smstext)
//   {
//     String sql = "select reinfo.sp_productid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.servicename,        re.orderfee,        re.ondemandfee,        re.uniqueid,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.servicecode = reinfo.productid        and re.vasid = v.vasid        and re.status='2' and re.vasid = ? ";
//     
// 
// 
// 
// 
// 
// 
// 
// 
//     this.logger.info("=====>" + sql + " {" + spNumber + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setSp_productid(rs.getString("sp_productid"));
//             bean.setVaspid(rs.getString("vaspid"));
//             bean.setOrdercode(rs.getString("ordercode"));
//             bean.setCancelordercode(rs
//               .getString("cancelordercode"));
//             bean.setOndemandcode(rs.getString("ondemandcode"));
//             bean.setServicename(rs.getString("servicename"));
//// 100.0D;
//// 100.0D;
////             bean.setFee(String.valueOf(orderfee != 0.0D ? orderfee : 
////               ondemand));
//             bean.setServiceId(rs.getString("serviceId"));
//             bean.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     List<SmsSenderDto> smsdtoList = new ArrayList();
//     this.logger.info("the size of list query from db    " + list.size());
//     if ((list != null) && (list.size() > 0)) {
//       for (int i = 0; i < list.size(); i++) {
//         SmsSenderDto dto = (SmsSenderDto)list.get(i);
//         if ((dto.getOndemandcode() != null) && 
//           (!"".equals(dto.getOndemandcode().trim()))) {
//           String ondemandcode = dto.getOndemandcode();
//           this.logger.info("the ondemandcode is :" + ondemandcode + 
//             "   smstext:" + smstext);
//           if ((ondemandcode.contains("#")) || 
//             (ondemandcode.contains("*"))) {
//             if (smstext.startsWith(ondemandcode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(1);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(ondemandcode)) {
//             dto.setType(1);
//             smsdtoList.add(dto);
//           }
//         }
//         
//         if ((dto.getOrdercode() != null) && 
//           (!"".equals(dto.getOrdercode().trim()))) {
//           String ordercode = dto.getOrdercode();
//           this.logger.info("the ondemandcode is :" + ordercode + 
//             "   smstext:" + smstext);
//           if ((ordercode.contains("#")) || (ordercode.contains("*"))) {
//             if (smstext.startsWith(ordercode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(2);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(ordercode)) {
//             dto.setType(2);
//             smsdtoList.add(dto);
//           }
//         }
//         
//         if ((dto.getCancelordercode() != null) && 
//           (!"".equals(dto.getCancelordercode().trim()))) {
//           String cancelordercode = dto.getCancelordercode();
//           this.logger.info("the ondemandcode is :" + cancelordercode + 
//             "   smstext:" + smstext);
//           if ((cancelordercode.contains("#")) || 
//             (cancelordercode.contains("*"))) {
//             if (smstext.startsWith(cancelordercode.replace("#", "")
//               .replace("*", ""))) {
//               dto.setType(3);
//               smsdtoList.add(dto);
//             }
//           }
//           else if (smstext.equalsIgnoreCase(cancelordercode)) {
//             dto.setType(3);
//             smsdtoList.add(dto);
//           }
//         }
//       }
//     }
//     
//     return smsdtoList;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public SmsSenderDto getSPInfo(String spNumber)
//   {
//     String sql = "select vasp.VASPNAME,VASP.BUSINESSPHONE,VASP.VASPID from VASP,vas v  where v.VASPID=VASP.VASPID and v.VASID=? fetch first 1 rows only";
//     
//     this.logger.info("=====>" + sql + " {" + spNumber + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setVaspid(rs.getString("VASPID"));
//             bean.setVaspname(rs.getString("VASPNAME"));
//             bean.setBusinessphone(rs.getString("BUSINESSPHONE"));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     SmsSenderDto dto = new SmsSenderDto();
//     if ((list != null) && (list.size() > 0))
//     {
//       dto.setVaspid(((SmsSenderDto)list.get(0)).getVaspid());
//       dto.setServiceId(((SmsSenderDto)list.get(0)).getServiceId());
//       dto.setVaspname(((SmsSenderDto)list.get(0)).getVaspname());
//       dto.setBusinessphone(((SmsSenderDto)list.get(0))
//         .getBusinessphone());
//     }
//     return dto;
//   }
//   
// 
// 
// 
// 
// 
// 
//   public SmsSenderDto getCPInfo(String spNumber)
//   {
//     String sql = " SELECT vasp.VASPNAME, cp.CLIENTLINKMANTEL, vasp.VASPID  FROM VASP vasp, VAS vas, VASSERVICE vass, CP_INFO cp  WHERE vasp.VASPID = vas.VASPID AND vas.VASID = vass.VASID AND vass.CPID = cp.CPID  AND vass.VASID = ? FETCH FIRST 1 ROWS ONLY ";
//     
// 
// 
//     this.logger.info("=====>" + sql + " {" + spNumber + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setVaspid(rs.getString("VASPID"));
//             bean.setVaspname(rs.getString("VASPNAME"));
//             bean.setBusinessphone(rs
//               .getString("CLIENTLINKMANTEL"));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     SmsSenderDto dto = new SmsSenderDto();
//     if ((list != null) && (list.size() > 0))
//     {
//       dto.setVaspid(((SmsSenderDto)list.get(0)).getVaspid());
//       dto.setServiceId(((SmsSenderDto)list.get(0)).getServiceId());
//       dto.setVaspname(((SmsSenderDto)list.get(0)).getVaspname());
//       dto.setBusinessphone(((SmsSenderDto)list.get(0))
//         .getBusinessphone());
//     }
//     return dto;
//   }
//   
// 
// 
// 
// 
// 
//   public List<SmsSenderDto> getCPSpVasserviceInfo()
//   {
//     try
//     {
//       String queryString = " SELECT distinct vass.VASID,vasp.VASPNAME, cp.CLIENTLINKMANTEL, vasp.VASPID  FROM VASP vasp, VAS vas, VASSERVICE vass, CP_INFO cp  WHERE vasp.VASPID = vas.VASPID AND vas.VASID = vass.VASID AND vass.CPID = cp.CPID ";
//       
// 
// 
//       getJdbcTemplate().query(queryString, 
//         new RowMapper()
//         {
//           public Object mapRow(ResultSet rs, int arg1) throws SQLException
//           {
//             SmsSenderDto bean = new SmsSenderDto();
//             if (rs != null) {
//               bean.setVaspid(rs.getString("VASPID"));
//               bean.setVaspname(rs.getString("VASPNAME"));
//               bean.setBusinessphone(rs
//                 .getString("CLIENTLINKMANTEL"));
//               bean.setVasid(rs.getString("VASID"));
//             }
//             return bean;
//           }
//         });
//     }
//     catch (RuntimeException re) {
//       log.error("find by property name failed", re);
//       throw re;
//     }
//	return null;
//   }
//   
// 
// 
// 
// 
// 
// 
//   public SmsSenderDto getSPInfoByProdId(String productId)
//   {
//     String sql = "select t1.VASPNAME,t1.BUSINESSPHONE,t1.VASPID from VASP t1,vasservice t2 , VASSERVICE_RESERVE_INFO t3  where t1.vaspid=t2.vaspid and t2.SERVICECODE=t3.PRODUCTID  and t3.SP_PRODUCTID=? fetch first 1 rows only";
//     
// 
//     this.logger.info("=====>" + sql + " {" + productId + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { productId }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setVaspid(rs.getString("VASPID"));
//             bean.setVaspname(rs.getString("VASPNAME"));
//             bean.setBusinessphone(rs.getString("BUSINESSPHONE"));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     SmsSenderDto dto = new SmsSenderDto();
//     if ((list != null) && (list.size() > 0))
//     {
//       dto.setVaspid(((SmsSenderDto)list.get(0)).getVaspid());
//       dto.setServiceId(((SmsSenderDto)list.get(0)).getServiceId());
//       dto.setVaspname(((SmsSenderDto)list.get(0)).getVaspname());
//       dto.setBusinessphone(((SmsSenderDto)list.get(0))
//         .getBusinessphone());
//     }
//     return dto;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public SmsSenderDto getDto(String spNumber)
//   {
//     String sql = "select reinfo.sp_productid, re.vaspid,re.cancelordercode, v.serviceid  ,vasp.vaspname,vasp.businessphone   from vasservice re, vas v ,vasservice_reserve_info reinfo ,vasp  where re.vasid = v.vasid and re.servicecode = reinfo.productid   and v.vaspid=vasp.vaspid  and re.status='2' and re.feetype='1' and re.vasid = ? ";
//     
// 
// 
// 
// 
//     this.logger.info("=====>" + sql + " {" + spNumber + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setVaspid(rs.getString("vaspid"));
//             bean.setServiceId(rs.getString("serviceId"));
//             bean.setSp_productid(rs.getString("sp_productid"));
//             bean.setCancelordercode(rs
//               .getString("cancelordercode"));
//             bean.setVaspname(rs.getString("vaspname"));
//             bean.setBusinessphone(rs.getString("businessphone"));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     SmsSenderDto dto = new SmsSenderDto();
//     if ((list != null) && (list.size() > 0)) {
//       String spproductids = "";
//       
//       for (int i = 0; i < list.size(); i++) {
//         SmsSenderDto s = (SmsSenderDto)list.get(i);
//         spproductids = spproductids + s.getSp_productid() + ",";
//         dto.getProducts().add(s);
//       }
//       
//       dto.setVaspid(((SmsSenderDto)list.get(0)).getVaspid());
//       dto.setServiceId(((SmsSenderDto)list.get(0)).getServiceId());
//       dto.setSp_productid(spproductids);
//       dto.setVaspname(((SmsSenderDto)list.get(0)).getVaspname());
//       dto.setBusinessphone(((SmsSenderDto)list.get(0))
//         .getBusinessphone());
//     }
//     
//     return dto;
//   }
//   
// 
// 
// 
// 
// 
//   public SmsSenderDto getVasSpCpInfo(String spNumber)
//   {
//     String sql = "select reinfo.sp_productid, re.vaspid,re.cancelordercode, v.serviceid  ,vasp.vaspname,cp.CLIENTLINKMANTEL  from vasservice re, vas v ,vasservice_reserve_info reinfo ,vasp, CP_INFO cp  where re.vasid = v.vasid and re.servicecode = reinfo.productid  and v.vaspid=vasp.vaspid and re.status='2' and re.feetype='1'  and re.vasid = ? and re.CPID=cp.CPID FETCH FIRST 1 ROWS ONLY ";
//     
// 
// 
// 
// 
//     this.logger.info("=====>" + sql + " {" + spNumber + "}");
//     
//     List list = getJdbcTemplate().query(sql, 
//       new Object[] { spNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           SmsSenderDto bean = new SmsSenderDto();
//           if (rs != null) {
//             bean.setVaspid(rs.getString("vaspid"));
//             bean.setServiceId(rs.getString("serviceId"));
//             bean.setSp_productid(rs.getString("sp_productid"));
//             bean.setCancelordercode(rs
//               .getString("cancelordercode"));
//             bean.setVaspname(rs.getString("vaspname"));
//             bean.setBusinessphone(rs
//               .getString("CLIENTLINKMANTEL"));
//             return bean;
//           }
//           return null;
//         }
//         
// 
//       });
//     SmsSenderDto dto = new SmsSenderDto();
//     if ((list != null) && (list.size() > 0)) {
//       String spproductids = "";
//       
//       for (int i = 0; i < list.size(); i++) {
//         SmsSenderDto s = (SmsSenderDto)list.get(i);
//         spproductids = spproductids + s.getSp_productid() + ",";
//         dto.getProducts().add(s);
//       }
//       
//       dto.setVaspid(((SmsSenderDto)list.get(0)).getVaspid());
//       dto.setServiceId(((SmsSenderDto)list.get(0)).getServiceId());
//       dto.setSp_productid(spproductids);
//       dto.setVaspname(((SmsSenderDto)list.get(0)).getVaspname());
//       dto.setBusinessphone(((SmsSenderDto)list.get(0))
//         .getBusinessphone());
//     }
//     return dto;
//   }
//   
//   public void saveDemandMessage(MO_SMMessage mosms, String provinceCode, String cityCode)
//   {
//     if (provinceCode == null) {
//       provinceCode = getPlatformArea();
//     }
//     if (cityCode == null) {
//       cityCode = provinceCode + "99";
//     }
//     String[] values = mosms.getServiceCode().split("#");
//     this.logger.info("==============uniqueid:" + values[5] + "   fee:" + 
//       values[4]);
//     try {
//       String sql = "select NEXT VALUE FOR USER_ORDER_HIS_SEQ from sysibm.sysdummy1";
//       int hisID = getJdbcTemplate().queryForInt(sql);
//       this.logger.info("the next hisid is " + hisID);
//       
//       sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD,FEETYPE, FEE,SP_ORDERID) values(?,?,?,?,current timestamp,?,?,?,?)";
//       
//       int number = getJdbcTemplate().update(
//         sql, 
//         new Object[] { Integer.valueOf(hisID), mosms.getSendAddress(), 
//         mosms.getSendAddress(), values[5], "4", "2", 
//         values[4], mosms.getLinkId() });
//       this.logger.info("the number of dianbo is " + number);
//       insertUserInfo(mosms.getSendAddress(), null, provinceCode, 
//         cityCode);
//     } catch (Exception e) {
//       e.printStackTrace();
//     }
//   }
//   
//   public void saveOrderMessage(MO_SMMessage mosms, String provinceCode, String cityCode, String notifyflag)
//   {
//     if (provinceCode == null) {
//       provinceCode = getPlatformArea();
//     }
//     if (cityCode == null) {
//       cityCode = provinceCode + "99";
//     }
//     String[] values = mosms.getServiceCode().split("#");
//     
//     String sql = "select NEXT VALUE FOR USER_ORDER_HIS_SEQ from sysibm.sysdummy1";
//     int hisID = getJdbcTemplate().queryForInt(sql);
//     
//     sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE,SP_ORDERID,notify_sp_flag) values(?,?,?,?,current timestamp,?,?,?,?,?)";
//     
//     getJdbcTemplate().update(
//       sql, 
//       new Object[] { Integer.valueOf(hisID), mosms.getSendAddress(), 
//       mosms.getSendAddress(), values[5], "4", "1", values[4], 
//       "", notifyflag });
//     this.logger.info("insert user_order_his sql :" + sql + "{" + hisID + "," + 
//       mosms.getSendAddress() + "," + mosms.getSendAddress() + "," + 
//       values[5] + ",4,1," + values[4] + ",," + notifyflag);
//     
//     sql = "insert into USER_ORDER(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,PRIORITY,ISPACKAGE,USERAREA,SP_ORDERID,notify_sp_flag) values(?,?,?,current timestamp,?,?,?,?,?,?,?,?,?,?)";
//     
// 
//     getJdbcTemplate().update(
//       sql, 
//       new Object[] { mosms.getSendAddress(), mosms.getSendAddress(), 
//       values[5], "4", "1", values[4], Integer.valueOf(hisID), Integer.valueOf(0), Integer.valueOf(2), "0", 
//       provinceCode, "", notifyflag });
//     this.logger.info("insert user_order sql :" + sql + "{" + 
//       mosms.getSendAddress() + "," + mosms.getSendAddress() + "," + 
//       values[5] + ",4,1," + values[4] + "," + hisID + ",0,2,0," + 
//       provinceCode + ",," + notifyflag);
//     insertUserInfo(mosms.getSendAddress(), null, provinceCode, 
//       cityCode);
//   }
//   
// 
// 
// 
// 
//   public void updateOrderMessage(MO_SMMessage mosms)
//   {
//     String sql = "update user_order set notify_sp_flag='0' where cellphonenumber=? and SERVICEUNIQUEID=?";
//     int num = getJdbcTemplate().update(
//       sql, 
//       new Object[] { mosms.getSendAddress(), 
//       mosms.getServiceCode().split("#")[5] });
//     if (num == 1)
//     {
// 
// 
// 
// 
//       String sql1 = "select ORDERHISID from user_order where cellphonenumber=? and SERVICEUNIQUEID=?";
//       int hisid = getJdbcTemplate().queryForInt(
//         sql1, 
//         new Object[] { mosms.getSendAddress(), 
//         mosms.getServiceCode().split("#")[5] });
//       
// 
// 
//       String sql2 = "update user_order_his set notify_sp_flag='0' where uniqueid=?";
//       getJdbcTemplate().update(sql2, new Object[] { Integer.valueOf(hisid) });
//     }
//   }
//   
// 
// 
//   public void updateOrderRelation(String userId, String serviceuniqueid)
//   {
//     String sql = "update user_order set notify_sp_flag='0' where cellphonenumber=? and SERVICEUNIQUEID=?";
//     int num = getJdbcTemplate().update(sql, 
//       new Object[] { userId, serviceuniqueid });
//     if (num == 1)
//     {
// 
// 
//       String sql1 = "select ORDERHISID from user_order where cellphonenumber=? and SERVICEUNIQUEID=?";
//       int hisid = getJdbcTemplate().queryForInt(sql1, 
//         new Object[] { userId, serviceuniqueid });
//       
// 
// 
//       String sql2 = "update user_order_his set notify_sp_flag='0' where uniqueid=?";
//       getJdbcTemplate().update(sql2, new Object[] { Integer.valueOf(hisid) });
//     }
//   }
//   
//   private String getPlatformArea() {
////     String sql = " select value from  sys_parameters where name='PLATFORM_AREA'";
////     this.logger.info("=====>" + sql);
////     ParameterizedRowMapper<Object> rm = new ParameterizedRowMapper() {
////       public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
////         Object obj = rs.getString("value");
////         return obj;
////       }
////     };
////     String areaName = (String)getJdbcTemplate().queryForObject(sql,  rm);
////     if ((areaName == null) || (areaName.length() == 0)) {
////       this.logger.error("areaName in sys parameter is null ! set it first before run the program!");
////     }
////     areaName = areaName.substring(0, areaName.indexOf('@'));
//     return "Error";//areaName;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
//   public int insertUserInfo(String number, String uaType, String provinceCode, String cityCode)
//   {
//     UserInfo user = getUserInfo(number);
//     if (user != null) {
//       return 0;
//     }
//     
//     if (uaType == null) {
//       uaType = "0";
//     }
//     
// 
// 
// 
// 
// 
// 
// 
// 
// 
//     String sql = "insert into user_info (userid,cellphonenumber,TERMINALTYPE,status,provincecode,citycode,CREATEDATE, UA_TYPE_ID,DELETE_FLAG) values ( NEXT VALUE FOR USER_INFO_SEQ,?,'2','1',?,?,current timestamp,cast(? as DECIMAL),'0')";
//     
//     int flag = getJdbcTemplate().update(sql, 
//       new Object[] { number, provinceCode, cityCode, uaType });
//     return flag;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
//   public UserInfo getUserInfo(String number)
//   {
//     String sql = "select USERID from  user_info where CELLPHONENUMBER = ?";
//     List userList = getJdbcTemplate().query(sql, 
//       new Object[] { number }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           UserInfo ret = null;
//           if (rs != null) {
//             ret = new UserInfo();
//             ret.setUserid(Integer.valueOf(rs.getInt("USERID")));
//           }
//           return ret;
//         }
//       });
//     if ((userList != null) && (userList.size() > 0)) {
//       return (UserInfo)userList.get(0);
//     }
//     return null;
//   }
//   
//   public String[] getServiceNameByAcc(String vasId) {
//     String sql = "select vaspid,vasname from  vas where vasid = ?";
//     List userList = getJdbcTemplate().query(sql, 
//       new Object[] { vasId }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           String[] spidandvasname = new String[2];
//           if (rs != null) {
//             spidandvasname[0] = rs.getString("vaspid");
//             spidandvasname[1] = rs.getString("vasname");
//           }
//           return spidandvasname;
//         }
//       });
//     if ((userList != null) && (userList.size() > 0)) {
//       return (String[])userList.get(0);
//     }
//     return null;
//   }
//   
//   public void delAllOrderRelation(String sendAddress) {
//     String sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where feetype='1' and CANCELORDERDATE is null and  cellphonenumber = ?";
//     getJdbcTemplate().update(sql, new Object[] { sendAddress });
//     
//     String delSql = "delete from USER_ORDER where CELLPHONENUMBER = ? ";
//     getJdbcTemplate().update(delSql, new Object[] { sendAddress });
//   }
//   
// 
// 
// 
// 
// 
// 
//   public void delServiceOrderRelation(String sendAddress, String vasId)
//   {
//     try
//     {
//       String sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where CANCELORDERDATE is null and  cellphonenumber = ? and serviceuniqueid=any(select uniqueid from  vasservice where feetype='1' and  vasid=? )";
//       
// 
//       getJdbcTemplate().update(sql, 
//         new Object[] { sendAddress, vasId });
//       
// 
// 
// 
// 
// 
//       String delsql = "delete from USER_ORDER where cellphonenumber = ? and serviceuniqueid=any(select uniqueid from  vasservice where feetype='1' and  vasid=? )";
//       
// 
//       getJdbcTemplate().update(delsql, 
//         new Object[] { sendAddress, vasId });
//     } catch (Exception e) {
//       this.logger.info("cancel_service error", e);
//     }
//   }
//   
//   public static void main(String[] args) {
//     ApplicationContext ctx = new FileSystemXmlApplicationContext("applicationContext.xml");
//     SmsDAO smsdao = (SmsDAO)ctx.getBean("smsDAO");
//     
// 
// 
// 
// 
//     smsdao.delServiceOrderRelation("18635134278", "1065556500101");
//   }
//   
// 
// 
// 
// 
//   public List<String> getProductId(String vasId)
//   {
//     String sql = "select reinfo.sp_productid  from vasservice re,vasservice_reserve_info reinfo   where re.servicecode = reinfo.productid and re.status='2' and re.feetype='1' and re.vasid = ? ";
//     
//     List productidList = getJdbcTemplate().query(sql, 
//       new Object[] { vasId }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           String productid = null;
//           if (rs != null) {
//             productid = rs.getString("sp_productid");
//           }
//           return productid;
//         }
//       });
//     if ((productidList != null) && (productidList.size() > 0)) {
//       return productidList;
//     }
//     return null;
//   }
//   
//   public List<Vasservice> getVasservice(String sendAddress)
//   {
//     String sql = "select v.vaspid, vinfo.sp_productid from user_order uo, vasservice v,vasservice_reserve_info vinfo where uo.serviceuniqueid=v.uniqueid and  v.servicecode = vinfo.productid and v.status='2' and v.feetype='1' and uo.cellphonenumber=? ";
//     
//     List vasList = getJdbcTemplate().query(sql, 
//       new Object[] { sendAddress }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           Vasservice vas = new Vasservice();
//           if (rs != null) {
//             vas.setVaspid(rs.getString("vaspid"));
//             vas.setServicecode(rs.getString("sp_productid"));
//           }
//           return vas;
//         }
//       });
//     if ((vasList != null) && (vasList.size() > 0)) {
//       return vasList;
//     }
//     return null;
//   }
//   
//   public String getProductIds(String phoneNumber) {
//     String sql = " select reinfo.sp_productid   from user_order uo, vasservice re,vasservice_reserve_info reinfo   where uo.serviceuniqueid=re.uniqueid and re.servicecode = reinfo.productid   and re.status='2' and re.feetype='1' and uo.cellphonenumber = ? ";
//     
// 
// 
//     List idList = getJdbcTemplate().query(sql, 
//       new Object[] { phoneNumber }, new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//           String id = "";
//           if (rs != null) {
//             id = rs.getString("sp_productid");
//           }
//           return id;
//         }
//       });
//     if ((idList != null) && (idList.size() > 0)) {
//       String ids = "";
//       for (int i = 0; i < idList.size(); i++) {
//         String productid = (String)idList.get(i);
//         ids = ids + productid + ",";
//       }
//       return ids;
//     }
//     return null;
//   }
//   
//   	/**
//   	 * 	获取产品用户订购产品
//   	 * @param phoneNumber
//   	 * @return
//   	 */
//	public List<String[]> getProduct00000(String phoneNumber) {
//		String sql = " select re.CANCELORDERCODE,re.vasid,reinfo.sp_productid   from user_order uo, vasservice re,vasservice_reserve_info reinfo   where uo.serviceuniqueid=re.uniqueid and re.servicecode = reinfo.productid   and re.status='2' and re.feetype='1' and uo.cellphonenumber = ? ";
//
//		List idList = getJdbcTemplate().query(sql, new Object[] { phoneNumber }, new RowMapper() {
//			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//				String[] data = new String[3];
//				if (rs != null) {
//					data[0] = rs.getString("sp_productid");
//					data[1] = rs.getString("CANCELORDERCODE");
//					data[2] = rs.getString("vasid");
//				}
//				return data;
//			}
//		});
//		List<String[]> productCmd = new ArrayList();
//		if ((idList != null) && (idList.size() > 0)) {
//			for (int i = 0; i < idList.size(); i++) {
//				productCmd.add((String[]) idList.get(i));
//			}
//			return productCmd;
//		}
//		return null;
//	}
//   
//
//   public java.util.Map[] getWhiteList()
//   {
//	return null;
//
//   }
//   
//   public void insertPauseUser(MmsUserMessage user)
//   {
//     String sql = " insert into user_send_temp(contentid,serviceid,vasid,vaspid,productid,usernumber,chargenumber,contentname,contentpath,sendaddress) values(?,?,?,?,?,?,?,?,?,?)";
//     getJdbcTemplate().update(
//       sql, 
//       new Object[] { Long.valueOf(user.getContentId()), Long.valueOf(user.getServiceId()), 
//       user.getVasId(), user.getVaspId(), user.getProductId(), 
//       user.getUserNumber(), user.getChargeNumber(), 
//       user.getContentName(), user.getContentFile(), 
//       user.getSendAddress() });
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
//   public UserOrder getUserOrder(String cellPhoneNumber, String serviceUniqueid)
//   {
//     String sql = "select * from USER_ORDER order where order.CELLPHONENUMBER = ? and order.SERVICEUNIQUEID = ?";
//     List userList = getJdbcTemplate().query(sql, 
//       new Object[] { cellPhoneNumber, serviceUniqueid }, 
//       new RowMapper()
//       {
//         public Object mapRow(ResultSet rs, int arg1)
//           throws SQLException
//         {
//           UserOrder userOrder = null;
//           if (rs != null) {
//             userOrder = new UserOrder();
//             UserOrderId id = new UserOrderId();
//             id.setCellphonenumber(rs
//               .getString("CELLPHONENUMBER"));
//             id.setServiceuniqueid(Integer.valueOf(rs.getInt("SERVICEUNIQUEID")));
//             userOrder.setId(id);
//             userOrder.setChargeparty(rs
//               .getString("CHARGEPARTY"));
//             userOrder.setOrderdate(rs.getDate("ORDERDATE"));
//             userOrder.setOrdermethod(rs
//               .getString("ORDERMETHOD"));
//             userOrder.setFeetype(rs.getString("FEETYPE"));
//             userOrder.setFee(Double.valueOf(rs.getDouble("FEE")));
//             userOrder.setOrderhisid(Integer.valueOf(rs.getInt("ORDERHISID")));
//             userOrder.setFeaturestr(rs.getString("FEATURESTR"));
//             userOrder.setStatus(Integer.valueOf(rs.getInt("STATUS")));
//             userOrder.setVersion(rs.getString("VERSION"));
//             userOrder.setNodisturbtime(rs
//               .getString("NODISTURBTIME"));
//             userOrder.setPriority(Integer.valueOf(rs.getInt("PRIORITY")));
//             userOrder.setUserarea(rs.getString("USERAREA"));
//           }
//           return userOrder;
//         }
//       });
//     if ((userList != null) && (userList.size() > 0)) {
//       return (UserOrder)userList.get(0);
//     }
//     return null;
//   }
//   
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
// 
//   public boolean saveUserZSMTRecord(String userphone, String chargeParty, String transactionid, String spid, String serviceid, String messageid, String servicecode)
//   {
//     this.logger.info("userphone:" + userphone + " transactionid: " + 
//       transactionid + " spid: " + spid + " serviceid: " + serviceid + 
//       " messageid: " + messageid + " servicecode: " + servicecode);
//     
//     String sql = "insert into user_serv_history (status,mttime,cellphonenumber,chargeParty,contentid,servuniqueid,spid,messageid,servicecode) values('1',?,?,?,1,?,?,?,?)";
//     
//     SimpleDateFormat formatDate = new SimpleDateFormat(
//       "yyyy-MM-dd HH:mm:ss");
//     int result = getJdbcTemplate().update(
//       sql, 
//       new Object[] { formatDate.format(new Date()), userphone, 
//       chargeParty, Integer.valueOf(Integer.parseInt(serviceid)), spid, 
//       messageid, servicecode });
//     return result > 0;
//   }
// }





