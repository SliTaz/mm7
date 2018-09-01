package com.zbensoft.mmsmp.common.ra.MM7.servlet;

import com.zbensoft.mmsmp.common.ra.MM7.sp.SpDao;
import com.cmcc.mm7.vasp.message.MM7DeliveryReportReq;
import org.apache.log4j.Logger;

public class ReportHandler {
	private static final Logger logger = Logger.getLogger(ReportHandler.class);
	private MM7DeliveryReportReq req;
	private SpDao spdao;

	public ReportHandler() {
		this.spdao = new SpDao();
	}

	public void save_status() {
		String messageID = this.req.getMessageID();
		byte status = this.req.getMMStatus();
		this.spdao.save_msgstatus(messageID, status);
	}

	public MM7DeliveryReportReq getReq() {
		return this.req;
	}

	public void setReq(MM7DeliveryReportReq req) {
		this.req = req;
	}
}
