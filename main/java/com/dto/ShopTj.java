package com.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopTj implements Serializable {

    private int totalCount;

    private int totalSuccessCount;

    private BigDecimal totalPrice;

    private BigDecimal selfGet;

    private BigDecimal boxPrice;

    private BigDecimal sendPrice;


    public ShopTj(int totalCount, int totalSuccessCount, BigDecimal totalPrice, BigDecimal selfGet, BigDecimal boxPrice, BigDecimal sendPrice) {
        this.totalCount = totalCount;
        this.totalSuccessCount = totalSuccessCount;
        this.totalPrice = totalPrice;
        this.selfGet = selfGet;
        this.boxPrice = boxPrice;
        this.sendPrice = sendPrice;
    }

    public BigDecimal getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(BigDecimal boxPrice) {
        this.boxPrice = boxPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public void setTotalSuccessCount(int totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSelfGet() {
        return selfGet;
    }

    public void setSelfGet(BigDecimal selfGet) {
        this.selfGet = selfGet;
    }



    public BigDecimal getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(BigDecimal sendPrice) {
        this.sendPrice = sendPrice;
    }
}
