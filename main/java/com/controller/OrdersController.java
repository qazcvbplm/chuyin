package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dao.LogsMapper;
import com.entity.Logs;
import com.entity.OrderProduct;
import com.entity.Orders;
import com.entity.School;
import com.entity.WxUser;
import com.entity.WxUserBell;
import com.service.OrdersService;
import com.service.ProductService;
import com.service.SchoolService;
import com.util.ResponseObject;
import com.util.Util;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.EmojiTransformer;
import com.wxutil.WXpayUtil;
import com.wxutil.WxGUtil;

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
	private ProductService productService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	
	
	
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,
			Integer[] productIds,Integer[] attributeIndex,Integer[] counts,@ModelAttribute @Valid Orders orders,BindingResult result){
		              Util.checkParams(result);
		              /*if(Util.containsEmoji(orders.getRemark())){
		            	  orders.setRemark(Util.filterEmoji(orders.getRemark()));
		              }*/
		              if(orders.getRemark()!=null&&EmojiManager.isEmoji(orders.getRemark())){
		            	  orders.setRemark(EmojiParser.removeAllEmojis(orders.getRemark()));
		              }
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
		 List<OrderProduct> ops=orders.getOp();
		 List<Integer> pids=new ArrayList<>();
		 List<Integer> counts=new ArrayList<>();
		 for(OrderProduct temp:ops){
			pids.add(temp.getProductId());
			counts.add(temp.getProductCount());
		 }
		 productService.sale(pids,counts);
		 if(payment.equals("微信支付")){
			 School school=schoolService.findById(orders.getSchoolId());
			  Object msg=WXpayUtil.payrequest(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
					  "椰子-w", orders.getId(),orders.getPayPrice().multiply(new BigDecimal(100)).intValue()+"", orders.getOpenId(),
					  request.getRemoteAddr(), "", OrdersNotify.URL+"notify/takeout");
			  return new ResponseObject(true, "ok").push("msg", msg);
		 }
		 if(payment.equals("余额支付")){
			 if(ordersService.pay(orders)==1){
				 Map<String,Object> map=new HashMap<>();
				 map.put("schoolId", orders.getSchoolId());
				 map.put("amount", orders.getPayPrice());
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
		
		    int i=ordersService.cancel(id);
		    if(i>0){
		    	if(stringRedisTemplate.boundHashOps("SHOP_DJS"+i).delete(id)<=0){
		      		 return new ResponseObject(false,"联系管理员");
		      	}
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
			 List<Orders> list;
			 if(Boolean.parseBoolean(stringRedisTemplate.opsForValue().get("cache"))){
				 list=JSON.parseArray(stringRedisTemplate.boundHashOps("SHOP_DJS"+shopId).values().toString(),Orders.class);
			 }else{
				list=ordersService.findByShopByDjs(shopId);
			 }
		     return new ResponseObject(true,list.size()+"")
		    		 .push("list",JSON.toJSONString(list));
	}
	
	@ApiOperation(value="商家查询订单",httpMethod="POST")
	@PostMapping("android/findorders")
	public ResponseObject android_findorders(HttpServletRequest request,HttpServletResponse response,int shopId,int page,int size){
		     List<Orders> list=ordersService.findByShop(shopId,page,size);
		     return new ResponseObject(true, list.size()+"").push("list", Util.toJson(list));
	}
	
	@ApiOperation(value="商家查询已接手订单",httpMethod="POST")
	@PostMapping("android/findordersyjs")
	public ResponseObject android_findorders2(HttpServletRequest request,HttpServletResponse response,int shopId,int page,int size){
		     List<Orders> list=ordersService.findByShopYJS(shopId,page,size);
		     return new ResponseObject(true, list.size()+"").push("list", Util.toJson(list));
	}
	
	@ApiOperation(value="商家接手订单",httpMethod="POST")
	@PostMapping("android/acceptorder")
	public ResponseObject android_findDjs(HttpServletRequest request,HttpServletResponse response,String orderId){
		     int i=ordersService.shopAcceptOrderById(orderId);
		     if(i>0){
		    	 if(Boolean.parseBoolean(stringRedisTemplate.opsForValue().get("cache"))){
		    		 stringRedisTemplate.boundHashOps("SHOP_DJS"+i).delete(orderId);
		    		 Orders order=ordersService.findById(orderId);
		    	 }
		    	 return new ResponseObject(true, "接手成功").push("order",Util.toJson(ordersService.findById(orderId)));
		     }
		     else{
		    	 return new ResponseObject(false, "已经接手");
		     }
	}
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////android/////////////////////////////////////////////////////////////////
	
}
