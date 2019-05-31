package com.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class DayLogTakeout {
	@TableId(type=IdType.AUTO)
    private Integer id;

    private String selfName;

    private Integer selfId;
    
    private Integer parentId;

    private String day;

    private Integer totalCount;

    private BigDecimal totalPrice;

    private BigDecimal selfGet;

    private BigDecimal parentGet;

    private Integer takeoutTotalCount;

    private Integer selfgetTotalCount;

    private String type;
    
    private BigDecimal boxPrice;
    
    private BigDecimal sendPrice;
    
    private BigDecimal productPrice;
    
    
    
    
    
    public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DayLogTakeout shoplog(String selfName, Integer selfId, String day,Orders order,String type,Integer parentId) {
		this.selfName = selfName;
		this.selfId = selfId;
		this.day = day;
		this.totalCount=Integer.valueOf(order.getRemark());
		this.totalPrice=order.getPayPrice();
		if(order.getComplete()!=null) {
			this.selfGet=order.getComplete().getShopGetTotal();
			this.parentGet=order.getComplete().getSchoolGetSender().add(order.getComplete().getSchoolGetShop());
		}
		this.type=type;
		this.parentId=parentId;
		this.boxPrice=order.getBoxPrice();
		this.sendPrice=order.getSendPrice();
		this.productPrice=order.getProductPrice();
		return this;
	}
	
	public DayLogTakeout schoollog(String selfName, Integer selfId, String day,Orders order,String type,Integer parentId) {
		this.selfName = selfName;
		this.selfId = selfId;
		this.day = day;
		this.totalCount=Integer.valueOf(order.getRemark());
		this.totalPrice=order.getPayPrice();
		if(order.getComplete()!=null) {
			this.parentGet=order.getComplete().getAppGetTotal();
			this.selfGet=order.getComplete().getSchoolGetSender()
					.add(order.getComplete().getSchoolGetShop()).subtract(order.getSendAddCountPrice());
		}
		this.type=type;
		this.parentId=parentId;
		this.boxPrice=order.getBoxPrice();
		this.sendPrice=order.getSendPrice();
		this.productPrice=order.getProductPrice();
		return this;
	}

	public DayLogTakeout() {
		super();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName == null ? null : selfName.trim();
    }

    public Integer getSelfId() {
        return selfId;
    }

    public void setSelfId(Integer selfId) {
        this.selfId = selfId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public BigDecimal getParentGet() {
        return parentGet;
    }

    public void setParentGet(BigDecimal parentGet) {
        this.parentGet = parentGet;
    }

    public Integer getTakeoutTotalCount() {
        return takeoutTotalCount;
    }

    public void setTakeoutTotalCount(Integer takeoutTotalCount) {
        this.takeoutTotalCount = takeoutTotalCount;
    }

    public Integer getSelfgetTotalCount() {
        return selfgetTotalCount;
    }

    public void setSelfgetTotalCount(Integer selfgetTotalCount) {
        this.selfgetTotalCount = selfgetTotalCount;
    }
}