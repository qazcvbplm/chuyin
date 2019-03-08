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

import com.entity.ShopCategory;
import com.service.ShopCategoryService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="店铺分类模块")
@RequestMapping("ops/shopcategory")
public class ShopCategoryController {
	@Autowired
	private ShopCategoryService shopCategoryService;

	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid ShopCategory shopCategory,BindingResult result){
		              Util.checkParams(result);
		              shopCategory.setSchoolId(Integer.valueOf(request.getAttribute("Id").toString()));
		              shopCategoryService.add(shopCategory);
		              return new ResponseObject(true, "添加成功");
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,ShopCategory shopCategory){
		              List<ShopCategory> list = shopCategoryService.find(shopCategory);
		              return new ResponseObject(true, "ok").push("list", list);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,ShopCategory shopCategory){
		              int r = shopCategoryService.update(shopCategory);
		              return new ResponseObject(true, "更新"+r+"条记录");
	}
}
