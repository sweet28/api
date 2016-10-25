package com.arttraining.api.pojo;

import java.util.Date;

public class Activities {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private String title;

    private String pic;

    private String purpose;

    private String theWay;

    private String hostOrg;

    private String contractors;

    private String supportOrg;

    private String sponsorOrg;

    private String startTime;

    private String endTime;

    private String signUpStaTime;

    private String signUpEndTime;

    private Integer endFlag;

    private String address;

    private Integer signUpNum;

    private String content;

    private String money;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getTheWay() {
        return theWay;
    }

    public void setTheWay(String theWay) {
        this.theWay = theWay == null ? null : theWay.trim();
    }

    public String getHostOrg() {
        return hostOrg;
    }

    public void setHostOrg(String hostOrg) {
        this.hostOrg = hostOrg == null ? null : hostOrg.trim();
    }

    public String getContractors() {
        return contractors;
    }

    public void setContractors(String contractors) {
        this.contractors = contractors == null ? null : contractors.trim();
    }

    public String getSupportOrg() {
        return supportOrg;
    }

    public void setSupportOrg(String supportOrg) {
        this.supportOrg = supportOrg == null ? null : supportOrg.trim();
    }

    public String getSponsorOrg() {
        return sponsorOrg;
    }

    public void setSponsorOrg(String sponsorOrg) {
        this.sponsorOrg = sponsorOrg == null ? null : sponsorOrg.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getSignUpStaTime() {
        return signUpStaTime;
    }

    public void setSignUpStaTime(String signUpStaTime) {
        this.signUpStaTime = signUpStaTime == null ? null : signUpStaTime.trim();
    }

    public String getSignUpEndTime() {
        return signUpEndTime;
    }

    public void setSignUpEndTime(String signUpEndTime) {
        this.signUpEndTime = signUpEndTime == null ? null : signUpEndTime.trim();
    }

    public Integer getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(Integer endFlag) {
        this.endFlag = endFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSignUpNum() {
        return signUpNum;
    }

    public void setSignUpNum(Integer signUpNum) {
        this.signUpNum = signUpNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }
}