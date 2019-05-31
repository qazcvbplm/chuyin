package com.redis.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.dao.OrdersMapper;
import com.dao.WxUserMapper;
import com.entity.Orders;
import com.entity.WxUser;
import com.service.SenderService;
import com.util.LoggerUtil;
import com.wxutil.WxGUtil;
@Component
public class KeyOutTimeListener extends KeyExpirationEventMessageListener{

	public KeyOutTimeListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SenderService senderService;
	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private WxUserMapper wxUserMapper;
	
	@Override
	public void onMessage(Message key, byte[] arg1) {
		Orders orders=orderMapper.selectByPrimaryKey(key.toString().split(",")[1]);
		if(key.toString().startsWith("tsout")){
			   try {
				senderService.end(key.toString().split(",")[1],true);
				stringRedisTemplate.convertAndSend("bell", "addsource"+","+orders.getOpenId()+","+orders.getPayPrice().toString());
				WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
				WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
				if(wxGUser!=null){
					Map<String, String> mb = new HashMap<>();
					mb.put("touser", wxGUser.getOpenId());
					mb.put("template_id", "8Qy7KQRt2upGjwmhp7yYaR2ycfKkXNI8gqRvGBnovsk");
					mb.put("data_first", "您的"+orders.getTyp()+"已经自动完成!");
					mb.put("data_keyword1", orders.getId());
					mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					mb.put("data_remark", "成功获得"+orders.getPayPrice().intValue()+"积分，可以前往积分商城兑换哟！");
					WxGUtil.snedM(mb);
				}
			} catch (Exception e) {
				LoggerUtil.log("堂食完成失败:"+e.getMessage());
			}
					 //增加积分
		}
	}

}
