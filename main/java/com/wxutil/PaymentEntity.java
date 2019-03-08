package com.wxutil;

import java.util.Map;

public class PaymentEntity {
	
	
	 /*params.add(new HTTPParam("appid", appid));
	   params.add(new HTTPParam("mch_id", mch_id));
	   params.add(new HTTPParam("device_info", device_info));
	   params.add(new HTTPParam("nonce_str", nonce_str));
	   params.add(new HTTPParam("sign", sign));
	   params.add(new HTTPParam("sign_type", sign_type));
	   params.add(new HTTPParam("body", body));
	   params.add(new HTTPParam("detail", detail));
	   params.add(new HTTPParam("attach", attach));
	   params.add(new HTTPParam("out_trade_no", out_trade_no));
	   params.add(new HTTPParam("fee_type", fee_type));
	   params.add(new HTTPParam("total_fee", total_fee));
	   params.add(new HTTPParam("spbill_create_ip", spbill_create_ip));
	   params.add(new HTTPParam("time_start", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())));
	   params.add(new HTTPParam("time_expire", new SimpleDateFormat("yyyyMMddhhmmss").format(System.currentTimeMillis()+360)));
	   params.add(new HTTPParam("goods_tag", goods_tag));
	   params.add(new HTTPParam("notify_url", notify_url));
	   params.add(new HTTPParam("trade_type", trade_type));
	   params.add(new HTTPParam("limit_pay", limit_pay));
	   params.add(new HTTPParam("openid", openid));*/
	
	
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String body;
	private String out_trade_no;
	private String total_fee;
	private String spbill_create_ip;
	private String notify_url;
	private String trade_type;
	private String sign;
	private String openid;
	private String attach;
	
	
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	PaymentEntity(Map<String,String> map)
	{
		this.appid=map.get("appid");
		this.mch_id=map.get("mch_id");
		this.nonce_str=map.get("nonce_str");
		this.body=map.get("body");
		this.out_trade_no=map.get("out_trade_no");
		this.total_fee=map.get("total_fee");
		this.spbill_create_ip=map.get("spbill_create_ip");
		this.notify_url=map.get("notify_url");
		this.trade_type=map.get("trade_type");
		this.sign=map.get("sign");
		this.openid=map.get("openid");
		this.attach=map.get("attach");
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	

}
