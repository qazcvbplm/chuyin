package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.entity.WxUser;

public interface WxUserMapper {
    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);

	List<WxUser> find(WxUser wxUser);

	int findByPhone(String phone);

	WxUser findByschoolAndPhone(WxUser query);

	int countBySchoolId(int schoolId);

	List<WxUser> findByPhoneGZH(String query);

	WxUser findGzh(String phone);

}