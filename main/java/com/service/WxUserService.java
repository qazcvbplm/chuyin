package com.service;

import java.util.List;

import com.entity.Orders;
import com.entity.WxUser;

public interface WxUserService {

	WxUser login(String openid, Integer schoolId, Integer integer);

	WxUser update(WxUser wxUser);

	List<WxUser> find(WxUser wxUser);

	Object charge(String string, int chargeId);

	void chargeSuccess(String orderId, String openId, String attach);

	Object findcharge(String openId);

	WxUser findByid(String openId);

	int addSource(String openId, Integer source);

	int countBySchoolId(int schoolId);

}
