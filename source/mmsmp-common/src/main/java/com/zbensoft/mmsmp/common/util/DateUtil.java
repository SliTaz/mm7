package com.zbensoft.mmsmp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期操作类
 * 
 * @author 谢强
 */
public final class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	/** yyyy-MM-dd. */
	public static final String DATE_FORMAT_ONE = "yyyy-MM-dd";

	/** yyyy/MM/dd. */
	public static final String DATE_FORMAT_TWO = "yyyy/MM/dd";

	/** yyyyMMdd. */
	public static final String DATE_FORMAT_THREE = "yyyyMMdd";

	/** yyyyMM. */
	public static final String DATE_FORMAT_FOUR = "yyyyMM";

	/** yyyy-MM-dd HH:mm:ss. */
	public static final String DATE_FORMAT_FIVE = "yyyy-MM-dd HH:mm:ss";

	/** yyyy-MM-dd HH. */
	public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";

	/** yyyy. */
	public static final String DATE_FORMAT_SIX = "yyyy";

	/** MM. */
	public static final String DATE_FORMAT_SEVEN = "MM";

	/** yyyy/MM/dd HH:mm:ss. */
	public static final String DATE_FORMAT_EIGHT = "yyyy/MM/dd HH:mm:ss";

	/** HH:mm:ss. */
	public static final String DATE_FORMAT_NINE = "HH:mm:ss";

	/** yy/MM/dd. */
	public static final String DATE_FORMAT_TEN = "yy/MM/dd";

	/** yyyy/M/d. */
	public static final String DATE_FORMAT_ELEVEN = "yyyy/M/d";

	/** yyyy/MM. */
	public static final String DATE_FORMAT_TWELEVE = "yyyy/MM";

	/** yyyyMMddHHmmssSSS. */
	public static final String DATE_FORMAT_THIRTEEN = "yyyyMMddHHmmssSSS";

	/** yyMMdd. */
	public static final String DATE_FORMAT_FOURTEEN = "yyMMdd";

	/** yyyy.MM.dd. */
	public static final String DATE_FORMAT_SIXTEEN = "yyyy.MM.dd";

	/** yyyyMMddHHmmss. */
	public static final String DATE_FORMAT_SEVENTEEN = "yyyyMMddHHmmss";

	/** yyyy年MM月dd日 */
	public static final String DATE_FORMAT_EIGHTEEN = "yyyy年MM月dd日";

	/** HH:mm. */
	public static final String DATE_FORMAT_NINETEEN = "HH:mm";

	/** yyyy/MM/dd HH24:mm:ss. */
	public static final String DATE_FORMAT_TWENTY = "yyyy/MM/dd HH24:mm:ss";

	/** INT_4. */
	private static final int INT_4 = 4;

	/** INT_6. */
	private static final int INT_6 = 6;

	/** INT_8. */
	private static final int INT_8 = 8;

	/** . */
	public static final String YEAR_FORMAT = "[12][0-9]{3}";

	/** 年. */
	public static final int YEAR = 1;

	/** 月. */
	public static final int MONTH = 2;

	/** 日. */
	public static final int DAY = 3;
	
	/** yyyy/MM/dd HH:mm:ss. */
	public static final String DATE_FORMAT_TWENTY_ONE = "yyyy/MM/dd HH:mm:ss.SSS";
	
	/** dd/MM/yyyy. */
	public static final String DATE_FORMAT_TWENTY_TWO = "dd/MM/yyyy";
	/** yyyy-MM-dd. */
	public static final String DATE_FORMAT_TWENTY_THREE = "yyyy-MM";
	/** dd/MM/yyyy HH:mm:ss*/
	public static final String DATE_FORMAT_TWENTY_FOUR = "dd/MM/yyyy HH:mm:ss";

	/**
	 * <PRE>
	 * 比较两个日期的前后
	 * </PRE>
	 * 
	 * @param day1
	 *            String
	 * @param day2
	 *            String
	 * @return boolean 如果day1先于 day2 返回true,否则返回false.
	 */
	public static boolean isEarlierDate(String day1, String day2) {

		boolean isEarly = true;

		if (day1 == null || day1.trim().equals("") || day2 == null || day2.trim().equals("")) {
			isEarly = false;
		} else {

			day1 = day1.replaceAll("/", "");
			day2 = day2.replaceAll("/", "");
			day1 = day1.replaceAll("-", "");
			day2 = day2.replaceAll("-", "");

			int year1 = Integer.parseInt(day1.substring(0, INT_4));
			int month1 = Integer.parseInt(day1.substring(INT_4, INT_6)) - 1;
			int day11 = Integer.parseInt(day1.substring(INT_6, INT_8));
			Calendar cal1 = Calendar.getInstance();
			cal1.set(year1, month1, day11);

			int year2 = Integer.parseInt(day2.substring(0, INT_4));
			int month2 = Integer.parseInt(day2.substring(INT_4, INT_6)) - 1;
			int day22 = Integer.parseInt(day2.substring(INT_6, INT_8));
			Calendar cal2 = Calendar.getInstance();
			cal2.set(year2, month2, day22);

			if (cal1.getTime().compareTo(cal2.getTime()) > 0) {
				isEarly = false;
			}
		}
		return isEarly;

	}

	/**
	 * 将字符串yyyy-MM-dd 转换成Date类型对象
	 * 
	 * @param 日期字符串yyyy-MM-dd
	 * @return 转换后的Date对象
	 */
	public static Date convertStringToDate(String strDate) {
		Date date = null;
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_TWO);
		try {
			date = sf.parse(strDate);
		} catch (ParseException e) {
			log.error("", e);
		}
		return date;
	}

	/**
	 * <PRE>
	 * DateFormat转化.
	 * </PRE>
	 * 
	 * @param value
	 *            String
	 * @param fromFormatStr
	 *            String
	 * @param toFormatStr
	 *            String
	 * 
	 * 
	 * @return 转化后的Date
	 * 
	 * @throws ParseException
	 *             ParseException
	 */
	public static String parseDateFormat(String value, String fromFormatStr, String toFormatStr) throws ParseException {

		if (value == null || value.equals("")) {
			return null;
		}

		DateFormat formFormat = new SimpleDateFormat(fromFormatStr);
		formFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat(toFormatStr);
		toFormat.setLenient(false);

		return toFormat.format(formFormat.parse(value));
	}

	/**
	 * DateFormat转化.
	 * 
	 * @param value
	 *            String
	 * @param fromFormatStr
	 *            String
	 * @param toFormatStr
	 *            String
	 * 
	 * @return FormatDate Date
	 * 
	 * @throws ParseException
	 *             ParseException
	 */
	public static Date getFormatDate(String value, String fromFormatStr, String toFormatStr) throws ParseException {

		if (value == null || value.equals("")) {
			return null;
		}
		if (fromFormatStr == null || fromFormatStr.equals("")) {
			return null;
		}
		if (toFormatStr == null || toFormatStr.equals("")) {
			return null;
		}

		DateFormat formFormat = new SimpleDateFormat(fromFormatStr);
		formFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat(toFormatStr);
		toFormat.setLenient(false);

		return toFormat.parse(toFormat.format(formFormat.parse(value)));
	}

	/**
	 * <PRE>
	 * 比较两个日期
	 * </PRE>
	 * 
	 * @param day1
	 *            String
	 * @param day2
	 *            String
	 * @return int day1>day2返回1，day1<day2返回-1，day1=day2返回0
	 */
	public static int compareDate(String day1, String day2) {

		int isEarly = -1;

		if (day1 == null || day1.trim().equals("") || day2 == null || day2.trim().equals("")) {
			isEarly = -1;
		} else {

			day1 = day1.replaceAll("/", "");
			day2 = day2.replaceAll("/", "");

			int year1 = Integer.parseInt(day1.substring(0, INT_4));
			int month1 = Integer.parseInt(day1.substring(INT_4, INT_6)) - 1;
			int day = Integer.parseInt(day1.substring(INT_6, INT_8));
			Calendar cal1 = Calendar.getInstance();
			cal1.set(year1, month1, day);

			int year2 = Integer.parseInt(day2.substring(0, INT_4));
			int month2 = Integer.parseInt(day2.substring(INT_4, INT_6)) - 1;
			int day22 = Integer.parseInt(day2.substring(INT_6, INT_8));
			Calendar cal2 = Calendar.getInstance();
			cal2.set(year2, month2, day22);

			if (cal1.getTime().compareTo(cal2.getTime()) > 0) {
				isEarly = 1;
			} else if (cal1.getTime().compareTo(cal2.getTime()) == 0) {
				isEarly = 0;
			}
		}
		return isEarly;
	}

	/**
	 * <PRE>
	 * 比较两个日期
	 * </PRE>
	 * 
	 * @param day1
	 *            String
	 * @param day2
	 *            String
	 * @param strFormat
	 *            String
	 * @return int day1>day2返回1，day1<day2返回-1，day1=day2返回0
	 */
	public static int compareDate(String day1, String day2, String strFormat) throws ParseException {
		Date date1 = getFormatDate(day1, strFormat, DATE_FORMAT_THREE);
		Date date2 = getFormatDate(day2, strFormat, DATE_FORMAT_THREE);
		return date1.compareTo(date2);
	}

	/**
	 * 日期追加的计算
	 * 
	 * @param strDate
	 *            原日期(YYYYMMDD)
	 * @param nAddNum
	 *            追加的年月日的大小
	 * @param nType
	 *            追加的类型
	 * 
	 * @return 追加后的新日期(YYYYMMDD)
	 */
	public static String addDate(String strDate, int nAddNum, int nType) {
		int nYear = Integer.parseInt(strDate.substring(0, 4));
		int nMonth = Integer.parseInt(strDate.substring(4, 6)) - 1;
		int nDay = Integer.parseInt(strDate.substring(6));

		GregorianCalendar objCal = new GregorianCalendar();
		objCal.set(nYear, nMonth, nDay);

		switch (nType) {
		case 1: {
			objCal.add(GregorianCalendar.YEAR, nAddNum);
			break;
		}
		case 2: {
			objCal.add(GregorianCalendar.MONTH, nAddNum);
			break;
		}
		case 3: {
			objCal.add(GregorianCalendar.DATE, nAddNum);
			break;
		}
		default: {
			break;
		}
		}

		return convertDateToString(objCal.getTime(), DATE_FORMAT_THREE);
	}

	/**
	 * 日期追加的计算
	 * 
	 * @param date
	 *            原日期
	 * @param nAddNum
	 *            追加的年月日的大小
	 * @param nType
	 *            追加的类型 1: year 2:month 3:day 4 hour
	 * 
	 * @return 追加后的新日期
	 */
	public static Date addDate(Date date, int nAddNum, int nType) {

		GregorianCalendar objCal = new GregorianCalendar();
		objCal.setTime(date);

		switch (nType) {
		case 1: {
			objCal.add(GregorianCalendar.YEAR, nAddNum);
			break;
		}
		case 2: {
			objCal.add(GregorianCalendar.MONTH, nAddNum);
			break;
		}
		case 3: {
			objCal.add(GregorianCalendar.DATE, nAddNum);

			break;
		}
			case 4: {
				objCal.add(GregorianCalendar.HOUR_OF_DAY, nAddNum);
				break;
			}
		}

		return objCal.getTime();
	}

	/**
	 * 月天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 月天数
	 */
	public static int solarDays(int year, int month) {

		// 返回公历 y年某月的天数
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else if (month == 2) {
			GregorianCalendar objCal = new GregorianCalendar();
			if (objCal.isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		} else {
			return 31;
		}

	}

	/**
	 * 将提供格式的字符串 转换成Date类型对象
	 * 
	 * @param 日期字符串
	 * @param 字符串格式。eg：yyyy-MM-dd
	 *            HH:mm:ss
	 * @return 转换后的Date对象
	 */
	public static Date convertStringToDate(String strDate, String format) {
		Date date = null;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			date = sf.parse(strDate);
		} catch (ParseException e) {
			log.error("", e);
		}
		return date;
	}

	/**
	 * 将Date类型对象 转换成String类型对象(yyyy-MM-dd)
	 * 
	 * @param Date类型对象
	 * @return 转换后的String类型对象(yyyy-MM-dd)
	 */
	public static String convertDateToString(Date date) {
		String str = "";
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_ONE);
		str = df.format(date);
		return str;
	}

	/**
	 * 将Date类型对象 转换成String类型对象(任意格式)
	 * 
	 * @param Date类型对象
	 * @param 字符串格式。eg：yyyy-MM-dd
	 *            HH:mm:ss
	 * @return 转换后的String类型对象
	 */
	public static String convertDateToString(Date date, String format) {
		if(date == null) 
			return "";
		String str = "";
		SimpleDateFormat df = new SimpleDateFormat(format);
		str = df.format(date);
		return str;
	}

	/**
	 * 将Date类型对象 转换成String类型对象(yyyy-MM-dd HH:mm:ss )
	 * 
	 * @param Date类型对象
	 * @return 转换后的String类型对象(yyyy-MM-dd HH:mm:ss)
	 */
	public static String convertDateToFormatString(Date date) {
		String str = "";
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_FIVE);
		str = df.format(date);
		return str;
	}
	
	/**
	 * 获取某一天的开始或结束时间
	 * @param beforeAfter 正数：几天后，负数：几天前，0：今天
	 * @param StartEnd true：开始，false：结束
	 * @param formate 需要输出的日期格式
	 * @return 格式化日期
	 */
	public static String getDayStartEndTime(int beforeAfter,boolean StartEnd,String format) {
		
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar now =Calendar.getInstance();  
		now.setTime(new Date());  
		now.set(Calendar.DATE,now.get(Calendar.DATE)+beforeAfter);  
		if(StartEnd){
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
		}else{
			now.set(Calendar.HOUR_OF_DAY, 23);
			now.set(Calendar.MINUTE, 59);
			now.set(Calendar.SECOND, 59);
		}
		Date resultDate=now.getTime();
		
		return df.format(resultDate);
		
	}
	/**
	 * 通过某一天的日期, 获取某一天的开始或结束时间
	 * @param dateStr
	 * @param srcFormat
	 * @param startEnd
	 * @param destFormat
	 * @return
	 */
	public static String getDayStrStartEndTime(String dateStr,String srcFormat,boolean startEnd, String destFormat) {
		Date sourDate=convertStringToDate(dateStr,srcFormat);
		SimpleDateFormat df = new SimpleDateFormat(destFormat);
		Calendar day =Calendar.getInstance();  
		day.setTime(sourDate);
		if(startEnd){
			day.set(Calendar.HOUR_OF_DAY, 0);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.SECOND, 0);
		}else{
			day.set(Calendar.HOUR_OF_DAY, 23);
			day.set(Calendar.MINUTE, 59);
			day.set(Calendar.SECOND, 59);
		}
		Date resultDate=day.getTime();
		return df.format(resultDate);
		
	}
	
	/**
	 * 通过某一天的日期, 获取前一天10点到今天10点的数据
	 * @param dateStr
	 * @param srcFormat
	 * @param startEnd
	 * @param destFormat
	 * @return
	 */
	public static String getDayStrTenTime(String dateStr,String srcFormat,boolean startEnd,int hourOfDay, String destFormat) {
		Date sourDate=convertStringToDate(dateStr,srcFormat);
		SimpleDateFormat df = new SimpleDateFormat(destFormat);
		Calendar day =Calendar.getInstance();  
		day.setTime(sourDate);
		 
		if(startEnd){
			day.set(Calendar.DATE,day.get(Calendar.DATE)-1); 
			day.set(Calendar.HOUR_OF_DAY, hourOfDay);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.SECOND, 0);
		}else{
			day.set(Calendar.HOUR_OF_DAY, (hourOfDay-1));
			day.set(Calendar.MINUTE, 59);
			day.set(Calendar.SECOND, 59);
		}
		Date resultDate=day.getTime();
		return df.format(resultDate);
		
	}
	
	
	/**
	 * 获取某一天的开始或结束时间
	 * @param beforeAfter 正数：几天后，负数：几天前，0：今天
	 * @param StartEnd true：开始，false：结束
	 * @param formate 需要输出的日期格式
	 * @return 日期 Date
	 */
	public static Date getDayStartEndDate(int beforeAfter,boolean StartEnd,String format) {

		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar now =Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE,now.get(Calendar.DATE)+beforeAfter);
		if(StartEnd){
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
		}else{
			now.set(Calendar.HOUR_OF_DAY, 23);
			now.set(Calendar.MINUTE, 59);
			now.set(Calendar.SECOND, 59);
		}
		Date resultDate=now.getTime();

		return resultDate;

	}
	
	
	/**
	 * 判断今天是否为 {n} 号
	 * @param dayth 几号
	 * @return
	 */
	public static boolean isDayOfMonth(int dayth) {
		if (dayth > 0 && dayth < 31) {
			Calendar day = Calendar.getInstance();
			day.setTime(new Date());
			if (day.get(Calendar.DAY_OF_MONTH) == dayth) {// 每月1号
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取某一天的开始或结束时间
	 * @param beforeAfter 正数：几天后，负数：几天前，0：今天
	 * @param StartEnd true：开始，false：结束
	 * @param formate 需要输出的日期格式
	 * @return 日期 Date
	 */
	public static Date getDayStartEndDateAtHour(int beforeAfter,boolean StartEnd,String format,int hour) {

		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar now =Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE,now.get(Calendar.DATE)+beforeAfter);
		if(StartEnd){
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
		}else{
			now.set(Calendar.HOUR_OF_DAY, hour - 1);
			now.set(Calendar.MINUTE, 59);
			now.set(Calendar.SECOND, 59);
		}
		Date resultDate=now.getTime();

		return resultDate;

	}

	public static void main(String[] args) {
//		boolean t=isFirstDayOfMonth();
//		System.out.println(t);
	}

	

}
