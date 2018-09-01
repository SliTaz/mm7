package com.zbensoft.mmsmp.api.alarm.type.db;

import java.util.Calendar;

import com.zbensoft.mmsmp.api.alarm.AlarmMessage;
import com.zbensoft.mmsmp.api.alarm.type.AlarmTypeProcess;
import com.zbensoft.mmsmp.api.common.IDGenerate;
import com.zbensoft.mmsmp.api.common.MessageDef;
import com.zbensoft.mmsmp.api.common.SpringBeanUtil;
import com.zbensoft.mmsmp.api.service.api.AlarmInfoService;
import com.zbensoft.mmsmp.common.util.DateUtil;
import com.zbensoft.mmsmp.db.domain.AlarmInfo;

public class DBProcess implements AlarmTypeProcess {
	private static AlarmInfoService alarmInfoService = (AlarmInfoService) SpringBeanUtil.getBean(AlarmInfoService.class);

	@Override
	public void process(AlarmMessage message) {
		AlarmInfo alarmInfo = new AlarmInfo();
		alarmInfo.setAlarmTime(DateUtil.convertDateToFormatString(Calendar.getInstance().getTime()));
		alarmInfo.setAlarmInfoCode(IDGenerate.generateCommOne(IDGenerate.ALARM_INFO));
		alarmInfo.setAlarmLevelCode(message.getLevel());
		alarmInfo.setContent(message.getContent());
		alarmInfo.setAlarmOrigin(message.getKey());
		alarmInfo.setStatus(MessageDef.ALARM_STATUS.UN_HANDLING);
		alarmInfoService.insert(alarmInfo);
	}

}
