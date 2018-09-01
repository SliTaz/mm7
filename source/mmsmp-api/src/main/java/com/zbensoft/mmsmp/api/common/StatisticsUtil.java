package com.zbensoft.mmsmp.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * 统计银行充值，银行取消<br>
 * 用户消费
 * 
 * @author xieqiang
 *
 */
public class StatisticsUtil {

	private static final Logger log = LoggerFactory.getLogger(StatisticsUtil.class);

	private static Environment env = SpringBeanUtil.getBean(Environment.class);
	// private static long interval_ms = time_sec * 1000l;
	// private static long time_db_sec = Long.valueOf(env.getProperty("statistics.monitor.interval.db.seconds"));// 2小时
	// private static long interval_db_ms = time_db_sec * 1000l;

	private static long request_count_start_time = 0;



}
