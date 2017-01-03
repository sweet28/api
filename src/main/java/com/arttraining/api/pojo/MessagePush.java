package com.arttraining.api.pojo;

import java.util.Date;

public class MessagePush {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private Integer visitor;

    private String visitorType;

    private Integer host;

    private String hostType;

    private String msgType;

    private String msgContent;

    private Integer statusId;

    private String statusType;

    private String statusPic;

    private Integer foreignKey;

    private Integer ownerId;

    private String ownerType;

    private Integer isRead;

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

    public Integer getVisitor() {
        return visitor;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType == null ? null : visitorType.trim();
    }

    public Integer getHost() {
        return host;
    }

    public void setHost(Integer host) {
        this.host = host;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType == null ? null : hostType.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType == null ? null : statusType.trim();
    }

    public String getStatusPic() {
        return statusPic;
    }

    public void setStatusPic(String statusPic) {
        this.statusPic = statusPic == null ? null : statusPic.trim();
    }

    public Integer getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType == null ? null : ownerType.trim();
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}