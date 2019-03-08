package com.service;

import java.util.List;

import javax.validation.Valid;

import com.dto.SenderTj;
import com.entity.FullCut;
import com.entity.Shop;
import com.entity.ShopOpenTime;

public interface ShopService {

	void add(@Valid Shop shop);

	List<Shop> find(Shop shop);

	int update(Shop shop);

	void addFullCut(@Valid FullCut fullcut);

	int deleteFullCut(int id);

	List<FullCut> findFullCut(int shopId);

	int count(Shop shop);

	Shop login(String loginName, String enCode);

	SenderTj statistics(Integer shopId, String beginTime, String endTime);

	Shop findById(int id);

	int openorclose(Integer id);

	int addOpenTime(@Valid ShopOpenTime time);

	int removeopentime(int id);

	List<ShopOpenTime> findOpenTime(int shopId);

}
