package com.dao;

import org.apache.ibatis.annotations.Mapper;

import com.entity.OrderProduct;
public interface OrderProductMapper {
    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    OrderProduct findByOrderId(String id);

    int updateByPrimaryKeySelective(OrderProduct record);

    int updateByPrimaryKey(OrderProduct record);
}