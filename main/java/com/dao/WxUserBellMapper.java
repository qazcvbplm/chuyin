package com.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.entity.WxUserBell;
public interface WxUserBellMapper {

    int insert(WxUserBell record);


    WxUserBell selectByPrimaryKey(String phone);


	int pay(Map<String, Object> map);


	int charge(Map<String, Object> map);


	int paySource(Map<String, Object> map);


	int addSource(Map<String, Object> map);


	int findByPhone(String string);

}