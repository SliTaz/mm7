 package com.zbensoft.mmsmp.vas.sjb.data;
 
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtMode;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServMtModeId;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.ServiceCapacity;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.SysChannels;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.SysParameters;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.UserBlackWhiteList;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vas;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.VasReserveInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasp;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.VaspReserveInfo;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
 import com.zbensoft.mmsmp.common.ra.common.db.entity.VasserviceReserveInfo;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.Constants;
 import com.zbensoft.mmsmp.vas.sjb.unibusiness.dto.VasServiceDto;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.RowMapper;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 
 
 
 
 
 
 public class BusinessManageDAO
   extends JdbcDaoSupport
 {
   private static final Logger log = Logger.getLogger(BusinessManageDAO.class);
   
 
 
 
 
 
   public Vasservice getVASServiceByServiceCode(String serviceCode)
   {
     String sql = "select v.uniqueid,v.vaspid,v.vasid,v.servicecode,v.feetype,v.orderfee,v.ISPACKAGE as isPackage,v.product_type, va.serviceid from vasservice v, vas va where SERVICECODE = ? and v.vasid = va.vasid";
     List o = getJdbcTemplate().query(sql, new Object[] { serviceCode }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         Vasservice ret = null;
         if (rs != null) {
           ret = new Vasservice();
           ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
           ret.setVaspid(rs.getString("vaspid"));
           ret.setVasid(rs.getString("vasid").substring(0, 8));
           ret.setServicecode(rs.getString("serviceid"));
           
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
   
 
 
 
 
   public Vas getVASByVASID(String vasid)
   {
     String sql = "select uniqueid,vaspid,vasid,vasismpid from vas where VASISMPID = ?";
     List o = getJdbcTemplate().query(sql, new Object[] { vasid }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         Vas ret = null;
         if (rs != null) {
           ret = new Vas();
           ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
           ret.setVaspid(rs.getString("vaspid"));
           ret.setVasid(rs.getString("vasid"));
           ret.setVasIsmpId(rs.getString("vasismpid"));
         }
         
 
         return ret;
       }
     });
     if ((o != null) && (o.size() > 0)) {
       return (Vas)o.get(0);
     }
     
     return null;
   }
   
 
 
 
 
   public ServiceCapacity getServiceCapacityByCAPACITYID(String capacityID)
   {
     String sql = "select uniqueid,SP_ID as spId,CAPACITY_ID as capacityId from SERVICE_CAPACITY where CAPACITY_ID = ?";
     Object o = getJdbcTemplate().queryForObject(sql, new Object[] { capacityID }, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
         ServiceCapacity ret = null;
         if (rs != null) {
           ret = new ServiceCapacity();
           ret.setUniqueid(rs.getString("uniqueid"));
           ret.setSpId(rs.getString("spId"));
           ret.setCapacityId(rs.getString("capacityId"));
         }
         
         return ret;
       }
     });
     if (o != null) {
       return (ServiceCapacity)o;
     }
     
     return null;
   }
   
 
 
 
 
 
 
 
   public boolean updateByProduct(Vasservice product)
   {
     try
     {
    	 String updateSql = "update vasservice set status = '" + product.getStatus() + 
          "' where servicecode = '" + product.getServicecode() + "'";
        log.debug("update product/package:  " + updateSql);
        getJdbcTemplate().update(updateSql);
        log.debug("update product/package information success!");
       
 
       if (Constants.ManageRequest_Operate_Cancel == Integer.valueOf(product.getStatus()).intValue()) {
         String delSql = "delete from user_order o where o.serviceuniqueid = (select uniqueid from vasservice v where v.servicecode = '" + 
           product.getServicecode() + "')";
         log.debug("delete user_order relation: " + delSql);
         getJdbcTemplate().update(delSql);
         log.debug("delete all user_order relation   success!");
       }
       
 
       return true;
     } catch (Throwable e) {
       e.printStackTrace();
       log.error("updateByProduct error: " + e.getMessage()); }
     return false;
   }
   
 
 
 
 
 
 
 
 
   public boolean updateByVas(Vas vas)
   {
     try
     {
       String vasSql = "update vas v set v.status = " + vas.getStatus() + 
         " where vasismpid = '" + vas.getVasIsmpId() + "'";
       
       log.debug("update vas:  " + vasSql);
       getJdbcTemplate().update(vasSql);
       log.debug("update vas information success!!!");
       
 
       String updateSql = "update vasservice set status = '" + vas.getStatus() + 
         "' where vasnmscid = '" + vas.getVasIsmpId() + "'";
       
       log.debug("update product/package:  " + updateSql);
       getJdbcTemplate().update(updateSql);
       log.debug("update product/package information success!!!");
       
 
       if (Constants.ManageRequest_Operate_Cancel == Integer.valueOf(vas.getStatus()).intValue()) {
         String delSql = "delete from user_order o where o.serviceuniqueid in (select uniqueid from vasservice v where v.vasnmscid = '" + 
           vas.getVasIsmpId() + "')";
         log.debug("delete user_order relation" + 
           delSql);
         getJdbcTemplate().update(delSql);
         log.debug("delete all user_order relation   success!!!");
       }
       
       return true;
     } catch (Throwable e) {
       e.printStackTrace();
       log.error("updateByVas error: " + e.getMessage()); }
     return false;
   }
   
 
 
 
 
 
 
 
 
   public boolean updateByCapacity(ServiceCapacity serviceCapacity)
   {
     try
     {
       String capSql = "update service_capacity set status = '" + serviceCapacity.getStatus() + 
         "' where capacity_id = '" + serviceCapacity.getCapacityId() + "'";
       log.debug("update service_capacity status: " + capSql);
       getJdbcTemplate().update(capSql);
       log.debug("update service_capacity status success.");
       
 
       String vasSql = "update vas v set v.status = " + 
         serviceCapacity.getStatus() + 
         " where capacity_id =  '" + 
         serviceCapacity.getCapacityId() + "'";
       
       log.debug("update vas status ：" + vasSql);
       getJdbcTemplate().update(vasSql);
       log.debug("update vas status success.");
       
 
       String updateSql = "update vasservice set status = '" + 
         serviceCapacity.getStatus() + 
         "' where vasnmscid in (select vasnmscid from vas va where va.capacity_id =  '" + 
         serviceCapacity.getCapacityId() + "')";
       
       log.debug("update vasservice:  " + updateSql);
       getJdbcTemplate().update(updateSql);
       log.debug("update product/package information success!!!");
       
 
       if (Constants.ManageRequest_Operate_Cancel == Integer.valueOf(serviceCapacity.getStatus()).intValue()) {
         String delSql = "delete from user_order o where o.serviceuniqueid in (select uniqueid from vasservice v where v.vasnmscid in (select vasnmscid from vas va where va.capacity_id =   '" + 
         
           serviceCapacity.getCapacityId() + "'))";
         log.debug("delete user_order relation" + 
           delSql);
         getJdbcTemplate().update(delSql);
         log.debug("delete all user_order relation   success");
       }
       
       return true;
     } catch (Throwable e) {
       e.printStackTrace();
       log.debug("updateByCapacity error: " + e.getMessage()); }
     return false;
   }
   
 
 
 
 
 
 
   public boolean updateProductDealStatus(Integer dealStatus, Integer id)
   {
     Connection conn = null;
     PreparedStatement preStmt = null;
     
     String sql = "update vasservice  set dealstatus = ? where UNIQUEID = ?";
     
     try
     {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, dealStatus.intValue());
       preStmt.setInt(2, id.intValue());
       int updateRowNum = preStmt.executeUpdate();
       if (updateRowNum > 0) {
         return true;
       }
     }
     catch (Exception e) {
       e.printStackTrace();
       this.logger.error("set dealstatus error! sql: " + sql, e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     releaseConnection(conn);
     if (preStmt != null) {
       try {
         preStmt.close();
       } catch (SQLException e) {
         e.printStackTrace();
       }
     }
     
     return false;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public Integer getDealStatus(Integer id)
   {
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     String sql = "select dealstatus from vasservice  where UNIQUEID = ?";
     Integer ret = null;
     try
     {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, id.intValue());
       result = preStmt.executeQuery();
       
       if (result.next()) {
         ret = Integer.valueOf(result.getInt("dealstatus"));
       }
       return ret;
     }
     catch (Exception e) {
       e.printStackTrace();
       this.logger.error("get dealstatus error! sql: " + sql, e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
   
 
 
 
   public String getContentStatus(Integer id)
   {
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     String sql = "select status from Content_Info  where contentid = ?";
     String ret = null;
     try
     {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       preStmt.setInt(1, id.intValue());
       result = preStmt.executeQuery();
       
       if (result.next()) {
         ret = result.getString("status");
       }
       return ret;
     }
     catch (Exception e) {
       e.printStackTrace();
       this.logger.error("get content status error! sql: " + sql, e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
   
 
 
 
   public List<Vasservice> findNormalStatus()
   {
     try
     {
       String queryString = "select a.uniqueid,a.vaspid,a.vasid,a.servicecode,a.feetype,a.orderfee,a.ispackage as isPackage,a.product_type,a.servicename,a.ondemandfee,lower(a.ordercode) ordercode,lower(a.cancelordercode) cancelordercode,lower(a.ondemandcode) ondemandcode,b.recordsequenceid,b.spec_productid,b.sp_productid,b.confirm,b.presenter,b.confirmprompt,b.successprompt,b.cancelprompt,b.popularizestart,b.popularizestop,b.freeuseflag,b.freeusetime,b.billingid,b.discount_des,b.discountid,b.needconfm,b.maxusetimes,b.maxusetime,b.sendnum,b.ordercmdmatch,b.orderacc,b.orderaccmatch,b.cancelcmdmatch,b.cancelacc,b.cancelaccmatch,b.requestcmdmatch,b.requestacc,b.requestaccmatch,b.effdate,b.expdate,b.vacsub,b.notifytype,b.productcity,b.productperiodgrade,b.productservicegrade,b.productcredit,b.productstatus,b.productid,b.product_extend_id,b.product_expand_code,b.report_message_url,c.serviceid from vasservice a, vasservice_reserve_info b,vas c where a.servicecode = b.productid and a.vasid=c.vasid and   a.status ='2' and a.dealStatus = 0 or a.dealStatus =1";
       
 
 
 
 
 
 
 
 
       getJdbcTemplate().query(queryString, new RowMapper()
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
             ret.setProducttype(rs.getString("PRODUCT_TYPE"));
             
             ret.setServicename(rs.getString("servicename"));
             ret.setOndemandfee(Double.valueOf(rs.getDouble("ondemandfee")));
             ret.setOrdercode(rs.getString("ordercode"));
             ret.setCancelordercode(rs.getString("cancelordercode"));
             ret.setOndemandcode(rs.getString("ondemandcode"));
             
             ret.setServicedesc(rs.getString("serviceid"));
             
             VasserviceReserveInfo vasserviceReserveInfo = new VasserviceReserveInfo();
             vasserviceReserveInfo.setRecordsequenceid(rs.getString("recordsequenceid"));
             vasserviceReserveInfo.setSpecProductid(rs.getString("spec_productid"));
             vasserviceReserveInfo.setSpProductid(rs.getString("sp_productid"));
             vasserviceReserveInfo.setConfirm(Integer.valueOf(rs.getInt("confirm")));
             vasserviceReserveInfo.setPresenter(Integer.valueOf(rs.getInt("presenter")));
             vasserviceReserveInfo.setConfirmprompt(rs.getString("confirmprompt"));
             vasserviceReserveInfo.setSuccessprompt(rs.getString("successprompt"));
             vasserviceReserveInfo.setCancelprompt(rs.getString("cancelprompt"));
             vasserviceReserveInfo.setPopularizestart(rs.getString("popularizestart"));
             vasserviceReserveInfo.setPopularizestop(rs.getString("popularizestop"));
             vasserviceReserveInfo.setFreeuseflag(Integer.valueOf(rs.getInt("freeuseflag")));
             vasserviceReserveInfo.setFreeusetime(Integer.valueOf(rs.getInt("freeusetime")));
             vasserviceReserveInfo.setBillingid(rs.getString("billingid"));
             vasserviceReserveInfo.setDiscountDes(rs.getString("discount_des"));
             vasserviceReserveInfo.setDiscountid(rs.getString("discountid"));
             vasserviceReserveInfo.setNeedconfm(Integer.valueOf(rs.getInt("needconfm")));
             vasserviceReserveInfo.setMaxusetimes(Integer.valueOf(rs.getInt("maxusetimes")));
             vasserviceReserveInfo.setMaxusetime(Integer.valueOf(rs.getInt("maxusetime")));
             vasserviceReserveInfo.setSendnum(Integer.valueOf(rs.getInt("sendnum")));
             vasserviceReserveInfo.setOrdercmdmatch(Integer.valueOf(rs.getInt("ordercmdmatch")));
             vasserviceReserveInfo.setOrderacc(rs.getString("orderacc"));
             vasserviceReserveInfo.setOrderaccmatch(Integer.valueOf(rs.getInt("orderaccmatch")));
             vasserviceReserveInfo.setCancelcmdmatch(Integer.valueOf(rs.getInt("cancelcmdmatch")));
             vasserviceReserveInfo.setCancelacc(rs.getString("cancelacc"));
             vasserviceReserveInfo.setCancelaccmatch(Integer.valueOf(rs.getInt("cancelaccmatch")));
             vasserviceReserveInfo.setRequestcmdmatch(Integer.valueOf(rs.getInt("requestcmdmatch")));
             vasserviceReserveInfo.setRequestacc(rs.getString("requestacc"));
             vasserviceReserveInfo.setRequestaccmatch(Integer.valueOf(rs.getInt("requestaccmatch")));
             vasserviceReserveInfo.setEffdate(rs.getString("effdate"));
             vasserviceReserveInfo.setExpdate(rs.getString("expdate"));
             vasserviceReserveInfo.setVacsub(Integer.valueOf(rs.getInt("vacsub")));
             vasserviceReserveInfo.setNotifytype(Integer.valueOf(rs.getInt("notifytype")));
             vasserviceReserveInfo.setProductcity(rs.getString("productcity"));
             vasserviceReserveInfo.setProductperiodgrade(rs.getString("productperiodgrade"));
             vasserviceReserveInfo.setProductservicegrade(rs.getString("productservicegrade"));
             vasserviceReserveInfo.setProductcredit(Integer.valueOf(rs.getInt("productcredit")));
             vasserviceReserveInfo.setProductstatus(Integer.valueOf(rs.getInt("productstatus")));
             vasserviceReserveInfo.setProductid(rs.getString("productid"));
             vasserviceReserveInfo.setProductExtendId(Integer.valueOf(rs.getInt("product_extend_id")));
             vasserviceReserveInfo.setProductExpandCode(rs.getString("product_expand_code"));
             vasserviceReserveInfo.setReportMessageUrl(rs.getString("report_message_url"));
             ret.setVasserviceReserveInfo(vasserviceReserveInfo);
           }
           
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
 
 
   public List<Vasp> findSpInfo()
   {
     try
     {
       String queryString = "select a.vaspid,a.vaspname,a.province,b.record_sequence_id,b.sp_psedo_flag,b.spid,b.sp_city,b.is_trust,b.sp_order_url,b.order_key,b.syn_order_func,b.eff_date,b.expdate,b.reserve1,b.sp_extend_num,b.reportmessageurl from vasp a, vasp_reserve_info b where a.vaspid = b.spid";
       
 
 
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           Vasp ret = null;
           if (rs != null) {
             ret = new Vasp();
             ret.setVaspid(rs.getString("vaspid"));
             ret.setVaspname(rs.getString("vaspname"));
             ret.setProvince(rs.getString("province"));
             
             VaspReserveInfo info = new VaspReserveInfo();
             info.setRecordSequenceId(rs.getString("record_sequence_id"));
             info.setSpPsedoFlag(Integer.valueOf(rs.getInt("sp_psedo_flag")));
             info.setSpid(rs.getString("spid"));
             info.setSpCity(rs.getString("sp_city"));
             info.setIsTrust(Integer.valueOf(rs.getInt("is_trust")));
             info.setSpOrderUrl(rs.getString("sp_order_url"));
             info.setOrderKey(rs.getString("order_key"));
             info.setSynOrderFunc(Integer.valueOf(rs.getInt("syn_order_func")));
             info.setEffDate(rs.getString("eff_date"));
             info.setExpdate(rs.getString("expdate"));
             info.setReserve1(rs.getString("reserve1"));
             info.setSpExtendNum(rs.getString("sp_extend_num"));
             info.setReportmessageurl(rs.getString("reportmessageurl"));
             ret.setVaspReserveInfo(info);
           }
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
 
 
   public List<UserBlackWhiteList> findWhiteUserInfo()
   {
     try
     {
       String queryString = "select a.productid, a.cellphonenumber from USER_BLACKWHITELIST a";
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           UserBlackWhiteList ret = null;
           if (rs != null) {
             ret = new UserBlackWhiteList();
             ret.setProductId(rs.getString("productid"));
             ret.setCellphoneNumber(rs.getString("cellphoneNumber"));
           }
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
 
   public List<Vas> findVasInfo()
   {
     try
     {
       String queryString = "select a.vaspid,a.vasid,a.vasname,a.status,a.serviceid,b.recordsequenceid,b.spname,b.servicetype,b.groupservice,b.servicecompose,b.servicecredit,b.introurl,b.wapintropic,b.servicecolumn,b.enterurl,b.confirmurl,b.priceurl,b.freeurl,b.needconfmback,b.checktype,b.effdate,b.expdate,b.wapservicetype,b.sporderurl,b.synorderfunc,b.sppsedoflag,b.serviceid from vas a, vas_reserve_info b where a.serviceid = b.serviceid and a.status = 2";
       
 
 
 
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           Vas ret = null;
           if (rs != null) {
             ret = new Vas();
             ret.setVaspid(rs.getString("vaspid"));
             ret.setVasid(rs.getString("vasid"));
             ret.setVasname(rs.getString("vasname"));
             ret.setStatus(rs.getString("status"));
             ret.setServiceId(rs.getString("serviceid"));
             
             VasReserveInfo info = new VasReserveInfo();
             info.setRecordsequenceid(rs.getString("recordsequenceid"));
             info.setSpname(rs.getString("spname"));
             info.setServicetype(rs.getString("servicetype"));
             info.setGroupservice(Integer.valueOf(rs.getInt("groupservice")));
             info.setServicecompose(rs.getString("servicecompose"));
             info.setServicecredit(Integer.valueOf(rs.getInt("servicecredit")));
             info.setIntrourl(rs.getString("introurl"));
             info.setWapintropic(rs.getString("wapintropic"));
             info.setServicecolumn(rs.getString("servicecolumn"));
             info.setEnterurl(rs.getString("enterurl"));
             info.setConfirmurl(rs.getString("confirmurl"));
             info.setPriceurl(rs.getString("priceurl"));
             info.setFreeurl(rs.getString("freeurl"));
             info.setNeedconfmback(Integer.valueOf(rs.getInt("needconfmback")));
             info.setChecktype(Integer.valueOf(rs.getInt("checktype")));
             info.setEffdate(rs.getString("effdate"));
             info.setExpdate(rs.getString("expdate"));
             info.setWapservicetype(Integer.valueOf(rs.getInt("wapservicetype")));
             info.setSporderurl(rs.getString("sporderurl"));
             info.setSynorderfunc(Integer.valueOf(rs.getInt("synorderfunc")));
             info.setSppsedoflag(Integer.valueOf(rs.getInt("sppsedoflag")));
             info.setServiceid(rs.getString("serviceid"));
             ret.setVasReserveInfo(info);
           }
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
 
   public List<ServMtMode> findAllServMod()
   {
     try
     {
       String queryString = "select * from SERV_MT_MODE";
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           ServMtMode ret = null;
           if (rs != null) {
             ret = new ServMtMode();
             ServMtModeId id = new ServMtModeId();
             id.setIndexno(Integer.valueOf(rs.getInt("INDEXNO")));
             id.setServuniqueid(Integer.valueOf(rs.getInt("SERVUNIQUEID")));
             
             ret.setId(id);
             ret.setMtendtime1(rs.getString("MTENDTIME1"));
             ret.setMtendtime2(rs.getString("MTENDTIME2"));
             ret.setMtendtime3(rs.getString("MTENDTIME3"));
             ret.setMtendtime4(rs.getString("MTENDTIME4"));
             ret.setMtendtime5(rs.getString("MTENDTIME5"));
             
             ret.setMtstarttime1(rs.getString("MTSTARTTIME1"));
             ret.setMtstarttime2(rs.getString("MTSTARTTIME2"));
             ret.setMtstarttime3(rs.getString("MTSTARTTIME3"));
             ret.setMtstarttime4(rs.getString("MTSTARTTIME4"));
             ret.setMtstarttime5(rs.getString("MTSTARTTIME5"));
           }
           
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
 
   public List<String> getALLProvince()
   {
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     String sql = "select t.CODE from PROVINCE t where t.parent_code = '0000'";
     List<String> list = null;
     try {
       list = new ArrayList();
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       
       for (result = preStmt.executeQuery(); result.next();) {
         String areaCode = result.getString("CODE");
         
         list.add(areaCode);
       }
       
       return list;
     } catch (Exception e) {
       log.error("get province error! sql: ", e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
   
 
 
   public String getPlatFormModel()
   {
     String sql = "select value from sys_parameters t where t.name='PLATFORM_AREA'";
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     String ret = null;
     try {
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       result = preStmt.executeQuery();
       
       if (result.next()) {
         ret = result.getString("value");
       }
       return ret;
     }
     catch (Exception e) {
       e.printStackTrace();
       this.logger.error("get content status error! sql: " + sql, e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
   
 
   public List<SysParameters> findAllSysParams()
   {
     try
     {
       String queryString = "select * from sys_parameters";
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           SysParameters ret = null;
           if (rs != null) {
             ret = new SysParameters();
             ret.setName(rs.getString("NAME"));
             ret.setValue(rs.getString("VALUE"));
             ret.setDescription(rs.getString("DESCRIPTION"));
           }
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
   public List<SysChannels> findAllChannel(String area)
   {
     try
     {
       String queryString = "select * from sys_channels where areaid='" + area + "' and channel_status='1'";
       getJdbcTemplate().query(queryString, new RowMapper()
       {
         public Object mapRow(ResultSet rs, int arg1) throws SQLException {
           SysChannels ret = null;
           if (rs != null) {
             ret = new SysChannels();
             ret.setAreaID(rs.getString("AREAID"));
             ret.setChannelID(Integer.valueOf(rs.getInt("CHANNELID")));
             ret.setChannelName(rs.getString("CHANNEL_NAME"));
             ret.setChannelType(rs.getString("CHANNEL_TYPE"));
             ret.setChannelURL(rs.getString("CHANNEL_URL"));
             ret.setMultiNum(rs.getInt("MULTINUM"));
             ret.setStatus(rs.getString("CHANNEL_STATUS"));
             ret.setUserName(rs.getString("USERNAME"));
             ret.setUserPasswd(rs.getString("USERPASSWORD"));
             ret.setOperators(rs.getString("TELECOM_OPERATORS"));
           }
           return ret;
         }
       });
     }
     catch (RuntimeException re)
     {
       log.error("find by property name failed", re);
       throw re;
     }
	return null;
   }
   
 
   public List<String> getALLArea(String province)
   {
     Connection conn = null;
     PreparedStatement preStmt = null;
     ResultSet result = null;
     String sql = "select t.CODE from PROVINCE t where t.parent_code = '" + province + "'";
     List<String> list = null;
     try {
       list = new ArrayList();
       conn = getConnection();
       preStmt = conn.prepareStatement(sql);
       
       for (result = preStmt.executeQuery(); result.next();) {
         String areaCode = result.getString("CODE");
         
         list.add(areaCode);
       }
       
       return list;
     } catch (Exception e) {
       log.error("get province error! sql: ", e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     return null;
   }
   
 
 
 
 
 
   public boolean updateProductDealStatus(Integer dealStatus, List<Vasservice> list)
   {
     if ((list == null) || (list.size() == 0)) {
       return true;
     }
     
     Connection conn = null;
     PreparedStatement preStmt = null;
     
     StringBuffer sql = new StringBuffer("update vasservice  set dealstatus = ? where UNIQUEID in (");
     try
     {
       for (Vasservice s : list) {
         sql.append(s.getUniqueid()).append(" ,");
       }
       sql.replace(sql.length() - 1, sql.length(), " )");
       
       conn = getConnection();
       preStmt = conn.prepareStatement(sql.toString());
       preStmt.setInt(1, dealStatus.intValue());
       int updateRowNum = preStmt.executeUpdate();
       if (updateRowNum > 0) {
         return true;
       }
     }
     catch (Exception e) {
       e.printStackTrace();
       this.logger.error("set dealstatus error! sql: " + sql, e);
     } finally {
       releaseConnection(conn);
       if (preStmt != null) {
         try {
           preStmt.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
     }
     releaseConnection(conn);
     if (preStmt != null) {
       try {
         preStmt.close();
       } catch (SQLException e) {
         e.printStackTrace();
       }
     }
     
     return false;
   }
   
 
 
 
 
 
   public VasServiceDto findVasServiceByPid(String pid)
   {
     return null;
   }
 }





