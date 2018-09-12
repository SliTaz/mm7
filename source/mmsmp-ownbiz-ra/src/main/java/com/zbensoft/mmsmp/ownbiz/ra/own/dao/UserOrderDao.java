

package com.zbensoft.mmsmp.ownbiz.ra.own.dao;

import com.zbensoft.mmsmp.ownbiz.ra.own.entity.UserOrderEntity;
import com.zbensoft.mmsmp.ownbiz.ra.own.util.HttpHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserOrderDao implements Dao {
    private static final Log log = LogFactory.getLog(SystemParamDao.class);

    public UserOrderDao() {
    }

    public boolean isExistOrderRelation(String cellPhoneNumber, String serviceUniqueid) {
//        try {
//            StringBuilder builder = new StringBuilder();
//            builder.append(" SELECT count (*) FROM USER_ORDER uo WHERE uo.CELLPHONENUMBER = ? AND uo.SERVICEUNIQUEID =");
//            builder.append(" (SELECT vass.UNIQUEID FROM VASSERVICE vass, VASSERVICE_RESERVE_INFO vri ");
//            builder.append(" WHERE vass.SERVICECODE = vri.PRODUCTID AND vri.SP_PRODUCTID = ?) ");
//            long count = this.getJdbcTemplate().queryForLong(builder.toString(), new Object[]{cellPhoneNumber, serviceUniqueid});
//            this.logger.info("select sql :" + builder.toString() + "{" + cellPhoneNumber + serviceUniqueid + "}");
//            return count > 0L;
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//            return false;
//        }
    	
        return HttpHelper.isExistOrderRelation(cellPhoneNumber,serviceUniqueid);
    }

    public long insertUserOrderHis(UserOrderEntity userOrder) {
//        String sql = "select NEXT VALUE FOR USER_ORDER_HIS_SEQ from sysibm.sysdummy1";
//        long hisID = (long)this.getJdbcTemplate().queryForInt(sql);
//
//        try {
//            sql = "insert into USER_ORDER_HIS(UNIQUEID, CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE,SP_ORDERID,notify_sp_flag) values(?,?,?,?,current timestamp,?,?,?,?,?)";
//            this.logger.info("insert user_order_his sql :" + sql + "{" + hisID + userOrder.getCellPhoneNo() + userOrder.getChargeParty() + "," + userOrder.getServiceUniqueId() + "," + userOrder.getOrderMethod() + "," + userOrder.getFeeType() + "," + userOrder.getFee() + "," + userOrder.getSpOrderId() + "," + userOrder.getNotifySpFlag() + "}");
//            this.getJdbcTemplate().update(sql, new Object[]{hisID, userOrder.getCellPhoneNo(), userOrder.getChargeParty(), userOrder.getServiceUniqueId(), userOrder.getOrderMethod(), userOrder.getFeeType(), userOrder.getFee(), userOrder.getSpOrderId(), userOrder.getNotifySpFlag()});
//        } catch (Exception var6) {
//            log.error(var6.getMessage(), var6);
//        }

        return HttpHelper.insertUserOrderHis(userOrder);
    }

    public long insertUserOrder(UserOrderEntity userOrder, long hisID) {
//        try {
//            String sql = "insert into USER_ORDER(CELLPHONENUMBER, CHARGEPARTY, SERVICEUNIQUEID, ORDERDATE, ORDERMETHOD, FEETYPE, FEE, ORDERHISID, STATUS,PRIORITY,ISPACKAGE,USERAREA,SP_ORDERID,notify_sp_flag) values(?,?,?,current timestamp,?,?,?,?,?,?,?,(select PROVINCE from MOBILE_SEGMENT where SEGMENT=substr(?,1,7) fetch first 1 rows only),?,?)";
//            this.logger.info("insert user_order sql :" + sql + "{" + userOrder.getCellPhoneNo() + "," + userOrder.getChargeParty() + "," + userOrder.getServiceUniqueId() + "," + userOrder.getOrderMethod() + "," + userOrder.getFeeType() + "," + userOrder.getFee() + "," + hisID + "," + 0 + "," + 2 + "," + "0," + userOrder.getCellPhoneNo() + "," + userOrder.getSpOrderId() + "," + userOrder.getNotifySpFlag() + "}");
//            this.getJdbcTemplate().update(sql, new Object[]{userOrder.getCellPhoneNo(), userOrder.getChargeParty(), userOrder.getServiceUniqueId(), userOrder.getOrderMethod(), userOrder.getFeeType(), userOrder.getFee(), hisID, 0, 2, "0", userOrder.getCellPhoneNo(), userOrder.getSpOrderId(), userOrder.getNotifySpFlag()});
//        } catch (Exception var5) {
//            log.error(var5.getMessage(), var5);
//        }

        return HttpHelper.insertUserOrder(userOrder,hisID) ;
    }

    public void deleteUserOrder(String userPhone, String serviceUniqueID) {
//        String sql = "select ORDERHISID from user_order t where t.CELLPHONENUMBER=? and t.SERVICEUNIQUEID=? fetch first 1 rows only ";
//
//        try {
//            log.info(sql + "{" + userPhone + "," + serviceUniqueID + "}");
//            long uniqueID = this.getJdbcTemplate().queryForLong(sql, new Object[]{userPhone, serviceUniqueID});
//            if (0L != uniqueID) {
//                sql = "update USER_ORDER_HIS set CANCELORDERDATE = current timestamp, CANCELORDERMETHOD = '1' where UNIQUEID = ? ";
//                this.getJdbcTemplate().update(sql, new Object[]{uniqueID});
//                log.info(sql + "{" + uniqueID + "}");
//                String delSql = "delete from USER_ORDER where ORDERHISID=? ";
//                log.info(sql + "{" + uniqueID + "}");
//                this.getJdbcTemplate().update(delSql, new Object[]{uniqueID});
//            }
//        } catch (Exception var8) {
//            log.error(var8.getMessage(), var8);
//        }
    	HttpHelper.deleteUserOrder(userPhone,serviceUniqueID);
    }
}
