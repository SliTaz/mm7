package com.zbensoft.mmsmp.ftp.sysc.orderRelationsFtp;

import com.zbensoft.mmsmp.ftp.sysc.orderRelationsFtp.bean.Vasservice;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;


public class UserRelationDAO {
    private static final Logger logger = Logger.getLogger(UserRelationDAO.class);

    public UserRelationDAO() {
    }

    public void saveRelations(List<OrderRelation> ors) {
        Iterator iterator = ors.iterator();

        while (iterator.hasNext()) {
            OrderRelation orderRelation = (OrderRelation) iterator.next();
            this.saveRelations(orderRelation);
        }

    }

    public void saveRelations(String user_number, String product_id) {
        OrderRelation or = new OrderRelation();
        or.userId = user_number;
        or.productId = product_id;
        this.saveRelations(or);
    }

    public void saveRelations(OrderRelation orderRelation) {
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            int updateType = orderRelation.updateType;
//            String effectiveDate = orderRelation.effectiveDate;
//            String expireDate = orderRelation.expireDate;
//            String updateDate = orderRelation.updateTime;
//
//            try {
//                if (expireDate != null && !"".equals(expireDate)) {
//                    expireDate = expireDate.substring(0, 4) + "-" + expireDate.substring(4, 6) + "-" + expireDate.substring(6, 8) + " " + expireDate.substring(8, 10) + ":" + expireDate.substring(10, 12) + ":" + expireDate.substring(12, 14);
//                }
//
//                if (updateDate != null && !"".equals(updateDate)) {
//                    updateDate = updateDate.substring(0, 4) + "-" + updateDate.substring(4, 6) + "-" + updateDate.substring(6, 8) + " " + updateDate.substring(8, 10) + ":" + updateDate.substring(10, 12) + ":" + updateDate.substring(12, 14);
//                }
//
//                if (effectiveDate != null && !"".equals(effectiveDate)) {
//                    effectiveDate = effectiveDate.substring(0, 4) + "-" + effectiveDate.substring(4, 6) + "-" + effectiveDate.substring(6, 8) + " " + effectiveDate.substring(8, 10) + ":" + effectiveDate.substring(10, 12) + ":" + effectiveDate.substring(12, 14);
//                } else {
//                    if (expireDate != null && !"".equals(expireDate)) {
//                        effectiveDate = expireDate;
//                    }
//
//                    if (updateDate != null && !"".equals(updateDate)) {
//                        effectiveDate = updateDate;
//                    }
//                }
//            } catch (Exception var14) {
//                logger.info(new String(("订购关系的生效日期格式不正确，日期为： " + orderRelation.effectiveDate + "，记录订购关系生效日期为空！").getBytes("gbk"), "gbk"));
//            }
//
//            Vasservice vasservice = this.getVasservice(orderRelation);
//            if (vasservice == null) {
//                logger.info(new String(("产品信息不存在，产品代码为： " + orderRelation.productId).getBytes("gbk"), "gbk"));
//                return;
//            }
//
//            int uniqueid = vasservice.getUniqueid();
//            String sql = "select count(*) from user_order where cellphonenumber = '" + FormatPhone(orderRelation.userId) + "' and serviceuniqueid = " + uniqueid;
//            int count = this.getJdbcTemplate().queryForInt(sql);
//            if (updateType == 1 && count == 0) {
//                this.saveUserInfo(orderRelation, format);
//                double fee = 0.0D;
//                if (vasservice.getFeetype().equals("1")) {
//                    fee = Double.valueOf(vasservice.getOrderfee() != null ? vasservice.getOrderfee().toString() : "0");
//                } else if (vasservice.getFeetype().equals("2")) {
//                    fee = Double.valueOf(vasservice.getOrderfee() != null ? vasservice.getOndemandfee().toString() : "0");
//                }
//
//                Integer userOrderHisId = this.saveUserOrderHistory(orderRelation, vasservice, uniqueid, fee, effectiveDate, expireDate);
//                this.saveUserOrder(orderRelation, format, vasservice, uniqueid, fee, userOrderHisId, effectiveDate);
//                logger.info("saveRelations(OrderRelation) - OrderRelation orderRelation=" + orderRelation);
//            } else if (updateType == 2 && count > 0) {
//                sql = "updateSp user_order_his             set cancelorderdate =to_date('" + updateDate + "', 'yyyy-mm-dd hh24:mi:ss'), " + "   cancelordermethod = '" + DataUtil.getServiceTypeForOrder(orderRelation.serviceType) + "'  " + " where uniqueid = " + "(select orderhisid from user_order where " + "cellphonenumber = '" + FormatPhone(orderRelation.userId) + "' and serviceuniqueid = " + uniqueid + ")";
//                logger.info(sql);
//                this.getJdbcTemplate().execute(sql);
//                sql = "delete from user_order            where cellphonenumber = '" + FormatPhone(orderRelation.userId) + "' " + "   and serviceuniqueid = " + uniqueid;
//                logger.info(sql);
//                this.getJdbcTemplate().execute(sql);
//            }
//        } catch (DataAccessException var15) {
//            var15.printStackTrace();
//        } catch (NumberFormatException var16) {
//            var16.printStackTrace();
//        } catch (UnsupportedEncodingException var17) {
//            var17.printStackTrace();
//        }

    }

    private void saveUserOrder(OrderRelation orderRelation, SimpleDateFormat format, Vasservice vasservice, int uniqueid, double fee, Integer userOrderHisId, String effectiveDate) {
//        String sql = "insert into user_order   (cellphonenumber,    chargeparty,    serviceuniqueid,    orderdate,    ordermethod,    status,    effectivedate,    transactionid,    fee,    feetype,    orderhisid) values   ('" + FormatPhone(orderRelation.userId) + "', " + "   '" + FormatPhone(orderRelation.userId) + "', " + "   " + uniqueid + ", " + "   to_date('" + format.format(new Date()) + "', 'yyyy-mm-dd hh24:mi:ss'), " + "   '" + DataUtil.getServiceTypeForOrder(orderRelation.serviceType) + "', " + "   0, " + "   to_date('" + effectiveDate + "', 'yyyy-mm-dd hh24:mi:ss'), " + "   '" + orderRelation.linkId + "', " + "   " + fee + ", " + "   '" + vasservice.getFeetype() + "', " + "   " + userOrderHisId + " " + "   )";
//        this.getJdbcTemplate().execute(sql);
    }

    private Integer saveUserOrderHistory(OrderRelation orderRelation, Vasservice vasservice, int uniqueid, double fee, String effectiveDate, String expireDate) {
//        String sql = "select max(uniqueid) + 1 uniqueid from user_order_his";
//        final List list = new ArrayList();
//        this.getJdbcTemplate().query(sql, new RowMapper() {
//            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//                int userOrderId = rs.getInt("uniqueid");
//                list.add(userOrderId);
//                return list;
//            }
//        });
//        Integer userOrderId = 0;
//        if (list != null && list.size() != 0 && list.get(0) != null && (Integer)list.get(0) != 0) {
//            userOrderId = (Integer)list.get(0);
//        } else {
//            userOrderId = 1;
//        }
//
//        sql = "insert into user_order_his   (uniqueid,                  serviceuniqueid,           cellphonenumber,           chargeparty,               orderdate,                 ordermethod,               effectivedate,             expiredate,               fee)                    values                       (" + userOrderId + ", " + uniqueid + ", " + "   '" + FormatPhone(orderRelation.userId) + "', '" + FormatPhone(orderRelation.userId) + "', to_date('" + effectiveDate + "', 'yyyy-mm-dd hh24:mi:ss'), " + "   '" + vasservice.getFeetype() + "',to_date('" + effectiveDate + "', 'yyyy-mm-dd hh24:mi:ss'), to_date('" + expireDate + "', 'yyyy-mm-dd hh24:mi:ss'), " + fee + ")";
//        logger.info(sql);
//        this.getJdbcTemplate().execute(sql);
        return 0;
    }

    private boolean checkUserInfo(String cellphonenumber) {
//        String sql = " select count(" + cellphonenumber + ") from user_info u where u.cellphonenumber ='" + cellphonenumber + "' ";
//        int count = 0;
//
//        try {
//            count = this.getJdbcTemplate().queryForInt(new String(sql.getBytes(), "gbk"));
//        } catch (DataAccessException var5) {
//            var5.printStackTrace();
//        } catch (UnsupportedEncodingException var6) {
//            var6.printStackTrace();
//        }
//
//        return count > 0;
        return true;
    }

    private void saveUserInfo(OrderRelation orderRelation, SimpleDateFormat format) {
//        if (!this.checkUserInfo(FormatPhone(orderRelation.userId))) {
//            String areaCode = this.getAreaCode();
//            String sql = "select max(userid) + 1 userid from user_info";
//            final List list = new ArrayList();
//            this.getJdbcTemplate().query(sql, new RowMapper() {
//                public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//                    int uniqueid = rs.getInt("userid");
//                    list.add(uniqueid);
//                    return list;
//                }
//            });
//            Integer uniqueid = 0;
//            if (list != null && list.size() != 0 && list.get(0) != null && (Integer)list.get(0) != 0) {
//                uniqueid = (Integer)list.get(0);
//            } else {
//                uniqueid = 1;
//            }
//
//            sql = " insert into user_info                                   (userid, cellphonenumber, status, delete_flag, createdate, provincecode)  values                                                  (" + uniqueid + ", " + " '" + FormatPhone(orderRelation.userId) + "', '1', '0',  to_date('" + format.format(new Date()) + "', 'yyyy-mm-dd hh24:mi:ss'), '" + areaCode + "' )";
//            logger.info(sql);
//            this.getJdbcTemplate().execute(new String(sql.getBytes(), "gbk"));
//        }

    }

    private String getAreaCode() {
//        String sql = "select value from sys_parameters where NAME = 'PLATFORM_AREA'";
//        final List list = new ArrayList();
//        this.getJdbcTemplate().query(sql, new RowMapper() {
//            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//                String value = rs.getString("value");
//                list.add(value);
//                return list;
//            }
//        });
//        String value = null;
//        if (list != null && list.size() != 0 && list.get(0) != null) {
//            value = list.get(0).toString();
//        } else {
//            logger.info("平台所属地域为null，会导致产品信息在管理门户无法展示");
//        }
//
//        String[] temp = value.split("@");
//        return temp[0];
        return "";
    }

    private Vasservice getVasservice(OrderRelation orderRelation) {
//        String sql = "select * from vasservice t where t.servicecode = '" + orderRelation.productId + "'";
//        final List list = new ArrayList();
//        this.getJdbcTemplate().query(sql, new RowMapper() {
//            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
//                Vasservice vasservice = new Vasservice();
//                vasservice.setUniqueid(rs.getInt("uniqueid"));
//                vasservice.setOrderfee(rs.getDouble("orderfee"));
//                vasservice.setOndemandfee(rs.getDouble("ondemandfee"));
//                vasservice.setFeetype(rs.getString("feetype"));
//                list.add(vasservice);
//                return list;
//            }
//        });
//        if (list != null && list.size() != 0) {
//            Vasservice vasservice = (Vasservice)list.get(0);
//            return vasservice;
//        } else {
//            return null;
//        }
        return null;
    }

    public static String FormatPhone(String phone) {
        String cellPhone = phone;
        if (phone != null && phone.trim().length() > 1) {
            if (phone.startsWith("86")) {
                cellPhone = phone.substring(2, phone.length());
            } else if (phone.startsWith("+86")) {
                cellPhone = phone.substring(3, phone.length());
            }
        } else {
            cellPhone = phone;
        }

        return cellPhone;
    }
}
