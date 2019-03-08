package com.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.dto.SenderTj;
import com.entity.Orders;
import com.entity.RunOrders;
import com.entity.Sender;

public interface SenderService {

	void add(@Valid Sender sender);

	List<Sender> find(Sender sender);

	int update(Sender sender);

	Sender findByPhone(String phone);

	List<Orders> findorderbydjs(int senderId, int page, int size, String status);

	int acceptOrder(int senderId, String orderId);

	int sendergetorder(String orderId);


	List<RunOrders> findorderbyrundjs(int senderId, int page, int size, String status);

	int acceptOrderRun(int senderId, String orderId);

	void endRun(String orderId);

	SenderTj statistics(int senderId, String beginTime, String endTime);

	int count(Sender sender);

	void end(String orderId, boolean end);

	int tx(String senderId, BigDecimal amount);

	int finddsh(int schoolId);

}
