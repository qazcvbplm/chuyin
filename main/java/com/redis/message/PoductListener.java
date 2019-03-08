package com.redis.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ProductMapper;
import com.entity.OrderProduct;
import com.entity.Orders;
import com.service.OrdersService;

@Service
public class PoductListener {

	@Autowired
	private OrdersService orderService;
	@Autowired
	private ProductMapper productMapper;
	
	 public void receiveMessage(String message){
		 Orders orders=orderService.findById(message);
		 List<OrderProduct> list=orders.getOp();
		 for(OrderProduct temp:list){
			 Map<String,Object> map=new HashMap<>();
			 map.put("id", temp.getProductId());
			 map.put("count", temp.getProductCount());
			  productMapper.sale(map);
		 }
	  }
}
