package com.controller;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dto.SenderTj;
import com.dto.ShopTj;
import com.entity.FullCut;
import com.entity.Orders;
import com.entity.School;
import com.entity.Shop;
import com.entity.ShopOpenTime;
import com.feign.AuthController;
import com.service.SchoolService;
import com.service.ShopService;
import com.util.ResponseObject;
import com.util.Util;
import com.wxutil.WXUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="店铺模块")
@RequestMapping("ops/shop")
public class ShopController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private SchoolService schoolService;
    @Autowired
    private AuthController auth;
	/* @Value("${barcode.path}")
	private  String barcodepath;*/
    @Autowired
	private StringRedisTemplate cache;
    
  
    
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid Shop shop,BindingResult result){
		              Util.checkParams(result);
		              shop.setSchoolId(Integer.valueOf(request.getAttribute("Id").toString()));
		              shopService.add(shop);
		              return new ResponseObject(true, "添加成功");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@RequestMapping("find")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,Shop shop){
		            /*  String rs=null;
		              List<Shop> list=null;
			      	  String key=shop.toString();
			      	  if((rs=cache.opsForValue().get(key))==null){
			      			list=shopService.find(shop);
			      			cache.opsForValue().set(key, JSON.toJSONString(list),5,TimeUnit.MINUTES);
			      	  }else{
			      		  list=(List<Shop>) JSON.parse(cache.opsForValue().get(key));
			      	  }*/
		              int count = shopService.count(shop);
		              return new ResponseObject(true, "ok").push("list", shopService.find(shop)).push("total", count);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,Shop shop){
		              int i = shopService.update(shop);
		              return new ResponseObject(true, "更新"+i+"条记录");
	}
	
	
	@ApiOperation(value="添加满减",httpMethod="POST")
	@PostMapping("add_fullcut")
	public ResponseObject add_fullcut(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid FullCut fullcut,BindingResult result){
		              Util.checkParams(result);
		              shopService.addFullCut(fullcut);
		              return new ResponseObject(true, "添加成功");
	}
	
	@ApiOperation(value="删除满减",httpMethod="POST")
	@PostMapping("delete_fullcut")
	public ResponseObject add_fullcut(HttpServletRequest request,HttpServletResponse response,int id){
		              int i=shopService.deleteFullCut(id);
		              return new ResponseObject(true, "删除"+i+"条记录");
	}
	
	@ApiOperation(value="查询满减",httpMethod="POST")
	@PostMapping("find_fullcut")
	public ResponseObject find_fullcut(HttpServletRequest request,HttpServletResponse response,int shopId){
		              List<FullCut> list=shopService.findFullCut(shopId);
		              return new ResponseObject(true, "ok").push("list", list);
	}
	
	@ApiOperation(value="按店铺查询营业时间",httpMethod="POST")
	@PostMapping("find/openTime")
	public ResponseObject openTime(HttpServletRequest request,HttpServletResponse response,int shopId){
		List<ShopOpenTime> list = shopService.findOpenTime(shopId);
		return new ResponseObject(true, "ok").push("time", list);
	}
	
	@ApiOperation(value="添加营业时间",httpMethod="POST")
	@PostMapping("add/openTime")
	public ResponseObject openTime(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid ShopOpenTime time,BindingResult result){
		Util.checkParams(result);
		shopService.addOpenTime(time);
		return new ResponseObject(true, "ok");
	}
	
	@ApiOperation(value="删除营业时间",httpMethod="POST")
	@PostMapping("delete/deleteopenTime")
	public ResponseObject deleteopenTime(HttpServletRequest request,HttpServletResponse response,int id){
		shopService.removeopentime(id);
		return new ResponseObject(true, "ok");
	}
	
	
	
	@ApiOperation(value="店铺登录",httpMethod="POST")
	@PostMapping("android/login")
	public ResponseObject android_login(HttpServletRequest request,HttpServletResponse response,String loginName,String loginPassWord){
        Shop shop = shopService.login(loginName, loginPassWord);
        String token = auth.getToken(shop.getId() + "", shop.getShopLoginName(), "android");
        return new ResponseObject(true, "ok").push("msg", Util.toJson(shop)).push("token", token);
	}
	
	@ApiOperation(value="店铺营业开关",httpMethod="POST")
	@PostMapping("android/openorclose")
	public String openorclose(HttpServletRequest request,HttpServletResponse response,Integer id){
		return shopService.openorclose(id)+"";
	}
	
	
	
	
	
	
	@ApiOperation(value="商家临时统计",httpMethod="POST")
	@PostMapping("nocheck/shoptempstatistics")
	public ResponseObject shoptempstatistics(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Integer shopId,@RequestParam String beginTime,@RequestParam String endTime){
		                  SenderTj result=shopService.statistics(shopId,beginTime,endTime);
		                  return new ResponseObject(true, "ok").push("result", result);
	}

	@ApiOperation(value="商家统计",httpMethod="POST")
	@PostMapping("nocheck/shopstatistics")
	public ResponseObject shopstatistics(HttpServletRequest request,HttpServletResponse response,
										   @RequestParam Integer shopId,@RequestParam String beginTime,@RequestParam String endTime){
		ShopTj result=shopService.shopstatistics(shopId,beginTime,endTime);
		return new ResponseObject(true, "ok").push("result", result);
	}
	
	
	@ApiOperation(value="店铺二维码",httpMethod="POST")
	@PostMapping("barcode")
	public ResponseObject barcode(HttpServletRequest request,HttpServletResponse response,int id){
		     Shop shop=shopService.findById(id);
		     School school=schoolService.findById(shop.getSchoolId());
		     String path="/home/nginx/shopbarcode/shopbarcode/";
		     File dir=new File(path);
		     if(!dir.exists()){
		    	 dir.mkdirs();
		     }
		     WXUtil.getCode(school.getWxAppId()
		    		 , school.getWxSecret(), "pages/index/item/menu/menu?shopid="+id,path+id+".jpg");
		     return new ResponseObject(true, "ok").push("barcode", "https://www.chuyinkeji.cn/shopbarcode/"+id+".jpg");
	}
	
}
