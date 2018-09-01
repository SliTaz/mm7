package com.zbensoft.mmsmp.common.ra.vas.sjb.receive;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.ConfirmPriceMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMDeliverMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_MMMessage;
import com.zbensoft.mmsmp.common.ra.common.message.MT_ReportMessage;
import com.zbensoft.mmsmp.common.ra.vas.sjb.common.Router;
import com.zbensoft.mmsmp.common.ra.vas.sjb.common.Task;
import org.apache.log4j.Logger;

public class ReceiveRouter implements Router {
	private static final Logger logger = Logger.getLogger(ReceiveRouter.class);
	private Task demandTask;
	private Task logTask;

	public void doRouter(AbstractMessage message) {
		if (message == null) {
			return;
		}
		if ((message instanceof MO_MMMessage)) {
			this.demandTask.doTask(message);
			return;
		}
		if ((message instanceof MO_SMMessage)) {
			this.demandTask.doTask(message);
			return;
		}
		if ((message instanceof MO_ReportMessage)) {
			logger.info("do task MO_ReportMessage");
			this.logTask.doTask(message);
			this.demandTask.doTask(message);
			return;
		}
		if ((message instanceof MT_MMMessage)) {
			this.logTask.doTask(message);
			return;
		}
		if ((message instanceof MT_ReportMessage)) {
			logger.info("received MT_ReportMessage......");
			this.logTask.doTask(message);
		} else if ((message instanceof MO_MMDeliverMessage)) {
			logger.info("received MO_MMDeliverMessage......");
			this.demandTask.doTask(message);
		} else if ((message instanceof ConfirmPriceMessage)) {
			this.demandTask.doTask(message);
		}
	}

	public Task getDemandTask() {
		return this.demandTask;
	}

	public void setDemandTask(Task demandTask) {
		this.demandTask = demandTask;
	}

	public Task getLogTask() {
		return this.logTask;
	}

	public void setLogTask(Task logTask) {
		this.logTask = logTask;
	}
}
