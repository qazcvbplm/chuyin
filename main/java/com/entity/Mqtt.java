package com.entity;

import java.math.BigDecimal;

public class Mqtt {
    private Integer id;

    private Boolean enable;

    private BigDecimal total;

    private BigDecimal per;
    
    private Boolean tx;
    
    private String open;
    
    private Integer schoolId;
    
    


	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public Boolean getTx() {
		return tx;
	}

	public void setTx(Boolean tx) {
		this.tx = tx;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPer() {
        return per;
    }

    public void setPer(BigDecimal per) {
        this.per = per;
    }
}