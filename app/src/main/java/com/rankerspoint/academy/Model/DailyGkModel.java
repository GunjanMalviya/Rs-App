package com.rankerspoint.academy.Model;

public class DailyGkModel {

    public DailyGkModel(String dailyGKId, String GKCategory, String GKName, String title, String detailsLag1, String detailsLag2, String link, String addDate, String pic1) {
        DailyGKId = dailyGKId;
        this.GKCategory = GKCategory;
        this.GKName = GKName;
        Title = title;
        DetailsLag1 = detailsLag1;
        DetailsLag2 = detailsLag2;
        Link = link;
        AddDate = addDate;
        Pic1 = pic1;
    }

    private String DailyGKId;
    private String GKCategory;
    private String GKName;
    private String Title;
    private String DetailsLag1;
    private String DetailsLag2;
    private String Link;
    private String AddDate;
    private String Pic1;

    public String getDailyGKId() {
        return DailyGKId;
    }

    public void setDailyGKId(String dailyGKId) {
        DailyGKId = dailyGKId;
    }

    public String getGKCategory() {
        return GKCategory;
    }

    public void setGKCategory(String GKCategory) {
        this.GKCategory = GKCategory;
    }

    public String getGKName() {
        return GKName;
    }

    public void setGKName(String GKName) {
        this.GKName = GKName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetailsLag1() {
        return DetailsLag1;
    }

    public void setDetailsLag1(String detailsLag1) {
        DetailsLag1 = detailsLag1;
    }

    public String getDetailsLag2() {
        return DetailsLag2;
    }

    public void setDetailsLag2(String detailsLag2) {
        DetailsLag2 = detailsLag2;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }
}
