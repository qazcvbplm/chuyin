package com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.SignMapper;
import com.entity.Sign;
import com.service.WxUserService;
import com.util.ResponseObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="签到模块")
@RequestMapping("ops/sign")
public class SignController {

	@Autowired
	private SignMapper signMapper;
	@Autowired
	private WxUserService wxUserService;
	
	public static int day;
	
	static{
		day=Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}
	
	@ApiOperation(value="签到",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@RequestParam String openId){
		              Sign sign=signMapper.findLast(openId);
		              if(sign==null){
		            	  sign=new Sign(openId, day, 3, 1);
		              }else{
		            	  if(sign.getDay()==day){
		            		  return new ResponseObject(true, "今日已签过");
		            	  }else{
		            		  if(sign.getDay()!=day-1){
		            			  //不是连续签到
		            			  sign=new Sign(openId, day, 3, 1);
		            		  }else{
		            			  //连续签到
		            			  int index =sign.getIndexs()+1;
		            			  sign=new Sign(openId, day,index*3, index);
		            		  }
		            	  }
		              }
		              signMapper.insert(sign);
		              int i=wxUserService.addSource(openId,sign.getSource());
		              return new ResponseObject(true, "成功获得"+sign.getSource()+"积分");
	}
}
