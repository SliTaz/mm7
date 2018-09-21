package com.zbensoft.mmsmp.ftp.sysc.orderRelationsFtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.SpFilter;
import org.apache.log4j.Logger;

public class OrderRelation {
    private static final Logger logger = Logger.getLogger(OrderRelation.class);
    public String recordSequenceId;
    public int userIdType;
    public String userId;
    public String serviceType;
    public String spId;
    public String productId;
    public int updateType;
    public String updateTime;
    public String updateDesc;
    public String linkId;
    public String content;
    public String effectiveDate;
    public String expireDate;
    public String time_stamp;
    public String encodeStr;

    public OrderRelation() {
    }

    public static List<OrderRelation> parseFile(File file, List<String> spIdList) throws Exception {
        List<OrderRelation> list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String header = br.readLine();
        String[] strs = new String[16];

        for (int i = 0; i < strs.length; ++i) {
            strs[i] = "";
        }

        String str;
        while ((str = br.readLine()) != null) {
            String[] strsTemp = str.split("\t");

            for (int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            OrderRelation or = new OrderRelation();
            or.recordSequenceId = strs[0].trim();
            or.userIdType = Integer.parseInt("".equals(strs[1].trim()) ? "0" : strs[1].trim());
            or.userId = strs[2].trim();
            or.serviceType = strs[3].trim();
            or.spId = strs[4].trim();
            or.productId = strs[5].trim();
            or.updateType = Integer.parseInt("".equals(strs[6].trim()) ? "0" : strs[6].trim());
            or.updateTime = strs[7].trim();
            or.updateDesc = strs[8].trim();
            or.linkId = strs[9].trim();
            or.content = strs[10].trim();
            or.effectiveDate = strs[11].trim();
            or.expireDate = strs[12].trim();
            or.time_stamp = strs[13].trim();
            or.encodeStr = strs[14].trim();
            if (SpFilter.spFilter(or.spId, spIdList)) {
                logger.debug("readInFile(File) - String order_relation=" + or);
                list.add(or);
            }
        }

        br.close();
        return list;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append(" recordSequenceId=");
        sb.append(this.recordSequenceId);
        sb.append(" userIdType=");
        sb.append(this.userIdType);
        sb.append(" userId=");
        sb.append(this.userId);
        sb.append(" serviceType=");
        sb.append(this.serviceType);
        sb.append(" spId=");
        sb.append(this.spId);
        sb.append(" productId=");
        sb.append(this.productId);
        sb.append(" updateType=");
        sb.append(this.updateType);
        sb.append(" updateTime=");
        sb.append(this.updateTime);
        sb.append(" updateDesc=");
        sb.append(this.updateDesc);
        sb.append(" linkId=");
        sb.append(this.linkId);
        sb.append(" content=");
        sb.append(this.content);
        sb.append(" effectiveDate=");
        sb.append(this.effectiveDate);
        sb.append(" expireDate=");
        sb.append(this.expireDate);
        sb.append(" time_stamp=");
        sb.append(this.time_stamp);
        sb.append(" encodeStr=");
        sb.append(this.encodeStr);
        sb.append(" ]");
        return sb.toString();
    }
}
