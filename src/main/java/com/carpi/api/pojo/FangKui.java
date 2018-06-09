package com.carpi.api.pojo;

import java.util.Date;

public class FangKui {
    private Integer id;

    private String bak1;

    private String bak2;

    private String bak3;

    private Integer isDelete;

    private Date createDate;

    private String creater;

    private Date deleteDate;

    private String attachment;

    private Integer gongdanId;

    private String fankuiConent;

    private Integer fankuiType;

    private Integer type;

    private Integer fensUserId;

    private String fensName;

    private Integer kefuId;

    private String kefuName;

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

    public Integer getGongdanId() {
        return gongdanId;
    }

    public void setGongdanId(Integer gongdanId) {
        this.gongdanId = gongdanId;
    }

    public String getFankuiConent() {
        return fankuiConent;
    }

    public void setFankuiConent(String fankuiConent) {
        this.fankuiConent = fankuiConent == null ? null : fankuiConent.trim();
    }

    public Integer getFankuiType() {
        return fankuiType;
    }

    public void setFankuiType(Integer fankuiType) {
        this.fankuiType = fankuiType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFensUserId() {
        return fensUserId;
    }

    public void setFensUserId(Integer fensUserId) {
        this.fensUserId = fensUserId;
    }

    public String getFensName() {
        return fensName;
    }

    public void setFensName(String fensName) {
        this.fensName = fensName == null ? null : fensName.trim();
    }

    public Integer getKefuId() {
        return kefuId;
    }

    public void setKefuId(Integer kefuId) {
        this.kefuId = kefuId;
    }

    public String getKefuName() {
        return kefuName;
    }

    public void setKefuName(String kefuName) {
        this.kefuName = kefuName == null ? null : kefuName.trim();
    }
}