package com.zbensoft.mmsmp.common.ra.common.message;

import java.io.Serializable;

public abstract interface IMessage extends Serializable
{
  public abstract MessageType getMessageType();

  public abstract String encodeString();

  public abstract void decodeString(String paramString)
    throws DecodeMessageException;

  public abstract int getServiceId();
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.IMessage
 * JD-Core Version:    0.6.0
 */