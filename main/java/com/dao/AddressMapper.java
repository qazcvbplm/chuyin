package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.entity.Address;

public interface AddressMapper {
    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

	List<Address> find(Address address);
}