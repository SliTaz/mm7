package com.zbensoft.mmsmp.ftp.sysc.NumToGWMapftp;

import com.zbensoft.mmsmp.ftp.util.HttpHelper;
import org.apache.log4j.Logger;

import java.util.List;

public class NumToGWMapInfoDAO  {
    private static final Logger logger = Logger.getLogger(NumToGWMapInfoDAO.class);

    public NumToGWMapInfoDAO() {
    }

    public void saveNumToGWMapInfo(final List<NumToGWMapInfo> ors) {

//        try {
//            String sql = "delete from mobile_segment";
//            logger.info(sql);
//            this.getJdbcTemplate().execute(sql);
//            String sqlInsert = "insert into mobile_segment(segment,province,city,id) values(?,?,?,?)";
//            this.getJdbcTemplate().batchUpdate(sqlInsert, new BatchPreparedStatementSetter() {
//                public int getBatchSize() {
//                    return ors.size();
//                }
//
//                public void setValues(PreparedStatement ps, int i) throws SQLException {
//                    NumToGWMapInfo numToGWMapInfo = (NumToGWMapInfo)ors.get(i);
//                    ps.setString(1, numToGWMapInfo.segment);
//                    ps.setString(2, numToGWMapInfo.province);
//                    ps.setString(3, numToGWMapInfo.city);
//                    ps.setInt(4, i + 1);
//                }
//            });
//            logger.info("数据成功导入结束 ");
//        } catch (DataAccessException var4) {
//            logger.info("数据导入出错");
//            var4.printStackTrace();
//        }

    }
}

