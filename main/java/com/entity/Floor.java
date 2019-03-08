package com.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Floor extends Base{
    private Integer id;
    @NotBlank
    private String name;

    private Integer schoolId;

    private Long sort;
    @NotBlank
    private String lat;
    @NotBlank
    private String lng;

    private Integer isDelete;
    @NotNull
    private Integer ableTop;
    
    

    public Integer getAbleTop() {
		return ableTop;
	}

	public void setAbleTop(Integer ableTop) {
		this.ableTop = ableTop;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

   

    public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}