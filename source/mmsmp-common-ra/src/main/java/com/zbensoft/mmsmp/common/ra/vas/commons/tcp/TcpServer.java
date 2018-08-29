package com.zbensoft.mmsmp.common.ra.vas.commons.tcp;

import java.io.Serializable;

public abstract interface TcpServer
{
  public abstract void setDataHandler(IServerHandler paramIServerHandler, int paramInt);

  public abstract boolean beginListen(int paramInt);

  public abstract boolean beginListen(int paramInt, String paramString);

  public abstract boolean endListening();

  public abstract int sendToClient(int paramInt, byte[] paramArrayOfByte);

  public abstract int sendToClient(int paramInt, Serializable paramSerializable);

  public abstract void attach(int paramInt, Object paramObject);

  public abstract void disconnect(int paramInt);
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\aceway-nio.jar
 * Qualified Name:     com.aceway.vas.commons.tcp.TcpServer
 * JD-Core Version:    0.6.0
 */