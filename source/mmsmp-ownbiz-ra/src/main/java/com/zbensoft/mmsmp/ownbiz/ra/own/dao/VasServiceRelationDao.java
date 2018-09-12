

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.OrderRelationUpdateNotifyRequest;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.MmsUserEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.SysParametersEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VasServiceRelationEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.entity.VaspEnitiy;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class VasServiceRelationDao implements Dao{
    private static final Log log = LogFactory.getLog(VasServiceRelationDao.class);

    public VasServiceRelationDao() {
    }

    public List<VasServiceRelationEntity> getDistributeVasServiceRelation(List<VaspEnitiy> own_sps) {
        return  getAllVasServiceRelation();
    }

    public List<VaspEnitiy> getAllVaspEnitiy() {
        return HttpHelper.getAllVaspEnitiy();
    }

    public List<VaspEnitiy> getAllOwnVasps() {
        return HttpHelper.getAllVaspEnitiy();
    }

    public List<VasServiceRelationEntity> getAllVasServiceRelation() {
        return HttpHelper.getAllVasServiceRelation();
    }

    public VasServiceRelationEntity getVasServiceRelation(String sp_productId) {
        return HttpHelper.getVasServiceRelation(sp_productId);
    }

    public MmsUserEntity queryMmsUserEntity(MO_SMMessage mos) {
    		//先不写
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
        return HttpHelper.getVasServiceRelation(spProductId);
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
        return  HttpHelper.queryContentCount(message.getProductId());
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
        return  HttpHelper.querySysParameters();
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
    	 return HttpHelper.getSystemParamBykey(key);
    }

    public void updateNotifySpStatus(String uniqueid, String userPhone, int status) {
//        try {
//            String sql = "update user_order_his set notify_sp_flag=? where uniqueid=(select ORDERHISID from user_order where cellphonenumber=? and SERVICEUNIQUEID=? fetch first 1 row only)";
//            log.info(sql + "{" + status + "," + userPhone + "," + uniqueid + "}");
//            this.getJdbcTemplate().update(sql, new Object[]{status, userPhone, uniqueid});
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//        }
    	HttpHelper.updateNotifySpStatus(uniqueid, userPhone, status);
    }
}
