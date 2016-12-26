package com.arttraining.api.pojo;

import java.util.Date;

import com.arttraining.commons.util.ImageUtil;

public class ActivityVote {
    private Integer id;

    private String uuid;

    private Integer isDeleted;

    private Date createTime;

    private String orderCode;

    private Date deleteTime;

    private String remarks;

    private String attachment;

    private Integer playerId;

    private String playerName;

    private String playerType;

    private String playerTel;

    private Integer voterId;

    private String voterName;

    private String voterType;

    private String voterTel;

    private Integer foreignKey;

    private Integer popularNum;

    private String headPic;

    private String thumbnail;

    private String contentType;

    private String content;

    private String duration;

    private Integer age;

    private String schoolName;

    private String collegeName;

    private String gradeName;

    private String specName;

    private String city;

    private String cityFather;

    private Integer actId;

    private String actType;

    private String startTime;

    private String endTime;

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

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName == null ? null : playerName.trim();
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType == null ? null : playerType.trim();
    }

    public String getPlayerTel() {
        return playerTel;
    }

    public void setPlayerTel(String playerTel) {
        this.playerTel = playerTel == null ? null : playerTel.trim();
    }

    public Integer getVoterId() {
        return voterId;
    }

    public void setVoterId(Integer voterId) {
        this.voterId = voterId;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName == null ? null : voterName.trim();
    }

    public String getVoterType() {
        return voterType;
    }

    public void setVoterType(String voterType) {
        this.voterType = voterType == null ? null : voterType.trim();
    }

    public String getVoterTel() {
        return voterTel;
    }

    public void setVoterTel(String voterTel) {
        this.voterTel = voterTel == null ? null : voterTel.trim();
    }

    public Integer getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Integer getPopularNum() {
        return popularNum;
    }

    public void setPopularNum(Integer popularNum) {
        this.popularNum = popularNum;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        //this.headPic = headPic == null ? null : headPic.trim();
    	this.headPic=ImageUtil.parsePicPath(headPic, 4);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName == null ? null : collegeName.trim();
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName == null ? null : gradeName.trim();
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCityFather() {
        return cityFather;
    }

    public void setCityFather(String cityFather) {
        this.cityFather = cityFather == null ? null : cityFather.trim();
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}