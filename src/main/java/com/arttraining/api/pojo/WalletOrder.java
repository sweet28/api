package com.arttraining.api.pojo;

import java.util.Date;

public class WalletOrder {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private String codeNumber;

    private String userId;

    private Integer type;

    private Integer status;

    private Date payTime;

    private Date cancelTime;

    private Double money;

    private String auditor;

    private Double couponPay;

    private Double finalPay;

    private String payType;

    private String tradeId;

    private Integer orderDetailNum;

    private Date activeTime;

    private Date remainingTime;

    private Integer couponId;

    private Integer couponType;

    private String bystr1;

    private String bystr2;

    private String bystr3;

    private Integer byint1;

    private Integer byint2;

    private Integer byint3;
    
    private Double bydouble1;
    private Double bydouble2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber == null ? null : codeNumber.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Double getCouponPay() {
        return couponPay;
    }

    public void setCouponPay(Double couponPay) {
        this.couponPay = couponPay;
    }

    public Double getFinalPay() {
        return finalPay;
    }

    public void setFinalPay(Double finalPay) {
        this.finalPay = finalPay;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public Integer getOrderDetailNum() {
        return orderDetailNum;
    }

    public void setOrderDetailNum(Integer orderDetailNum) {
        this.orderDetailNum = orderDetailNum;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Date remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getBystr1() {
        return bystr1;
    }

    public void setBystr1(String bystr1) {
        this.bystr1 = bystr1 == null ? null : bystr1.trim();
    }

    public String getBystr2() {
        return bystr2;
    }

    public void setBystr2(String bystr2) {
        this.bystr2 = bystr2 == null ? null : bystr2.trim();
    }

    public String getBystr3() {
        return bystr3;
    }

    public void setBystr3(String bystr3) {
        this.bystr3 = bystr3 == null ? null : bystr3.trim();
    }

    public Integer getByint1() {
        return byint1;
    }

    public void setByint1(Integer byint1) {
        this.byint1 = byint1;
    }

    public Integer getByint2() {
        return byint2;
    }

    public void setByint2(Integer byint2) {
        this.byint2 = byint2;
    }

    public Integer getByint3() {
        return byint3;
    }

    public void setByint3(Integer byint3) {
        this.byint3 = byint3;
    }

	public Double getBydouble1() {
		return bydouble1;
	}

	public void setBydouble1(Double bydouble1) {
		this.bydouble1 = bydouble1;
	}

	public Double getBydouble2() {
		return bydouble2;
	}

	public void setBydouble2(Double bydouble2) {
		this.bydouble2 = bydouble2;
	}
    
}