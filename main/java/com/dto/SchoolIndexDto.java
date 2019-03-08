package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SchoolIndexDto implements Serializable{

	
	private int schoolId;
	
	private int userCount;
	
	private int takeoutCount;
	
	private int runcount;
	
    private int takeoutCountSuccess;
	
	private int runcountSuccess;
	
	private BigDecimal amount;
	
	

	public SchoolIndexDto() {
		super();
		this.schoolId = 0;
		this.userCount = 0;
		this.takeoutCount = 0;
		this.runcount = 0;
		this.takeoutCountSuccess = 0;
		this.runcountSuccess = 0;
		this.amount = new BigDecimal(0);
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getTakeoutCount() {
		return takeoutCount;
	}

	public void setTakeoutCount(int takeoutCount) {
		this.takeoutCount = takeoutCount;
	}

	public int getRuncount() {
		return runcount;
	}

	public void setRuncount(int runcount) {
		this.runcount = runcount;
	}

	public int getTakeoutCountSuccess() {
		return takeoutCountSuccess;
	}

	public void setTakeoutCountSuccess(int takeoutCountSuccess) {
		this.takeoutCountSuccess = takeoutCountSuccess;
	}

	public int getRuncountSuccess() {
		return runcountSuccess;
	}

	public void setRuncountSuccess(int runcountSuccess) {
		this.runcountSuccess = runcountSuccess;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void userCountadd() {
		this.userCount++;
	}

	public void takeoutCountadd() {
		this.takeoutCount++;		
	}

	public void runCountadd() {
		this.runcount++;
		
	}

	public void takeoutCountaddSuccess() {
          this.takeoutCountSuccess++;		
	}

	public void runCountSuccessadd() {
		this.runcountSuccess++;
	}

	public void amountadd(BigDecimal amount2) {
         this.amount=this.amount.add(amount2);		
	}
	
	
	
}
