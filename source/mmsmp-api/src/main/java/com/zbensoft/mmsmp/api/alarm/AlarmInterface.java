package com.zbensoft.mmsmp.api.alarm;
import com.zbensoft.mmsmp.db.domain.AlarmManger;
public interface AlarmInterface {
	public void initSetup();
	public boolean haveAlarm();
	public AlarmMessage getAlarmMessage();
	/** 设置告警参数 **/
	public void setParam(Object ob);
	public void init(AlarmManger alarmManager);
	public Object getParam();
}
