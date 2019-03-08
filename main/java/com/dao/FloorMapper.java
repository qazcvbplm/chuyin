package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Floor;
public interface FloorMapper {
    int insert(Floor record);

    int insertSelective(Floor record);

    Floor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Floor record);

    int updateByPrimaryKey(Floor record);

	List<Floor> find(Floor floor);
}