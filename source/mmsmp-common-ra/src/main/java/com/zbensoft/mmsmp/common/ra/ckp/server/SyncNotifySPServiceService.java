package com.zbensoft.mmsmp.common.ra.ckp.server;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface SyncNotifySPServiceService extends Service {
	public abstract String getSyncNotifySPAddress();

	public abstract SyncNotifySPService getSyncNotifySP() throws ServiceException;

	public abstract SyncNotifySPService getSyncNotifySP(URL paramURL) throws ServiceException;
}
