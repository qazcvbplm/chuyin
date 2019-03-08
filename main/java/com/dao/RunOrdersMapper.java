package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.entity.RunOrders;
import com.entity.Sender;
public interface RunOrdersMapper {
    int insert(RunOrders record);


    RunOrders selectByPrimaryKey(String id);


	List<RunOrders> find(RunOrders orders);


	int count(RunOrders orders);


	int paySuccess(Map<String, Object> map);


	List<RunOrders> findBySenderRun(Sender sender);


	int SenderAccept(RunOrders orders);


	int end(String orderId);


	void remove();


	List<RunOrders> senderStatistics(Map<String, Object> map);


	List<RunOrders> temp(Map<String, Object> map);


	int pl(String orderid);


	int cancel(String id);


	int countBySchoolId(int schoolId);
}