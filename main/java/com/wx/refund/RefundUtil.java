package com.wx.refund;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.util.LoggerUtil;
import com.util.XMLUtil;

public class RefundUtil {
	

	
	/**
	 * 退款函数，该方法可以对曾经部分退款的订单进行再次退款
	 * @param out_trade_no 商户订单号
	 * @param total_fee1 退款对应的订单的总金额（以“分”为单位）
	 * @param refund_fee1 计划退款的金额（以“分”为单位）
	 * @return
	 */
	public static int wechatRefund1(String appid,String appsecret,String mch_id,String partnerkey,String cert,
			String out_trade_no,String total_fee1,String refund_fee1){
		String out_refund_no = out_trade_no;// 退款单号，随机生成 ，但长度应该跟文档一样（32位）(卖家信息校验不一致，请核实后再试)
		String total_fee =total_fee1;//订单的总金额,以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		String refund_fee =refund_fee1;// 退款金额，以分为单位（填错了貌似提示：同一个out_refund_no退款金额要一致）
		String nonce_str = UUID.randomUUID().toString().substring(0, 32);// 随机字符串
		//微信公众平台文档：“基本配置”--》“开发者ID”
		//微信公众平台文档：“基本配置”--》“开发者ID”
		//商户号
		//微信公众平台文档：“微信支付”--》“商户信息”--》“商户号”，将该值赋值给partner
		String op_user_id = mch_id;//就是MCHID
		//微信公众平台："微信支付"--》“商户信息”--》“微信支付商户平台”（登录）--》“API安全”--》“API密钥”--“设置密钥”（设置之后的那个值就是partnerkey，32位）
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);
	
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + 
				"<appid>" + appid + "</appid>" + 
				"<mch_id>" + mch_id + "</mch_id>" + 
				"<nonce_str>" + nonce_str + "</nonce_str>" + 
				"<sign><![CDATA[" + sign + "]]></sign>"	+ 
				"<out_trade_no>" + out_trade_no + "</out_trade_no>"	+ 
				"<out_refund_no>" + out_refund_no + "</out_refund_no>" + 
				"<total_fee>" + total_fee + "</total_fee>" + 
				"<refund_fee>" + refund_fee + "</refund_fee>" + 
				"<op_user_id>" + op_user_id + "</op_user_id>" + 
				"</xml>";
		
		try {
			ClientCustomSSL.setPartner(mch_id);
			String refundResult= ClientCustomSSL.doRefund(cert, xml);
			Map<String, Object> resultMap =  XMLUtil.doXMLParse(refundResult);
			String result=(String) resultMap.get("result_code");
			if(result.equals("SUCCESS")){
				return 1;
			}else{
				LoggerUtil.log((String) resultMap.get("err_code_des"));
			}
		} catch (Exception e) {
			//记录
			LoggerUtil.log(e.getMessage());
		}
		return 0;
	}
	

}
