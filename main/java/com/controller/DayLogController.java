package com.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dao.DayLogTakeoutMapper;
import com.entity.DayLogTakeout;
import com.util.ResponseObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="店铺日营业额统计")
@RequestMapping("ops/daylogtakeout")
public class DayLogController {

	@Autowired
	private DayLogTakeoutMapper dayLogTakeoutMapper;
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,
			String selfId,String day,String type,Integer parentId,int page,int size){
		              QueryWrapper<DayLogTakeout> query=new QueryWrapper<>();
		              if(parentId!=null)
		              query.eq("parent_id", parentId);
		              if(selfId!=null)
			          query.eq("self_id", selfId);
		              if(type!=null)
			          query.eq("type", type);
		              if(day!=null)
				      query.eq("day", day);
		              query.orderByDesc("day");
		              IPage<DayLogTakeout> list=dayLogTakeoutMapper.selectPage(new Page<>(page, size), query);
		              return new ResponseObject(true, "ok").push("total", list.getTotal()).push("list", list.getRecords());
	}
}
