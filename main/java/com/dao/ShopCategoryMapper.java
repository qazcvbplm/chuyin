package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.ShopCategory;

public interface ShopCategoryMapper {
    int insert(ShopCategory record);

    int insertSelective(ShopCategory record);

    ShopCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCategory record);

    int updateByPrimaryKey(ShopCategory record);

	List<ShopCategory> find(ShopCategory shopCategory);
}