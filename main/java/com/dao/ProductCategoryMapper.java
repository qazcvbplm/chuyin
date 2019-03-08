package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.ProductCategory;
public interface ProductCategoryMapper {
    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

	List<ProductCategory> findByShop(int shopId);
}