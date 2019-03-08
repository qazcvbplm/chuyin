package com.redis.message;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.entity.Sender;
import com.entity.WxUser;
import com.util.LoggerUtil;

@Service
public class WxUserListener {

	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void receiveMessage(String message){
		 String[] params=message.split(",");
		  if(params[0].equals("addsource")){
			  addsource(params[1], new BigDecimal(params[2]).intValue());
		  }
		  if(params[0].equals("addmoney")){
			  senderAddMoney(new BigDecimal(params[2]),params[1]);
		  }
	}
	
	public void addsource(String openid,int source){
		 //增加积分
		    WxUser wxUser=wxUserMapper.selectByPrimaryKey(openid);
			Map<String,Object> map2=new HashMap<>();
	        map2.put("phone", wxUser.getOpenId()+"-"+wxUser.getPhone());
	        map2.put("source", source);
			if(wxUserBellMapper.addSource(map2)==0){
				LoggerUtil.log("用户增加积分失败："+wxUser.getOpenId()+"-"+wxUser.getPhone()+source);
			}
	}
	
	public void senderAddMoney(BigDecimal amount,String openId){
		amount=amount.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		 WxUser wxUser=wxUserMapper.selectByPrimaryKey(openId);
		 Map<String,Object> map=new HashMap<>();
         map.put("phone", wxUser.getOpenId()+"-"+wxUser.getPhone());
         map.put("amount", amount);
         if(wxUserBellMapper.charge(map)==0){
       	   LoggerUtil.log("配送员送达订单增加余额失败："+wxUser.getOpenId()+"-"+wxUser.getPhone()+amount.toString());
         }
	}
	
}
