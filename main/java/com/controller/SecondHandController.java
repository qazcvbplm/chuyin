package com.controller;

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

import com.entity.Address;
import com.entity.SecondHand;
import com.service.SecondHandService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="二手模块")
@RequestMapping("ops/secondhand")
public class SecondHandController {

	@Autowired
	private SecondHandService secondHandService;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid SecondHand secondHand,BindingResult result){
		              Util.checkParams(result);
		              secondHandService.add(secondHand);
		              return new ResponseObject(true, "添加成功");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,SecondHand secondHand){
		              List<SecondHand> list = secondHandService.find(secondHand);
		              return new ResponseObject(true, "ok").push("total", secondHandService.count(secondHand)).push("list", list);
	}
	
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,SecondHand secondHand){
		  int i= secondHandService.update(secondHand);
          return new ResponseObject(true, "更新"+i+"条记录").push("result", i);
	}
}
