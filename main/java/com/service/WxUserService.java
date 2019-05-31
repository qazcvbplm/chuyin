package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Orders;
import com.entity.WxUser;

public interface WxUserService {


	WxUser update(WxUser wxUser);

	List<WxUser> find(WxUser wxUser);

	Object charge(String string, int chargeId);

	void chargeSuccess(String orderId, String openId, String attach);

	Object findcharge(String openId);

	WxUser findByid(String openId);

	int addSource(String openId, Integer source);

	int countBySchoolId(int schoolId);

	WxUser login(String openid, Integer schoolId, Integer appId, String client);

	List<WxUser> findByPhoneGZH(String phone);

	WxUser findGZH(String phone);

}
