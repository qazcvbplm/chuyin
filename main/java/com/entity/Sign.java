package com.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Sign {
	 @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull
    private String openId;

    private Integer day;

    private Integer source;
    
    private Integer indexs;
    
    

    public Sign(@NotNull String openId, Integer day, Integer source, Integer indexs) {
		super();
		this.openId = openId;
		this.day = day;
		this.source = source;
		this.indexs = indexs;
	}

	public Sign() {
		super();
	}

	public Integer getIndexs() {
		return indexs;
	}

	public void setIndexs(Integer indexs) {
		this.indexs = indexs;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}