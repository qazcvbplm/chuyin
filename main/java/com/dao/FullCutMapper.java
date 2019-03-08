package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.FullCut;
public interface FullCutMapper {
    int insert(FullCut record);

    int delete(int id);

	List<FullCut> findByShop(int shopId);
}