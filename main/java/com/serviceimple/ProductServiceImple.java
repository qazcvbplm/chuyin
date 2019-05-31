package com.serviceimple;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ProductAttributeMapper;
import com.dao.ProductMapper;
import com.entity.Product;
import com.entity.ProductAttribute;
import com.service.ProductService;

@Service
public class ProductServiceImple implements ProductService{
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductAttributeMapper productAttributeMapper;

	@Transactional
	@Override
	public void add(BigDecimal[] attributePrice, String[] attributeName, Product product) {
		productMapper.insert(product);
		int length=attributePrice.length;
		for(int i=0;i<length;i++){
			ProductAttribute pa=new ProductAttribute(product.getId(),attributeName[i], attributePrice[i]);
			productAttributeMapper.insert(pa);
		}
	}

	@Override
	public List<Product> findByCategoryId(int productCategoryId) {
		return productMapper.findByCategoryId(productCategoryId);
	}

	@Override
	public int update(Product product) {
		return productMapper.updateByPrimaryKeySelective(product);
	}

	@Override
	public List<Product> findByCategoryId_wxUser(int productCategoryId) {
		return productMapper.findByCategoryId_wxUser(productCategoryId);
	}

	@Override
	public List<Product> findByShopAllDiscount(int productCategoryId) {
		return productMapper.findByShopAllDiscount(productCategoryId);
	}

	@Override
	public int adda(int pid, BigDecimal attributePrice, String attributeName) {
		ProductAttribute pa=new ProductAttribute(pid,attributeName, attributePrice);
		productAttributeMapper.insert(pa);
		return 1;
	}

	@Override
	public int removea(int id) {
		return productAttributeMapper.delete(id);
	}

	@Transactional
	@Override
	public void sale(List<Integer> pids, List<Integer> counts) {
		int size=pids.size();
		//List<Product> ps=productMapper.findin(pids);
		Map<String,Object> map=new HashMap<>();
		for(int i=0;i<size;i++){
			Product temp=productMapper.selectByPrimaryKey(pids.get(i));
			map.put("id", temp.getId());
			map.put("count", counts.get(i));
             if(temp.getStockFlag()==1){
				if(productMapper.stock(map)!=1){
					throw new RuntimeException("库存不足");
				}
			}
		}
		
	}


}
