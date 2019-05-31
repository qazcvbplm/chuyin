package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Mqtt;

public interface MqttMapper extends BaseMapper<Mqtt>{

	void incr(Integer id);

	int tx(Integer id);
}