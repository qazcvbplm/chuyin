package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ApplicationMapper;
import com.entity.Address;
import com.entity.Application;
import com.entity.SourceOrder;
import com.github.qcloudsms.httpclient.HTTPException;
import com.service.SourceOrderService;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="积分订单模块")
@RequestMapping("ops/source/order")
public class SourceOrderController {

	@Autowired
	private SourceOrderService sourceOrderService;
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@ApiOperation(value="积分兑换商品",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@RequestParam Integer id,
			@ModelAttribute @Valid SourceOrder sourceOrder,BindingResult result) throws JSONException, HTTPException, IOException{
		              Util.checkParams(result);
		              String oid=sourceOrderService.add(id,sourceOrder);
		              Application application=applicationMapper.selectByPrimaryKey(sourceOrder.getAppId());
		              Util.qqsms(1400169549, "0eb188f83ef4b2dc8976b5e76c70581e",application.getPhone(), 317887, sourceOrder.getProductName(), null);
		              return new ResponseObject(true, "兑换成功");
	}
	
	
	
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,SourceOrder sourceOrder){
		              List<SourceOrder> list = sourceOrderService.find(sourceOrder);
		              return new ResponseObject(true, "ok").push("total", sourceOrderService.count(sourceOrder)).push("list", list);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,SourceOrder sourceOrder){
		              int i = sourceOrderService.update(sourceOrder);
		              return new ResponseObject(true, "更新了"+i+"条记录");
	}
}
