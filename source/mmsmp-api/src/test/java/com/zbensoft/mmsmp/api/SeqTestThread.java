package com.zbensoft.mmsmp.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.mmsmp.api.common.seq.SeqDefinded;
import com.zbensoft.mmsmp.api.common.seq.SequenceUtils;
import com.zbensoft.mmsmp.api.log.ALARM_LOG;

public class SeqTestThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(SeqTestThread.class);

	public SeqTestThread(String name, int times, Date time) {
		super(name);
		this.totalTimes = times;
		this.startTime = time;
	}

	private int totalTimes;
	private Date startTime;

	@Override
	public void run() {
		int count = 0;

		while (true) {
			try {
				// if (new Date().before(startTime)) {
				// Thread.sleep(3000);
				// continue;
				// }
				long s = System.currentTimeMillis();
				// System.out.println("Test-Thread-" + this.getName() + " start");
				String seq = SequenceUtils.getNextValueFormatLen(SeqDefinded.MERCHANT_EMPLOYEE_SEQ);

				ALARM_LOG.WARN(this.getName() + " No." + count + "==" + seq);
				System.out.println(this.getName() + " No." + count + "==" + seq + " use time=" + (System.currentTimeMillis() - s) + "ms");

			} catch (Exception e) {
				log.error("getSeq Exception in :" + count + e.getMessage(), e.getMessage());
			}
			count++;
			if (count >= totalTimes) {
				break;
			}
		}
	}

}
