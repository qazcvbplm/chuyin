package com.dao;

import org.apache.ibatis.annotations.Mapper;

import com.entity.ProductAttribute;
public interface ProductAttributeMapper {
    int insert(ProductAttribute record);

    int insertSelective(ProductAttribute record);

    ProductAttribute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductAttribute record);

    int updateByPrimaryKey(ProductAttribute record);

	int delete(int id);
}