package com.zbensoft.mmsmp.ftp.sysc.serviceInfoFtp;

import com.zbensoft.mmsmp.ftp.util.HttpHelper;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;


public class ServiceInfoDAO {
    private static final Logger logger = Logger.getLogger(ServiceInfoDAO.class);

    public ServiceInfoDAO() {
    }

    public void saveServiceInfo(List<ServiceInfo> ors) {
        Iterator iterator = ors.iterator();

        while (iterator.hasNext()) {
            ServiceInfo serviceInfo = (ServiceInfo) iterator.next();
            this.saveServiceInfo(serviceInfo);
        }

    }

    public void saveServiceInfo(ServiceInfo serviceInfo) {
        int updateType = serviceInfo.updateType;
        try {
            if (1 == updateType || 2 == updateType)
                HttpHelper.saveOrUpdateAccess(serviceInfo);
            else if (3 == updateType)
                HttpHelper.deleteAccess(serviceInfo);
        } catch (Exception e) {
            logger.error("process service info error", e);
        }

    }

    public List queryListBySql() {
        return HttpHelper.queryListBySql();
    }
}
