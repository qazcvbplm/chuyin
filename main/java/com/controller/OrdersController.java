package com.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ApplicationMapper;
import com.entity.Application;
import com.entity.Orders;
import com.entity.School;
import com.service.OrdersService;
import com.service.SchoolService;
import com.util.ResponseObject;
import com.util.Util;
import com.wxutil.WXpayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="订单模块")
@RequestMapping("ops/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private ApplicationMapper applicationMapper;
	
	
	
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,
			Integer[] productIds,Integer[] attributeIndex,Integer[] counts,@ModelAttribute @Valid Orders orders,BindingResult result){
		              Util.checkParams(result);
		              orders.init();
		              if((productIds.length==attributeIndex.length)&&(productIds.length==counts.length)){
		            	  if(productIds.length>0){
		            		  orders.setOpenId(request.getAttribute("Id").toString());
		            		  ordersService.addTakeout(productIds,attributeIndex,counts,orders);
		            		  return new ResponseObject(true, orders.getId());
		            	  }
		              }
		              return null;
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			Orders orders){
		    List<Orders> list=ordersService.find(orders);
		    return new ResponseObject(true, "ok").push("list", list).push("total", ordersService.count(orders));
	}
	
	@ApiOperation(value="支付订单",httpMethod="POST")
	@PostMapping("pay")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			String orderId,String payment){
		 Orders orders=ordersService.findById(orderId);
		 if(payment.equals("微信支付")){
			 School school=schoolService.findById(orders.getSchoolId());
			  Object msg=WXpayUtil.payrequest(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
					  "椰子-w", orders.getId(),orders.getPayPrice().multiply(new BigDecimal(100)).intValue()+"", orders.getOpenId(),
					  request.getRemoteAddr(), "", OrdersNotify.URL+"notify/takeout");
			  return new ResponseObject(true, "ok").push("msg", msg);
		 }
		 if(payment.equals("余额支付")){
			 ordersService.pay(orders);
			 return new ResponseObject(true, orderId);
		 }
		 return null;
	}
	
	
	@ApiOperation(value="取消订单",httpMethod="POST")
	@PostMapping("cancel")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			String id){
		    int i=ordersService.cancel(id);
		    if(i==1){
		    	return new ResponseObject(true, "ok");
		    } 
		    else if(i==2){
		    	return new ResponseObject(false, "5分钟后才能退款");
		    }else{
		    	return new ResponseObject(false, "请重试");
		    }
	}
	
	
	
	
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	
	@ApiOperation(value="商家查询待接手订单",httpMethod="POST")
	@PostMapping("android/findDjs")
	public ResponseObject android_findDjs(HttpServletRequest request,HttpServletResponse response,int shopId){
		     List<Orders> list=ordersService.findByShopByDjs(shopId);
		     return new ResponseObject(true, list.size()+"").push("list", Util.toJson(list));
	}
	
	@ApiOperation(value="商家查询订单",httpMethod="POST")
	@PostMapping("android/findorders")
	public ResponseObject android_findorders(HttpServletRequest request,HttpServletResponse response,int shopId,int page,int size){
		     List<Orders> list=ordersService.findByShop(shopId,page,size);
		     return new ResponseObject(true, list.size()+"").push("list", Util.toJson(list));
	}
	
	@ApiOperation(value="商家接手订单",httpMethod="POST")
	@PostMapping("android/acceptorder")
	public ResponseObject android_findDjs(HttpServletRequest request,HttpServletResponse response,String orderId){
		     int i=ordersService.shopAcceptOrderById(orderId);
		     if(i==1)
		    	 return new ResponseObject(true, "接手成功").push("order",Util.toJson(ordersService.findById(orderId)));
		     else
		    	 return new ResponseObject(false, "已经接手");
	}
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	
}
