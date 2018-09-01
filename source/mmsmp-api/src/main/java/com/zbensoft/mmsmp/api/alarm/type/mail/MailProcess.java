package com.zbensoft.mmsmp.api.alarm.type.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbensoft.mmsmp.api.alarm.AlarmEmailMangerFacory;
import com.zbensoft.mmsmp.api.alarm.AlarmMessage;
import com.zbensoft.mmsmp.api.alarm.type.AlarmTypeProcess;
import com.zbensoft.mmsmp.api.alarm.util.MailUtil;
import com.zbensoft.mmsmp.db.domain.AlarmEmailManage;

public class MailProcess implements AlarmTypeProcess {

	private static final Logger log = LoggerFactory.getLogger(MailProcess.class);

	@Override
	public void process(AlarmMessage message) {
		try {
			AlarmEmailManage alarmEmailManage = AlarmEmailMangerFacory.getInstance().get(message.getAlarmEmailManageId());
			if (alarmEmailManage != null) {
				MailUtil.sendEmail(MailUtil.ALARM_FILE_NAME, "Alarm-" + message.getName(), alarmEmailManage.getRecvPersonMail(), alarmEmailManage.getCcPersonMail(), alarmEmailManage.getBccPersonMail(),
						message.getContent());
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
