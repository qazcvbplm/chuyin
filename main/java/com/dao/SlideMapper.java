package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Slide;
public interface SlideMapper {
    int insert(Slide record);

    int insertSelective(Slide record);
    
    

    Slide findById(Integer id);

    int updateByPrimaryKeySelective(Slide record);

    int updateByPrimaryKey(Slide record);

	List<Slide> find(int schoolId);
}