package com.zbensoft.mmsmp.common.ra.vas.commons.tcp;

import java.io.Serializable;

public abstract interface IClientHandler
{
  public abstract void onConnect(String paramString, int paramInt);

  public abstract void onDisconnect();

  public abstract int slice(byte[] paramArrayOfByte);

  public abstract void onReceiveMsg(byte[] paramArrayOfByte);

  public abstract void onReceiveMsg(Serializable paramSerializable);

  public abstract void onSendedMsg(byte[] paramArrayOfByte);

  public abstract void onSendedMsg(Serializable paramSerializable);
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.IClientHandler
 * JD-Core Version:    0.6.0
 */