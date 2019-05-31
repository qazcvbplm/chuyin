package com.redis.message;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.entity.Orders;
import com.socket.WebSocketServer;
import com.util.LoggerUtil;


@Service
public class AndroidListener {


    public void receiveMessage(String message) {
        Orders orders = JSON.parseObject(message,Orders.class);
        if (WebSocketServer.webSocketSet.containsKey(orders.getShopId())) {
            try {
                WebSocketServer.webSocketSet.get(orders.getShopId()).sendMessage(message);
            } catch (IOException e) {
                LoggerUtil.log("socket发送失败:" + e.getMessage());
            }

        }
    }
}
