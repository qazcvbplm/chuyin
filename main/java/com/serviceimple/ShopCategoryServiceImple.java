package com.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dao.ShopCategoryMapper;
import com.entity.ShopCategory;
import com.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImple implements ShopCategoryService{

	@Autowired
	private ShopCategoryMapper shopCategoryMapper;

	@Override
	public void add(ShopCategory shopCategory) {
		shopCategory.setSort(System.currentTimeMillis());
		shopCategoryMapper.insert(shopCategory);
	}

	@Override
	public List<ShopCategory> find(ShopCategory shopCategory) {
		return shopCategoryMapper.find(shopCategory);
	}

	@Override
	public int update(ShopCategory shopCategory) {
		return shopCategoryMapper.updateByPrimaryKeySelective(shopCategory);
	}
	
	
}
