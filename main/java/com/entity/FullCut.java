package com.entity;

import javax.validation.constraints.NotNull;

public class FullCut {
	
    private Integer id;
    @NotNull
    private Integer shopId;
    @NotNull
    private Integer full;
    @NotNull
    private Integer cut;
    
   

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getFull() {
        return full;
    }

    public void setFull(Integer full) {
        this.full = full;
    }

    public Integer getCut() {
        return cut;
    }

    public void setCut(Integer cut) {
        this.cut = cut;
    }
}