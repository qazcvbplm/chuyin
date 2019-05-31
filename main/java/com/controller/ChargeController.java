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

import com.dao.ChargeLogMapper;
import com.dao.ChargeMapper;
import com.entity.Charge;
import com.entity.ChargeLog;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="充值项模块")
@RequestMapping("ops/charge")
public class ChargeController {

	@Autowired
	private ChargeMapper chargeMapper;
	@Autowired
	private ChargeLogMapper chargeLogMapper;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid Charge charge,BindingResult result){
		              Util.checkParams(result);
		              chargeMapper.insert(charge);
		              return new ResponseObject(true, "添加成功");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response){
		              List<Charge> list=chargeMapper.find();
		              return new ResponseObject(true, "ok").push("list", list);
	}
	
	@ApiOperation(value="删除",httpMethod="POST")
	@PostMapping("remove")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,int id){
		              int i=chargeMapper.remove(id);
		              return new ResponseObject(true, "移除"+i+"条记录");
	}
	
	/*@ApiOperation(value="按照appid统计",httpMethod="POST")
	@PostMapping("tj")
	public ResponseObject tj(HttpServletRequest request,HttpServletResponse response,int appId){
		              ChargeLog rs=chargeLogMapper.tj(appId);
		              return new ResponseObject(true, "ok").push("charge", rs.getPay()).push("send", rs.getSend()).push("surplus",chargeLogMapper.surplus(appId));
	}*/
	
	
	
}
