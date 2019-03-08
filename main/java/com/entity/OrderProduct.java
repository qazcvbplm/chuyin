package com.entity;

import java.math.BigDecimal;

public class OrderProduct extends Base{
    private Integer id;

    private String productName;

    private String productImage;

    private Integer productCount;

    private BigDecimal productDiscount;

    private String orderId;

    private BigDecimal totalPrice;

    private String attributeName;

    private BigDecimal attributePrice;
    
    private Integer productId;
    
    
    
    

    public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public OrderProduct(Integer productId,String productName, String productImage, Integer productCount, BigDecimal productDiscount,
			String orderId, String attributeName, BigDecimal attributePrice) {
		super();
		this.productId=productId;
		this.productName = productName;
		this.productImage = productImage;
		this.productCount = productCount;
		this.productDiscount = productDiscount;
		this.orderId = orderId;
		this.attributeName = attributeName;
		this.attributePrice = attributePrice.multiply(this.productDiscount);
		this.totalPrice=this.attributePrice.multiply(new BigDecimal(this.productCount)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public OrderProduct() {
		super();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(BigDecimal productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
    }

    public BigDecimal getAttributePrice() {
        return attributePrice;
    }

    public void setAttributePrice(BigDecimal attributePrice) {
        this.attributePrice = attributePrice;
    }
}