package com.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.springframework.validation.BindingResult;

import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

public class Util{
	
	public static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd hh:mm");
	public static SimpleDateFormat sdf4=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 将小时开始的时间转成时间戳
	 * @param hh
	 * @return
	 */
	public static Long TimetoLong(String hhmm){
		String day=sdf2.format(new Date());
		try {
			return sdf3.parse(day).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("时间格式有误");
		}
	}
	
	
	/**
	 * 获取0.00的时间戳
	 * @return
	 */
	public static Long get00(){
		Calendar c = Calendar.getInstance();    
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Long today=c.getTimeInMillis();
		return today;
	}
	
	/**
	 * 生成随机订单号
	 * @return
	 */
	public static String GenerateOrderId(){
		  String time=sdf.format(new Date());
		  String  timeM=System.currentTimeMillis()+"";
		  for(int i=0;i<10;i++){
			  int z=(int) (Math.random()*timeM.length());
			  time+=timeM.charAt(z);
		  }
		  return time;
	}
	
	
	/**
	 * 对参数进行验证
	 */
    public static void checkParams(BindingResult result){
    	    if(result.hasErrors())
    	    {
    	    	throw new RuntimeException(result.getAllErrors().get(0).getDefaultMessage());
    	    }
    }
    
	/**
	 * 对字符串加密
	 * @param content
	 * @return
	 */
	public static String EnCode(String content) {
		String miwen1 = "o" + new String(Base64.getEncoder().encode(content.getBytes())) + "p";
		return new String(Base64.getEncoder().encode(miwen1.getBytes()));
	}

	/**
	 * 对字符串解密
	 * 
	 * @param content
	 * @return
	 */
	public static String DeCode(String content) {
		String mingwen1 = new String(Base64.getDecoder().decode(content));
		return new String(Base64.getDecoder().decode(mingwen1.substring(1, mingwen1.length() - 1)));
	}
	
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 腾讯发送短信
	 * @param appid
	 * @param appkey
	 * @param phoneNumber
	 * @param templateId
	 * @param params
	 * @throws JSONException
	 * @throws HTTPException
	 * @throws IOException
	 * @throws org.json.JSONException 
	 */
	public static void qqsms(int appid,String appkey,String phoneNumber,int templateId,String params,String sign) throws JSONException, HTTPException, IOException, org.json.JSONException{
		  SmsSingleSender sender=new SmsSingleSender(appid, appkey);
		  String[] param={""};
		  if(params!=null)
		  param=params.split(",");
		  SmsSingleSenderResult result=sender.sendWithParam("86", phoneNumber, templateId, param,sign, "", "");
		  if(result.result!=0){
			  throw new RuntimeException(result.errMsg);
		  }
	}
}
