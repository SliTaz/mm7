package com.zbensoft.mmsmp.ftp.util;

import com.zbensoft.mmsmp.ftp.sysc.serviceInfoFtp.ServiceInfo;
import com.zbensoft.mmsmp.ftp.sysc.serviceInfoFtp.ServiceInfoDAO;
import org.junit.Test;

public class SpAccessDaoTest {
    @Test
    public void testAdd() {
        ServiceInfoDAO dao = new ServiceInfoDAO();
        ServiceInfo info = new ServiceInfo();
        info.spID="sys_test_import1";
        info.serviceType = "31";
        info.updateType = 1;
        dao.saveServiceInfo(info);
    }
}
