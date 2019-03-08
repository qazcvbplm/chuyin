package com.service;

import java.math.BigDecimal;
import java.util.List;


import com.entity.Product;

public interface ProductService {

	void add(BigDecimal[] attributePrice, String[] attributeName,Product product);

	List<Product> findByCategoryId(int productCategoryId);

	int update(Product product);

	List<Product> findByCategoryId_wxUser(int productCategoryId);

	List<Product> findByShopAllDiscount(int productCategoryId);

	int adda(int pid, BigDecimal attributePrice, String attributeName);

	int removea(int id);

}
