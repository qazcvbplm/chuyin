package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.TxLogMapper;
import com.entity.TxLog;
import com.util.ResponseObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="提现记录模块")
@RequestMapping("ops/txlog")
public class TxLogController {

	@Autowired
	private TxLogMapper txLogMapper;
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(String appId,String schoolId,int page,int size){
		QueryWrapper<TxLog> query=new QueryWrapper<>();
		if(schoolId!=null)
		query.eq("school_id", schoolId);
		if(appId!=null)
		query.eq("app_id", appId);
		query.orderByDesc("create_time");
		IPage<TxLog> rs=txLogMapper.selectPage(new Page<>(page, size), query);
		return new ResponseObject(true, "ok").push("list", rs.getRecords()).push("total", rs.getTotal());
	}
}
