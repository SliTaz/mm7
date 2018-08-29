package com.zbensoft.mmsmp.common.ra.common.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

public class DateUtil {
	private static final Logger log = Logger.getLogger(DateUtil.class);

	public boolean isExpired(String endDate) {
		long currentDate = 0L;
		int month = Calendar.getInstance().get(2) + 1;
		int day = Calendar.getInstance().get(5);

		if (month < 10) {
			if (day < 10)
				currentDate = Long.parseLong(Calendar.getInstance().get(1) + "0" + month + "0" + day);
			else {
				currentDate = Long.parseLong(Calendar.getInstance().get(1) + "0" + month + day);
			}

		} else if (day < 10)
			currentDate = Long.parseLong(Calendar.getInstance().get(1) + month + "0" + day);
		else {
			currentDate = Long.parseLong(Calendar.getInstance().get(1) + month + "" + day);
		}

		int index = 0;
		long end_date = 0L;

		StringBuffer strBuff = new StringBuffer();
		char temp = ' ';

		for (index = 0; index < endDate.length(); index++) {
			temp = endDate.charAt(index);

			if ((temp >= '0') && (temp <= '9'))
				strBuff.append(temp);
		}
		end_date = Long.parseLong(strBuff.toString());

		return currentDate > end_date;
	}

	public boolean isValidDate(String startDate, String endDate, boolean loopSend) {
		long currentDate = 0L;
		int month = Calendar.getInstance().get(2) + 1;
		int day = Calendar.getInstance().get(5);

		if (month < 10) {
			if (day < 10)
				currentDate = Long.parseLong(Calendar.getInstance().get(1) + "0" + month + "0" + day);
			else {
				currentDate = Long.parseLong(Calendar.getInstance().get(1) + "0" + month + day);
			}

		} else if (day < 10)
			currentDate = Long.parseLong(Calendar.getInstance().get(1) + month + "0" + day);
		else {
			currentDate = Long.parseLong(Calendar.getInstance().get(1) + month + "" + day);
		}

		int index = 0;
		long start_date = 0L;
		long end_date = 0L;

		StringBuffer strBuff = new StringBuffer();
		char temp = ' ';

		for (index = 0; index < startDate.length(); index++) {
			temp = startDate.charAt(index);

			if ((temp >= '0') && (temp <= '9'))
				strBuff.append(temp);
		}
		start_date = Long.parseLong(strBuff.toString());

		if (loopSend) {
			strBuff.delete(0, index);

			for (index = 0; index < endDate.length(); index++) {
				temp = endDate.charAt(index);

				if ((temp >= '0') && (temp <= '9'))
					strBuff.append(temp);
			}
			end_date = Long.parseLong(strBuff.toString());

			return (currentDate >= start_date) && (end_date >= currentDate);
		}

		return start_date == currentDate;
	}

	public boolean isValidTime(String startTime, String endTime) {
		int currentTime = 0;
		Calendar cal = Calendar.getInstance();
		int minute = cal.get(12);
		int second = cal.get(13);

		if (minute < 10) {
			if (second < 10)
				currentTime = Integer.parseInt(cal.get(11) + "0" + minute + "0" + second);
			else {
				currentTime = Integer.parseInt(cal.get(11) + "0" + minute + second);
			}

		} else if (second < 10)
			currentTime = Integer.parseInt(cal.get(11) + minute + "0" + second);
		else {
			currentTime = Integer.parseInt(cal.get(11) + minute + "" + second);
		}

		StringBuffer strBuff = new StringBuffer();
		int start_time = 0;
		int end_time = 0;
		char temp = ' ';
		int index = 0;

		for (index = 0; index < startTime.length(); index++) {
			temp = startTime.charAt(index);

			if ((temp >= '0') && (temp <= '9'))
				strBuff.append(temp);
		}
		start_time = Integer.parseInt(strBuff.toString());
		strBuff.delete(0, index);

		if (log.isDebugEnabled()) {
			log.debug("start_time is: " + start_time);
		}
		for (index = 0; index < endTime.length(); index++) {
			temp = endTime.charAt(index);

			if ((temp >= '0') && (temp <= '9'))
				strBuff.append(temp);
		}
		end_time = Integer.parseInt(strBuff.toString());

		if (log.isDebugEnabled()) {
			log.debug("end_time is: " + end_time);
		}
		return (currentTime >= start_time) && (currentTime <= end_time);
	}

	public long getCurrentTimeMillis(String timeStr) {
		Calendar cal = Calendar.getInstance();
		int[] time = new int[6];
		int i = 0;

		for (StringTokenizer st = new StringTokenizer(timeStr, " -:"); st.hasMoreElements(); i++) {
			time[i] = Integer.parseInt(st.nextToken());
		}
		cal.set(time[0], time[1] - 1, time[2], time[3], time[4], time[5]);

		return cal.getTime().getTime();
	}

	public Integer getWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int n = cal.get(7) - 1;
		if (n <= 0)
			n = 7;
		return Integer.valueOf(n);
	}

	public Integer getDayOfMonthNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int n = cal.get(5);
		if (n <= 0)
			n = 1;
		return Integer.valueOf(n);
	}

	public static String getTime(Date date) {
		DateFormat format = new SimpleDateFormat("HHmm");
		return format.format(date);
	}

	public static void main(String[] argc) {
		DateUtil tester = new DateUtil();

		System.out.print(tester.getWeekNum(new Date()));
	}
}
