package com.zbensoft.mmsmp.common.ra.common.mina;

public abstract interface TcpServer
{
  public abstract int startListener(String paramString, int paramInt);

  public abstract int startListener(int paramInt);

  public abstract int stopListener();
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.mina.TcpServer
 * JD-Core Version:    0.6.0
 */