package com.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.config.RedisConfig;
import com.dao.WxUserBellMapper;
import com.dto.ReplyTextMsg;
import com.entity.WxUser;
import com.service.WxUserService;
import com.util.LoggerUtil;
import com.util.ResponseObject;
import com.wxutil.XMLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(tags="总体信息模块")
@RequestMapping("ops/application")
public class ApplicationController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private WxUserController wxUserController;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	
	@RequestMapping("check")
	public String version(HttpServletRequest request,HttpServletResponse response) throws IOException, SAXException{
		Map<String, String>  map = XMLUtil.parseXML(request);
		String ToUserName=map.get("ToUserName");//	开发者微信号
		String FromUserName=map.get("FromUserName");//发送方帐号（一个OpenID）
		String MsgType=map.get("MsgType");
		String Content=map.get("Content");
		String Event=map.get("Event");
		WxUser user=checkopenId(FromUserName);
		if(MsgType.equals("text")){
			if(Content.startsWith("绑定")){
				if(user.getPhone()==null||user.getPhone().equals("")){
					Content=Content.replace("绑定", "");
					ReplyTextMsg re;
					List<WxUser> minUser=wxUserService.findByPhoneGZH(Content);
					if(minUser.size()==0){
						re=new ReplyTextMsg(ToUserName,FromUserName,"text","该号码未在客户端小程序中绑定手机号!");
						return re.Msg2Xml();
					}
					if(minUser.size()>1){
						re=new ReplyTextMsg(ToUserName,FromUserName,"text","验证码发送失败，等待客服与您回复!");
						return re.Msg2Xml();
					}
					ResponseObject rs=wxUserController.getcode(request, response, Content);
					stringRedisTemplate.opsForValue().set(user.getOpenId(), Content,5,TimeUnit.MINUTES);
					if(rs.isCode()){
						re=new ReplyTextMsg(ToUserName,FromUserName,"text","验证码已发送，回复验证＋验证码即可绑定成功，例：验证8888（勿加空格和额外字符）");
					}else{
						re=new ReplyTextMsg(ToUserName,FromUserName,"text",rs.getMsg());
					}
					return re.Msg2Xml();
				}else{
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text","已经绑定了"+user.getPhone());
					return re.Msg2Xml();
				}
			}
			if(Content.startsWith("验证")){
				Content=Content.replace("验证", "");
				String phone=stringRedisTemplate.opsForValue().get(user.getOpenId());
				request.setAttribute("Id", user.getOpenId());
				List<WxUser> minUser=wxUserService.findByPhoneGZH(phone);
				ResponseObject rs=wxUserController.bind(request, response, phone, Content);
				if(rs.isCode()){
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text","绑定成功，恭喜您获得88积分奖励和0.5元红包奖励。奖励都已发放至您的客户端小程序中，请自行前往查看！");
					//增加积分
					user=minUser.get(0);
	        		stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL, "addsource"+","+user.getOpenId()+","+"88");
	        		Map<String,Object> ch=new HashMap<>();
	        		ch.put("phone", user.getOpenId()+"-"+user.getPhone());
	        	    ch.put("amount", "0.5");
	        	    wxUserBellMapper.charge(ch);
					return re.Msg2Xml();
				}else{
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text","绑定失败请联系管理员");
					return re.Msg2Xml();
				}
			}
			
		}
		if(MsgType.equals("event")){
			if(Event.equals("subscribe")){
				ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text",
						"老了老弟！为了与您的用户信息匹配来给您发放奖励和订单物流推送，请先绑定与小程序一致的手机号。回复绑定＋手机号获取验证码，例：绑定13788889999（勿加空格和额外字符）");
				return re.Msg2Xml();
			}
			if(Event.equals("CLICK")){
				if(map.get("EventKey").equals("我要绑定")){
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text",
							"来老了老弟！为了与您的用户信息匹配来给您发放奖励和订单物流推送，请先绑定与小程序一致的手机号。回复绑定＋手机号获取验证码，例：绑定13788889999（勿加空格和额外字符）");
					return re.Msg2Xml();
				}
				if(map.get("EventKey").equals("商家APP")){
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text",
							"安卓版本：www.chuyinkeji.cn/yzxy/new.apk        苹果版本：https://itunes.apple.com/cn/app/id1457549508?mt=8");
					return re.Msg2Xml();
				}
				if(map.get("EventKey").equals("商务合作")){
					ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text",
							"请加客服qq：2858385406");
					return re.Msg2Xml();
				}
			}
			if(Event.equals("unsubscribe")){
				if(user.getPhone()!=null){
					
				}
			}
		}
		ReplyTextMsg re=new ReplyTextMsg(ToUserName,FromUserName,"text","格式不正确");
		return re.Msg2Xml();
	}
	
	public WxUser checkopenId(String openId){
			 WxUser user=wxUserService.login(openId,0,0,"微信公众号");
			 return user;
	}
	
	
	
	@GetMapping("version")
	@ApiOperation(value="获取版本号",httpMethod="POST")
	public String version(){
		return stringRedisTemplate.opsForValue().get("min_version");
	}
	
	@GetMapping("setversion")
	@ApiOperation(value="设置版本号",httpMethod="POST")
	public String setversion(String version){
		stringRedisTemplate.opsForValue().set("min_version",version);
		return "ok";
	}
	
	@GetMapping("appversion")
	@ApiOperation(value="获取app版本号",httpMethod="POST")
	public ResponseObject appversion(){
		String version=stringRedisTemplate.opsForValue().get("app_version");
		return new ResponseObject(true, "")
				.push("version", version)
				.push("androidUrl", "http://www.chuyinkeji.cn/yzxy/new.apk")
				.push("iosUrl", "https://itunes.apple.com/cn/app/id1457549508?mt=8");
	}
	
	@GetMapping("setappversion")
	@ApiOperation(value="设置app版本号",httpMethod="POST")
	public String setappversion(String version){
		stringRedisTemplate.opsForValue().set("app_version",version);
		return "ok";
	}
	
}
