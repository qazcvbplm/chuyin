package com.redis.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.dao.OrdersMapper;
import com.entity.Orders;
import com.util.LoggerUtil;
@Component
public class KeyOutTimeListener extends KeyExpirationEventMessageListener{

	public KeyOutTimeListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Override
	public void onMessage(Message key, byte[] arg1) {
		if(key.toString().startsWith("tsout")){
			Orders orders=ordersMapper.selectByPrimaryKey(key.toString().split(",")[1]);
			 orders.setDestination(1);
			   if(ordersMapper.end(orders)==1){
					 //增加积分
					stringRedisTemplate.convertAndSend("bell", "addsource"+","+orders.getOpenId()+","+orders.getPayPrice().toString());
			   }else{
				   LoggerUtil.log("堂食完成失败:"+key.toString());
			   }
		}
	}

}
