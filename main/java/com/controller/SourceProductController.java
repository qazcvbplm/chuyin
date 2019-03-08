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
import com.entity.SourceProduct;
import com.service.SourceProductService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="积分商品模块")
@RequestMapping("ops/source/product")
public class SourceProductController {
	
	@Autowired
	private SourceProductService sourceProductService;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Valid SourceProduct sourceProduct,BindingResult result){
		              Util.checkParams(result);
		              sourceProductService.add(sourceProduct);
		              return new ResponseObject(true, "添加成功");
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,SourceProduct sourceProduct){
		              List<SourceProduct> list = sourceProductService.find(sourceProduct);
		              return new ResponseObject(true, "ok").push("list", list);
	}
	
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,SourceProduct sourceProduct){
		              int i= sourceProductService.update(sourceProduct);
		              return new ResponseObject(true, "更新"+i+"条记录").push("result", i);
	}
}
