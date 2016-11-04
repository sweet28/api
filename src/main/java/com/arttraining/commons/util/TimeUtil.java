package com.arttraining.commons.util;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtil {
	public static Timestamp getTimeStamp() {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		return time;
	}
}
