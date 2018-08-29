package com.zbensoft.mmsmp.common.ra.common.send;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMHttpMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;

public abstract interface SendChannel
{
  public abstract MT_ReportMessage sendSMS(AbstractMessage paramAbstractMessage);

  public abstract MT_ReportMessage sendMMS(AbstractMessage paramAbstractMessage);

  public abstract MT_ReportMessage sendHttpMMS(AbstractMessage paramAbstractMessage);

  public abstract MT_MMHttpMessage doHandleSPMMS(AbstractMessage paramAbstractMessage);

  public abstract MO_ReportMessage doHandleConfirm(AbstractMessage paramAbstractMessage);

  public abstract MT_ReportMessage sendWAP(AbstractMessage paramAbstractMessage);

  public abstract MT_ReportMessage sendMME(AbstractMessage paramAbstractMessage);
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.send.SendChannel
 * JD-Core Version:    0.6.0
 */