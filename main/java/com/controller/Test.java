package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dao.SenderMapper;
import com.entity.Orders;
import com.entity.Shop;
import com.service.ShopService;

@RestController
public class Test {
	
	@Autowired
	private StringRedisTemplate cache;
	
	@Autowired
	private SenderMapper senderMapper;
	
	@GetMapping("test")
	public void test(){
		
		System.out.println(senderMapper.selectByPrimaryKey(100).getName());
	}
}
