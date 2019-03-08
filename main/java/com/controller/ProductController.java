package com.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Product;
import com.service.ProductService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="商品模块")
@RequestMapping("ops/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,BigDecimal[] attributePrice,
			String[] attributeName, @ModelAttribute @Valid Product product,BindingResult result){
		              Util.checkParams(result);
		              if(attributePrice.length>0){
		            	  if(attributePrice.length==attributeName.length){
		            		  productService.add(attributePrice,attributeName,product);
		            		  return new ResponseObject(true, "添加成功");
		            	  }
		              }
		              return new ResponseObject(false, "添加失败");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,int productCategoryId,Integer type){
		             List<Product> list;
		              if(request.getAttribute("role").toString().equals("wxuser")){
		            	  if(type==null)
		            	     list=productService.findByCategoryId_wxUser(productCategoryId);
		            	  else
		            		  list=productService.findByShopAllDiscount(productCategoryId);
		              }else{
		            	  list=productService.findByCategoryId(productCategoryId);
		              }
		             return new ResponseObject(true, "ok").push("list", list);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,Product product){
		             int i=productService.update(product);
		             return new ResponseObject(true, "更新了"+i+"条记录");
	}
	
	
	@ApiOperation(value="添加规格",httpMethod="POST")
	@PostMapping("adda")
	public ResponseObject adda(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int pid,@RequestParam BigDecimal attributePrice,
			@RequestParam String attributeName ){
		             int i=productService.adda(pid,attributePrice,attributeName );
		             return new ResponseObject(true, "ok");
	}
	
	@ApiOperation(value="删除规格",httpMethod="POST")
	@PostMapping("removea")
	public ResponseObject removea(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int id){
		             int i=productService.removea(id);
		             return new ResponseObject(true, "ok");
	}
	
}
