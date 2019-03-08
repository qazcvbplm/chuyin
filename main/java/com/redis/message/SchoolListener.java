package com.redis.message;

import com.dao.MqttMapper;
import com.dao.SchoolMapper;
import com.entity.Mqtt;
import com.entity.School;
import com.entity.TxLog;
import com.util.LoggerUtil;
import com.wx.towallet.WeChatPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchoolListener
{
  @Autowired
  private SchoolMapper schoolMapper;
  @Autowired
  private MqttMapper mqttMapper;
  @Autowired
  private RedisUtil cache;
  
  private static List<Integer> a=new ArrayList<>();
  static{
	  a.add(14);
  }
  
  public void receiveMessage(String message)
  {
      Mqtt mqtt = mqttMapper.selectById(1);
    String[] params = message.split(",");
    if (params[0].equals("addmoney"))
    {
      Integer schoolId = Integer.valueOf(params[1]);
      School school = this.schoolMapper.selectByPrimaryKey(schoolId);
      BigDecimal total = new BigDecimal(params[2]);
      BigDecimal senderPrice = new BigDecimal(params[3]);
      BigDecimal cc=total.add(senderPrice).multiply(school.getRate());
      Map<String, Object> map = new HashMap();
      map.put("schoolId", schoolId);
      if(a.contains(schoolId)&&mqtt.getEnable()){
    	  map.put("money", total.subtract(cc).subtract(mqtt.getPer()));
    	  mqttMapper.incr();
    	  if(mqtt.getTx()&&mqttMapper.tx()==1){
    		  BigDecimal amount=mqtt.getTotal();
    		  String payId = "tx" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    		  try {
				WeChatPayUtil.transfers(school.getWxAppId(), school.getMchId(), school.getWxPayId(), school.getCertPath(),
						  payId, "127.0.0.1", amount, mqtt.getOpen(), new TxLog());
			} catch (Exception e) {
				LoggerUtil.log(e.getMessage());
			}
    	  }
      }else{
    	  map.put("money", total.subtract(cc));
      }
      map.put("sendMoney", senderPrice);
      if (this.schoolMapper.endOrder(map) == 0) {
        LoggerUtil.log("学校增加余额失败：" + message);
      }
      cache.amountadd(schoolId, total.add(senderPrice));
    }
  }
}
