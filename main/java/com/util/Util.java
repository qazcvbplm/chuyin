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
	public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }
	
	private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
	public static String filterEmoji(String source) {
        if (source==null || source.trim().equals("")) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
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
