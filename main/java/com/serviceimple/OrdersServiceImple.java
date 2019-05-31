package com.serviceimple;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.config.RedisConfig;
import com.dao.ApplicationMapper;
import com.dao.FloorMapper;
import com.dao.FullCutMapper;
import com.dao.OrderProductMapper;
import com.dao.OrdersMapper;
import com.dao.ProductAttributeMapper;
import com.dao.ProductMapper;
import com.dao.SchoolMapper;
import com.dao.ShopMapper;
import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.entity.Application;
import com.entity.Floor;
import com.entity.OrderProduct;
import com.entity.Orders;
import com.entity.Product;
import com.entity.ProductAttribute;
import com.entity.School;
import com.entity.Shop;
import com.entity.WxUser;
import com.entity.WxUserBell;
import com.redis.message.RedisUtil;
import com.service.OrdersService;
import com.wx.refund.RefundUtil;
import com.wxutil.AmountUtils;
import com.wxutil.WxGUtil;

@Service
public class OrdersServiceImple implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderProductMapper orderProductMapper;
	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductAttributeMapper productAttributeMapper;
	@Autowired
	private FloorMapper floorMapper;
	@Autowired
	private FullCutMapper fullCutMapper;
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisUtil cache;

	@Transactional
	@Override
	public void addTakeout(Integer[] productIds, Integer[] attributeIndex, Integer[] counts, @Valid Orders orders) {
		WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
		School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
		Shop shop = shopMapper.selectByPrimaryKey(productMapper.selectByPrimaryKey(productIds[0]).getShopId());
		Floor floor = floorMapper.selectByPrimaryKey(orders.getFloorId());
		Product pt;
		ProductAttribute pa;
		int totalcount = 0;
		int boxcount = 0;
		boolean isDiscount = false;
		for (int i = 0; i < productIds.length; i++) {
			totalcount += counts[i];
			pt = productMapper.selectByPrimaryKey(productIds[i]);
			pa = productAttributeMapper.selectByPrimaryKey(attributeIndex[i]);
			OrderProduct op = new OrderProduct(productIds[i], pt.getProductName(), pt.getProductImage(), counts[i],
					pt.getDiscount(), orders.getId(), pa.getName(), pa.getPrice());
			orderProductMapper.insert(op);
			orders.setProductPrice(
					orders.getProductPrice().add(op.getAttributePrice().multiply(new BigDecimal(counts[i]))));
			// 查看餐盒费
			if (pt.getBoxPriceFlag() == 1) {
				boxcount += counts[i];
			}
			// 计算商品折扣
			if (pa.getIsDiscount() == 1) {
				isDiscount = true;
				orders.setDiscountType("商品折扣");
				BigDecimal DiscountPrice = (pa.getPrice().subtract(op.getAttributePrice()))
						.multiply(new BigDecimal(counts[i]));
				orders.setDiscountPrice(orders.getDiscountPrice().add(DiscountPrice));
			}
		}
		orders.takeoutinit1(wxUser, school, shop, floor, totalcount, isDiscount, fullCutMapper.findByShop(shop.getId()),
				boxcount);
		ordersMapper.insert(orders);
	}

	@Override
	public List<Orders> find(Orders orders) {
		return ordersMapper.find(orders);
	}

	@Override
	public int count(Orders orders) {
		return ordersMapper.count(orders);
	}

	@Override
	public Orders findById(String orderId) {
		return ordersMapper.selectByPrimaryKey(orderId);
	}

	@Transactional
	@Override
	public int paySuccess(String orderId, String payment) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("payment", payment);
		map.put("payTimeLong", System.currentTimeMillis());
		Orders orders = findById(orderId);
		int rs = ordersMapper.paySuccess(map);
		if (rs == 1) {
			cache.takeoutCountadd(orders.getSchoolId());
			String ordersStr=JSON.toJSONString(orders);
			orders.setStatus("待接手");
			stringRedisTemplate.boundHashOps("SHOP_DJS"+orders.getShopId()).put(orderId, JSON.toJSONString(orders));
			stringRedisTemplate.convertAndSend(RedisConfig.SOCKET, ordersStr);
		}
		return rs;
	}

	@Override
	public List<Orders> findByShopByDjs(int shopId) {
		return ordersMapper.findByShopByDjs(shopId);
	}

	@Transactional
	@Override
	public int shopAcceptOrderById(String orderId) {
		Orders orders = findById(orderId);
		synchronized (orders.getShopId()) {
			Orders update = new Orders();
			update.setShopId(orders.getShopId());
			update.setId(orderId);
			update.setPayTime(orders.getCreateTime().toString().substring(0, 10) + "%");
			synchronized (update.getShopId()) {
				int water = ordersMapper.waterNumber(update);
				update.setWaterNumber(water + 1);
			}
			if (ordersMapper.shopAcceptOrderById(update) == 1) {
				if (orders.getTyp().equals("堂食订单")||orders.getTyp().equals("自取订单")) {
					stringRedisTemplate.opsForValue().set("tsout," + orderId, "1",2, TimeUnit.HOURS);
				}
				WxUser wxUser = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
				School school = schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
				WxUser wxGUser = wxUserMapper.findGzh(wxUser.getPhone());
				if(wxGUser!=null){
					Map<String, String> mb = new HashMap<>();
					mb.put("touser", wxGUser.getOpenId());
					mb.put("template_id", "AFavOESyzBju1s8Wjete1SNVUvJr-YixgR67v6yMxpg");
					mb.put("data_first", "您的订单已被商家接手!");
					mb.put("data_keyword1", orderId);
					mb.put("data_keyword2", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					mb.put("data_remark", " 商家正火速给您备餐中，请耐心等待");
					mb.put("min_appid", school.getWxAppId());
		       		mb.put("min_path", "pages/order/orderDetail/orderDetail?orderId=" 
					+ orders.getId() + "&typ=" + orders.getTyp());
					WxGUtil.snedM(mb);
				}
				return orders.getShopId();
			}
			return 0;
		}
	}

	@Override
	public List<Orders> findByShop(int shopId, int page, int size) {
		Shop s = new Shop();
		s.setId(shopId);
		s.setPage(page);
		s.setSize(size);
		return ordersMapper.findByShop(s);
	}

	@Override
	public void remove() {
		ordersMapper.remove();
	}

	@Transactional
	@Override
	public int pay(Orders orders) {
		Shop shop = shopMapper.selectByPrimaryKey(orders.getShopId());
		School school = schoolMapper.selectByPrimaryKey(shop.getSchoolId());
		Application application = applicationMapper.selectByPrimaryKey(school.getAppId());
		if (application.getVipTakeoutDiscountFlag() == 1) {
			orders.setPayPrice((orders.getPayPrice().multiply(application.getVipTakeoutDiscount())).setScale(2,
					BigDecimal.ROUND_HALF_DOWN));
		}

		Map<String, Object> map = new HashMap<>();
		WxUser user = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
		map.put("phone", user.getOpenId() + "-" + user.getPhone());
		map.put("amount", orders.getPayPrice());
		if (wxUserBellMapper.pay(map) == 1) {
			if (paySuccess(orders.getId(), "余额支付") == 0) {
				throw new RuntimeException("订单状态异常");
			}
		  WxUser wxUser=wxUserMapper.selectByPrimaryKey(orders.getOpenId());
		  WxUserBell userbell= wxUserBellMapper.selectByPrimaryKey(wxUser.getOpenId()+"-"+wxUser.getPhone());
       	  WxUser wxGUser=wxUserMapper.findGzh(wxUser.getPhone());
       	  if(wxGUser!=null){
       		  Map<String,String> mb=new HashMap<>();
       		  mb.put("touser", wxGUser.getOpenId());
       		  mb.put("template_id", "JlaWQafk6M4M2FIh6s7kn30yPdy2Cd9k2qtG6o4SuDk");
       		  mb.put("data_first", " 您的会员帐户余额有变动！");
       		  mb.put("data_keyword1",  "暂无");
       		  mb.put("data_keyword2", "-"+orders.getPayPrice());
       		  mb.put("data_keyword3",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
       		  mb.put("data_keyword4",  "消费");
       		  mb.put("data_keyword5", userbell.getMoney()+"");
       		  mb.put("data_remark", "如有疑问请在小程序内联系客服人员！");
       		  mb.put("min_appid", school.getWxAppId());
       		  mb.put("min_path", "pages/mine/payment/payment");
       		  WxGUtil.snedM(mb);
       	  }
       //	stringRedisTemplate.boundListOps("shopDJS"+orders.getShopId()).rightPush(value);
			return 1;
		} else {
			throw new RuntimeException("余额不足");
		}
	}

	@Transactional
	@Override
	public int cancel(String id) {
		Orders orders = ordersMapper.selectByPrimaryKey(id);
		if (System.currentTimeMillis() - orders.getPayTimeLong() < 5 * 60 * 1000) {
			return 2;
		}
		if (ordersMapper.cancel(id) == 1) {
			if (orders.getPayment().equals("微信支付")) {
				School school = schoolMapper.selectByPrimaryKey(orders.getSchoolId());
				String fee = AmountUtils.changeY2F(orders.getPayPrice().toString());
				int result = RefundUtil.wechatRefund1(school.getWxAppId(), school.getWxSecret(), school.getMchId(),
						school.getWxPayId(), school.getCertPath(), orders.getId(), fee, fee);
				if (result != 1) {
					throw new RuntimeException("退款失败联系管理员");
				} else {
					return orders.getShopId();
				}
			}
			if (orders.getPayment().equals("余额支付")) {
				Map<String, Object> map = new HashMap<>();
				WxUser user = wxUserMapper.selectByPrimaryKey(orders.getOpenId());
				map.put("phone", user.getOpenId() + "-" + user.getPhone());
				map.put("amount", orders.getPayPrice());
				if (wxUserBellMapper.charge(map) == 1) {
					return orders.getShopId();
				} else {
					throw new RuntimeException("退款失败联系管理员");
				}
			}
		}
		return 0;
	}

	@Override
	public int countBySchoolId(int schoolId) {
		return ordersMapper.countBySchoolId(schoolId);
	}

	@Override
	public List<Orders> findByShopYJS(int shopId, int page, int size) {
		Shop s = new Shop();
		s.setId(shopId);
		s.setPage(page);
		s.setSize(size);
		return ordersMapper.findByShopYJS(s);
	}

	@Override
	public List<Orders> findAllDjs() {
		return ordersMapper.findAllDjs();
	}
}
