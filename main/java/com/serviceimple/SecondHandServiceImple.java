package com.serviceimple;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.SecondHandMapper;
import com.dao.WxUserMapper;
import com.entity.SecondHand;
import com.entity.WxUser;
import com.service.SecondHandService;

@Service
public class SecondHandServiceImple implements SecondHandService{

	@Autowired
	private SecondHandMapper secondHandMapper;
	@Autowired
	private WxUserMapper wxUserMapper;

	@Override
	public void add(@Valid SecondHand secondHand) {
		WxUser wxUser=wxUserMapper.selectByPrimaryKey(secondHand.getOpenId());
		secondHand.setAvatarUrl(wxUser.getAvatarUrl());
		secondHand.setNickName(wxUser.getNickName());
		secondHandMapper.insert(secondHand);
	}

	@Override
	public List<SecondHand> find(SecondHand secondHand) {
		return secondHandMapper.find(secondHand);
	}

	@Override
	public int update(SecondHand secondHand) {
		return secondHandMapper.updateByPrimaryKeySelective(secondHand);
	}

	@Override
	public int count(SecondHand secondHand) {
		return secondHandMapper.count(secondHand);
	}
}
