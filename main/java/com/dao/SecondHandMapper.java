package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.SecondHand;
public interface SecondHandMapper {
    int insert(SecondHand record);

    int insertSelective(SecondHand record);

    SecondHand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecondHand record);

    int updateByPrimaryKeyWithBLOBs(SecondHand record);

    int updateByPrimaryKey(SecondHand record);

	List<SecondHand> find(SecondHand secondHand);

	int count(SecondHand secondHand);
}