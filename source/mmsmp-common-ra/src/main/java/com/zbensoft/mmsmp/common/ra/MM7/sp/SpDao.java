package com.zbensoft.mmsmp.common.ra.MM7.sp;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SpDao extends JdbcDaoSupport {
	private static final Logger logger = Logger.getLogger(SpDao.class);

	public void save_submit(SubmitReq submit) {
		logger.info(submit);
	}

	public void update_submit(SubmitResp resp) {
		logger.info(resp);
	}

	public void save_msgstatus(String messageID, int status) {
		logger.info(messageID + ":" + status);
	}
}
