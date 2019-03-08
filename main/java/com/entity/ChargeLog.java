package com.entity;

import java.math.BigDecimal;

public class ChargeLog {
    private String id;

    private BigDecimal pay;

    private BigDecimal send;

    private String openId;

    private String createTime;
    
    private Integer appId;
    
    
    
    

    public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public ChargeLog(String id, BigDecimal pay, BigDecimal send, String openId,int appId) {
		super();
		this.id = id;
		this.pay = pay;
		this.send = send;
		this.openId = openId;
		this.appId=appId;
	}

	public ChargeLog() {
		super();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getSend() {
        return send;
    }

    public void setSend(BigDecimal send) {
        this.send = send;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}