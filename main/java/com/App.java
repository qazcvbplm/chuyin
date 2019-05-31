package com;


import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.entity.Orders;
import com.service.OrdersService;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableScheduling
@MapperScan("com.dao")
public class App {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private OrdersService ordersService;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource druidDataSource() {
	   return new DruidDataSource();
	}
	 @Bean
	 public String ordersRedisConfig() {
		 
		List<Orders> orders= ordersService.findAllDjs();
		for(Orders temp:orders){
			stringRedisTemplate.boundHashOps("SHOP_DJS"+temp.getShopId()).put(temp.getId(), JSON.toJSONString(temp));;
		}
	        return "ok";
	 }

	
}
