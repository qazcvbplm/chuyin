package com.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Sender extends Base{
	
    private Integer id;

    private String openId;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String classNo;
    @NotNull
    private Integer schoolId;

    private String exam;

    private BigDecimal rate;

    private Integer takeoutFlag;

    private Integer runFlag;

    private String shopIds;

    private String floorIds;
    
    

    public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo == null ? null : classNo.trim();
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam == null ? null : exam.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTakeoutFlag() {
        return takeoutFlag;
    }

    public void setTakeoutFlag(Integer takeoutFlag) {
        this.takeoutFlag = takeoutFlag;
    }

    public Integer getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(Integer runFlag) {
        this.runFlag = runFlag;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds == null ? null : shopIds.trim();
    }

    public String getFloorIds() {
        return floorIds;
    }

    public void setFloorIds(String floorIds) {
        this.floorIds = floorIds == null ? null : floorIds.trim();
    }
}