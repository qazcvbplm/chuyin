package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.entity.OrdersComplete;

public class ShopTj implements Serializable {

	private int totalCount;

	private int totalSuccessCount;

	private BigDecimal totalPrice;

	private BigDecimal selfGet;

	private BigDecimal boxPrice;

	private BigDecimal sendPrice;

	public ShopTj(Integer totalCount, Integer totalSuccessCount, BigDecimal totalPrice, OrdersComplete ordersComplete,
			BigDecimal boxPrice, BigDecimal sendPrice) {
		this.totalCount = totalCount;
		this.totalSuccessCount = totalSuccessCount;
		if (totalPrice != null)
			this.totalPrice = totalPrice;
		else
			this.totalPrice = new BigDecimal(0);
		if (ordersComplete != null)
			this.selfGet = ordersComplete.getShopGetTotal();
		else
			this.selfGet = new BigDecimal(0);
		if (boxPrice != null)
			this.boxPrice = boxPrice;
		else
			this.boxPrice = new BigDecimal(0);
		if (sendPrice != null)
			this.sendPrice = sendPrice;
		else
			this.sendPrice = new BigDecimal(0);
	}

	

	public ShopTj(int totalCount, int totalSuccessCount, BigDecimal totalPrice, BigDecimal selfGet, BigDecimal boxPrice,
			BigDecimal sendPrice) {
		super();
		this.totalCount = totalCount;
		this.totalSuccessCount = totalSuccessCount;
		this.totalPrice = totalPrice;
		this.selfGet = selfGet;
		this.boxPrice = boxPrice;
		this.sendPrice = sendPrice;
	}



	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalSuccessCount() {
		return totalSuccessCount;
	}

	public void setTotalSuccessCount(int totalSuccessCount) {
		this.totalSuccessCount = totalSuccessCount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getSelfGet() {
		return selfGet;
	}

	public void setSelfGet(BigDecimal selfGet) {
		this.selfGet = selfGet;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}
}
