package com.carpi.api.pojo;

import java.util.Date;

public class FensUserInfo {
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

    private Integer sex;

    private String city;

    private Integer age;

    private String birthday;

    private String titlePic;

    private Long fensCount;

    private Double fensComputingPower;

    private Double fensEarningPower;

    private Double minerComputingPower;

    private Integer fensGrade;

    private Double profitBonus;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getTitlePic() {
        return titlePic;
    }

    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic == null ? null : titlePic.trim();
    }

    public Long getFensCount() {
        return fensCount;
    }

    public void setFensCount(Long fensCount) {
        this.fensCount = fensCount;
    }

    public Double getFensComputingPower() {
        return fensComputingPower;
    }

    public void setFensComputingPower(Double fensComputingPower) {
        this.fensComputingPower = fensComputingPower;
    }

    public Double getFensEarningPower() {
        return fensEarningPower;
    }

    public void setFensEarningPower(Double fensEarningPower) {
        this.fensEarningPower = fensEarningPower;
    }

    public Double getMinerComputingPower() {
        return minerComputingPower;
    }

    public void setMinerComputingPower(Double minerComputingPower) {
        this.minerComputingPower = minerComputingPower;
    }

    public Integer getFensGrade() {
        return fensGrade;
    }

    public void setFensGrade(Integer fensGrade) {
        this.fensGrade = fensGrade;
    }

    public Double getProfitBonus() {
        return profitBonus;
    }

    public void setProfitBonus(Double profitBonus) {
        this.profitBonus = profitBonus;
    }
}