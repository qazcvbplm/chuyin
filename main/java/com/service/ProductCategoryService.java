package com.service;

import java.util.List;

import javax.validation.Valid;

import com.entity.ProductCategory;

public interface ProductCategoryService {

	void add(@Valid ProductCategory productCategory);

	List<ProductCategory> findByShop(int shopId);

	int update(ProductCategory pc);

}
