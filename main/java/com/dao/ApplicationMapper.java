package com.dao;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Application;
public interface ApplicationMapper {
    int insert(Application record);

    int insertSelective(Application record);

    Application selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKey(Application record);

	Application login(Application login);
}