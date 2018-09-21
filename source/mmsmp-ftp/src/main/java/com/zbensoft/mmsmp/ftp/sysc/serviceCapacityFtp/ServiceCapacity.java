package com.zbensoft.mmsmp.ftp.sysc.serviceCapacityFtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zbensoft.mmsmp.ftp.util.SpFilter;
import org.apache.log4j.Logger;

public class ServiceCapacity {
    private static final Logger logger = Logger.getLogger(ServiceCapacity.class);
    public String RecordSequenceID;
    public int updateType;
    public String SPID;
    public String ServiceType;
    public String ServiceCapabilityStatus;
    public String startDate;
    public String endDate;
    public CapabilityInfo ServiceCapabilityInfo;

    public ServiceCapacity() {
    }

    public static List<ServiceCapacity> parseFile(File file, List<String> spIdList) throws Exception {
        List<ServiceCapacity> list = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String header = br.readLine();
        String[] strs = new String[30];

        String str;
        while((str = br.readLine()) != null && !"".equals(str)) {
            for(int i = 0; i < strs.length; ++i) {
                strs[i] = "";
            }

            String[] strsTemp = str.split("\t");

            for(int i = 0; i < strsTemp.length; ++i) {
                strs[i] = strsTemp[i];
            }

            ServiceCapacity serviceCapacity = new ServiceCapacity();
            serviceCapacity.RecordSequenceID = strs[0].trim();
            serviceCapacity.updateType = Integer.parseInt("".equals(strs[1].trim()) ? "0" : strs[1].trim());
            serviceCapacity.SPID = strs[2].trim();
            serviceCapacity.ServiceType = strs[3].trim();
            serviceCapacity.ServiceCapabilityStatus = strs[4].trim();
            serviceCapacity.startDate = strs[5].trim();
            serviceCapacity.endDate = strs[6].trim();
            if (serviceCapacity.ServiceType.equals("31")) {
                MMSCapabilityInfo mmsCapabilityInfo = new MMSCapabilityInfo();
                mmsCapabilityInfo.accessNo = strs[7].trim();
                mmsCapabilityInfo.SPIP = strs[8].trim();
                mmsCapabilityInfo.URL = strs[9].trim();
                mmsCapabilityInfo.account = strs[10].trim();
                mmsCapabilityInfo.password = strs[11].trim();
                mmsCapabilityInfo.auth_type = Integer.parseInt("".equals(strs[12].trim()) ? "0" : strs[12].trim());
                mmsCapabilityInfo.flowNum = Integer.parseInt("".equals(strs[13].trim()) ? "0" : strs[13].trim());
                mmsCapabilityInfo.spRevId = strs[14].trim();
                mmsCapabilityInfo.spRevPassword = strs[15].trim();
                mmsCapabilityInfo.MMSURL = strs[16].trim();
                mmsCapabilityInfo.spStatusUrl = strs[17].trim();
                serviceCapacity.ServiceCapabilityInfo = mmsCapabilityInfo;
            } else if (serviceCapacity.ServiceType.equals("90")) {
                SMSCapabilityInfo smsCapabilityInfo = new SMSCapabilityInfo();
                smsCapabilityInfo.account = strs[7].trim();
                smsCapabilityInfo.password = strs[8].trim();
                smsCapabilityInfo.SPIP = strs[9].trim();
                smsCapabilityInfo.SP_Port = strs[10].trim();
                smsCapabilityInfo.accessNo = strs[11].trim();
                smsCapabilityInfo.accountPriority = Integer.parseInt("".equals(strs[12].trim()) ? "0" : strs[12].trim());
                smsCapabilityInfo.flow = Integer.parseInt("".equals(strs[13].trim()) ? "0" : strs[13].trim());
                smsCapabilityInfo.NEWaccessNo = strs[14].trim();
                smsCapabilityInfo.spRevId = strs[15].trim();
                smsCapabilityInfo.spRevPassword = strs[16].trim();
                smsCapabilityInfo.SMSURL = strs[17].trim();
                smsCapabilityInfo.spStatusUrl = strs[18].trim();
                serviceCapacity.ServiceCapabilityInfo = smsCapabilityInfo;
            } else {
                serviceCapacity.ServiceCapabilityInfo = null;
            }

            if (SpFilter.spFilter(serviceCapacity.SPID, spIdList)) {
                list.add(serviceCapacity);
                logger.debug("readInFile(File) - String serviceCapacity=" + serviceCapacity);
                logger.info("serviceCapacity:" + serviceCapacity.ServiceCapabilityInfo);
            }
        }

        br.close();
        return list;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" [");
        sb.append("RecordSequenceID=" + this.RecordSequenceID);
        sb.append("updateType=" + this.updateType);
        sb.append("SPID=" + this.SPID);
        sb.append("ServiceType=" + this.ServiceType);
        sb.append("ServiceCapabilityStatus=" + this.ServiceCapabilityStatus);
        sb.append("startDate=" + this.startDate);
        sb.append("endDate=" + this.endDate);
        sb.append(" ]");
        return sb.toString();
    }
}

