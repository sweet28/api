package com.arttraining.api.pojo;

import java.util.Date;

import com.arttraining.commons.util.ImageUtil;

public class VoteCheer {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private Integer cheerId;

    private String cheerType;

    private String thumbnail;

    private String contentType;

    private String content;

    private String duration;

    private Integer actId;

    private String actType;

    private String cheerName;

    private String headPic;

    private String introduction;

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

    public Integer getCheerId() {
        return cheerId;
    }

    public void setCheerId(Integer cheerId) {
        this.cheerId = cheerId;
    }

    public String getCheerType() {
        return cheerType;
    }

    public void setCheerType(String cheerType) {
        this.cheerType = cheerType == null ? null : cheerType.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        //this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    	this.thumbnail=ImageUtil.parsePicPath(thumbnail, 4);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        //this.content = content == null ? null : content.trim();
    	this.content=ImageUtil.parsePicPath(content, 4);
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType == null ? null : actType.trim();
    }

    public String getCheerName() {
        return cheerName;
    }

    public void setCheerName(String cheerName) {
        this.cheerName = cheerName == null ? null : cheerName.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        //this.headPic = headPic == null ? null : headPic.trim();
    	this.headPic=ImageUtil.parsePicPath(headPic, 4);
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}