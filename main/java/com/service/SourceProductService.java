package com.service;

import java.util.List;

import javax.validation.Valid;

import com.entity.SourceProduct;

public interface SourceProductService {

	void add(@Valid SourceProduct sourceProduct);

	List<SourceProduct> find(SourceProduct sourceProduct);

	int update(SourceProduct sourceProduct);

}
