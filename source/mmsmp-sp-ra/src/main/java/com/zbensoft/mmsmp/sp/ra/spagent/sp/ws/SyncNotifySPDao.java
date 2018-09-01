package com.zbensoft.mmsmp.sp.ra.spagent.sp.ws;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SyncNotifySPDao extends JdbcDaoSupport {
	public String getSpOrderUrl(String spid) {
		String sql = "select sp_order_url from vasp_reserve_info where spid = ?";
		List list = getJdbcTemplate().query(sql, new Object[] { spid }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("sp_order_url");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return null;
	}
}
