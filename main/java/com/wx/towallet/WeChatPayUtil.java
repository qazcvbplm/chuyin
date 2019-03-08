/*    */ package com.wx.towallet;
import com.entity.TxLog;
/*    */ 
/*    */ import com.util.LoggerUtil;
/*    */ import com.util.XMLUtil;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ 
/*    */ public class WeChatPayUtil
/*    */ {
/*    */   public static int transfers(String appid, String mch_id, String paykey, String cert, String payId, String ip, BigDecimal amount, String openId, TxLog log)
/*    */     throws Exception
/*    */   {
/* 26 */     String[] uiids = null;
/*    */     
/*    */ 
/* 29 */     String nonceStr = WeChatUtil.getNonceStr();
/* 30 */     Map<String, String> map = null;
/* 31 */     List<Map<String, String>> list = new ArrayList();
/*    */     
/*    */ 
/* 34 */     TreeMap<String, String> parms = new TreeMap();
/* 35 */     parms.put("mch_appid", appid);
/* 36 */     parms.put("mchid", mch_id);
/* 37 */     parms.put("nonce_str", nonceStr);
/* 38 */     parms.put("partner_trade_no", WeChatUtil.getPartnerTradeNo(payId));
/* 39 */     parms.put("amount", WeChatUtil.bigDecimalToPoint(amount));
/* 40 */     parms.put("desc", "提现款额");
/* 41 */     parms.put("spbill_create_ip", ip);
/* 42 */     parms.put("openid", openId);
/* 43 */     parms.put("check_name", "NO_CHECK");
/*    */     
/* 45 */     WeChatConfig.API_KEY = paykey;
/* 46 */     WeChatConfig.MCHID = mch_id;
/* 47 */     WeChatConfig.CA_LICENSE = cert;
/* 48 */     parms.put("sign", SignTools.buildRequestMysign(parms));
/*    */     
/* 50 */     String resultXML = HttpClientCustomSSL.httpClientResult(parms);
/*    */     
/* 52 */     Map<String, Object> resultMap = XMLUtil.doXMLParse(resultXML);
/* 64 */     LoggerUtil.log(resultXML);
/* 53 */     String return_code = (String)resultMap.get("return_code");
/* 54 */     String result_code = (String)resultMap.get("result_code");
/* 56 */     if ((return_code.equalsIgnoreCase("SUCCESS")) && (result_code.equalsIgnoreCase("SUCCESS"))) {
				log.setResult(resultMap.get("partner_trade_no").toString());
/* 60 */       return 1;
/*    */     }
					log.setResult(resultMap.get("err_code_des")+","+resultMap.get("return_msg"));		
/* 65 */     return 0;
/*    */   }
/*    */ }


/* Location:           D:\j2eework2\BOOT-INF\classes\
 * Qualified Name:     com.wx.towallet.WeChatPayUtil
 * JD-Core Version:    0.7.0.1
 */