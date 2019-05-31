package com.serviceimple;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.config.RedisConfig;
import com.dao.OrdersCompleteMapper;
import com.dao.OrdersMapper;
import com.dao.RunOrdersMapper;
import com.dao.SchoolMapper;
import com.dao.SenderMapper;
import com.dao.ShopMapper;
import com.dao.TxLogMapper;
import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.dto.SenderTj;
import com.entity.Orders;
import com.entity.OrdersComplete;
import com.entity.RunOrders;
import com.entity.School;
import com.entity.Sender;
import com.entity.TxLog;
import com.entity.WxUser;
import com.entity.WxUserBell;
import com.redis.message.RedisUtil;
import com.service.SenderService;
import com.util.LoggerUtil;
import com.wx.towallet.WeChatPayUtil;
import com.wxutil.WxGUtil;

@Service
public class SenderServiceImple implements SenderService {

	@Autowired
	private SenderMapper senderMapper;
	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private RunOrdersMapper runordersMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private TxLogMapper txLogMapper;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private OrdersCompleteMapper ocm;
	@Autowired
	private RedisUtil cache;

	@Override
	public void add(Sender sender) {
		if (senderMapper.check(sender.getOpenId()) == null) {
			senderMapper.insert(sender);
		} else {
			throw new RuntimeException("请勿重复申请");
		}
	}

	@Override
	public List<Sender> find(Sender sender) {
		switch (sender.getQueryType()) {
		case "wxuser":
			sender.setOpenId(sender.getQuery());
			break;
		case "school":
			sender.setSchoolId(Integer.valueOf(sender.getQuery()));
			break;
		case "admin":
			break;
		}
		return senderMapper.find(sender);
	}

	@Override
	public int update(Sender sender) {
		return senderMapper.updateByPrimaryKeySelective(sender);
	}

	@Override
	public Sender findByPhone(String phone) {
		Sender query = new Sender();
		query.setPhone(phone);
		List<Sender> list = senderMapper.find(query);
		if (list.size() > 0) {
			/* if(list.get(0).getExam().equals("审核通过")) */
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Orders> findorderbydjs(int senderId, int page, int size, String status) {
		Sender sender = senderMapper.selectByPrimaryKey(senderId);
		sender.setPage(page);
		sender.setSize(size);
		sender.setOrderBy(status);
		if (sender.getTakeoutFlag() == 1) {
			return ordersMapper.findBySenderTakeout(sender);
		} else {
			return null;
		}
	}

	@Override
	public int acceptOrder(int senderId, String orderId) {
		Sender sender = senderMapper.selectByPrimaryKey(senderId);
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		orders.setSenderId(senderId);
		orders.setSenderName(sender.getName());
		orders.setSenderPhone(sender.getPhone());
		int rs=0;
		if((rs=ordersMapper.SenderAccept(orders))==1){
			WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
			School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
			WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
			if(wxGUser!=null){
				Map<String, String> mb = new HashMap<>();
				mb.put("touser", wxGUser.getOpenId());
				mb.put("template_id", "dVHcAp-Bc2ATpgYe09-5D7n50hjLshju8Zl6GGoyB7M");
				mb.put("data_first", "您的订单已被配送员接手！");
				mb.put("data_keyword1", sender.getName());
				mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				mb.put("data_remark", " 配送员正火速配送中，请耐心等待！");
				mb.put("min_appid", school.getWxAppId());
	       		mb.put("min_path", "pages/order/orderDetail/orderDetail?orderId=" 
				+ orders.getId() + "&typ=" + orders.getTyp());
				WxGUtil.snedM(mb);
			}
		}
		return rs;
	}

	@Override
	public int sendergetorder(String orderId) {
		return ordersMapper.getorder(orderId);
	}

	@Transactional
	@Override
	public void end(String orderId, boolean end) {
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		Sender sender = senderMapper.selectByPrimaryKey(orders.getSenderId());
		WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
		if (end) {
			orders.setDestination(1);
			if (ordersMapper.end(orders) == 1) {
				BigDecimal senderGet = new BigDecimal(0);
				if (orders.getSenderId() != 0) {
					senderGet = orders.getSendPrice().multiply(new BigDecimal(1).subtract(sender.getRate()));
					stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
							"addmoney" + "," + sender.getOpenId() + "," + senderGet.toString());
				}
				stringRedisTemplate.convertAndSend(RedisConfig.SCHOOLBELL, "addmoney" + "," + orders.getSchoolId() + ","
						+ orders.getPayPrice().subtract(senderGet).toString() + "," + senderGet.toString());
				// 增加积分
				stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
						"addsource" + "," + orders.getOpenId() + "," + orders.getPayPrice().toString());
				
			} else {
				return;
			}
		} else {
			orders.setDestination(0);
			BigDecimal returnPrice;
			if (orders.getSendPrice().compareTo(orders.getSchoolTopDownPrice()) == -1) {
				orders.setPayPrice(orders.getPayPrice().subtract(orders.getSendPrice()));
				orders.setSendPrice(new BigDecimal(0));
				returnPrice = orders.getSendPrice();
			} else {
				orders.setPayPrice(orders.getPayPrice().subtract(orders.getSchoolTopDownPrice()));
				orders.setSendPrice(orders.getSendPrice().subtract(orders.getSchoolTopDownPrice()));
				returnPrice = orders.getSchoolTopDownPrice();
			}
				Map<String, Object> map = new HashMap<>();
				map.put("phone", wxUser.getOpenId() + "-" + wxUser.getPhone());
				map.put("amount", returnPrice);
				if (ordersMapper.end(orders) == 1) {
					if (wxUserBellMapper.charge(map) == 1) {
						BigDecimal senderGet = orders.getSendPrice().multiply(new BigDecimal(1).subtract(sender.getRate()));
						stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
								"addmoney" + "," + sender.getOpenId() + "," + senderGet.toString());
						stringRedisTemplate.convertAndSend(RedisConfig.SCHOOLBELL, "addmoney" + "," + orders.getSchoolId()
						+ "," + orders.getPayPrice().subtract(senderGet).toString() + "," + senderGet.toString());
						// 增加积分
						stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
								"addsource" + "," + orders.getOpenId() + "," + orders.getPayPrice().toString());
					}
				}
			else {
				return;
			}
		}
		// 对订单进行结算
		OrdersComplete oc = new OrdersComplete(orders, schoolMapper.selectByPrimaryKey(orders.getSchoolId()),
				shopMapper.selectByPrimaryKey(orders.getShopId()), sender);
		ocm.insert(oc);
		cache.takeoutCountSuccessadd(orders.getSchoolId());
		stringRedisTemplate.convertAndSend(RedisConfig.PRODUCTADD, orderId);
		if(orders.getTyp().equals("外卖订单")){
			WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
			School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
			if(wxGUser!=null){
				Map<String, String> mb = new HashMap<>();
				mb.put("touser", wxGUser.getOpenId());
				mb.put("template_id", "8Qy7KQRt2upGjwmhp7yYaR2ycfKkXNI8gqRvGBnovsk");
				if(orders.getDestination()==1){
					mb.put("data_first", "您的订单已送达到寝。");
				}else{
					mb.put("data_first", "您的订单已送达楼下，请下楼自取。系统已返还"+orders.getSchoolTopDownPrice()+"元至您钱包余额内，请注意查收！");
				}
				mb.put("data_keyword1", orderId);
				mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				mb.put("data_remark", "成功获得"+orders.getPayPrice().intValue()+"积分，可以前往积分商城兑换哟！");
				mb.put("min_appid", school.getWxAppId());
				mb.put("min_path", "pages/mine/integral/integral");
				WxGUtil.snedM(mb);
			}
		}
	}

	@Override
	public void endRun(String orderId) {
		if (runordersMapper.end(orderId) == 1) {
			RunOrders orders = runordersMapper.selectByPrimaryKey(orderId);
			Sender sender = senderMapper.selectByPrimaryKey(orders.getSenderId());
			BigDecimal senderGet = orders.getTotalPrice().multiply(new BigDecimal(1).subtract(sender.getRate()));
			stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
					"addmoney" + "," + sender.getOpenId() + "," + senderGet.toString());
			stringRedisTemplate.convertAndSend(RedisConfig.SCHOOLBELL, "addmoney" + "," + sender.getSchoolId() + ","
					+ orders.getTotalPrice().subtract(senderGet).toString() + "," + senderGet.toString());
			// senderAddMoney(orders.getTotalPrice(),orders.getSenderId());
			// 增加积分
			// addsource(orders.getOpenId(), orders.getTotalPrice().intValue());
			stringRedisTemplate.convertAndSend(RedisConfig.SENDERBELL,
					"addsource" + "," + orders.getOpenId() + "," + orders.getTotalPrice().toString());
			cache.runCountSuccessadd(orders.getSchoolId());
			WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
			School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
			WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
			if(wxGUser!=null){
				Map<String, String> mb = new HashMap<>();
				mb.put("touser", wxGUser.getOpenId());
				mb.put("template_id", "8Qy7KQRt2upGjwmhp7yYaR2ycfKkXNI8gqRvGBnovsk");
				mb.put("data_first", "您的跑腿订单已经完成!");
				mb.put("data_keyword1", orders.getId());
				mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				mb.put("data_remark", "成功获得"+orders.getTotalPrice().intValue()+"积分，可以前往积分商城兑换哟！");
				mb.put("min_appid", school.getWxAppId());
	       		mb.put("min_path", "pages/mine/integral/integral");
				WxGUtil.snedM(mb);
			}
		}
	}

	/*
	 * public void addsource(String openid,int source){ //增加积分 WxUser
	 * wxUser=wxUserMapper.selectByPrimaryKey(openid); Map<String,Object>
	 * map2=new HashMap<>(); map2.put("phone",
	 * wxUser.getOpenId()+"-"+wxUser.getPhone()); map2.put("source", source);
	 * wxUserBellMapper.addSource(map2); }
	 */

	public void senderAddMoney(BigDecimal amount, Integer senderId) {
		if (senderId == 0) {
			return;
		}
		Sender sender = senderMapper.selectByPrimaryKey(senderId);
		amount = amount.multiply(new BigDecimal(1).subtract(sender.getRate())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		WxUser wxUser = wxUserMapper.selectByPrimaryKey(sender.getOpenId());
		Map<String, Object> map = new HashMap<>();
		map.put("phone", wxUser.getOpenId() + "-" + wxUser.getPhone());
		map.put("amount", amount);
		if (wxUserBellMapper.charge(map) == 0) {
			LoggerUtil.log("配送员送达订单增加余额失败：" + amount.toString());
		}
	}

	@Override
	public List<RunOrders> findorderbyrundjs(int senderId, int page, int size, String status) {
		Sender sender = senderMapper.selectByPrimaryKey(senderId);
		sender.setPage(page);
		sender.setSize(size);
		sender.setOrderBy(status);
		if (sender.getRunFlag() == 1) {
			return runordersMapper.findBySenderRun(sender);
		} else {
			return null;
		}
	}

	@Override
	public int acceptOrderRun(int senderId, String orderId) {
		Sender sender = senderMapper.selectByPrimaryKey(senderId);
		RunOrders orders = runordersMapper.selectByPrimaryKey(orderId);
		orders.setSenderId(senderId);
		orders.setSenderName(sender.getName());
		orders.setSenderPhone(sender.getPhone());
		int rs=0;
		if((rs=runordersMapper.SenderAccept(orders))==1){
			WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
			School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
			WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
			if(wxGUser!=null){
				Map<String, String> mb = new HashMap<>();
				mb.put("touser", wxGUser.getOpenId());
				mb.put("template_id", "dVHcAp-Bc2ATpgYe09-5D7n50hjLshju8Zl6GGoyB7M");
				mb.put("data_first", "您的订单已被配送员接手！");
				mb.put("data_keyword1", sender.getName());
				mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				mb.put("data_remark", " 配送员正火速配送中，请耐心等待！");
				mb.put("min_appid", school.getWxAppId());
	       		mb.put("min_path", "pages/order/orderDetail/orderDetail?orderId=" 
				+ orders.getId() + "&typ=" + orders.getTyp());
				WxGUtil.snedM(mb);
			}
		}
		return rs;
	}

	@Override
	public SenderTj statistics(int senderId, String beginTime, String endTime) {
		SenderTj rs = new SenderTj();
		Map<String, Object> map = new HashMap<>();
		map.put("senderId", senderId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<Orders> result = ordersMapper.senderStatistics(map);
		List<RunOrders> result2 = runordersMapper.senderStatistics(map);
		for (Orders temp : result) {
			if (temp.getStatus().equals("配送员已接手")) {
				rs.setTakeoutNosuccess(temp.getFloorId());
			}
			if (temp.getStatus().equals("已完成")) {
				rs.setTakeoutSuccess(temp.getFloorId());
			}
			if (temp.getSendPrice() != null)
				rs.setTakeout_Price(rs.getTakeout_Price().add(temp.getSendPrice()));
		}
		for (RunOrders temp : result2) {
			if (temp.getStatus().equals("配送员已接手")) {
				rs.setRunNosuccess(temp.getFloorId());
			}
			if (temp.getStatus().equals("已完成")) {
				rs.setRunSuccess(temp.getFloorId());
			}
			if (temp.getTotalPrice() != null)
				rs.setRun_price(rs.getRun_price().add(temp.getTotalPrice()));
		}
		return rs;
	}

	@Override
	public int count(Sender sender) {
		return senderMapper.count(sender);
	}

	@Transactional
	public int tx(String senderId, BigDecimal amount) {
		Sender sender = senderMapper.check(senderId);
		WxUser query = new WxUser();
		query.setSchoolId(sender.getSchoolId());
		query.setPhone(sender.getPhone());
		WxUser user = wxUserMapper.findByschoolAndPhone(query);
		School school = schoolMapper.selectByPrimaryKey(sender.getSchoolId());
		if (user == null) {
			throw new RuntimeException("提现失败，请返回" + school.getName() + "绑定该手机号码");
		}
		Map<String, Object> map = new HashMap();
		map.put("phone", senderId + "-" + sender.getPhone());
		map.put("amount", amount);
		map.put("schoolId", sender.getSchoolId());
		String payId = "tx" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		if (this.wxUserBellMapper.pay(map) == 1) {
			try {
				TxLog log = new TxLog(sender.getId(), "配送员提现", null, amount, "", sender.getSchoolId(), user.getAppId());
				if (WeChatPayUtil.transfers(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
						school.getCertPath(), payId, "127.0.0.1", amount, user.getOpenId(), log) == 1) {
					txLogMapper.insert(log);
					if (schoolMapper.sendertx(map) == 0) {
						LoggerUtil.log("配送员提现学校减少金额失败:" + senderId + ":" + amount);
					}
					return 1;
				}
			} catch (Exception e) {
				LoggerUtil.log(senderId + ":" + amount + e.getMessage());
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 2;
		}
		throw new RuntimeException("余额不足");
	}

	@Override
	public int finddsh(int schoolId) {
		return senderMapper.finddsh(schoolId);
	}

	@Override
	public int tx2(String senderId, String userId) {
		Sender sender = senderMapper.check(senderId);
		WxUser wxUser = wxUserMapper.selectByPrimaryKey(userId);
		WxUser senderUser = wxUserMapper.selectByPrimaryKey(senderId);
		School school = schoolMapper.selectByPrimaryKey(sender.getSchoolId());
		WxUserBell wxUserBell = wxUserBellMapper
				.selectByPrimaryKey(senderUser.getOpenId() + "-" + senderUser.getPhone());
		Map<String, Object> map = new HashMap();
		map.put("phone", senderId + "-" + sender.getPhone());
		map.put("amount", wxUserBell.getMoney());
		map.put("schoolId", sender.getSchoolId());
		if (this.wxUserBellMapper.pay(map) == 1) {
			try {
				String payId = "tx" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				TxLog log = new TxLog(sender.getId(), "配送员提现", null, wxUserBell.getMoney(), "", sender.getSchoolId(),
						wxUser.getAppId());
				if (WeChatPayUtil.transfers(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
						school.getCertPath(), payId, "127.0.0.1", wxUserBell.getMoney(), wxUser.getOpenId(),
						log) == 1) {
					txLogMapper.insert(log);
					if (schoolMapper.sendertx(map) == 0) {
						LoggerUtil.log("配送员提现学校减少金额失败:" + senderId + ":" + wxUserBell.getMoney());
					}
					return 1;
				}
			} catch (Exception e) {
				LoggerUtil.log(senderId + ":" + wxUserBell.getMoney() + e.getMessage());
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 2;
		}
		throw new RuntimeException("余额不足");
	}

}
