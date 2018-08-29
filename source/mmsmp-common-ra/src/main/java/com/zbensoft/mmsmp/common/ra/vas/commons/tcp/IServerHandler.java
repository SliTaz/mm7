package com.zbensoft.mmsmp.common.ra.vas.commons.tcp;

import java.io.Serializable;

public abstract interface IServerHandler
{
  public abstract void onConnect(int paramInt1, String paramString, int paramInt2);

  public abstract void onDisconnect(int paramInt, Object paramObject);

  public abstract int slice(int paramInt, byte[] paramArrayOfByte);

  public abstract void onReceiveMsg(int paramInt, Object paramObject, byte[] paramArrayOfByte);

  public abstract void onReceiveMsg(int paramInt, Object paramObject, Serializable paramSerializable);

  public abstract void onSendedMsg(int paramInt, Object paramObject, byte[] paramArrayOfByte);

  public abstract void onSendedMsg(int paramInt, Object paramObject, Serializable paramSerializable);
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.IServerHandler
 * JD-Core Version:    0.6.0
 */