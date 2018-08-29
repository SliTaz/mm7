package com.zbensoft.mmsmp.common.ra.common.message;

import com.cmcc.mm7.vasp.common.MMContent;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MO_MMDeliverMessage2
  implements Serializable
{
  private List bcclist;
  private List cclist;
  private MMContent Content;
  private String LinkedID;
  private String MM7Version;
  private String MMSRelayServerID;
  private String ReplyChargingID;
  private String Sender;
  private String Subject;
  private Date TimeStamp;
  private List To;
  private String TransactionID;
  private String contentType;
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.message.MO_MMDeliverMessage2
 * JD-Core Version:    0.6.0
 */