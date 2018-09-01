package com.zbensoft.mmsmp.vac.ra.mina.listen;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.vac.ra.aaa.Header;
import com.zbensoft.mmsmp.vac.ra.mina.util.MessageProcessor;
import com.zbensoft.mmsmp.vac.ra.mina.vac.VACConnectManager;
import com.zbensoft.mmsmp.vac.ra.util.PropertiesHelper;
import com.zbensoft.mmsmp.vac.ra.util.SequenceIDHelper;

public class ServerSenderThread implements Runnable {
	private static final Logger logger = Logger.getLogger(ServerSenderThread.class);
	private CheckMessageQuence cMessageQuence;
	private String threadname;
	private VACConnectManager connectManager = null;

	public ServerSenderThread(CheckMessageQuence instance, String threadname) {
		this.cMessageQuence = instance;
		this.threadname = threadname;
	}

	public String getThreadame() {
		return this.threadname;
	}

	private VACConnectManager getConnectManagerInstance() {
		if (this.connectManager == null) {
			this.connectManager = new VACConnectManager(this.threadname);
		}
		return this.connectManager;
	}

	public void run() {
		getConnectManagerInstance().start();

		Long intervel = PropertiesHelper.getVacAaaSendIntervel();
		while (true) {
			CheckRequest cRequest = null;
			try {
				cRequest = (CheckRequest) this.cMessageQuence.getRequestQuence().take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (cRequest != null) {
					if (cRequest.getMessageid() == null) {
						cRequest.setMessageid(SequenceIDHelper.getIntValue());
					}

					if ((!"6".equals(cRequest.getServiceType())) || (cRequest.getSrc_SequenceNumber() == null)) {
						cRequest.setSrc_SequenceNumber(new Header().generateSequenceNumber("090102"));
					}
					logger.info("===========Src_SequenceNumber:" + cRequest.getSrc_SequenceNumber());

					cRequest.setPutTime(new Date().getTime());
					Map rMap = this.cMessageQuence.getRequestMap();
					rMap.put(cRequest.getMessageid(), cRequest);

					MessageProcessor.dealCheckRequest(cRequest, this.threadname);
				}
			} catch (RuntimeException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(intervel.longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
