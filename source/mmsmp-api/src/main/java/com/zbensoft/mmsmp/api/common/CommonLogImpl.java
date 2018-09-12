package com.zbensoft.mmsmp.api.common;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zbensoft.mmsmp.api.config.spring.securityjwt.JwtUser;
import com.zbensoft.mmsmp.api.service.api.SysLogService;
import com.zbensoft.mmsmp.api.service.api.SysMenuService;
import com.zbensoft.mmsmp.api.service.api.SysUserService;
import com.zbensoft.mmsmp.common.util.DateUtil;
import com.zbensoft.mmsmp.db.domain.SysLog;

public class CommonLogImpl {
	

	
	private static final Logger log = LoggerFactory.getLogger(CommonLogImpl.class);

	// 操作类型
	public final static Integer OPERTYPE_INSERT = new Integer(1);
	public final static Integer OPERTYPE_UPDATE = new Integer(2);
	public final static Integer OPERTYPE_DELETE = new Integer(3);
	public final static Integer OPERTYPE_ACTIVATE = new Integer(4);
	public final static Integer OPERTYPE_INACTIVATE = new Integer(5);
	public final static Integer OPERTYPE_FROST = new Integer(6);
	public final static Integer OPERTYPE_UNFROST = new Integer(7);
	public final static Integer OPERTYPE_RESETPWD = new Integer(8);
	public final static Integer OPERTYPE_RESETPAYPWD = new Integer(9);
	public final static Integer OPERTYPE_IMPORT = new Integer(10);
	public final static Integer OPERTYPE_PUBLISHED = new Integer(11);    //发布
	public final static Integer OPERTYPE_REVOKED = new Integer(12);      //取消发布
	public final static Integer OPERTYPE_IMPLEMENT = new Integer(13);      //执行中
	public final static Integer OPERTYPE_UNIMPLEMENT = new Integer(14);      //未执行
	public final static Integer OPERTYPE_SUCCESS = new Integer(15);      //成功
	public final static Integer OPERTYPE_FAIL = new Integer(16);      //失败
	public final static Integer OPERTYPE_PAUSED = new Integer(17);      //暂停
	public final static Integer OPERTYPE_NORMAL = new Integer(18);      //恢复
	public final static Integer OPERTYPE_RESTORE = new Integer(19);      //恢复
	
	// 操作模块

	public final static Integer APPLICATION_SERVER = new Integer(2);
	public final static Integer CONTENT_INFO = new Integer(3);
	public final static Integer CP_INFO = new Integer(4);
	public final static Integer SP = new Integer(5);
	public final static Integer CUSTOMER_MANAGE = new Integer(7);
	public final static Integer MANAGE_USER = new Integer(9);
	public final static Integer TASK = new Integer(11);
	public final static Integer STATISTICS = new Integer(12);
	public final static Integer ALARM = new Integer(13);
	public final static Integer SYSTEM_MANAGE = new Integer(16);

	private static SysLogService sysLogService = SpringBeanUtil.getBean(SysLogService.class);
	
	private static SysUserService sysUserService = SpringBeanUtil.getBean(SysUserService.class);

	private static SysMenuService SysMenuService = SpringBeanUtil.getBean(SysMenuService.class);

	/**
	 * 
	 * @param OperType
	 *            操作类型
	 * @param operContent
	 *            操作内容
	 */
	public static void insertLog(Integer OperType, Object obj,Integer OperModel) {
		try {
//			SysLog ban = new SysLog();
//			List<SysLog> list = sysLogService.selectPage(ban);

			SysLog sysLog = new SysLog();
			sysLog.setSysLogId(IDGenerate.generateCommOne(IDGenerate.SYS_LOG));
			sysLog.setOperTime(DateUtil.convertDateToFormatString(new Date()));

			sysLog.setOperUser(getPrincipal());
			sysLog.setOperType(OperType);
			sysLog.setOperModel(OperModel);

			String className = obj.getClass().getSimpleName();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(className, obj);
			String operContent = JSON.toJSONString(jsonObject);
			sysLog.setOperContent(operContent);
            
			//菜单关键字
/*			String keyWord = camelToUnderline(className);
			System.out.println(keyWord);
		    SysMenu rootMenu = SysMenuService.findTopMenu(keyWord); 
			  if (rootMenu !=null) { 
			     System.out.println(rootMenu.getMenuName());
			  }*/
			if(sysLog.getOperUser()!=null){

				 sysLogService.insert(sysLog);

			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 获取系统当前用户
	 */
	private static String getPrincipal() {
		try {
			String userName = null;
			String id=null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(principal!=null){
				if (principal instanceof JwtUser) {
					id=((JwtUser) principal).getId();
					if(StringUtils.isNotEmpty(id)&&id.length()>1){
						if(id.startsWith(MessageDef.NOTICE_VIEW_TYPE.GOV_STRING)||id.startsWith(MessageDef.NOTICE_VIEW_TYPE.SYS_STRING)){
							userName = ((JwtUser) principal).getUsername();
						}
					}
				} 
			}
			return userName;
		} catch (Exception e) {
			return "";
		}
	}

	// 将格式BankInfo 转换成Bank Info
	public static final char UNDERLINE = ' ';

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c) && i != 0) {
				sb.append(UNDERLINE);
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getClassLower(String className) {
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}
}
