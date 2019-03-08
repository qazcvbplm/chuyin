package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Sender;
public interface SenderMapper {
    int insert(Sender record);

    int insertSelective(Sender record);

    Sender selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sender record);

    int updateByPrimaryKey(Sender record);

	List<Sender> find(Sender sender);

	Sender check(String openId);

	int count(Sender sender);

	int finddsh(int schoolId);
}