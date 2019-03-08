package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.entity.Orders;
import com.entity.RunOrders;
import com.service.OrdersService;
import com.service.RunOrdersService;
import com.service.WxUserService;
import com.wxutil.XMLUtil;

import io.swagger.annotations.ApiOperation;



@Controller
@RequestMapping("ops/notify")
public class OrdersNotify {
	
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private RunOrdersService runordersService;
	@Autowired
	private WxUserService wxUserService;
	
	public static final String URL="https://www.chuyinkeji.cn/ops/";

	@PostMapping("takeout")
	@ApiOperation(value="",hidden=true)
	public void takeout_noti(HttpServletResponse response,HttpServletRequest request) throws IOException, SAXException{
			Map<String, String>  map = XMLUtil.parseXML(request);
			// 返回状态码
			String return_code = map.get("return_code");
			// 返回信息
			String return_msg = map.get("return_msg");
			// 业务结果,判断交易是否成功
			String result_code = map.get("result_code");
			
			 if(return_code=="SUCCESS"||return_code.equals("SUCCESS"))
		     {
				String orderId=map.get("out_trade_no");
				Orders orders= ordersService.findById(orderId);
				if(orders.getStatus().equals("待付款")){
					if(ordersService.paySuccess(orderId,"微信支付")==1){
						//wxUserService.addSourcePaySuccess(orders);
						Success(response);
					}
				}
		     }
	}
	
	@PostMapping("run")
	@ApiOperation(value="",hidden=true)
	public void run(HttpServletResponse response,HttpServletRequest request) throws IOException, SAXException{
			Map<String, String>  map = XMLUtil.parseXML(request);
			// 返回状态码
			String return_code = map.get("return_code");
			// 返回信息
			String return_msg = map.get("return_msg");
			// 业务结果,判断交易是否成功
			String result_code = map.get("result_code");
			
			 if(return_code=="SUCCESS"||return_code.equals("SUCCESS"))
		     {
				String orderId=map.get("out_trade_no");
				RunOrders orders= runordersService.findById(orderId);
				if(orders.getStatus().equals("待付款")){
					if(runordersService.paySuccess(orderId,"微信支付")==1){
						Success(response);
					}
				}
		     }
	}
	
	@PostMapping("charge")
	@ApiOperation(value="",hidden=true)
	public void charge(HttpServletResponse response,HttpServletRequest request) throws IOException, SAXException{
			Map<String, String>  map = XMLUtil.parseXML(request);
			// 返回状态码
			String return_code = map.get("return_code");
			// 返回信息
			String return_msg = map.get("return_msg");
			// 业务结果,判断交易是否成功
			String result_code = map.get("result_code");
			
			 if(return_code=="SUCCESS"||return_code.equals("SUCCESS"))
		     {
				String orderId=map.get("out_trade_no");
				String openId=map.get("openid");
				String attach=map.get("attach");
				wxUserService.chargeSuccess(orderId,openId,attach);
				Success(response);
		     }
	}
	
	public static void Success(HttpServletResponse response) throws IOException {
		PrintWriter out=response.getWriter();
		out.print("<xml><return_code><![CDATA[SUCCESS]]></return_code>"
        		+ "<return_msg><![CDATA[SUCCESS]]></return_msg></xml>");
		out.flush();
		out.close();
    }
	
}
