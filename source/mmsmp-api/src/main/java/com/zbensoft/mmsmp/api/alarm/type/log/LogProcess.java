package com.zbensoft.mmsmp.api.alarm.type.log;

import com.zbensoft.mmsmp.api.alarm.AlarmMessage;
import com.zbensoft.mmsmp.api.alarm.type.AlarmTypeProcess;
import com.zbensoft.mmsmp.api.log.ALARM_LOG;

public class LogProcess implements AlarmTypeProcess {

	@Override
	public void process(AlarmMessage message) {
		ALARM_LOG.INFO("key=" + message.getKey() + ",name=" + message.getName() + ",level=" + message.getLevel() + ",content=" + message.getContent());
	}

}
