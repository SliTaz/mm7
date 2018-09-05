package com.zbensoft.mmsmp.vac.ra.log;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.wo.util.PropertiesHelper;

/**
 * 
 * @author 谢强
 * 
 */
public class PROCESS_LOG {

	private static Logger log = Logger.getLogger(PROCESS_LOG.class);

	public static void INFO(String s) {
		if (isDebugEnabled()) {
			if (s != null && !"".equals(s)) {
				log.info(s);
			}
		}
	}

	public static void ERROR(String s, Throwable e) {
		if (isDebugEnabled()) {
			if (s != null && !"".equals(s)) {
				log.error(s, e);
			}
		}
	}

	public static void ERROR(String s) {
		if (isDebugEnabled()) {
			if (s != null && !"".equals(s)) {
				log.error(s);
			}
		}
	}

	public static boolean isDebugEnabled() {
		// 默认
		int logFlag = PropertiesHelper.getInteger("PROCESS_LOG", 0);// ConfigManage.getInstance().getConfigConfig().getConfigInt(ConfigKeyConfig.CHANNEL_LOG);

		if (logFlag == 0) {
			return false;
		} else {
			return true;
		}

	}
}
