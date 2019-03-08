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

import com.entity.Floor;
import com.service.FloorService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="楼栋模块")
@RequestMapping("ops/floor")
public class FloorController {

	@Autowired
	private FloorService floorService;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid Floor floor,BindingResult result){
		              Util.checkParams(result);
		              floor.setSchoolId(Integer.valueOf(request.getAttribute("Id").toString()));
		              floorService.add(floor);
		              return new ResponseObject(true, "添加成功");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,Floor floor){
		              floor.setQuery(request.getAttribute("Id").toString());
		              floor.setQueryType(request.getAttribute("role").toString());
		              List<Floor> list=floorService.find(floor);
		              floor.setTotal(1);
		              return new ResponseObject(true, "ok").push("list", list).push("total",floorService.find(floor));
	}
	
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,Floor floor){
		              int i=floorService.update(floor);
		              return new ResponseObject(true, "更新"+i+"条记录");
	}
}
