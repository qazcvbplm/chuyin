package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShopTimeUtil {

	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	public static long parse(String time) {
		String newtime=sdf.format(new Date())+" "+time;
		try {
			return sdf2.parse(newtime).getTime();
		} catch (ParseException e) {
		    throw new RuntimeException("时间格式不正确");
		}
		
	}
	
	public static long getDay() {
		long today;
		try {
			return today = sdf.parse(sdf.format(new Date())).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("时间格式不正确");
		}
	}
	
	
}
