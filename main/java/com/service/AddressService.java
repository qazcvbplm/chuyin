package com.service;


import java.util.List;

import com.entity.Address;

public interface AddressService {

	void add(Address address);

	List<Address> find(Address address);

	int update(Address address);

}
