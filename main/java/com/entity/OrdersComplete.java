package com.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class OrdersComplete {
	@TableId(type=IdType.AUTO)
    private Integer id;

    private String orderId;

    private BigDecimal appGetSchoolRate;

    private BigDecimal schoolGetShopRate;

    private BigDecimal schoolGetSenderRate;

    private BigDecimal appGetTotal;

    private BigDecimal schoolGetShop;

    private BigDecimal schoolGetSender;

    private BigDecimal schoolGetTotal;

    private BigDecimal senderGetTotal;

    private BigDecimal shopGetTotal;
    
    

    public OrdersComplete() {
		super();
	}
    
    public OrdersComplete(Orders order,School school,Shop shop,Sender sender) {
    	this.orderId=order.getId();
    	this.appGetSchoolRate=school.getRate();
    	this.schoolGetShopRate=shop.getRate();
    	if(order.getSenderId()==0){
    		this.schoolGetSenderRate=new BigDecimal(0);
    	}else{
    		this.schoolGetSenderRate=sender.getRate();
    	}
    	BigDecimal wx=order.getPayPrice().multiply(new BigDecimal(0.006));
    	BigDecimal total=order.getPayPrice().multiply(this.appGetSchoolRate);
    	this.appGetTotal=total.subtract(wx);
    	this.senderGetTotal=order.getSendPrice().multiply(new BigDecimal(1).subtract(this.schoolGetSenderRate));
    	this.shopGetTotal=order.getPayPrice().subtract(order.getSendPrice()).multiply(new BigDecimal(1).subtract(this.schoolGetShopRate));
    	this.schoolGetShop=order.getPayPrice().subtract(order.getSendPrice()).multiply(this.schoolGetShopRate);
    	this.schoolGetSender=order.getSendPrice().multiply(this.schoolGetSenderRate);
    	this.schoolGetTotal=this.schoolGetShop.add(this.schoolGetSender).subtract(total);
    	
    	
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAppGetSchoolRate() {
        return appGetSchoolRate;
    }

    public void setAppGetSchoolRate(BigDecimal appGetSchoolRate) {
        this.appGetSchoolRate = appGetSchoolRate;
    }

    public BigDecimal getSchoolGetShopRate() {
        return schoolGetShopRate;
    }

    public void setSchoolGetShopRate(BigDecimal schoolGetShopRate) {
        this.schoolGetShopRate = schoolGetShopRate;
    }

    public BigDecimal getSchoolGetSenderRate() {
        return schoolGetSenderRate;
    }

    public void setSchoolGetSenderRate(BigDecimal schoolGetSenderRate) {
        this.schoolGetSenderRate = schoolGetSenderRate;
    }

    public BigDecimal getAppGetTotal() {
        return appGetTotal;
    }

    public void setAppGetTotal(BigDecimal appGetTotal) {
        this.appGetTotal = appGetTotal;
    }

    public BigDecimal getSchoolGetShop() {
        return schoolGetShop;
    }

    public void setSchoolGetShop(BigDecimal schoolGetShop) {
        this.schoolGetShop = schoolGetShop;
    }

    public BigDecimal getSchoolGetSender() {
        return schoolGetSender;
    }

    public void setSchoolGetSender(BigDecimal schoolGetSender) {
        this.schoolGetSender = schoolGetSender;
    }

    public BigDecimal getSchoolGetTotal() {
        return schoolGetTotal;
    }

    public void setSchoolGetTotal(BigDecimal schoolGetTotal) {
        this.schoolGetTotal = schoolGetTotal;
    }

    public BigDecimal getSenderGetTotal() {
        return senderGetTotal;
    }

    public void setSenderGetTotal(BigDecimal senderGetTotal) {
        this.senderGetTotal = senderGetTotal;
    }

    public BigDecimal getShopGetTotal() {
        return shopGetTotal;
    }

    public void setShopGetTotal(BigDecimal shopGetTotal) {
        this.shopGetTotal = shopGetTotal;
    }
}