package com.arttraining.api.pojo;

public class LiveAuthWithBLOBs extends LiveAuth {
    private String introduction;

    private String resume;

    private String winPrice;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume == null ? null : resume.trim();
    }

    public String getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(String winPrice) {
        this.winPrice = winPrice == null ? null : winPrice.trim();
    }
}