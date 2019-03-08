package com.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Sign;
public interface SignMapper extends BaseMapper<Sign>{

	Sign findLast(String openId);
}