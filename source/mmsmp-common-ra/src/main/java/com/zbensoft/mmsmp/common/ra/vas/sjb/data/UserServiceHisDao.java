package com.zbensoft.mmsmp.common.ra.vas.sjb.data;

import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoAdapter;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ContentInfoRelation;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class UserServiceHisDao extends JdbcDaoSupport {
	private static Logger log = Logger.getLogger(UserServiceHisDao.class);

	public List<Userservhistory> getUserInfo() {
		int resendHourBefore = getResendHoueBefore();
		String resendDate = getDate(-resendHourBefore);
		log.info(resendDate);
		List members = new ArrayList();
		String sql = null;
		try {
			sql = "select u.cellphonenumber,u.contentid,u.sendtype,s.servicecode from user_serv_history u, vasservice s , content_info c where s.uniqueid=u.servuniqueid and u.contentid = c.contentid and u.status='1' and u.reqid is null";
			sql = sql + " and c.validstarttime>= timestamp('" + resendDate + "') ";
			log.info(sql);
			members = getJdbcTemplate().query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Userservhistory info = null;
					if (rs != null) {
						info = new Userservhistory();
						info.setCellphonenumber(rs.getString("CELLPHONENUMBER"));
						info.setContentid(rs.getInt("contentid"));
						info.setSendtype(rs.getInt("sendtype"));
						info.setServiceCode(rs.getString("servicecode"));
					}

					return info;
				}
			});
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		if ((members != null) && (members.size() > 0)) {
			sql = "delete from user_serv_history where status='1' and reqid is null and mttime is null ";
			getJdbcTemplate().execute(sql);
		}
		return members;
	}

	public List<Vasservice> findService() {
		try {
			String queryString = "select uniqueid,vaspid,vasid,servicecode,feetype,orderfee,ISPACKAGE as isPackage,PRODUCT_TYPE from vasservice   vs where vs.uniqueid in (select distinct servuniqueid from user_serv_history where status='1' and reqid is null)";

			log.info(queryString);
			return getJdbcTemplate().query(queryString, new RowMapper() {
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
					}

					return ret;
				}
			});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ContentInfo> getNeedSendContentList(boolean isNational) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet result = null;
		List contentList = new ArrayList();
		ContentInfo content = null;

		String sql = "select t.smstext, t.contentid, t.contentname, t.contentpath, t.contenttype, t.validstarttime, t.version_id, t.authorname from content_info t where t.contentid in (select distinct contentid from user_serv_history where status='1' and reqid is null ) ";

		log.info(sql);
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);

			for (result = preStmt.executeQuery(); result.next();) {
				content = new ContentInfo();
				content.setContentid(Integer.valueOf(result.getInt("contentid")));
				content.setContentname(result.getString("contentname"));
				content.setContentpath(result.getString("contentpath"));
				content.setContenttype(result.getString("contenttype"));
				content.setValidstarttime(result.getTimestamp("validstarttime"));
				content.setVersionId(Integer.valueOf(result.getInt("version_id")));
				content.setAuthorname(result.getString("authorname"));
				content.setSmsText(result.getString("smstext"));

				Map adapterContentMap = getAdapterSendContentMap(content.getContentid());
				content.setAdapterContentMap(adapterContentMap);

				Map provinceContent = getProviceSendContentMap(content.getContentid(), isNational);
				content.setProvinceContent(provinceContent);

				contentList.add(content);
			}

			return contentList;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("get contentList error! sql: " + sql + " " + e);
		} finally {
			releaseConnection(conn);
		}
		if (this.logger.isInfoEnabled()) {
			this.logger.info("getNeedSendContentList return null...");
		}
		return null;
	}

	private Map<Integer, ContentInfoAdapter> getAdapterSendContentMap(Integer contentID) {
		Map ret = new HashMap();

		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet result = null;

		StringBuffer sql = new StringBuffer("select t.RELATIONID,t.contentid , t.ADAPTERTYPEID, t.contentpath from CONTENT_INFO_ADAPTER t where t.CONTENTID =?");
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql.toString());
			preStmt.setInt(1, contentID.intValue());

			result = preStmt.executeQuery();
			if (result != null)
				while (result.next()) {
					ContentInfoAdapter content = new ContentInfoAdapter();
					content.setRealationID(Integer.valueOf(result.getInt("RELATIONID")));
					content.setContentID(Integer.valueOf(result.getInt("contentid")));
					content.setContentpath(result.getString("contentpath"));
					content.setAdapterTypeID(Integer.valueOf(result.getInt("ADAPTERTYPEID")));

					ret.put(content.getAdapterTypeID(), content);
				}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("getAdapterSendContentMap error! sql:" + sql, e);

			releaseConnection(conn);
			if (preStmt != null)
				try {
					preStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		if (this.logger.isInfoEnabled()) {
			this.logger.info("getAdapterSendContentMap return :" + ret);
		}
		return ret;
	}

	private Map<String, ContentInfoRelation> getProviceSendContentMap(Integer contentID, boolean isNational) {
		Map ret = new HashMap();

		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet result = null;

		String sql = "select t.RELATIONID,t.contentid , t.PROVINCECODE, t.contentpath,t.AREA_CODE from CONTENT_INFO_RELATION t where t.CONTENTID = ?";
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, contentID.intValue());

			result = preStmt.executeQuery();
			if (result != null)
				while (result.next()) {
					ContentInfoRelation content = new ContentInfoRelation();
					content.setRelationId(Integer.valueOf(result.getInt("RELATIONID")));
					content.setContentid(Integer.valueOf(result.getInt("contentid")));
					content.setContentpath(result.getString("contentpath"));
					if (isNational)
						content.setProvincecode(result.getString("PROVINCECODE"));
					else {
						content.setProvincecode(result.getString("AREA_CODE"));
					}

					ret.put(content.getProvincecode(), content);
				}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("getProviceSendContentMap error! sql:" + sql, e);

			releaseConnection(conn);
			if (preStmt != null)
				try {
					preStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		if (this.logger.isInfoEnabled()) {
			this.logger.info("getProviceSendContentMap return :" + ret);
		}
		return ret;
	}

	public int getResendHoueBefore() {
		int result = 3;
		try {
			String queryString = "select * from sys_parameters where name='RESEND_HOUR_BEFORE'";
			log.info(queryString);
			List list = getJdbcTemplate().query(queryString, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SysParameter ret = null;
					if (rs != null) {
						ret = new SysParameter();
						ret.setValue(rs.getString("value"));
					}

					return ret;
				}
			});
			if ((list != null) && (list.size() > 0))
				;
			return Integer.parseInt(((SysParameter) list.get(0)).getValue());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
		}
		return result;
	}

	public String getDate(int add) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(11, add);
		result = sdf.format(cal.getTime());
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new UserServiceHisDao().getDate(-12));
	}
}
