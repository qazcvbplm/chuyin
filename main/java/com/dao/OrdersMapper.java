package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.dto.SenderTj;
import com.entity.Orders;
import com.entity.Sender;
import com.entity.Shop;
public interface OrdersMapper {
    int insert(Orders record);

    Orders selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Orders record);

	List<Orders> find(Orders orders);

	int count(Orders orders);


	int paySuccess(Map<String, Object> map);

	List<Orders> findByShopByDjs(int shopId);

	int shopAcceptOrderById(Orders update);

	List<Orders> findByShop(Shop s);

	int waterNumber(Orders update);

	void remove();


	List<Orders> findBySenderTakeout(Sender sender);

	int SenderAccept(Orders orders);

	int getorder(String orderId);

	int end(Orders orders);

	List<Orders> senderStatistics(Map<String, Object> map);

	int pl(String id);

	int cancel(String id);


	Orders completeByShopId(Map<String, Object> map);

	Orders completeBySchoolId(Map<String, Object> map);

	int countBySchoolId(int schoolId);

}