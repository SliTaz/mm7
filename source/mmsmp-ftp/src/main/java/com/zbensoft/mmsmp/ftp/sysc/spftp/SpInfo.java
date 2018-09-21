package com.zbensoft.mmsmp.ftp.sysc.spftp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.SpFilter;
import org.apache.log4j.Logger;

public class SpInfo {
    private static final Logger logger = Logger.getLogger(SpInfo.class);
    public String recordSequenceID;
    public int updateType;
    public String spid;
    public String spName;
    public int spPsedoFlag;
    public int spStatus;
    public String spAreaID;
    public String spCity;
    public String spSrvPhone;
    public int isTrust;
    public String spOrderUrl;
    public String orderKey;
    public int synOrderFunc;
    public String effDate;
    public String expDate;
    public String reserve1;
    public String reserve2;

    public SpInfo() {
    }

    public static List<SpInfo> parseFile(File file, List<String> spIdList) throws Exception {
        List<SpInfo> list = new ArrayList();
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GBK"));
        String[] strs = new String[17];

        String str;
        while((str = br.readLine()) != null) {
            for(int i = 0; i < strs.length; ++i) {
                strs[i] = "";
            }

            String[] strsTemp = str.split("\t");

            for(int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            SpInfo spInfo = new SpInfo();
            spInfo.recordSequenceID = strs[0].trim();
            spInfo.updateType = Integer.parseInt("".equals(strs[1].trim()) ? "0" : strs[1].trim());
            spInfo.spid = strs[2].trim();
            spInfo.spName = strs[3].trim();
            spInfo.spPsedoFlag = Integer.parseInt("".equals(strs[4].trim()) ? "0" : strs[4].trim());
            spInfo.spStatus = Integer.parseInt("".equals(strs[5].trim()) ? "0" : strs[5].trim());
            spInfo.spAreaID = strs[6].trim();
            spInfo.spCity = strs[7].trim();
            spInfo.spSrvPhone = strs[8].trim();
            spInfo.isTrust = Integer.parseInt("".equals(strs[9].trim()) ? "0" : strs[9].trim());
            spInfo.spOrderUrl = strs[10].trim();
            spInfo.orderKey = strs[11].trim();
            spInfo.synOrderFunc = Integer.parseInt("".equals(strs[12].trim()) ? "0" : strs[12].trim());
            spInfo.effDate = strs[13].trim();
            spInfo.expDate = strs[14].trim();
            spInfo.reserve1 = strs[15].trim();
            spInfo.reserve2 = strs[16].trim();
            if (SpFilter.spFilter(spInfo.spid, spIdList)) {
                list.add(spInfo);
                logger.info("readInFile(File) - String spInfo=" + spInfo.spid);
            }
        }

        br.close();
        return list;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append(" recordSequenceID=" + this.recordSequenceID);
        sb.append(" updateType=" + this.updateType);
        sb.append(" spid=" + this.spid);
        sb.append(" spName=" + this.spName);
        sb.append(" spPsedoFlag=" + this.spPsedoFlag);
        sb.append(" spStatus=" + this.spStatus);
        sb.append(" spAreaID=" + this.spAreaID);
        sb.append(" spCity=" + this.spCity);
        sb.append(" spSrvPhone=" + this.spSrvPhone);
        sb.append(" isTrust=" + this.isTrust);
        sb.append(" spOrderUrl=" + this.spOrderUrl);
        sb.append(" orderKey=" + this.orderKey);
        sb.append(" synOrderFunc=" + this.synOrderFunc);
        sb.append(" effDate=" + this.effDate);
        sb.append(" expDate=" + this.expDate);
        sb.append(" reserve1=" + this.reserve1);
        sb.append(" reserve2=" + this.reserve2);
        sb.append(" ]");
        return sb.toString();
    }
}

