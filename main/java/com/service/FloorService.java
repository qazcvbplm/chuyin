package com.service;


import java.util.List;

import com.entity.Floor;

public interface FloorService {

	void add(Floor floor);

	List<Floor> find(Floor floor);

	int update(Floor floor);


}
