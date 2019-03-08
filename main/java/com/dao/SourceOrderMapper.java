package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.SourceOrder;
public interface SourceOrderMapper {
    int insert(SourceOrder record);

    int insertSelective(SourceOrder record);

    SourceOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SourceOrder record);

    int updateByPrimaryKey(SourceOrder record);

	List<SourceOrder> find(SourceOrder sourceOrder);

	int count(SourceOrder sourceOrder);
}