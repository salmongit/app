package com.salmon.jpa.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	public static String format() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String format(String pattern) {
		return format(new Date(), pattern);
	}
	
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String format(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * 将符合"yyyy-MM-dd HH:mm:ss"格式的字符串转换为java.util.Date类型
	 * @param str
	 * @return
	 */
	public static Date parse(String str) {
		return parse(str, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date parse(String str, String pattern) {
		if(str == null || str.trim().length()<=0)return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date getDate(Integer beforeSecond) {
		return getDate(new Date(), beforeSecond);
	}
	
	public static Date getDate(Date date, Integer beforeSecond) {
		Long time = date.getTime() - beforeSecond * 1000;
		Date result = new Date(time);
		result.setTime(time);
		return result;
	}
	
	public static Date getDate(Long beforeSecond) {
		return getDate(new Date(), beforeSecond);
	}
	
	public static Date getDate(Date date, Long beforeSecond) {
		Long time = date.getTime() - beforeSecond * 1000;
		Date result = new Date(time);
		result.setTime(time);
		return result;
	}
	
	/**
	 * 获取年份第一天
	 * @param	calendar 
	 * @author	zhouming
	 * */
	public static Date getYearFirst(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	/**
	 * 获取年份第一天
	 * @param	year 
	 * @author	zhouming
	 * */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	/**
	 * 获取年份最后一天
	 * @param	calendar 
	 * @author	zhouming
	 * */
	public static Date getYearLast(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取年份最后一天
	 * @param	year 
	 * @author	zhouming
	 * */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取月份第一天
	 * @param	calendar
	 * @author	zhouming
	 * */
	public static Date getMonthFirst(Calendar calendar) {
		GregorianCalendar gCalendar = (GregorianCalendar)calendar;
		gCalendar.set(Calendar.DAY_OF_MONTH, 1);
		return gCalendar.getTime();
	}
	
	/**
	 * 获取月份最后一天
	 * @param	calendar
	 * @author	zhouming
	 * */
	public static Date getMonthLast(Calendar calendar) {
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
}
