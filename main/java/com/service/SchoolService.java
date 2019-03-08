package com.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.entity.School;

public interface SchoolService {

	void add(@Valid School school) throws Exception;

	List<School> find(School school);

	int update(School school);

	School findById(Integer schoolId);

	School login(String loginName, String enCode);


	String tx(int schoolId, BigDecimal amount, String openId);

}
