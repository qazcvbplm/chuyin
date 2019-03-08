package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ApplicationMapper;
import com.entity.Application;
import com.entity.School;
import com.feign.AuthController;
import com.github.qcloudsms.httpclient.HTTPException;
import com.service.SchoolService;
import com.util.BaiduUtil;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="学校模块")
@RequestMapping("ops/school")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private AuthController auth;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid School school,BindingResult result){
		              Util.checkParams(result);
		              try {
						schoolService.add(school);
		              } catch (Exception e) {
						return new ResponseObject(false, e.getMessage());
		              }
		              return new ResponseObject(true, "添加成功");
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,School school){
		              List<School> list=schoolService.find(school);
		              return new ResponseObject(true,"ok").push("list", list);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,School school){
						if(school.getLoginPassWord()!=null){
							school.setLoginPassWord(Util.EnCode(school.getLoginPassWord()));
						}
		              int i=schoolService.update(school);
		              return new ResponseObject(true,"更新"+i+"条记录");
	}
	
	@ApiOperation(value="登录",httpMethod="POST")
	@PostMapping("login")
	public ResponseObject login(HttpServletRequest request,HttpServletResponse response,String loginName,String loginPass,int type){
		String token="123S";
		if(type==1){
			School school=schoolService.login(loginName,Util.EnCode(loginPass));
			if(school!=null){
				token=auth.getToken(school.getId()+"", school.getLoginName(),"school");
				return new ResponseObject(true,"ok").push("token", token).push("school", school).push("type", "school");
			}else{
				return new ResponseObject(false, "账号或密码错误");
			}
		}else{
			Application login=new Application();
			login.setLoginName(loginName);
			login.setLoginPass(Util.EnCode(loginPass));
			Application application=applicationMapper.login(login);
			if(application!=null){
				token=auth.getToken(application.getId()+"", application.getLoginName(),"admin");
				return new ResponseObject(true,"ok").push("token", token).push("admin", application).push("type", "admin");
			}else{
				return new ResponseObject(false, "账号或密码错误");
			}
		}
	}
	
	
	@ApiOperation(value="计算额外距离",httpMethod="POST")
	@PostMapping("extra_send_price")
	public BigDecimal extra_send_price(HttpServletRequest request,HttpServletResponse response,int schoolId,String origin,String des){
		   School school=schoolService.findById(schoolId);
		   int distance=BaiduUtil.DistanceAll(origin, des);
			if(distance>school.getSendMaxDistance()){
				int per=(distance/school.getSendPerOut())+1;
				return new BigDecimal(per).multiply(school.getSendPerMoney());
			}else{
				return new BigDecimal(0);
			}
   }
	
	@ApiOperation(value="代理提现",httpMethod="POST")
	@PostMapping("tx")
	public ResponseObject tx(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String openId,
			@RequestParam int schoolId,
			@RequestParam BigDecimal amount,
			@RequestParam String codes){
		          School school=schoolService.findById(schoolId);
		          String cache=stringRedisTemplate.opsForValue().get(schoolId+school.getPhone());
		          String res="";
		          if(cache!=null&&cache.equals(codes)){
		        	  stringRedisTemplate.delete(schoolId+school.getPhone());
		        	  res=schoolService.tx(schoolId,amount,openId);
		          }else{
		        	  return new ResponseObject(false, "验证码错误或者失效");
		          }
		          return new ResponseObject(true, res);
   }
	
	@ApiOperation(value="获取验证码",httpMethod="POST")
	@PostMapping("getcode")
	public ResponseObject getcode(HttpServletRequest request,HttpServletResponse response,int schoolId){
		            School school=schoolService.findById(schoolId);
		            StringBuilder codes=new StringBuilder();
			  		for(int i=0;i<6;i++){
			  			codes.append((int)(Math.random()*9));
			  		}
			  		try {
						Util.qqsms(1400169549, "0eb188f83ef4b2dc8976b5e76c70581e", school.getPhone(), 244026, codes.toString(), null);
						stringRedisTemplate.opsForValue().set(schoolId+school.getPhone(), codes.toString());
						stringRedisTemplate.expire(schoolId+school.getPhone(), 3, TimeUnit.MINUTES);
					} catch (HTTPException | IOException | org.json.JSONException e) {
						return new ResponseObject(false, "发送失败");
					}
		          return new ResponseObject(true, "验证吗3分钟有效哦！亲");
   }
	
	
}
