package com.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dto.SchoolIndexDto;
import com.entity.School;
import com.entity.WxUser;
import com.feign.AuthController;
import com.github.qcloudsms.httpclient.HTTPException;
import com.redis.message.RedisUtil;
import com.service.SchoolService;
import com.service.WxUserService;
import com.util.ResponseObject;
import com.util.Util;
import com.wxutil.WXUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="微信用户模块")
@RequestMapping("ops/user")
public class WxUserController {

	
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private AuthController auth;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisUtil cache;
	
  /*public static Map<String,Long> code=new HashMap<>();
	public static Map<String,String> code2=new HashMap<>();*/
	
	@ApiOperation(value="微信用户登录",httpMethod="POST")
	@PostMapping("wx/login")
	public ResponseObject login(HttpServletRequest request,HttpServletResponse response,String code,Integer schoolId){
		   School school=schoolService.findById(schoolId);
		   if(school!=null){
			   String openid=WXUtil.wxlogin(school.getWxAppId(), school.getWxSecret(), code);
			   String token=auth.getToken(openid, "wx","wxuser");
			   WxUser user=wxUserService.login(openid,schoolId,school.getAppId());
			   cache.userCountadd(schoolId);
			   return new ResponseObject(true, "ok").push("token", token).push("user",user);
		   }else{
			   return new ResponseObject(false, "请选择学校");
		   }
    }
	
	@ApiOperation(value="微信用户更新",httpMethod="POST")
	@PostMapping("wx/update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute WxUser wxUser){
		wxUser.setOpenId(request.getAttribute("Id").toString());
		wxUser.setPhone(null);
		wxUser = wxUserService.update(wxUser);
		return new ResponseObject(true, "ok").push("user", wxUser);
    }
	
	@ApiOperation(value="查询微信用户",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,WxUser wxUser){
		wxUser.setQueryType(request.getAttribute("role").toString());
		wxUser.setQuery(request.getAttribute("Id").toString());
		List<WxUser> list = wxUserService.find(wxUser);
		wxUser.setTotal(1);
		return new ResponseObject(true, "ok").push("list", list).push("total", wxUserService.find(wxUser));
    }
	
	
	@ApiOperation(value="获取验证码",httpMethod="POST")
	@PostMapping("getcode")
	public ResponseObject getcode(HttpServletRequest request,HttpServletResponse response,@RequestParam String phone){
		StringBuilder codes=new StringBuilder();
		for(int i=0;i<4;i++){
			codes.append((int)(Math.random()*9));
		}
		try {
			Util.qqsms(1400169549, "0eb188f83ef4b2dc8976b5e76c70581e", phone, 244026, codes.toString(), null);
			/*code.put(phone, System.currentTimeMillis()+5*60*1000);
			code2.put(phone, codes.toString());*/
			stringRedisTemplate.opsForValue().set(phone, codes.toString(),5, TimeUnit.MINUTES);
		} catch (HTTPException | IOException | org.json.JSONException e) {
			return new ResponseObject(false, "发送失败");
		}
		return new ResponseObject(true, "ok");
    }
	
	@ApiOperation(value="绑定手机",httpMethod="POST")
	@PostMapping("bind")
	public ResponseObject getcode(HttpServletRequest request,HttpServletResponse response,@RequestParam String phone,@RequestParam String codes){
		String code=stringRedisTemplate.opsForValue().get(phone);
		if(/*code.get(phone)>System.currentTimeMillis()*/code!=null){
			if(codes.equals(code)){
				String id=request.getAttribute("Id").toString();
				WxUser wxUser=new WxUser();
				wxUser.setPhone(phone);
				wxUser.setOpenId(id);
				/*code.remove(phone);
				code2.remove(phone);*/
				stringRedisTemplate.delete(phone);
				return new ResponseObject(true, "ok").push("user", wxUserService.update(wxUser));
			}else{
				return new ResponseObject(false, "验证码错误");
			}
		}else{
			return new ResponseObject(false, "验证码过期");
		}
    }
	
	
	@ApiOperation(value="充值",httpMethod="POST")
	@PostMapping("charges")
	public ResponseObject charges(HttpServletRequest request,HttpServletResponse response,int chargeId){
		                  Object obj=wxUserService.charge(request.getAttribute("Id").toString(),chargeId);
		                  return new ResponseObject(true, "ok").push("msg", obj);
    }
	
	@ApiOperation(value="查询充值记录",httpMethod="POST")
	@PostMapping("findcharges")
	public ResponseObject charges(HttpServletRequest request,HttpServletResponse response,String openId){
		                  Object obj=wxUserService.findcharge(openId);
		                  return new ResponseObject(true, "ok").push("msg", obj);
    }
	
}
