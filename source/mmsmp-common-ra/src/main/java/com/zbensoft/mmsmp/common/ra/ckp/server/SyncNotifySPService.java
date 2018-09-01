package com.zbensoft.mmsmp.common.ra.ckp.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface SyncNotifySPService extends Remote {
	public abstract OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(OrderRelationUpdateNotifyRequest paramOrderRelationUpdateNotifyRequest) throws RemoteException;
}
