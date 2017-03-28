package com.arttraining.api.pojo;

import java.util.Date;

public class WalletValueTransform {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private Double money;

    private Double cloudMoney;

    private String bystr1;

    private String bystr2;

    private String bystr3;

    private Integer byint1;

    private Integer byint2;

    private Integer byint3;

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getCloudMoney() {
        return cloudMoney;
    }

    public void setCloudMoney(Double cloudMoney) {
        this.cloudMoney = cloudMoney;
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
}