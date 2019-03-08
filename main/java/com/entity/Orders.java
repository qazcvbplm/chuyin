package com.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.util.BaiduUtil;
import com.util.Util;

public class Orders extends Base{
    private String id;
    
    private Integer appId;

    private Integer schoolId;

    private BigDecimal schoolTopDownPrice;
    
    private Integer shopId;

    private String shopName;
    
    private String shopImage;

    private String shopAddress;

    private String shopPhone;
    
    private String openId;
    @NotBlank
    private String addressName;
    @NotBlank
    private String addressPhone;
    @NotBlank
    private String addressDetail;
    @NotNull
    private Integer floorId;
    @NotBlank
    @Pattern(regexp="外卖订单|堂食订单|跑腿订单")
    private String typ;

    private String status;

    private BigDecimal boxPrice;

    private BigDecimal sendPrice;

    private BigDecimal sendBasePrice;

    private BigDecimal sendAddDistancePrice;

    private BigDecimal sendAddCountPrice;

    private BigDecimal productPrice;

    private String discountType;

    private BigDecimal discountPrice;

    private BigDecimal payPrice;

    private String senderName;

    private String senderPhone;

    private Integer senderId;

    private Integer destination;
    @NotNull
    private String remark;

    private Integer waterNumber;

    private String createTime;

    private String payment;

    private String payTime;
    
    private Long payTimeLong;

    private Integer sendGetFlag;

    private String endTime;
    
    private List<OrderProduct> op;
    
    private Integer evaluateFlag;
    
    private String reseverTime;
    
    private OrdersComplete complete;
    
    
    
    


	public OrdersComplete getComplete() {
		return complete;
	}

	public void setComplete(OrdersComplete complete) {
		this.complete = complete;
	}

	public Long getPayTimeLong() {
		return payTimeLong;
	}

	public void setPayTimeLong(Long payTimeLong) {
		this.payTimeLong = payTimeLong;
	}

	public String getReseverTime() {
		return reseverTime;
	}

	public void setReseverTime(String reseverTime) {
		this.reseverTime = reseverTime;
	}

	public Integer getEvaluateFlag() {
		return evaluateFlag;
	}

	public void setEvaluateFlag(Integer evaluateFlag) {
		this.evaluateFlag = evaluateFlag;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public void takeoutinit1(WxUser wxUser, School school, Shop shop, Floor floor, int totalcount, boolean isDiscount,List<FullCut> fullcut, int boxcount) {
		this.schoolId=school.getId();
		this.appId=school.getAppId();
		this.schoolTopDownPrice=school.getTopDown();
		this.shopId=shop.getId();
		this.shopAddress=shop.getShopAddress();
		this.shopImage=shop.getShopImage();
		this.shopName=shop.getShopName();
		this.shopPhone=shop.getShopPhone();
		if(!isDiscount){
			for(FullCut temp:fullcut){
				if(this.productPrice.compareTo(new BigDecimal(temp.getFull()))!=-1){
					this.setDiscountType("满减优惠");
					this.discountPrice=new BigDecimal(temp.getCut());
					this.productPrice=this.productPrice.subtract(this.discountPrice);
					break;
				}
			}
		}
		//计算餐盒费
		if(this.typ.equals("外卖订单")||this.typ.equals("堂食订单")){
			this.boxPrice=shop.getBoxPrice().multiply(new BigDecimal(boxcount));
		}else{
			this.boxPrice=new BigDecimal(0);
		}
		//开始计算外卖的配送费
		if(this.typ.equals("外卖订单")){
			this.sendBasePrice=shop.getSendPrice();
			//按物品件数增加配送费
			if(shop.getSendPriceAddByCountFlag()==1){
				this.sendAddCountPrice=new BigDecimal((totalcount-1)).multiply(shop.getSendPriceAdd());
			}else{
				this.sendAddCountPrice=new BigDecimal(0);
			}
			//判断配送距离
			int distance=BaiduUtil.DistanceAll(floor.getLat()+","+floor.getLng(), shop.getLat()+","+shop.getLng());
			if(distance>school.getSendMaxDistance()){
				int per=(distance/school.getSendPerOut())+1;
				this.sendAddDistancePrice=new BigDecimal(per).multiply(school.getSendPerMoney());
			}else{
				this.sendAddDistancePrice=new BigDecimal(0);
			}
		}else{
			this.sendBasePrice=new BigDecimal(0);
			this.sendAddDistancePrice=new BigDecimal(0);
			this.sendAddCountPrice=new BigDecimal(0);
		}
		//计算总的配送费
		this.sendPrice=this.sendBasePrice.add(this.sendAddCountPrice).add(this.sendAddDistancePrice);
		//计算总价
		this.payPrice=this.productPrice.add(this.boxPrice).add(this.sendPrice);
	}
    
    public Orders() {
		super();
		
	}
    
    public void init(){
    	synchronized (this) {
			this.id=Util.GenerateOrderId();
		}
    }

    
    
    

    public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	public List<OrderProduct> getOp() {
		return op;
	}

	public void setOp(List<OrderProduct> op) {
		this.op = op;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public BigDecimal getSchoolTopDownPrice() {
        return schoolTopDownPrice;
    }

    public void setSchoolTopDownPrice(BigDecimal schoolTopDownPrice) {
        this.schoolTopDownPrice = schoolTopDownPrice;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone == null ? null : shopPhone.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone == null ? null : addressPhone.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ == null ? null : typ.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

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

    public BigDecimal getSendBasePrice() {
        return sendBasePrice;
    }

    public void setSendBasePrice(BigDecimal sendBasePrice) {
        this.sendBasePrice = sendBasePrice;
    }

    public BigDecimal getSendAddDistancePrice() {
        return sendAddDistancePrice;
    }

    public void setSendAddDistancePrice(BigDecimal sendAddDistancePrice) {
        this.sendAddDistancePrice = sendAddDistancePrice;
    }

    public BigDecimal getSendAddCountPrice() {
        return sendAddCountPrice;
    }

    public void setSendAddCountPrice(BigDecimal sendAddCountPrice) {
        this.sendAddCountPrice = sendAddCountPrice;
    }

    public BigDecimal getProductPrice() {
    	if(this.productPrice==null){
    		this.productPrice=new BigDecimal(0);
    	}
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType == null ? null : discountType.trim();
    }

    public BigDecimal getDiscountPrice() {
    	if(this.discountPrice==null){
    		this.discountPrice=new BigDecimal(0);
    	}
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
    
        this.discountPrice = discountPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone == null ? null : senderPhone.trim();
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getWaterNumber() {
        return waterNumber;
    }

    public void setWaterNumber(Integer waterNumber) {
        this.waterNumber = waterNumber;
    }

  

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public Integer getSendGetFlag() {
        return sendGetFlag;
    }

    public void setSendGetFlag(Integer sendGetFlag) {
        this.sendGetFlag = sendGetFlag;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

	
}