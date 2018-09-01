package com.zbensoft.mmsmp.common.ra.common.mina;

public abstract interface TcpClient
{
  public abstract int connect(String paramString, int paramInt);

  public abstract int connect(String paramString, int paramInt, Object paramObject);

  public abstract void reConnect();

  public abstract void disConnect();
}
