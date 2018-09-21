package com.zbensoft.mmsmp.ftp.sysc.NumToGWMapftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NumToGWMapInfo {
    private static final Logger logger = Logger.getLogger(NumToGWMapInfo.class);
    public String segment;
    public String province;
    public String city;

    public NumToGWMapInfo() {
    }

    public static List<NumToGWMapInfo> parseFile(File file) throws Exception {
        List<NumToGWMapInfo> list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String[] strs = new String[4];

        for(int i = 0; i < strs.length; ++i) {
            strs[i] = "";
        }

        String str;
        while((str = br.readLine()) != null) {
            String[] strsTemp = str.split(",");

            for(int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            NumToGWMapInfo numInfo = new NumToGWMapInfo();
            numInfo.segment = strs[0].trim() + strs[1].trim();
            numInfo.province = strs[2].trim();
            numInfo.city = strs[3].trim();
            list.add(numInfo);
        }

        br.close();
        return list;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append(" segment=" + this.segment);
        sb.append(" province=" + this.province);
        sb.append(" city=" + this.city);
        sb.append(" ]");
        return sb.toString();
    }
}
