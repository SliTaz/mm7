package com.zbensoft.mmsmp.common.ra.send.sgipsms;

import com.zbensoft.mmsmp.common.ra.common.db.entity.UserInfo;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrder;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderHis;
import com.zbensoft.mmsmp.common.ra.common.db.entity.UserOrderId;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vas;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Vasservice;
import com.zbensoft.mmsmp.common.ra.vas.sjb.data.SysParameter;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SmsSenderDao extends JdbcDaoSupport {
	private static final Logger logger = Logger.getLogger(SmsSenderDao.class);
	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DecimalFormat format = new DecimalFormat("#####.00");

	public String getSmsSenderUrl(String service_id) {
		String sql = "select scs.spip,scs.sp_port from vas va,SERVICE_CAPACITY_SMS scs,SERVICE_CAPACITY sc where va.vaspid=sc.sp_id and scs.RECORDSEQUENCEID=sc.CAPACITY_ID  and va.serviceid='" + service_id + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("spip") + ":" + rs.getString("sp_port");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return null;
	}

	public String queryOrderMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='COREBIZ_SEND_ORDER_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryOndemandMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='COREBIZ_SEND_ONDEMAND_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryUpOndemandMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='UP_ONDEMAND_TIP'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String querySecondConfirmMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='COREBIZ_SEND_SECONDCONFIRM_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryCancelMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='COREBIZ_SEND_CANCEL_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryClientMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='COREBIZ_SEND_CLIENT_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryMOMessage() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='MO_ERROR_MESSAGE'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryErrorMsgTemplate() {
		String sql = "select value from SYS_PARAMETERS sys where sys.NAME='ERROR_MSG'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("value");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public SmsSenderDto getProductInfo(String productid, final int feeType) {
		String sql = "";
		if (feeType == 1)
			sql = "select a.ONDEMANDFEE,a.ORDERFEE, a.SERVICENAME from vasservice a, vasservice_reserve_info b where a.servicecode = b.productid and b.SP_PRODUCTID ='" + productid + "' and a.feetype = '1'";
		else if (feeType == 2)
			sql = "select a.ONDEMANDFEE,a.ORDERFEE, a.SERVICENAME from vasservice a, vasservice_reserve_info b where a.servicecode = b.productid and b.SP_PRODUCTID ='" + productid + "' and a.feetype = '2'";
		try {
			System.out.println(getJdbcTemplate());
			Object object = getJdbcTemplate().queryForObject(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SmsSenderDto bean = new SmsSenderDto();
					if (rs != null) {
						int orderfee = 0;
						if (feeType == 1) {
							orderfee = rs.getInt("ORDERFEE");
						} else if (feeType == 2) {
							orderfee = rs.getInt("ONDEMANDFEE");
						}
						bean.setFee(SmsSenderDao.this.format.format(orderfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(orderfee / 100.0D) : SmsSenderDao.this.format.format(orderfee / 100.0D));
						bean.setServicename(rs.getString("SERVICENAME"));
						return bean;
					}
					return new SmsSenderDto();
				}
			});
			if (object != null)
				return (SmsSenderDto) object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SmsSenderDto();
	}

	public SmsSenderDto getOrderAndProduct_id(String messageContent) {
		String sql = "select reinfo.sp_productid,        re.vasid,        re.cpid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.orderfee,        re.servicename,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '1'        and re.servicecode = reinfo.productid        and re.vasid = v.vasid        and lower(re.ordercode)='"
				+ messageContent.toLowerCase() + "'";
		logger.info("=====>" + sql);
		try {
			List list = getJdbcTemplate().query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SmsSenderDto bean = new SmsSenderDto();
					if (rs != null) {
						bean.setVasid(rs.getString("vasid"));
						bean.setSp_productid(rs.getString("sp_productid"));
						bean.setOrdercode(rs.getString("ordercode"));
						bean.setCancelordercode(rs.getString("cancelordercode"));
						bean.setOndemandcode(rs.getString("ondemandcode"));
						int orderfee = rs.getInt("orderfee");
						bean.setFee(SmsSenderDao.this.format.format(orderfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(orderfee / 100.0D) : SmsSenderDao.this.format.format(orderfee / 100.0D));
						bean.setServiceId(rs.getString("serviceId"));
						bean.setServicename(rs.getString("servicename"));
						bean.setCpid(rs.getInt("cpid"));
						bean.setVaspid(rs.getString("vaspid"));
						return bean;
					}
					return null;
				}
			});
			if ((list != null) && (list.size() != 0))
				return (SmsSenderDto) list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SmsSenderDto();
	}

	public String getSpProductId(String productId) {
		String productid = null;
		String sql = "select SP_PRODUCTID from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and re.uniqueid=" + productId;
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			productid = list.get(0).toString();
		}
		return productid;
	}

	public static String getJingHao(int a) {
		String jinghao = "";
		for (int c = 0; c < a; c++) {
			jinghao = jinghao + "#";
		}
		return jinghao;
	}

	public SmsSenderDto getCancelOrderAndProduct_id(String messageContent) {
		String sql = "select reinfo.sp_productid,        re.vasid,        re.cpid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.orderfee,        re.servicename,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '1'        and re.servicecode = reinfo.productid        and re.vasid = v.vasid        and lower(re.cancelordercode) = '"
				+ messageContent.toLowerCase() + "'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsSenderDto bean = new SmsSenderDto();
				if (rs != null) {
					bean.setVasid(rs.getString("vasid"));
					bean.setSp_productid(rs.getString("sp_productid"));
					bean.setOrdercode(rs.getString("ordercode"));
					bean.setCancelordercode(rs.getString("cancelordercode"));
					bean.setOndemandcode(rs.getString("ondemandcode"));
					int orderfee = rs.getInt("orderfee");
					bean.setFee(SmsSenderDao.this.format.format(orderfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(orderfee / 100.0D) : SmsSenderDao.this.format.format(orderfee / 100.0D));

					bean.setServiceId(rs.getString("serviceId"));
					bean.setServicename(rs.getString("servicename"));
					bean.setCpid(rs.getInt("cpid"));
					bean.setVaspid(rs.getString("vaspid"));
					return bean;
				}
				return null;
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return (SmsSenderDto) list.get(0);
		}
		return new SmsSenderDto();
	}

	public SmsSenderDto getDianBoAndProduct_id(String messageContent) {
		String sql = "select reinfo.sp_productid,        re.vasid,        re.cpid,        re.vaspid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.ondemandfee,        re.servicename,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '2'        and re.servicecode = reinfo.productid        and re.vasid = v.vasid        and lower(re.ondemandcode) = '"
				+ messageContent.toLowerCase() + "'";

		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsSenderDto bean = new SmsSenderDto();
				if (rs != null) {
					bean.setVasid(rs.getString("vasid"));
					bean.setSp_productid(rs.getString("sp_productid"));
					bean.setOrdercode(rs.getString("ordercode"));
					bean.setCancelordercode(rs.getString("cancelordercode"));
					bean.setOndemandcode(rs.getString("ondemandcode"));
					int ondemandfee = rs.getInt("ondemandfee");
					bean.setFee(
							SmsSenderDao.this.format.format(ondemandfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(ondemandfee / 100.0D) : SmsSenderDao.this.format.format(ondemandfee / 100.0D));

					bean.setServiceId(rs.getString("serviceId"));
					bean.setServicename(rs.getString("servicename"));
					bean.setCpid(rs.getInt("cpid"));
					bean.setVaspid(rs.getString("vaspid"));
					return bean;
				}
				return null;
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return (SmsSenderDto) list.get(0);
		}
		return new SmsSenderDto();
	}

	public String getService_idByProduct_id(String productId) {
		String service_id = "";
		String sql = "select VASID from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and reinfo.SP_PRODUCTID='" + productId + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		logger.info("list is null " + (list == null ? "Yes" : "No") + "list size : " + list.size());
		if ((list != null) && (list.size() > 0)) {
			service_id = list.get(0).toString();
		}
		return service_id;
	}

	public String getAccessNoByProductId(String productId) {
		logger.info("query ProductId by product id   :" + productId);
		String accessNo = "";
		String sql = "select distinct a.AccessNo   from SERVICE_CAPACITY_MMS a, SERVICE_CAPACITY b, vasservice c  where a.RECORDSEQUENCEID = b.CAPACITY_ID    and b.SP_ID = c.VASPID    and c.UNIQUEID = " + productId;
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		logger.info("list is null " + (list == null ? "Yes" : "No") + "list size : " + list.size());
		if ((list != null) && (list.size() > 0)) {
			accessNo = list.get(0).toString();
		}
		return accessNo;
	}

	public String getVASIDByService_id(String serviceId) {
		return null;
	}

	public String getSpcodebyProduct_id(String productId) {
		String spid = "";
		String sql = "select re.cpid from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and reinfo.SP_PRODUCTID='" + productId + "'";

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

	public String getConfirmmsgByProductid(String productId) {
		String confirmPrompt = "";
		String sql = "select confirmprompt from vasservice_reserve_info t where t.sp_productid = '" + productId + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			confirmPrompt = list.get(0).toString();
		}
		return confirmPrompt;
	}

	public boolean saveMoMsg(final String msgContent, String phoneNumber, final String spNumber) {
		String sqlSeqNext = "select NEXT VALUE FOR USER_MO_HISTORY_SEQ from sysibm.sysdummy1 ";
		final int seqNextId = getJdbcTemplate().queryForInt(sqlSeqNext);

		String phonenumberw = phoneNumber;
		if (phoneNumber.startsWith("+86"))
			phonenumberw = phoneNumber.substring(3);
		else if (phoneNumber.startsWith("86"))
			phonenumberw = phoneNumber.substring(2);
		final String userphonenumber = phonenumberw;
		String sql = "insert into user_mo_history (id,cellphonenumber, SERVICEACTIVATIONNUMBER,smstext,MOTIME, messagetype ,order_flag) values (?,?,?,?,{ts '" + this.formatDate.format(new Date()) + "'},?,?)";

		int result = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, seqNextId);
				ps.setString(2, userphonenumber);
				ps.setString(3, spNumber);
				ps.setString(4, msgContent);
				ps.setString(5, "s");
				ps.setString(6, "1");
			}
		});
		return result >= 1;
	}

	public String getLatestMoOrderMsg(String cellphonenumber, long deltaTime) {
		String sql = "select smstext from user_mo_history t where t.order_flag is not null and t.cellphonenumber = '" + cellphonenumber + "'  and lower(t.SMSTEXT)!='y' order by id desc";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String getSpReportUrl(String spid) {
		String sql = "select SPSTATUSURL from service_capacity t1, service_capacity_mms t2 where t1.CAPACITY_ID  = t2.RECORDSEQUENCEID and  t1.sp_id = '" + spid + "' and t1.status = '2' and t1.capacity = 'm'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String getRelationNotifyUrl(String spid) {
		String sql = " select sp_order_url from vasp_reserve_info where spid = '" + spid + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public SmsSenderDto getTuidingByNumber(String serviceNumber, String messageContent) {
		String wheresql = "";
		int mclen = messageContent.length();
		messageContent = messageContent.toLowerCase();
		String sql = "select reinfo.sp_productid,        re.servicename,        re.vaspid,        re.cpid,        re.vasid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.ondemandfee,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '1'        and re.vasid = v.vasid        and re.servicecode = reinfo.productid        and re.vasid = '"
				+ serviceNumber + "'";

		List beanlist = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsSenderDto bean = new SmsSenderDto();
				if (rs != null) {
					bean.setVasid(rs.getString("vasid"));
					bean.setSp_productid(rs.getString("sp_productid"));
					bean.setOrdercode(rs.getString("ordercode"));
					bean.setCancelordercode(rs.getString("cancelordercode"));
					bean.setOndemandcode(rs.getString("ondemandcode"));
					int ondemandfee = rs.getInt("ondemandfee");
					bean.setFee(
							SmsSenderDao.this.format.format(ondemandfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(ondemandfee / 100.0D) : SmsSenderDao.this.format.format(ondemandfee / 100.0D));

					bean.setServiceId(rs.getString("serviceId"));
					bean.setServicename(rs.getString("servicename"));
					bean.setCpid(rs.getInt("cpid"));
					bean.setVaspid(rs.getString("vaspid"));
					return bean;
				}
				return new SmsSenderDto();
			}
		});
		if (beanlist != null) {
			for (int a = 0; a < beanlist.size(); a++) {
				SmsSenderDto obj = (SmsSenderDto) beanlist.get(a);
				if (obj.getOndemandcode() != null) {
					String ondemandcode = obj.getOndemandcode().replace("#", "");
					if (messageContent.startsWith(ondemandcode))
						return obj;
				} else {
					return new SmsSenderDto();
				}
			}
		} else {
			return new SmsSenderDto();
		}
		return new SmsSenderDto();
	}

	public SmsSenderDto getDingzhiByNumber(String serviceNumber, String messageContent) {
		String wheresql = "";
		int mclen = messageContent.length();
		messageContent = messageContent.toLowerCase();
		String sql = "select reinfo.sp_productid,        re.servicename,        re.vaspid,        re.cpid,        re.vasid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.ondemandfee,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '1'        and re.vasid = v.vasid        and re.servicecode = reinfo.productid        and re.vasid = '"
				+ serviceNumber + "'";

		List beanlist = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsSenderDto bean = new SmsSenderDto();
				if (rs != null) {
					bean.setVasid(rs.getString("vasid"));
					bean.setSp_productid(rs.getString("sp_productid"));
					bean.setOrdercode(rs.getString("ordercode"));
					bean.setCancelordercode(rs.getString("cancelordercode"));
					bean.setOndemandcode(rs.getString("ondemandcode"));
					int ondemandfee = rs.getInt("ondemandfee");
					bean.setFee(
							SmsSenderDao.this.format.format(ondemandfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(ondemandfee / 100.0D) : SmsSenderDao.this.format.format(ondemandfee / 100.0D));

					bean.setServiceId(rs.getString("serviceId"));
					bean.setServicename(rs.getString("servicename"));
					bean.setCpid(rs.getInt("cpid"));
					bean.setVaspid(rs.getString("vaspid"));
					return bean;
				}
				return new SmsSenderDto();
			}
		});
		if (beanlist != null) {
			for (int a = 0; a < beanlist.size(); a++) {
				SmsSenderDto obj = (SmsSenderDto) beanlist.get(a);
				if (obj.getOndemandcode() != null) {
					String ondemandcode = obj.getOndemandcode().replace("#", "");
					if (messageContent.startsWith(ondemandcode))
						return obj;
				} else {
					return new SmsSenderDto();
				}
			}
		} else {
			return new SmsSenderDto();
		}
		return new SmsSenderDto();
	}

	public SmsSenderDto getDianBoByNumber(String serviceNumber, String messageContent) {
		String wheresql = "";
		int mclen = messageContent.length();
		messageContent = messageContent.toLowerCase();
		String sql = "select reinfo.sp_productid,        re.servicename,        re.vaspid,        re.cpid,        re.vasid,        re.ordercode,        re.cancelordercode,        re.ondemandcode,        re.ondemandfee,        v.serviceid   from vasservice re,        vasservice_reserve_info reinfo,        vas v  where re.feetype = '2'        and re.vasid = v.vasid        and re.servicecode = reinfo.productid        and re.vasid = '"
				+ serviceNumber + "'";

		List beanlist = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsSenderDto bean = new SmsSenderDto();
				if (rs != null) {
					bean.setVasid(rs.getString("vasid"));
					bean.setSp_productid(rs.getString("sp_productid"));
					bean.setOrdercode(rs.getString("ordercode"));
					bean.setCancelordercode(rs.getString("cancelordercode"));
					bean.setOndemandcode(rs.getString("ondemandcode"));
					int ondemandfee = rs.getInt("ondemandfee");
					bean.setFee(
							SmsSenderDao.this.format.format(ondemandfee / 100.0D).startsWith(".") ? "0" + SmsSenderDao.this.format.format(ondemandfee / 100.0D) : SmsSenderDao.this.format.format(ondemandfee / 100.0D));

					bean.setServiceId(rs.getString("serviceId"));
					bean.setServicename(rs.getString("servicename"));
					bean.setCpid(rs.getInt("cpid"));
					bean.setVaspid(rs.getString("vaspid"));
					return bean;
				}
				return new SmsSenderDto();
			}
		});
		if (beanlist != null) {
			for (int a = 0; a < beanlist.size(); a++) {
				SmsSenderDto obj = (SmsSenderDto) beanlist.get(a);
				if (obj.getOndemandcode() != null) {
					String ondemandcode = obj.getOndemandcode().replace("#", "");
					if (messageContent.startsWith(ondemandcode))
						return obj;
				} else {
					return new SmsSenderDto();
				}
			}
		} else {
			return new SmsSenderDto();
		}
		return new SmsSenderDto();
	}

	public String getDianBoByExtpandCode(String serviceNumber) {
		String productid = "";
		String sql = "select SP_PRODUCTID from VASSERVICE re,VASSERVICE_RESERVE_INFO reinfo where re.SERVICECODE=reinfo.PRODUCTID and reinfo.product_Expand_code='" + serviceNumber + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		if ((list != null) && (list.size() > 0)) {
			productid = list.get(0).toString();
		}
		return productid;
	}

	public String getAreaCodeByUserPhone(String userPhone) {
		String segSql = "select province from mobile_segment where  segment = substr('" + userPhone + "',1,7) ";
		logger.info("=====>OrderRelationDAO getAreaCodeByUserPhone sql:" + segSql);
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
		logger.info("=====>OrderRelationDAO getCityCodeByUserPhone sql:" + segSql);
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

		logger.debug("=====>OrderRelationDAO getOrderRelation sql:" + sql);

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
		String sql = " select CELLPHONENUMBER ,SERVICEUNIQUEID ,ORDERHISID,STATUS  from USER_ORDER  where CELLPHONENUMBER = '" + userPhone + "' and SERVICEUNIQUEID = " + serviceCode;
		logger.info("=====>" + sql);
		logger.info("=====>OrderRelationDAO userPhone:" + userPhone);
		logger.info("=====>OrderRelationDAO uniqueID:" + serviceCode);
		List o = getJdbcTemplate().query(sql, new RowMapper() {
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
		logger.info("=====>" + sql);
		int ret = getJdbcTemplate().update(sql, new Object[] { status, userPhone, uniqueID });
		logger.info("=====>" + ret);
		return ret;
	}

	public int updateSPOrderRelation(String userPhone, String spID, Integer status) {
		String sql = " update USER_ORDER set status = ?  where CELLPHONENUMBER = ?  and SERVICEUNIQUEID in(select UNIQUEID from VASSERVICE where VASPID=?)";

		logger.info("=====>" + sql);
		int ret = getJdbcTemplate().update(sql, new Object[] { status, userPhone, spID });
		logger.debug("OrderRelationDAO updateSPOrderRelation :" + ret);
		return ret;
	}

	public List<UserOrder> getOrderRelation(String userPhone) {
		String sql = " select CELLPHONENUMBER ,SERVICEUNIQUEID ,ORDERHISID,STATUS  from USER_ORDER  where CELLPHONENUMBER = ?";

		logger.debug("=====>OrderRelationDAO getOrderRelation sql:" + sql);
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

	public int updateOrderRelation(String userPhone, String productId, String spOrderId) {
		String uniqueid = getUniqueid(productId);
		String sql = " update USER_ORDER_HIS set SP_ORDERID = ?  where UNIQUEID = (select ORDERHISID from user_order where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?)";
		logger.info("=====>" + sql);
		int ret = getJdbcTemplate().update(sql, new Object[] { spOrderId, userPhone, uniqueid });
		logger.info("=====>" + ret);
		sql = " update USER_ORDER set SP_ORDERID = ?  where CELLPHONENUMBER = ? and SERVICEUNIQUEID = ?";
		logger.info("=====>" + sql);
		ret = getJdbcTemplate().update(sql, new Object[] { spOrderId, userPhone, uniqueid });
		logger.info("=====>" + ret);
		return ret;
	}

	public boolean getNeedConfm(String sp_productId) {
		String needConfm = "";
		String sql = "select needconfm from vasservice_reserve_info where sp_productid = '" + sp_productId + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString(1);
			}
		});
		logger.info("list is null " + (list == null ? "Yes" : "No") + "list size : " + list.size());
		if ((list != null) && (list.size() > 0)) {
			needConfm = list.get(0).toString();
		}
		if (needConfm.equals("0"))
			return true;
		if (needConfm.equals("1")) {
			return false;
		}
		return false;
	}

	public void AddOrderRelation(String userPhone, Vasservice service, String provinceCode, String cityCode, String channel, String spOrderId, String notifySpFlag) {
		logger.info("=============== add order");
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

		sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE,SP_ORDERID,notify_sp_flag) values(?,?,?,?,current timestamp,?,?,?,?,?)";

		getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(hisID), userPhone, userPhone, service.getUniqueid(), channel, service.getFeetype(), service.getOrderfee(), spOrderId, notifySpFlag });
		if (service.getFeetype().equals("1")) {
			sql = "insert into USER_ORDER(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,PRIORITY,ISPACKAGE,USERAREA,SP_ORDERID,notify_sp_flag) values(?,?,?,current timestamp,?,?,?,?,?,?,?,?,?,?)";

			getJdbcTemplate().update(sql, new Object[] { userPhone, userPhone, service.getUniqueid(), channel, service.getFeetype(), service.getOrderfee(), Integer.valueOf(hisID), Integer.valueOf(0), Integer.valueOf(2),
					service.getIsPackage(), provinceCode, spOrderId, notifySpFlag });
		}
		insertUserInfo(userPhone, null, provinceCode, cityCode);
	}

	public void CancelOrderRelation(String userPhone, Vasservice service, UserOrder userOrder, String channel) {
		String sql = "";

		sql = "update user_order_his set expiredate = current timestamp, cancelordermethod = '" + channel + "' where uniqueid = " + userOrder.getOrderhisid();
		getJdbcTemplate().update(sql);

		sql = "delete from user_order where cellphonenumber = '" + userPhone + "' and serviceuniqueid = " + service.getUniqueid();
		getJdbcTemplate().update(sql);
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
		String sql = "insert into user_info (userid,cellphonenumber,TERMINALTYPE,status,provincecode,citycode,CREATEDATE, UA_TYPE_ID,DELETE_FLAG) values ( NEXT VALUE FOR USER_INFO_SEQ,?,'2','1',?,?,current timestamp,cast(? as DECIMAL),'0')";

		logger.info("=====>" + sql);

		int flag = getJdbcTemplate().update(sql, new Object[] { number, provinceCode, cityCode, uaType });
		return flag;
	}

	private String getPlatformArea() {
		String sql = " select value from  sys_parameters where name='PLATFORM_AREA'";
		logger.info("=====>" + sql);
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
		logger.info("=====>" + sql);
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

	public String getUniqueid(String productId) {
		String sql = "select vas.UNIQUEID from VASSERVICE vas,VASSERVICE_RESERVE_INFO vasri where vas.SERVICECODE=vasri.PRODUCTID and vasri.SP_PRODUCTID='" + productId + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("UNIQUEID");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public Integer getUserOrderHisId() {
		String sql = "select NEXT VALUE FOR USER_ORDER_HIS_SEQ from sysibm.sysdummy1";
		int orderHisId = getJdbcTemplate().queryForInt(sql);

		return Integer.valueOf(orderHisId);
	}

	public Vasservice getVASServiceByServiceCode(String serviceCode) {
		String sql = "select vas.uniqueid,vas.vaspid,vas.vasid,vas.servicecode,vas.feetype,vas.orderfee,vas.ISPACKAGE,vas.product_type,  vas.ordercode,vas.cancelordercode,vas.ondemandcode from vasservice vas,VASSERVICE_RESERVE_INFO vasri where vas.SERVICECODE=vasri.PRODUCTID and vasri.SP_PRODUCTID='"
				+ serviceCode + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Vasservice ret = new Vasservice();
				if (rs != null) {
					ret = new Vasservice();
					ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
					ret.setVaspid(rs.getString("vaspid"));
					ret.setVasid(rs.getString("vasid"));
					ret.setServicecode(rs.getString("servicecode"));
					ret.setFeetype(rs.getString("feetype"));
					ret.setOrderfee(Double.valueOf(rs.getDouble("orderfee")));
					ret.setIsPackage(rs.getString("ISPACKAGE"));
					ret.setProducttype(rs.getString("product_type"));

					ret.setOrdercode(rs.getString("ordercode"));
					ret.setCancelordercode(rs.getString("cancelordercode"));
					ret.setOndemandcode(rs.getString("ondemandcode"));
				}
				return ret;
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return (Vasservice) list.get(0);
		}

		return null;
	}

	public Vas getVasByVasId(String vasId) {
		String sql = "select * from vas where vasid = '" + vasId + "'";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Vas ret = new Vas();
				if (rs != null) {
					ret.setUniqueid(Integer.valueOf(rs.getInt("uniqueid")));
					ret.setVaspid(rs.getString("vaspid"));
					ret.setVasid(rs.getString("vasid"));
					ret.setServiceId(rs.getString("serviceid"));
					ret.setIfServiceIndependent(Integer.valueOf(rs.getInt("IFDEPENDENT")));
				}
				return ret;
			}
		});
		if ((list != null) && (list.size() > 0)) {
			return (Vas) list.get(0);
		}
		return new Vas();
	}

	public boolean isNumberExist(String number, Integer serviceuniqueid) {
		String sql = "select count(*) from user_order where cellphonenumber = '" + number + "' and serviceuniqueid = " + serviceuniqueid;
		int count = getJdbcTemplate().queryForInt(sql);
		if (count == 0) {
			return false;
		}
		return true;
	}

	public SysParameter querySysParamById(String id) {
		String sql = "select * from sys_parameters where name = ?";
		List o = getJdbcTemplate().query(sql, new Object[] { id }, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SysParameter ret = null;
				if (rs != null) {
					ret = new SysParameter();
					ret.setName(rs.getString("name"));
					ret.setValue(rs.getString("value"));
					ret.setDescription(rs.getString("DESCRIPTION"));
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (SysParameter) o.get(0);
		}
		return null;
	}

	public Map getAllSegmentsForCode() {
		String sql = "select segment,(select code from province where code = t.province and parent_code = '0000') as provincecode,(select code from province where code = t.city and parent_code = t.province ) as city from mobile_segment t ";

		ParameterizedRowMapper rw = new ParameterizedRowMapper() {
			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] model = new String[3];
				model[0] = rs.getString(1);
				model[1] = rs.getString(2);
				model[2] = rs.getString(3);
				return model;
			}
		};
		List results = getJdbcTemplate().query(sql, rw);
		Map segMap = new HashMap();

		for (int i = 0; i < results.size(); i++) {
			String[] datas = (String[]) results.get(i);
			if (datas[2] == null) {
				datas[2] = "";
			}
			if (datas[1] == null)
				segMap.put(datas[0], "");
			else {
				segMap.put(datas[0], datas[1] + "@" + datas[2]);
			}
		}

		return segMap;
	}

	public boolean isCellphonenumberExisted(String phone) {
		StringBuffer sql = new StringBuffer("select count(*) from user_info where cellphonenumber = '").append(phone).append("'");

		int count = getJdbcTemplate().queryForInt(sql.toString());
		if (count == 0) {
			return false;
		}
		return true;
	}

	public void saveUserInfo(UserInfo userInfo) {
		String sql = "insert into user_info (userid,cellphonenumber,TERMINALTYPE,status,provincecode,citycode,CREATEDATE, UA_TYPE_ID,DELETE_FLAG) values ( NEXT VALUE FOR USER_INFO_SEQ,?,'2','1',?,?,current timestamp,cast(? as DECIMAL),'0')";

		getJdbcTemplate().update(sql, new Object[] { userInfo.getCellphonenumber(), userInfo.getProvincecode(), userInfo.getCitycode(), userInfo.getUaTypeId() });
	}

	public void saveOrderHis(UserOrderHis userOrderHis) {
		logger.debug("saving UserOrderHis instance");
		String sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE) values(?,?,?,?,current timestamp,?,?,?)";

		getJdbcTemplate().update(sql, new Object[] { userOrderHis.getUniqueid(), userOrderHis.getCellphonenumber(), userOrderHis.getCellphonenumber(), userOrderHis.getServiceuniqueid(), userOrderHis.getOrdermethod(),
				userOrderHis.getFeetype(), userOrderHis.getFee() });
	}

	public Vasservice queryVasService(Integer id) throws Exception {
		String sql = "select * from vasservice where uniqueid = ?";
		List o = getJdbcTemplate().query(sql, new Object[] { id }, new RowMapper() {
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
					ret.setIsPackage(rs.getString("ISPACKAGE"));
					ret.setProducttype(rs.getString("product_type"));
				}
				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (Vasservice) o.get(0);
		}

		return null;
	}

	public Vasservice getVasserviceByVasIdAndCmd(String vasId, String cmd) {
		String sql = "select * from vasservice where vasid = ? and ordercode = ?";
		logger.info("========> " + sql);
		List o = getJdbcTemplate().query(sql, new Object[] { vasId, cmd }, new RowMapper() {
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
					ret.setIsPackage(rs.getString("ISPACKAGE"));
					ret.setProducttype(rs.getString("product_type"));
				}

				return ret;
			}
		});
		if ((o != null) && (o.size() > 0)) {
			return (Vasservice) o.get(0);
		}

		return new Vasservice();
	}

	public void saveUserOrder(UserOrder userOrder) {
		logger.debug("saving UserOrder instance");

		String sql = "insert into USER_ORDER (CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,PRIORITY,USERAREA) values(?,?,?,current timestamp,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(sql, new Object[] { userOrder.getId().getCellphonenumber(), userOrder.getId().getCellphonenumber(), userOrder.getId().getServiceuniqueid(), "1", userOrder.getFeetype(),
				userOrder.getFee(), userOrder.getOrderhisid(), Integer.valueOf(0), Integer.valueOf(2), userOrder.getUserarea() });
	}

	public String getContentIdByVasserviceUniqueid(Integer vasserviceUniqueId) {
		String sql = "select contentid from CONTENT_INFO c where c.serviceid = " + vasserviceUniqueId + " and c.createdate is not null order by c.createdate desc";
		logger.info("=====>" + sql);
		List list = getJdbcTemplate().query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("contentid");
			}
		});
		if ((list != null) && (list.size() != 0)) {
			return list.get(0).toString();
		}
		return "";
	}

	public String queryMsg(String string) {
		return string;
	}

	public void AddOrderRelation(String userPhone, String chargeparty, Vasservice service, String provinceCode, String cityCode, String channel, String spOrderId, String notifySpFlag, String valueOf) {
		AddOrderRelation(userPhone, service, provinceCode, cityCode, channel, spOrderId, notifySpFlag);
		
	}
}
