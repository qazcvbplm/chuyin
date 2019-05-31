package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Evaluate;
public interface EvaluateMapper {
    int insert(Evaluate record);

    int insertSelective(Evaluate record);

    Evaluate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Evaluate record);

    int updateByPrimaryKey(Evaluate record);

	List<Evaluate> find(Evaluate evaluate);

	int count(Evaluate evaluate);

    List<Evaluate> findByShopId(Map<String, Object> map);
}