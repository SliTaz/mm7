package com.zbensoft.mmsmp.sp.ra.spagent.mina;

import com.zbensoft.mmsmp.common.ra.common.message.AbstractMessage;
import com.zbensoft.mmsmp.sp.ra.spagent.MessageQueue;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class SPMessageQuene {
	private MessageQueue<AbstractMessage> moQuence;
	private MessageQueue<AbstractMessage> mtQuence;
	private MessageQueue<AbstractMessage> reportQuence;
	private static SPMessageQuene messageQuence = null;
	private static Properties p = null;

	static {
		p = new Properties();
		try {
			System.out.println(SPMessageQuene.class.getClassLoader().getResource("queue.properties"));//获取根路径下的资源文件
			p.load(SPMessageQuene.class.getClassLoader().getResourceAsStream("queue.properties"));//获取根路径下的资源文件
			
			
			System.out.println("baseDir:"+p.getProperty("baseDir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private SPMessageQuene() {
		this.moQuence = new MessageQueue(p.getProperty("baseDir", "/home/mmsmp/queue"), "moQuence");
		this.mtQuence = new MessageQueue(p.getProperty("baseDir", "/home/mmsmp/queue"), "mtQuence");
		this.reportQuence = new MessageQueue(p.getProperty("baseDir", "/home/mmsmp/queue"), "reportQuence");
	}

	public MessageQueue<AbstractMessage> getMoQuence() {
		return this.moQuence;
	}

	public MessageQueue<AbstractMessage> getMtQuence() {
		return this.mtQuence;
	}

	public MessageQueue<AbstractMessage> getReportQuence() {
		return this.reportQuence;
	}

	public static SPMessageQuene getInstance() {
		if (messageQuence == null) {
			messageQuence = new SPMessageQuene();
		}
		return messageQuence;
	}
}
