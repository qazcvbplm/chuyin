package com.service;

import java.util.List;

import javax.validation.Valid;

import com.entity.ShopCategory;

public interface ShopCategoryService {

	void add(ShopCategory shopCategory);

	List<ShopCategory> find(ShopCategory shopCategory);

	int update(ShopCategory shopCategory);

}
