/*     */ package com.wx.towallet;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ 
/*     */ public class SignTools
/*     */ {
/*     */   public static Map<String, String> paraFilter(Map<String, String> sArray)
/*     */   {
/*  27 */     Map<String, String> result = new HashMap();
/*  29 */     if ((sArray == null) || (sArray.size() <= 0)) {
/*  30 */       return result;
/*     */     }
/*  33 */     for (String key : sArray.keySet())
/*     */     {
/*  34 */       String value = (String)sArray.get(key);
/*  35 */       if ((value != null) && (!value.equals("")) && (!key.equalsIgnoreCase("sign")) && 
/*  36 */         (!key.equalsIgnoreCase("key"))) {
/*  39 */         result.put(key, value);
/*     */       }
/*     */     }
/*  41 */     return result;
/*     */   }
/*     */   
/*     */   public static String createLinkString(Map<String, String> params)
/*     */   {
/*  51 */     List<String> keys = new ArrayList(params.keySet());
/*  52 */     Collections.sort(keys);
/*     */     
/*  54 */     String prestr = "";
/*  55 */     for (int i = 0; i < keys.size(); i++)
/*     */     {
/*  56 */       String key = (String)keys.get(i);
/*  57 */       String value = (String)params.get(key);
/*  59 */       if (i == keys.size() - 1) {
/*  60 */         prestr = prestr + key + "=" + value;
/*     */       } else {
/*  62 */         prestr = prestr + key + "=" + value + "&";
/*     */       }
/*     */     }
/*  65 */     return prestr;
/*     */   }
/*     */   
/*     */   public static String buildRequestMysign(Map<String, String> sPara)
/*     */   {
/*  75 */     Map<String, String> sParaTemp = paraFilter(sPara);
/*     */     
/*  77 */     String prestr = createLinkString(sParaTemp);
/*     */     
/*  79 */     String mysign = sign(prestr, WeChatConfig.API_KEY, "UTF-8");
/*     */     
/*  81 */     return mysign.toUpperCase();
/*     */   }
/*     */   
/*     */   public static String sign(String text, String key, String charset)
/*     */   {
/*  92 */     text = text + "&key=" + key;
/*  93 */     return DigestUtils.md5Hex(getContentBytes(text, charset));
/*     */   }
/*     */   
/*     */   private static byte[] getContentBytes(String content, String charset)
/*     */   {
/* 103 */     if ((charset == null) || ("".equals(charset))) {
/* 104 */       return content.getBytes();
/*     */     }
/*     */     try
/*     */     {
/* 107 */       return content.getBytes(charset);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 109 */       throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
/*     */     }
/*     */   }
/*     */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.SignTools
 * JD-Core Version:    0.7.0.1
 */