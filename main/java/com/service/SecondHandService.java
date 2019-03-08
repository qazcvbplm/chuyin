package com.service;

import java.util.List;

import javax.validation.Valid;

import com.entity.SecondHand;

public interface SecondHandService {

	void add(@Valid SecondHand secondHand);

	List<SecondHand> find(SecondHand secondHand);

	int update(SecondHand secondHand);

	int count(SecondHand secondHand);

}
