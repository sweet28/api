package com.carpi.api.pojo;

import java.util.Date;

public class FensEarn {
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

    private Integer earnType;

    private Double earnCount;

    private Date earnDate;

    private Integer fensMinerId;

    private Integer earnState;

    private Integer minerType;

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

    public Integer getEarnType() {
        return earnType;
    }

    public void setEarnType(Integer earnType) {
        this.earnType = earnType;
    }

    public Double getEarnCount() {
        return earnCount;
    }

    public void setEarnCount(Double earnCount) {
        this.earnCount = earnCount;
    }

    public Date getEarnDate() {
        return earnDate;
    }

    public void setEarnDate(Date earnDate) {
        this.earnDate = earnDate;
    }

    public Integer getFensMinerId() {
        return fensMinerId;
    }

    public void setFensMinerId(Integer fensMinerId) {
        this.fensMinerId = fensMinerId;
    }

    public Integer getEarnState() {
        return earnState;
    }

    public void setEarnState(Integer earnState) {
        this.earnState = earnState;
    }

    public Integer getMinerType() {
        return minerType;
    }

    public void setMinerType(Integer minerType) {
        this.minerType = minerType;
    }
}