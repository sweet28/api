package com.arttraining.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	/***
	 * 将string类型转换成指定格式的类型
	 */
	public static Date strToDateByFormat(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/***
	 * 将string类型转换成指定格式的类型
	 */
	public static Date strToDateDayByFormat(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/***
	 * 将string类型转换成date类型
	 */
	public static Date strToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/***
	 * 判断支付时间是否已经错过
	 * date1--支付时间
	 * date2--当前时间
	 * @return
	 */
	public static long isOverTime(Date date1, Date date2) {
		long  between = (date1.getTime() - date2.getTime())/1000;
		return between;
	}
	
	/***
	 * 判断支付时间是否已经错过
	 * date1--减数时间
	 * date2--被减数时间
	 * @return
	 * @throws ParseException 
	 */
	public static long isOverDay(String date1, String date2) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //跨年不会出现问题
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
        Date fDate=sdf.parse(date1);
        Date oDate=sdf.parse(date2);
        long days=(fDate.getTime()-oDate.getTime())/(1000*3600*24);
		return days;
	}
	
	public static Timestamp getTimeStamp() {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		return time;
	}
	
	public static long getTimeStampLong() {
		Date date = new Date();
		return date.getTime();
	}
	
	/**
     * 将date类型的时间 转换成字符串类型
     * 
     * **/
	public static String getTimeByDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
    
    
	public static long diffSeconds(long nowTime, long oldTime){
		long result = 0;
		//这个方法是用来计算在秒单位上的差距 所以永远不可能大于60秒  考虑比较时差需要加上 天、小时、分钟、秒 才是总差
		long diff = nowTime - oldTime;
//	    long day = diff / (1000 * 60 * 60 * 24);
//	    long hour=(diff/(60*60*1000)-day*24);
//	    long min=((diff/(60*1000))-day*24*60-hour*60);
//	    long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
//	    System.out.println("day-"+day+" min-"+hour+" -min "+min + " sec -"+s);
	    long s=diff/1000;
	    result = s;
		
	    return result;
	}
	
	/** 
	 * 获取当前时间之前或之后几分钟 minute
    */
    public static String getTimeStringByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
    
    /** 
	 * 获取当前时间之前或之后几分钟 minute
     * @throws ParseException 
    */
    public static Timestamp getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        Date date;
        Timestamp time;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
	        time = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			time = new Timestamp(new Date().getTime());
		}
        
        return time;

    }
    
}
