

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.corebiz.message.ProxyPayMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.UserOrderPayEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProxyPayMessageDao extends JdbcDaoSupport {
    private static final Log log = LogFactory.getLog(ProxyPayMessageDao.class);

    public ProxyPayMessageDao() {
    }

    public void insert(ProxyPayMessage ppm) {
//        try {
//            String sql = " insert into MMSP_PROXYPAYMESSAGE (GLOBALMESSAGEID,PHONENUMBER,CHARGEPARTY ,CPID,SERVICEID,SERVICENAME,FEETYPE,FEE,ACCOUNTID,STATUS,VALIDATECODE,SPID,USERTYPE,PERORID,PRODUCTID ,PRODUCTNAME,CREATEDATE,UPDATEDATE,SOURCE_TYPE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            this.getJdbcTemplate().update(sql, new Object[]{ppm.getGlobalMessageid(), ppm.getPhoneNumber(), ppm.getPhoneNumber(), ppm.getCpID(), ppm.getServiceId(), ppm.getServiceName(), ppm.getFeeType(), ppm.getFee(), ppm.getAccountId(), ppm.getStatus(), ppm.getValidateCode(), ppm.getSpId(), ppm.getUserType(), ppm.getPerorid(), ppm.getProductId(), ppm.getProductName(), ppm.getCreateDate(), ppm.getUpdateDate(), ppm.getSourceType()});
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//        }

    }

    public void insertUserOrderPay(UserOrderPayEntity entity) {
//        try {
//            String sql = "insert into USER_ORDER_PAY(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, GLOBALMESSAGEID, STATUS,PRIORITY,ISPACKAGE,USERAREA,SP_ORDERID,notify_sp_flag) values(?,?,?,current timestamp,?,?,?,?,?,?,?,?,?,?)";
//            this.getJdbcTemplate().update(sql, new Object[]{entity.getCellPhonenum(), entity.getCellPhonenum(), entity.getServiceUniqueid(), "4", "1", entity.getFee(), entity.getGlobalMessageid(), 0, 2, "0", "", "", ""});
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//        }

    }

    public void deleteUserOrderPay(UserOrderPayEntity entity) {
//        try {
//            String sql = "delete from USER_ORDER_PAY where CELLPHONENUMBER =? and SERVICEUNIQUEID = ?";
//            this.getJdbcTemplate().update(sql, new Object[]{entity.getCellPhonenum(), entity.getServiceUniqueid()});
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//        }

    }

    public void update(ProxyPayMessage proxyPayMessage) {
//        String sql = "update MMSP_PROXYPAYMESSAGE set  UPDATEDATE = ?";
//        Object[] param = new Object[]{DateUtil.getFormatDate()};
//        Object[] destParam;
//        if (StringUtils.isNotBlank(proxyPayMessage.getStatus())) {
//            sql = sql + " ,STATUS = ?";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getStatus();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getFeeType())) {
//            sql = sql + " ,FEETYPE = ?";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getFeeType();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (0.0D != proxyPayMessage.getFee()) {
//            sql = sql + " ,FEE = ?";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getFee();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (proxyPayMessage != null && proxyPayMessage.getServiceId() > 0) {
//            sql = sql + " ,SERVICEID = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getServiceId();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getServiceName())) {
//            sql = sql + " ,SERVICENAME = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getServiceName();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getSpId())) {
//            sql = sql + " ,SPID = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getSpId();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getProductId())) {
//            sql = sql + " ,PRODUCTID = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getProductId();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getProductName())) {
//            sql = sql + " ,PRODUCTNAME = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getProductName();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getChargeParty())) {
//            sql = sql + " ,CHARGEPARTY = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getChargeParty();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getPerorid())) {
//            sql = sql + " ,PERORID = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getPerorid();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getUserType())) {
//            sql = sql + " ,USERTYPE = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getUserType();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        if (StringUtils.isNotBlank(proxyPayMessage.getCpID())) {
//            sql = sql + " ,CPID = ? ";
//            destParam = new Object[param.length + 1];
//            destParam[param.length] = proxyPayMessage.getCpID();
//            System.arraycopy(param, 0, destParam, 0, param.length);
//            param = destParam;
//        }
//
//        sql = sql + " where GLOBALMESSAGEID = ? ";
//        destParam = new Object[param.length + 1];
//        destParam[param.length] = proxyPayMessage.getGlobalMessageid();
//        System.arraycopy(param, 0, destParam, 0, param.length);
//        this.getJdbcTemplate().update(sql, destParam);
    }

    public CooperKeyEntity getCooperKeyMessage(String messageId) {
//        try {
//            String sql = "select distinct COOPER_KEY, NOTIFY_URL from  MMSP_COOPER_KEY mc , MMSP_PROXYPAYMESSAGE mp where mc.COOPER_ID = mp.ACCOUNTID and mp.GLOBALMESSAGEID = ?";
//            ParameterizedRowMapper<CooperKeyEntity> rm = new ParameterizedRowMapper<CooperKeyEntity>() {
//                public CooperKeyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    CooperKeyEntity cke = new CooperKeyEntity();
//                    cke.setCooperKey(rs.getString("COOPER_KEY"));
//                    cke.setNotifyUrl(rs.getString("NOTIFY_URL"));
//                    return cke;
//                }
//            };
//            return (CooperKeyEntity)this.getJdbcTemplate().queryForObject(sql, new Object[]{messageId}, rm);
//        } catch (EmptyResultDataAccessException var4) {
//            return null;
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return null;
//        }
        return null;

    }

    public boolean getProxyPayMessage(String messageId, String validateCode) {
//        try {
//            String sql = "select STATUS,CREATEDATE from MMSP_PROXYPAYMESSAGE where GLOBALMESSAGEID = ? and VALIDATECODE = ?";
//            log.info("sql:=  " + sql + "{" + messageId + "," + validateCode + "}");
//            ParameterizedRowMapper<ProxyPayMessage> rw = new ParameterizedRowMapper<ProxyPayMessage>() {
//                public ProxyPayMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    ProxyPayMessage model = new ProxyPayMessage();
//                    model.setAccountId("");
//                    model.setCreateDate(rs.getString("CREATEDATE"));
//                    model.setStatus(rs.getString("STATUS"));
//                    return model;
//                }
//            };
//            ProxyPayMessage ppm = (ProxyPayMessage)this.getJdbcTemplate().queryForObject(sql, new Object[]{messageId, validateCode}, rw);
//            return ppm == null || ppm.getStatus() != null && ppm.getStatus().equals("9") ? true : true;
//        } catch (EmptyResultDataAccessException var6) {
//            return false;
//        } catch (Exception var7) {
//            log.error(var7.getMessage(), var7);
//            return false;
//        }

        return true;
    }

    public ProxyPayMessage getProxyPayMessageById(String messageId) {
        try {
            String sql = "select mp.FEETYPE,mp.ACCOUNTID,mp.CPID,mp.PHONENUMBER,mp.PRODUCTID ,mp.SOURCE_TYPE,mp.GLOBALMESSAGEID,mck.COOPER_KEY,mck.NOTIFY_URL from MMSP_PROXYPAYMESSAGE mp,MMSP_COOPER_KEY mck where mp.ACCOUNTID=mck.COOPER_ID and mp.GLOBALMESSAGEID = ?";
            log.info("sql:=  " + sql + "{" + messageId + "}");
            ParameterizedRowMapper<ProxyPayMessage> rw = new ParameterizedRowMapper<ProxyPayMessage>() {
                public ProxyPayMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ProxyPayMessage model = new ProxyPayMessage();
                    model.setFeeType(rs.getString("FEETYPE"));
                    model.setAccountId(rs.getString("ACCOUNTID"));
                    model.setCpID(rs.getString("CPID"));
                    model.setPhoneNumber(rs.getString("PHONENUMBER"));
                    model.setProductId(rs.getString("PRODUCTID"));
                    model.setGlobalMessageid("GLOBALMESSAGEID");
                    model.setSourceType(rs.getInt("SOURCE_TYPE"));
                    model.setCooperKey(rs.getString("COOPER_KEY"));
                    model.setNotifyURL(rs.getString("NOTIFY_URL"));
                    return model;
                }
            };
            return (ProxyPayMessage)this.getJdbcTemplate().queryForObject(sql, new Object[]{messageId}, rw);
        } catch (EmptyResultDataAccessException var4) {
            return null;
        } catch (Exception var5) {
            log.error(var5.getMessage(), var5);
            return null;
        }
    }

    public String[] getServiceId(String sp_productId) {
        try {
            String sql = "select v.serviceid ,vs.SERVICENAME from vas v,vasservice vs,vasservice_reserve_info vri where vri.productid=vs.servicecode and v.vasid=vs.vasid and vri.SP_PRODUCTID = ?";
            ParameterizedRowMapper<String[]> rm = new ParameterizedRowMapper<String[]>() {
                public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String[] obj = new String[]{rs.getString(1), rs.getString(2)};
                    return obj;
                }
            };
            return (String[])this.getJdbcTemplate().queryForObject(sql, new Object[]{sp_productId}, rm);
        } catch (EmptyResultDataAccessException var4) {
            return null;
        } catch (Exception var5) {
            log.error(var5.getMessage(), var5);
            return null;
        }
    }
}
