package com.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.entity.School;

public interface SchoolMapper {
    int insert(School record);

    School selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

	School findByLoginName(String loginName);

	List<School> find(School school);

	School login(School school);

	int endOrder(Map<String, Object> map);

	int sendertx(Map<String, Object> map);

	int tx(Map<String, Object> map);
	
	int charge(Map<String,Object> map);

	int chargeUse(Map<String,Object> map);
}