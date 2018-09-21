package com.zbensoft.mmsmp.ftp.sysc.serviceInfoFtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.SpFilter;
import org.apache.log4j.Logger;

public class ServiceInfo {
    private static final Logger logger = Logger.getLogger(ServiceInfo.class);
    public String recordSequenceID;
    public int updateType;
    public String spID;
    public String spName;
    public String serviceType;
    public int groupService;
    public String serviceCompose;
    public String serviceID;
    public String serviceName;
    public int serviceCredit;
    public int serviceStatus;
    public String introURL;
    public String wapintropic;
    public String servicecolumn;
    public String enterurl;
    public String confirmurl;
    public String priceurl;
    public String freeurl;
    public int needConfmBack;
    public int checkType;
    public String effDate;
    public String expDate;
    public int wAPServiceType;
    public String spOrderUrl;
    public int synOrderFunc;
    public int spPsedoFlag;
    public String resv1;
    public String resv2;

    public ServiceInfo() {
    }

    public static List<ServiceInfo> parseFile(File file, List<String> spIdList, List<String> serviceIdList) throws Exception {
        List<ServiceInfo> list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String header = br.readLine();
        String[] strs = new String[28];

        String str;
        while ((str = br.readLine()) != null) {
            for (int i = 0; i < strs.length; ++i) {
                strs[i] = "";
            }

            String[] strsTemp = str.split("\t");

            for (int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.recordSequenceID = strs[0].trim();
            serviceInfo.updateType = Integer.parseInt("".equals(strs[1].trim()) ? "0" : strs[1].trim());
            serviceInfo.spID = strs[2].trim();
            serviceInfo.spName = strs[3].trim();
            serviceInfo.serviceType = strs[4].trim();
            serviceInfo.groupService = Integer.parseInt("".equals(strs[5].trim()) ? "0" : strs[5].trim());
            serviceInfo.serviceCompose = strs[6].trim();
            serviceInfo.serviceID = strs[7].trim();
            serviceInfo.serviceName = strs[8].trim();

            try {
                serviceInfo.serviceCredit = Integer.parseInt("".equals(strs[9].trim()) ? "0" : strs[9].trim());
            } catch (Exception var12) {
                serviceInfo.serviceCredit = 0;
            }

            try {
                serviceInfo.serviceStatus = Integer.parseInt("".equals(strs[10].trim()) ? "0" : strs[10].trim());
            } catch (Exception var11) {
                serviceInfo.serviceStatus = 2;
            }

            serviceInfo.introURL = strs[11].trim();
            serviceInfo.wapintropic = strs[12].trim();
            serviceInfo.servicecolumn = strs[13].trim();
            serviceInfo.enterurl = strs[14].trim();
            serviceInfo.confirmurl = strs[15].trim();
            serviceInfo.priceurl = strs[16].trim();
            serviceInfo.freeurl = strs[17].trim();
            serviceInfo.needConfmBack = Integer.parseInt("".equals(strs[18].trim()) ? "0" : strs[18].trim());
            serviceInfo.checkType = Integer.parseInt("".equals(strs[19].trim()) ? "0" : strs[19].trim());
            serviceInfo.effDate = strs[20].trim();
            serviceInfo.expDate = strs[21].trim();
            serviceInfo.wAPServiceType = Integer.parseInt("".equals(strs[22].trim()) ? "0" : strs[22].trim());
            serviceInfo.spOrderUrl = strs[23].trim();
            serviceInfo.synOrderFunc = Integer.parseInt("".equals(strs[24].trim()) ? "0" : strs[24].trim());
            serviceInfo.spPsedoFlag = Integer.parseInt("".equals(strs[25].trim()) ? "0" : strs[25].trim());
            serviceInfo.resv1 = strs[26].trim();
            serviceInfo.resv2 = strs[27].trim();
//            if (SpFilter.spFilter(serviceInfo.spID, spIdList) && serviceFilterById(serviceIdList, serviceInfo.serviceID)) {
//                logger.info("serviceInfo.serviceID=" + serviceInfo.serviceID);
//                list.add(serviceInfo);
//                logger.debug("readInFile(File) - String serviceInfo=" + serviceInfo);
//            }

            if (SpFilter.spFilter(serviceInfo.spID, spIdList)) {
                logger.info("serviceInfo.serviceID=" + serviceInfo.serviceID);
                list.add(serviceInfo);
                logger.debug("readInFile(File) - String serviceInfo=" + serviceInfo);
            }
        }

        br.close();
        return list;
    }

    public static boolean serviceFilterById(List<String> serviceIdList, String serviceId) {
        if (serviceIdList != null) {
            for (int i = 0; i < serviceIdList.size(); ++i) {
                if (((String) serviceIdList.get(i)).toString().trim().equals(serviceId)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append(" recordSequenceID=" + this.recordSequenceID);
        sb.append(" updateType=" + this.updateType);
        sb.append(" spID=" + this.spID);
        sb.append(" spName=" + this.spName);
        sb.append(" serviceType=" + this.serviceType);
        sb.append(" groupService=" + this.groupService);
        sb.append(" serviceCompose=" + this.serviceCompose);
        sb.append(" serviceID=" + this.serviceID);
        sb.append(" serviceName=" + this.serviceName);
        sb.append(" serviceCredit=" + this.serviceCredit);
        sb.append(" serviceStatus=" + this.serviceStatus);
        sb.append(" introURL=" + this.introURL);
        sb.append(" wapintropic=" + this.wapintropic);
        sb.append(" servicecolumn=" + this.servicecolumn);
        sb.append(" enterurl=" + this.enterurl);
        sb.append(" confirmurl=" + this.confirmurl);
        sb.append(" priceurl=" + this.priceurl);
        sb.append(" freeurl=" + this.freeurl);
        sb.append(" needConfmBack=" + this.needConfmBack);
        sb.append(" checkType=" + this.checkType);
        sb.append(" effDate=" + this.effDate);
        sb.append(" expDate=" + this.expDate);
        sb.append(" wAPServiceType=" + this.wAPServiceType);
        sb.append(" spOrderUrl=" + this.spOrderUrl);
        sb.append(" synOrderFunc=" + this.synOrderFunc);
        sb.append(" spPsedoFlag=" + this.spPsedoFlag);
        sb.append(" resv1=" + this.resv1);
        sb.append(" resv2=" + this.resv2);
        sb.append(" ]");
        return sb.toString();
    }
}
