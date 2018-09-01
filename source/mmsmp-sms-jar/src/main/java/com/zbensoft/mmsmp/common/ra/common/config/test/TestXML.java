/*    */ package com.zbensoft.mmsmp.common.ra.common.config.test;

import com.zbensoft.mmsmp.common.ra.common.config.configbean.MmsEditor;
import com.zbensoft.mmsmp.common.ra.common.config.util.ConfigUtil;

/*    */

/*    */
/*    */ public class TestXML
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 18 */     MmsEditor config = ConfigUtil.getInstance().getMmsEditor();
/* 19 */     System.out.println(config.getProjectUrl());
/*    */   }
/*    */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.config.test.TestXML
 * JD-Core Version:    0.6.0
 */