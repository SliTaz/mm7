package com.zbensoft.mmsmp.common.ra.common.pool;

public abstract interface ObjectsPool
{
  public abstract void saveObj(Object paramObject1, Object paramObject2);

  public abstract Object getObj(Object paramObject);

  public abstract boolean exists(Object paramObject);

  public abstract void setPoolSize(int paramInt);
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.pool.ObjectsPool
 * JD-Core Version:    0.6.0
 */