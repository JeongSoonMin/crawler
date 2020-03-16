/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Package : kr.co.smij.framework.util
 * @File    : DateUtil.java
 * 
 * @Title   : 날짜 관련 util
 * @date    : 2017. 12. 18.
 */
public class DateUtil {

	/**
	 * @Purpose : 원하는 format으로 오늘날짜 및 시간 구하기
	 *
	 * @Method Name : getToday
	 * @Return          : String
	 * @description     : 년도 : yyyy 월 : MM 일 : dd 시간 : HH 분 : mm 초 : ss
	 * 						   대소문자 구분 필수
	 */
	public static String getToday(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
	}

	/**
	 * @Purpose : date 날짜 포멧
	 *
	 * @Method Name 	: dateFormat
	 * @Return        	: String
	 * @Description
	 */
	public static String dateFormat(Date date, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * @Purpose : 요일 코드 return
	 *
	 * @Method Name 	: getDayOfWeek
	 * @Return        	: int
	 * @Description
	 * 
	 * 일요일 : 1 ~ 토요일 : 7
	 * 
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @Purpose : 날짜 더하기
	 *
	 * @Method Name 	: addDate
	 * @Return        	: Date
	 * @Description
	 */
	public static Date addDate(Date date, int add) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, add);

		return calendar.getTime();
	}

	/**
	 * @Purpose : 날짜 비교
	 *
	 * @Method Name 	: compareDate
	 * @Return        	: String
	 * @Description
	 * 
	 * result 
	 * date2 기준
	 * 0 : error
	 * 1 : befor
	 * 2 : equal(same)
	 * 3 : after
	 * 
	 */
	public static String compareDate(Date date1, Date date2) {
		String result = "0";

		if (date1.before(date2)) {
			result = "1";
		} else if (date1.equals(date2)) {
			result = "2";
		} else if (date1.after(date2)) {
			result = "3";
		}

		return result;
	}

	/**
	 * @Purpose : 날짜 비교 (비교 대상이 string 이나 date, string 일 경우 사용) 
	 *
	 * @Method Name 	: compareDate
	 * @Return        	: String
	 * @Description
	 */
	public static String compareDate(Object date1, Object date2, String format) {
		Date dateConvert1 = new Date();
		Date dateConvert2 = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		try {
			if ("String".equals(date1.getClass().getSimpleName())) {
				dateConvert1 = (Date) formatter.parse(String.valueOf(date1));
			} else {
				dateConvert1 = (Date) date1;
			}

			if ("String".equals(date2.getClass().getSimpleName())) {
				dateConvert2 = (Date) formatter.parse(String.valueOf(date2));
			} else {
				dateConvert2 = (Date) date2;
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		return compareDate(dateConvert1, dateConvert2);
	}

	/**
	 * @Purpose : 2012-10-23 00:00:00 -> 2012-10-23 23:59:59
	 *
	 * @Method Name 	: getEndDate
	 * @Return        	: Date
	 * @Description
	 */
	public static Date getEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.SECOND, -1);

		return calendar.getTime();
	}

	/**
	 * @Purpose : 오늘 날짜가 포함되는지 검사
	 *
	 * @Method Name 	: compareBetweenToday
	 * @Return        	: boolean
	 * @Description
	 */
	public static boolean compareBetweenToday(Date start, Date end) {
		boolean result = false;
		Date today = Calendar.getInstance().getTime();

		if ("1".equals(compareDate(start, today)) || "2".equals(compareDate(start, today))) {
			if ("2".equals(compareDate(end, today)) || "3".equals(compareDate(end, today))) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * @Purpose : 종료시간, 시작시간 차이를 초로 return
	 *
	 * @Method Name 	: getTimeGap
	 * @Return        	: int
	 * @Description
	 */
	public static int getTimeGap(String start, String end) {
		Date startDate = new Date();
		Date endDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startDate = (Date) formatter.parse(start);
			endDate = (Date) formatter.parse(end);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

		long s = startDate.getTime();
		long e = endDate.getTime();

		return (int) (e - s) / 1000;
	}

}
