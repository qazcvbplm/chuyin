/*     */ package com.wx.towallet;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ 
/*     */ public class WeChatUtil
/*     */ {
/*     */   public static class DateUtil
/*     */   {
/*     */     public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
/*     */     public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
/*     */     
/*     */     public static String getStringDateTime(String dateFormat)
/*     */     {
/*  50 */       Date currentTime = new Date();
/*  51 */       SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
/*  52 */       String dateString = formatter.format(currentTime);
/*  53 */       return dateString;
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getNonceStr()
/*     */   {
/*  63 */     String currTime = DateUtil.getStringDateTime("yyyyMMddHHmmss");
/*     */     
/*  65 */     String strRandom = String.valueOf(buildRandom(4));
/*     */     
/*  67 */     return currTime + strRandom;
/*     */   }
/*     */   
/*     */   public static String getPartnerTradeNo(String applyForId)
/*     */   {
/*  76 */     StringBuilder sb = new StringBuilder(applyForId);
/*  77 */     String timeStr = DateUtil.getStringDateTime("yyyyMMddHHmm");
/*     */     
/*  79 */     String strRandom = String.valueOf(buildRandom(4));
/*  80 */     for (int i = 0; i < 10 - applyForId.length(); i++) {
/*  81 */       sb.insert(0, "0");
/*     */     }
/*  83 */     sb.insert(0, timeStr);
/*  84 */     sb.append(strRandom);
/*  85 */     if (sb.length() > 32) {
/*  86 */       sb.substring(0, 31);
/*     */     }
/*  88 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static int buildRandom(int length)
/*     */   {
/*  98 */     int num = 1;
/*  99 */     double random = Math.random();
/* 100 */     if (random < 0.1D) {
/* 101 */       random += 0.1D;
/*     */     }
/* 103 */     for (int i = 0; i < length; i++) {
/* 104 */       num *= 10;
/*     */     }
/* 106 */     return (int)(random * num);
/*     */   }
/*     */   
/*     */   public static String getIpAddr(HttpServletRequest request)
/*     */   {
/* 114 */     if (request == null) {
/* 115 */       return "119.27.165.50";
/*     */     }
/* 117 */     String ip = request.getHeader("X-Forwarded-For");
/* 118 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 119 */       ip = request.getHeader("Proxy-Client-IP");
/*     */     }
/* 121 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 122 */       ip = request.getHeader("WL-Proxy-Client-IP");
/*     */     }
/* 124 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 125 */       ip = request.getHeader("HTTP_CLIENT_IP");
/*     */     }
/* 127 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 128 */       ip = request.getHeader("HTTP_X_FORWARDED_FOR");
/*     */     }
/* 130 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/* 131 */       ip = request.getRemoteAddr();
/*     */     }
/* 133 */     return ip;
/*     */   }
/*     */   
/*     */   public static String bigDecimalToPoint(BigDecimal amount)
/*     */   {
/* 143 */     BigDecimal oneHundred = new BigDecimal(100);
/* 144 */     amount = amount.abs();
/* 145 */     String amountRes = amount.multiply(oneHundred).toString();
/* 146 */     if (amountRes.indexOf(".") >= 0) {
/* 147 */       amountRes = amountRes.substring(0, amountRes.indexOf("."));
/*     */     }
/* 149 */     return amountRes;
/*     */   }
/*     */   
/*     */   public static List<NameValuePair> generatListNameValuePair(Map<String, String> properties)
/*     */   {
/* 158 */     List<NameValuePair> nvps = new ArrayList();
/* 159 */     for (Map.Entry<String, String> entry : properties.entrySet()) {
/* 160 */       nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
/*     */     }
/* 162 */     return nvps;
/*     */   }
/*     */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.WeChatUtil
 * JD-Core Version:    0.7.0.1
 */