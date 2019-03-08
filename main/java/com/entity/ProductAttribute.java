package com.entity;

import java.math.BigDecimal;

public class ProductAttribute extends Base{
    private Integer id;

    private Integer productId;

    private String name;

    private BigDecimal price;

    private Integer isDelete;
    
    private Integer isDiscount;
    

   

	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}

	public ProductAttribute(Integer productId, String name, BigDecimal price) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	public ProductAttribute() {
		super();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}