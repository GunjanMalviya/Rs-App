package com.rankerspoint.academy.Model;

public class HomeVideoSeriesListModel {

    public HomeVideoSeriesListModel() {
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getDImageCategoryId() {
        return DImageCategoryId;
    }

    public void setDImageCategoryId(String DImageCategoryId) {
        this.DImageCategoryId = DImageCategoryId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic1() {
        return Pic1;
    }

    public void setPic1(String pic1) {
        Pic1 = pic1;
    }

    public String getFeeStatus() {
        return FeeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        FeeStatus = feeStatus;
    }

    public HomeVideoSeriesListModel(String categoryId, String DImageCategoryId, String courseId, String name, String pic1, String feeStatus) {
        CategoryId = categoryId;
        this.DImageCategoryId = DImageCategoryId;
        CourseId = courseId;
        Name = name;
        Pic1 = pic1;
        FeeStatus = feeStatus;
    }

    private String CategoryId;
    private String DImageCategoryId;
    private String CourseId;
    private String Name;
    private String Pic1;
    private String FeeStatus;


}
