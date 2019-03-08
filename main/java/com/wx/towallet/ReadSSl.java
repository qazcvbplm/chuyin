/*    */ package com.wx.towallet;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.security.KeyStore;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*    */ import org.apache.http.conn.ssl.SSLContextBuilder;
/*    */ import org.apache.http.conn.ssl.SSLContexts;
/*    */ 
/*    */ public class ReadSSl
/*    */ {
/* 18 */   private static ReadSSl readSSL = null;
/*    */   
/*    */   public static ReadSSl getInstance()
/*    */   {
/* 25 */     if (readSSL == null) {
/* 26 */       readSSL = new ReadSSl();
/*    */     }
/* 28 */     return readSSL;
/*    */   }
/*    */   
/*    */   public SSLConnectionSocketFactory readCustomSSL()
/*    */     throws Exception
/*    */   {
/* 37 */     KeyStore keyStore = KeyStore.getInstance("PKCS12");
/* 38 */     FileInputStream instream = new FileInputStream(new File(WeChatConfig.CA_LICENSE));
/*    */     try
/*    */     {
/* 40 */       keyStore.load(instream, WeChatConfig.MCHID.toCharArray());
/*    */     }
/*    */     finally
/*    */     {
/* 42 */       instream.close();
/*    */     }
/* 44 */     SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WeChatConfig.MCHID.toCharArray()).build();
/*    */     
/* 46 */     SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, 
/* 47 */       SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
/* 48 */     return sslsf;
/*    */   }
/*    */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.ReadSSl
 * JD-Core Version:    0.7.0.1
 */