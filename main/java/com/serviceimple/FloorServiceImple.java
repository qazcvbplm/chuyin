package com.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dao.FloorMapper;
import com.dao.WxUserMapper;
import com.entity.Floor;
import com.service.FloorService;

@Service
public class FloorServiceImple implements FloorService {

	@Autowired
	private FloorMapper floorMapper;
	@Autowired
	private WxUserMapper wxUserMapper;

	@Override
	public void add(Floor floor) {
		floor.setSort(System.currentTimeMillis());
		floorMapper.insert(floor);
	}

	@Override
	public List<Floor> find(Floor floor) {
		switch (floor.getQueryType()) {
		case "wxuser":
			if(floor.getSchoolId()==null){
				floor.setSchoolId(Integer.valueOf(floor.getQuery()));
			}
			break;
		case "school":
			floor.setSchoolId(Integer.valueOf(floor.getQuery()));
			break;
		case "admin":
			break;
		}
		return floorMapper.find(floor);
	}

	@Override
	public int update(Floor floor) {
		return floorMapper.updateByPrimaryKeySelective(floor);
	}
}
