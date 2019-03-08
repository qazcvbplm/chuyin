package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Shop;
public interface ShopMapper{
    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

	Shop checkByLoginName(String shopLoginName);

	List<Shop> find(Shop shop);

	int count(Shop shop);
}