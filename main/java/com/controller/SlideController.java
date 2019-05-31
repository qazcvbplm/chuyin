package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dao.SlideMapper;
import com.entity.Slide;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="轮播图模块")
@RequestMapping("ops/slide")
public class SlideController {

	@Autowired
	private SlideMapper slideMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid Slide slide,BindingResult result){
		              Util.checkParams(result);
		              if(Boolean.parseBoolean(stringRedisTemplate.opsForValue().get("cache"))){
		            	  stringRedisTemplate.boundHashOps("SLIDE_LIST").delete(slide.getSchoolId()+"");
		              }
		              slideMapper.insert(slide);
		              return new ResponseObject(true, "添加成功");
	}
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,int schoolId){
						List<Slide> list=null;
						if(Boolean.parseBoolean(stringRedisTemplate.opsForValue().get("cache"))){
							if(stringRedisTemplate.boundHashOps("SLIDE_LIST").get(schoolId+"")!=null){
								 list=JSON.parseArray(stringRedisTemplate.boundHashOps("SLIDE_LIST").get(schoolId+"").toString(), Slide.class);
							}else{
								list = slideMapper.find(schoolId);
								stringRedisTemplate.boundHashOps("SLIDE_LIST").put(schoolId+"", JSON.toJSONString(list));
							}
						}
		              return new ResponseObject(true, "ok").push("list", list);
	}
	
	@ApiOperation(value="更新",httpMethod="POST")
	@PostMapping("update")
	public ResponseObject update(HttpServletRequest request,HttpServletResponse response,Slide slide){
		              int i= slideMapper.updateByPrimaryKeySelective(slide);
		              if(Boolean.parseBoolean(stringRedisTemplate.opsForValue().get("cache"))){
		            	  Slide temp=slideMapper.findById(slide.getId());
		            	  stringRedisTemplate.boundHashOps("SLIDE_LIST").delete(temp.getSchoolId()+"");
		              }
		              return new ResponseObject(true, "更新"+i+"条记录").push("result", i);
	}
}
