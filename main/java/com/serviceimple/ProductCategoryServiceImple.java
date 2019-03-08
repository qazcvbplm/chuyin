package com.serviceimple;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dao.ProductCategoryMapper;
import com.entity.ProductCategory;
import com.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImple implements ProductCategoryService{

	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Override
	public void add(@Valid ProductCategory productCategory) {
		productCategory.setSort(System.currentTimeMillis());
		productCategoryMapper.insert(productCategory);
	}

	@Override
	public List<ProductCategory> findByShop(int shopId) {
		return productCategoryMapper.findByShop(shopId);
	}

	@Override
	public int update(ProductCategory pc) {
		return productCategoryMapper.updateByPrimaryKeySelective(pc);
	}
}
