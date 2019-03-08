package com.wxutil;

import java.util.Map;

public interface NotifyImple {
	/**
	 * 支付回调逻辑的实现
	 * @param map 支付参数集合
	 * @return 是否成功
	 */
     boolean notifcation(Map<String, String> map);
}