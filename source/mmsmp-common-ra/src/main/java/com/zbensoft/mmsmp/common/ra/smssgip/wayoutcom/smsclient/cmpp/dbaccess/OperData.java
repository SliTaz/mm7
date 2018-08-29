package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.dbaccess;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsclient.cmpp.SMSObj;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OperData {
    private static Logger log = Logger.getLogger(OperData.class);

    public OperData() {
    }

    public static void insertSMSLog(List list) {
        if (list != null && list.size() != 0) {
            Connection conn = null;
            PreparedStatement pstm = null;

            try {
                conn = DbAccess.getConnection();
                pstm = conn.prepareStatement("insert into dpushlog(PUSHID, OPID, PUSHADDR, PHONENO, CONTENT, PUSHFLAG, ERRTYPE, PUSHTYPE, PUSHTIME) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

                for(int i = 0; i < list.size(); ++i) {
                    SMSObj obj = (SMSObj)list.get(i);
                    pstm.setString(1, obj.getPushID());
                    pstm.setString(2, obj.getOpID());
                    pstm.setString(3, obj.getFrom());
                    pstm.setString(4, obj.getTo());
                    pstm.setString(5, obj.getContent());
                    pstm.setString(6, obj.getPushFlag());
                    pstm.setString(7, obj.getErrorType());
                    pstm.setString(8, obj.getPushType());
                    pstm.setTimestamp(9, obj.getPushTime());
                    pstm.addBatch();
                }

                pstm.executeBatch();
            } catch (Exception var8) {
                log.error(var8);
            } finally {
                DbAccess.Close(conn, pstm);
            }

        }
    }
}

