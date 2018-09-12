

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpHelper;

public class SystemParamDao implements Dao{
    private static final Log log = LogFactory.getLog(SystemParamDao.class);

    public SystemParamDao() {
    }

    public String getSystemParamBykey(String key) {
//        try {
//            String sql = "select t.VALUE from sys_parameters t where t.NAME=?";
//            ParameterizedRowMapper<String> rm = new ParameterizedRowMapper<String>() {
//                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    return rs.getString("VALUE");
//                }
//            };
//            return (String)this.getJdbcTemplate().queryForObject(sql, new Object[]{key}, rm);
//        } catch (Exception var4) {
//            log.error(var4.getMessage(), var4);
//            return null;
//        }
        return HttpHelper.getSystemParamBykey(key);
    }
}
