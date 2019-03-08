package com.serviceimple;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SourceProductMapper;
import com.entity.SourceProduct;
import com.service.SourceProductService;

@Service
public class SourceProductServiceImple implements SourceProductService{

	@Autowired
	private SourceProductMapper sourceProductMapper;

	@Override
	public void add(@Valid SourceProduct sourceProduct) {
		sourceProductMapper.insert(sourceProduct);
	}

	@Override
	public List<SourceProduct> find(SourceProduct sourceProduct) {
		return sourceProductMapper.find(sourceProduct);
	}

	@Override
	public int update(SourceProduct sourceProduct) {
		// TODO Auto-generated method stub
		return sourceProductMapper.updateByPrimaryKeySelective(sourceProduct);
	}
}
