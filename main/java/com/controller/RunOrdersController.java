package com.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Orders;
import com.entity.RunOrders;
import com.entity.School;
import com.service.RunOrdersService;
import com.service.SchoolService;
import com.util.ResponseObject;
import com.util.Util;
import com.wxutil.AmountUtils;
import com.wxutil.WXpayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="跑腿订单模块")
@RequestMapping("ops/runorders")
public class RunOrdersController {

	
	@Autowired
	private RunOrdersService runOrdersService;
	@Autowired
	private SchoolService schoolService;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Valid RunOrders orders,BindingResult result){
		              Util.checkParams(result);
		              orders.init();
		              orders.setOpenId(request.getAttribute("Id").toString());
		              School school=schoolService.findById(orders.getSchoolId());
		              orders.setAppId(school.getAppId());
		              runOrdersService.add(orders);
		              return new ResponseObject(true, orders.getId());
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			RunOrders orders){
		    List<RunOrders> list=runOrdersService.find(orders);
		    return new ResponseObject(true, "ok").push("list", list).push("total", runOrdersService.count(orders));
	}
	
	@ApiOperation(value="支付订单",httpMethod="POST")
	@PostMapping("pay")
	public ResponseObject pay(HttpServletRequest request,HttpServletResponse response,
			String orderId,String payment){
		 RunOrders orders=runOrdersService.findById(orderId);
		 if(payment.equals("微信支付")){
			 School school=schoolService.findById(orders.getSchoolId());
			  Object msg=WXpayUtil.payrequest(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
					  "椰子-w", orders.getId(),orders.getTotalPrice().multiply(new BigDecimal(100)).intValue()+"", orders.getOpenId(),
					  request.getRemoteAddr(), "", OrdersNotify.URL+"notify/run");
			  return new ResponseObject(true, "ok").push("msg", msg);
		 } 
		 if(payment.equals("余额支付")){
			 if(runOrdersService.pay(orders)==1){
				 Map<String,Object> map=new HashMap<>();
				 map.put("schoolId", orders.getSchoolId());
				 map.put("amount", orders.getTotalPrice());
				 schoolService.chargeUse(map);
			 }
			 return new ResponseObject(true, orderId);
		 }
		 return null;
	}
	
	
	@ApiOperation(value="取消订单",httpMethod="POST")
	@PostMapping("cancel")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			String id){
		    int i=runOrdersService.cancel(id);
		    if(i==1){
		    	return new ResponseObject(true, "ok");
		    }else if(i==2){
		    	return new ResponseObject(false, "5分钟后才可取消");
		    }
		    else
		    {
		    	return new ResponseObject(false, "请重试");
		    }
	}
}
