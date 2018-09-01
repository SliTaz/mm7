package com.zbensoft.mmsmp.common.ra.vas.commons.tcp;

import java.io.Serializable;

public abstract interface TcpClient
{
  public abstract void setDataHandler(IClientHandler paramIClientHandler, int paramInt);

  public abstract void setAutoReconnect(boolean paramBoolean, long paramLong);

  public abstract boolean connect(String paramString, int paramInt);

  public abstract int send(byte[] paramArrayOfByte);

  public abstract int send(Serializable paramSerializable);

  public abstract boolean disconnect();

  public abstract boolean isConnected();
}
