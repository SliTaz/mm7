

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.SysParametersEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class VasServiceRelationDao extends JdbcDaoSupport {
    private static final Log log = LogFactory.getLog(VasServiceRelationDao.class);

    public VasServiceRelationDao() {
    }

    public List<VasServiceRelationEntity> getDistributeVasServiceRelation(List<VaspEnitiy> own_sps) {
//        try {
//            String own_spid = "";
//
//            for(int i = 0; i < own_sps.size(); ++i) {
//                own_spid = own_spid + "'" + ((VaspEnitiy)own_sps.get(i)).getVaspId() + "',";
//            }
//
//            if (own_spid != null && !"".equals(own_spid)) {
//                own_spid = own_spid.substring(0, own_spid.length() - 1);
//            } else {
//                own_spid = "''";
//            }
//
//            String sql = "select vri.SP_PRODUCTID,v.SERVICEID ,v.VASNAME ,vs.CPID,c.CPTYPE,c.ACCESSURL,vs.SERVICENAME ,vs.UNIQUEID,vs.FEETYPE ,vs.ORDERFEE,vs.ONDEMANDFEE ,vs.ORDERCODE,vs.CANCELORDERCODE,vs.ONDEMANDCODE from vas v,vasservice vs,vasservice_reserve_info vri ,CP_INFO c where vs.cpid=c.cpid and vri.productid=vs.servicecode and v.vasid=vs.vasid and vs.VASPID in( " + own_spid + " ) and vs.STATUS='2' and c.STATUS='1' ";
//            log.info("刷新自主业务产品相关信息sql：" + sql + "{" + own_spid + "}");
//            ParameterizedRowMapper<VasServiceRelationEntity> rm = new ParameterizedRowMapper<VasServiceRelationEntity>() {
//                public VasServiceRelationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VasServiceRelationEntity model = new VasServiceRelationEntity();
//                    model.setSpProductId(rs.getString("SP_PRODUCTID"));
//                    model.setProductName(rs.getString("SERVICENAME"));
//                    model.setServiceId(rs.getString("SERVICEID"));
//                    model.setServiceName(rs.getString("VASNAME"));
//                    model.setCpId(rs.getLong("CPID"));
//                    model.setCpType(rs.getInt("CPTYPE"));
//                    model.setAccessUrl(rs.getString("ACCESSURL"));
//                    model.setVasserviceUniqueId(rs.getLong("UNIQUEID"));
//                    model.setOrderFee(rs.getDouble("ORDERFEE"));
//                    model.setDbFee(rs.getDouble("ONDEMANDFEE"));
//                    model.setFeeType(rs.getString("FEETYPE"));
//                    model.setOrderCode(rs.getString("ORDERCODE"));
//                    model.setCancelOrderCode(rs.getString("CANCELORDERCODE"));
//                    model.setOnDemandCode(rs.getString("ONDEMANDCODE"));
//                    return model;
//                }
//            };
//            return this.getJdbcTemplate().query(sql, rm);
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return null;
//        }
        return  null;
    }

    public List<VaspEnitiy> getAllVaspEnitiy() {
//        try {
//            String sql = "select vaspid,vaspname,businessphone from VASP";
//            log.info("刷新sp相关信息sql：" + sql);
//            ParameterizedRowMapper<VaspEnitiy> rm = new ParameterizedRowMapper<VaspEnitiy>() {
//                public VaspEnitiy mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VaspEnitiy vasp = new VaspEnitiy();
//                    vasp.setVaspId(rs.getString("vaspid"));
//                    vasp.setVaspName(rs.getString("vaspname"));
//                    vasp.setBusinessPhone(rs.getString("businessphone"));
//                    return vasp;
//                }
//            };
//            return this.getJdbcTemplate().query(sql, rm);
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//            return null;
//        }
        return null;
    }

    public List<VaspEnitiy> getAllOwnVasps() {
//        try {
//            String sql = "select vaspid,vaspname,businessphone from VASP where isownbusiness='1'";
//            log.info("刷新自有业务企业代码：" + sql);
//            ParameterizedRowMapper<VaspEnitiy> rm = new ParameterizedRowMapper<VaspEnitiy>() {
//                public VaspEnitiy mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VaspEnitiy vasp = new VaspEnitiy();
//                    vasp.setVaspId(rs.getString("vaspid"));
//                    vasp.setVaspName(rs.getString("vaspname"));
//                    vasp.setBusinessPhone(rs.getString("businessphone"));
//                    return vasp;
//                }
//            };
//            return this.getJdbcTemplate().query(sql, rm);
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//            return null;
//        }
        return null;
    }

    public List<VasServiceRelationEntity> getAllVasServiceRelation() {
//        try {
//            String sql = "select distinct vri.SP_PRODUCTID,v.SERVICEID ,v.VASNAME,vs.SERVICENAME ,vs.VASPID ,vs.CPID ,vs.UNIQUEID ,vs.FEETYPE,vs.ORDERFEE,vs.ONDEMANDFEE ,vs.CPID ,vs.ORDERCODE,vs.CANCELORDERCODE,vs.ONDEMANDCODE,vs.is_notify_sms,ci.reporturl,ci.CLIENTLINKMANTEL,ci.CPTYPE  from vas v,vasservice_reserve_info vri,vasservice vs LEFT JOIN CP_INFO ci ON ci.CPID = vs.CPID  where vri.productid=vs.servicecode and v.vasid=vs.vasid  and vs.STATUS='2'";
//            log.info("刷新自主业务产品相关信息sql：" + sql);
//            ParameterizedRowMapper<VasServiceRelationEntity> rm = new ParameterizedRowMapper<VasServiceRelationEntity>() {
//                public VasServiceRelationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VasServiceRelationEntity model = new VasServiceRelationEntity();
//                    model.setSpProductId(rs.getString("SP_PRODUCTID"));
//                    model.setSpId(rs.getString("VASPID"));
//                    model.setProductName(rs.getString("SERVICENAME"));
//                    model.setServiceId(rs.getString("SERVICEID"));
//                    model.setServiceName(rs.getString("VASNAME"));
//                    model.setVasserviceUniqueId(rs.getLong("UNIQUEID"));
//                    model.setOrderFee(rs.getDouble("ORDERFEE"));
//                    model.setDbFee(rs.getDouble("ONDEMANDFEE"));
//                    model.setFeeType(rs.getString("FEETYPE"));
//                    model.setCpId(rs.getLong("CPID"));
//                    model.setOrderCode(rs.getString("ORDERCODE"));
//                    model.setCancelOrderCode(rs.getString("CANCELORDERCODE"));
//                    model.setOnDemandCode(rs.getString("ONDEMANDCODE"));
//                    model.setIsNotifySms(rs.getString("is_notify_sms"));
//                    model.setReportUrl(rs.getString("reportUrl"));
//                    model.setClientLinkManTel(rs.getString("CLIENTLINKMANTEL"));
//                    model.setCpType(rs.getInt("CPTYPE"));
//                    return model;
//                }
//            };
//            return this.getJdbcTemplate().query(sql, rm);
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//            return null;
//        }
        return null;
    }

    public VasServiceRelationEntity getVasServiceRelation(String sp_productId) {
//        try {
//            String sql = "select vri.SP_PRODUCTID,v.SERVICEID ,v.VASNAME,vs.SERVICENAME ,vs.VASPID, vs.UNIQUEID ,vs.FEETYPE,vs.ORDERFEE,vs.ONDEMANDFEE ,vs.CPID from vas v,vasservice vs,vasservice_reserve_info vri where vri.productid=vs.servicecode and v.vasid=vs.vasid and vri.SP_PRODUCTID =? and vs.STATUS='2'";
//            log.info(sql + "{" + sp_productId + "}");
//            ParameterizedRowMapper<VasServiceRelationEntity> rm = new ParameterizedRowMapper<VasServiceRelationEntity>() {
//                public VasServiceRelationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VasServiceRelationEntity model = new VasServiceRelationEntity();
//                    model.setSpId(rs.getString("VASPID"));
//                    model.setSpProductId(rs.getString("SP_PRODUCTID"));
//                    model.setProductName(rs.getString("SERVICENAME"));
//                    model.setServiceId(rs.getString("SERVICEID"));
//                    model.setServiceName(rs.getString("VASNAME"));
//                    model.setVasserviceUniqueId(rs.getLong("UNIQUEID"));
//                    model.setOrderFee(rs.getDouble("ORDERFEE"));
//                    model.setDbFee(rs.getDouble("ONDEMANDFEE"));
//                    model.setFeeType(rs.getString("FEETYPE"));
//                    model.setCpId(rs.getLong("CPID"));
//                    return model;
//                }
//            };
//            return (VasServiceRelationEntity)this.getJdbcTemplate().queryForObject(sql, new Object[]{sp_productId}, rm);
//        } catch (EmptyResultDataAccessException var4) {
//            return null;
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return null;
//        }
        return null;
    }

    public MmsUserEntity queryMmsUserEntity(MO_SMMessage mos) {
//        String sql = "select c.CONTENTID,c.SERVICEID,c.CONTENTNAME,c.CONTENTPATH,vri.REQUESTACC,vri.PRODUCT_EXPAND_CODE  from CONTENT_INFO c ,VASSERVICE v,VASSERVICE_RESERVE_INFO vri where v.SERVICECODE=vri.PRODUCTID and c.SERVICEID=v.UNIQUEID and c.STATUS = '30' and c.CONTENTTYPE ='0' and v.FEETYPE='2' and v.STATUS='2' and v.VASPID = ? and v.VASID = ? and v.UNIQUEID = ? order by c.LASTAUDITTIME desc fetch first 1 row only";
//
//        try {
//            this.logger.info(sql + "{" + mos.getVaspId() + "," + mos.getVasId() + "," + mos.getServiceCode().split("#")[5] + "}");
//            ParameterizedRowMapper<MmsUserEntity> rw = new ParameterizedRowMapper<MmsUserEntity>() {
//                public MmsUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    MmsUserEntity user = new MmsUserEntity();
//                    user.setContentId(rs.getLong("contentId"));
//                    user.setServiceId(rs.getLong("serviceId"));
//                    String sendaddress = "";
//                    if (rs.getString("requestacc") != null && !"".equals(rs.getString("requestacc"))) {
//                        sendaddress = rs.getString("requestacc");
//                    }
//
//                    if (rs.getString("product_expand_code") != null && !"".equals(rs.getString("product_expand_code"))) {
//                        sendaddress = sendaddress + rs.getString("product_expand_code");
//                    }
//
//                    user.setSendAddress(sendaddress);
//                    user.setContentName(rs.getString("contentName"));
//                    user.setContentFile(rs.getString("contentPath"));
//                    return user;
//                }
//            };
//            MmsUserEntity mue = (MmsUserEntity)this.getJdbcTemplate().queryForObject(sql, new Object[]{mos.getVaspId(), mos.getVasId(), mos.getServiceCode().split("#")[5]}, rw);
//            if (mue == null) {
//                return null;
//            } else {
//                mue.setVasId(mue.getSendAddress());
//                mue.setVaspId(mos.getVaspId());
//                mue.setUserNumber(mos.getSendAddress());
//                mue.setChargeNumber(mos.getSendAddress());
//                return mue;
//            }
//        } catch (EmptyResultDataAccessException var5) {
//            return null;
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//            return null;
//        }
        return null;
    }

    public VasServiceRelationEntity getVasServiceRelationForDis(String spProductId, String spId) {
//        String sql = "select vs.CPID,c.CPTYPE,c.ACCESSURL,vs.UNIQUEID from CP_INFO c, VASSERVICE vs ,VASSERVICE_RESERVE_INFO vri where c.CPID = vs.CPID and vs.SERVICECODE = vri.PRODUCTID and vri.SP_PRODUCTID=? and vs.VASPID=? and c.STATUS='1' and vs.STATUS='2'";
//
//        try {
//            ParameterizedRowMapper<VasServiceRelationEntity> rw = new ParameterizedRowMapper<VasServiceRelationEntity>() {
//                public VasServiceRelationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    VasServiceRelationEntity model = new VasServiceRelationEntity();
//                    model.setCpId(rs.getLong("CPID"));
//                    model.setCpType(rs.getInt("CPTYPE"));
//                    model.setAccessUrl(rs.getString("ACCESSURL"));
//                    model.setVasserviceUniqueId(rs.getLong("UNIQUEID"));
//                    return model;
//                }
//            };
//            return (VasServiceRelationEntity)this.getJdbcTemplate().queryForObject(sql, new Object[]{spProductId, spId}, rw);
//        } catch (EmptyResultDataAccessException var5) {
//            return null;
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//            return null;
//        }
        return null;
    }

    public int queryContentCount(OrderRelationUpdateNotifyRequest message) {
//        String sql = "select count(c.CONTENTID) from CONTENT_INFO c, VASSERVICE vs ,VASSERVICE_RESERVE_INFO vri where c.SERVICEID = vs.UNIQUEID and vs.SERVICECODE = vri.PRODUCTID and vri.SP_PRODUCTID=? and vs.VASPID=? and vs.status='2'";
//
//        try {
//            ParameterizedRowMapper<Integer> rw = new ParameterizedRowMapper<Integer>() {
//                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    Integer count = rs.getInt(1);
//                    return count;
//                }
//            };
//            return (Integer)this.getJdbcTemplate().queryForObject(sql, new Object[]{message.getProductId(), message.getSpId()}, rw);
//        } catch (EmptyResultDataAccessException var4) {
//            return 0;
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return 0;
//        }
        return  1;
    }

    public List<SysParametersEntity> querySysParameters() {
//        try {
//            String sql = "select NAME,VALUE,DESCRIPTION from SYS_PARAMETERS";
//            ParameterizedRowMapper<SysParametersEntity> rw = new ParameterizedRowMapper<SysParametersEntity>() {
//                public SysParametersEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    SysParametersEntity model = new SysParametersEntity();
//                    model.setKey(rs.getString("NAME"));
//                    model.setValue(rs.getString("VALUE"));
//                    model.setDesc(rs.getString("DESCRIPTION"));
//                    return model;
//                }
//            };
//            log.info("刷新cooper_key sql:" + sql);
//            return this.getJdbcTemplate().query(sql, rw);
//        } catch (Exception var3) {
//            log.error(var3.getMessage(), var3);
//            return null;
//        }
        return null;
    }

    public String querySysParametersByKey(String key) {
//        try {
//            String sql = "select VALUE from SYS_PARAMETERS where NAME=? ";
//            String value = (String)this.getJdbcTemplate().queryForObject(sql, new Object[]{key}, String.class);
//            return value;
//        } catch (EmptyResultDataAccessException var4) {
//            return null;
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//            return null;
//        }
        return null;
    }

    public void updateNotifySpStatus(long uniqueid, String userPhone, int status) {
//        try {
//            String sql = "update user_order_his set notify_sp_flag=? where uniqueid=(select ORDERHISID from user_order where cellphonenumber=? and SERVICEUNIQUEID=? fetch first 1 row only)";
//            log.info(sql + "{" + status + "," + userPhone + "," + uniqueid + "}");
//            this.getJdbcTemplate().update(sql, new Object[]{status, userPhone, uniqueid});
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//        }

    }
}
