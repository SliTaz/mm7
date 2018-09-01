package com.zbensoft.mmsmp.common.ra.vas.sjb.data;

import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OrderRelationDAO extends JdbcDaoSupport {
	private static final Logger logger = Logger.getLogger(OrderRelationDAO.class);

	public String getAreaCodeByUserPhone(String userPhone) {
		String segSql = "select province from mobile_segment where  segment = substr('" + userPhone + "',1,7) ";
		logger.info("OrderRelationDAO getAreaCodeByUserPhone sql:" + segSql);
		List o = getJdbcTemplate().query(segSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				String ret = null;
				if (rs != null) {
					ret = rs.getString("province");
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (String) o.get(0);
		}

		return null;
	}

	public String getCityCodeByUserPhone(String userPhone) {
		String segSql = " select city from mobile_segment where  segment = substr('" + userPhone + "',1,7)";
		logger.info("OrderRelationDAO getCityCodeByUserPhone sql:" + segSql);
		List o = getJdbcTemplate().query(segSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				String ret = null;
				if (rs != null) {
					ret = rs.getString("city");
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (String) o.get(0);
		}

		return null;
	}

	public UserOrder getOrderRelation(String userPhone, Integer uniqueID) {
		String sql = " select CELLPHONENUMBER ,SERVICEUNIQUEID ,ORDERHISID,STATUS  from USER_ORDER  where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";

		logger.debug("OrderRelationDAO getOrderRelation sql:" + sql);
		List o = getJdbcTemplate().query(sql, new Object[] { userPhone, uniqueID }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UserOrder ret = null;
				if (rs != null) {
					ret = new UserOrder();

					UserOrderId id = new UserOrderId();
					id.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
					id.setServiceuniqueid(Integer.valueOf(rs.getInt("SERVICEUNIQUEID")));
					ret.setId(id);
					ret.setOrderhisid(Integer.valueOf(rs.getInt("ORDERHISID")));
					ret.setStatus(Integer.valueOf(rs.getInt("STATUS")));
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (UserOrder) o.get(0);
		}

		return null;
	}

	public UserOrder getOrderRelation(String userPhone, String serviceCode) {
		String sql = " select CELLPHONENUMBER ,SERVICEUNIQUEID ,ORDERHISID,STATUS  from USER_ORDER  where CELLPHONENUMBER = ? and SERVICEUNIQUEID = (select t.uniqueid from vasservice t where t.servicecode=?)";

		logger.debug("OrderRelationDAO getOrderRelation sql:" + sql);
		List o = getJdbcTemplate().query(sql, new Object[] { userPhone, serviceCode }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UserOrder ret = null;
				if (rs != null) {
					ret = new UserOrder();

					UserOrderId id = new UserOrderId();
					id.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
					id.setServiceuniqueid(Integer.valueOf(rs.getInt("SERVICEUNIQUEID")));
					ret.setId(id);
					ret.setOrderhisid(Integer.valueOf(rs.getInt("ORDERHISID")));
					ret.setStatus(Integer.valueOf(rs.getInt("STATUS")));
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (UserOrder) o.get(0);
		}

		return null;
	}

	public int updateOrderRelation(String userPhone, Integer uniqueID, Integer status) {
		String sql = " update USER_ORDER set status = ?  where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";
		logger.debug("OrderRelationDAO updateOrderRelation sql:" + sql);
		int ret = getJdbcTemplate().update(sql, new Object[] { status, userPhone, uniqueID });
		logger.debug("OrderRelationDAO updateOrderRelation sucess :" + ret);
		return ret;
	}

	public int updateSPOrderRelation(String userPhone, String spID, Integer status) {
		String sql = " update USER_ORDER set status = ?  where CELLPHONENUMBER = ?  and SERVICEUNIQUEID in(select UNIQUEID from VASSERVICE where VASPID=?)";

		logger.debug("OrderRelationDAO updateSPOrderRelation sql:" + sql);
		int ret = getJdbcTemplate().update(sql, new Object[] { status, userPhone, spID });
		logger.debug("OrderRelationDAO updateSPOrderRelation :" + ret);
		return ret;
	}

	public List<UserOrder> getOrderRelation(String userPhone) {
		String sql = " select CELLPHONENUMBER ,SERVICEUNIQUEID ,ORDERHISID,STATUS  from USER_ORDER  where CELLPHONENUMBER = ?";

		logger.debug("OrderRelationDAO getOrderRelation sql:" + sql);
		List o = getJdbcTemplate().query(sql, new Object[] { userPhone }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UserOrder ret = null;
				if (rs != null) {
					ret = new UserOrder();

					UserOrderId id = new UserOrderId();
					id.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
					id.setServiceuniqueid(Integer.valueOf(rs.getInt("SERVICEUNIQUEID")));
					ret.setId(id);
					ret.setOrderhisid(Integer.valueOf(rs.getInt("ORDERHISID")));
					ret.setStatus(Integer.valueOf(rs.getInt("STATUS")));
				}

				return ret;
			}
		});
		return o;
	}

	public void AddOrderRelation(String userPhone, Vasservice service, String provinceCode, String cityCode) {
		if (provinceCode == null) {
			provinceCode = getPlatformArea();
		}

		if (cityCode == null) {
			cityCode = provinceCode + "99";
		}

		if (service.getIsPackage() == null) {
			service.setIsPackage("0");
		}
		String sql = "select NEXT VALUE FOR USER_ORDER_HIS_SEQ from sysibm.sysdummy1";
		int hisID = getJdbcTemplate().queryForInt(sql);

		sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE) values(?,?,?,?,current timestamp,?,?,?)";

		getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(hisID), userPhone, userPhone, service.getUniqueid(), "1", service.getFeetype(), service.getOrderfee() });

		sql = "insert into USER_ORDER(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,PRIORITY,ISPACKAGE,USERAREA) values(?,?,?,current timestamp,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(sql, new Object[] { userPhone, userPhone, service.getUniqueid(), "1", service.getFeetype(), service.getOrderfee(), Integer.valueOf(hisID), Integer.valueOf(0), Integer.valueOf(2),
				service.getIsPackage(), provinceCode });

		insertUserInfo(userPhone, null, provinceCode, cityCode);
	}

	public void delOrderRelation(String userPhone, Integer serviceUniqueID) {
		UserOrder userOrder = getOrderRelation(userPhone, serviceUniqueID);

		if (userOrder == null) {
			return;
		}

		String sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where UNIQUEID = ?";
		getJdbcTemplate().update(sql, new Object[] { userOrder.getOrderhisid() });

		String delSql = "delete from USER_ORDER where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";
		getJdbcTemplate().update(delSql, new Object[] { userPhone, serviceUniqueID });
	}

	public void delOrderRelation(String userPhone, String serviceCode) {
		UserOrder userOrder = getOrderRelation(userPhone, serviceCode);

		if (userOrder == null) {
			return;
		}

		String sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where UNIQUEID = ?";
		int updateResult = getJdbcTemplate().update(sql, new Object[] { userOrder.getOrderhisid() });

		String delSql = "delete from USER_ORDER where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";
		int delResult = getJdbcTemplate().update(delSql, new Object[] { userPhone, userOrder.getId().getServiceuniqueid() });
	}

	public void delSPOrderRelation(String userPhone, String spid) {
		List userOrderList = getOrderRelation(userPhone);
		if ((userOrderList == null) || (userOrderList.size() == 0)) {
			return;
		}
		for (Iterator localIterator = userOrderList.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			UserOrder userOrder = (UserOrder) o;
			if (userOrder != null) {
				String sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where UNIQUEID = ?";
				getJdbcTemplate().update(sql, new Object[] { userOrder.getOrderhisid() });

				String delSql = "delete from USER_ORDER where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";
				getJdbcTemplate().update(delSql, new Object[] { userPhone, userOrder.getId().getServiceuniqueid() });
			}
		}
	}

	public int insertUserInfo(String number, String uaType, String provinceCode, String cityCode) {
		UserInfo user = getUserInfo(number);

		if (user != null) {
			return 0;
		}

		logger.info("number:\t\t" + number);
		logger.info("uaType:\t\t" + uaType);
		logger.info("provinceCode:\t\t" + provinceCode);
		logger.info("cityCode:\t\t" + cityCode);
		String sql = "insert into user_info (userid,cellphonenumber,TERMINALTYPE,status,provincecode,citycode , UA_TYPE_ID) values ( NEXT VALUE FOR USER_INFO_SEQ,?,'2','1',?,?,cast(? as DECIMAL))";

		logger.info("add userInfo:  " + sql);

		int flag = getJdbcTemplate().update(sql, new Object[] { number, provinceCode, cityCode, uaType });
		return flag;
	}

	private String getPlatformArea() {
		String sql = " select value from  sys_parameters where name='PLATFORM_AREA'";
		ParameterizedRowMapper rm = new ParameterizedRowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Object obj = rs.getString("value");
				return obj;
			}
		};
		String areaName = (String) getJdbcTemplate().queryForObject(sql, rm);
		if ((areaName == null) || (areaName.length() == 0)) {
			logger.error("areaName in sys parameter is null ! set it first before run the program!");
		}
		areaName = areaName.substring(0, areaName.indexOf('@'));
		return areaName;
	}

	public UserInfo getUserInfo(String number) {
		String sql = "select USERID from  user_info where CELLPHONENUMBER = ?";

		List userList = getJdbcTemplate().query(sql, new Object[] { number }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UserInfo ret = null;
				if (rs != null) {
					ret = new UserInfo();
					ret.setUserid(Integer.valueOf(rs.getInt("USERID")));
				}
				return ret;
			}
		});
		if ((userList != null) && (userList.size() > 0)) {
			return (UserInfo) userList.get(0);
		}

		return null;
	}
}
