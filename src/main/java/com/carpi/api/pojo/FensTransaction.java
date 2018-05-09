package com.carpi.api.pojo;

import java.util.Date;

public class FensTransaction {
    private Integer id;

    private String bak1;

    private String bak2;

    private String bak3;

    private Integer isDelete;

    private Date createDate;

    private String creater;

    private Date deleteDate;

    private String attachment;

    private Integer fensUserId;

    private Integer traderId;

    private Integer traderType;

    private Integer traderState;

    private Double traderCount;

    private Double traferMoneySingle;

    private Double moneyCount;

    private Double entrustPrice;

    private Date traderDate;

    private String orderNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1 == null ? null : bak1.trim();
    }

    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2 == null ? null : bak2.trim();
    }

    public String getBak3() {
        return bak3;
    }

    public void setBak3(String bak3) {
        this.bak3 = bak3 == null ? null : bak3.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public Integer getFensUserId() {
        return fensUserId;
    }

    public void setFensUserId(Integer fensUserId) {
        this.fensUserId = fensUserId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public Integer getTraderState() {
        return traderState;
    }

    public void setTraderState(Integer traderState) {
        this.traderState = traderState;
    }

    public Double getTraderCount() {
        return traderCount;
    }

    public void setTraderCount(Double traderCount) {
        this.traderCount = traderCount;
    }

    public Double getTraferMoneySingle() {
        return traferMoneySingle;
    }

    public void setTraferMoneySingle(Double traferMoneySingle) {
        this.traferMoneySingle = traferMoneySingle;
    }

    public Double getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(Double moneyCount) {
        this.moneyCount = moneyCount;
    }

    public Double getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(Double entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public Date getTraderDate() {
        return traderDate;
    }

    public void setTraderDate(Date traderDate) {
        this.traderDate = traderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }
}