package com.serviceimple;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.SourceOrderMapper;
import com.dao.SourceProductMapper;
import com.dao.WxUserBellMapper;
import com.dao.WxUserMapper;
import com.entity.SourceOrder;
import com.entity.SourceProduct;
import com.entity.WxUser;
import com.service.SourceOrderService;
import com.util.Util;

@Service
public class SourceOrderServiceImple implements SourceOrderService{

	@Autowired
	private SourceOrderMapper sourceOrderMapper;
	@Autowired
	private SourceProductMapper sourceProductMapper;
	@Autowired
	private WxUserMapper wxUserMapper;
	@Autowired
	private WxUserBellMapper wxUserBellMapper;
	@Transactional
	@Override
	public String add(Integer id, @Valid SourceOrder sourceOrder) {
		WxUser wxUser=wxUserMapper.selectByPrimaryKey(sourceOrder.getOpenId());
		SourceProduct sp=sourceProductMapper.selectByPrimaryKey(id);
		sourceOrder.setId(Util.GenerateOrderId());
		sourceOrder.setPayPrice(sp.getPrice());
		sourceOrder.setProductImage(sp.getProductImage());
		sourceOrder.setProductName(sp.getProductName());
		Map<String,Object> map=new java.util.HashMap<>();
		map.put("phone", wxUser.getOpenId()+"-"+wxUser.getPhone());
		map.put("source", sp.getPrice());
		if(wxUserBellMapper.paySource(map)==1){
			sourceOrderMapper.insert(sourceOrder);
		}else{
			throw new RuntimeException("积分不足");
		}
		return sourceOrder.getId();
	}
	@Override
	public List<SourceOrder> find(SourceOrder sourceOrder) {
		return sourceOrderMapper.find(sourceOrder);
	}
	@Override
	public int update(SourceOrder sourceOrder) {
		return sourceOrderMapper.updateByPrimaryKeySelective(sourceOrder);
	}
	@Override
	public int count(SourceOrder sourceOrder) {
		return sourceOrderMapper.count(sourceOrder);
	}
}
