package com.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class TxLog {
	@TableId(type=IdType.AUTO)
    private Integer id;

    private Integer txerId;

    private String type;

    private Date createTime;

    private BigDecimal amount;

    private String result;

    private Integer schoolId;

    private Integer appId;

    
    public TxLog() {
		super();
	}

	public TxLog( Integer txerId, String type, Date createTime, BigDecimal amount, String result,
			Integer schoolId, Integer appId) {
		super();
		this.txerId = txerId;
		this.type = type;
		this.createTime = createTime;
		this.amount = amount;
		this.result = result;
		this.schoolId = schoolId;
		this.appId = appId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTxerId() {
        return txerId;
    }

    public void setTxerId(Integer txerId) {
        this.txerId = txerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }
}