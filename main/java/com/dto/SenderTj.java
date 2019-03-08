package com.dto;

import java.math.BigDecimal;

public class SenderTj {

	private Integer total;
	
	private Integer takeoutTotal;
	
	private Integer takeoutSuccess;
	
	private Integer takeoutNosuccess;
	
    private Integer runTotal;
	
	private Integer runSuccess;
	
	private Integer runNosuccess;
	
	private Integer cancel;
	
	private BigDecimal totalPrice;
	
	private BigDecimal totalGet;
	
	private BigDecimal takeout_Price;
	
	private BigDecimal run_price;
	
	
	

	public SenderTj() {
		super();
		this.total = 0;
		this.takeoutTotal = 0;
		this.takeoutSuccess = 0;
		this.takeoutNosuccess = 0;
		this.runTotal = 0;
		this.runSuccess = 0;
		this.runNosuccess = 0;
		this.cancel = 0;
		this.totalPrice = new BigDecimal(0);
		this.totalGet = new BigDecimal(0);
		this.takeout_Price = new BigDecimal(0);
		this.run_price = new BigDecimal(0);
	}

	public BigDecimal getTakeout_Price() {
		return takeout_Price;
	}

	public void setTakeout_Price(BigDecimal takeout_Price) {
		this.takeout_Price = takeout_Price;
	}

	public BigDecimal getRun_price() {
		
		return run_price==null?new BigDecimal(0):run_price;
	}

	public void setRun_price(BigDecimal run_price) {
		this.run_price = run_price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTakeoutTotal() {
		return takeoutTotal;
	}

	public void setTakeoutTotal(Integer takeoutTotal) {
		this.takeoutTotal = takeoutTotal;
	}

	public Integer getTakeoutSuccess() {
		return takeoutSuccess;
	}

	public void setTakeoutSuccess(Integer takeoutSuccess) {
		this.takeoutSuccess = takeoutSuccess;
	}

	public Integer getTakeoutNosuccess() {
		return takeoutNosuccess;
	}

	public void setTakeoutNosuccess(Integer takeoutNosuccess) {
		this.takeoutNosuccess = takeoutNosuccess;
	}

	public Integer getRunTotal() {
		return runTotal;
	}

	public void setRunTotal(Integer runTotal) {
		this.runTotal = runTotal;
	}

	public Integer getRunSuccess() {
		return runSuccess;
	}

	public void setRunSuccess(Integer runSuccess) {
		this.runSuccess = runSuccess;
	}

	public Integer getRunNosuccess() {
		return runNosuccess;
	}

	public void setRunNosuccess(Integer runNosuccess) {
		this.runNosuccess = runNosuccess;
	}

	public Integer getCancel() {
		return cancel;
	}

	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTotalGet() {
		return totalGet;
	}

	public void setTotalGet(BigDecimal totalGet) {
		this.totalGet = totalGet;
	}
	
	
}
