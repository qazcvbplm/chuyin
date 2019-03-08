package com.serviceimple;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controller.OrdersNotify;
import com.dao.ChargeLogMapper;
import com.dao.ChargeMapper;
import com.dao.SchoolMapper;
import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.entity.Charge;
import com.entity.ChargeLog;
import com.entity.Orders;
import com.entity.School;
import com.entity.WxUser;
import com.entity.WxUserBell;
import com.service.WxUserService;
import com.util.LoggerUtil;
import com.util.Util;
import com.wxutil.WXpayUtil;

@Service
public class WxUserServiceImple implements WxUserService{

	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Autowired
	private ChargeMapper chargeMapper;
	@Autowired
	private SchoolMapper schoolMapper;
	@Autowired
	private ChargeLogMapper chargeLogMapper;

	@Override
	public WxUser login(String openid,Integer schoolId, Integer appId) {
		WxUser wxUser=wxUserMapper.selectByPrimaryKey(openid);
		if(wxUser==null){
			wxUser=new WxUser(openid,"微信小程序");
			wxUser.setAppId(appId);
			wxUser.setSchoolId(schoolId);
			wxUserMapper.insert(wxUser);
		}
		return wxUser;
	}

	@Transactional
	@Override
	public WxUser update(WxUser wxUser) {
		if(wxUser.getPhone()!=null){
			if(wxUserBellMapper.findByPhone(wxUser.getOpenId()+"-"+wxUser.getPhone())==0){
				wxUserBellMapper.insert(new WxUserBell(wxUser.getOpenId()+"-"+wxUser.getPhone()));
			}else{
				throw new RuntimeException("手机号码已被注册");
			}
		}
		wxUserMapper.updateByPrimaryKeySelective(wxUser);
		return wxUserMapper.selectByPrimaryKey(wxUser.getOpenId());
	}

	@Override
	public List<WxUser> find(WxUser wxUser) {
		if(wxUser.getQueryType().equals("school")){
			wxUser.setSchoolId(Integer.valueOf(wxUser.getQuery()));
		}
		return wxUserMapper.find(wxUser);
	}

	@Override
	public Object charge(String openId, int chargeId) {
		Charge charge=chargeMapper.selectByPrimaryKey(chargeId);
		WxUser wxUser=wxUserMapper.selectByPrimaryKey(openId);
		School school=schoolMapper.selectByPrimaryKey(wxUser.getSchoolId());
		if(charge!=null){
			return WXpayUtil.payrequest(school.getWxAppId(), school.getMchId(), school.getWxPayId(),
					  "椰子-w", Util.GenerateOrderId(), charge.getFull()*100+"", openId,
					  "127.0.0.1", chargeId+"", OrdersNotify.URL+"notify/charge");
		}
		return null;
	}
	

	@Transactional
	@Override
	public void chargeSuccess(String orderId, String openId, String attach) {
		  WxUser wxUser=wxUserMapper.selectByPrimaryKey(openId);
          Charge charge = chargeMapper.selectByPrimaryKey(Integer.valueOf(attach));	
          ChargeLog log=new ChargeLog(orderId,new BigDecimal(charge.getFull()),new BigDecimal(charge.getSend()),openId,wxUser.getAppId());
          chargeLogMapper.insert(log);
          Map<String,Object> map=new HashMap<>();
          map.put("phone", wxUser.getOpenId()+"-"+wxUser.getPhone());
          map.put("amount", log.getPay().add(log.getSend()));
          if(wxUserBellMapper.charge(map)==0){
        	  LoggerUtil.log("充值失败："+wxUser.getPhone()+""+(log.getPay().add(log.getSend()).toString()));
          }
	}

	@Override
	public Object findcharge(String openId) {
		return chargeLogMapper.findByOpenId(openId);
	}


	@Override
	public WxUser findByid(String openId) {
		return wxUserMapper.selectByPrimaryKey(openId);
	}

	@Override
	public int addSource(String openId, Integer source) {
		WxUser wxUser=findByid(openId);
		Map<String,Object> map2=new HashMap<>();
        map2.put("phone", wxUser.getOpenId()+"-"+wxUser.getPhone());
        map2.put("source", source);
		wxUserBellMapper.addSource(map2);
		return 0;
	}

	@Override
	public int countBySchoolId(int schoolId) {
		return wxUserMapper.countBySchoolId(schoolId);
	}
	
}
