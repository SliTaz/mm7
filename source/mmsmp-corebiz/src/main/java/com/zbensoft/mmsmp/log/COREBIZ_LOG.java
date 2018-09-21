package com.zbensoft.mmsmp.log;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;

import com.zbensoft.mmsmp.config.SpringBeanUtil;

public class COREBIZ_LOG {
    private static Logger log = Logger.getLogger(COREBIZ_LOG.class);
    
    private Environment env = SpringBeanUtil.getBean(Environment.class);
	private String COREBIZ_LOG_DEBUG= env.getProperty("COREBIZ.LOG.DEBUG");
	
	private static COREBIZ_LOG logConfig = new COREBIZ_LOG();

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
    public static void WARN(String s) {
        if (isDebugEnabled()) {
            if (s != null && !"".equals(s)) {
                log.warn(s);
            }
        }
    }

    public static boolean isDebugEnabled() {
        // 默认
    	int logFlag = 0;
    	if(logConfig.COREBIZ_LOG_DEBUG!=null&&!logConfig.COREBIZ_LOG_DEBUG.equals(0)){
    		logFlag=1;
    	}

        if (logFlag == 0) {
            return false;
        } else {
            return true;
        }

    }
}
