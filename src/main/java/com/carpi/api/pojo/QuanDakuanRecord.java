package com.carpi.api.pojo;

import java.util.Date;

public class QuanDakuanRecord {
    private Integer id;

    private String bak1;

    private String bak2;

    private String bak3;

    private String bak4;

    private String bak5;

    private Integer isDelete;

    private Date createDate;

    private String creater;

    private Date deleteDate;

    private String attachment;

    private Integer quanId;

    private Integer dakuangId;

    private String daName;

    private Integer shoukuanId;

    private String shouName;

    private Integer dakuanType;

    private String dakuanImg;

    private Integer type;

    private Date dakuanDate;

    private Date shoukuanDate;

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

    public String getBak4() {
        return bak4;
    }

    public void setBak4(String bak4) {
        this.bak4 = bak4 == null ? null : bak4.trim();
    }

    public String getBak5() {
        return bak5;
    }

    public void setBak5(String bak5) {
        this.bak5 = bak5 == null ? null : bak5.trim();
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

    public Integer getQuanId() {
        return quanId;
    }

    public void setQuanId(Integer quanId) {
        this.quanId = quanId;
    }

    public Integer getDakuangId() {
        return dakuangId;
    }

    public void setDakuangId(Integer dakuangId) {
        this.dakuangId = dakuangId;
    }

    public String getDaName() {
        return daName;
    }

    public void setDaName(String daName) {
        this.daName = daName == null ? null : daName.trim();
    }

    public Integer getShoukuanId() {
        return shoukuanId;
    }

    public void setShoukuanId(Integer shoukuanId) {
        this.shoukuanId = shoukuanId;
    }

    public String getShouName() {
        return shouName;
    }

    public void setShouName(String shouName) {
        this.shouName = shouName == null ? null : shouName.trim();
    }

    public Integer getDakuanType() {
        return dakuanType;
    }

    public void setDakuanType(Integer dakuanType) {
        this.dakuanType = dakuanType;
    }

    public String getDakuanImg() {
        return dakuanImg;
    }

    public void setDakuanImg(String dakuanImg) {
        this.dakuanImg = dakuanImg == null ? null : dakuanImg.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDakuanDate() {
        return dakuanDate;
    }

    public void setDakuanDate(Date dakuanDate) {
        this.dakuanDate = dakuanDate;
    }

    public Date getShoukuanDate() {
        return shoukuanDate;
    }

    public void setShoukuanDate(Date shoukuanDate) {
        this.shoukuanDate = shoukuanDate;
    }
}