package com.zbensoft.mmsmp.common.ra.common.notifymessageclient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.SendNotificationMessage;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.TcpClient;
import com.zbensoft.mmsmp.common.ra.vas.commons.tcp.impl.TcpClientImpl;

public class NotifyMessageClientTcpImpl {
	private static Logger logger = Logger.getLogger(NotifyMessageClientTcpImpl.class);
	private TcpClient tcpClient;
	private String host;
	private int port;
	private Lock lock = new ReentrantLock();
	private Condition connectedCondition = this.lock.newCondition();
	boolean connected;

	public NotifyMessageClientTcpImpl(String host, int port) {
		this.host = host;
		this.port = port;

		this.tcpClient = new TcpClientImpl();
		this.tcpClient.setDataHandler(new SendMessageHandler(this), 51200);

		if (this.tcpClient.connect(host, port)) {
			this.connected = true;
		} else
			logger.error("connect to server error!");
	}

	public static NotifyMessageClientTcpImpl getClientInstance(String host, int port) {
		return new NotifyMessageClientTcpImpl(host, port);
	}

	public int send(SendNotificationMessage message) {
		if (!this.connected) {
			if (this.lock.tryLock()) {
				try {
					reConnect();
				} catch (Exception ex) {
					ex.printStackTrace();
					return 1;
				} finally {
					this.lock.unlock();
				}
			} else {
				try {
					this.connectedCondition.await(10L, TimeUnit.SECONDS);
				} catch (Exception ex) {
					logger.error(ex.fillInStackTrace());
					ex.printStackTrace();
					if (!this.connected) {
						return 1;
					}
				}
				if (!this.connected) {
					return 1;
				}
			}
		}

		try {
			if (this.tcpClient.send(message) == 0) {
				logger.debug("message sened. message:" + message);
				return 0;
			}
		} catch (Exception e) {
			logger.error("message send is error", e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			this.tcpClient.disconnect();
		}
		this.tcpClient.disconnect();

		return 1;
	}

	private void reConnect() {
		try {
			if (this.tcpClient == null) {
				this.tcpClient = new TcpClientImpl();
				this.tcpClient.setDataHandler(new SendMessageHandler(this), 51200);
			}

			if (this.tcpClient.connect(this.host, this.port)) {
				this.connected = true;
			} else {
				logger.error("connect to server error!");
			}

			this.connectedCondition.signalAll();
		} catch (Exception e) {
			logger.error("reConnect is error!");
		}
	}

	public void stop() {
		this.lock.lock();
		try {
			if (this.tcpClient != null)
				this.tcpClient.disconnect();
		} finally {
			this.lock.unlock();
		}
	}
}

/*
 * Location: E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar Qualified Name:
 * com.aceway.common.notifymessageclient.NotifyMessageClientTcpImpl JD-Core Version: 0.6.0
 */