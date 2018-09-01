package com.zbensoft.mmsmp.common.ra.utils;

import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.Userservhistory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class MM7DAO extends JdbcDaoSupport {
	private static final Logger logger = Logger.getLogger(MM7DAO.class);

	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getServicecode(String messageid) {
		messageid = messageid.replace("null", "");
		logger.info("=====>select SERVUNIQUEID from USER_SERV_HISTORY where MTTRANID='" + messageid + "'");
		String service_code = (String) getJdbcTemplate().queryForObject("select SERVUNIQUEID from USER_SERV_HISTORY where MTTRANID=?", new Object[] { messageid }, String.class);
		logger.info("service_code:" + service_code);
		return service_code;
	}
	
	public String getSpReportUrlByServiceCode(String messageid) {
		return "getSpReportUrlByServiceCode";
	}
	
	public String getMTLimitNumber(String messageid) {
		return "getMTLimitNumber";
	}

	public String getServiceIDbyProductid(String Sp_product_id) {
		logger.info("=====>query serviceid by sp_productid :" + Sp_product_id);
		logger.info("=====>select vas.serviceid from VASSERVICE_RESERVE_INFO vassr,VASSERVICE vass,VAS vas where vas.VASID=vass.VASID and vass.SERVICECODE=vassr.PRODUCTID and vassr.SP_PRODUCTID=" + Sp_product_id);

		String serviceId = "";
		try {
			serviceId = (String) getJdbcTemplate().queryForObject(
					"select vas.serviceid from VASSERVICE_RESERVE_INFO vassr,VASSERVICE vass,VAS vas where vas.VASID=vass.VASID and vass.SERVICECODE=vassr.PRODUCTID and vassr.SP_PRODUCTID=?",
					new Object[] { Sp_product_id }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceId;
	}

	public String getService_id(String serviceCode) {
		String service_id = "";
		String sql = "select VASID from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and reinfo.SP_PRODUCTID='" + serviceCode + "'";
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			service_id = list.get(0).toString();
		}
		return service_id;
	}

	public String getSp_id(String serviceCode) {
		String spid = "";
		String sql = "select re.cpid from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and reinfo.SP_PRODUCTID='" + serviceCode + "'";
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			spid = list.get(0).toString();
		}
		return spid;
	}

	public String getSp_urlbySpid(String spid) {
		String sql = "select reportmessageurl from VASP_RESERVE_INFO where spid=?";
		List list = getJdbcTemplate().query(sql, new Object[] { spid }, new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				if (rs != null) {
					return rs.getString(1);
				}
				return null;
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return (String) list.get(0);
		}

		return null;
	}

	public void SaveSpMMSSendRecord(MT_ReportMessage mtMsg, String service_ids, String messageid, String servicecode) {
		String phone = mtMsg.getRcvAddresses()[0];
		List argsList = new ArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String reqid = format.format(new Date());
		logger.info("reqid is : " + reqid);

		String sql = "insert into  USER_SERV_HISTORY (REQID,STATUS,MTTIME ,MTTRANID ,CELLPHONENUMBER,contentid,SERVUNIQUEID,spid,messageid,SERVICECODE) values('" + reqid + "','" + mtMsg.getStatus() + "','"
				+ this.formatDate.format(mtMsg.getMtDate()) + "','" + reqid + phone + "','" + phone + "',1," + service_ids + ",'" + mtMsg.getSpid() + "','" + messageid + "','" + servicecode + "')";
		logger.info("sql:" + sql);
		try {
			getJdbcTemplate().update(sql, argsList.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====>Save MT message Exception", e);
		}
	}

	public void updateSpMMSSendRecord(String status, String messageid, String reqid, String mttranid) {
		String sql = "update USER_SERV_HISTORY set status = '" + status + "', reqid = '" + reqid + "', mttranid = '" + mttranid + "' where messageid = '" + messageid + "'";
		logger.info("sql:" + sql);
		try {
			getJdbcTemplate().update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====>update MT message Exception", e);
		}
	}

	public Userservhistory getUserservhistoryBymessageid(String messageid) {
		String sql = "select * from USER_SERV_HISTORY where MESSAGEID='" + messageid + "'";
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
						dto.setServiceCode(rs.getString("SERVICECODE"));
						MM7DAO.logger.info("rs.getString(SERVICECODE)   " + rs.getString("SERVICECODE"));
						return dto;
					}
					return new Userservhistory();
				}
			});
			if (object != null)
				return (Userservhistory) object;
		} catch (Exception e) {
			logger.info("=====>no record in db:");
		}
		return new Userservhistory();
	}

	public String getSp_url(String messageid) {
		String url = "";
		String sql = "select va.reportmessageurl from VASP_RESERVE_INFO va,USER_SERV_HISTORY us where va.spid=us.spid and us.MESSAGEID='" + messageid + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			url = list.get(0).toString();
		}
		return url;
	}

	public String getSp_ReportUrl(String productId) {
		String sql = "select vasp.REPORTMESSAGEURL from vasp_reserve_info vasp where vasp.SPID =( select vaspid from vasservice vas , vasservice_reserve_info vari where vas.SERVICECODE = vari.PRODUCTID and vari.SP_PRODUCTID = '"
				+ productId + "')";
		String url = "";
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			url = list.get(0).toString();
		}
		return url;
	}

	public String getServuniqueIdbySpproductid(String sp_productid) {
		String ServuniqueId = "";
		String sql = "select vass.UNIQUEID from VASSERVICE_RESERVE_INFO vassr,VASSERVICE vass where vass.SERVICECODE=vassr.PRODUCTID and vassr.SP_PRODUCTID='" + sp_productid + "'";
		logger.info("=====>sql:" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			ServuniqueId = list.get(0).toString();
		}
		return ServuniqueId;
	}
}
