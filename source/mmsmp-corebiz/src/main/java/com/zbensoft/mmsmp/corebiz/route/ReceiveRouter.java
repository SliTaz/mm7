package com.zbensoft.mmsmp.corebiz.route;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.common.ra.common.message.CheckRequest;
import com.zbensoft.mmsmp.common.ra.common.message.CheckResponse;
import com.zbensoft.mmsmp.log.COREBIZ_LOG;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

public class ReceiveRouter implements IMessageRouter {
	static final Logger logger = Logger.getLogger(ReceiveRouter.class);

	ConcurrentHashMap<String, BlockingQueue> policyMap;

	ConcurrentHashMap<String, BlockingQueue> extendMap;

	ConcurrentHashMap<String, String> clientMap;

	ConcurrentHashMap<String, Counter> countMap;
	boolean debug = false;

	public ReceiveRouter() {
		String value = System.getProperty("debug");
		if ((value != null) && (value.equalsIgnoreCase("true"))) {
			this.debug = true;
		}
	}

	public void doRouter(Serializable message) {
		AbstractMessage msg = null;
		System.out.println("message---->" + message);
		try {
			if ((message instanceof AbstractMessage)) {
				msg = (AbstractMessage) message;

				COREBIZ_LOG.INFO("corebiz<- " + getClientName(msg.getClass().getName()) + " one message normal[gmsgid:"
						+ msg.getGlobalMessageid() + ",classType:" + msg.getClass().getSimpleName() + "]");

				long time = System.currentTimeMillis();

				if ((msg.getGlobalMessageid() == null) || ("".equals(msg.getGlobalMessageid().trim()))) {
					msg.setGlobalMessageid(AbstractMessage.generateUUID("ERR"));
				}

				msg.setGlobalMessageTime(time);

				String msgKey = message.getClass().getName();

				if (!this.extendMap.containsKey(msgKey)) {
					((BlockingQueue) this.policyMap.get(msgKey)).put(msg);
					if (this.debug) {
						((Counter) this.countMap.get(msgKey)).oneStep();
					}
				} else {
					doExtendRoute(msg);
				}
			} else if (!(message instanceof String)) {

				COREBIZ_LOG.INFO("corebiz<- client one other type message[classType:" + message.getClass().getName() + "]");
			}
		} catch (Exception ex) {
			COREBIZ_LOG.ERROR("corebiz receive message", ex);
		}
	}

	public void setExtendMap(ConcurrentHashMap<String, BlockingQueue> extendMap) {
		this.extendMap = extendMap;
	}

	public void setPolicyMap(ConcurrentHashMap<String, BlockingQueue> policyMap) {
		this.policyMap = policyMap;

		if (this.debug) {
			this.countMap = new ConcurrentHashMap();

			Iterator<String> it = this.policyMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				this.countMap.put(key, new Counter());
			}

			Thread monitor = new Thread(new Runnable() {
				public void run() {
					for (;;) {
						Iterator<String> it = ReceiveRouter.this.policyMap.keySet().iterator();
						ArrayList<BlockingQueue> qList = new ArrayList();
						ArrayList<ArrayList<String>> sbList = new ArrayList();
						while (it.hasNext()) {
							String key = (String) it.next();
							BlockingQueue q = (BlockingQueue) ReceiveRouter.this.policyMap.get(key);
							if (!qList.contains(q)) {
								qList.add(q);
								ArrayList<String> clist = new ArrayList();
								clist.add(key);
								sbList.add(clist);
							} else {
								int index = qList.indexOf(q);
								((ArrayList) sbList.get(index)).add(key);
							}
						}

						for (int i = 0; i < qList.size(); i++) {
							BlockingQueue tq = (BlockingQueue) qList.get(i);
							COREBIZ_LOG.ERROR(i + " queue size:" + tq.size());
							ArrayList<String> tc = (ArrayList) sbList.get(i);
							for (String c : tc) {
								COREBIZ_LOG.ERROR("    " + c + " SUM:"
										+ ((ReceiveRouter.Counter) ReceiveRouter.this.countMap.get(c)).getNumber());
							}
						}
						try {
							Thread.sleep(1000L);
						} catch (Exception localException) {
						}
					}
				}
			});
			monitor.setDaemon(true);
			monitor.start();
		}
	}

	public String getClientName(String key) {
		String client = (String) this.clientMap.get(key);
		return client == null ? "unknow" : client;
	}

	public void setClientMap(ConcurrentHashMap<String, String> clientMap) {
		this.clientMap = clientMap;
	}

	public void doExtendRoute(AbstractMessage message) throws Exception {
		String msgKey = message.getClass().getName();

		if ((message instanceof CheckResponse)) {
			CheckResponse chkResponse = (CheckResponse) message;
			int chkType = -1;

			try {
				chkType = Integer.parseInt(chkResponse.getCRequest().getServiceType());
			} catch (Exception localException) {
			}

			if ((chkType == 4) || (chkType == 5)) {
				((BlockingQueue) this.extendMap.get(msgKey)).put(message);
			} else {
				((BlockingQueue) this.policyMap.get(msgKey)).put(message);
			}

		} else {
			((BlockingQueue) this.extendMap.get(msgKey)).put(message);
		}
	}

	class Counter {
		long number = 0L;

		Counter() {
		}

		synchronized void oneStep() {
			this.number += 1L;
		}

		synchronized long getNumber() {
			return this.number;
		}
	}
}
