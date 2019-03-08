package com.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.ChargeLog;
public interface ChargeLogMapper {
    int insert(ChargeLog record);

    int insertSelective(ChargeLog record);

    ChargeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeLog record);

    int updateByPrimaryKey(ChargeLog record);

	List<ChargeLog> findByOpenId(String openId);

	ChargeLog tj(int appId);

	BigDecimal surplus(int appId);
}