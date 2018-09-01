package com.zbensoft.mmsmp.common.ra.vas.sjb.data;

import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class LoadUserOrderDao extends JdbcDaoSupport {
	private static Logger logger = Logger.getLogger(LoadUserOrderDao.class);

	public List<UserInfo> getUserOrderRecord(Integer serviceuniqueid, Integer reqNum, String streamingNum, String province, Integer priority, String contentID, boolean isNational) {
		logger.info("enter getUserOrderRecord for areacode:\t" + province);

		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;

		LinkedList list = null;
		try {
			UserInfo user = null;
			list = new LinkedList();

			conn = getConnection();
			if (isNational)
				cstm = conn.prepareCall("{call getBatchContentsProv(?,?,?,?,?,?,?,?,?)}");
			else {
				cstm = conn.prepareCall("{call getBatchContentsArea(?,?,?,?,?,?,?,?,?)}");
			}

			cstm.setString(1, province);
			cstm.setInt(2, serviceuniqueid.intValue());
			cstm.setInt(3, priority.intValue());
			cstm.setInt(4, reqNum.intValue());
			cstm.setString(5, streamingNum);
			cstm.setString(6, contentID);
			cstm.setString(7, "0");

			cstm.registerOutParameter(8, 4);
			cstm.registerOutParameter(9, 12);

			cstm.execute();
			logger.info("return code " + cstm.getInt(8));
			logger.info("return comment " + cstm.getString(9));
			if (cstm.getInt(8) == 0) {
				rs = cstm.getResultSet();
				if (rs != null)
					while (rs.next()) {
						user = new UserInfo();
						user.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
						user.setUaTypeId(Integer.valueOf(rs.getInt("ua_type_id")));
						user.setTerminaltype(rs.getString("terminaltype"));
						list.add(user);
					}
			}
		} catch (Exception e) {
			logger.error("getUserOrderRecord excute exception! ");
			e.printStackTrace();
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		logger.info("getUserOrderRecord .return size is :" + list.size() + " .streamingNum is: " + streamingNum + " service id: " + serviceuniqueid);
		return list;
	}

	public List<UserInfo> getUserOrderRecordBySendStatus(Integer uniqueid, Integer contentid, int reqNum, String streamingNum, String province, String[] status, String mtType) {
		logger.info("enter getUserOrderRecordBySendStatus");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;

		LinkedList list = null;
		try {
			StringBuffer statBuffer = new StringBuffer();
			for (String stat : status) {
				statBuffer.append(stat);
				statBuffer.append(",");
			}
			String stat = statBuffer.toString();
			UserInfo user = null;
			list = new LinkedList();

			conn = getConnection();
			cstm = conn.prepareCall("{call getRePushTasks(?,?,?,?,?,?,?,?,?)}");

			cstm.setString(1, province);
			cstm.setInt(2, uniqueid.intValue());
			cstm.setInt(3, reqNum);
			cstm.setString(4, streamingNum);
			cstm.setInt(5, contentid.intValue());
			cstm.setString(6, stat.substring(0, stat.length() - 1));
			cstm.setString(7, mtType);

			cstm.registerOutParameter(8, 4);
			cstm.registerOutParameter(9, 12);
			logger.debug("pv_provincecode=" + province + "\n" + "pn_serviceuniqueid=" + uniqueid + "\n" + "pn_reqNum=" + reqNum + "\n" + "pv_mttranid=" + streamingNum + "\n" + "pn_contentId=" + contentid + "\n"
					+ "pv_status=" + stat.substring(0, stat.length() - 1) + "\n" + "PV_SENDTYPE=" + mtType + "\n");
			cstm.execute();
			logger.info("return code " + cstm.getInt(8));
			logger.info("return comment " + cstm.getString(9));
			if (cstm.getInt(8) == 0) {
				rs = cstm.getResultSet();
				if (rs != null)
					while (rs.next()) {
						user = new UserInfo();

						user.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
						user.setUaTypeId(Integer.valueOf(rs.getInt("ua_type_id")));
						user.setTerminaltype(rs.getString("terminaltype"));
						list.add(user);
					}
			}
		} catch (Exception e) {
			logger.error("getUserOrderRecord excute exception! ");
			e.printStackTrace();
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		logger.info("getUserOrderRecord .return size is :" + list.size() + " .streamingNum is: " + streamingNum + " service id: " + uniqueid);
		return list;
	}

	public List<UserInfo> getUserInfo(Integer uniqueid, Integer contentid, String generateStreamingNum, String[] phoneNumber, String mtType) {
		String sql = null;
		final List members = new LinkedList();
		for (String phone : phoneNumber) {
			sql = "select i.* from user_info i,user_order o where o.cellphonenumber='" + phone + "' and i.cellphonenumber=o.cellphonenumber and o.serviceuniqueid = " + uniqueid;
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

	public List<UserInfo> getUserInfoForDemand(String[] phoneNumber, String mtTrandId, int contentid, int serviceid, String mtType) {
		List list = new LinkedList();
		String sql = null;
		final List members = new LinkedList();
		for (String phone : phoneNumber) {
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
				list.add((UserInfo) members.get(0));
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

	public List<UserOrder> db2ProcTest() {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;

		LinkedList list = null;
		try {
			UserOrder user = null;
			UserOrderId orderId = null;
			list = new LinkedList();

			conn = getConnection();
			cstm = conn.prepareCall("{call TESTCURSORRETURN()}");
			cstm.execute();
			rs = cstm.getResultSet();
			while (rs.next()) {
				user = new UserOrder();
				orderId = new UserOrderId();
				orderId.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
				user.setId(orderId);
				list.add(user);
			}
		} catch (Exception e) {
			logger.error("getUserOrderRecord excute exception! ");
			e.printStackTrace();
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (cstm != null)
					cstm.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				releaseConnection(conn);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		logger.info("getUserOrderRecord .return size is :" + list.size());
		return list;
	}
}
