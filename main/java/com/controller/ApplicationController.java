package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(tags="总体信息模块")
@RequestMapping("ops/application")
public class ApplicationController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
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
	
}
