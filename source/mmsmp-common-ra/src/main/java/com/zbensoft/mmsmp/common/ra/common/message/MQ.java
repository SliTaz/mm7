package com.zbensoft.mmsmp.common.ra.common.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.cx.queue.MessageReceiver;
import com.cx.queue.MessageSender;
import com.cx.queue.MessageSenderPool;
import com.zbensoft.mmsmp.common.ra.common.db.cache.ServCtlsCache;
import com.zbensoft.mmsmp.common.ra.common.db.cache.SysappCache;
import com.zbensoft.mmsmp.common.ra.common.db.entity.ServCtls;
import com.zbensoft.mmsmp.common.ra.common.db.entity.Sysapp;

public class MQ {
	private static final Log logger = LogFactory.getLog(MQ.class);

	private static MQ instance = null;

	private int poolSize = 10;

	private boolean stopped = false;

	private ConcurrentMap<String, MessageSenderPool> senderPoolMap = new ConcurrentHashMap();

	private Map<String, MessageReceiverThread> paylayXReceiveThreadsMap = null;

	private Map<String, Map<String, MessageReceiverThread>> controllerReceiveThreadsMap = new HashMap();

	private ReceiveMessageList receiveList = new ReceiveMessageList(null);

	public static MQ getInstance() {
		if (instance == null) {
			synchronized (MQ.class.getName()) {
				if (instance == null) {
					instance = new MQ();
				}
			}
		}
		return instance;
	}

	public void stopMQ() {
		close();
		this.stopped = true;
	}

	public void startMQ() {
		this.stopped = false;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public void sendMessage(IMessage message) throws SendMessageException {
		sendMessage(message, false);
	}

	public void sendMessage(IMessage message, boolean adFlag) throws SendMessageException {
		if (this.stopped) {
			return;
		}
		MessageSenderPool senderPool = getMessageSenderPool(message, adFlag ? -1 : message.getServiceId());
		if (senderPool == null) {
			throw new SendMessageException("no availabel SenderPool found.", null);
		}
		MessageSender sender = senderPool.getSender();

		if (sender == null)
			throw new SendMessageException("no availabel Sender found.", null);
		try {
			sender.send(message.encodeString());
		} catch (Exception e) {
			throw new SendMessageException("failed to send message.", e);
		} finally {
			senderPool.putSender(sender);
		}
	}

	public IMessage getPaylayXMessage(String paylayXId) throws GetMessageException {
		try {
			if ((!this.stopped) && (this.paylayXReceiveThreadsMap == null)) {
				this.paylayXReceiveThreadsMap = new HashMap();
				List<ServCtls> servCtlsList = ServCtlsCache.getInstance().getServCtlsList();
				String mqAppName = null;
				String bakMqAppName = null;
				String paylayXAppName = null;
				MessageReceiverThread thread = null;

				for (ServCtls servCtls : servCtlsList) {
					mqAppName = servCtls.getMqappname();
					bakMqAppName = servCtls.getBakmqappname();
					paylayXAppName = servCtls.getPaylayxappname();
					if ((paylayXAppName != null) && (paylayXAppName.trim().equals(""))) {
						paylayXAppName = null;
					}
					if (bakMqAppName == null) {
						bakMqAppName = mqAppName;
					}
					if (((paylayXAppName != null) && (paylayXId != null) && (!paylayXAppName.equals(paylayXId))) || (this.paylayXReceiveThreadsMap.containsKey(mqAppName + bakMqAppName)))
						continue;
					thread = new MessageReceiverThread(mqAppName, bakMqAppName, this.receiveList, "MT");
					thread.start();
					this.paylayXReceiveThreadsMap.put(mqAppName + bakMqAppName, thread);
				}

			}

			return this.receiveList.getMessage();
		} catch (Exception e) {
			throw new GetMessageException("failed to receive message.", e);
		}
	}

	public IMessage getControllerMessage(String controllerId) throws GetMessageException {
		if (this.stopped)
			return null;
		try {
			Map controllerReceiveThreadsMap = (Map) this.controllerReceiveThreadsMap.get(controllerId);

			if ((!this.stopped) && (controllerReceiveThreadsMap == null)) {
				controllerReceiveThreadsMap = new HashMap();

				this.controllerReceiveThreadsMap.put(controllerId, controllerReceiveThreadsMap);

				List<ServCtls> servCtlrsList = ServCtlsCache.getInstance().getServCtlsList();
				String mqAppName = null;
				String bakMqAppName = null;
				String ctlrAppName = null;
				MessageReceiverThread thread = null;

				for (ServCtls servCtls : servCtlrsList) {
					mqAppName = servCtls.getMqappname();
					bakMqAppName = servCtls.getBakmqappname();

					if (bakMqAppName == null) {
						bakMqAppName = mqAppName;
					}
					ctlrAppName = servCtls.getCtlappname();

					if (!ctlrAppName.equals(controllerId)) {
						continue;
					}
					if (controllerReceiveThreadsMap.containsKey(mqAppName + bakMqAppName + ctlrAppName))
						continue;
					thread = new MessageReceiverThread(mqAppName, bakMqAppName, this.receiveList, ctlrAppName);
					thread.start();

					controllerReceiveThreadsMap.put(mqAppName + bakMqAppName, thread);
				}

				this.controllerReceiveThreadsMap.put(controllerId, controllerReceiveThreadsMap);
			}

			return this.receiveList.getMessage();
		} catch (Exception e) {
			throw new GetMessageException("failed to receive message.", e);
		}
	}

	private MessageSenderPool getMessageSenderPool(IMessage message, int servId) {
		ServCtls servCtlrs = ServCtlsCache.getInstance().getServCtls(servId);

		logger.info("*********************************************");
		logger.info(servCtlrs.toString());
		logger.info("*********************************************");
		if (servCtlrs == null) {
			logger.warn("no service controlle info found -- servId: " + servId);
			return null;
		}

		Sysapp mqApp = SysappCache.getInstance().getApp(servCtlrs.getMqappname());

		if (mqApp == null) {
			logger.warn("no mqApp info found -- servId: " + servId + " mqAppName: " + servCtlrs.getMqappname());
			return null;
		}

		Sysapp ctlApp = SysappCache.getInstance().getApp(servCtlrs.getCtlappname());

		if ((servId != -1) && (ctlApp == null)) {
			logger.warn("no ctlApp info found -- servId: " + servId + " ctlAppName: " + servCtlrs.getCtlappname());
			return null;
		}

		Sysapp backMqApp = SysappCache.getInstance().getApp(servCtlrs.getBakmqappname());

		if (backMqApp == null) {
			backMqApp = mqApp;
		}
		String queueName = servId == -1 ? "MT" : ctlApp.getStrappname();

		if (((message instanceof MT_MMMessage)) || ((message instanceof MT_SMMessage)) || ((message instanceof MT_WapPushMessage))) {
			queueName = "MT";
		}
		String senderPoolName = queueName + mqApp.getStrappname() + backMqApp.getStrappname();

		if (this.senderPoolMap.containsKey(senderPoolName)) {
			return (MessageSenderPool) this.senderPoolMap.get(senderPoolName);
		}
		String queueURL = mqApp.getStrappagentipaddress() + ":1115;" + backMqApp.getStrappagentipaddress() + ":1115";

		if (logger.isDebugEnabled()) {
			logger.debug("queueURL:" + queueURL);
		}
		if ((queueURL == null) || (queueName == null)) {
			logger.warn("queueName or queueURL is null");
			return null;
		}
		try {
			MessageSenderPool senderPool = new MessageSenderPool(this.poolSize, queueURL, queueName);

			this.senderPoolMap.put(senderPoolName, senderPool);

			return senderPool;
		} catch (Exception e) {
		}
		return null;
	}

	public void refresh() {
		close();
	}

	public void close() {
		Map temp = new HashMap();
		temp.putAll(this.senderPoolMap);

		this.senderPoolMap = new ConcurrentHashMap();
		String key = null;
		MessageSenderPool senderPool = null;

		for (Iterator iter = temp.keySet().iterator(); iter.hasNext();) {
			key = (String) iter.next();

			senderPool = (MessageSenderPool) temp.get(key);
			senderPool.clear();
		}

		if (this.paylayXReceiveThreadsMap != null) {
			Map tempMap = this.paylayXReceiveThreadsMap;

			for (Iterator iterTemp = tempMap.values().iterator(); iterTemp.hasNext();) {
				((MessageReceiverThread) iterTemp.next()).myStop();
			}

			this.paylayXReceiveThreadsMap = null;
		}

		for (Iterator iterTemp2 = this.controllerReceiveThreadsMap.values().iterator(); iterTemp2.hasNext();) {
			Map controllerReceiveThreadsMap = (Map) iterTemp2.next();

			if (controllerReceiveThreadsMap != null) {
				Map tempMap = controllerReceiveThreadsMap;

				for (Iterator iterTemp = tempMap.values().iterator(); iterTemp.hasNext();) {
					((MessageReceiverThread) iterTemp.next()).myStop();
				}
				controllerReceiveThreadsMap = null;
			}
		}

		this.controllerReceiveThreadsMap = new HashMap();
	}

	private class MessageReceiverThread extends Thread {
		private final Logger log = Logger.getLogger(MessageReceiverThread.class);

		private String mqAppName = null;

		private String bakMqAppName = null;

		private MQ.ReceiveMessageList receiveList = null;

		private String queueName = null;

		private MessageReceiver receiver = null;

		private boolean isWorking = true;

		public void myStop() {
			this.isWorking = false;
			this.receiver.close();
		}

		public MessageReceiverThread(String mqAppName, String bakMqAppName, MQ.ReceiveMessageList receiveList, String queueName) {
			this.mqAppName = mqAppName;
			this.bakMqAppName = bakMqAppName;
			this.receiveList = receiveList;
			this.queueName = queueName;
		}

		private void init() {
			if (this.log.isDebugEnabled()) {
				this.log.debug("MQAppName is > " + this.mqAppName + " <BackMQAppName is> " + this.bakMqAppName);
			}
			Sysapp mqApp = SysappCache.getInstance().getApp(this.mqAppName);
			Sysapp backMqApp = SysappCache.getInstance().getApp(this.bakMqAppName);
			String queueURL = mqApp.getStrappagentipaddress() + ":1115;" + backMqApp.getStrappagentipaddress() + ":1115";
			this.receiver = new MessageReceiver(queueURL, this.queueName);
		}

		public void run() {
			init();
			String message = null;

			while (this.isWorking) {
				if (this.receiveList.size() > 30) {
					try {
						Thread.sleep(100L);
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				} else
					try {
						message = (String) this.receiver.receive();
						if (message != null)
							this.receiveList.addMessage(MessageDecoder.getMessage(message));
					} catch (Exception e) {
						MQ.logger.error("", e);
					}
			}
			try {
				this.receiver.close();
			} catch (Throwable e) {
				MQ.logger.error("", e);
			}
		}
	}

	private class ReceiveMessageList {
		private List<IMessage> messageList = new LinkedList();

		private ReceiveMessageList() {
		}

		public int size() {
			return this.messageList.size();
		}

		public void addMessage(IMessage message) {
			synchronized (this.messageList) {
				this.messageList.add(message);
			}
		}

		public IMessage getMessage() {
			synchronized (this.messageList) {
				if ((this.messageList == null) || (this.messageList.size() == 0)) {
					return null;
				}
				return (IMessage) this.messageList.remove(0);
			}
		}
	}
}
