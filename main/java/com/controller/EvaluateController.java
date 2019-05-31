package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.EvaluateMapper;
import com.dao.OrdersMapper;
import com.dao.RunOrdersMapper;
import com.entity.Base;
import com.entity.Evaluate;
import com.util.ResponseObject;
import com.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="评论模块")
@RequestMapping("ops/evaluate")
public class EvaluateController {

	@Autowired
	private EvaluateMapper evaluateMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private RunOrdersMapper runOrdersMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@ApiOperation(value="添加",httpMethod="POST")
	@PostMapping("add")
	public ResponseObject add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid Evaluate evaluate,
			@RequestParam String userId,BindingResult result){
		              Util.checkParams(result);
		              if(ordersMapper.pl(evaluate.getOrderid())==1){
		            	  evaluateMapper.insert(evaluate);
		            	  stringRedisTemplate.convertAndSend("bell", "addsource"+","+userId+","+"3");
		              }
		              if(runOrdersMapper.pl(evaluate.getOrderid())==1){
		            	  evaluateMapper.insert(evaluate);
		            	  stringRedisTemplate.convertAndSend("bell", "addsource"+","+userId+","+"3");		
		            	  }
		              return new ResponseObject(true, "添加成功");
	}
	
	
	@ApiOperation(value="查询",httpMethod="POST")
	@PostMapping("find")
	public ResponseObject find(HttpServletRequest request,HttpServletResponse response,Evaluate evaluate){
		              List<Evaluate> list = evaluateMapper.find(evaluate);
		              return new ResponseObject(true, "ok").push("total", evaluateMapper.count(evaluate)).push("list", list);
	}

    @ApiOperation(value = "按店铺查询", httpMethod = "POST")
    @PostMapping("findbyshopid")
    public ResponseObject find(HttpServletRequest request, HttpServletResponse response, int shopId,Base base) {
    	Map<String, Object> map=new HashMap<String,Object>();
    	map.put("shopId", shopId);
    	map.put("page", base.getPage());
    	map.put("size", base.getSize());
        List<Evaluate> list = evaluateMapper.findByShopId(map);
        return new ResponseObject(true, "ok").push("list", list);
    }


}
