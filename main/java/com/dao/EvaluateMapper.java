package com.dao;

import com.entity.Evaluate;

import java.util.List;
public interface EvaluateMapper {
    int insert(Evaluate record);

    int insertSelective(Evaluate record);

    Evaluate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKey(Evaluate record);

	List<Evaluate> find(Evaluate evaluate);

	int count(Evaluate evaluate);

    List<Evaluate> findByShopId(int i);
}