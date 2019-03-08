package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Charge;
import com.entity.ChargeLog;
public interface ChargeMapper {
    int insert(Charge record);

    Charge selectByPrimaryKey(Integer id);

	List<Charge> find();

	int remove(int id);

}