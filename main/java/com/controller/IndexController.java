package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.message.RedisUtil;
import com.util.ResponseObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="后台")
@RequestMapping("ops/admin")
public class IndexController {
	
	@Autowired
	  private RedisUtil cache;

	
	@ApiOperation(value="后台首页",httpMethod="POST")
	@PostMapping("index")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,int  schoolId){
		              return new ResponseObject(true, "ok").push("msg", cache.get(schoolId));
	}
}
