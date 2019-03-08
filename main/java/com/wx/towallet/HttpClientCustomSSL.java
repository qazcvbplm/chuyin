/*     */ package com.wx.towallet;
/*     */ 
/*     */ import com.util.XMLUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.ConnectException;
/*     */ import java.net.URL;
/*     */ import java.security.KeyStore;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.conn.ssl.SSLContextBuilder;
/*     */ import org.apache.http.conn.ssl.SSLContexts;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ 
/*     */ public class HttpClientCustomSSL
/*     */ {
/*     */   public static String httpClientResult(TreeMap<String, String> parms)
/*     */     throws Exception
/*     */   {
/*  49 */     StringBuffer reultBuffer = new StringBuffer();
/*     */     
/*  51 */     String requestXML = XMLUtil.getRequestXml(parms);
/*  52 */     SSLConnectionSocketFactory sslsf = ReadSSl.getInstance().readCustomSSL();
/*     */     
/*  54 */     HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
/*  55 */     CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
/*  56 */     StringEntity myEntity = new StringEntity(requestXML, "UTF-8");
/*  57 */     myEntity.setContentType("text/xml;charset=UTF-8");
/*  58 */     myEntity.setContentEncoding("UTF-8");
/*  59 */     httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
/*  60 */     httpPost.setEntity(myEntity);
/*     */     
/*  62 */     CloseableHttpResponse response = null;
/*  63 */     InputStream inputStream = null;
/*  64 */     InputStreamReader inputStreamReader = null;
/*  65 */     BufferedReader bufferedReader = null;
/*     */     try
/*     */     {
/*  67 */       response = httpclient.execute(httpPost);
/*  68 */       HttpEntity entity = response.getEntity();
/*  69 */       if (entity != null)
/*     */       {
/*  70 */         inputStream = entity.getContent();
/*  71 */         inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
/*  72 */         bufferedReader = new BufferedReader(inputStreamReader);
/*  73 */         String str = null;
/*  74 */         while ((str = bufferedReader.readLine()) != null) {
/*  75 */           reultBuffer.append(str);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ClientProtocolException e)
/*     */     {
/*  79 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  81 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  84 */       httpclient.close();
/*  85 */       response.close();
/*  86 */       bufferedReader.close();
/*  87 */       inputStreamReader.close();
/*  88 */       inputStream.close();
/*  89 */       inputStream = null;
/*     */     }
/*  91 */     System.out.println("httpclient结果:" + reultBuffer.toString());
/*  92 */     return reultBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String httpsUrlRequest(String requestUrl, String outputStr)
/*     */   {
/*     */     try
/*     */     {
/* 101 */       KeyStore keyStore = KeyStore.getInstance("PKCS12");
/* 102 */       FileInputStream instream = new FileInputStream(new File(WeChatConfig.CA_LICENSE));
/*     */       try
/*     */       {
/* 104 */         keyStore.load(instream, WeChatConfig.MCHID.toCharArray());
/*     */       }
/*     */       finally
/*     */       {
/* 106 */         instream.close();
/*     */       }
/* 108 */       SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WeChatConfig.MCHID.toCharArray()).build();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 113 */       SSLSocketFactory ssf = sslcontext.getSocketFactory();
/* 114 */       URL url = new URL(requestUrl);
/* 115 */       HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
/* 116 */       conn.setSSLSocketFactory(ssf);
/* 117 */       conn.setDoOutput(true);
/* 118 */       conn.setDoInput(true);
/* 119 */       conn.setUseCaches(false);
/*     */       
/* 121 */       conn.setRequestMethod("post");
/* 122 */       conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
/* 124 */       if (outputStr != null)
/*     */       {
/* 125 */         OutputStream outputStream = conn.getOutputStream();
/*     */         
/* 127 */         outputStream.write(outputStr.getBytes("UTF-8"));
/* 128 */         outputStream.close();
/*     */       }
/* 131 */       InputStream inputStream = conn.getInputStream();
/* 132 */       InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
/* 133 */       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/* 134 */       String str = null;
/* 135 */       StringBuffer buffer = new StringBuffer();
/* 136 */       while ((str = bufferedReader.readLine()) != null) {
/* 137 */         buffer.append(str);
/*     */       }
/* 140 */       bufferedReader.close();
/* 141 */       inputStreamReader.close();
/* 142 */       inputStream.close();
/* 143 */       inputStream = null;
/* 144 */       conn.disconnect();
/* 145 */       return buffer.toString();
/*     */     }
/*     */     catch (ConnectException localConnectException) {}catch (Exception localException) {}
/* 149 */     return null;
/*     */   }
/*     */   
/*     */   public void requestThree()
/*     */     throws Exception
/*     */   {
/* 157 */     TreeMap<String, String> parms = new TreeMap();
/* 158 */     KeyStore keyStore = KeyStore.getInstance("PKCS12");
/* 159 */     FileInputStream instream = new FileInputStream(new File(WeChatConfig.CA_LICENSE));
/*     */     try
/*     */     {
/* 161 */       keyStore.load(instream, "1296637601".toCharArray());
/*     */     }
/*     */     finally
/*     */     {
/* 163 */       instream.close();
/*     */     }
/* 167 */     SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1296637601".toCharArray()).build();
/*     */     
/* 169 */     SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, 
/* 170 */       SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 171 */     CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
/*     */     
/* 173 */     HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
/*     */     
/* 175 */     httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
/* 176 */     List<NameValuePair> nvps = WeChatUtil.generatListNameValuePair(parms);
/* 177 */     UrlEncodedFormEntity uene = new UrlEncodedFormEntity(nvps, "utf-8");
/* 178 */     uene.setContentType("text/xml;charset=UTF-8");
/*     */     try
/*     */     {
/* 180 */       httpPost.setEntity(uene);
/* 181 */       CloseableHttpResponse response = httpclient.execute(httpPost);
/*     */       
/* 183 */       HttpEntity entity = response.getEntity();
/* 184 */       if (entity != null)
/*     */       {
/* 185 */         InputStream inputStream = entity.getContent();
/* 186 */         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
/* 187 */         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/* 188 */         String str = null;
/* 189 */         StringBuffer buffer = new StringBuffer();
/* 190 */         while ((str = bufferedReader.readLine()) != null) {
/* 191 */           buffer.append(str);
/*     */         }
/* 194 */         bufferedReader.close();
/* 195 */         inputStreamReader.close();
/* 196 */         inputStream.close();
/* 197 */         inputStream = null;
/* 198 */         buffer.toString();
/* 199 */         System.out.println("httpclient结果:" + buffer.toString());
/*     */       }
/*     */     }
/*     */     catch (ClientProtocolException e)
/*     */     {
/* 202 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 204 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.HttpClientCustomSSL
 * JD-Core Version:    0.7.0.1
 */