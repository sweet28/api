package com.carpi.api.pojo;

import java.util.Date;

public class FensMiner {
    private Integer id;

    private String bak1;

    private String bak2;

    private String bak3;

    private Integer isDelete;

    private Date createDate;

    private String creater;

    private Date deleteDate;

    private String attachment;

    private Integer minerType;

    private Integer minerId;

    private Integer fensUserId;

    private Double minerComputingPower;

    private Double totalRevenue;

    private Double ableEarn;

    private Double lockEarn;

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

    public Integer getMinerType() {
        return minerType;
    }

    public void setMinerType(Integer minerType) {
        this.minerType = minerType;
    }

    public Integer getMinerId() {
        return minerId;
    }

    public void setMinerId(Integer minerId) {
        this.minerId = minerId;
    }

    public Integer getFensUserId() {
        return fensUserId;
    }

    public void setFensUserId(Integer fensUserId) {
        this.fensUserId = fensUserId;
    }

    public Double getMinerComputingPower() {
        return minerComputingPower;
    }

    public void setMinerComputingPower(Double minerComputingPower) {
        this.minerComputingPower = minerComputingPower;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getAbleEarn() {
        return ableEarn;
    }

    public void setAbleEarn(Double ableEarn) {
        this.ableEarn = ableEarn;
    }

    public Double getLockEarn() {
        return lockEarn;
    }

    public void setLockEarn(Double lockEarn) {
        this.lockEarn = lockEarn;
    }
}