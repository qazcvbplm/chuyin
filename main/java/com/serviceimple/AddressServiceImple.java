package com.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dao.AddressMapper;
import com.entity.Address;
import com.service.AddressService;

@Service
public class AddressServiceImple implements AddressService{

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public void add(Address address) {
		addressMapper.insert(address);
	}

	@Override
	public List<Address> find(Address address) {
		switch (address.getQueryType()) {
		case "wxuser":
			address.setOpenId(address.getQuery());
			break;
		case "school":
			address.setSchoolId(Integer.valueOf(address.getQuery()));
			break;
		}
		return addressMapper.find(address);
	}

	@Override
	public int update(Address address) {
		return addressMapper.updateByPrimaryKeySelective(address);
	}
	
	
}
