package com.arttraining.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static Timestamp getTimeStamp() {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		return time;
	}
	
	public static long diffSeconds(long nowTime, long oldTime){
		long result = 0;
		
		long diff = nowTime - oldTime;
	    long day = diff / (1000 * 60 * 60 * 24);
	    long hour=(diff/(60*60*1000)-day*24);
	    long min=((diff/(60*1000))-day*24*60-hour*60);
	    long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
	    
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
