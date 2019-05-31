package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class School extends Base implements Serializable{
	
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String loginName;
    @NotBlank
    private String loginPassWord;
    @DecimalMin(value="0.006")
    private BigDecimal rate;
    @NotNull
    private Integer appId;

    private BigDecimal money;
    @NotNull
    private BigDecimal topDown;

    private Long sort;

    private BigDecimal senderMoney;
    @NotNull
    private Integer sendMaxDistance;
    @NotNull
    private Integer sendPerOut;
    @NotNull
    private BigDecimal sendPerMoney;
    @NotBlank
    private String wxAppId;
    @NotBlank
    private String wxSecret;
    @NotBlank
    private String mchId;
    @NotBlank
    private String wxPayId;
    @NotBlank
    private String certPath;

    private Integer isDelete;

    private Date createTime;
    @NotNull
    private Integer enableTakeout;
    
    private BigDecimal senderAllTx;
    
    private BigDecimal userCharge;
    
    private BigDecimal userBellAll;
    
    private BigDecimal userChargeSend;
    
    

    public BigDecimal getUserChargeSend() {
		return userChargeSend;
	}

	public void setUserChargeSend(BigDecimal userChargeSend) {
		this.userChargeSend = userChargeSend;
	}

	public BigDecimal getSenderAllTx() {
		return senderAllTx;
	}

	public void setSenderAllTx(BigDecimal senderAllTx) {
		this.senderAllTx = senderAllTx;
	}

	public BigDecimal getUserCharge() {
		return userCharge;
	}

	public void setUserCharge(BigDecimal userCharge) {
		this.userCharge = userCharge;
	}

	public BigDecimal getUserBellAll() {
		return userBellAll;
	}

	public void setUserBellAll(BigDecimal userBellAll) {
		this.userBellAll = userBellAll;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEnableTakeout() {
		return enableTakeout;
	}

	public void setEnableTakeout(Integer enableTakeout) {
		this.enableTakeout = enableTakeout;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public BigDecimal getTopDown() {
		return topDown;
	}

	public void setTopDown(BigDecimal topDown) {
		this.topDown = topDown;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    
    public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassWord() {
        return loginPassWord;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.loginPassWord = loginPassWord == null ? null : loginPassWord.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public BigDecimal getSenderMoney() {
        return senderMoney;
    }

    public void setSenderMoney(BigDecimal senderMoney) {
        this.senderMoney = senderMoney;
    }

    public Integer getSendMaxDistance() {
        return sendMaxDistance;
    }

    public void setSendMaxDistance(Integer sendMaxDistance) {
        this.sendMaxDistance = sendMaxDistance;
    }

    public Integer getSendPerOut() {
        return sendPerOut;
    }

    public void setSendPerOut(Integer sendPerOut) {
        this.sendPerOut = sendPerOut;
    }

    public BigDecimal getSendPerMoney() {
        return sendPerMoney;
    }

    public void setSendPerMoney(BigDecimal sendPerMoney) {
        this.sendPerMoney = sendPerMoney;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId == null ? null : wxAppId.trim();
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public void setWxSecret(String wxSecret) {
        this.wxSecret = wxSecret == null ? null : wxSecret.trim();
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    public String getWxPayId() {
        return wxPayId;
    }

    public void setWxPayId(String wxPayId) {
        this.wxPayId = wxPayId == null ? null : wxPayId.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}