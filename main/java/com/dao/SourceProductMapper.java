package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.SourceProduct;
public interface SourceProductMapper {
    int insert(SourceProduct record);

    int insertSelective(SourceProduct record);

    SourceProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SourceProduct record);

    int updateByPrimaryKey(SourceProduct record);

	List<SourceProduct> find(SourceProduct sourceProduct);
}