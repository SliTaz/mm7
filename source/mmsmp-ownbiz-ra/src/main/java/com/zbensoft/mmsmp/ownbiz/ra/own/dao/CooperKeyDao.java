

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.ownbiz.ra.own.entity.CooperKeyEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class CooperKeyDao implements Dao {
    private static final Log log = LogFactory.getLog(CooperKeyDao.class);

    public CooperKeyDao() {
    }

    public CooperKeyEntity getCooperKeyEntityByAccountId(String spAccountId) {
    	
    	
    
//        try {
//            String sql = "select * from MMSP_COOPER_KEY where COOPER_ID = ?";
//            log.info(sql + "{" + spAccountId + "}");
//            ParameterizedRowMapper<CooperKeyEntity> rm = new ParameterizedRowMapper<CooperKeyEntity>() {
//                public CooperKeyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    CooperKeyEntity model = new CooperKeyEntity();
//                    model.setKeyId(Integer.parseInt(rs.getString("KEY_ID")));
//                    model.setCooperId(rs.getString("COOPER_ID"));
//                    model.setCooperKey(rs.getString("COOPER_KEY"));
//                    model.setCooperName(rs.getString("COOPER_NAME"));
//                    model.setIPS(rs.getString("IPS"));
//                    model.setRemark(rs.getString("REMARK"));
//                    model.setNotifyUrl(rs.getString("NOTIFY_URL"));
//                    model.setServiceTel(rs.getString("SERVICE_TEL"));
//                    model.setAppName(rs.getString("APP_NAME"));
//                    model.setCooperCode(rs.getInt("COOPER_CODE"));
//                    model.setSourceType(rs.getString("SOURCE_TYPE"));
//                    model.setTelNotifyUrl(rs.getString("TEL_NOTIFY_URL"));
//                    return model;
//                }
//            };
//            return (CooperKeyEntity)this.getJdbcTemplate().queryForObject(sql, new Object[]{spAccountId}, rm);
//        } catch (EmptyResultDataAccessException var4) {
//            return null;
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return null;
//        }
    	return	HttpHelper.getCooperKeyEntityByAccountId(spAccountId);
    }

    public List<CooperKeyEntity> queryAllCooperKeyEntity() {
//        try {
//            String sql = "select * from MMSP_COOPER_KEY";
//            ParameterizedRowMapper<CooperKeyEntity> rw = new ParameterizedRowMapper<CooperKeyEntity>() {
//                public CooperKeyEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    CooperKeyEntity model = new CooperKeyEntity();
//                    model.setKeyId(Integer.parseInt(rs.getString("KEY_ID")));
//                    model.setCooperId(rs.getString("COOPER_ID"));
//                    model.setCooperKey(rs.getString("COOPER_KEY"));
//                    model.setCooperName(rs.getString("COOPER_NAME"));
//                    model.setIPS(rs.getString("IPS"));
//                    model.setRemark(rs.getString("REMARK"));
//                    model.setNotifyUrl(rs.getString("NOTIFY_URL"));
//                    model.setServiceTel(rs.getString("SERVICE_TEL"));
//                    model.setAppName(rs.getString("APP_NAME"));
//                    model.setCooperCode(rs.getInt("COOPER_CODE"));
//                    model.setSourceType(rs.getString("SOURCE_TYPE"));
//                    model.setTelNotifyUrl(rs.getString("TEL_NOTIFY_URL"));
//                    return model;
//                }
//            };
//            log.info("刷新cooper_key sql:" + sql);
//            return this.getJdbcTemplate().query(sql, rw);
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//            return null;
//        }
        return HttpHelper.queryAllCooperKeyEntity();
    }
}
