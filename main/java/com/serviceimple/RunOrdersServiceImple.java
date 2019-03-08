package com.serviceimple;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ApplicationMapper;
import com.dao.RunOrdersMapper;
import com.dao.SchoolMapper;
import com.dao.ShopMapper;
import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.entity.Application;
import com.entity.Orders;
import com.entity.RunOrders;
import com.entity.School;
import com.entity.Shop;
import com.entity.WxUser;
import com.redis.message.RedisUtil;
import com.service.RunOrdersService;
import com.wx.refund.RefundUtil;
import com.wxutil.AmountUtils;

@Service
public class RunOrdersServiceImple implements RunOrdersService{
	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private RunOrdersMapper runOrdersMapper;
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private RedisUtil cache;
	@Override
	public void add(@Valid RunOrders orders) {
		runOrdersMapper.insert(orders);
		
	}

	@Override
	public List<RunOrders> find(RunOrders orders) {
		return runOrdersMapper.find(orders);
	}

	@Override
	public int count(RunOrders orders) {
		return runOrdersMapper.count(orders);
	}

	@Override
	public RunOrders findById(String orderId) {
		return runOrdersMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int paySuccess(String orderId, String payment) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		map.put("payment", payment);
		map.put("payTimeLong", System.currentTimeMillis());
		RunOrders orders=findById(orderId);
		int rs=runOrdersMapper.paySuccess(map);
		if(rs==1){
			cache.runCountadd(orders.getSchoolId());
		}
		return rs;
	}

	@Override
	public int pay(RunOrders orders) {
		Application application =applicationMapper.selectByPrimaryKey(orders.getAppId());
		if(application.getVipRunDiscountFlag()==1){
			orders.setTotalPrice((orders.getTotalPrice().multiply(application.getVipRunDiscount())).setScale(2, BigDecimal.ROUND_HALF_DOWN));
		}
		Map<String,Object> map=new HashMap<>();
		WxUser user=wxUserMapper.selectByPrimaryKey(orders.getOpenId());
		map.put("phone",user.getOpenId()+"-"+user.getPhone());
		map.put("amount", orders.getTotalPrice());
		if(wxUserBellMapper.pay(map)==1){
			if(paySuccess( orders.getId(),"余额支付")==0){
				throw new RuntimeException("订单状态异常");
			}
			return 1;
		}else{
			throw new RuntimeException("余额不足");
		}
	}

	@Override
	public void remove() {
		 runOrdersMapper.remove();
	}
	@Transactional
	@Override
	public int cancel(String id) {
		RunOrders orders=runOrdersMapper.selectByPrimaryKey(id);
		if(System.currentTimeMillis()-orders.getPayTimeLong()<5*60*1000){
				return 2;
		}
		if(runOrdersMapper.cancel(id)==1){
			if(orders.getPayment().equals("微信支付")){
				School school=schoolMapper.selectByPrimaryKey(orders.getSchoolId());
				String fee=AmountUtils.changeY2F(orders.getTotalPrice().toString());
                int result=RefundUtil.wechatRefund1(school.getWxAppId(), school.getWxSecret(), school.getMchId(), school.getWxPayId(), school.getCertPath(),
                		orders.getId(), fee, fee); 
                if(result!=1){
                	throw new RuntimeException("退款失败联系管理员");
                }else{
                	return 1;
                }
			}
			if(orders.getPayment().equals("余额支付")){
				Map<String,Object> map=new HashMap<>();
				WxUser user=wxUserMapper.selectByPrimaryKey(orders.getOpenId());
				map.put("phone",user.getOpenId()+"-"+user.getPhone());
				map.put("amount", orders.getTotalPrice());
				if(wxUserBellMapper.charge(map)==1){
					return 1;
				}else{
					throw new RuntimeException("退款失败联系管理员");
				}
			}
		}
		return 0;
	}

	@Override
	public int countBySchoolId(int schoolId) {
		// TODO Auto-generated method stub
		return runOrdersMapper.countBySchoolId(schoolId);
	}

}
