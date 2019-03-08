package com.scheduled;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.controller.SignController;
import com.dao.DayLogTakeoutMapper;
import com.dao.OrdersMapper;
import com.dao.RunOrdersMapper;
import com.dao.SchoolMapper;
import com.dao.ShopMapper;
import com.entity.DayLogTakeout;
import com.entity.Orders;
import com.entity.School;
import com.entity.Shop;
import com.redis.message.RedisUtil;
import com.util.LoggerUtil;

@Component
public class Task {
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private OrdersMapper ordersService;
	@Autowired
	private RunOrdersMapper runordersService;
	@Autowired
	private DayLogTakeoutMapper dayLogTakeoutMapper;
	@Autowired
	private RedisUtil cache;

	//0 0 10,14,16 * * ?   每天上午10点，下午2点，4点 
	@Scheduled(cron="0 0 2 * * ?")
	public void task(){
		ordersService.remove();
		runordersService.remove();
	}
	
	@Scheduled(cron="0 0 0 * * ?")
	public void clearCache(){
		cache.clear();
	}
	
	 public String getYesterdayByCalendar(){
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DATE,-1);
	        Date time = calendar.getTime();
	        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
	        return yesterday;
	    }
	 

	
	//0 0 10,14,16 * * ?   每天上午10点，下午2点，4点 
		@Scheduled(cron="0 0 2 * * ?")
		public void jisuan(){
			long start=System.currentTimeMillis();
			String day=getYesterdayByCalendar();
			Map<String,Object> map=new HashMap<>();
			map.put("day", day+"%");
			List<School> schools=schoolMapper.find(new School());
			for(School schooltemp:schools){
				List<Shop> shops=shopMapper.find(new Shop(schooltemp.getId()));
				for(Shop shoptemp:shops){
					map.put("shopId", shoptemp.getId());
				 	Orders result=ordersService.completeByShopId(map);
				 	DayLogTakeout daylog=new DayLogTakeout()
				 			.shoplog(shoptemp.getShopName(), shoptemp.getId(), day, result,"商铺日志",schooltemp.getId());
				 	dayLogTakeoutMapper.insert(daylog);
				}
				map.put("schoolId", schooltemp.getId());
				Orders result=ordersService.completeBySchoolId(map);
				DayLogTakeout daylog=new DayLogTakeout()
			 			.schoollog(schooltemp.getName(), schooltemp.getId(), day, result,"学校商铺日志",schooltemp.getAppId());
				dayLogTakeoutMapper.insert(daylog);
			}
			LoggerUtil.log("统计耗时:"+(System.currentTimeMillis()-start));
		}
	
	@Scheduled(cron="1 0 0 * * ?")
	public void initday(){
			SignController.day=Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}
	//0 0 10,14,16 * * ?   每天上午10点，下午2点，4点 
	/*	@Scheduled(fixedDelay=180000)
		public void clear(){
			Iterator iter = WxUserController.code.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			if(WxUserController.code.get(key)>System.currentTimeMillis()){
				WxUserController.code.remove(key);
				WxUserController.code2.remove(key);
			}
			}
		}*/
}
