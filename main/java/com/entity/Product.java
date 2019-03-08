package com.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Product extends Base{
	
    private Integer id;
    @NotBlank
    private String productName;
    @NotBlank
    private String productImage;
    @NotNull
    private BigDecimal discount;
    @NotNull
    private Integer boxPriceFlag;
    @NotNull
    private Integer productCategoryId;
    @NotNull
    private Integer shopId;
    @NotNull
    private Integer schoolId;
    @NotNull
    private Integer sale;
    @NotNull
    private Integer isShow;

    private Integer isDelete;
    
    private List<ProductAttribute> attribute;
    
    

    public List<ProductAttribute> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<ProductAttribute> attribute) {
		this.attribute = attribute;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getBoxPriceFlag() {
        return boxPriceFlag;
    }

    public void setBoxPriceFlag(Integer boxPriceFlag) {
        this.boxPriceFlag = boxPriceFlag;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}