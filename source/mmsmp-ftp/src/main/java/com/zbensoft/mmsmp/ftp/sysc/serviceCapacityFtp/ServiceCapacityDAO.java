package com.zbensoft.mmsmp.ftp.sysc.serviceCapacityFtp;


import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;


public class ServiceCapacityDAO {
    private static final Logger logger = Logger.getLogger(ServiceCapacityDAO.class);

    public ServiceCapacityDAO() {
    }

    public void saveServiceCapacity(List<ServiceCapacity> ors) {
        Iterator iterator = ors.iterator();

        while(iterator.hasNext()) {
            ServiceCapacity spInfo = (ServiceCapacity)iterator.next();
            this.saveServiceCapacity(spInfo);
        }

    }

    public void saveServiceCapacity(ServiceCapacity serviceCapacity) {
//        try {
//            String sql = "";
//            sql = "select count(*) from service_capacity where sp_id = '" + serviceCapacity.SPID + "' and capacity = '" + DataUtil.getServiceType(serviceCapacity.ServiceType) + "'";
//            int count = this.getJdbcTemplate().queryForInt(sql);
//            int updateType = serviceCapacity.updateType;
//            String serviceType = serviceCapacity.ServiceType;
//            if (updateType == 1 && count == 0) {
//                this.saveInfos(serviceCapacity, serviceType);
//                logger.info("saveServiceCapacity(ServiceCapacity) - ServiceCapacity serviceCapacity=" + serviceCapacity);
//            } else if ((updateType == 2 || updateType == 3) && count > 0) {
//                if (updateType == 2) {
//                    this.updateInfos(serviceCapacity, serviceType);
//                    logger.info("updateServiceCapacity(ServiceCapacity) - ServiceCapacity serviceCapacity=" + serviceCapacity);
//                } else if (updateType == 3) {
//                    this.deleteInfos(serviceCapacity, serviceType);
//                    logger.info("deleteServiceCapacity(ServiceCapacity) - ServiceCapacity serviceCapacity=" + serviceCapacity);
//                }
//            } else if (updateType != 1 && updateType != 2 && updateType != 3) {
//                logger.info("updateType is error!");
//            }
//        } catch (Exception var6) {
//            var6.printStackTrace();
//        }

    }

    private void deleteInfos(ServiceCapacity serviceCapacity, String serviceType) throws UnsupportedEncodingException {
//        String sql = "delete from service_capacity  where sp_id = '" + serviceCapacity.SPID + "' and capacity = '" + DataUtil.getServiceType(serviceCapacity.ServiceType) + "'";
//        this.getJdbcTemplate().execute(sql);
//        if (serviceType.equals("31")) {
//            sql = "delete from service_capacity_mms    where recordsequenceid = '" + serviceCapacity.RecordSequenceID + "' and servicetype = '" + serviceCapacity.ServiceType + "' ";
//            this.getJdbcTemplate().execute(sql);
//        } else if (serviceType.equals("90")) {
//            sql = "delete from service_capacity_sms    where recordsequenceid = '" + serviceCapacity.RecordSequenceID + "' and servicetype = '" + serviceCapacity.ServiceType + "' ";
//            this.getJdbcTemplate().execute(sql);
//        }

    }

    private void updateInfos(ServiceCapacity serviceCapacity, String serviceType) throws UnsupportedEncodingException {
//        String sql = "updateSp service_capacity    set name = '" + DataUtil.getServiceTypeName(serviceCapacity.ServiceType) + "', " + "       capacity = '" + DataUtil.getServiceType(serviceCapacity.ServiceType) + "', " + "       status = '" + DataUtil.getServiceCapabilityStatus(serviceCapacity.ServiceCapabilityStatus) + "', " + "       sp_id = '" + serviceCapacity.SPID + "', " + "       capacity_id = '" + serviceCapacity.RecordSequenceID + "' " + " where sp_id = '" + serviceCapacity.SPID + "' and capacity = '" + DataUtil.getServiceType(serviceCapacity.ServiceType) + "'";
//        this.getJdbcTemplate().execute(sql);
//        if (serviceType.equals("31")) {
//            MMSCapabilityInfo mmsDemo = (MMSCapabilityInfo)serviceCapacity.ServiceCapabilityInfo;
//            sql = "updateSp service_capacity_mms    set accessno = '" + mmsDemo.accessNo + "', " + "       spip = '" + mmsDemo.SPIP + "', " + "       url = '" + mmsDemo.URL + "', " + "       account = '" + mmsDemo.account + "', " + "       password = '" + mmsDemo.password + "', " + "       auth_type = " + mmsDemo.auth_type + ", " + "       flownum = " + mmsDemo.flowNum + ", " + "       sprevid = '" + mmsDemo.spRevId + "', " + "       sprevpassword = '" + mmsDemo.spRevPassword + "', " + "       mmsurl = '" + mmsDemo.MMSURL + "', " + "       spstatusurl = '" + mmsDemo.spStatusUrl + "', " + "       recordsequenceid = '" + serviceCapacity.RecordSequenceID + "', " + "       startdate = '" + serviceCapacity.startDate + "', " + "       servicetype = '" + serviceCapacity.ServiceType + "', " + "       enddate = '" + serviceCapacity.endDate + "' " + "   where recordsequenceid = '" + serviceCapacity.RecordSequenceID + "' and servicetype = '" + serviceCapacity.ServiceType + "' ";
//            this.getJdbcTemplate().execute(sql);
//        } else if (serviceType.equals("90")) {
//            SMSCapabilityInfo smsDemo = (SMSCapabilityInfo)serviceCapacity.ServiceCapabilityInfo;
//            sql = "updateSp service_capacity_sms    set account = '" + smsDemo.account + "', " + "       password = '" + smsDemo.password + "', " + "       spip = '" + smsDemo.SPIP + "', " + "       sp_port = '" + smsDemo.SP_Port + "', " + "       accessno = '" + smsDemo.accessNo + "', " + "       accountpriority = " + smsDemo.accountPriority + ", " + "       flow = " + smsDemo.flow + ", " + "       newaccessno = '" + smsDemo.NEWaccessNo + "', " + "       sprevid = '" + smsDemo.spRevId + "', " + "       sprevpassword = '" + smsDemo.spRevPassword + "', " + "       smsurl = '" + smsDemo.SMSURL + "', " + "       spstatusurl = '" + smsDemo.spStatusUrl + "', " + "       recordsequenceid = '" + serviceCapacity.RecordSequenceID + "', " + "       startdate = '" + serviceCapacity.startDate + "', " + "       servicetype = '" + serviceCapacity.ServiceType + "', " + "       enddate = '" + serviceCapacity.endDate + "'" + "   where recordsequenceid = '" + serviceCapacity.RecordSequenceID + "' and servicetype = '" + serviceCapacity.ServiceType + "' ";
//            this.getJdbcTemplate().execute(sql);
//        }

    }

    private void saveInfos(ServiceCapacity serviceCapacity, String serviceType) throws UnsupportedEncodingException {
//        String sql = "insert into service_capacity            (name,             capacity,             status,             sp_id,             capacity_id)      values('" + DataUtil.getServiceTypeName(serviceCapacity.ServiceType) + "'" + "            , " + "            '" + DataUtil.getServiceType(serviceCapacity.ServiceType) + "'" + "            , " + "            '" + DataUtil.getServiceCapabilityStatus(serviceCapacity.ServiceCapabilityStatus) + "'" + "            , " + "            '" + serviceCapacity.SPID + "'" + "            , " + "            '" + serviceCapacity.RecordSequenceID + "'" + ")";
//        this.getJdbcTemplate().execute(sql);
//        if (serviceType.equals("31")) {
//            MMSCapabilityInfo mmsDemo = (MMSCapabilityInfo)serviceCapacity.ServiceCapabilityInfo;
//            sql = "insert into service_capacity_mms            (accessno,             spip,             url,             account,             password,             auth_type,             flownum,             sprevid,             sprevpassword,             mmsurl,             spstatusurl,             recordsequenceid,             startdate,             servicetype,             enddate)      values('" + mmsDemo.accessNo + "'" + "            , " + "            '" + mmsDemo.SPIP + "'" + "            , " + "            '" + mmsDemo.URL + "'" + "            , " + "            '" + mmsDemo.account + "'" + "            , " + "            '" + mmsDemo.password + "'" + "            , " + "            " + mmsDemo.auth_type + "            , " + "            " + mmsDemo.flowNum + "            , " + "            '" + mmsDemo.spRevId + "'" + "            , " + "            '" + mmsDemo.spRevPassword + "'" + "            , " + "            '" + mmsDemo.MMSURL + "'" + "            , " + "            '" + mmsDemo.spStatusUrl + "'" + "            , " + "            '" + serviceCapacity.RecordSequenceID + "'" + "            , " + "            '" + serviceCapacity.startDate + "'" + "            , " + "            '" + serviceCapacity.ServiceType + "'" + "            , " + "            '" + serviceCapacity.endDate + "'" + ")";
//            this.getJdbcTemplate().execute(sql);
//        } else if (serviceType.equals("90")) {
//            SMSCapabilityInfo smsDemo = (SMSCapabilityInfo)serviceCapacity.ServiceCapabilityInfo;
//            sql = "insert into service_capacity_sms            (account,             password,             spip,             sp_port,             accessno,             accountpriority,             flow,             newaccessno,             sprevid,             sprevpassword,             smsurl,             spstatusurl,             recordsequenceid,             startdate,             servicetype,             enddate)      values('" + smsDemo.account + "'" + "            , " + "            '" + smsDemo.password + "'" + "            , " + "            '" + smsDemo.SPIP + "'" + "            , " + "            '" + smsDemo.SP_Port + "'" + "            , " + "            '" + smsDemo.accessNo + "'" + "            , " + "            " + smsDemo.accountPriority + "            , " + "            " + smsDemo.flow + "            , " + "            '" + smsDemo.NEWaccessNo + "'" + "            , " + "            '" + smsDemo.spRevId + "'" + "            , " + "            '" + smsDemo.spRevPassword + "'" + "            , " + "            '" + smsDemo.SMSURL + "'" + "            , " + "            '" + smsDemo.spStatusUrl + "'" + "            , " + "            '" + serviceCapacity.RecordSequenceID + "'" + "            , " + "            '" + serviceCapacity.startDate + "'" + "            , " + "            '" + serviceCapacity.ServiceType + "'" + "            , " + "            '" + serviceCapacity.endDate + "'" + ")";
//            this.getJdbcTemplate().execute(sql);
//        }

    }
}

