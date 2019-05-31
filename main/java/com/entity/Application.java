package com.entity;

import java.math.BigDecimal;

public class Application {
    private Integer id;

    private BigDecimal vipTakeoutDiscount;

    private BigDecimal vipRunDiscount;

    private BigDecimal money;

    private BigDecimal useableMoney;

    private String loginName;

    private String loginPass;

    private Integer vipTakeoutDiscountFlag;

    private Integer vipRunDiscountFlag;

    private Integer maxSchoolCount;
    
    private String phone;

    private Integer isDelete;

    
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getVipTakeoutDiscount() {
        return vipTakeoutDiscount;
    }

    public void setVipTakeoutDiscount(BigDecimal vipTakeoutDiscount) {
        this.vipTakeoutDiscount = vipTakeoutDiscount;
    }

    public BigDecimal getVipRunDiscount() {
        return vipRunDiscount;
    }

    public void setVipRunDiscount(BigDecimal vipRunDiscount) {
        this.vipRunDiscount = vipRunDiscount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getUseableMoney() {
        return useableMoney;
    }

    public void setUseableMoney(BigDecimal useableMoney) {
        this.useableMoney = useableMoney;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass == null ? null : loginPass.trim();
    }

    public Integer getVipTakeoutDiscountFlag() {
        return vipTakeoutDiscountFlag;
    }

    public void setVipTakeoutDiscountFlag(Integer vipTakeoutDiscountFlag) {
        this.vipTakeoutDiscountFlag = vipTakeoutDiscountFlag;
    }

    public Integer getVipRunDiscountFlag() {
        return vipRunDiscountFlag;
    }

    public void setVipRunDiscountFlag(Integer vipRunDiscountFlag) {
        this.vipRunDiscountFlag = vipRunDiscountFlag;
    }

    public Integer getMaxSchoolCount() {
        return maxSchoolCount;
    }

    public void setMaxSchoolCount(Integer maxSchoolCount) {
        this.maxSchoolCount = maxSchoolCount;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}