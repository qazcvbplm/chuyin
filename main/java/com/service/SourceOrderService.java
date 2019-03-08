package com.service;

import java.util.List;

import javax.validation.Valid;

import com.entity.SourceOrder;

public interface SourceOrderService {

	String add(Integer id, @Valid SourceOrder sourceOrder);

	List<SourceOrder> find(SourceOrder sourceOrder);

	int update(SourceOrder sourceOrder);

	int count(SourceOrder sourceOrder);

}
